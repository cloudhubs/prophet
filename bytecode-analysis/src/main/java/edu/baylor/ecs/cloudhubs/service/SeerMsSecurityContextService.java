package edu.baylor.ecs.cloudhubs.service;

import edu.baylor.ecs.seer.common.SeerSecurityNode;
import edu.baylor.ecs.seer.common.context.SeerRequestContext;
import edu.baylor.ecs.seer.common.context.SeerSecurityContext;
import edu.baylor.ecs.seer.common.security.SecurityMethod;
import edu.baylor.ecs.seer.common.security.SecurityRootMethod;
import edu.baylor.ecs.seer.common.security.SeerSecurityConstraintViolation;
import edu.baylor.ecs.seer.common.security.SeerSecurityEntityAccessViolation;
import edu.baylor.ecs.seer.common.security.ViolationType;
import javassist.CtClass;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The SeerMsEntityContextService service constructs a
 * {@link edu.baylor.ecs.cli.dence.contexts.context.SeerSecurityContext} from an initial list of
 * {@link javassist.CtClass} and a {@link SeerRequestContext}.
 *
 * @author Jan Svacina
 * @version 1.0
 * @since 0.3.0
 */
@Service
public class SeerMsSecurityContextService {

  /**
   * This method returns a {@link SeerSecurityContext} populated from the global
   * {@link List} of {@link CtClass} objects and a {@link SeerRequestContext}.
   *
   * @param ctClasses the global {@link List} of {@link CtClass} objects
   * @param req       the {@link SeerRequestContext} containing the path to the project to analyze
   * @return a {@link SeerContext} populated with {@link SeerMsContext} objects
   */
  SeerSecurityContext getMsSeerSecurityContext(List<CtClass> ctClasses, SeerRequestContext req) {

    String roleHierarchy = req.getSecurityAnalyzerInterface();
    SeerSecurityNode root = createRoleTree(roleHierarchy);

    SeerSecurityContext securityContext = new SeerSecurityContext(roleHierarchy, root);

    SecurityFilterContext securityFilterContext =
      new SecurityFilterContext(new SecurityFilterGeneralAnnotationStrategy());

    /* Security method contains: name, roles and children */
    Set<SecurityRootMethod> rootMethods = new HashSet<>();

    /* ! getSecurityMethods indeed updates the set, despite the fact it retrieves nothing  */
    for (CtClass ctClass : ctClasses) {
      securityFilterContext.doFilter(ctClass, rootMethods);
    }

    reduceMethodRoles(rootMethods, securityContext.getRoot());
    securityContext.setSecurityRoots(rootMethods);

    Map<String, SecurityMethod> map = buildMap(securityContext);

    List<SecurityMethod> allSecurityMethods = new ArrayList<>();
    for (Map.Entry entry : map.entrySet()) {
      allSecurityMethods.add((SecurityMethod) entry.getValue());
    }

    List<SecurityMethod> violatingMethods = allSecurityMethods
      .stream()
      .filter(x -> x.getRoles().size() > 1)
      .collect(Collectors.toList());

    Set<SeerSecurityConstraintViolation> roleViolations = findEndpointRoleViolations(securityContext, rootMethods);
    roleViolations.addAll(findFlowViolations(securityContext, violatingMethods));
    Set<SeerSecurityEntityAccessViolation> entityAccessViolations = findApiViolations(securityContext, rootMethods);

    securityContext.setRoleViolations(roleViolations);
    securityContext.setEntityAccessViolations(entityAccessViolations);

    return securityContext;
  }

  private SeerSecurityNode createRoleTree(String roleDef) {
    String[] lines = roleDef.split("\n");
    if (lines[0].contains("->")) {
      System.out.println("ERROR! Line 0 should not be an edge!");
      return null;
    }

    SeerSecurityNode roleTree = new SeerSecurityNode(lines[0].replaceAll(" ", ""));

    for (int i = 1; i < lines.length; i++) {
      String line = lines[i].replaceAll(" ", "");
      String[] split = line.split("->");
      if (split.length != 2) {
        System.out.println("ERROR! Bad input line on line " + i + "!");
        return null;
      }
      if (split[0].endsWith("<")) {
        String first = split[0].substring(0, split[0].length() - 1);
        if (! roleTree.insert(split[1], first)) {
          System.out.println("ERROR! Bad input on line " + i + "!\n" +
            "Left hand side of edge must appear earlier as right hand side!");
          return null;
        }
        if (! roleTree.insert(first, split[1])) {
          System.out.println("ERROR! Bad input on line " + i + "!\n" +
            "Left hand side of edge must appear earlier as right hand side!");
          return null;
        }
      } else {
        if (! roleTree.insert(split[1], split[0])) {
          System.out.println("ERROR! Bad input on line " + i + "!\n" +
            "Left hand side of edge must appear earlier as right hand side!");
          return null;
        }
      }
    }

    return roleTree;
  }

  private void reduceMethodRoles(Set<SecurityRootMethod> rootMethods, SeerSecurityNode root) {
    for (SecurityRootMethod method : rootMethods) {
      if (method.getRoles().size() > 0) {
        List<String> roles = new ArrayList<>(method.getRoles());

        int maxDepth = root.depth(getFormattedRoleName(roles.get(0)));
        String minPermission = roles.get(0);

        for (int i = 1; i < roles.size(); i++) {
          int depth = root.depth(getFormattedRoleName(roles.get(i)));
          if (depth > maxDepth) {
            minPermission = roles.get(i);
            maxDepth = depth;
          }
        }

        Set<String> reducedRoles = new HashSet<>();
        reducedRoles.add(minPermission);

        method.setRoles(reducedRoles);

      }
    }
  }

  private Map<String, SecurityMethod> buildMap(SeerSecurityContext context) {

    Map<String, SecurityMethod> allSecurityMethods = new HashMap<>();
    Queue<String> queue = new LinkedList<>();

    for (SecurityRootMethod method : context.getSecurityRoots()) {
      SecurityMethod securityMethod = new SecurityMethod(method.getMethodName(), method.getChildMethods(),
        method.getRoles());
      allSecurityMethods.put(method.getMethodName(), securityMethod);
      queue.add(method.getMethodName());
    }

    do {
      String method = queue.remove();
      SecurityMethod parent = allSecurityMethods.getOrDefault(method, new SecurityMethod(method));

      for (String m : parent.getChildMethods()) {
        SecurityMethod child = allSecurityMethods.getOrDefault(m, new SecurityMethod(m));
        child.getRoles().addAll(parent.getRoles());
        allSecurityMethods.put(child.getMethodName(), child);
      }

      queue.addAll(parent.getChildMethods());

    } while (! queue.isEmpty());


    return allSecurityMethods;
  }

  private Set<SeerSecurityConstraintViolation> findEndpointRoleViolations(SeerSecurityContext context,
                                                                          Set<SecurityRootMethod> allEntryPoints) {
    Set<SeerSecurityConstraintViolation> violations = new HashSet<>();

    for (SecurityRootMethod rootMethod : allEntryPoints) {
      for (String s : rootMethod.getRoles()) {
        if (s.equals("SEER_ALL_ACCESS_ALLOWED")) {
          violations.add(new SeerSecurityConstraintViolation(ViolationType.UNRESTRICTED_ACCESS,
            rootMethod.getMethodName(), rootMethod.getRoles()));
        } else if (context.getRoot().depth(s) == - 1) {
          violations.add(new SeerSecurityConstraintViolation(ViolationType.INVALID_ROLE, rootMethod.getMethodName(),
            rootMethod.getRoles()));
        }
      }
    }

    return violations;
  }

  private Set<SeerSecurityConstraintViolation> findFlowViolations(SeerSecurityContext context,
                                                                  List<SecurityMethod> violatingMethods) {
    Set<SeerSecurityConstraintViolation> violations = new HashSet<>();

    for (SecurityMethod violatingMethod : violatingMethods) {
      List<String> roles = new ArrayList<>(violatingMethod.getRoles());
      String r1 = roles.get(0);
      String r2 = roles.get(1);

      int depth1 = context.getRoot().depth(getFormattedRoleName(r1));
      int depth2 = context.getRoot().depth(getFormattedRoleName(r2));

      if (r1.equals("SEER_ALL_ACCESS_ALLOWED") || r2.equals("SEER_ALL_ACCESS_ALLOWED") || (depth1 != - 1 && depth2 != - 1)) {
        // violations.add(new SeerSecurityConstraintViolation(ViolationType.INVALID_ROLE, violatingMethod));
        if (depth2 > depth1) {
          SeerSecurityNode n1 = context.getRoot().search(getFormattedRoleName(r1));
          boolean hierarchy = n1.childContains(getFormattedRoleName(r2));
          if (hierarchy) {
            violations.add(new SeerSecurityConstraintViolation(ViolationType.HIERARCHY, violatingMethod));
          } else {
            violations.add(new SeerSecurityConstraintViolation(ViolationType.UNRELATED, violatingMethod));
          }
        } else if (depth2 < depth1) {
          SeerSecurityNode n2 = context.getRoot().search(getFormattedRoleName(r2));
          boolean hierarchy = n2.childContains(getFormattedRoleName(r1));
          if (hierarchy) {
            violations.add(new SeerSecurityConstraintViolation(ViolationType.HIERARCHY, violatingMethod));
          } else {
            violations.add(new SeerSecurityConstraintViolation(ViolationType.UNRELATED, violatingMethod));
          }
        } else {
          violations.add(new SeerSecurityConstraintViolation(ViolationType.UNRELATED, violatingMethod));
        }
      }
    }

    return violations;
  }

  private Set<SeerSecurityEntityAccessViolation> findApiViolations(SeerSecurityContext context,
                                                                   Set<SecurityRootMethod> allEntryPoints) {
    Set<SeerSecurityEntityAccessViolation> violations = new HashSet<>();

    List<SecurityRootMethod> asList = new ArrayList<>(allEntryPoints);

    for (int i = 0; i < asList.size() - 1; i++) {
      SecurityRootMethod this_ = asList.get(i);

      for (int j = i + 1; j < asList.size(); j++) {
        SecurityRootMethod that_ = asList.get(j);

        // Check that they are not the same method
        if (! this_.getMethodName().equals(that_.getMethodName())) {

          // If they have the same HTTP type then there could be a violation
          if (this_.getHttpType() == that_.getHttpType()) {

            // If they have the same parameters then there could be a violation
            if (this_.getParameters().equals(that_.getParameters())) {

              // If they have the same return type then there could be a violation
              if (this_.getReturnType() != null && that_.getReturnType() != null && this_.getReturnType().equals(that_.getReturnType())) {

                // If their roles are not equal then it's a violation
                if (! this_.getRoles().equals(that_.getRoles())) {
                  violations.add(new SeerSecurityEntityAccessViolation(ViolationType.ENTITY_ACCESS, this_, that_));
                }
              }
            }
          }

        }
      }
    }

    return violations;
  }

  public String getFormattedRoleName(String roleName) {
    if (roleName.contains("ROLE_")) {
      int startNdx = roleName.indexOf("_") + 1;
      return roleName.substring(startNdx);
    } else {
      return roleName;
    }
  }
}

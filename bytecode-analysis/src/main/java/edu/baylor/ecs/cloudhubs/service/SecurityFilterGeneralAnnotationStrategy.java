package edu.baylor.ecs.cloudhubs.service;

import edu.baylor.ecs.seer.common.security.HttpType;
import edu.baylor.ecs.seer.common.security.SecurityRootMethod;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SecurityFilterGeneralAnnotationStrategy implements SecurityFilterStrategy {

  /*getSecurityMethods*/
  @Override
  public boolean doFilter(CtClass clazz,
                          Set<SecurityRootMethod> securityMethods) {

    if (clazz.getPackageName().startsWith("java.")) {
      return true;
    }

    AnnotationsAttribute annotationsAttribute =
      (AnnotationsAttribute) clazz.getClassFile().getAttribute(AnnotationsAttribute.visibleTag);
    if (annotationsAttribute == null) {
      return true;
    }
    Annotation[] clazzAnnotations = annotationsAttribute.getAnnotations();

    boolean isController = false;
    for (Annotation annotation : clazzAnnotations) {
      if (annotation.getTypeName().equals("org.springframework.web.bind.annotation.RestController")
        || annotation.getTypeName().equals("org.springframework.web.bind.annotation.Controller")) {
        isController = true;
        break;
      }
    }

    if (isController) {
      CtMethod[] methods = clazz.getMethods();
      for (CtMethod ctMethod : methods) {

        if (ctMethod.getLongName().startsWith("java.")) {
          continue;
        }

        MethodInfo methodInfo = ctMethod.getMethodInfo();
        AnnotationsAttribute attr = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);

        List<Annotation> allMethodAnnotations = new ArrayList<>();

        SecurityRootMethod rootMethod = new SecurityRootMethod(ctMethod.getLongName());
        if (securityMethods.stream().noneMatch(x -> x.getMethodName().equals(ctMethod.getLongName()))) {
          securityMethods.add(rootMethod);
        } else {
          rootMethod = securityMethods
            .stream()
            .filter(x -> x.getMethodName().equals(ctMethod.getLongName()))
            .findFirst()
            .get();
        }

        CtClass[] params = new CtClass[0];
        try {
          params = ctMethod.getParameterTypes();
        } catch (NotFoundException e) {
          e.printStackTrace();
        }

        if (params.length > 0) {
          List<CtClass> streamable = Arrays.asList(params);

          List<String> params_s = streamable
            .stream()
            .map(CtClass::getName)
            .collect(Collectors.toList());

          rootMethod.setParameters(params_s);
        }

        String returnType = "";
        try {
          CtClass returnClazz = ctMethod.getReturnType();

          if (returnClazz.subtypeOf(ClassPool.getDefault().get(Collection.class.getName()))) {
            returnType = getReturnType(ctMethod.getGenericSignature());
          } else {
            returnType = returnClazz.getName();
          }
        } catch (NotFoundException e) {
          // returnType = e.getMessage();
        }
        rootMethod.setReturnType(returnType);

        if (attr != null) {
          Annotation[] methodAnnotations = attr.getAnnotations();
          allMethodAnnotations.addAll(Arrays.asList(methodAnnotations));

          boolean hasSecurity = false;
          boolean hasHttpType = false;
          for (Annotation annotation : allMethodAnnotations) {
            if (annotation.getTypeName().equals("javax.annotation.security.RolesAllowed")) {

              hasSecurity = true;

              Set<String> names = annotation.getMemberNames();
              for (String name : names) {
                MemberValue value = annotation.getMemberValue(name);
                if (value instanceof ArrayMemberValue) {
                  ArrayMemberValue amv = (ArrayMemberValue) value;
                  MemberValue[] memberValues = amv.getValue();
                  for (MemberValue mv : memberValues) {
                    String val = mv.toString().replace("\"", "");
                    rootMethod.getRoles().add(val);
                  }
                }
              }

              try {
                ctMethod.instrument(
                  new ExprEditor() {
                    public void edit(MethodCall m) {

                      String fullSignature = "";
                      try {
                        CtMethod meth = m.getMethod();
                        fullSignature = meth.getLongName();
                      } catch (NotFoundException e) {
                        String className = m.getClassName();
                        String methodName = m.getMethodName();
                        String signature = sigToPackage(m.getSignature());
                        fullSignature = className.concat(".").concat(methodName).concat(signature);
                      }

                      final String sig = fullSignature;

                      if (! fullSignature.startsWith("java.")) {
                        securityMethods
                          .stream()
                          .filter(x -> x.getMethodName()
                            .equals(ctMethod.getLongName()))
                          .findFirst()
                          .ifPresent(mthd -> mthd.getChildMethods().add(sig));
                      }
                    }
                  }
                );
              } catch (CannotCompileException cex) {
                System.out.println(cex.toString());
                return false;
              }
            } else if (annotation.getTypeName().equals("org.springframework.web.bind.annotation.PostMapping")) {
              hasHttpType = true;
              rootMethod.setHttpType(HttpType.POST);
            } else if (annotation.getTypeName().equals("org.springframework.web.bind.annotation.GetMapping")) {
              hasHttpType = true;
              rootMethod.setHttpType(HttpType.GET);
            } else if (annotation.getTypeName().equals("org.springframework.web.bind.annotation.PutMapping")) {
              hasHttpType = true;
              rootMethod.setHttpType(HttpType.PUT);
            } else if (annotation.getTypeName().equals("org.springframework.web.bind.annotation.DeleteMapping")) {
              hasHttpType = true;
              rootMethod.setHttpType(HttpType.DELETE);
            } else if (annotation.getTypeName().equals("org.springframework.web.bind.annotation.PatchMapping")) {
              hasHttpType = true;
              rootMethod.setHttpType(HttpType.PATCH);
            } else if (annotation.getTypeName().equals("org.springframework.web.bind.annotation.RequestMapping")) {
              hasHttpType = true;
              rootMethod.setHttpType(HttpType.NONE);
            }
          }

          if (! hasSecurity) {
            rootMethod.getRoles().add("SEER_ALL_ACCESS_ALLOWED");
          }

          if (! hasHttpType) {
            rootMethod.setHttpType(HttpType.NONE);
          }
        } else {
          rootMethod.getRoles().add("SEER_ALL_ACCESS_ALLOWED");
          rootMethod.setHttpType(HttpType.NONE);
        }
      }
    }

    return true;
  }

  private String getReturnType(String sig) {
    if (sig == null) {
      return "";
    }
    String returnType = sig;

    // ()Ljava/util/List<Ledu/baylor/ecs/qms/model/Configuration;>; ->
    // Ljava/util/List<Ledu/baylor/ecs/qms/model/Configuration;>;
    int ndx = sig.lastIndexOf(")");
    returnType = returnType.substring(ndx + 1);

    // Ljava/util/List<Ledu/baylor/ecs/qms/model/Configuration;>; ->
    // java/util/List<Ledu/baylor/ecs/qms/model/Configuration;>;
    if (returnType.startsWith("L")) {
      returnType = returnType.substring(1);
    }

    // java/util/List<Ledu/baylor/ecs/qms/model/Configuration;>; -> java/util/List
    String outerClass = returnType.substring(0, returnType.indexOf("<"));

    // java/util/List -> java.util.List
    outerClass = outerClass.replaceAll("/", ".");

    int startNdx = returnType.indexOf("<") + 1;
    int endNdx = returnType.lastIndexOf(">");

    // java/util/List<Ledu/baylor/ecs/qms/model/Configuration;>; -> Ledu/baylor/ecs/qms/model/Configuration;
    String innerClass = returnType.substring(startNdx, endNdx);

    // Ledu/baylor/ecs/qms/model/Configuration; -> edu/baylor/ecs/qms/model/Configuration;
    if (innerClass.startsWith("L")) {
      innerClass = innerClass.substring(1);
    }

    // edu/baylor/ecs/qms/model/Configuration; -> edu/baylor/ecs/qms/model/Configuration
    innerClass = innerClass.replaceAll(";", "");

    // edu/baylor/ecs/qms/model/Configuration -> edu.baylor.ecs.qms.model.Configuration
    innerClass = innerClass.replaceAll("/", ".");

    return outerClass.concat("<").concat(innerClass).concat(">");
  }

  private String sigToPackage(String sig) {
    // Removes the return value
    String pckge = sig.substring(0, sig.lastIndexOf(")") + 1);

    // Remove parentheses for parsing - will be added at then end
    pckge = pckge.replaceAll("[(]", "");
    pckge = pckge.replaceAll("[)]", "");

    // Ljava/lang/Object; -> java/lang/Object;
    if (pckge.startsWith("L")) {
      pckge = pckge.substring(1);
    }

    // java/lang/Object; -> java.lang.Object;
    pckge = pckge.replaceAll("/", ".");

    // java.lang.Object; -> java.lang.Object
    if (pckge.contains(";")) {
      int count = StringUtils.countOccurrencesOf(pckge, ";");
      if (count == 1) {
        pckge = pckge.replaceAll(";", "");
      } else {
        int lastNdx = pckge.lastIndexOf(";");
        pckge = pckge.substring(0, lastNdx);
        pckge = pckge.replaceAll(";", ", ");
      }
    }

    // java.lang.Object -> (java.lang.Object)
    pckge = "(" + pckge + ")";
    return pckge;
  }
}

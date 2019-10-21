package edu.baylor.ecs.cloudhubs.service;

import edu.baylor.ecs.seer.common.api.SeerApiMethod;
import edu.baylor.ecs.seer.common.api.SeerApiType;
import edu.baylor.ecs.seer.common.components.ComponentModel;
import edu.baylor.ecs.seer.common.context.SeerApiContext;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The SeerMsApiContextService service constructs a
 * {@link edu.baylor.ecs.cli.dence.contexts.context.SeerApiContext} from an initial list of
 * {@link javassist.CtClass}.
 *
 * @author Jan Svacina
 * @version 1.0
 * @since 0.3.0
 */
@Service
public class SeerMsApiContextService {

  /**
   * This method returns a {@link SeerApiContext} populated from a
   * {@link List} of {@link CtClass} objects.
   *
   * @param ctClassesApiIn the {@link List} of {@link CtClass} objects to analyze
   * @return a {@link SeerApiContext} populated from a {@link List} of {@link CtClass} objects
   */
  SeerApiContext createSeerApiContext(List<CtClass> ctClassesApiIn) {

    List<SeerApiMethod> apiMethods = new ArrayList<>();
    List<CtClass> apiClasses = getApiClasses(ctClassesApiIn);
    for (CtClass ctClass : apiClasses) {
      CtMethod[] ctMethods;
      if (ctClass.isInterface()) {
        System.out.println("I am an interface " + ctClass.getName());
        ctMethods = ctClass.getDeclaredMethods();
      } else {
        System.out.println("I am not an interface " + ctClass.getName());
        ctMethods = ctClass.getMethods();
      }
      for (CtMethod ctMethod : ctMethods
      ) {
        SeerApiMethod seerApiMethod = createSeerApiMethod(ctClass, ctMethod);
        if (seerApiMethod.getClassName().contains("edu.baylor.ecs.seer.usermanagement")) {
          apiMethods.add(seerApiMethod);
        }


      }
    }
    SeerApiContext seerApiContext = new SeerApiContext();
    seerApiContext.setSeerApiMethods(apiMethods);
    return seerApiContext;
  }

  /**
   * This method returns a {@link List} of {@link CtClass} objects who are annotated
   * with the {@link javax.ws.rs.Path} annotation
   *
   * @param allClasses the {@link List} of {@link CtClass} objects to analyze
   * @return a {@link List} of {@link CtClass} objects who are annotated with the
   * {@link javax.ws.rs.Path} annotation
   */
  private List<CtClass> getApiClasses(List<CtClass> allClasses) {
    List<CtClass> entityClasses = new ArrayList<>();
    for (CtClass ctClass : allClasses
    ) {
      AnnotationsAttribute annotationsAttribute =
        (AnnotationsAttribute) ctClass.getClassFile().getAttribute(AnnotationsAttribute.visibleTag);
      if (annotationsAttribute != null) {
        Annotation[] annotations = annotationsAttribute.getAnnotations();
        for (Annotation annotation : annotations) {
          if (annotation.getTypeName().equals("javax.ws.rs.Path")) {
            entityClasses.add(ctClass);
          }
        }
      }
    }
    return entityClasses;
  }

  /**
   * This method constructs a {@link SeerApiMethod} from an existing {@link CtClass} and a
   * corresponding {@link CtMethod}
   *
   * @param ctClass  the {@link CtClass} that holds the API method
   * @param ctMethod the {@link CtMethod} that is API method
   * @return a {@link SeerApiMethod} from an existing {@link CtClass} and a corresponding
   * {@link CtMethod}
   */
  private SeerApiMethod createSeerApiMethod(CtClass ctClass, CtMethod ctMethod) {
    SeerApiMethod seerApiMethod = new SeerApiMethod();
    seerApiMethod.setClassName(ctClass.getName());
    seerApiMethod.setMethodName(ctMethod.getLongName());
    seerApiMethod.setSeerApiType(getSeerApiType(ctMethod));
    if (seerApiMethod.getSeerApiType().equals(SeerApiType.OUT)) {
      seerApiMethod.setEntityModel(getReturnType(ctMethod));
    } else {
      seerApiMethod.setEntityModel(getParameterType(ctMethod));
    }
    return seerApiMethod;
  }

  /**
   * This method determines from an {@link CtMethod} if the API call is OUT or IN and
   * returns the corresponding {@link SeerApiType}.
   *
   * @param ctMethod the {@link CtMethod} that is the API method
   * @return the {@link SeerApiType} of the API call for the {@link CtMethod}
   */
  private SeerApiType getSeerApiType(CtMethod ctMethod) {
    Object[] annotations = new Object[]{};
    SeerApiType seerApiType = SeerApiType.IN;
    try {
      annotations = ctMethod.getAnnotations();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    ComponentModel entityModel = getReturnType(ctMethod);
    if (entityModel.getClassName().contains("edu.baylor.ecs.seer.usermanagement")) {
      seerApiType = SeerApiType.OUT;
    }
    //        for (Object annotation: annotations
    //             ) {
    //            if (annotation.getClass().getName().equals("javax.ws.rs.GET")){
    //                seerApiType = SeerApiType.OUT;
    //            } else if (annotation.getClass().getName().equals("javax.ws.rs.POST")){
    //                seerApiType = SeerApiType.IN;
    //            }
    //        }
    return seerApiType;
  }

  private ComponentModel getReturnType(CtMethod ctMethod) {
    ComponentModel entityModel = new ComponentModel();
    try {
      CtClass ctClass = ctMethod.getReturnType();
      entityModel.setClassNameShort(ctClass.getSimpleName());
      entityModel.setClassName(ctClass.getName());
    } catch (NotFoundException e) {
      e.printStackTrace();
    }
    return entityModel;
  }

  private ComponentModel getParameterType(CtMethod ctMethod) {
    ComponentModel entityModel = new ComponentModel();
    try {
      CtClass[] parameterTypes = ctMethod.getParameterTypes();
      for (CtClass p : parameterTypes) {
        System.out.println(p.getName());
        entityModel.setClassNameShort(p.getSimpleName());
        entityModel.setClassName(p.getName());
      }
    } catch (NotFoundException e) {
      e.printStackTrace();
    }
    return entityModel;
  }

}

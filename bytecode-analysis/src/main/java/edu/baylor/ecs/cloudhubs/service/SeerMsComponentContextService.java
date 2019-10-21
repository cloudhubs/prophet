package edu.baylor.ecs.cloudhubs.service;

import edu.baylor.ecs.seer.common.components.ComponentModel;
import edu.baylor.ecs.seer.common.components.ComponentType;
import edu.baylor.ecs.seer.common.context.SeerComponentsContext;
import edu.baylor.ecs.seer.common.entity.SeerEntityRelation;
import edu.baylor.ecs.seer.common.entity.SeerField;
import edu.baylor.ecs.seer.common.entity.SeerFlowMethodRepresentation;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.MemberValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SeerMsComponentContextService {

  Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  // Service for managing the flow aspect of the microservices
  private final FlowStructureService flowService;

  // Service for managing the bytecode flow aspect of the microservices
  private final BytecodeFlowStructureService bytecodeService;

  public SeerMsComponentContextService(FlowStructureService flowService,
                                       BytecodeFlowStructureService bytecodeFlowStructureService) {
    this.flowService = flowService;
    this.bytecodeService = bytecodeFlowStructureService;
  }

  public SeerComponentsContext getComponentClasses(List<CtClass> allClasses) {

    /*
     * ToDo: Repeated operations for all concerns (entity, service, repository, controller)
     */
    SeerComponentsContext seerComponents = new SeerComponentsContext();

    Set<CtClass> entityClasses = new HashSet<>();
    Set<CtClass> controllerClasses = new HashSet<>();
    Set<CtClass> serviceClasses = new HashSet<>();
    Set<CtClass> repositoryClasses = new HashSet<>();
    Set<CtClass> genericClasses = new HashSet<>();
    Set<CtClass> otherClasses = new HashSet<>();

    for (CtClass ctClass : allClasses) {
      ComponentType componentType;

      Optional<Annotation[]> annotations = getAnnotationForClass(ctClass);
      if (annotations.isPresent()) {
        componentType = getComponentWithAnnotation(ctClass, annotations.get());
      } else {
        componentType = getComponentClassWithoutAnnotation(ctClass);
      }

      switch (componentType) {
        case ENTITY:
          entityClasses.add(ctClass);
          break;
        case CONTROLLER:
          controllerClasses.add(ctClass);
          break;
        case SERVICE:
          serviceClasses.add(ctClass);
          break;
        case REPOSITORY:
          repositoryClasses.add(ctClass);
          break;
        case GENERIC_COMPONENT:
          genericClasses.add(ctClass);
          break;
        default:
          otherClasses.add(ctClass);
          break;
      }
    }

    seerComponents.setEntities(deriveComponent(entityClasses, ComponentType.ENTITY));
    seerComponents.setControllers(deriveComponent(controllerClasses, ComponentType.CONTROLLER));
    seerComponents.setServices(deriveComponent(serviceClasses, ComponentType.SERVICE));
    seerComponents.setComponents(deriveComponent(genericClasses, ComponentType.GENERIC_COMPONENT));
    seerComponents.setRepositories(deriveComponent(repositoryClasses, ComponentType.REPOSITORY));
    seerComponents.setOthers(deriveComponent(otherClasses, ComponentType.OTHER_COMPONENT));

    return seerComponents;
  }

  private Optional<Annotation[]> getAnnotationForClass(CtClass ctClass) {
    AnnotationsAttribute annotationsAttribute =
      (AnnotationsAttribute) ctClass.getClassFile().getAttribute(AnnotationsAttribute.visibleTag);
    if (annotationsAttribute != null) {
      Annotation[] annotations = annotationsAttribute.getAnnotations();
      if (annotations.length < 1) {
        return Optional.empty();
      } else {
        return Optional.of(annotations);
      }
    } else {
      return Optional.empty();
    }
  }

  private ComponentType getComponentWithAnnotation(CtClass ctClass, Annotation[] annotations) {
    ComponentType componentType;
    for (Annotation annotation : annotations) {
      componentType = getComponentTypeAnnotation(annotation.getTypeName());
      if (componentType != null) return componentType;
    }
    return getComponentClassWithoutAnnotation(ctClass);
  }

  private ComponentType getComponentClassWithoutAnnotation(CtClass ctClass) {
    if (Arrays.asList(ctClass.getClassFile2().getInterfaces())
      .contains("org.springframework.data.repository.Repository")) {
      return ComponentType.REPOSITORY;
    }
    return ComponentType.OTHER_COMPONENT;
  }

  private Set<ComponentModel> deriveComponent(Set<CtClass> componentClasses, ComponentType componentType) {

    /*
     * ToDo: Different strategy for annotations on fields and setters (FieldAnnotationStrategy...)
     * ToDo: @Column does not have to be necessary included!
     * ToDo: separate building the object, field aggregation, field processing, find matching setter
     */

    Set<ComponentModel> components = new HashSet<>();

    // Loop through every class
    for (CtClass clazz : componentClasses) {

      // Create a new ComponentModel for the class
      ComponentModel component = new ComponentModel(clazz.getName());
      component.setClassNameShort(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1));


      component.setFields(setFields(clazz, componentType));
      component.setMethods(setMethods(clazz));

      // set type
      component.setComponentType(componentType);

      // set ctClass
      setCtClassOptions(component, clazz);

      // Add the entity to the list
      components.add(component);
    }

    return components;
  }

  private void setCtClassOptions(ComponentModel component, CtClass ctClass) {
    ComponentModel.CtType ctType;

    if(ctClass.isInterface()) {
      ctType = ComponentModel.CtType.INTERFACE_TYPE;
    } else if(ctClass.isEnum()) {
      ctType = ComponentModel.CtType.ENUM_TYPE;
    } else {
      ctType = ComponentModel.CtType.CLASS_TYPE;
    }

    component.setCtType(ctType);
    component.setExtendingClass(ctClass.getClassFile2().getSuperclass());
    component.setImplementingInterfaces(ctClass.getClassFile2().getInterfaces());
  }

  private static ComponentType getComponentTypeAnnotation(String annotation) {
    switch (annotation) {
      // spring boot annotations
      case "javax.persistence.Entity":
        return ComponentType.ENTITY;

      case "org.springframework.stereotype.Controller":
      case "org.springframework.web.bind.annotation.RestController":
        return ComponentType.CONTROLLER;

      case "org.springframework.stereotype.Service":
        // ejb annotations
      case "javax.ejb.Stateless":
      case "javax.ejb.Stateful":
      case "javax.ejb.MessageDrivenBean":
        return ComponentType.SERVICE;

      case "org.springframework.stereotype.Repository":
        return ComponentType.REPOSITORY;

      case "org.springframework.stereotype.Component":
        return ComponentType.GENERIC_COMPONENT;

    }
    return null;
  }

  private List<SeerField> setFields(CtClass clazz, ComponentType componentType) {
    // Get all the public and private fields
    CtField[] fields = clazz.getFields();
    CtField[] privateFields = clazz.getDeclaredFields();
    Set<CtField> aggregateFields = new HashSet<>();
    aggregateFields.addAll(Arrays.asList(fields));
    aggregateFields.addAll(Arrays.asList(privateFields));

    List<SeerField> seerFields = new ArrayList<>();

    // Loop through all of the instance fields
    for (CtField field : aggregateFields) {

      // SeerField init
      SeerField seerField = new SeerField();
      seerField.setName(field.getName());

      try {
        String fullType = field.getType().getName();
        String type = fullType.substring(fullType.lastIndexOf('.') + 1);

        // We don't want a relationship with List or Set objects
        if (type.equals("List") || type.equals("Set")) {
          continue;
        }

        seerField.setFullType(fullType);
        seerField.setType(type);


      } catch (NotFoundException e) {
        logger.error("Ignoring field " + field.getName());
      }

      if (componentType == ComponentType.ENTITY) {
        applyEntityOptions(field, seerField);
      }

      seerFields.add(seerField);

    }
    return seerFields;
  }

  private List<SeerFlowMethodRepresentation> setMethods(CtClass clazz) {
    List<SeerFlowMethodRepresentation> methodRepresentations = flowService.processClazz(clazz);
    bytecodeService.process(methodRepresentations);
    return methodRepresentations;
  }

  private void applyEntityOptions(CtField field, SeerField seerField) {
    // Get the attributes and loop through them
    AnnotationsAttribute annotationsAttribute =
      (AnnotationsAttribute) field.getFieldInfo().getAttribute(AnnotationsAttribute.visibleTag);
    if (annotationsAttribute != null) {

      Annotation[] annotations = annotationsAttribute.getAnnotations();

      // Loop through annotations on field
      for (Annotation annotation : annotations) {

        // ToDo: Refactor this switch statement to be more elegant
        // https://stackoverflow.com/questions/126409/ways-to-eliminate-switch-in-code

        String annotationType = annotation.getTypeName();
        switch (annotationType) {
          case ("javax.persistence.ManyToOne"):
            seerField.setSeerEntityRelation(SeerEntityRelation.MANYTOONE);
            break;
          case ("javax.persistence.OneToMany"):
            seerField.setSeerEntityRelation(SeerEntityRelation.ONETOMANY);
            break;
          case ("javax.validation.constraints.NotNull"):
            seerField.setNotNull(true);
            break;
          case ("javax.validation.constraints.Size"):
            MemberValue max = annotation.getMemberValue("max");
            if (max != null) {
              seerField.setMax(Integer.parseInt(max.toString()));
            }
            MemberValue min = annotation.getMemberValue("min");
            if (min != null) {
              seerField.setMin(Integer.parseInt(min.toString()));
            }
            break;
          default:
            // default empty on purpose
        }

      }
    }
  }

  //  private static ComponentType getComponentTypeRaw(String componentName) {
  //    String name = componentName.toLowerCase();
  //    if(name.contains("repository")) return ComponentType.REPOSITORY;
  //    if(name.contains("service")) return ComponentType.SERVICE;
  //    if(name.contains("controller")) return ComponentType.CONTROLLER;
  //    if(name.contains("entity")) return ComponentType.ENTITY;
  //    else return ComponentType.GENERIC_COMPONENT;
  //  }


}

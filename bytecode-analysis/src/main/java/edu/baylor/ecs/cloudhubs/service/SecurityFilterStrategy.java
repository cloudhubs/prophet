package edu.baylor.ecs.cloudhubs.service;

import edu.baylor.ecs.seer.common.security.SecurityRootMethod;
import javassist.CtClass;

import java.util.Set;

public interface SecurityFilterStrategy {

  boolean doFilter(CtClass clazz, Set<SecurityRootMethod> methods);

}

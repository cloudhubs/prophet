package edu.baylor.ecs.cloudhubs.service;

import edu.baylor.ecs.seer.common.security.SecurityRootMethod;
import javassist.CtClass;

import java.util.Set;

public class SecurityFilterContext {

  private SecurityFilterStrategy strategy;

  public SecurityFilterContext(SecurityFilterStrategy strategy) {
    this.strategy = strategy;
  }

  public boolean doFilter(CtClass clazz,
                          Set<SecurityRootMethod> methods) {
    return strategy.doFilter(clazz, methods);
  }

}

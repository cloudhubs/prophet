package edu.baylor.ecs.cloudhubs.prophet.service;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import static org.junit.jupiter.api.Assertions.*;

class DbSystemServiceTest {

  @Autowired
  private DbSystemService dbSystemService;

  @Autowired
  private EmbeddedDb embeddedDb;

  @BeforeClass
  public void beforeClass(){
    embeddedDb.registerDb();

  }

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void createSystem() {
  }

  @Test
  void changeName() {
  }

  @Test
  void deleteSystem() {
  }

  @Test
  void deleteModuleRelRec() {
  }

  @Test
  void createModuleRel() {
  }
}
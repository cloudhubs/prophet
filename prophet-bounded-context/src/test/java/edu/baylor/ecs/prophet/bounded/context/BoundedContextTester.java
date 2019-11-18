package edu.baylor.ecs.prophet.bounded.context;

import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.*;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.Module;
import edu.baylor.ecs.prophet.bounded.context.exception.FieldMappingException;
import edu.baylor.ecs.prophet.bounded.context.utils.BoundedContextUtils;
import edu.baylor.ecs.prophet.bounded.context.utils.impl.BoundedContextUtilsImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class BoundedContextTester {

    private static SystemContext simpleSystem = null;
    private static BoundedContextUtils boundedContextUtils;
    private static Module moduleOne;
    private static Module moduleTwo;
    private static Entity dogEntity;
    private static Entity catentity;
    private static Entity dogAndCatEntityAllFields;

    @BeforeAll
    public static void  initSystems(){
        SystemContext systemContext = new SystemContext("test", null);
        boundedContextUtils = new BoundedContextUtilsImpl();

        // MODULE ONE *******************************************************
        moduleOne = new Module("Module One");

        // person entity
        Entity person = new Entity("Person");
        Field personName = new Field("string", "name");
        Field personId = new Field("int", "id");
        Field personWeight = new Field("long", "weight");
        person.setFields(Arrays.asList(personName, personId, personWeight));

        // dog entity
        Entity dog = new Entity("dog");
        Field dogBreed = new Field("string", "breed");
        Field dogWeight = new Field("long", "weight");
        Field dogName = new Field("string", "name");
        Field dogOwner = new Field("object", "owner");
        dogOwner.setEntityReference(person);
        dog.setFields(Arrays.asList(dogBreed, dogWeight, dogName));

        // cat entity
        Entity cat = new Entity("cat");
        Field catBreed = new Field("string", "breed");
        Field catWeight = new Field("long", "weight");
        Field catName = new Field("string", "name");
        cat.setFields(Arrays.asList(catBreed, catWeight, catName));
        Field catOwner = new Field("object", "owner");
        catOwner.setEntityReference(person);

        moduleOne.setEntities(Arrays.asList(person, dog, cat));

        // MODULE TWO *******************************************************
        moduleTwo = new Module("Module two");

        // person entity
        Entity m2_person = new Entity("Person");
        Field  m2_personName = new Field("string", "name");
        Field  m2_personId = new Field("long", "id");
        Field  m2_personWeight = new Field("string", "weight");
        Field  m2_personEthnicity = new Field("string", "ethnicity");
        m2_person.setFields(Arrays.asList(m2_personName, m2_personId, m2_personWeight, m2_personEthnicity));

        // car entity
        Entity m2_car = new Entity("Car");
        Field  car_model = new Field("string", "model");
        Field  car_brand = new Field("string", "brand");
        Field  car_color = new Field("long", "color");
        Field  car_serial_number = new Field("string", "serialNumber");
        Field  car_base_price = new Field("string", "base price");
        m2_car.setFields(Arrays.asList(car_model, car_brand, car_color, car_serial_number, car_base_price));

        // car entity
        Entity m2_motorcycle = new Entity("Motorcycle");
        Field  moto_model = new Field("string", "model");
        Field  moto_brand = new Field("string", "brand");
        Field  moto_vin = new Field("long", "vin");
        Field  moto_engine = new Field("string", "engine");
        m2_motorcycle.setFields(Arrays.asList(moto_model, moto_brand, moto_vin, moto_engine));

        moduleTwo.setEntities(Arrays.asList(m2_person, m2_car, m2_motorcycle));

        // associate the modules with the system context
        systemContext.setModules(Arrays.asList(moduleOne, moduleTwo));

        simpleSystem = systemContext;
        dogEntity = dog;
        catentity = cat;
        dogAndCatEntityAllFields = new Entity();
        dogAndCatEntityAllFields.setEntityName("dog and cat");
        List<Field> mixedFields = new ArrayList<>(dogEntity.getFields());
        mixedFields.addAll(catentity.getFields());
        dogAndCatEntityAllFields.setFields(mixedFields);
    }

    @Nested
    @DisplayName("Field merging")
    public class FieldMerging{

        private void mergeAndCompare(Field one, Field two, Field result){
            Field merged = boundedContextUtils.mergeFields(one, two);

            assertEquals(result, merged);
        }

        @Nested
        @DisplayName("null field")
        public class NullField{
            @Test
            public void nullFieldOne(){
                Field field = new Field("int", "foo");
                assertThrows(NullPointerException.class, () -> boundedContextUtils.mergeFields(null, field));
            }
            @Test
            public void nullFieldTwo(){
                Field field = new Field("int", "foo");
                assertThrows(NullPointerException.class, () -> boundedContextUtils.mergeFields(field, null));
            }
            @Test
            public void nullBothFields(){
                Field field = new Field("int", "foo");
                assertThrows(NullPointerException.class, () -> boundedContextUtils.mergeFields(null, null));
            }
        }

        @Test
        @DisplayName("same field")
        public void testSameField(){
            Field field = new Field("int", "foo");

            mergeAndCompare(field, field, field);

        }

        @Test
        @DisplayName("equal field")
        public void testEqualField(){
            Field field = new Field("int", "foo");
            Field field2 = new Field("int", "foo");

            mergeAndCompare(field, field2, field);

        }

        @Nested
        @DisplayName("Differing Types")
        public class DifferentTypes{

            @DisplayName("{0} x {1} -> {2}")
            @ParameterizedTest
            @CsvSource({"int, long, long", "short, int, int", "short, long, long", "byte, long, long", "int, string, string", "byte, string, string", "byte, char, char"})
            public void testDiffTypesOne(String typeOne, String typeTwo, String resultType){
                Field field = new Field(typeOne, "foo");
                Field field2 = new Field(typeTwo, "foo");
                Field result = new Field(resultType, "foo");

                mergeAndCompare(field, field2, result);
            }

            @Test
            @DisplayName("differing annotations")
            public void testAnnotations(){
                Field field = new Field("int", "foo");
                Field field2 = new Field("int", "bar");
                Field ExpectedResult = new Field("int", "foo");

                Annotation ann1 = new Annotation("annotationOne", "", 1);
                Annotation ann2 = new Annotation("annotationTwo", "", 2);
                Annotation ann3 = new Annotation("annotationThree", "", 3);
                Annotation ann4 = new Annotation("annotationFour", "", 4);
                Annotation ann5 = new Annotation("annotationFive", "", 5);

                field.setAnnotations(new HashSet<>(Arrays.asList(ann1, ann2, ann3)));
                field2.setAnnotations(new HashSet<>(Arrays.asList(ann3, ann4, ann5)));

                ExpectedResult.setAnnotations(new HashSet<>(Arrays.asList(ann1, ann2, ann3, ann4, ann5)));

                Field result = boundedContextUtils.mergeFields(field, field2);

                assertEquals(result.getAnnotations(), ExpectedResult.getAnnotations());
            }
        }

    }

    @Nested
    @DisplayName("Entity Merging tests")
    public class EntityMergingTests{

        @Test
        @DisplayName("null field mapping")
        public void testNullFieldMapping(){
            Entity result = boundedContextUtils.mergeEntities(dogEntity, catentity, null);
            assertEquals(result.getFields().size(), dogAndCatEntityAllFields.getFields().size());
        }

        @Test
        @DisplayName("empty field mapping")
        public void testEmptyFieldMapping(){
            Entity result = boundedContextUtils.mergeEntities(dogEntity, catentity, new HashMap<>());
            assertEquals(result.getFields().size(), dogAndCatEntityAllFields.getFields().size());
        }

        @Test
        @DisplayName("Mapping to non existant field")
        public void testNonExistantFieldInMappingDest(){
            HashMap<Field, Field> fieldMapping = new HashMap<>();
            fieldMapping.put(dogEntity.getFields().get(0), new Field("string", "NO_EXISTO"));
            assertThrows(FieldMappingException.class, () -> boundedContextUtils.mergeEntities(dogEntity, catentity, fieldMapping));
        }

        @Test
        @DisplayName("Mapping from non existant field")
        public void testNonExistantFieldInMappingSource(){
            HashMap<Field, Field> fieldMapping = new HashMap<>();
            fieldMapping.put(new Field("string", "NO_EXISTO"), dogEntity.getFields().get(0));
            assertThrows(FieldMappingException.class, () -> boundedContextUtils.mergeEntities(dogEntity, catentity, fieldMapping));
        }

        @Test
        @DisplayName("test single mapping")
        public void testGoodMapping(){
            HashMap<Field, Field> fieldMapping = new HashMap<>();
            fieldMapping.put(dogEntity.getFields().get(0), catentity.getFields().get(0));
            Entity result = boundedContextUtils.mergeEntities(dogEntity, catentity, fieldMapping);
            assertEquals(result.getFields().size(), dogAndCatEntityAllFields.getFields().size() - 1);
        }

        @Test
        @DisplayName("test two mappings to the same field")
        public void testDoubleMapping(){
            HashMap<Field, Field> fieldMapping = new HashMap<>();
            fieldMapping.put(dogEntity.getFields().get(0), catentity.getFields().get(0));
            fieldMapping.put(dogEntity.getFields().get(1), catentity.getFields().get(0));
            assertThrows(FieldMappingException.class, () -> boundedContextUtils.mergeEntities(dogEntity, catentity, fieldMapping));
        }

        @Test
        @DisplayName("test size n mapping")
        public void testNMapping(){
            HashMap<Field, Field> fieldMapping = new HashMap<>();
            for(int i = 0; i< dogEntity.getFields().size(); i++){
                fieldMapping.put(dogEntity.getFields().get(i), catentity.getFields().get(i));
            }
            Entity result = boundedContextUtils.mergeEntities(dogEntity, catentity, fieldMapping);
            // all dog fields are consumed in the transformation
            assertEquals(result.getFields().size(), catentity.getFields().size());
        }

    }

    @Nested
    @DisplayName("Module Testing")
    public class ModuleTesting{

        @Test
        @DisplayName("Merge Modules")
        public void testModuleMerge(){
            Module returnModule = boundedContextUtils.mergeModules(moduleOne, moduleTwo);
            assertNotNull(returnModule.getName());
            assertNotEquals("", returnModule.getName());
            assertTrue(returnModule.getEntities().size() <= moduleOne.getEntities().size() + moduleTwo.getEntities().size());
        }
    }
}

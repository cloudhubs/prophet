package edu.baylor.ecs.prophet.bounded.context.runner;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.*;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Module;

import java.io.*;
import java.util.*;

public class BoundedContextRunner {

    public static void main(String[] args) {

        // create a scanner for stdin
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        SystemContext systemContext = getSystemContext(in, System.out);
    }

    private static SystemContext getSystemContext(Scanner in, PrintStream out){
        // get the name of the system context
        out.println("This Runner will allow you to create a System Context to then merge into a Bounded Context");
        out.println("Enter the name of the System Context");
        String systemName = in.nextLine();

        // get the number of modules in the system context
        out.println("Enter the number of Modules that will be in the System Context");
        int numModules = in.nextInt();

        List<Module> moduleList = new ArrayList<>();

        // read in the data for all of the modules
        for(int i = 0; i < numModules; ++i){

            // get the module name
            out.printf("Enter data for module %d:\n", i);
            out.println("module name");
            String moduleName = in.nextLine();

            // get the number of entities in this module
            out.println("Enter the number of entities in the module");
            int entityNum = in.nextInt();

            // enter each of the entities names
            out.println("Enter the names of the entities each on their own line");
            Map<String, Entity> entityNames = new HashMap<>();
            for(int j=0; j < entityNum; ++j){
                String entityName = in.nextLine();
                Entity e = new Entity();
                e.setEntityName(entityName);
                entityNames.put(entityName, e);
            }

            List<Entity> entityList = new ArrayList<Entity>();

            // iterate over all of the entities in this module
            for(Map.Entry<String, Entity> entityName : entityNames.entrySet()){

                // get the number of fields in this entity
                out.printf("Entering data for entity %s in module %s", entityName.getKey());
                out.println("How many fields are in this entity");
                int fieldNum = in.nextInt();

                // the list that will hold all of the fields
                List<Field> entityFields = new ArrayList<>();

                // read in the data for all of the fields
                for (int k = 0; k < fieldNum; k++){

                    // read in the field name
                    out.printf("Entering data for field %d in entity $s in module %d", k, entityName.getKey(), moduleName);
                    out.println("Field name:");
                    String fieldName = in.nextLine();

                    // read in the field type
                    out.println("field type");
                    String fieldType = in.nextLine();

                    // get if this is an entity reference
                    out.println("Is this an entity reference? t/f");
                    boolean isEntityReference = in.nextBoolean();

                    Entity entityReference = null;

                    // if it is an entity reference
                    if(isEntityReference){

                        // entity type should always be objects
                        if(!"object".equals(fieldType.toLowerCase())){
                            System.err.println("Error: entity reference must be object");
                            fieldType = "object";
                        }

                        // make sure that the entity it references exists IN THIS MODULE
                        if(!entityNames.containsKey(fieldName)){
                            System.err.println("Error: the referenced entity does not exist in this module");
                            continue;
                        }

                        // get the entity reference
                        entityReference = entityNames.get(fieldName);

                    }
                    // now create the field
                    // TODO get the annotations
                    Field f = new Field(fieldName, fieldType);
                    f.setEntityReference(entityReference);
                    entityFields.add(f);
                }
                Entity e = new Entity(entityName.getKey());
                e.setFields(entityFields);
                entityList.add(e);
            }
            moduleList.add(new Module(moduleName, entityList));
        }
        return new SystemContext(systemName, moduleList);
    }
}

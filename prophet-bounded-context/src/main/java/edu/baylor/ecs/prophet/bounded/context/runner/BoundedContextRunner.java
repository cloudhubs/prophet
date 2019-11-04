package edu.baylor.ecs.prophet.bounded.context.runner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BoundedContextRunner {

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        System.out.println("This Runner will allow you to create a System Context to then merge into a Bounded Context");
        System.out.println("Enter the name of the System Context");
        String systemName = in.nextLine();
        System.out.println("Enter the number of Modules that will be in the System Context");
        int numModules = in.nextInt();

        for(int i = 0; i < numModules; ++i){
            System.out.printf("Enter data for module %d:\n", i);
            System.out.println("module name");
            String moduleName = in.nextLine();
            System.out.println("Enter the number of entities in the module");
            int entityNum = in.nextInt();
            System.out.println("Enter the names of the entities each on their own line");
            List<String> entityNames = new LinkedList<>();
            for(int j=0; j < entityNum; ++j){
                entityNames.add(in.nextLine());
            }

            for(String entityName : entityNames){
                System.out.printf("Entering data for entity %s in module %s", entityName);
                System.out.println("How many fields are in this entity");
                int fieldNum = in.nextInt();
                for (int k = 0; k < fieldNum; k++){
                    System.out.printf("Entering data for field %d in entity $s in module %d", k, entityName, moduleName);
                    System.out.println("Field name:");
                    String fieldName = in.nextLine();
                    System.out.println("field type");
                    String fieldType = in.nextLine();
                    System.out.println("Is this an entity reference? t/f");
                    boolean isEntityReference = in.nextBoolean();
                    if(isEntityReference){
                        if("object".equals(fieldType.toLowerCase())){
                            System.err.println("Error: entity reference must be object");
                        }
                        fieldType = "object";
                        if(!entityNames.contains(fieldName)){
                            System.err.println("Error: the referenced entity does not exist in this module");
                            continue;
                        }
                    }
                }
            }
        }
    }
}

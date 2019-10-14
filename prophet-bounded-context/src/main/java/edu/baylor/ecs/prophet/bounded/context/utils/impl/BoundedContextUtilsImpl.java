package edu.baylor.ecs.prophet.bounded.context.utils.impl;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.*;
import edu.baylor.ecs.prophet.bounded.context.utils.BoundedContextUtils;
import org.neo4j.cypher.internal.compiler.v2_3.No;

import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class BoundedContextUtilsImpl implements BoundedContextUtils {

    @Override
    public BoundedContext mergeEntities(SystemContext systemContext) {
        return null;
    }

    @Override
    public Entity merge(Entity one, Entity two, Map<Field, Field> fieldMapping) {
        Entity newEntity = new Entity();
        newEntity.setEntityName(one.getEntityName());
        Set<Field> entityTwoFields = new HashSet<>(two.getFields());
        Field toAdd = null;

        for(Field f1 : one.getFields()){
            Field f2 = fieldMapping.get(f1);
            toAdd = f1;

            if(f2 != null) {

                //make sure that mapped to field is present in entity 2
                if (!entityTwoFields.remove(f2)) {
                    throw new NoSuchElementException("Field not found in entity 2 " + f2.toString());
                }

                //see if they are both entity references to different things
                if(f1.getEntityReference() != null && f2.getEntityReference() != null){
                    newEntity.getFields().add(f2);
                    toAdd = f1;
                }
                else {
                    toAdd = mergeFields(f1, f2);
                }
            }
            newEntity.getFields().add(toAdd);
        }

        // add all of the remaining fields in entity 2
        newEntity.getFields().addAll(entityTwoFields);

        return newEntity;
    }

    @Override
    public Field mergeFields(Field one, Field two) {
        Field toReturn = new Field();

        // set the name
        toReturn.setName(one.getName());

        // set the type
        toReturn.setType(Type.get(one.getName()).ordinal() < Type.get(two.getName()).ordinal() ? two.getName() : one.getName());

        // set the annotations
        toReturn.setAnnotations(one.getAnnotations());
        toReturn.getAnnotations().addAll(two.getAnnotations());

        // ASSUMED THAT BOTH DO NOT HAVE ENTITY REFERENCES
        toReturn.setEntityReference(one.getEntityReference() == null ? two.getEntityReference() : one.getEntityReference());

        return toReturn;
    }
}

/**
 * Copyright 2019, Cloud Innovation Labs, All rights reserved
 * Version: 1.0
 */

package edu.baylor.ecs.prophet.bounded.context.utils.impl;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.*;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Module;
import edu.baylor.ecs.prophet.bounded.context.utils.BoundedContextUtils;
import edu.baylor.ecs.prophet.bounded.context.utils.SimilarityUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * methods for creating a {@link BoundedContext} from a {@link SystemContext}
 * @author Ian laird
 */
public class BoundedContextUtilsImpl implements BoundedContextUtils {

    // tools used for finding similarities
    private SimilarityUtils similarityUtils = new SimilarityUtilsImpl();

    /**
     * creates a bounded context for the system context
     * @param systemContext the system
     * @return the bounded context
     */
    @Override
    public BoundedContext createBoundedContext(SystemContext systemContext) {
        return null;
    }

    /**
     * merges two modules into one module
     * @param moduleOne one of the modules
     * @param moduleTwo the other module
     * @return a new module comprised of the other two
     */
    @Override
    public Module mergeModules(Module moduleOne, Module moduleTwo){

        // for each entity find the similarity it has to other entities
        final Map<Entity, TreeMap<Double, ImmutablePair<Entity, Map<Field, Field>>>>
                entitySimilarity = new HashMap<>();

        // get all entities in module one
        moduleOne.getEntities()

            // for each entity in entity one add to entity similarity
            .forEach(x -> entitySimilarity.put(

                // the current entity of module one
                x,

                // create stream of entity two entities
                moduleTwo.getEntities().stream()

                    // create map
                    .collect(Collectors.toMap(

                        // similarity of entity from module one and entity from module two
                        y -> similarityUtils.globalFieldSimilarity(x, y).getLeft(),

                        // tuple of entity from module two and field mapping
                        y -> new ImmutablePair<>(y,similarityUtils.globalFieldSimilarity(x, y).getRight()),
                        (oldValue,newValue) -> newValue,
                        TreeMap::new
                    ))
                ));

        Module newModule = new Module();

        // sets the entities of the new module
        newModule.setEntities(

                // stream of all entries in entitysimilarity
                entitySimilarity.entrySet().stream()

                        // map each mapping between entities to a new merged entity
                        .map(x -> mergeEntities(x.getKey(), x.getValue().lastEntry().getValue().getLeft(), x.getValue().lastEntry().getValue().getRight()))

                        // collect as a list
                        .collect(Collectors.toList())
        );

        return null;
    }

    /**
     * merges two entities together using the field mapping
     * @param one the first entity to merge
     * @param two the second entity to merge
     * @param fieldMapping the mapping between the fields of the entities
     * @return the newly creataed merged entity
     */
    @Override
    public Entity mergeEntities(Entity one, Entity two, Map<Field, Field> fieldMapping) {
        Entity newEntity = new Entity();
        newEntity.setEntityName(one.getEntityName());
        Set<Field> entityTwoFields = new HashSet<>(two.getFields());
        Field toAdd = null;

        // for each field in in entity one
        for(Field f1 : one.getFields()){
            Field f2 = fieldMapping.get(f1);
            toAdd = f1;

            if(f2 != null) {

                //make sure that mapped to field is present in entity 2
                if (!entityTwoFields.remove(f2)) {
                    throw new NoSuchElementException("Field not found in entity 2 " + f2.toString());
                }

                //see if they are both entity references to different things add them both
                if(f1.getEntityReference() != null && f2.getEntityReference() != null && !f2.equals(f1)){
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

    /**
     * merges two fields into one field
     * @param one the first field to merge
     * @param two the second field to merge
     * @return the new field
     */
    @Override
    public Field mergeFields(Field one, Field two) {
        Field toReturn = new Field();

        // set the name to the name of the first one
        toReturn.setName(one.getName());

        // set the type to whichever is a more 'general' type
        toReturn.setType(Type.get(one.getName()).ordinal() < Type.get(two.getName()).ordinal() ? two.getName() : one.getName());

        // set the annotations
        toReturn.setAnnotations(one.getAnnotations());
        toReturn.getAnnotations().addAll(two.getAnnotations());

        // ASSUMED THAT BOTH DO NOT HAVE ENTITY REFERENCES
        toReturn.setEntityReference(one.getEntityReference() == null ? two.getEntityReference() : one.getEntityReference());

        return toReturn;
    }
}

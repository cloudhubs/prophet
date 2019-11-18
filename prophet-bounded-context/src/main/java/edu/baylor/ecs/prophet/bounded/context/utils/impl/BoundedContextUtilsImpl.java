/*
 * Copyright 2019, Cloud Innovation Labs, All rights reserved
 * Version: 1.0
 */

package edu.baylor.ecs.prophet.bounded.context.utils.impl;


import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.*;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.Module;

import edu.baylor.ecs.prophet.bounded.context.exception.FieldMappingException;
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

    private static double ENTITY_SIMILARITY_CUTOFF = .9;

    /**
     * creates a bounded context for the system context
     * @param systemContext the system
     * @return the bounded context
     */
    @Override
    public BoundedContext createBoundedContext(SystemContext systemContext) {

        // take all of the modules in the System context and merge them
        Optional<Module> mergedModule = systemContext.getModules().stream().reduce(this::mergeModules);

        // make sure that the modules were able to merge properly
        if(!mergedModule.isPresent()){
            throw new RuntimeException("Unable to merge the Contexts");
        }
        Module m = mergedModule.get();

        //create the bounded context
        BoundedContext toReturn = new BoundedContext(systemContext.getSystemName(), m.getEntities());
        return toReturn;
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

                        // tuple of entity from module two
                        y -> new ImmutablePair<>(y,
                                //and the field mapping
                                similarityUtils.globalFieldSimilarity(x, y).getRight()),
                        (oldValue,newValue) -> newValue,
                        TreeMap::new
                    ))
                ));

        Module newModule = new Module();

        newModule.setName(moduleOne.getName());
        newModule.setEntities(new LinkedList<>());

        // sets the entities of the new module
        newModule.getEntities().addAll(

                // stream of all entries in entitysimilarity
                entitySimilarity.entrySet().stream()

                        // if the similarity is strong enough
                        .filter(x -> {
                            Map.Entry<Double, ImmutablePair<Entity, Map<Field, Field>>> val = x.getValue().lastEntry();
                            double similarity = val.getKey();

                            if(similarity > ENTITY_SIMILARITY_CUTOFF){
                                return true;
                            }
                            else{
                                newModule.getEntities().add(x.getKey());
                                newModule.getEntities().add(val.getValue().getLeft());
                                return false;
                            }
                        })

                        // map each mapping between entities to a new merged entity
                        .map(x -> mergeEntities(x.getKey(), x.getValue().lastEntry().getValue().getLeft(), x.getValue().lastEntry().getValue().getRight()))

                        // collect as a list
                        .collect(Collectors.toList())
        );

        return newModule;
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

        // the entity that is to be returned
        Entity newEntity = new Entity();

        // set the entity name to the name of the first entity
        newEntity.setEntityName(one.getEntityName());

        // get the fields of the second entity
        Set<Field> entityTwoFields = new HashSet<>(two.getFields());
        Field toAdd = null;

        if(Objects.isNull(fieldMapping)){
            fieldMapping = new HashMap<>();
        }

        Set<Field> alreadySeen = new HashSet<>();
        // make sure that all fields in the field mapping are also in f1 and that no two map to the same value
        for(Map.Entry<Field, Field> f : fieldMapping.entrySet()){
            // TODO make this more efficient
            if(!one.getFields().contains(f.getKey()) || !alreadySeen.add(f.getValue())){
                throw new FieldMappingException();
            }
        }

        // for each field in entity one
        for (Field f1 : one.getFields()){

            // get the field that this field in entity one maps to
            Field f2 = fieldMapping.get(f1);
            toAdd = f1;

            if (f2 != null) {

                //make sure that mapped to field is present in entity 2
                if (!entityTwoFields.remove(f2)) {

                    // will be thrown if the entity mapping is invalid
                    throw new FieldMappingException("Field not found in entity 2 " + f2.toString());
                }

                //see if they are both entity references to different things add them both
                if(f1.getEntityReference() != null && f2.getEntityReference() != null && !f2.equals(f1)){
                    newEntity.getFields().add(f2);
                    toAdd = f1;
                }

                // merge the fields into one field
                else {
                    toAdd = mergeFields(f1, f2);
                }
            }

            // add the field
            // TODO what if a field of this name already exists?
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
        String name = one.getName();
        String type = Type.get(one.getType()).ordinal() < Type.get(two.getType()).ordinal() ? two.getType() : one.getType();

        Field toReturn = new Field(type, name);

        // set the annotations
        toReturn.setAnnotations(one.getAnnotations());
        toReturn.getAnnotations().addAll(two.getAnnotations());

        // set the entity reference (if it is present)
        // ASSUMED THAT BOTH DO NOT HAVE DIFFERENT ENTITY REFERENCES
        toReturn.setEntityReference(one.getEntityReference() == null ? two.getEntityReference() : one.getEntityReference());

        return toReturn;
    }
}

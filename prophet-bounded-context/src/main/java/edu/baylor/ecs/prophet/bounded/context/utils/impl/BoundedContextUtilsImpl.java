package edu.baylor.ecs.prophet.bounded.context.utils.impl;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.*;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Module;
import edu.baylor.ecs.prophet.bounded.context.utils.BoundedContextUtils;
import edu.baylor.ecs.prophet.bounded.context.utils.SimilarityUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.neo4j.cypher.internal.compiler.v2_3.No;

import java.util.*;
import java.util.stream.Collectors;

/**
 * methods for creating a {@link BoundedContext} from a {@link SystemContext}
 * @author Ian laird
 */
public class BoundedContextUtilsImpl implements BoundedContextUtils {

    private SimilarityUtils similarityUtils = new SimilarityUtilsImpl();
    @Override
    public BoundedContext createBoundedContext(SystemContext systemContext) {
        return null;
    }

    @Override
    public Module mergeModules(Module moduleOne, Module moduleTwo){

        // for each entity find the similarity it has to other entities
        final Map<Entity, TreeMap<Double, ImmutablePair<Entity, Map<Field, Field>>>>  entitySimilarity = new HashMap<>();

        moduleOne.getEntities()
                .forEach(x -> entitySimilarity.put(
                        x,
                        moduleTwo.getEntities()
                                .stream()
                                .collect(Collectors.toMap(
                                        y -> similarityUtils.globalFieldSimilarity(x, y).getLeft(),
                                        y -> new ImmutablePair<>(y,similarityUtils.globalFieldSimilarity(x, y).getRight()),
                                        (oldValue,newValue) -> newValue,
                                        TreeMap::new
                                ))
                ));

        Module newModule = new Module();
        newModule.setEntities(entitySimilarity.entrySet().stream().map(x -> mergeEntities(x.getKey(), x.getValue().lastEntry().getValue().getLeft(), x.getValue().lastEntry().getValue().getRight())).collect(Collectors.toList()));

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

    /**
     * merges two fields into one field
     * @param one the first field to merge
     * @param two the second field to merge
     * @return the new field
     */
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

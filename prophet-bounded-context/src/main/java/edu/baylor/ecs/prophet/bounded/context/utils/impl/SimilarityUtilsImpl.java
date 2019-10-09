package edu.baylor.ecs.prophet.bounded.context.utils.impl;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Entity;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Field;
import edu.baylor.ecs.prophet.bounded.context.utils.SimilarityUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;
import java.util.stream.Collectors;

public class SimilarityUtilsImpl implements SimilarityUtils {

    private static final double ENTITY_NAME_SIMILARITY_CUTOFF = .8;
    @Override
    public double localFieldSimilarity(Field fieldOne, Field fieldTwo) {
        return 0;
    }

    @Override
    public double globalFieldSimilarity(Entity entityOne, Entity entityTwo) {
        // see if the names of the entities are similar
        double nameSimilarity = stringSimilarity(entityOne.getEntityName(), entityOne.getEntityName());
        if(nameSimilarity > ENTITY_NAME_SIMILARITY_CUTOFF){
            return nameSimilarity;
        }

        // for each field find the similarity they have to other fields
        final Map<Field, TreeMap<Double, Field>>  fieldSimilarity = new HashMap<>();

        entityOne.getFields()
                .forEach(x -> fieldSimilarity.put(
                        x,
                        entityTwo.getFields()
                                .stream()
                                .collect(Collectors.toMap(
                                        y -> localFieldSimilarity(x, y),
                                        y -> y,
                                        (oldValue,newValue) -> newValue,
                                        TreeMap::new
                                ))
                ));

        boolean changeOccurred = true;
        while(changeOccurred){
            Map<Field, ImmutablePair<Double, TreeMap<Double, Field>>> encountered = new HashMap<>();
            for(Map.Entry<Field, TreeMap<Double, Field>> entry : fieldSimilarity.entrySet()){
                //get the best field of entity 2 for this field of entity 1
                Field best = entry.getValue().lastEntry().getValue();
                Double val = entry.getValue().lastKey();
                if(encountered.containsKey(best)){
                    changeOccurred = true;
                    // if this one is better then remove the old one
                    if(val > encountered.get(best).getLeft()){
                        encountered.get(best).getRight().remove(best);
                        encountered.put(best, new ImmutablePair<>(val, entry.getValue()));
                    }
                    // if the other one is better then remove this one
                    else {
                        entry.getValue().remove(best);
                    }
                }
            }
        }

        return 0;
    }

    @Override
    public double stringSimilarity(String one, String two) {
        return 0;
    }
}

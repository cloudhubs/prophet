package edu.baylor.ecs.prophet.bounded.context.utils.impl;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Entity;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Field;
import edu.baylor.ecs.prophet.bounded.context.utils.SimilarityUtils;

public class SimilarityUtilsImpl implements SimilarityUtils {

    @Override
    public double localFieldSimilarity(Field fieldOne, Field fieldTwo) {
        return 0;
    }

    @Override
    public double globalFieldSimilarity(Entity entityOne, Entity entityTwo) {
        // see if the names of the entities are similar
        return 0;
    }

    @Override
    public double stringSimilarity(String one, String two) {
        return 0;
    }
}

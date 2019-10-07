package edu.baylor.ecs.prophet.bounded.context.utils;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Entity;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Field;

public interface SimilarityUtils {

    double localFieldSimilarity(Field fieldOne, Field fieldTwo);

    double globalFieldSimilarity(Entity entityOne, Entity entityTwo);

    double stringSimilarity(String one, String two);
}

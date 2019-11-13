/**
 * Copyright 2019, Cloud Innovation Labs, All rights reserved
 * Version: 1.0
 */

package edu.baylor.ecs.prophet.bounded.context.utils;

import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.Entity;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.Field;

import org.apache.commons.lang3.tuple.ImmutablePair;
import java.util.Map;

/**
 * similaity util methods
 */
public interface SimilarityUtils {

    /**
     * finds the similarity between two fields
     *
     * @param fieldOne the first field to compare
     * @param fieldTwo the second field to compare
     * @return the similarity of the fields
     */
    double localFieldSimilarity(Field fieldOne, Field fieldTwo);

    /**
     * find the similarity of two entities
     *
     * @param entityOne first entity to find similarity of
     * @param entityTwo second entity to find similarity of
     * @return tuple of similarity of the entities as well as the mapping between their fields
     */
    ImmutablePair<Double, Map<Field, Field>> globalFieldSimilarity(Entity entityOne, Entity entityTwo);

    /**
     * finds the similalrity of two names (i.e. nouns)
     *
     * @param one the first name to comapare
     * @param two the second name to compare
     * @return the Wu Palmer similarity of these names
     */
    double nameSimilarity(String one, String two);
}

/**
 * Copyright 2019, Cloud Innovation Labs, All rights reserved
 * Version: 1.0
 */

package edu.baylor.ecs.prophet.bounded.context.utils.impl;

import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.Entity;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.Field;

import edu.baylor.ecs.prophet.bounded.context.utils.SimilarityUtils;
import edu.cmu.lti.jawjaw.pobj.POS;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.lexical_db.data.Concept;
import edu.cmu.lti.ws4j.Relatedness;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ian Laird
 */
public class SimilarityUtilsImpl implements SimilarityUtils {

    // the cutoff for when to consider two entities to be similar
    private static final double ENTITY_NAME_SIMILARITY_CUTOFF = .8;

    // used for finding Wu Palmer similarity *******************
    private static ILexicalDatabase db = new NictWordNet();
    private static RelatednessCalculator rc = new WuPalmer(db);
    // *********************************************************

    /**
     * stores last computer entity similarity to try and short circuit computation
     */
    private class EntitySimilarity {

        // the first entity
        Entity entityOne = null;

        // the second entity
        Entity entityTwo = null;

        // the computed similarity and mapping between the fields
        ImmutablePair<Double, Map<Field, Field> > savedVal;
    }

    // stores last computer entity similarity to try and short circuit computation
    private EntitySimilarity lastComputedEntitySimilarity = new EntitySimilarity();

    /**
     * finds the similarity between two fields
     * @param fieldOne the first field to compare
     * @param fieldTwo the second field to compare
     * @return the similarity of the fields
     */
    @Override
    public double localFieldSimilarity(Field fieldOne, Field fieldTwo) {
        //TODO is this good enough?
        return nameSimilarity(fieldOne.getName(), fieldTwo.getName());
    }

    /**
     * find the similarity of two entities
     * @param entityOne first entity to find similarity of
     * @param entityTwo second entity to find similarity of
     * @return tuple of similarity of the entities as well as the mapping between their fields
     */
    @Override
    public ImmutablePair<Double, Map<Field, Field> > globalFieldSimilarity(Entity entityOne, Entity entityTwo) {
        //store the result of the last comp
        // intentionally using == here instead of equals because we only short circuit for exact objects
        if(entityOne == lastComputedEntitySimilarity.entityOne && entityTwo == lastComputedEntitySimilarity.entityTwo){
            return lastComputedEntitySimilarity.savedVal;
        }

        // if the entity names are too dissimilar then dont try
        double nameSimilarity = nameSimilarity(entityOne.getEntityName(), entityOne.getEntityName());
        if(nameSimilarity < ENTITY_NAME_SIMILARITY_CUTOFF){
            return new ImmutablePair<>(0.0, new HashMap<>());
        }

        // for each field find the similarity they have to other fields
        final Map<Field, TreeMap<Double, Field>>  fieldSimilarity = new HashMap<>();

        // get each field in entity one
        entityOne.getFields()
                // for each field put
                .forEach(x -> fieldSimilarity.put(
                        // that field
                        x,
                        // and a map of fields and similarity
                        entityTwo.getFields()
                                .stream()
                                .collect(Collectors.toMap(
                                        // the similarity of field one and field 2
                                        y -> localFieldSimilarity(x, y),
                                        // field 2
                                        y -> y,
                                        (oldValue,newValue) -> newValue,
                                        // using tree map so that it is sorted
                                        TreeMap::new
                                ))
                ));

        boolean changeOccurred = true;

        // keep going until no two fields map to the same field
        // this is really slow
        while(changeOccurred){
            changeOccurred = false;
            Map<Field, ImmutablePair<Double, TreeMap<Double, Field>>> encountered = new HashMap<>();
            for(Map.Entry<Field, TreeMap<Double, Field>> entry : fieldSimilarity.entrySet()){
                boolean repeat = true;

                // this will always execute at least once
                while(repeat) {
                    repeat = false;

                    //get the similarity and field that are best from entity two for this field in entity one
                    Map.Entry<Double, Field> bestEntry = entry.getValue().lastEntry();
                    if(Objects.nonNull(bestEntry)) {
                        Field best = bestEntry.getValue();
                        Double val = bestEntry.getKey();

                        // see if a field already maps to this field
                        if (encountered.containsKey(best)) {

                            // if this one is better then remove the old one and restart the whole algorithm
                            if (val > encountered.get(best).getLeft()) {
                                encountered.get(best).getRight().remove(best);
                                encountered.put(best, new ImmutablePair<>(val, entry.getValue()));
                                changeOccurred = true;
                            }
                            // if the other one is better then remove this one and get the next best mapping
                            else {
                                entry.getValue().remove(best);
                                repeat = true;
                            }
                        }
                    }
                }
            }
        }

        // get the average of the field similarity
        Double similarity = fieldSimilarity.entrySet().stream().mapToDouble(entry -> entry.getValue().isEmpty() ? 0.0 : entry.getValue().lastKey()).average().getAsDouble();

        // get the field mapping
        Map<Field, Field> fieldMap = fieldSimilarity
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                x -> x.getKey(),
                x->
                    // if there is a field put it in the mapping, else put null
                    {
                        return x.getValue().isEmpty() ? null : x.getValue().lastEntry().getValue();
                    }
            ));

        // compute the return value
        ImmutablePair<Double, Map<Field, Field> > toReturn = new ImmutablePair<>(similarity, fieldMap);

        // save the computed values to maybe short circuit next time
        lastComputedEntitySimilarity.entityOne = entityOne;
        lastComputedEntitySimilarity.entityTwo = entityTwo;
        lastComputedEntitySimilarity.savedVal = toReturn;
        return toReturn;
    }

    /**
     * finds the similarity of two names (i.e. nouns)
     * @param one the first name to compare
     * @param two the second name to compare
     * @return the Wu Palmer similarity of these names
     */
    @Override
    public double nameSimilarity(String one, String two) {
        return wordSimilarity(one, POS.n, two, POS.n);
    }

    /**
     * finds the wu palmer similarity of two words
     * @param word1 the first word to compare
     * @param posWord1 the pos of the first word
     * @param word2 the second word to compare
     * @param posWord2 the pos of the second word
     * @return the wu palmer similarity of the words given their pos
     */
    // https://blog.thedigitalgroup.com/words-similarityrelatedness-using-wupalmer-algorithm
    private static double wordSimilarity(String word1, POS posWord1, String word2, POS posWord2) {
        double maxScore = 0.0;
        try {
            WS4JConfiguration.getInstance().setMFS(true);
            List<Concept> synsets1 = (List < Concept > ) db.getAllConcepts(word1, posWord1.name());
            List < Concept > synsets2 = (List < Concept > ) db.getAllConcepts(word2, posWord2.name());
            for (Concept synset1: synsets1) {
                for (Concept synset2: synsets2) {
                    Relatedness relatedness = rc.calcRelatednessOfSynset(synset1, synset2);
                    double score = relatedness.getScore();
                    if (score > maxScore) {
                        maxScore = score;
                    }
                }
            }
        } catch (Exception e) {}
        return maxScore;
    }
}

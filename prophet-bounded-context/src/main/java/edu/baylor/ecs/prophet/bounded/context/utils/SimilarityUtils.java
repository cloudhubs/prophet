package edu.baylor.ecs.prophet.bounded.context.utils;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Entity;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Field;
import edu.cmu.lti.jawjaw.pobj.POS;
import edu.cmu.lti.lexical_db.data.Concept;
import edu.cmu.lti.ws4j.Relatedness;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;
import java.util.Map;

public interface SimilarityUtils {

    double localFieldSimilarity(Field fieldOne, Field fieldTwo);

    ImmutablePair<Double, Map<Field, Field>> globalFieldSimilarity(Entity entityOne, Entity entityTwo);

    double nameSimilarity(String one, String two);

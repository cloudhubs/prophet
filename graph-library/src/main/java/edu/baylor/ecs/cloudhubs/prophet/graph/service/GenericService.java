package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import org.neo4j.ogm.session.Session;

abstract class GenericService<T> implements Service<T> {

    private static final int DEPTH_LIST = 0;
    private static final int DEPTH_ENTITY = 1;

    //attempt to create a generic service, because session factory does not work, this does not work as well

//    protected Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

//    @Override
//    public Iterable<T> findAll() {
//        return session.loadAll(getEntityType(), DEPTH_LIST)
//    }
//
//    @Override
//    T find(Long id) {
//        return session.load(getEntityType(), id, DEPTH_ENTITY)
//    }
//
//    @Override
//    void delete(Long id) {
//        session.delete(session.load(getEntityType(), id))
//    }
//
//    @Override
//    T createOrUpdate(T entity) {
//        session.save(entity, DEPTH_ENTITY)
//        return find(entity.id)
//    }
//
//    abstract Class<T> getEntityType()
}
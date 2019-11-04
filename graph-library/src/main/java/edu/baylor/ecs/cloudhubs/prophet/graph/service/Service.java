package edu.baylor.ecs.cloudhubs.prophet.graph.service;

interface Service<T> {

    Iterable<T> findAll();

    T find(Long id);

    void delete(Long id);

    T createOrUpdate(T object);

}
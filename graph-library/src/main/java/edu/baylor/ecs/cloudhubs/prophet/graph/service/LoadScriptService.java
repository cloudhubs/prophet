package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

@Service
public class LoadScriptService {


    public LoadScriptService(){}

    @Autowired
    private Session session;

    @Transactional
    public void clearDatabase() {
        session.purgeDatabase();
    }

    @Transactional
    public void load(String path) {
        StringBuilder sb = new StringBuilder();
        ClassLoader t = Thread.currentThread().getContextClassLoader();
//        ClassLoader t = this.getClass().getClassLoader();
        InputStream s = t.getResourceAsStream(path);

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(s));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(" ");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String cqlFile = sb.toString();
        session.query(cqlFile, Collections.EMPTY_MAP);
    }
}
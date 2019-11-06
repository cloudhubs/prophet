package edu.baylor.ecs.cloudhubs.prophet.application.services;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;


@Service
public class LoadDataService {

    private Session session;

    @Autowired
    public LoadDataService(Session session) {
        this.session = session;
    }

    @Transactional
    public void clearDatabase() {
        session.purgeDatabase();
    }

    @Transactional
    public void load(String path) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(path)));
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

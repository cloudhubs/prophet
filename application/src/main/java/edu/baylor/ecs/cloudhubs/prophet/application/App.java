package edu.baylor.ecs.cloudhubs.prophet.application;

import edu.baylor.ecs.cloudhubs.prophet.application.services.DataService;
import edu.baylor.ecs.cloudhubs.prophet.application.services.LoadDataService;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.repository.DbClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

//@SpringBootApplication(scanBasePackages = "edu.baylor.ecs.cloudhubs.prophet")
//@EnableNeo4jRepositories("edu.baylor.ecs.cloudhubs.prophet.graph.repository")
//@RestController



//@SpringBootApplication(scanBasePackages = "edu.baylor.ecs.cloudhubs.prophet")
//@EnableNeo4jRepositories("edu.baylor.ecs.cloudhubs.prophet.graph.repository")
@EntityScan("edu.baylor.ecs.cloudhubs.prophet.metamodel.db")
@SpringBootApplication
@EnableNeo4jRepositories("edu.baylor.ecs.cloudhubs.prophet.metamodel.repository")
public class App {

//    private final MyService myService;
//
//    private final EmbeddedDb embeddedDb;
//
//    private final DbSystemService dbSystemService;
//
//    public App(MyService myService, EmbeddedDb embeddedDb, DbSystemService dbSystemService) {
//        this.myService = myService;
//        this.embeddedDb = embeddedDb;
//        this.dbSystemService = dbSystemService;
//    }
//
//    @GetMapping("/system")
//    public String home() {
//        embeddedDb.registerDb();
//        return myService.message();
//    }
//
//    @GetMapping("/all")
//    public Iterable<DbSystem> getAll(){
//        embeddedDb.registerDb();
//        dbSystemService.createByName("My Service X");
//        Iterable<DbSystem> it = dbSystemService.getAll();
//        embeddedDb.shutDown();
//        return it;
//
//    }

//
//    private SystemService service;
//
//    private SystemRepository systemRepository;

    private final static Logger log = LoggerFactory.getLogger(App.class);


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner demo(LoadDataService loadDataService, DataService dataService) {
        return args -> {

//            dataService.insertData();

            //dataService.createSystem();


            loadDataService.load("bounded-context.cql");

//            Module a = new Module();
//            a.setName("ModuleA");
//            a = moduleRepository.save(a);
//
//            System s = new System("SystemA");
//            s.addModule(a);
//            systemRepository.save(s);
//
//            Iterable<System> all = systemRepository.findAll();
//            all.forEach(n -> log.info(n.toString()));

//            DbClass dbClass = new DbClass();
//            dbClass.setName("ClassC");
//            dbClassRepository.save(dbClass);


//            personRepository.deleteAll();
//
//            loadDataService.load();
//
//            Person greg = new Person("Greg");
//            Person roy = new Person("Roy");
//            Person craig = new Person("Craig");
//
//            List<Person> team = Arrays.asList(greg, roy, craig);
//
//            log.info("Before linking up with Neo4j...");
//
//            team.stream().forEach(person -> log.info("\t" + person.toString()));
//
//            personRepository.save(greg);
//            personRepository.save(roy);
//            personRepository.save(craig);
//
//            greg = personRepository.findByName(greg.getName());
//            greg.worksWith(roy);
//            greg.worksWith(craig);
//            personRepository.save(greg);
//
//            roy = personRepository.findByName(roy.getName());
//            roy.worksWith(craig);
//            // We already know that roy works with greg
//            personRepository.save(roy);
//
//            // We already know craig works with roy and greg
//
//            log.info("Lookup each person by name...");
//            team.stream().forEach(person -> log.info(
//                    "\t" + personRepository.findByName(person.getName()).toString()));
//
//            List<Person> people = personRepository.queryNodesAll();
//            people.stream().forEach(n -> log.info(n.toString()));


        };
    }
}
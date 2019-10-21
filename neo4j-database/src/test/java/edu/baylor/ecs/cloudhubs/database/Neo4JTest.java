package edu.baylor.ecs.cloudhubs.database;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.neo4j.test.rule.TestDirectory;

public class Neo4JTest {

//    @Rule
//    public TestDirectory testDirectory = TestDirectory.testDirectory();
//    protected GraphDatabaseService graphDb;
//
//    /**
//     * Create temporary database for each unit test.
//     */
//    // tag::beforeTest[]
//    @Before
//    public void prepareTestDatabase()
//    {
//        graphDb = new TestGraphDatabaseFactory().newImpermanentDatabase( testDirectory.directory() );
//    }
//    // end::beforeTest[]
//
//    /**
//     * Shutdown the database.
//     */
//    // tag::afterTest[]
//    @After
//    public void destroyTestDatabase()
//    {
//        graphDb.shutdown();
//    }
//    // end::afterTest[]
//
//    @Test
//    public void startWithConfiguration()
//    {
//        // tag::startDbWithConfig[]
//        GraphDatabaseService db = new TestGraphDatabaseFactory()
//                .newImpermanentDatabaseBuilder()
//                .setConfig( GraphDatabaseSettings.pagecache_memory, "512M" )
//                .setConfig( GraphDatabaseSettings.string_block_size, "60" )
//                .setConfig( GraphDatabaseSettings.array_block_size, "300" )
//                .newGraphDatabase();
//        // end::startDbWithConfig[]
//        db.shutdown();
//    }
//
//    @Test
//    public void shouldCreateNode() {
//        // tag::unitTest[]
//        Node n;
//        try (Transaction tx = graphDb.beginTx()) {
//            n = graphDb.createNode();
//            n.setProperty("name", "Nancy");
//            tx.success();
//        }
//
//        // The node should have a valid id
//        assertThat(n.getId(), is(greaterThan(-1L)));
//
//        // Retrieve a node by using the id of the created node. The id's and
//        // property should match.
//        try (Transaction tx = graphDb.beginTx()) {
//            Node foundNode = graphDb.getNodeById(n.getId());
//            assertThat(foundNode.getId(), is(n.getId()));
//            assertThat((String) foundNode.getProperty("name"), is("Nancy"));
//        }
//        // end::unitTest[]
//    }
}

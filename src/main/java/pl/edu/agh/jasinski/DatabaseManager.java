package pl.edu.agh.jasinski;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.Schema;

import java.io.File;
import java.util.Optional;

public class DatabaseManager {
    private File dbDirectory;
    private GraphDatabaseService graphDb;

    void connectToDatabase(String dbDir) {
        this.dbDirectory = new File(dbDir);
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbDirectory);
        registerShutdownHook(graphDb);
    }

    void setUpDatabase() {
        createIndex(graphDb);
        DataCreator dc = new DataCreator(this);
    }

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    private void createIndex(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            Schema schema = graphDb.schema();
            schema.indexFor(Label.label("SENSOR"))
                    .on("SENSOR_NAME")
                    .create();
            schema.indexFor(Label.label("MEASUREMENT"))
                    .on("ID")
                    .create();
            tx.success();
        }
    }

    String runCypher(String cypher) {
        try (Transaction transaction = graphDb.beginTx()) {
            final Result result = graphDb.execute(cypher);
            transaction.success();
            return result.resultAsString();
        }
    }

    public Optional<Node> createNode(Label label) {
        Node node;
        try (Transaction tx = graphDb.beginTx()) {
            node = graphDb.createNode(label);
            tx.success();
        }
        return Optional.ofNullable(node);
    }

    public void setProperty(Node node, String key, Object value) {
        try (Transaction tx = graphDb.beginTx()) {
            node.setProperty(key, value);
            tx.success();
        }
    }

    public Optional<Relationship> createRelationShip(Node from, Node to, RelationshipType relationshipType) {
        Relationship relationship;
        try (Transaction tx = graphDb.beginTx()) {
            relationship = from.createRelationshipTo(to, relationshipType);
            tx.success();
        }
        return Optional.ofNullable(relationship);
    }

    public void setRelationshipProperty(Relationship relationship, String key, Object value) {
        try (Transaction tx = graphDb.beginTx()) {
            relationship.setProperty(key, value);
            tx.success();
        }
    }
}

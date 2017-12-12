package pl.edu.agh.jasinski.domain;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import pl.edu.agh.jasinski.DatabaseManager;

import java.util.Optional;

public class Measurement {
    private static final Label SENSOR_LABEL = Label.label("MEASUREMENT");
    private DatabaseManager databaseManager;
    private long id;
    private double usage;
    private String unit;
    private Node node;

    public Measurement(DatabaseManager databaseManager, long id, double usage, String unit) {
        this.databaseManager = databaseManager;
        this.id = id;
        this.usage = usage;
        this.unit = unit;
        Optional<Node> optionalNode = createNode();
        optionalNode.ifPresent(node -> this.node = node);
    }

    private Optional<Node> createNode() {
        Optional<Node> node = databaseManager.createNode(SENSOR_LABEL);
        node.ifPresent(n -> databaseManager.setProperty(n, "ID", this.id));
        node.ifPresent(n -> databaseManager.setProperty(n, "USAGE", this.usage));
        node.ifPresent(n -> databaseManager.setProperty(n, "UNIT", this.unit));
        return node;
    }

    Node getNode() {
        return node;
    }
}

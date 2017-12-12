package pl.edu.agh.jasinski.domain;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import pl.edu.agh.jasinski.DatabaseManager;

import java.util.Optional;

public class Sensor {
    private static final String SENSOR_NAME = "SENSOR_NAME";
    private static Label SENSOR_LABEL = Label.label("SENSOR");
    private DatabaseManager databaseManager;
    private String sensorName;
    private Node node;

    public Sensor(DatabaseManager databaseManager, String sensorName) {
        this.databaseManager = databaseManager;
        this.sensorName = sensorName;
        Optional<Node> optionalNode = createNode();
        optionalNode.ifPresent(node -> this.node = node);
    }

    private Optional<Node> createNode() {
        Optional<Node> node = databaseManager.createNode(SENSOR_LABEL);
        node.ifPresent(n -> databaseManager.setProperty(n, SENSOR_NAME, this.sensorName));
        return node;
    }

    Node getNode() {
        return node;
    }
}

package pl.edu.agh.jasinski.domain;

import org.neo4j.graphdb.Relationship;
import pl.edu.agh.jasinski.DatabaseManager;

import java.util.Optional;

public class NetworkUse {
    private Sensor sensor;
    private Measurement measurement;
    private long measurementDate;
    private String networkUseType;
    private DatabaseManager databaseManager;
    private Relationship relationship;

    public NetworkUse(Sensor sensor, Measurement measurement, long measurementDate, String networkUseType, DatabaseManager databaseManager) {
        this.sensor = sensor;
        this.measurement = measurement;
        this.measurementDate = measurementDate;
        this.networkUseType = networkUseType;
        this.databaseManager = databaseManager;
        Optional<Relationship> relationship = createRelationship();
        relationship.ifPresent(r -> this.relationship = r);
    }

    private Optional<Relationship> createRelationship() {
        Optional<Relationship> relationship = databaseManager.createRelationShip(this.sensor.getNode(), this.measurement.getNode(), RelTypes.NETWORK_USE);
        relationship.ifPresent(r -> databaseManager.setRelationshipProperty(r, "DATE", this.measurementDate));
        relationship.ifPresent(r -> databaseManager.setRelationshipProperty(r, "NETWORK_USE_TYPE", networkUseType));
        return relationship;
    }

    public Relationship getRelationship() {
        return relationship;
    }
}

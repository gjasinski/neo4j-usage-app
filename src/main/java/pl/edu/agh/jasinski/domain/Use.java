package pl.edu.agh.jasinski.domain;


import org.neo4j.graphdb.Relationship;
import pl.edu.agh.jasinski.DatabaseManager;

import java.util.Date;
import java.util.Optional;

public class Use {
    private Relationship relationship;
    private Sensor sensor;
    private Measurement measurement;
    private long measurementDate;
    private String measurementType;
    private DatabaseManager databaseManager;

    public Use(Sensor sensor, Measurement measurement, long measurementDate, String measurementType, DatabaseManager databaseManager) {
        this.sensor = sensor;
        this.measurement = measurement;
        this.measurementDate = measurementDate;
        this.measurementType = measurementType;
        this.databaseManager = databaseManager;
        Optional<Relationship> relationship = createRelationship();
        relationship.ifPresent(r -> this.relationship = r);
    }

    private Optional<Relationship> createRelationship() {
        Optional<Relationship> relationship = databaseManager.createRelationShip(this.sensor.getNode(), this.measurement.getNode(), RelTypes.USE);
        relationship.ifPresent(r -> databaseManager.setRelationshipProperty(r, "DATE", this.measurementDate));
        relationship.ifPresent(r -> databaseManager.setRelationshipProperty(r, "MEASUREMENT_TYPE", measurementType));
        return relationship;
    }

    public Relationship getRelationship() {
        return relationship;
    }
}

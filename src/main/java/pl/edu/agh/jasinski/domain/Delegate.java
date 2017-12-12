package pl.edu.agh.jasinski.domain;

import org.neo4j.graphdb.Relationship;
import pl.edu.agh.jasinski.DatabaseManager;

import java.util.Optional;

public class Delegate {
    private Relationship relationship;
    private Sensor delegator;
    private Sensor delegatee;
    private DatabaseManager databaseManager;

    public Delegate(Sensor delegator, Sensor delegatee, DatabaseManager databaseManager) {
        this.delegator = delegator;
        this.delegatee = delegatee;
        this.databaseManager = databaseManager;
        Optional<Relationship> relationship = createRelationship();
        relationship.ifPresent(r -> this.relationship = r);
    }

    private Optional<Relationship> createRelationship() {
        return databaseManager.createRelationShip(this.delegator.getNode(), this.delegatee.getNode(), RelTypes.DELEGATE);
    }

    public Relationship getRelationship() {
        return relationship;
    }
}

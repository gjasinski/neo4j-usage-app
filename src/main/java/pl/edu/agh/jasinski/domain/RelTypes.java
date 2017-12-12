package pl.edu.agh.jasinski.domain;

import org.neo4j.graphdb.RelationshipType;

public enum RelTypes implements RelationshipType {
    USE,
    DELEGATE,
    NETWORK_USE
}

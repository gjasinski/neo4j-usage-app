package pl.edu.agh.jasinski;

public class RelationFinder {
    private static final String SENSOR_NAME = "1";
    private static final int SENSOR_ID = 1;

    public static void main(String[] args) {
        DatabaseManager dm = new DatabaseManager();
        dm.connectToDatabase(UsageApp.DB_PATH);
        System.out.println(findAllSensorsRelation(SENSOR_NAME, dm));
        System.out.println(findAllMesurementsRelation(SENSOR_ID, dm));
    }

    private static String findAllSensorsRelation(String sensorName, DatabaseManager dm) {
        String query = String.format("MATCH (otherNode)-[r]->(s:SENSOR) WHERE s.SENSOR_NAME = \"%s\" RETURN otherNode, r, s "
                + "UNION" +
                " MATCH (s:SENSOR)-[r]->(otherNode) WHERE s.SENSOR_NAME = \"%s\" RETURN otherNode, r, s", sensorName, sensorName);
        return dm.runCypher(query);

    }

    private static String findAllMesurementsRelation(int id, DatabaseManager dm) {
        String query = String.format("MATCH (otherNode)-[r]->(m:MEASUREMENT) WHERE m.ID = %d RETURN otherNode, r, m "
                + "UNION" +
                " MATCH (m:MEASUREMENT)-[r]->(otherNode) WHERE m.ID = %d RETURN otherNode, r, m", id, id);
        return dm.runCypher(query);
    }

}

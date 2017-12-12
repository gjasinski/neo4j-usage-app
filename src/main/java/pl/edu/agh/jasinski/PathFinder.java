package pl.edu.agh.jasinski;

public class PathFinder {

    public static void main(String[] args) {
        DatabaseManager dm = new DatabaseManager();
        dm.connectToDatabase(UsageApp.DB_PATH);
        System.out.println(findPathTwoMeasurements(8, 14, dm));
        System.out.println(findPathTwoSensors("1", "3", dm));
        System.out.println(findPathMeasurementAndSensor("2", 6, dm));
    }

    private static String findPathTwoMeasurements(int measurementID1, int measurementID2, DatabaseManager dm) {
        String query = String.format("MATCH p=((m1:MEASUREMENT)<-[*]-(m2:MEASUREMENT)) WHERE (m1.ID = %d AND m2.ID = %d) OR (m1.ID = %d AND m2.ID = %d) return p", measurementID1, measurementID2, measurementID2, measurementID1);
        return dm.runCypher(query);
        //always empty since measurements are leafs and edges are directed to them ep.  (MEASUREMENT)<--(NODE)-->(MEASUREMENT)
    }

    private static String findPathTwoSensors(String sensorName1, String sensorName2, DatabaseManager dm) {
        String query = String.format("MATCH p=((s1:SENSOR)<-[*]-(s2:SENSOR)) WHERE (s1.SENSOR_NAME = \"%s\" AND s2.SENSOR_NAME = \"%s\") OR (s1.SENSOR_NAME = \"%s\" AND s2.SENSOR_NAME = \"%s\") RETURN p", sensorName1, sensorName2, sensorName2, sensorName1);
        return dm.runCypher(query);
    }

    private static String findPathMeasurementAndSensor(String sensorName, int measurementId, DatabaseManager dm) {
        String query = String.format("MATCH p=((s:SENSOR)-[*]->(m:MEASUREMENT)) WHERE s.SENSOR_NAME = \"%s\" AND m.ID= %d RETURN p", sensorName, measurementId);
        return dm.runCypher(query);
    }
}

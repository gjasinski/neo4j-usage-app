package pl.edu.agh.jasinski;

import pl.edu.agh.jasinski.domain.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DataCreator {
    private static int SENSOR_NUMBER = 5;
    private DatabaseManager dbm;
    private Random random;
    private int id = 0;

    public DataCreator(DatabaseManager dbm) {
        this.random = new Random();
        this.dbm = dbm;
        List<Sensor> sensors = createSensors(SENSOR_NUMBER);
        createRamUseEdges(sensors);
        createNetworkUseEdges(sensors);
        createDelegateEdges(sensors);
    }

    private List<Sensor> createSensors(int sensors) {
        List<Sensor> sensorList = new LinkedList<>();
        for (int i = 0; i < sensors; i++) {
            Sensor s = new Sensor(this.dbm, Integer.toString(i));
            sensorList.add(s);
        }
        return sensorList;
    }

    private void createRamUseEdges(List<Sensor> sensors) {
        sensors.forEach(this::addRamUsage);
        sensors.forEach(this::addRamUsage);
    }

    private void addRamUsage(Sensor sensor) {
        Measurement ramUsage = new Measurement(this.dbm, this.id, this.random.nextInt(8096), Unit.GB);
        this.id++;
        long randomDate = LocalDate.now().minusDays(this.random.nextInt(7)).toEpochDay();
        Use use = new Use(sensor, ramUsage, randomDate, MeasurementType.AMOUNT, this.dbm);
    }

    private void createNetworkUseEdges(List<Sensor> sensors) {
        sensors.forEach(this::addDownloadNetworkUsage);
        sensors.forEach(this::addUploadNetworkUsage);
    }

    private void addDownloadNetworkUsage(Sensor sensor) {
        Measurement downloadNetworkUsage = new Measurement(this.dbm, this.id, this.random.nextInt(128), Unit.Mbps);
        this.id++;
        long randomDate = LocalDate.now().minusDays(this.random.nextInt(7)).toEpochDay();
        NetworkUse networkUse = new NetworkUse(sensor, downloadNetworkUsage, randomDate, NetworkUseType.DOWNLOAD, this.dbm);
    }

    private void addUploadNetworkUsage(Sensor sensor) {
        Measurement uploadNetworkUsage = new Measurement(this.dbm, this.id, this.random.nextInt(32), Unit.Mbps);
        this.id++;
        long randomDate = LocalDate.now().minusDays(this.random.nextInt(7)).toEpochDay();
        NetworkUse networkUse = new NetworkUse(sensor, uploadNetworkUsage, randomDate, NetworkUseType.UPLOAD, this.dbm);
    }

    private void createDelegateEdges(List<Sensor> sensors) {
        for (Sensor delegator : sensors) {
            Sensor delegatee;
            do {
                delegatee = sensors.get(this.random.nextInt(sensors.size() - 1));
            } while (delegatee == delegator);
            addDelegator(delegator, delegatee);
        }
    }

    private void addDelegator(Sensor delegator, Sensor delegatee) {
        Delegate networkUse = new Delegate(delegator, delegatee, this.dbm);
    }
}

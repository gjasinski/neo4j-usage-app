package pl.edu.agh.jasinski;

public class UsageApp {
    static final String DB_PATH = "graph.db";

    public static void main(String[] args) {
        DatabaseManager ds = new DatabaseManager();
        ds.connectToDatabase(DB_PATH);
        ds.setUpDatabase();
    }
}

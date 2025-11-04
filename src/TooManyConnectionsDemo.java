import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TooManyConnectionsDemo {
    private static final String URL = "jdbc:mysql://10.65.134.76:3310/sys";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        // Launch 50 threads — each opens and closes a DB connection without pooling
        for (int i = 0; i < 5000; i++) {
            new Thread(() -> {
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                    System.out.println(Thread.currentThread().getName() + " connected.");
                    Thread.sleep(2000); // Simulate query running
                } catch (SQLException e) {
                    System.err.println(Thread.currentThread().getName() + " SQL exception: " + e.getMessage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Thread-" + i).start();
        }
    }
}

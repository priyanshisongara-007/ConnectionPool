import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPoolDemo {
    private static final String URL = "jdbc:mysql://10.65.134.76:3310/sys";
    private static final String USER = "root";
    private static final String PASSWORD = "****";

    static class ConnectionPool {
        private BlockingQueue<Connection> pool;

        public ConnectionPool(int poolSize) throws SQLException {
            pool = new ArrayBlockingQueue<>(poolSize);

            for (int i = 0; i < poolSize; i++) {
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                pool.offer(conn);
            }
        }

        public Connection getConnection() throws InterruptedException {
            return pool.take(); // waits if no connection available
        }

        public void releaseConnection(Connection conn) {
            if (conn != null) {
                pool.offer(conn);
            }
        }

        public void closeAllConnections() throws SQLException {
            for (Connection conn : pool) {
                conn.close();
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        final ConnectionPool connectionPool = new ConnectionPool(5); // pool size 5

        for (int i = 0; i < 5000; i++) {
            new Thread(() -> {
                Connection conn = null;
                try {
                    conn = connectionPool.getConnection();
                    System.out.println(Thread.currentThread().getName() + " acquired connection.");
                    Thread.sleep(2000); // Simulate query running
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    connectionPool.releaseConnection(conn);
                    System.out.println(Thread.currentThread().getName() + " released connection.");
                }
            }, "Thread-" + i).start();
        }
    }
}

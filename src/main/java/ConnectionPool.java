import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool implements javax.sql.DataSource {
    private final String url;  // 数据库连接URL
    private final String username;  // 数据库用户名
    private final String password;  // 数据库密码
    private final List<Connection> connectionPool;  // 连接池
    private final int initialSize;  // 初始连接池大小
    private final int maxPoolSize;  // 最大连接池大小

    public void releaseConnection(Connection connection) {
        synchronized (connectionPool) {
            connectionPool.add(connection);
        }
    }

    public ConnectionPool(String url, String username, String password, int initialSize, int maxPoolSize) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.initialSize = initialSize;
        this.maxPoolSize = maxPoolSize;
        connectionPool = new ArrayList<>(initialSize);
        initializeConnectionPool();
    }

    private void initializeConnectionPool() {
        try {
            for (int i = 0; i < initialSize; i++) {
                Connection connection = createConnection();
                connectionPool.add(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection() throws SQLException {
        synchronized (connectionPool) {
            if (connectionPool.isEmpty()) {
                if (connectionPool.size() < maxPoolSize) {
                    connectionPool.add(createConnection());
                } else {
                    throw new SQLException("Connection pool exceeded maximum size");
                }
            }
            return connectionPool.remove(connectionPool.size() - 1);
//            return createConnection();
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException("Not supported");
    }
}

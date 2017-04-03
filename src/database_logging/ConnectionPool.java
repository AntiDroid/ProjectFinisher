package database_logging;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionPool {

	private static ConnectionPool instance = null;

	public static ConnectionPool getInstance() {

		if (instance == null) {
			instance = new ConnectionPool();
			instance.InitializeConnectionPool();
		}

		return instance;
	}

	ComboPooledDataSource cpds = new ComboPooledDataSource();

	public Connection getConnection() throws SQLException {
		return cpds.getConnection();
	}

	public void InitializeConnectionPool() {
		
		try {

			Properties p = new Properties();
			p.load(getClass().getClassLoader().getResourceAsStream("context.properties"));
			cpds.setDriverClass(p.getProperty("driver"));

			cpds.setJdbcUrl(p.getProperty("con_string"));
			cpds.setUser(p.getProperty("user"));
			cpds.setPassword(p.getProperty("pw"));
		
		} catch (PropertyVetoException | IOException e) {
			MyLogger.getLogger().severe(e.getMessage());
		}
	}

}

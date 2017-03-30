package database;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionPool {

	private static ConnectionPool instance = null;
	private final static Logger LOGGER = Logger.getLogger(ConnectionPool.class.getName());

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

		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.INFO);  
		LOGGER.addHandler(consoleHandler);
		LOGGER.setLevel(Level.FINE);
		
		try {

			Properties p = new Properties();
			p.load(getClass().getClassLoader().getResourceAsStream("context.properties"));
			cpds.setDriverClass(p.getProperty("driver"));

			cpds.setJdbcUrl(p.getProperty("con_string"));
			cpds.setUser(p.getProperty("user"));
			cpds.setPassword(p.getProperty("pw"));
		
		} catch (PropertyVetoException | IOException e) {
			LOGGER.severe(e.getMessage());
		}
	}

}

package database;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionPool {

	private ConnectionPool() {
	}

	private static ConnectionPool instance = null;

	public static ConnectionPool getInstance() {

		if (instance == null){
			instance = new ConnectionPool();
			instance.InitializeConnectionPool();
		}

		return instance;
	}

	ComboPooledDataSource cpds = new ComboPooledDataSource();

	public Connection getConnection() throws SQLException {
		return cpds.getConnection();
	}

	// MUSS EINMAL AUFGERUFEN WERDEN -> ServletContext
	public void InitializeConnectionPool() {
		
		 try {
			cpds.setDriverClass("com.mysql.jdbc.Driver");
		 } catch (PropertyVetoException e) {
			e.printStackTrace();
		 }
		 
		 cpds.setJdbcUrl("jdbc:mysql://localhost/interaktivefolien");
		 cpds.setUser("root");
		 cpds.setPassword("");

		// the settings below are optional -- c3p0 can work with defaults
		// cpds.setMinPoolSize(5);
		// cpds.setAcquireIncrement(5);
		// cpds.setMaxPoolSize(20);
	}

}

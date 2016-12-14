package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import database.ConnectionPool;
import database.DBManager;

public class StudentTest {

	static DBManager conn;
	
	@BeforeClass
	public static void setUp(){
		
		conn = new DBManager();
		ConnectionPool.getInstance().InitializeConnectionPool();
	}
	
	@Test
	public void testAuswahlbereich() {
		
		assertTrue(true);
	}
}

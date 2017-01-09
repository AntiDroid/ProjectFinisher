package test;

import static org.junit.Assert.*;
import models.Lehrer;
import models.Student;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import database.DBManager;

public class LehrerTest {

	static DBManager conn;
	static Lehrer lehrer;

	@BeforeClass
	public static void setUp() {
		conn = new DBManager();
	}
	
	@Before
	public void setUpBefore(){
		lehrer = new Lehrer("MaxMus2016", "Max", "Mustermann", "passwort");
	}

	@Test
	public void testLehrerSaveGet() {

		conn.save(lehrer);
		Lehrer getL = conn.getLehrer(lehrer.getBenutzername());

		if (getL.getID() == -99)
			assertTrue(false);
		else
			assertTrue(getL.equals(lehrer));

		lehrer.setVorname("Peter");
		lehrer.setNachname("Petermann");

		conn.save(lehrer);
		getL = conn.getLehrer(lehrer.getBenutzername());

		if (getL.getID() == -99)
			assertTrue(false);
		else
			assertTrue(getL.equals(lehrer));
	}
	
	@Test
	public void testLehrerDelete() {

		conn.save(lehrer);
		conn.delete(lehrer);
		assertTrue(conn.getLehrer(lehrer.getBenutzername()).getID() == -99);
		lehrer.setID(-1);
	}
	
	@AfterClass
	public static void end() {

		conn.delete(lehrer);
		conn.dispose();
	}
}

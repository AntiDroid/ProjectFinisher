package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import models.Kurs;
import models.Lehrer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.ConnectionPool;
import database.DBManager;

public class LehrerTest {

	static DBManager conn;
	static Lehrer[] lehrer;
	
	@BeforeClass
	public static void setUp() {

		conn = new DBManager();
		

	}

	@Test
	public void testKursSaveNewAndGet() {

		
		
	}

	@Test
	public void testKursSaveModified() {


	}

	@Test
	public void testKursDelete() {


	}

	@AfterClass
	public static void end() {
		
		
		
		conn.dispose();
	}
}

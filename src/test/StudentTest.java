package test;

import static org.junit.Assert.*;
import models.Student;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import database.DBManager;

public class StudentTest {

	static DBManager conn;
	static Student student;

	@BeforeClass
	public static void setUp() {

		conn = new DBManager();
		student = new Student("MaxMus-2016", "Max", "Mustermann", "passwort");
	}

	@Test
	public void testStudentSaveGet() {

		conn.save(student);
		Student getS = conn.getStudent(student.getBenutzername());

		if (getS.getID() == -99)
			assertTrue(false);
		else
			assertTrue(getS.equals(student));

		student.setVorname("Peter");
		student.setNachname("Petermann");

		conn.save(student);
		getS = conn.getStudent(student.getBenutzername());

		if (getS.getID() == -99)
			assertTrue(false);
		else
			assertTrue(getS.equals(student));
	}

	@Test
	public void testStudentDelete() {

		conn.save(student);
		conn.delete(student);
		assertTrue(conn.getLehrer(student.getBenutzername()).getID() == -99);
	}

	@AfterClass
	public static void end() {

		conn.delete(student);
		conn.dispose();
	}
}

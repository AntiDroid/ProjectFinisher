package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import models.Folie;
import models.Foliensatz;
import models.Kurs;
import models.Student;
import models.Uservoting;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import database.DBManager;

public class UservotingTest {

	static DBManager conn;
	static Uservoting[] uvBereiche;
	static Kurs kurs;
	static Foliensatz fSatz;
	static Folie folie;
	static Student student;

	static String[] sessions = { "Test1", "Test3", "Test2" };

	static int[] clickX1 = { 1, 2, 3, 7, 8, 9, 13, 14, 15 };
	static int[] clickY1 = { 4, 5, 6, 10, 11, 12, 16, 17, 18 };
	
	static int[] clickX2 = { 11, 12, 13, 17, 18, 19, 113, 114, 115 };
	static int[] clickY2 = { 14, 15, 16, 110, 111, 112, 116, 117, 118 };

	static String[] optionen = { "", "A", "Flugzeug" };

	@BeforeClass
	public static void setUp() {

		conn = new DBManager();

		kurs = new Kurs("Test");
		conn.save(kurs);
		fSatz = new Foliensatz(kurs.getID(), kurs, "Test");
		conn.save(fSatz);
		folie = new Folie(fSatz.getID(), fSatz, "Test", 'T');
		conn.save(folie);
		student = new Student("Test", "TestVName", "TestNName", "TestPW");
		conn.save(student);

		uvBereiche = new Uservoting[9];
		for (int i = 0; i < uvBereiche.length; i++)
			uvBereiche[i] = new Uservoting("SessionX", student.getID(),
					student, folie.getID(), folie, 0, 0, "");
	}

	@Test
	public void testAuswahlbereichSaveGet() {

		// Standard
		for (int i = 0; i < uvBereiche.length; i++) {
			uvBereiche[i].setSessionID(sessions[i/3]);
			uvBereiche[i].setKoordX(clickX1[i]);
			uvBereiche[i].setKoordY(clickY1[i]);
			uvBereiche[i].setAuswahloption(optionen[i/3]);
		}
		for (Uservoting uv : uvBereiche)
			conn.save(uv);

		// Überprüfung - normal
		ArrayList<Uservoting> uvListe = conn.getUservotings(student, folie);

		for (int i = 0; i < uvListe.size(); i++)
			assertTrue(uvListe.get(i).getSessionID() == sessions[i/3]
					&& uvListe.get(i).getKoordX() == clickX1[i]
					&& uvListe.get(i).getKoordY() == clickY1[i]
					&& uvListe.get(i).getAuswahloption() == optionen[i/3]
					&& uvListe.get(i).getID() == (i + 1));

		// Änderung
		for (int i = 0; i < uvBereiche.length; i++) {
			uvBereiche[i].setSessionID(sessions[i/3]);
			uvBereiche[i].setKoordX(clickX2[i]);
			uvBereiche[i].setKoordY(clickY2[i]);
			uvBereiche[i].setAuswahloption(optionen[i/3]);
		}
		for (Uservoting uv : uvBereiche)
			conn.save(uv);

		// Überprüfung - Änderung
		uvListe.clear();
		uvListe = conn.getUservotings(student, folie);

		for (int i = 0; i < uvListe.size(); i++)
			assertTrue(uvListe.get(i).getSessionID() == sessions[i/3]
					&& uvListe.get(i).getKoordX() == clickX2[i]
					&& uvListe.get(i).getKoordY() == clickY2[i]
					&& uvListe.get(i).getAuswahloption() == optionen[i/3]
					&& uvListe.get(i).getID() == (i + 1));
	}

	@Test
	public void testAuswahlbereichDelete() {

		for (int i = 0; i < uvBereiche.length; i++) {
			uvBereiche[i].setSessionID(sessions[i/3]);
			uvBereiche[i].setKoordX(clickX1[i]);
			uvBereiche[i].setKoordY(clickY1[i]);
			uvBereiche[i].setAuswahloption(optionen[i/3]);
		}
		for (Uservoting uv : uvBereiche)
			conn.save(uv);

		conn.delete(uvBereiche[1]);

		ArrayList<Uservoting> uvListe = conn.getUservotings(student, folie);

		for (int i = 0; i < uvListe.size(); i++)
			assertTrue(uvListe.get(i).getSessionID() != sessions[0]
					&& uvListe.get(i).getKoordX() != clickX2[1]
					&& uvListe.get(i).getKoordY() != clickY2[1]
					&& uvListe.get(i).getAuswahloption() != optionen[0]
					&& uvListe.get(i).getID() != 2);

		conn.delete(uvBereiche[0]);
		conn.delete(uvBereiche[2]);

		assertTrue(conn.getUservotings(student, folie).isEmpty());
	}

	@AfterClass
	public static void end() {

		conn.delete(kurs);
		conn.delete(student);

		conn.dispose();
	}
}

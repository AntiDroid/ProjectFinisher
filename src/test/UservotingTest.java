package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import models.Auswahlbereich;
import models.Folie;
import models.Foliensatz;
import models.Kurs;
import models.Student;
import models.Uservoting;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.ConnectionPool;
import database.DBManager;

public class UservotingTest {

	static DBManager conn;
	static Uservoting[] uvBereiche;
	static Kurs kurs;
	static Foliensatz fSatz;
	static Folie folie;
	static Student student;

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

		uvBereiche = new Uservoting[3];
		for (int i = 0; i < uvBereiche.length; i++)
			uvBereiche[i] = new Uservoting("SessionX", student.getID(), student, folie.getID(), folie, 0, 0, "");
	}

	//SessionID, Klick KoordX und Y, Auswahloption ist optional
	
	@Test
	public void testAuswahlbereichSaveGet() {

		// Standard
		for (int i = 0; i < uvBereiche.length; i++) {
			uvBereiche[i].setObenLinksX(xOben1[i]);
			uvBereiche[i].setObenLinksY(yOben1[i]);
		}
		for (Uservoting uv : uvBereiche)
			conn.save(uv);

		// Überprüfung - normal
		ArrayList<Uservoting> uvListe = conn.getUservotings(student, folie);

		for (int i = 0; i < uvListe.size(); i++)
			assertTrue(uvListe.get(i).getObenLinksX() == xOben1[i]
					&& uvListe.get(i).getObenLinksY() == yOben1[i]
					&& uvListe.get(i).getID() == (i + 1));
	
		// Änderung
		for (int i = 0; i < uvBereiche.length; i++) {
			uvBereiche[i].setObenLinksX(xOben2[i]);
			uvBereiche[i].setObenLinksY(yOben2[i]);
		}
		for (Uservoting uv : uvBereiche)
			conn.save(uv);

		// Überprüfung - Änderung
		uvListe.clear();
		uvListe = conn.getUservotings(student, folie);

		for (int i = 0; i < uvListe.size(); i++)
			assertTrue(uvListe.get(i).getObenLinksX() == xOben2[i]
					&& uvListe.get(i).getObenLinksY() == yOben2[i]
					&& uvListe.get(i).getID() == (i + 1));
	}

	@Test
	public void testAuswahlbereichDelete() {

		for (int i = 0; i < uvBereiche.length; i++) {
			uvBereiche[i].setObenLinksX(xOben1[i]);
			uvBereiche[i].setObenLinksY(yOben1[i]);
		}
		for (Uservoting uv : uvBereiche)
			conn.save(uv);

		conn.delete(uvBereiche[1]);

		ArrayList<Auswahlbereich> abListe = conn.getAuswahlbereiche(folie);

		for (int i = 0; i < abListe.size(); i++)
			assertTrue(abListe.get(i).getObenLinksX() != xOben1[1]
					&& abListe.get(i).getObenLinksY() != yOben1[1]
					&& abListe.get(i).getID() != 2);

		conn.delete(uvBereiche[0]);
		conn.delete(uvBereiche[2]);

		assertTrue(conn.getAuswahlbereiche(folie).isEmpty());
	}
	
	@AfterClass
	public static void end() {

		conn.delete(kurs);
		conn.delete(student);

		conn.dispose();
	}
}

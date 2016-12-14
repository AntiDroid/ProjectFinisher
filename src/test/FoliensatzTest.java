package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import models.Foliensatz;
import models.Kurs;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.ConnectionPool;
import database.DBManager;

public class FoliensatzTest {

	static DBManager conn;
	static Foliensatz[] fSätze;
	static Kurs kurs;

	@BeforeClass
	public static void setUp() {

		conn = new DBManager();
		
		kurs = new Kurs("Test");
		conn.save(kurs);

		fSätze = new Foliensatz[3];
		for (int i = 0; i < fSätze.length; i++)
			fSätze[i] = new Foliensatz();
	}

	@Test
	public void testKursSaveGet() {

		// Standard
		for (int i = 0; i < fSätze.length; i++)
			fSätze[i].setName("NAME");
		for (Foliensatz fs : fSätze)
			conn.save(fs);

		// Überprüfung - normal
		ArrayList<Foliensatz> fsListe = conn.getFoliensätze(kurs);

		for (int i = 0; i < fsListe.size(); i++)
			assertTrue(fsListe.get(i).getName().equals("NAME")
					&& fsListe.get(i).getID() == (i + 1));

		// Änderung
		for (int i = 0; i < fSätze.length; i++)
			fSätze[i].setName("NAME");
		for (Foliensatz fs : fsListe)
			conn.save(fs);

		// Überprüfung - Änderung
		fsListe.clear();
		fsListe = conn.getFoliensätze(kurs);

		for (int i = 0; i < fsListe.size(); i++)
			assertTrue(fsListe.get(i).getName().equals("NAME")
					&& fsListe.get(i).getID() == (i + 1));
	}

	@Test
	public void testKursDelete() {

		for (int i = 0; i < fSätze.length; i++)
			fSätze[i].setName("NAME");

		for (Foliensatz fs : fSätze)
			conn.save(fs);

		conn.delete(fSätze[1]);

		ArrayList<Kurs> fsListe = conn.getKurse();

		for (int i = 0; i < fsListe.size(); i++)
			assertTrue(!fsListe.get(i).getName().equals(fSätze[1].getName())
					&& fsListe.get(i).getID() != 2);

		conn.delete(fSätze[0]);
		conn.delete(fSätze[2]);

		assertTrue(conn.getKurse().isEmpty());
	}

	@AfterClass
	public static void end() {

		for (Foliensatz fs : fSätze)
			conn.delete(fs);

		conn.dispose();
	}
}

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
	static Foliensatz[] fS�tze;
	static Kurs kurs;

	// Testdaten 1
	static String[] names1 = { "Aufgabe 1, Mathe", "Aufgabe 3, Deutsch",
			"Aufgabe 2, Englisch" };
	// Testdaten 2
	static String[] names2 = { "A1", "A3", "A2" };

	@BeforeClass
	public static void setUp() {

		conn = new DBManager();

		kurs = new Kurs("Test");
		conn.save(kurs);

		fS�tze = new Foliensatz[3];
		for (int i = 0; i < fS�tze.length; i++)
			fS�tze[i] = new Foliensatz(kurs.getID(), kurs, "");
	}

	@Test
	public void testFoliensatzSaveGet() {

		// Standard
		for (int i = 0; i < fS�tze.length; i++)
			fS�tze[i].setName(names1[i]);
		for (Foliensatz fs : fS�tze)
			conn.save(fs);

		// �berpr�fung - normal
		ArrayList<Foliensatz> fsListe = conn.getFoliens�tze(kurs);

		for (int i = 0; i < fsListe.size(); i++)
			assertTrue(fsListe.get(i).getName().equals(names1[i])
					&& fsListe.get(i).getID() == (i + 1));

		// �nderung
		for (int i = 0; i < fS�tze.length; i++)
			fS�tze[i].setName(names2[i]);
		for (Foliensatz fs : fsListe)
			conn.save(fs);

		// �berpr�fung - �nderung
		fsListe.clear();
		fsListe = conn.getFoliens�tze(kurs);

		for (int i = 0; i < fsListe.size(); i++)
			assertTrue(fsListe.get(i).getName().equals(names2[i])
					&& fsListe.get(i).getID() == (i + 1));
	}

	@Test
	public void testKursDelete() {

		for (int i = 0; i < fS�tze.length; i++)
			fS�tze[i].setName(names1[i]);

		for (Foliensatz fs : fS�tze)
			conn.save(fs);

		conn.delete(fS�tze[1]);

		ArrayList<Foliensatz> fsListe = conn.getFoliens�tze(kurs);

		for (int i = 0; i < fsListe.size(); i++)
			assertTrue(!fsListe.get(i).getName().equals(fS�tze[1].getName())
					&& fsListe.get(i).getID() != 2);

		conn.delete(fS�tze[0]);
		conn.delete(fS�tze[2]);

		assertTrue(conn.getFoliens�tze(kurs).isEmpty());
	}

	@AfterClass
	public static void end() {

		conn.delete(kurs);

		conn.dispose();
	}
}

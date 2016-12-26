package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import models.Folie;
import models.Foliensatz;
import models.Kurs;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import database.DBManager;

public class FolieTest {

	static DBManager conn;
	static Folie[] folien;
	static Kurs kurs;
	static Foliensatz fSatz;

	// Testdaten 1
	static String[] names1 = { "C:\\Users\\3", "C:\\Users\\2", "C:\\Users\\1" };

	static char[] art1 = { 'H', 'C', 'M' };

	// Testdaten 2
	static String[] names2 = { "C:\\Windows\\3", "C:\\Windows\\2",
			"C:\\Windows\\1" };

	static char[] art2 = { 'C', 'H', 'H' };

	@BeforeClass
	public static void setUp() {

		conn = new DBManager();

		kurs = new Kurs("Test");
		conn.save(kurs);
		fSatz = new Foliensatz(kurs.getID(), kurs, "Test");
		conn.save(fSatz);

		folien = new Folie[3];
		for (int i = 0; i < folien.length; i++)
			folien[i] = new Folie(fSatz.getID(), fSatz, "", 'a');
	}

	@Test
	public void testFoliensatzSaveGet() {

		// Standard
		for (int i = 0; i < folien.length; i++) {
			folien[i].setfPath(names1[i]);
			folien[i].setFolienTyp(art1[i]);
		}
		for (Folie f : folien)
			conn.save(f);

		// Überprüfung - normal
		ArrayList<Folie> fListe = conn.getFolien(fSatz);

		for (int i = 0; i < fListe.size(); i++)
			assertTrue(fListe.get(i).getfPath().equals(names1[i])
					&& fListe.get(i).getFolienTyp() == art1[i]
					&& fListe.get(i).getID() == (i + 1));

		// Änderung
		for (int i = 0; i < folien.length; i++) {
			folien[i].setfPath(names2[i]);
			folien[i].setFolienTyp(art1[i]);
		}
		for (Folie f : folien)
			conn.save(f);

		// Überprüfung - Änderung
		fListe.clear();
		fListe = conn.getFolien(fSatz);

		for (int i = 0; i < fListe.size(); i++)
			assertTrue(fListe.get(i).getfPath().equals(names2[i])
					&& fListe.get(i).getFolienTyp() == art1[i]
					&& fListe.get(i).getID() == (i + 1));
	}

	@Test
	public void testKursDelete() {

		for (int i = 0; i < folien.length; i++) {
			folien[i].setfPath(names1[i]);
			folien[i].setFolienTyp(art1[i]);
		}
		for (Folie f : folien)
			conn.save(f);

		conn.delete(folien[1]);

		ArrayList<Folie> fListe = conn.getFolien(fSatz);

		for (int i = 0; i < fListe.size(); i++)
			assertTrue(!fListe.get(i).getfPath().equals(folien[1].getfPath())
					&& fListe.get(i).getFolienTyp() != art1[i]
					&& fListe.get(i).getID() != 2);

		conn.delete(folien[0]);
		conn.delete(folien[2]);

		assertTrue(conn.getFolien(fSatz).isEmpty());
	}

	@AfterClass
	public static void end() {

		conn.delete(kurs);

		conn.dispose();
	}
}

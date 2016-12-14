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

	@BeforeClass
	public static void setUp() {

		conn = new DBManager();
		
		kurs = new Kurs("Test");
		conn.save(kurs);

		fS�tze = new Foliensatz[3];
		for (int i = 0; i < fS�tze.length; i++)
			fS�tze[i] = new Foliensatz();
	}

	@Test
	public void testKursSaveGet() {

		// Standard
		for (int i = 0; i < fS�tze.length; i++)
			fS�tze[i].setName("NAME");
		for (Foliensatz fs : fS�tze)
			conn.save(fs);

		// �berpr�fung - normal
		ArrayList<Foliensatz> fsListe = conn.getFoliens�tze(kurs);

		for (int i = 0; i < fsListe.size(); i++)
			assertTrue(fsListe.get(i).getName().equals("NAME")
					&& fsListe.get(i).getID() == (i + 1));

		// �nderung
		for (int i = 0; i < fS�tze.length; i++)
			fS�tze[i].setName("NAME");
		for (Foliensatz fs : fsListe)
			conn.save(fs);

		// �berpr�fung - �nderung
		fsListe.clear();
		fsListe = conn.getFoliens�tze(kurs);

		for (int i = 0; i < fsListe.size(); i++)
			assertTrue(fsListe.get(i).getName().equals("NAME")
					&& fsListe.get(i).getID() == (i + 1));
	}

	@Test
	public void testKursDelete() {

		for (int i = 0; i < fS�tze.length; i++)
			fS�tze[i].setName("NAME");

		for (Foliensatz fs : fS�tze)
			conn.save(fs);

		conn.delete(fS�tze[1]);

		ArrayList<Kurs> fsListe = conn.getKurse();

		for (int i = 0; i < fsListe.size(); i++)
			assertTrue(!fsListe.get(i).getName().equals(fS�tze[1].getName())
					&& fsListe.get(i).getID() != 2);

		conn.delete(fS�tze[0]);
		conn.delete(fS�tze[2]);

		assertTrue(conn.getKurse().isEmpty());
	}

	@AfterClass
	public static void end() {

		for (Foliensatz fs : fS�tze)
			conn.delete(fs);

		conn.dispose();
	}
}

package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import models.Kurs;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.DBManager;

public class KursTest {

	static DBManager conn;
	static Kurs[] kurse;
	
	//Testdaten 1
	static String[] names1 = { "Mathe, 3. Semester", "Deutsch, 1. Semester", "English, 2. Semester"};
	//Testdaten 2
	static String[] names2 = { "Mathe", "Deutsch", "English"};
	
	
	@BeforeClass
	public static void setUp() {

		conn = new DBManager();
		
		kurse = new Kurs[3];
		for(int i = 0; i < kurse.length; i++)
			kurse[i] = new Kurs();
	}

	@Test
	public void testKursSaveNewAndGet() {

		for(int i = 0; i < kurse.length; i++)
			kurse[i].setName(names1[i]);
		
		for (Kurs k : kurse)
			conn.save(k);

		ArrayList<Kurs> kListe = conn.getKurse();

		for (int i = 0; i < kListe.size(); i++) {
			if (!kListe.get(i).getName().equals(names1[i]) || kListe.get(i).getID() != (i+1))
				assertTrue(false);
		}

		assertTrue(true);
	}

	@Test
	public void testKursSaveModified() {

		for(int i = 0; i < kurse.length; i++)
			kurse[i].setName(names2[i]);
		
		for (Kurs k : kurse)
			conn.save(k);
		
		ArrayList<Kurs> kListe = conn.getKurse();
		
		for (int i = 0; i < kListe.size(); i++) {
			if (!kListe.get(i).getName().equals(names2[i]) || kListe.get(i).getID() != (i+1))
				assertTrue(false);
		}
		
		assertTrue(true);
	}

	@Test
	public void testKursDelete() {

		for(int i = 0; i < kurse.length; i++)
			kurse[i].setName(names1[i]);
		
		for (Kurs k : kurse)
			conn.save(k);
		
		conn.delete(kurse[1]);
		
		ArrayList<Kurs> kListe = conn.getKurse();
		
		for (int i = 0; i < kListe.size(); i++) {
			if (kListe.get(i).getName().equals(kurse[1].getName()) || kListe.get(i).getID() == 2)
				assertTrue(false);
		}
		
		conn.delete(kurse[0]);
		conn.delete(kurse[2]);
		
		if(!conn.getKurse().isEmpty())
			assertTrue(false);
		else
			assertTrue(true);
	}

	@AfterClass
	public static void end() {
		
		for (Kurs k : kurse)
			conn.delete(k);
		
		conn.dispose();
	}
}

package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import models.Auswahlbereich;
import models.Folie;
import models.Foliensatz;
import models.Kurs;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import database.DBManager;

public class AuswahlbereichTest {

	static DBManager conn;
	static Auswahlbereich[] aBereiche;
	static Kurs kurs;
	static Foliensatz fSatz;
	static Folie folie;

	// Testdaten 1
	static int[] xOben1 = { 11, 12, 13 };
	static int[] yOben1 = { 14, 15, 16 };
	static int[] xUnten1 = { 17, 18, 19 };
	static int[] yUnten1 = { 20, 21, 22 };

	// Testdaten 2
	static int[] xOben2 = { 23, 24, 25 };
	static int[] yOben2 = { 26, 27, 28 };
	static int[] xUnten2 = { 29, 30, 31 };
	static int[] yUnten2 = { 32, 33, 34 };

	@BeforeClass
	public static void setUp() {

		conn = new DBManager();

		kurs = new Kurs("Test");
		conn.save(kurs);
		fSatz = new Foliensatz(kurs.getID(), kurs, "Test");
		conn.save(fSatz);
		folie = new Folie(fSatz.getID(), fSatz, "Test", 'T');
		conn.save(folie);

		aBereiche = new Auswahlbereich[3];
		for (int i = 0; i < aBereiche.length; i++)
			aBereiche[i] = new Auswahlbereich(folie.getID(), folie, 0, 0, 0, 0);
	}

	@Test
	public void testAuswahlbereichSaveGet() {

		// Standard
		for (int i = 0; i < aBereiche.length; i++) {
			aBereiche[i].setObenLinksX(xOben1[i]);
			aBereiche[i].setObenLinksY(yOben1[i]);
			aBereiche[i].setUntenRechtsX(xUnten1[i]);
			aBereiche[i].setUntenRechtsY(yUnten1[i]);
		}
		for (Auswahlbereich ab : aBereiche)
			conn.save(ab);

		// Überprüfung - normal
		ArrayList<Auswahlbereich> abListe = conn.getAuswahlbereiche(folie);
		assertTrue(abListe.size() == 3);
		
		for (int i = 0; i < abListe.size(); i++)
			assertTrue(abListe.get(i).getObenLinksX() == xOben1[i]
					&& abListe.get(i).getObenLinksY() == yOben1[i]
					&& abListe.get(i).getUntenRechtsX() == xUnten1[i]
					&& abListe.get(i).getUntenRechtsY() == yUnten1[i]);
	
		// Änderung
		for (int i = 0; i < aBereiche.length; i++) {
			aBereiche[i].setObenLinksX(xOben2[i]);
			aBereiche[i].setObenLinksY(yOben2[i]);
			aBereiche[i].setUntenRechtsX(xUnten2[i]);
			aBereiche[i].setUntenRechtsY(yUnten2[i]);
		}
		for (Auswahlbereich ab : aBereiche)
			conn.save(ab);

		// Überprüfung - Änderung
		abListe.clear();
		abListe = conn.getAuswahlbereiche(folie);
		assertTrue(abListe.size() == 3);
		
		for (int i = 0; i < abListe.size(); i++)
			assertTrue(abListe.get(i).getObenLinksX() == xOben2[i]
					&& abListe.get(i).getObenLinksY() == yOben2[i]
					&& abListe.get(i).getUntenRechtsX() == xUnten2[i]
					&& abListe.get(i).getUntenRechtsY() == yUnten2[i]);
	}

	@Test
	public void testAuswahlbereichDelete() {

		for (int i = 0; i < aBereiche.length; i++) {
			aBereiche[i].setObenLinksX(xOben1[i]);
			aBereiche[i].setObenLinksY(yOben1[i]);
			aBereiche[i].setUntenRechtsX(xUnten1[i]);
			aBereiche[i].setUntenRechtsY(yUnten1[i]);
		}
		for (Auswahlbereich ab : aBereiche)
			conn.save(ab);

		conn.delete(aBereiche[1]);

		ArrayList<Auswahlbereich> abListe = conn.getAuswahlbereiche(folie);
		assertTrue(abListe.size() == 2);
		
		for (int i = 0; i < abListe.size(); i++)
			assertTrue(abListe.get(i).getObenLinksX() != xOben1[1]
					&& abListe.get(i).getObenLinksY() != yOben1[1]
					&& abListe.get(i).getUntenRechtsX() != xUnten1[1]
					&& abListe.get(i).getUntenRechtsY() != yUnten1[1]);

		conn.delete(aBereiche[0]);
		conn.delete(aBereiche[2]);

		assertTrue(conn.getAuswahlbereiche(folie).isEmpty());
	}

	@AfterClass
	public static void end() {

		conn.delete(kurs);
		conn.dispose();
	}
}

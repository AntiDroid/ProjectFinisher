package database;

public class Berechtigung {

	private int LehrerID, kursID;
	
	private char berechtigungsTyp;
	
	public Berechtigung(int lID, int kID, char b) {
		this.LehrerID = lID;
		this.kursID = kID;
		
		this.berechtigungsTyp = b;
	}

	public int getLehrerID() {
		return LehrerID;
	}

	public void setLehrerID(int lehrerID) {
		LehrerID = lehrerID;
	}

	public int getKursID() {
		return kursID;
	}

	public void setKursID(int kursID) {
		this.kursID = kursID;
	}

	public char getBerechtigungsTyp() {
		return berechtigungsTyp;
	}

	public void setBerechtigungsTyp(char berechtigungsTyp) {
		this.berechtigungsTyp = berechtigungsTyp;
	}
	
}

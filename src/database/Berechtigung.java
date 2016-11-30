package database;

public class Berechtigung extends DBObject{

	private int berechtigungsID;
	
	private int LehrerID, kursID;
	private Lehrer lehrer;
	private Kurs kurs;
	
	private char berechtigungsTyp;
	
	public Berechtigung(int lID, int kID, Lehrer l, Kurs k, char b) {
		this.berechtigungsID = -1;
		
		this.lehrer = l;
		this.kurs = k;
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

	public int getID() {
		return berechtigungsID;
	}

	public void setID(int berechtigungsID) {
		this.berechtigungsID = berechtigungsID;
	}
	
	public void Save(){
		DBManager.Instance().Save(this);
	}
	
	public void Delete(){
		DBManager.Instance().Save(this);
	}

	public Lehrer getLehrer() {
		return lehrer;
	}

	public void setLehrer(Lehrer lehrer) {
		this.lehrer = lehrer;
	}

	public Kurs getKurs() {
		return kurs;
	}

	public void setKurs(Kurs kurs) {
		this.kurs = kurs;
	}
	
}

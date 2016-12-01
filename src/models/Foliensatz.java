package models;

public class Foliensatz {

	private int folienSatzID;
	
	private Kurs kurs;
	private int kursID;
	
	private String name;
	
	public Foliensatz(int kID, Kurs k, String n) {
		this.folienSatzID = -1;
		this.kursID = kID;
		this.kurs = k;
		
		this.name = n;
	}

	public Foliensatz() {
		// TODO Auto-generated constructor stub
	}

	public int getID() {
		return folienSatzID;
	}

	public void setID(int folienSatzID) {
		this.folienSatzID = folienSatzID;
	}

	public int getKursID() {
		return kursID;
	}

	public void setKursID(int kursID) {
		this.kursID = kursID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Kurs getKurs() {
		return kurs;
	}

	public void setKurs(Kurs kurs) {
		this.kurs = kurs;
	}
	
}

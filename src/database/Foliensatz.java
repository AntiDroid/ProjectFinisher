package database;

public class Foliensatz {

	private int folienSatzID;
	private int kursID;
	
	private String name;
	
	public Foliensatz(int kID, String n) {
		this.folienSatzID = -1;
		this.kursID = kID;
		
		this.name = n;
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
	
}

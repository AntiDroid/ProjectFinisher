package database;

public class Kurs {

	private int kursID;
	
	private String name;
	
	public Kurs(String n) {
		this.kursID = -1;
		
		this.name = n;
	}

	public int getID() {
		return kursID;
	}

	public void setID(int kursID) {
		this.kursID = kursID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

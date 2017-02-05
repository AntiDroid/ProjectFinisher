package models;

public class Kurs {

	private int kursID;
	
	private String name, passwort;
	
	public Kurs(String n) {
		this.kursID = -1;
		
		this.name = n;
	}

	public Kurs() {
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
	
	public boolean equals(Object o){
		
		Kurs k = (Kurs) o;
		
		return (this.kursID == k.kursID) && (this.getName() == k.getName());
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	
}

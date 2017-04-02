package models;

import java.io.Serializable;

public class Kurs implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int kursID;
	
	private Lehrer lehrer;
	private int lehrerID;
	
	private String name, passwort;
	
	public Kurs(String n, String pw, Lehrer l, int lID) {
		this.kursID = -1;
		
		this.lehrer = l;
		this.lehrerID = lID;
		
		this.name = n;
		this.passwort = pw;
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

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public int getLehrerID() {
		return lehrerID;
	}

	public void setLehrerID(int lehrerID) {
		this.lehrerID = lehrerID;
	}

	public Lehrer getLehrer() {
		return lehrer;
	}

	public void setLehrer(Lehrer lehrer) {
		this.lehrer = lehrer;
	}
	
    @Override
    public boolean equals(Object obj){
    	Kurs k = (Kurs) obj;
    	
    	return this.kursID == k.kursID;
    }
	
}

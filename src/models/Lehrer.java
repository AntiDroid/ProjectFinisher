package models;

public class Lehrer {

	private int lehrerID;
	
	private String benutzername, vorname, nachname, passwort;
	
	public Lehrer(String bn, String vn, String nn, String pw) {
		
		this.lehrerID = -1;
		
		this.benutzername = bn;
		this.vorname = vn;
		this.nachname = nn;
		this.passwort = pw;
	}

	public Lehrer() {
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public int getID() {
		return lehrerID;
	}

	public void setID(int lehrerID) {
		this.lehrerID = lehrerID;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}
	
}

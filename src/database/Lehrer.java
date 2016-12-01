package database;

public class Lehrer {

	private int lehrerID;
	
	private String vorname, nachname, passwort;
	
	public Lehrer(String vn, String nn, String pw) {
		
		this.lehrerID = -1;
		
		this.vorname = vn;
		this.nachname = nn;
		this.passwort = pw;
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
	
}

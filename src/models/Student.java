package models;

public class Student {

	private int studentenID;
	
	private String benutzername, vorname, nachname, passwort;
	
	public Student(String bn, String vn, String nn, String pw) {
		this.setID(-1);
		
		this.benutzername = bn;
		this.vorname = vn;
		this.nachname = nn;
		this.passwort = pw;
	}

	public Student() {
	}

	public int getID() {
		return studentenID;
	}

	public void setID(int studentenID) {
		this.studentenID = studentenID;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}
	
}

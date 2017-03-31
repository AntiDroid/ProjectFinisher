package models;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

public abstract class Client implements Serializable{

	public static ConcurrentHashMap<String, HttpSession> actLogin = new ConcurrentHashMap<String, HttpSession>();
	
	private int ID;

	private String benutzername, vorname, nachname, passwort;

	public Client(String bn, String vn, String nn, String pw) {
		this.ID = -1;

		this.benutzername = bn;
		this.vorname = vn;
		this.nachname = nn;
		this.passwort = pw;
	}

	public Client() {
		this.ID = -99;
	}

	public int getID() {
		return ID;
	}

	public void setID(int id) {
		this.ID = id;
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

	public boolean equals(Object o) {

		Client c = (Client) o;
		
		return (benutzername.equals(c.benutzername))
				&& (vorname.equals(c.vorname)) && (nachname.equals(c.nachname))
				&& (passwort.equals(c.passwort));
	}
	
}

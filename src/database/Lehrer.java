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
	
}

package database;

public class Student {

	private int studentenID;
	
	private String vorname, nachname, passwort;
	
	public Student(String vn, String nn, String pw) {
		this.studentenID = -1;
		
		this.vorname = vn;
		this.nachname = nn;
		this.passwort = pw;
	}
	
}

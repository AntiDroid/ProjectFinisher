package database;

public class Student extends DBObject{

	private int studentenID;
	
	private String vorname, nachname, passwort;
	
	public Student(String vn, String nn, String pw) {
		this.setID(-1);
		
		this.vorname = vn;
		this.nachname = nn;
		this.passwort = pw;
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
	
	public void sqlSave(){
		DBManager.Instance().Save(this);
	}
	
	public void sqlDelete(){
		DBManager.Instance().Save(this);
	}
	
}

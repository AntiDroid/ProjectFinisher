package models;

public class Uservoting {

	private int votingID;
	private String sessionID;
	private int studentenID, folienID;
	private Student student;
	private Folie folie;
	
	private int koordX, koordY;
	private String auswahloption;
	
	public Uservoting(String sesID, int stID, Student s, int fID, Folie f, int kX, int kY, String ao) {
		this.votingID = -1;
		this.sessionID = sesID;
		this.studentenID = stID;
		this.student = s;
		this.folienID = fID;
		this.folie = f;
		
		this.koordX = kX;
		this.koordY = kY;
		this.auswahloption = ao;
	}

	public Uservoting() {
	}

	public int getID() {
		return votingID;
	}

	public void setID(int votingID) {
		this.votingID = votingID;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public int getStudentenID() {
		return studentenID;
	}

	public void setStudentenID(int studentenID) {
		this.studentenID = studentenID;
	}

	public int getKoordX() {
		return koordX;
	}

	public void setKoordX(int koordX) {
		this.koordX = koordX;
	}

	public int getFolienID() {
		return folienID;
	}

	public void setFolienID(int folienID) {
		this.folienID = folienID;
	}

	public int getKoordY() {
		return koordY;
	}

	public void setKoordY(int koordY) {
		this.koordY = koordY;
	}

	public String getAuswahloption() {
		return auswahloption;
	}

	public void setAuswahloption(String auswahloption) {
		this.auswahloption = auswahloption;
	}

	public Folie getFolie() {
		return folie;
	}

	public void setFolie(Folie folie) {
		this.folie = folie;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}

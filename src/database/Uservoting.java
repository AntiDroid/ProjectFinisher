package database;

public class Uservoting {

	private int votingID;
	private String sessionID;
	private int studentenID, folienID;
	
	private int koordX, koordY;
	private String auswahloption;
	
	public Uservoting(String sesID, int stID, int fID, int kX, int kY, String ao) {
		this.votingID = -1;
		this.sessionID = sesID;
		this.studentenID = stID;
		this.folienID = fID;
		
		this.koordX = kX;
		this.koordY = kY;
		this.auswahloption = ao;
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
}

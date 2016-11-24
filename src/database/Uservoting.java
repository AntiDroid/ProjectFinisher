package database;

public class Uservoting {

	private int votingID;
	private String sessionID;
	private int studentenID, folienID;
	
	private int koordX, koordY;
	private String auswahloption;
	
	public Uservoting(int kX, int kY, String ao) {
		this.votingID = -1;
		this.sessionID = "";
		this.studentenID = -1;
		this.folienID = -1;
		
		this.koordX = kX;
		this.koordY = kY;
		this.auswahloption = ao;
	}
}

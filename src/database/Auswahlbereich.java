package database;

public class Auswahlbereich {

	private int auswahlBereichsID;
	private int folienID;
	
	private int obenLinks, untenRechts;
	
	public Auswahlbereich(int oL, int uL) {
		this.auswahlBereichsID = -1;
		this.folienID = -1;
		
		this.obenLinks = oL;
		this.untenRechts = uL;
	}
}

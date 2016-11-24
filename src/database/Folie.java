package database;

public class Folie {

	private int folienID;
	private int foliensatzID;
	
	private String fPath;
	private char folienTyp;
	
	public Folie(String fP, char fT) {
		this.folienID = -1;
		this.foliensatzID = -1;
		
		this.fPath = fP;
		this.folienTyp = fT;
	}
	
}

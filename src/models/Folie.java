package models;

public class Folie {

	private int folienID;

	private Foliensatz fSatz;
	private int foliensatzID;

	private String fPath;
	private char folienTyp;

	public Folie(int fsID, Foliensatz fS, String fP, char fT) {
		this.folienID = -1;

		this.fSatz = fS;
		this.foliensatzID = fsID;

		this.fPath = fP;
		this.folienTyp = fT;
	}

	public Folie() {
		// TODO Auto-generated constructor stub
	}

	public int getID() {
		return folienID;
	}

	public void setID(int folienID) {
		this.folienID = folienID;
	}

	public int getFoliensatzID() {
		return foliensatzID;
	}

	public void setFoliensatzID(int foliensatzID) {
		this.foliensatzID = foliensatzID;
	}

	public String getfPath() {
		return fPath;
	}

	public void setfPath(String fPath) {
		this.fPath = fPath;
	}

	public char getFolienTyp() {
		return folienTyp;
	}

	public void setFolienTyp(char folienTyp) {
		this.folienTyp = folienTyp;
	}

	public Foliensatz getfSatz() {
		return fSatz;
	}

	public void setFSatz(Foliensatz fSatz) {
		this.fSatz = fSatz;
	}

}

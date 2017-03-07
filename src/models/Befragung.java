package models;

import java.sql.Timestamp;

public class Befragung {

	private int befID;

	private Folie folie;
	private int folienID;

	private Timestamp beginn, ende;
	
	public Befragung(Folie f, int fID, Timestamp b, Timestamp e){
		this.befID = -1;
		
		this.folie = f;
		this.folienID = fID;
		this.beginn = b;
		this.ende = e;
	}
	
	public Befragung(){
	}

	public int getID() {
		return befID;
	}

	public void setID(int befID) {
		this.befID = befID;
	}

	public Folie getFolie() {
		return folie;
	}

	public void setFolie(Folie folie) {
		this.folie = folie;
	}

	public int getFolienID() {
		return folienID;
	}

	public void setFolienID(int folienID) {
		this.folienID = folienID;
	}

	public Timestamp getEnde() {
		return ende;
	}

	public void setEnde(Timestamp ende) {
		this.ende = ende;
	}

	public Timestamp getBeginn() {
		return beginn;
	}

	public void setBeginn(Timestamp beginn) {
		this.beginn = beginn;
	}
	
}

package models;

public class Auswahlbereich {

	private int auswahlBereichsID;

	private Folie folie;
	private int folienID;

	private int obenLinksX, obenLinksY, untenRechtsX, untenRechtsY;

	public Auswahlbereich(int fID, Folie f, int oLX, int oLY, int uRX, int uRY) {
		this.auswahlBereichsID = -1;

		this.folie = f;
		this.folienID = fID;

		this.obenLinksX = oLX;
		this.obenLinksY = oLY;
		this.untenRechtsX = uRX;
		this.untenRechtsY = uRY;
	}

	public Auswahlbereich() {
	}

	public int getID() {
		return auswahlBereichsID;
	}

	public void setID(int auswahlBereichsID) {
		this.auswahlBereichsID = auswahlBereichsID;
	}

	public int getFolienID() {
		return folienID;
	}

	public void setFolienID(int folienID) {
		this.folienID = folienID;
	}

	public Folie getFolie() {
		return folie;
	}

	public void setFolie(Folie folie) {
		this.folie = folie;
	}

	public int getUntenRechtsY() {
		return untenRechtsY;
	}

	public void setUntenRechtsY(int untenRechtsY) {
		this.untenRechtsY = untenRechtsY;
	}

	public int getObenLinksX() {
		return obenLinksX;
	}

	public void setObenLinksX(int obenLinksX) {
		this.obenLinksX = obenLinksX;
	}

	public int getObenLinksY() {
		return obenLinksY;
	}

	public void setObenLinksY(int obenLinksY) {
		this.obenLinksY = obenLinksY;
	}

	public int getUntenRechtsX() {
		return untenRechtsX;
	}

	public void setUntenRechtsX(int untenRechtsX) {
		this.untenRechtsX = untenRechtsX;
	}
}

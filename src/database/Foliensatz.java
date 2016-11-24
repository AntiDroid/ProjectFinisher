package database;

public class Foliensatz {

	private int folienSatzID;
	private int kursID;
	
	private String name;
	
	public Foliensatz(String n) {
		this.folienSatzID = -1;
		this.kursID = -1;
		
		this.name = n;
	}
	
}

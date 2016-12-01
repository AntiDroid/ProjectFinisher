package database;

public class Kurs extends DBObject{

	private int kursID;
	
	private String name;
	
	public Kurs(String n) {
		this.kursID = -1;
		
		this.name = n;
	}

	public int getID() {
		return kursID;
	}

	public void setID(int kursID) {
		this.kursID = kursID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void sqlSave(){
		DBManager.Instance().Save(this);
	}
	
	public void sqlDelete(){
		DBManager.Instance().Save(this);
	}
}

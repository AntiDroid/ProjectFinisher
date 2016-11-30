package database;

public class Kursteilnahme extends DBObject{

	private int kursteilnahmeID;
	
	private int kursID, studentenID;
	private Kurs kurs;
	private Student student;
	
	public Kursteilnahme(int kID, Kurs k, int sID, Student s){
		
		this.kursteilnahmeID = -1;
		
		this.kurs = k;
		this.student = s;
		this.kursID = kID;
		this.studentenID = sID;
	}

	public int getID() {
		return kursteilnahmeID;
	}

	public void setID(int kursteilnahmeID) {
		this.kursteilnahmeID = kursteilnahmeID;
	}

	public int getKursID() {
		return kursID;
	}

	public void setKursID(int kursID) {
		this.kursID = kursID;
	}

	public int getStudentenID() {
		return studentenID;
	}

	public void setStudentenID(int studentenID) {
		this.studentenID = studentenID;
	}
	
	public void Save(){
		DBManager.Instance().Save(this);
	}
	
	public void Delete(){
		DBManager.Instance().Save(this);
	}

	public Kurs getKurs() {
		return kurs;
	}

	public void setKurs(Kurs kurs) {
		this.kurs = kurs;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
}

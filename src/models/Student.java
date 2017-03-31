package models;

public class Student extends Client{

	private static final long serialVersionUID = 1L;
	
	public Student(String bn, String vn, String nn, String pw) {
		super(bn, vn, nn, pw);
	}
	
	public Student() {
		super();
	}
}

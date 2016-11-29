package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

public class DBManager {

	Connection conn;

	// Singleton Pattern
	private static DBManager instance = null;
	
	//Standardtabellen
	ConcurrentHashMap<Integer, Lehrer> lehrerMem = new ConcurrentHashMap<Integer, Lehrer>();
	ConcurrentHashMap<Integer, Student> studentMem = new ConcurrentHashMap<Integer, Student>();
	ConcurrentHashMap<Integer, Kurs> kursMem = new ConcurrentHashMap<Integer, Kurs>();
	ConcurrentHashMap<Integer, Uservoting> userVotMem = new ConcurrentHashMap<Integer, Uservoting>();
	ConcurrentHashMap<Integer, Folie> folieMem = new ConcurrentHashMap<Integer, Folie>();
	ConcurrentHashMap<Integer, Foliensatz> folienSatzMem = new ConcurrentHashMap<Integer, Foliensatz>();
	ConcurrentHashMap<Integer, Auswahlbereich> auswBMem = new ConcurrentHashMap<Integer, Auswahlbereich>();
	
	//Zwischentabellen
	//ConcurrentHashMap<Integer, Uservoting> userVotMem = new ConcurrentHashMap<Integer, Uservoting>();
	
	private DBManager(){

		try {
			conn = DriverManager.getConnection("jdbc:mysql:/localhost/interaktiveFolien", "root", "");
		} catch (SQLException e) {
			System.out.println("Datenbankverbindung fehlgeschlagen!");
			e.printStackTrace();
		}
	}

	public static DBManager Instance(){
		if (instance == null)
			instance = new DBManager();
		return instance;
	}

	public void Save(Lehrer p){

		if(p.getID() < 0){

			String sql = "INSERT INTO Person VALUES (?, ?, ?, ?)";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setString(2, p.getNachname());
				stat.setString(3, p.getVorname());
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if(rs.next()){
					p.setID(rs.getInt(1));
				}

			}catch(SQLException e){
				System.out.println("Insertproblem - Person");
			}
		}
		else{

			String sql = "UPDATE Person SET Nachname = ?, Vorname = ?, Geld = ? WHERE PersonID = ?";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, p.getNachname());
				stat.setString(2, p.getVorname());
				stat.setInt(4, p.getID());
				stat.execute();

			}catch(SQLException e){
				System.out.println("Updateproblem - Person");
			}
		}

	}
}

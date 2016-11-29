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
	ConcurrentHashMap<Integer, Berechtigung> rechteMem = new ConcurrentHashMap<Integer, Berechtigung>();
	
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

			String sql = "INSERT INTO Lehrer VALUES (?, ?, ?, ?)";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setString(2, p.getVorname());
				stat.setString(3, p.getNachname());
				stat.setString(4, p.getPasswort());
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if(rs.next()){
					p.setID(rs.getInt(1));
				}

			}catch(SQLException e){
				System.out.println("Insertproblem - Lehrer");
			}
		}
		else{

			String sql = "UPDATE Lehrer SET Vorname = ?, Nachname = ?, Passwort = ? WHERE LehrerID = ?";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, p.getVorname());
				stat.setString(2, p.getNachname());
				stat.setString(3, p.getPasswort());
				stat.setInt(4, p.getID());
				stat.execute();

			}catch(SQLException e){
				System.out.println("Updateproblem - Lehrer");
			}
		}
		
	}
	
	public void Save(Student st){

		if(st.getID() < 0){

			String sql = "INSERT INTO Student VALUES (?, ?, ?, ?)";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setString(2, st.getVorname());
				stat.setString(3, st.getNachname());
				stat.setString(4, st.getPasswort());
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if(rs.next()){
					st.setID(rs.getInt(1));
				}

			}catch(SQLException e){
				System.out.println("Insertproblem - Student");
			}
		}
		else{

			String sql = "UPDATE Student SET Vorname = ?, Nachname = ?, Passwort = ? WHERE StudentenID = ?";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, st.getVorname());
				stat.setString(2, st.getNachname());
				stat.setString(3, st.getPasswort());
				stat.setInt(4, st.getID());
				stat.execute();

			}catch(SQLException e){
				System.out.println("Updateproblem - Student");
			}
		}
	}
	
	public void Save(Kurs k){

		if(k.getID() < 0){

			String sql = "INSERT INTO Kurs VALUES (?, ?)";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setString(2, k.getName());
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if(rs.next()){
					k.setID(rs.getInt(1));
				}

			}catch(SQLException e){
				System.out.println("Insertproblem - Kurs");
			}
		}
		else{

			String sql = "UPDATE Kurs SET Name = ? WHERE KursID = ?";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, k.getName());
				stat.setInt(2, k.getID());
				stat.execute();

			}catch(SQLException e){
				System.out.println("Updateproblem - Kurs");
			}
		}
	}
	
	public void Save(Foliensatz f){

		if(f.getID() < 0){

			String sql = "INSERT INTO Foliensatz VALUES (?, ?, ?)";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, f.getKursID());
				stat.setString(3, f.getName());
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if(rs.next()){
					f.setID(rs.getInt(1));
				}

			}catch(SQLException e){
				System.out.println("Insertproblem - Foliensatz");
			}
		}
		else{

			String sql = "UPDATE Foliensatz SET KursID = ?, Name = ? WHERE FoliensatzID = ?";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setInt(1, f.getKursID());
				stat.setString(2, f.getName());
				stat.setInt(3, f.getID());
				stat.execute();

			}catch(SQLException e){
				System.out.println("Updateproblem - Foliensatz");
			}
		}
	}
	
	public void Save(Berechtigung b){

		if(b.getID() < 0){

			String sql = "INSERT INTO Berechtigung VALUES (?, ?, ?)";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setInt(1, b.getLehrerID());
				stat.setInt(2, b.getKursID());
				stat.setString(3, String.valueOf(b.getBerechtigungsTyp()));
				stat.execute();

			}catch(SQLException e){
				System.out.println("Insertproblem - Berechtigung");
			}
		}
		else{

			String sql = "UPDATE Berechtigung SET Berechtigungstyp = ? WHERE KursID = ? AND LehrerID = ?";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, String.valueOf(b.getBerechtigungsTyp()));
				stat.setInt(2, b.getKursID());
				stat.setInt(3, b.getLehrerID());
				stat.execute();

			}catch(SQLException e){
				System.out.println("Updateproblem - Berechtigung");
			}
		}
	}

	public void Save(Kursteilnahme k){

		if(k.getID() < 0){

			String sql = "INSERT INTO Kursteilnahme VALUES (?, ?)";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setInt(1, k.getKursID());
				stat.setInt(2, k.getStudentenID());
				stat.execute();

			}catch(SQLException e){
				System.out.println("Insertproblem - Kursteilnahme");
			}
		}
		else{

			String sql = "UPDATE Berechtigung SET Berechtigungstyp = ? WHERE KursID = ? AND LehrerID = ?";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, k.getStudentenID());
				stat.setInt(2, b.getKursID());
				stat.execute();

			}catch(SQLException e){
				System.out.println("Updateproblem - Kursteilnahme");
			}
		}
	}

	public void Save(Folie f){

		if(f.getID() < 0){

			String sql = "INSERT INTO Folie VALUES (?, ?, ?, ?)";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, f.getFoliensatzID());
				stat.setString(3, f.getfPath());
				stat.setString(4, String.valueOf(f.getFolienTyp()));
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if(rs.next()){
					f.setID(rs.getInt(1));
				}

			}catch(SQLException e){
				System.out.println("Insertproblem - Folie");
			}
		}
		else{

			String sql = "UPDATE Folie SET FoliensatzID = ?, fPath = ?, Folientyp = ? WHERE FolienID = ?";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setInt(1, f.getFoliensatzID());
				stat.setString(2, f.getfPath());
				stat.setString(3, String.valueOf(f.getFolienTyp()));
				stat.setInt(4, f.getID());
				stat.execute();

			}catch(SQLException e){
				System.out.println("Updateproblem - Folie");
			}
		}
	}
}

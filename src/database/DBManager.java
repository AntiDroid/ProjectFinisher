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

	//Typen als char- oder als enum-Elemente
	//bei delete aus der hashmap entfernen, sicher?
	
	// Singleton Pattern
	private static DBManager instance = null;
	
	//Standardtabellen
	ConcurrentHashMap<Integer, Auswahlbereich> auswBMem = new ConcurrentHashMap<Integer, Auswahlbereich>();
	ConcurrentHashMap<Integer, Folie> folieMem = new ConcurrentHashMap<Integer, Folie>();
	ConcurrentHashMap<Integer, Foliensatz> folienSatzMem = new ConcurrentHashMap<Integer, Foliensatz>();
	ConcurrentHashMap<Integer, Kurs> kursMem = new ConcurrentHashMap<Integer, Kurs>();
	ConcurrentHashMap<Integer, Lehrer> lehrerMem = new ConcurrentHashMap<Integer, Lehrer>();
	ConcurrentHashMap<Integer, Student> studentMem = new ConcurrentHashMap<Integer, Student>();
	ConcurrentHashMap<Integer, Uservoting> userVotMem = new ConcurrentHashMap<Integer, Uservoting>();
	
	//Zwischentabellen
	ConcurrentHashMap<Integer, Berechtigung> rechteMem = new ConcurrentHashMap<Integer, Berechtigung>();
	ConcurrentHashMap<Integer, Kursteilnahme> kursTeilnahmeMem = new ConcurrentHashMap<Integer, Kursteilnahme>();
	
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

	public void Save(Auswahlbereich a){
	
		if(a.getID() < 0){
	
			String sql = "INSERT INTO Auswahlbereich VALUES (?, ?, ?, ?)";
	
			try{
	
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, a.getFolienID());
				stat.setInt(3, a.getObenLinks());
				stat.setInt(4, a.getUntenRechts());
				stat.execute();
				
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
	
				if(rs.next()){
					a.setID(rs.getInt(1));
				}
	
			}catch(SQLException e){
				System.out.println("Insertproblem - Auswahlbereich");
			}
		}
		else{
	
			String sql = "UPDATE Auswahlbereich SET FolienID = ?, EckeOL = ?, EckeUR = ? WHERE BereichID = ?";
	
			try{
	
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setInt(1, a.getFolienID());
				stat.setInt(2, a.getObenLinks());
				stat.setInt(3, a.getUntenRechts());
				stat.setInt(4, a.getID());
				stat.execute();
	
			}catch(SQLException e){
				System.out.println("Updateproblem - Auswahlbereich");
			}
		}
	}
	
	public void Delete(Auswahlbereich a){
		
		String sql = "DELETE FROM Auswahlbereich WHERE BereichID = ?";
		
		try{

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, a.getID());
			stat.execute();

		}catch(SQLException e){
			System.out.println("Deleteproblem - Auswahlbereich");
		}
	}

	public void Save(Berechtigung b){
	
		if(b.getID() < 0){
	
			String sql = "INSERT INTO Berechtigung VALUES (?, ?, ?, ?)";
	
			try{
	
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, b.getLehrerID());
				stat.setInt(3, b.getKursID());
				stat.setString(4, String.valueOf(b.getBerechtigungsTyp()));
				stat.execute();
				
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
	
				if(rs.next()){
					b.setID(rs.getInt(1));
				}
	
			}catch(SQLException e){
				System.out.println("Insertproblem - Berechtigung");
			}
		}
		else{
	
			String sql = "UPDATE Berechtigung SET KursID = ?, Berechtigungstyp = ? WHERE BerechtigungsID = ?";
	
			try{
	
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setInt(1, b.getKursID());
				stat.setString(2, String.valueOf(b.getBerechtigungsTyp()));
				stat.setInt(3, b.getID());
				stat.execute();
	
			}catch(SQLException e){
				System.out.println("Updateproblem - Berechtigung");
			}
		}
	}

	public void Delete(Berechtigung b){
		
		String sql = "DELETE FROM Berechtigung WHERE BerechtigungsID = ?";
		
		try{

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, b.getID());
			stat.execute();

		}catch(SQLException e){
			System.out.println("Deleteproblem - Berechtigung");
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

	public void Delete(Folie f){
		
		String sql = "DELETE FROM Folie WHERE FolienID = ?";
		
		try{

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, f.getID());
			stat.execute();

		}catch(SQLException e){
			System.out.println("Deleteproblem - Folie");
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
	
	public void Delete(Foliensatz f){
		
		String sql = "DELETE FROM Foliensatz WHERE FoliensatzID = ?";
		
		try{

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, f.getID());
			stat.execute();

		}catch(SQLException e){
			System.out.println("Deleteproblem - Foliensatz");
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
	
	public void Delete(Kurs k){
		
		String sql = "DELETE FROM Kurs WHERE KursID = ?";
		
		try{

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, k.getID());
			stat.execute();

		}catch(SQLException e){
			System.out.println("Deleteproblem - Kurs");
		}
	}

	public void Save(Kursteilnahme k){
	
		if(k.getID() < 0){
	
			String sql = "INSERT INTO Kursteilnahme VALUES (?, ?, ?)";
	
			try{
	
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, k.getKursID());
				stat.setInt(3, k.getStudentenID());
				stat.execute();
				
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
	
				if(rs.next()){
					k.setID(rs.getInt(1));
				}
	
			}catch(SQLException e){
				System.out.println("Insertproblem - Kursteilnahme");
			}
		}
		else{
	
			String sql = "UPDATE Kursteilnahme SET KursID = ?, StudentenID = ? WHERE KursteilnahmeID = ?";
	
			try{
	
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setInt(1, k.getStudentenID());
				stat.setInt(2, k.getKursID());
				stat.setInt(3, k.getID());
				stat.execute();
	
			}catch(SQLException e){
				System.out.println("Updateproblem - Kursteilnahme");
			}
		}
	}
	
	public void Delete(Kursteilnahme k){
		
		String sql = "DELETE FROM Kursteilnahme WHERE KursteilnahmeID = ?";
		
		try{

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, k.getID());
			stat.execute();

		}catch(SQLException e){
			System.out.println("Deleteproblem - Kursteilnahme");
		}
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
	
	public void Delete(Lehrer l){
		
		String sql = "DELETE FROM Lehrer WHERE LehrerID = ?";
		
		try{

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, l.getID());
			stat.execute();

		}catch(SQLException e){
			System.out.println("Deleteproblem - Lehrer");
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
	
	public void Delete(Student s){
		
		String sql = "DELETE FROM Student WHERE StudentenID = ?";
		
		try{

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, s.getID());
			stat.execute();

		}catch(SQLException e){
			System.out.println("Deleteproblem - Student");
		}
	}
	
	public void Save(Uservoting u){

		if(u.getID() < 0){

			String sql = "INSERT INTO Uservoting VALUES (?, ?, ?, ?, ?, ?, ?)";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setString(2, u.getSessionID());
				stat.setInt(3, u.getStudentenID());
				stat.setInt(4, u.getFolienID());
				stat.setInt(5, u.getKoordX());
				stat.setInt(6, u.getKoordY());
				stat.setString(7, u.getAuswahloption());
				stat.execute();
				
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if(rs.next()){
					u.setID(rs.getInt(1));
				}

			}catch(SQLException e){
				System.out.println("Insertproblem - Uservoting");
			}
		}
		else{

			String sql = "UPDATE Kursteilnahme SET SessionID = ?, StudentenID = ?, FolienID = ?, "
					+ "KoordX = ?, KoordY = ?, Auswahloption = ? WHERE KursteilnahmeID = ?";

			try{

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, u.getSessionID());
				stat.setInt(2, u.getStudentenID());
				stat.setInt(3, u.getFolienID());
				stat.setInt(4, u.getKoordX());
				stat.setInt(5, u.getKoordY());
				stat.setInt(6, u.getID());
				stat.execute();

			}catch(SQLException e){
				System.out.println("Updateproblem - Uservoting");
			}
		}
	}
	
	public void Delete(Uservoting u){
		
		String sql = "DELETE FROM Uservoting WHERE VotingID = ?";
		
		try{

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, u.getID());
			stat.execute();

		}catch(SQLException e){
			System.out.println("Deleteproblem - Uservoting");
		}
	}
	
	public void synchronize(){
		
		for(Auswahlbereich x: auswBMem.values()){
			if(x.isToBeDeleted()){
				x.Delete();
				auswBMem.remove(x.getID());
			}
			else if(x.isModified()){
				x.Save();
				x.setModified(false);
			}
		}
		
		for(Folie x: folieMem.values()){
			if(x.isToBeDeleted()){
				x.Delete();
				folieMem.remove(x.getID());
			}
			else if(x.isModified()){
				x.Save();
				x.setModified(false);
			}
		}

		for(Foliensatz x: folienSatzMem.values()){
			if(x.isToBeDeleted()){
				x.Delete();
				folienSatzMem.remove(x.getID());
			}
			else if(x.isModified()){
				x.Save();
				x.setModified(false);
			}
		}
		
		for(Kurs x: kursMem.values()){
			if(x.isToBeDeleted()){
				x.Delete();
				kursMem.remove(x.getID());
			}
			else if(x.isModified()){
				x.Save();
				x.setModified(false);
			}
		}

		for(Lehrer x: lehrerMem.values()){
			if(x.isToBeDeleted()){
				x.Delete();
				lehrerMem.remove(x.getID());
			}
			else if(x.isModified()){
				x.Save();
				x.setModified(false);
			}
		}
		
		for(Student x: studentMem.values()){
			if(x.isToBeDeleted()){
				x.Delete();
				studentMem.remove(x.getID());
			}
			else if(x.isModified()){
				x.Save();
				x.setModified(false);
			}
		}
		
		for(Uservoting x: userVotMem.values()){
			if(x.isToBeDeleted()){
				x.Delete();
				userVotMem.remove(x.getID());
			}
			else if(x.isModified()){
				x.Save();
				x.setModified(false);
			}
		}
		
		for(Berechtigung x: rechteMem.values()){
			if(x.isToBeDeleted()){
				x.Delete();
				rechteMem.remove(x.getID());
			}
			else if(x.isModified()){
				x.Save();
				x.setModified(false);
			}
		}
		
		for(Kursteilnahme x: kursTeilnahmeMem.values()){
			if(x.isToBeDeleted()){
				x.Delete();
				kursTeilnahmeMem.remove(x.getID());
			}
			else if(x.isModified()){
				x.Save();
				x.setModified(false);
			}
		}
		
	}
}

package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.Auswahlbereich;
import models.Folie;
import models.Foliensatz;
import models.Kurs;
import models.Lehrer;
import models.Student;
import models.Uservoting;

public class DBManager {

	Connection conn;

	//	ev die System.out.println durch Einträge mit 
	//	java.util.Logging oder log4j ersetzen, auch e.printStackTrace()
	
	public DBManager() {

		try {
			conn = ConnectionPool.getInstance().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Datenbankverbindung fehlgeschlagen!");
		}
	}

	public void dispose() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Trennung der DB-Verbindung fehlgeschlagen!");
		}
	}

	public void save(Auswahlbereich a) {

		PreparedStatement stat = null;
		
		if (a.getID() < 0) {

			ResultSet rs = null;
			Statement statGetID = null;
			String sql = "INSERT INTO Auswahlbereich VALUES(?, ?, ?, ?, ?, ?)";
			
			try {

				stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, a.getFolienID());
				stat.setInt(3, a.getObenLinksX());
				stat.setInt(4, a.getObenLinksY());
				stat.setInt(5, a.getUntenRechtsX());
				stat.setInt(6, a.getUntenRechtsY());
				stat.execute();

				statGetID = conn.createStatement();
				rs = statGetID.executeQuery("SELECT LAST_INSERT_ID()");

				if (rs.next())
					a.setID(rs.getInt(1));
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Insertproblem - Auswahlbereich");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
				try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
			    try { if (statGetID != null) statGetID.close(); } catch (Exception e) {e.printStackTrace();};
			}
			
		} else {

			String sql = "UPDATE Auswahlbereich SET FolienID = ?, EckeOLX = ?, EckeOLY = ?, EckeURX = ?, EckeURY = ? WHERE BereichID = ?";

			try {

				stat = conn.prepareStatement(sql);
				stat.setInt(1, a.getFolienID());
				stat.setInt(2, a.getObenLinksX());
				stat.setInt(3, a.getObenLinksY());
				stat.setInt(4, a.getUntenRechtsX());
				stat.setInt(5, a.getUntenRechtsY());
				stat.setInt(6, a.getID());
				stat.execute();

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Updateproblem - Auswahlbereich");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			}
			
		}
	}

	public void save(Folie f) {
	
		PreparedStatement stat = null;
		
		if (f.getID() < 0) {
	
			ResultSet rs = null;
			Statement statGetID = null;
			String sql = "INSERT INTO Folie VALUES (?, ?, ?, ?)";
	
			try {
	
				stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, f.getFoliensatzID());
				stat.setString(3, f.getfPath());
				stat.setString(4, String.valueOf(f.getFolienTyp()));
				stat.execute();
	
				statGetID = conn.createStatement();
				rs = statGetID.executeQuery("SELECT LAST_INSERT_ID()");
	
				if (rs.next())
					f.setID(rs.getInt(1));
	
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Insertproblem - Folie");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
				try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
			    try { if (statGetID != null) statGetID.close(); } catch (Exception e) {e.printStackTrace();};
			}
			
		} else {
	
			String sql = "UPDATE Folie SET FoliensatzID = ?, fPath = ?, Folientyp = ? WHERE FolienID = ?";
	
			try {
	
				stat = conn.prepareStatement(sql);
				stat.setInt(1, f.getFoliensatzID());
				stat.setString(2, f.getfPath());
				stat.setString(3, String.valueOf(f.getFolienTyp()));
				stat.setInt(4, f.getID());
				stat.execute();
	
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Updateproblem - Folie");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			}
		}
	}

	public void save(Foliensatz f) {
	
		PreparedStatement stat = null;
		
		if (f.getID() < 0) {
	
			ResultSet rs = null;
			Statement statGetID = null;
			String sql = "INSERT INTO Foliensatz VALUES (?, ?, ?)";
	
			try {
	
				stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, f.getKursID());
				stat.setString(3, f.getName());
				stat.execute();
	
				statGetID = conn.createStatement();
				rs = statGetID.executeQuery("SELECT LAST_INSERT_ID()");
	
				if (rs.next())
					f.setID(rs.getInt(1));
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Insertproblem - Foliensatz");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
				try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
			    try { if (statGetID != null) statGetID.close(); } catch (Exception e) {e.printStackTrace();};
			}
			
		} else {
	
			String sql = "UPDATE Foliensatz SET KursID = ?, Name = ? WHERE FoliensatzID = ?";
	
			try {
				
				stat = conn.prepareStatement(sql);
				stat.setInt(1, f.getKursID());
				stat.setString(2, f.getName());
				stat.setInt(3, f.getID());
				stat.execute();
	
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Updateproblem - Foliensatz");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			}
		}
	}

	public void save(Kurs k) {
	
		PreparedStatement stat = null;
		
		if (k.getID() < 0) {
	
			ResultSet rs = null;
			Statement statGetID = null;
			String sql = "INSERT INTO Kurs VALUES (?, ?, ?, ?)";
	
			try {
	
				stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, k.getLehrerID());
				stat.setString(3, k.getName());
				stat.setString(4, k.getPasswort());
				stat.execute();
	
				statGetID = conn.createStatement();
				rs = statGetID.executeQuery("SELECT LAST_INSERT_ID()");
	
				if (rs.next())
					k.setID(rs.getInt(1));
	
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Insertproblem - Kurs");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
				try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
			    try { if (statGetID != null) statGetID.close(); } catch (Exception e) {e.printStackTrace();};
			}
			
		} else {
	
			String sql = "UPDATE Kurs SET Name = ?, Passwort = ? WHERE KursID = ?";
	
			try {
	
				stat = conn.prepareStatement(sql);
				stat.setString(1, k.getName());
				stat.setString(2, k.getPasswort());
				stat.setInt(3, k.getID());
				stat.execute();
	
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Updateproblem - Kurs");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			}
		}
	}

	public void save(Lehrer p) {
	
		PreparedStatement stat = null;
		
		if (p.getID() < 0) {
	
			ResultSet rs = null;
			Statement statGetID = null;
			String sql = "INSERT INTO Lehrer VALUES (?, ?, ?, ?, ?)";
	
			try {
	
				stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setString(2, p.getBenutzername());
				stat.setString(3, p.getVorname());
				stat.setString(4, p.getNachname());
				stat.setString(5, p.getPasswort());
				stat.execute();
	
				statGetID = conn.createStatement();
				rs = statGetID.executeQuery("SELECT LAST_INSERT_ID()");
	
				if (rs.next())
					p.setID(rs.getInt(1));
	
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Insertproblem - Lehrer");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
				try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
			    try { if (statGetID != null) statGetID.close(); } catch (Exception e) {e.printStackTrace();};
			}
			
		} else {
	
			String sql = "UPDATE Lehrer SET Vorname = ?, Nachname = ?, Passwort = ? WHERE LehrerID = ?";
	
			try {
	
				stat = conn.prepareStatement(sql);
				stat.setString(1, p.getVorname());
				stat.setString(2, p.getNachname());
				stat.setString(3, p.getPasswort());
				stat.setInt(4, p.getID());
				stat.execute();
	
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Updateproblem - Lehrer");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			}
		}
	
	}

	public void save(Student st) {
	
		PreparedStatement stat = null;
		
		if (st.getID() < 0) {
	
			ResultSet rs = null;
			Statement statGetID = null;
			String sql = "INSERT INTO Student VALUES (?, ?, ?, ?, ?)";
	
			try {
	
				stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setString(2, st.getBenutzername());
				stat.setString(3, st.getVorname());
				stat.setString(4, st.getNachname());
				stat.setString(5, st.getPasswort());
				stat.execute();
	
				statGetID = conn.createStatement();
				rs = statGetID.executeQuery("SELECT LAST_INSERT_ID()");
	
				if (rs.next()) 
					st.setID(rs.getInt(1));
	
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Insertproblem - Student");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
				try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
			    try { if (statGetID != null) statGetID.close(); } catch (Exception e) {e.printStackTrace();};
			}
			
		} else {
	
			String sql = "UPDATE Student SET Vorname = ?, Nachname = ?, Passwort = ? WHERE StudentenID = ?";
	
			try {
	
				stat = conn.prepareStatement(sql);
				stat.setString(1, st.getVorname());
				stat.setString(2, st.getNachname());
				stat.setString(3, st.getPasswort());
				stat.setInt(4, st.getID());
				stat.execute();
	
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Updateproblem - Student");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			}
			
		}
	}

	public void save(Uservoting u) {
	
		PreparedStatement stat = null;
		
		if (u.getID() < 0) {
	
			ResultSet rs = null;
			Statement statGetID = null;
			String sql = "INSERT INTO Uservoting VALUES (?, ?, ?, ?, ?, ?, ?)";
	
			try {
	
				stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, u.getBefID());
				stat.setInt(3, u.getStudentenID());
				stat.setInt(4, u.getFolienID());
				stat.setInt(5, u.getKoordX());
				stat.setInt(6, u.getKoordY());
				stat.setString(7, u.getAuswahloption());
				stat.execute();
	
				statGetID = conn.createStatement();
				rs = statGetID.executeQuery("SELECT LAST_INSERT_ID()");
	
				if (rs.next())
					u.setID(rs.getInt(1));
	
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Insertproblem - Uservoting");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
				try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
			    try { if (statGetID != null) statGetID.close(); } catch (Exception e) {e.printStackTrace();};
			}
			
		} else {
	
			String sql = "UPDATE Uservoting SET BefID = ?, StudentenID = ?, FolienID = ?, "
					+ "KoordX = ?, KoordY = ?, Auswahloption = ? WHERE VotingID = ?";
	
			try {
	
				stat = conn.prepareStatement(sql);
				stat.setInt(1, u.getBefID());
				stat.setInt(2, u.getStudentenID());
				stat.setInt(3, u.getFolienID());
				stat.setInt(4, u.getKoordX());
				stat.setInt(5, u.getKoordY());
				stat.setString(6, u.getAuswahloption());
				stat.setInt(7, u.getID());
				stat.execute();
	
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Updateproblem - Uservoting");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			}
		}
	}

	public void delete(Auswahlbereich a) {

		PreparedStatement stat = null;
		String sql = "DELETE FROM Auswahlbereich WHERE BereichID = ?";

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, a.getID());
			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Deleteproblem - Auswahlbereich");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
		}
	}

	public void delete(Folie f) {

		PreparedStatement stat = null;
		String sql = "DELETE FROM Folie WHERE FolienID = ?";

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, f.getID());
			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Deleteproblem - Folie");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
		}
	}

	public void delete(Foliensatz f) {

		PreparedStatement stat = null;
		String sql = "DELETE FROM Foliensatz WHERE FoliensatzID = ?";

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, f.getID());
			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Deleteproblem - Foliensatz");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
		}
	}

	public void delete(Kurs k) {

		String sql = "DELETE FROM Kurs WHERE Name = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, k.getName());
			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Deleteproblem - Kurs");
		}
	}

	public void delete(Lehrer l) {

		PreparedStatement stat = null;
		String sql = "DELETE FROM Lehrer WHERE LehrerID = ?";

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, l.getID());
			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Deleteproblem - Lehrer");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
		}
	}

	public void delete(Student s) {

		PreparedStatement stat = null;
		String sql = "DELETE FROM Student WHERE StudentenID = ?";

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, s.getID());
			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Deleteproblem - Student");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
		}
		
	}

	public void delete(Uservoting u) {

		PreparedStatement stat = null;
		String sql = "DELETE FROM Uservoting WHERE VotingID = ?";

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, u.getID());
			stat.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Deleteproblem - Uservoting");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
		}
	}

	public ArrayList<Auswahlbereich> getAuswahlbereiche(int folienID) {

		ArrayList<Auswahlbereich> list = new ArrayList<Auswahlbereich>();

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Auswahlbereich WHERE FolienID = ?";

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, folienID);

			rs = stat.executeQuery();

			while (rs.next()) {

				Auswahlbereich obj = new Auswahlbereich();

				obj.setID(rs.getInt("BereichID"));
				obj.setFolie(getFolie(folienID));
				obj.setFolienID(folienID);
				obj.setObenLinksX(rs.getInt("EckeOLX"));
				obj.setObenLinksY(rs.getInt("EckeOLY"));
				obj.setUntenRechtsX(rs.getInt("EckeURX"));
				obj.setUntenRechtsY(rs.getInt("EckeURY"));

				list.add(obj);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Auswahlbereiche");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return list;
	}

	public ArrayList<Folie> getFolien(int folienSatzID) {

		ArrayList<Folie> list = new ArrayList<Folie>();

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Folie WHERE FoliensatzID = ?";

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, folienSatzID);

			rs = stat.executeQuery();

			while (rs.next()) {

				Folie obj = new Folie();

				obj.setID(rs.getInt("FolienID"));
				obj.setFSatz(getFoliensatz(folienSatzID));
				obj.setFoliensatzID(folienSatzID);
				obj.setfPath(rs.getString("fPath"));
				obj.setFolienTyp(rs.getString("FolienTyp").charAt(0));

				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Folien");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return list;
	}

	public ArrayList<Foliensatz> getFoliensätze(int kursID) {

		ArrayList<Foliensatz> list = new ArrayList<Foliensatz>();

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Foliensatz WHERE KursID = ?";

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, kursID);

			rs = stat.executeQuery();

			while (rs.next()) {

				Foliensatz obj = new Foliensatz();

				obj.setID(rs.getInt("FoliensatzID"));
				obj.setKursID(kursID);
				obj.setKurs(getKurs(kursID));
				obj.setName(rs.getString("Name"));

				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Foliensätze");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return list;
	}

	public ArrayList<Kurs> getKurse() {

		ArrayList<Kurs> list = new ArrayList<Kurs>();

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Kurs";

		try {

			stat = conn.prepareStatement(sql);
			rs = stat.executeQuery();

			while (rs.next()) {

				Kurs obj = new Kurs();

				obj.setID(rs.getInt("KursID"));
				obj.setName(rs.getString("Name"));
				obj.setPasswort(rs.getString("Passwort"));

				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Kurse");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return list;
	}
	
	public ArrayList<Kurs> getKurseStudent(int studentenID) {

		ArrayList<Kurs> list = new ArrayList<Kurs>();

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT k.KursID, k.Name, k.Passwort FROM Student s "
				+ "JOIN Kursteilnahme USING(StudentenID) "
				+ "JOIN Kurs k USING(KursID) "
				+ "WHERE s.StudentenID = ?";

		try {
			
			stat = conn.prepareStatement(sql);
			stat.setInt(1, studentenID);
			rs = stat.executeQuery();

			while (rs.next()) {

				Kurs obj = new Kurs();

				obj.setID(rs.getInt("KursID"));
				obj.setName(rs.getString("Name"));
				obj.setPasswort(rs.getString("Passwort"));

				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Kurse von Student");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return list;
	}
	
	public ArrayList<Kurs> getKurseLehrer(int lehrerID) {

		ArrayList<Kurs> list = new ArrayList<Kurs>();

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Kurs WHERE LehrerID = ?";

		try {
			
			stat = conn.prepareStatement(sql);
			stat.setInt(1, lehrerID);
			rs = stat.executeQuery();

			while (rs.next()) {

				Kurs obj = new Kurs();

				obj.setID(rs.getInt("KursID"));
				obj.setName(rs.getString("Name"));
				obj.setPasswort(rs.getString("Passwort"));

				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Kurse von Lehrer");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return list;
	}

	public Auswahlbereich getAuswahlbereich(int awID) {

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Auswahlbereich WHERE BereichID = ?";
		Auswahlbereich obj = new Auswahlbereich();

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, awID);
			rs = stat.executeQuery();

			if (rs.next()) {
				obj.setID(awID);
				obj.setFolie(getFolie(rs.getInt("FolienID")));
				obj.setFolienID(rs.getInt("FolienID"));
				obj.setObenLinksX(rs.getInt("EckeOLX"));
				obj.setObenLinksY(rs.getInt("EckeOLY"));
				obj.setUntenRechtsX(rs.getInt("EckeURX"));
				obj.setUntenRechtsY(rs.getInt("EckeURY"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Folie");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return obj;
	}
	
	public Folie getFolie(int folienID) {

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Folie WHERE FolienID = ?";
		Folie obj = new Folie();

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, folienID);
			rs = stat.executeQuery();

			if (rs.next()) {
				obj.setID(folienID);
				obj.setFSatz(this.getFoliensatz(rs.getInt("FoliensatzID")));
				obj.setFoliensatzID(rs.getInt("FoliensatzID"));
				obj.setfPath(rs.getString("fPath"));
				obj.setFolienTyp(rs.getString("FolienTyp").charAt(0));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Folie");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return obj;
	}
	
	public Foliensatz getFoliensatz(int folienSatzID) {

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Folie f JOIN Foliensatz USING(FoliensatzID) WHERE f.FolienID = ?";
		Foliensatz obj = new Foliensatz();

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, folienSatzID);
			rs = stat.executeQuery();

			if (rs.next()) {
				obj.setID(folienSatzID);
				obj.setKurs(this.getKurs(rs.getInt("KursID")));
				obj.setKursID(rs.getInt("KursID"));
				obj.setName(rs.getString("Name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Foliensatz");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return obj;
	}
	
	public Kurs getKurs(int kursID) {

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Kurs WHERE KursID = ?";
		Kurs obj = new Kurs();

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, kursID);
			rs = stat.executeQuery();

			if (rs.next()) {
				obj.setID(kursID);
				obj.setName(rs.getString("Name"));
				obj.setPasswort(rs.getString("Passwort"));
				obj.setLehrerID(rs.getInt("LehrerID"));
				obj.setLehrer(getLehrer(rs.getInt("LehrerID")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Kurs");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return obj;
	}
	
	public Lehrer getLehrer(String bn) {

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Lehrer WHERE Benutzername = ?";
		Lehrer obj = new Lehrer();

		try {

			stat = conn.prepareStatement(sql);
			stat.setString(1, bn);
			rs = stat.executeQuery();

			if (rs.next()) {
				obj.setID(rs.getInt("LehrerID"));
				obj.setBenutzername(bn);
				obj.setVorname(rs.getString("Vorname"));
				obj.setNachname(rs.getString("Nachname"));
				obj.setPasswort(rs.getString("Passwort"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Lehrer");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return obj;
	}
	
	public Lehrer getLehrer(int lehrerID) {

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Lehrer WHERE LehrerID = ?";
		Lehrer obj = new Lehrer();

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, lehrerID);
			rs = stat.executeQuery();

			if (rs.next()) {
				obj.setID(lehrerID);
				obj.setBenutzername(rs.getString("Benutzername"));
				obj.setVorname(rs.getString("Vorname"));
				obj.setNachname(rs.getString("Nachname"));
				obj.setPasswort(rs.getString("Passwort"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Lehrer");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return obj;
	}

	public Student getStudent(String bn) {

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Student WHERE Benutzername = ?";
		Student obj = new Student();

		try {

			stat = conn.prepareStatement(sql);
			stat.setString(1, bn);
			rs = stat.executeQuery();

			if (rs.next()) {
				obj.setID(rs.getInt("StudentenID"));
				obj.setBenutzername(bn);
				obj.setVorname(rs.getString("Vorname"));
				obj.setNachname(rs.getString("Nachname"));
				obj.setPasswort(rs.getString("Passwort"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Student");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return obj;
	}

	public Student getStudent(int studentID) {

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Student WHERE StudentenID = ?";
		Student obj = new Student();

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, studentID);
			rs = stat.executeQuery();

			if (rs.next()) {
				obj.setID(studentID);
				obj.setBenutzername(rs.getString("Benutzername"));
				obj.setVorname(rs.getString("Vorname"));
				obj.setNachname(rs.getString("Nachname"));
				obj.setPasswort(rs.getString("Passwort"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Student");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return obj;
	}
	
	// # was wenn alle Uservotings einer folie und session gebraucht werden?
	public ArrayList<Uservoting> getUservotings(int studentID, int folienID, int befID) {

		ArrayList<Uservoting> list = new ArrayList<Uservoting>();

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Uservoting WHERE FolienID = ?";

		try {
			
			if(studentID > 0)
				sql += " AND StudentenID = ?";
			if(befID > 0)
				sql += " AND BefID = ?";
			
			stat = conn.prepareStatement(sql);
			stat.setInt(1, folienID);
			if(studentID != 0)
				stat.setInt(2, studentID);
			if(befID > 0)
				stat.setInt(3, befID);
			
			rs = stat.executeQuery();

			while (rs.next()) {

				Uservoting obj = new Uservoting();

				obj.setID(rs.getInt("VotingID"));
				obj.setBefID(rs.getInt("BefID"));
				obj.setStudentenID(studentID);
				obj.setStudent(getStudent(studentID));
				obj.setFolienID(folienID);
				obj.setFolie(getFolie(folienID));
				obj.setKoordX(rs.getInt("KoordX"));
				obj.setKoordY(rs.getInt("KoordY"));
				obj.setAuswahloption(rs.getString("Auswahloption"));

				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Uservotings");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return list;
	}

	public boolean isKursBeteiligt(String kurs, String student) {
	
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT KursID, Name FROM Kursteilnahme JOIN Kurs k USING(KursID) "
				+ "JOIN Student s USING(StudentenID) "
				+ "WHERE k.Name = ? AND s.Benutzername = ?";
	
		try {
	
			stat = conn.prepareStatement(sql);
			stat.setString(1, kurs);
			stat.setString(2, student);
	
			rs = stat.executeQuery();
	
			if (rs.next())
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - isKursBeteiligt");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}
	
		return false;
	}

	public boolean isStudent(String benutzer, String pw) {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Student WHERE Benutzername = ? AND Passwort = ?";
	
		try {
	
			stat = conn.prepareStatement(sql);
			stat.setString(1, benutzer);
			stat.setString(2, pw);
	
			rs = stat.executeQuery();
	
			if (rs.next())
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - isStudent");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}
	
		return false;
	}
	
	public boolean isLehrer(String benutzer, String pw) {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Lehrer WHERE Benutzername = ? AND Passwort = ?";
	
		try {
	
			stat = conn.prepareStatement(sql);
			stat.setString(1, benutzer);
			stat.setString(2, pw);
	
			rs = stat.executeQuery();
	
			if (rs.next())
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - isLehrer");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		}
	
		return false;
	}
	
	public void addKursteilnahme(int kursID, int studentID){
			
			PreparedStatement stat = null;
			ResultSet rs = null;
			
			String sql = "INSERT INTO Kursteilnahme VALUES (?, ?, ?)";
		
			try {
				
				stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, kursID);
				stat.setInt(3, studentID);
				stat.execute();
		
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Insertproblem - Kursteilnahme");
			} finally {
				try { if (stat != null) stat.close(); } catch (Exception e) {e.printStackTrace();};
				try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
			}
	}
}
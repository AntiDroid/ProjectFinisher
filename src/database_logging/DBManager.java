package database_logging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.Auswahlbereich;
import models.Befragung;
import models.Folie;
import models.Foliensatz;
import models.Kurs;
import models.Lehrer;
import models.Student;
import models.Uservoting;

public class DBManager {

	Connection conn;
	
	public DBManager() {

		try {
			conn = ConnectionPool.getInstance().getConnection();
			
		} catch (Exception e) {
			MyLogger.getLogger().severe("Datenbankverbindung fehlgeschlagen!");
			MyLogger.getLogger().severe(e.getMessage());
		}
	}

	public void dispose() {
		try {
			conn.close();
		} catch (SQLException e) {
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Trennung der DB-Verbindung fehlgeschlagen!");
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
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Insertproblem - Auswahlbereich");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
				try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			    try { if (statGetID != null) statGetID.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Updateproblem - Auswahlbereich");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			}
			
		}
	}

	public void save(Befragung b) {

		PreparedStatement stat = null;
		
		if (b.getID() < 0) {

			ResultSet rs = null;
			Statement statGetID = null;
			String sql = "INSERT INTO Befragung VALUES(?, ?, ?, ?)";
			
			try {

				stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, b.getFolienID());
				stat.setTimestamp(3, b.getBeginn());
				stat.setTimestamp(4, b.getEnde());
				stat.execute();

				statGetID = conn.createStatement();
				rs = statGetID.executeQuery("SELECT LAST_INSERT_ID()");

				if (rs.next())
					b.setID(rs.getInt(1));
				
			} catch (SQLException e) {
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Insertproblem - Befragung");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
				try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			    try { if (statGetID != null) statGetID.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			}
			
		} else {

			String sql = "UPDATE Befragung SET FolienID = ?, Beginn = ?, Ende = ? WHERE BefID = ?";

			try {
				stat = conn.prepareStatement(sql);
				stat.setInt(1, b.getFolienID());
				stat.setTimestamp(2, b.getBeginn());
				stat.setTimestamp(3, b.getEnde());
				stat.setInt(4, b.getID());
				stat.execute();

			} catch (SQLException e) {
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Updateproblem - Befragung");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Insertproblem - Folie");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
				try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			    try { if (statGetID != null) statGetID.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Updateproblem - Folie");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Insertproblem - Foliensatz");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
				try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			    try { if (statGetID != null) statGetID.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Updateproblem - Foliensatz");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Insertproblem - Kurs");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
				try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			    try { if (statGetID != null) statGetID.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Updateproblem - Kurs");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Insertproblem - Lehrer");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
				try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			    try { if (statGetID != null) statGetID.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Updateproblem - Lehrer");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Insertproblem - Student");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
				try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			    try { if (statGetID != null) statGetID.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Updateproblem - Student");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Insertproblem - Uservoting");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
				try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			    try { if (statGetID != null) statGetID.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Updateproblem - Uservoting");
			} finally {
			    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Deleteproblem - Auswahlbereich");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
		}
	}
	
	public void delete(Befragung b) {

		PreparedStatement stat = null;
		String sql = "DELETE FROM Befragung WHERE BefID = ?";

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, b.getID());
			stat.execute();

		} catch (SQLException e) {
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Deleteproblem - Befragung");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Deleteproblem - Folie");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Deleteproblem - Foliensatz");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
		}
	}

	public void delete(Kurs k) {

		String sql = "DELETE FROM Kurs WHERE Name = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, k.getName());
			stat.execute();

		} catch (SQLException e) {
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Deleteproblem - Kurs");
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Deleteproblem - Lehrer");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Deleteproblem - Student");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Deleteproblem - Uservoting");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Auswahlbereiche");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Folien");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Foliensätze");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
		}

		return list;
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Kurs");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
		}
	
		return obj;
	}
	
	public Kurs getKurs(String kursName) {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Kurs WHERE Name = ?";
		Kurs obj = new Kurs();
	
		try {
	
			stat = conn.prepareStatement(sql);
			stat.setString(1, kursName);
			rs = stat.executeQuery();
	
			if (rs.next()) {
				obj.setID(rs.getInt("KursID"));
				obj.setName(kursName);
				obj.setPasswort(rs.getString("Passwort"));
				obj.setLehrerID(rs.getInt("LehrerID"));
				obj.setLehrer(getLehrer(rs.getInt("LehrerID")));
			}
	
		} catch (SQLException e) {
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Kurs");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
		}
	
		return obj;
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Kurse");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Kurse von Student");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Kurse von Lehrer");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Folie");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Folie");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
		}

		return obj;
	}
	
	public Foliensatz getFoliensatz(int folienSatzID) {

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Foliensatz WHERE FoliensatzID = ?";
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Foliensatz");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Lehrer");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Lehrer");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Student");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Student");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			
			int count = 2;
			if(studentID > 0){
				stat.setInt(count, studentID);
				count++;
			}
			if(befID > 0)
				stat.setInt(count, befID);
			
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Uservotings");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
		}

		return list;
	}
	
	public Befragung getBefragung(int befID) {

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Befragung WHERE BefID = ?";
		Befragung obj = new Befragung();

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, befID);
			rs = stat.executeQuery();

			if (rs.next()) {
				obj.setID(befID);
				obj.setFolie(getFolie(rs.getInt("FolienID")));
				obj.setFolienID(rs.getInt("FolienID"));
				obj.setBeginn(rs.getTimestamp("Beginn"));
				obj.setEnde(rs.getTimestamp("Ende"));
			}

		} catch (SQLException e) {
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Befragung");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
		}

		return obj;
	}
	
	public ArrayList<Befragung> getBefragungen(int folienID) {

		ArrayList<Befragung> list = new ArrayList<Befragung>();

		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Befragung WHERE FolienID = ?";

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, folienID);
			rs = stat.executeQuery();

			while (rs.next()) {

				Befragung obj = new Befragung();

				obj.setID(rs.getInt("BefID"));
				obj.setFolie(getFolie(rs.getInt("FolienID")));
				obj.setFolienID(rs.getInt("FolienID"));
				obj.setBeginn(rs.getTimestamp("Beginn"));
				obj.setEnde(rs.getTimestamp("Ende"));

				list.add(obj);
			}

		} catch (SQLException e) {
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Befragungen");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
		}

		return list;
	}
	
	public Folie getAktiveFolie(int kursID){
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT FolienID FROM Folie "
				+ "JOIN Foliensatz USING(FoliensatzID) "
				+ "JOIN Kurs k USING(KursID) "
				+ "JOIN Befragung b USING(FolienID) "
				+ "WHERE b.Ende IS NULL AND k.KursID = ?";

		try {

			stat = conn.prepareStatement(sql);
			stat.setInt(1, kursID);
			rs = stat.executeQuery();

			if (rs.next()) 
				return getFolie(rs.getInt("FolienID"));

		} catch (SQLException e) {
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - Befragung");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
		}

		return null;
		
	}
	
	public int getCurrentBef(int kursID){
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT BefID FROM Befragung "
				+ "JOIN Folie USING(FolienID) "
				+ "JOIN Foliensatz USING(FoliensatzID) "
				+ "JOIN Kurs USING (KursID) "
				+ "WHERE FolienID = ? AND Ende IS NULL";
	
		try {
	
			stat = conn.prepareStatement(sql);
			stat.setInt(1, kursID);
			rs = stat.executeQuery();
	
			if (rs.next())
				return rs.getInt("BefID");
			
		} catch (SQLException e) {
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - getCurrentBef");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
		}
	
		return 0;
	}

	public boolean isKursVorhanden(String kurs) {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Kurs WHERE Name = ?";
	
		try {
	
			stat = conn.prepareStatement(sql);
			stat.setString(1, kurs);
	
			rs = stat.executeQuery();
	
			if (rs.next())
				return true;
			
		} catch (SQLException e) {
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - isKursVorhanden");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
		}
	
		return false;
	}
	
	public boolean isKursBeteiligt(String kurs, String student) {
	
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Kursteilnahme "
				+ "JOIN Kurs k USING(KursID) "
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - isKursBeteiligt");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - isStudent");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - isLehrer");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
		}
	
		return false;
	}
	
	public boolean isBeantwortet(int befID, int folienID, int studentID){
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Uservoting WHERE BefID = ? AND FolienID = ? AND StudentenID = ?";
	
		try {
	
			stat = conn.prepareStatement(sql);
			stat.setInt(1, befID);
			stat.setInt(2, folienID);
			stat.setInt(3, studentID);
	
			rs = stat.executeQuery();
	
			if (rs.next())
				return true;
			
		} catch (SQLException e) {
			MyLogger.getLogger().severe(e.getMessage());
			MyLogger.getLogger().severe("Selectproblem - isBeantwortet");
		} finally {
		    try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
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
				MyLogger.getLogger().severe(e.getMessage());
				MyLogger.getLogger().severe("Insertproblem - Kursteilnahme");
			} finally {
				try { if (stat != null) stat.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
				try { if (rs != null) rs.close(); } catch (Exception e) {MyLogger.getLogger().severe(e.getMessage());};
			}
	}
}
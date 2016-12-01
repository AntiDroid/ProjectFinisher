package database;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBManager {


	// singleton aufgrund von mehrfach Zugriffen entfernen
	// Zwischenspeicher mit HashMaps unwichtig
	// Datenbankeigenschaften aus Klassen entfernen
	
	//Transaktionen commit und rollback

	// Singleton Pattern rausnehmen?
	private static DBManager instance = null;

	public static DBManager Instance() {
		if (instance == null) instance = new DBManager();
		return instance;
	}

	public Connection GetConnection() throws SQLException
	{
		// Alt aus Pool
		// return cpds.getConnection();
		
		// ALT
		return  DriverManager.getConnection(
							"jdbc:mysql:/localhost/interaktiveFolien", "root", "");
	}
	public void closeConnection(Connection conn) throws SQLException
	{
		conn.close();
	}
	
	ComboPooledDataSource cpds = new ComboPooledDataSource();
	
	private void InitalizeConnectionPool() throws PropertyVetoException
	{
		// Bitte Parameter aus Conf lesen und setzen
		cpds.setDriverClass("com.mysql.jdbc.Driver"); // loads the jdbc
														// driver
		cpds.setJdbcUrl("jdbc:mysql:/localhost/interaktiveFolien");
		cpds.setUser("root");
		cpds.setPassword("");

		//the settings below are optional -- c3p0 can work with defaults
		// cpds.setMinPoolSize(5);
		// cpds.setAcquireIncrement(5);
		// cpds.setMaxPoolSize(20);
	}
	
	
	
	private DBManager() {

		try {

			// NEU
			InitalizeConnectionPool();

			

		} catch (Exception e) {
			System.out.println("Datenbankverbindung fehlgeschlagen!");
			e.printStackTrace();
		}
	}

	
	
	public void sqlSave(Connection conn, Auswahlbereich a) {

		if (a.getID() < 0) {

			String sql = "INSERT INTO Auswahlbereich VALUES (?, ?, ?, ?)";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, a.getFolienID());
				stat.setInt(3, a.getObenLinks());
				stat.setInt(4, a.getUntenRechts());
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if (rs.next()) {
					a.setID(rs.getInt(1));
				}

			} catch (SQLException e) {
				System.out.println("Insertproblem - Auswahlbereich");
			}
		} else {

			String sql = "UPDATE Auswahlbereich SET FolienID = ?, EckeOL = ?, EckeUR = ? WHERE BereichID = ?";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setInt(1, a.getFolienID());
				stat.setInt(2, a.getObenLinks());
				stat.setInt(3, a.getUntenRechts());
				stat.setInt(4, a.getID());
				stat.execute();

			} catch (SQLException e) {
				System.out.println("Updateproblem - Auswahlbereich");
			}
		}
	}

	public void sqlDelete(Connection conn, Auswahlbereich a) {

		String sql = "DELETE FROM Auswahlbereich WHERE BereichID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, a.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Auswahlbereich");
		}
	}

	public void sqlSave(Connection conn, Berechtigung b) {

		if (b.getID() < 0) {

			String sql = "INSERT INTO Berechtigung VALUES (?, ?, ?, ?)";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, b.getLehrerID());
				stat.setInt(3, b.getKursID());
				stat.setString(4, String.valueOf(b.getBerechtigungsTyp()));
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if (rs.next()) {
					b.setID(rs.getInt(1));
				}

			} catch (SQLException e) {
				System.out.println("Insertproblem - Berechtigung");
			}
		} else {

			String sql = "UPDATE Berechtigung SET KursID = ?, Berechtigungstyp = ? WHERE BerechtigungsID = ?";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setInt(1, b.getKursID());
				stat.setString(2, String.valueOf(b.getBerechtigungsTyp()));
				stat.setInt(3, b.getID());
				stat.execute();

			} catch (SQLException e) {
				System.out.println("Updateproblem - Berechtigung");
			}
		}
	}

	public void sqlDelete(Connection conn, Berechtigung b) {

		String sql = "DELETE FROM Berechtigung WHERE BerechtigungsID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, b.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Berechtigung");
		}
	}

	public void sqlSave(Connection conn, Folie f) {

		if (f.getID() < 0) {

			String sql = "INSERT INTO Folie VALUES (?, ?, ?, ?)";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, f.getFoliensatzID());
				stat.setString(3, f.getfPath());
				stat.setString(4, String.valueOf(f.getFolienTyp()));
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if (rs.next()) {
					f.setID(rs.getInt(1));
				}

			} catch (SQLException e) {
				System.out.println("Insertproblem - Folie");
			}
		} else {

			String sql = "UPDATE Folie SET FoliensatzID = ?, fPath = ?, Folientyp = ? WHERE FolienID = ?";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setInt(1, f.getFoliensatzID());
				stat.setString(2, f.getfPath());
				stat.setString(3, String.valueOf(f.getFolienTyp()));
				stat.setInt(4, f.getID());
				stat.execute();

			} catch (SQLException e) {
				System.out.println("Updateproblem - Folie");
			}
		}
	}

	public void sqlDelete(Connection conn, Folie f) {

		String sql = "DELETE FROM Folie WHERE FolienID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, f.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Folie");
		}
	}

	public void sqlSave(Connection conn, Foliensatz f) {

		if (f.getID() < 0) {

			String sql = "INSERT INTO Foliensatz VALUES (?, ?, ?)";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, f.getKursID());
				stat.setString(3, f.getName());
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if (rs.next()) {
					f.setID(rs.getInt(1));
				}

			} catch (SQLException e) {
				System.out.println("Insertproblem - Foliensatz");
			}
		} else {

			String sql = "UPDATE Foliensatz SET KursID = ?, Name = ? WHERE FoliensatzID = ?";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setInt(1, f.getKursID());
				stat.setString(2, f.getName());
				stat.setInt(3, f.getID());
				stat.execute();

			} catch (SQLException e) {
				System.out.println("Updateproblem - Foliensatz");
			}
		}
	}

	public void sqlDelete(Connection conn, Foliensatz f) {

		String sql = "DELETE FROM Foliensatz WHERE FoliensatzID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, f.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Foliensatz");
		}
	}

	public void sqlSave(Connection conn, Kurs k) {

		if (k.getID() < 0) {

			String sql = "INSERT INTO Kurs VALUES (?, ?)";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setString(2, k.getName());
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if (rs.next()) {
					k.setID(rs.getInt(1));
				}

			} catch (SQLException e) {
				System.out.println("Insertproblem - Kurs");
			}
		} else {

			String sql = "UPDATE Kurs SET Name = ? WHERE KursID = ?";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, k.getName());
				stat.setInt(2, k.getID());
				stat.execute();

			} catch (SQLException e) {
				System.out.println("Updateproblem - Kurs");
			}
		}
	}

	public void sqlDelete(Connection conn, Kurs k) {

		String sql = "DELETE FROM Kurs WHERE KursID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, k.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Kurs");
		}
	}

	public void sqlSave(Connection conn, Kursteilnahme k) {

		if (k.getID() < 0) {

			String sql = "INSERT INTO Kursteilnahme VALUES (?, ?, ?)";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, k.getKursID());
				stat.setInt(3, k.getStudentenID());
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if (rs.next()) {
					k.setID(rs.getInt(1));
				}

			} catch (SQLException e) {
				System.out.println("Insertproblem - Kursteilnahme");
			}
		} else {

			String sql = "UPDATE Kursteilnahme SET KursID = ?, StudentenID = ? WHERE KursteilnahmeID = ?";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setInt(1, k.getStudentenID());
				stat.setInt(2, k.getKursID());
				stat.setInt(3, k.getID());
				stat.execute();

			} catch (SQLException e) {
				System.out.println("Updateproblem - Kursteilnahme");
			}
		}
	}

	public void sqlDelete(Connection conn, Kursteilnahme k) {

		String sql = "DELETE FROM Kursteilnahme WHERE KursteilnahmeID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, k.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Kursteilnahme");
		}
	}

	public void sqlSave(Connection conn, Lehrer p) {

		if (p.getID() < 0) {

			String sql = "INSERT INTO Lehrer VALUES (?, ?, ?, ?)";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setString(2, p.getVorname());
				stat.setString(3, p.getNachname());
				stat.setString(4, p.getPasswort());
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if (rs.next()) {
					p.setID(rs.getInt(1));
				}

			} catch (SQLException e) {
				System.out.println("Insertproblem - Lehrer");
			}
		} else {

			String sql = "UPDATE Lehrer SET Vorname = ?, Nachname = ?, Passwort = ? WHERE LehrerID = ?";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, p.getVorname());
				stat.setString(2, p.getNachname());
				stat.setString(3, p.getPasswort());
				stat.setInt(4, p.getID());
				stat.execute();

			} catch (SQLException e) {
				System.out.println("Updateproblem - Lehrer");
			}
		}

	}

	public void sqlDelete(Connection conn, Lehrer l) {

		String sql = "DELETE FROM Lehrer WHERE LehrerID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, l.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Lehrer");
		}
	}

	public void sqlSave(Connection conn, Student st) {

		if (st.getID() < 0) {

			String sql = "INSERT INTO Student VALUES (?, ?, ?, ?)";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setString(2, st.getVorname());
				stat.setString(3, st.getNachname());
				stat.setString(4, st.getPasswort());
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if (rs.next()) {
					st.setID(rs.getInt(1));
				}

			} catch (SQLException e) {
				System.out.println("Insertproblem - Student");
			}
		} else {

			String sql = "UPDATE Student SET Vorname = ?, Nachname = ?, Passwort = ? WHERE StudentenID = ?";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, st.getVorname());
				stat.setString(2, st.getNachname());
				stat.setString(3, st.getPasswort());
				stat.setInt(4, st.getID());
				stat.execute();

			} catch (SQLException e) {
				System.out.println("Updateproblem - Student");
			}
		}
	}

	public void sqlDelete(Connection conn, Student s) {

		String sql = "DELETE FROM Student WHERE StudentenID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, s.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Student");
		}
	}

	public void sqlSave(Connection conn, Uservoting u) {

		if (u.getID() < 0) {

			String sql = "INSERT INTO Uservoting VALUES (?, ?, ?, ?, ?, ?, ?)";

			try {

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

				if (rs.next()) {
					u.setID(rs.getInt(1));
				}

			} catch (SQLException e) {
				System.out.println("Insertproblem - Uservoting");
			}
		} else {

			String sql = "UPDATE Kursteilnahme SET SessionID = ?, StudentenID = ?, FolienID = ?, "
					+ "KoordX = ?, KoordY = ?, Auswahloption = ? WHERE KursteilnahmeID = ?";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, u.getSessionID());
				stat.setInt(2, u.getStudentenID());
				stat.setInt(3, u.getFolienID());
				stat.setInt(4, u.getKoordX());
				stat.setInt(5, u.getKoordY());
				stat.setInt(6, u.getID());
				stat.execute();

			} catch (SQLException e) {
				System.out.println("Updateproblem - Uservoting");
			}
		}
	}

	public void sqlDelete(Connection conn, Uservoting u) {

		String sql = "DELETE FROM Uservoting WHERE VotingID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, u.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Uservoting");
		}
	}

	/*	//Unnötig
	public void sqlSynchronize(Connection conn) {

		for (Auswahlbereich x : auswBMem.values()) {
			if (x.isToBeDeleted()) {
				x.sqlDelete(conn);
				auswBMem.remove(x.getID());
			} else if (x.isModified()) {
				x.sqlSave(conn);
				x.setModified(false);
			}
		}

		for (Folie x : folieMem.values()) {
			if (x.isToBeDeleted()) {
				x.sqlDelete();
				folieMem.remove(x.getID());
			} else if (x.isModified()) {
				x.sqlSave();
				x.setModified(false);
			}
		}

		for (Foliensatz x : folienSatzMem.values()) {
			if (x.isToBeDeleted()) {
				x.sqlDelete();
				folienSatzMem.remove(x.getID());
			} else if (x.isModified()) {
				x.sqlSave();
				x.setModified(false);
			}
		}

		for (Kurs x : kursMem.values()) {
			if (x.isToBeDeleted()) {
				x.sqlDelete();
				kursMem.remove(x.getID());
			} else if (x.isModified()) {
				x.sqlSave();
				x.setModified(false);
			}
		}

		for (Lehrer x : lehrerMem.values()) {
			if (x.isToBeDeleted()) {
				x.sqlDelete();
				lehrerMem.remove(x.getID());
			} else if (x.isModified()) {
				x.sqlSave();
				x.setModified(false);
			}
		}

		for (Student x : studentMem.values()) {
			if (x.isToBeDeleted()) {
				x.sqlDelete();
				studentMem.remove(x.getID());
			} else if (x.isModified()) {
				x.sqlSave();
				x.setModified(false);
			}
		}

		for (Uservoting x : userVotMem.values()) {
			if (x.isToBeDeleted()) {
				x.sqlDelete();
				userVotMem.remove(x.getID());
			} else if (x.isModified()) {
				x.sqlSave();
				x.setModified(false);
			}
		}

		for (Berechtigung x : rechteMem.values()) {
			if (x.isToBeDeleted()) {
				x.sqlDelete();
				rechteMem.remove(x.getID());
			} else if (x.isModified()) {
				x.sqlSave();
				x.setModified(false);
			}
		}

		for (Kursteilnahme x : kursTeilnahmeMem.values()) {
			if (x.isToBeDeleted()) {
				x.sqlDelete();
				kursTeilnahmeMem.remove(x.getID());
			} else if (x.isModified()) {
				x.sqlSave();
				x.setModified(false);
			}
		}
		
	}
	*/
}
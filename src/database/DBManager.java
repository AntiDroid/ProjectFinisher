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

		if (a.getID() < 0) {

			String sql = "INSERT INTO Auswahlbereich VALUES (?, ?, ?, ?, ?, ?)";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setInt(2, a.getFolienID());
				stat.setInt(3, a.getObenLinksX());
				stat.setInt(4, a.getObenLinksY());
				stat.setInt(5, a.getUntenRechtsX());
				stat.setInt(6, a.getUntenRechtsY());
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if (rs.next()) {
					a.setID(rs.getInt(1));
				}

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Insertproblem - Auswahlbereich");
			}
		} else {

			String sql = "UPDATE Auswahlbereich SET FolienID = ?, EckeOLX = ?, EckeOLY = ?, EckeURX = ?, EckeURY = ? WHERE BereichID = ?";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
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
			}
		}
	}

	public void delete(Auswahlbereich a) {

		String sql = "DELETE FROM Auswahlbereich WHERE BereichID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, a.getID());
			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Deleteproblem - Auswahlbereich");
		}
	}

	public void save(Folie f) {

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
				e.printStackTrace();
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
				e.printStackTrace();
				System.out.println("Updateproblem - Folie");
			}
		}
	}

	public void delete(Folie f) {

		String sql = "DELETE FROM Folie WHERE FolienID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, f.getID());
			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Deleteproblem - Folie");
		}
	}

	public void save(Foliensatz f) {

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
				e.printStackTrace();
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
				e.printStackTrace();
				System.out.println("Updateproblem - Foliensatz");
			}
		}
	}

	public void delete(Foliensatz f) {

		String sql = "DELETE FROM Foliensatz WHERE FoliensatzID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, f.getID());
			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Deleteproblem - Foliensatz");
		}
	}

	public void save(Kurs k) {

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
				e.printStackTrace();
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
				e.printStackTrace();
				System.out.println("Updateproblem - Kurs");
			}
		}
	}

	public void delete(Kurs k) {

		String sql = "DELETE FROM Kurs WHERE KursID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, k.getID());
			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Deleteproblem - Kurs");
		}
	}

	public void save(Lehrer p) {

		if (p.getID() < 0) {

			String sql = "INSERT INTO Lehrer VALUES (?, ?, ?, ?, ?)";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setString(2, p.getBenutzername());
				stat.setString(3, p.getVorname());
				stat.setString(4, p.getNachname());
				stat.setString(5, p.getPasswort());
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if (rs.next()) {
					p.setID(rs.getInt(1));
				}

			} catch (SQLException e) {
				e.printStackTrace();
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
				e.printStackTrace();
				System.out.println("Updateproblem - Lehrer");
			}
		}

	}

	public void delete(Lehrer l) {

		String sql = "DELETE FROM Lehrer WHERE LehrerID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, l.getID());
			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Deleteproblem - Lehrer");
		}
	}

	public void save(Student st) {

		if (st.getID() < 0) {

			String sql = "INSERT INTO Student VALUES (?, ?, ?, ?, ?)";

			try {

				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, null);
				stat.setString(2, st.getBenutzername());
				stat.setString(3, st.getVorname());
				stat.setString(4, st.getNachname());
				stat.setString(5, st.getPasswort());
				stat.execute();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if (rs.next()) {
					st.setID(rs.getInt(1));
				}

			} catch (SQLException e) {
				e.printStackTrace();
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
				e.printStackTrace();
				System.out.println("Updateproblem - Student");
			}
		}
	}

	public void delete(Student s) {

		String sql = "DELETE FROM Student WHERE StudentenID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, s.getID());
			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Deleteproblem - Student");
		}
	}

	public void save(Uservoting u) {

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
				e.printStackTrace();
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
				e.printStackTrace();
				System.out.println("Updateproblem - Uservoting");
			}
		}
	}

	public void delete(Uservoting u) {

		String sql = "DELETE FROM Uservoting WHERE VotingID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, u.getID());
			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Deleteproblem - Uservoting");
		}
	}

	public ArrayList<Auswahlbereich> getAuswahlbereiche(Folie folie) {

		ArrayList<Auswahlbereich> list = new ArrayList<Auswahlbereich>();

		String sql = "SELECT * FROM Auswahlbereich WHERE FolienID = ?";

		try {

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, folie.getID());

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Auswahlbereich obj = new Auswahlbereich();

				obj.setID(rs.getInt("BereichID"));
				obj.setFolie(folie);
				obj.setFolienID(folie.getID());
				obj.setObenLinksX(rs.getInt("EckeOLX"));
				obj.setObenLinksY(rs.getInt("EckeOLY"));
				obj.setUntenRechtsX(rs.getInt("EckeURX"));
				obj.setUntenRechtsY(rs.getInt("EckeURY"));

				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Auswahlbereiche");
		}

		return list;
	}

	public char getBerechtigung(Lehrer lehrer, Kurs kurs) {

		char res = 'X';

		String sql = "SELECT Berechtigungstyp FROM Berechtigung WHERE LehrerID = ? AND KursID = ?";
		try {

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, lehrer.getID());
			stmt.setInt(2, kurs.getID());

			ResultSet rs = stmt.executeQuery();

			if (rs.next())
				res = rs.getString("Berechtigungstyp").charAt(0);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Berechtigung");
		}

		return res;
	}

	public ArrayList<Folie> getFolien(Foliensatz fSatz) {

		ArrayList<Folie> list = new ArrayList<Folie>();

		String sql = "SELECT * FROM Folie WHERE FoliensatzID = ?";

		try {

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, fSatz.getID());

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Folie obj = new Folie();

				obj.setID(rs.getInt("FolienID"));
				obj.setFSatz(fSatz);
				obj.setFoliensatzID(fSatz.getID());
				obj.setfPath(rs.getString("fPath"));
				obj.setFolienTyp(rs.getString("FolienTyp").charAt(0));

				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Folien");
		}

		return list;
	}

	public ArrayList<Foliensatz> getFoliensätze(Kurs kurs) {

		ArrayList<Foliensatz> list = new ArrayList<Foliensatz>();

		String sql = "SELECT * FROM Foliensatz WHERE KursID = ?";

		try {

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, kurs.getID());

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Foliensatz obj = new Foliensatz();

				obj.setID(rs.getInt("FoliensatzID"));
				obj.setKursID(kurs.getID());
				obj.setKurs(kurs);
				obj.setName(rs.getString("Name"));

				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Foliensätze");
		}

		return list;
	}

	public ArrayList<Kurs> getKurse() {

		ArrayList<Kurs> list = new ArrayList<Kurs>();

		String sql = "SELECT * FROM Kurs";

		try {

			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Kurs obj = new Kurs();

				obj.setID(rs.getInt("KursID"));
				obj.setName(rs.getString("Name"));

				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Kurse");
		}

		return list;
	}

	public boolean isKursBeteiligt(Kurs kurs, Student student) {

		String sql = "SELECT * FROM Kursteilnahme WHERE KursID = ? AND StudentenID = ?";

		try {

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(kurs.getID(), student.getID());

			ResultSet rs = stmt.executeQuery();

			if (rs.next())
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - isKursBeteiligt");
		}

		return false;
	}

	public Lehrer getLehrer(String bn) {

		String sql = "SELECT * FROM Lehrer WHERE Benutzername = ?";
		Lehrer obj = new Lehrer();

		try {

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bn);
			ResultSet rs = stmt.executeQuery();

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
		}

		return obj;
	}

	public Student getStudent(String bn) {

		String sql = "SELECT * FROM Student WHERE Benutzername = ?";
		Student obj = new Student();

		try {

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bn);
			ResultSet rs = stmt.executeQuery();

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
		}

		return obj;
	}

	// # was wenn alle Uservotings einer folie und session gebraucht werden?
	public ArrayList<Uservoting> getUservotings(Student student, Folie folie) {

		ArrayList<Uservoting> list = new ArrayList<Uservoting>();

		String sql = "SELECT * FROM Uservoting WHERE StudentenID = ? AND FolienID = ?";

		try {

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, student.getID());
			stmt.setInt(2, folie.getID());

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Uservoting obj = new Uservoting();

				obj.setID(rs.getInt("VotingID"));
				obj.setSessionID(rs.getString("SessionID"));
				obj.setStudentenID(student.getID());
				obj.setStudent(student);
				obj.setFolienID(folie.getID());
				obj.setFolie(folie);
				obj.setKoordX(rs.getInt("KoordX"));
				obj.setKoordY(rs.getInt("KoordY"));
				obj.setAuswahloption(rs.getString("Auswahloption"));

				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - Uservotings");
		}

		return list;
	}

	public int getLetzteAktiveFolienID(int lID, int fSatzID, int fID) {

		String sql = "SELECT LetzteFolieID FROM LetzteAktiveFolie WHERE LehrerID = ?"
				+ " AND FoliensatzID = ? AND LetzteFolieID = ?";

		try {

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, lID);
			stmt.setInt(2, fSatzID);
			stmt.setInt(3, fID);

			ResultSet rs = stmt.executeQuery();

			if (rs.next())
				return rs.getInt("LetzteFolieID");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Selectproblem - letzte aktive Folie");
		}

		return -1;
	}
}
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

	Connection conn;

	private DBManager() {

		try {

			conn = ConnectionPool.getInstance().getConnection();

		} catch (Exception e) {
			System.out.println("Datenbankverbindung fehlgeschlagen!");
			e.printStackTrace();
		}
	}

	public void dispose() throws SQLException {
		conn.close();
	}

	public void sqlSave(Auswahlbereich a) {

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

	public void sqlDelete(Auswahlbereich a) {

		String sql = "DELETE FROM Auswahlbereich WHERE BereichID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, a.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Auswahlbereich");
		}
	}

	public void sqlSave(Berechtigung b) {

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

	public void sqlDelete(Berechtigung b) {

		String sql = "DELETE FROM Berechtigung WHERE BerechtigungsID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, b.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Berechtigung");
		}
	}

	public void sqlSave(Folie f) {

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

	public void sqlDelete(Folie f) {

		String sql = "DELETE FROM Folie WHERE FolienID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, f.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Folie");
		}
	}

	public void sqlSave(Foliensatz f) {

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

	public void sqlDelete(Foliensatz f) {

		String sql = "DELETE FROM Foliensatz WHERE FoliensatzID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, f.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Foliensatz");
		}
	}

	public void sqlSave(Kurs k) {

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

	public void sqlDelete(Kurs k) {

		String sql = "DELETE FROM Kurs WHERE KursID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, k.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Kurs");
		}
	}

	public void sqlSave(Kursteilnahme k) {

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

	public void sqlDelete(Kursteilnahme k) {

		String sql = "DELETE FROM Kursteilnahme WHERE KursteilnahmeID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, k.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Kursteilnahme");
		}
	}

	public void sqlSave(Lehrer p) {

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

	public void sqlDelete(Lehrer l) {

		String sql = "DELETE FROM Lehrer WHERE LehrerID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, l.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Lehrer");
		}
	}

	public void sqlSave(Student st) {

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

	public void sqlDelete(Student s) {

		String sql = "DELETE FROM Student WHERE StudentenID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, s.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Student");
		}
	}

	public void sqlSave(Uservoting u) {

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

	public void sqlDelete(Uservoting u) {

		String sql = "DELETE FROM Uservoting WHERE VotingID = ?";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, u.getID());
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Deleteproblem - Uservoting");
		}
	}
}
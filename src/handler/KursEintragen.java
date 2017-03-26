package handler;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Kurs;
import models.Lehrer;
import models.Student;
import database.DBManager;

@WebServlet("/KursEintragenServlet")
public class KursEintragen extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DBManager dbm = new DBManager();

		String kursName = request.getParameter("kursname");
		String kurspw = request.getParameter("kurspw");
		ArrayList<Kurs> kursListe = dbm.getKurse();
		Kurs addKurs = null;
		String redirectTo = "";
		
		for (Kurs k : kursListe) {
			if (k.getName().equals(kursName) && k.getPasswort().equals(kurspw)) {
				addKurs = k;
				break;
			}
		}

		// Nur nehmen, wenn existiert
		HttpSession s = request.getSession(false);
		
		if (s == null)
			redirectTo = "login";
		else if(s.getAttribute("benutzer") instanceof Lehrer)
			redirectTo = "lehrer_kurse";
		else if (!dbm.isKursVorhanden(kursName))		
			redirectTo = "studenten_kurse";
		else if (!dbm.isKursBeteiligt(kursName, ((Student) s.getAttribute("benutzer")).getBenutzername())) {
			
			dbm.addKursteilnahme(addKurs.getID(), ((Student) s.getAttribute("benutzer")).getID());

			@SuppressWarnings("unchecked")
			ArrayList<Kurs> kurse = (ArrayList<Kurs>) s.getAttribute("kursListe");
			kurse.add(addKurs);
			s.setAttribute("kursListe", kurse);
			redirectTo = "studenten_kurse";
		}

		dbm.dispose();
		response.sendRedirect(redirectTo+".jsp");
	}

}

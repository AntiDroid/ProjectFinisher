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
		
		Kurs newKurs = dbm.getKurs(kursName);
		String redirectTo = "";

		// Nur nehmen, wenn existiert
		HttpSession session = request.getSession(false);
		
		if (session == null)
			redirectTo = "login";
		else if(session.getAttribute("benutzer") instanceof Lehrer)
			redirectTo = "lehrer_kurse";
		else if (!(dbm.isKursVorhanden(kursName) && newKurs.getPasswort().equals(kurspw)))		
			redirectTo = "studenten_kurse";
		else if (!dbm.isKursBeteiligt(kursName, ((Student) session.getAttribute("benutzer")).getBenutzername())) {
			
			dbm.addKursteilnahme(newKurs.getID(), ((Student) session.getAttribute("benutzer")).getID());

			@SuppressWarnings("unchecked")
			ArrayList<Kurs> kurse = (ArrayList<Kurs>) session.getAttribute("kursListe");
			kurse.add(newKurs);
			session.setAttribute("kursListe", kurse);
			redirectTo = "studenten_kurse";
		}
		
		dbm.dispose();
		response.sendRedirect(redirectTo+".jsp");
	}

}

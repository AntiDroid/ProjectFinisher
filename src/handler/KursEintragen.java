package handler;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Client;
import models.Kurs;
import models.Lehrer;
import models.Student;
import database.DBManager;

@WebServlet("/KursEintragenServlet")
public class KursEintragen extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		DBManager dbm = new DBManager();

		String kursName = request.getParameter("kursname");
		String kurspw = request.getParameter("kurspw");

		HttpSession session = request.getSession();
		Client benutzer = (Client) session.getAttribute("benutzer");
		String redirectTo = "";
		
		Kurs newKurs = dbm.getKurs(kursName);
		
		if (benutzer == null)
			redirectTo = "login";
		else if(benutzer instanceof Lehrer)
			redirectTo = "lehrer_kurse";
		else if (!(dbm.isKursVorhanden(kursName) && newKurs.getPasswort().equals(kurspw)))		
			redirectTo = "studenten_kurse";
		else if (!dbm.isKursBeteiligt(kursName, benutzer.getBenutzername())) {
			
			dbm.addKursteilnahme(newKurs.getID(), benutzer.getID());

			@SuppressWarnings("unchecked")
			ArrayList<Kurs> kurse = (ArrayList<Kurs>) session.getAttribute("kursListe");
			kurse.add(newKurs);
			session.setAttribute("kursListe", kurse);
			redirectTo = "studenten_kurse";
		}
		
		dbm.dispose();
		try {
			response.sendRedirect(redirectTo+".jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

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
import database.DBManager;

@WebServlet("/KursEintragenServlet")
public class KursEintragenAlt extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DBManager dbm = new DBManager();

		String kursName = request.getParameter("kursname");
		ArrayList<Kurs> kursListe = dbm.getKurse();
		Kurs addKurs = null;

		for (Kurs k : kursListe) {
			if (k.getName().equals(kursName)) {
				addKurs = k;
				break;
			}
		}

		// Nur nehmen, wenn existiert
		HttpSession s = request.getSession(false);

		String benutzer = request.getParameter("user");
		String pw = request.getParameter("pw");
		
		if(dbm.isStudent(benutzer, pw)){
			dbm.dispose();
			response.sendRedirect("login.html");
			return;
		}
		// Wenn es bereits ausgetimet ist oder ein solcher Kurs nicht existiert
		else if (addKurs == null || s == null) {			
			dbm.dispose();
			response.sendRedirect("studenten_kurse.jsp");
			return;
		}
		else if (addKurs != null && !dbm.isKursBeteiligt(kursName, (String) s.getAttribute("benutzer"))) {
			
			dbm.addKursteilnahme(addKurs, dbm.getStudent((String) s.getAttribute("benutzer")));

			//anpassen der Kursliste
			//Temporärlösung
			@SuppressWarnings("unchecked")
			ArrayList<String> kurse = (ArrayList<String>) s.getAttribute("kursListe");
			kurse.add(addKurs.getName());
			s.setAttribute("kursListe", kurse);

			dbm.dispose();
			response.sendRedirect("studenten_kurse.jsp");
			return;
		}

		dbm.dispose();
		response.sendRedirect("studenten_kurse.jsp");
	}

}

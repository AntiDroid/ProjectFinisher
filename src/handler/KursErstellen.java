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
import database.DBManager;

@WebServlet("/KursErstellenServlet")
public class KursErstellen extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		DBManager dbm = new DBManager();

		String kursName = request.getParameter("newKursName");
		// String kursPass = request.getParameter("newKursPass");
		ArrayList<Kurs> kursListe = dbm.getKurse();

		boolean kursExists = false;

		for (Kurs k : kursListe) {
			if (k.getName().equals(kursName)) {
				kursExists = true;
				break;
			}
		}

		// Nur nehmen, wenn existiert
		HttpSession s = request.getSession(false);

		// Wenn es bereits ausgetimet ist oder ein solcher Kurs bereits existiert
		if (s == null || kursExists) {
			response.sendRedirect("lehrer_kurse.jsp");
			return;
		}

		String userName = (String) s.getAttribute("benutzer");

		if (!kursExists) {

			Kurs k = new Kurs(kursName);
			dbm.save(k);

			Lehrer l = dbm.getLehrer(userName);
			dbm.addBerechtigung(k, l, "V");

			//anpassen der Kursliste
			//Temporärlösung
			@SuppressWarnings("unchecked")
			ArrayList<String> kurse = (ArrayList<String>) s.getAttribute("kursListe");
			kurse.add(k.getName());
			s.setAttribute("kursListe", kurse);

			response.sendRedirect("lehrer_kurse.jsp");
		}

		dbm.dispose();
	}

}

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
		String kursPW = request.getParameter("newKursPass");
		
		// String kursPass = request.getParameter("newKursPass");
		ArrayList<Kurs> kursListe = dbm.getKurse();
		boolean kursExists = false;

		for (Kurs k : kursListe) {
			if (k.getName().equals(kursName)) {
				kursExists = true;
				break;
			}
		}

		String benutzer = request.getParameter("user");
		String pw = request.getParameter("pw");
		
		// Nur nehmen, wenn existiert
		HttpSession s = request.getSession(false);
		
		if(!s.getAttribute("type").equals("L")){
			dbm.dispose();
			response.sendRedirect("login.jsp");
			return;
		}
		// Wenn es bereits ausgetimet ist oder ein solcher Kurs bereits existiert
		else if (s == null || kursExists){
			dbm.dispose();
			response.sendRedirect("lehrer_kurse.jsp");
			return;
		}
		else {
			String userName = (String) s.getAttribute("benutzer");
			
			Kurs k = new Kurs(kursName, kursPW);
			dbm.save(k);

			Lehrer l = dbm.getLehrer(userName);
			dbm.addBerechtigung(k, l, "V");

			//anpassen der Kursliste
			//Temporärlösung
			@SuppressWarnings("unchecked")
			ArrayList<String> kurse = (ArrayList<String>) s.getAttribute("kursListe");
			kurse.add(k.getName());
			s.setAttribute("kursListe", kurse);

			dbm.dispose();
			response.sendRedirect("lehrer_kurse.jsp");
			return;
		}
	}

}

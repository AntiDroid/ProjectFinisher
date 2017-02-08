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
		
		// Nur nehmen, wenn existiert
		HttpSession s = request.getSession(false);
		
		if(!(s.getAttribute("benutzer") instanceof Lehrer)){
			dbm.dispose();
			response.sendRedirect("login.jsp");
			return;
		}
		// Wenn ein solcher Kurs bereits existiert
		else if (s == null || kursExists){
			dbm.dispose();
			response.sendRedirect("lehrer_kurse.jsp");
			return;
		}
		else {
			
			Kurs k = new Kurs(kursName, kursPW);
			dbm.save(k);

			dbm.addBerechtigung(k, (Lehrer)s.getAttribute("benutzer"), "V");

			//anpassen der Kursliste
			//Temporärlösung
			@SuppressWarnings("unchecked")
			ArrayList<Kurs> kurse = (ArrayList<Kurs>) s.getAttribute("kursListe");
			kurse.add(k);
			s.setAttribute("kursListe", kurse);

			dbm.dispose();
			response.sendRedirect("lehrer_kurse.jsp");
			return;
		}
	}

}

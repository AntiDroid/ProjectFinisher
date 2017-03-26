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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DBManager dbm = new DBManager();

		String kursName = request.getParameter("newKursName");
		String kursPW = request.getParameter("newKursPass");

		boolean kursExists = dbm.isKursVorhanden(kursName);
		
		// Die Session nur nehmen, wenn sie bereits existiert
		HttpSession s = request.getSession(false);
		
		if (s == null || kursExists){
			dbm.dispose();
			response.sendRedirect("lehrer_kurse.jsp");
		}
		else if(!(s.getAttribute("benutzer") instanceof Lehrer)){
			dbm.dispose();
			response.sendRedirect("login.jsp");
		}
		else {
			Lehrer l = ((Lehrer)s.getAttribute("benutzer"));
			Kurs k = new Kurs(kursName, kursPW, l, l.getID());
			dbm.save(k);

			//Aktualisieren der anzuzeigenden Kursliste
			@SuppressWarnings("unchecked")
			ArrayList<Kurs> kurse = (ArrayList<Kurs>) s.getAttribute("kursListe");
			kurse.add(k);
			s.setAttribute("kursListe", kurse);

			dbm.dispose();
			response.sendRedirect("lehrer_kurse.jsp");
		}
	}

}

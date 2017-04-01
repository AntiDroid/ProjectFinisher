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
		
		HttpSession session = request.getSession();
		Client benutzer = (Client) session.getAttribute("benutzer");
		String redirectTo = "";
		
		if (benutzer == null)
			redirectTo = "login";
		else if(benutzer instanceof Student)
			redirectTo = "studenten_kurse";
		else if(kursName == null || kursPW == null)
			redirectTo = "lehrer_kurse";
		else if(dbm.isKursVorhanden(kursName))
			redirectTo = "lehrer_kurse";
		else {
			Lehrer l = (Lehrer) benutzer;
			Kurs k = new Kurs(kursName, kursPW, l, l.getID());
			dbm.save(k);

			//Aktualisieren der anzuzeigenden Kursliste
			@SuppressWarnings("unchecked")
			ArrayList<Kurs> kurse = (ArrayList<Kurs>) session.getAttribute("kursListe");
			kurse.add(k);
			session.setAttribute("kursListe", kurse);

			redirectTo = "lehrer_kurse";
		}
		
		dbm.dispose();
		response.sendRedirect(redirectTo+".jsp");
	}

}

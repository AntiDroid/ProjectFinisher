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

@WebServlet("/LoginServlet")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		DBManager dbm = new DBManager();

		String benutzer = request.getParameter("user");
		String pw = request.getParameter("pw");

		// Direkter Aufruf von LoginServlet
		// --Implementierung zu Testzwecken--
		if (benutzer == null) {
			System.out.println("So nicht!");
			response.sendRedirect("login.html");
		} else if (dbm.isStudent(benutzer, pw)) {

			Student s = dbm.getStudent(benutzer);
			ArrayList<String> kursListe = getKursnamen(dbm.getKurseStudent(s.getID()));

			// Create a session object if it is already not created.
			HttpSession session = request.getSession(true);
			doStuff(benutzer, s, kursListe, session);

			response.sendRedirect("studenten_kurse.jsp");
		} else if (dbm.isLehrer(benutzer, pw)) {

			Lehrer l = dbm.getLehrer(benutzer);
			ArrayList<String> kursListe = getKursnamen(dbm.getKurseLehrer(l.getID()));

			// Create a session object if it is already not created.
			HttpSession session = request.getSession(true);
			doStuff(benutzer, l, kursListe, session);

			response.sendRedirect("lehrer_kurse.jsp");
		} else
			response.sendRedirect("login.html");

		dbm.dispose();
	}

	public ArrayList<String> getKursnamen(ArrayList<Kurs> kurse) {

		ArrayList<String> namen = new ArrayList<String>();

		for (Kurs k : kurse)
			namen.add(k.getName());

		return namen;
	}
	
	public void doStuff(String benutzer, Client c, ArrayList<String> kursListe, HttpSession session){
		
		session.setAttribute("benutzer", benutzer);
		// Inaktivität in Serverinteraktion bis Session erlischt (Sekunden)
		session.setMaxInactiveInterval(15);
		session.setAttribute("user", c.getVorname() + " " + c.getNachname());
		session.setAttribute("kursListe", kursListe);
		
	}

}

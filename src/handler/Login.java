package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Client;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DBManager dbm = new DBManager();

		String benutzer = request.getParameter("user");
		String pw = request.getParameter("pw");

		// Direkter Aufruf von LoginServlet
		// --Implementierung zu Testzwecken--
		if (benutzer == null) {
			System.out.println("So nicht!");
			response.sendRedirect("login.jsp");
		} else if (dbm.isStudent(benutzer, pw)) {

			Student s = dbm.getStudent(benutzer);

			// Create a session object if it is already not created.
			HttpSession session = request.getSession(true);
			doStuff(s, dbm.getKurseStudent(s.getID()), session);
			response.sendRedirect("studenten_kurse.jsp");
		} else if (dbm.isLehrer(benutzer, pw)) {

			Lehrer l = dbm.getLehrer(benutzer);

			// Create a session object if it is already not created.
			HttpSession session = request.getSession(true);
			doStuff(l, dbm.getKurseLehrer(l.getID()), session);
			response.sendRedirect("lehrer_kurse.jsp");
		} 
		else{
			response.sendRedirect("login.jsp");
		}

		dbm.dispose();
	}
	
	public void doStuff(Client c, ArrayList<?> kursListe, HttpSession session){
		
		HttpSession old = Client.actLogin.put(c.getBenutzername(), session);
	
		if(old != null){
			try{
				old.invalidate();
			}catch(IllegalStateException e){}
		}
		
		session.setAttribute("benutzer", c);
		// Inaktivität in Serverinteraktion bis Session erlischt (Sekunden)
		// Später in web.xml auslagern
		session.setMaxInactiveInterval(15*60);
		session.setAttribute("kursListe", kursListe);
	}

}

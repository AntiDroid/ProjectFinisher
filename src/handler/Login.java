package handler;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Client;
import models.Kurs;
import models.Lehrer;
import models.Student;
import database_logging.DBManager;
import database_logging.MyLogger;

@WebServlet("/LoginServlet")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		DBManager dbm = new DBManager();

		String benutzer = request.getParameter("user");
		String pw = request.getParameter("pw");
		
		String redirectTo = "";

		if (benutzer == null || pw == null) {
			redirectTo = "login";
		} else if (dbm.isStudent(benutzer, pw)) {

			Student s = dbm.getStudent(benutzer);
			ArrayList<Kurs> kurse = dbm.getKurseStudent(s.getID());
			
			processSession(s, kurse, request.getSession());
			redirectTo = "studenten_kurse";
		} else if (dbm.isLehrer(benutzer, pw)) {

			Lehrer l = dbm.getLehrer(benutzer);
			ArrayList<Kurs> kurse = dbm.getKurseLehrer(l.getID());
			
			processSession(l, kurse, request.getSession());
			redirectTo = "lehrer_kurse";
		} 
		else{
			redirectTo = "login";
		}

		dbm.dispose();
		try {
			response.sendRedirect(redirectTo+".jsp");
		} catch (IOException e) {
			MyLogger.getLogger().severe(e.getMessage());
		}
	}
	
	public void processSession(Client c, ArrayList<Kurs> kursListe, HttpSession session){
		
		HttpSession oldSession = Client.actLogin.put(c.getBenutzername(), session);
	
		if(oldSession != null){
			try{
				oldSession.invalidate();
			}catch(IllegalStateException e){}
		}
		
		session.setAttribute("benutzer", c);
		session.setMaxInactiveInterval(15*60);
		session.setAttribute("kursListe", kursListe);
	}

}

package handler;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Kurs;
import models.Lehrer;
import models.Student;
import database.DBManager;

@WebServlet("/LoginServlet")
public class Login extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DBManager dbm = new DBManager();
		
		String benutzer = request.getParameter("user");
		String pw = request.getParameter("pw");
		
		if(dbm.isStudent(benutzer, pw)){
			
			HttpSession session = request.getSession();
			
			Session.sessions.put(session.getId(), new Session(session, benutzer));
			
			//Session-Dauer bis ungültig (Sekunden)
			session.setMaxInactiveInterval(15);
			Cookie userName = new Cookie("user", benutzer);
			
			//Cookie-Lebensdauer
			userName.setMaxAge(15);
			response.addCookie(userName);
			
			Student s = dbm.getStudent(benutzer);
			session.setAttribute("user", s.getVorname()+" "+s.getNachname());				
			session.setAttribute("kursListe", getKursNamenListe( dbm.getKurseStudent(s.getID()) ) );
			
			response.sendRedirect("studenten_kurse.jsp");
		}
		else if(dbm.isLehrer(benutzer, pw)){
			
			HttpSession session = request.getSession();
			
			Session.sessions.put(session.getId(), new Session(session, benutzer));
			
			//Session-Dauer bis ungültig (Sekunden)
			session.setMaxInactiveInterval(15);
			Cookie userName = new Cookie("user", benutzer);
			//Sekunden
			userName.setMaxAge(15);
			response.addCookie(userName);
			
			Lehrer l = dbm.getLehrer(benutzer);
			session.setAttribute("user", l.getVorname()+" "+l.getNachname());				
			session.setAttribute("kursListe", getKursNamenListe( dbm.getKurseLehrer(l.getID()) ) );
			
			response.sendRedirect("lehrer_kurse.jsp");
		}
		else
			response.sendRedirect("login.html");
		
		dbm.dispose();
	}
	
	public ArrayList<String> getKursNamenListe(ArrayList<Kurs> kurse){
		
		ArrayList<String> namen = new ArrayList<String>();
		
		for(Kurs k: kurse)
			namen.add(k.getName());
		
		return namen;
	}

}

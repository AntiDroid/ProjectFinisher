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
import models.Student;
import database.DBManager;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DBManager dbm = new DBManager();
		
		HttpSession session = request.getSession();
		
		String benutzer = request.getParameter("user");
		String pw = request.getParameter("pw");
		
		if(dbm.isStudent(benutzer, pw)){
			
			Student s = dbm.getStudent(benutzer);
			session.setAttribute("studentenName", s.getVorname()+" "+s.getNachname());
			
			ArrayList<Kurs> liste = dbm.getKurseStudent(s.getID());
			ArrayList<String> listeNeu = new ArrayList<String>();
			for(Kurs k: liste)
				listeNeu.add(k.getName());
					
			session.setAttribute("kursListe", listeNeu);
			
			response.sendRedirect("schueler_kurse.jsp");
		}
		else if(dbm.isLehrer(benutzer, pw)){
			
			Lehrer l = dbm.getLehrer(benutzer);
			session.setAttribute("lehrerName", l.getVorname()+" "+l.getNachname());
			
			ArrayList<Kurs> liste = dbm.getKurseLehrer(l.getID());
			
			System.out.println(liste.size());
			
			ArrayList<String> listeNeu = new ArrayList<String>();
			for(Kurs k: liste)
				listeNeu.add(k.getName());
					
			session.setAttribute("kursListe", listeNeu);
			
			response.sendRedirect("lehrer_kurse.jsp");
		}
		else
			response.sendRedirect("login.html");
		
		dbm.dispose();
	}

}

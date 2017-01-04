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

@WebServlet("/KursEintragenServlet")
public class KursEintragen extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DBManager dbm = new DBManager();
		
		String kursName = request.getParameter("kursname");
		
		ArrayList<Kurs> kursListe = dbm.getKurse();
		
		Kurs addKurs = null;
		
		for(Kurs k: kursListe)
			if(k.getName().equals(kursName))
				addKurs = k;
		
		Session s = Session.sessions.get(request.getSession().getId());
		String userName = s.getUser();
		
		if(addKurs != null && !dbm.isKursBeteiligt(kursName, userName)){
			dbm.addKursteilnahme(addKurs, dbm.getStudent(userName));
			
			ArrayList<String> kurse = (ArrayList<String>)s.getSession().getAttribute("kursListe");
			kurse.add(addKurs.getName());
			s.getSession().setAttribute("kursListe", kurse);
			
			response.sendRedirect("studenten_kurse.jsp");
		}
		
		dbm.dispose();
	}

}

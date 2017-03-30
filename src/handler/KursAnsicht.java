package handler;

import java.io.IOException;

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

@WebServlet("/KursServlet")
public class KursAnsicht extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DBManager dbm = new DBManager();
		
		int kursId = Integer.parseInt(request.getParameter("kursId"));
		HttpSession session = request.getSession(true);
		String redirectTo = "";
		
		if (session == null)
			redirectTo = "login";
		else if(session.getAttribute("benutzer") instanceof Student){
			
			Student s = (Student) session.getAttribute("benutzer");
			
			//Wenn nicht am Kurs beteiligt
			if(!dbm.isKursBeteiligt(dbm.getKurs(kursId).getName(), s.getBenutzername()))
				redirectTo = "studenten_kurse";
			else{
				session.setAttribute("kursId", kursId);
				redirectTo = "studenten_folie";
			}
		}
		else if(session.getAttribute("benutzer") instanceof Lehrer){
			
			Lehrer l = (Lehrer) session.getAttribute("benutzer");
			
			//Wenn nicht am Kurs beteiligt
			if(dbm.getKurs(kursId).getLehrerID() != l.getID())
				redirectTo = "lehrer_kurse";
			else{
				session.setAttribute("kursId", kursId);
				redirectTo = "lehrer_folien";
			}
		}
		
		dbm.dispose();
		response.sendRedirect(redirectTo+".jsp");
	}

}

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
		
		HttpSession session = request.getSession();
		Client benutzer = (Client) session.getAttribute("benutzer");
		String redirectTo = "";
		
		if (benutzer == null)
			redirectTo = "login";
		else if(benutzer instanceof Student){
			
			//Wenn nicht am Kurs beteiligt
			if(!dbm.isKursBeteiligt(dbm.getKurs(kursId).getName(), benutzer.getBenutzername()))
				redirectTo = "studenten_kurse";
			else{
				session.setAttribute("kursId", kursId);
				redirectTo = "studenten_folie";
			}
		}
		else if(benutzer instanceof Lehrer){
			
			//Wenn nicht am Kurs beteiligt
			if(dbm.getKurs(kursId).getLehrerID() != benutzer.getID())
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

package handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Lehrer;
import models.Student;
import database.DBManager;

@WebServlet("/KursServlet")
public class KursServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DBManager dbm = new DBManager();
		
		int kursId = Integer.parseInt(request.getParameter("kursId"));
		HttpSession session = request.getSession(true);
		
		if(session.getAttribute("benutzer") == null || kursId == 0){
			dbm.dispose();
			response.sendRedirect("login.jsp");
		}
		else if(session.getAttribute("benutzer") instanceof Student){
			session.setAttribute("kursId", kursId);
			dbm.dispose();
			response.sendRedirect("studenten_folie.jsp");
		}
		else if(session.getAttribute("benutzer") instanceof Lehrer){
			session.setAttribute("kursId", kursId);
			dbm.dispose();
			response.sendRedirect("lehrer_folien.jsp");
		}

	}

}

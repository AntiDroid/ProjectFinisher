package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.ServerEndpoint;

import database.DBManager;

@ServerEndpoint("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GO");
		DBManager dbm = new DBManager();
		
		HttpSession session = request.getSession();
		
		String benutzer = request.getParameter("user");
		String pw = request.getParameter("pw");
		
		if(dbm.isStudent(benutzer, pw)){
			response.sendRedirect("schueler_kurse.html");
		} 
		else if(dbm.isLehrer(benutzer, pw)){
			response.sendRedirect("lehrer_kurse.html");
		}
		else{
			response.sendRedirect("login.html");
		}
		
		/*
		if (benutzer.equals("edv") && pw.equals("edv")) {
			session.setAttribute("ben", benutzer);
			response.sendRedirect("home.jsp");
		} 
		else
			response.sendRedirect("login.jsp");
		*/
		
		dbm.dispose();
	}

}

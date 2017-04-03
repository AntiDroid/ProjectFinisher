package handler;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database_logging.MyLogger;
import models.Client;

@WebServlet("/LogoutServlet")
public class Logout extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
    	HttpSession session = request.getSession();
		Client c = (Client) session.getAttribute("benutzer");
    	
    	if(c != null){
    		Client.actLogin.remove(c.getBenutzername());
    		session.invalidate();
    	}
    	
    	try {
			response.sendRedirect("login.jsp");
		} catch (IOException e) {
			MyLogger.getLogger().severe(e.getMessage());
		}
	}
}

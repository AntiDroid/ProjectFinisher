package loginMVC.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String benutzer = request.getParameter("user");
		String pw = request.getParameter("pw");
		
		if (benutzer.equals("edv") && pw.equals("edv")) {
			session.setAttribute("ben", benutzer);
			response.sendRedirect("home.jsp");
		} 
		else
			response.sendRedirect("login.jsp");
		
	}

}

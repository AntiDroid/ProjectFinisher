package handler;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Client;
import models.Folie;
import models.Kurs;
import models.Lehrer;
import database_logging.DBManager;
import database_logging.MyLogger;

@WebServlet("/ImgServlet")
public class GetImg extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		DBManager dbm = new DBManager();
		
		String folienID = request.getParameter("id");
		String isSmall = request.getParameter("thumb");
		
		Folie f = dbm.getFolie(Integer.parseInt(folienID));
		
		if(f.getfSatz() == null){
			dbm.dispose();
			return;
		}
		
		HttpSession session = request.getSession();
		Client benutzer = (Client) session.getAttribute("benutzer");
		
		@SuppressWarnings("unchecked")
		ArrayList<Kurs> kursListe = (ArrayList<Kurs>) session.getAttribute("kursListe");
			
		try{
			if (benutzer == null || kursListe == null)
				response.sendRedirect("login.jsp");
			else if(!kursListe.contains(f.getfSatz().getKurs())){
				if(benutzer instanceof Lehrer)
					response.sendRedirect("lehrer_kurse");
				else
					response.sendRedirect("studenten_kurse");
			}
			else{
				
				response.setContentType("image/png");
				String fPathLocal = System.getProperty("java.io.tmpdir");
				
				BufferedImage bi = null;
				try{
					
					if(isSmall == null)
						bi = ImageIO.read(new File(fPathLocal+f.getfPath()));
					else{				
						Image img = ImageIO.read(new File(fPathLocal+f.getfPath())).getScaledInstance(250, 250, BufferedImage.SCALE_SMOOTH);
						
						bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.SCALE_SMOOTH);
	
					    Graphics2D bGr = bi.createGraphics();
					    bGr.drawImage(img, 0, 0, null);
					    bGr.dispose();
					}
				
				}catch(IOException e){
					bi = ImageIO.read(new File(getServletContext().getRealPath("imgs/na.jpg")));
				}
					
				ImageIO.write(bi, "png", response.getOutputStream());
				response.getOutputStream().close();
			}
		
		}catch(IOException e){
			MyLogger.getLogger().severe(e.getMessage());
		}
		
		dbm.dispose();
	}
}

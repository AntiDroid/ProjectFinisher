package handler;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Folie;
import database.DBManager;

@WebServlet("/ImgServlet")
public class ImgServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		DBManager dbm = new DBManager();
		
		try {
			
			String fPathLocal = System.getProperty("java.io.tmpdir");
			
			String folienID = request.getParameter("id");
			String isSmall = request.getParameter("thumb");
			
			Folie f = dbm.getFolie(Integer.parseInt(folienID));
			
			response.setContentType("image/png");
			
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
			Graphics g = bi.getGraphics();
			g.dispose();
			
			ImageIO.write(bi, "png", response.getOutputStream());
			
			response.getOutputStream().close();
		} catch (Exception e) {	
			e.printStackTrace();
		} finally{
			try {
				response.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			dbm.dispose();
		}
		
	}
}

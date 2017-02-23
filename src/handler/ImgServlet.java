package handler;

import java.awt.Graphics;
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

		try {
			
			DBManager dbm = new DBManager();
			String fPathLocal = System.getProperty("java.io.tmpdir");
			
			String folienID = request.getParameter("id");
			Folie f = dbm.getFolie(Integer.parseInt(folienID));
			
			response.setContentType("image/png");
			
			BufferedImage bi = null;
			try{
				bi = ImageIO.read(new File(fPathLocal+f.getfPath()));
			}catch(IOException e){
				bi = ImageIO.read(new File(getServletContext().getRealPath("/imgs/na.jpg")));
			}
			Graphics g = bi.getGraphics();
			g.dispose();
			
			ImageIO.write(bi, "png", response.getOutputStream());
			
		} catch (NullPointerException e) {
			System.out.println("ImgServlet ohne Parameter aufgerufen!");
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}
}

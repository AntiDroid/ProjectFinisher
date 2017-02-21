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

			String fPathLocal = "C:/Users/ndsts_000/Desktop/";
			
			DBManager dbm = new DBManager();
			
			String folienID = request.getParameter("id");
			
			Folie f = dbm.getFolie(Integer.parseInt(folienID));
			
			response.setContentType("image/jpg");
			BufferedImage bi = ImageIO.read(new File(fPathLocal+f.getfPath()));

			Graphics g = bi.getGraphics();

			g.dispose();
			ImageIO.write(bi, "jpg", response.getOutputStream());

		} catch (NullPointerException e) {
			System.out.println("ImgServlet ohne Parameter aufgerufen!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

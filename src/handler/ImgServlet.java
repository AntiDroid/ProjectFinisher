package handler;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			
			String folienID = request.getParameter("id");
			String fSName = dbm.getFolie(Integer.parseInt(folienID)).getfSatz().getName();
			
			response.setContentType("image/jpg");
			BufferedImage bi = ImageIO.read(new File(getServletContext().getRealPath("locale_database/"+fSName+"/"+folienID+".jpg")));

			Graphics g = bi.getGraphics();

			g.dispose();
			ImageIO.write(bi, "jpg", response.getOutputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

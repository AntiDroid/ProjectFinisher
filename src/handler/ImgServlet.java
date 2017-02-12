package handler;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ImgServlet")
public class ImgServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		try {

			response.setContentType("image/jpg");
			BufferedImage bi = ImageIO.read(new File(getServletContext().getRealPath("imgs/Beispiele/7.png")));

			Graphics g = bi.getGraphics();

			g.dispose();
			ImageIO.write(bi, "jpg", response.getOutputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

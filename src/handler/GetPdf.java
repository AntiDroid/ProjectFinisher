package handler;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.SimpleRenderer;

import java.awt.Image;

@WebServlet("/GetPdfServlet")
@MultipartConfig
public class GetPdf extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		try {
			
			Part filePart = request.getPart("pdfDatei");
			String fileName = Paths.get(filePart.getSubmittedFileName()) .getFileName().toString(); // MSIE fix.
			InputStream fileContent = filePart.getInputStream();
			
			File uploads = new File("C:\\Users\\Talip\\Desktop");
			File file = new File(uploads, fileName);

			System.out.println(fileName+" arrived!");
			
			Files.copy(fileContent, file.toPath());
			
			PDFDocument document = new PDFDocument();
		    document.load(new File("C:\\Users\\Talip\\Desktop\\test.pdf"));
		    
		    SimpleRenderer renderer = new SimpleRenderer();

		    // set resolution (in DPI)
		    renderer.setResolution(300);
		    
		    List<Image> images = renderer.render(document);
		    
		    for (int i = 0; i < images.size(); i++) {
                ImageIO.write((RenderedImage) images.get(i), "png", new File((i + 1) + ".png"));
            }
			
		    System.out.println("Sooo..");
		    
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
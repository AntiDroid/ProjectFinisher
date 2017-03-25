package handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import models.Folie;
import models.Foliensatz;
import database.DBManager;

@WebServlet("/GetPdfServlet")
@MultipartConfig
public class UploadPdf extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		DBManager dbm = new DBManager();
		
		try {
			
			String fPathLocal = System.getProperty("java.io.tmpdir");
			
			String fSName = request.getParameter("name");
			int kursID = Integer.parseInt(request.getParameter("kursId"));
			
	        Foliensatz fs = new Foliensatz(kursID, dbm.getKurs(kursID), fSName);
	        dbm.save(fs);
			
			Part filePart = request.getPart("pdfDatei");
			//String fileName = Paths.get(filePart.getSubmittedFileName()) .getFileName().toString(); // MSIE fix.
			InputStream fileContent = filePart.getInputStream();
			
			File fSFolder = new File(fPathLocal+"/locale_database/"+fs.getID());
			deleteDir(fSFolder);
			fSFolder.mkdirs();

			// Loading an existing PDF document
			PDDocument document = PDDocument.load(fileContent);

			// Instantiating the PDFRenderer class
			PDFRenderer renderer = new PDFRenderer(document);

			// Rendering an image from the PDF document

			for (int i = 0; i < document.getNumberOfPages(); i++) {
				
	        	Folie f = new Folie(fs.getID(), fs, "wird noch eingefuegt", 'A');
	        	dbm.save(f);
	        	f.setfPath("/locale_database/"+fs.getID()+"/"+f.getID()+".png");
	        	dbm.save(f);
				
				BufferedImage image = renderer.renderImage(i, 2.6f);
				//image = renderer.renderImageWithDPI(i, dpi);

				// Writing the image to a file
				ImageIO.write(image, "PNG", new File(fPathLocal+f.getfPath()));
			}
			// Closing the document
			document.close();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			dbm.dispose();
			try {
				response.sendRedirect("lehrer_folien.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static boolean deleteDir(File dir) {
	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }
	    return dir.delete();
	}
}      
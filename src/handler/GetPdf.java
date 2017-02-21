package handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import models.Folie;
import models.Foliensatz;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

import database.DBManager;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

@WebServlet("/GetPdfServlet")
@MultipartConfig
public class GetPdf extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		try {
			
			DBManager dbm = new DBManager();
			String fPathLocal = "C:/Users/ndsts_000/Desktop";
			
			String fSName = request.getParameter("name");
			int kursID = Integer.parseInt(request.getParameter("kursId"));
			
	        Foliensatz fs = new Foliensatz(kursID, dbm.getKurs(kursID), fSName);
	        dbm.save(fs);
			
			Part filePart = request.getPart("pdfDatei");
			//String fileName = Paths.get(filePart.getSubmittedFileName()) .getFileName().toString(); // MSIE fix.
			InputStream fileContent = filePart.getInputStream();
			
			File fSFolder = new File(fPathLocal+"/locale_database/"+fs.getID());
			fSFolder.mkdirs();

			RandomAccessFile raf = InputStreamConverter.toRandomAccessFile(fileContent);
	        FileChannel channel = raf.getChannel();
	        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
	        PDFFile pdf = new PDFFile(buf);
	        
	        for (int i=0; i<pdf.getNumPages(); i++){
	        	
	        	Folie f = new Folie(fs.getID(), fs, "wird noch eingefuegt", 'A');
	        	dbm.save(f);
	        	f.setfPath("/locale_database/"+fs.getID()+"/"+f.getID()+".png");
	        	dbm.save(f);
	   
	            createImage(pdf.getPage(i+1), fPathLocal+f.getfPath());
	        }
	        
	        channel.close();
	        fileContent.close();
	        raf.close();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				response.sendRedirect("lehrer_folien.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void createImage(PDFPage page, String destination) throws IOException{
        Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(),
                (int) page.getBBox().getHeight());
        BufferedImage bufferedImage = new BufferedImage(rect.width, rect.height,
                         BufferedImage.TYPE_INT_RGB);

        Image image = page.getImage(rect.width, rect.height,    // width & height
                   rect,                       // clip rect
                   null,                       // null for the ImageObserver
                   true,                       // fill background with white
                   true                        // block until drawing is done
        );
        Graphics2D bufImageGraphics = bufferedImage.createGraphics();
        bufImageGraphics.drawImage(image, 0, 0, null);
        ImageIO.write(bufferedImage, "png", new File(destination));
    }
	
}

class InputStreamConverter {
     
    public static RandomAccessFile toRandomAccessFile(InputStream is) throws IOException {
    	
        RandomAccessFile raf = new RandomAccessFile(File.createTempFile("isc", "tmp"), "rwd");
  
        byte[] buffer = new byte[2048];
        int    tmp    = 0;
  
        while ((tmp = is.read(buffer)) != -1) 
        {
          raf.write(buffer, 0, tmp);
        }
         
        raf.seek(0);
         
        return raf;
    }
}      
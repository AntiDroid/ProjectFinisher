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

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

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
			
			//String fSName = request.getParameter("name");
			
			Part filePart = request.getPart("pdfDatei");
			String fileName = Paths.get(filePart.getSubmittedFileName()) .getFileName().toString(); // MSIE fix.
			InputStream fileContent = filePart.getInputStream();
			File uploads = new File("C:\\Users\\Talip\\Desktop");
			File file = new File(uploads, fileName);
			Files.deleteIfExists(file.toPath());
			Files.copy(fileContent, file.toPath());

	        RandomAccessFile raf = new RandomAccessFile(file, "r");
	        FileChannel channel = raf.getChannel();
	        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
	        PDFFile pdf = new PDFFile(buf);

	        for (int i=0; i<pdf.getNumPages(); i++){
	            createImage(pdf.getPage(i+1), "C:\\Users\\Talip\\Desktop\\PICTURE_" + (i+1) + ".jpg");
	        }
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
        ImageIO.write(bufferedImage, "JPG", new File( destination ));
    }
	
}
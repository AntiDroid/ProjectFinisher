package handler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

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
			File file = new File(uploads, "Test.pdf");

			Files.copy(fileContent, file.toPath());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
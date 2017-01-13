package handler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class GetPdfServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		PrintWriter pw = null;

		try {
			pw = response.getWriter();

			ByteArrayOutputStream ba = loadPdf("myFile.pdf");

			// Converting byte[] to base64 string
			// NOTE: Always remember to encode your base 64 string in utf8
			// format other wise you may always get problems on browser.

			String pdfBase64String = StringUtils.newStringUtf8(Base64.encodeBase64(ba.toByteArray()));

			// wrting json response to browser

			pw.println("{");
			pw.println("\"successful\": true,");
			pw.println("\"pdf\": \"" + pdfBase64String + "\"");
			pw.println("}");

			return;

		} catch (Exception ex) {

			pw.println("{");
			pw.println("\"successful\": false,");
			pw.println("\"message\": \"" + ex.getMessage() + "\",");
			pw.println("}");

			return;
		}
	}

	private ByteArrayOutputStream loadPdf(String fileName) throws IOException {

		File file = new File(fileName);
		FileInputStream fis;

		fis = new FileInputStream(file);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];

		try {

			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum); // no doubt here is 0
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		fis.close();

		return bos;
	}
}
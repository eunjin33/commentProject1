package co.yedam.email;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MailSendSerlvet")
public class MailSendSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MailSendSerlvet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		System.out.println(title);
		System.out.println(content);
		String[] conAry = content.split("");
		StringBuilder result = new StringBuilder();
		for (String con : conAry) {
			if (con.charAt(0) == (char) 10) {
				result.append("<br>");
				continue;
			}
			result.append(con);
		}

		String contentText = "<div style='border:1px dotted blue; width:250px;'>" + result + "</div>";

		Properties p = System.getProperties();
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.host", "smtp.naver.com");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.port", "587");

		Authenticator auth = new MyAuthentication("dnfldnfl33", "ejqmftidlsl55!");
		Session session = Session.getDefaultInstance(p, auth);
		MimeMessage msg = new MimeMessage(session);

		try {
			msg.setSentDate(new Date());
			InternetAddress from = new InternetAddress();

			from = new InternetAddress("dnfldnfl33@naver.com");
			msg.setFrom(from);

			InternetAddress to = new InternetAddress("eunjin90033@gmail.com");
			msg.setRecipient(Message.RecipientType.TO, to);

			msg.setSubject(title, "UTF-8");
			msg.setText(contentText, "UTF-8");
			msg.setHeader("content-Type", "text/html");

			javax.mail.Transport.send(msg);
			System.out.println("success");

		} catch (AddressException addr_e) {
			addr_e.printStackTrace();
		} catch (MessagingException msg_e) {
			msg_e.printStackTrace();
		}

		response.getWriter().println("<b>OK</b>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

class MyAuthentication extends Authenticator {

	PasswordAuthentication account;

	public MyAuthentication(String i, String p) {
		String id = "id";
		String pw = "pw";
		id = i;
		pw = p;
		account = new PasswordAuthentication(id, pw);
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return account;
	}
}

package jsoft.ads.user;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsoft.ConnectionPool;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class UserResetPass
 */
@WebServlet("/user/reset")
public class UserResetPass extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String generatePassword(int length) {
		SecureRandom random = new SecureRandom();
		StringBuilder password = new StringBuilder();

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(CHARS.length());
			password.append(CHARS.charAt(index));
		}

		return password.toString();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserResetPass() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = (jsoft.library.Utilities.getIntParam(request, "id"));
		// tìm thông tin đăng nhập
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

		if (user != null) {
			UserObject userR = null;

			if (id > 0) {
				// tìm bộ quản lý kết nối
				ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
				// tạo đối tượng thực thi chức năng
				UserControl uc = new UserControl(cp);
				if (cp == null) {
					getServletContext().setAttribute("CPool", cp);
				}
				userR = uc.getUserObject(id);

				// trả về kết nối
				uc.releaseConnection();
			}
//            System.out.println(userR.getUser_name());
			// Sender's email credentials
			final String senderEmail = "sinhkimphan20@gmail.com";
			final String senderPassword = "foctyundbhlygtts"; // Replace with your password
			if (userR.getUser_email() == null) {
				response.sendRedirect("/adv/user/list?err=notok");
			}
			// Recipient's email address
			String recipientEmail = userR.getUser_email();

			// Email properties
			Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");

			// Create a session with the sender's credentials
			Session session = Session.getInstance(properties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(senderEmail, senderPassword);
				}
			});

			try {
				// tìm bộ quản lý kết nối
				ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
				// tạo đối tượng thực thi chức năng
				UserControl uc = new UserControl(cp);
				UserObject eUser = new UserObject();
				eUser.setUser_id(id);
				eUser.setUser_pass(generatePassword(8));
				// thuc hien doi mat khau
				if (cp == null) {
					getServletContext().setAttribute("CPool", cp);
				}
				boolean result = uc.editUser(eUser, USER_EDIT_TYPE.PASS);
//				boolean result =true;
				// trả về kết nối
				uc.releaseConnection();
				// Create a new email message
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(senderEmail));
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
				message.setSubject("Reset password account is " + userR.getUser_name());
				message.setText("This password is "+eUser.getUser_pass());

				// Send the message
				Transport.send(message);
				response.getWriter().write("Email sent successfully.");
				if (result) {
					response.sendRedirect("/adv/user/list");
				} else {
					response.sendRedirect("/adv/user/list?err=changepass");
				}
			} catch (MessagingException e) {
				e.printStackTrace();
				response.getWriter().write("Email sending failed.");
				response.sendRedirect("/adv/user/list?err=failed");
			}

		} else {
			response.sendRedirect("/adv/user/login");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package jsoft.ads.main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class error
 */
@WebServlet("/error")
public class error extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public error() {
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

		PrintWriter out = response.getWriter();

		String err = request.getParameter("err");
		if (err != null) {

			out.append("<div class=\"toast-container position-fixed top-1 end-0 ps-3 pe-5 mb-3\">");
			out.append(
					"<div id=\"liveToast\" class=\"toast\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\">");
			out.append("<div class=\"toast-header\">");
			// out.append("<img src=\"...\" class=\"rounded me-2\" alt=\"...\">");
			out.append("<strong class=\"me-auto text-danger\">Thông báo</strong>");
			out.append("<small>10 giây</small>");
			out.append(
					"<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"toast\" aria-label=\"Close\"></button>");
			out.append("</div>");
			out.append("<div class=\"toast-body\">");
			switch (err) {
			case "add":
				out.append("Lỗi thêm mới!!!");
				break;
			case "notok":
				out.append("Lỗi!!!");
				break;
			case "valueadd":
				out.append("Dữ liệu thêm mới chưa hợp lệ");
				break;
			case "valuepass":
				out.append("Dữ liệu đổi mật khẩu chưa hợp lệ");
				break;
			case "changepass":
				out.append("Lỗi đổi mật khẩu");
				break;
			case "passnotmatch":
				out.append("Mật khẩu hiện tại không khớp với tài khoản");
				break;
			case "edit":
				out.append("Lỗi chỉnh sủa");
				break;
			case "del":
				out.append("lỗi thực hiện khi xóa");
				break;
			case "acclogin":
				out.append("không thể xóa tài khoản đang đang nhập");
				break;
			case "nopermis":
				out.append("không có quyền được xóa");
				break;
			default:
				out.append("Có lỗi,vui lòng kiểm tra lại");
			}
			out.append("</div>");
			out.append("</div>");
			out.append("</div>");

			// script
			out.append("<script language=\"javascript\" >");
			out.append("const viewToast = document.getElementById('liveToast');");
			out.append("const toast = new bootstrap.Toast(viewToast);");
			out.append("toast.show();");
			out.append("</script>");

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

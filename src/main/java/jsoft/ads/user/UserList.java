package jsoft.ads.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class View
 */
@WebServlet("/user/list")
public class UserList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserList() {
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

		// lay page 
		int page = 1;
		if(jsoft.library.Utilities.getIntParam(request, "page")>0) {
			page = jsoft.library.Utilities.getIntParam(request, "page");
		}
		// lay tu khoa tim kiem
			String key = request.getParameter("key");
			String saveKey = (key!=null && !key.equalsIgnoreCase(""))?key.trim():"";
		
		// tìm thông tin đăng nhập
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");
        
		if (user != null) {
			view(request, response, user,page,saveKey);
		} else {
			response.sendRedirect("/adv/user/login");
		}

	}

	protected void view(HttpServletRequest request, HttpServletResponse response, UserObject user,int page,String saveKey)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// xác định kiểu nội dung xuất về trình khách
		response.setContentType(CONTENT_TYPE);

		// tạo đối tượng thực hiện xuất nội dung
		PrintWriter out = response.getWriter();

		// tìm bộ quản lý kết nối
		ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");

		if (cp == null) {
			getServletContext().setAttribute("CPool", cp);
		}
		
		saveKey = jsoft.library.Utilities.encode(saveKey);
//		System.out.println(saveKey);

		// tạo đối tượng thực thi chức năng
		UserControl uc = new UserControl(cp);

		// lấy cấu trúc
		UserObject similar = new UserObject();
		similar.setUser_id(user.getUser_id());
		similar.setUser_permission(user.getUser_permission());
		similar.setUser_deleted(false);
		similar.setUser_name(saveKey);
		// tim thanh so xac dinh loại danh sách
		String trash =request.getParameter("trash");
		boolean isTrash = (trash!=null)?true:false;
		String title;
		if(trash==null) {
	        similar.setUser_deleted(false);
	   		title="Danh sách người sử dụng";
		} else {
			title ="Danh sách người sử dụng bị xóa";
			similar.setUser_deleted(true);
		}
		byte pageSize = 5;
		Triplet<UserObject, Integer, Byte> infos = new Triplet<>(similar, pageSize*(page-1), pageSize);
		ArrayList<String> viewList = uc.viewUser(infos, new Pair<>(USER_SOFT.ID, ORDER.ASC),page,saveKey,isTrash);
		
		// trả về kết nối
		uc.releaseConnection();

		
		String pos = (trash==null)?"/header?pos=urlist":"/header?pos=urtrash";
		
		// tham chiếu tìm header
		RequestDispatcher h = request.getRequestDispatcher(pos);
		if (h != null) {
			h.include(request, response);
		}

		out.append("<main id=\"main\" class=\"main\">");

		RequestDispatcher error = request.getRequestDispatcher("/error");
		if (error != null) {
			error.include(request, response);
		}
		
		// data export message
		String success = request.getParameter("success");
		if (success != null) {
			out.append("<div class=\"toast-container position-fixed top-1 end-0 ps-3 pe-5 mb-3\">");
			out.append(
					"<div id=\"liveToast\" class=\"toast\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\">");
			out.append("<div class=\"toast-header\">");
			// out.append("<img src=\"...\" class=\"rounded me-2\" alt=\"...\">");
			out.append("<strong class=\"me-auto text-success\">Thông báo</strong>");
			out.append("<small>10 giây</small>");
			out.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"toast\" aria-label=\"Close\"></button>");
			out.append("</div>");
			out.append("<div class=\"toast-body\">");
			out.append("Đã export");
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

		out.append("<div class=\"pagetitle d-flex\">");
		out.append("<h1>"+title+"</h1>");
		out.append("<nav class=\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/adv/view\"><i class=\"bi bi-house\"></i></a></li>");
		out.append("<li class=\"breadcrumb-item\">Người sử dụng</li>");
		out.append("<li class=\"breadcrumb-item active\">"+(isTrash?"Thùng rác":"Danh sách")+"</li>");
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");

		
		// list user
		out.append("<section class=\"section\">");
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-12\">");
		
		out.append("<div class=\"card\">");
		out.append("<div class=\"card-body\">");
        
		if(trash==null) {
		// add user modal
		out.append("<button type=\"button\" class=\"btn btn-primary my-4 \" data-bs-toggle=\"modal\" data-bs-target=\"#staticBackdrop\"><i class=\"fa-solid fa-user-plus me-2\"></i>Thêm mới</button>");

		out.append("<form method=\"POST\" >");
		out.append("<div class=\"modal fade modal-lg\" id=\"staticBackdrop\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">");
		out.append("<div class=\"modal-dialog\">");
		out.append("<div class=\"modal-content\">");
		out.append("<div class=\"modal-header\">");
		out.append("<h1 class=\"modal-title fs-5\" id=\"staticBackdropLabel\">Thêm người sử dụng</h1>");
		out.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
		out.append("</div>");

		out.append("<div class=\"modal-body\">");

		out.append("<div class=\"container\">");

		out.append("<div class=\"row mb-2\">");
		out.append("<div class=\"col\">");
		out.append("<label for=\"userName\" class=\"form-label\"><i class=\"fas fa-user me-2\"></i>Tên tài khoản</label>");
		out.append("<input type=\"text\" class=\"form-control\" placeholder=\"...\" name=\"txtUsername\" id=\"userName\" required>");
		out.append("</div>");
		out.append("<div class=\"col\">");
		out.append("<label for=\"fullName\" class=\"form-label\"><i class=\"fas fa-file-signature me-2\"></i>Tên đầy đủ</label>");
		out.append("<input type=\"text\" class=\"form-control\" placeholder=\"...\" name=\"txtFullname\" id=\"fullName\" required>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-2\">");
		out.append("<div class=\"col\">");
		out.append("<label for=\"inputPassword4\" class=\"form-label\"><i class=\"fas fa-key me-2\"></i>Mật khẩu</label>");
		out.append("<input type=\"password\" class=\"form-control\" placeholder=\"...\" name=\"txtUserpass\" id=\"inputPassword4\" required>");
		out.append("</div>");
		out.append("<div class=\"col\">");
		out.append("<label for=\"inputPassword4\" class=\"form-label\"><i class=\"fas fa-key me-2\"></i>Xác thực mật khẩu</label>");
		out.append("<input type=\"password\" class=\"form-control\" placeholder=\"...\" name=\"txtUserpass2\" id=\"inputPassword5\" required>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-2\">");
		out.append("<div class=\"col\">");
		out.append("<label for=\"email\" class=\"form-label\"><i class=\"fas fa-envelope-open me-2\"></i>Hộp thư</label>");
		out.append("<input type=\"email\" class=\"form-control\" placeholder=\"...\" name=\"txtUseremail\" id=\"email\" required>");
		out.append("</div>");
		out.append("<div class=\"col\">");
		out.append("<label for=\"address\" class=\"form-label\"><i class=\"fas fa-map-marker-alt me-2\"></i>Địa chỉ</label>");
		out.append("<input type=\"text\" class=\"form-control\" placeholder=\"...\" name=\"txtUseraddress\" id=\"address\" required>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-2\">");
		out.append("<div class=\"col\">");
		out.append("<label for=\"phoneNumber\" class=\"form-label\"><i class=\"fas fa-phone-square me-2\"></i>Số điện thoại</label>");
		out.append("<input type=\"text\" class=\"form-control\" placeholder=\"...\" name=\"txtUserphone\" id=\"phoneNumber\" required>");
		out.append("</div>");
		
		out.append("<div class=\"col\">");
		out.append("<label for=\"address\" class=\"form-label\"><i class=\"fas fa-user-tag\"></i>Quyền thực thi</label>");
		out.append("<select class=\"form-select\" id=\"userpermis\" name=\"slcPermis\" required>");
		out.append("<option value=\"1\">Thành viên</option>");
		out.append("<option value=\"2\">Tác giả</option>");
		out.append("<option value=\"3\">Quản lý</option>");
		out.append("<option value=\"4\">Quản trị</option>");
		out.append("<option value=\"5\">Quản trị cấp cao</option>");
		out.append("</select>");
		out.append("</div>");
		
		out.append("</div>");
		out.append("</div>"); // end container

		out.append("</div>");
		out.append("<div class=\"modal-footer justify-content-center \">");
		out.append("<button type=\"button\" class=\"btn btn-secondary px-5 text-uppercase\" data-bs-dismiss=\"modal\"><i class=\"fas fa-window-close pe-1\"></i>đóng</button>");
		out.append("<button type=\"submit\" class=\"btn btn-primary px-5 text-uppercase \" ><i class=\"fas fa-user-plus pe-1\"></i>tạo</button>");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");
		out.append("</form>");
		}
        // list user
		out.append(viewList.get(0));
	    out.append("<a href=\"/adv/user/export\" class=\"btn btn-primary\">xuất file</a>");
		// phan trang
		out.append(viewList.get(2));
		
		out.append("</div>"); // end card-body
		out.append("</div>"); // end card

		out.append("</div>");
		out.append("</div>");
		out.append("</section>");

		// charts
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-12\">");
		out.append(viewList.get(1));
		out.append("</div>");
		out.append("</div>");
		out.append("</main><!-- End #main -->");

		
		// tham chiếu tìm sidebar
		RequestDispatcher f = request.getRequestDispatcher("/footer");
		if (f != null) {
			f.include(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.setCharacterEncoding("UTF-8");

		String name = request.getParameter("txtUsername");
		String pass = request.getParameter("txtUserpass");
		String comfirmPass = request.getParameter("txtUserpass2");
		String email = request.getParameter("txtUseremail");
		String addresss = request.getParameter("txtUseraddress");
		String phone = request.getParameter("txtUserphone");

		byte permis = jsoft.library.Utilities.getByteParam(request, "slcPermis");

		if (name != null && !name.equalsIgnoreCase("") && jsoft.library.Utilities_text.checkValidPass(pass, comfirmPass)
				&& email != null && !email.equalsIgnoreCase("") && phone != null && !phone.equalsIgnoreCase("")
				&& addresss != null && !addresss.equalsIgnoreCase("") && permis > 0) {

			UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

			String fullname = request.getParameter("txtFullname");

			UserObject newUser = new UserObject();
			newUser.setUser_name(name);
//			newUser.setUser_fullname(fullname);
			newUser.setUser_fullname(jsoft.library.Utilities.encode(fullname));
			newUser.setUser_pass(pass);
			newUser.setUser_permission(permis);
			newUser.setUser_homephone(phone);
			newUser.setUser_address(jsoft.library.Utilities.encode(addresss));
			newUser.setUser_email(email);
			newUser.setUser_avatar("default.jpg");
			newUser.setUser_parent_id(user.getUser_id());
			newUser.setUser_created_date(jsoft.library.Utilities_date.getDate());

			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			UserControl uc = new UserControl(cp);
			// add new user
			if (cp != null) {
				getServletContext().setAttribute("CPool", uc.getCP());
			}
			boolean result = uc.addUser(newUser);
			// return connect
			uc.releaseConnection();
			if (result) {
				response.sendRedirect("/adv/user/list");
			} else {
				response.sendRedirect("/adv/user/list?err=add");
			}

		} else {
			String key = request.getParameter("keyword");
			 String encodedKeyword = URLEncoder.encode(key, "UTF-8");
			String key_url = (key!=null && !key.equalsIgnoreCase(""))?("&key="+encodedKeyword):"";
			response.sendRedirect("/adv/user/list?page=1"+key_url);
		}
	}

}

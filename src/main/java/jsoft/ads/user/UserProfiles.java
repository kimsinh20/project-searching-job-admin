package jsoft.ads.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import jsoft.ConnectionPool;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class View
 */
@WebServlet("/user/profiles")
@MultipartConfig
public class UserProfiles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserProfiles() {
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

		// tìm thông tin đăng nhập
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

		if (user != null) {
			view(request, response, user);
		} else {
			response.sendRedirect("/adv/user/login");
		}

	}

	protected void view(HttpServletRequest request, HttpServletResponse response, UserObject user)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// xác định kiểu nội dung xuất về trình khách
		response.setContentType(CONTENT_TYPE);

		// tạo đối tượng thực hiện xuất nội dung
		PrintWriter out = response.getWriter();

		// tim id của người sử dụng đẻ cập nhập
		int id = jsoft.library.Utilities.getIntParam(request, "id");

		UserObject editUser = null;

		if (id > 0) {
			// tìm bộ quản lý kết nối
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			// tạo đối tượng thực thi chức năng
			UserControl uc = new UserControl(cp);
			if (cp == null) {
				getServletContext().setAttribute("CPool", cp);
			}
			editUser = uc.getUserObject(id);

			// trả về kết nối
			uc.releaseConnection();
		}

		// tham chiếu tìm header
		RequestDispatcher h = request.getRequestDispatcher("/header?pos=urlist");
		if (h != null) {
			h.include(request, response);
		}

		out.append("<main id=\"main\" class=\"main\">");

		RequestDispatcher error = request.getRequestDispatcher("/error");
		if (error != null) {
			error.include(request, response);
		}

		out.append("<div class=\"pagetitle d-flex\">");
		out.append("<h1>Danh sách người sử dụng</h1>");
		out.append("<nav class=\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/adv/view\"><i class=\"bi bi-house\"></i></a></li>");
		out.append("<li class=\"breadcrumb-item\">Người sử dụng</li>");
		out.append("<li class=\"breadcrumb-item active\">Cập nhật chi tiết</li>");
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");

		out.append("<section class=\"section profile\">");
		out.append("<div class=\"row\">");

//		out.append("<div class=\"card\">");
//		out.append("<div class=\"card-body\">");

		out.append("<div class=\"col-xl-4\">");

		out.append("<div class=\"card\">");
		out.append("<div class=\"card-body profile-card pt-4 d-flex flex-column align-items-center\">");

		if (editUser != null) {
			out.append("<img src=\"/adv/adimgs/" + editUser.getUser_avatar()
					+ "\" alt=\"Profile\" class=\"rounded-circle\">");
			out.append("<h2>" + editUser.getUser_fullname() + "</h2>");
			out.append("<h3>" + editUser.getUser_name() + "</h3>");
			out.append("<div class=\"social-links mt-2\">");
			out.append("<a href=\"#\" class=\"twitter\"><i class=\"bi bi-twitter\"></i></a>");
			out.append("<a href=\"#\" class=\"facebook\"><i class=\"bi bi-facebook\"></i></a>");
			out.append("<a href=\"#\" class=\"instagram\"><i class=\"bi bi-instagram\"></i></a>");
			out.append("<a href=\"#\" class=\"linkedin\"><i class=\"bi bi-linkedin\"></i></a>");
			out.append("</div>");
		}

		out.append("</div>");
		out.append("</div>");

		out.append("</div>"); // end cod-xl-4

		out.append("<div class=\"col-xl-8\">");

		out.append("<div class=\"card\">");
		out.append("<div class=\"card-body pt-3\">");
		out.append("<!-- Bordered Tabs -->");
		out.append("<ul class=\"nav nav-tabs nav-tabs-bordered\">");

		out.append("<li class=\"nav-item\">");
		out.append(
				"<button class=\"nav-link active\" data-bs-toggle=\"tab\" data-bs-target=\"#overview\"><i class=\"fas fa-info-circle me-1\"></i>Tổng quát</button>");
		out.append("</li>");

		out.append("<li class=\"nav-item\">");
		out.append(
				"<button class=\"nav-link\" data-bs-toggle=\"tab\" data-bs-target=\"#edit\"><i class=\"fas fa-pen-square me-1\"></i>Chỉnh sửa</button>");
		out.append("</li>");

		out.append("<li class=\"nav-item\">");
		out.append(
				"<button class=\"nav-link\" data-bs-toggle=\"tab\" data-bs-target=\"#settings\"><i class=\"fas fa-user-cog me-1\"></i>Cài đặt</button>");
		out.append("</li>");

		out.append("<li class=\"nav-item\">");
		out.append(
				"<button class=\"nav-link\" data-bs-toggle=\"tab\" data-bs-target=\"#profile-change-password\"><i class=\"fas fa-unlock me-1\"></i>Đổi mật khẩu</button>");
		out.append("</li>");

		out.append("</ul>");
		out.append("<div class=\"tab-content pt-2\">");

		// tab tong quat
		String sumary = "", name = "", fullname = "", address = "", email = "", home = "", job = "", jobarea = "";
		Short logined = 0;
		String birthday = "", office = "", mobile = "";
		int gender = 0;
		String avatar = "";
		boolean isEdit = false;
		boolean isChangePass = false;
		if (editUser != null) {
			sumary = editUser.getUser_notes() != null ? editUser.getUser_notes() : "";
			name = editUser.getUser_name();
			fullname = editUser.getUser_fullname();
			address = editUser.getUser_address();
			email = editUser.getUser_email();
			home = editUser.getUser_homephone();
			office = editUser.getUser_officephone() != null ? editUser.getUser_officephone() : "";
			mobile = editUser.getUser_mobilephone() != null ? editUser.getUser_mobilephone() : "";
			gender = editUser.getUser_gender();
			birthday = editUser.getUser_birthday();
			avatar = editUser.getUser_avatar();
			job = editUser.getUser_job() != null ? editUser.getUser_job() : "";
			jobarea = editUser.getUser_jobarea() != null ? editUser.getUser_jobarea() : "";
			logined = editUser.getUser_logined();
			isEdit = true;
			isChangePass = true;
		}

		out.append("<div class=\"tab-pane fade show active profile-overview\" id=\"overview\">");
		out.append("<h5 class=\"card-title\">Tóm tắt<i class=\"bi bi-journal-text ms-2\"></i></h5>");
		out.append("<p class=\"small fst-italic\">" + sumary + "</p>");

		out.append("<h5 class=\"card-title\">Chi tiết<i class=\"fas fa-info-circle ms-2\"></i></h5>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label \">Họ và tên</div>");
		out.append("<div class=\"col-lg-6 col-md-5\">" + fullname + "</div>");
		out.append("<div class=\"col-lg-3 col-md-3\">(" + name + ")</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Nghề nghiệp</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + job + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Lĩnh vực</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + jobarea + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">địa chỉ</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + address + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">điện thoại</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + home + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">hộp thư</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + email + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Lần đăng nhập</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + logined + "</div>");
		out.append("</div>");

		out.append("</div>");

		// tab chỉnh sửa
		out.append("<div class=\"tab-pane fade profile-edit pt-3\" id=\"edit\">");

		out.append("<!-- Profile Edit Form -->");
		out.append("<form method=\"POST\" action=\"/adv/user/profiles\" enctype=\"multipart/form-data\">");
		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"profileImage\" class=\"col-md-3 col-lg-2 col-form-label\">Hình Ảnh</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<img src=\"/adv/adimgs/" + avatar + "\" id=\"blah\" alt=\"Profile\">");
		out.append("<div class=\"pt-2\">");
		out.append(
				"<input type=\"file\" name=\"avatar\" onchange=\"document.getElementById('blah').src = window.URL.createObjectURL(this.files[0])\">");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"fullName\" class=\"col-md-3 col-lg-2 col-form-label\">Họ và tên</label>");
		out.append("<div class=\"col-md-6 col-lg-7\">");
		out.append("<div class=\"input-group\">");
		out.append("<input name=\"txtFullName\" type=\"text\" class=\"form-control\" id=\"fullName\" value=\""
				+ fullname + "\">");
		out.append("<input name=\"txtAlias\" type=\"text\" class=\"form-control\" id=\"alias\" readonly >");
		out.append("</div>");
		out.append("</div>");
		out.append("<div class=\"col-md-3 col-lg-3\">");
		out.append("<input name=\"name\" type=\"text\" class=\"form-control\" id=\"name\" disabled value=\"" + name
				+ "\">");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"notes\" class=\"col-md-3 col-lg-2 col-form-label\">Tóm tắt</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<textarea name=\"txtNotes\" class=\"form-control\" id=\"notes\" style=\"height: 100px\">" + sumary
				+ "</textarea>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"date\" class=\"col-md-3 col-lg-2 col-form-label\">Ngày Sinh</label>");
		out.append("<div class=\"col-md-3 col-lg-4\">");
		out.append("<input name=\"txtBirthday\" type=\"date\" class=\"form-control\" id=\"date\" value=\"" + birthday
				+ "\">");
		out.append("</div>");
		out.append("<label for=\"date\" class=\"col-md-3 col-lg-2 col-form-label\">Giới tính</label>");
		out.append("<div class=\"col-md-3 col-lg-4\">");
		out.append("<select class=\"form-control\" id=\"slcGender\"  name=\"slcGender\"> ");
		out.append("<option value=\"0\">----</option>");
		out.append("<option value=\"1\" " + (gender == 1 ? "Selected" : "") + ">Nam</option>");
		out.append("<option value=\"2\" " + (gender == 2 ? "selected" : "") + ">Nữ</option>");
		out.append("</select>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"Job\" class=\"col-md-3 col-lg-2 col-form-label\">Nghề nghiệp</label>");
		out.append("<div class=\"col-md-3 col-lg-4\">");
		out.append("<input name=\"txtJob\" type=\"text\" class=\"form-control\" id=\"Job\" value=\"" + job + "\">");
		out.append("</div>");
		out.append("<label for=\"Country\" class=\"col-md-3 col-lg-2 col-form-label\">Lĩnh vực</label>");
		out.append("<div class=\"col-md-3 col-lg-4\">");
		out.append("<input name=\"txtJobArea\" type=\"text\" class=\"form-control\" id=\"Country\" value=\"" + jobarea
				+ "\">");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"Address\" class=\"col-md-3 col-lg-2 col-form-label\">Địa chỉ</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<input name=\"txtAddress\" type=\"text\" class=\"form-control\" id=\"Address\" value=\"" + address
				+ "\">");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"txtPhone\" class=\"col-md-3 col-lg-2 col-form-label\">Điện thoại</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<div class=\"input-group\">");
		out.append(
				"<input name=\"txtHomePhone\" type=\"text\" class=\"form-control\" placehoder=\"Home phone\" id=\"homePhone\" value=\""
						+ home + "\">");
		out.append(
				"<input name=\"txtOfficePhone\" type=\"text\" class=\"form-control\" placehoder=\"Office phone\" id=\"officePhone\" value=\""
						+ office + "\">");
		out.append(
				"<input name=\"txtMobilePhone\" type=\"text\" class=\"form-control\" placehoder=\"Mobile phone\" id=\"mobilePhone\" value=\""
						+ mobile + "\">");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"Email\" class=\"col-md-3 col-lg-2 col-form-label\">Hộp thư</label>");
		out.append("<div class=\"col-md-3 col-lg-4\">");
		out.append(
				"<input name=\"txtEmail\" type=\"email\" class=\"form-control\" id=\"Email\" value=\"" + email + "\">");
		out.append("</div>");
		out.append("<label for=\"Facebook\" class=\"col-md-3 col-lg-2 col-form-label\">Facebook</label>");
		out.append("<div class=\"col-md-3 col-lg-4\">");
		out.append(
				"<input name=\"facebook\" type=\"text\" class=\"form-control\" id=\"Facebook\" value=\"https://facebook.com/#\">");
		out.append("</div>");
		out.append("</div>");

		// truyen id theo co che bien trong an de thuc hien edit
		if (id > 0 && isEdit) {
			out.append("<input type=\"hidden\" name=\"idForPost\" value=\"" + id + "\">");
			out.append("<input type=\"hidden\" name=\"act\" value=\"edit\">");
		}
		out.append("<div class=\"text-center\">");
		out.append(
				"<button type=\"submit\" class=\"btn btn-primary\"><i class=\"far fa-save me-2\"></i>Lưu thay đổi</button>");
		out.append("</div>");

		out.append("</form><!-- End Profile Edit Form -->");
		out.append("</div>");

		// tab setting
		out.append("<div class=\"tab-pane fade pt-3\" id=\"settings\">");
		out.append("<!-- Settings Form -->");
		out.append("<form>");

		out.append("<div class=\"row mb-3\">");
		out.append("<label for=\"fullName\" class=\"col-md-4 col-lg-3 col-form-label\">Email Notifications</label>");
		out.append("<div class=\"col-md-8 col-lg-9\">");
		out.append("<div class=\"form-check\">");
		out.append("<input class=\"form-check-input\" type=\"checkbox\" id=\"changesMade\" checked>");
		out.append("<label class=\"form-check-label\" for=\"changesMade\">");
		out.append("Changes made to your account");
		out.append("</label>");
		out.append("</div>");
		out.append("<div class=\"form-check\">");
		out.append("<input class=\"form-check-input\" type=\"checkbox\" id=\"newProducts\" checked>");
		out.append("<label class=\"form-check-label\" for=\"newProducts\">");
		out.append("Information on new products and services");
		out.append("</label>");
		out.append("</div>");
		out.append("<div class=\"form-check\">");
		out.append("<input class=\"form-check-input\" type=\"checkbox\" id=\"proOffers\">");
		out.append("<label class=\"form-check-label\" for=\"proOffers\">");
		out.append("Marketing and promo offers");
		out.append("</label>");
		out.append("</div>");
		out.append("<div class=\"form-check\">");
		out.append("<input class=\"form-check-input\" type=\"checkbox\" id=\"securityNotify\" checked disabled>");
		out.append("<label class=\"form-check-label\" for=\"securityNotify\">");
		out.append("Security alerts");
		out.append("</label>");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"text-center\">");
		out.append("<button type=\"submit\" class=\"btn btn-primary\">Save Changes</button>");
		out.append("</div>");
		out.append("</form><!-- End settings Form -->");

		out.append("</div>");

		// tab passowrd
		out.append("<div class=\"tab-pane fade pt-3\" id=\"profile-change-password\">");
		out.append("<!-- Change Password Form -->");
		out.append("<form method=\"POST\" action=\"/adv/user/profiles\">");
		out.append("<div class=\"row mb-3\">");
		out.append(
				"<label for=\"currentPassword\" class=\"col-md-4 col-lg-3 col-form-label\">Mật khẩu hiện tại</label>");
		out.append("<div class=\"col-md-8 col-lg-9\">");
		out.append("<input name=\"currentPassword\" type=\"password\" class=\"form-control\" id=\"currentPassword\">");
		out.append("</div>");
		out.append("<div id=\"errpassword\"></div>");
		out.append("</div>");
		out.append("<div class=\"row mb-3\">");
		out.append("<label for=\"newPassword\" class=\"col-md-4 col-lg-3 col-form-label\">Mật khẩu mới</label>");
		out.append("<div class=\"col-md-8 col-lg-9\">");
		out.append("<input name=\"newpassword\" type=\"password\" class=\"form-control\" id=\"newPassword\">");
		out.append("</div>");
		out.append("<div id=\"errnewpassword\"></div>");
		out.append("</div>");
		out.append("<div class=\"row mb-3\">");
		out.append(
				"<label for=\"renewPassword\" class=\"col-md-4 col-lg-3 col-form-label\">Nhập lại mật khẩu mới</label>");
		out.append("<div class=\"col-md-8 col-lg-9\">");
		out.append("<input name=\"renewpassword\" type=\"password\" class=\"form-control\" id=\"renewPassword\">");
		out.append("</div>");
		out.append("<div id=\"errrenewpassword\"></div>");
		out.append("</div>");
		// truyen id theo co che bien trong an de thuc hien change pass
		if (id > 0 && isChangePass) {
			out.append("<input type=\"hidden\" name=\"idForPost\" value=\"" + id + "\">");
			out.append("<input type=\"hidden\" name=\"act\" value=\"changePass\">");
		}
		out.append("<div class=\"text-center\">");
		out.append("<button type=\"submit\" id=\"btnChangePass\" class=\"btn btn-primary me-2\">Đổi mật khẩu</button>");
		out.append("<a href=\"/adv/user/reset?id="+id+"\" id=\"btnChangePass\" class=\"btn btn-success\">Reset mật khẩu</button>");
		out.append("</div>");
		out.append("</form>");
		out.append("</div>");

		out.append("</div><!-- End Bordered Tabs -->");

		out.append("</div>");
		out.append("</div>");

		out.append("</div>");

		out.append("</div>");
		out.append("</section>");

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
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

		// thiết lập tập ký tự cần lấy
		request.setCharacterEncoding("utf-8");

		// lay id

		int id = jsoft.library.Utilities.getIntParam(request, "idForPost");
		String action = request.getParameter("act");
//		System.out.println(action);

		if (id > 0) {
			if (action != null && action.equalsIgnoreCase("edit")) {
				// lấy thông tin
				String fullname = request.getParameter("txtFullName");
				String alias = request.getParameter("txtAlias");
				String address = request.getParameter("txtAddress");
				String job = request.getParameter("txtJob");
				String jobArea = request.getParameter("txtJobArea");
				String email = request.getParameter("txtEmail");
				String notes = request.getParameter("txtNotes");
				String birthaday = request.getParameter("txtBirthday");
				String home = request.getParameter("txtHomePhone");
				String office = request.getParameter("txtOfficePhone");
				String mobile = request.getParameter("txtMobilePhone");
				int gender = jsoft.library.Utilities.getByteParam(request, "slcGender");
				Part filePart = request.getPart("avatar");
				// Save the file to the server's file system.
				String avatar = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

//			    String avatarObject = filePart.getHeader();

//				System.out.println(avatar);

				if (fullname != null && !fullname.equalsIgnoreCase("") && email != null && !email.equalsIgnoreCase("")
						&& home != null && !home.equalsIgnoreCase("")) {
					// Tạo đối tượng UserObject
					UserObject eUser = new UserObject();
					eUser.setUser_id(id);
					eUser.setUser_fullname(jsoft.library.Utilities.encode(fullname));
					eUser.setUser_alias(alias);
					eUser.setUser_job(jsoft.library.Utilities.encode(job));
					eUser.setUser_jobarea(jsoft.library.Utilities.encode(jobArea));
					eUser.setUser_parent_id(user.getUser_id()); // lay tu tai khoan dang nhap
					eUser.setUser_email(email);
					eUser.setUser_homephone(home);
					eUser.setUser_officephone(office);
					eUser.setUser_gender(gender);
					eUser.setUser_mobilephone(mobile);
					if (avatar != null && !avatar.equalsIgnoreCase("")) {
						eUser.setUser_avatar(avatar);
					}
					eUser.setUser_last_logined(jsoft.library.Utilities_date.getDate());
					eUser.setUser_birthday(birthaday);
					eUser.setUser_notes(jsoft.library.Utilities.encode(notes));
					eUser.setUser_address(jsoft.library.Utilities.encode(address));

					//
					ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
					UserControl uc = new UserControl(cp);
					if (cp == null) {
						getServletContext().setAttribute("CPool", uc.getCP());
					}

					// thuc hien chỉnh sủa
					boolean result = uc.editUser(eUser, USER_EDIT_TYPE.GENERAL);

					// tra ve ket noi
					uc.releaseConnection();

					//
					if (result) {
						response.sendRedirect("/adv/user/list");
					} else {
						response.sendRedirect("/adv/user/list?err=edit");
					}

				} else {
					response.sendRedirect("/adv/user/list?err=valueeadd");
				}
			} else if (action != null && action.equalsIgnoreCase("changePass")) {
				// sua mat khau
				String currenPass = request.getParameter("currentPassword");

				if (currenPass != null && !currenPass.equalsIgnoreCase("")) {
					// tìm bộ quản lý kết nối
					ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
					// tạo đối tượng thực thi chức năng
					UserControl uc = new UserControl(cp);
					if (cp == null) {
						getServletContext().setAttribute("CPool", cp);
					}
					UserObject userE = uc.getCheckPass(id,currenPass);
					// trả về kết nối
					uc.releaseConnection();
                    
					  if (userE!=null) {
							String newPass = request.getParameter("newpassword");
							String reNewPass = request.getParameter("renewpassword");
							if (newPass != null && !newPass.equalsIgnoreCase("") && reNewPass != null
									&& !newPass.equalsIgnoreCase("") && newPass.equalsIgnoreCase(reNewPass)) {
								UserObject eUser = new UserObject();
								eUser.setUser_id(id);
								eUser.setUser_pass(newPass);
								// thuc hien doi mat khau
								if (cp == null) {
									getServletContext().setAttribute("CPool", cp);
								}
								boolean result = uc.editUser(eUser, USER_EDIT_TYPE.PASS);
//								boolean result =true;
								// trả về kết nối
								uc.releaseConnection();
								if (result) {
									response.sendRedirect("/adv/user/list");
								} else {
									response.sendRedirect("/adv/user/list?err=changepass");
								}

							} else {
								response.sendRedirect("/adv/user/list?err=valuepass");
							}
						}
					else {
						response.sendRedirect("/adv/user/list?err=passnotmatch");
					}
				} else {
					response.sendRedirect("/adv/user/list?err=valuepass");
				}
			}
		} else {
			response.sendRedirect("/adv/user/list?err=profiles");
		}
	}

}

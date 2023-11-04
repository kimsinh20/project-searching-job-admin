//package jsoft.ads.job;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.PrintWriter;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Part;
//
//import org.javatuples.Triplet;
//
//import com.google.gson.Gson;
//
//import jsoft.ConnectionPool;
//import jsoft.objects.AddressObject;
//import jsoft.objects.JobObject;
//import jsoft.objects.FieldObject;
//import jsoft.objects.UserObject;
//
///**
// * Servlet implementation class SectionEdit
// */
//@WebServlet("/job/edit")
//@MultipartConfig(
//		fileSizeThreshold = 1024*1024*2,
//		maxFileSize = 1024*1024*10,
//		maxRequestSize = 1024*1024*11
//		)
//public class JobEdit extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	// định nghĩa kiểu nội dung xuất về trình khách
//	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
//
//	/**
//	 * @see HttpServlet#HttpServlet()
//	 */
//	public JobEdit() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		// tìm thông tin đăng nhập
//		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");
//
//		if (user != null) {
//			view(request, response, user);
//		} else {
//			response.sendRedirect("/adv/user/login");
//		}
//	}
//
//	@SuppressWarnings("unlikely-arg-type")
//	protected void view(HttpServletRequest request, HttpServletResponse response, UserObject user)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		// xác định kiểu nội dung xuất về trình khách
//		response.setContentType(CONTENT_TYPE);
//
//		// tạo đối tượng thực hiện xuất nội dung
//		PrintWriter out = response.getWriter();
//
//		// tim id của người sử dụng đẻ cập nhập
//		short id = jsoft.library.Utilities.getShortParam(request, "id");
//		short page = jsoft.library.Utilities.getShortParam(request, "page");
//		
//		String view = request.getParameter("view");
//		boolean isView = (view != null) ? true : false;
//
//		JobObject editCom = null;
//		ArrayList<FieldObject> fields = new ArrayList<>();
//		HashMap<Integer, String> author_name = new HashMap<>();
//
//		if (id > 0) {
//			// tìm bộ quản lý kết nối
//			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
//			// tạo đối tượng thực thi chức năng
//			JobControl cc = new JobControl(cp);
//			if (cp == null) {
//				getServletContext().setAttribute("CPool", cp);
//			}
//			Triplet<JobObject, HashMap<Integer, String>, ArrayList<FieldObject>> getCateObject = cc
//					.getJobObject(id, user);
//			editCom = getCateObject.getValue0();
//			author_name = getCateObject.getValue1();
//			fields = getCateObject.getValue2();
//
//			// trả về kết nối
//			cc.releaseConnection();
//		}
//
//		String Job_sumary = "", Job_name = "", Job_officephone = "", Job_logo = "", email = "",
//				Job_banner = "", Job_about = "", Job_remuneration = "";
//		String Job_website = "", Job_created_date = "", Job_nationality = "", Job_last_modified = "";
//		FieldObject field = new FieldObject();
//		String isOt = "", Job_location = "", Job_size = "";
//		boolean isEdit = false;
//		int size_id;
//		AddressObject[] addressList = null;
//		boolean isChangePass = false;
//		if (editCom != null) {
//			Gson gson = new Gson();
//			addressList = gson.fromJson(jsoft.library.Utilities.decode(editCom.getJob_location()),
//					AddressObject[].class);
//
//			// System.out.println(jsoft.library.Utilities.decode(editCom.getJob_location()));
//			Job_name = editCom.getJob_name() != null ? editCom.getJob_name() : "";
//			Job_sumary = editCom.getJob_summary() != null ? editCom.getJob_summary() : "";
//			Job_officephone = editCom.getJob_officephone() != null ? editCom.getJob_officephone() : "";
//			Job_logo = editCom.getJob_logo() != null ? editCom.getJob_logo() : "";
//			email = editCom.getJob_email() != null ? editCom.getJob_email() : "";
//			Job_banner = editCom.getJob_banner() != null ? editCom.getJob_banner() : "";
//			Job_about = editCom.getJob_about() != null
//					? jsoft.library.Utilities.decode(editCom.getJob_about())
//					: "";
//			Job_website = editCom.getJob_website() != null ? editCom.getJob_website() : "";
//			field = editCom.getField() != null ? editCom.getField() : null;
//			Job_created_date = editCom.getJob_created_date() != null ? editCom.getJob_created_date() : "";
//			Job_last_modified = editCom.getJob_last_modified() != null ? editCom.getJob_last_modified()
//					: "";
//			Job_remuneration = editCom.getJob_remuneration() != null
//					? jsoft.library.Utilities.decode(editCom.getJob_remuneration())
//					: "";
//			isOt = editCom.isJob_isOT() ? "có" : "không";
//			size_id = editCom.getJob_size();
//			switch (editCom.getJob_size()) {
//			case 0:
//				Job_size = "Dưới 100 người";
//				break;
//			case 1:
//				Job_size = "100 đến 500 người";
//				break;
//			case 2:
//				Job_size = "500 trên đến 1000 người";
//				break;
//			case 3:
//				Job_size = "Trên 1000 người";
//				break;
//			case 4:
//				Job_size = "Trên 5000 người";
//				break;
//			case 5:
//				Job_size = "Trên 10000 người";
//				break;
//			default:
//				throw new IllegalArgumentException("Unexpected value: " + editCom);
//			}
//
//			switch (editCom.getJob_nationality()) {
//			case 0:
//				Job_nationality = "Việt Nam";
//				break;
//			case 1:
//				Job_nationality = "Trung quốc";
//				break;
//			case 2:
//				Job_nationality = "Nhật bản";
//				break;
//			case 3:
//				Job_nationality = "Hàn quốc";
//				break;
//			case 4:
//				Job_nationality = "Singapo";
//				break;
//			case 5:
//				Job_nationality = "Mỹ";
//				break;
//			default:
//				throw new IllegalArgumentException("Unexpected value: " + editCom);
//			}
//		}
//
//		// tham chiếu tìm header
//		RequestDispatcher h = request.getRequestDispatcher("/header?pos=cplist");
//		if (h != null) {
//			h.include(request, response);
//		}
//
//		out.append("<main id=\"main\" class=\"main\">");
//
//		RequestDispatcher error = request.getRequestDispatcher("/error");
//		if (error != null) {
//			error.include(request, response);
//		}
//
//		out.append("<div class=\"pagetitle d-flex\">");
//		out.append("<h1>Hồ sơ công ty</h1>");
//		out.append("<nav class=\"ms-auto\">");
//		out.append("<ol class=\"breadcrumb\">");
//		out.append("<li class=\"breadcrumb-item\"><a href=\"/adv/view\"><i class=\"bi bi-house\"></i></a></li>");
//		out.append("<li class=\"breadcrumb-item\">Công ty</li>");
//		out.append("<li class=\"breadcrumb-item active\">Cập nhật chi tiết</li>");
//		out.append("</ol>");
//		out.append("</nav>");
//		out.append("</div><!-- End Page Title -->");
//
//		out.append("<section class=\"section profile\">");
//		out.append("<div class=\"row\">");
//
////		out.append("<div class=\"card\">");
////		out.append("<div class=\"card-body\">");
//
//		out.append("<div class=\"col-xl-4\">");
//
//		out.append("<div class=\"card\">");
//		out.append("<div class=\"card-body profile-card pt-4 d-flex flex-column align-items-center\">");
//
//		if (editCom != null) {
//			out.append("<img src=\"" + Job_logo + "\" alt=\"Profile\" class=\"rounded-circle\">");
//			out.append("<h2>" + Job_name + "</h2>");
//			out.append("<div class=\"social-links mt-2\">");
//			out.append("<a href=\"#\" class=\"twitter\"><i class=\"bi bi-twitter\"></i></a>");
//			out.append("<a href=\"#\" class=\"facebook\"><i class=\"bi bi-facebook\"></i></a>");
//			out.append("<a href=\"#\" class=\"instagram\"><i class=\"bi bi-instagram\"></i></a>");
//			out.append("<a href=\"#\" class=\"linkedin\"><i class=\"bi bi-linkedin\"></i></a>");
//			out.append("</div>");
//		}
//
//		out.append("</div>");
//		out.append("</div>");
//
//		out.append("</div>"); // end cod-xl-4
//
//		out.append("<div class=\"col-xl-8\">");
//
//		out.append("<div class=\"card\">");
//		out.append("<div class=\"card-body pt-3\">");
//		out.append("<!-- Bordered Tabs -->");
//		out.append("<ul class=\"nav nav-tabs nav-tabs-bordered\">");
//
//		out.append("<li class=\"nav-item\">");
//		out.append(
//				"<button class=\"nav-link active\" data-bs-toggle=\"tab\" data-bs-target=\"#overview\"><i class=\"fas fa-info-circle me-1\"></i>Tổng quát</button>");
//		out.append("</li>");
//		if (!isView) {
//			out.append("<li class=\"nav-item\">");
//			out.append(
//					"<button class=\"nav-link\" data-bs-toggle=\"tab\" data-bs-target=\"#edit\"><i class=\"fas fa-pen-square me-1\"></i>Chỉnh sửa</button>");
//			out.append("</li>");
//		}
//		out.append("</ul>");
//		out.append("<div class=\"tab-content pt-2\">");
//
//		// tab tong quat
//
//		out.append("<div class=\"tab-pane fade show active profile-overview\" id=\"overview\">");
//		out.append("<h5 class=\"card-title\">Tóm tắt<i class=\"bi bi-journal-text ms-2\"></i></h5>");
//		out.append("<p class=\"small fst-italic\">" + Job_sumary + "</p>");
//
//		out.append("<h5 class=\"card-title\">Chi tiết<i class=\"fas fa-info-circle ms-2\"></i></h5>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label \">Tên công ty</div>");
//		out.append("<div class=\"col-lg-6 col-md-5\">" + Job_name + "</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label\">Quốc gia</div>");
//		out.append("<div class=\"col-lg-9 col-md-8\">" + Job_nationality + "</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label\">Lĩnh vực</div>");
//		out.append("<div class=\"col-lg-9 col-md-8\">" + field.getFieldName() + "</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label\">địa chỉ</div>");
//		out.append("<div class=\"col-lg-9 col-md-8\">");
//		if(addressList!=null) {
//		for (AddressObject address : addressList) {
//			out.append("<p>"+address.getAddressDetail()).append(" " + address.getWards()).append(" " + address.getDistricts())
//					.append(" " + address.getProvinces()).append("</p>");
//		}
//		}
//		out.append("</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label\">điện thoại</div>");
//		out.append("<div class=\"col-lg-9 col-md-8\">" + Job_officephone + "</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label\">hộp thư</div>");
//		out.append("<div class=\"col-lg-9 col-md-8\">" + email + "</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label\">Website</div>");
//		out.append("<div class=\"col-lg-9 col-md-8\">" + Job_website + "</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label\">Làm thêm giờ</div>");
//		out.append("<div class=\"col-lg-9 col-md-8\">" + isOt + "</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label\">quy mô</div>");
//		out.append("<div class=\"col-lg-9 col-md-8\">" + Job_size + "</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label\">Ngày tạo</div>");
//		out.append("<div class=\"col-lg-9 col-md-8\">" + Job_created_date + "</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label\">Ngày chỉnh sửa gần nhất</div>");
//		out.append("<div class=\"col-lg-9 col-md-8\">" + Job_last_modified + "</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label\">Mô tả</div>");
//		out.append("<div class=\"col-lg-9 col-md-8\">" + Job_about + "</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label\">Phúc lợi</div>");
//		out.append("<div class=\"col-lg-9 col-md-8\">" + Job_remuneration + "</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label\">Đánh giá </div>");
//		out.append(
//				"<div class=\"col-lg-9 col-md-8\">" + "4.4 <i class=\"fa-solid fa-star text-warning\"></i>" + "</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label\">Lượt xem</div>");
//		out.append("<div class=\"col-lg-9 col-md-8\">" + 2 + "</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-3 col-md-4 label\">Lượt theo dõi</div>");
//		out.append("<div class=\"col-lg-9 col-md-8\">" + 201 + "</div>");
//		out.append("</div>");
//
//		out.append("</div>");
//
//		// tab chỉnh sửa
//		out.append("<div class=\"tab-pane fade profile-edit pt-3\" id=\"edit\">");
//
//		out.append("<!-- Profile Edit Form -->");
//		out.append("<form method=\"POST\" enctype=\"multipart/form-data\">");
//		
//		out.append("<div class=\"row mb-3 align-items-center\">");
//		out.append("<label for=\"profileImage\" class=\"col-md-3 col-lg-2 col-form-label\">Logo</label>");
//		out.append("<div class=\"col-md-9 col-lg-10\">");
//		out.append("<img src=\"" + Job_logo + "\" id=\"Job_logo\" alt=\"Profile\">");
//		out.append("<div class=\"pt-2\">");
//		out.append("<input type=\"file\" value=\"" + Job_logo
//				+ "\" name=\"Job_logo\" onchange=\"document.getElementById('Job_logo').src = window.URL.createObjectURL(this.files[0])\">");
//		out.append("</div>");
//		out.append("</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row mb-3 align-items-center\">");
//		out.append("<label for=\"profileImage\" class=\"col-md-3 col-lg-2 col-form-label\">Hình nền</label>");
//		out.append("<div class=\"col-md-9 col-lg-10\">");
//		out.append("<img src=\"" + Job_banner + "\" id=\"Job_banner\" alt=\"Profile\">");
//		out.append("<div class=\"pt-2\">");
//		out.append("<input type=\"file\" value=\"" + Job_banner
//				+ "\" name=\"Job_banner\" onchange=\"document.getElementById('Job_banner').src = window.URL.createObjectURL(this.files[0])\">");
//		out.append("</div>");
//		out.append("</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row mb-3 align-items-center\">");
//		out.append("<div class=\"col-md-5 col-lg-6\">");
//		out.append("<label for=\"Job_name\" class=\"col-form-label\">Tên công ty</label>");
//		out.append("<input name=\"Job_name\" type=\"text\" class=\"form-control\" id=\"Job_name\" value=\""
//				+ Job_name + "\">");
//		out.append("</div>");
//		out.append("<div class=\"col-md-7 col-lg-6\">");
//		out.append("<label for=\"Job_nationality\" class=\"form-label\"><i class=\"fas fa-file-signature me-2\"></i>Quốc gia</label>");
//		out.append("<select class=\"form-select\" id=\"Job_nationality\" name=\"Job_nationality\" required>");
//		out.append("<option value=\"0\" " + (editCom.getJob_nationality() == 0 ? "selected" : "") + ">Việt Nam</option>");
//		out.append("<option value=\"1\" " + (editCom.getJob_nationality() == 1 ? "selected" : "") + ">Trung Quốc</option>");
//		out.append("<option value=\"2\" " + (editCom.getJob_nationality() == 2 ? "selected" : "") + ">Nhật Bản</option>");
//		out.append("<option value=\"3\" " + (editCom.getJob_nationality() == 3 ? "selected" : "") + ">Hàn Quốc</option>");
//		out.append("<option value=\"4\" " + (editCom.getJob_nationality() == 4 ? "selected" : "") + ">Singapo</option>");
//		out.append("<option value=\"5\" " + (editCom.getJob_nationality() == 5 ? "selected" : "") + ">Mỹ</option>");
//
//		out.append("</select>");
//		out.append("</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row mb-3 align-items-center\">");
//		out.append("<div class=\"col-md-5 col-lg-6\">");
//		out.append("<label for=\"Job_field\" class=\"form-label\">Lĩnh vực</label>");
//		out.append("<select class=\"form-select\" id=\"Job_field\" name=\"Job_field\" required>");
//		for (FieldObject f : fields) {
//			if (f.getFieldId() == field.getFieldId()) {
//				out.append("<option value=\"" + f.getFieldId() + "\" selected>" + f.getFieldName() + "</option>");
//			} else {
//				out.append("<option value=\"" + f.getFieldId() + "\">" + f.getFieldName() + "</option>");
//			}
//		}
//		;
//		out.append("</select>");
//		out.append("</div>");
//		out.append("<div class=\"col-md-5 col-lg-6\">");
//		out.append("<label for=\"Job_size\" class=\"form-label\">Quy mô</label>");
//		out.append("<select class=\"form-select\" id=\"Job_size\" name=\"Job_size\" required>");
//		out.append("<option value=\"0\"" + (editCom.getJob_size() == 0 ? "selected" : "")
//				+ ">Dưới 100 người</option>");
//		out.append("<option value=\"1\" " + (editCom.getJob_size() == 1 ? "selected" : "")
//				+ ">100 đến 500 người</option>");
//		out.append("<option value=\"2\"" + (editCom.getJob_size() == 2 ? "selected" : "")
//				+ ">500 trên đến 1000 người</option>");
//		out.append("<option value=\"3\"" + (editCom.getJob_size() == 3 ? "selected" : "")
//				+ ">Trên 1000 người</option>");
//		out.append("<option value=\"4\"" + (editCom.getJob_size() == 4 ? "selected" : "")
//				+ ">Trên 5000 người</option>");
//		out.append("<option value=\"5\"" + (editCom.getJob_size() == 5 ? "selected" : "")
//				+ ">Trên 10000 người</option>");
//		out.append("</select>");
//		out.append("</div>");
//		out.append("</div>");
//
//		out.append("<button type=\"button\" class=\"btn btn-primary mt-2\" data-bs-toggle=\"modal\" data-bs-target=\"#basicModal\">");
//		out.append("Thêm địa chỉ");
//		out.append("</button>");
//		out.append("<div class=\"modal fade\" id=\"basicModal\" tabindex=\"-1\">");
//		out.append("<div class=\"modal-dialog\">");
//		out.append("<div class=\"modal-content\">");
//		out.append("<div class=\"modal-header\">");
//		out.append("<h5 class=\"modal-title\">Thêm địa chỉ</h5>");
//		out.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
//		out.append("</div>");
//		out.append("<div class=\"modal-body\">");
//		
//		out.append("<div class=\"row mb-2\">");
//		out.append("<div class=\"col\">");
//		out.append("<label for=\"provinces\" class=\"form-label\"><i class=\"fas fa-envelope-open me-2\"></i>Tỉnh/Thành phố</label>");
//		out.append("<select class=\"form-select\" id=\"provinces\" onchange='getProvinces(event)' name=\"slcprovinces\" >");
//		out.append("<option value=''>-- Chọn Tỉnh/Thành phố --</option>");
//		out.append("</select>");
//		// out.append("<small>Error</small>");
//		out.append("</div>");
//		out.append("<div class=\"col\">");
//		out.append("<label for=\"districts\" class=\"form-label\"><i class=\"fas fa-envelope-open me-2\"></i>Huyện/Thị xã</label>");
//		out.append("<select id='districts' class=\"form-control\" name=\"slcdistricts\" onchange='getDistricts(event)' >");
//		out.append("<option value=''>-- Chọn Huyện/Thị xã --</option>");
//		out.append("</select>");
//		// out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
//		out.append("</div>");
//		out.append("</div>");
//		
//		out.append("<div class=\"row mb-2\">");
//		out.append("<div class=\"col\" >");
//		out.append("<label for=\"wards\" class=\"form-label\"><i class=\"fas fa-file-signature me-2\"></i>Xã/Phường</label>");
//		out.append("<select class=\"form-select\" id=\"wards\" name=\"slcwards\" >");
//		out.append("<option value=''>-- Chọn Xã/Phường --</option>");
//		out.append("</select>");
//		// out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
//		out.append("</div>");
//		out.append("<div class=\"col\">");
//		out.append("<label for=\"addressDetail\" class=\"form-label\"><i class=\"fas fa-phone-square me-2\"></i>Địa chỉ cụ thể</label>");
//		out.append("<input type=\"text\" class=\"form-control\" placeholder=\"...\" name=\"addressDetail\" id=\"addressDetail\">");
//		out.append("<input type=\"hidden\" id=\"locationData\" name=\"locationData\">");
//		// out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
//		out.append("</div>");
//		out.append("</div>");
//		out.append("</div>");
//		out.append("<div class=\"modal-footer\">");
//		out.append("<button class=\"btn btn-primary text-center\" data-bs-dismiss=\"modal\"  type=\"button\" onclick=\"addAddress()\">Thêm</button>");
//		out.append("</div>");
//		out.append("</div>");
//		out.append("</div>");
//		out.append("</div><!-- End Basic Modal-->");
//		
//		//map
//		out.append("<div class=\"row mb-2\">");
//		out.append("<div class=\"col\" id=\"form-control\">");
//		out.append("<label for=\"Job_location\" class=\"form-label\"> Địa chỉ</label>");
//		out.append("<div id=\"address-list\">");
//		out.append("<ul id=\"address-items\">");
//		// list addres
//		out.append("</ul>");
//		out.append("</div>");
//        out.append("</div>");
//        out.append("</div>");
//        
////        if(addressList!=null) {
////			for (AddressObject address : addressList) {
////				out.append(address.getAddressDetail()).append(" " + address.getWards()).append(" " + address.getDistricts())
////						.append(" " + address.getProvinces());
////			}
////		}
//
//		out.append("<div class=\"row mb-3 align-items-center\">");
//		out.append("<div class=\"col-md-5 col-lg-6\">");
//		out.append("<label for=\"Job_email\" class=\"form-label\">Hộp thư</label>");
//		out.append("<input name=\"Job_email\" type=\"email\" class=\"form-control\" id=\"Job_email\" value=\""
//				+ email + "\">");
//		out.append("</div>");
//		out.append("<div class=\"col-md-7 col-lg-6\">");
//		out.append("<label for=\"Job_website\" class=\"form-label\">Website</label>");
//		out.append(
//				"<input name=\"Job_website\" type=\"text\" class=\"form-control\" id=\"Job_website\" value=\""
//						+ Job_website + "\">");
//		out.append("</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row mb-3 align-items-center\">");
//		out.append(
//				"<label for=\"chkOT\" class=\"col-md-3 com-lg-2 col-form-label\"><i class=\"fas fa-file-signature me-2\"></i>Làm thêm giờ</label>");
//		out.append("<div class=\"col-md-6 col-lg-6\" id=\"form-control\">");
//		out.append("<label class=\"me-2\">có</label>");
//		out.append("<input type=\"radio\" class=\"me-4\" value=\"1\" name=\"chkOT\" "
//				+ (editCom.isJob_isOT() ? "checked" : "") + ">");
//		out.append("<label class=\"me-2\">không</label>");
//		out.append("<input type=\"radio\" value=\"0\" name=\"chkOT\" " + (!editCom.isJob_isOT() ? "checked" : "")
//				+ ">");
//		out.append("</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row mb-3 align-items-center\">");
//		out.append("<label for=\"Job_phone\" class=\"col-md-3 col-lg-2 col-form-label\">Điện thoại</label>");
//		out.append("<div class=\"col-md-9 col-lg-10\">");
//		out.append("<div class=\"input-group\">");
//		out.append(
//				"<input name=\"Job_homephone\" type=\"text\" class=\"form-control\" placehoder=\"Home phone\" id=\"homePhone\" value=\""
//						+ Job_officephone + "\">");
//		out.append(
//				"<input name=\"Job_officephone\" type=\"text\" class=\"form-control\" placehoder=\"Office phone\" id=\"officePhone\" value=\""
//						+ Job_officephone + "\">");
//		out.append(
//				"<input name=\"Job_mobilephone\" type=\"text\" class=\"form-control\" placehoder=\"Mobile phone\" id=\"mobilePhone\" value=\""
//						+ Job_officephone + "\">");
//		out.append("</div>");
//		out.append("</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row mb-3 align-items-center\">");
//		out.append("<label for=\"Job_summary\" class=\"col-md-3 col-lg-2 col-form-label\">Tóm tắt</label>");
//		out.append("<div class=\"col-md-9 col-lg-10\">");
//		out.append(
//				"<textarea name=\"Job_summary\" class=\"form-control\" id=\"Job_summary\" style=\"height: 100px\">"
//						+ Job_sumary + "</textarea>");
//		out.append("</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row mb-3 align-items-center\">");
//		out.append("<label for=\"Job_about\" class=\"col-md-3 col-lg-2 col-form-label\">Về chúng tôi</label>");
//		out.append("<div class=\"col-md-9 col-lg-10\" id=\"form-control\">");
//		out.append(
//				"<textarea name=\"Job_about\" class=\"form-control\" id=\"Job_about\" style=\"height: 100px\">"
//						+ Job_about + "</textarea>");
//		out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
//		out.append("<script>");
//		out.append("var editor = CKEDITOR.replace(\"Job_about\");");
//		out.append("CKFinder.setupCKEditor(editor,\"/adv/ckfinder/\")");
//		out.append("</script>");
//		out.append("</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row mb-3 align-items-center\">");
//		out.append("<label for=\"Job_remuneration\" class=\"col-md-3 col-lg-2 col-form-label\">Phúc lợi</label>");
//		out.append("<div class=\"col-md-9 col-lg-10\" id=\"form-control\">");
//		out.append("<textarea name=\"Job_remuneration\" class=\"form-control\" id=\"Job_remuneration\" style=\"height: 100px\">"
//						+ Job_remuneration + "</textarea>");
//		out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
//		out.append("<script>");
//		out.append("var editor = CKEDITOR.replace(\"Job_remuneration\");");
//		out.append("CKFinder.setupCKEditor(editor,\"/adv/ckfinder/\")");
//		out.append("</script>");
//		out.append("</div>");
//		out.append("</div>");
//
//		// truyen id theo co che bien trong an de thuc hien edit
//		if (id > 0 && page > 0) {
//			out.append("<input type=\"hidden\" id=\"location\" name=\"location\" value=\"" + editCom.getJob_location() + "\">");
//			out.append("<input type=\"hidden\" name=\"idForPost\" value=\"" + id + "\">");
//			out.append("<input type=\"hidden\" name=\"page\" value=\"" + page + "\">");
//		}
//		out.append("<div class=\"text-center\">");
//		out.append("<button type=\"submit\" class=\"btn btn-primary\"><i class=\"far fa-save me-2\"></i>Lưu thay đổi</button>");
//		out.append("</div>");
//
//		out.append("</form><!-- End Profile Edit Form -->");
//		out.append("</div>"); // end tabpane
//
//		out.append("</div><!-- End Bordered Tabs -->");
//
//		out.append("</div>");
//		out.append("</div>");
//
//		out.append("</div>");
//
//		out.append("</div>");
//		out.append("</section>");
//
//		out.append("</main><!-- End #main -->");
//
//		// tham chiếu tìm sidebar
//		RequestDispatcher f = request.getRequestDispatcher("/footer");
//		if (f != null) {
//			f.include(request, response);
//		}
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	public static boolean checkValidString(String str) {
//		boolean flag = true;
//		if (str == null || str.equalsIgnoreCase("")) {
//			flag = false;
//		}
//		return flag;
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");
//
//		// thiết lập tập ký tự cần lấy
//		request.setCharacterEncoding("utf-8");
//		// lay id
//		
//		System.out.println(request.getParameter("Job_name"));
//		int id = jsoft.library.Utilities.getIntParam(request, "idForPost");
//		short page = jsoft.library.Utilities.getShortParam(request, "page");
//		
//     
//		if (id > 0) {
//			// lấy thông tin
//			String JobName = request.getParameter("Job_name");
//			String email = request.getParameter("Job_email");
//			// String location = request.getParameter("locationData");
//			String Job_homephone = request.getParameter("Job_homephone");
//			String Job_officephone = request.getParameter("Job_officephone");
//			String Job_mobilephone = request.getParameter("Job_mobilephone");
//			String website = request.getParameter("Job_website");
//			String sumary = request.getParameter("Job_summary");
//			String about = request.getParameter("Job_about");
//			String remuneration = request.getParameter("Job_remuneration");
//			String logo = "";
//			Part filePart = request.getPart("Job_logo");
//			String filename = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//			InputStream io = filePart.getInputStream();
//			String path = getServletContext().getRealPath("/") + "adimgs" + File.separator + filename;
//
//			if (jsoft.library.Utilities.saveFile(io, path)) {
//				logo = "/adv/adimgs/" + filename;
//			}
//			String banner = "";
//			Part filePart2 = request.getPart("Job_banner");
//			String filename2 = Paths.get(filePart2.getSubmittedFileName()).getFileName().toString();
//			InputStream io2 = filePart2.getInputStream();
//			String path2 = getServletContext().getRealPath("/") + "adimgs" + File.separator + filename2;
//
//			if (jsoft.library.Utilities.saveFile(io2, path2)) {
//				banner = "/adv/adimgs/" + filename2;
//			}
//			String location = request.getParameter("locationData");
//			byte Ot = jsoft.library.Utilities.getByteParam(request, "chkOT");
//			boolean isOt = (Ot == 1) ? true : false;
//			byte nationality = jsoft.library.Utilities.getByteParam(request, "Job_nationality");
//			byte JobSize = jsoft.library.Utilities.getByteParam(request, "Job_size");
//			byte field = jsoft.library.Utilities.getByteParam(request, "Job_field");
//
//			if (checkValidString(JobName) && checkValidString(email) && checkValidString(Job_officephone)
//					&& checkValidString(sumary) && checkValidString(about) && checkValidString(remuneration)
//					&& nationality >= 0 && JobSize >= 0 && field > 0) {
//				// Tạo đối tượng UserObject
//				JobObject Job = new JobObject();
//				Job.setJob_id(id);
//				Job.setJob_name(jsoft.library.Utilities.encode(JobName));
//				Job.setJob_email(email);
//				Job.setJob_homephone(Job_homephone);
//				Job.setJob_officephone(Job_officephone);
//				Job.setJob_mobilephone(Job_mobilephone);
//				Job.setJob_logo(logo);
//				Job.setJob_location(jsoft.library.Utilities.encode(location));
//				Job.setJob_banner(banner);
//				Job.setJob_website(website);
//				Job.setJob_summary(jsoft.library.Utilities.encode(sumary));
//				Job.setJob_about(jsoft.library.Utilities.encode(about));
//				Job.setJob_remuneration(jsoft.library.Utilities.encode(remuneration));
//				Job.setJob_isOT(isOt);
//				Job.setJob_field_id(field);
//				Job.setJob_nationality(nationality);
//				Job.setJob_size(JobSize);
//				Job.setJob_last_modified(jsoft.library.Utilities_date.getDate());
////							
//
//				// connect db
//				ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
//				JobControl cc = new JobControl(cp);
//				if (cp == null) {
//					getServletContext().setAttribute("CPool", cc.getCP());
//				}
//
//				// thuc hien chỉnh sủa
//				boolean result = cc.editJob(Job, JOB_EDIT_TYPE.GENERAL);
//
//				// tra ve ket noi
//				cc.releaseConnection();
//
//				//
//				if (result) {
//					response.sendRedirect("/adv/job/list?page=" + page);
//				} else {
//					response.sendRedirect("/adv/job/list?err=edit&page=" + page);
//				}
//
//			} else {
//				response.sendRedirect("/adv/job/list?err=valueeadd&page=" + page);
//			}
//		} else {
//			response.sendRedirect("/adv/job/list?err=profiles&page=" + page);
//		}
//	}
//
//}

package jsoft.ads.section;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class SectionEdit
 */
@WebServlet("/section/edit")
public class SectionEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SectionEdit() {
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
		short id = jsoft.library.Utilities.getShortParam(request, "id");
		short page = jsoft.library.Utilities.getShortParam(request, "page");
		String view =request.getParameter("view");
		boolean isView = (view!=null)?true:false;
		
		SectionObject editSection = null;
		ArrayList<UserObject> users = new ArrayList<>();
		HashMap<Integer, String> manager_name = new HashMap<>();
		HashMap<Integer, String> author_name = new HashMap<>();
		
		if (id > 0) {
			// tìm bộ quản lý kết nối
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			// tạo đối tượng thực thi chức năng
			SectionControl uc = new SectionControl(cp);
			if (cp == null) {
				getServletContext().setAttribute("CPool", cp);
			}
			Quartet<SectionObject, HashMap<Integer, String>,HashMap<Integer, String>,ArrayList<UserObject>> getSectionObject = uc.getSectionObject(id, user);
			editSection = getSectionObject.getValue0();
			manager_name = getSectionObject.getValue1();
			author_name = getSectionObject.getValue2();
			users = getSectionObject.getValue3();

			// trả về kết nối
			uc.releaseConnection();
		}

		// tham chiếu tìm header
		RequestDispatcher h = request.getRequestDispatcher("/header?pos=sclist");
		if (h != null) {
			h.include(request, response);
		}

		out.append("<main id=\"main\" class=\"main\">");

		RequestDispatcher error = request.getRequestDispatcher("/error");
		if (error != null) {
			error.include(request, response);
		}

		out.append("<div class=\"pagetitle d-flex\">");
		out.append("<h1>Hồ sơ chuyên mục</h1>");
		out.append("<nav class=\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/adv/view\"><i class=\"bi bi-house\"></i></a></li>");
		out.append("<li class=\"breadcrumb-item\">Chuyên mục</li>");
		out.append("<li class=\"breadcrumb-item active\">Cập nhật chi tiết</li>");
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");

		out.append("<section class=\"section profile\">");
		out.append("<div class=\"row\">");

		out.append("<div class=\"col-xl-12\">");

		out.append("<div class=\"card\">");
		out.append("<div class=\"card-body pt-3\">");
		out.append("<!-- Bordered Tabs -->");
		out.append("<ul class=\"nav nav-tabs nav-tabs-bordered\">");

		out.append("<li class=\"nav-item\">");
		out.append(
				"<button class=\"nav-link active\" data-bs-toggle=\"tab\" data-bs-target=\"#overview\"><i class=\"fas fa-info-circle me-1\"></i>Tổng quát</button>");
		out.append("</li>");
		if(!isView) {
		out.append("<li class=\"nav-item\">");
		out.append(
				"<button class=\"nav-link\" data-bs-toggle=\"tab\" data-bs-target=\"#edit\"><i class=\"fas fa-pen-square me-1\"></i>Chỉnh sửa</button>");
		out.append("</li>");
		}
		out.append("</ul>");
		out.append("<div class=\"tab-content pt-2\">");

		// tab tong quat
		String sectionName = "", setionNotes = "", sectionCreatedDate = "", sectionLastmodify = "", userManager = "", userAuthor = "",sectionStatus="";
		boolean isEdit = false;
		if (editSection != null) {
			sectionName = editSection.getSection_name() != null ? editSection.getSection_name() : "";
			setionNotes = editSection.getSection_notes() != null ? editSection.getSection_notes() : "";
			sectionCreatedDate = editSection.getSection_created_date() != null ? editSection.getSection_created_date() : "";
			sectionLastmodify = editSection.getSection_last_modified() != null ? editSection.getSection_last_modified() : "";
			userManager = manager_name.get(editSection.getSection_manager_id())!= null ? manager_name.get(editSection.getSection_manager_id()) : "";
			userAuthor = author_name.get(editSection.getSection_created_author_id()) != null ? author_name.get(editSection.getSection_created_author_id()) : "";
			sectionStatus = editSection.isSection_delete() ? "Đã xóa !!! Sao lưu trong thùng rác" : "";
			isEdit = true;
		}

		out.append("<div class=\"tab-pane fade show active profile-overview\" id=\"overview\">");

		out.append("<div class=\"row mt-3\">");
		out.append("<div class=\"col-lg-3 col-md-4 label \">Tên chuyên mục</div>");
		out.append("<div class=\"col-lg-6 col-md-5\">" + sectionName + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Ghi chú</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + setionNotes + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Ngày tạo</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + sectionCreatedDate + "</div>");
		out.append("</div>");
		
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Ngày cập nhật</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + sectionLastmodify + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Người quản lý</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + userManager + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Tình trạng</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + sectionStatus + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Người tạo</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + userAuthor + "</div>");
		out.append("</div>");

		out.append("</div>");

		// tab chỉnh sửa
		out.append("<div class=\"tab-pane fade profile-edit pt-3\" id=\"edit\">");

		out.append("<!-- Profile Edit Form -->");
		out.append("<form method=\"POST\">");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"sectionName\" class=\"col-md-3 col-lg-2 col-form-label\">Tên chuyên mục</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<div class=\"input-group\">");
		out.append("<input name=\"txtSectionName\" type=\"text\" class=\"form-control\" id=\"sectionName\" value=\""
				+ sectionName + "\">");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"sectionNotes\" class=\"col-md-3 col-lg-2 col-form-label\">Ghi chú</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<textarea name=\"txtSectionNotes\" class=\"form-control\" id=\"sectionNotes\" style=\"height: 100px\">" + setionNotes
				+ "</textarea>");
		 out.append("<script>");
		  out.append("var editor = CKEDITOR.replace(\"sectionNotes\");");
		  out.append("CKFinder.setupCKEditor(editor,\"/adv/ckfinder/\")");
		  out.append("</script>");
		out.append("</div>");
		out.append("</div>");

		
        int idManager  = (editSection!=null)?editSection.getSection_manager_id():1;
        
		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"slcManager\" class=\"col-md-3 col-lg-2 col-form-label\">Quyền thực thi</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<select class=\"form-select\" id=\"slcManager\" name=\"slcManager\" required>");
		users.forEach(item->{
			if(item.getUser_id()== idManager) {
				out.append("<option value=\""+item.getUser_id()+"\" selected>");
			} else {
				out.append("<option value=\""+item.getUser_id()+"\">");	
			}
			out.append(item.getUser_fullname()).append(" (").append(item.getUser_name()).append(")");
			out.append("</option>");
		});
		out.append("</select>");
		out.append("</div>");
		out.append("</div>");

		// truyen id theo co che bien trong an de thuc hien edit
		if (id > 0 && isEdit && page>0) {
			out.append("<input type=\"hidden\" name=\"idForPost\" value=\"" + id + "\">");
			out.append("<input type=\"hidden\" name=\"page\" value=\"" + page + "\">");
			out.append("<input type=\"hidden\" name=\"act\" value=\"edit\">");
		}
		out.append("<div class=\"text-center\">");
		out.append(
				"<button type=\"submit\" class=\"btn btn-primary\"><i class=\"far fa-save me-2\"></i>Lưu thay đổi</button>");
		out.append("</div>");

		out.append("</form><!-- End Profile Edit Form -->");
		out.append("</div>");// end edit

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
		// TODO Auto-generated method stub
				UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

				// thiết lập tập ký tự cần lấy
				request.setCharacterEncoding("utf-8");

				// lay id

				short id = jsoft.library.Utilities.getShortParam(request, "idForPost");
				short page = jsoft.library.Utilities.getShortParam(request, "page");
				String action = request.getParameter("act");

				if (id > 0) {
					if (action != null && action.equalsIgnoreCase("edit")) {
						// lấy thông tin
						String section_name = request.getParameter("txtSectionName");
						String section_notes = request.getParameter("txtSectionNotes");
						int manager = jsoft.library.Utilities.getByteParam(request, "slcManager");

						if (section_name != null && !section_name.equalsIgnoreCase("") && section_notes != null && !section_notes.equalsIgnoreCase("") && manager>0 ) {
							// Tạo đối tượng UserObject
							SectionObject eSection = new SectionObject();
							eSection.setSection_id(id);
							eSection.setSection_name(section_name);
							eSection.setSection_notes(section_notes);
							eSection.setSection_manager_id(manager);
							eSection.setSection_last_modified(jsoft.library.Utilities_date.getDate());
							
							// connect db
							ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
							SectionControl uc = new SectionControl(cp);
							if (cp == null) {
								getServletContext().setAttribute("CPool", uc.getCP());
							}

							// thuc hien chỉnh sủa
							boolean result = uc.editSection(eSection, SECTION_EDIT_TYPE.GENERAL);

							// tra ve ket noi
							uc.releaseConnection();

							//
							if (result) {
								response.sendRedirect("/adv/section/list?page="+page);
							} else {
								response.sendRedirect("/adv/section/list?err=edit&page="+page);
							}

						} else {
							response.sendRedirect("/adv/section/list?err=valueeadd&page="+page);
						}
					} 
				} else {
					response.sendRedirect("/adv/section/list?err=profiles&page="+page);
				}
	}

}

package jsoft.ads.category;

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
import org.javatuples.Quintet;

import jsoft.ConnectionPool;
import jsoft.objects.CategoryObject;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class SectionEdit
 */
@WebServlet("/category/edit")
public class CategoryEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CategoryEdit() {
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
	@SuppressWarnings("unlikely-arg-type")
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
		
		
		CategoryObject editCate = null;
		ArrayList<UserObject> users = new ArrayList<>();
		ArrayList<SectionObject> sections = new ArrayList<>();
		HashMap<Integer, String> manager_name = new HashMap<>();
		HashMap<Integer, String> author_name = new HashMap<>();
		
		
		
		if (id > 0) {
			// tìm bộ quản lý kết nối
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			// tạo đối tượng thực thi chức năng
			CategoryControl cc = new CategoryControl(cp);
			if (cp == null) {
				getServletContext().setAttribute("CPool", cp);
			}
			Quintet<CategoryObject, HashMap<Integer, String>,HashMap<Integer, String>,ArrayList<UserObject>,ArrayList<SectionObject>> getCateObject = cc.getCategoryObject(id, user);
			editCate = getCateObject.getValue0();
			manager_name = getCateObject.getValue1();
			author_name = getCateObject.getValue2();
			users = getCateObject.getValue3();
		    sections = getCateObject.getValue4();

			// trả về kết nối
			cc.releaseConnection();
		}

		// tham chiếu tìm header
		RequestDispatcher h = request.getRequestDispatcher("/header?pos=cclist");
		if (h != null) {
			h.include(request, response);
		}

		out.append("<main id=\"main\" class=\"main\">");

		RequestDispatcher error = request.getRequestDispatcher("/error");
		if (error != null) {
			error.include(request, response);
		}

		out.append("<div class=\"pagetitle d-flex\">");
		out.append("<h1>Hồ sơ thể loại</h1>");
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
		String categoryName = "", categoryInSection="", categoryNotes = "", categoryCreatedDate = "", categoryLastmodify = "", userManager = "", userAuthor = "",categoryStatus="";
		boolean isEdit = false;
		if (editCate != null) {
			
			categoryName = editCate.getCategory_name() != null ? editCate.getCategory_name() : "";
			categoryNotes = editCate.getCategory_notes() != null ? editCate.getCategory_notes() : "";
			categoryCreatedDate = editCate.getCategory_created_date() != null ? editCate.getCategory_created_date() : "";
			categoryLastmodify = editCate.getCategory_last_modified() != null ? editCate.getCategory_last_modified() : "";
			userManager = manager_name.get(editCate.getCategory_manager_id())!= null ? manager_name.get(editCate.getCategory_manager_id()) : "";
			userAuthor = author_name.get(editCate.getCategory_created_author_id()) != null ? author_name.get(editCate.getCategory_created_author_id()) : "";
			categoryInSection = editCate.getCategory_section_name()!= null ? editCate.getCategory_section_name() : "";
			categoryStatus = editCate.isCategory_delete() ? "Đã xóa !!! Sao lưu trong thùng rác" : "";
			isEdit = true;
		}

		out.append("<div class=\"tab-pane fade show active profile-overview\" id=\"overview\">");

		out.append("<div class=\"row mt-3\">");
		out.append("<div class=\"col-lg-3 col-md-4 label \">Tên thể loại</div>");
		out.append("<div class=\"col-lg-6 col-md-5\">" + categoryName + "</div>");
		out.append("</div>");
		
		out.append("<div class=\"row mt-3\">");
		out.append("<div class=\"col-lg-3 col-md-4 label \">Chuyên mục</div>");
		out.append("<div class=\"col-lg-6 col-md-5\">" + categoryInSection + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Ghi chú</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + categoryNotes + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Ngày tạo</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + categoryCreatedDate + "</div>");
		out.append("</div>");
		
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Ngày cập nhật</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + categoryLastmodify + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Người quản lý</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + userManager + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Tình trạng</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + categoryStatus + "</div>");
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
		out.append("<label for=\"categoryName\" class=\"col-md-3 col-lg-2 col-form-label\">Tên thể loại</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<div class=\"input-group\">");
		out.append("<input name=\"txtcategoryName\" type=\"text\" class=\"form-control\" id=\"categoryName\" value=\""
				+ categoryName + "\">");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"sectionNotes\" class=\"col-md-3 col-lg-2 col-form-label\">Ghi chú</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<textarea name=\"txtCategoryNotes\" class=\"form-control\" id=\"categoryNotes\" style=\"height: 100px\">" + categoryNotes
				+ "</textarea>");
		  out.append("<script>");
		  out.append("var editor = CKEDITOR.replace(\"categoryNotes\");");
		  out.append("CKFinder.setupCKEditor(editor,\"/adv/ckfinder/\")");
		  out.append("</script>");
		out.append("</div>");
		out.append("</div>");

		 short cateInSectionId  = (editCate!=null)?editCate.getCategory_section_id():1;
		 out.append("<div class=\"row mb-3 align-items-center\">");
			out.append("<label for=\"slcManager\" class=\"col-md-3 col-lg-2 col-form-label\">Chuyên mục</label>");
			out.append("<div class=\"col-md-9 col-lg-10\">");
			out.append("<select class=\"form-select\" id=\"slcSection\" name=\"slcSection\" required>");
			sections.forEach(item->{
				if(item.getSection_id()== cateInSectionId) {
					out.append("<option value=\""+item.getSection_id()+"\" selected>");
				} else {
					out.append("<option value=\""+item.getSection_id()+"\">");	
				}
				out.append(item.getSection_name());
				out.append("</option>");
			});
			out.append("</select>");
			out.append("</div>");
			out.append("</div>");
		 
        int idManager  = (editCate!=null)?editCate.getCategory_manager_id():1;
        
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
						String category_name = request.getParameter("txtcategoryName");
						String category_notes = request.getParameter("txtCategoryNotes");
						int manager = jsoft.library.Utilities.getByteParam(request, "slcManager");
						short section_id = jsoft.library.Utilities.getShortParam(request, "slcSection");
						
						if (category_name != null && !category_name.equalsIgnoreCase("") && category_notes != null && !category_notes.equalsIgnoreCase("") && manager>0 && section_id>0 ) {
							// Tạo đối tượng UserObject
							CategoryObject eCate = new CategoryObject();
							eCate.setCategory_id(id);
							eCate.setCategory_name(category_name);
							eCate.setCategory_notes(category_notes);
							eCate.setCategory_manager_id(manager);
							eCate.setCategory_last_modified(jsoft.library.Utilities_date.getDate());
							eCate.setCategory_section_id(section_id);
							
							// connect db
							ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
							CategoryControl cc = new CategoryControl(cp);
							if (cp == null) {
								getServletContext().setAttribute("CPool", cc.getCP());
							}

							// thuc hien chỉnh sủa
							boolean result = cc.editCategory(eCate, CATEGORY_EDIT_TYPE.GENERAL);

							// tra ve ket noi
							cc.releaseConnection();

							//
							if (result) {
								response.sendRedirect("/adv/category/list?page="+page);
							} else {
								response.sendRedirect("/adv/category/list?err=edit&page="+page);
							}

						} else {
							response.sendRedirect("/adv/category/list?err=valueeadd&page="+page);
						}
					} 
				} else {
					response.sendRedirect("/adv/category/list?err=profiles&page="+page);
				}
	}

}

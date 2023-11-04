package jsoft.ads.section;

import java.io.File;
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

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class SectionList
 */
@WebServlet("/section/list")
public class SectionList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SectionList() {
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
		
		// tìm thông tin đăng nhập
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");
        
		if (user != null) {
			view(request, response, user,page);
		} else {
			response.sendRedirect("/adv/user/login");
		}

	}

	protected void view(HttpServletRequest request, HttpServletResponse response, UserObject user,int page)
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
		
		// lay tu khoa tim kiem
				String key = request.getParameter("key");
				String saveKey = (key!=null && !key.equalsIgnoreCase(""))?key.trim():"";
		        saveKey = jsoft.library.Utilities.encode(saveKey);
		// tạo đối tượng thực thi chức năng
		SectionControl sc = new SectionControl(cp);

		// lấy cấu trúc
		SectionObject similar = new SectionObject();
		similar.setSection_delete(false);
		similar.setSection_name(saveKey);

		// tim thanh so xac dinh loại danh sách
		String trash =request.getParameter("trash");
		boolean isTrash = (trash!=null)?true:false;
		String title;
		if(trash==null) {
	        similar.setSection_delete(false);
	   		title="Danh sách chuyên mục";
		} else {
			title ="Danh sách chuyên mục bị xóa";
			similar.setSection_delete(true);
		}
		
		byte pageSize = 4;
		Quartet<SectionObject, Integer, Byte,UserObject> infos = new Quartet<>(similar,pageSize*(page-1),pageSize,user);
		
		
		ArrayList<String> viewList = sc.viewSection(infos, new Pair<>(SECTION_SOFT.NAME, ORDER.ASC),page,saveKey,isTrash);
		
//         Short total = viewList.get(0);
//		
//		int totalPage = total < pageSize ? 1 : (int) Math.ceil((double) total / pageSize);
//		
//		if(total%pageSize==0) {
//			totalPage = totalPage-1;
//		}
		// trả về kết nối
		sc.releaseConnection();

		
		String pos = (trash==null)?"/header?pos=sclist":"/header?pos=sctrash";
		
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

		out.append("<div class=\"pagetitle d-flex\">");
		out.append("<h1>"+title+"</h1>");
		out.append("<nav class=\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/adv/view\"><i class=\"bi bi-house\"></i></a></li>");
		out.append("<li class=\"breadcrumb-item\">Chuyên mục</li>");
		out.append("<li class=\"breadcrumb-item active\">Danh sách</li>");
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
			
		out.append("<div class=\"d-flex justify-content-between\">");
		out.append("<button type=\"button\" class=\"btn btn-primary my-4 \" data-bs-toggle=\"modal\" data-bs-target=\"#staticBackdrop\"><i class=\"fa-solid fa-plus me-2\"></i>Thêm mới</button>");
		out.append("<a href=\"/adv/section/list?trash\" class=\"btn btn-danger my-4 \" ><i class=\"fas fa-trash-restore\"></i>Thùng rác</a>");
		out.append("</div><!-- End div -->");
		
		out.append("<form method=\"POST\" >");
		out.append("<div class=\"modal fade modal-lg\" id=\"staticBackdrop\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">");
		out.append("<div class=\"modal-dialog\">");
		out.append("<div class=\"modal-content\">");
		out.append("<div class=\"modal-header\">");
		out.append("<h1 class=\"modal-title fs-5\" id=\"staticBackdropLabel\">Thêm chuyên mục</h1>");
		out.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
		out.append("</div>");

		out.append("<div class=\"modal-body\">");
		out.append("<div class=\"container\">");

		out.append("<div class=\"row mb-3\">");
		out.append("<div class=\"col\">");
		out.append("<label for=\"sectionName\" class=\"form-label\"><i class=\"fas fa-user me-2\"></i>Tên chuyên mục</label>");
		out.append("<input type=\"text\" class=\"form-control\" placeholder=\"...\" name=\"txtsectionname\" id=\"userName\" required>");
		out.append("</div>");
		out.append("<div class=\"col\">");
		out.append("<label for=\"slcManager\" class=\"form-label\"><i class=\"fas fa-user-tag\"></i>Quyền thực thi</label>");
		out.append("<select class=\"form-select\" id=\"slcManager\" name=\"slcManager\" required>");
		out.append(viewList.get(1));
		out.append("</select>");
		out.append("</div>");
		out.append("</div>");// end row

		out.append("<div class=\"row mb-2\">");
		out.append("<div class=\"col\">");
		out.append("<label for=\"sectionNotes\" class=\"form-label\"><i class=\"fas fa-user me-2\"></i>Ghi chú</label>");
		out.append("<textarea row=\"8\" class=\"form-control\" placeholder=\"\" name=\"txtsectionnotes\" id=\"userName\" required></textarea>");
		out.append("</div>");
		out.append("</div>");// end row
		
		out.append("</div>"); // end container
		out.append("</div>"); // end model body
		
		out.append("<div class=\"modal-footer justify-content-center \">");
		out.append("<button type=\"button\" class=\"btn btn-secondary px-5 text-uppercase\" data-bs-dismiss=\"modal\"><i class=\"fas fa-window-close pe-1\"></i>đóng</button>");
		out.append("<button type=\"submit\" class=\"btn btn-primary px-5 text-uppercase \" ><i class=\"fas fa-plus pe-1\"></i>tạo</button>");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");
		out.append("</form>");
		}
		// list section
		out.append(viewList.get(0));
		// phan trang
		out.append(viewList.get(2));
		
		out.append("</div>"); // end card-body
		out.append("</div>"); // end card

		out.append("</div>"); // col-lg-12
		out.append("</div>"); // row
		out.append("</section>");

		// charts
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-12\">");
		out.append(viewList.get(3));
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
		
		String sectionName = request.getParameter("txtsectionname");
		String notes = request.getParameter("txtsectionnotes");

		byte manager = jsoft.library.Utilities.getByteParam(request, "slcManager");

		if (sectionName != null && !sectionName.equalsIgnoreCase("") 
				&& notes != null && !notes.equalsIgnoreCase("") && manager > 0) {

			UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

			SectionObject newSection = new SectionObject();
			newSection.setSection_name(jsoft.library.Utilities.encode(sectionName));
			newSection.setSection_notes(jsoft.library.Utilities.encode(notes));
			newSection.setSection_created_date(jsoft.library.Utilities_date.getDate());
			newSection.setSection_manager_id(manager);
			newSection.setSection_enable(true);
			newSection.setSection_last_modified(jsoft.library.Utilities_date.getDate());
			newSection.setSection_created_author_id(user.getUser_id());
			
			
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			SectionControl uc = new SectionControl(cp);
			// add new user
			if (cp != null) {
				getServletContext().setAttribute("CPool", uc.getCP());
			}
			boolean result = uc.addSection(newSection);
			// return connect
			uc.releaseConnection();
			if (result) {
				response.sendRedirect("/adv/section/list");
			} else {
				response.sendRedirect("/adv/section/list?err=add");
			}
		} else {
			String key = request.getParameter("keyword");
			String encodedKeyword = URLEncoder.encode(key, "UTF-8");
			String key_url = (key!=null && !key.equalsIgnoreCase(""))?("&key="+encodedKeyword):"";
			response.sendRedirect("/adv/section/list?page=1"+key_url);
		}
	}


}

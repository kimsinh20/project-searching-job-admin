package jsoft.ads.article;

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
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.ads.section.SECTION_SOFT;
import jsoft.ads.section.SectionControl;
import jsoft.library.ORDER;
import jsoft.objects.ArticleObject;
import jsoft.objects.CategoryObject;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class CategoryList
 */
@WebServlet("/ar/list")
public class ArticleList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArticleList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
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
		ArticleControl ac = new ArticleControl(cp);

		// lấy cấu trúc
		ArticleObject similar = new ArticleObject();
		similar.setArticle_delete(false);
		similar.setArticle_title(saveKey);

		// tim thanh so xac dinh loại danh sách
		String trash =request.getParameter("trash");
		boolean isTrash = (trash!=null)?true:false;
		String title;
		if(!isTrash) {
	        similar.setArticle_delete(false);
	   		title="Danh sách bài viết";
		} else {
			title ="Danh sách bài viết bị xóa";
			similar.setArticle_delete(true);
		}
		
		byte pageSize = 5;
		
		Quartet<ArticleObject, Integer, Byte,UserObject> infos = new Quartet<>(similar,pageSize*(page-1),pageSize,user);
		
		
		ArrayList<String> viewList = ac.viewArticle(infos, new Pair<>(ARTICLE_SOFT.GENERAL, ORDER.ASC),page,saveKey,isTrash);
		
		// trả về kết nối
		ac.releaseConnection();

		
		String pos = (trash==null)?"/header?pos=arlist":"/header?pos=artrash";
		
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
		out.append("<li class=\"breadcrumb-item\">Bài viết</li>");
		out.append("<li class=\"breadcrumb-item active\">"+(isTrash?"Thùng rác":"Danh sách")+"</li>");
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");

		
		// list article
		out.append("<section class=\"section\">");
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-12\">");
		out.append("<div class=\"card\">");
		out.append("<div class=\"card-body\">");
        
		if(trash==null) {
		// add user modal
			
		out.append("<div class=\"d-flex justify-content-between\">");
		out.append("<a href=\"/adv/ar/upd\" class=\"btn btn-primary my-4 \"><i class=\"fa-solid fa-plus me-2\"></i>Thêm mới</a>");
		out.append("</div><!-- End div -->");
		}
		// list article
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
		
		String categoryName = request.getParameter("txtcategoryname");
		String notes = request.getParameter("txtcategorynotes");

		byte manager = jsoft.library.Utilities.getByteParam(request, "slcManager");
		byte section_id = jsoft.library.Utilities.getByteParam(request, "slcSection");

		if (categoryName != null && !categoryName.equalsIgnoreCase("") 
				&& notes != null && !notes.equalsIgnoreCase("") && manager > 0 && section_id>0) {

			UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

			SectionObject newSection = new SectionObject();
			CategoryObject newCate  = new CategoryObject();
			newCate.setCategory_name(jsoft.library.Utilities.encode(categoryName));;
			newCate.setCategory_notes(jsoft.library.Utilities.encode(notes));
			newCate.setCategory_created_date(jsoft.library.Utilities_date.getDate());
			newCate.setCategory_manager_id(manager);
			newCate.setCategory_enable(true);
			newCate.setCategory_last_modified(jsoft.library.Utilities_date.getDate());
			newCate.setCategory_created_author_id(user.getUser_id());
			newCate.setCategory_section_id(section_id);
			
			
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			ArticleControl cc = new ArticleControl(cp);
			// add new user
			if (cc != null) {
				getServletContext().setAttribute("CPool", cc.getCP());
			}
//			boolean result = cc.addCategory(newCate);
			// return connect
			cc.releaseConnection();
//			if (result) {
//				response.sendRedirect("/adv/category/list");
//			} else {
//				response.sendRedirect("/adv/category/list?err=add");
//			}
		} else {
			String key = request.getParameter("keyword");
			String encodedKeyword = URLEncoder.encode(key, "UTF-8");
			String key_url = (key!=null && !key.equalsIgnoreCase(""))?("&key="+encodedKeyword):"";
			response.sendRedirect("/adv/ar/list?page=1"+key_url);
		}
	}

}

package jsoft.ads.article;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.ads.category.CATEGORY_SOFT;
import jsoft.ads.category.CategoryControl;
import jsoft.library.ORDER;
import jsoft.objects.ArticleObject;
import jsoft.objects.CategoryObject;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class ArticleAdd
 */
@WebServlet("/ar/upd")
@MultipartConfig(
		fileSizeThreshold = 1024*1024*2,
		maxFileSize = 1024*1024*10,
		maxRequestSize = 1024*1024*11
		)
public class ArticleAdd extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ArticleAdd() {
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
		String saveKey = (key != null && !key.equalsIgnoreCase("")) ? key.trim() : "";
		saveKey = jsoft.library.Utilities.encode(saveKey);
		// tạo đối tượng thực thi chức năng
		ArticleControl ac = new ArticleControl(cp);

		// lấy cấu trúc
		CategoryObject similar = new CategoryObject();
		similar.setCategory_delete(false);

		Quartet<CategoryObject, Integer, Byte, UserObject> infos = new Quartet<>(similar, 0, (byte) 100, user);

		ArrayList<CategoryObject> categories = ac.getCategorys(infos, new Pair<>(CATEGORY_SOFT.GENERAL, ORDER.ASC));

		// trả về kết nối
		ac.releaseConnection();

		// tham chiếu tìm header
		RequestDispatcher h = request.getRequestDispatcher("/header?pos=arupd");
		if (h != null) {
			h.include(request, response);
		}
		out.append("<main id=\"main\" class=\"main\">");

		RequestDispatcher error = request.getRequestDispatcher("/error");
		if (error != null) {
			error.include(request, response);
		}

		out.append("<div class=\"pagetitle d-flex\">");
		out.append("<h1>Thêm bài viết & Tin tức </h1>");
		out.append("<nav class=\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/adv/view\"><i class=\"bi bi-house\"></i></a></li>");
		out.append("<li class=\"breadcrumb-item\">Bài viết</li>");
		out.append("<li class=\"breadcrumb-item active\">Thêm bài viết</li>");
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");
		// main
		out.append("<section class=\"section\">");
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-12\">");
		out.append("<div class=\"card\">");
		out.append("<div class=\"card-body\">");

		out.append("<div class=\"tab-pane profile-edit pt-3\" id=\"edit\">");

		out.append("<!-- Profile Edit Form -->");
		out.append("<form method=\"POST\" enctype=\"multipart/form-data\">");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"articletitle\" class=\"col-md-3 col-lg-2 col-form-label\">Tiêu đề</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<div class=\"input-group\">");
		out.append("<input name=\"txtarticletitle\" type=\"text\" class=\"form-control\" id=\"articletitle\">");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"articlesumary\" class=\"col-md-3 col-lg-2 col-form-label\">Tóm tắt</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<div class=\"input-group\">");
		out.append(
				"<textarea name=\"txtarticlesumary\" class=\"form-control\" id=\"articlesumary\" style=\"height: 100px\"></textarea>");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"article_image\" class=\"col-md-3 col-lg-2 col-form-label\">Ảnh bài viết</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<div class=\"d-flex align-items-center\">");
		out.append("<input class=\"me-3\" type=\"file\" class=\"form-control\" name=\"article_image\" id=\"article_image\" onchange=\"document.getElementById('blah').src = window.URL.createObjectURL(this.files[0])\">");
		out.append("<img  width=\"400\" id=\"blah\" alt=\"\">");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"slcCategory\" class=\"col-md-3 col-lg-2 col-form-label\">Thể loại</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<select class=\"form-select\" id=\"slcCategory\" name=\"slcCategory\" required>");
		categories.forEach(item -> {
			out.append("<option value=\"" + item.getCategory_id() + ":" + item.getCategory_section_id() + "\">");
			out.append(item.getCategory_name());
			out.append("</option>");
		});
		out.append("</select>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"articlecontent\" class=\"col-md-3 col-lg-2 col-form-label\">Nội dung</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<textarea name=\"txtarticlecontent\" class=\"form-control\" id=\"articlecontent\" style=\"height: 100px\"></textarea>");
		out.append("<script>");
		out.append("var editor = CKEDITOR.replace(\"articlecontent\");");
		out.append("CKFinder.setupCKEditor(editor,\"/adv/ckfinder/\")");
		out.append("</script>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"articlesource\" class=\"col-md-3 col-lg-2 col-form-label\">Nguồn</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<div class=\"input-group\">");
		out.append("<input name=\"txtarticlesource\" type=\"text\" class=\"form-control\" id=\"articlesource\">");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");
		
		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"txtarticletag\" class=\"col-md-3 col-lg-2 col-form-label\">Liên kết</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<div class=\"input-group\">");
		out.append("<input type=\"text\" id=\"todo-input\">");
		out.append("<button type=\"button\" id=\"add-todo\"><i class=\"fas fa-plus\"></i></button>");
		out.append("<input name=\"txtarticletag\" type=\"text\" class=\"form-control\" id=\"tags\" hidden>");
		out.append("</div>");
		out.append("<ul id=\"todo-list\"></ul>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"text-center\">");
		out.append(
				"<button type=\"submit\" class=\"btn btn-primary\"><i class=\"far fa-add me-2\"></i>Thêm bài viết</button>");
		out.append("</div>");

		out.append("</form><!-- End Profile Edit Form -->");
		out.append("</div>");// end add

		out.append("</div>"); // end card-body
		out.append("</div>"); // end card

		out.append("</div>"); // col-lg-12
		out.append("</div>"); // row
		out.append("</section>");

		out.append("</main><!-- End #main -->");

		// tham chiếu tìm sidebar
		RequestDispatcher f = request.getRequestDispatcher("/footer");
		if (f != null) {
			f.include(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");
		
        request.setCharacterEncoding("UTF-8");
		
		String articletitle = request.getParameter("txtarticletitle");
		String articlesummary = request.getParameter("txtarticlesumary");
		String articlecontent = request.getParameter("txtarticlecontent");
		
		// Save the file to the server's file system.
		String article_image ="";
		Part filePart = request.getPart("article_image");
		String filename = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		InputStream io = filePart.getInputStream();
		  String path  = getServletContext().getRealPath("/")+"adimgs" + File.separator + filename;
		  
        if(jsoft.library.Utilities.saveFile(io, path)) {
        	article_image = "/adv/adimgs/"+filename;
        } 
		
		String articlesoure = request.getParameter("txtarticlesource");
		String articletag = request.getParameter("txtarticletag");

		String categoryObj[] =request.getParameter("slcCategory").split(":");
		byte categoryid = Byte.parseByte(categoryObj[0]);
		byte sectionid = Byte.parseByte(categoryObj[1]);

		if (articletitle != null && !articletitle.equalsIgnoreCase("") 
				&& articlecontent != null && !articlecontent.equalsIgnoreCase("") && categoryid > 0 && sectionid>0) {

			ArticleObject newar  = new ArticleObject();
			newar.setArticle_title(jsoft.library.Utilities.encode(articletitle));;
			newar.setArticle_summary(jsoft.library.Utilities.encode(articlesummary));
			newar.setArticle_content(jsoft.library.Utilities.encode(articlecontent));
			newar.setArticle_created_date(jsoft.library.Utilities_date.getDate());
			newar.setArticle_category_id(categoryid);
			newar.setArticle_image(article_image);
			newar.setArticle_enable(true);
			newar.setArticle_delete(false);
			newar.setArticle_tag(jsoft.library.Utilities.encode(articletag));
			newar.setArticle_fee(0);
			newar.setArticle_isfee(false);
			newar.setArticle_section_id(sectionid);
			newar.setArticle_modified_author_name(user.getUser_name());
			newar.setArticle_author_permission(user.getUser_permission());
			newar.setArticle_language((byte)0);
			newar.setArticle_focus(false);
			newar.setArticle_type((byte)0);
			newar.setArticle_forhome(false);
			newar.setArticle_last_modified(jsoft.library.Utilities_date.getDate());
			newar.setArticle_author_name(user.getUser_name());
			newar.setArticle_source(jsoft.library.Utilities.encode(articlesoure));
			
			
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			ArticleControl ac = new ArticleControl(cp);
			// add new user
			if (ac != null) {
				getServletContext().setAttribute("CPool", ac.getCP());
			}
			boolean result = ac.addArticle(newar);
			// return connect
			ac.releaseConnection();
			if (result) {
				response.sendRedirect("/adv/ar/list");
			} else {
				response.sendRedirect("/adv/ar/list?err=add");
			}
		} else {
			response.sendRedirect("/adv/ar/list?err=valueadd");
		}
	}

}

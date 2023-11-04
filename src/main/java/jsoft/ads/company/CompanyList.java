package jsoft.ads.company;

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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import com.google.gson.JsonObject;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.CompanyObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class CategoryList
 */
@WebServlet("/company/list")
@MultipartConfig(
		fileSizeThreshold = 1024*1024*2,
		maxFileSize = 1024*1024*10,
		maxRequestSize = 1024*1024*11
	)
public class CompanyList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyList() {
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
		CompanyControl cc = new CompanyControl(cp);

		// lấy cấu trúc
		CompanyObject similar = new CompanyObject();
		similar.setCompany_delete(false);
		similar.setCompany_name(saveKey);

		// tim thanh so xac dinh loại danh sách
		String trash =request.getParameter("trash");
		boolean isTrash = (trash!=null)?true:false;
		String title;
		if(!isTrash) {
	        similar.setCompany_delete(false);
	   		title="Danh sách công ty";
		} else {
			title ="Danh sách công ty bị xóa";
			similar.setCompany_delete(true);
		}
		
		byte pageSize = 5;
		
		Quartet<CompanyObject, Integer, Byte,UserObject> infos = new Quartet<>(similar,pageSize*(page-1),pageSize,user);
		
		
		ArrayList<String> viewList = cc.viewCompany(infos, new Pair<>(COMPANY_SOFT.GENERAL, ORDER.ASC),page,saveKey,isTrash);
		
		// trả về kết nối
		cc.releaseConnection();

		
		String pos = (trash==null)?"/header?pos=cplist":"/header?pos=cptrash";
		
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
		out.append("<li class=\"breadcrumb-item\">công ty</li>");
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
			
		out.append("<div class=\"d-flex justify-content-between\">");
		out.append("<button type=\"button\" class=\"btn btn-primary my-4 \" data-bs-toggle=\"modal\" data-bs-target=\"#staticBackdrop\"><i class=\"fa-solid fa-plus me-2\"></i>Thêm mới</button>");
		out.append("<a href=\"/adv/company/list?trash\" class=\"btn btn-danger my-4 \" ><i class=\"fas fa-trash-restore\"></i>Thùng rác</a>");
		out.append("</div><!-- End div -->");
		
		out.append("<form method=\"POST\" id=\"form_add\" enctype=\"multipart/form-data\">");
		out.append("<div class=\"modal fade modal-lg\" id=\"staticBackdrop\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">");
		out.append("<div class=\"modal-dialog\">");
		out.append("<div class=\"modal-content\">");
		out.append("<div class=\"modal-header\">");
		out.append("<h1 class=\"modal-title fs-5\" id=\"staticBackdropLabel\">Thêm công ty</h1>");
		out.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
		out.append("</div>");

		out.append("<div class=\"modal-body\">");
		out.append("<div class=\"container\">");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-12\">");

		out.append("<!-- Bordered Tabs Justified -->");
		out.append("<ul class=\"nav nav-tabs nav-tabs-bordered d-flex\" id=\"borderedTabJustified\" role=\"tablist\">");
		out.append("<li class=\"nav-item flex-fill\" role=\"presentation\">");
		out.append("<button class=\"nav-link w-100 active\" id=\"home-tab\" data-bs-toggle=\"tab\"");
		out.append("data-bs-target=\"#bordered-justified-home\" type=\"button\" role=\"tab\"");
		out.append("aria-controls=\"home\" aria-selected=\"true\">Thông tin cơ bản</button>");
		out.append("</li>");
		out.append("<li class=\"nav-item flex-fill\" role=\"presentation\">");
		out.append("<button class=\"nav-link w-100\" id=\"profile-tab\" data-bs-toggle=\"tab\"");
		out.append("data-bs-target=\"#bordered-justified-profile\" type=\"button\" role=\"tab\"");
		out.append("aria-controls=\"profile\" aria-selected=\"false\">Địa chỉ</button>");
		out.append("</li>");
		out.append("<li class=\"nav-item flex-fill\" role=\"presentation\">");
		out.append("<button class=\"nav-link w-100\" id=\"contact-tab\" data-bs-toggle=\"tab\"");
		out.append("data-bs-target=\"#bordered-justified-contact\" type=\"button\" role=\"tab\"");
		out.append("aria-controls=\"contact\" aria-selected=\"false\">Thông tin khác</button>");
		out.append("</li>");
		out.append("</ul>");
		out.append("<div class=\"tab-content pt-2\" id=\"borderedTabJustifiedContent\">");
		out.append("<div class=\"tab-pane fade show active\" id=\"bordered-justified-home\" role=\"tabpanel\"");
		out.append("aria-labelledby=\"home-tab\">");
		
		out.append("<div class=\"row mb-2\">");
		out.append("<div class=\"col-md-6\" id=\"form-control\">");
		out.append("<label for=\"companyname\" class=\"form-label\"><i class=\"fas fa-user me-2\"></i>Tên công ty</label>");
		out.append("<input type=\"text\" class=\"form-control\" placeholder=\"...\" name=\"companyname\" id=\"companyname\" required>");
		 out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
		out.append("</div>");
		out.append("<div class=\"col-md-6\" id=\"form-control\">");
		out.append("<label for=\"nationality\" class=\"form-label\"><i class=\"fas fa-globe-americas me-2\"></i>Quốc gia</label>");
		out.append("<select class=\"form-select\" id=\"nationality\" name=\"slcnationality\" required>");
		out.append("<option value=\"0\">Việt Nam</option>");
		out.append("<option value=\"1\">Trung Quốc</option>");
		out.append("<option value=\"2\">Nhật Bản</option>");
		out.append("<option value=\"3\">Hàn Quốc</option>");
		out.append("<option value=\"4\">Singapo</option>");
		out.append("<option value=\"5\">Mỹ</option>");
		out.append("</select>");
//		 out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
		out.append("</div>");
		out.append("</div>");
		
		out.append("<div class=\"row mb-2 align-items-center\">");
		out.append("<div class=\"lable col-md-3 col-md-3\"><i class=\"fas fa-images me-2\"></i>Logo</div>");
		out.append("<div class=\"col-md-9 col-lg-9 d-flex flex-column \">");
		out.append("<img src=\"\" style=\"border-radius: 45%;\" class=\"\" id=\"blah\" width=\"120\" height=\"100\" alt=\"\">");
		out.append("<div class=\"divs\">");
		out.append("<input name=\"logo\" onchange=\"document.getElementById('blah').src = window.URL.createObjectURL(this.files[0])\" type='file' id='image' class=\"inputs\">");
		out.append("<i class=\"fa fa-image fa-2x icons my-2\"></i>");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");//end row
		
		out.append("<div class=\"row mb-2\">");
		out.append("<div class=\"col\" id=\"form-control\">");
		out.append("<label for=\"email\" class=\"form-label\"><i class=\"fas fa-envelope-open me-2\"></i>Hộp thư</label>");
		out.append("<input type=\"email\" class=\"form-control\" placeholder=\"...\" name=\"txtemail\" id=\"email\" required>");
		 out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
		out.append("</div>");
		out.append("<div class=\"col\" id=\"form-control\">");
		out.append("<label for=\"phoneNumber\" class=\"form-label\"><i class=\"fas fa-phone-square me-2\"></i>Số điện thoại</label>");
		out.append("<input type=\"text\" class=\"form-control\" placeholder=\"...\" name=\"txtphone\" id=\"phoneNumber\" required>");
		 out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-2\">");
		out.append("<div class=\"col\" id=\"form-control\">");
		out.append("<label for=\"website\" class=\"form-label\"><i class=\"bi bi-browser-chrome me-2\"></i>Website</label>");
		out.append("<input type=\"website\" class=\"form-control\" placeholder=\"...\" name=\"txtwebsite\" id=\"website\" required>");
		 out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
		out.append("</div>");
		out.append("<div class=\"col\" id=\"form-control\">");
		out.append("<label for=\"companysize\" class=\"form-label\"><i class=\"bi bi-arrows-angle-contract me-2\"></i>Quy mô</label>");
		out.append("<select class=\"form-select\" id=\"companysize\" name=\"slccompanysize\" required>");
		out.append("<option value=\"0\" selected>Dưới 100 người</option>");
		out.append("<option value=\"1\">100 đến 500 người</option>");
		out.append("<option value=\"2\">500 trên đến 1000 người</option>");
		out.append("<option value=\"3\">Trên 1000 người</option>");
		out.append("<option value=\"4\">Trên 5000 người</option>");
		out.append("<option value=\"5\">Trên 10000 người</option>");
		out.append("</select>");
		// out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
		out.append("</div>");
		out.append("</div>");
		
		out.append("</div>");
		out.append("<div class=\"tab-pane fade\" id=\"bordered-justified-profile\" role=\"tabpanel\"");
		out.append("aria-labelledby=\"profile-tab\">");
		// js for select
	
		out.append("<div class=\"row mb-2\">");
		out.append("<div class=\"col\">");
		out.append("<label for=\"provinces\" class=\"form-label\"><i class=\"fas fa-envelope-open me-2\"></i>Tỉnh/Thành phố</label>");
		out.append("<select class=\"form-select\" id=\"provinces\" onchange='getProvinces(event)' name=\"slcprovinces\" >");
		out.append("<option value=''>-- Chọn Tỉnh/Thành phố --</option>");
		out.append("</select>");
		// out.append("<small>Error</small>");
		out.append("</div>");
		out.append("<div class=\"col\">");
		out.append("<label for=\"districts\" class=\"form-label\"><i class=\"fas fa-envelope-open me-2\"></i>Huyện/Thị xã</label>");
		out.append("<select id='districts' class=\"form-control\" name=\"slcdistricts\" onchange='getDistricts(event)' >");
		out.append("<option value=''>-- Chọn Huyện/Thị xã --</option>");
		out.append("</select>");
		// out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
		out.append("</div>");
		out.append("</div>");
		
		out.append("<div class=\"row mb-2\">");
		out.append("<div class=\"col\" >");
		out.append("<label for=\"wards\" class=\"form-label\"><i class=\"fas fa-file-signature me-2\"></i>Xã/Phường</label>");
		out.append("<select class=\"form-select\" id=\"wards\" name=\"slcwards\" >");
		out.append("<option value=''>-- Chọn Xã/Phường --</option>");
		out.append("</select>");
		// out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
		out.append("</div>");
		out.append("<div class=\"col\">");
		out.append("<label for=\"addressDetail\" class=\"form-label\"><i class=\"fas fa-phone-square me-2\"></i>Địa chỉ cụ thể</label>");
		out.append("<input type=\"text\" class=\"form-control\" placeholder=\"...\" name=\"addressDetail\" id=\"addressDetail\">");
		out.append("<input type=\"hidden\" id=\"locationData\" name=\"locationData\">");
		// out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
		out.append("</div>");
		out.append("</div>");
		
		out.append("<div class=\"row mb-2\">");
		out.append("<div class=\"col d-flex justify-content-center\">");
		out.append("<button class=\"btn px-3 btn-primary text-center fw-3\" type=\"button\" onclick=\"addAddress()\">Thêm</button>");
		out.append("</div>");
		out.append("</div>");
		//map
		out.append("<div class=\"row mb-2\">");
		out.append("<div class=\"col\" id=\"form-control\">");
		out.append("<h2 class=\"title\">Danh sách địa chỉ</h2>");
		out.append("<div id=\"address-list\">");
		out.append("<ul id=\"address-items\"></ul>");
		out.append("</div>");
        out.append("</div>");
        out.append("</div>");
        
		out.append("</div>");// end tab pane
		
		out.append("<div class=\"tab-pane fade\" id=\"bordered-justified-contact\" role=\"tabpanel\"");
		out.append("aria-labelledby=\"contact-tab\">");
		
		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<div class=\"col-md-5 col-lg-6 text-center\" id=\"form-control\">");
		out.append("<label for=\"isOT\" class=\"form-label\"><i class=\"fas fa-plus-circle me-2\"></i>Làm thêm giờ</label>");
		out.append("<div class=\"\">");
		out.append("<label class=\"me-2\">có</label>");
		out.append("<input type=\"radio\" class=\"me-4\" value=\"1\" name=\"isOT\" >");
		out.append("<label class=\"me-2\">không</label>");
		out.append("<input type=\"radio\" value=\"0\" name=\"isOT\" checked>");
		out.append("</div>");
		out.append("</div>");
		out.append("<div class=\"col-md-7 col-lg-6\" id=\"form-control\">");
		out.append("<label for=\"slcfield\" class=\"form-label text-center\"><i class=\"fas fa-file-signature me-2\"></i>Lĩnh vực</label>");
		out.append("<select class=\"form-select\" id=\"slcfield\" name=\"slcfield\" required>");
		out.append("<option value=\"-1\" selected>-- Chọn ngay --</option>");
		out.append(viewList.get(2));
		out.append("</select>");
		 out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
		out.append("</div>");
		out.append("</div>");
		
		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"summary\" class=\"col-md-4 col-lg-3 col-form-label\"><i class=\"far fa-sun me-2\"></i>Tóm tắt</label>");
		out.append("<div class=\"col-md-8 col-lg-9\" id=\"form-control\">");
		out.append("<textarea name=\"txtsumary\" class=\"form-control\" id=\"summary\" style=\"height: 100px\"></textarea>");
		out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
		out.append("</div>");
		out.append("</div>");
		
		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"about\" class=\"col-md-4 col-lg-3 col-form-label\"><i class=\"bi bi-file-earmark-person-fill me-2\"></i>Về chúng tôi</label>");
		out.append("<div class=\"col-md-8 col-lg-9\" id=\"form-control\">");
		out.append("<textarea name=\"txtabout\" class=\"form-control\" id=\"about\" style=\"height: 100px\"></textarea>");
		out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
		out.append("<script>");
		out.append("var editor = CKEDITOR.replace(\"about\");");
		out.append("CKFinder.setupCKEditor(editor,\"/adv/ckfinder/\")");
		out.append("</script>");
		out.append("</div>");
		out.append("</div>");
		
		out.append("<div class=\"row mb-3 align-items-center\">");
		out.append("<label for=\"companyremuneration\" class=\"col-md-4 col-lg-3 col-form-label\"><i class=\"fab fa-superpowers me-2\"></i>Phúc lợi</label>");
		out.append("<div class=\"col-md-8 col-lg-9\" id=\"form-control\">");
		out.append("<textarea name=\"companyremuneration\" class=\"form-control\" id=\"remuneration\" style=\"height: 100px\"></textarea>");
		out.append("<small><i class=\"bi bi-ban-fill\"></i></small>");
		out.append("<script>");
		out.append("var editor = CKEDITOR.replace(\"companyremuneration\");");
		out.append("CKFinder.setupCKEditor(editor,\"/adv/ckfinder/\")");
		out.append("</script>");
		out.append("</div>");
		out.append("</div>");
		
		
		out.append("</div>");//end tab pane
		out.append("</div><!-- End Bordered Tabs Justified -->");

		out.append("</div>"); //end col
		out.append("</div>"); //end row
		
		out.append("</div>"); // end container
		out.append("</div>"); // end model body
		
		out.append("<div class=\"modal-footer d-flex justify-content-between \">");
		out.append("<button class=\"btn btn-secondary\" type=\"button\" id=\"previousTab\"><i class=\"bi bi-arrow-left-square\"></i></button>");
		out.append("<button class=\"btn btn-secondary\" type=\"button\" id=\"nextTab\"><i class=\"bi bi-arrow-right-square\"></i></button>");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");
		out.append("</form>");
		}
		// list section
		out.append(viewList.get(0));
		// phan trang
		out.append(viewList.get(1));
		
		out.append("</div>"); // end card-body
		out.append("</div>"); // end card

		out.append("</div>"); // col-lg-12
		out.append("</div>"); // row
		out.append("</section>");

		// charts
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-12\">");
//		out.append(viewList.get(3));
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
	public static boolean checkValidString(String str) {
		boolean flag = true;
		if(str==null || str.equalsIgnoreCase("")) {
			flag = false;
		}
		return flag;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.setCharacterEncoding("UTF-8");
		
		String key = request.getParameter("keyword");
		
		String companyName = request.getParameter("companyname");
		String email = request.getParameter("txtemail");
		String location = request.getParameter("locationData");
		String phone = request.getParameter("txtphone");
		String website = request.getParameter("txtwebsite");
		String sumary = request.getParameter("txtsumary");
		String about = request.getParameter("txtabout");
		String remuneration = request.getParameter("companyremuneration");
		String logo ="";
		if(key==null||key.equalsIgnoreCase("")) {
		Part filePart = request.getPart("logo");
		String filename = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		InputStream io = filePart.getInputStream();
		  String path  = getServletContext().getRealPath("/")+"adimgs" + File.separator + filename;
		  
        if(jsoft.library.Utilities.saveFile(io, path)) {
        	logo = "/adv/adimgs/"+filename;
        } 
		}
        
		byte Ot = jsoft.library.Utilities.getByteParam(request, "isOT");
		boolean isOt = (Ot==1)?true:false;
		byte nationality = jsoft.library.Utilities.getByteParam(request, "slcnationality");
		byte companySize = jsoft.library.Utilities.getByteParam(request, "slccompanysize");
		byte field = jsoft.library.Utilities.getByteParam(request, "slcfield");

		if (checkValidString(companyName) && checkValidString(email) 
				&& checkValidString(phone) && checkValidString(sumary) 
				&& checkValidString(about) && checkValidString(remuneration)
			    && nationality>=0 && companySize>=0 && field>0) {

			UserObject user = (UserObject) request.getSession().getAttribute("userLogined");
            CompanyObject company = new CompanyObject();
            company.setCompany_name(jsoft.library.Utilities.encode(companyName));
            company.setCompany_email(email);
            company.setCompany_homephone(phone);
            company.setCompany_officephone(phone);
            company.setCompany_mobilephone(phone);
            company.setCompany_logo(logo);
            company.setCompany_location(jsoft.library.Utilities.encode(location));
            
            company.setCompany_banner("/adv/adimgs/default.jpg");
            company.setCompany_website(website);
            company.setCompany_summary(jsoft.library.Utilities.encode(sumary));
            company.setCompany_about(jsoft.library.Utilities.encode(about));
            company.setCompany_remuneration(jsoft.library.Utilities.encode(remuneration));
            company.setCompany_isOT(isOt);
            company.setCompany_field_id(field);
            company.setCompany_nationality(nationality);
            company.setCompany_size(companySize);
            company.setCompany_created_date(jsoft.library.Utilities_date.getDate());
            company.setCompany_last_modified(jsoft.library.Utilities_date.getDate());
			company.setCompany_author_id(user.getUser_id());
			
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			CompanyControl cc = new CompanyControl(cp);
			// add new user
			if (cc != null) {
				getServletContext().setAttribute("CPool", cc.getCP());
			}
			boolean result = cc.addCompany(company);
			// return connect
			cc.releaseConnection();
			if (result) {
				response.sendRedirect("/adv/company/list");
			} else {
				response.sendRedirect("/adv/company/list?err=add");
			}
		} else {
			//String key = request.getParameter("keyword");
			String encodedKeyword = URLEncoder.encode(key, "UTF-8");
			String key_url = (key!=null && !key.equalsIgnoreCase(""))?("&key="+encodedKeyword):"";
			System.out.println("ads"+key_url);
			response.sendRedirect("/adv/company/list?page=1"+key_url);
		}
	}

}

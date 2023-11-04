package jsoft.ads.job;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.CareerObject;
import jsoft.objects.JobObject;
import jsoft.objects.SkillObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class CategoryList
 */
@WebServlet("/job/list")
public class JobList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobList() {
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
		JobControl jc = new JobControl(cp);

		// lấy cấu trúc
		JobObject similar = new JobObject();
		similar.setJob_delete(false);
		similar.setJob_title(saveKey);

		// tim thanh so xac dinh loại danh sách
		String trash =request.getParameter("trash");
		boolean isTrash = (trash!=null)?true:false;
		String title;
		if(!isTrash) {
	        similar.setJob_delete(false);
	   		title="Danh sách tin tuyển dụng";
		} else {
			title ="Danh sách tin tuyển dụng bị xóa";
			similar.setJob_delete(true);
		}
		
		byte pageSize = 5;
		
		Quartet<JobObject, Integer, Byte,UserObject> infos = new Quartet<>(similar,pageSize*(page-1),pageSize,user);
		
		
		ArrayList<String> viewList = jc.viewJob(infos, new Pair<>(JOB_SOFT.GENERAL, ORDER.ASC),page,saveKey,isTrash);
		
		// trả về kết nối
		jc.releaseConnection();

		
		String pos = (trash==null)?"/header?pos=jblist":"/header?pos=jbtrash";
		
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
		out.append("<li class=\"breadcrumb-item\">Tin tuyển dụng</li>");
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
		out.append("<button type=\"button\" class=\"btn btn-primary my-4 \" data-bs-toggle=\"modal\" data-bs-target=\"#addEditJob\"><i class=\"fa-solid fa-plus me-2\"></i>Thêm mới</button>");
		out.append("<a href=\"/adv/job/list?trash\" class=\"btn btn-danger my-4 \" ><i class=\"fas fa-trash-restore\"></i>Thùng rác</a>");
		out.append("</div><!-- End div -->");
		
		
		out.append("<div class=\"modal fade modal-lg\" id=\"addEditJob\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">");
		out.append("<div class=\"modal-dialog\">");
		out.append("<div class=\"modal-content\">");
		out.append("<div class=\"modal-header\">");
		out.append("<h1 class=\"modal-title fs-5\" id=\"staticBackdropLabel\">Thêm tin tuyển dụng</h1>");
		out.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
		out.append("</div>");

		out.append("<div class=\"modal-body\">");
		out.append("<div class=\"container\">");
		
		out.append("<div class=\"row\">");
		out.append("<div class=\"text-center my-3\">");
		out.append("<span class=\"step\"></span>");
		out.append("<span class=\"step\"></span>");
		out.append("<span class=\"step\"></span>");
		out.append("<span class=\"step\"></span>");
		out.append("</div>");
		out.append("</div>");
		
		out.append("<form method=\"POST\" id=\"regForm\" id=\"form_add\">");
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-12\">");
		
		out.append("<!-- One \"tab\" for each step in the form: -->");
		out.append("<div class=\"tab\">");
		out.append("<h2 class=\"text-center\">Thông tin cơ bản</h2>");
		out.append("<div class=\"row mb-3\">");
		out.append("<div class=\"col\"");
		out.append("<label for=\"job_title\" class=\"form-label mb-2\"><i class=\"fas fa-heading me-2\"></i>Tiêu đề</label>");
		out.append("<input type=\"text\" class='form-control' name=\"job_title\" id=\"job_title\" placeholder=\"...\" oninput=\"this.className = 'form-control'\">");
		out.append("</div>");
		out.append("</div>");//end row
		
		out.append("<div class=\"row mb-3\">");
		out.append("<div class=\"col-6\"");
		out.append("<label for=\"job_company\" class=\"form-label mb-2\"><i class=\"fas fa-building me-2\"></i>Công ty</label>");
		out.append("<select class=\"form-select\" onchange='getCareerByCompany(event)' id=\"job_company\" name=\"job_company\" required>");
		out.append("<option value=\"-1\">-- Chọn công ty -- </option>");
		out.append(viewList.get(2));
		out.append("</select>");
		out.append("</div>");
		out.append("<div class=\"col-6\"");
		out.append("<label for=\"job_career\" class=\"form-label mb-2\"><i class=\"fas fa-hospital-user me-2\"></i>Ngành nghề</label>");
		out.append("<select onChange=\"this.className='form-select';\" class=\"form-select\" id=\"job_career\" name=\"job_career\" required>");
		out.append("<option value=\"-1\">-- Chọn ngành/nghề -- </option>");
		out.append("</select>");
		out.append("</div>");
		out.append("</div>");//end row
		
		out.append("<div class=\"row mb-3\">");
		out.append("<div class=\"col-6\"");
		out.append("<label for=\"job_expiration_date\" class=\"form-label mb-2\"><i class=\"fas fa-clock me-2\"></i>Ngày hết hạn</label>");
		out.append("<input class='form-control' type=\"date\" name=\"job_expiration_date\" id=\"job_expiration_date\" placeholder=\"...\" oninput=\"this.className = 'form-control'\">");
		out.append("</div>");
		out.append("<div class=\"col-6\"");
		out.append("<label for=\"job_quantity\" class=\"form-label mb-2\"><i class=\"fas fa-sort-numeric-up me-2\"></i>Số lượng</label>");
		out.append("<input type=\"number\" class=\"form-control\" id=\"job_quantity\" name=\"job_quantity\" min=\"1\" max=\"100\" oninput=\"this.className = 'form-control'\">");
		out.append("</div>");
		out.append("</div>");//end row
		
		out.append("<div class=\"row mb-3\">");
		out.append("<div class=\"col-6\"");
		out.append("<label for=\"job_work_time\" class=\"form-label mb-2\"><i class=\"fas fa-business-time me-2\"></i>Thời gian làm việc</label>");
		out.append("<select onChange=\"this.className='form-select';\" class=\"form-select\" id=\"job_work_time\" name=\"job_work_time\" required>");
		out.append("<option value=\"-1\">-- Vui lòng chọn --</option>");
		out.append("<option value=\"1\">Toàn thời gian Thứ 2 - Thứ 6</option>");
		out.append("<option value=\"2\">Bán thời gian (Tối thiểu 6 buổi/tuần)</option>");
		out.append("<option value=\"3\">Thực tập (Part time hoặc Full time)</option>");
		out.append("<option value=\"4\">Việc làm online</option>");
		out.append("<option value=\"5\">Nghề tự do</option>");
		out.append("<option value=\"6\">Hợp đồng thời vụ</option>");
		out.append("<option value=\"7\">Khác</option>");
		out.append("</select>");
		out.append("</div>");
		out.append("<div class=\"col-6\"");
		out.append("<label for=\"job_level\" class=\"form-label mb-2\"><i class=\"fas fa-map me-2\"></i>Vị trí/Chức vụ</label>");
		out.append("<select onChange=\"this.className='form-select';\" class=\"form-select\" id=\"job_level\" name=\"job_level\" required>");
		out.append("<option value=\"-1\">-- Vui lòng chọn --</option>");
		out.append("<option value=\"1\">Nhân viên chính thức</option>");
		out.append("<option value=\"2\">Nhân viên thử việc</option>");
		out.append("<option value=\"3\">Quản lí</option>");
		out.append("<option value=\"4\">Thực tập sinh/Sinh viên</option>");
		out.append("<option value=\"5\">Trưởng nhóm</option>");
		out.append("<option value=\"6\">Trưởng phòng</option>");
		out.append("<option value=\"7\">Giám đốc và cấp cao hơn</option>");
		out.append("<option value=\"8\">Mới tốt nghiệp</option>");
		out.append("<option value=\"9\">Khác</option>");
		out.append("</select>");
		out.append("</div>");
		out.append("</div>");//end row

		out.append("<div class=\"row mb-3\">");
		out.append("<div class=\"col-12\"");
		out.append("<label for=\"job_location\" class=\"form-label mb-2\"><i class=\"fas fa-map-marker me-2\"></i>Địa chỉ làm việc</label>");
		out.append("<select onChange=\"this.className='form-select';\" class=\"form-select\" id=\"job_location\" name=\"job_location\" required>");
		out.append("<option value=\"-1\">-- Chọn địa chỉ -- </option>");
		out.append("<option value=\"1\">Làm việc trực tuyến</option>");
		out.append("<option value=\"2\">Làm việc ở nước ngoài</option>");
		out.append("</select>");
		out.append("</div>");
		out.append("</div>");//end row
		
		out.append("</div>");//end tab
		
		
		
        // tab 2
		out.append("<div class=\"tab\">");
		out.append("<h2 class=\"text-center\">Thông tin khác</h2>");
		
		out.append("<div class=\"row mb-3\">");
		out.append("<div class=\"col-6\"");
		out.append("<label for=\"job_salary\" class=\"form-label mb-2\"><i class=\"fas fa-money-bill-alt me-2\"></i>Lương & trợ cấp</label>");
		out.append("<select onChange=\"this.className='form-select';\" class=\"form-select\" id=\"job_salary\" name=\"job_salary\" required>");
		out.append("<option value=\"-1\">-- Vui lòng chọn --</option>");
		out.append("<option value=\"1\">3 triệu đến 5 triệu</option>");
		out.append("<option value=\"2\">5 triệu đến 7 triệu</option>");
		out.append("<option value=\"3\">7 triệu đến 10 triệu</option>");
		out.append("<option value=\"4\">10 triệu đến 15 triệu</option>");
		out.append("<option value=\"5\">15 triệu đến 30 triệu</option>");
		out.append("<option value=\"6\">Trên 30 triệu</option>");
		out.append("<option value=\"7\">Trên 50 triệu</option>");
		out.append("<option value=\"8\">Không lương</option>");
		out.append("<option value=\"9\">Thương lượng</option>");
		out.append("</select>");
		out.append("</div>");
		out.append("<div class=\"col-6\"");
		out.append("<label for=\"job_gender\" class=\"form-label mb-2\"><i class=\"fas fa-venus-double\"></i>Giới tính</label>");
		out.append("<select onChange=\"this.className='form-select';\" class=\"form-select\" id=\"job_gender\" name=\"job_gender\" required>");
		out.append("<option value=\"-1\">-- Vui lòng chọn --</option>");
		out.append("<option value=\"1\">Nam</option>");
		out.append("<option value=\"2\">Nữ</option>");
		out.append("<option value=\"3\">Không yêu cầu</option>");
		out.append("</select>");
		out.append("</div>");
		out.append("</div>");//end row
		
		out.append("<div class=\"row mb-3\">");
		out.append("<div class=\"col-6\"");
		out.append("<label for=\"job_degree\" class=\"form-label mb-2\"><i class=\"fas fa-graduation-cap me-2\"></i>Bằng cấp</label>");
		out.append("<select onChange=\"this.className='form-select';\" class=\"form-select\" id=\"job_degree\" name=\"job_degree\" required>");
		out.append("<option value=\"-1\">-- Vui lòng chọn --</option>");
		out.append("<option value=\"1\">Trung học</option>");
		out.append("<option value=\"2\">Trung cấp</option>");
		out.append("<option value=\"3\">Cao đẳng</option>");
		out.append("<option value=\"4\">Cử nhân</option>");
		out.append("<option value=\"5\">Thạc sĩ</option>");
		out.append("<option value=\"6\">Tiến sĩ</option>");
		out.append("<option value=\"7\">Không yêu cầu</option>");
		out.append("</select>");
		out.append("</div>");
		out.append("<div class=\"col-6\"");
		out.append("<label for=\"job_experience\" class=\"form-label mb-2\"><i class=\"fas fa-laptop-code me-2\"></i>Năm kinh nghiệm</label>");
		out.append("<select onChange=\"this.className='form-select';\" class=\"form-select\" id=\"job_experience\" name=\"job_experience\" required>");
		out.append("<option value=\"-1\">-- Vui lòng chọn --</option>");
		for(int i=1;i<=10;i++) {
			out.append("<option value=\""+i+"\">"+i+" Năm</option>");
		}
		out.append("<option value=\"11\">khác</option>");
		out.append("<option value=\"12\">Không yêu cầu</option>");
		out.append("</select>");
		out.append("</div>");
		out.append("</div>");//end row
		
		
		out.append("<div class=\"row mb-3\">");
		out.append("<div class=\"col-6\"");
		out.append("<label for=\"job_skill\" class=\"form-label mb-2\"><i class=\"fas fa-file-signature me-2\"></i>Kỹ năng</label>");
		out.append("<select name=\"job_skill\" id=\"job_skill\" multiple>");
        out.append(viewList.get(3));
		out.append("</select>");
		out.append("</div>");
		out.append("</div>");//end row
		
		out.append("</div>");//end tab
        // tab 3
		out.append("<div class=\"tab\">");
		out.append("<h2 class=\"text-center\">Thông tin khác</h2>");
		
		out.append("<div class=\"row mb-3\">");
		out.append("<div class=\"col-12\"");
		out.append("<label for=\"job_description\" class=\"form-label mb-2\"><i class=\"fas fa-info-circle me-2\"></i>Mô tả công việc</label>");
		out.append("<textarea name=\"job_description\" class=\"form-control\" id=\"job_description\" style=\"height: 150px\"></textarea>");
		out.append("<script>");
		out.append("var editor = CKEDITOR.replace(\"job_description\");");
		out.append("CKFinder.setupCKEditor(editor,\"/adv/ckfinder/\")");
		out.append("</script>");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");//end tab;
       // tab 4
		out.append("<div class=\"tab\">");
		
		out.append("<h2 class=\"text-center\">Thông tin khác</h2>");
		out.append("<div class=\"row mb-3\">");
		out.append("<div class=\"col-12\"");
		out.append("<label for=\"job_purpose\" class=\"form-label mb-2\"><i class=\"far fa-window-restore me-2\"></i>Yêu cầu công việc</label>");
		out.append("<textarea name=\"job_purpose\" class=\"form-control\" id=\"job_purpose\" style=\"height: 150px\"></textarea>");
		out.append("<script>");
		out.append("var editor = CKEDITOR.replace(\"job_purpose\");");
		out.append("CKFinder.setupCKEditor(editor,\"/adv/ckfinder/\")");
		out.append("</script>");
		out.append("</div>");
		out.append("</div>");
		
		out.append("<div class=\"row mb-3\">");
		out.append("<div class=\"col-12\"");
		out.append("<label for=\"job_welfare\" class=\"form-label mb-2\"><i class=\"fas fa-layer-group me-2\"></i>Phúc lợi</label>");
		out.append("<textarea name=\"job_welfare\" class=\"form-control\" id=\"job_welfare\" style=\"height: 150px\"></textarea>");
		out.append("<script>");
		out.append("var editor = CKEDITOR.replace(\"job_welfare\");");
		out.append("CKFinder.setupCKEditor(editor,\"/adv/ckfinder/\")");
		out.append("</script>");
		out.append("</div>");
		out.append("</div>");
		
		out.append("</div>"); //end tav

		out.append("<div style=\"overflow:auto;\">");
		out.append("<div class=\"my-2\">");
		out.append("<button class='btn btn-secondary ms-2 float-start ' type=\"button\" id=\"prevBtn\" onclick=\"nextPrev(-1)\"><i class=\"fas fa-caret-square-left\"></i></button>");
		out.append("<button class='btn btn-secondary me-2 float-end' type=\"button\" id=\"nextBtn\" onclick=\"nextPrev(1)\"><i class=\"fas fa-caret-square-right\"></i></button>");
		out.append("</div>");
		out.append("</div>");
		
		out.append("<input type=\"hidden\" id=\"job_skills\" name=\"job_skills\">");
		out.append("<input type=\"hidden\" id=\"idForPost\" name=\"idForPost\">");

		out.append("</div>"); //end col
		out.append("</div>"); //end row
		out.append("</form>");
		
		out.append("</div>"); // end container
		out.append("</div>"); // end model body
		
	
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");
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
		
		short id = jsoft.library.Utilities.getShortParam(request, "idForPost");
		String job_skills = request.getParameter("job_skills");
		String job_title = request.getParameter("job_title");
		String job_expiration_date = request.getParameter("job_expiration_date");
		String job_description = request.getParameter("job_description");
		String job_purpose = request.getParameter("job_purpose");
		String job_welfare = request.getParameter("job_welfare");
		String job_location = request.getParameter("job_location");
		byte job_experience =  jsoft.library.Utilities.getByteParam(request, "job_experience");
		byte job_degree  =  jsoft.library.Utilities.getByteParam(request, "job_degree");
		byte job_salary  =  jsoft.library.Utilities.getByteParam(request, "job_salary");
		byte job_gender = jsoft.library.Utilities.getByteParam(request, "job_gender");
		byte job_level  =  jsoft.library.Utilities.getByteParam(request, "job_level");
		byte job_work_time = jsoft.library.Utilities.getByteParam(request, "job_work_time");
		byte job_company = jsoft.library.Utilities.getByteParam(request, "job_company");
		int job_career = jsoft.library.Utilities.getIntParam(request, "job_career");
		byte job_quantity = jsoft.library.Utilities.getByteParam(request, "job_quantity");

		if (checkValidString(job_title) && checkValidString(job_expiration_date) 
				&& checkValidString(job_description) && checkValidString(job_purpose) 
				&& checkValidString(job_welfare)  
			    && job_company>=0 && job_career>=0 && job_quantity>0) {

			UserObject user = (UserObject) request.getSession().getAttribute("userLogined");
            JobObject job = new JobObject();
            if(id>0) {
    		job.setJob_id(id);	
    		}
            job.setJob_experience_id(job_experience);
            job.setJob_degree(job_degree);
            job.setJob_skills(job_skills);
            job.setJob_salary(job_salary);
            job.setJob_location(job_location);
            job.setJob_quantity(job_quantity);
            CareerObject c = new CareerObject();
            c.setCareer_id(job_career);
            job.setJob_career(c);
            job.setJob_level(job_level);
            job.setJob_work_time(job_work_time);
            job.setJob_gender(job_gender);
            job.setJob_company_id(job_company);
            job.setJob_created_date(jsoft.library.Utilities_date.getDate());
            job.setJob_last_modified(jsoft.library.Utilities_date.getDate());
            job.setJob_author_id(user.getUser_id());
            job.setJob_purpose(jsoft.library.Utilities.encode(job_purpose));
            job.setJob_expiration_date(jsoft.library.Utilities_date.getDateFomat(job_expiration_date));
            job.setJob_responsibility(jsoft.library.Utilities.encode(job_description));
            job.setJob_Welfare(jsoft.library.Utilities.encode(job_welfare));
            job.setJob_title(jsoft.library.Utilities.encode(job_title));
			
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			JobControl cc = new JobControl(cp);
			// add new user
			if (cc != null) {
				getServletContext().setAttribute("CPool", cc.getCP());
			}
			
			if(id>0) {
				boolean resultEdit = cc.editJob(job,JOB_EDIT_TYPE.GENERAL);
				// return connect
				cc.releaseConnection();
				if (resultEdit) {
					response.sendRedirect("/adv/job/list");
				} else {
					response.sendRedirect("/adv/job/list?err=edit");
				}
			} else {
				boolean resultAdd = cc.addJob(job);
				// return connect
				cc.releaseConnection();
				if (resultAdd) {
					response.sendRedirect("/adv/job/list");
				} else {
					response.sendRedirect("/adv/job/list?err=add");
				}
			}
		} else {
			String key = request.getParameter("keyword");
			String encodedKeyword = URLEncoder.encode(key, "UTF-8");
			String key_url = (key!=null && !key.equalsIgnoreCase(""))?("&key="+encodedKeyword):"";
			response.sendRedirect("/adv/job/list?page=1"+key_url);
		}
	}

}

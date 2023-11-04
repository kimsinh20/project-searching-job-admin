package jsoft.ads.job;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javatuples.Pair;

import jsoft.ConnectionPool;
import jsoft.objects.AddressObject;
import jsoft.objects.CompanyObject;
import jsoft.objects.FieldObject;
import jsoft.objects.JobObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class SectionEdit
 */
@WebServlet("/job/view")
public class JobView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JobView() {
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

		JobObject job = null;
		HashMap<Integer, String> author_name = new HashMap<>();

		if (id > 0) {
			// tìm bộ quản lý kết nối
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			// tạo đối tượng thực thi chức năng
			JobControl cc = new JobControl(cp);
			if (cp == null) {
				getServletContext().setAttribute("CPool", cp);
			}
			Pair<JobObject, HashMap<Integer, String>> getCateObject = cc.getJobObject(id, user);
			job = getCateObject.getValue0();
			author_name = getCateObject.getValue1();

			// trả về kết nối
			cc.releaseConnection();
		}
        String job_title = "";
		CompanyObject company = null;
		boolean isChangePass = false;
		if (job != null) {
			company = job.getCompany();
			job_title = job.getJob_title();
		}

		// tham chiếu tìm header
		RequestDispatcher h = request.getRequestDispatcher("/header?pos=jblist");
		if (h != null) {
			h.include(request, response);
		}

		out.append("<main id=\"main\" class=\"main\">");

		RequestDispatcher error = request.getRequestDispatcher("/error");
		if (error != null) {
			error.include(request, response);
		}

		out.append("<div class=\"pagetitle d-flex\">");
		out.append("<h1>Thông tin chi tiết tin tuyển dụng</h1>");
		out.append("<nav class=\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/adv/view\"><i class=\"bi bi-house\"></i></a></li>");
		out.append("<li class=\"breadcrumb-item\">Tin tuyển dụng</li>");
		out.append("<li class=\"breadcrumb-item active\">Cập nhật chi tiết</li>");
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");

		out.append("<section class=\"section profile\">");
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-xl-4\">");
		if (job != null) {
			out.append("<div class=\"card\" style=\"background-image: url('"+company.getCompany_banner()+"'); opacity: 0.7; width: 100%;\">");
			out.append("<div class=\"card-body profile-card pt-4 d-flex flex-column align-items-center\">");
			out.append("<img src=\"" + company.getCompany_logo() + "\" alt=\"Profile\" class=\"rounded-circle\">");
			out.append("<h2 class='text-dark text-center'>" + company.getCompany_name() + "</h2>");
			out.append("<div class=\"social-links mt-2\">");
			out.append("<a href=\"#\" class=\"twitter text-dark\"><i class=\"bi bi-twitter\"></i></a>");
			out.append("<a href=\"#\" class=\"facebook text-dark\"><i class=\"bi bi-facebook\"></i></a>");
			out.append("<a href=\"#\" class=\"instagram text-dark\"><i class=\"bi bi-instagram\"></i></a>");
			out.append("<a href=\"#\" class=\"linkedin text-dark\"><i class=\"bi bi-linkedin\"></i></a>");
			out.append("</div>");
			out.append("</div>");
			out.append("</div>"); 
		 }
		out.append("</div>"); // end cod-xl-4

		out.append("<div class=\"col-xl-8\">");

		out.append("<div class=\"card\">");
		out.append("<div class=\"card-body pt-3\">");
		out.append("<!-- Bordered Tabs -->");
		out.append("<ul class=\"nav nav-tabs nav-tabs-bordered\">");

		out.append("<li class=\"nav-item\">");
		out.append("<button class=\"nav-link active\" data-bs-toggle=\"tab\" data-bs-target=\"#overview\"><i class=\"fas fa-info-circle me-1\"></i>Tổng quát</button>");
		out.append("</li>");
		
		out.append("</ul>");
		out.append("<div class=\"tab-content pt-2\">");

		// tab tong quat

		out.append("<div class=\"tab-pane fade show active profile-overview\" id=\"overview\">");

		out.append("<h5 class=\"card-title\">Chi tiết<i class=\"fas fa-info-circle ms-2\"></i></h5>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label \">Tiêu đề tin</div>");
		out.append("<div class=\"col-lg-6 col-md-5\">" + job_title + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Ngày đăng tin</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + job.getJob_created_date() + "</div>");
		out.append("</div>");
		
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Ngày hết hạn</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + jsoft.library.Utilities_date.getDateFomat(job.getJob_expiration_date()) + "</div>");
		out.append("</div>");
		
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Ngành nghề</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + job_title + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Hình thức</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + job_title + "</div>");
		out.append("</div>");
		
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label \">Địa chỉ</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">");
		
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Số lượng</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + job.getJob_quantity() + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Vị trí / Chức vụ</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + job_title + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Thời gian / Hình thức</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + job_title + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Lương & Trợ cấp</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + job_title + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Giới tính</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + job_title + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Bằng cấp</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + job_title + "</div>");
		out.append("</div>");
		
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Khinh nghiệm làm việc</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + job_title + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Ngày chỉnh sửa gần nhất</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + job.getJob_last_modified() + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Mô tả công việc</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + jsoft.library.Utilities.decode(job.getJob_responsibility()) + "</div>");
		out.append("</div>");
		
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Yêu cầu công việc</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + jsoft.library.Utilities.decode(job.getJob_purpose()) + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Phúc lợi</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + jsoft.library.Utilities.decode(job.getJob_Welfare()) + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Đánh giá </div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + "4.4 <i class=\"fa-solid fa-star text-warning\"></i>" + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Lượt xem</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + 2 + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Lượt theo dõi</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + 201 + "</div>");
		out.append("</div>");

		out.append("</div>"); // tab-pane

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
	
	}

}

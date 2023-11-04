package jsoft.ads.main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsoft.objects.UserObject;

/**
 * Servlet implementation class View
 */
@WebServlet("/view")
public class View extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public View() {
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

	protected void view(HttpServletRequest request, HttpServletResponse response, UserObject user) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// xác định kiểu nội dung xuất về trình khách
		response.setContentType(CONTENT_TYPE);

		// tạo đối tượng thực hiện xuất nội dung
		PrintWriter out = response.getWriter();
		
		// tham chiếu tìm header
		RequestDispatcher h = request.getRequestDispatcher("/header");
		if(h != null) {
			h.include(request, response);
		}
		// if login thanh cong thi show toast
		String success = request.getParameter("success");
		if (success != null) {
			out.append("<div class=\"toast-container position-fixed top-1 end-0 ps-3 pe-5 mb-3\">");
			out.append(
					"<div id=\"liveToast\" class=\"toast\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\">");
			out.append("<div class=\"toast-header\">");
			// out.append("<img src=\"...\" class=\"rounded me-2\" alt=\"...\">");
			out.append("<strong class=\"me-auto text-success\">Thông báo</strong>");
			out.append("<small>10 giây</small>");
			out.append(
					"<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"toast\" aria-label=\"Close\"></button>");
			out.append("</div>");
			out.append("<div class=\"toast-body\">");
			out.append("Đăng nhập thành công");
			out.append("</div>");
			out.append("</div>");
			out.append("</div>");

			// script
			out.append("<script language=\"javascript\" >");
			out.append("const viewToast = document.getElementById('liveToast');");
			out.append("const toast = new bootstrap.Toast(viewToast);");
			out.append("toast.show();");
			out.append("</script>");
		}
		
		out.append("<main id=\"main\" class=\"main\">");

		out.append("<div class=\"pagetitle\">");
		out.append("<h1>Dashboard</h1>");
		out.append("<nav>");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/adv/view\">Trang chủ</a></li>");
		out.append("<li class=\"breadcrumb-item active\">Dashboard</li>");
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");

		out.append("<section class=\"section dashboard\">");
		out.append("<div class=\"row\">");

		out.append("<!-- Left side columns -->");
		out.append("<div class=\"col-lg-8\">");
		out.append("<div class=\"row\">");

		out.append("<!-- Sales Card -->");
		out.append("<div class=\"col-xxl-4 col-md-6\">");
		out.append("<div class=\"card info-card sales-card\">");

		out.append("<div class=\"filter\">");
		out.append("<a class=\"icon\" href=\"#\" data-bs-toggle=\"dropdown\"><i class=\"bi bi-three-dots\"></i></a>");
		out.append("<ul class=\"dropdown-menu dropdown-menu-end dropdown-menu-arrow\">");
		out.append("<li class=\"dropdown-header text-start\">");
		out.append("<h6>Filter</h6>");
		out.append("</li>");

		out.append("<li><a class=\"dropdown-item\" href=\"#\">Today</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Month</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Year</a></li>");
		out.append("</ul>");
		out.append("</div>");

		out.append("<div class=\"card-body\">");
		out.append("<h5 class=\"card-title\">Sales <span>| Today</span></h5>");

		out.append("<div class=\"d-flex align-items-center\">");
		out.append("<div class=\"card-icon rounded-circle d-flex align-items-center justify-content-center\">");
		out.append("<i class=\"bi bi-cart\"></i>");
		out.append("</div>");
		out.append("<div class=\"ps-3\">");
		out.append("<h6>145</h6>");
		out.append("<span class=\"text-success small pt-1 fw-bold\">12%</span> <span class=\"text-muted small pt-2 ps-1\">increase</span>");

		out.append("</div>");
		out.append("</div>");
		out.append("</div>");

		out.append("</div>");
		out.append("</div><!-- End Sales Card -->");

		out.append("<!-- Revenue Card -->");
		out.append("<div class=\"col-xxl-4 col-md-6\">");
		out.append("<div class=\"card info-card revenue-card\">");

		out.append("<div class=\"filter\">");
		out.append("<a class=\"icon\" href=\"#\" data-bs-toggle=\"dropdown\"><i class=\"bi bi-three-dots\"></i></a>");
		out.append("<ul class=\"dropdown-menu dropdown-menu-end dropdown-menu-arrow\">");
		out.append("<li class=\"dropdown-header text-start\">");
		out.append("<h6>Filter</h6>");
		out.append("</li>");

		out.append("<li><a class=\"dropdown-item\" href=\"#\">Today</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Month</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Year</a></li>");
		out.append("</ul>");
		out.append("</div>");

		out.append("<div class=\"card-body\">");
		out.append("<h5 class=\"card-title\">Revenue <span>| This Month</span></h5>");

		out.append("<div class=\"d-flex align-items-center\">");
		out.append("<div class=\"card-icon rounded-circle d-flex align-items-center justify-content-center\">");
		out.append("<i class=\"bi bi-currency-dollar\"></i>");
		out.append("</div>");
		out.append("<div class=\"ps-3\">");
		out.append("<h6>$3,264</h6>");
		out.append("<span class=\"text-success small pt-1 fw-bold\">8%</span> <span class=\"text-muted small pt-2 ps-1\">increase</span>");

		out.append("</div>");
		out.append("</div>");
		out.append("</div>");

		out.append("</div>");
		out.append("</div><!-- End Revenue Card -->");

		out.append("<!-- Customers Card -->");
		out.append("<div class=\"col-xxl-4 col-xl-12\">");

		out.append("<div class=\"card info-card customers-card\">");

		out.append("<div class=\"filter\">");
		out.append("<a class=\"icon\" href=\"#\" data-bs-toggle=\"dropdown\"><i class=\"bi bi-three-dots\"></i></a>");
		out.append("<ul class=\"dropdown-menu dropdown-menu-end dropdown-menu-arrow\">");
		out.append("<li class=\"dropdown-header text-start\">");
		out.append("<h6>Filter</h6>");
		out.append("</li>");

		out.append("<li><a class=\"dropdown-item\" href=\"#\">Today</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Month</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Year</a></li>");
		out.append("</ul>");
		out.append("</div>");

		out.append("<div class=\"card-body\">");
		out.append("<h5 class=\"card-title\">Customers <span>| This Year</span></h5>");

		out.append("<div class=\"d-flex align-items-center\">");
		out.append("<div class=\"card-icon rounded-circle d-flex align-items-center justify-content-center\">");
		out.append("<i class=\"bi bi-people\"></i>");
		out.append("</div>");
		out.append("<div class=\"ps-3\">");
		out.append("<h6>1244</h6>");
		out.append("<span class=\"text-danger small pt-1 fw-bold\">12%</span> <span class=\"text-muted small pt-2 ps-1\">decrease</span>");

		out.append("</div>");
		out.append("</div>");

		out.append("</div>");
		out.append("</div>");

		out.append("</div><!-- End Customers Card -->");

		out.append("<!-- Reports -->");
		out.append("<div class=\"col-12\">");
		out.append("<div class=\"card\">");

		out.append("<div class=\"filter\">");
		out.append("<a class=\"icon\" href=\"#\" data-bs-toggle=\"dropdown\"><i class=\"bi bi-three-dots\"></i></a>");
		out.append("<ul class=\"dropdown-menu dropdown-menu-end dropdown-menu-arrow\">");
		out.append("<li class=\"dropdown-header text-start\">");
		out.append("<h6>Filter</h6>");
		out.append("</li>");

		out.append("<li><a class=\"dropdown-item\" href=\"#\">Today</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Month</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Year</a></li>");
		out.append("</ul>");
		out.append("</div>");

		out.append("<div class=\"card-body\">");
		out.append("<h5 class=\"card-title\">Reports <span>/Today</span></h5>");

		out.append("<!-- Line Chart -->");
		out.append("<div id=\"reportsChart\"></div>");

		out.append("<script>");
		out.append("document.addEventListener(\"DOMContentLoaded\", () => {");
		out.append("new ApexCharts(document.querySelector(\"#reportsChart\"), {");
		out.append("series: [{");
		out.append("name: 'Sales',");
		out.append("data: [31, 40, 28, 51, 42, 82, 56],");
		out.append("}, {");
		out.append("name: 'Revenue',");
		out.append("data: [11, 32, 45, 32, 34, 52, 41]");
		out.append("}, {");
		out.append("name: 'Customers',");
		out.append("data: [15, 11, 32, 18, 9, 24, 11]");
		out.append("}],");
		out.append("chart: {");
		out.append("height: 350,");
		out.append("type: 'area',");
		out.append("toolbar: {");
		out.append("show: false");
		out.append("},");
		out.append("},");
		out.append("markers: {");
		out.append("size: 4");
		out.append("},");
		out.append("colors: ['#4154f1', '#2eca6a', '#ff771d'],");
		out.append("fill: {");
		out.append("type: \"gradient\",");
		out.append("gradient: {");
		out.append("shadeIntensity: 1,");
		out.append("opacityFrom: 0.3,");
		out.append("opacityTo: 0.4,");
		out.append("stops: [0, 90, 100]");
		out.append("}");
		out.append("},");
		out.append("dataLabels: {");
		out.append("enabled: false");
		out.append("},");
		out.append("stroke: {");
		out.append("curve: 'smooth',");
		out.append("width: 2");
		out.append("},");
		out.append("xaxis: {");
		out.append("type: 'datetime',");
		out.append("categories: [\"2018-09-19T00:00:00.000Z\", \"2018-09-19T01:30:00.000Z\", \"2018-09-19T02:30:00.000Z\", \"2018-09-19T03:30:00.000Z\", \"2018-09-19T04:30:00.000Z\", \"2018-09-19T05:30:00.000Z\", \"2018-09-19T06:30:00.000Z\"]");
		out.append("},");
		out.append("tooltip: {");
		out.append("x: {");
		out.append("format: 'dd/MM/yy HH:mm'");
		out.append("},");
		out.append("}");
		out.append("}).render();");
		out.append("});");
		out.append("</script>");
		out.append("<!-- End Line Chart -->");

		out.append("</div>");

		out.append("</div>");
		out.append("</div><!-- End Reports -->");

		out.append("<!-- Recent Sales -->");
		out.append("<div class=\"col-12\">");
		out.append("<div class=\"card recent-sales overflow-auto\">");

		out.append("<div class=\"filter\">");
		out.append("<a class=\"icon\" href=\"#\" data-bs-toggle=\"dropdown\"><i class=\"bi bi-three-dots\"></i></a>");
		out.append("<ul class=\"dropdown-menu dropdown-menu-end dropdown-menu-arrow\">");
		out.append("<li class=\"dropdown-header text-start\">");
		out.append("<h6>Filter</h6>");
		out.append("</li>");

		out.append("<li><a class=\"dropdown-item\" href=\"#\">Today</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Month</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Year</a></li>");
		out.append("</ul>");
		out.append("</div>");

		out.append("<div class=\"card-body\">");
		out.append("<h5 class=\"card-title\">Recent Sales <span>| Today</span></h5>");

		out.append("<table class=\"table table-borderless datatable\">");
		out.append("<thead>");
		out.append("<tr>");
		out.append("<th scope=\"col\">#</th>");
		out.append("<th scope=\"col\">Customer</th>");
		out.append("<th scope=\"col\">Product</th>");
		out.append("<th scope=\"col\">Price</th>");
		out.append("<th scope=\"col\">Status</th>");
		out.append("</tr>");
		out.append("</thead>");
		out.append("<tbody>");
		out.append("<tr>");
		out.append("<th scope=\"row\"><a href=\"#\">#2457</a></th>");
		out.append("<td>Brandon Jacob</td>");
		out.append("<td><a href=\"#\" class=\"text-primary\">At praesentium minu</a></td>");
		out.append("<td>$64</td>");
		out.append("<td><span class=\"badge bg-success\">Approved</span></td>");
		out.append("</tr>");
		out.append("<tr>");
		out.append("<th scope=\"row\"><a href=\"#\">#2147</a></th>");
		out.append("<td>Bridie Kessler</td>");
		out.append("<td><a href=\"#\" class=\"text-primary\">Blanditiis dolor omnis similique</a></td>");
		out.append("<td>$47</td>");
		out.append("<td><span class=\"badge bg-warning\">Pending</span></td>");
		out.append("</tr>");
		out.append("<tr>");
		out.append("<th scope=\"row\"><a href=\"#\">#2049</a></th>");
		out.append("<td>Ashleigh Langosh</td>");
		out.append("<td><a href=\"#\" class=\"text-primary\">At recusandae consectetur</a></td>");
		out.append("<td>$147</td>");
		out.append("<td><span class=\"badge bg-success\">Approved</span></td>");
		out.append("</tr>");
		out.append("<tr>");
		out.append("<th scope=\"row\"><a href=\"#\">#2644</a></th>");
		out.append("<td>Angus Grady</td>");
		out.append("<td><a href=\"#\" class=\"text-primar\">Ut voluptatem id earum et</a></td>");
		out.append("<td>$67</td>");
		out.append("<td><span class=\"badge bg-danger\">Rejected</span></td>");
		out.append("</tr>");
		out.append("<tr>");
		out.append("<th scope=\"row\"><a href=\"#\">#2644</a></th>");
		out.append("<td>Raheem Lehner</td>");
		out.append("<td><a href=\"#\" class=\"text-primary\">Sunt similique distinctio</a></td>");
		out.append("<td>$165</td>");
		out.append("<td><span class=\"badge bg-success\">Approved</span></td>");
		out.append("</tr>");
		out.append("</tbody>");
		out.append("</table>");

		out.append("</div>");

		out.append("</div>");
		out.append("</div><!-- End Recent Sales -->");

		out.append("<!-- Top Selling -->");
		out.append("<div class=\"col-12\">");
		out.append("<div class=\"card top-selling overflow-auto\">");

		out.append("<div class=\"filter\">");
		out.append("<a class=\"icon\" href=\"#\" data-bs-toggle=\"dropdown\"><i class=\"bi bi-three-dots\"></i></a>");
		out.append("<ul class=\"dropdown-menu dropdown-menu-end dropdown-menu-arrow\">");
		out.append("<li class=\"dropdown-header text-start\">");
		out.append("<h6>Filter</h6>");
		out.append("</li>");

		out.append("<li><a class=\"dropdown-item\" href=\"#\">Today</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Month</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Year</a></li>");
		out.append("</ul>");
		out.append("</div>");

		out.append("<div class=\"card-body pb-0\">");
		out.append("<h5 class=\"card-title\">Top Selling <span>| Today</span></h5>");

		out.append("<table class=\"table table-borderless\">");
		out.append("<thead>");
		out.append("<tr>");
		out.append("<th scope=\"col\">Preview</th>");
		out.append("<th scope=\"col\">Product</th>");
		out.append("<th scope=\"col\">Price</th>");
		out.append("<th scope=\"col\">Sold</th>");
		out.append("<th scope=\"col\">Revenue</th>");
		out.append("</tr>");
		out.append("</thead>");
		out.append("<tbody>");
		out.append("<tr>");
		out.append("<th scope=\"row\"><a href=\"#\"><img src=\"assets/img/product-1.jpg\" alt=\"\"></a></th>");
		out.append("<td><a href=\"#\" class=\"text-primary fw-bold\">Ut inventore ipsa voluptas nulla</a></td>");
		out.append("<td>$64</td>");
		out.append("<td class=\"fw-bold\">124</td>");
		out.append("<td>$5,828</td>");
		out.append("</tr>");
		out.append("<tr>");
		out.append("<th scope=\"row\"><a href=\"#\"><img src=\"assets/img/product-2.jpg\" alt=\"\"></a></th>");
		out.append("<td><a href=\"#\" class=\"text-primary fw-bold\">Exercitationem similique doloremque</a></td>");
		out.append("<td>$46</td>");
		out.append("<td class=\"fw-bold\">98</td>");
		out.append("<td>$4,508</td>");
		out.append("</tr>");
		out.append("<tr>");
		out.append("<th scope=\"row\"><a href=\"#\"><img src=\"assets/img/product-3.jpg\" alt=\"\"></a></th>");
		out.append("<td><a href=\"#\" class=\"text-primary fw-bold\">Doloribus nisi exercitationem</a></td>");
		out.append("<td>$59</td>");
		out.append("<td class=\"fw-bold\">74</td>");
		out.append("<td>$4,366</td>");
		out.append("</tr>");
		out.append("<tr>");
		out.append("<th scope=\"row\"><a href=\"#\"><img src=\"assets/img/product-4.jpg\" alt=\"\"></a></th>");
		out.append("<td><a href=\"#\" class=\"text-primary fw-bold\">Officiis quaerat sint rerum error</a></td>");
		out.append("<td>$32</td>");
		out.append("<td class=\"fw-bold\">63</td>");
		out.append("<td>$2,016</td>");
		out.append("</tr>");
		out.append("<tr>");
		out.append("<th scope=\"row\"><a href=\"#\"><img src=\"assets/img/product-5.jpg\" alt=\"\"></a></th>");
		out.append("<td><a href=\"#\" class=\"text-primary fw-bold\">Sit unde debitis delectus repellendus</a></td>");
		out.append("<td>$79</td>");
		out.append("<td class=\"fw-bold\">41</td>");
		out.append("<td>$3,239</td>");
		out.append("</tr>");
		out.append("</tbody>");
		out.append("</table>");

		out.append("</div>");

		out.append("</div>");
		out.append("</div><!-- End Top Selling -->");

		out.append("</div>");
		out.append("</div><!-- End Left side columns -->");

		out.append("<!-- Right side columns -->");
		out.append("<div class=\"col-lg-4\">");

		out.append("<!-- Recent Activity -->");
		out.append("<div class=\"card\">");
		out.append("<div class=\"filter\">");
		out.append("<a class=\"icon\" href=\"#\" data-bs-toggle=\"dropdown\"><i class=\"bi bi-three-dots\"></i></a>");
		out.append("<ul class=\"dropdown-menu dropdown-menu-end dropdown-menu-arrow\">");
		out.append("<li class=\"dropdown-header text-start\">");
		out.append("<h6>Filter</h6>");
		out.append("</li>");

		out.append("<li><a class=\"dropdown-item\" href=\"#\">Today</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Month</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Year</a></li>");
		out.append("</ul>");
		out.append("</div>");

		out.append("<div class=\"card-body\">");
		out.append("<h5 class=\"card-title\">Recent Activity <span>| Today</span></h5>");

		out.append("<div class=\"activity\">");

		out.append("<div class=\"activity-item d-flex\">");
		out.append("<div class=\"activite-label\">32 min</div>");
		out.append("<i class='bi bi-circle-fill activity-badge text-success align-self-start'></i>");
		out.append("<div class=\"activity-content\">");
		out.append("Quia quae rerum <a href=\"#\" class=\"fw-bold text-dark\">explicabo officiis</a> beatae");
		out.append("</div>");
		out.append("</div><!-- End activity item-->");

		out.append("<div class=\"activity-item d-flex\">");
		out.append("<div class=\"activite-label\">56 min</div>");
		out.append("<i class='bi bi-circle-fill activity-badge text-danger align-self-start'></i>");
		out.append("<div class=\"activity-content\">");
		out.append("Voluptatem blanditiis blanditiis eveniet");
		out.append("</div>");
		out.append("</div><!-- End activity item-->");

		out.append("<div class=\"activity-item d-flex\">");
		out.append("<div class=\"activite-label\">2 hrs</div>");
		out.append("<i class='bi bi-circle-fill activity-badge text-primary align-self-start'></i>");
		out.append("<div class=\"activity-content\">");
		out.append("Voluptates corrupti molestias voluptatem");
		out.append("</div>");
		out.append("</div><!-- End activity item-->");

		out.append("<div class=\"activity-item d-flex\">");
		out.append("<div class=\"activite-label\">1 day</div>");
		out.append("<i class='bi bi-circle-fill activity-badge text-info align-self-start'></i>");
		out.append("<div class=\"activity-content\">");
		out.append("Tempore autem saepe <a href=\"#\" class=\"fw-bold text-dark\">occaecati voluptatem</a> tempore");
		out.append("</div>");
		out.append("</div><!-- End activity item-->");

		out.append("<div class=\"activity-item d-flex\">");
		out.append("<div class=\"activite-label\">2 days</div>");
		out.append("<i class='bi bi-circle-fill activity-badge text-warning align-self-start'></i>");
		out.append("<div class=\"activity-content\">");
		out.append("Est sit eum reiciendis exercitationem");
		out.append("</div>");
		out.append("</div><!-- End activity item-->");

		out.append("<div class=\"activity-item d-flex\">");
		out.append("<div class=\"activite-label\">4 weeks</div>");
		out.append("<i class='bi bi-circle-fill activity-badge text-muted align-self-start'></i>");
		out.append("<div class=\"activity-content\">");
		out.append("Dicta dolorem harum nulla eius. Ut quidem quidem sit quas");
		out.append("</div>");
		out.append("</div><!-- End activity item-->");

		out.append("</div>");

		out.append("</div>");
		out.append("</div><!-- End Recent Activity -->");

		out.append("<!-- Budget Report -->");
		out.append("<div class=\"card\">");
		out.append("<div class=\"filter\">");
		out.append("<a class=\"icon\" href=\"#\" data-bs-toggle=\"dropdown\"><i class=\"bi bi-three-dots\"></i></a>");
		out.append("<ul class=\"dropdown-menu dropdown-menu-end dropdown-menu-arrow\">");
		out.append("<li class=\"dropdown-header text-start\">");
		out.append("<h6>Filter</h6>");
		out.append("</li>");

		out.append("<li><a class=\"dropdown-item\" href=\"#\">Today</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Month</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Year</a></li>");
		out.append("</ul>");
		out.append("</div>");

		out.append("<div class=\"card-body pb-0\">");
		out.append("<h5 class=\"card-title\">Budget Report <span>| This Month</span></h5>");

		out.append("<div id=\"budgetChart\" style=\"min-height: 400px;\" class=\"echart\"></div>");

		// thêm biểu đồ
		
		out.append("</div>");
		out.append("</div><!-- End Budget Report -->");

		out.append("<!-- Website Traffic -->");
		out.append("<div class=\"card\">");
		out.append("<div class=\"filter\">");
		out.append("<a class=\"icon\" href=\"#\" data-bs-toggle=\"dropdown\"><i class=\"bi bi-three-dots\"></i></a>");
		out.append("<ul class=\"dropdown-menu dropdown-menu-end dropdown-menu-arrow\">");
		out.append("<li class=\"dropdown-header text-start\">");
		out.append("<h6>Filter</h6>");
		out.append("</li>");

		out.append("<li><a class=\"dropdown-item\" href=\"#\">Today</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Month</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Year</a></li>");
		out.append("</ul>");
		out.append("</div>");

		out.append("<div class=\"card-body pb-0\">");
		out.append("<h5 class=\"card-title\">Website Traffic <span>| Today</span></h5>");

		out.append("<div id=\"trafficChart\" style=\"min-height: 400px;\" class=\"echart\"></div>");

	    // thêm biểu đồ

		out.append("</div>");
		out.append("</div><!-- End Website Traffic -->");

		out.append("<!-- News & Updates Traffic -->");
		out.append("<div class=\"card\">");
		out.append("<div class=\"filter\">");
		out.append("<a class=\"icon\" href=\"#\" data-bs-toggle=\"dropdown\"><i class=\"bi bi-three-dots\"></i></a>");
		out.append("<ul class=\"dropdown-menu dropdown-menu-end dropdown-menu-arrow\">");
		out.append("<li class=\"dropdown-header text-start\">");
		out.append("<h6>Filter</h6>");
		out.append("</li>");

		out.append("<li><a class=\"dropdown-item\" href=\"#\">Today</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Month</a></li>");
		out.append("<li><a class=\"dropdown-item\" href=\"#\">This Year</a></li>");
		out.append("</ul>");
		out.append("</div>");

		out.append("<div class=\"card-body pb-0\">");
		out.append("<h5 class=\"card-title\">News &amp; Updates <span>| Today</span></h5>");

		out.append("<div class=\"news\">");
		
		// thêm các bài viết 

		out.append("</div><!-- End sidebar recent posts-->");

		out.append("</div>");
		out.append("</div><!-- End News & Updates -->");

		out.append("</div><!-- End Right side columns -->");

		out.append("</div>");
		out.append("</section>");

		out.append("</main><!-- End #main -->");
		
		// tham chiếu tìm sidebar
		RequestDispatcher f = request.getRequestDispatcher("/footer");
		if(f != null) {
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
		doGet(request, response);
	}

}

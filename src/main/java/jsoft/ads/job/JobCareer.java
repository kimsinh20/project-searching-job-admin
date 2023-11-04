package jsoft.ads.job;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jsoft.ConnectionPool;
import jsoft.objects.CareerObject;

/**
 * Servlet implementation class JobCareer
 */
@WebServlet("/api/job/career")
public class JobCareer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobCareer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  short company_field_id = jsoft.library.Utilities.getByteParam(request, "company_field_id");

	        if (company_field_id >0) {
	            // Gọi phương thức để truy xuất CSDL và lấy danh sách các ngành nghề dựa trên ID công ty
	        	ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
	        	JobControl jb = new JobControl(cp);
	        	if (jb != null) {
					getServletContext().setAttribute("CPool", jb.getCP());
				}
	        	List<CareerObject> industries = jb.getCareerByCompany(company_field_id);

	        	jb.releaseConnection();
	            // Chuyển danh sách ngành nghề thành JSON
	            Gson gson = new Gson();
	            String json = gson.toJson(industries);

	            // Thiết lập các tiêu đề HTTP và gửi phản hồi JSON
	            response.setContentType("application/json");
	            response.setCharacterEncoding("UTF-8");
	            response.getWriter().write(json);
	        } else {
	            // Xử lý lỗi nếu không có ID công ty trong yêu cầu
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            response.getWriter().write("Missing 'company_id' parameter.");
	        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package jsoft.ads.company;

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
import jsoft.objects.ProvinceObject;
import jsoft.objects.WardObject;

/**
 * Servlet implementation class JobCareer
 */
@WebServlet("/api/location/wards")
public class GetWards extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetWards() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		        String code = request.getParameter("code");
		        if(code!=null) {
	            // Gọi phương thức để truy xuất CSDL và lấy danh sách các ngành nghề dựa trên ID công ty
	        	ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
	        	CompanyControl cb = new CompanyControl(cp);
	        	if (cb != null) {
					getServletContext().setAttribute("CPool", cb.getCP());
				}
	        	List<WardObject> industries = cb.getWards(code);

	        	cb.releaseConnection();
	            // Chuyển danh sách ngành nghề thành JSON
	            Gson gson = new Gson();
	            String json = gson.toJson(industries);

	            // Thiết lập các tiêu đề HTTP và gửi phản hồi JSON
	            response.setContentType("application/json");
	            response.setCharacterEncoding("UTF-8");
	            response.getWriter().write(json);
		        } else {
		        	  response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			          response.getWriter().write("Missing 'code' parameter.");
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

package jsoft.ads.user;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class UserExport
 */
@WebServlet("/user/export")
public class UserExport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserExport() {
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
		// tìm bộ quản lý kết nối
		ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");

		if (cp == null) {
			getServletContext().setAttribute("CPool", cp);
		}
		// tạo đối tượng thực thi chức năng
		UserModel um = new UserModel(cp);
		// lấy cấu trúc
		UserObject similar = new UserObject();
		similar.setUser_id(user.getUser_id());
		similar.setUser_permission(user.getUser_permission());
		similar.setUser_deleted(false);
		byte pageSize = 100;
		Triplet<UserObject, Integer, Byte> infos = new Triplet<>(similar, 0, pageSize);
		Pair<ArrayList<UserObject>, Short> datas = um.getUserObjects(infos, new Pair<>(USER_SOFT.ID, ORDER.ASC));
        ArrayList<UserObject> u = datas.getValue0();
      
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		
		sheet.createRow(0);
		
		sheet.getRow(0).createCell(0).setCellValue("tên tài khoản");
		sheet.getRow(0).createCell(1).setCellValue("họ và tên");
		sheet.getRow(0).createCell(2).setCellValue("lần đăng nhập");
		int i =1;
		for(UserObject e : u) {
			sheet.createRow(i);
			sheet.getRow(i).createCell(0).setCellValue(e.getUser_name());
			sheet.getRow(i).createCell(1).setCellValue(jsoft.library.Utilities.decode(e.getUser_fullname()));
			sheet.getRow(i).createCell(2).setCellValue(e.getUser_logined());
			i++;
		}
		
		Desktop desktop = Desktop.getDesktop();
		String path = "D:\\user_"+LocalDate.now()+"_by_"+user.getUser_name()+".xls";
		File file = new File(path);
		try {
			workbook.write(file);
			desktop.open(file);
			workbook.close();
			response.sendRedirect("/adv/user/list?success");
		} catch (IOException e) {
			response.sendRedirect("/adv/user/list?err=add");
			// TODO Auto-generated catch block
			e.printStackTrace();
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

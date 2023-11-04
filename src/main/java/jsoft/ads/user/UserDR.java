package jsoft.ads.user;

import java.io.IOException;
import jsoft.library.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsoft.ConnectionPool;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class UserDR
 */
@WebServlet("/user/dr")
public class UserDR extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDR() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = (jsoft.library.Utilities.getIntParam(request, "id"));
		int page = (jsoft.library.Utilities.getIntParam(request, "page"));
		if(page<=0) {
			page = 1;
		}
		int pid = (jsoft.library.Utilities.getIntParam(request, "pid"));// tai khoan cha
		// tìm thông tin đăng nhập
				UserObject user = (UserObject) request.getSession().getAttribute("userLogined");
		        
				if (user != null && id>0) {
					if(user.getUser_id()!=id) {
						if(user.getUser_permission() >= 4 && user.getUser_id() != pid) {
							ConnectionPool cp = (ConnectionPool)getServletContext().getAttribute("CPool");
							UserControl uc = new UserControl(cp);
							UserObject dUser = new UserObject();
							dUser.setUser_id(id);
							dUser.setUser_parent_id(pid);
							dUser.setUser_last_modified(Utilities_date.getDate());
							// tim tham so
							String trash =request.getParameter("t");
							String restore =request.getParameter("r");
							String url = "/adv/user/list?page="+page;
							boolean delResult;
							if(trash == null) {
								delResult = uc.delUser(dUser);	
								url +="&trash"; 
							} else {
								if(restore==null) {
									delResult = uc.editUser(dUser, USER_EDIT_TYPE.TRASH);
								} else {
									delResult = uc.editUser(dUser, USER_EDIT_TYPE.RESTORE);
								}
								
							}
							
							uc.releaseConnection();
							
							if(delResult) {
								response.sendRedirect(url);
							} else {
								response.sendRedirect("/adv/user?err=notok&page="+page);
							}
						} else {
							response.sendRedirect("/adv/user/list?err=nopermis&page="+page);
						}
					} else {
						response.sendRedirect("/adv/user/list?err=acclogin&page="+page);
					}
				} else {
					response.sendRedirect("/adv/user/list?err=del&page="+page);
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

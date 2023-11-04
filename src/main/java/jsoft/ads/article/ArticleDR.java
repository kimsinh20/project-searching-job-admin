package jsoft.ads.article;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsoft.ConnectionPool;
import jsoft.library.Utilities_date;
import jsoft.objects.ArticleObject;
import jsoft.objects.CategoryObject;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class SectionDR
 */
@WebServlet("/ar/dr")
public class ArticleDR extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ArticleDR() {
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
		short id = (jsoft.library.Utilities.getShortParam(request, "id"));
		int page = (jsoft.library.Utilities.getIntParam(request, "page"));
		if (page <= 0) {
			page = 1;
		}
		// tìm thông tin đăng nhập
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

		System.out.println(id);
		
		if (user != null && id > 0) {
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			ArticleControl uc = new ArticleControl(cp);
			ArticleObject dAr = new ArticleObject();
			dAr.setArticle_id(id);
			dAr.setArticle_last_modified(Utilities_date.getDate());
			// tim tham so
			String trash = request.getParameter("t");
			String restore = request.getParameter("r");
			String url = "/adv/ar/list?page=" + page;
			boolean delResult;
			if (trash == null) {
				delResult = uc.delArticle(dAr);
				url += "&trash";
			} else {
				if (restore == null) {
					delResult = uc.editArticle(dAr, ARTICLE_EDIT_TYPE.TRASH);
				} else {
					delResult = uc.editArticle(dAr, ARTICLE_EDIT_TYPE.RESTORE);
				}
			}

			uc.releaseConnection();

			if (delResult) {
				response.sendRedirect(url);
			} else {
				response.sendRedirect("/adv/article?err=notok");
			}
		} else {
			response.sendRedirect("/adv/article/list?err=nopermis");
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

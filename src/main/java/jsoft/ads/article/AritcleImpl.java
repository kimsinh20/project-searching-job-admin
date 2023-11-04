package jsoft.ads.article;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.ConnectionPoolImpl;
import jsoft.ads.basic.BasicImpl;
import jsoft.ads.category.CATEGORY_SOFT;
import jsoft.library.ORDER;
import jsoft.objects.ArticleObject;
import jsoft.objects.CategoryObject;
import jsoft.objects.UserObject;

public class AritcleImpl extends BasicImpl implements Article {

	public AritcleImpl(ConnectionPool cp) {
		super(cp, "Article");
	}

	@Override
	public boolean addArticle(ArticleObject item) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO tblArticle(");
		sql.append("article_title, article_summary, article_content, article_created_date, ");
		sql.append("article_last_modified, article_image, article_category_id, article_section_id, article_visited, ");
		sql.append("article_author_name, article_enable, article_url_link, article_tag, article_title_en, ");
		sql.append("article_summary_en, article_content_en, article_tag_en, article_fee, article_isfee, ");
		sql.append("article_delete, article_deleted_date, article_restored_date, article_modified_author_name, article_author_permission, ");
		sql.append("article_source, article_language, article_focus, article_type, article_forhome");
		sql.append(")");
		sql.append("VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getArticle_title());
			pre.setString(2, item.getArticle_summary());
			pre.setString(3, item.getArticle_content());
			pre.setString(4, item.getArticle_created_date());
			pre.setString(5, item.getArticle_last_modified());
			pre.setString(6,item.getArticle_image());
			pre.setShort(7, item.getArticle_category_id());
			pre.setShort(8, item.getArticle_section_id());
			pre.setShort(9, item.getArticle_visited());
			pre.setString(10, item.getArticle_author_name());
			pre.setBoolean(11, item.isArticle_enable());
			pre.setString(12, item.getArticle_url_link());
			pre.setString(13, item.getArticle_tag());
			pre.setString(14, item.getArticle_title_en());
			pre.setString(15, item.getArticle_summary_en());
			pre.setString(16, item.getArticle_content_en());
			pre.setString(17, item.getArticle_tag_en());
			pre.setInt(18, item.getArticle_fee());
			pre.setBoolean(19, item.isArticle_isfee());
			pre.setBoolean(20, item.isArticle_delete());
			pre.setString(21, item.getArticle_deleted_date());
			pre.setString(22, item.getArticle_restored_date());
			pre.setString(23, item.getArticle_modified_author_name());
			pre.setByte(24, item.getArticle_author_permission());
			pre.setString(25, item.getArticle_source());
			pre.setByte(26, item.getArticle_language());
			pre.setBoolean(27, item.isArticle_focus());
			pre.setByte(28, item.getArticle_type());
			pre.setBoolean(29, item.isArticle_forhome());
			return this.add(pre);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean editArticle(ArticleObject item,ARTICLE_EDIT_TYPE et) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tblarticle SET ");
		switch(et) {
		case GENERAL:
			sql.append("article_title=?, article_summary=?, article_content=? ,");
			sql.append("article_last_modified=?, article_image=?, article_category_id=?, article_section_id=?, ");
			sql.append("article_author_name=?, article_enable=?, article_url_link=?, article_tag=?, article_title_en=?, ");
			sql.append("article_summary_en =?, article_content_en=?, article_tag_en=?, article_fee=?, article_isfee=?, ");
			sql.append("article_modified_author_name =?, article_author_permission =?, article_source=?,");
		    sql.append("article_language=?, article_focus=?, article_type=?, article_forhome=? ");
			break;
		case TRASH:
			sql.append("article_delete= 1, article_last_modified=?,article_deleted_date=? ");
			break;
		case RESTORE :
			sql.append("article_delete= 0, article_last_modified=?,article_restored_date=? ");
			break;
		}
		sql.append(" WHERE article_id=?;");
		System.out.println(sql);
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			switch (et) {
			case GENERAL: 
			    pre.setString(1, item.getArticle_title());
			    pre.setString(2, item.getArticle_summary());
			    pre.setString(3, item.getArticle_content());
			    pre.setString(4, item.getArticle_last_modified());
			    pre.setString(5, item.getArticle_image());
			    pre.setInt(6, item.getArticle_category_id());
			    pre.setInt(7, item.getArticle_section_id());
			    pre.setString(8, item.getArticle_author_name());
			    pre.setBoolean(9, item.isArticle_enable());
			    pre.setString(10, item.getArticle_url_link());
			    pre.setString(11, item.getArticle_tag());
			    pre.setString(12, item.getArticle_title_en());
			    pre.setString(13, item.getArticle_summary_en());
			    pre.setString(14, item.getArticle_content_en());
			    pre.setString(15, item.getArticle_tag_en());
			    pre.setInt(16, item.getArticle_fee());
			    pre.setBoolean(17, item.isArticle_isfee());
			    pre.setString(18, item.getArticle_modified_author_name());
			    pre.setByte(19, item.getArticle_author_permission());
			    pre.setString(20, item.getArticle_source());
			    pre.setByte(21, item.getArticle_language());
			    pre.setBoolean(22, item.isArticle_focus());
			    pre.setByte(23, item.getArticle_type());
			    pre.setBoolean(24, item.isArticle_forhome());
				pre.setInt(25, item.getArticle_id());
				break;
			case TRASH:
				pre.setString(1, item.getArticle_last_modified());
				pre.setString(2, item.getArticle_deleted_date());
				pre.setInt(3, item.getArticle_id());
				break;
			case RESTORE:
				pre.setString(1, item.getArticle_last_modified());
				pre.setString(2, item.getArticle_deleted_date());
				pre.setInt(3, item.getArticle_id());
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + et);
			}
			

			return this.edit(pre);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean delArticle(ArticleObject item) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM tblarticle WHERE (article_id=?);");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getArticle_id());
			return this.del(pre);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// tro ve trang thai an toan cua ket noi
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		return false;
	}
	
	

	@Override
	public ArrayList<ResultSet> getArticle(int id) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM tblArticle WHERE article_id = ").append(id);
		sql.append(" ; ");
		sql.append("SELECT * FROM tblcategory AS c ");
		sql.append("LEFT JOIN tblsection AS s ON c.category_section_id = s.section_id ");
		sql.append("WHERE (category_delete = 0) AND (section_delete=0);");
		return this.getMR(sql.toString());
	}

	@Override
	public ArrayList<ResultSet> getArticles(Quartet<ArticleObject, Integer, Byte,UserObject> infos) {
		// TODO Auto-generated method stub
		
		// đối tượng lưu trữ thông tin lọc kết quả
		ArticleObject similar = infos.getValue0();
		
		// vị trí bắt đầu bản ghi
		int at = infos.getValue1();
		
		// số bản ghi đc lấy
		byte total = infos.getValue2();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT * FROM tblArticle a ");
		sql.append("LEFT JOIN tblcategory c ON c.category_id = a.article_category_id ");
		sql.append("LEFT JOIN tblsection s ON c.category_section_id = s.section_id ");
		sql.append(this.createConditions(similar));
		sql.append("ORDER BY article_id DESC ");
		sql.append("LIMIT " + at + ", " + total + ";");
		
		sql.append("SELECT COUNT(article_id) AS total FROM tblArticle");
		sql.append(this.createConditions(similar));
		sql.append(";");
		
		// thong ke
		sql.append("select YEAR(str_to_date(article_last_modified,'%d/%m/%y')) as 'year', MONTH(str_to_date(article_last_modified,'%d/%m/%y')) as 'month',sum(article_visited) as 'totalview' ");
		sql.append("from tblarticle group by YEAR(str_to_date(article_last_modified,'%d/%m/%y')), MONTH(str_to_date(article_last_modified,'%d/%m/%y')) ");
		sql.append("order by YEAR(str_to_date(article_last_modified,'%d/%m/%y')), MONTH(str_to_date(article_last_modified,'%d/%m/%y')) asc;");
		
		System.out.println(sql);
		return this.getMR(sql.toString());
	}
	private String createConditions(ArticleObject similar) {
		StringBuffer conds = new StringBuffer();
		if (similar != null) {
			if(similar.isArticle_delete()) {
				conds.append(" (article_delete=1)");
			} else {
				conds.append(" (article_delete=0) ");
			}
			// tu khoa tim kiem
			String key = similar.getArticle_title();
			if(key!=null && !key.equalsIgnoreCase("")) {
				conds.append(" AND ((article_title LIKE '%"+key+"%') OR ");
				conds.append("(article_summary LIKE '%"+key+"%') ");
//				conds.append("(user_name LIKE '%"+key+"%') ");
				conds.append(")");
			} 
				
		}
		if (!conds.toString().equalsIgnoreCase("")) {
			conds.insert(0, " WHERE ");
		}
		return conds.toString();
	}

	public static void main(String[] args) {
		// tạo bộ quản lý kết nối
		ConnectionPool cp = new ConnectionPoolImpl();

		// đối tượng thực thi chức năng mức interface
		Article a = new AritcleImpl(cp);
		
		UserObject u = new UserObject();
		
		Quartet<ArticleObject, Integer, Byte,UserObject> infos = new Quartet<>(null, 0, (byte) 15,u);
		
		ArrayList<ResultSet> res = a.getArticles(infos);
		
		ResultSet rs = res.get(0);
		
		String row;
		
		if(rs != null) {
			try {
				while(rs.next()) {
					row = "ID: " + rs.getInt("article_id");
					row += "\tTITLE: " + rs.getString("article_title");
					System.out.println(row);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		rs = res.get(1);
		if(rs != null) {
			try {
				while(rs.next()) {
					System.out.println("Tong so bai bao trong DB: " + rs.getShort("total"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public ArrayList<ResultSet> getCategories(Quartet<CategoryObject, Integer, Byte, UserObject> infos,
			Pair<CATEGORY_SOFT, ORDER> so) {
		// TODO Auto-generated method stub
		CategoryObject similar = infos.getValue0();

		// vị trí bắt đầu lấy bản ghi
		int at = infos.getValue1();

		// Số bản ghi được lấy trong một lần
		byte total = infos.getValue2();
		
		// tai khoan dang nhap
		UserObject user = infos.getValue3();

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT * FROM tblcategory AS c ");
		sql.append("LEFT JOIN tblsection AS s ON c.category_section_id = s.section_id ");
		sql.append("WHERE (category_delete = 0) ");
		switch (so.getValue0()) {
		case GENERAL:
			sql.append("ORDER BY c.category_id DESC ");
			break;
		case NAME:
			sql.append("ORDER BY c.category_name ").append(so.getValue1().name());
			break;
		case MANAGER:
			sql.append("ORDER BY c.category_manager_id  ").append(so.getValue1().name());
			break;
		default:
			sql.append("ORDER BY c.category_id DESC ");

		}
		sql.append(" LIMIT ").append(at).append(", ").append(total).append(";");

		System.out.println(sql.toString());
		
		return this.getMR(sql.toString());
	}
}


package jsoft.ads.article;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.ConnectionPoolImpl;
import jsoft.ads.basic.BasicImpl;
import jsoft.objects.ArticleObject;

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
			Array listImages = this.con.createArrayOf("VARCHAR", item.getArticle_image().toArray());
			pre.setArray(6, listImages);
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
	public boolean editArticle(ArticleObject item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delArticle(ArticleObject item) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean isEmptyArticleObject(ArticleObject item) {
		boolean flag = true;
		
		return flag;
	}

	@Override
	public ResultSet getArticle(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM tblArticle WHERE article_id = ?";
		return this.get(sql, id);
	}

	@Override
	public ArrayList<ResultSet> getArticles(Triplet<ArticleObject, Integer, Byte> infos) {
		// TODO Auto-generated method stub
		
		// đối tượng lưu trữ thông tin lọc kết quả
		ArticleObject similar = infos.getValue0();
		
		// vị trí bắt đầu bản ghi
		int at = infos.getValue1();
		
		// số bản ghi đc lấy
		byte total = infos.getValue2();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT * FROM tblArticle ");
		sql.append("ORDER BY article_title ASC ");
		sql.append("LIMIT " + at + ", " + total + ";");
		
		sql.append("SELECT COUNT(article_id) AS total FROM tblArticle");
		return this.getMR(sql.toString());
	}

	public static void main(String[] args) {
		// tạo bộ quản lý kết nối
		ConnectionPool cp = new ConnectionPoolImpl();

		// đối tượng thực thi chức năng mức interface
		Article a = new AritcleImpl(cp);
		
		Triplet<ArticleObject, Integer, Byte> infos = new Triplet<>(null, 0, (byte) 15);
		
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
}


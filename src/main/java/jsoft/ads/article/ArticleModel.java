package jsoft.ads.article;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Sextet;

import jsoft.ConnectionPool;
import jsoft.ads.category.CATEGORY_EDIT_TYPE;
import jsoft.ads.category.CATEGORY_SOFT;
import jsoft.library.ORDER;
import jsoft.objects.ArticleObject;
import jsoft.objects.CategoryObject;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

public class ArticleModel {
	private Article a;
	public ArticleModel(ConnectionPool cp) {
		this.a = new AritcleImpl(cp);
	}
	
	public ConnectionPool getCP() {
		return this.a.getCP();
	}

	public void releaseConnection() {
		this.a.releaseConnection();
	}
	
//	---------------------------------------
	public boolean addArticle(ArticleObject item) {
		return this.a.addArticle(item);
	}
	
	public boolean editArticle(ArticleObject item,ARTICLE_EDIT_TYPE et) {
		return this.a.editArticle(item,et);
	}
	
	public boolean delArticle(ArticleObject item) {
		return this.a.delArticle(item);
	}
	
//	----------------------------------------
	public Pair<ArticleObject, ArrayList<CategoryObject>> getArticle(int id,UserObject user) {
		
		ArticleObject item = null;
		
        ArrayList<ResultSet> res = this.a.getArticle(id);
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				if(rs.next()) {
					item = new ArticleObject();
					item.setArticle_id(rs.getInt("article_id"));
					item.setArticle_visited(rs.getShort("article_visited"));
					item.setArticle_title(rs.getString("article_title"));
//					manager_name.put(rs.getInt("category_manager_id"), rs.getString("user_fullname")+"("+rs.getString("user_name")+")");
					item.setArticle_summary(rs.getString("article_summary"));
					item.setArticle_created_date(rs.getString("article_created_date"));
					item.setArticle_author_name(rs.getString("article_author_name"));
					item.setArticle_delete(rs.getBoolean("article_delete"));
					item.setArticle_enable(rs.getBoolean("article_enable"));
					item.setArticle_image(rs.getString("article_image"));
					item.setArticle_content(jsoft.library.Utilities.decode(rs.getString("article_content")));
					item.setArticle_last_modified(rs.getString("article_last_modified"));
					item.setArticle_source(rs.getString("article_source"));
					item.setArticle_tag(jsoft.library.Utilities.decode(rs.getString("article_tag")));
					item.setArticle_category_id(rs.getShort("article_category_id"));
					item.setArticle_section_id(rs.getShort("article_section_id"));
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ArrayList<CategoryObject> items = new ArrayList<>();
		CategoryObject cate = null;
		
		rs = res.get(1);
		if(rs!=null) {
			try {
				while(rs.next()) {
					cate = new CategoryObject();
					cate.setCategory_id(rs.getShort("category_id"));
					cate.setCategory_name(rs.getString("category_name"));
					cate.setCategory_created_date(rs.getString("category_created_date"));
					cate.setCategory_created_author_id(rs.getShort("category_created_author_id"));
					cate.setCategory_delete(rs.getBoolean("category_delete"));
					cate.setCategory_enable(rs.getBoolean("category_enable"));
					cate.setCategory_image(rs.getString("category_image"));
					cate.setCategory_last_modified(rs.getString("category_last_modified"));
					cate.setCategory_manager_id(rs.getShort("category_manager_id"));
					cate.setCategory_notes(rs.getString("category_notes"));
					cate.setCategory_section_id(rs.getShort("category_section_id"));
					cate.setCategory_section_name(rs.getString("section_name"));
					items.add(cate);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new Pair<>(item, items);
	}
	public ArrayList<CategoryObject> getCategories(Quartet<CategoryObject, Integer, Byte,UserObject> infos,Pair<CATEGORY_SOFT, ORDER> so){
		ArrayList<CategoryObject> items = new ArrayList<>();
		CategoryObject item = null;
		
		ArrayList<ResultSet> res = this.a.getCategories(infos, so);
		
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				while(rs.next()) {
					item = new CategoryObject();
					item.setCategory_id(rs.getShort("category_id"));
					item.setCategory_name(rs.getString("category_name"));
					item.setCategory_created_date(rs.getString("category_created_date"));
					item.setCategory_created_author_id(rs.getShort("category_created_author_id"));
					item.setCategory_delete(rs.getBoolean("category_delete"));
					item.setCategory_enable(rs.getBoolean("category_enable"));
					item.setCategory_image(rs.getString("category_image"));
					item.setCategory_last_modified(rs.getString("category_last_modified"));
					item.setCategory_manager_id(rs.getShort("category_manager_id"));
					item.setCategory_notes(rs.getString("category_notes"));
					item.setCategory_section_id(rs.getShort("category_section_id"));
					item.setCategory_section_name(rs.getString("section_name"));
					items.add(item);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return items;
	}
	public Sextet<ArrayList<ArticleObject>, Short,HashMap<Integer, String>,ArrayList<UserObject>,HashMap<String, Integer>,ArrayList<CategoryObject>> getArticleObjects
	(Quartet<ArticleObject, Integer, Byte, UserObject> infos) {
		ArrayList<ArticleObject> items = new ArrayList<>();
		ArticleObject item = null;
		
        HashMap<Integer, String> manager_name = new HashMap<>();
		
		HashMap<String, Integer> datas = new HashMap<>();
		
		ArrayList<ResultSet> res = this.a.getArticles(infos);
		
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				while(rs.next()) {
					item = new ArticleObject();
					item.setArticle_id(rs.getInt("article_id"));
					item.setArticle_visited(rs.getShort("article_visited"));
					item.setArticle_title(rs.getString("article_title"));
//					manager_name.put(rs.getInt("category_manager_id"), rs.getString("user_fullname")+"("+rs.getString("user_name")+")");
					item.setArticle_summary(rs.getString("article_summary"));
					item.setArticle_created_date(rs.getString("article_created_date"));
					item.setArticle_author_name(rs.getString("article_author_name"));
					item.setArticle_delete(rs.getBoolean("article_delete"));
					item.setArticle_enable(rs.getBoolean("article_enable"));
					item.setArticle_image(rs.getString("article_image"));
					
					item.setArticle_last_modified(rs.getString("article_last_modified"));
					item.setArticle_source(rs.getString("article_source"));
					item.setArticle_category_id(rs.getShort("article_category_id"));
					item.setArticle_section_id(rs.getShort("article_section_id"));
					items.add(item);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		rs = res.get(1);
		short total = 0;
		if(rs!= null) {
			try {
				if(rs.next()) {
					total = rs.getShort("total");
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		rs = res.get(2);
		ArrayList<UserObject> users = new ArrayList<>();
		UserObject user = null;
//		if(rs!=null) {
//			try {
//				while(rs.next()) {
//					user =new UserObject();
//					user.setUser_id(rs.getInt("user_id"));
//					user.setUser_name(rs.getString("user_name"));
//					user.setUser_fullname(rs.getString("user_fullname"));
//					users.add(user);
//				}
//				rs.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		rs = res.get(2);
		
		if(rs!=null) {
			try {
				while(rs.next()) {
					int year = rs.getInt("year");
					int month = rs.getInt("month");
					String time = month+"-"+year;
					datas.put(time,rs.getInt("totalview"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		rs = res.get(4);
		ArrayList<CategoryObject> cates = new ArrayList<>();
		CategoryObject cate = null;
//		if(rs!=null) {
//			try {
//				while(rs.next()) {
//					cate =new CategoryObject();
//					cate.setCategory_id(rs.getShort("category_id"));
//					cate.setCategory_name(rs.getString("scategory_name"));
//					cate.setCategory_manager_id(rs.getShort("category_manager_id"));
//					cates.add(cate);
//				}
//				rs.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		return new Sextet<>(items, total, manager_name, users, datas, cates);
	}
}

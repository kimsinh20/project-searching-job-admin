package jsoft.ads.category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Sextet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.CategoryObject;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

public class CategoryModel {
	private Category c;
	public CategoryModel(ConnectionPool cp) {
		this.c = new CategoryImpl(cp);
	}
	
	public ConnectionPool getCP() {
		return this.c.getCP();
	}

	public void releaseConnection() {
		this.c.releaseConnection();
	}
	
//	---------------------------------------
	public boolean addCategory(CategoryObject item) {
		return this.c.addCategory(item);
	}
	
	public boolean editCategory(CategoryObject item,CATEGORY_EDIT_TYPE et) {
		return this.c.editCategory(item,et);
	}
	
	public boolean delCategory(CategoryObject item) {
		return this.c.delCategory(item);
	}
	
//	----------------------------------------
	
	public Quintet<CategoryObject, HashMap<Integer, String>,HashMap<Integer, String>,ArrayList<UserObject>,ArrayList<SectionObject>> getCategoryObject(short id, UserObject userLogined) {
		CategoryObject item = null;
		HashMap<Integer, String> manager_name = new HashMap<>();
		HashMap<Integer, String> author_name = new HashMap<>();
		HashMap<Integer, String> categorySection = new HashMap<>();
		ArrayList<ResultSet> res= this.c.getCategory(id, userLogined);
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				if(rs.next()) {
					item = new CategoryObject();
					System.out.println("a"+rs.getString("user_name"));
					item.setCategory_section_name(rs.getString("section_name"));
					manager_name.put(rs.getInt("category_manager_id"), rs.getString("user_fullname")+"("+rs.getString("user_name")+")");
					author_name.put(rs.getInt("category_created_author_id"), rs.getString("user_fullname")+"("+rs.getString("user_name")+")");
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
					
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rs = res.get(1);
		ArrayList<UserObject> users = new ArrayList<>();
		UserObject user = null;
		if(rs!=null) {
			try {
				while(rs.next()) {
					user =new UserObject();
					user.setUser_id(rs.getInt("user_id"));
					user.setUser_name(rs.getString("user_name"));
					user.setUser_fullname(rs.getString("user_fullname"));
					users.add(user);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rs = res.get(2);
		ArrayList<SectionObject> sections = new ArrayList<>();
		SectionObject section = null;
		if(rs!=null) {
			try {
				while(rs.next()) {
					section =new SectionObject();
					section.setSection_id(rs.getShort("section_id"));
					section.setSection_name(rs.getString("section_name"));
					section.setSection_manager_id(rs.getShort("section_manager_id"));
					sections.add(section);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new Quintet<>(item,manager_name,author_name,users,sections);
	}
	
	public Sextet<ArrayList<CategoryObject>, Short,HashMap<Integer, String>,ArrayList<UserObject>,HashMap<Integer, String>,ArrayList<SectionObject>> getCategoryObjects
	(Quartet<CategoryObject, Integer, Byte,UserObject> infos, Pair<CATEGORY_SOFT, ORDER> so) {
		ArrayList<CategoryObject> items = new ArrayList<>();
		CategoryObject item = null;
		
        HashMap<Integer, String> manager_name = new HashMap<>();
		
		HashMap<Integer, String> datas = new HashMap<>();
		
		ArrayList<ResultSet> res = this.c.getCategories(infos, so);
		
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				while(rs.next()) {
					item = new CategoryObject();
					item.setCategory_id(rs.getShort("category_id"));
					manager_name.put(rs.getInt("category_manager_id"), rs.getString("user_fullname")+"("+rs.getString("user_name")+")");
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
					item.setCategory_permis(rs.getShort("user_permission"));
					item.setCategory_section_name(rs.getString("section_name"));
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
		
		rs = res.get(2);
		ArrayList<UserObject> users = new ArrayList<>();
		UserObject user = null;
		if(rs!=null) {
			try {
				while(rs.next()) {
					user =new UserObject();
					user.setUser_id(rs.getInt("user_id"));
					user.setUser_name(rs.getString("user_name"));
					user.setUser_fullname(rs.getString("user_fullname"));
					users.add(user);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rs = res.get(3);
		
		if(rs!=null) {
			try {
				while(rs.next()) {
					datas.put(rs.getInt("total_category"),rs.getString("section_name"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rs = res.get(4);
		ArrayList<SectionObject> sections = new ArrayList<>();
		SectionObject section = null;
		if(rs!=null) {
			try {
				while(rs.next()) {
					section =new SectionObject();
					section.setSection_id(rs.getShort("section_id"));
					section.setSection_name(rs.getString("section_name"));
					section.setSection_manager_id(rs.getShort("section_manager_id"));
					sections.add(section);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new Sextet<>(items, total,manager_name,users,datas,sections);
	}
}

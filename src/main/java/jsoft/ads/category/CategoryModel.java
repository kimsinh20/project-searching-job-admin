package jsoft.ads.category;

import jsoft.*;
import jsoft.library.ORDER;
import jsoft.objects.*;

import java.sql.*;
import java.util.*;

import org.javatuples.Pair;
import org.javatuples.Triplet;

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
	
	public boolean editCategory(CategoryObject item) {
		return this.c.editCategory(item);
	}
	
	public boolean delCategory(CategoryObject item) {
		return this.c.delCategory(item);
	}
	
//	----------------------------------------
	
	public CategoryObject getCategoryObject(short id, UserObject userLogined) {
		CategoryObject item = null;
		ResultSet rs = this.c.getCategory(id, userLogined);
		if(rs!=null) {
			try {
				if(rs.next()) {
					item = new CategoryObject();
					item.setCategory_id(rs.getShort("category_id"));
					item.setCategory_name(rs.getString("category_name"));
					item.setCategory_created_date(rs.getString("category_created_date"));
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return item;
	}
	
	public Pair<ArrayList<CategoryObject>, Short> getCategoryObjects(Triplet<CategoryObject, Integer, Byte> infos, Pair<CATEGORY_SOFT, ORDER> so) {
		ArrayList<CategoryObject> items = new ArrayList<>();
		CategoryObject item = null;
		
		ArrayList<ResultSet> res = this.c.getCategories(infos, so);
		
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				while(rs.next()) {
					item = new CategoryObject();
					item.setCategory_id(rs.getShort("category_id"));
					item.setCategory_name(rs.getString("category_name"));
					item.setCategory_created_date(rs.getString("category_created_date"));
					
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
		
		return new Pair<>(items, total);
	}
}

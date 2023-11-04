package jsoft.ads.category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.ConnectionPoolImpl;
import jsoft.ads.basic.BasicImpl;
import jsoft.library.ORDER;
import jsoft.objects.CategoryObject;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

public class CategoryImpl extends BasicImpl implements Category {

	public CategoryImpl(ConnectionPool cp) {
		super(cp, "Category");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean addCategory(CategoryObject item) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tblcategory (");
		sql.append("category_name, category_section_id, category_notes, category_created_date, category_created_author_id, category_last_modified, category_manager_id, category_enable, category_delete, category_image,category_name_en,category_language");
		sql.append(")");
		sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");
		sql.append("");
		
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());

			pre.setString(1, item.getCategory_name());
			pre.setInt(2, item.getCategory_section_id());
			pre.setString(3, item.getCategory_notes());
			pre.setString(4, item.getCategory_created_date());
			pre.setInt(5, item.getCategory_created_author_id());
			pre.setString(6, item.getCategory_last_modified());
			pre.setInt(7, item.getCategory_manager_id());
			pre.setBoolean(8, item.isCategory_enable());
			pre.setBoolean(9, item.isCategory_delete());
			pre.setString(10, item.getCategory_image());
			pre.setString(11, item.getCategory_name_en());
			pre.setByte(12, item.getCategory_language());

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
	public boolean editCategory(CategoryObject item,CATEGORY_EDIT_TYPE et) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tblcategory SET ");
		switch(et) {
		case GENERAL:
			sql.append("category_name=?, category_notes=?, category_manager_id=?, ");
			sql.append("category_enable=?, category_last_modified=?, ");
			sql.append("category_name_en=?, category_language=? ");
			break;
		case TRASH:
			sql.append("category_delete= 1, category_last_modified=? ");
			break;
		case RESTORE :
			sql.append("category_delete= 0, category_last_modified=? ");
			break;
		}
		sql.append(" WHERE category_id=?;");
		System.out.println(sql);
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			switch (et) {
			case GENERAL: 
				pre.setString(1, item.getCategory_name());
				pre.setString(2, item.getCategory_notes());
				pre.setInt(3, item.getCategory_manager_id());
				pre.setBoolean(4, item.isCategory_enable());
				pre.setString(5, item.getCategory_last_modified());
				pre.setString(6, item.getCategory_name_en());
				pre.setInt(7, item.getCategory_language());
				pre.setInt(8, item.getCategory_id());
				break;
			case TRASH:
				pre.setString(1, item.getCategory_last_modified());
				pre.setInt(2, item.getCategory_id());
				break;
			case RESTORE:
				pre.setString(1, item.getCategory_last_modified());
				pre.setInt(2, item.getCategory_id());
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
	public boolean delCategory(CategoryObject item) {
		// TODO Auto-generated method stub
		if (!this.isEmpty(item)) {
			return false;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM tblcategory WHERE (category_id=?);");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setShort(1, item.getCategory_id());
//			pre.setInt(2, item.getCategory_created_author_id());
//			pre.setInt(3, item.getCategory_manager_id());
//            System.out.println(sql);
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

	private boolean isEmpty(CategoryObject item) {
		boolean flag = true;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblarticle WHERE article_category_id='").append(item.getCategory_id()).append("';");
		
		ArrayList<ResultSet> res = this.getMR(sql.toString());
        System.out.println(sql);
		for (ResultSet rs : res) {
			if (rs != null) {
				try {
					if (rs.next()) {
						flag = false;
						break;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return flag;
	}


	@Override
	public ArrayList<ResultSet> getCategory(short id, UserObject userLogined) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblcategory c "); // cate la con cua section
		sql.append("LEFT JOIN tblsection s ON c.category_section_id = s.section_id ");
		sql.append("LEFT JOIN tbluser AS u ON c.category_manager_id = u.user_id ");
		sql.append("WHERE (c.category_id="+id+");"); 
		// danh sách người sử dụng đc cấp quyền ql phụ thuộc vào tk đăng nhập
		sql.append("SELECT * FROM tbluser WHERE ");
        sql.append("(user_permission<=").append(userLogined.getUser_permission()).append(" ) AND (");
		sql.append("(user_parent_id=").append(userLogined.getUser_id()).append(") OR (user_id=").append(userLogined.getUser_id()).append(")");
		sql.append(");");
		sql.append("SELECT * FROM tblsection AS s  ");
		sql.append("WHERE (section_delete=0);");
		System.out.println(sql);
		
		return this.getMR(sql.toString());
	}

	@Override
	public ArrayList<ResultSet> getCategories(Quartet<CategoryObject, Integer, Byte,UserObject> infos, Pair<CATEGORY_SOFT, ORDER> so) {
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
		sql.append("LEFT JOIN tbluser AS u ON c.category_manager_id = u.user_id ");
		sql.append(this.createConditions(similar));
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

		sql.append("SELECT COUNT(category_id) AS total FROM tblcategory");
		sql.append(createConditions(similar));
		sql.append(";");
		
		// danh sách người sử dụng đc cấp quyền ql phụ thuộc vào tk đăng nhập
		sql.append("SELECT * FROM tbluser WHERE ");
        sql.append("(user_permission<=").append(user.getUser_permission()).append(" ) AND (");
		sql.append("(user_parent_id=").append(user.getUser_id()).append(") OR (user_id=").append(user.getUser_id()).append(")");
		sql.append(");");
		
		// thong ke danh sach cac tai khoan quan ly cac danh muc 
		sql.append("SELECT section_name,category_section_id,count(*) as 'total_category' FROM tblcategory AS c LEFT JOIN tblsection AS s ON c.category_section_id = s.section_id WHERE (section_delete=0) group by category_section_id ORDER BY total_category DESC; ");
		
		sql.append("SELECT * FROM tblsection AS s  ");
		sql.append("WHERE (section_delete=0);");
		System.out.println(sql.toString());
		
		return this.getMR(sql.toString());
	}

	private String createConditions(CategoryObject similar) {
		StringBuffer conds = new StringBuffer();
		if (similar != null) {
			if(similar.isCategory_delete()) {
				conds.append(" (category_delete=1)");
			} else {
				conds.append(" (category_delete=0) ");
			}
			// tu khoa tim kiem
			String key = similar.getCategory_name();
			if(key!=null && !key.equalsIgnoreCase("")) {
				conds.append(" AND ((category_name LIKE '%"+key+"%') OR ");
				conds.append("(category_notes LIKE '%"+key+"%') ");
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
		Category c = new CategoryImpl(cp);
		UserObject user =new UserObject();
		user.setUser_id(1);
		
		Quartet<CategoryObject, Integer, Byte,UserObject> infos = new Quartet<>(null, 0, (byte) 6,user);

		ArrayList<ResultSet> res = c.getCategories(infos, new Pair<>(CATEGORY_SOFT.NAME, ORDER.DESC));

		ResultSet rs = res.get(0);

		String row;

		if (rs != null) {
			try {
				while (rs.next()) {
					row = "ID: " + rs.getInt("category_id");
					row += "\tNAME: " + rs.getString("category_name");
					row += "\tCATEGORY_SECTION_ID: " + rs.getInt("category_section_id");
					System.out.println(row);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		rs = res.get(1);
		if (rs != null) {
			try {
				while (rs.next()) {
					System.out.println("Tong so danh muc trong DB:" + rs.getShort("total"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

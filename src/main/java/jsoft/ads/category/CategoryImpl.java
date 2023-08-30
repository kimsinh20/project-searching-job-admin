package jsoft.ads.category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.ConnectionPoolImpl;
import jsoft.ads.basic.BasicImpl;
import jsoft.ads.category.CATEGORY_SOFT;
import jsoft.ads.user.USER_SOFT;
import jsoft.ads.user.User;
import jsoft.ads.user.UserImpl;
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
		sql.append("");
		sql.append("");
		sql.append("");
		sql.append("");
		sql.append("");
		return false;
	}

	@Override
	public boolean editCategory(CategoryObject item) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public boolean delCategory(CategoryObject item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResultSet getCategory(short id, UserObject userLogined) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblcategory c "); // cate la con cua section
		sql.append("LEFT JOIN tblsection s ON c.category_section_id = s.section_id ");
		sql.append("WHERE (c.category_id=?) AND ((c.category_created_author_id =...) OR (c.category_manager_id=...))"); // (ccad OR cmad) ...userLogined.getUser_id
		return this.get(sql.toString(), id);
	}

	@Override
	public ArrayList<ResultSet> getCategories(Triplet<CategoryObject, Integer, Byte> infos, Pair<CATEGORY_SOFT, ORDER> so) {
		// TODO Auto-generated method stub
		CategoryObject similar = infos.getValue0();

		// vị trí bắt đầu lấy bản ghi
		int at = infos.getValue1();

		// Số bản ghi được lấy trong một lần
		byte total = infos.getValue2();

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT * FROM tblcategory AS c ");
		sql.append("LEFT JOIN tblsection AS s ON c.category_section_id = s.section_id ");
		sql.append("LEFT JOIN tbluser AS u ON c.category_manager_id = u.user_id ");
		sql.append("");
		switch (so.getValue0()) {
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

		sql.append("SELECT COUNT(category_id) AS total FROM tblcategory;");

		System.out.println(sql.toString());
		
		return this.getMR(sql.toString());
	}

	public static void main(String[] args) {
		// tạo bộ quản lý kết nối
		ConnectionPool cp = new ConnectionPoolImpl();

		// đối tượng thực thi chức năng mức interface
		Category c = new CategoryImpl(cp);
		
		Triplet<CategoryObject, Integer, Byte> infos = new Triplet<>(null, 0, (byte) 6);

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

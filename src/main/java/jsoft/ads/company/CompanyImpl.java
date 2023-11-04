package jsoft.ads.company;

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
import jsoft.objects.CompanyObject;
import jsoft.objects.UserObject;

public class CompanyImpl extends BasicImpl implements Company {

	public CompanyImpl(ConnectionPool cp) {
		super(cp, "company");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean addCompany(CompanyObject item) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tblcompany (");
		sql.append(
				"`company_name`, `company_summary`, `company_mobilephone`, `company_homephone`, `company_officephone`, `company_logo`, `company_email`, `company_about`, `company_remuneration`, `company_nationality`, `company_size`, `company_work_time`, `company_isOT`, `company_website`,`company_created_date`,`company_last_modified`, `company_author_id`,");
		sql.append("`company_location`,`company_banner`,`company_field_id`) ");
		sql.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		sql.append("");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());

			pre.setString(1, item.getCompany_name());
			pre.setString(2, item.getCompany_summary());
			pre.setString(3, item.getCompany_mobilephone());
			pre.setString(4, item.getCompany_homephone());
			pre.setString(5, item.getCompany_officephone());
			pre.setString(6, item.getCompany_logo());
			pre.setString(7, item.getCompany_email());
			pre.setString(8, item.getCompany_about());
			pre.setString(9, item.getCompany_remuneration());
			pre.setInt(10, item.getCompany_nationality());
			pre.setInt(11, item.getCompany_size());
			pre.setInt(12, item.getCompany_work_time());
			pre.setBoolean(13, item.isCompany_isOT());
			pre.setString(14, item.getCompany_website());
			pre.setString(15, item.getCompany_created_date());
			pre.setString(16, item.getCompany_last_modified());
			pre.setInt(17, item.getCompany_author_id());
			pre.setString(18, item.getCompany_location());
			pre.setString(19, item.getCompany_banner());
			pre.setInt(20, item.getCompany_field_id());

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
	public boolean editCompany(CompanyObject item, COMPANY_EDIT_TYPE et) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tblcompany SET ");
		switch (et) {
		case GENERAL:
			sql.append("`company_name`=?, `company_summary`=?, `company_mobilephone`=?, ");
			sql.append("`company_homephone`=?, `company_officephone`=?, `company_logo`=?, ");
			sql.append("`company_email`=?, `company_about`=?, `company_remuneration`=?, ");
			sql.append("`company_nationality`=?, `company_size`=?, `company_work_time`=?, ");
			sql.append("`company_isOT`=?, `company_website`=?,`company_last_modified`=?,");
			sql.append("`company_banner`=?,`company_field_id`=?,`company_location`=? ");
			break;
		case TRASH:
			sql.append("company_delete= 1, company_last_modified=? ");
			break;
		case RESTORE:
			sql.append("company_delete= 0, company_last_modified=? ");
			break;
		}
		sql.append(" WHERE company_id=?;");
		System.out.println(sql);
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			switch (et) {
			case GENERAL:
				pre.setString(1, item.getCompany_name());
				pre.setString(2, item.getCompany_summary());
				pre.setString(3, item.getCompany_mobilephone());
				pre.setString(4, item.getCompany_homephone());
				pre.setString(5, item.getCompany_officephone());
				pre.setString(6, item.getCompany_logo());
				pre.setString(7, item.getCompany_email());
				pre.setString(8, item.getCompany_about());
				pre.setString(9, item.getCompany_remuneration());
				pre.setInt(10, item.getCompany_nationality());
				pre.setInt(11, item.getCompany_size());
				pre.setInt(12, item.getCompany_work_time());
				pre.setBoolean(13, item.isCompany_isOT());
				pre.setString(14, item.getCompany_website());
				pre.setString(15, item.getCompany_last_modified());
				pre.setString(16, item.getCompany_banner());
				pre.setInt(17, item.getCompany_field_id());
				pre.setString(18, item.getCompany_location());
				pre.setInt(19, item.getCompany_id());
				break;
			case TRASH:
				pre.setString(1, item.getCompany_last_modified());
				pre.setInt(2, item.getCompany_id());
				break;
			case RESTORE:
				pre.setString(1, item.getCompany_last_modified());
				pre.setInt(2, item.getCompany_id());
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
	public boolean delCompany(CompanyObject item) {
		// TODO Auto-generated method stub

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM tblcompany WHERE (company_id=?);");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getCompany_id());
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

	@Override
	public ArrayList<ResultSet> getProvices() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM provinces;"); 
		return this.getMR(sql.toString());
	}
	@Override
	public ArrayList<ResultSet> getditricts(String code) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM districts where province_code='"+code+"';"); 
		return this.getMR(sql.toString());
	}

	@Override
	public ArrayList<ResultSet> getWards(String code) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM wards where district_code='"+code+"';"); 
		return this.getMR(sql.toString());
	}

	@Override
	public ArrayList<ResultSet> getCompany(short id, UserObject userLogined) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblcompany c "); // cate la con cua section
		sql.append("LEFT JOIN tblfield f ON c.company_field_id = f.field_id ");
		sql.append("LEFT JOIN tbluser AS u ON c.company_author_id = u.user_id ");
		sql.append("WHERE (c.company_id=" + id + ");");
		sql.append("SELECT * FROM tblfield AS f  ");
		sql.append("WHERE (f.field_delete=0);");
		System.out.println(sql);

		return this.getMR(sql.toString());
	}

	@Override
	public ArrayList<ResultSet> getCompanies(Quartet<CompanyObject, Integer, Byte, UserObject> infos, Pair<COMPANY_SOFT, ORDER> so) {
		// TODO Auto-generated method stub
		CompanyObject similar = infos.getValue0();

		// vị trí bắt đầu lấy bản ghi
		int at = infos.getValue1();

		// Số bản ghi được lấy trong một lần
		byte total = infos.getValue2();

		// tai khoan dang nhap
		UserObject user = infos.getValue3();

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT * FROM tblcompany AS c ");
		sql.append("LEFT JOIN tbluser AS u ON u.user_id=c.company_author_id ");
		sql.append(this.createConditions(similar));
		switch (so.getValue0()) {
		case GENERAL:
			sql.append("ORDER BY c.company_id DESC ");
			break;
		default:
			sql.append("ORDER BY c.company_id DESC ");

		}
		sql.append(" LIMIT ").append(at).append(", ").append(total).append(";");

		sql.append("SELECT COUNT(company_id) AS total FROM tblcompany");
		sql.append(createConditions(similar));
		sql.append(";");

		sql.append("SELECT * FROM tblfield WHERE field_delete = 0;");

		System.out.println(sql.toString());

		return this.getMR(sql.toString());
	}

	private String createConditions(CompanyObject similar) {
		StringBuffer conds = new StringBuffer();
		if (similar != null) {
			if (similar.isCompany_delete()) {
				conds.append(" (company_delete=1)");
			} else {
				conds.append(" (company_delete=0) ");
			}
			// tu khoa tim kiem
			String key = similar.getCompany_name();
			if (key != null && !key.equalsIgnoreCase("")) {
				conds.append(" AND ((company_name LIKE '%" + key + "%') OR ");
				conds.append("(company_summary LIKE '%" + key + "%') ");
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
		Company c = new CompanyImpl(cp);
		UserObject user = new UserObject();
		user.setUser_id(1);

		Quartet<CompanyObject, Integer, Byte, UserObject> infos = new Quartet<>(null, 0, (byte) 6, user);

		ArrayList<ResultSet> res = c.getCompanies(infos, new Pair<>(COMPANY_SOFT.NAME, ORDER.DESC));

		ResultSet rs = res.get(0);

		String row;

		if (rs != null) {
			try {
				while (rs.next()) {
					row = "ID: " + rs.getInt("company_id");
					row += "\tNAME: " + rs.getString("company_name");
					System.out.println(row);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	
	
}

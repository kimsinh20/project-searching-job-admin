package jsoft.ads.job;

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
import jsoft.objects.JobObject;
import jsoft.objects.SkillObject;
import jsoft.objects.UserObject;

public class JobImpl extends BasicImpl implements Job {

	public JobImpl(ConnectionPool cp) {
		super(cp, "Job");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean addJob(JobObject item) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tbljob (");
		sql.append("job_title, job_purpose, job_quantity, job_responsibility, "
				+ "job_Welfare, job_salary, job_work_time, job_gender, job_level, "
				+ "job_experience_id, job_created_date,job_expiration_date, "
				+ "job_company_id, job_last_modified, job_author_id, job_degree,job_career_id,job_location,job_skills ) ");
		sql.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		sql.append("");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getJob_title());
			pre.setString(2, item.getJob_purpose());
			pre.setInt(3, item.getJob_quantity());
			pre.setString(4, item.getJob_responsibility());
			pre.setString(5, item.getJob_Welfare());
			pre.setInt(6, item.getJob_salary());
			pre.setInt(7, item.getJob_work_time());
			pre.setInt(8, item.getJob_gender());
			pre.setInt(9, item.getJob_level());
			pre.setInt(10, item.getJob_experience_id());
			pre.setString(11, item.getJob_created_date());
			pre.setString(12, item.getJob_expiration_date());
			pre.setInt(13, item.getJob_company_id());
			pre.setString(14, item.getJob_last_modified());
			pre.setInt(15, item.getJob_author_id());
			pre.setInt(16, item.getJob_degree());
			pre.setInt(17, item.getJob_career().getCareer_id());
			pre.setString(18, item.getJob_location());
			pre.setString(19, item.getJob_skills());
			

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
	public boolean editJob(JobObject item, JOB_EDIT_TYPE et) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tblJob SET ");
		switch (et) {
		case GENERAL:
			sql.append("job_title = ?, job_purpose = ?, job_quantity =?, job_responsibility =?, ");
		    sql.append("job_Welfare =?, job_salary =?, job_work_time =?, job_gender =?, job_level =?, ");
			sql.append("job_experience_id =?,job_expiration_date = ?, ");
			sql.append("job_company_id =? , job_last_modified =?, job_author_id = ?, job_degree = ?,job_career_id =? ,job_location=? ");
			break;
		case TRASH:
			sql.append("job_delete= 1, job_last_modified=? ");
			break;
		case RESTORE:
			sql.append("job_delete= 0, job_last_modified=? ");
			break;
		case STATUS:
				sql.append("job_status= ?, job_last_modified=? ");
				break;
		}
		sql.append(" WHERE job_id=?;");
		
		System.out.println(sql);
		//System.out.println(sql2);
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
		//	PreparedStatement pre2 = this.con.prepareStatement(sql2.toString());
			switch (et) {
			case GENERAL:
				pre.setString(1, item.getJob_title());
				pre.setString(2, item.getJob_purpose());
				pre.setInt(3, item.getJob_quantity());
				pre.setString(4, item.getJob_responsibility());
				pre.setString(5, item.getJob_Welfare());
				pre.setInt(6, item.getJob_salary());
				pre.setInt(7, item.getJob_work_time());
				pre.setInt(8, item.getJob_gender());
				pre.setInt(9, item.getJob_level());
				pre.setInt(10, item.getJob_experience_id());
				pre.setString(11, item.getJob_expiration_date());
				pre.setInt(12, item.getJob_company_id());
				pre.setString(13, item.getJob_last_modified());
				pre.setInt(14, item.getJob_author_id());
				pre.setInt(15, item.getJob_degree());
				pre.setInt(16, item.getJob_career().getCareer_id());
				pre.setString(17, item.getJob_location());
				pre.setInt(18, item.getJob_id());
				break;
			case TRASH:
				pre.setString(1, item.getJob_last_modified());
				pre.setInt(2, item.getJob_id());
				break;
			case RESTORE:
				pre.setString(1, item.getJob_last_modified());
				pre.setInt(2, item.getJob_id());
				break;
			case STATUS:
				pre.setInt(1, item.getJob_status());
				pre.setString(2, item.getJob_last_modified());
				pre.setInt(3, item.getJob_id());
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
	public boolean delJob(JobObject item) {
		// TODO Auto-generated method stub

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM tblJob WHERE (Job_id=?);");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getJob_id());
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
	public ResultSet getLocationByCompany(short company_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from tblcompany ");
		sql.append("where company_id= " + company_id + ";");
		return this.gets(sql.toString());
	}

	@Override
	public ArrayList<ResultSet> getCareerByCompany(short company_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from tblcareer ");
		sql.append("Where career_field_id = ");
		sql.append("(SELECT company_field_id ");
		sql.append("FROM tblcompany ");
		sql.append("where company_id= " + company_id + ");");
		System.out.println(sql);
		return this.getMR(sql.toString());
	}
//	private boolean isEmpty(JobObject item) {
//		boolean flag = true;
//
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT * FROM tblarticle WHERE article_Job_id='").append(item.getJob_id()).append("';");
//		
//		ArrayList<ResultSet> res = this.getMR(sql.toString());
//        System.out.println(sql);
//		for (ResultSet rs : res) {
//			if (rs != null) {
//				try {
//					if (rs.next()) {
//						flag = false;
//						break;
//					}
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//
//		return flag;
//	}

	@Override
	public ArrayList<ResultSet> getJob(short id, UserObject userLogined) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblJob j "); // cate la con cua section
		sql.append("LEFT JOIN tblcareer f ON j.job_career_id = f.career_id ");
		sql.append("LEFT JOIN tblcompany p ON p.company_id = j.job_company_id ");
		sql.append("LEFT JOIN tbluser AS u ON j.job_author_id = u.user_id ");
		sql.append("WHERE (j.job_id=" + id + ");");

	//	sql.append("SELECT * FROM tblcompany WHERE company_delete = 0;");
		System.out.println(sql);

		return this.getMR(sql.toString());
	}

	@Override
	public ArrayList<ResultSet> getJobs(Quartet<JobObject, Integer, Byte, UserObject> infos, Pair<JOB_SOFT, ORDER> so) {
		// TODO Auto-generated method stub
		JobObject similar = infos.getValue0();

		// vị trí bắt đầu lấy bản ghi
		int at = infos.getValue1();

		// Số bản ghi được lấy trong một lần
		byte total = infos.getValue2();

		// tai khoan dang nhap
		UserObject user = infos.getValue3();

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT * FROM tblJob AS j ");
		sql.append("LEFT JOIN tbluser AS u ON u.user_id = j.job_author_id ");
		sql.append("LEFT JOIN tblcompany AS c ON c.company_id = j.job_company_id ");
		sql.append("LEFT JOIN tblcareer AS a ON a.career_id = j.job_career_id ");
		sql.append(this.createConditions(similar));
		switch (so.getValue0()) {
		case GENERAL:
			sql.append("ORDER BY j.job_id DESC ");
			break;
		default:
			sql.append("ORDER BY j.job_id DESC ");

		}
		sql.append(" LIMIT ").append(at).append(", ").append(total).append(";");

		sql.append("SELECT COUNT(job_id) AS total FROM tbljob");
		sql.append(createConditions(similar));
		sql.append(";");

		sql.append("SELECT * FROM tblcompany WHERE company_delete = 0;");
		
		sql.append("SELECT * FROM tblskill WHERE skill_delete = 0;");

		System.out.println(sql.toString());

		return this.getMR(sql.toString());
	}

	private String createConditions(JobObject similar) {
		StringBuffer conds = new StringBuffer();
		if (similar != null) {
			if (similar.isJob_delete()) {
				conds.append(" (job_delete=1)");
			} else {
				conds.append(" (job_delete=0) ");
			}
			// tu khoa tim kiem
			String key = similar.getJob_title();
			if (key != null && !key.equalsIgnoreCase("")) {
				conds.append(" AND ((job_title LIKE '%" + key + "%') OR ");
				conds.append("(job_responsibility LIKE '%" + key + "%') ");
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
		Job c = new JobImpl(cp);
		UserObject user = new UserObject();
		user.setUser_id(1);
		JobObject similar = new JobObject();
		similar.setJob_delete(false);

		Quartet<JobObject, Integer, Byte, UserObject> infos = new Quartet<>(similar, 0, (byte) 6, user);

		ArrayList<ResultSet> res = c.getJobs(infos, new Pair<>(JOB_SOFT.NAME, ORDER.DESC));

		ResultSet rs = res.get(0);

		String row;

		if (rs != null) {
			try {
				while (rs.next()) {
					row = "ID: " + rs.getInt("job_id");
					row += "\tNAME: " + rs.getString("job_title");

					System.out.println(row);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}

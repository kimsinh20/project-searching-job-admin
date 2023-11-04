package jsoft.ads.job;

import java.sql.ResultSet;
import jsoft.library.Utilities;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.CareerObject;
import jsoft.objects.CompanyObject;
import jsoft.objects.JobObject;
import jsoft.objects.SkillObject;
import jsoft.objects.UserObject;

public class JobModel {
	private Job c;
	public JobModel(ConnectionPool cp) {
		this.c = new JobImpl(cp);
	}
	
	public ConnectionPool getCP() {
		return this.c.getCP();
	}

	public void releaseConnection() {
		this.c.releaseConnection();
	}
	
//	---------------------------------------
	public boolean addJob(JobObject item) {
		return this.c.addJob(item);
	}
	
	public boolean editJob(JobObject item,JOB_EDIT_TYPE et) {
		return this.c.editJob(item,et);
	}
	
	public boolean delJob(JobObject item) {
		return this.c.delJob(item);
	}
	
//	----------------------------------------
	public ArrayList<CareerObject> getCareerByCompany(short company_id) {
		ArrayList<ResultSet> res = this.c.getCareerByCompany(company_id);
		ArrayList<CareerObject> careers= new ArrayList<>();
		CareerObject career = null;
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				while(rs.next()) {
					career = new CareerObject();
					career.setCareer_id(rs.getInt("career_id"));
					career.setCareer_name(rs.getString("career_name"));
					careers.add(career);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return careers;
	}
	public CompanyObject getLocation(short company_id) {
		CompanyObject company = null;
		 ResultSet rs = this.c.getLocationByCompany(company_id);
		if(rs!=null) {
			try {
				if(rs.next()) {
					company = new CompanyObject();
					company.setCompany_id(rs.getInt("company_id"));
					company.setCompany_name(rs.getString("company_name"));
					company.setCompany_location(jsoft.library.Utilities.decode(rs.getString("company_location")));
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return company;
	}
	
	public Pair<JobObject, HashMap<Integer, String>> getJobObject(short id, UserObject userLogined) {
		
		ArrayList<ResultSet> res = this.c.getJob(id, userLogined);
		//  Job
		JobObject Job = null;
		HashMap<Integer,String> author = new HashMap<>();
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				if(rs.next()) {
					Job = new JobObject();
					author.put(rs.getInt("Job_author_id"), rs.getString("user_fullname")+"("+rs.getString("user_name")+")");
					Job.setJob_id(rs.getShort("job_id"));
				    Job.setJob_title(jsoft.library.Utilities.decode(rs.getString("job_title")));
				    
				    CompanyObject company = new CompanyObject();
				    company.setCompany_id(rs.getInt("job_company_id"));
					company.setCompany_name(jsoft.library.Utilities.decode(rs.getString("company_name")));
					company.setCompany_field_id(rs.getInt("company_field_id"));
					company.setCompany_logo(rs.getString("company_logo"));
					company.setCompany_banner(rs.getString("company_banner"));
				    Job.setCompany(company);
				    
				    CareerObject career =  new CareerObject();
				    career.setCareer_id(rs.getInt("job_career_id"));
				    career.setCareer_name(rs.getString("career_name"));
				    Job.setJob_career(career);
				
				    Job.setJob_skills(rs.getString("job_skills"));
				    Job.setJob_quantity(rs.getInt("job_quantity"));
				    // job_purpose
				    Job.setJob_purpose(jsoft.library.Utilities.decode(rs.getString("job_purpose")));

				    // job_responsibility
				    Job.setJob_responsibility(jsoft.library.Utilities.decode(rs.getString("job_responsibility")));

				    // job_Welfare
				    Job.setJob_Welfare(jsoft.library.Utilities.decode(rs.getString("job_Welfare")));

				    // job_salary
				    Job.setJob_salary(rs.getByte("job_salary"));

				    // job_work_time
				    Job.setJob_work_time(rs.getByte("job_work_time"));

				    // job_gender
				    Job.setJob_gender(rs.getInt("job_gender"));

				    // job_level
				    Job.setJob_level(rs.getInt("job_level"));

				    // job_location
				    Job.setJob_location(rs.getString("job_location"));

				    // job_degree
				    Job.setJob_degree(rs.getInt("job_degree"));

				    // job_experience_id
				    Job.setJob_experience_id(rs.getInt("job_experience_id"));

				    // job_visited
				    Job.setJob_visited(rs.getInt("job_visited"));

				    // job_created_date
				    Job.setJob_created_date(rs.getString("job_created_date"));

				    // job_expiration_date
				    Job.setJob_expiration_date(jsoft.library.Utilities_date.getDateForJs(rs.getString("job_expiration_date")));

				    // job_delete
				    Job.setJob_delete(rs.getBoolean("job_delete"));

				    // job_enable
				    Job.setJob_enable(rs.getBoolean("job_enable"));

				    // job_interview_process_id
				    Job.setJob_interview_process_id(rs.getInt("job_interview_process_id"));

				    // job_company_id
				    Job.setJob_company_id(rs.getInt("job_company_id"));

				    // job_last_modified
				    Job.setJob_last_modified(rs.getString("job_last_modified"));

				    // job_author_id
				    Job.setJob_author_id(rs.getInt("job_author_id"));

				    // job_status
				    Job.setJob_status(rs.getInt("job_status"));
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return new Pair<>(Job, author);
	}
	public Quintet<ArrayList<JobObject>, Short, HashMap<Integer,String>,ArrayList<CompanyObject>,ArrayList<SkillObject>> getJobObjects
	(Quartet<JobObject, Integer, Byte,UserObject> infos, Pair<JOB_SOFT, ORDER> so) {
		
		ArrayList<ResultSet> res = this.c.getJobs(infos, so);
		// list Job
		ArrayList<JobObject> jobs = new ArrayList<>();
		JobObject Job = null;
		HashMap<Integer,String> author = new HashMap<>();
		
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				while(rs.next()) {
					Job = new JobObject();
					author.put(rs.getInt("job_author_id"), rs.getString("user_fullname")+"("+rs.getString("user_name")+")");
					Job.setJob_id(rs.getShort("job_id"));
				    Job.setJob_title(rs.getString("job_title"));
				    
				    CompanyObject company = new CompanyObject();
				    company.setCompany_id(rs.getInt("job_company_id"));
					company.setCompany_name(rs.getString("company_name"));
				    Job.setCompany(company);
				    
				    CareerObject career =  new CareerObject();
				    career.setCareer_id(rs.getInt("job_career_id"));
				    career.setCareer_name(rs.getString("career_name"));
				    Job.setJob_career(career);
				    
				    Job.setJob_expiration_date(rs.getString("job_expiration_date"));
				    Job.setJob_created_date(rs.getString("job_created_date"));
				    Job.setJob_status(rs.getInt("job_status"));
				    Job.setJob_quantity(rs.getInt("job_quantity"));
				    Job.setJob_delete(rs.getBoolean("job_delete"));
					jobs.add(Job);
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
		
		ArrayList<CompanyObject> companys = new ArrayList<>();
		CompanyObject company = null;
		 rs = res.get(2);
		if(rs!=null) {
			try {
				while(rs.next()) {
					company = new CompanyObject();
					company.setCompany_id(rs.getInt("company_id"));
					company.setCompany_name(rs.getString("company_name"));
					company.setCompany_field_id(rs.getInt("company_field_id"));
					companys.add(company);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ArrayList<SkillObject> skills = new ArrayList<>();
		SkillObject skill = null;
		 rs = res.get(3);
		if(rs!=null) {
			try {
				while(rs.next()) {
					skill = new SkillObject();
					skill.setSkill_id(rs.getInt("skill_id"));
					skill.setSkill_name(rs.getString("skill_name"));
					skills.add(skill);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new Quintet<>(jobs,total,author,companys,skills);
	}
}

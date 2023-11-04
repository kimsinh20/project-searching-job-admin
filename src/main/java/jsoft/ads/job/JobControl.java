package jsoft.ads.job;

import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.CareerObject;
import jsoft.objects.CompanyObject;
import jsoft.objects.JobObject;
import jsoft.objects.SkillObject;
import jsoft.objects.UserObject;

public class JobControl {
	private JobModel cm;

	public JobControl(ConnectionPool cp) {
		this.cm = new JobModel(cp);
	}

	public ConnectionPool getCP() {
		return this.cm.getCP();
	}

	public void releaseConnection() {
		this.cm.releaseConnection();
	}
	
//	------------------------------------------
	public boolean addJob(JobObject item) {
		return this.cm.addJob(item);
	}

	public boolean editJob(JobObject eCate,JOB_EDIT_TYPE et) {
		return this.cm.editJob(eCate,et);
	}

	public boolean delJob(JobObject item) {
		return this.cm.delJob(item);
	}

//	---------------------------------------------
	public Pair<JobObject, HashMap<Integer, String>> getJobObject(short id, UserObject userLogin) {
		return this.cm.getJobObject(id, userLogin);
	}
    
	public ArrayList<CareerObject> getCareerByCompany(short company_id) {
		return this.cm.getCareerByCompany(company_id);
	}
	public CompanyObject getLocation(short company_id) {
		
		return this.cm.getLocation(company_id);
	}
	
//	----------------------------------------------
	public ArrayList<String> viewJob(Quartet<JobObject, Integer, Byte,UserObject> infos, Pair<JOB_SOFT, ORDER> so,int page,String saveKey,boolean trash) {
	Quintet<ArrayList<JobObject>, Short,HashMap<Integer,String>,ArrayList<CompanyObject>,ArrayList<SkillObject>> datas = this.cm.getJobObjects(infos, so);
		ArrayList<String> views = new ArrayList<>();
		views.add(JobLibrary.viewJob(datas.getValue0(), datas.getValue1(),datas.getValue2(),page,infos.getValue3()));
		String view2= JobLibrary.pagination(datas.getValue1(), infos.getValue2(),page,saveKey,trash).toString();
        views.add(view2);
		views.add(JobLibrary.viewCompanyOptions(datas.getValue3()));
		views.add(JobLibrary.viewSkillOptions(datas.getValue4()));
//        views.add(CategoryLibrary.createdChart(datas.getValue4()).toString());
//		views.add(CategoryLibrary.viewSectionOptions(datas.getValue5(), infos.getValue3().getUser_id()));
		return views;
	}

	public static void main(String[] args) {
	
	}
}

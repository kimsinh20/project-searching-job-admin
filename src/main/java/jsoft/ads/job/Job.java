package jsoft.ads.job;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ShareControl;
import jsoft.library.ORDER;
import jsoft.objects.JobObject;
import jsoft.objects.UserObject;

public interface Job extends ShareControl {
	// các chức năng cập nhật
	public boolean addJob(JobObject item);

	public boolean editJob(JobObject item,JOB_EDIT_TYPE et);

	public boolean delJob(JobObject item);

	public ResultSet getLocationByCompany(short company_id);
	
	public ArrayList<ResultSet> getCareerByCompany(short company_id);
	// các chức năng lấy dữ liệu
	public ArrayList<ResultSet> getJob(short id, UserObject userLogined);

	public ArrayList<ResultSet> getJobs(Quartet<JobObject, Integer, Byte, UserObject> infos, Pair<JOB_SOFT, ORDER> so);
}

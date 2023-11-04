package jsoft.ads.company;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ShareControl;
import jsoft.library.ORDER;
import jsoft.objects.CompanyObject;
import jsoft.objects.LocationObject;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

public interface Company extends ShareControl {
	// các chức năng cập nhật
	public boolean addCompany(CompanyObject item);

	
	public boolean editCompany(CompanyObject item,COMPANY_EDIT_TYPE et);

	public boolean delCompany(CompanyObject item);
 
	public ArrayList<ResultSet> getProvices();
	
	public ArrayList<ResultSet> getditricts(String code);
	
	public ArrayList<ResultSet> getWards(String code);
	
	// các chức năng lấy dữ liệu
	public ArrayList<ResultSet> getCompany(short id, UserObject userLogined);

	public ArrayList<ResultSet> getCompanies(Quartet<CompanyObject, Integer, Byte, UserObject> infos, Pair<COMPANY_SOFT, ORDER> so);
}

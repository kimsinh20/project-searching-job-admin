package jsoft.ads.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.CompanyObject;
import jsoft.objects.DistrictObject;
import jsoft.objects.FieldObject;
import jsoft.objects.ProvinceObject;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;
import jsoft.objects.WardObject;

public class CompanyModel {
	private Company c;
	public CompanyModel(ConnectionPool cp) {
		this.c = new CompanyImpl(cp);
	}
	
	public ConnectionPool getCP() {
		return this.c.getCP();
	}

	public void releaseConnection() {
		this.c.releaseConnection();
	}
	
//	---------------------------------------
	public boolean addCompany(CompanyObject item) {
		return this.c.addCompany(item);
	}
	
	public boolean editCompany(CompanyObject item,COMPANY_EDIT_TYPE et) {
		return this.c.editCompany(item,et);
	}
	
	public boolean delCompany(CompanyObject item) {
		return this.c.delCompany(item);
	}
	public ArrayList<ProvinceObject> getProvies(){
		ArrayList<ResultSet> res = this.c.getProvices();
		ArrayList<ProvinceObject> provices = new ArrayList<>();
		ProvinceObject province = null;
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				while(rs.next()) {
					province = new ProvinceObject();
					province.setCode(rs.getString("code"));
					province.setName(rs.getString("name"));
					province.setName_en(rs.getString("name_en"));
					province.setFull_name(rs.getString("full_name"));
					province.setFull_name_en(rs.getString("full_name_en"));
					province.setCode_name(rs.getString("code_name"));
					province.setAdministrative_unit_id(rs.getInt("administrative_unit_id"));
					province.setAdministrative_region_id(rs.getInt("administrative_region_id"));
					provices.add(province);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return provices;
	}
	public ArrayList<DistrictObject> getDistricts(String code){
		ArrayList<ResultSet> res = this.c.getditricts(code);
		ArrayList<DistrictObject> districts = new ArrayList<>();
		DistrictObject district = null;
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				while(rs.next()) {
					district = new DistrictObject();
					district.setCode(rs.getString("code"));
					district.setName(rs.getString("name"));
					district.setName_en(rs.getString("name_en"));
					district.setFull_name(rs.getString("full_name"));
					district.setFull_name_en(rs.getString("full_name_en"));
					district.setCode_name(rs.getString("code_name"));
					district.setProvince_code(rs.getString("province_code"));
					district.setAdministrative_unit_id(rs.getInt("administrative_unit_id"));
					districts.add(district);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return districts;
	}
	public ArrayList<WardObject> getWards(String code){
		ArrayList<ResultSet> res = this.c.getWards(code);
		ArrayList<WardObject> wards = new ArrayList<>();
		WardObject ward = null;
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				while(rs.next()) {
					ward = new WardObject();
					ward.setCode(rs.getString("code"));
					ward.setName(rs.getString("name"));
					ward.setName_en(rs.getString("name_en"));
					ward.setFull_name(rs.getString("full_name"));
					ward.setDistrict_code(rs.getString("district_code"));
					ward.setFull_name_en(rs.getString("full_name_en"));
					ward.setCode_name(rs.getString("code_name"));
					ward.setAdministrative_unit_id(rs.getInt("administrative_unit_id"));
					wards.add(ward);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return wards;
	}
//	----------------------------------------
	
	public Triplet<CompanyObject, HashMap<Integer, String>,ArrayList<FieldObject>> getCompanyObject(short id, UserObject userLogined) {
		
		ArrayList<ResultSet> res = this.c.getCompany(id, userLogined);
		//  company
		CompanyObject company = null;
		HashMap<Integer,String> author = new HashMap<>();
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				if(rs.next()) {
					company = new CompanyObject();
					author.put(rs.getInt("company_author_id"), rs.getString("user_fullname")+"("+rs.getString("user_name")+")");
					company.setCompany_id(rs.getShort("company_id"));
					company.setCompany_name(rs.getString("company_name"));
					company.setCompany_about(rs.getString("company_about"));
					company.setCompany_summary(rs.getString("company_summary"));
					company.setCompany_remuneration(rs.getString("company_remuneration"));
					company.setCompany_author_id(rs.getInt("company_author_id"));
					company.setCompany_created_date(rs.getString("company_created_date"));
					company.setCompany_last_modified(rs.getString("company_last_modified"));
					company.setCompany_delete(rs.getBoolean("company_delete"));
					company.setCompany_size(rs.getInt("company_size"));
					company.setCompany_email(rs.getString("company_email"));
					company.setCompany_nationality(rs.getInt("company_nationality"));
					company.setCompany_website(rs.getString("company_website"));
					// get field
					FieldObject field = new FieldObject();
					field.setFieldId(rs.getInt("company_field_id"));
					field.setFieldName(rs.getString("field_name"));
					company.setField(field);
					company.setCompany_location(rs.getString("company_location"));
					company.setCompany_officephone(rs.getString("company_officephone"));
					company.setCompany_homephone(rs.getString("company_homephone"));
					company.setCompany_mobilephone(rs.getString("company_mobilephone"));
					company.setCompany_establish_date(rs.getString("company_establish_date"));
					company.setCompany_logo(rs.getString("company_logo"));
					company.setCompany_banner(rs.getString("company_banner"));
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ArrayList<FieldObject> fields = new ArrayList<>();
		FieldObject field = null;
		 rs = res.get(1);
		if(rs!=null) {
			try {
				while(rs.next()) {
					field = new FieldObject();
					field.setFieldId(rs.getInt("field_id"));
					field.setFieldName(rs.getString("field_name"));
					fields.add(field);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new Triplet<>(company, author, fields);
	}
	public Quartet<ArrayList<CompanyObject>, Short, HashMap<Integer,String>,ArrayList<FieldObject>> getCompanyObjects
	(Quartet<CompanyObject, Integer, Byte,UserObject> infos, Pair<COMPANY_SOFT, ORDER> so) {
		
		ArrayList<ResultSet> res = this.c.getCompanies(infos, so);
		// list company
		ArrayList<CompanyObject> companies = new ArrayList<>();
		CompanyObject company = null;
		HashMap<Integer,String> author = new HashMap<>();
		
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				while(rs.next()) {
					company = new CompanyObject();
					author.put(rs.getInt("company_author_id"), rs.getString("user_fullname")+"("+rs.getString("user_name")+")");
					company.setCompany_id(rs.getShort("company_id"));
					company.setCompany_name(rs.getString("company_name"));
					company.setCompany_about(rs.getString("company_about"));
					company.setCompany_author_id(rs.getInt("company_author_id"));
					company.setCompany_created_date(rs.getString("company_created_date"));
					company.setCompany_delete(rs.getBoolean("company_delete"));
					company.setCompany_email(rs.getString("company_email"));
					company.setCompany_nationality(rs.getInt("company_nationality"));
					company.setCompany_officephone(rs.getString("company_officephone"));
					company.setCompany_homephone(rs.getString("company_homephone"));
					company.setCompany_mobilephone(rs.getString("company_mobilephone"));
					company.setCompany_establish_date(rs.getString("company_establish_date"));
					company.setCompany_logo(rs.getString("company_logo"));
					companies.add(company);
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
		ArrayList<FieldObject> fields = new ArrayList<>();
		FieldObject field = null;
		 rs = res.get(2);
		if(rs!=null) {
			try {
				while(rs.next()) {
					field = new FieldObject();
					field.setFieldId(rs.getInt("field_id"));
					field.setFieldName(rs.getString("field_name"));
					fields.add(field);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return new Quartet<>(companies,total,author,fields);
	}
}

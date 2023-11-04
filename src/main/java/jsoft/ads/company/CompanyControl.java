package jsoft.ads.company;

import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.ads.category.CategoryLibrary;
import jsoft.library.ORDER;
import jsoft.objects.CompanyObject;
import jsoft.objects.DistrictObject;
import jsoft.objects.FieldObject;
import jsoft.objects.ProvinceObject;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;
import jsoft.objects.WardObject;

public class CompanyControl {
	private CompanyModel cm;

	public CompanyControl(ConnectionPool cp) {
		this.cm = new CompanyModel(cp);
	}

	public ConnectionPool getCP() {
		return this.cm.getCP();
	}

	public void releaseConnection() {
		this.cm.releaseConnection();
	}
	
//	------------------------------------------
	public boolean addCompany(CompanyObject item) {
		return this.cm.addCompany(item);
	}

	public boolean editCompany(CompanyObject eCate,COMPANY_EDIT_TYPE et) {
		return this.cm.editCompany(eCate,et);
	}

	public boolean delCompany(CompanyObject item) {
		return this.cm.delCompany(item);
	}

	public ArrayList<ProvinceObject> getProvies(){
		return this.cm.getProvies();
	}
	public ArrayList<DistrictObject> getDistricts(String code){
		return this.cm.getDistricts(code);
	}
	public ArrayList<WardObject> getWards(String code){
		return this.cm.getWards(code);
	}
//	---------------------------------------------
	public Triplet<CompanyObject, HashMap<Integer, String>,ArrayList<FieldObject>> getCompanyObject(short id, UserObject userLogin) {
		return this.cm.getCompanyObject(id, userLogin);
	}

//	----------------------------------------------
	public ArrayList<String> viewCompany(Quartet<CompanyObject, Integer, Byte,UserObject> infos, Pair<COMPANY_SOFT, ORDER> so,int page,String saveKey,boolean trash) {
	Quartet<ArrayList<CompanyObject>, Short,HashMap<Integer,String>,ArrayList<FieldObject>> datas = this.cm.getCompanyObjects(infos, so);
		ArrayList<String> views = new ArrayList<>();
		views.add(CompanyLibrary.viewCompany(datas.getValue0(), datas.getValue1(),datas.getValue2(),page,infos.getValue3()));
		String view2= CompanyLibrary.pagination(datas.getValue1(), infos.getValue2(),page,saveKey,trash).toString();
        views.add(view2);
		views.add(CompanyLibrary.viewFieldOptions(datas.getValue3(), infos.getValue3().getUser_id()));
//        views.add(CategoryLibrary.createdChart(datas.getValue4()).toString());
//		views.add(CategoryLibrary.viewSectionOptions(datas.getValue5(), infos.getValue3().getUser_id()));
		return views;
	}

	public static void main(String[] args) {
	
	}
}

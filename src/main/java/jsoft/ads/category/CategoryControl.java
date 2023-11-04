package jsoft.ads.category;

import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Sextet;

import jsoft.ConnectionPool;
import jsoft.ads.section.SectionLibrary;
import jsoft.library.ORDER;
import jsoft.objects.CategoryObject;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

public class CategoryControl {
	private CategoryModel cm;

	public CategoryControl(ConnectionPool cp) {
		this.cm = new CategoryModel(cp);
	}

	public ConnectionPool getCP() {
		return this.cm.getCP();
	}

	public void releaseConnection() {
		this.cm.releaseConnection();
	}
	
//	------------------------------------------
	public boolean addCategory(CategoryObject item) {
		return this.cm.addCategory(item);
	}

	public boolean editCategory(CategoryObject eCate,CATEGORY_EDIT_TYPE et) {
		return this.cm.editCategory(eCate,et);
	}

	public boolean delCategory(CategoryObject item) {
		return this.cm.delCategory(item);
	}

//	---------------------------------------------
	public Quintet<CategoryObject, HashMap<Integer, String>,HashMap<Integer, String>,ArrayList<UserObject>,ArrayList<SectionObject>> getCategoryObject(short id, UserObject userLogin) {
		return this.cm.getCategoryObject(id, userLogin);
	}

//	----------------------------------------------
	public ArrayList<String> viewCategory(Quartet<CategoryObject, Integer, Byte,UserObject> infos, Pair<CATEGORY_SOFT, ORDER> so,int page,String saveKey,boolean trash) {
		Sextet<ArrayList<CategoryObject>, Short,HashMap<Integer, String>,ArrayList<UserObject>,HashMap<Integer, String>,ArrayList<SectionObject>> datas = this.cm.getCategoryObjects(infos, so);
		ArrayList<String> views = new ArrayList<>();
		views.add(CategoryLibrary.viewCategory(datas.getValue0(), datas.getValue1(),datas.getValue2(), page,infos.getValue3() ));
		views.add(CategoryLibrary.viewManagerOptions(datas.getValue3(), infos.getValue3().getUser_id()));
		String view3= CategoryLibrary.pagination(datas.getValue1(), infos.getValue2(),page,saveKey,trash).toString();
        views.add(view3);
        views.add(CategoryLibrary.createdChart(datas.getValue4()).toString());
		views.add(CategoryLibrary.viewSectionOptions(datas.getValue5(), infos.getValue3().getUser_id()));
		return views;
	}

	public static void main(String[] args) {
	
	}
}

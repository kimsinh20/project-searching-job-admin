package jsoft.ads.section;

import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

public class SectionControl {
	private SectionModel sm;

	public SectionControl(ConnectionPool cp) {
		this.sm = new SectionModel(cp);
	}

	public ConnectionPool getCP() {
		return this.sm.getCP();
	}

	public void releaseConnection() {
		this.sm.releaseConnection();
	}
	
//	------------------------------------------
	public boolean addSection(SectionObject item) {
		return this.sm.addSection(item);
	}

	public boolean editSection(SectionObject item,SECTION_EDIT_TYPE et) {
		return this.sm.editSection(item,et);
	}

	public boolean delSection(SectionObject item) {
		return this.sm.delSection(item);
	}

//	---------------------------------------------
	public Quartet<SectionObject, HashMap<Integer, String>,HashMap<Integer, String>,ArrayList<UserObject>> getSectionObject(short id, UserObject userLogin) {
		return this.sm.getSectionObject(id, userLogin);
	}

//	----------------------------------------------
	public ArrayList<String> viewSection(Quartet<SectionObject, Integer, Byte,UserObject> infos, Pair<SECTION_SOFT, ORDER> so,int page,String saveKey,boolean trash) {
		Quintet<ArrayList<SectionObject>, Short,HashMap<Integer, String>,ArrayList<UserObject>,HashMap<Integer, String>> datas = this.sm.getSectionObjects(infos, so);

		ArrayList<String> views = new ArrayList<>();
		
		views.add(SectionLibrary.viewSection(datas.getValue0(),datas.getValue1(),datas.getValue2(),page,infos.getValue3()));
		views.add(SectionLibrary.viewManagerOptions(datas.getValue3(), infos.getValue3().getUser_id()));
		String view3= SectionLibrary.pagination(datas.getValue1(), infos.getValue2(),page,saveKey,trash).toString();
        views.add(view3);
        views.add(SectionLibrary.createdChart(datas.getValue4()).toString());
		return views;
	}

	public static void main(String[] args) {
	
	}
}

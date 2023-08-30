package jsoft.ads.section;

import jsoft.*;
import jsoft.library.ORDER;
import jsoft.objects.*;

import java.sql.ResultSet;
import java.util.*;
import org.javatuples.*;

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

	public boolean editSection(SectionObject item) {
		return this.sm.editSection(item);
	}

	public boolean delSection(SectionObject item) {
		return this.sm.delSection(item);
	}

//	---------------------------------------------
	public SectionObject getSectionObject(short id, UserObject userLogin) {
		return this.sm.getSectionObject(id, userLogin);
	}

//	----------------------------------------------
	public ArrayList<String> viewSection(Triplet<SectionObject, Integer, Byte> infos, Pair<SECTION_SOFT, ORDER> so) {
		Pair<ArrayList<SectionObject>, Short> datas = this.sm.getSectionObjects(infos, so);

		ArrayList<String> views = new ArrayList<>();
		views.add(SectionLibrary.viewSection(datas.getValue0()));

		return views;
	}

	public static void main(String[] args) {
		ConnectionPool cp = new ConnectionPoolImpl();
		SectionControl sc = new SectionControl(cp);

		Triplet<SectionObject, Integer, Byte> infos = new Triplet<>(null, 0, (byte) 15);

		ArrayList<String> views = sc.viewSection(infos, new Pair<>(SECTION_SOFT.NAME, ORDER.ASC));
		
		sc.releaseConnection();
		System.out.println(views);
	}
}

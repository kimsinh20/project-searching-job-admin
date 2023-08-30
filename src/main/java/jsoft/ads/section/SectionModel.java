package jsoft.ads.section;

import jsoft.*;
import jsoft.ads.section.SECTION_SOFT;
import jsoft.ads.user.User;
import jsoft.ads.user.UserImpl;
import jsoft.library.ORDER;
import jsoft.objects.*;

import java.sql.*;
import java.util.*;

import org.javatuples.Pair;
import org.javatuples.Triplet;

public class SectionModel {
	private Section s;

	public SectionModel(ConnectionPool cp) {
		this.s = new SectionImpl(cp);
	}
	
	public ConnectionPool getCP() {
		return this.s.getCP();
	}
	
	public void releaseConnection() {
		this.s.releaseConnection();
	}

//	----------------------------------
	public boolean addSection(SectionObject item) {
		return this.s.addSection(item);
	}

	public boolean editSection(SectionObject item) {
		return this.s.editSection(item);
	}

	public boolean delSection(SectionObject item) {
		return this.s.delSection(item);
	}

//	----------------------------------
	public SectionObject getSectionObject(short id, UserObject userLogin) {
		SectionObject item = null;
		ResultSet rs = this.s.getSection(id, userLogin);
		if (rs != null) {
			try {
				if (rs.next()) {
					item = new SectionObject();
					item.setSection_id(rs.getShort("section_id"));
					item.setSection_name(rs.getString("section_name"));
					item.setSection_notes(rs.getString("section_notes"));
					item.setSection_created_date(rs.getString("section_created_date"));
					item.setSection_manager_id(rs.getInt("section_manager_id"));
					item.setSection_enable(rs.getBoolean("section_enable"));
					item.setSection_delete(rs.getBoolean("section_delete"));
					item.setSection_last_modified(rs.getString("section_last_modified"));
					item.setSection_created_author_id(rs.getInt("section_created_author_id"));
					item.setSection_name_en(rs.getString("section_name_en"));
					item.setSection_language(rs.getByte("section_language"));
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return item;
	}

	public Pair<ArrayList<SectionObject>, Short> getSectionObjects(Triplet<SectionObject, Integer, Byte> infos, Pair<SECTION_SOFT, ORDER> so) {
		ArrayList<SectionObject> items = new ArrayList<>();

		SectionObject item = null;

		ArrayList<ResultSet> res = this.s.getSections(infos, so);

		ResultSet rs = res.get(0);

		if (rs != null) {
			try {
				while (rs.next()) {
					item = new SectionObject();
					item.setSection_id(rs.getShort("section_id"));
					item.setSection_name(rs.getString("section_name"));
					item.setSection_notes(rs.getString("section_notes"));
					item.setSection_created_date(rs.getString("section_created_date"));
					item.setSection_manager_id(rs.getInt("section_manager_id"));
					item.setSection_enable(rs.getBoolean("section_enable"));
					item.setSection_delete(rs.getBoolean("section_delete"));
					item.setSection_last_modified(rs.getString("section_last_modified"));
					item.setSection_created_author_id(rs.getInt("section_created_author_id"));
					item.setSection_name_en(rs.getString("section_name_en"));
					item.setSection_language(rs.getByte("section_language"));

					items.add(item);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		rs = res.get(1);
		short total = 0;
		if (rs != null) {
			try {
				if (rs.next()) {
					total = rs.getShort("total");
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return new Pair<>(items, total);
	}
}

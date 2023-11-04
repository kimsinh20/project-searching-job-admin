package jsoft.ads.section;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ShareControl;
import jsoft.library.ORDER;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

public interface Section extends ShareControl {
	// các chức năng cập nhật
	public boolean addSection(SectionObject item);

	public boolean editSection(SectionObject item,SECTION_EDIT_TYPE et);

	public boolean delSection(SectionObject item);

	
	// các chức năng lấy dữ liệu
	public  ArrayList<ResultSet> getSection(short id, UserObject userLogined);

	public ArrayList<ResultSet> getSections(Quartet<SectionObject, Integer, Byte,UserObject> infos, Pair<SECTION_SOFT, ORDER> so);
}

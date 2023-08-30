package jsoft.ads.section;

import jsoft.objects.*;
import java.sql.*;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import jsoft.ShareControl;
import jsoft.library.*;

public interface Section extends ShareControl {
	// các chức năng cập nhật
	public boolean addSection(SectionObject item);

	public boolean editSection(SectionObject item);

	public boolean delSection(SectionObject item);

	
	// các chức năng lấy dữ liệu
	public ResultSet getSection(short id, UserObject userLogined);

	public ArrayList<ResultSet> getSections(Triplet<SectionObject, Integer, Byte> infos, Pair<SECTION_SOFT, ORDER> so);
}

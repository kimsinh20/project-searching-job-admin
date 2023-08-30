package jsoft.ads.category;

import jsoft.ShareControl;
import jsoft.ads.user.USER_SOFT;
import jsoft.library.ORDER;
import jsoft.objects.*;
import java.sql.*;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Triplet;

public interface Category extends ShareControl {
	// các chức năng cập nhật
	public boolean addCategory(CategoryObject item);

	public boolean editCategory(CategoryObject item);

	public boolean delCategory(CategoryObject item);

	// các chức năng lấy dữ liệu
	public ResultSet getCategory(short id, UserObject userLogined);

	public ArrayList<ResultSet> getCategories(Triplet<CategoryObject, Integer, Byte> infos, Pair<CATEGORY_SOFT, ORDER> so);
}

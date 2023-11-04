package jsoft.ads.category;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ShareControl;
import jsoft.library.ORDER;
import jsoft.objects.CategoryObject;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

public interface Category extends ShareControl {
	// các chức năng cập nhật
	public boolean addCategory(CategoryObject item);

	public boolean editCategory(CategoryObject item,CATEGORY_EDIT_TYPE et);

	public boolean delCategory(CategoryObject item);

	// các chức năng lấy dữ liệu
	public ArrayList<ResultSet> getCategory(short id, UserObject userLogined);

	public ArrayList<ResultSet> getCategories(Quartet<CategoryObject, Integer, Byte, UserObject> infos, Pair<CATEGORY_SOFT, ORDER> so);
}

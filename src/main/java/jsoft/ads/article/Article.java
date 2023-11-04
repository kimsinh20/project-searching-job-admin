package jsoft.ads.article;

import jsoft.ShareControl;
import jsoft.ads.category.CATEGORY_SOFT;
import jsoft.library.ORDER;
import jsoft.objects.*;
import java.sql.*;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

public interface Article extends ShareControl {
	// các chức năng cập nhật
	public boolean addArticle(ArticleObject item);

	public boolean editArticle(ArticleObject item,ARTICLE_EDIT_TYPE et);

	public boolean delArticle(ArticleObject item);

	// các chức năng lấy dữ liệu
	public ArrayList<ResultSet> getArticle(int id);
	// get cate funadd
	public ArrayList<ResultSet> getCategories(Quartet<CategoryObject, Integer, Byte,UserObject> infos,Pair<CATEGORY_SOFT, ORDER> so);

//	public ResultSet getArticles(ArticleObject similar, int at, byte total);
	public ArrayList<ResultSet> getArticles(Quartet<ArticleObject, Integer, Byte,UserObject> infos);
}

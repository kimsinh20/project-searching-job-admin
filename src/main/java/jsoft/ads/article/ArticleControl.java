package jsoft.ads.article;

import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Sextet;

import jsoft.ConnectionPool;
import jsoft.ads.category.CATEGORY_SOFT;
import jsoft.ads.section.SectionLibrary;
import jsoft.library.ORDER;
import jsoft.objects.ArticleObject;
import jsoft.objects.CategoryObject;
import jsoft.objects.SectionObject;
import jsoft.objects.UserObject;

public class ArticleControl {
	private ArticleModel am;

	public ArticleControl(ConnectionPool cp) {
		this.am = new ArticleModel(cp);
	}

	public ConnectionPool getCP() {
		return this.am.getCP();
	}

	public void releaseConnection() {
		this.am.releaseConnection();
	}
	
//	------------------------------------------
	public boolean addArticle(ArticleObject item) {
		return this.am.addArticle(item);
	}

	public boolean editArticle(ArticleObject eCate,ARTICLE_EDIT_TYPE et) {
		return this.am.editArticle(eCate,et);
	}

	public boolean delArticle(ArticleObject item) {
		return this.am.delArticle(item);
	}

//	---------------------------------------------
	public Pair<ArticleObject,ArrayList<CategoryObject>> getArticle(short id, UserObject userLogin) {
		return this.am.getArticle(id, userLogin);
	}

//	----------------------------------------------
	public ArrayList<CategoryObject> getCategorys(Quartet<CategoryObject, Integer, Byte,UserObject> infos,Pair<CATEGORY_SOFT, ORDER> so){
		return this.am.getCategories(infos, so);
	}
	public ArrayList<String> viewArticle(Quartet<ArticleObject, Integer, Byte,UserObject> infos, Pair<ARTICLE_SOFT, ORDER> so,int page,String saveKey,boolean trash) {
		Sextet<ArrayList<ArticleObject>, Short,HashMap<Integer, String>,ArrayList<UserObject>,HashMap<String,Integer >,ArrayList<CategoryObject>> datas = this.am.getArticleObjects(infos);
		ArrayList<String> views = new ArrayList<>();
		views.add(ArticleLibrary.viewArticles(datas.getValue0(), datas.getValue1(),datas.getValue2(), page,infos.getValue3() ));
		views.add(ArticleLibrary.viewManagerOptions(datas.getValue3(), infos.getValue3().getUser_id()));
		String view3= ArticleLibrary.pagination(datas.getValue1(), infos.getValue2(),page,saveKey,trash).toString();
        views.add(view3);
        views.add(ArticleLibrary.createdChart(datas.getValue4()).toString());
//		views.add(ArticleLibrary.viewSectionOptions(datas.getValue5(), infos.getValue3().getUser_id()));
		return views;
	}

	public static void main(String[] args) {
	
	}
}

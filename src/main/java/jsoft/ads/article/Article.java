package jsoft.ads.article;

import jsoft.objects.*;
import java.sql.*;
import java.util.ArrayList;

import org.javatuples.Triplet;

public interface Article {
	// các chức năng cập nhật
	public boolean addArticle(ArticleObject item);

	public boolean editArticle(ArticleObject item);

	public boolean delArticle(ArticleObject item);

	// các chức năng lấy dữ liệu
	public ResultSet getArticle(int id);

//	public ResultSet getArticles(ArticleObject similar, int at, byte total);
	public ArrayList<ResultSet> getArticles(Triplet<ArticleObject, Integer, Byte> infos);
}

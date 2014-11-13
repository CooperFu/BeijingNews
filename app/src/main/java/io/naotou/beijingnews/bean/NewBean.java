package io.naotou.beijingnews.bean;

import java.util.List;

public class NewBean {
	public NewBeanItem data;
	public String retcode;
	
	public class NewBeanItem{
		public String countcommenturl;
		public String more;
		public List<News> news;
		public String title;
		public List<Topic> topic;
		public List<TopNews> topnews;
	}
	
	public class News{
		public boolean comment;
		public String commentlist;
		public String commenturl;
		//新闻唯一性标示,记录在本地，判读是是已读的新闻
		public String id;
		//图片url
		public String listimage;
		//时间
		public String pubdate;
		//新闻缩略内容
		public String title;
		public String type;
		//url关联详情页请求地址  html------>WebView(加载HTML网页)
		public String url;
		
		//是否已读
		public boolean isRead;
	}
	
	public class Topic{
		public String description;
		public int id;
		public String listimage;
		public String sort;
		public String title;
		public String url;
	}
	
	public class TopNews{
		public boolean comment;
		public String commentlist;
		public String commenturl;
		public int id;
		public String pubdate;
		//标题
		public String title;
		//图片
		public String topimage;
		public String type;
		//详情页url地址
		public String url;
	}
}

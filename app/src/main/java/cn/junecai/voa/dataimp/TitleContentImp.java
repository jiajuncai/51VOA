package cn.junecai.voa.dataimp;


public class TitleContentImp {
	
	/*
	* title content vo
	 */
	public   final static String  TITLE="title";  //title
	public   final static String  TITLETXT="titletxt";
	public   final static String  TITLEURL="titleurl"; //文章地址留作后续
	public   final static String  PHOURL="phourl";
	public   final static String  MUSURl="musurl";
	public   final static String  TITLEID="titleid";
	private String title;
	private String titletxt;
	private String titleurl;
	private String phourl;
	private String musurl;
	private int titleid=-1;
	private  int id_DB=-1;//数据库主键id
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitletxt() {
		return titletxt;
	}
	public void setTitletxt(String titletxt) {
		this.titletxt = titletxt;
	}
	public String getTitleurl() {
		return titleurl;
	}
	public void setTitleurl(String titleurl) {
		this.titleurl = titleurl;
	}
	public String getPhourl() {
		return phourl;
	}
	public void setPhourl(String phourl) {
		this.phourl = phourl;
	}
	public String getMusurl() {
		return musurl;
	}
	public void setMusurl(String musurl) {
		this.musurl = musurl;
	}
	public int getTitleid() {
		return titleid;
	}
	public void setTitleid(int titleid) {
		this.titleid = titleid;
	}
	public int getId_DB() {
		return id_DB;
	}
	public void setId_DB(int id_DB) {
		this.id_DB = id_DB;
	}
	
	
	
}

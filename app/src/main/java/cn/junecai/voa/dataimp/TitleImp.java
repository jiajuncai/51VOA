package cn.junecai.voa.dataimp;

public class TitleImp {


	
	public   final static String  TITLE="title";  
	public   final static String  TITLEURL="titleurl"; 
	public   final static String  PHOURL="phourl";
//	public   final static String  USERADDRESS="userAddress";
//	public   final static String  USERSEX="userSex";
//	public   final static String  USERPHONE="userPhone";
//	public   final static String  USERBIRTH="userBirth";
	private String title;
	private String titleurl;
	private String phourl;
//	private String userAddress;
//	private String userSex;
//	private String userPhone;
//	private String userBirth;
	private  int id_DB=-1;//数据库主键id
	
	public int getId_DB() {
		return id_DB;
	}
	public void setId_DB(int idDB) {
		id_DB = idDB;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	
}

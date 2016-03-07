package cn.junecai.voa.dataimp;

public class ClassifyImp {

	public   final static String  CLASSIFYTITLE="classifyTitle";  
	public   final static String  CLASSIFYTITLEURL="classifyTitleurl"; 
	private String classifyTitle;
	private String classifyTitleurl;
	private  int id_DB=-1;//数据库主键id
	
	public int getId_DB() {
		return id_DB;
	}
	public void setId_DB(int idDB) {
		id_DB = idDB;
	}
	public String getClassifyTitle() {
		return classifyTitle;
	}
	public void setClassifyTitle(String classifyTitle) {
		this.classifyTitle = classifyTitle;
	}
	public String getClassifyTitleurl() {
		return classifyTitleurl;
	}
	public void setClassifyTitleurl(String classifyTitleurl) {
		this.classifyTitleurl = classifyTitleurl;
	}
	
	
}

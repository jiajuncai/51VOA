package cn.junecai.voa.dataimp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class TitleContent {
	private   final static String TABLENAME="TitleCont";//表名
	private   DataImp   db;//数据库管理
	public   TitleContent(Context context)
	{
	    db=new  DataImp(context);
	    if(!db.isTableExits(TABLENAME))
	    {
	    	String  createTableSql="CREATE TABLE IF NOT EXISTS " +
	   		     TABLENAME + " (  id_DB  integer   " +
			     "primary key  AUTOINCREMENT , " +
			     cn.junecai.voa.dataimp.TitleContentImp.TITLE     + "  VARCHAR," +
			     cn.junecai.voa.dataimp.TitleContentImp.TITLETXT   + "  VARCHAR," +
			      cn.junecai.voa.dataimp.TitleContentImp.TITLEURL + "  VARCHAR," +
			     cn.junecai.voa.dataimp.TitleContentImp.MUSURl    + "  VARCHAR,"+
			     cn.junecai.voa.dataimp.TitleContentImp.TITLEID    + "  VARCHAR,"+
			     cn.junecai.voa.dataimp.TitleContentImp.PHOURL + " VARCHAR)";
//			     创建表
	             db.creatTable(createTableSql);
			}
		}
	
	/*
	 * 添加数据到文章表
	 */
	public  boolean  addData(cn.junecai.voa.dataimp.TitleContentImp titleContent)
	{
		String keyword = titleContent.getTitleurl();
		if (!db.findbytitle(TABLENAME,"titleurl", keyword)) {
		ContentValues values = new ContentValues();
		values.put(cn.junecai.voa.dataimp.TitleContentImp.TITLE, titleContent.getTitle());
		values.put(cn.junecai.voa.dataimp.TitleContentImp.TITLETXT, titleContent.getTitletxt());
		values.put(cn.junecai.voa.dataimp.TitleContentImp.TITLEURL, titleContent.getTitleurl());
		values.put(cn.junecai.voa.dataimp.TitleContentImp.PHOURL, titleContent.getPhourl());
		values.put(cn.junecai.voa.dataimp.TitleContentImp.MUSURl, titleContent.getMusurl());
		values.put(cn.junecai.voa.dataimp.TitleContentImp.TITLEID, titleContent.getTitleid());
		return	db.save(TABLENAME, values);
	} else {
		Log.e("数据库", "update");
		return db.update(TABLENAME, null, null, null);
	}
	    
	}
	/**
	* 获取表数据
	* 通过标题
	* @return
	*/
	public cn.junecai.voa.dataimp.TitleContentImp geTitleContentbyUrl(String str)
	{
		Cursor cursor = null;
        try {
            cursor = db.find(
            		"select * from " + TABLENAME +"   where  "
            	+"titleurl=?" ,new String[]{str });
            cn.junecai.voa.dataimp.TitleContentImp temp = new cn.junecai.voa.dataimp.TitleContentImp();
        	cursor.moveToNext();
                   
            temp.setId_DB(cursor.getInt(
                	cursor.getColumnIndex("id_DB")));
                temp.setTitle(cursor.getString(
                	cursor.getColumnIndex(cn.junecai.voa.dataimp.TitleContentImp.TITLE)));
                temp.setTitletxt(cursor.getString(
                    	cursor.getColumnIndex(cn.junecai.voa.dataimp.TitleContentImp.TITLETXT)));
                temp.setPhourl(cursor.getString(
                	cursor.getColumnIndex(cn.junecai.voa.dataimp.TitleContentImp.TITLEURL)));
                temp.setPhourl(cursor.getString(
                    	cursor.getColumnIndex(cn.junecai.voa.dataimp.TitleContentImp.PHOURL)));
                temp.setMusurl(cursor.getString(
                    	cursor.getColumnIndex(cn.junecai.voa.dataimp.TitleContentImp.MUSURl)));
                temp.setTitleid(cursor.getInt(
                    	cursor.getColumnIndex(cn.junecai.voa.dataimp.TitleContentImp.TITLEID)));
           return temp;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.closeConnection();
        }
        return null;
}
}

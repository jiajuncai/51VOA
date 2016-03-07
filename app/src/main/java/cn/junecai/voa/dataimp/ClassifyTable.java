package cn.junecai.voa.dataimp;

import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class ClassifyTable {
	private final static String TABLENAME = "ClassifyTable";// 表名
	private cn.junecai.voa.dataimp.DataImp db;// 数据库管理

	public ClassifyTable(Context context) {
		db = new cn.junecai.voa.dataimp.DataImp(context);
		Log.i("class", "ClassifyTable");
		if (!db.isTableExits(TABLENAME)) {
			String createTableSql = "CREATE TABLE IF NOT EXISTS " + TABLENAME
					+ " (  id_DB  integer   " + "primary key  AUTOINCREMENT , "
					+ ClassifyImp.CLASSIFYTITLE + "  VARCHAR,"
					+ ClassifyImp.CLASSIFYTITLEURL + "  VARCHAR)";
			// 创建表
			db.creatTable(createTableSql);
		}
	}
	/**
	 * 添加数据到表
	 */
	public boolean addData(ClassifyImp classifyImp) {
		String keyword = classifyImp.getClassifyTitle();
		if (!db.findbytitle(TABLENAME,"classifyTitle", keyword)) {
			ContentValues values = new ContentValues();
			values.put(ClassifyImp.CLASSIFYTITLE,
					classifyImp.getClassifyTitle());
			values.put(ClassifyImp.CLASSIFYTITLEURL,
					classifyImp.getClassifyTitleurl());
			return db.save(TABLENAME, values);
		} else {
			Log.e("数据库", "update");
			return db.update(TABLENAME, null, null, null);
		}
	}
	/**
	 * 获取表数据
	 * 
	 * @return
	 */
	public ClassifyImp[] getAllUser() {
		Vector<ClassifyImp> v = new Vector<ClassifyImp>();
		Cursor cursor = null;
		try {
			cursor = db.find("select * from " + TABLENAME, null);
			while (cursor.moveToNext()) {
				ClassifyImp temp = new ClassifyImp();
				temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
				temp.setClassifyTitle(cursor.getString(cursor
						.getColumnIndex(ClassifyImp.CLASSIFYTITLE)));
				temp.setClassifyTitleurl(cursor.getString(cursor
						.getColumnIndex(ClassifyImp.CLASSIFYTITLEURL)));
				v.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			db.closeConnection();
		}
		if (v.size() > 0) {
			return v.toArray(new ClassifyImp[] {});
		} else {
			ClassifyImp[] classifyImps = new ClassifyImp[1];
			ClassifyImp classifyImp = new ClassifyImp();
			classifyImp.setClassifyTitle("无结果");
			classifyImps[0] = classifyImp;
			return classifyImps;
		}
	}

	/**
	 * 根据数据库ID主键来获取
	 * 
	 * @param id
	 * @return
	 */
	public ClassifyImp getUserByID(int id) {
		Cursor cursor = null;
		try {
			cursor = db.find("select * from " + TABLENAME + "   where  "
					+ "id_DB=?", new String[] { id + "" });
			ClassifyImp temp = new ClassifyImp();
			cursor.moveToNext();

			temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
			temp.setClassifyTitle(cursor.getString(cursor
					.getColumnIndex(ClassifyImp.CLASSIFYTITLE)));
			temp.setClassifyTitleurl(cursor.getString(cursor
					.getColumnIndex(ClassifyImp.CLASSIFYTITLEURL)));
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

	/**
	 * 修改文章列表信息
	 */
	public boolean updateUser(ClassifyImp classifyImp) {

		ContentValues values = new ContentValues();
		values.put(ClassifyImp.CLASSIFYTITLE, classifyImp.getClassifyTitle());
		values.put(ClassifyImp.CLASSIFYTITLEURL,
				classifyImp.getClassifyTitleurl());
		return db.update(TABLENAME, values, "  id_DB=? ",
				new String[] { classifyImp.getId_DB() + "" });
	}

	/**
	 * 删除联系人
	 * 
	 * @param classifyImp
	 * @return
	 */
	public boolean deleteByUser(ClassifyImp classifyImp) {
		return db.delete(TABLENAME, "  CLASSIFYTITLE=?",
				new String[] { classifyImp.getClassifyTitle() + "" });
	}
}

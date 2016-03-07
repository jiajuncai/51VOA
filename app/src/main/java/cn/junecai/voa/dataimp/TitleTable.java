package cn.junecai.voa.dataimp;

import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class TitleTable {
	private final static String TABLENAME = "TitleTable";// 表名
	private DataImp db;// 数据库管理

	public TitleTable(Context context) {
		db = new DataImp(context);
		Log.i("qisong", "titleTable01");
		if (!db.isTableExits(TABLENAME)) {
			String createTableSql = "CREATE TABLE IF NOT EXISTS " + TABLENAME
					+ " (  id_DB  integer   " + "primary key  AUTOINCREMENT , "
					+ TitleImp.TITLE + "  VARCHAR," + TitleImp.TITLEURL
					+ "  VARCHAR," + TitleImp.PHOURL + "  VARCHAR)";
			// 创建表
			db.creatTable(createTableSql);
		}
	}

	/**
	 * 添加数据到联系人表
	 * 
	 * @param name
	 * @param moblie
	 * @param phone
	 * @return
	 */
	public boolean addData(TitleImp user) {
		String keyword = user.getTitleurl();
		if (!db.findbytitle(TABLENAME,"titleurl", keyword)) {
			ContentValues values = new ContentValues();
			values.put(TitleImp.TITLE, user.getTitle());
			values.put(TitleImp.TITLEURL, user.getTitleurl());
			values.put(TitleImp.PHOURL, user.getPhourl());
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
	public TitleImp[] getAllUser() {
		Vector<TitleImp> v = new Vector<TitleImp>();
		Cursor cursor = null;
		try {
			cursor = db.find("select * from " + TABLENAME, null);
			while (cursor.moveToNext()) {
				TitleImp temp = new TitleImp();
				temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
				temp.setTitle(cursor.getString(cursor
						.getColumnIndex(TitleImp.TITLE)));
				temp.setTitleurl(cursor.getString(cursor
						.getColumnIndex(TitleImp.TITLEURL)));
				temp.setPhourl(cursor.getString(cursor
						.getColumnIndex(TitleImp.PHOURL)));
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
			return v.toArray(new TitleImp[] {});
		} else {
			TitleImp[] users = new TitleImp[1];
			TitleImp user = new TitleImp();
			user.setTitle("无结果");
			users[0] = user;
			return users;
		}
	}

	/**
	 * 根据数据库ID主键来获取
	 * 
	 * @param id
	 * @return
	 */
	public TitleImp getUserByID(int id) {
		Cursor cursor = null;
		try {
			cursor = db.find("select * from " + TABLENAME + "   where  "
					+ "id_DB=?", new String[] { id + "" });
			TitleImp temp = new TitleImp();
			cursor.moveToNext();

			temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
			temp.setTitle(cursor.getString(cursor
					.getColumnIndex(TitleImp.TITLE)));
			temp.setPhourl(cursor.getString(cursor
					.getColumnIndex(TitleImp.TITLEURL)));
			temp.setPhourl(cursor.getString(cursor
					.getColumnIndex(TitleImp.PHOURL)));
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
	public boolean updateUser(TitleImp user) {

		ContentValues values = new ContentValues();
		values.put(TitleImp.TITLE, user.getTitle());
		values.put(TitleImp.TITLEURL, user.getTitleurl());
		values.put(TitleImp.PHOURL, user.getPhourl());
		return db.update(TABLENAME, values, "  id_DB=? ",
				new String[] { user.getId_DB() + "" });
	}

	/**
	 * 删除联系人
	 * 
	 * @param user
	 * @return
	 */
	public boolean deleteByUser(TitleImp user) {
		return db.delete(TABLENAME, "  TITLE=?", new String[] { user.getTitle()
				+ "" });
	}
}

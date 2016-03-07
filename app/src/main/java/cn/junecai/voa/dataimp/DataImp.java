package cn.junecai.voa.dataimp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataImp extends SQLiteOpenHelper {

	private static String DB_NAME = "My_voa.db"; // 数据库名称

	private static int DB_VERSION = 2; // 版本号

	private SQLiteDatabase db; // 数据库操作对象

	public DataImp(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		db = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 创建数据库后，对数据库的操作
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO 每次成功打开数据库后首先被执行
		super.onOpen(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 更改数据库版本的操作
	}

	/***
	 * 执行SQLite数据库连接
	 * 
	 * @return SQLiteDatabase
	 */
	public SQLiteDatabase openConnection() {

		if (!db.isOpen()) {
			// 读写方式获取SQLiteDatabase
			db = getWritableDatabase();
		}
		return db;
	}

	/***
	 * 关闭 SQLite数据库连接操作
	 * 
	 * @return
	 */
	public void closeConnection() {
		try {
			if (db != null && db.isOpen())
				db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建表
	 * 
	 * @param createTableSql
	 * @return
	 */
	public boolean creatTable(String createTableSql) {
		try {
			openConnection();
			db.execSQL(createTableSql);

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			closeConnection();
		}
		return true;
	}

	/**
	 * 添加操作
	 * 
	 * @param tableName
	 *            表名
	 * @param values
	 *            集合对象
	 * @return
	 */
	public boolean save(String tableName, ContentValues values) {
		try {
			openConnection();
			db.insert(tableName, null, values);

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			closeConnection();
		}
		return true;
	}

	/**
	 * 更新操作
	 * 
	 * @param table
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public boolean update(String table, ContentValues values,
			String whereClause, String[] whereArgs) {
		try {
			openConnection();
			db.update(table, values, whereClause, whereArgs);

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			closeConnection();
		}
		return true;
	}

	/**
	 * 删除
	 * 
	 * @param deleteSql
	 *            对应跟新字段 如： "where personid=?"
	 * @param obj
	 *            [] 对应值 如： new Object[]{person.getPersonid()};
	 * @return
	 */
	public boolean delete(String table, String deleteSql, String obj[]) {
		try {
			openConnection();
			db.delete(table, deleteSql, obj);

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			closeConnection();
		}
		return true;
	}

	/**
	 * 查询操作
	 * 
	 * @param findSql
	 *            对应查询字段 如： select * from person limit ?,?
	 * @param obj
	 *            对应值 如： new String[]{String.valueOf(fristResult)
	 *            ,String.valueOf(maxResult)}
	 * @return
	 */
	public Cursor find(String findSql, String obj[]) {

		try {
			openConnection();
			Cursor cursor = db.rawQuery(findSql, obj);
			return cursor;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean findbytitle(String tablename, String key,String keyword) {
		boolean result = false;
		Cursor cursor = null;
		try {
			openConnection();
			cursor = db.rawQuery("select count(*) as classifyTitle from "
					+ tablename + " where " + key +"='"
					+ keyword + "' ", null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * 判断表是否存在
	 * 
	 * @param tablename
	 * @return
	 */
	public boolean isTableExits(String tablename) {
		try {
			openConnection();
			String str = "select count(*) xcount  from  " + tablename;
			db.rawQuery(str, null).close();
		} catch (Exception ex) {
			return false;
		} finally {
			closeConnection();
		}
		return true;
	}

}

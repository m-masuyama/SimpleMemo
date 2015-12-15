package jp.co.iseise.sample.simplememo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbAdapter {
	private static final String DATABASE_NAME = "simplememodata";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_MEMO = "memo";
	// テーブル定義分
	private static final String CREATE_TABLE_MEMO = "create table memo (_id integer primary key autoincrement"
			+ ", memo text, updated integer);";
	// カラム定義
	public static final String COL_ROWID = "_id";
	public static final String COL_MOMO = "memo";
	public static final String COL_UPDATED = "updated";

	private static final String[] TABLE_MEMO_ITEMS = new String[] { COL_ROWID, COL_MOMO, COL_UPDATED };

	private static class DbHelper extends SQLiteOpenHelper {
		DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE_MEMO);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}

	private Context context;
	private DbHelper dbHelper;
	private SQLiteDatabase db;

	/**
	 * コンストラクタ
	 * @param context コンテキスト
	 */
	public DbAdapter(Context context) {
		this.context = context;
	}

	/**
	 * Open
	 *
	 * @throws android.database.SQLException
	 */
	public void open() throws SQLException {
		if (db != null && db.isOpen()) {
			db.close();
		}
		dbHelper = new DbHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * Close
	 */
	public void close() {
		dbHelper.close();
		db.close();
	}

	/**
	 * Memo Insert
	 */
	public long insertMemo(Memo memo) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(COL_MOMO, memo.getMemo());
		initialValues.put(COL_UPDATED, memo.getUpdated());
		return db.insert(TABLE_MEMO, null, initialValues);
	}

	/**
	 * Memo Update
	 * @param memo メモ
	 */
	public long updateMemo(Memo memo) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(COL_MOMO, memo.getMemo());
		initialValues.put(COL_UPDATED, memo.getUpdated());
		return db.update(TABLE_MEMO, initialValues, COL_ROWID + "=" + memo.getId(), null);
	}

	/**
	 * Memo Delete
	 * @param rowId ID
	 */
	public long deleteMemo(long rowId) {
		return db.delete(TABLE_MEMO, COL_ROWID + "=" + rowId, null);
	}

	/**
	 * Memo Select
	 * @param rowId ID
	 * @return
	 */
	public Memo selectMemo(long rowId) {
		String where = COL_ROWID + " = ?";
		String[] params = new String[] { "" + rowId };
		Cursor cursor = db.query(TABLE_MEMO, TABLE_MEMO_ITEMS, where, params, null, null, null);
		try {
			if (cursor.moveToFirst()) {
				return getMemoFromCursor(cursor);
			}
		} finally {
			cursor.close();
		}
		return null;
	}

	/**
	 * Memo Select All
	 */
	public List<Memo> selectAllMemos() {
		List<Memo> list = new ArrayList<Memo>();
		Cursor c = db.query(TABLE_MEMO, TABLE_MEMO_ITEMS, null, null, null, null, COL_UPDATED + " desc");
		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++) {
			Memo memo = getMemoFromCursor(c);
			list.add(memo);
			c.moveToNext();
		}
		c.close();
		return list;
	}

	/**
	 * カーソルからMemoを取得
	 * @param c
	 * @return
	 */
	private Memo getMemoFromCursor(Cursor c) {
		Memo memo = new Memo();
		memo.setId(c.getLong(c.getColumnIndex(COL_ROWID)));
		memo.setMemo(c.getString(c.getColumnIndex(COL_MOMO)));
		memo.setUpdated(c.getLong(c.getColumnIndex(COL_UPDATED)));
		return memo;
	}

}

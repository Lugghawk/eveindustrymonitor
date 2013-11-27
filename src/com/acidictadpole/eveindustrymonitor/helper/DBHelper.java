package com.acidictadpole.eveindustrymonitor.helper;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.acidictadpole.eveindustrymonitor.persist.EveApi;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBHelper extends OrmLiteSqliteOpenHelper {
	private static final String DB_NAME = "eveindustry.db";
	private static final int DB_VERSION = 1;

	private Dao<EveApi, Integer> apiDao = null;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	/**
	 * Returns DAO for Eve Apis.
	 * 
	 * @return EveAPI Dao.
	 * @throws SQLException
	 */
	public Dao<EveApi, Integer> getApiDao() throws SQLException {
		if (apiDao == null) {
			apiDao = getDao(EveApi.class);
		}
		return apiDao;
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
			Log.i(DBHelper.class.getName(), "onCreate");
			// Create Table for Apis
			Log.i(DBHelper.class.getName(), "Attempting to create api table");
			TableUtils.createTable(connectionSource, EveApi.class);

		} catch (SQLException e) {
			Log.e(DBHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}

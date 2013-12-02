package com.acidictadpole.eveindustrymonitor;

import java.sql.SQLException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.acidictadpole.eveindustrymonitor.helper.DBHelper;
import com.acidictadpole.eveindustrymonitor.helper.EveApiHelper;
import com.acidictadpole.eveindustrymonitor.persist.EveApi;
import com.beimin.eveapi.exception.ApiException;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

public class ApiAddActivity extends Activity {

	private EditText mKeyID;
	private EditText mVCode;

	private static DBHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.api_add);

		mKeyID = (EditText) findViewById(R.id.api_keyId);
		mVCode = (EditText) findViewById(R.id.api_vCode);

		Button confirmButton = (Button) findViewById(R.id.api_confirm);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Integer keyId = extras.getInt(EveApi.KEY_ID);
			String vCode = extras.getString(EveApi.V_CODE);

			if (keyId != null && vCode != null) {
				mKeyID.setText(keyId);
				mVCode.setText(vCode);
			}
		}

		confirmButton.setOnClickListener(new View.OnClickListener() {

			private boolean isValidKey(String keyId, String vCode) {
				return (keyId.matches("[0-9]{6,}") && vCode
						.matches("[0-9a-zA-Z]{64}"));
			}

			@Override
			public void onClick(View v) {
				String keyId = mKeyID.getText().toString();
				String vCode = mVCode.getText().toString();
				if (!isValidKey(keyId, vCode)) {
					// If the key isn't valid, put up a toast and return
					// nothing.
					Toast.makeText(getApplicationContext(),
							"Doesn't look like a valid key", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				EveApi api = new EveApi(Integer.valueOf(keyId), vCode);
				KeyInfoAsyncTask task = new KeyInfoAsyncTask(
						ApiAddActivity.this);
				task.execute(api);

			}
		});
	}

	public void returnSuccess() {

		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		finish();
	}

	public void returnFail() {
		Toast.makeText(getApplicationContext(), "Adding Key Failed",
				Toast.LENGTH_SHORT).show();
	}

	private DBHelper getDBHelper() {
		if (dbHelper == null) {
			dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
		}
		return dbHelper;
	}

	public class KeyInfoAsyncTask extends AsyncTask<EveApi, Integer, EveApi> {

		ProgressDialog progress;

		public KeyInfoAsyncTask(Activity activity) {
			this.progress = new ProgressDialog(activity);
		}

		@Override
		protected void onPostExecute(EveApi result) {
			try {
				Dao<EveApi, Integer> dao = getDBHelper().getApiDao();
				dao.create(result);
				// To dismiss the dialog
				progress.dismiss();
				returnSuccess();
			} catch (SQLException e) {
				Log.e(this.getClass().getName(), "DatabaseException");
				throw new RuntimeException(e);
			}
		}

		@Override
		protected void onPreExecute() {
			progress.setTitle("Checking Key");
			progress.setMessage("Checking key info");
			progress.show();

		}

		@Override
		protected EveApi doInBackground(EveApi... params) {
			if (params.length != 1) {
				throw new IllegalArgumentException(
						"Only one EveApi can be passed");
			}
			EveApi api = params[0];
			try {
				api.setKeyType(EveApiHelper.getKeyInfo(api).getType());

			} catch (ApiException e) {
				Log.e(this.getClass().getName(),
						"ApiException occured: " + e.getMessage());
				returnFail();
			}
			return api;
		}

	}

}

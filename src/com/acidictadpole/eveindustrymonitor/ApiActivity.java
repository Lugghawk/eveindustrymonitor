package com.acidictadpole.eveindustrymonitor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.acidictadpole.eveindustrymonitor.helper.DBHelper;
import com.acidictadpole.eveindustrymonitor.persist.EveApi;
import com.acidictadpole.eveindustrymonitor.view.ApiListAdapter;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

public class ApiActivity extends Activity {

	public static final int ACTIVITY_ADD = 0;

	private DBHelper dbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.api_settings);
		refreshView();
	}

	private void refreshView() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_list_item_1);
		adapter.add(getString(R.string.add_api));
		ListView list = (ListView) findViewById(R.id.api_edit_list);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					beginApiAdd();
					return;
				default:
					return;
				}
			}

			private void beginApiAdd() {
				Intent i = new Intent(getBaseContext(), ApiAddActivity.class);
				startActivityForResult(i, ACTIVITY_ADD);
			}
		});
		list.setAdapter(adapter);
		list.invalidate();
		getFragmentManager().beginTransaction()
				.add(R.id.api_list, new ApiFragment()).commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case ACTIVITY_ADD:
			if (resultCode == RESULT_OK) {
				refreshView();
			}
		}
	}

	public static class ApiFragment extends ListFragment {
		DBHelper dbHelper;

		List<String> apis;

		public ApiFragment() {
			super();
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			Toast toast = Toast.makeText(getActivity().getApplicationContext(),
					String.valueOf(((EveApi) l.getAdapter().getItem(position))
							.getKeyId()), Toast.LENGTH_SHORT);
			toast.show();
			super.onListItemClick(l, v, position, id);

		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			ApiListAdapter adapter = new ApiListAdapter(getActivity()
					.getApplicationContext(), getStoredApis());
			setListAdapter(adapter);
			return super.onCreateView(inflater, container, savedInstanceState);
		}

		private List<EveApi> getStoredApis() {
			List<EveApi> apis = new ArrayList<EveApi>();
			Log.i(this.getClass().getName(), "Attempting to get dbhelper");
			DBHelper dbhelper = getDBHelper();
			Dao<EveApi, Integer> apiDao;
			try {
				apiDao = dbhelper.getApiDao();
				apis = apiDao.queryForAll();
			} catch (SQLException e) {
				Log.e(ApiFragment.class.getName(), "Database exception", e);
			} finally {
				OpenHelperManager.releaseHelper();
			}

			return apis;
		}

		private DBHelper getDBHelper() {
			if (dbHelper == null) {
				dbHelper = OpenHelperManager.getHelper(this.getActivity(),
						DBHelper.class);
			}
			return dbHelper;
		}
	}

}

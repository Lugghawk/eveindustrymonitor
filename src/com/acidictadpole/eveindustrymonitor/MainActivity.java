package com.acidictadpole.eveindustrymonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.acidictadpole.eveindustrymonitor.view.ContractListAdapter;
import com.acidictadpole.eveindustrymonitor.view.JobListAdapter;

public class MainActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			// showSettings();
			break;
		case R.id.api_settings:
			showApiSettings();
			break;
		default:
			super.onOptionsItemSelected(item);

		}
		return true;
	}

	private void showApiSettings() {
		Intent intent = new Intent();
		intent.setClass(this, ApiActivity.class);
		startActivityForResult(intent, 0);
	}

	private void showSettings() {
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, SettingsActivity.class);
		startActivityForResult(intent, 0);

	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
			case 1:
				fragment = new JobSectionFragment();
				break;
			case 2:
				fragment = new ContractSectionFragment();
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	public static class ContractSectionFragment extends Fragment {
		public static final String CONTRACT_TYPE = "contract_type";
		public static final String CONTRACT_STATE = "contract_state";

		private List<HashMap<String, String>> data;

		public ContractSectionFragment() {
			data = getDummyContracts();
		}

		// This gets called when this Fragment is moving into adjacency with an
		// active fragment.
		// So if the active fragment is two spots away from this one, this is
		// redrawn as it comes into adjacency.
		// The result of this is that we want to not generate/retrieve any data
		// here.
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.contract_fragment,
					container, false);
			ListView jobListView = (ListView) rootView
					.findViewById(R.id.contract_list);
			ContractListAdapter contractAdapter = new ContractListAdapter(
					getActivity().getApplicationContext(), data);
			jobListView.setAdapter(contractAdapter);
			return rootView;
		}

		private List<HashMap<String, String>> getDummyContracts() {
			List<HashMap<String, String>> dummyData = new ArrayList<HashMap<String, String>>();
			String[] states = new String[] { "In Progress", "Outstanding",
					"Completed", "Failed" };
			String[] types = new String[] { "Courier", "Auction",
					"Item Exchange" };

			for (int i = 0; i < 10; i++) {
				String state = states[(int) (Math.random() * (states.length))];
				String type = types[(int) (Math.random() * (types.length))];
				HashMap<String, String> contract = new HashMap<String, String>();
				contract.put(CONTRACT_TYPE, type);
				contract.put(CONTRACT_STATE, state);
				dummyData.add(contract);
			}
			return dummyData;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class JobSectionFragment extends Fragment {
		public static final String JOB_NAME = "job_name";
		public static final String JOB_TIME = "job_time";

		private List<HashMap<String, String>> data;

		public JobSectionFragment() {
			data = getDummyData();
		}

		private List<HashMap<String, String>> getDummyData() {
			long startTime = System.nanoTime();
			List<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
			for (int i = 0; i < 10; i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(JOB_NAME, "Item " + i);
				DateTime dateNow = new DateTime();
				if ((((int) Math.random()) % 2) == 0) {
					Log.i("Test", "Adding Hours");
					dateNow = dateNow.plusHours((int) (Math.random() * 5));
				} else {
					Log.i("Test", "Minusing Hours");
					dateNow = dateNow.minusHours((int) (Math.random() * 5));
				}

				map.put(JOB_TIME, dateNow.toString());
				items.add(map);
			}
			long endTime = System.nanoTime();
			Log.i(this.getClass().getName(), "dummyData took "
					+ (endTime - startTime) + " nanoseconds");
			return items;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.job_fragment, container,
					false);
			ListView jobListView = (ListView) rootView
					.findViewById(R.id.job_list);

			JobListAdapter jobAdapter = new JobListAdapter(getActivity()
					.getApplicationContext(), data);
			jobListView.setAdapter(jobAdapter);

			return rootView;
		}
	}

}

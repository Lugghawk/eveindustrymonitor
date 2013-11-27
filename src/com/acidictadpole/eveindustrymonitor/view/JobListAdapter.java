package com.acidictadpole.eveindustrymonitor.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.acidictadpole.eveindustrymonitor.MainActivity.DummySectionFragment;
import com.acidictadpole.eveindustrymonitor.R;

public class JobListAdapter extends BaseAdapter {
	private final Context context;
	private final List<HashMap<String, String>> values;

	public JobListAdapter(Context context, List<HashMap<String, String>> values) {
		super();
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.job_item, null);
		Map<String, String> itemValues = values.get(position);
		TextView jobItemView = (TextView) rowView.findViewById(R.id.job_item);
		jobItemView.setText(itemValues.get(DummySectionFragment.JOB_NAME));
		TextView jobTimeView = (TextView) rowView.findViewById(R.id.job_time);
		jobTimeView.setText(itemValues.get(DummySectionFragment.JOB_TIME));

		return rowView;
	}

	@Override
	public int getCount() {
		return values.size();
	}

	@Override
	public Object getItem(int arg0) {
		return values.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
}

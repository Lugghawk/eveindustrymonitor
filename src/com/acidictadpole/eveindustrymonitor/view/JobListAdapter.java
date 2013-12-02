package com.acidictadpole.eveindustrymonitor.view;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateTime;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.acidictadpole.eveindustrymonitor.MainActivity.JobSectionFragment;
import com.acidictadpole.eveindustrymonitor.R;

/**
 * Special adapter to display the job lists.
 * 
 */
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
		jobItemView.setText(itemValues.get(JobSectionFragment.JOB_NAME));
		DateTime completionDate = new DateTime(
				itemValues.get(JobSectionFragment.JOB_TIME));

		TextView jobTimeView = (TextView) rowView.findViewById(R.id.job_time);
		DateTime nowTime = DateTime.now();
		String prefix = "";
		if (completionDate.isAfter(nowTime)) {
			jobTimeView.setTextColor(Color.YELLOW);
			prefix = "Completed on: ";
		} else {
			jobTimeView.setTextColor(Color.GREEN);
			prefix = "Will complete at: ";
		}
		SimpleDateFormat df = new SimpleDateFormat("EEEE MMM d HH:mm:ss",
				Locale.getDefault());
		jobTimeView.setText(prefix + df.format(completionDate.toDate()));

		return rowView;
	}

	// private List<Map<String,String>> sortValues(List<Map<String,String>>
	// values){

	// }

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

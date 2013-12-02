package com.acidictadpole.eveindustrymonitor.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.acidictadpole.eveindustrymonitor.R;
import com.acidictadpole.eveindustrymonitor.persist.EveApi;

/**
 * 
 * Adapter to display APIs in a list on the API page. Will provide details like
 * the Key ID, what type of Key it is, etc.
 * 
 */
public class ApiListAdapter extends BaseAdapter {
	private final Context context;
	private final List<EveApi> apis;

	public ApiListAdapter(Context context, List<EveApi> apiValues) {
		this.context = context;
		this.apis = apiValues;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCount() {
		return apis.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getItem(int arg0) {
		return apis.get(arg0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.single_api, null);
		EveApi api = apis.get(position);
		TextView apiIdView = (TextView) rowView
				.findViewById(R.id.single_api_key_id);
		apiIdView.setText(String.valueOf(api.getKeyId()));
		TextView apiKeyTypeView = (TextView) rowView
				.findViewById(R.id.single_api_key_type);
		String keyTypeText = "";
		switch (api.getKeyType()) {
		case Account:
			keyTypeText = "Account";
			break;
		case Character:
			keyTypeText = "Character";
			break;
		case Corporation:
			keyTypeText = "Corporation";
			break;
		}
		apiKeyTypeView.setText(keyTypeText);

		return rowView;
	}
}

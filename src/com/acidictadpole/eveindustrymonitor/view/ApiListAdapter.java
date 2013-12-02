package com.acidictadpole.eveindustrymonitor.view;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}

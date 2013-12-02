package com.acidictadpole.eveindustrymonitor.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.acidictadpole.eveindustrymonitor.MainActivity.ContractSectionFragment;
import com.acidictadpole.eveindustrymonitor.R;
import com.beimin.eveapi.shared.contract.ContractStatus;

/**
 * Special adapter to display the job lists.
 * 
 */
public class ContractListAdapter extends BaseAdapter {
	private final Context context;
	private final List<HashMap<String, String>> values;

	public ContractListAdapter(Context context,
			List<HashMap<String, String>> values) {
		super();
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.contract_item, null);
		Map<String, String> itemValues = values.get(position);
		TextView contractTypeView = (TextView) rowView
				.findViewById(R.id.contract_type);
		contractTypeView.setText(itemValues
				.get(ContractSectionFragment.CONTRACT_TYPE));
		TextView contractStateView = (TextView) rowView
				.findViewById(R.id.contract_state);
		String contractState = itemValues
				.get(ContractSectionFragment.CONTRACT_STATE);
		switch (getContractStatus(contractState)) {
		case CANCELLED:
		case FAILED:
		case REVERSED:
		case REJECTED:
			contractStateView.setTextColor(Color.RED);
			break;
		case COMPLETED:
			contractStateView.setTextColor(Color.GREEN);
			break;
		case INPROGRESS:
			contractStateView.setTextColor(Color.YELLOW);
			break;
		default:
			break;
		}
		contractStateView.setText(contractState);

		return rowView;
	}

	private ContractStatus getContractStatus(String status) {
		if (status == "In Progress") {
			return ContractStatus.INPROGRESS;
		} else if (status == "Outstanding") {
			return ContractStatus.OUTSTANDING;
		} else if (status == "Completed") {
			return ContractStatus.COMPLETED;
		} else if (status == "Failed") {
			return ContractStatus.FAILED;
		} else if (status == "Cancelled") {
			return ContractStatus.FAILED;
		} else {
			return null;
		}
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

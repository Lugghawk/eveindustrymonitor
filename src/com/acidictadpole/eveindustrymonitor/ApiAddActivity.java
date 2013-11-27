package com.acidictadpole.eveindustrymonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.acidictadpole.eveindustrymonitor.persist.EveApi;

public class ApiAddActivity extends Activity {

	private EditText mKeyID;
	private EditText mVCode;

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

			@Override
			public void onClick(View v) {
				Bundle returnValues = new Bundle();
				returnValues.putString(EveApi.V_CODE, mVCode.getText()
						.toString());
				returnValues.putInt(EveApi.KEY_ID,
						Integer.valueOf(mKeyID.getText().toString()));

				Intent intent = new Intent();
				intent.putExtras(returnValues);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
}
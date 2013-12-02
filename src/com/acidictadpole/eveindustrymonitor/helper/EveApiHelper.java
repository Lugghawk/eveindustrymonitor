package com.acidictadpole.eveindustrymonitor.helper;

import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.util.Log;

import com.acidictadpole.eveindustrymonitor.persist.EveApi;
import com.beimin.eveapi.account.apikeyinfo.ApiKeyInfoParser;
import com.beimin.eveapi.account.apikeyinfo.ApiKeyInfoResponse;
import com.beimin.eveapi.core.ApiAuthorization;
import com.beimin.eveapi.exception.ApiException;

/**
 * Class to help work with the EveApi library
 * 
 */
public class EveApiHelper {

	public static EveApi createEveApi(int keyId, String vCode)
			throws ApiException {
		EveApi api = new EveApi(keyId, vCode);
		KeyInfoAsyncTask task = new KeyInfoAsyncTask();
		task.execute(api);
		try {
			api.setKeyType(task.get().getType());
		} catch (InterruptedException e) {
			Log.e(EveApiHelper.class.getName(),
					"Interrupted Exception: " + e.getMessage());
		} catch (ExecutionException e) {
			Log.e(EveApiHelper.class.getName(),
					"Execution Exception: " + e.getMessage());
		}
		return api;
	}

	public static ApiKeyInfoResponse getKeyInfo(EveApi api) throws ApiException {
		return getKeyInfo(api.getKeyId(), api.getvCode());
	}

	public static ApiKeyInfoResponse getKeyInfo(int keyID, String vCode)
			throws ApiException {
		ApiAuthorization auth = new ApiAuthorization(keyID, vCode);
		ApiKeyInfoParser parser = ApiKeyInfoParser.getInstance();
		return parser.getResponse(auth);
	}

	public static class KeyInfoAsyncTask extends
			AsyncTask<EveApi, Integer, ApiKeyInfoResponse> {

		@Override
		protected ApiKeyInfoResponse doInBackground(EveApi... params) {
			if (params.length != 1) {
				throw new IllegalArgumentException(
						"Only one EveApi can be passed");
			}
			EveApi api = params[0];
			try {
				return getKeyInfo(api);
			} catch (ApiException e) {
				Log.e(this.getClass().getName(),
						"ApiException occured: " + e.getMessage());
				throw new RuntimeException(e);
			}
		}

	}
}

package com.acidictadpole.eveindustrymonitor.helper;

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
		ApiKeyInfoResponse keyInfo = getKeyInfo(keyId, vCode);
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
}

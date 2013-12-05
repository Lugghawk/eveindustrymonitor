package com.acidictadpole.eveindustrymonitor.helper;

import java.util.ArrayList;
import java.util.List;

import com.acidictadpole.eveindustrymonitor.persist.EveApi;
import com.beimin.eveapi.account.apikeyinfo.ApiKeyInfoParser;
import com.beimin.eveapi.account.apikeyinfo.ApiKeyInfoResponse;
import com.beimin.eveapi.core.ApiAuthorization;
import com.beimin.eveapi.exception.ApiException;
import com.beimin.eveapi.shared.KeyType;
import com.beimin.eveapi.shared.contract.ContractsResponse;

/**
 * Class to help work with the EveApi library
 * 
 */
public class EveApiHelper {

	public static ApiKeyInfoResponse getKeyInfo(EveApi api) throws ApiException {
		return getKeyInfo(api.getKeyId(), api.getvCode());
	}

	public static ApiKeyInfoResponse getKeyInfo(int keyID, String vCode)
			throws ApiException {
		ApiAuthorization auth = new ApiAuthorization(keyID, vCode);
		ApiKeyInfoParser parser = ApiKeyInfoParser.getInstance();
		return parser.getResponse(auth);
	}

	public static List<ContractsResponse> getContracts(EveApi api)
			throws ApiException {
		ApiAuthorization auth = new ApiAuthorization(api.getKeyId(),
				api.getvCode());
		List<ContractsResponse> contracts = new ArrayList<ContractsResponse>();
		if (api.getKeyType() == KeyType.Corporation) {
			com.beimin.eveapi.corporation.contract.ContractsParser parser = com.beimin.eveapi.corporation.contract.ContractsParser
					.getInstance();
			contracts.add(parser.getResponse(auth));
		} else if (api.getKeyType() == KeyType.Character) {
			com.beimin.eveapi.character.contract.ContractsParser parser = com.beimin.eveapi.character.contract.ContractsParser
					.getInstance();
			contracts.add(parser.getResponse(auth));
		} else if (api.getKeyType() == KeyType.Account) {
			// For each character. Need to store characters.
		}
		return contracts;

	}
}

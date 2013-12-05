package com.acidictadpole.eveindustrymonitor.test;

import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

import com.acidictadpole.eveindustrymonitor.helper.EveApiHelper;
import com.acidictadpole.eveindustrymonitor.persist.EveApi;
import com.beimin.eveapi.account.apikeyinfo.ApiKeyInfoResponse;
import com.beimin.eveapi.exception.ApiException;
import com.beimin.eveapi.shared.KeyType;
import com.beimin.eveapi.shared.contract.ContractsResponse;
import com.beimin.eveapi.shared.contract.EveContract;

public class EveApiHelperTest extends TestCase {

	@Test
	public void testGetKeyInfo() throws ApiException {
		ApiKeyInfoResponse response = EveApiHelper
				.getKeyInfo(2758254,
						"GCEL0idhezt2oj6wzvFiUybIZZQmnVT7PaDy5iXSl7vJBlPXMrGiGPuo9rVj494s");
		long mask = response.getAccessMask();
		KeyType keyType = response.getType();
		System.out.println(mask + " " + keyType);
	}

	@Test
	public void testGetContracts() throws ApiException {
		EveApi api = new EveApi(2758254,
				"GCEL0idhezt2oj6wzvFiUybIZZQmnVT7PaDy5iXSl7vJBlPXMrGiGPuo9rVj494s");
		api.setKeyType(KeyType.Corporation);
		List<ContractsResponse> responses = EveApiHelper.getContracts(api);
		for (ContractsResponse response : responses) {
			Set<EveContract> contracts = response.getAll();
			for (EveContract contract : contracts) {
				System.out.println(contract);
			}
		}
	}

}

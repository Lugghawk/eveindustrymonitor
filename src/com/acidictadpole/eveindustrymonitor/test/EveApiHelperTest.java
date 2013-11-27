package com.acidictadpole.eveindustrymonitor.test;

import org.junit.Test;

import junit.framework.TestCase;

import com.acidictadpole.eveindustrymonitor.helper.EveApiHelper;
import com.beimin.eveapi.account.apikeyinfo.ApiKeyInfoResponse;
import com.beimin.eveapi.exception.ApiException;
import com.beimin.eveapi.shared.KeyType;

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

}

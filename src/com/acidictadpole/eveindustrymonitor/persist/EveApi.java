package com.acidictadpole.eveindustrymonitor.persist;

import com.beimin.eveapi.shared.KeyType;
import com.j256.ormlite.field.DatabaseField;

/**
 * Class to store an Eve Online API.
 */
public class EveApi {

	public static final String KEY_ID = "keyId";
	public static final String V_CODE = "vCode";

	@DatabaseField(generatedId = true)
	int id;
	@DatabaseField(index = true)
	Integer keyId;
	@DatabaseField
	String vCode;
	@DatabaseField
	KeyType apiKeyType;

	public void setKeyType(KeyType type) {
		this.apiKeyType = type;
	}

	public KeyType getKeyType() {
		return this.apiKeyType;
	}

	public int getId() {
		return id;
	}

	public Integer getKeyId() {
		return keyId;
	}

	public String getvCode() {
		return vCode;
	}

	EveApi() {

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EveApi [id=");
		builder.append(id);
		builder.append(", keyId=");
		builder.append(keyId);
		builder.append(", vCode=");
		builder.append(vCode);
		builder.append(", apiKeyType=");
		builder.append(apiKeyType);
		builder.append("]");
		return builder.toString();
	}

	public EveApi(Integer keyId, String vCode) {
		this.keyId = keyId;
		this.vCode = vCode;
	}

}

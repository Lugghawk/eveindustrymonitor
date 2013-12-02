package com.acidictadpole.eveindustrymonitor.persist;

import com.j256.ormlite.field.DatabaseField;

/**
 * 
 * Class to store Character information in in the database
 * 
 */
public class Character {

	@DatabaseField(index = true)
	Long id;
	@DatabaseField
	String name;

	Character() {

	}

	public Character(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Character [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

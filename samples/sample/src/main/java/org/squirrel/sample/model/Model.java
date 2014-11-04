package org.squirrel.sample.model;

import org.squirrel.annotation.INCREMENT;

public abstract class Model {

	protected @INCREMENT int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
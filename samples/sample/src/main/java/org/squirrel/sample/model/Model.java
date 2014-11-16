package org.squirrel.sample.model;

import org.squirrel.annotation.INCREMENT;

public abstract class Model {

	protected @INCREMENT Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
package org.squirrel;

import java.util.List;
import org.squirrel.util.BeanPropertyUtil;
import org.squirrel.util.SQLHelper;

public enum Column {

	ALL, ACTIVE;
	
	Class<?> objClass;
	
	Column from(Class<?> objClass) {
		this.objClass = objClass;
		return this;
	}
	
	String toCode() {
		switch (this) {
		case ALL :
			return "*";
		case ACTIVE :
			List<String> names = BeanPropertyUtil.getAllNonStaticFieldNames(objClass);
			StringBuilder columns = new StringBuilder();
			for(String name : names)
				columns.append(name).append(SQLHelper.DELIMITER);
			return columns.substring(0, columns.length() - 2);
		}
		return null;
	}
	
}
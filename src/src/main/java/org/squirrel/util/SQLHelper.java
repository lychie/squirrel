package org.squirrel.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.squirrel.SQLModel;
import org.squirrel.annotation.INCREMENT;
import org.squirrel.annotation.UUID;

public class SQLHelper {

	public static final int PK_NAME  = 0;
	public static final int PK_FIELD = 1;
	public static final int PK_VALUE = 2;
	public static final int PK_STRATEGY = 3;
	public static final int MODEL_FIELDS = 4;
	public static final int INFO_ITEMS = 5;
	public static final String DELIMITER = ", ";
	
	private SQLHelper(){}

	public static SQLModel generateInsertSQL(Object entity) throws Throwable {
		Object[] model = parseModel(entity.getClass());
		@SuppressWarnings("unchecked")
		List<Field> fields = (List<Field>) model[MODEL_FIELDS];
		int length = fields.size();
		Object[] values = new Object[length];
		StringBuilder columns = new StringBuilder();
		StringBuilder params = new StringBuilder();
		Field field;  String fieldName;
		for(int i = 0; i < length; i++){
			field = fields.get(i);
			fieldName = field.getName();
			if(fieldName.equals(model[PK_NAME])){
				values[i] = model[PK_VALUE];
				field.set(entity, model[PK_VALUE]);
			}else
				values[i] = field.get(entity);
			params.append("?").append(DELIMITER);
			columns.append(fieldName).append(DELIMITER);
		}
		params = params.replace(params.lastIndexOf(DELIMITER), params.length(), " )");
		columns = columns.replace(columns.lastIndexOf(DELIMITER), columns.length(), " ) ");
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ").append(entity.getClass().getSimpleName());
		sql.append(" ( ").append(columns);
		sql.append("VALUES ( ").append(params);
		return new SQLModel(sql.toString(), values, model);
	}
	
	public static SQLModel generateUpdateSQL(Object newObj, Object oldObj) throws Throwable {
		if(oldObj == null)
			return updateAllColumnSQL(newObj);
		return updateActiveColumnSQL(newObj, oldObj);
	}
	
	public static SQLModel generateDeleteSQL(Object entity) throws Throwable {
		Class<?> entityClass = entity.getClass();
		Object[] model = parseModel(entityClass);
		String table = entityClass.getSimpleName();
		String pk = model[PK_NAME].toString();
		Object id = ((Field)model[PK_FIELD]).get(entity);
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ").append(table).append(" WHERE ");
		sql.append(pk).append(" = ?");
		return new SQLModel(sql.toString(), new Object[]{id}, model);
	}
	
	public static SQLModel generateDeleteSQL(Class<?> entityClass, Object id) {
		Object[] model = parseModel(entityClass);
		String table = entityClass.getSimpleName();
		String pk = model[PK_NAME].toString();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ").append(table).append(" WHERE ");
		sql.append(pk).append(" = ?");
		return new SQLModel(sql.toString(), new Object[]{id}, model);
	}
	
	public static Object[] parseModel(Class<?> entityClass) {
		List<Field> fields = BeanPropertyUtil.getAllNonStaticFields(entityClass);
		Object[] info = new Object[INFO_ITEMS];
		info[MODEL_FIELDS] = fields;
		Annotation[] annotations = null;
		for(Field field : fields){
			annotations = field.getAnnotations();
			if(info[PK_VALUE] == null && annotations != null && annotations.length > 0)
				for(Annotation annotation : annotations){
					if(UUID.class.isAssignableFrom(annotation.annotationType())){
						info[PK_FIELD] = field;
						info[PK_VALUE] = pkGenerator(UUID.class);
						info[PK_NAME]  = field.getName();
						info[PK_STRATEGY] = UUID.class;
						return info;
					}
					if(INCREMENT.class.isAssignableFrom(annotation.annotationType())){
						info[PK_FIELD] = field;
						info[PK_NAME]  = field.getName();
						info[PK_STRATEGY] = INCREMENT.class;
						return info;
					}
				}
		}
		return info;
	}
	
	private static SQLModel updateAllColumnSQL(Object obj) throws Throwable {
		Object[] model = parseModel(obj.getClass());
		@SuppressWarnings("unchecked")
		List<Field> fields = (List<Field>) model[MODEL_FIELDS];
		List<Field> traces = new ArrayList<Field>(fields.size());
		String pkName = model[PK_NAME].toString();
		Object[] values = new Object[fields.size()];
		StringBuilder builder = new StringBuilder();
		int index = 0; String fieldName;
		for(Field field : fields){
			fieldName = field.getName();
			if(!fieldName.equals(pkName)){
				traces.add(field);
				values[index++] = field.get(obj);
				builder.append(fieldName).append(" = ?, ");
			}
		}
		traces.add((Field) model[PK_FIELD]);
		values[index] = ((Field) model[PK_FIELD]).get(obj);
		String table = obj.getClass().getSimpleName();
		String columns = builder.substring(0, builder.length() - 2);
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ").append(table).append(" SET ");
		sql.append(columns).append(" WHERE ").append(pkName).append(" = ?");
		model[MODEL_FIELDS] = traces;
		return new SQLModel(sql.toString(), values, model);
	}
	
	private static SQLModel updateActiveColumnSQL(Object newObj, Object oldObj) throws Throwable {
		Object[] model = parseModel(newObj.getClass());
		@SuppressWarnings("unchecked")
		List<Field> fields = (List<Field>) model[MODEL_FIELDS];
		List<Field> activeFields = new ArrayList<Field>();
		String pkName = model[PK_NAME].toString();
		Object[] values = new Object[fields.size()];
		StringBuilder builder = new StringBuilder();
		Object newValue, oldValue;
		int index = 0; String fieldName;
		for(Field field : fields){
			fieldName = field.getName();
			newValue = field.get(newObj);
			oldValue = field.get(oldObj);
			if(!fieldName.equals(pkName) && !newValue.toString().equals(oldValue.toString())){
				activeFields.add(field);
				values[index++] = newValue;
				builder.append(fieldName).append(" = ?, ");
			}
		}
		activeFields.add((Field) model[PK_FIELD]);
		values[index++] = ((Field) model[PK_FIELD]).get(newObj);
		Object[] newValues = new Object[index];
		System.arraycopy(values, 0, newValues, 0, index);
		String table = newObj.getClass().getSimpleName();
		String columns = builder.substring(0, builder.length() - 2);
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ").append(table).append(" SET ");
		sql.append(columns).append(" WHERE ").append(pkName).append(" = ?");
		model[MODEL_FIELDS] = activeFields;
		return new SQLModel(sql.toString(), newValues, model);
	}
	
	public static String pkGenerator(Class<?> pkStrategy) {
		if(pkStrategy != null && UUID.class.isAssignableFrom(pkStrategy))
			return java.util.UUID.randomUUID().toString().replaceAll("-", "");
		return null;
	} 
	
}
package org.squirrel;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.squirrel.util.SQLHelper;

public class Session {

	private boolean showSQL;
	private JdbcTemplate jdbcTemplate;
	
	public <E> E get(Class<E> entityClass, Object id) {
		try {
			String pkName = SQLHelper.parseModel(entityClass)[SQLHelper.PK_NAME].toString();
			Criteria criteria = new Query(entityClass).createCriteria();
			criteria.add(Restrictions.eq(pkName, id));
			return queryObject(criteria);
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}
	
	public int save(Object entity) {
		try {
			SQLModel sql = SQLHelper.generateInsertSQL(entity);
			return jdbcTemplate.update(sql.build(showSQL), sql.params());
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}
	
	public int update(Object entity) {
		try {
			SQLModel sql = SQLHelper.generateUpdateSQL(entity, null);
			return jdbcTemplate.update(sql.build(showSQL), sql.params());
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}
	
	public int delete(Object entity) {
		try {
			SQLModel sql = SQLHelper.generateDeleteSQL(entity);
			return jdbcTemplate.update(sql.build(showSQL), sql.params());
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}
	
	public int delete(Class<?> entityClass, Object id) {
		try {
			SQLModel sql = SQLHelper.generateDeleteSQL(entityClass, id);
			return jdbcTemplate.update(sql.build(showSQL), sql.params());
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}
	
	public int saveOrUpdate(Object entity) {
		try {
			Object[] model = SQLHelper.parseModel(entity.getClass());
			Object pkValue = ((Field)model[SQLHelper.PK_FIELD]).get(entity);
			if(pkValue == null)
				return save(entity);
			Object obj = get(entity.getClass(), pkValue);
			if(obj == null)
				return save(entity);
			SQLModel sql = SQLHelper.generateUpdateSQL(entity, obj);
			return jdbcTemplate.update(sql.build(showSQL), sql.params());
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}
	
	public int[] batchInsert(final List<?> entities) {
		try {
			final SQLModel sql = SQLHelper.generateInsertSQL(entities.get(0));
			return jdbcTemplate.batchUpdate(sql.build(showSQL), new BatchPreparedStatementSetter() {
				List<Field> fields = sql.trace(SQLHelper.MODEL_FIELDS);
				Class<?> pkStrategy = sql.trace(SQLHelper.PK_STRATEGY);
				String pkName = sql.trace(SQLHelper.PK_NAME);
				int length = fields.size();
				Object entity; Field field;
				@Override
				public void setValues(PreparedStatement ps, int pos) throws SQLException {
					for(int i = 0; i < length; i++){
						try {
							field = fields.get(i);
							entity = entities.get(pos);
							if(field.getName().equals(pkName)){
								String pkValue = SQLHelper.pkGenerator(pkStrategy);
								ps.setObject(i + 1, pkValue);
								field.set(entity, pkValue);
							}else{
								ps.setObject(i + 1, field.get(entity));
							}
						} catch (Throwable e) {
							throw new UnexpectedException(e);
						}
					}
				}
				@Override
				public int getBatchSize() {
					return entities.size();
				}
			});
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}
	
	public int[] batchUpdate(final List<?> entities) {
		try {
			final SQLModel sql = SQLHelper.generateUpdateSQL(entities.get(0), null);
			return jdbcTemplate.batchUpdate(sql.build(showSQL), new BatchPreparedStatementSetter() {
				List<Field> fields = sql.trace(SQLHelper.MODEL_FIELDS);
				int length = fields.size();
				@Override
				public void setValues(PreparedStatement ps, int pos) throws SQLException {
					for(int i = 0; i < length; i++)
						try {
							ps.setObject(i + 1, fields.get(i).get(entities.get(pos)));
						} catch (Throwable e) {
							throw new UnexpectedException(e);
						}
				}
				@Override
				public int getBatchSize() {
					return entities.size();
				}
			});
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}
	
	public int[] batchDelete(final List<?> entities) {
		try {
			final SQLModel sql = SQLHelper.generateDeleteSQL(entities.get(0));
			return jdbcTemplate.batchUpdate(sql.build(showSQL), new BatchPreparedStatementSetter() {
				Field pk = sql.trace(SQLHelper.PK_FIELD);
				@Override
				public void setValues(PreparedStatement ps, int pos) throws SQLException {
					try {
						ps.setObject(1, pk.get(entities.get(pos)));
					} catch (Throwable e) {
						throw new UnexpectedException(e);
					}
				}
				@Override
				public int getBatchSize() {
					return entities.size();
				}
			});
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}
	
	public int[] batchDelete(final Class<?> entityClass, final List<?> ids) {
		try {
			final SQLModel sql = SQLHelper.generateDeleteSQL(entityClass, null);
			return jdbcTemplate.batchUpdate(sql.build(showSQL), new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int pos) throws SQLException {
					ps.setObject(1, ids.get(pos));
				}
				@Override
				public int getBatchSize() {
					return ids.size();
				}
			});
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}
	
	public <E> List<E> queryList(final Criteria criteria) {
		try {
			@SuppressWarnings("unchecked")
			Class<E> entityClass = (Class<E>)criteria.getQuery().getEntityClass();
			return jdbcTemplate.query(criteria.build(showSQL), criteria.params(), mapper(entityClass));
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}
	
	public <E> E queryObject(final Criteria criteria) {
		try {
			@SuppressWarnings("unchecked")
			Class<E> entityClass = (Class<E>)criteria.getQuery().getEntityClass();
			return jdbcTemplate.queryForObject(criteria.build(showSQL), criteria.params(), mapper(entityClass));
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}
	
	public <E> List<E> queryList(final Query query) {
		try {
			@SuppressWarnings("unchecked")
			Class<E> entityClass = (Class<E>)query.getEntityClass();
			return jdbcTemplate.query(query.build(showSQL), query.params(), mapper(entityClass));
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}
	
	public <E> E queryObject(final Query query) {
		try {
			@SuppressWarnings("unchecked")
			Class<E> entityClass = (Class<E>)query.getEntityClass();
			return jdbcTemplate.queryForObject(query.build(showSQL), query.params(), mapper(entityClass));
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}
	
	public List<Map<String, Object>> queryRawList(final Query query) {
		return jdbcTemplate.query(query.build(showSQL), query.params(), new ColumnMapRowMapper());
	}

	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void setShowSQL(boolean showSQL) {
		this.showSQL = showSQL;
	}
	
	private <E> RowMapper<E> mapper(Class<E> entityClass){
		return new BeanPropertyRowMapper<E>(entityClass);
	}
	
}
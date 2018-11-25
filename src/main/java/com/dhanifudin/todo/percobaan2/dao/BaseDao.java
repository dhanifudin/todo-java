/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhanifudin.todo.percobaan2.dao;

import com.dhanifudin.todo.percobaan2.lib.ConnectionFactory;
import com.dhanifudin.todo.percobaan2.lib.TextUtil;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dhanifudin
 * @param <E>
 */
public abstract class BaseDao<E> {

	public BaseDao() {
	}

	protected abstract String getTableName();
	protected abstract String getId();
	protected abstract String getPrimaryKey();
	protected abstract void setAttributes(Map<String, Object> attrs, E item);
	protected abstract E iterate(List<E>items, ResultSet resultset) throws SQLException;

	private String questionMarks(int length) {
		StringBuilder sb = new StringBuilder();
		String loopDelimiter = "";
		for (int i = 0; i < length; i++) {
			sb.append(loopDelimiter);
			sb.append("?");
			loopDelimiter = ",";
		}
		return sb.toString();
	}

	protected String getJoinedTables() {
		return getTableName();
	}

	public List<E> all() {
		return search(null);
	}

	public E getById(Object id) {
		Map<String, Object> conditions = new HashMap<>();
		String key = String.format("%s.%s", getTableName(), getId());
		conditions.put(key, id);
		List<E> items = search(conditions, false);
		return (items.size() > 0) ? items.get(0) : null;
	}

	public List<E> search(Map<String, Object> conditions) {
		return search(conditions, false);
	}

	public List<E> search(Map<String, Object> conditions, boolean useLike) {
		List<E> items = null;
		try {
			String operator = (useLike) ? " like ? " : " = ? ";
			String where = (conditions != null) ? 
				TextUtil.join(conditions.keySet(), operator, "and") : "1";
			String sql = String.format("select * from %s where %s", getJoinedTables(), where);
			Connection connection = ConnectionFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			if (conditions != null) {
				Collection<Object> values = conditions.values();
				int i = 1;
				for (Object o : values) {
					statement.setObject(i, o);
					i++;
				}
			}
			ResultSet resultset = statement.executeQuery();
			items = new ArrayList<>();
			while(resultset.next()) {
				E item = iterate(items, resultset);
				items.add(item);
			}
		} catch (SQLException ex) {
			Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		return items;
	}

	public int insert(E item) {
		Map<String, Object> attrs = new HashMap<>();
		setAttributes(attrs, item);
		int result = -1;
		try {
			Set<String> keys = attrs.keySet();
			Collection<Object> values = attrs.values();
			String fields = TextUtil.join(keys, ",");
			String params = questionMarks(keys.size());
			String sql = String.format("insert into %s (%s) values (%s)", getTableName(), fields, params);

			Connection connection = ConnectionFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);

			int i = 1;
			for (Object o : values) {
				statement.setObject(i, o);
				i++;
			}

			result = statement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	public int update(E item) {
		Map<String, Object> attrs = new HashMap<>();
		setAttributes(attrs, item);
		int result = -1;
		try {
			attrs.remove(getId());
			Set<String> keys = attrs.keySet();
			String fields = TextUtil.join(keys, "=?", ",");
			String sql = String.format("update %s set %s where %s = ?", getTableName(), fields, getId());

			Connection connection = ConnectionFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);

			int i = 1;
			for (Map.Entry<String, Object> e : attrs.entrySet()) {
				statement.setObject(i, e.getValue());
				i++;
			}

			Class itemClass = item.getClass();
			Object key = itemClass.getMethod(getPrimaryKey()).invoke(item);
			statement.setObject(i, key);

			result = statement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NoSuchMethodException ex) {
			Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvocationTargetException ex) {
			Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	public int delete(E item) {
		int result = -1;
		try {
			String sql = String.format("delete from %s where %s = ?", getTableName(), getId());
			Connection connection = ConnectionFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);

			Class itemClass = item.getClass();
			Object key = itemClass.getMethod(getPrimaryKey()).invoke(item);
			statement.setObject(1, key);
			result = statement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NoSuchMethodException ex) {
			Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvocationTargetException ex) {
			Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	protected int executeUpdate(String sql) {
		int result = -1;
		try {
			Connection connection = ConnectionFactory.getConnection();
			Statement statement = connection.createStatement();
			result = statement.executeUpdate(sql);
		} catch (SQLException ex) {
			Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

}

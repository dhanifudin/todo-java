/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhanifudin.todo.percobaan2.dao;

import com.dhanifudin.todo.percobaan2.entities.User;
import com.dhanifudin.todo.percobaan2.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dhanifudin
 */
public class UserDao extends BaseDao<User> {

	@Override
	protected String getTableName() {
		return "users";
	}

	@Override
	protected String getId() {
		return "id";
	}

	@Override
	protected String getPrimaryKey() {
		return "getId";
	}

	@Override
	protected void setAttributes(Map<String, Object> attrs, User item) {
		attrs.put("id", item.getId());
		attrs.put("username", item.getUsername());
		attrs.put("password", item.getPassword());
	}

	@Override
	protected User iterate(List<User> items, ResultSet resultset) throws SQLException {
		User user = new User();
		user.setId(resultset.getInt("id"));
		user.setUsername(resultset.getString("username"));
		user.setPassword(resultset.getString("password"));
		return user;
	}

}

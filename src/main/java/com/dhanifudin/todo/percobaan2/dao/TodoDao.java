/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhanifudin.todo.percobaan2.dao;

import com.dhanifudin.todo.percobaan2.entities.Todo;
import com.dhanifudin.todo.percobaan2.entities.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dhanifudin
 */
public class TodoDao extends BaseDao<Todo> {

	@Override
	protected String getTableName() {
		return "todo";
	}

	@Override
	protected String getJoinedTables() {
		return "todo join users on todo.user_id = users.id";
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
	protected void setAttributes(Map<String, Object> attrs, Todo item) {
		attrs.put("id", item.getId());
		attrs.put("user_id", item.getUser().getId());
		attrs.put("todo", item.getTodo());
		attrs.put("done", item.isDone());
	}

	@Override
	protected Todo iterate(List<Todo> items, ResultSet resultset) throws SQLException {
		Todo todo = new Todo();
		User user = new User();
		todo.setId(resultset.getInt("todo.id"));
		todo.setTodo(resultset.getString("todo.todo"));
		todo.setDone(resultset.getBoolean("todo.done"));
		user.setId(resultset.getInt("users.id"));
		user.setUsername(resultset.getString("users.username"));
		user.setPassword(resultset.getString("users.password"));
		todo.setUser(user);
		return todo;
	}
	
}

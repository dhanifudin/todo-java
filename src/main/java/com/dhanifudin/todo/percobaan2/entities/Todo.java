/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhanifudin.todo.percobaan2.entities;

/**
 *
 * @author dhanifudin
 */
public class Todo {
	private Integer id;
	private User user;
	private String todo;
	private boolean done;

	public Todo() {}

	public Todo(Integer id, User user, String todo, boolean done) {
		this.id = id;
		this.user = user;
		this.todo = todo;
		this.done = done;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}


}

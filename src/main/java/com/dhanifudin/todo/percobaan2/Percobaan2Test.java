/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhanifudin.todo.percobaan2;

import com.dhanifudin.todo.percobaan2.dao.TodoDao;
import com.dhanifudin.todo.percobaan2.dao.UserDao;
import com.dhanifudin.todo.percobaan2.entities.Todo;
import com.dhanifudin.todo.percobaan2.entities.User;
import java.util.List;

/**
 *
 * @author dhanifudin
 */
public class Percobaan2Test {
	public static void main(String[] args) {
//		User user = new User(1, "dhanifudin", "OK");
//		UserDao dao = new UserDao();
//		List<User> users = dao.all();
//		for (User u : users) {
//			System.out.println(u);
//		}
		TodoDao dao = new TodoDao();
		List<Todo> todos = dao.all();
		for (Todo todo : todos) {
			System.out.println(todo);
		}

		System.out.println("////");

		Todo todo = dao.getById(1);
		System.out.println(todo);
	}
}

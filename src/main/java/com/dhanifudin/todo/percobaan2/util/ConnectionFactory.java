/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhanifudin.todo.percobaan2.util;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author dhanifudin
 */
public class ConnectionFactory {

	private static final BasicDataSource dataSource = new BasicDataSource();

	static {
		dataSource.setUrl("jdbc:mysql://localhost/todo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		dataSource.setUsername("dhanifudin");
		dataSource.setPassword("database");
		dataSource.setMinIdle(5);
		dataSource.setMaxIdle(10);
		dataSource.setMaxOpenPreparedStatements(100);
	}

	private ConnectionFactory() {}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}

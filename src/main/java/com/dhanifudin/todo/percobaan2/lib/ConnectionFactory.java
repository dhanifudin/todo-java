/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhanifudin.todo.percobaan2.lib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author dhanifudin
 */
public class ConnectionFactory {

	private static final BasicDataSource dataSource = new BasicDataSource();

	static {
		InputStream input = null;
		try {
			String home = System.getProperty("user.home");
			String config =  String.format("%s/.config.properties", home);
			input = new FileInputStream(config);
			Properties properties = new Properties();
			properties.load(input);
			String server = properties.getProperty("server");
			String database = properties.getProperty("database");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");
			String url = String.format("jdbc:mysql://%s/%s", server, database);
			dataSource.setUrl(url);
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			dataSource.setMinIdle(5);
			dataSource.setMaxIdle(10);
			dataSource.setMaxOpenPreparedStatements(100);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException ex) {
					Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	private ConnectionFactory() {}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}

package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connector class establishes connection to a defined database
 * connection driver must be in apache /lib dir or classnotfound exception 
 * (ie "tomcat/10.0.16/libexec/lib/mysql-connector-java-8.0.28.jar")
 * @author dannyzorn
 *
 */
public class DB_Connector {
	//init class vars Travis project
//	private String dbUrl = "jdbc:mysql://localhost:3306/fitphys_db";
//	private String dbUname = "root";
//	private String dbPassword = "password";

	//init class vars Danny project
	private String dbUrl = "jdbc:mysql://localhost:3306/fitphys_db";
	private String dbUname = "root";
	private String dbPassword = "Password";
	
	//init class vars Victor project
//	private String dbUrl = "jdbc:mysql://localhost:3306/fitphys_db";
//	private String dbUname = "root";
//	private String dbPassword = "root";

	//init class vars Swann project
//	private String dbUrl = "jdbc:mysql://localhost:3306/fitphys_db";
//	private String dbUname = "root";
//	private String dbPassword = "root";
	
	//init class vars VM project
//	private String dbUrl = "jdbc:mysql://192.168.6.3:3306/fitphys_db";
//	private String dbUname = "student";
//	private String dbPassword = "Cpt275Ttc!";
	
	//constructor
	public DB_Connector() {
		
	}
	
	//create connection to the db
	public Connection connect() {
		Connection l_connector = null;
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
			l_connector = DriverManager.getConnection(dbUrl, dbUname, dbPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l_connector;
	}
}

package org.wwu.bpm.wfm.processEmtours.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class MySQLConnector {
	private static String dbHost="172.17.0.2";
	private static String dbPort="3306";
	private static String dbName="";
	private static String dbUser="root";
	private static String dbPass="root";
	private static boolean autogenerate = false;

	private static Connection con;

	private MySQLConnector(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection tempCon = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/?user=root&password=root");
			//createMissingDatabase(tempCon);
			tempCon.close();
			con = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+ dbPort+"/"+dbName+"?"+"user="+dbUser+"&"+"password="+dbPass);
			//createMissingTables();
			if(autogenerate){
				//createDefaultEntries();
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String [] args){
	}
	
	public static Connection getConnection(){
		if(con==null)
			new MySQLConnector();
		return con;
	}
	
	private static void createTables(){
		Vector<String> dbNames = new Vector<String>();
		try {
			ResultSet rs = getConnection().getMetaData().getTables(getConnection().getCatalog(), null, null, null);
			int i=0;
			while(rs.next()){
				dbNames.add(rs.getString(3));
			}

			if(!dbNames.contains("customer")){
				String statement = 
						"CREATE TABLE `customer` ("
								+"`id_customer` INT NOT NULL AUTO_INCREMENT,"
								+"`customername` VARCHAR(45) NULL,"
								+"`birthday` DATE NULL,"
								+"`tel` VARCHAR(45) NULL,"
								+"`email` VARCHAR(45) NULL,"
								+"PRIMARY KEY (`id_customer`),"
								+"UNIQUE INDEX `email_UNIQUE` (`email` ASC));";
				Statement stmt = getConnection().createStatement();
				stmt.executeUpdate(statement);
			} 
			if(!dbNames.contains("Request")){
				String statement = 
						"CREATE TABLE `Request` ("
								+"`id_request` INT NOT NULL AUTO_INCREMENT,"
								+"`interests` VARCHAR(255) NULL,"
								+"`budget` DOUBLE(10,2) NULL,"
								+"`number_adults` INT(5) NULL,"
								+"`number_children` INT(5) NULL,"
								+"`start_date` DATE NULL,"
								+"`end_date` DATE NULL,"
								+"`payment_method` VARCHAR(45) NULL,"
								+"`customer_id` INT NOT NULL,"
								+"PRIMARY KEY (`id_request`),"
								+"FOREIGN KEY (`customer_id`)"
								+"REFERENCES `customer` (`id_customer`)";
				Statement stmt = getConnection().createStatement();
				stmt.executeUpdate(statement);
			}
			if(!dbNames.contains("recommendation")){
				String statement = 
						"CREATE TABLE `recommendation` ("
								+"`id_recommendation` INT NOT NULL AUTO_INCREMENT,"
								+"`start_date` DATE NOT NULL,"
								+"`end_date` DATE NOT NULL,"
								+"`accommodation` VARCHAR(45) NULL,"
								+"`transport` VARCHAR(255) NULL,"
								+"`cost` DOUBLE(10,2) NULL,"
								+"`number_customer` INT(5) NULL,"
								+"PRIMARY KEY (`id_recommendation`),"
								+"FOREIGN KEY (`customer_id`)"
								+"REFERENCES `customer` (`id_customer`);";
				Statement stmt = getConnection().createStatement();
				stmt.executeUpdate(statement);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

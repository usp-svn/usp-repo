package net.webHMI.pli.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	public ConnectDB(){
		
	}
	public Connection connectToDB() {

		Connection connection = null;

		try
		{
			// the sql server driver string
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			// the sql server url
			//String url = "jdbc:sqlserver://191.168.250.150:1433;DatabaseName=CC_PipeID_10_11_02_22_28_11";//CC_PipeID_10_11_07_18_26_25";  //
			//String url = "jdbc:sqlserver://192.168.1.72:1433;DatabaseName=CC_PipeID_10_11_02_22_28_11";//CC_PipeID_10_11_07_18_26_25";  //
			//connection = DriverManager.getConnection(url,"sa", "sa");
			
			
			//String url = "jdbc:sqlserver://191.168.100.100:1433;DatabaseName=CC_PipeID_10_12_02_14_18_02";//CC_PipeID_10_09_29_18_38_38";
			//connection = DriverManager.getConnection(url,"sa", "usppipeid");
			
			//String url = "jdbc:sqlserver://LT026:1433;DatabaseName=CC_PipeID_10_09_29_18_38_38";
			//connection = DriverManager.getConnection(url,"pis", "pis");
			
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");         
			String conStr = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" +             
			"\\\\192.168.16.248\\Data\\PipeCounters\\pipectr.accdb";         
			connection = DriverManager.getConnection(conStr);  
			
		}
		catch (ClassNotFoundException e)
		{    
			System.err.println("connectToDB() " + e.getMessage());
			e.printStackTrace();
			

		}
		catch (SQLException e)
		{
			System.err.println("connectToDB() " + e.getMessage());
			e.printStackTrace();

		}
		return connection;


	}
}

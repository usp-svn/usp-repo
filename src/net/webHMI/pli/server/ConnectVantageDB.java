package net.webHMI.pli.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectVantageDB {

public ConnectVantageDB(){
		
	}
	public Connection connectToDB() {

		Connection connection = null;
		
		try {
			// the sql server driver string
						Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

						// the sql server url						
						String url = "jdbc:sqlserver://VANTAGE:1433; DatabaseName=MfgSys803";
						connection = DriverManager.getConnection(url,"sqldev", "Sqlreports!");
						
					 
						
					}
					catch (ClassNotFoundException e)
					{    
						System.err.println("connectVantageDB() " + e.getMessage());
						e.printStackTrace();
						

					}
					catch (SQLException e)
					{
						System.err.println("connectVantageDB()  " + e.getMessage());
						e.printStackTrace();

					}
					return connection;
	}

}

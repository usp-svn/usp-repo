package net.webHMI.pli.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectPipeIDDB {
	

	public ConnectPipeIDDB(){
		
	}
	public Connection connectToDB() {

		Connection connection = null;
		
		try {
			// the sql server driver string
						Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

						// the sql server url						
						String url = "jdbc:sqlserver://192.168.16.99:1433; DatabaseName=pipedb";
						connection = DriverManager.getConnection(url,"sa", "usppipeid");
						
					 
						
					}
					catch (ClassNotFoundException e)
					{    
						System.err.println("connectPipeIDDB() " + e.getMessage());
						e.printStackTrace();
						

					}
					catch (SQLException e)
					{
						System.err.println("connectPipeIDDB() " + e.getMessage());
						e.printStackTrace();

					}
					return connection;
	}

}

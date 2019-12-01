package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataSource {

	public static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// Connection from HOME
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@199.212.26.208:1521:SQLD", "COMP214_F19_zor_84", "password");
			
			// Connection from COLLEGE (Secured WIFI)
			//Connection con = DriverManager.getConnection("jdbc:oracle:thin:@oracle1.centennialcollege.ca:SQLD", "COMP214_F19_zor_84", "password");
			
			return con;
		} catch (Exception ex) {
			System.out.println("Database.getConnection() Error -->" + ex.getMessage());
			return null;
		}
	}

	public static void close(Connection con) {
		try {
			con.close();
		} catch (Exception ex) {
		}
	}

}

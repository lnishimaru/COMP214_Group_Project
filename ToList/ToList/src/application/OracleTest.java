package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class OracleTest {
	public static void main(String[] args) {
		try {
			Connection con = DataSource.getConnection();

			//------------------------------------------------------------------
			// using statement
			Statement statement = con.createStatement();
			String sql = "Select * from DD_PROJECT";
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next())
				System.out.println(rs.getInt(1) + "    " + rs.getString(2));

			//------------------------------------------------------------------
			// using PreparedStatement with parameters on SQL
			System.out.println();
			PreparedStatement ps = con.prepareStatement("Select * from DD_PROJECT where IDPROJ = ?");
			ps.setInt(1, 532);
			ResultSet rs1 = ps.executeQuery();

			while (rs1.next())
				System.out.println(rs1.getInt(1) + "    " + rs1.getString(2));

			//------------------------------------------------------------------
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

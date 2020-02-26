package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Main {
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Connection conn = DB.getConnection();
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES"
					+ "(?, ?, ?, ?, ?)"
					, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, "Anna");
			ps.setString(2, "anna@gmail.com");
			ps.setDate(3, new java.sql.Date(sdf.parse("03/03/1999").getTime()));
			ps.setDouble(4, 3500.0);
			ps.setInt(5, 1);
			
			int linhasAfetadas = ps.executeUpdate();
			
			if(linhasAfetadas > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				while(rs.next()) {
					int id = rs.getInt(1);
					
					System.out.println("Pronto! Id = " + id);
				}
			}else {
				System.out.println("Nem uma linha afetada");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(ps);
			DB.closeConnection();
		}
	}
}

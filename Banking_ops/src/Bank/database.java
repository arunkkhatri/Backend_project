package Bank;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
	public static Connection getConnection(){
		Connection con=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","akumar15");
			return con;
		}catch(Exception e){System.out.println(e);}
		return con;
	}

}


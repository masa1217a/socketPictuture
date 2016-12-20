package watchdog;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBAccess {
	static String sel = "PhotoID";
	static int photo_id;
	static int connect_id;
	static int day;
	static int time;
	static String pass;
	static int point;


	DBAccess(int connect_id, int day, int time, String pass, int point){
		this.connect_id = connect_id;
		this.day = day;
		this.time = time;
		this.pass = pass;
		this.point = point;
	}
	public static void DBinsert(){
		//System.out.println(photo_id +"\t"+ connect_id +"\t"+ day +"\t"+ time +"\t"+ pass +"\t"+ point);

	        try {
	            // JDBCドライバのロード - JDBC4.0（JDK1.6）以降は不要
	            //Class.forName("com.mysql.jdbc.Driver").newInstance();
	            // MySQLに接続
	        	Connection con = null;
	            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_camera", "root", "superman");
				 
	            PreparedStatement stm;
	            stm = con.prepareStatement("INSERT INTO t_test VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
							stm.setInt(1, photo_id);
							stm.setInt(2, connect_id);
							stm.setInt(3, day);
							stm.setInt(4, time);
							stm.setString(5, pass);
							stm.setInt(6, 0);
							stm.setInt(7, point);
							stm.setString(8, "");
		        int result = stm.executeUpdate();
		       // System.out.println("挿入できました。");
		        System.out.println("MySQLに接続できました。");
		        System.out.println("更新件数は" + result + "です。");

		        stm.close();
               con.close();
	        } catch (SQLException e) {
	            System.out.println("MySQLに接続できませんでした。");
	        }
	}


	public static void DBnextID(){
		try {
            // MySQLに接続
        	Connection con = null;
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_camera", "root", "superman");
          // System.out.println("MySQLに接続できました。");
					 Statement stmt = con.createStatement();
					 ResultSet rs = stmt.executeQuery("select PhotoID from t_test order by PhotoID desc limit 1");
					 while(rs.next()){
						 photo_id = rs.getInt(sel)+1;
					 }
					 System.out.println(photo_id);
			 stmt.close();
           con.close();
        	 //rs.close();
        } catch (SQLException e) {
            System.out.println("MySQLに接続できませんでした。");
        }
	}

}

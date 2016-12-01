package watchdog;

import java.sql.*;
import java.util.ArrayList;

public class DBAccess {
	int photo_id;
	int connect_id;
	int day;
	int time;
	static String pass = new String("/var/www/html/php/test/TEST/");
	int point;
	
	
	DBAccess(int photo_id, int connect_id, int day, int time, String pass, int point){
		this.photo_id = photo_id;
		this.connect_id = connect_id;
		this.day = day;
		this.time = time;
		this.pass = pass;
		this.point = point;
	}
	
	public static void DBconnect(){
		 
	        try {
	            // JDBCドライバのロード - JDBC4.0（JDK1.6）以降は不要
	            //Class.forName("com.mysql.jdbc.Driver").newInstance();
	            // MySQLに接続
	        	Connection con;
	            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_camera", "root", "superman");
	            System.out.println("MySQLに接続できました。");
	            Statement stm;
	            stm = con.createStatement();
		        int result = stm.executeUpdate("INSERT INTO T_t VALUES(5, 'aho')");
		       // System.out.println("挿入できました。");
		        System.out.println("更新件数は" + result + "です。");
		        
		        stm.close();
               con.close();
	        } catch (SQLException e) {
	            System.out.println("MySQLに接続できませんでした。");
	        }
	}
	
}



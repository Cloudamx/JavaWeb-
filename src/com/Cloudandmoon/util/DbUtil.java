package com.Cloudandmoon.util;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;

//导错包了！

public class DbUtil {
	
	private String diverclass = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/db_student_manager_web?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false";
	private String user = "root";
	private String password = "zxc2486";
	private Connection connection = null;

	public Connection getConnection() {
		
			try {
				Class.forName(diverclass);
				connection = DriverManager.getConnection(url, user, password);
				System.out.println("成功了！");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		return connection;
	}
	
	public void closeCon() {
		if(connection != null)
			try {
				connection.close();
				System.out.println("数据库连接已关闭");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	public static void main(String[] args) {
		DbUtil dbUtil = new DbUtil();
		
		dbUtil.getConnection();
		
	}
	
	
	
}

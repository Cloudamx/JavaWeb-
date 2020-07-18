package com.Cloudandmoon.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Cloudandmoon.util.DbUtil;


public class BaseDao {
	
	private DbUtil dbUtil =  new DbUtil();
	
	/*
	 * 关闭数据库连接
	 * 
	 */
	
	public void ConClose() {
		dbUtil.closeCon();
	}
	
	public ResultSet query(String sql) {
		
		try {
			//实在没看出来哪里错了！！！
			//sql有好多个包，导错了我去
			PreparedStatement preparedStatement =  dbUtil.getConnection().prepareStatement(sql);
			return preparedStatement.executeQuery();
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
		
	}
	
	public boolean update(String sql) {
		try {
			return dbUtil.getConnection().prepareStatement(sql).executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public Connection getConnection(){
		return dbUtil.getConnection();
	}
	
	
}

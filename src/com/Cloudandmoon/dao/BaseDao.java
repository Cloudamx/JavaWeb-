package com.Cloudandmoon.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Cloudandmoon.util.DbUtil;


public class BaseDao {
	
	private DbUtil dbUtil =  new DbUtil();
	
	/*
	 * �ر����ݿ�����
	 * 
	 */
	
	public void ConClose() {
		dbUtil.closeCon();
	}
	
	public ResultSet query(String sql) {
		
		try {
			//ʵ��û������������ˣ�����
			//sql�кö��������������ȥ
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

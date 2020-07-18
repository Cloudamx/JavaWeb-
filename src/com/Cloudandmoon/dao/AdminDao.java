package com.Cloudandmoon.dao;
/*
 * 管理员数据库
 * 操作封装
 * 
 * 
 */

import java.sql.ResultSet;
import java.sql.SQLException;

import com.Cloudandmoon.model.Admin;
import com.Cloudandmoon.model.Clazz;

public class AdminDao extends BaseDao{
	public Admin login(String name, String password) {
		String sql = "select * from s_admin where name = '" + name + "' and password = '" + password+"'";
		
		ResultSet resultset = query(sql);
		
		try {
			if(resultset.next()) {
				//获取东西之前
				//创建对象 设置对象   循环读取    遍历放入设置
				
				Admin admin = new Admin();
				//admin.setId(resultset.getInt(0));
				admin.setId(resultset.getInt("id"));
				admin.setName(resultset.getString("Name"));
				admin.setPasswrod(resultset.getString("password"));
				admin.setStatus(resultset.getInt("status"));
				return admin;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public boolean editPassword(Admin admin,String newpassword) {
		String sql = "update s_admin set password = '"+newpassword+"' where id = " + admin.getId();
		return update(sql);
	}
	
}

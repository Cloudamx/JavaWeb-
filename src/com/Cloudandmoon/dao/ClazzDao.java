package com.Cloudandmoon.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Cloudandmoon.model.Clazz;
import com.Cloudandmoon.model.Page;
import com.Cloudandmoon.util.StringUtil;

public class ClazzDao extends BaseDao{
	
	public int getClazzlistTotal(Clazz clazz){
		
		
		int total = 0;
		
		String sql = "select count(*) as total from db_clazz ";
		
		if(!StringUtil.isEmpty(clazz.getName())) {
			sql += "where name like '%"+clazz.getName()+"%'";
		}
		System.out.println(sql);
		ResultSet resultSet = query(sql);
		
		try {
			while(resultSet.next()) {
				total= resultSet.getInt("total");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
	   		e.printStackTrace();
		}
		
		
		
		return total;
	}
	
	//是直接与数据库进行操作的方法
	public boolean addClazz(Clazz clazz){
		
		
		
		String sql = "insert into db_clazz values(null,'"+clazz.getName()+"','"+clazz.getInfo()+"') ";
		
		
		
		System.out.println(sql);
		ResultSet resultSet = query(sql);
		
		return update(sql);
	}
	
public boolean deleteClazz(int id){
		
		
		
		String sql = "delete from db_clazz where id = " + id;
		
		
		
		System.out.println(sql);
		ResultSet resultSet = query(sql);
		
		return update(sql);
	}
	
	
	
	//获取对象集合
	public List<Clazz> getClazzlist(Clazz clazz,Page page){
		
		//创建一个对象集
		List<Clazz> ret = new ArrayList<>();
		
		String sql = "select * from db_clazz ";
		
		if(!StringUtil.isEmpty(clazz.getName())) {
			//在一个Sql里面一个空格也会爆炸
			//sql += "where name like '%"+clazz.getName()+"%'";
			sql += "where name like'%"+clazz.getName()+"%'";
		}
		
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		
		System.out.println(sql);
		ResultSet resultSet = query(sql);
		
		try {
			while(resultSet.next()){
				Clazz cl = new Clazz();
				cl.setId(resultSet.getInt("id"));
				cl.setName(resultSet.getString("Name"));
				//cl.setInfo(resultSet.getString("id")) ctrl + alt + ↓ + 不改动
				cl.setInfo(resultSet.getString("Info"));       
				ret.add(cl);	
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public boolean editClazz(Clazz clazz) {
		String sql = "update db_clazz set name = '"+clazz.getName()+"',info = '"+clazz.getInfo()+"'"
				+ " where id = " + clazz.getId();
		return update(sql);
	}
	
	
}

package com.Cloudandmoon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Cloudandmoon.model.Page;
import com.Cloudandmoon.model.Teacher;
import com.Cloudandmoon.model.Teacher;
import com.Cloudandmoon.model.Teacher;
import com.Cloudandmoon.model.Teacher;
import com.Cloudandmoon.model.Teacher;
import com.Cloudandmoon.util.StringUtil;

public class TeacherDao extends BaseDao{
	
	public boolean addTeacher(Teacher teacher){
		String sql = "insert into s_teacher values(null,'"+teacher.getSn()+"','"+teacher.getName()+"'";
		sql += ",'" + teacher.getPassword() + "'," + teacher.getClazzId();
		sql += ",'" + teacher.getSex() + "','" + teacher.getMobile() + "'";
		sql += ",'" + teacher.getQq() + "',null)";
		return update(sql);
	}
	
	public boolean editTeacher(Teacher teacher) {
		String sql = "update s_teacher set name = '"+teacher.getName()+"'";
				sql += ", sex = '" + teacher.getSex() + "'";
				sql += ", mobile = '" + teacher.getMobile() + "'";
				sql += ", qq = '" + teacher.getQq() +"'";
				sql += ", clazz_id ='" + teacher.getClazzId()+	"'";
				sql += " where id = " + teacher .getId();
		return update(sql);
	}
	
	//删除老师
	public boolean deleteTeacher(String ids) {
		String sql = "delete from s_teacher where id  in("+ids+")";
				
		return update(sql);
	}
	
	
	
	
	//获取全体老师
public List<Teacher> getTeacherList(Teacher teacher,Page page){
		
		//创建一个对象集
		List<Teacher> ret = new ArrayList<>();
		String sql = "select * from s_teacher ";
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql += "and name like'%"+teacher.getName()+"%'";
		}
		if(teacher.getClazzId() != 0) {
			sql += " and clazz_id = "+teacher.getClazzId();
		}
		if(teacher.getId() != 0) {
			sql += " and id = "+teacher.getId();
		}
		
		
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultSet = query(sql.replaceFirst("and", "where"));
		try {
			while(resultSet.next()){
				Teacher t = new Teacher();
				t.setId(resultSet.getInt("id"));
				t.setClazzId(resultSet.getInt("clazz_id"));
				t.setName(resultSet.getString("Name"));
				t.setMobile(resultSet.getString("mobile"));      
				t.setName(resultSet.getString("name"));
				t.setPassword(resultSet.getString("password"));
				t.setPhoto(resultSet.getBinaryStream("photo"));
				t.setQq(resultSet.getString("qq"));
				t.setSex(resultSet.getString("sex"));
				t.setSn(resultSet.getString("sn"));
				ret.add(t);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public int getTeacherListTotal(Teacher teacher){
	
	
		int total = 0;
		
		String sql = "select count(*) as total from s_teacher ";
		
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql += "and name like'%"+teacher.getName()+"%'";
		}
		if(teacher.getClazzId() != 0) {
			sql += " and clazz_id = "+teacher.getClazzId();
		}
		if(teacher.getId() != 0) {
			sql += " and id = "+teacher.getId();
		}
		
		System.out.println(sql);
		ResultSet resultSet = query(sql.replaceFirst("and", "where"));
		
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

	public boolean SetTeacherPhoto(Teacher teacher) {
		String sql = "update s_teacher set photo = ? where id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setBinaryStream(1, teacher.getPhoto());
			prepareStatement.setInt(2, teacher.getId());
			return prepareStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return update(sql);
	}

	public Teacher getTeacher(int tid) {
		
		String sql = "select * from s_teacher where id = " + tid;
		Teacher teacher = null;
		
		ResultSet resultSet = query(sql);
		
		try {
			if(resultSet.next()) {
				teacher = new Teacher();
				teacher.setId(resultSet.getInt("id"));
				teacher.setClazzId(resultSet.getInt("clazz_id"));
				teacher.setMobile(resultSet.getString("mobile"));
				teacher.setName(resultSet.getString("name"));
				teacher.setPassword(resultSet.getString("password"));
				teacher.setPhoto(resultSet.getBinaryStream("photo"));
				teacher.setQq(resultSet.getString("qq"));
				teacher.setSex(resultSet.getString("sex"));
				teacher.setSn(resultSet.getString("sn"));
				return teacher;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return teacher;

	}
	
	public Teacher login(String name, String password) {
		String sql = "select * from s_teacher where name = '" + name + "' and password = '" + password+"'";
		
		ResultSet resultset = query(sql);
		
		try {
			if(resultset.next()) {
				//获取东西之前
				//创建对象 设置对象   循环读取    遍历放入设置
				
				Teacher teacher = new Teacher();
				//admin.setId(resultset.getInt(0));
				teacher.setId(resultset.getInt("id"));
				teacher.setName(resultset.getString("Name"));
				teacher.setPassword(resultset.getString("password"));
				teacher.setClazzId(resultset.getInt("clazz_id"));
				teacher.setMobile(resultset.getString("mobile"));
				teacher.setPhoto(resultset.getBinaryStream("photo"));
				teacher.setQq(resultset.getString("qq"));
				teacher.setSex(resultset.getString("sex"));
				teacher.setSn(resultset.getString("sn"));
				
				
				return teacher;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	public boolean editPassword(Teacher teacher, String newpassword) {
		String sql = "update s_teacher set password = '"+newpassword+"' where id = " + teacher.getId();
		return update(sql);
	}
	
	
	
}

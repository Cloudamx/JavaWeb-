package com.Cloudandmoon.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Cloudandmoon.model.Admin;
import com.Cloudandmoon.model.Page;
import com.Cloudandmoon.model.Student;
import com.Cloudandmoon.util.StringUtil;

public class StudentDao extends BaseDao {
	
	//登录
	public Student login(String name, String password) {
		String sql = "select * from s_student where name = '" + name + "' and password = '" + password+"'";
		
		ResultSet resultset = query(sql);
		
		try {
			if(resultset.next()) {
				//获取东西之前
				//创建对象 设置对象   循环读取    遍历放入设置
				
				Student studnet = new Student();
				//admin.setId(resultset.getInt(0));
				studnet.setId(resultset.getInt("id"));
				studnet.setName(resultset.getString("Name"));
				studnet.setPassword(resultset.getString("password"));
				studnet.setClazzId(resultset.getInt("clazz_id"));
				studnet.setMobile(resultset.getString("mobile"));
				studnet.setPhoto(resultset.getBinaryStream("photo"));
				studnet.setQq(resultset.getString("qq"));
				studnet.setSex(resultset.getString("sex"));
				studnet.setSn(resultset.getString("sn"));
				
				
				return studnet;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	
	public boolean addStudent(Student student){
		String sql = "insert into s_student values(null,'"+student.getSn()+"','"+student.getName()+"'";
		sql += ",'" + student.getPassword() + "'," + student.getClazzId();
		sql += ",'" + student.getSex() + "','" + student.getMobile() + "'";
		sql += ",'" + student.getQq() + "',null)";
		return update(sql);
	}
	
	public boolean editStudent(Student student) {
		String sql = "update s_student set name = '"+student.getName()+"'";
				sql += ", sex = '" + student.getSex() + "'";
				sql += ", mobile = '" + student.getMobile() + "'";
				sql += ", qq = '" + student.getQq() +"'";
				sql += ", clazz_id ='" + student.getClazzId()+	"'";
				sql += " where id = " + student .getId();
		return update(sql);
	}
	//删除
	public boolean deleteStudent(String ids) {
		String sql = "delete from s_student where id  in("+ids+")";
				
		return update(sql);
	}
	
	
	
	
	public boolean SetStudentPhoto(Student student) {
		String sql = "update s_student set photo = ? where id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setBinaryStream(1, student.getPhoto());
			prepareStatement.setInt(2, student.getId());
			return prepareStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return update(sql);
	}
	
	
	
	
	public Student getStudent(int id) {
		String sql = "select * from s_student where id = " + id;
		Student student = null;
		
		ResultSet resultSet = query(sql);
		
		try {
			if(resultSet.next()) {
				student = new Student();
				student.setId(resultSet.getInt("id"));
				student.setClazzId(resultSet.getInt("clazz_id"));
				student.setMobile(resultSet.getString("mobile"));
				student.setName(resultSet.getString("name"));
				student.setPassword(resultSet.getString("password"));
				student.setPhoto(resultSet.getBinaryStream("photo"));
				student.setQq(resultSet.getString("qq"));
				student.setSex(resultSet.getString("sex"));
				student.setSn(resultSet.getString("sn"));
				return student;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return student;
		
	}
	
	public List<Student> getStudentList(Student student,Page page){
		
		//创建一个对象集
		List<Student> ret = new ArrayList<>();
		String sql = "select * from s_student ";
		if(!StringUtil.isEmpty(student.getName())) {
			sql += "and name like'%"+student.getName()+"%'";
		}
		if(student.getClazzId() != 0) {
			sql += " and clazz_id ="+student.getClazzId();
		}
		if(student.getId() != 0) {
			sql += " and id ="+student.getId();
		}
		
		System.out.println(sql);
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultSet = query(sql.replaceFirst("and", "where"));
		try {
			while(resultSet.next()){
				Student s = new Student();
				s.setId(resultSet.getInt("id"));
				s.setClazzId(resultSet.getInt("clazz_id"));
				s.setName(resultSet.getString("Name"));
				s.setMobile(resultSet.getString("mobile"));      
				s.setName(resultSet.getString("name"));
				s.setPassword(resultSet.getString("password"));
				s.setPhoto(resultSet.getBinaryStream("photo"));
				s.setQq(resultSet.getString("qq"));
				s.setSex(resultSet.getString("sex"));
				s.setSn(resultSet.getString("sn"));
				ret.add(s);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public int getStudentListTotal(Student student){
		
		
		int total = 0;
		
		String sql = "select count(*) as total from s_student ";
		
		if(!StringUtil.isEmpty(student.getName())) {
			sql += "and name like'%"+student.getName()+"%'";
		}
		if(student.getClazzId() != 0) {
			sql += " and clazz_id = "+student.getClazzId();
		}
		if(student.getId() != 0) {
			sql += " and id ="+student.getId();
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



	public boolean editPassword(Student student, String newpassword) {
		String sql = "update s_student set password = '"+newpassword+"' where id = " + student.getId();
		return update(sql);
	}

	
	
	
	
}
	
	


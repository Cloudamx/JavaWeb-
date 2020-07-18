package com.Cloudandmoon.Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Cloudandmoon.dao.StudentDao;
import com.Cloudandmoon.model.Page;
import com.Cloudandmoon.model.Student;
import com.Cloudandmoon.util.SnGenerateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StudentServlet extends HttpServlet{

	
	private static final long serialVersionUID = 5455996190105455083L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//先获取一个标识
		String method = request.getParameter("method");
		response.setCharacterEncoding("UTF-8");
		if("toStudentListView".equals(method)) {
			studentList(request,response);
		}else if("AddStudent".equals(method)){
			addstudent(request,response);
		}else if("StudentList".equals(method)) {
			getStudentList(request,response);
		}else if("EditStudent".equals(method)) {
			editStudent(request,response);
		}else if("DeleteStudent".equals(method)) {
			deleteStudent(request,response);
		}
		
		
	}
	//添加和修改需要传入所有的参数
	//但是删除则只需要传入id就可以了
	
	
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) {
		String[] ids = request.getParameterValues("ids[]");
		String idstr = "";
		for(String id:ids) {
			idstr += id + ",";
		}
		
		idstr = idstr.substring(0, idstr.length() -1 );
		StudentDao studentDao = new StudentDao();
		
		if(studentDao.deleteStudent(idstr)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				studentDao.ConClose();
			}
		}
	}

	private void editStudent(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		String sex = request.getParameter("sex");
		String mobile = request.getParameter("mobile");
		String qq = request.getParameter("qq");
		int clazzId = Integer.parseInt(request.getParameter("clazzid"));
		Student student = new Student();
		student.setClazzId(clazzId);
		student.setMobile(mobile);
		student.setSex(sex);
		student.setId(id);
		student.setName(name);
		student.setQq(qq);
		StudentDao studentdao = new StudentDao();
		
		if(studentdao.editStudent(student)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				studentdao.ConClose();
			}
		}
		
		
		
	}

	private void getStudentList(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("StudentName");
		Integer currentPage = request.getParameter("page") == null ? 1 :Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999  : Integer.parseInt(request.getParameter("rows"));
		Integer clazz = request.getParameter("clazzid") == null ? 0  : Integer.parseInt(request.getParameter("clazzid"));
		
		int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
		
		
		Student student = new Student();
		student.setName(name);
		student.setClazzId(clazz);
		
		if(userType == 2) {
			Student currentUser = (Student) request.getSession().getAttribute("user");
			student.setId(currentUser.getId());
		}
		
		
		StudentDao studentDao = new StudentDao();
		
		List<Student> clazzList = studentDao.getStudentList(student, new Page(currentPage, pageSize));
		int total = studentDao.getStudentListTotal(student);
		studentDao.ConClose();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", clazzList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(clazzList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
		

	private void addstudent(HttpServletRequest request, HttpServletResponse response) {
		//先接受数据
		//再响应
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String mobile = request.getParameter("mobile");
		String qq = request.getParameter("qq");
		int clazzId = Integer.parseInt(request.getParameter("clazzid"));
		
		Student student = new Student();
		student.setClazzId(clazzId);
		student.setMobile(mobile);
		student.setSex(sex);
		student.setPassword(password);
		student.setName(name);
		student.setQq(qq);
		student.setSn(SnGenerateUtil.generatesn(clazzId));
		//Servlet显示request接受  创建实体类，赋值给实体类（集合？）   创建对应dao  调用dao方法   传入数据库
		
		StudentDao studentdao = new StudentDao();
		//如果获取了student的话
		if(studentdao.addStudent(student)){
			try {
				response.getWriter().write("success");
				//我的response其实是为了给jsp提醒你妹的
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				studentdao.ConClose();
			}
			
		}
		
		
		
		
		
	}

	private void studentList(HttpServletRequest request, 
			HttpServletResponse response) throws IOException {
		
		
		try {
			request.getRequestDispatcher("view/studentList.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}

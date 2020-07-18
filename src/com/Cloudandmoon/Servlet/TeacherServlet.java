package com.Cloudandmoon.Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Cloudandmoon.dao.TeacherDao;
import com.Cloudandmoon.dao.TeacherDao;
import com.Cloudandmoon.model.Page;
import com.Cloudandmoon.model.Teacher;
import com.Cloudandmoon.model.Teacher;
import com.Cloudandmoon.util.SnGenerateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TeacherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4316538961797125069L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//先获取一个标识
		String method = request.getParameter("method");
		response.setCharacterEncoding("UTF-8");
		if("toTeacherListView".equals(method)) {
			TeacherList(request,response);
		}else if("AddTeacher".equals(method)){
			addTeacher(request,response);
		}else if("TeacherList".equals(method)) {
			getTeacherList(request,response);
		}else if("EditTeacher".equals(method)) {
			editTeacher(request,response);
		}else if("DeleteTeacher".equals(method)) {
			deleteTeacher(request,response);
		}
	
	
}

	private void deleteTeacher(HttpServletRequest request, HttpServletResponse response) {
		
		String[] ids = request.getParameterValues("ids[]");
		String idstr = "";
		for(String id:ids) {
			idstr += id + ",";
		}
		
		idstr = idstr.substring(0, idstr.length() -1 );
		TeacherDao teacherDao = new TeacherDao();
		
		if(teacherDao.deleteTeacher(idstr)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				teacherDao.ConClose();
			}
		}
		
	}

	private void editTeacher(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		String sex = request.getParameter("sex");
		String mobile = request.getParameter("mobile");
		String qq = request.getParameter("qq");
		int clazzId = Integer.parseInt(request.getParameter("clazzid"));
		Teacher teacher = new Teacher();
		teacher.setClazzId(clazzId);
		teacher.setMobile(mobile);
		teacher.setSex(sex);
		teacher.setId(id);
		teacher.setName(name);
		teacher.setQq(qq);
		TeacherDao teacherDao = new TeacherDao();
		
		if(teacherDao.editTeacher(teacher)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				teacherDao.ConClose();
			}
		}
	}

	private void getTeacherList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String name = request.getParameter("teacherName");
		Integer currentPage = request.getParameter("page") == null ? 1 :Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999  : Integer.parseInt(request.getParameter("rows"));
		Integer clazz = request.getParameter("clazzid") == null ? 0  : Integer.parseInt(request.getParameter("clazzid"));
		
		int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
		
		Teacher teacher = new Teacher();
		teacher.setName(name);
		teacher.setClazzId(clazz);
		
		if(userType == 3) {
			Teacher currentUser = (Teacher) request.getSession().getAttribute("user");
			teacher.setId(currentUser.getId());
		}
		
		TeacherDao teacherDao = new TeacherDao();
		
		List<Teacher> teacherList = teacherDao.getTeacherList(teacher, new Page(currentPage, pageSize));
		int total = teacherDao.getTeacherListTotal(teacher);
		teacherDao.ConClose();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", teacherList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(teacherList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void addTeacher(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String mobile = request.getParameter("mobile");
		String qq = request.getParameter("qq");
		int clazzId = Integer.parseInt(request.getParameter("clazzid"));
		
		Teacher teacher = new Teacher();
		teacher.setClazzId(clazzId);
		teacher.setMobile(mobile);
		teacher.setSex(sex);
		teacher.setPassword(password);
		teacher.setName(name);
		teacher.setQq(qq);
		teacher.setSn(SnGenerateUtil.generateTeachersn(clazzId));
		//Servlet显示request接受  创建实体类，赋值给实体类（集合？）   创建对应dao  调用dao方法   传入数据库
		
		TeacherDao teacherdao = new TeacherDao();
		//如果获取了student的话
		if(teacherdao.addTeacher(teacher)){
			try {
				response.getWriter().write("success");
				//我的response其实是为了给jsp提醒你妹的
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				teacherdao.ConClose();
			}
		}
	}

	private void TeacherList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.getRequestDispatcher("view/teacherList.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			
			e.printStackTrace();
		}
	}
}
package com.Cloudandmoon.Servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Cloudandmoon.dao.AdminDao;
import com.Cloudandmoon.dao.StudentDao;
import com.Cloudandmoon.dao.TeacherDao;
import com.Cloudandmoon.model.Admin;
import com.Cloudandmoon.model.Student;
import com.Cloudandmoon.model.Teacher;
import com.Cloudandmoon.util.StringUtil;

/*
 * 第二个Servlet
 * 
 */

public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = -3116921268241836964L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//method?
		String method = request.getParameter("method");
		
		if("logout".equals(method)) {
			logout(request, response);
			//不许要往下执行  提高性能
			return;
		}
		
		
		
		
		//用户提交的
		String vcode = request.getParameter("vcode");
		
		//再从session中获取设置的验证码
		
		String loginCapcha = request.getSession().getAttribute("loginCpacha").toString();
		//7.14
		//Jsp中的name属性就是request中的Parameter
		String name = request.getParameter("account");
		
		String password = request.getParameter("password");
		
		//使用数字代表复杂的管理权限
		int type = Integer.parseInt(request.getParameter("type"));
		
		
		
		
		//先比较是否为空，再比较相不相等
		//System.out.println(vcode);
		//System.out.println("属性里面的"+loginCapcha);
		if(StringUtil.isEmpty(vcode)) {
			response.getWriter().write("vcodeError");	
			return;
		}
		//不等也是错误,这里开发的时候统一变成大写！或者小写
		if(!vcode.toUpperCase().equals(loginCapcha.toUpperCase())) {
			response.getWriter().write("vcodeError");	
			return;
		}
		//此时已经确保了验证码通过，于是需要确定用户名和密码
		//Servlet中实例化Dao并调用Dao的方法
		
		//先是实现登录  再通过switch来实现区分权限
		
		//设置一个标志量
		String loginSatuts = "failed";
		
				switch (type) {
					case 1:{
					
						AdminDao adminDao = new AdminDao();
						
						Admin admin = adminDao.login(name, password);
						
						adminDao.ConClose();
						//如果找不到就是不存在
						if(admin == null ) {
							response.getWriter().write("loginError");	
							return;
						}
						//这里用到session了，那么明显会有一个地方读取session
						HttpSession session = request.getSession();
						session.setAttribute("user", admin);
						session.setAttribute("userType", type);
						loginSatuts = "loginSuccess";
						//syo
						System.out.println("没有备份敲俚码");
						break;
					}
					case 2:{
						
						StudentDao studentDao = new StudentDao();
						
						Student student = studentDao.login(name, password);
						
						studentDao.ConClose();
						//如果找不到就是不存在
						if(student == null ) {
							response.getWriter().write("loginError");	
							return;
						}
						//这里用到session了，那么明显会有一个地方读取session
						HttpSession session = request.getSession();
						session.setAttribute("user", student);
						session.setAttribute("userType", type);
						loginSatuts = "loginSuccess";
						//syo
						System.out.println("没有备份敲俚码");
						break;
					}
					case 3:{
						
						TeacherDao teacherDao = new TeacherDao();
						
						Teacher teacher = teacherDao.login(name, password);
						
						teacherDao.ConClose();
						//如果找不到就是不存在
						if(teacher == null ) {
							response.getWriter().write("loginError");	
							return;
						}
						//这里用到session了，那么明显会有一个地方读取session
						HttpSession session = request.getSession();
						session.setAttribute("user", teacher);
						session.setAttribute("userType", type);
						loginSatuts = "loginSuccess";
						//syo
						System.out.println("没有备份敲俚码");
						break;
					}
				
				default:
					break;
				}
				//为什么？
				response.getWriter().write(loginSatuts);
				
		}
		
		private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
			
			request.removeAttribute("user");
			request.removeAttribute("userType");
			response.sendRedirect("index.jsp");
			
		}
	
	
	
	
	
	
	
	
	
	
}	


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
 * �ڶ���Servlet
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
			//����Ҫ����ִ��  �������
			return;
		}
		
		
		
		
		//�û��ύ��
		String vcode = request.getParameter("vcode");
		
		//�ٴ�session�л�ȡ���õ���֤��
		
		String loginCapcha = request.getSession().getAttribute("loginCpacha").toString();
		//7.14
		//Jsp�е�name���Ծ���request�е�Parameter
		String name = request.getParameter("account");
		
		String password = request.getParameter("password");
		
		//ʹ�����ִ����ӵĹ���Ȩ��
		int type = Integer.parseInt(request.getParameter("type"));
		
		
		
		
		//�ȱȽ��Ƿ�Ϊ�գ��ٱȽ��಻���
		//System.out.println(vcode);
		//System.out.println("���������"+loginCapcha);
		if(StringUtil.isEmpty(vcode)) {
			response.getWriter().write("vcodeError");	
			return;
		}
		//����Ҳ�Ǵ���,���￪����ʱ��ͳһ��ɴ�д������Сд
		if(!vcode.toUpperCase().equals(loginCapcha.toUpperCase())) {
			response.getWriter().write("vcodeError");	
			return;
		}
		//��ʱ�Ѿ�ȷ������֤��ͨ����������Ҫȷ���û���������
		//Servlet��ʵ����Dao������Dao�ķ���
		
		//����ʵ�ֵ�¼  ��ͨ��switch��ʵ������Ȩ��
		
		//����һ����־��
		String loginSatuts = "failed";
		
				switch (type) {
					case 1:{
					
						AdminDao adminDao = new AdminDao();
						
						Admin admin = adminDao.login(name, password);
						
						adminDao.ConClose();
						//����Ҳ������ǲ�����
						if(admin == null ) {
							response.getWriter().write("loginError");	
							return;
						}
						//�����õ�session�ˣ���ô���Ի���һ���ط���ȡsession
						HttpSession session = request.getSession();
						session.setAttribute("user", admin);
						session.setAttribute("userType", type);
						loginSatuts = "loginSuccess";
						//syo
						System.out.println("û�б�����ٵ��");
						break;
					}
					case 2:{
						
						StudentDao studentDao = new StudentDao();
						
						Student student = studentDao.login(name, password);
						
						studentDao.ConClose();
						//����Ҳ������ǲ�����
						if(student == null ) {
							response.getWriter().write("loginError");	
							return;
						}
						//�����õ�session�ˣ���ô���Ի���һ���ط���ȡsession
						HttpSession session = request.getSession();
						session.setAttribute("user", student);
						session.setAttribute("userType", type);
						loginSatuts = "loginSuccess";
						//syo
						System.out.println("û�б�����ٵ��");
						break;
					}
					case 3:{
						
						TeacherDao teacherDao = new TeacherDao();
						
						Teacher teacher = teacherDao.login(name, password);
						
						teacherDao.ConClose();
						//����Ҳ������ǲ�����
						if(teacher == null ) {
							response.getWriter().write("loginError");	
							return;
						}
						//�����õ�session�ˣ���ô���Ի���һ���ط���ȡsession
						HttpSession session = request.getSession();
						session.setAttribute("user", teacher);
						session.setAttribute("userType", type);
						loginSatuts = "loginSuccess";
						//syo
						System.out.println("û�б�����ٵ��");
						break;
					}
				
				default:
					break;
				}
				//Ϊʲô��
				response.getWriter().write(loginSatuts);
				
		}
		
		private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
			
			request.removeAttribute("user");
			request.removeAttribute("userType");
			response.sendRedirect("index.jsp");
			
		}
	
	
	
	
	
	
	
	
	
	
}	


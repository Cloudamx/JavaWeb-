package com.Cloudandmoon.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Cloudandmoon.dao.AdminDao;
import com.Cloudandmoon.dao.StudentDao;
import com.Cloudandmoon.dao.TeacherDao;
import com.Cloudandmoon.model.Admin;
import com.Cloudandmoon.model.Student;
import com.Cloudandmoon.model.Teacher;

public class SystemServlet extends HttpServlet{

	private static final long serialVersionUID = 3764542932389968528L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//response.getWriter().write("ץ³Ѹ������������ʲô��ϵ��");
		doPost(request,response);
	
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//ÿһpost���涼��һ��ĬĬ�����session
		String method = request.getParameter("method");
		
		if("toPersonalView".equals(method)) {
			personalView(request,response);
			//ΪʲôҪreturn
			return;
		}else if ("EditPassword".equals(method)) {
			editPassword(request,response);
			//����֪��ΪʲôҪreturn�˰ɣ�
			return;
		}
		
		
		//ת��ҳ��
		try {
			request.getRequestDispatcher("view/system.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
	}


	private void editPassword(HttpServletRequest request, HttpServletResponse response) {
		//��ȡ���룬У��
		String password = request.getParameter("password");
		String newpassword = request.getParameter("newpassword");
		response.setCharacterEncoding("UTF-8");
		
		//�ж��û�����
		int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
		
		if(userType == 1) {
			Admin admin = (Admin)request.getSession().getAttribute("user");
			if(!admin.getPassword().equals(password)) {
				try {
					response.getWriter().write("ԭ�������");
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			AdminDao adminDao = new AdminDao();
			if(adminDao.editPassword(admin, newpassword)) {
				 try {
					response.getWriter().write("success");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					adminDao.ConClose();
				}
			}else {
				try {
					response.getWriter().write("���ݿ��޸Ĵ���");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					adminDao.ConClose();
				}
			}
			
		}
		//ѧ���ͽ�ʦ
		if(userType == 2) {
			Student student = (Student)request.getSession().getAttribute("user");
			if(!student.getPassword().equals(password)) {
				try {
					response.getWriter().write("ԭ�������");
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			StudentDao studentDao = new StudentDao();
			if(studentDao.editPassword(student, newpassword)) {
				 try {
					response.getWriter().write("success");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					studentDao.ConClose();
				}
			}else {
				try {
					response.getWriter().write("���ݿ��޸Ĵ���");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					studentDao.ConClose();
				}
			}
			
		}
		//��ʦ
		
		if(userType == 3) {
			Teacher teacher = (Teacher)request.getSession().getAttribute("user");
			if(!teacher.getPassword().equals(password)) {
				try {
					response.getWriter().write("ԭ�������");
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			TeacherDao teacherDao = new TeacherDao();
			if(teacherDao.editPassword(teacher, newpassword)) {
				 try {
					response.getWriter().write("success");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					teacherDao.ConClose();
				}
			}else {
				try {
					response.getWriter().write("���ݿ��޸Ĵ���");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					teacherDao.ConClose();
				}
			}
			
		}
		
		
		
		
		
		
	}


	private void personalView(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.getRequestDispatcher("view/personalView.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}

package com.Cloudandmoon.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain)
			throws IOException, ServletException {
		//http�������session
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)rep;
		
		//��ȡ����
		Object admin = request.getSession().getAttribute("user");
		//�ж��Ƿ�Ϊ��
		
		if(admin == null) {
			response.sendRedirect("index.jsp");
			//ΪʲôҪreturn;
			return;
		}else {
			chain.doFilter(request, response);
		}
		
		
		
		
		

	}

}

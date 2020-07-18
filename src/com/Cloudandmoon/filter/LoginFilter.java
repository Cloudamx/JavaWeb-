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
		//http里面才有session
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)rep;
		
		//获取对象
		Object admin = request.getSession().getAttribute("user");
		//判断是否为空
		
		if(admin == null) {
			response.sendRedirect("index.jsp");
			//为什么要return;
			return;
		}else {
			chain.doFilter(request, response);
		}
		
		
		
		
		

	}

}

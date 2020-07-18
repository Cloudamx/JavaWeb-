package com.Cloudandmoon.Servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Cloudandmoon.util.CpachaUtil;

/*
 * 验证码Servlet
 */
public class CpachaServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1784717530325655179L;
	
	
	//不大写就爆炸！！！
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//为什么要做一个method出来
		String method = request.getParameter("method");
		
		//
		if ("loginCapcha".equals(method)) {
			//如果相同，那么执行generateLoginCapcha方法,生成验证码何图片
			generateLoginCapcha(request,response);
			
			//结束
			return;	
		}else {
			//不是method方法？就输出错误信息？流弊
			response.getWriter().write("不是method方法");
			
		}
		
	}
		//1.创建一个方法获得取LoginCapcha的值
	private void generateLoginCapcha(HttpServletRequest request, 
			HttpServletResponse response) throws IOException {
		//2.获取对象
		CpachaUtil cpachautil = new CpachaUtil();
		//3.调用方法,获取验证码，放入String中
		String generatorVCode = cpachautil.generatorVCode();
		//4.把验证码放入session中
		
		//5.1获取session都象，调用setA..方法
		request.getSession().setAttribute("loginCpacha", generatorVCode);
		
		//6生成图片
		BufferedImage generatorRotateVCodeImage =cpachautil.generatorRotateVCodeImage(generatorVCode, true);
		
		//7使用ImageIO将生成图片输出值response
		ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
		
	}
	
	

}

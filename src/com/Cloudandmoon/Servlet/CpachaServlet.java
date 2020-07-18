package com.Cloudandmoon.Servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Cloudandmoon.util.CpachaUtil;

/*
 * ��֤��Servlet
 */
public class CpachaServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1784717530325655179L;
	
	
	//����д�ͱ�ը������
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//ΪʲôҪ��һ��method����
		String method = request.getParameter("method");
		
		//
		if ("loginCapcha".equals(method)) {
			//�����ͬ����ôִ��generateLoginCapcha����,������֤���ͼƬ
			generateLoginCapcha(request,response);
			
			//����
			return;	
		}else {
			//����method�����������������Ϣ������
			response.getWriter().write("����method����");
			
		}
		
	}
		//1.����һ���������ȡLoginCapcha��ֵ
	private void generateLoginCapcha(HttpServletRequest request, 
			HttpServletResponse response) throws IOException {
		//2.��ȡ����
		CpachaUtil cpachautil = new CpachaUtil();
		//3.���÷���,��ȡ��֤�룬����String��
		String generatorVCode = cpachautil.generatorVCode();
		//4.����֤�����session��
		
		//5.1��ȡsession���󣬵���setA..����
		request.getSession().setAttribute("loginCpacha", generatorVCode);
		
		//6����ͼƬ
		BufferedImage generatorRotateVCodeImage =cpachautil.generatorRotateVCodeImage(generatorVCode, true);
		
		//7ʹ��ImageIO������ͼƬ���ֵresponse
		ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
		
	}
	
	

}

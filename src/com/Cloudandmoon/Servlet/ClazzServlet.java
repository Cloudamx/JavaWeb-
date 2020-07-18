package com.Cloudandmoon.Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Cloudandmoon.dao.ClazzDao;
import com.Cloudandmoon.model.Clazz;
import com.Cloudandmoon.model.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class ClazzServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3763628973191698404L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//method?
		//method是自定的   相当于key    
		String method = request.getParameter("method");
		if ("toClazzListView".equals(method)) {
			clazzList(request,response);
		}else if ("getClazzList".equals(method)) {
			getClazzList(request, response);
		}else if ("AddClazz".equals(method)) {
			addClazz(request,response);
		}else if ("DeleteClazz".equals(method)) {
			deleteClazz(request,response);
		}else if ("EditClazz".equals(method)) {
			editClazz(request,response);
		}
		
		
		
		
		
		
	}

	private void editClazz(HttpServletRequest request, HttpServletResponse response) {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String info = request.getParameter("info");
		Clazz clazz = new Clazz();
		clazz.setName(name);
		clazz.setInfo(info);
		clazz.setId(id);
		
		ClazzDao clazzdao = new ClazzDao();
		
		if(clazzdao.editClazz(clazz)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				clazzdao.ConClose();
			}
		}
		
	}

	private void deleteClazz(HttpServletRequest request, HttpServletResponse response) {
		
		Integer id = Integer.parseInt(request.getParameter("clazzid"));
		
		ClazzDao clazzdao = new ClazzDao();
		
		if(clazzdao.deleteClazz(id)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				clazzdao.ConClose();
			}
		}
		
		
		
		
		
	}

	private void addClazz(HttpServletRequest request, HttpServletResponse response) {
		
		String name = request.getParameter("name");
		String info = request.getParameter("info");
		Clazz clazz = new Clazz();
		clazz.setName(name);
		clazz.setInfo(info);
		
		ClazzDao clazzdao = new ClazzDao();
		
		if(clazzdao.addClazz(clazz)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				clazzdao.ConClose();
			}
		}
		
	}

	private void clazzList(HttpServletRequest request, HttpServletResponse response) {
		//转发
		try {
			request.getRequestDispatcher("view/clazzList.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getClazzList(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("clazzName");
		Integer currentPage = request.getParameter("page") == null ? 1 :Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999  : Integer.parseInt(request.getParameter("rows"));
		
		Clazz clazz = new Clazz();
		
		clazz.setName(name);
		
		ClazzDao clazzDao = new ClazzDao();
		
		List<Clazz> clazzList = clazzDao.getClazzlist(clazz, new Page(currentPage, pageSize));
		
		int total = clazzDao.getClazzlistTotal(clazz);
		clazzDao.ConClose();
		
		//JsonConfig jsonConfig = new JsonConfig();
		//String clazzzString = JSONArray.fromObject(clazzList, jsonConfig).toString();
		
		
		//不会，但是显然是用于向前端输出数据的
		
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<>();
		
		ret.put("total", total);
		ret.put("rows", clazzList);
	
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)) {
				response.getWriter().write(JSONArray.fromObject(clazzList 	).toString());
			}else {
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}

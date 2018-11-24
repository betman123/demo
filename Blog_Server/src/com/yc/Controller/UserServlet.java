package com.yc.Controller;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yc.bean.User;
import com.yc.dao.BeanUtils;
import com.yc.dao.DBHelper2;

import Biz.BizException;
import Biz.UserBiz;

@WebServlet("/user.s")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private UserBiz ubiz=new UserBiz(); 
	
	public void init(ServletConfig config) throws ServletException {
	}
	public void destroy() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op=request.getParameter("op");
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		if("login".equals(op)) {
			login(request,response);
		}else if("query".equals(op)) {
			query(request,response);
		}else if("add".equals(op)) {
			add(request,response);
		}else if("find".equals(op)) {
			find(request,response);
		}else if("save".equals(op)) {
			save(request,response);
		}
	}

	private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user=BeanUtils.asBean(request, User.class);
		String msg;
		try {
			ubiz.save(user);
			msg="用户信息保存成功！";
		} catch (Exception e) {
			e.printStackTrace();
			msg=e.getMessage();  
		}
		response.getWriter().append(msg);
	}
	
	private void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		String id=request.getParameter("id");
		User user=ubiz.findByid(id);
		
		//将user返回给页面
		String userString=JSON.toJSONString(user);
		response.getWriter().append(userString);
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//将参数加载到user对象中
		User user=BeanUtils.asBean(request, User.class);
		try {
			String repwd=request.getParameter("repwd");
			ubiz.add(user,repwd);
		}catch(BizException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
		}finally {
			//跳转到用户查询界面
			query(request, response);
		}
	}

	private void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user=BeanUtils.asBean(request, User.class);
		request.setAttribute("userList", ubiz.select(user));
		request.getRequestDispatcher("manage-user.jsp").forward(request, response);
	}


	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("username"); 
		String userpwd=request.getParameter("userpwd");
		
		User ret=null;
		
		try {
			ret=ubiz.login(username, userpwd);
		} catch (BizException e) {
			e.printStackTrace();
			
			request.setAttribute("msg", e.getMessage());  //获取错误信息
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
		if(ret ==null) {
			request.setAttribute("msg", "账号或者密码错误");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else {
			request.getSession().setAttribute("loginedUser", ret);
			response.sendRedirect("index.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

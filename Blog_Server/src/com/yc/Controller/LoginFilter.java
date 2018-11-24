package com.yc.Controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns= {"*.jsp","*.s"})
public class LoginFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest HttpRequest=(HttpServletRequest) request;
		/**
		 * ʵ���ų� �������ص�Ȩ��
		 */
		//��ȡ������
		String path=HttpRequest.getServletPath();  //��ȡ���ʵ���Դ·��
		//�ж���Դ�Ƿ���Ҫ������
		if(path.endsWith("user.s") || path.endsWith("login.jsp")) {
			chain.doFilter(request, response);
			return;
		}
		
		if(HttpRequest.getSession().getAttribute("loginedUser") !=null) {
			// ����ҵ�����ִ�� ����������doFilter
			chain.doFilter(request, response);
		}else {
			request.setAttribute("msg", "���ȵ�¼ϵͳ��");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}

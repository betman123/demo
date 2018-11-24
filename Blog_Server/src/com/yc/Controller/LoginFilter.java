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
		 * 实现排除 访问拦截的权限
		 */
		//获取访问名
		String path=HttpRequest.getServletPath();  //获取访问的资源路径
		//判断资源是否需要被拦截
		if(path.endsWith("user.s") || path.endsWith("login.jsp")) {
			chain.doFilter(request, response);
			return;
		}
		
		if(HttpRequest.getSession().getAttribute("loginedUser") !=null) {
			// 正常业务必须执行 过滤器链的doFilter
			chain.doFilter(request, response);
		}else {
			request.setAttribute("msg", "请先登录系统！");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}

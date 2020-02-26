package com.jang.bbs.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Object userId = request.getSession().getAttribute("userId");

		if (request.getRequestURI().equals("/bbs/member/login.do") ||  request.getRequestURI().equals("/bbs/member/findId.do")
				|| request.getRequestURI().equals("/bbs/member/findPass.do")
				|| request.getRequestURI().equals("/bbs/member/join.do")
				|| request.getRequestURI().equals("/bbs/member/ajaxlogin.do"))
				//프로젝트와 파일경로까지 싸그리 다 가져오는 것 
		{
			if (userId != null) {
				response.sendRedirect(request.getContextPath() + "/board/list.do");
				return true; 
			} else {
				return true; 
			}
		}
		if (userId == null) {
			response.sendRedirect(request.getContextPath() + "/member/ajaxlogin.do");
			return false;  
		} else {
			return true;  
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}
}

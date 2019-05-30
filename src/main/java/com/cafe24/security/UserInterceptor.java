package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UserInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String uri = request.getRequestURI();
		String[] uriArr = uri.split("/");
		
		// 1. handler 종류 확인
		if (handler instanceof HandlerMethod == false) {
			return true; // image,js파일이 여기에 들어온다. assets에 없는 애들
		}
		
		if(uriArr.length<=3) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		
		return true;
	}

}

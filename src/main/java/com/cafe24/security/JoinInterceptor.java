package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.jblog.repository.UserDao;

public class JoinInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private UserDao userDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 1. handler 종류 확인
		if (handler instanceof HandlerMethod == false) {
			return true; // image,js파일이 여기에 들어온다. assets에 없는 애들
		}

		// 2.casting
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		
		
		return true;
	}

}

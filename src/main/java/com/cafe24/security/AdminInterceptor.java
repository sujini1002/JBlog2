package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.jblog.repository.BlogDao;
import com.cafe24.jblog.repository.CategoryDao;
import com.cafe24.jblog.repository.PostDao;
import com.cafe24.jblog.repository.UserDao;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.UserVo;

public class AdminInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BlogDao blogDao;
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		String[] uriArr = uri.split("/");
		String beforeUrl = (String)request.getHeader("REFERER");
		
		
		//1. handler 종류 확인
		if(handler instanceof HandlerMethod == false) {
			return true; //image,js파일이 여기에 들어온다. assets에 없는 애들
		}
		
		//2.casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		
		// 3.uriarr 2번째 값이 존재하지 않을 때 false
		BlogVo blogVo = blogDao.getBlogById(uriArr[2]);
		if(blogVo==null) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		
		
		
		//4..uriArr 길이가 3이하거나 3번째가 admin이 아닐때 : 블로그 방문 (true)
		if(uriArr.length<=3 || "admin".equals(uriArr[3])==false) {
			return true;
		}
		
		//uriArr 3번째가 admin일 때 
		//5. 세션 값이 없으면 리턴
		HttpSession session = request.getSession();
		if(session==null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		//6. 로그인 상태 체크
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		//7. 로그인한 사용자와 블로그 주인이 다를 때 : admin 페이지 불가(false)
		if(uriArr[2].equals(authUser.getId())==false) {
			response.sendRedirect(request.getContextPath()+"/"+uriArr[2]);
			return false;
		}
		//8. 블로그 주인과 로그인한 사용자가 같을 때 해당 블로그가 존재하는지 체크
		UserVo adminVo = userDao.getById(authUser.getId());
		if(adminVo==null) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		
		
		//uriArr 3번째가 admin일 때 현재 로그인한 사용자와 2번째의 같으면
		return true;
	}
	
}

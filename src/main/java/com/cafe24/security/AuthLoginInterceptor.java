package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.jblog.service.UserService;
import com.cafe24.jblog.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private UserService userService;//interceptor는 spring container안에 존재하므로 autowired를 할 수 있다.
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
	
		UserVo userVo = new UserVo();
		userVo.setId(id);
		userVo.setPassword(password);
		
		UserVo authUser = userService.userExist(userVo);
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		//세션 처리
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		//자신의 블로그로 가기
		response.sendRedirect(request.getContextPath()+"/"+authUser.getId());
		
		return false;//컨트롤러에 로그인 체크 하는 것이 사라지므로 handler mapping에서 정보들이 사라진다. 그러므로 controller에 접근하면 안됨
	}

}

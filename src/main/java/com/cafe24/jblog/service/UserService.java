package com.cafe24.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.repository.BlogDao;
import com.cafe24.jblog.repository.CategoryDao;
import com.cafe24.jblog.repository.UserDao;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private CategoryDao categoryDao;

	public Boolean join(UserVo userVo) {
		boolean result = false;
		//사용자 추가
		result=userDao.insert(userVo);
		
		//블로그 개설
		BlogVo blogVo = new BlogVo(userVo.getId(),userVo.getId()+"님의 블로그","spring-logo.jpg");
		result=blogDao.insert(blogVo);
		
		//카테고리 추가
		CategoryVo categoryVo = new CategoryVo(userVo.getId(), "기본","기본 카테고리 입니다.");
		result=categoryDao.insert(categoryVo);
				
		return result;
	}

	public Boolean existEmail(String id) {
		UserVo userVo = userDao.getById(id);
		return userVo != null;
	}

	public UserVo userExist(UserVo userVo) {
		return userDao.getByIdPassword(userVo);
	}

}

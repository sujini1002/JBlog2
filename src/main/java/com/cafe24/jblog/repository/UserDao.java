package com.cafe24.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;

	public Boolean insert(UserVo userVo) {
		int cnt = sqlSession.insert("users.insert", userVo);
		return cnt == 1;
	}

	public UserVo getById(String id) {
		return sqlSession.selectOne("users.getById", id);
	}

	public UserVo getByIdPassword(UserVo userVo) {
		return sqlSession.selectOne("users.getByIdPassword", userVo);
	}

}

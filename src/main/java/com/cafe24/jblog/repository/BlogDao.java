package com.cafe24.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.BlogVo;

@Repository
public class BlogDao {
	
	@Autowired
	private SqlSession sqlSession;

	public Boolean insert(BlogVo blogVo) {
		int cnt = sqlSession.insert("blog.insert", blogVo);
		return cnt ==1;
	}

	public BlogVo getBlogById(String id) {
		return sqlSession.selectOne("blog.getBlogById", id);
	}

	public Boolean updateSkin(BlogVo blogVo) {
		int cnt = sqlSession.update("blog.updateSkin", blogVo);
		return 1==cnt;
	}

	


}

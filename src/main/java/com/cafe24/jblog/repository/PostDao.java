package com.cafe24.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.PostVo;

@Repository
public class PostDao {
	
	@Autowired
	private SqlSession sqlSession;

	public Boolean insert(PostVo postVo) {
		int cnt = sqlSession.insert("post.insert",postVo);
		return 1== cnt ;
	}

	public Long getId(long category_no) {
		return sqlSession.selectOne("post.getId", category_no);
	}

	public Long basicPostNo(Long category_no) {
		return sqlSession.selectOne("post.basicNo", category_no);
	}

	public PostVo getPost(PostVo postVo) {
		return sqlSession.selectOne("post.getPost", postVo);
	}

	public List<PostVo> getList(Map<String, Object> map) {
		return sqlSession.selectList("post.getList", map);
	}
	public Long getPostByCateNo(Long no) {
		return sqlSession.selectOne("post.getPostByCateNo", no);
	}

	public Boolean delete(Map<String, Object> map) {
		int cnt =sqlSession.delete("post.delete", map);
		return 1==cnt;
	}


}

package com.cafe24.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.CategoryVo;

@Repository
public class CategoryDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<CategoryVo> getById(String id) {
		return sqlSession.selectList("category.getById", id);
	}

	public boolean insert(CategoryVo categoryVo) {
		int cnt = sqlSession.insert("category.insert", categoryVo);
		return 1==cnt;
	}

	public Boolean delete(CategoryVo categoryVo) {
		int cnt = sqlSession.delete("category.delete",categoryVo);
		return 1==cnt;
	}

	public Long basicCateNo(String id) {
		return sqlSession.selectOne("category.basicNo", id);
	}

}

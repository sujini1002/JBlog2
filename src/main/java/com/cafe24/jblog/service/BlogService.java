package com.cafe24.jblog.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.repository.BlogDao;
import com.cafe24.jblog.repository.CategoryDao;
import com.cafe24.jblog.repository.PostDao;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;

@Service
public class BlogService {

	@Autowired
	private BlogDao blogDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private PostDao postDao;

	// 블로그 정보가져오기
	public BlogVo getAdminBlog(String id) {
		return blogDao.getBlogById(id);
	}

	// 블로그 정보 업데이트
	public Boolean updateSkin(BlogVo blogVo) {
		//블로그 제목이 공백이면
		if("".equals(blogVo.getTitle())) {
			blogVo.setTitle(blogDao.getBlogTitleById(blogVo.getId()));
		} 
		return blogDao.updateSkin(blogVo);
	}

	// 카테고리 가져오기
	public List<CategoryVo> getCategory(String id) {
		return categoryDao.getById(id);
	}

	// 카테고리 추가
	public Boolean addCategory(String id, CategoryVo categoryVo) {
		categoryVo.setId(id);
		return categoryDao.insert(categoryVo);
	}

	// 카테고리 삭제
	public Boolean deleteCategory(String id, long no) {
		if(postDao.getPostByCateNo(no)>0) {
			return false;
		}
		if(categoryDao.getByNo(no)==categoryDao.basicCateNo(id)) {
			return false;
		}
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setId(id);
		categoryVo.setNo(no);
		return categoryDao.delete(categoryVo);
	}

	// 글 작성
	public Boolean writePost(PostVo postVo) {
		return postDao.insert(postVo);
	}

	// 지금 작성한 글번호 가져오기
	public Long getPostId(Long category_no) {
		return postDao.getId(category_no);
	}
	
	// 해당 글 가져오기
	public PostVo getPost(String id, Long category_no, Long post_no) {
		
		// 기본글을 찾기 위한 변수 셋팅
		category_no = category_no==0?categoryDao.basicCateNo(id):category_no;
		post_no = post_no == 0?postDao.getId(category_no):post_no;
		
		//글 가져오기
		PostVo postVo = new PostVo();
		postVo.setCategory_no(category_no);
		postVo.setNo(post_no);
		
		return postDao.getPost(postVo);
	}
	
	//해당 카테고리에 해당하는 글 가져오기
	public List<PostVo> getPostList(Long category_no,String id) {
		//카테고리 번호
		category_no = category_no==0?categoryDao.basicCateNo(id):category_no;
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("category_no", category_no);
		
		return postDao.getList(map);
	}
	// 로고 이미지 물리적 저장
	public String fileRestore(MultipartFile logoFile) {
		final String SAVE_PATH = "/jblog-uploads";

		String logoName = "";
		try {
			if (logoFile.isEmpty()) {
				return "spring-logo.jpg";
			}

			String originalFilename = logoFile.getOriginalFilename(); // 다른 사용자와 이름이 겺칠 수 있기 때문에 다른이름으로 저장한다.
			String extName = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);// 확장자
			logoName = generateSaveFileName(extName);

			byte[] fileData = logoFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + logoName);
			os.write(fileData);
			os.close();

		} catch (IOException e) {
			throw new RuntimeException("Fileupload error:" + e);
		}

		return logoName;
	}

	// 로고 이미지 사진 이름 구하기
	private String generateSaveFileName(String extName) {
		String filename = "";

		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);

		return filename;
	}
	// 글삭제
	public Boolean deletePost(long category_no, long no) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("category_no", category_no);
		map.put("no", no);
		return postDao.delete(map);
	} 
 
	

	

}

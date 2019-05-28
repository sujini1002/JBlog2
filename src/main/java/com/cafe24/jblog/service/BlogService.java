package com.cafe24.jblog.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.repository.BlogDao;
import com.cafe24.jblog.repository.CategoryDao;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;

@Service
public class BlogService {
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private CategoryDao categoryDao;	
	
	//블로그 정보가져오기
	public BlogVo getAdminBlog(String id) {
		return blogDao.getBlogById(id);
	}
	//블로그 정보 업데이트
	public Boolean updateSkin(BlogVo blogVo) {
		return blogDao.updateSkin(blogVo);
	}
	//카테고리 가져오기
	public List<CategoryVo> getCategory(String id) {
		return categoryDao.getById(id);
	}
	//카테고리 추가
	public Boolean addCategory(String id, CategoryVo categoryVo) {
		categoryVo.setId(id);
		return categoryDao.insert(categoryVo);
	}
	
	//로고 이미지 물리적 저장
	public String fileRestore(MultipartFile logoFile) {
		final String SAVE_PATH = "/jblog-uploads";
		
		String logoName = "";
		try {
		if(logoFile.isEmpty()) {
			return "spring-logo.jpg";
		}
		
		String originalFilename = logoFile.getOriginalFilename(); //다른 사용자와 이름이 겺칠 수 있기 때문에 다른이름으로 저장한다.
		String extName = originalFilename.substring(originalFilename.lastIndexOf('.')+1);//확장자
		logoName = generateSaveFileName(extName);
		
		byte[] fileData = logoFile.getBytes();
		OutputStream os = new FileOutputStream(SAVE_PATH+"/"+logoName);
		os.write(fileData);
		os.close();
		
		
		} catch (IOException e) {
			throw new RuntimeException("Fileupload error:" + e);
		}
		
		return logoName;
	}
	
	//로고 이미지 사진 이름 구하기
	private String generateSaveFileName(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("."+extName);
		
		return filename;
	}
	public Boolean deleteCategory(String id) {
		return categoryDao.delete(id);
	}

	
 
}

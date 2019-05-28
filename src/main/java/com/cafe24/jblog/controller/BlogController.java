package com.cafe24.jblog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	
	//블로그가기
	@RequestMapping("")
	public String goToBlog(@PathVariable(value="id")String id,ModelMap modelMap) {
		//블로그 정보
		BlogVo blogVo = blogService.getAdminBlog(id);
		modelMap.put("blogVo", blogVo);
		//카테고리 목록
		List<CategoryVo> categoryList = blogService.getCategory(id);
		modelMap.put("categoryList", categoryList);
		
		return "blog/blog-main";
	}
	
	//블로그 관리 메인 페이지 가기(인터셉터)
	@RequestMapping({"/admin/basic","/admin"})
	public String goToAdmin(@PathVariable(value="id")String id,Model model) {
		BlogVo blogVo = blogService.getAdminBlog(id);
		model.addAttribute("blogVo", blogVo);
		return "blog/blog-admin-basic";
	}
	//블로그 제목 및 로고 변경(인터셉터)
	@RequestMapping("/admin/update")
	public String adminSkinUpdate(@ModelAttribute BlogVo blogVo,@RequestParam("logoFile")MultipartFile logoFile) {
		//로고 저장 서비스 호출
		blogVo.setLogo(blogService.fileRestore(logoFile));
		//수정
		blogService.updateSkin(blogVo);
		return "redirect:/"+blogVo.getId()+"/admin/basic";
	}
	
	//블로그 관리 카테고리 페이지 보여주기(인터셉터)
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable(value="id")String id,ModelMap modelMap) {
		System.out.println("adminCategory에 들어모");
		//블로그 정보
		BlogVo blogVo = blogService.getAdminBlog(id);
		modelMap.put("blogVo", blogVo);
		//카테고리 정보
		List<CategoryVo> categoryList = blogService.getCategory(id);
		System.out.println(categoryList);
		modelMap.put("categoryList", categoryList);
		
		return "blog/blog-admin-category";
	}
	
	//카테고리 추가(인터셉터 및 Valid)
	@RequestMapping("/admin/category/add")
	public String adminAddCategory(@PathVariable(value="id")String id,@ModelAttribute CategoryVo categoryVo) {
		blogService.addCategory(id,categoryVo);
		return "redirect:/"+id+"/admin/category";
	}
	
	//카테고리 삭제(인터셉터)
	@RequestMapping("/admin/category/delete")
	public String adminDeleteCategory(@PathVariable(value="id")String id) {
		blogService.deleteCategory(id);
		return "redirect:/"+id+"/admin/category";
	} 
	
	 
}

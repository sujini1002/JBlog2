package com.cafe24.jblog.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!images|assets).*}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	
	//블로그가기
	@RequestMapping(value= {"","/{c_no}","/{c_no}/{p_no}"})
	public String goToBlog(@PathVariable(value="id")String id,
							@PathVariable(value="c_no")Optional<Long> c_no,
							@PathVariable(value="p_no")Optional<Long> p_no,
							ModelMap modelMap) {
		//블로그 정보
		BlogVo blogVo = blogService.getAdminBlog(id);
		modelMap.put("blogVo", blogVo);
		//카테고리 목록
		List<CategoryVo> categoryList = blogService.getCategory(id);
		modelMap.put("categoryList", categoryList);
		
		//PathVariable의 기본 값 셋팅
		Long category_no = c_no.isPresent()?c_no.get():0;
		Long post_no = p_no.isPresent()?p_no.get():0;
		
		//화면에 보여질 페이지 
		PostVo postVo = blogService.getPost(id,category_no,post_no);
		modelMap.put("postVo",postVo);
		
		//화면에 보여질 카테고리에 해당하는 포스트 리스트
		List<PostVo> postList = blogService.getPostList(category_no,id);
		modelMap.put("postList", postList);
		//포스트 리스트 이동을 위한 id
		modelMap.put("adminId", id);
		
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
		if(logoFile.isEmpty()==false) {
			blogVo.setLogo(blogService.fileRestore(logoFile));
		}
		//수정
		blogService.updateSkin(blogVo);
		return "redirect:/"+blogVo.getId()+"/admin/basic";
	}
	
	//블로그 관리 카테고리 페이지 보여주기(인터셉터)
	@GetMapping("/admin/category")
	public String adminCategory(@PathVariable(value="id")String id,ModelMap modelMap,@ModelAttribute CategoryVo categoryVo) {
		//블로그 정보
		BlogVo blogVo = blogService.getAdminBlog(id);
		modelMap.put("blogVo", blogVo);
		//카테고리 정보
		List<CategoryVo> categoryList = blogService.getCategory(id);
		modelMap.put("categoryList", categoryList);
		
		return "blog/blog-admin-category";
	}
	
	//카테고리 추가(인터셉터 및 Valid)
	@PostMapping("/admin/category")
	public String adminAddCategory(
			@PathVariable(value="id")String id,
			@ModelAttribute @Valid CategoryVo categoryVo,
			BindingResult result,
			Model model	) {
		// Valid 체크가 틀릴 시, join form으로 넘김
		if (result.hasErrors()) {
			model.addAttribute(result.getModel()); // Map으로 보내줌
			//카테고리 리스트 
			List<CategoryVo> categoryList = blogService.getCategory(id);
			model.addAttribute("categoryList", categoryList);
			
			return "blog/blog-admin-category";
		}
		blogService.addCategory(id, categoryVo);
		return "redirect:/" + id + "/admin/category";
	}
	
	//카테고리 삭제(인터셉터)
	@RequestMapping("/admin/category/delete/{no}")
	public String adminDeleteCategory(@PathVariable(value="id")String id,@PathVariable(value="no")long no) {
		blogService.deleteCategory(id,no);
		
		return "redirect:/"+id+"/admin/category";
	} 
	
	//글 작성 폼(인터셉터)
	@RequestMapping(value="/admin/write",method = RequestMethod.GET)
	public String adminWrite(@PathVariable(value="id")String id,@ModelAttribute PostVo postVo,ModelMap modelMap) {
		//카테고리 정보가져오기
		List<CategoryVo> categoryList = blogService.getCategory(id);
		modelMap.put("categoryList", categoryList);
		//블로그 정보
		BlogVo blogVo = blogService.getAdminBlog(id);
		modelMap.put("blogVo", blogVo);

		return "blog/blog-admin-write";
	}
	
	//글 작성(인터셉터)
	@RequestMapping(value="/admin/write",method = RequestMethod.POST)
	public String adminWirte(@PathVariable(value="id")String id,@ModelAttribute @Valid PostVo postVo,
							BindingResult result,Model model) {
		
		
		// Valid 체크가 틀릴 시, join form으로 넘김
		if (result.hasErrors()) {
			model.addAttribute(result.getModel()); // Map으로 보내줌
			List<CategoryVo> categoryList = blogService.getCategory(id);
			model.addAttribute("categoryList", categoryList);
			return "blog/blog-admin-write";
		}
		//글 작성
		blogService.writePost(postVo);
		
		//글작성한 페이지로 가기
		return "redirect:/"+id+"/"+postVo.getCategory_no()+"/"+blogService.getPostId(postVo.getCategory_no());
	}
	
	//글 삭제
	@RequestMapping("/admin/post/delete/{category_no}/{no}")
	public String adminDeletePost(@PathVariable(value="id")String id,
							@PathVariable(value="category_no")long category_no,
							@PathVariable(value="no")long no) {
		
		//글삭제
		blogService.deletePost(category_no,no);
		
		return "redirect:/"+id+"/"+category_no;
	}
	
}

package com.cafe24.jblog.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.jblog.service.UserService;
import com.cafe24.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	// 회원가입폼
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}

	// 회원가입 동작 및 블로그 개설
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result, Model model) {

		// Valid 체크가 틀릴 시, join form으로 넘김
		if (result.hasErrors()) {
			model.addAllAttributes(result.getModel()); // Map으로 보내줌
			return "user/join";
		}
		//가입하기
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	// 회원가입 성공
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	
	// 로그인 폼
	@RequestMapping(value= {"/login","/login/{fail}"})
	public String login(@PathVariable (value="fail")Optional<String> fail,Model model) {
		if(fail.isPresent()) {
			model.addAttribute("fail","아이디와 비밀번호가 일치하지 않습니다.");
		}
		return "user/login";
	}
	
}

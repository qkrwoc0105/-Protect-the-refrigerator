package kr.ac.hansung.cse.controller;
import java.lang.reflect.Member;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.hansung.cse.service.MemberService;
//import kr.ac.hansung.cse.vo.MemberVO;

@Controller

@RequestMapping("/logins/*") //URL에 signup라는 요청이 들어오면 무조건 MemberController.java으로 보내겠다.
public class MemberController {
private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	MemberService service;
	
	// 회원가입 get
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String getSignup() throws Exception {
		logger.info("get sign up");
		return "signup";
	}
	
	// 회원가입 post
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public void postSignup(Member vo) throws Exception {
		logger.info("post sign up");
		
		//service.signup(vo);
		
		//return null;
	}
}

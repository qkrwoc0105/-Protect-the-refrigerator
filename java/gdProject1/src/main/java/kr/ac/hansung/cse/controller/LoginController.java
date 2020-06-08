package kr.ac.hansung.cse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
//RequestMapping("user/*")
public class LoginController {

	@RequestMapping(value="/logins", method=RequestMethod.GET) //requestmapping으로 의해서 /product라고하는 url로 들어오는 request가 들어오게되면 이 메소드가 수행됨
	public ModelAndView login(){
		ModelAndView mav = new ModelAndView();
		
		return mav; //view's logical name. products.jsp파일로 넘겨주면 되면은 이 뷰에서 모델로 저장된 products의 값을 출력해주면됨
	}
	@RequestMapping(value="/signup",method = RequestMethod.GET)
	public ModelAndView signUp() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
}

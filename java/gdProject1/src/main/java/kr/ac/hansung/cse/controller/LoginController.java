package kr.ac.hansung.cse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
//RequestMapping("user/*")
public class LoginController {

	@RequestMapping(value="/logins", method=RequestMethod.GET) //requestmapping���� ���ؼ� /product����ϴ� url�� ������ request�� �����ԵǸ� �� �޼ҵ尡 �����
	public ModelAndView login(){
		ModelAndView mav = new ModelAndView();
		
		return mav; //view's logical name. products.jsp���Ϸ� �Ѱ��ָ� �Ǹ��� �� �信�� �𵨷� ����� products�� ���� ������ָ��
	}
	@RequestMapping(value="/signup",method = RequestMethod.GET)
	public ModelAndView signUp() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
}

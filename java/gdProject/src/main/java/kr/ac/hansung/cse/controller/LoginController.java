package kr.ac.hansung.cse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@RequestMapping("/logins") //requestmapping으로 의해서 /product라고하는 url로 들어오는 request가 들어오게되면 이 메소드가 수행됨
	public String getProducts(Model model) {
		
		return "logins"; //view's logical name. products.jsp파일로 넘겨주면 되면은 이 뷰에서 모델로 저장된 products의 값을 출력해주면됨
	}
}

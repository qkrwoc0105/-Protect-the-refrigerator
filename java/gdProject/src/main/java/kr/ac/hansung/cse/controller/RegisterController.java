package kr.ac.hansung.cse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterController {
	@RequestMapping("/registers") //requestmapping���� ���ؼ� /product����ϴ� url�� ������ request�� �����ԵǸ� �� �޼ҵ尡 �����
	public String getProducts(Model model) {
		
		return "registers"; //view's logical name. products.jsp���Ϸ� �Ѱ��ָ� �Ǹ��� �� �信�� �𵨷� ����� products�� ���� ������ָ��
	}
}

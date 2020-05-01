package kr.ac.hansung.cse.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
//controller��� ��Ű���� �� ��ĵ�ϴٰ� @controller�� ������ Ŭ������ ������ �ڵ������� �������. �׷����ϱ����� servlet-context.xml���� component scan����
//basic package�� kr.ac.hansung.cse.controller��� ��
public class ProductController { //controller -> service -> dao ȣ��

	
	@RequestMapping("/products") //requestmapping���� ���ؼ� /product����ϴ� url�� ������ request�� �����ԵǸ� �� �޼ҵ尡 �����
	public String getProducts(Model model) {
		
		return "products"; //view's logical name. products.jsp���Ϸ� �Ѱ��ָ� �Ǹ��� �� �信�� �𵨷� ����� products�� ���� ������ָ��
	}
}

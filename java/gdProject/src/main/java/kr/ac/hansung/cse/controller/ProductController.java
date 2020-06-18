package kr.ac.hansung.cse.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
//controller라는 패키지를 쭉 스캔하다가 @controller가 있으면 클래스를 빈으로 자동적으로 등록해줌. 그렇게하기위해 servlet-context.xml에서 component scan에서
//basic package를 kr.ac.hansung.cse.controller라고 함
public class ProductController { //controller -> service -> dao 호출

	
	@RequestMapping("/products") //requestmapping으로 의해서 /product라고하는 url로 들어오는 request가 들어오게되면 이 메소드가 수행됨
	public String getProducts(Model model) {
		
		return "products"; //view's logical name. products.jsp파일로 넘겨주면 되면은 이 뷰에서 모델로 저장된 products의 값을 출력해주면됨
	}
}

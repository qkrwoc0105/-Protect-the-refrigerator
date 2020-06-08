package kr.ac.hansung.cse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.service.ProductService;

@Controller
//controller라는 패키지를 쭉 스캔하다가 @controller가 있으면 클래스를 빈으로 자동적으로 등록해줌. 그렇게하기위해 servlet-context.xml에서 component scan에서
//basic package를 kr.ac.hansung.cse.controller라고 함
public class ProductController { // controller -> service -> dao 호출

	@Autowired
	private ProductService productService;

	
	  @RequestMapping("/infridge") // requestmapping으로 의해서 /product라고하는 url로 들어오는 request가 들어오게되면 이 메소드가 수행됨 
	  public String getProducts(Model model) {
	  List<Product> products = productService.getProducts(); // service layer에 있는etProducts()를 호출해서 그 Product의 List형을가져옴.그럼 products를 가져다
	
	  model.addAttribute("products", products); // 여기 오른쪽 products에 저장함. 키값은"products"이고 model에다가 저장해두고
	  
	  List<Product> photo = productService.getPhoto();
	  model.addAttribute("photo", photo);
	  
	  return "infridge"; // view's logical name. products.jsp파일로 넘겨주면 되면은 이 뷰에서 모델로저장된 products의 값을 출력해주면됨 
	  }
	 

	/*
	 * @Autowired private ProductDao productDao;
	 */

	/*
	 * @RequestMapping("/infridge") public ModelAndView view2() {
	 * 
	 * List<String> paramList = new ArrayList<String>(); ModelAndView model = new
	 * ModelAndView("views/view2");
	 * 
	 * List<Map<String, Object>> list = productDao.getByteImageList();
	 * model.addObject("list", list);
	 * 
	 * Iterator<Map<String, Object>> itr = list.iterator();
	 * 
	 * while (itr.hasNext()) {
	 * 
	 * Map<String, Object> element = (Map<String, Object>) itr.next(); byte[]
	 * encoded = org.apache.commons.codec.binary.Base64.encodeBase64((byte[])
	 * element.get("img")); String encodedString = new String(encoded);
	 * paramList.add(encodedString); model.addObject("image", paramList);
	 * 
	 * } return model; }
	 */

	
	/*
	 * @RequestMapping(value = "/infridge") public String infridge() { return
	 * "infridge"; }
	 * 
	 * @RequestMapping(value = "/getByteImage") public ResponseEntity<byte[]>
	 * getByteImage() { Map<String, Object> map = productDao.getByteImage(); byte[]
	 * imageContent = (byte[]) map.get("data"); final HttpHeaders headers = new
	 * HttpHeaders(); headers.setContentType(MediaType.IMAGE_PNG); return new
	 * ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK); }
	 */
}

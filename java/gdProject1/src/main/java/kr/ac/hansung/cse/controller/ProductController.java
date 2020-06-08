package kr.ac.hansung.cse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.service.ProductService;

@Controller
//controller��� ��Ű���� �� ��ĵ�ϴٰ� @controller�� ������ Ŭ������ ������ �ڵ������� �������. �׷����ϱ����� servlet-context.xml���� component scan����
//basic package�� kr.ac.hansung.cse.controller��� ��
public class ProductController { // controller -> service -> dao ȣ��

	@Autowired
	private ProductService productService;

	
	  @RequestMapping("/infridge") // requestmapping���� ���ؼ� /product����ϴ� url�� ������ request�� �����ԵǸ� �� �޼ҵ尡 ����� 
	  public String getProducts(Model model) {
	  List<Product> products = productService.getProducts(); // service layer�� �ִ�etProducts()�� ȣ���ؼ� �� Product�� List����������.�׷� products�� ������
	
	  model.addAttribute("products", products); // ���� ������ products�� ������. Ű����"products"�̰� model���ٰ� �����صΰ�
	  
	  List<Product> photo = productService.getPhoto();
	  model.addAttribute("photo", photo);
	  
	  return "infridge"; // view's logical name. products.jsp���Ϸ� �Ѱ��ָ� �Ǹ��� �� �信�� �𵨷������ products�� ���� ������ָ�� 
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

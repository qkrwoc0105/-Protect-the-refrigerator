package kr.ac.hansung.cse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.cse.dao.ProductDao;
import kr.ac.hansung.cse.model.Product;

@Service
public class ProductService { // service-context.xml이 불리면 service 패키지를 쭉 스캔해서 @Service의 클래스를 빈으로 등록. 추후에 컨트롤러
								// @Autowired에 의존성주입이되는 그런 양상

	@Autowired // ProductService는 Dao를 의존성 주입해서 받고
	private ProductDao productDao;

	/*
	 * public Product getProductById(String photoID) {
	 * 
	 * return productDao.getProductById(photoID); }
	 */

	public List<Product> getProducts() {

		return productDao.getProducts(); // dao활용해서 getProducts()라는 메소드 호출해서 리턴
	}

	public List<Product> getPhoto() {

		return productDao.getPhoto(); // dao활용해서 getProducts()라는 메소드 호출해서 리턴
	}

	/*
	 * public byte[] getPhotos(int num) { // TODO Auto-generated method stub return
	 * null; }
	 */

}

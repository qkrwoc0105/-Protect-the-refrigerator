package kr.ac.hansung.cse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.cse.dao.ProductDao;
import kr.ac.hansung.cse.model.Product;

@Service
public class ProductService { // service-context.xml�� �Ҹ��� service ��Ű���� �� ��ĵ�ؼ� @Service�� Ŭ������ ������ ���. ���Ŀ� ��Ʈ�ѷ�
								// @Autowired�� �����������̵Ǵ� �׷� ���

	@Autowired // ProductService�� Dao�� ������ �����ؼ� �ް�
	private ProductDao productDao;

	/*
	 * public Product getProductById(String photoID) {
	 * 
	 * return productDao.getProductById(photoID); }
	 */

	public List<Product> getProducts() {

		return productDao.getProducts(); // daoȰ���ؼ� getProducts()��� �޼ҵ� ȣ���ؼ� ����
	}

	public List<Product> getPhoto() {

		return productDao.getPhoto(); // daoȰ���ؼ� getProducts()��� �޼ҵ� ȣ���ؼ� ����
	}

	/*
	 * public byte[] getPhotos(int num) { // TODO Auto-generated method stub return
	 * null; }
	 */

}

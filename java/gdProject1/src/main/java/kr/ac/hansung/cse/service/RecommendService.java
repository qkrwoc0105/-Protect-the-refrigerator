package kr.ac.hansung.cse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.cse.dao.RecommendDao;
import kr.ac.hansung.cse.model.Recipe;

@Service
public class RecommendService {
	@Autowired
	private RecommendDao recommendDao;

	public List<Recipe> getRecipes() {

		return recommendDao.getRecipes();
	}
	
	public List<Recipe> getProductById(String recipeName) {

		return recommendDao.getProductById(recipeName);

	}

	public List<Recipe> getScoreById(String recipeName) {
		
		return recommendDao.getScoreById(recipeName);
	}

	public List<Recipe> getData() {
		
		return recommendDao.getData();
		
	}

}

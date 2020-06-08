package kr.ac.hansung.cse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.cse.dao.RecipeDao;
import kr.ac.hansung.cse.model.Recipe;

@Service
public class RecipeService {

	@Autowired
	private RecipeDao recipeDao;

	public List<Recipe> getRecipes() {

		return recipeDao.getRecipes();
	}

	public List<Recipe> getDetailRecipes() {

		return recipeDao.getDetailRecipes();
	}

	public List<Recipe> getProductById(String recipeName) {

		return recipeDao.getProductById(recipeName);

	}

	/*
	 * public void updateRecipe(Recipe recipe) { RecipeDao.updateRecipe(recipe); }
	 */

	public List<Recipe> getScoreById(String recipeName) {
		
		return recipeDao.getScoreById(recipeName);
	}

	
	
	/*
	 * public Recipe getScores(String recipeName) {
	 * 
	 * return recipeDao.getScores(recipeName);
	 * 
	 * }
	 */
	  
	
	  public boolean getScores(Recipe recipe) {
	  
	  return recipeDao.getScores(recipe);
	  
	  }
	 

	public boolean addScore(Recipe recipe) {
		return recipeDao.addScore(recipe);
	}
	 	 
}

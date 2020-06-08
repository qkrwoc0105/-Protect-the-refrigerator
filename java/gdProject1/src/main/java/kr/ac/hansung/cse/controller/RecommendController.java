package kr.ac.hansung.cse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.hansung.cse.model.Recipe;
import kr.ac.hansung.cse.service.RecommendService;

@Controller
public class RecommendController {

	@Autowired
	private RecommendService recommendService;

	@RequestMapping("/totalRecommend")
	public String getRecipes(String recipeName, Model model) {

		List<Recipe> recipes = recommendService.getRecipes();

		model.addAttribute("recipes", recipes);

	//	List<Recipe> detailrecipes = recommendService.getProductById(recipeName);
	//	model.addAttribute("detailrecipes", detailrecipes);

		List<Recipe> data = recommendService.getData();
		model.addAttribute("data", data);
		return "totalRecommend";
	}



	@RequestMapping("/totalRecommend/detailRecommend/{recipeName}")
	public String getDetailRecipes(@PathVariable String recipeName, Model model) {

		List<Recipe> detailrecipes = recommendService.getProductById(recipeName);
		model.addAttribute("detailrecipes", detailrecipes);

		List<Recipe> scores = recommendService.getScoreById(recipeName);
		model.addAttribute("scores", scores);

		return "detailRecommend";
	}

	

}

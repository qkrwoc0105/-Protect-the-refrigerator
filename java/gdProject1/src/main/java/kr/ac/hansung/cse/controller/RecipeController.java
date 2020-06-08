package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.hansung.cse.model.Recipe;
import kr.ac.hansung.cse.service.RecipeService;

@Controller
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	@RequestMapping("/totalRecipe")
	public String getRecipes(Model model) {

		List<Recipe> recipes = recipeService.getRecipes();

		model.addAttribute("recipes", recipes);

		return "totalRecipe";
	}

	/*
	 * @RequestMapping("/totalRecipe/detailTotalRecipe") public String
	 * getRecipes(Model model) {
	 * 
	 * List<Recipe> recipes = recipeService.getRecipes();
	 * 
	 * model.addAttribute("recipes", recipes);
	 * 
	 * return "totalRecipe"; }
	 */

	// Recipe recipes = recipeService.getProductById(recipeName);
//recipeService.getRecipes(recipes);
//List<Recipe> detailrecipes = recipeService.getRecipes();

	// recipeName.addAttribute("detailrecipes", detailrecipes);
	/*
	 * @RequestMapping("/totalRecipe/detailTotalRecipe") public String
	 * getDetailRecipes(Model model) {
	 * 
	 * 
	 * List<Recipe> detailrecipes = recipeService.getDetailRecipes();
	 * model.addAttribute("detailrecipes", detailrecipes); return
	 * "detailTotalRecipe"; }
	 */

	// 메소드 없는 detailTotalRecipe

	@RequestMapping("/totalRecipe/detailTotalRecipe/{recipeName}")
	public String getDetailRecipes(@PathVariable String recipeName, Model model) {

		List<Recipe> detailrecipes = recipeService.getProductById(recipeName);
		model.addAttribute("detailrecipes", detailrecipes);

		List<Recipe> scores = recipeService.getScoreById(recipeName);
		model.addAttribute("scores", scores);

		return "detailTotalRecipe";
	}

	@RequestMapping(value = "/totalRecipe/detailTotalRecipe/scoreAdd/{recipeName}", method = RequestMethod.GET)
	public String addScore(@PathVariable String recipeName, Model model) {

		// List<Recipe> detailrecipes = recipeService.getProductById(recipeName);
		// model.addAttribute("detailrecipes", detailrecipes);

		// List<Recipe> scores = recipeService.getScoreById(recipeName);
		// model.addAttribute("scores", scores);

		// Product product = new Product();
		// Recipe recipe = new Recipe();
		// List<Recipe> recipe = new List<>();

		// List<Recipe> recipe = recipeService.getScoreById(recipeName);

		List<Recipe> recipe = new ArrayList<Recipe>();
		model.addAttribute("recipe", recipe);
		// List<Recipe> detailrecipes = recipeService.getProductById(recipeName);
		// model.addAttribute("detailrecipes", detailrecipes);

		// List<Recipe> recipe= recipeService.getScoreById(recipeName);
		// model.addAttribute("scores", recipe);

		return "scoreAdd";
	}

	@RequestMapping(value = "/totalRecipe/detailTotalRecipe/scoreAdd", method = RequestMethod.POST)
	public String addScorePost(Recipe recipe) {

		System.out.println(recipe); // console에 찍히는 부분

		// 실제로 db에 반영되는 부분
		if (!recipeService.addScore(recipe)) // 이거 작성하고 recipeService.java에서getScores메소드 만들어줌
			System.out.println("add score cannot be done");

		return "redirect:/totalRecipe/detailTotalRecipe/{recipeName}";
	}

	@RequestMapping(value = "/totalRecipe/detailTotalRecipe/scoreRegist/{recipeName}", method = RequestMethod.GET)
	public String getScores(@ModelAttribute("recipe") Recipe recipe, @PathVariable String recipeName, Model model) {

		// List<Recipe> detailrecipes = recipeService.getProductById(recipeName);
		// model.addAttribute("detailrecipes", detailrecipes);

		// List<Recipe> scores = recipeService.getScoreById(recipeName);
		// model.addAttribute("scores", scores);

		// Product product = new Product();
	// Recipe recipe = new Recipe();

		// List<Recipe> recipe = recipeService.getScoreById(recipeName);

		List<Recipe> detailrecipes = recipeService.getProductById(recipeName);
		model.addAttribute("detailrecipes", detailrecipes);
		
		List<Recipe> scores = recipeService.getScoreById(recipeName);
		model.addAttribute("scores", scores);
		// Recipe recipe = new Recipe();
		/*
		 * List<Recipe> recipe2 = new ArrayList<>(); recipe2 =
		 * recipeService.getScoreById(recipeName); model.addAttribute("scores",
		 * recipe2);
		 */

		return "scoreRegist";	
	}

	@RequestMapping(value = "/totalRecipe/detailTotalRecipe/scoreRegist/{recipeName}", method = RequestMethod.POST)
	public String getScoresPost(@ModelAttribute("recipe") Recipe recipe, @PathVariable String recipeName) {

		System.out.println(recipe); // console에 찍히는 부분
		
		// 실제로 db에 반영되는 부분
		 if (!recipeService.getScores(recipe)) // 이거 작성하고
		// recipeService.java에서getScores메소드 만들어줌
		 System.out.println("regist score cannot be done");

		return "redirect:/totalRecipe/detailTotalRecipe/{recipeName}";
	}

}

package kr.ac.hansung.cse.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Recipe {

	private String recipeName;
	
	private String data;
	
	private String process;
	//private String processa;
	
	private String recipeTime;
	
	private String difficulty;
	
	private String classification;
	
	private String method;
	
//	private String foodName;
	
//	private String meterage;
	
	private int score;
	
	
}

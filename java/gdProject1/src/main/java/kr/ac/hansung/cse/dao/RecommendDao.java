package kr.ac.hansung.cse.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.cse.model.Recipe;

@Repository
public class RecommendDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {

		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Recipe> getRecipes() { // totalRecipe.jsp

		String sqlStatement = " select * from recommendRecipe where email=100";

		return jdbcTemplate.query(sqlStatement, new RowMapper<Recipe>() {

			@Override
			public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {

				Recipe recipe = new Recipe();

				// String data = new String(rs.getBytes("data"));

				// recipe.setData(data);
				recipe.setRecipeName(rs.getString("recipeName"));
				// recipe.setProcess(rs.getString("process"));
				// recipe.setRecipeTime(rs.getString("recipeTime"));
				// recipe.setDifficulty(rs.getString("difficulty"));
				// recipe.setClassification(rs.getString("classification"));
				// recipe.setMethod(rs.getString("method"));
				// recipe.setFoodName(rs.getString("foodName"));
				// recipe.setMeterage(rs.getString("meterage"));

				return recipe;

			}

		});

	}

	public List<Recipe> getData() {
		String sqlStatement = "select * from recipe where recipeName= any(select recipeName from recommendRecipe where email=100)";

		return jdbcTemplate.query(sqlStatement, new RowMapper<Recipe>() {

			@Override
			public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
				Recipe recipe = new Recipe();

				String data = new String(rs.getBytes("data"));

				recipe.setData(data);
				recipe.setRecipeName(rs.getString("recipeName"));
			
				return recipe;
				
			}
		});
	}

	public List<Recipe> getProductById(String recipeName) {

		String sqlStatement = "select * from recipe where recipeName=?";

		// 하나의 객체를 읽어들이는 것이기에 query가 아닌 queryForObject . new Object[] {id}는
		// ?에해당되는placeholder를 지정해줘야하기에 . 3번째인자 rowmappper는 존나김
		return jdbcTemplate.query(sqlStatement, new Object[] { recipeName }, new RowMapper<Recipe>() {

			@Override
			public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException { // mapRow라고하는 메소드 만들어짐.여기서 레코드를 객체로 //
				String process = new String(rs.getString("process"));

				/*
				 * String[] psplit = process.split("$");
				 * 
				 * String psplit1 = psplit[0] + "\n"; String psplit2 = psplit[1] + "\n"; String
				 * psplit3 = psplit[2] + "\n"; String psplit4 = psplit[3] + "\n"; String psplit5
				 * = psplit[4] + "\n"; String psplit6 = psplit[5] + "\n"; String psplit7 =
				 * psplit[6] + "\n"; String psplit8 = psplit[7] + "\n"; String psplit9 =
				 * psplit[8] + "\n"; String psplit10 = psplit[9] + "\n"; String psplit11 =
				 * psplit[10] + "\n"; String psplit12 = psplit[11] + "\n"; String processa =
				 * psplit1 + psplit2 + psplit3 + psplit4 + psplit5 + psplit6 + psplit7 + psplit8
				 * + psplit9 + psplit10 + psplit11 + psplit12; System.out.println(processa); //
				 * 맵핑해주는 부분을 만들면됨
				 * 
				 * for(String i=0; i<psplit.length; i++) {
				 * 
				 * }
				 */

				// 앞에 lombok을 사용해서 getter,setter만들어놓았음
				Recipe recipe = new Recipe();

				String data = new String(rs.getBytes("data"));

				recipe.setData(data);
				recipe.setRecipeName(rs.getString("recipeName"));
				recipe.setProcess(process);
				recipe.setRecipeTime(rs.getString("recipeTime"));
				recipe.setDifficulty(rs.getString("difficulty"));
				recipe.setClassification(rs.getString("classification"));
				recipe.setMethod(rs.getString("method"));
				// recipe.setFoodName(rs.getString("foodName"));
				// recipe.setMeterage(rs.getString("meterage"));

				return recipe;

			}

		});

	}

	public List<Recipe> getScoreById(String recipeName) {
		String sqlStatement = "select * from recipeScore where email=100 and recipeName=?";

		// 하나의 객체를 읽어들이는 것이기에 query가 아닌 queryForObject . new Object[] {id}는
		// ?에해당되는placeholder를 지정해줘야하기에 . 3번째인자 rowmappper는 존나김
		return jdbcTemplate.query(sqlStatement, new Object[] { recipeName }, new RowMapper<Recipe>() {

			@Override
			public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException { // mapRow라고하는 메소드 만들어짐.여기서 레코드를 객체로 //

				Recipe recipe = new Recipe();

				recipe.setRecipeName(rs.getString("recipeName"));

				recipe.setScore(rs.getInt("score"));

				return recipe;

			}

		});

	}

}

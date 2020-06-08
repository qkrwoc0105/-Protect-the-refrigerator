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

		// �ϳ��� ��ü�� �о���̴� ���̱⿡ query�� �ƴ� queryForObject . new Object[] {id}��
		// ?���ش�Ǵ�placeholder�� ����������ϱ⿡ . 3��°���� rowmappper�� ������
		return jdbcTemplate.query(sqlStatement, new Object[] { recipeName }, new RowMapper<Recipe>() {

			@Override
			public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException { // mapRow����ϴ� �޼ҵ� �������.���⼭ ���ڵ带 ��ü�� //
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
				 * �������ִ� �κ��� ������
				 * 
				 * for(String i=0; i<psplit.length; i++) {
				 * 
				 * }
				 */

				// �տ� lombok�� ����ؼ� getter,setter����������
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

		// �ϳ��� ��ü�� �о���̴� ���̱⿡ query�� �ƴ� queryForObject . new Object[] {id}��
		// ?���ش�Ǵ�placeholder�� ����������ϱ⿡ . 3��°���� rowmappper�� ������
		return jdbcTemplate.query(sqlStatement, new Object[] { recipeName }, new RowMapper<Recipe>() {

			@Override
			public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException { // mapRow����ϴ� �޼ҵ� �������.���⼭ ���ڵ带 ��ü�� //

				Recipe recipe = new Recipe();

				recipe.setRecipeName(rs.getString("recipeName"));

				recipe.setScore(rs.getInt("score"));

				return recipe;

			}

		});

	}

}

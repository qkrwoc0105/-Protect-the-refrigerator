<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- spring form tag library -->
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page import ="kr.ac.hansung.cse.controller.RecipeController" %>
<%@ page import ="kr.ac.hansung.cse.service.RecipeService" %>
<%@ page import ="kr.ac.hansung.cse.dao.RecipeDao" %>
<br>
<div align="center">
<div class="container-wrapper">
<div class="container">
<h1>당신의 SCORE 는?</h1>
		<p class="lead">Fill the below information to update a score:</p>
	<sf:form
		action="${pageContext.request.contextPath}/totalRecipe/detailTotalRecipe/scoreRegist/${recipeName}"
	method="post"  modelAttribute="recipe">
		<!-- method="post"  -->
		
		<sf:hidden path ="recipeName"/>
		
		<div class="form-group">
				<label for="score">Score</label>
				<sf:input path="score" id="score" class="form-control" length="10" />
			
			</div>
		
		<button type="submit" class="btn btn-primary">Submit</button>
		<a href="<c:url value="/totalRecipe/detailTotalRecipe/${recipeName}"/>"
			class="btn btn-dark">Cancel</a>
		<!-- cancel할때는 productInventory로 보내라 -->
	</sf:form>
	</div>
	</div>
	
</div>
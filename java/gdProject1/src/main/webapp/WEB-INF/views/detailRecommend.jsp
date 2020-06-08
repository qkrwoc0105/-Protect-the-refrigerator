<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Total Recipe</title>
</head>
<body>

	<br>

	<div align="center">

		<BR>
		<table style="text-align: center;">

			<c:forEach var="recipe" items="${detailrecipes}">
				<tr>
					<td><img src="data:product/png;base64,${recipe.data}"
						class="mx-auto d-block" width="400px"></td>
				</tr>


				<tr>
					<td><h2>"${recipe.recipeName}"</h2></td>
				</tr>
				<tr>
					<td>요리 방식 : ${recipe.method}</td>
				</tr>
				<tr>
					<td>주재료 분류 : ${recipe.classification}</td>
				</tr>
				<tr>
					<td>소요 시간 : ${recipe.recipeTime}</td>
				</tr>
				<tr>
					<td>난이도 : ${recipe.difficulty}</td>
				</tr>
				<tr>
					<td>조리 방법 : ${recipe.process}</td>
				</tr>
			</c:forEach>
			<c:forEach var="score" items="${scores}">
				<tr>
					<td>내 점수 : ${score.score} 점</td>
				</tr>

				

				

			</c:forEach>
		</table>
	</div>

	<br>
	<br>
	<br>
	<br>
</body>
</html>
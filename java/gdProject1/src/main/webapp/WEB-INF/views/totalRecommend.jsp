<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<br>
	<br>
	<br>
	<div align="left">

		<table
			style="width: 60%; height: 100px; margin: auto; text-align: center; ">
			<tbody>
				<tr>
					<c:forEach var="recipe" items="${data}">



						<td><a
							href="totalRecommend/detailRecommend/${recipe.recipeName}"> <img
								src="data:product/png;base64,${recipe.data}"
								class="mx-auto d-block" width="200px">
						</a></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</c:forEach>
				</tr>
				<tr>

					<c:forEach var="recipe" items="${recipes}">
						<td>"${recipe.recipeName }"</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</c:forEach>

				</tr>
			</tbody>
		</table>
		<br>
	</div>
<br><br><br><br><br><br><br><br><br><br><br><br>
</body>
</html>
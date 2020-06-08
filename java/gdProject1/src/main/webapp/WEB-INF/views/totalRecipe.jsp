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
	<div align="left">
		
		<table style="table-layout:fixed;">
			<tbody>
				<c:forEach var="recipe" items="${recipes}">

					<tr>
						<td><a href="totalRecipe/detailTotalRecipe/${recipe.recipeName}"><img src="data:product/png;base64,${recipe.data}"
							class="mx-auto d-block" width="200px"></a></td>
						<td></td>
						<td>" ${recipe.recipeName } "</td>
					</tr>


				</c:forEach>

			</tbody>
		</table>
		<br>


	</div>

</body>
</html>
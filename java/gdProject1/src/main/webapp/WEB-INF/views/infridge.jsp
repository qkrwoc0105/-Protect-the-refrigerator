<!-- 냉장고 속 보여주는 페이지  -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- <img src="${pageContext.request.contextPath }/resources/paris.jpg" class="mx-auto d-block , img-thumbnail"  alt="Cinque Terre" >
 --%>
<br>



<html>
<body>
	<div align="center">
		<BR>
		<h2>IN FRIDGE</h2>
		<br>
		<table>
			<tbody>
				<c:forEach var="product" items="${photo }">
					<tr>
						<%-- <td><img src="data:product/png;base64,${product.data}"></td> --%>
						<td><img
							src="data:product/png;base64,${product.analyzedData}"
							class="mx-auto d-block , img-thumbnail" width="550px"></td>


					</tr>
				</c:forEach>

			</tbody>
		</table>
		<br>
		<h3>유통기한</h3>
		<table border="1" style="text-align: center;">

			<thead>
				<!-- <tr><th>사진</th> -->
				<th>상품 식별 코드</th>
				<th>상품정보</th>
				<th>입고날짜</th>
				<th>유통기한</th>
				<th>섭취 기한</th>

			</thead>
			<tbody>
				<c:forEach var="product" items="${products }">

					<tr>

						<td>${product.count }</td>
						<td>${product.foodName }</td>
						<td>${product.inString }</td>
						<td>유통기한:${product.finish3 }까지</td>
						<td>${product.realShelf }일 남았습니다</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>

	</div>
	<br><br>
</body>

</html>




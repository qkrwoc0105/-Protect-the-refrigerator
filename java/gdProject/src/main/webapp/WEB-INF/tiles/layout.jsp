<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author"
	content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
<meta name="generator" content="Jekyll v3.8.6">

<title> <tiles:insertAttribute name="title"/> </title>

<link rel="canonical"
	href="https://getbootstrap.com/docs/4.4/examples/carousel/">

<!-- Bootstrap core CSS -->
<link href="<c:url value="/resources/css/bootstrap.min.css"/>"
	rel="stylesheet"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiF7eWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">

<!-- Favicons -->

<link rel="icon" href="<c:url value="/resources/mango2.ico"/>">

<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}
</style>

<!-- Custom styles for this template -->
<link href="<c:url value="/resources/css/carousel.css"/>"
	rel="stylesheet">
<link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
</head>

<body>
	<!--body는 상단 메뉴-->
	<!-- 태그 header는 menu.jsp로 옮겨갔음 -->
	<!-- <main role="main">이 부분은 body에 관련된 부분이어서 옮겨갔음  -->

	<!-- tile.xml에 보면 menu라는 attribute가 있는데 그 값인 menu.jsp가 런타임시에 랜더링된다 -->
	<tiles:insertAttribute name="menu"/>
	<tiles:insertAttribute name="body"/>
	<tiles:insertAttribute name="footer"/>
	
	
	<!--jquery는 javascript이니까 꼭필요한것.cdn에서 가져온것 둔다-->
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
		integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
		crossorigin="anonymous"></script>
	<!--bootstrap에 대한 javascript사용하려면 앞단에 jquery에 대한거 써줘야한다-->
	<script src="<c:url value="/resources/js/bootstrap.bundle.min.js"/>"
		integrity="sha384-6khuMg9gaYr5AxOqhkVIODVIvm9ynTT5J4V1cfthmT+emCG6yVmEZsRHdxlotUnm"
		crossorigin="anonymous"></script>
</body>
</html>

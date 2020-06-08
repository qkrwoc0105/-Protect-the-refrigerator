
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- 부가적인 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>  -->
<!-- <script src="http://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script> -->
<!-- <script  src="https://code.jquery.com/jquery-3.5.0.js" type="text/javascript"></script>  -->
<!-- <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script> -->
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
<!-- src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js" -->
<!--"${pageContext.request.contextPath}/resources/jq/jquery-3.5.1.min.js"  -->
<title>회원가입</title>

<script  type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"  ></script>
<script type="text/javascript">

document.cookie = "crosCookie=bar; SameSite=None; Secure";

	function signUpValidation() {

		var userId = $("#userId").val();
		var userPw = $("#password").val();
		var userPwCheck = $("#passwordCheck").val();

		var email = $("#email").val();

		if (!userId) {
			alert("아이디 입력은 필수입니다.");
			$("#userId").focus();
		} else if (!userPw) {
			alert("비밀번호 입력은 필수입니다.");
			$("#password").focus();
		} else if (!userPwCheck) {
			alert("비밀번호 확인 입력은 필수입니다.");
			$("#passwordCheck").focus();
		} else if (userPw != userPwCheck) {
			alert("비밀번호가 맞지 않습니다.");
			$("#userPwCheck").focus();
		} else if (!email) {
			alert("이메일 입력은 필수입니다.");
			$("#email").focus();
		} else {
			signUp()
		}

	}

	function signUp() {

		$.ajax({
			url : "/jquery/signup",
			type : 'POST',
			data : $("#registerform").serialize(),
			success : function(data) {
				if (data == 1) {
					alert("회원가입이 완료됐습니다.");
					location.href = "logins"
				} else if (data == 2) {
					alert("이미 존재하는 아이디입니다.");
					return false;
				}

			}

		})
	}
</script>
</head>
<body>


	<section id="container">
		<form id="registerform">
			<!-- 	<form action="/signup" method="post"> -->
			<div class="form-group has-feedback">
				<label class="control-label" for="userId">아이디</label> <input
					class="form-control" type="text" id="userId" name="userId"
					maxlength="20" value="" />
			</div>

			<div class="form-group has-feedback">
				<label class="control-label" for="password">패스워드</label> <input
					class="form-control" type="password" id="password" name="password"
					maxlength="20" autocomplete="off" />
			</div>

			<div class="form-group has-feedback">
				<label class="control-label" for="passwordCheck">패스워드 확인</label> <input
					class="form-control" type="password" id="passwordCheck"
					name="passwordCheck" maxlength="20" autocomplete="off" />
			</div>

			<div class="form-group has-feedback">
				<label class="control-label" for="email">이메일</label> <input
					class="form-control" type="text" id="email" name="email" size="20"
					maxlength="20" value="" autocomplete="off">
				<!-- 	<span>@</span>  -->
				<!-- <input class="form-control" id="domain" list="domains" name="domain"
					placeholder="도메인입력/선택">
				<datalist id="domains">
					<option value="naver.com">
					<option value="daum.net">
					<option value="gmail.com">
					<option value="yahoo.co.kr">
				</datalist> -->
			</div>
</form>
			<div class="form-group has-feedback">
				<input type="button" class="btn btn-success"
					onclick="signUpValidation()" value="회원가입">
				<button class="cencle btn btn-danger" type="reset">취소</button>
			</div>
		
	</section>

</body>

</html>
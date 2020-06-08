<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<!-- CDN 방식 콘텐츠 전송 네트워크(Content delivery network)는 콘텐츠를 효율적으로 전달하기 위해 여러 노드를 가진 네트워크에 데이터를 저장하여 제공하는 시스템을 말한다. 인터넷 서비스 제공자에 직접 연결되어 데이터를 전송하므로, 콘텐츠 병목을 피할 수 있는 장점이 있다-->
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> -->
<!-- <script  src="https://code.jquery.com/jquery-3.5.0.js" type="text/javascript"></script> -->
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>  -->
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
<!-- src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js" -->
<meta charset="UTF-8">
<title>로그인 페이지 with 회원가입</title>

<script  type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"  ></script>
 <script type="text/javascript">
function loginValidation(){
	
	var userId = $("#userId").val();
	var password = $("#password").val();
	
	if(!userId){
		alert("아이디를 입력하세요.");
		$("#userId").focus();
		return false;
	}else if(!password){
		alert("비밀번호를 입력하세요.");
		$("#password").focus();
		return false;
	}else {
		login(userId,password);
	}
	
}

function login(userId,password){
	
	$.ajax({
		
		url : "/jquery/logins",
		type : 'POST',
		data : { userId : userId, 
				password : password	
		},
		success:function(data){
			if(data == 2){
				alert("아이디 혹은 비밀번호가 맞지 않습니다.");
				return false;
			}else if(data == 3){
				location.href="home";
			}
		}
		
	})
	
}

function enterKeyCheck(){
	
 if(event.keyCode == 13)
	  {
	 loginValidation();
	  }
	
	
}

</script>

</head>
<br>
<body class="bg-dark">


	<div class="container">
		<div class="card card-login mx-auto mt-5">
			<div class="card-header">Login</div>
			<div class="card-body">

				<form id="signupForm">
					<!-- form.action은 security의 login-processing-url로 해야함 -->
					<!--  <form action="/j_spring_security_check" method="post">-->
					<div class="form-group">
						<div class="form-label-group">

							<!-- input.name은 security의 username-parameter로 해야함 -->
							<input type="text" id="userId" class="form-control"
								placeholder="ID" autofocus onkeyup="enterKeyCheck()">
								<label for="inputId"></label>
							<br>
							<input type="password" id="password" class="form-control"
								placeholder="Password" onkeyup="enterKeyCheck()">
								<label for="inputPw"></label>
						</div>
					</div>

					<div class="form-group"></div>
					<div class="form-group"></div>
					<!-- 	<button class="btn btn-primary btn-block" type="submit">Login</button> -->
					<input type="button" class="btn btn-primary btn-block"
						value="Login" onclick="loginValidation()" >
					<!--  	</form>-->

					<br> <input type=button value="Sign Up"
						class="btn btn-danger btn-block"
						onclick="location.href='signup'">
<!-- onclick="location.href='<c:url value="/logins/signup"/>'">  -->
					<div class="text-center"></div>
					</form>
				
			</div>

		</div>
	</div>

</body>
</html>
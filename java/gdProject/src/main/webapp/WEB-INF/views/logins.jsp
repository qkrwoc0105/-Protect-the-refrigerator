<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>로그인 페이지 with 회원가입</title>
</head>
<br>
<body class="bg-dark">
	<div class="container">
		<div class="card card-login mx-auto mt-5">
			<div class="card-header">Login</div>
			<div class="card-body">
				<!-- form.action은 security의 login-processing-url로 해야함 -->
				<form action="/j_spring_security_check" method="post">
					<div class="form-group">
						<div class="form-label-group">
							<!-- input.name은 security의 username-parameter로 해야함 -->
							<input type="text" id="inputId" name="id" class="form-control"
								placeholder="ID" required="required" autofocus="autofocus">
							<label for="inputId"></label>
							<input type="password" id="inputPw" name="pw" class="form-control"
								placeholder="Password" required="required" autofocus="autofocus">
							<label for="inputPw"></label>
						</div>
					</div>
					<div class="form-group"></div>
					<div class="form-group"></div>
					<button class="btn btn-primary btn-block" type="submit">Login</button>
				</form>
				<form action="/signup" method="post">
				<br>
				<button class="btn btn-danger btn-block" type="submit">Sign Up</button>
				</form>
				<div class="text-center"></div>
			</div>

		</div>
	</div>

</body>
</html>
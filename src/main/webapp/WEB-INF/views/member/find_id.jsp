<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Custom Login Form Styling with CSS3" />
	<meta name="keywords" content="css3, login, form, custom, input, submit, button, html5, placeholder" />
	<meta name="author" content="Codrops" />
	<link rel="shortcut icon" href="../favicon.ico"> 
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css" />
	<script src="${pageContext.request.contextPath}/resources/js/modernizr.custom.63321.js"></script>
	
<title>아이디 찾기</title>

 <style>
			body {
				background-image: url("${pageContext.request.contextPath}/resources/images/blurred.jpg");
			}
			
		</style>
		
</head>
<body>
<form action="login.do" method="post" class="form-3" name="form-3" id="form-3">

<h1><span class="log-in">Log in</span> or <span class="sign-up">sign up</span></h1>
<p class="float">
		<label for="id"><i class="icon-user"></i>ID</label>
		<input type="text" name="id" id="id" value= "${userId}"></p>
			
			<div>
				<h3>
				 아이디는 
				 ${userId}
				 입니다.
				
				</h3>
			<p class="clearfix">
			<input type="submit" class="submitBt" value="로그인화면"/>
			</div>
	
	</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	
<title>Member</title>

<style>
			body {
				background-image: url("${pageContext.request.contextPath}/resources/images/blurred.jpg");
			}
			
		</style>
<script type="text/javascript">
function checkErrCode(){
	var errCode = ${errCode};
	if(errCode != null || errCode != ""){
		switch (errCode) {
		case 1:
			alert("회원정보가 없습니다!");
			break;
		case 2:
			alert("주민번호를 올바르게 입력해주세요!");
			break;
		}
	}
}
</script>
<style>
			body {
				background-image: url("${pageContext.request.contextPath}/resources/images/bg3.jpg");
	
			}
			
		</style>
</head>
<body onload="checkErrCode()">
	<div class="wrapper">
		<form:form modelAttribute="userVO" method="post" action="findPass.do" class="form-4" name="form-4" id="form-4">
			<fieldset>
			<h1><span class="log-in">PA</span> SS <span class="sign-up">WAED</span></h1>
				<p class="float">
				<label for="id"><i class="icon-user"></i>아 이 디 : </label> 
				<input type="text" id="userId" name="userId" placeholder="UserID" required>
				<%-- <span class="error"><form:errors path="userId" /></span><br /> --%>
				
				<label for="name"><i class="icon-user"></i>이 &nbsp 름 : </label>
				<input type="text" id="name" name="name" placeholder="UserName" required></p>
				<%-- <span class="error"><form:errors path="name" /></span><br />
				 --%>
				 
				 <p class="float">
				<label for="email"><i class="icon-user"></i>E - Mail : </label>
				 <input type="text" id="email" name="email" placeholder="E - Mail" required></p>
				<%-- <span class="error"><form:errors path="email" /></span><br />
				 --%>
					<input type="submit" class="submitBt" value="비밀번호찾기" /> 
					<a href="login.do" class="log-twitter">이전</a>
				
			</fieldset>
		</form:form>
	</div>
</body>
</html>
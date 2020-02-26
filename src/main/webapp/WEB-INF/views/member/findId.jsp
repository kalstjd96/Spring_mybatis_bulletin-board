<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Member</title>
<link href="${pageContext.request.contextPath}/resources/css/main.css"
	rel="stylesheet" type="text/css">
 --%>	

	
<%-- <c:if test="${errCode == null}">
	<c:set var="errCode" value="\"\""></c:set>
</c:if> --%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Custom Login Form Styling with CSS3" />
	<meta name="keywords" content="css3, login, form, custom, input, submit, button, html5, placeholder" />
	<meta name="author" content="Codrops" />
	<link rel="shortcut icon" href="../favicon.ico"> 
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css" />
	<script src="${pageContext.request.contextPath}/resources/js/modernizr.custom.63321.js"></script>
	
<script type="text/javascript">
function checkErrCode(){
	var errCode = ${errCode};
	if(errCode != null || errCode != ""){
		switch (errCode) {
		case 1:
			alert("회원정보가 없습니다!");
			break;
		case 2:
			alert("회원님의 아이디는" + id);
			break;
			
		}
	}
}
</script>
 <style>
			body {
				background-image: url("${pageContext.request.contextPath}/resources/images/wood_pattern.jpg");
			}
			
		</style> 
</head>
<body>

<section class="main">
<form:form modelAttribute="userVO" method="post" action="findId.do" class="form-2" name="form-2" id="form-2">
		
			<h1><span class="log-in">Log in</span> or <span class="sign-up">sign up</span></h1>
			<p class="float">
			<label for="name"><i class="icon-user"></i>이름</label>
			<input type="text" name="name" id="name" placeholder="Username"></p>
			<%-- <span class="error"><form:errors path="name" /></span> --%>
			
			<p class="float">
			<label for="email"><i class="icon-lock"></i>E = Mail</label>
			<input type="text" name="email" id="email" placeholder="E - Mail"></p>
			<p class="clearfix">
			<input type="submit" class="submitBt" value="아이디찾기"/>
			<a href="login.do" class="log-twitter">이전</a>
			</p>
	
		</form:form>
	</section>
</body>
</html>



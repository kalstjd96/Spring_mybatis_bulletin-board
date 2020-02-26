
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>탈퇴 화면</title>
<link href="${pageContext.request.contextPath}/resources/css/main.css"
	rel="stylesheet" type="text/css">
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	

<style>
			body {
				background-image: url("${pageContext.request.contextPath}/resources/images/wood_pattern.jpg");
	
			}
			
		</style> 

<script>
	// 비밀번호 미입력시 경고창
	function check() {
		if (!document.deleteform.userId.value) {
			alert("비밀번호를 입력하지 않았습니다.");
			return false;
		}

	}
</script>

</head>
<body>

	<h2 class=delete0>탈퇴</h2>
	<h2 class=delete1>시 모든 정보가 삭제됩니다.</h2>
	<br>
	<br>
	<br>
	
	
<form:form modelAttribute="userVO"  id="form" Method='post' Action='delete.do' onsubmit="return check()">
		<label for="userId">아 이 디 : </label> 
				
				<input type="text" id="userId" name="userId" class="loginInput">
				<span class="error"><form:errors path="userId" /></span><br />
		<br> 
		<input type="button" value="취소" onclick='history.go(-1)'/>	
			<input type="submit" value="탈퇴" id="submitBt" class="submotBt"/>
	</form:form>     

</body>
</html>
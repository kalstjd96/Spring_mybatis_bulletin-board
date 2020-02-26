
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
		
<link href="${pageContext.request.contextPath}/resources/css/main.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script type="text/javascript">
function addOrsung(min){
	if(min != ""){
		$("h2").text("회원정보수정");
		$("#userId").attr("readonly", true);
		$("#jumin").attr("readonly", true);
		$("#submitBt").attr("value", "수정");
		$("#form-3").attr("action", "detail.do");
	}
}
</script>

<script type="text/javascript">

/* function Check_id() 
{
   if (joinForm.userId.value.length < 1){
		alert("아이디를 입력하세요.");
		joinForm.userId.focus();
		return false;
   }
   
   var loc = '/member/checkid.do?userId='+joinForm.id.value
		   //Controller에서 받아들였던 checkid.do를 의미하는것이다
   location.href = loc ;
} */

function check() {
	if ($("#userId").val().length < 1) {
		$("#userId").focus();
		alert("아이디를 입력해주세요!");
		return false;
	}
/* 	if (joinForm.re_id.value != "1"){  //value값이 1이 아니라면 alert부분을 실행한다
	    alert ("중복확인을 클릭하여 주세요.");
	    joinForm.id.value="";
	    joinForm.id.focus();
	    return false;  //즉 1이 아니다 중복체크를 클릭을 안했다면 false 값을 넘기지 않는다 
	}
	 */
	if ($("#passwd").val().length < 1) {
		$("#passwd").focus();
		alert("비밀번호를 확인해주세요!");
		return false;
	}
	
	if ($("#passwd").val() != $("#passwd1").val()) {
		$("#passwd").focus();
		alert("비밀번호가 일치하지 않습니다.");
		return false;
	}
	
	if ($("#name").val().length < 1) {
		$("#name").focus();
		alert("이름을 입력해주세요!");
		return false;
	}
	
	if ($("#jumin").val().length < 13 || $("#jumin").val().length > 14) {
		$("#jumin").focus();
		alert("주민번호를 확인해주세요!");
		return false;
	}
	
	if ($("#email").val().length < 1) {
		$("#email").focus();
		alert("이메일을 입력해주세요!");
		return false;
	}
}
</script>
<style>
			body {
				background-image: url("${pageContext.request.contextPath}/resources/images/wood_pattern.jpg");
	
			}
			
		</style> 

</head>
<body onload="addOrsung('${min}')">
	<div class="wrapper">
		<h2>회원가입</h2>
		<form:form modelAttribute="userVO"  class="form-3" name="form-3" id="form-3" method="post" action="join.do" onsubmit="return check()">
		<h1><span class="log-in">WelComE</span> 헿 <span class="sign-up">JOIN</span></h1>
				<input type="hidden" id="mno" name="mno" value="${user.mno}" />
				<p class="float">
				<label for="userId"><i class="icon-user"></i>ID</label>
				
				<input type="text" id="userId" name="userId" class="loginInput" value="${user.userId}" required></p>
		        
		        
		        <p class="float">
				<label for="passwd"><i class="icon-user"></i>PWd</label>
				<input type="password" id="passwd" name="passwd" class="loginInput" required></p>
				
		        <p class="float">
				<label for="passwd"><i class="icon-user"></i>비밀번호확인 : </label>
				<input type="password" id="passwd1" name="passwd1" class="loginInput" required></p>
				
				<p class="float">
				<label for="name"><i class="icon-user"></i>이 &nbsp 름 : </label>
				<input type="text" id="name" name="name" class="loginInput" value="${user.name}" required></p>
				
				<p class="float">
				<label for="jumin"><i class="icon-user"></i>주민번호 : </label>
				<input type="password" id="jumin" name="jumin" class="loginInput" value="${user.jumin}" required></p>
				
				<p class="float">
				<label for="email"><i class="icon-user"></i>E - Mail : </label>
				<input type="text" id="email" name="email" class="loginInput" value="${user.email}" required></p>
				
				<center>
					<input type="submit" id="submitBt" class="submitBt" value="가입"  /> 
					<input type="button" class="submitBt" onclick="location.href='<%=request.getContextPath()%>/member/ajaxlogin.do'" value="이전" />
					<%-- <input type="button" class="submitBt" onclick="location.href='<%=request.getContextPath()%>/baord/list.do'" value="이전" />
				 --%>
				</center>
			
		</form:form>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Member</title>
<link href="${pageContext.request.contextPath}/resources/css/main.css"
	rel="stylesheet" type="text/css">
</head>

<body>
<div align="center" class="body">
<h2>유저 등록 완료 화면</h2>
<br><br>
<b><font color="red">다음과 같이 유저 등록이 완료되었습니다.</font></b><br>

<table border='1' width='600' cellpadding='0' cellspacing='0'>
	<tr height="40px">
		<td bgcolor='cccccc' width='100' align='center' >유저ID:</td>
		<td>${user.userid}</td>
	</tr>
	<%-- <tr height="40px">
		<td bgcolor='cccccc' width='100' align='center' >패스워드:</td>
		<td>${user.pass}</td>
	</tr> --%>
	<tr height="40px">
		<td bgcolor='cccccc' width='100' align='center' >이름:</td>
		<td>${user.name}</td>
	</tr>
	<tr height="40px">
		<td bgcolor='cccccc' width='100' align='center' >이메일:</td>
		<td>${user.email}</td>
	</tr>
	<tr height="40px">
		<td bgcolor='cccccc' width='100' align='center' >가입날짜:</td>
		<td>${user.regdate}</td>
	</tr>
		
</table>
        <br><p><ul id="ullog" >
		<li id="lilogb"><a href="/member/login.do">로그인</a></li>
        </ul></p>
</div>
</body>
</html>
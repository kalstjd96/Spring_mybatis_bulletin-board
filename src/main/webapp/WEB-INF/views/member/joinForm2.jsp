
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
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script type="text/javascript">
function addOrsung(aom){
	if(aom != ""){
		$("h2").text("회원정보수정");
		$("#userId").attr("readonly", true);
		$("#jumin").attr("readonly", true);
		$("#submitBt").attr("value", "정보수정");
		$("#form").attr("action", "detail.do");
	}
}
</script>

<script type="text/javascript">

function Check_id() 
{
   if (joinForm.userId.value.length < 1){
		alert("아이디를 입력하세요.");
		joinForm.userId.focus();
		return false;
   }
   
   var loc = '/member/checkid.do?userId='+joinForm.id.value
		   //Controller에서 받아들였던 checkid.do를 의미하는것이다
   location.href = loc ;
}

function Check_Dup()  //회원가입 클릭 시 실행 되는 것이다.
{
	if (joinForm.re_id.value != "1"){  //value값이 1이 아니라면 alert부분을 실행한다
	    alert ("중복확인을 클릭하여 주세요.");
	    joinForm.userId.value="";
	    joinForm.userId.focus();
	    return false;  //즉 1이 아니다 중복체크를 클릭을 안했다면 false 값을 넘기지 않는다 
	}
	if (joinForm.passwd.value != joinForm.passwd1.value){ 
	    alert ("패스워드가 일치하지않습니다.");
	    joinForm.passwd.value="";
	    joinForm.passwd1.value="";
	    joinForm.passwd.focus();
	    return false;
	}
	
	if ($("#jumin").val().length < 13 || $("#jumin").val().length > 14) {
		$("#jumin").focus();
		alert("주민번호를 확인해주세요!");
		return false;
	}
	
	return true; //위에 결과를 모두 넘긴다면 true 값을 넘긴다 페이지를 넘긴다는것 
}


</script>
<style>
			body {
				background-image: url("${pageContext.request.contextPath}/resources/images/wood_pattern.jpg");
	
			}
			
		</style> 

</head>
<body onload="addOrsung('${aom}')">

<form:form modelAttribute="userVO" Name='joinForm' Method='post' Action='join.do' onsubmit='return Check_Dup()'>
  	  															<!-- submit을 했을 때 onsubmit 즉 Check Dup를 실행해보고 값의 따라서 true면 이동 
  	  																아닌 false이면 값을 실행시키지 않는다 즉 결과값이 true일 때만 페이지를 넘긴다는 것이다 -->
<TABLE  border='2' width='600'  cellSpacing=0 cellPadding=5 align=center>
<TR>
<td>
<input type="hidden" id="mno" name="mno" value="${user.mno}" />
</td>
</TR>
<TR>

	<TD bgcolor='cccccc' width='100' align='center'><font size='2'>아 이 디</font></TD>
	<TD bgcolor='cccccc'>
		<input type="text" id="userId" name="userId" class="loginInput" value="${user.userId}">
				<input type='button' OnClick='Check_id()' value='ID 중복검사'>
		<span class="fieldError"><form:errors path="userId" /></span><span class="fieldError"> *${message}</span>
        <input type="hidden" name="re_id" value="${reDiv}" >
	</TD>
</TR>
<TR>
	<TD bgcolor='cccccc' align='center'><font size='2'>비 밀 번 호</font></TD>
	<TD bgcolor='cccccc'>
		<form:password path="passwd" maxlength="20" cssClass="password"  size='10' />
		<span class="fieldError"><form:errors path="passwd" /></span>
	</TD>
</TR>
<TR>
	<TD bgcolor='cccccc' align='center'><font size='2'>비밀번호확인</font></TD>
	<TD bgcolor='cccccc'>
		<input type="password" name="passwd1" maxlength="20" cssClass="password"  size='10' value="" showPassword="true" />
	</TD>
</TR>
<TR>
	<TD bgcolor='cccccc' align='center'><font size='2'>이 름</font></TD>
	<TD bgcolor='cccccc'>
		<form:input path="name" maxlength="20" cssClass="name" size='10' value="${user.name}" />
		<span class="fieldError"><form:errors path="name" /></span>
	</TD>
</TR>
<TR>
	<TD bgcolor='cccccc' align='center'><font size='2'>E - M a i l</font></TD>
	<TD bgcolor='cccccc'>
		<input type='text' maxlength='50' size='50' name='email' value="${user.email}">
		<span class="fieldError"><form:errors path="email" /></span>
	</TD>
</TR>
<TR>
	<TD bgcolor='cccccc' align='center'><font size='2'>J - u m i n</font></TD>
	<TD bgcolor='cccccc'>
		<input type='password' maxlength='50' size='50' name='jumin' value="${user.jumin}">
		<span class="fieldError"><form:errors path="jumin" /></span>
	</TD>
</TR>
</TABLE><br>
<TABLE border='0' width='700' cellpadding='0' cellspacing='0'>
	<TR><TD><hr size='1' noshade></TD></TR>
</TABLE>
<TABLE>
<TR><TD colspan='2' align='center'><input type='submit' value='회원가입'></TD></TR>

</TABLE>
</form:form>
<%-- 	<div class="wrapper">
		<h2>회원가입</h2>
		<form:form modelAttribute="userVO" id="form" method="post" action="join.do"  onsubmit='return Check_Dup()'>
			<fieldset>
				<input type="hidden" id="mno" name="mno" value="${user.mno}" />
				<label for="userId">아 이 디 : </label> 
				
				<form:input path="userId" maxLength='20' size='10' value="${userId}" cssClass="userId"  />
		<input type='button' OnClick='Check_id()' value='ID 중복검사'>
		<span class="fieldError"><form:errors path="userId" /></span><span class="fieldError"> *${message}</span>
        <input type="hidden" name="re_id" value="${reDiv}" ></br>
		        
				<label for="passwd">비밀번호 : </label><input type="password" id="passwd" name="passwd" class="loginInput">
				<span class="error"><form:errors path="passwd" /></span><br />
				
		        
				<label for="passwd">비밀번호확인 : </label><input type="password" id="passwd1" name="passwd1" class="loginInput">
				<span class="error"></span><br />
				<label for="name">이 &nbsp 름 : </label><input type="text" id="name" name="name" class="loginInput" value="${user.name}">
				<span class="error"><form:errors path="name" /></span><br />
				<label for="jumin">주민번호 : </label><input type="password" id="jumin" name="jumin" class="loginInput" value="${user.jumin}">
				<span class="error"><form:errors path="jumin" /></span><br />
				<label for="jumin">E - Mail : </label> <input type="text" id="email" name="email" class="loginInput" value="${user.email}">
				<span class="error"><form:errors path="email" /></span><br />
				<center>
					<input type="submit" id="submitBt" class="submitBt" value="회원가입"  /> 
					<input type="button" class="submitBt" onclick="location.href='<%=request.getContextPath()%>/member/ajaxlogin.do'" value="이전" />
					<input type="button" class="submitBt" onclick="location.href='<%=request.getContextPath()%>/baord/list.do'" value="이전" />
				
				</center>
			</fieldset>
		</form:form>
	</div> --%>
</body>
</html>
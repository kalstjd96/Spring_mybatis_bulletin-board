<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
<title>Custom Login Form Styling</title>
	<meta name="description" content="Custom Login Form Styling with CSS3" />
	<meta name="keywords" content="css3, login, form, custom, input, submit, button, html5, placeholder" />
	<meta name="author" content="Codrops" />
	<link rel="shortcut icon" href="../favicon.ico"> 
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css" />
	<script src="${pageContext.request.contextPath}/resources/js/modernizr.custom.63321.js"></script>
	<!--[if lte IE 7]><style>.main{display:none;} .support-note .note-ie{display:block;}</style><![endif]-->
<script>
function formSubmit() {
    $.ajax({
        url: 'ajaxlogin.do',
        type: 'POST',
        data: $("#form-2").serialize(),
        dataType: 'json',
        success: function (result) {  
            $("#ajax").remove();           
            if (result.msg=="Success"){
            alert('환영합니다! ' + result.id+' 님');
            location.href="http://localhost:8080/bbs/board/list.do";    
            }
            else {
             if (result.err=="eid"){
             	alert('ID가 입력되지않았습니다.');
             }
             else if(result.err=="epass"){
             	alert('패스워드가 입력되지않았습니다.');
             }
             else{
             	alert('ID 또는 패스워드가 잘못 입력되었습니다');
             }
            location.href ="http://localhost:8080/bbs/member/ajaxlogin.do";
            }  
        }
    });
}
<%-- function checkErrCode(userId){
	var errCode = ${errCode};
	if(errCode != null || errCode != ""){
		switch (errCode) {

		case 3:
			alert("회원가입 처리가 완료되었습니다! 로그인 해 주세요!");
			
			 location.href="http://localhost:8080/bbs/member/ajaxlogin.do"; 
			break;
		case 5:
			alert("회원가입에 실패하였습니다 ! 가입을 다시 해 주세요!");
			location.href = "<%=request.getContextPath()%>/member/ajaxlogin.do";
			break;
		}
	}
} --%>
</script>
<style>
			body {
				/* background: #e1c192 
				url(${pageContext.request.contextPath}/resources/images/wood_pattern.jpg);
				 */
				background-image: url("${pageContext.request.contextPath}/resources/images/wood_pattern.jpg");
	
			}
			
		</style>
</head>
<body>
	<section class="main">
		<form class="form-2" name="form-2" id="form-2" method="post">
			<h1><span class="log-in">Log in</span> or <span class="sign-up">sign up</span></h1>
			<p class="float">
			<label for="login"><i class="icon-user"></i>아이디</label>
			<input type="text" name="userId" id="userId" value="${userId}" placeholder="Username or email"></p>
			<p class="float">
			<label for="password"><i class="icon-lock"></i>비밀번호</label>
			<input type="password" name="passwd" id="passwd" placeholder="Password" class="showpassword"></p>
			<p class="clearfix">
			<a href="login.do" class="log-twitter">전버전</a>
			<a href="findId.do" class="log-twitter">ID찾기</a>
			<a href="findPass.do" class="log-twitter">PW찾기</a>
			<input type="button" name="submit" value="로그인" onclick="formSubmit()" class="log-twitter">
			</p>
		</form>
	</section>
	<!-- jQuery if needed -->
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	<script type="text/javascript">
	$(function(){
			$(".showpassword").each(function(index,input) {
			var $input = $(input);
			$("<p class='opt'/>").append($("<input type='checkbox' class='showpasswordcheckbox' id='showPassword' />").click(function() {
				var change = $(this).is(":checked") ? "text" : "password";
				var rep = $("<input placeholder='Password' type='" + change + "' />").attr("id", $input.attr("id"))
				.attr("name", $input.attr("name")).attr('class', $input.attr('class')).val($input.val()).insertBefore($input);
				$input.remove();
				$input = rep;
				})).append($("<label for='showPassword'/>").text("Show password")).insertAfter($input.parent());
			});
		$('#showPassword').click(function(){
			if($("#showPassword").is(":checked")) {$('.icon-lock').addClass('icon-unlock');
			$('.icon-unlock').removeClass('icon-lock');
			} else {
				$('.icon-unlock').addClass('icon-lock');
				$('.icon-lock').removeClass('icon-unlock');
			}
		});
	});
	</script>
</body>
</html>
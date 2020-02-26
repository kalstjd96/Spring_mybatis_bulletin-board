<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 글 목록</title>

<link href="${pageContext.request.contextPath}/resources/css/board.css"
	rel="stylesheet" type="text/css" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.7.1.js"></script>
<c:if test="${errCode == null}">
<c:set var="errCode" value="\"\""></c:set>
</c:if>
<script type="text/javascript">
	function selectedOptionCheck(){
	    $("#type > option[value=<%=request.getParameter("type")%>]").attr("selected", "true");
	}
	
	function moveAction(where) {
		switch (where) {
		case 1:
			location.href = "write.do";
			break;
		case 2:
			location.href = "list.do";
			break;
		case 3:
			location.href = "delete.do";
			break;
			
		}
	}
</script>
 <script type="text/javascript">
function addOrsung(min){
	if(min != ""){
		$("#keyword").attr("#userId");
		$("#mins").attr("action", "list.do");
		$("#submit").attr("value", "이전");	
	}
}
</script> 
<style>

    
.hit {
      animation-name: blink;
      animation-duration: 1.5s;
      animation-timing-function: ease;
      animation-iteration-count: infinite;
    }
    @keyframes blink {
      from {color: white;}
      30% {color: blue;}
      to {color: red; font-weight: bold;}
    }
    
			body {
				/* background: #e1c192 
				url(${pageContext.request.contextPath}/resources/images/wood_pattern.jpg);
				 */
				background-image: url("${pageContext.request.contextPath}/resources/images/bg3.jpg");
	
			}
			
    
</style>
</head>
<body onload="selectedOptionCheck();">
	<div class="wrapper">
		<div class="navBar">
			<ul>
				<li><a href="list.do">스프링 게시판</a></li>
				<%-- <c:if test="${not empty userId}"> 
					<li><a href="../member/edituser.do"> 내 정보 </a></li> 
				</c:if>  --%>
				
				<%-- <c:if test="${empty userId}">
					<li><a
						href="${pageContext.request.contextPath}/member/join.do">회원가입</a></li>
					 <!-- 이 EL 코드를 쓰는 이유는 server.xml부분에서 분리함으로써 변경 후에도 톰캣을 
					 재시작할 팔요가 없어 유지보수에 용이하기 때문에 사용하는 것    -->
					<li><a
						href="${pageContext.request.contextPath}/member/login.do">로그인</a></li>
				</c:if> --%>
				<!-- 로그인 됬을 경우 -->
				<c:if test="${!empty userId}">
				
					 <li><a
						href="${pageContext.request.contextPath}/board/mylist.do">내가 쓴 글</a></li>
						
					<li><a
						href="${pageContext.request.contextPath}/member/detail.do">비번 번경</a></li>
					<li><a
						href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a></li>
					<li><a
						href="${pageContext.request.contextPath}/member/detail.do">${userId}님 안녕하세요</a></li>
					<li><a
						href="${pageContext.request.contextPath}/member/delete.do">회원탈퇴</a></li>
						
				</c:if>
				
			</ul>
				
			<form action="list.do" modelAttribute="searchVO" method="get" id="mins" onclick="addOrsung('${min}')">
				<select id="type" name="type">
					<option value="title">제목</option>
					<option value="content">내용</option>
					<option value="writerId">작성자</option>
				</select> 
				<input type="text" id="keyword" name="keyword"
					value="<%if (request.getParameter("keyword") != null) {out.print(request.getParameter("keyword"));
			} else {
				out.print("");
			}%>" />
				<input type="submit" value="검색" />
			</form>
		</div>
		<table border="0" class="boardTable">
			<thead>
				<tr>
					<th>글번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>댓글수</th>
					<th>조회수</th>
					<th>추천수</th>
					<th>작성일</th>
					 <c:if  test="${userId == 'admin'}">
					<th>부정글 삭제</th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="board" items="${boardList}">
					<tr>
						<td class="idx">${board.bno}</td>
						<td align="left" class="subject"><c:if
								test="${board.replycnt >= 10}">
								<img
									src="${pageContext.request.contextPath}/resources/images/111.png" />
							</c:if>
							
							<a href="view.do?bno=${board.bno}">${board.title}</a>
							<c:if test="${board.recommendcnt >= 10}"><span class="hit">인기!</span></c:if></td>
							
						<td class="writer">${board.writerId}</td>
						<td class="comment">${board.replycnt}</td>
						<td class="hitcount">${board.viewcnt}</td>
						<td class="recommendcount">${board.recommendcnt}</td>
						<td class="writeDate">${board.regDate}</td>
					 	<c:if  test="${userId == 'admin'}">
						<td><form method="post" action="deletes.do">  
							<input type="hidden" name="board.bno" value="${board.bno}"/>
							<input type="submit" value="글 삭제">
			</form></td>
			</c:if>
			
			
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br /> ${pageHttp} <br />
		<br /> <input type="button" value="목록" class="writeBt"
			onclick="moveAction(2)" /> <input type="button" value="쓰기"
			class="writeBt" onclick="moveAction(1)" />
	</div>
</body>
</html>
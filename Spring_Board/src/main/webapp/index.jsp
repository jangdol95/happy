<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>
	<a href="./loginForm.do">로그인 폼이동</a>
</h1>


<a href="./signUpForm.do?">회원가입</a>
<a href="./idCheck.do?id=happ">아이디 확인</a>
<a href="./login.do?id=TEST001&pw=TEST001">로그인</a>
<a href="./memberList.do">회원리스트</a>

<hr>

<a href="./writeBoard.do?id=gold99&title=새글&content=new글">root글작성하기</a>
<a href="./reply.do?seq=9&id=happy&title=행복&content=어디있니">답글달기</a>
<a href="./getOneBoard.do?seq=10">상세글</a>
<a href="./redcountBoard.do?seq=10">조회증가</a>
<a href="./modifyBoard.do?seq=10&title=군대&content=잘갔다와요.">수정하기</a>
<a href="./delflagBoard.do?seq=10">짭삭제</a>
<a href="./deleteBoardSel.do?seq=9">삭제선택</a>
<a href="./deleteBoard.do?seq=10">찐삭제</a>
<a href="./userBoardListTotal.do">사용자가 볼수 있는</a>
<a href="./adminBoardListTotal.do">관리자가 볼수 있는</a>
<a href="./userBoardListRow.do">사용자 글갯수</a>
<a href="./adminBoardListRow.do">관리자 글갯수</a>
</body>
</html>
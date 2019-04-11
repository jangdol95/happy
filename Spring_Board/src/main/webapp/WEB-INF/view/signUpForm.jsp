<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="./css/SignUp.css">

<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
</head>
<script type="text/javascript">
function check() {
// 	alert("sdf");
	var pw=document.getElementById("pw").value;
	var passOK=document.getElementById("passOK").value;
	var frm=document.forms[0];
	
	var chk=document.getElementById("chaVal").value;
// 	alert(chk);

	if(pw!=passOK){
		swal("회원가입 오류:","비밀번호를 확인해주세요");
		return false;		
	}else if(chk=="0"){
		swal("회원가입 오류:","아이디 중복체크를 해주세요");
		return false;		
	}else{
		return true;
	}
}
	$(document).ready(function(){
		$("#id").keyup(function(){
			var inputlength=$(this).val().length;
// 			alert(inputlength);
			var id=$(this).val();
			//공백 유효값 검사
			if(id.indexOf(" ")!=-1){
// 				alert("공백있음");
				$("#result").css("color","red");
				$("#result").html("공백이 포함된 아이디는 사용이 불가합니다.");
				$("#chaVal").val("0");//document.getElementById("chaVal").value="0";
			}else if(inputlength>5){
// 				alert("작동");
				jQuery.ajax({
					url:"./idCheck.do",
					type:"post",
					data:"id="+$(this).val(),
					async:true,
					success:function(msg){
// 						alert(msg);
						$("#result").html(msg);
						var temp=msg;
						temp=temp.substring(0,temp.indexOf("디")+1);
						if(temp=="사용가능한 아이디"){
							$("#chaVal").val(1);
							$("#result").css("color","blue");
						}else{
							$("#chaVal").val(0);
							$("#result").css("color","red");
						}
					}
				});
			}else{
				$("#result").css("color","red");
				$("#result").html("6자리 이상만 가능합니다.");
				$("#chaVal").val("0");//document.getElementById("chaVal").value="0";
			}
		});
	});
</script>
<body>
<div id="container">
	<div id="title">
		<img id="TitleImage" src="./image/signuptitle.png">
	</div>
	
	<input type="hidden" value="0" id="chaVal">
	
	<form action="./signUp.do" method="post" id="frm" name="frm" onsubmit="return check()">
		<div id="info">
			<div id="LeftInfo">정보입력</div>
				
			<div id="CenterInfo">
				<input type="text" id="name" name="name" placeholder="이름" required="required">
				<br>
				
				<input type="text" id="id" name="id" placeholder="아이디" required="required">
				<br>
				<span id="result"></span>
				<br>
				
				<input type="text" id="pw" name="pw" placeholder="비밀번호" required="required">
				<br>
				<input type="text" id="passOK" required="required" placeholder="비밀번호확인">
				
				
			</div>
				<div id="RightInfo"></div>
			</div>
				<div id="line"></div>
				
				<div id="bottom">
					<br>
					*만 14세 미만의 법정대리인 동의 후 회원서비스가 이용가능합니다.
					<br>
					*선택 약관에 동의하지 않아도 회원가입은 가능합니다.
					<br>
					<strong id="bottomStrong">
					약관과 개인정보 수집 및 이용을 확인하였으며 이에 동의 합니까?
					</strong>
					<br>
					
					<div id="button">
						<input class="btn btn-success" type="submit" value="동의하고 회원가입">
						<input class="btn btn-sm btn-primary" type="button" value="돌아가기" onclick="javascript:history.back(-1)">
					</div>
				</div>
	</form>
	
</div>
</body>
</html>
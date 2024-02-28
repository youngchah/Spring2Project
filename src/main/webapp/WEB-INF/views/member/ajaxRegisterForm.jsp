<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AjaxRegisterForm</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>9. Ajax 방식 요청 처리</h1>
	<hr/>
	
	<form>
		<p>userId : <input type="text" id="userId" name="userId"> </p>
		<p>password : <input type="text" id="password" name="password"> </p>
	</form>
	
	<p>3) 객체 타입의 JSON 요청 데이터를 @RequestBody 어노테이션을 지정하여 자바빈즈 매개변수로 처리한다.</p>
	<button id="registerBtn03">registerBtn03</button>
	
	<p>5) 요청 URL에 쿼리 파라미터를 붙여서 전달하면 문자열 매개변수로 처리한다.</p>
	<button id="registerBtn05">registerBtn05</button><br/>
	
	<p>7) 객체 배열 타입의 JSON 요청 데이터를 자바빈즈 요소를 가진 리스트 컬렉션 매개변수에 @RequestBody를 지정하여 처리한다.</p>
	<button id="registerBtn07">registerBtn07</button><br/>
	
	<p>8) 중첩된 객체 타입의 JSON 요청 데이터를 @RequestBody 어노테이션을 지정하여 중첩된 자바빈즈 매개변수로 처리한다.</p>
	<button id="registerBtn08">registerBtn08</button><br/>
	
	<p>9) 중첩된 객체 타입(컬렉션 List)의 JSON 요청 데이터를 @RequestBody 어노테이션을 지정하여 중첩된 자바빈즈 매개변수로 처리한다.</p>
	<button id="registerBtn09">registerBtn09</button><br/>

</body>
<script type="text/javascript">
$(function(){
	var registerBtn03 = $("#registerBtn03");
	var registerBtn05 = $("#registerBtn05");
	var registerBtn07 = $("#registerBtn07");
	var registerBtn08 = $("#registerBtn08");
	var registerBtn09 = $("#registerBtn09");
	
	// 3) 객체 타입의 JSON 요청 데이터를 @RequestBody 어노테이션을 지정하여 자바빈즈 매개변수로 처리한다.
	registerBtn03.on("click", function(){
		var userId = $("#userId").val();
		var password = $("#password").val();
		
		var userObject = {
			userId : userId,
			password : password
		}
		
		$.ajax({
			type : "post",
			url : "/ajax/register03",
			contentType : "application/json; charset=utf-8",
			data : JSON.stringify(userObject),
			success : function(result){
				console.log("result : " + result);
				
				if(result === "SUCCESS"){
					alert(result);
				}
			}
		});
	});
	
	// 5) 요청 URL에 쿼리 파라미터를 붙여서 전달하면 문자열 매개변수로 처리한다.
	registerBtn05.on("click", function(){
		var userId = $("#userId").val();
		var password = $("#password").val();
		
		var userObject = {
			userId : userId,
			password : password
		}
		$.ajax({
			url : "/ajax/register05?userId="+userId,
			type: "post",
			contentType : "application/json;charset=utf-8",
			data : JSON.stringify(userObject),
			succuss: function(result){
				console.log(result);
				
				if(result ==="SUCCESS"){
					alert(result);
				}
			}
		});
	});
	
	// 7) 객체 배열 타입의 JSON 요청 데이터를 자바빈즈 요소를 가진 리스트 컬렉션 매개변수에 @RequestBody를 지정하여 처리한다.
	registerBtn07.on("click", function(){
		var userObjectArray = [
			{userId : "name01", password : "pw1"},
			{userId : "name02", password : "pw2"}
		];
		$.ajax({
			type : "post",
			url : "/ajax/register07",
			contentType : "application/json;charset=utf-8",
			data : JSON.stringify(userObjectArray),
			success : function(result){
				console.log(result);
				if(result === "SUCCESS"){
					alert(result);
				}
			}
		});
	});
	// 8) 중첩된 객체 타입의 JSON 요청 데이터를 @RequestBody 어노테이션을 지정하여 중첩된 자바빈즈 매개변수로 처리한다.
	registerBtn08.on("click", function(){
		var userId = $("#userId").val();
		var password = $("#password").val();
		
		var userObject = {
			userId : userId,
			password : password,
			address : {
				postCode : "010908",
				location : "Daejeon"
			}
		};
		$.ajax({
			type : "post",
			url : "/ajax/register08",
			contentType : "application/json;charset=utf-8",
			data : JSON.stringify(userObject),
			success: function(result){
				console.log(result);
				if(result === "SUCCESS"){
					alert(result);
				}
			}
		});
	});
	// 9) 중첩된 객체 타입(컬렉션 List)의 JSON 요청 데이터를 @RequestBody 어노테이션을 지정하여 중첩된 자바빈즈 매개변수로 처리한다.
	registerBtn09.on("click",function(){
		var userId = $("#userId").val();
		var password = $("#password").val();
		
		var userObject = {
				userId : userId,
				password : password,
				cardList : [
					{no: "12345", validMonth : "20231018"},
					{no: "56789", validMonth : "20231019"}
					
				]
			};
		$.ajax({ 
			type : "post",
			url : "/ajax/register09",
			contentType : "application/json;charset=utf-8",
			data : JSON.stringify(userObject),
			success : function(result){
				console.log(result);
				if(result === "SUCCESS"){
					alert(result);
				}
			}
		});
	});
});

</script>
</html>








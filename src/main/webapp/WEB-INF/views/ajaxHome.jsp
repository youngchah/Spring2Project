<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AJAXHOME</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h3>Ajax Home</h3>
	<hr/>
	<br/>
	
	<form>
		boardNo : <input type="text" name="boardNo" id="boardNo"/><br/>
		title : <input type="text" name="title" id="title"/><br/>
		content : <textarea rows="3" cols="20" name="content" id="content"></textarea> <br/>
		writer : <input type="text" name="writer" id="writer"/><br/>
		<input type="button" id="btn" value="전송"/>
	</form>
	
	<div>
		<h4>Headers 매핑</h4>
		<button id="putBtn">MODIFY(PUT)</button>
		<button id="putHeaderBtn">MODIFY(PUT With Header)</button>
		<hr/>
		<h4>Content Type 매핑</h4>
		<button id="postBtn">Modify(POST)</button>
		<button id="postJsonBtn">Modify(PUT Json)</button>
		<button id="postXmlBtn">Modify(PUT Xml)</button>
		<hr/>
		<h4>Accept 매핑</h4>
		<button id="getBtn">READ</button>
		<button id="getJsonBtn">READ(Json)</button>
		<button id="getXmlBtn">READ(Xml)</button>
	</div>
</body>
<script type="text/javascript">
$(function(){
	//headers 매핑 영역
	var putBtn = $("#putBtn");
	var putHeaderBtn = $("#putHeaderBtn");
	
	//contentType 매핑 영역
	var postBtn = $("#postBtn");
	var postJsonBtn = $("#postJsonBtn");
	var postXmlBtn = $("#postXmlBtn");
	
	//Accept 매핑 영역
	var getBtn = $("#getBtn");
	var getJsonBtn = $("#getJsonBtn");
	var getXmlBtn = $("#getXmlBtn");
	
	putBtn.on("click", function(){
		var boardNo = $("#boardNo").val();
		var title = $("#title").val();
		var content = $("#content").val();
		var writer = $("#writer").val();
		
		var boardObject = {
				boardNo : boardNo,
				title : title,
				content : content,
				writer : writer
		};
		
		//비동기 통신 준비
		$.ajax({
			type: "put",
			url : "/board/" + boardNo,
			data : JSON.stringify(boardObject),
			//내가 보낸 데이터의 타입
			contentType : "application/json; charset=utf-8",
			//해당 요청이 성공해서 돌아왔을 때 응답으로 받을 콜백
			success : function(result){
				console.log("result : "+result);
				// '=='는 Equals Operator, '==='는 'Strict Equal Operator'라고 한다.
				// '==='는 값을 더 엄격하게 비교할 때 사용한다.
				if(result === "SUCCESS"){
					alert(result);
				}
			}
		});
	});
	
	putHeaderBtn.on("click", function(){
		var boardNo = $("#boardNo").val();
		var title = $("#title").val();
		var content = $("#content").val();
		var writer = $("#writer").val();
		
		var boardObject = {
				boardNo : boardNo,
				title : title,
				content : content,
				writer : writer
		}
		
		$.ajax({
			url : "/board/" + boardNo,
			type : "put",
			data : JSON.stringify(boardObject),
			//내가 명시 할 헤더 키 값 작성
			headers : {
				"X-HTTP-Method-Override" : "PUT"
			},
			contentType : "application/json; charset=utf-8",
			success : function(result){
				console.log("result : "+result);
				if(result === "SUCCESS"){
					alert(result);
				}
			}
		});
	});

///////////////////////////////////////////////////////////////////
	postBtn.on("click", function(){
		var boardNo = $("#boardNo").val();
		var title = $("#title").val();
		var content = $("#content").val();
		var writer = $("#writer").val();
		
		var boardObject = {
				boardNo : boardNo,
				title : title,
				content : content,
				writer : writer
		}
		
		$.ajax({
			type : "post",
			url : "/board/" + boardNo,
			data : JSON.stringify(boardObject),
			contentType : "application/json; charset=utf-8",
			success : function(result){
				console.log("result : "+result);
				if(result === "SUCCESS"){
					alert(result);
				}
			}
		});
	});
	
	postJsonBtn.on("click", function(){
		var boardNo = $("#boardNo").val();
		var title = $("#title").val();
		var content = $("#content").val();
		var writer = $("#writer").val();
		
		var boardObject = {
				boardNo : boardNo,
				title : title,
				content : content,
				writer : writer
		}
		
		$.ajax({
			url : "/board/" + boardNo,
			type : "put",
			data : JSON.stringify(boardObject),
			contentType : "application/json; charset=utf-8",
			success : function(result){
				console.log("result : "+result);
				if(result === "SUCCESS"){
					alert(result);
				}
			}
		});
	});
	
	postXmlBtn.on("click", function(){
		var boardNo = $("#boardNo").val();
		var title = $("#title").val();
		var content = $("#content").val();
		var writer = $("#writer").val();
		
		var xmlData = "";
		xmlData += "<Board>";
		xmlData += "<boardNo>" + boardNo + "</boardNo>";
		xmlData += "<title>" + title + "</title>";
		xmlData += "<content>" + content + "</content>";
		xmlData += "<writer>" + writer + "</writer>";
		xmlData += "</Board>";
		
		$.ajax({
			url : "/board/" + boardNo,
			type : "put",
			data : xmlData,
			contentType : "application/xml; charset=utf-8",
			success : function(result){
				console.log("result : "+result);
				if(result === "SUCCESS"){
					alert(result);
				}
			}
		});
	});

/////////////////////////////////////////////////////////////
	getBtn.on("click", function(){
		var boardNo = $("#boardNo").val();
		
		//GET방식 비동기 HTTP 요청 수행
		$.get("/board/"+boardNo, function(data){
			console.log("data : "+data);
			alert(JSON.stringify(data));
		});
		
	});
	
	getJsonBtn.on("click", function(){
		var boardNo = $("#boardNo").val();
		
		$.ajax({
			type : "get",
			url : "/board/" + boardNo,
			headers : {
				"Accept" : "application/json"
			},
			success : function(result){
				console.log("result : "+result);
				alert(JSON.stringify(result));
			}
		});
	});
	
	getXmlBtn.on("click", function(){
		var boardNo = $("#boardNo").val();
		
		$.ajax({
			type : "get",
			url : "/board/" + boardNo,
			headers : {
				"Accept" : "application/xml"
			},
			success : function(result){
				console.log("result : "+result);
				alert(xmlToString(result));
			}
		});
	});
});

function xmlToString(xmlData){
	var xmlString;
	
	//window.ActiveXObject는 ActiveObject를 지원하는 브라우저면 Object를 리턴하고
	//그렇지 않다면 null을 리턴합니다.
	if(window.ActiveXObject){
		xmlString = xmlData.xml;
	}else{
		xmlString = (new XMLSerializer()).serializeToString(xmlData);
	}
	return xmlString;
}

</script>
</html>
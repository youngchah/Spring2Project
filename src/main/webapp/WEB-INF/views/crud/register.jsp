<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<div class="container">
		<h3 class="mt-3">Board Form</h3>
		<div class="row">
			<div class="col-md-12">
				<div class="card">
					<div class="card-header">
						
					</div>
					<div class="card-body">
						<form method="post" action="/crud/board/register" id="boardForm">
							<c:if test="${status eq 'u' }">
								<input type="hidden" name="boardNo" value="${board.boardNo }" />
							</c:if>
							<table class="table table-bordered">
								<tr>
									<td>
										<input class="form-control" type="text" name="title"
										 value="${board.title }" id="title" placeholder="제목을 입력해주세요."/>
									</td>
								</tr>
								<tr>
									<td>
										<input class="form-control" type="text" name="writer"
										 id="writer" value="${board.writer }" placeholder="작성자를 입력해주세요."/>
									</td>
								</tr>
								<tr>
									<td>
										<textarea class="form-control" name="content" 
										id="content" cols="30" rows="5">${board.content }</textarea>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div class="card-footer">
						<c:set value="등록" var="btnText"/>
						<c:if test="${status eq 'u' }">
							<c:set value="수정" var="btnText"/>
						</c:if>
						<button type="button" class="btn btn-info" id="registerBtn">${btnText }</button>
						<c:if test="${status eq 'u' }">
							<button type="button" class="btn btn-danger" id="cancelBtn">취소</button>
						</c:if>
						<c:if test="${status ne 'u' }">
							<button type="button" class="btn btn-primary" id="listBtn">목록</button>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function(){
	var registerBtn = $("#registerBtn");
	var cancelBtn = $("#cancelBtn");
	var listBtn = $("#listBtn");
	var boardForm = $("#boardForm");
	
	// 등록 버튼
	registerBtn.on('click', function(){
		var title = $('#title').val();	// 제목을 가져온다.
		var writer = $('#writer').val();// 작성자를 가져온다.
		var content = $('#content').val();// 내용을 가져온다.
		
		if(title == null || title == ""){
			alert("제목을 입력해주세요!");
			return false;
		}
		
		if(writer == null || writer == ""){
			alert("작성자를 입력해주세요!");
			return false;
		}
		
		if(content == null || content == ""){
			alert("내용을 입력해주세요!");
			return false;
		}
		
		if ($(this).text() == "수정") {
			boardForm.attr("action", "/crud/board/modify");
		}
		
		boardForm.submit();	// submit 이벤트를 날려 서버로 데이터를 전송한다.
		
	});
	
	// 취소 버튼
	cancelBtn.on('click', function(){
		location.href = "/crud/board/read?boardNo=${board.boardNo}";
	});
	
	// 목록 버튼
	listBtn.on('click', function(){
		location.href="/crud/board/list";
	});
});
</script>
</html>















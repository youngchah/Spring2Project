<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		<h3 class="mt-3">Board Detail</h3>
		<div class="row">
			<div class="col-md-12">
				<div class="card">
					<div class="card-header">
					
					
					</div>
					<div class="card-body">
						<table class="table table-bordered">
							<tr>
								<td>${board.title }</td>
							</tr>
							<tr>
								<td>${board.writer } <fmt:formatDate value="${board.regDate }" pattern="yyyy-MM-dd hh:mm"/> </td>
							</tr>
							<tr>
								<td>${board.content }</td>
							</tr>
						</table>
					</div>
					<form action="/crud/board/remove" method="post" id="delForm">
						<input type="hidden" name="boardNo" value="${board.boardNo }"/>
					</form>
					<div class="card-footer">
						<button type="button" class="btn btn-warning" id="updateBtn">수정</button>
						<button type="button" class="btn btn-danger" id="deleteBtn">삭제</button>
						<button type="button" class="btn btn-primary" id="listBtn">목록</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function(){
	var updateBtn = $('#updateBtn');
	var deleteBtn = $('#deleteBtn');
	var listBtn = $('#listBtn');
	var delForm = $('#delForm');
	
	updateBtn.on('click', function(){
		delForm.attr("method", "get");
		delForm.attr("action", "/crud/board/modify");
		delForm.submit();
		
	});
	
	deleteBtn.on('click', function(){
		if(confirm("정말로 삭제하시겠습니까?")){
			delForm.submit();
		}
	});
	
	listBtn.on('click', function(){
		location.href="/crud/board/list";
	});
	
});
</script>
</html>

















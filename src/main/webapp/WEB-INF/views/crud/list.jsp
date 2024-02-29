<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<h3 class="mt-3">Board List</h3>
		<div class="row">
			<div class="col-md-12">
				<div class="card">
					<div class="card-header">
						<div class="row">
							<form action="/crud/board/search" method="post">
								<div class="col-md-6"></div>
								<div class="col-md-2"></div>
								<div class="col-md-3">
									<input class="form-control" type="text" name="title" placeholder="키워드를 입력해주세요."/>
								</div>
								<div class="col-md-1">
									<button type="submit" class="btn btn-secondary">검색</button>
								</div>
							</form>
						</div>
					</div>
					<div class="card-body">
						<table class="table table-bordered">
							<thead>
								<tr>
									<td width="6%">번호</td>
									<td>제목</td>
									<td width="10%">작성자</td>
									<td width="14%">작성일</td>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${empty boardList }">
										<tr>
											<td colspan="5">조회하신 게시글이 존재하지 않습니다.</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${boardList }" var="board">
											<tr>
												<td>${board.boardNo }</td>
												<td>
													<a href="/crud/board/read?boardNo=${board.boardNo }">
														${board.title }
													</a>
												</td>
												<td>${board.writer }</td>
												<td>
													<fmt:formatDate value="${board.regDate }" pattern="yyyy-MM-dd hh:mm"/>
												</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
					<div class="card-footer">
						<button type="button" class="btn btn-primary" id="registerBtn">등록</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function(){
	var registerBtn = $('#registerBtn');
	
	registerBtn.on('click', function(){
		location.href = "/crud/board/register";
	});
	
});
</script>
</html>







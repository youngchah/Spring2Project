<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section class="content-header">
	<c:set value="등록" var="name"/>
	<c:if test="${status eq 'u' }">
		<c:set value="수정" var="name"/>
	</c:if>
	
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1>공지사항 등록/수정</h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">DDIT HOME</a></li>
					<li class="breadcrumb-item active">공지사항 등록/수정</li>
				</ol>
			</div>
		</div>
	</div>
</section>

<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="card card-dark">
				<div class="card-header">
					<h3 class="card-title">공지사항 ${name }</h3>
					<div class="card-tools"></div>
				</div>
				<form method="post" action="/notice/insert.do" id="noticeForm">
					<c:if test="${status eq 'u' }">
						<input type="hidden" name="boNo" value="${notice.boNo }"/>
					</c:if>
					<div class="card-body">
						<div class="form-group">
							<label for="boTitle">제목을 입력해주세요</label> 
							<input type="text" id="boTitle" name="boTitle" value="${notice.boTitle }" class="form-control" placeholder="제목을 입력해주세요">
						</div>
						<div class="form-group">
							<label for="boContent">내용을 입력해주세요</label>
							<textarea id="boContent" name="boContent" class="form-control" rows="14">${notice.boContent }</textarea>
						</div>
<!-- 						<div class="form-group"> -->
<!-- 							<div class="custom-file"> -->
	
<!-- 								<input type="file" class="custom-file-input" id="customFile" -->
<!-- 									multiple="multiple"> <label class="custom-file-label" -->
<!-- 									for="customFile">파일을 선택해주세요</label> -->
<!-- 							</div> -->
<!-- 						</div> -->
					</div>
					
					<c:if test="${status eq 'u' }">
						<div class="card-footer bg-white">
							<ul class="mailbox-attachments d-flex align-items-stretch clearfix">
								<li><span class="mailbox-attachment-icon"><i
										class="far fa-file-pdf"></i></span>
		
									<div class="mailbox-attachment-info">
										<a href="#" class="mailbox-attachment-name"><i
											class="fas fa-paperclip"></i> Sep2014-report.pdf</a> <span
											class="mailbox-attachment-size clearfix mt-1"> <span>1,245
												KB</span> <a href="#" class="btn btn-default btn-sm float-right"><i
												class="fas fa-times"></i></a>
										</span>
									</div></li>
								<li><span class="mailbox-attachment-icon"><i
										class="far fa-file-word"></i></span>
		
									<div class="mailbox-attachment-info">
										<a href="#" class="mailbox-attachment-name"><i
											class="fas fa-paperclip"></i> App Description.docx</a> <span
											class="mailbox-attachment-size clearfix mt-1"> <span>1,245
												KB</span> <a href="#" class="btn btn-default btn-sm float-right"><i
												class="fas fa-times"></i></a>
										</span>
									</div></li>
								<li><span class="mailbox-attachment-icon has-img"><img
										src="${pageContext.request.contextPath }/resources/dist/img/photo1.png" alt="Attachment"></span>
		
									<div class="mailbox-attachment-info">
										<a href="#" class="mailbox-attachment-name"><i
											class="fas fa-camera"></i> photo1.png</a> <span
											class="mailbox-attachment-size clearfix mt-1"> <span>2.67
												MB</span> <a href="#" class="btn btn-default btn-sm float-right"><i
												class="fas fa-times"></i></a>
										</span>
									</div></li>
								<li><span class="mailbox-attachment-icon has-img"><img
										src="${pageContext.request.contextPath }/resources/dist/img/photo2.png" alt="Attachment"></span>
		
									<div class="mailbox-attachment-info">
										<a href="#" class="mailbox-attachment-name"><i
											class="fas fa-camera"></i> photo2.png</a> <span
											class="mailbox-attachment-size clearfix mt-1"> <span>1.9
												MB</span> <a href="#" class="btn btn-default btn-sm float-right"><i
												class="fas fa-times"></i></a>
										</span>
									</div></li>
							</ul>
						</div>
					</c:if>
					<div class="card-footer bg-white">
						<div class="row">
							<div class="col-12">
							
								<input type="button" id="addBtn" value="${name }" class="btn btn-dark float-right">
								<c:if test="${status ne 'u' }">
									<input type="button" id="listBtn" value="목록" class="btn btn-secondary float-right"> 
								</c:if>
								<c:if test="${status eq 'u' }">
									<input type="button" id="cancleBtn" value="취소" class="btn btn-danger float-right"> 
								</c:if>
								
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript">
$(function(){
	CKEDITOR.replace("boContent");
	
	var noticeForm = $("#noticeForm");
	var listBtn = $("#listBtn");
	var addBtn = $("#addBtn");
	var cancleBtn = $("#cancleBtn");
	
	listBtn.on('click', function(){
		location.href="/notice/list.do";
	});	
	
	addBtn.on('click', function(){
		var title = $("#boTitle").val();
		// 일반 textarea일때 데이터를 가져온다
// 		var content = $("#boContent").val();
		// ckedtior를 이용한 데이터를 가져오는 방법
		var content = CKEDITOR.instances.boContent.getData();
		
		if(title == null || title == ""){
			alert("제목을 입력해주세요!");
			$("#boTitle").focus();
			return false;
		}
		
		if(content == null || content == ""){
			alert("내용을 입력해주세요!");
			$("#boContent").focus();
			return false;
		}
		if($(this).val() == "수정"){
			noticeForm.attr("action", "/notice/update.do");
		}
		
		
		noticeForm.submit();
	});	
	
	cancleBtn.on('click', function(){
		location.href="/notice/detail.do?boNo=${notice.boNo}";
	});	
	
	
});
</script>







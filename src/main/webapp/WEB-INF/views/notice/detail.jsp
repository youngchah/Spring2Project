<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1>공지사항 상세보기</h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">DDIT HOME</a></li>
					<li class="breadcrumb-item active">공지사항 상세보기</li>
				</ol>
			</div>
		</div>
	</div>
</section>

<section class="content">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="card card-dark">
					<div class="card-header">
						<h3 class="card-title">${notice.boTitle }</h3>
						<div class="card-tools">${notice.boWriter } ${notice.boDate } ${notice.boHit }</div>
					</div>
					<form id="quickForm" novalidate="novalidate">
						<div class="card-body">${notice.boContent }</div>
						<div class="card-footer bg-white">
							<ul
								class="mailbox-attachments d-flex align-items-stretch clearfix">
								<li><span class="mailbox-attachment-icon"> <i
										class="far fa-file-pdf"></i>
								</span>
									<div class="mailbox-attachment-info">
										<a href="#" class="mailbox-attachment-name"> <i
											class="fas fa-paperclip"></i> 파일명
										</a> <span class="mailbox-attachment-size clearfix mt-1"> <span>파일
												Fancy사이즈 KB</span> <a href="다운로드 url"> <span
												class="btn btn-default btn-sm float-right"> <i
													class="fas fa-download"></i>
											</span>
										</a>
										</span>
									</div></li>
							</ul>
						</div>
						<div class="card-footer">
							<button type="button" class="btn btn-secondary" id="listBtn">목록</button>
							<button type="button" class="btn btn-dark" id="updateBtn">수정</button>
							<button type="button" class="btn btn-danger" id="deleteBtn">삭제</button>
						</div>
					</form>
					<form id="delForm" action="/notice/delete.do" method="post">
						<input type="hidden" name="boNo" value="${notice.boNo }"/>
					</form>
				</div>
			</div>
			<div class="col-md-6"></div>
		</div>
	</div>
</section>
<script>
$(function(){
	var delForm = $('#delForm');
	var listBtn = $('#listBtn');
	var updateBtn = $('#updateBtn');
	var deleteBtn = $('#deleteBtn');
	
	listBtn.on('click', function(){
		location.href = "/notice/list.do";
		
	});
	updateBtn.on('click', function(){
		delForm.attr('method', 'get');
		delForm.attr('action', '/notice/update.do');
		delForm.submit();
		
	});
	deleteBtn.on('click', function(){
		if(confirm('정말로 삭제하시겠습니까?')){
			delForm.submit();
		}
	});
	
});
</script>















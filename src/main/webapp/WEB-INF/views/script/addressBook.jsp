<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' integrity='sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN' crossorigin='anonymous'>
<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js'></script>
</head>
<body>
	<h1>주소록</h1>
	<hr/>
	<div class="row">
		<div class="col-md-12">
			<h4>MENU GROUP</h4>
			<button type="button" class="btn btn-info" onclick="javascript:location.href='/test/addAddress.do'">등록</button>
		</div>
	</div>
	<hr/>
	<div class="row">
		<div class="col-md-8">
			<div class="row" id="bookArea">
				
			</div>
		</div>
		<div class="col-md-4">
			<div class="card">
				<div class="card-header">즐겨찾기</div>
				<div class="card-body" id="bookmarkArea">
				
				</div>
				<div class="card-footer">
					
				</div>
			</div> 
		</div>
	</div>
	
	<div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">성공적으로 수행하였습니다</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	       	주소록에 성공적으로 등록하였습니다.
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
	      </div>
	    </div>
	  </div>
	</div>
	
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">
let bookArea = document.querySelector('#bookArea');
let bookmarkArea = document.getElementById('bookmarkArea');

var modal = new bootstrap.Modal(document.getElementById('myModal'));

let params = new URLSearchParams(window.location.search);
let status = params.get("status");

if(status != null && status == 'true'){
	modal.show();
}

viewRendering();

function viewRendering(){
	getList();
	getBookMarkList();
}

function getBookMarkList(){
	// 비동기 방식 리스트 
	// fetch (Promise)
	// jquery ajax
	// axios << 최신기술 굉장히 가볍고 좋음 (단점: 라이브러리 사용해야함)
	
	$.ajax({
		url: 'getBookMarkList.do',
		success: function(res){
			let html = '';
			
			res.map((v) => {
				html += '<span>['+v.gender+']' + v.name + ' ' + v.phone1 + '-' + v.phone2 + '-' + v.phone3 + "</span>";
				html += '<button type="button" class="btn btn-sm btn-danger" data-no=' + v.no + '>삭제</button><br/>';
			});
			
			bookmarkArea.innerHTML = html;
		}
	})
}


function getList(){
	$.ajax({
		url: 'getList.do',
		success: function(res){
			
			let html = '';
			
			for(let data of res){
			
				html += '<div class="col-md-4" id="area-'+data.no+'">';
				html += '	<div class="card">';
				html += '		<div class="card-header">';
				html += '			'+data.name+'';
				html += '		</div>';
				html += '		<div class="card-body">';
				html += '			<table class="table table-bordered">';
				html += '				<tr>';
				html += '					<td>이름</td>';
				html += '					<td>'+data.name+'</td>';
				html += '				</tr>';
				html += '				<tr>';
				html += '					<td>성별</td>';
				html += '					<td>'+data.gender+'</td>';
				html += '				</tr>';
				html += '				<tr>';
				html += '					<td>전화번호</td>';
				html += '					<td>'+data.phone1+'-'+data.phone2+'-'+data.phone3+'</td>';
				html += '				</tr>';
				html += '				<tr>';
				html += '					<td>직업</td>';
				html += '					<td>'+data.job+'</td>';
				html += '				</tr>';
				html += '			</table>';
				html += '		</div>';
				html += '		<div class="card-footer">';
				html += '			<button type="button" class="btn btn-primary" data-no="'+data.no+'">추가</button>';
				html += '		</div>';
				html += '	</div>';
				html += '</div>';
			}
			
			bookArea.innerHTML = html;
		}
	})
	
	
	
	
	
	bookArea.addEventListener('click', function(e) {
		if(e.target.tagName === 'BUTTON'){
			let no = e.target.dataset.no;
			let area = document.querySelector('#area-'+no);
			
			
			$.ajax({
				url: 'removeAddress.do',
				type: 'POST',
				data: JSON.stringify({
					id : no
				}),
				contentType: 'application/json; charset=UTF-8',
				success: function(res){
					if(res === 'success'){
						viewRendering();
					}
				}
			})
			
		}
	})
	
	bookmarkArea.addEventListener('click',function(e){
		if(e.target.tagName === 'BUTTON'){
			let no = e.target.dataset.no;
			let area = document.querySelectorAll('#bookmarkArea button[data-no]');
			
			// [...area] 하나씩 꺼냄 
			// area 유사배열로 되어있기 때문에 배열에서 사용하는 매서드를 사용할 수가 없음 
			// 그렇기에 유사배열을 배열로 바꿔주기 위함 
			// 전계연산자[...] 또 다른 방법으로는 Array.from()
			
			let areaArr = Array.from(area);
			console.log(areaArr);
			// areaArr.filter(~~)
			
			[...area].filter(function(btn){
				if(btn.getAttribute('data-no') == no){
					bookmarkArea.removeChild(btn);
				}
			})
			
			$.ajax({
				url: 'removeBookMark.do',
				type: 'POST',
				contentType: 'application/json; charset=UTF-8',
				data: JSON.stringify({
					id : no
				}),
				success: function(res){
					console.log(res);
					if(res === 'success'){
						viewRendering();
					}
				}
			})
		}
	})
}

</script>
</html>
					





<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ITEM REGISTER</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h2>MODIFY</h2>
	<form action="modify" method="post" enctype="multipart/form-data" id="item3">
		<input type="hidden" name="itemId" value="${item.itemId }"/>
		<table border="1">
			<tr>
				<td>상품명</td>
				<td><input type="text" name="itemName" id="itemName" value="${item.itemName }"/></td>
			</tr>
			<tr>
				<td>가격</td>
				<td><input type="text" name="price" id="price" value="${item.price }"/></td>
			</tr>
			<tr>
				<td>파일</td>
				<td>
					<input type="file" id="inputFile" />
					<div class="uploadedList"></div>
				</td>
			</tr>
			<tr>
				<td>개요</td>
				<td><textarea rows="10" cols="30" name="description">${item.description }</textarea> </td>
			</tr>
		</table>
		<div>
			<button type="button" id="listBtn" onclick="javascript:location.href='list'">List</button>
			<button type="submit" id="modifyBtn">Modify</button>
		</div>
	</form>
</body>
<script type="text/javascript">
	let inputFile = document.querySelector('#inputFile');
	let item3 = $('#item3');
	
	let itemId = ${item.itemId};
	
	// $.get $.post
	$.getJSON('getAttach/' + itemId, function(list){
		$(list).each(function(){
			let data = this;
			let str = '';
			
			if(checkImageType(data)){
				str += '<div>';
				str += '	<a href="displayFile?fileName='+ data +'">';
				str += '		<img src="displayFile?fileName='+ getThumbnailName(data) + '">';
				str += '	</a>';
				str += '	<span>X</span>';
				str += '</div>';
			}else{	//이미지가 아니면 그냥 원본명 표시
				str += '<div>';
				str += '	<a href="displayFile?fileName='+ data +'">';
				str += '		' + getOriginalName(data);
				str += '	</a>';
				str += '	<span>X</span>';
				str += '</div>';
			}
			
			$('.uploadedList').append(str);
		});
	});
	
	function getOriginalName(fileName) {
		if (checkImageType(fileName)) {
			return;
		}

		let idx = fileName.indexOf("_") + 1;
		// _ 
		return fileName.substr(idx);
		// _ => 이후의 문자열을 가져옴 
	}

	function getThumbnailName(fileName) {
		let front = fileName.substr(0, 12);
		let end = fileName.substr(12);

		return front + "s_" + end;
	}

	function checkImageType(fileName) {
		let pattern = /jpg|gif|png|jpeg/i;
		return fileName.match(pattern);
	}
</script>
</html>














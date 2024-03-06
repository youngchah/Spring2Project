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
	<form action="/item3/modify" method="post" enctype="multipart/form-data" id="item3">
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
			<button type="button" id="listBtn" onclick="javascript:location.href='/item3/list'">List</button>
			<button type="submit" id="modifyBtn">Modify</button>
		</div>
	</form>
</body>
<script type="text/javascript">
$(function(){
	var inputFile = $('#inputFile');	// input file element
	var item3 = $('#item3');	// form element
	
	var itemId = ${item.itemId};
	console.log("itemId : " + itemId);
	
	$.getJSON("/item3/getAttach/" + itemId, function(list){
		$(list).each(function(){
			console.log('data : ' + this);
			var data = this;
			var str = "";
			if(checkImageType(data)){	// 이미지면 이미지태그를 이용하여 출력
				str += "<div>";
				str += "	<a href='/item3/displayFile?fileName=" + data +"'>";
				str += "		<img src='/item3/displayFile?fileName=" + 
						getThumbnailName(data) + "'>";
				str += "	</a>";
				str += "	<span>X</span>";
				str += "</div>";
			}else{
				str += "<div>";
				str += "	<a href='/item3/displayFile?fileName" + data + "'>";
				str += "	" + getOriginalName(data);
				str += "	</a>";
				str += "	<span>X</span>";
				str += "</div>";
				
			}
			$(".uploadedList").append(str);
		});
	});
	
	item3.submit(function(){
		var that = $(this);	// 현재 누른 form태그
		var str = "";
		$(".uploadedList a").each(function(index){
			var value = $(this).attr("href");
			value = value.substr(28);	// '?fileName=' 다음에 나오는 값 
					
			str += "<input type='hidden' name='files["+index+"]' value='"+value+"'>";
		});
		
		console.log("str = " + str);
		that.append(str);
	});
	
	
	// Open파일을 변경했을 때 발동
	inputFile.on('change', function(event){
		console.log("change event...!");
		
		var files = event.target.files;
		var file = files[0];
		
		console.log(file);	// 파일 체크 
		var formData = new FormData();	// 필수
		formData.append("file", file);
		
		$.ajax({
			url : "/item3/uploadAjax",
			type : "post",
			data : formData,
			dataType: "text",
			processData: false,
			contentType: false,
			success: function(data){
				console.log(data);
				
				var str = "";
				if(checkImageType(data)){	// 이미지면 이미지태그를 이용하여 출력
					str += "<div>";
					str += "	<a href='/item3/displayFile?fileName=" + data +"'>";
					str += "		<img src='/item3/displayFile?fileName=" + 
							getThumbnailName(data) + "'>";
					str += "	</a>";
					str += "	<span>X</span>";
					str += "</div>";
				}else{
					str += "<div>";
					str += "	<a href='/item3/displayFile?fileName" + data + "'>";
					str += "	" + getOriginalName(data);
					str += "	</a>";
					str += "	<span>X</span>";
					str += "</div>";
					
				}
				$(".uploadedList").append(str);
			}
			
		});
		
	});
	
	// 업로드 한 파일의 'X'를 클릭했을 때 
	$(".uploadedList").on('click', "span", function(){
		$(this).parent('div').remove();
	});
	
	// 파일명 추출
	function getOriginalName(fileName) {
		if(checkImageType(fileName)){
			return;
		}
		var idx = fileName.indexOf("_") + 1;
		return fileName.substr(idx);
	}
	
	// 임시 파일로 썸네일 이미지 만들기 
	function getThumbnailName(fileName) {
		var front = fileName.substr(0, 12);	//	/2024/03/06/ 폴더
		var end = fileName.substr(12);	// 뒤 파일명
		
		console.log("front : " + front);
		console.log("end : " + end);
		
		return front + "s_" + end;
	}
	
	function checkImageType(fileName){
		var pattern = /jpg|gif|png|jpeg/i;
		return fileName.match(pattern); // 패턴과 일치하면 true(이미지구나?)
	}
	
});

</script>
</html>














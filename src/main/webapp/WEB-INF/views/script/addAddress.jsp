<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' integrity='sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN' crossorigin='anonymous'>
<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js'></script>
</head>
<body>
	<h1>주소록 등록</h1>
	<hr/>
	
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8">
			<table class="table table-bordered">
				<tr>
					<td>이름</td>
					<td>
						<input type="text" name="name" id="name"/>
					</td>
				</tr>
				<tr>
					<td>닉네임</td>
					<td>
						<input type="text" name="nickName" id="nickName"/>
					</td>
				</tr>
				<tr>
					<td>이메일</td>
					<td>
						<input type="text" name="email" id="email"/>
					</td>
				</tr>
				<tr>
					<td>성별</td>
					<td>
						<input type="radio" name="gender" value="male"/>남자&nbsp;
						<input type="radio" name="gender" value="female"/>여자
					</td>
				</tr>
				<tr>
					<td>전화번호</td>
					<td>
						<select name="phone1">
							<option value="010">010</option>
							<option value="011">011</option>
							<option value="016">016</option>
							<option value="017">017</option>
							<option value="019">019</option>
						</select> - 
						<input type="text" name="phone2" maxlength="4"/> -
						<input type="text" name="phone3" maxlength="4" /> 
					</td>
				</tr>
				<tr>
					<td>직업</td>
					<td>
						<input type="text" name="job" id="job"/>
					</td>
				</tr>
			</table>
		</div>
		<div class="col-md-2"></div>
	</div>
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-10">
			<button type="button" class="btn btn-primary" id="addBtn">등록</button>
			<button type="button" class="btn btn-warning" id="backBtn">취소</button>
		</div>
		<div class="col-md-2"></div> 
	</div>
</body>
<script type="text/javascript">
$(function(){
	let add = $('#addBtn');
	let back = $('#backBtn');
	
	add.on('click', function() {
		let name = $('#name').val();
		let nickName = $('#nickName').val();
		let email = $('#email').val();
		let gender = $('input[name="gender"]:checked').val();
		let phone1 = $('select[name="phone1"]').val();
		let phone2 = $('input[name="phone2"]').val();
		let phone3 = $('input[name="phone3"]').val();
		let job = $('#job').val();
		
		console.log(name, nickName, email, gender, phone1, phone2, phone3, job);
		
		$.ajax({
			url: 'addAddress.do',
			type: 'POST',
			contentType: 'application/json; charset=UTF-8',
			data: JSON.stringify({
				name : name,
				nickName : nickName,
				email : email,
				gender : gender,
				phone1 : phone1,
				phone2 : phone2,
				phone3 : phone3,
				job : job
			}),
			success: function(res){
				console.log(res);
				if(res === 'success'){
					goHome(true);
				}else{
					alert('데이터 추가 중 오류가 발생하였습니다');
				}
			}
		})
	
	});
	
	back.on('click',function() {
		goHome();
	});
	
	function goHome(flag){
		if(flag){
			location.href = '/test/addressBook.do?status=true';
		}else{
			location.href= '/test/addressBook.do';
		}
	}
});

</script>

</html>

















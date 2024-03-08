<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="">
	<div class="card card-outline card-primary">
		<div class="card-header text-center">
			<p class="h4">
				<b>아이디찾기</b>
			</p>
		</div>
		<div class="card-body">
			<p class="login-box-msg">아이디 찾기는 이메일, 이름을 입력하여 찾을 수 있습니다.</p>
			<form action="" method="post" >
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memEmail" placeholder="이메일을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memName" placeholder="이름을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<p id="idMsg">
						회원님의 아이디는 [<span style="font-size: 30px;" id="findIdResult"></span>] 입니다.
					</p>
				</div>
				<div class="row">
					<div class="col-12">
						<button type="button" class="btn btn-primary btn-block" id="findId">아이디찾기</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<br />
	<div class="card card-outline card-primary">
		<div class="card-header text-center">
			<p href="" class="h4">
				<b>비밀번호찾기</b>
			</p>
		</div>
		<div class="card-body">
			<p class="login-box-msg">비밀번호 찾기는 아이디, 이메일, 이름을 입력하여 찾을 수 있습니다.</p>
			<form action="" method="post">
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="pwMemId" placeholder="아이디를 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="pwMemEmail" placeholder="이메일을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="pwMemName" placeholder="이름을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<p>
						회원님의 비밀번호는 [<span style="font-size: 30px;" id="findPwResult"></span>] 입니다.
					</p>
				</div>
				<div class="row">
					<div class="col-12">
						<button type="button" class="btn btn-primary btn-block" id="findPw">비밀번호찾기</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<br/>
	<div class="card card-outline card-secondary">
		<div class="card-header text-center">
			<h4>MAIN MENU</h4>
			<button type="button" class="btn btn-secondary btn-block" onclick="javascript:location.href='/notice/login.do'">로그인</button>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	
	var findId = $('#findId');
	var findPw = $('#findPw');
	
	findId.on('click',function(){
		var memEmail = $('#memEmail').val();
		var memName = $('#memName').val();
		
		
		if(memEmail == null || memEmail == ""){
			alert('이메일을 입력해주세요.');
			$('#memEmail').focus();
			return false;
		}
		if(memName == null || memName == ""){
			alert('이름을 입력해주세요.');
			$('#memName').focus();
			return false;
		}
		
		var data = {
			memEmail : memEmail,
			memName : memName
		}
		
		$.ajax({
			type : 'post',
			url : '/notice/idForget.do',
			data : JSON.stringify(data),
			contentType : 'application/json;charset=utf-8',
			success : function(res){
				console.log(res);
				if (!res || res === "") {
					alert('이메일이나 이름을 확인해주세요!');					
		        } else {
		            $('#findIdResult').html(res);
		        }
			}
		});
		
	});
	
	findPw.on('click', function(){
		var memId = $('#pwMemId').val();
		var memEmail = $('#pwMemEmail').val();
		var memName = $('#pwMemName').val();
		
		
		if(memId == null || memId == ""){
			alert('아이디를 입력해주세요.');
			$('#pwMemId').focus();
			return false;
		}
		if(memEmail == null || memEmail == ""){
			alert('이메일을 입력해주세요.');
			$('#pwMemEmail').focus();
			return false;
		}
		if(memName == null || memName == ""){
			alert('이름을 입력해주세요.');
			$('#pwMemName').focus();
			return false;
		}
		
		var data = {
			memId : memId,
			memEmail : memEmail,
			memName : memName
		}
		
		$.ajax({
			type : 'post',
			url : '/notice/pwForget.do',
			data : JSON.stringify(data),
			contentType : 'application/json;charset=utf-8',
			success : function(res){
				console.log(res);
				if(!res || res === "" ){
					alert('값을 다시 확인해주세요!');
				}else{
					$('#findPwResult').html(res);
				}
				
			}
		});
		
	});
	
});


</script>



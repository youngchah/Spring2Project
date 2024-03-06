<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="register-box">
	<div class="card card-outline card-danger mt-4 mb-4">
		<div class="card-header text-center">
			<a href="" class="h1"><b>DDIT</b>BOARD</a>
		</div>
		<div class="card-body">
			<p class="login-box-msg">회원가입</p>

			<form action="/notice/signup.do" method="post" id="signupForm"
				enctype="multipart/form-data">
				<div class="input-group mb-3 text-center">
					<img class="profile-user-img img-fluid img-circle" id="profileImg"
						src="${pageContext.request.contextPath }/resources/dist/img/AdminLTELogo.png" alt="User profile picture"
						style="width: 150px;">
				</div>
				<div class="input-group mb-3">
					<label for="inputDescription">프로필 이미지</label>
				</div>
				<div class="input-group mb-3">
					<div class="custom-file">
						<input type="file" class="custom-file-input" name="imgFile"
							id="imgFile"> <label class="custom-file-label"
							for="imgFile">프로필 이미지를 선택해주세요</label>
					</div>
				</div>
				<div class="input-group mb-3">
					<label for="inputDescription">프로필 정보</label>
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memId" name="memId"
						placeholder="아이디를 입력해주세요"> <span
						class="input-group-append">
						<button type="button" class="btn btn-secondary btn-flat"
							id="idCheckBtn">중복확인</button>
					</span> <span class="error invalid-feedback" style="display: block;"></span>
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memPw" name="memPw"
						placeholder="비밀번호를 입력해주세요"> <span
						class="error invalid-feedback" style="display: block;"></span>
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memName" name="memName"
						placeholder="이름을 입력해주세요"> <span
						class="error invalid-feedback" style="display: block;"></span>
				</div>
				<div class="input-group mb-3">
					<div class="form-group clearfix">
						<div class="icheck-primary d-inline">
							<input type="radio" id="memGenderM" name="memGender" value="M"
								checked="checked"> <label for="memGenderM">남자&nbsp;</label>
						</div>
						<div class="icheck-primary d-inline">
							<input type="radio" id="memGenderF" name="memGender" value="F">
							<label for="memGenderF">여자 </label>
						</div>
					</div>
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memEmail"
						name="memEmail" placeholder="이메일을 입력해주세요">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memPhone"
						name="memPhone" placeholder="전화번호를 입력해주세요">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memPostCode"
						name="memPostCode"> <span class="input-group-append">
						<button type="button" class="btn btn-secondary btn-flat">우편번호
							찾기</button>
					</span>
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memAddress1"
						name="memAddress1" placeholder="주소를 입력해주세요">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memAddress2"
						name="memAddress2" placeholder="상세주소를 입력해주세요">
				</div>
				<div class="row">
					<div class="col-8">
						<div class="icheck-primary">
							<input type="checkbox" id="memAgree" name="memAgree" value="Y">
							<label for="memAgree">개인정보처리방침</label>
						</div>
					</div>
					<div class="col-4">
						<button type="button" class="btn btn-dark btn-block"
							id="signupBtn">가입하기</button>
					</div>
					<button type="button" class="btn btn-secondary btn-block mt-4"
						onclick="javascript:location.href='/notice/login.do'">뒤로가기</button>
				</div>
			</form>
		</div>
	</div>
</div>
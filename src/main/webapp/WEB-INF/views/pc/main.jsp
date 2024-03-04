<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Board List</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<style>
.pc td {
	height: 100px;
	padding-top: 24px;
}
</style>
<body>
	<!-- 
		피시방 사장이 되어, 손님을 받는다.
		1) 손님이 이용할 PC를 선택하고 이름, 이용시간을 설정 후 등록을 진행한다.
			> 이때, 이용 시간으로 설정되어 있는 시간만큼 타이머가 해당 PC자리에 설정된다.
		2) 이용중인 PC를 종료 버튼을 클릭 하면 타이머가 설정되어 있는 PC가 종료되고
			아래 매출현황에 이용시간 만큼의 매출이 기록된다.
		
		*** 손님을 받을 때마다 PC 이용 현황판은 이용 시간만큼 타이머가 동시에 동작해야한다.
	 -->
	<div class="container">
		<h3 class="mt-3">피시방 카운터</h3>
		<div class="row">
			<div class="col-md-4">
				<div class="card">
					<div class="card-header"></div>
					<div class="card-body">
						<table class="table table-bordered">
							<tr>
								<td width="30%" id="pcNo">PC번호</td>
								<td></td>
							</tr>
							<tr>
								<td id="pcName">이름</td>
								<td><input type="text" class="form-control" /></td>
							</tr>
							<tr>
								<td id="pcTime">시간</td>
								<td><input type="text" class="form-control" /></td>
							</tr>
						</table>
					</div>
					<div class="card-footer">
						<button type="button" class="btn btn-primary">등록</button>
					</div>
				</div>
			</div>
			<div class="col-md-8">
				<table class="table table-bordered pc">
					<tr align="center">
						<td>PC-1<br /></td>
						<td>PC-2<br /></td>
						<td>PC-3<br /></td>
						<td>PC-4<br /></td>
						<td>PC-5<br /></td>
						<td>PC-6<br /></td>
						<td>PC-7<br /></td>
						<td>PC-8<br /></td>
					</tr>
					<tr align="center">
						<td>PC-9<br /></td>
						<td>PC-10<br /></td>
						<td>PC-11<br /></td>
						<td>PC-12<br /></td>
						<td>PC-13<br /></td>
						<td>PC-14<br /></td>
						<td>PC-15<br /></td>
						<td>PC-16<br /></td>
					</tr>
					<tr align="center">
						<td>PC-17<br /></td>
						<td>PC-18<br /></td>
						<td>PC-19<br /></td>
						<td>PC-20<br /></td>
						<td>PC-21<br /></td>
						<td>PC-22<br /></td>
						<td>PC-23<br /></td>
						<td>PC-24<br /></td>
					</tr>
					<tr align="center">
						<td>PC-25<br /></td>
						<td>PC-26<br /></td>
						<td>PC-27<br /></td>
						<td>PC-28<br /></td>
						<td>PC-29<br /></td>
						<td>PC-30<br /></td>
						<td>PC-31<br /></td>
						<td>PC-32<br /></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="card">
					<div class="card-header">이용 안내</div>
					<div class="card-body">
						<ul>
							<li>1시간 이용 시 1,000원 입니다.</li>
							<li>이용 후, 의자를 꼭 넣어주세요.</li>
							<li>화장실은 입구 오른쪽 끝입니다.</li>
							<li>각 흡연실, 비흡연실 구역이 나뉘어져있습니다.</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-8">
				<div class="card">
					<div class="card-header">매출 현황</div>
					<div class="card-body">
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>번호</th>
									<th>PC 번호</th>
									<th>이용 시간</th>
									<th>금액</th>
								</tr>
							</thead>
							<tbody id="salesBody">
<!-- 								<tr> -->
<!-- 									<td>1</td> -->
<!-- 									<td>PC-21</td> -->
<!-- 									<td>2시간 30분</td> -->
<!-- 									<td>2,500원</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td>2</td> -->
<!-- 									<td>PC-9</td> -->
<!-- 									<td>3시간 30분</td> -->
<!-- 									<td>3,500원</td> -->
<!-- 								</tr> -->
							</tbody>
						</table>
					</div>
					<div class="card-footer">
						<h5>총 매출 : <span id="resultArea">0</span>원</h5>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
let timers = [];
let backupTimers = [];
let salesData = [];

function init() {
	backupTimers = JSON.parse(sessionStorage.getItem('backupTimers')) || [];
	salesData = JSON.parse(sessionStorage.getItem('salesData')) || [];
	
	// 브라우저마다 존재하는 세션을 가져오는 메서드
	// window 안에 존재하고 session은 브라우저가 세션이 만료되기전까지 유지가 됨 
	
	// backupTimers 값이 있을 경우
	
	if(backupTimers){
		// backupTimers 안에 있는 객체 속성들을 하나하나씩 변수에 저장
		for(let obj of backupTimers){
			let pcNo = obj.pcNo;
			let minutes = obj.minutes;
			let seconds = obj.seconds;
			
			addTimer(pcNo, minutes, seconds);
		}
	}
	
	if(salesData){
		let body = document.querySelector('#salesBody');
		
		for(let obj of salesData){
			let html = '';
			let tr = document.createElement('tr');
			html += '<td>'+obj.no+'</td>';
			html += '<td>'+obj.pcNo+'</td>';
			html += '<td>'+obj.hours+'시간'+obj.minutes+'분</td>';
			html += '<td>'+obj.salesPrice.toLocaleString()+'원</td>';
			tr.innerHTML = html;
			body.append(tr);
		}
		
		salesResult();
	}
}

window.onload = function(){
	init();
}



let pcTable = document.getElementsByClassName('pc')[0];
let btn = $('.btn-primary');

pcTable.addEventListener('click', function(e){
	if(e.target.tagName === 'TD'){
		let target = e.target.innerHTML.split('<br>');	// 더미데이터가 있을경우
		let selectedPC = target[0];
		
		e.target.setAttribute('id', selectedPC);
		
		let counterPC = document.querySelector('#pcNo').nextElementSibling;
		
		// nextElementSibling
		// text요소가 아닌 태그 요소들 중 선택한 요소의 형제 요소를 찾는다
		
		counterPC.textContent = selectedPC;
		
		return;
	}
	
	if(e.target.id.includes('btn_PC')){
		let pcNo = e.target.id.split('_')[1];
		// id 값을 btn_PC-1 => split ('_') => PC-1
		
		let selectedPC = document.querySelector('#'+pcNo);
		let endTime = selectedPC.querySelector('span').textContent.split(':')[0];
		
		selectedPC.innerHTML = '';
		selectedPC.textContent = pcNo;
		
		for(let obj of timers){
			if(obj.pcNo == pcNo){
				delete selectedPC.dataset.pcno;
				clearInterval(obj.timer);
				endTime = 1;
				
				let salesTime = obj.startTime - endTime;
				let hours = parseInt(salesTime / 60);
				let minutes = salesTime % 60;
				let salesPrice = 0;
				
				if(hours == 0){
					salesPrice += 1000;
				}else{
					salesPrice = hours * 1000;
				}
				
				if(minutes >= 30)
					salesPrice += 500;
				
				let body = document.querySelector('#salesBody');
				let lastChild = body.lastElementChild;
				let no;
				
				if(lastChild && lastChild.firstElementChild){
					no = parseInt(lastChild.firstElementChild.textContent) + 1;
				}else{
					no = 1;
				}
				
				minutes = minutes < 10 ? '0' : '' + minutes;
				
				let html = '';
				let tr = document.createElement('tr');
				html += '<td>'+no+'</td>';
				html += '<td>'+obj.pcNo+'</td>';
				html += '<td>'+hours + '시간' +minutes +'분</td>';
				html += '<td>'+salesPrice.toLocaleString()+'원</td>';
				
				tr.innerHTML = html;
				body.append(tr);
				
				let data = {
						no : no,
						pcNo : obj.pcNo,
						hours : hours,
						minutes : minutes,
						salesPrice : salesPrice
				};
				
				/*
					no = 매출현황 번호
				 	pcNo = PC키값
				 	hours, minutes = 시간 ... 등;
					salesPrice = 매출
				
				*/
				
				
// 				for (let i in backupTimers){
// 					if(backupTimers[i].pcNo == obj.pcNo){
// 						backupTimers.splice(i, 1);
// 						break;
// 					}
// 				}
				// backupTimers(저장된 인터벌) 안에 있는
				// pcNo(키값)이 obj.pcNo와 일치할 경우(timers)
				// 해당 인덱스값부터 1번째까지 지워줍니다
				
				
// 				for (let i in timers){
// 					if(timers[i].pcNo == obj.pcNo){
// 						timers.splice(i, 1);
// 						break;
// 					}
// 				}
				// 위와 동일
				
				let list = JSON.parse(sessionStorage.getItem('backupTimers'));
				// 해당 세션에 있는 backupTimers 값들을 전부 불러옴
				// 저장할때 JSON.stringify를 해줬기 때문에 다시 불러올때는
				// 자바스크립트 객체형식으로 변환시켜줌
				list = list.filter(item => item.pcNo !== obj.pcNo);
				sessionStorage.setItem('backupTimers', JSON.stringify(list));
				// 필터 안에 선언된 조건에 부합하는 요소들만 새롭게 배열로 변환시킴
				
				salesData.push(data);
				sessionStorage.setItem('salesData', JSON.stringify(salesData));
				
				salesResult();
				
				break;
			}
		}
		
		
	}
});

btn.on('click', function(e){
	let pcNo = $('#pcNo').next().text();
	let pcName = $('#pcName').next().find('input');
	let pcTime = $('#pcTime').next().find('input');
	
	// next() 다음 형제 요소를 찾고
	// find() 해당 요소안에 있는 input 태그를 찾는다
	
	if(pcNo == null || pcNo.trim() == ''){
		alert('이용할 자리를 선택해주세요');
		return false;
	}
	if(!pcName.val()){
		alert('성함을 입력해주세요');
		pcName.focus();
		return false;
	}
	if(!pcTime.val()){
		alert('사용하실 시간을 입력해주세요');
		pcTime.focus();
		return false;
	}
	if(isNaN(pcTime.val())){
		alert('숫자만 입력해주세요');
		pcTime.focus();
		return false;
	}
		
	let selectedPC = $('#'+pcNo);
	let pcValue = selectedPC.data('pcno');
	
	if(pcValue){
		alert('현재 타이머가 동작중입니다.');
		return false;
	}
	
	let html = pcNo + '<br/><b>' + pcName.val() + "</b><br/><span>" + pcTime.val() + "</span><br/>";
	
	let button = $('<button>');
	button.attr('id', 'btn_'+pcNo);
	button.html('종료');
	button.addClass('btn btn-sm btn-danger');
	
	selectedPC.html(html);
	selectedPC.append(button);
	
	pcName.val('');
	pcTime.val('');
	
	addTimer(pcNo);
	
});

function addTimer(pcNo, ...data){
	let pc;
	let minutes;
	let seconds;
	
	if(data.length <= 0){
		pc = document.querySelector('#'+pcNo);
		pc.dataset.pcno = pcNo;
		let time = pc.innerHTML.split('<br>')[2];
		
		time = time.replace('<span>', '').replace('</span>', '');
		
		minutes = parseInt(time);
		seconds = 0;
	}else{
		let elements = document.querySelectorAll('.pc tr td');
		
		for(let i = 0; i<elements.length; i++){
			if(elements[i].innerText === pcNo || elements[i].textContent === pcNo){
				// elements[i] PC1~PC32 이 텍스트들이 현재 내가 넘겨준 pcNo 키값이랑 동일할 경우
				// 해당 영역에 코드를 삽입한다
				
				pc = elements[i];
				pc.setAttribute('id', pcNo);
				pc.dataset.pcno = pcNo;
				
				let span = document.createElement('span');
				
				let button = document.createElement('button');
				button.classList.add('btn', 'btn-sm', 'btn-danger');
				button.innerHTML = '종료';
				button.setAttribute('id', 'btn_'+pcNo);
				
				pc.append(span);
				pc.append(button);
			}
		}
		
		minutes = data[0];
		seconds = data[1];
		
	}	
	
	const timer = setInterval(function(){
		if(minutes === 0 && seconds === 0){
			clearInterval(timer);
			delete pc.dataset.pcno;
		} else{
			if (seconds === 0){
				minutes--;
				seconds = 59;
			}else{
				seconds--;
			}
		}
		
		const currentTimer = {
				pcNo : pcNo,
				minutes : minutes,
				seconds : seconds
		};
		// 1초마다 자바스크립트 객체로 현재 시간과 pc번호 값을 저장함
		
		const exist = backupTimers.findIndex(timer => timer.pcNo === pcNo);
		// exist = backupTimers 안에 있는 pcNo가 현재 pcNo랑 같은 경우
		// 해당 배열안에 있는 인덱스값을 반환한다.
		// 못찾을 경우 -1을 반환
		
		if(exist !== -1){
			backupTimers[exist] = currentTimer;
			// 중복된 타이머를 방지하기 위해서
			// exist 인덱스값이 있을 경우 현재 타이머를 넣어줍니다
		}else{
			backupTimers.push(currentTimer);
			// 없을 경우 새롭게 push
		}
		
		sessionStorage.setItem('backupTimers', JSON.stringify(backupTimers));
		// Storage는 String 타입만 저장 가능
		// 그렇기 때문에 자바스크립트 객체를 넣게 되면
		// Object[object] 로 나오기 때문에
		// JSON 형식으로 (stringify) 바꿔준다
		
		
		let timeText = pc.querySelector('span');
		timeText.textContent = minutes + ':' + seconds;
		
	}, 1000);
	
	// 함수가 실행될 때마다 하나씩 메모리에 쌓입니다
	// timer = setInterval 과 같이 변수로 저장할 경우
	// timer는 interval의 키값을 가지고 이후 다른 영역에서 clearInterval 등 접근이 가능해짐
	// setInterval {... } 괄호 안에 있는 코드들이 '}, 1000);' 정한 시간 간격마다 실행됨 (1000 = 1초)
	
	let obj = {
			pcNo : pcNo,
			startTime : minutes,
			timer : timer
	};
	timers.push(obj);
}

function salesResult(){
	let salesBody = $('#salesBody tr');
	let resultPrice = 0;
	
	for(let tr of salesBody){
		let price = $(tr).children(':last').text();
		resultPrice += parseInt(price.replaceAll(',',''));
	}
	
	$('#resultArea').text(resultPrice.toLocaleString());
	// 시간 형식 또는 화폐 형식
	// Default 값이 화폐면 (0) 3개마다 콤마가 찍힘 
}



</script>
</html>
















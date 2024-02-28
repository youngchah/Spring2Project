<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>RESULT</title>
</head>
<body>
	<h3>RESULT PAGE</h3>
	<c:choose>
		<c:when test="${empty AllFormMember }">
		<h5>회원정보가 존재하지 않습니다.</h5>
		</c:when>
		<c:otherwise>
			<table border="1">
			<tr>
				<td>유저 ID</td>
				<td>${AllFormMember.userId}</td>
			</tr>
			<tr>
				<td>패스워드</td>
				<td>${AllFormMember.password }</td>
			</tr>
			<tr>
				<td>이름</td>
				<td>${AllFormMember.userName }</td>
			</tr>
			<tr>
				<td>E-Mail</td>
				<td>${AllFormMember.email }</td>
			</tr>
			<tr>
				<td>생년월일</td>
				<td>${AllFormMember.dateOfBirth }</td>
			</tr>
			<tr>
				<td>성별</td>
				<td>${AllFormMember.gender }</td>
			</tr>
			<tr>
				<td>개발자 여부</td>
				<td>${AllFormMember.developer }</td>
			</tr>
			<tr>
				<td>외국인 여부</td>
				<c:choose>
					<c:when test="${AllFormMember.foreigner==false }">
						<td>내국인<td>
					</c:when>
					<c:otherwise>
						<td>외국인<td>
					</c:otherwise>
				
				</c:choose>
				
			</tr>
			<tr>
				<td>국적</td>
				<td>${AllFormMember.nationality }</td>
			</tr>
			<tr>
				<td>소유차량</td>
					<td><c:forEach items="${AllFormMember.cars}" var="car" varStatus="loop">
							<c:choose>
								<c:when test="${car eq 'jeep'}">
                    				JEEP
               					</c:when>
								<c:when test="${car eq 'bmw'}">
                    				BMW
                				</c:when>
								<c:when test="${car eq 'volvo'}">
                   	 				VOLVO
                				</c:when>
								<c:otherwise>
                    				AUDI
                				</c:otherwise>
							</c:choose>
							<c:if test="${not loop.last}">, </c:if>
						</c:forEach></td>
				</tr>
			<tr>
					<td>취미</td>
					
					<td><c:forEach items="${AllFormMember.hobby}" var="hobby" varStatus="loop">
							<c:choose>
								<c:when test="${hobby eq 'sports'}">
                    				운동
               					</c:when>
								<c:when test="${hobby eq 'music'}">
                    				음악감상
                				</c:when>
								<c:when test="${hobby eq 'movie'}">
                   	 				영화시청
                				</c:when>
								<c:when test="${hobby eq 'study'}">
                   	 				공부
                				</c:when>
								<c:when test="${hobby eq 'book'}">
                   	 				독서
                				</c:when>
								<c:otherwise>
                    				개발
                				</c:otherwise>
							</c:choose>
							<c:if test="${not loop.last}">, </c:if>
						</c:forEach></td>
				
			</tr>
			<tr>
				<td>우편번호</td>
				<td>${AllFormMember.address.postCode }</td>
			</tr>
			<tr>
				<td>주소</td>
				<td>${AllFormMember.address.location }</td>
			</tr>
			<tr>
				<td>카드1 - 번호</td>
				<td>${AllFormMember.cardList[0].no }</td>
			</tr>
			<tr>
				<td>카드1 - 유효년월</td>
				<td>${AllFormMember.cardList[0].validMonth }</td>
			</tr>
			<tr>
				<td>카드2 - 번호</td>
				<td>${AllFormMember.cardList[1].no }</td>
			</tr>
			<tr>
				<td>카드2 - 유효년월</td>
				<td>${AllFormMember.cardList[1].validMonth }</td>
			</tr>
			<tr>
				<td>소개</td>
				<td>
				<textarea rows="30" cols="30" name="introduction" readonly="readonly">${AllFormMember.introduction }</textarea>
				</td>
			</tr>
			<tr>
				<td>개인정보 동의</td>
				<td> 
					동의함
				</td>
			</tr>
			
		</table>
			
			
			
			
		</c:otherwise>
	</c:choose>	
	
</body>
</html>
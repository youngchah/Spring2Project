<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HOME0401</title>
</head>
<body>
	<p>dateStyle 속성을 short로 지정하여 문자열을 Date객체로 변환한다.</p>
	<p>dateValue : ${dateValueShort }</p>
	<fmt:parseDate value="${dateValueShort }" type="date" dateStyle="short" var="dateShort"/>
	<p>date : ${dateShort }</p><br/>
	
	<p>dateStyle 속성을 medium으로 지정하여 문자열을 Date객체로 변환한다.</p>
	<p>dateValue : ${dateValueMedium }</p>
	<fmt:parseDate value="${dateValueMedium }" type="date" dateStyle="medium" var="dateMedium"/>
	<p>date : ${dateMedium }</p> <br/>
	
	<p>dateStyle 속성을 long으로 지정하여 문자열을 Date객체로 변환한다.</p>
	<p>dateValue : ${dateValueLong }</p>
	<fmt:parseDate value="${dateValueLong }" type="date" dateStyle="long" var="dateLong"/>
	<p>date : ${dateLong }</p> <br/>
	
	<p>dateStyle 속성을 full으로 지정하여 문자열을 Date객체로 변환한다.</p>
	<p>dateValue : ${dateValueFull }</p>
	<fmt:parseDate value="${dateValueFull }" type="date" dateStyle="full" var="dateFull"/>
	<p>date : ${dateFull }</p> <br/>
	
	
</body>
</html>
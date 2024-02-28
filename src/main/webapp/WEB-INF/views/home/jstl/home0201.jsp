<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HOME0201</title>
</head>
<body>
    <p>c:set을 활용</p>
    <c:set value="${member.userId}" var="id"/>
    <table border="1">
        <tr>
            <td>member.userId</td>
            <td>${id}</td>
        </tr>
    </table>
    
    <c:set var="memId" value="${member.userId}"/>
    <table border="1">
        <tr>
            <td>member.userId</td>
            <td>${memId}</td>
        </tr>
    </table>
</body>
</html>

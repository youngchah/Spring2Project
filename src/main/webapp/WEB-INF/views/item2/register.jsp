<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ITEM REGISTER</title>
</head>
<body>
	<h2>REGISTER</h2>
	<form action="/item2/register" method="post" enctype="multipart/form-data">
		<table border="1">
			<tr>
				<td>상품명</td>
				<td><input type="text" name="itemName" id="itemName"/></td>
			</tr>
			<tr>
				<td>가격</td>
				<td><input type="text" name="price" id="price"/></td>
			</tr>
			<tr>
				<td>파일1</td>
				<td><input type="file" name="pictures" id="picture1"/></td>
			</tr>
			<tr>
				<td>파일2</td>
				<td><input type="file" name="pictures" id="picture2"/></td>
			</tr>
			<tr>
				<td>개요</td>
				<td><textarea rows="10" cols="30" name="description"></textarea> </td>
			</tr>
		</table>
		<div>
			<button type="button" id="listBtn" onclick="javascript:location.href='/item2/list'">List</button>
			<button type="submit" id="registerBtn">Register</button>
		</div>
	</form>
</body>
</html>














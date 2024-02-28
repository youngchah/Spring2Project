<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
   <select class="form-control" id="selectImgType">
      <option value="all">전체</option>
      <option value="jpg">JPG</option>
      <option value="png">PNG</option>
      <option value="gif">GIF</option>
   </select>
   
   <hr/>
   <div class="row" id="imageArea">
      <c:choose>
         <c:when test="${empty imageFileList }">
            <h1>이미지 파일이 존재하지 않습니다.</h1>
         </c:when>
         <c:otherwise>
            <c:forEach items="${imageFileList }" var="imageFile">
               <div class="col-md-3">
                  <div class="card">
                     <div class="card-header">${imageFile }</div>
                     <div class="card-body">
                        <img src="${pageContext.request.contextPath }/resources/image/${imageFile}"
                           style="width:200px;" data-filename='${imageFile }'/>
                     </div>   
                     <div class="card-footer">
                        <a class="btn btn-primary" href="/test/download.do?fileName=${imageFile}" >다운로드</a>
                     </div>               
                  </div>
               </div>
            </c:forEach>
         </c:otherwise>
      </c:choose>
   </div>
</body>
<script type="text/javascript">
$(function(){
   var selectImgType = $("#selectImgType");
   
   selectImgType.on("change", function(){
      var selectedValue = $(this).val();
      
      console.log(selectedValue);
      
      var data = {
            type : selectedValue
      }
      
      $.ajax({
         url : "/test/changeImage.do",
         type : "post",
         data : JSON.stringify(data),
         contentType : "application/json; charset=utf-8",
         success : function(res){
            console.log("res : "+res);
            
            var html = "";
            //function(값, 인덱스)
            //res안에 들어있는 데이터 갯수만큼 반복
            res.map(function(v,i){
               html += "<div class='col-md-3'>";
               html += "   <div class='card'>";
               html += "      <div class='card-header'>"+v+"</div>";
               html += "      <div class='card-body'>";
               html += "         <img src='${pageContext.request.contextPath }/resources/image/"+v+"'";
               html += "            style='width:200px;' data-filename='"+v+"'/>";
               html += "      </div>   ";
               html += "      <div class='card-footer'>";
               html += "         <a class='btn btn-primary' href='/test/download.do?fileName="+v+"'>다운로드</a>";
               html += "      </div>";
               html += "   </div>";
               html += "</div>";
            });
            $("#imageArea").html(html);
         }
      });
   });
   
   $("#imageArea").on("click", "img",function(){
	   var fileName = $(this).data("filename");
	   location.href = "/test/thumbnail.do?fileName=" + fileName;
   });
   
});
   


</script>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
 <head>
  <title> excel上传测试 </title>
 
 </head>

 <body>
 excel上传测试 <br>
  <form method="post" action="excelToDB.do"  ENCTYPE='multipart/form-data'>
 
	  <input type="hidden" name="path" value="exceltest">
	  <input type="hidden" name="ruleMapStr" value="{logcode:'#1',logtext:'#2'}">
	  <input type="hidden" name="tableName" value="mi009">
	  <input type="file" name="excelfile" >
	  <input type="submit" value="tj">
  </form>

   批量导出excel文件测试 <br>
  <form method="post" action="dbToExcel.do">
 
	  <!-- <input type="hidden" name="path" value="exceltest"> -->
	  <input type="hidden" name="titles" value="logcode,logtext">
	  <input type="hidden" name="expotrTableName" value="mi009">
	  <input name="fileName" >
	  <input type="submit" value="download">
  </form>
 </body>
</html>
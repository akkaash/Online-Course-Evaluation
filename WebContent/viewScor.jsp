<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TA view here</title>
<link href="bootstrap.css" rel="stylesheet">
<link href="tabs.css" rel="stylesheet">
<script type="text/javascript" src="jquery-1.11.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.tab li').click(function(e) 
		    { 
		     alert($(this).find("span.t").text());
		     var activediv=$(this).find("span.t").text();
		     if(activediv=="Report"){
		     $('html,body').animate({
		         scrollTop: $(".viewReport").offset().top},
		         'slow');
		     }
		   });
});

</script>
</head>
<body>
<center>
<div id="tabs" style="margin: 30px">
<ul class="tab">
	<li><a href="#"><span class="t">Back</span></a></li>
	<li><a href="<%=request.getContextPath()%>/logout">Logout</a>
</ul>	

</div>
<div id="dispScor">
<table border="1">
    <tr>
    <th>HOMEWORK_ID</th>
    <th>ATTEMPT_ID</th>
    <th>POINTS_SCORED</th>
    </tr>
    
	<c:forEach var="hw" items="${attDetails}">
	<tr>
		<td><c:out value="${hw.getHwID()}"/></td>
		<td><c:out value="${hw.getAttemptID()}"/></td>
		<td><c:out value="${hw.getPointsScored()}"/></td>
	</tr>
	</c:forEach>
</table>	
</div>	
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>

</body>
</html>
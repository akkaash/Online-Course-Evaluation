<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="tabs.css" rel="stylesheet">
<link href="bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="jquery-1.11.1.min.js">
</script>


<script type="text/javascript">
$(document).ready(function(){
	$('ul.tab li').click(function(e) 
		    { 
		     alert($(this).find("span.t").text());
		     var activediv=$(this).find("span.t").text();
		     if(activediv=="Sign Up"){
		     $('html,body').animate({
		         scrollTop: $(".create").offset().top},
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
				<li><a href="#"><span class="t">View Scores</span></a></li>
				<li><a href="#"><span class="t">Attempt Homework</span></a></li>
				<li><a href="#"><span class="t">View Past Submission</span></a></li>
				<li><a href="#"><span class="t">View Notification</span></a></li>
				<li><a href="<%=request.getContextPath()%>/logout">Logout</a>
			</ul>

		</div>
	</center>

</body>
</html>
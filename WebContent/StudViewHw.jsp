
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
	$(".attHW").hide();
	$('ul.tab li').click(function(e) 
		    { 
		     /* alert($(this).find("span.t").text()); */
		     var activediv=$(this).find("span.t").text();
		     if(activediv=="Attempt HomeWork"){
		    	 $(".attHW").show();
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
				<li><a href="#"><span class="t">Attempt HomeWork</span></a></li>
				<li><a href="/DBMS/ViewPastSubmission"><span class="t">View Past Submission</span></a></li>
				<li><a href="#"><span class="t">View Notification</span></a></li>
				<li><a href="<%=request.getContextPath()%>/logout">Logout</a>
			</ul>

		</div>



		<div class="attHW" id="selectHW">
			<table border="1">
				<form name="selectHW" method="post" action="/DBMS/GenerateHomework">

					<tr>
						<td><label><h4>Select HW:</h4></td>
						<td><select id="select" name="hw"
							placeholder="Select HW from here" required
							title="Do specify your HW">
								<c:forEach var="item" items="${hw}">
									<option value="${item}">${item}</option>
								</c:forEach>
						</select> </label></td>
					</tr>

					<tr>

						<td align="center" colspan="2"><label><input
								type="submit" id="submit" name="viewHW" value="ATTEMPT"required"></label></td>

					</tr>

				</form>
			</table>
		</div>
</body>
</html>
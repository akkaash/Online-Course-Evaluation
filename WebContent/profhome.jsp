<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Professor Home Page</title>
<link href="bootstrap.css" rel="stylesheet">
<link href="tabs.css" rel="stylesheet">
<script type="text/javascript" src="jquery-1.11.1.min.js">
</script>
<script language="javascript">
   function displayObject(){
	   if(request.getParameterMap().containsKey("course"))
	   {
		   var javascriptVar="${course}";
     	alert(javascriptVar);
	   }
   }
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

<body onload="displayObject()">
	<center>
		<div id="tabs" style="margin: 30px">
			<ul class="tab">
				<li><a href="<%=request.getContextPath()%>/profhome.jsp"><span
						class="t">Home</span></a></li>
				<li><a href="<%=request.getContextPath()%>/selectcourseprof"><span
						class="t">Select Course</span></a></li>
				<li><a href="<%=request.getContextPath()%>/sendtextbook"><span
						class="t">Add Course</span></a></li>
				<li><a href="<%=request.getContextPath()%>/logout">Logout</a>
			</ul>

		</div>
		<%if(request.getParameterMap().containsKey("username"))
{
session.setAttribute("username", request.getParameter("username"));
}%>
		<%Boolean notFlag=(Boolean)session.getAttribute("notifyFlag");
if (notFlag) { %>
		<div>Condition is true!</div>
		<% } else { %>
		<div>Condition is false</div>
		<% } %>

		<a href="<%=request.getContextPath()%>/viewNotifications">${param.message }</a>
		<%-- <%session.setAttribute("hid", request.getParameter("hid")); %> --%>
		<h4>
			Welcome Professor,
			<%=session.getAttribute("username") %></h4>
		<div id="profoptions">
			<a href="profhome.jsp">Home</a> <br> <a
				href="<%=request.getContextPath()%>/selectcourseprof">Select
				Course</a> <br>
			<!--  <a href="addcourseprof.jsp">Add Course</a> -->
			<a href="<%=request.getContextPath()%>/sendtextbook">Add Course</a> <br>

			<br> <a href="<%=request.getContextPath()%>/logout">Logout</a> <br>

		</div>
	</center>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="bootstrap.css" rel="stylesheet">
<link href="tabs.css" rel="stylesheet">
<script type="text/javascript" src="jquery-1.11.1.min.js"></script>
</head>
<body>
	REPORT DETAILS DIV
	<center>
		<div id="vwHW">
			<table border="2" align="center">
				<thead>
					<tr>
						<c:forEach items="${rows[0]}" var="column">
							<td><c:out value="${column.key}"></c:out></td>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${rows}" var="columns">
						<tr>
							<c:forEach items="${columns}" var="column">
								<td><c:out value="${column.value}" /></td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<% if(session.getAttribute("role").toString().equalsIgnoreCase("professor")){ %>
		<a href="<%=request.getContextPath()%>/courseoptions.jsp">Back</a>
			<% } else{%>
		<a href="<%=request.getContextPath()%>/selectHW">Back</a>

		<% } %>
		
	
</body>
</html>
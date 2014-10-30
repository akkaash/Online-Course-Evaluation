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
</head>
<body>
<center>
<c:set var="attemptObj" value="${attemptObj }"></c:set>

<b>Points Scored: ${attemptObj.getPointsScored() }</b><br/>
<b>Submit Date: ${attemptObj.getSubmitDate() }</b>
	
	<table border="1" width="60%">
	<tr>
		<th>QID</th>
		<th>QUEST</th>
		
		
	</tr>
		<c:forEach var="questions" items="${questionList}">
			<tr>
			<td><c:out value="${questions.getQuestionID()}" /></td>
			<td><c:out value="${questions.getText()}" /></td>
			</tr>
			
			<tr>
				<c:set var="questID" value="${questions.getQuestionID()}"/>
				
				<c:set var="answerList" value="${questionAnswerMap[questID]}"/>
				<c:forEach var="ans" items="${answerList}">
					<tr>
						<td/>
						
						<c:set var="AnsID" value="${ans.getAnswerID()}"/>
						<%-- <td>${AnsID} </td> --%>
					</tr>
				
			</tr>
			<tr>
		
			<td/>
				<td>	
				<c:set var="AnswerFlagVal" value="${answerSelectMap[AnsID]}"/>
				<c:choose>
					<c:when test="${AnswerFlagVal ==1}">
						Marked:<c:out value="${ans.getAnswer()}" />
						
						<c:set var="flg" value="${ans.getFlag()}" />
						<c:choose>
						<c:when test="${flg ==0}">
							InCorrect 
							<c:choose>
								<c:when test="${dueDateFlag == 1 }">
									<td><c:out value="${questions.getDetailedExplanation()}" /></td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${ans.getShortExplanation()}" /></td>
								</c:otherwise>
							</c:choose>	
						</c:when>
						<c:otherwise>
						Correct
						
						</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
					<c:out value="${ans.getAnswer()}" />
					</c:otherwise>
				</c:choose>
				</td>
				
			</tr>
			
			</c:forEach>
			
			
			
		</c:forEach>
	
	
	
	
	
	
	
	
	 </table>


<a href="${backLink}">Back</a>

</center>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<title>Insert title here</title>
</head>
<body>


<center>
<c:set var="homework" value="${homework}"/>
<c:set var="chapter" value="${chapter}"/>
<table border="1">
<thead>
<tr>
<th>Homework</th>
<th>Chapter</th>
<th>Difficulty Level</th>
<th>Start Date</th>
<th>End Date</th>
<th>No of retries</th>
<th>No of questions</th>
<th>Points Correct</th>
<th>Points Incorrect</th>
<th>Score Selection</th>

</tr>
</thead>
<tbody>
<tr>
<td>
${homework.getHomework_id() }
</td>
<td>
${chapter.getChapterTitle() }
</td>

<td>
${homework.getDifficulty_level_start() } to ${homework.getDifficulty_level_end() }
</td>

<td>
${homework.getStart_date() }
</td>

<td>
${homework.getEnd_date() }
</td>

<td>
${homework.getNo_of_retries() }
</td>

<td>
${homework.getNumberOfQuestions() }
</td>

<td>
${homework.getPoints_correct() }
</td>

<td>
${homework.getPoints_incorrect() }
</td>


<td>
	<c:choose>
		<c:when test="${homework.getScore_selection() == 0 }">
			Latest Attempt
		</c:when>
		
		<c:when test="${homework.getScore_selection() == 1 }">
			Average Score
		</c:when>
		
		<c:when test="${homework.getScore_selection() == 2 }">
			Maximum Score
		</c:when>
	</c:choose>
</td>
</tr>
</tbody>
</table>

<table border="1">
<thead>
	<tr>
		<th>Question</th>
		<th>Answer</th>
	</tr>
</thead>
<tbody>
<c:forEach items="${questionList}" var="question">
<tr>
	
	<td > ${question.getText()}</td>
	
	
	<c:set var="questionID" value="${question.getQuestionID()}"> </c:set>
	
	<c:choose>
		<c:when test="${question.getFlag() == 0 }">
			<c:forEach items="${questionAnswerMap.get(questionID)}" var="answer">
				<td>${answer.getAnswer()}<br></td>
			</c:forEach>	
		</c:when>
		
		<c:when test="${question.getFlag() == 1 }">
			<tr>
			<td>For Above Parameterized Question<br></td>
			</tr>
			<c:set var="paramList" value="${questionParamMap.get(questionID)}"></c:set>
			
			</tr>
			<c:forEach items="${paramList}" var="params">
			<tr>
				<td>Parameters: ${params.getParameter()}<br></td>
				<c:set var="parameterID" value="${params.getParameterID() }"/>
				<c:set var="answerList" value="${paramAnswerMap.get(parameterID) }"/>
				
				<c:forEach items="${answerList}" var="answers">
					<td>Answer: ${answers.getAnswer() }<br></td>
				</c:forEach>
				</tr>
			</c:forEach>
			</tr>
		</c:when>
	</c:choose>
	
	
</tr>	
</c:forEach>
</tbody>
</table>
<br>

<% if(session.getAttribute("role").toString().equalsIgnoreCase("ta")){ %>
<a href="<%=request.getContextPath()%>/selectHW">Back</a>
<% } else{  %>
<a href="<%=request.getContextPath()%>/previewhomework">Back</a>

<% } %>


</center>
</body>
</html>
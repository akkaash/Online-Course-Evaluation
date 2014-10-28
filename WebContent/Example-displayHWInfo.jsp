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



<c:set var="homework" value="${homework}"/>
<c:set var="chapter" value="${chapter}"/>

<h1>Homework ${homework.getHomework_id() }</h1>
<h3>Chapter: ${chapter.getChapterTitle() }</h3>
<h3>Difficulty Level: ${homework.getDifficulty_level_start() } to ${homework.getDifficulty_level_end() }</h3>
<h3>Start Date: ${homework.getStart_date() }</h3>
<h3>End Date: ${homework.getEnd_date() }</h3>
<h3>No of retries: ${homework.getNo_of_retries() }</h3>
<h3>No of questions: ${homework.getNumberOfQuestions() }</h3>
<h3>Points Correct: ${homework.getPoints_correct() }</h3>
<h3>Points Incorrect: ${homework.getPoints_incorrect() }</h3>

<h3> Score Selection:
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
</h3>

<c:forEach items="${questionList}" var="question">
	Question: ${question.getText()}<br>
	
	<c:set var="questionID" value="${question.getQuestionID()}"> </c:set>
	
	<c:choose>
		<c:when test="${question.getFlag() == 0 }">
			<c:forEach items="${questionAnswerMap.get(questionID)}" var="answer">
				Answer:${answer.getAnswer()}<br>
			</c:forEach>	
		</c:when>
		
		<c:when test="${question.getFlag() == 1 }">
			Parameterized Question<br>
			<c:set var="paramList" value="${questionParamMap.get(questionID)}"></c:set>
			
			<c:forEach items="${paramList}" var="params">
				Parameters: ${params.getParameter()}<br>
				<c:set var="parameterID" value="${params.getParameterID() }"/>
				<c:set var="answerList" value="${paramAnswerMap.get(parameterID) }"/>
				
				<c:forEach items="${answerList}" var="answers">
					Answer: ${answers.getAnswer() }<br>
				</c:forEach>
			</c:forEach>
		</c:when>
	</c:choose>
	
	
	
</c:forEach>

<br>
<a href="<%=request.getContextPath()%>/previewhomework">Back</a>
</body>
</html>
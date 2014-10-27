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

<h1>HELLO</h1>

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

</body>
</html>
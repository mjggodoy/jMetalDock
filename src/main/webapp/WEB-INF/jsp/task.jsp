<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList"%>
<%
	ArrayList<String> numList = new ArrayList<String>();
	numList.add("one");
	numList.add("two");
	numList.add("three");
	request.setAttribute("numList", numList);
%>
<!DOCTYPE html>
<html>
	<c:set var="task" value='${it}' />
	<body>
	<%= "hello" %>
		<h1>HELLO XUXA!!!!</h1>
		<h2>TASK</h2>
		<c:out value="${task.id}"/><br>
		<c:out value="${task.hash}"/><br>
		<c:out value="${task.state}"/><br>
		<h3>Parameters</h3>
		<c:out value="${task.parameters.algorithm}"/><br>
		<c:out value="${task.parameters.evaluation}"/><br>
		<c:out value="${task.parameters.populationSize}"/><br>
		<c:out value="${task.parameters.run}"/><br>
		<c:out value="${task.parameters.objective}"/><br>
	</body>
</html>
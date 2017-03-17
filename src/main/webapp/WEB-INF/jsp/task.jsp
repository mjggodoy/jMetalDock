<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
<%@ include file="header.jsp"%>
</head>
	<c:set var="task" value='${it}' />
	<body>
	<div class="container">
		<h4>TASK<br></h4>
		
		<div class="table table-bordered">
			<table  class="table table-striped table table-bordered">
		
			<thead>
				<tr>
					<td><strong>ID:</strong></td>
					<td><c:out value="${task.id}"/></td>
				</tr>
				<tr>
					<td><strong>Start time: </strong></td>
					<td><c:out value=""/></td>
				</tr>
				<tr>
					<td><strong>End time: </strong></td>
					<td><c:out value=""/></td>
				</tr>
				<tr>
					<td><strong>Token: </strong></td>
					<td><c:out value="${task.token}"/></td>
				</tr>
				<tr>
					<td><strong>State: </strong></td>
					<td><c:out value="${task.state}"/></td>
				</tr>				
			</thead>
			</table>
		</div>
		<h4>PARAMETERS<br></h4>
	</div>
	
		<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	
	</body>
</html>


		<%-- <p>
			<strong>Algorithm: </strong><c:out value="${task.parameters.algorithm}"/><br>
			<strong>No. of runs: </strong><c:out value="${task.parameters.runs}"/><br>
			<strong>No. of evaluations: </strong><c:out value="${task.parameters.evaluations}"/><br>
			<strong>Population size: </strong><c:out value="${task.parameters.evaluations}"/><br>
			<strong>Objective option: </strong><c:out value="${task.parameters.objectiveOption}"/><br>
			<!-- Incluir una leyenda del significado de cada opción -->
		</p> --%>
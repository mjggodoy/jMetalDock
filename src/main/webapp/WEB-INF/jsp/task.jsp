<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<%@ include file="header.jsp"%>
</head>
<c:set var="task" value='${it}' />
<body>
	<div class="container">
		<h4>
			<strong>TASK:<br></strong>
		</h4>

		<div class="table">
			<table class="table table-striped table table-bordered">
					<tr>
						<td><strong>ID:</strong></td>
						<td><c:out value="${task.id}" /></td>
					</tr>
					<tr>
						<td><strong>Start time: </strong></td>
						<td><c:out value="" /></td>
					</tr>
					<tr>
						<td><strong>End time: </strong></td>
						<td><c:out value="" /></td>
					</tr>
					<tr>
						<td><strong>Token: </strong></td>
						<td><c:out value="${task.token}" /></td>
					</tr>
					<tr>
						<td><strong>State: </strong></td>
						<td><c:out value="${task.state}" /></td>
					</tr>
			</table>
		</div>
		<br/>

		<h4>
			<strong>PARAMETERS:</strong><br>
		</h4>

		<div class="table">
			<table class="table table-striped table table-bordered">
				<tr>
					<td><strong>Algorithm:</strong></td>
					<td><c:out value="${task.parameters.algorithm}" /></td>
				</tr>
				<tr>
					<td><strong>Number of runs:</strong></td>
					<td><c:out value="${task.parameters.runs}" /></td>
				</tr>
				<tr>
					<td><strong>Number of evaluations:</strong></td>
					<td><c:out value="${task.parameters.evaluations}" /></td>
				</tr>
				<tr>
					<td><strong>Number of individuals in population size:</strong></td>
					<td><c:out value="${task.parameters.populationSize}" /></td>
				</tr>
				<tr>
					<td><strong>Objective option:</strong></td>
					<td><c:out value="${task.parameters.objectiveOption}" /></td>
				</tr>
			</table>
		</div>	
	<br/>	
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>

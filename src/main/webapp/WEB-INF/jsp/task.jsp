<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<c:set var="task" value='${it}' />
	<body>
		<h3>TASK</h3>
		<p>
			<strong>ID: </strong><c:out value="${task.id}"/><br>
			<strong>Start time: </strong><br/>
			<strong>End time: </strong><br/>
			<strong>Token: </strong><c:out value="${task.token}"/><br>
			<strong>State: </strong><c:out value="${task.state}"/><br>
		</p>
		<hr>
		<h4>Parameters</h4>
		<p>
			<strong>Algorithm: </strong><c:out value="${task.parameters.algorithm}"/><br>
			<strong>No. of runs: </strong><c:out value="${task.parameters.runs}"/><br>
			<strong>No. of evaluations: </strong><c:out value="${task.parameters.evaluations}"/><br>
			<strong>Population size: </strong><c:out value="${task.parameters.evaluations}"/><br>
			<strong>Objective option: </strong><c:out value="${task.parameters.objectiveOption}"/><br>
			<!-- Incluir una leyenda del significado de cada opción -->
		</p>
	</body>
</html>
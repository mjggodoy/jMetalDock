<!DOCTYPE html>
<html>

<head>
<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>
</head>
<c:set var="task" value='${it}' />
<body>

	<jsp:include page="/WEB-INF/jsp/header.jsp">
		<jsp:param name="page" value="none" />
	</jsp:include>

	<div class="container">

		<div class="page-header">
			<h3>TASK <small><c:out value="${task.id}" /></small></h3>
		</div>

		<div>
			<table class="table table-striped table-condensed">
				<tr>
					<td class="col-md-6"><strong>State: </strong></td>
					<td class="col-md-6">
						<c:if test="${task.state=='finished'}">
							<span class="label label-success">Finished</span>
							<a href="<c:out value="${task.id}" />/result?token=<c:out value="${task.token}" />">Go to results</a>
						</c:if>
						<c:if test="${task.state=='error'}">
							<span class="label label-danger">Error</span>
						</c:if>
						<c:if test="${task.state=='running'}">
							<span class="label label-primary">Running</span>
						</c:if>
						<c:if test="${task.state=='queued'}">
							<span class="label label-default">Queued</span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td><strong>Start time:</strong></td>
					<td><c:out value="" /></td>
				</tr>
				<tr>
					<td><strong>End time:</strong></td>
					<td><c:out value="" /></td>
				</tr>
				<tr>
					<td><strong>Token:</strong></td>
					<td><c:out value="${task.token}" /></td>
				</tr>
			</table>
		</div>

		<div class="page-header">
			<h4>Parameters</h4>
		</div>

		<div>
			<table class="table table-striped table-condensed">
				<tr>
					<td class="col-md-6"><strong>Algorithm:</strong></td>
					<td class="col-md-6"><c:out value="${task.parameters.algorithm}" /></td>
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

	</div>
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>

</body>
</html>

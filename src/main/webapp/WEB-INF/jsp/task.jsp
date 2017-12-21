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
			<!--<h3>Task <small><c:out value="${task.id}" /></small></h3>-->
			<ol class="breadcrumb">
				<li><a href='<c:url value="/." />'>Home</a></li>
				<li><a href='<c:url value="/task.jsp" />'>Task</a></li>
				<li class="active">${task.id}</li>
			</ol>
			<h3>Task</h3>
		</div>

		<div>
			<table class="table table-striped table-condensed">
				<tr>
					<td class="col-md-6"><strong>State: </strong></td>
					<td class="col-md-6">
						<c:if test="${task.state=='finished'}">
							<span class="label label-success">Finished</span>
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
					<td>
						<c:out value="${task.startTime!=null ? task.startTime : '--'}" />
					</td>
				</tr>
				<tr>
					<td><strong>End time:</strong></td>
					<td>
						<c:out value="${task.endTime!=null ? task.endTime : '--'}" />
					</td>
				</tr>
				<tr>
					<td><strong>ID:</strong></td>
					<td><c:out value="${task.id}" /></td>
				</tr>
				<tr>
					<td><strong>Token:</strong></td>
					<td><c:out value="${task.token}" /></td>
				</tr>
				<tr>
					<td><strong>Email:</strong></td>
					<td>
						<c:out value="${task.email!=null ? task.email : '--'}" />
					</td>
				</tr>
				<c:if test="${task.state=='finished'}">
					<tr>
						<td style="line-height: 30px;"><strong>Actions:</strong></td>
						<td>
							<a class="btn btn-default btn-sm" role="button"
							   href='<c:url value="${task.id}/result?token=${task.token}" />' >Go to results
							</a>
							<a class="btn btn-default btn-sm" role="button"
							   href='<c:url value="${task.id}/dlg?token=${task.token}" />'>
								<span class="glyphicon glyphicon-download" aria-hidden="true"></span>
								Download Docking Log File
							</a>
						</td>
					</tr>
				</c:if>
			</table>
		</div>

		<h4>Parameters</h4>

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
					<td><strong>Population size:</strong></td>
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

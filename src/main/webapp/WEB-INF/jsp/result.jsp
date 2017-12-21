<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>
</head>
<c:set var="result" value='${it}' />
<body>

	<jsp:include page="/WEB-INF/jsp/header.jsp">
		<jsp:param name="page" value="none" />
	</jsp:include>

	<div class="container">

		<div class="page-header">
			<ol class="breadcrumb">
				<li><a href='<c:url value="/." />'>Home</a></li>
				<li><a href='<c:url value="/task.jsp" />'>Task</a></li>
				<li>
					<a href='<c:url value="../../${result.taskId}?token=${param.token}" />'>${result.taskId}</a>
				</li>
				<li>
					<a href='<c:url value="../../${result.taskId}/result?token=${param.token}" />'>Results</a>
				</li>
				<li class="active">Run ${result.run}</li>
			</ol>
			<h3>Run</h3>
		</div>

		<div>
			<table class="table table-striped table-condensed">
				<tr>
					<td class="col-md-6"><strong>Run:</strong></td>
					<td class="col-md-6">${result.run}</td>
				</tr>
				<tr>
					<td class="col-md-6"><strong>Objectives:</strong></td>
					<td class="col-md-6">
						<c:out value="${result.solutions[0].objectives[0]}" />
						<c:if test="${result.solutions[0].objectives[1]!=null}">
							, <c:out value="${result.solutions[0].objectives[1]}" />
						</c:if>
					</td>
				</tr>
			</table>
		</div>
	
		<div>
			<table  class="table table-striped table-condensed">
				<thead>
					<tr>

						<th>ID</th>
						<!--
						<th>Task ID</th>
						<th>Run</th>
						-->
						<th>
							<span class="hidden-xs">Final Binding Energy</span>
							<span class="visible-xs">E<sub>final</sub> (kcal/mol)</span>
						</th>
						<th>
							<span class="hidden-xs">Intermolecular Energy</span>
							<span class="visible-xs">E<sub>inter</sub> (kcal/mol)</span>
						</th>
						<th>
							<span class="hidden-xs">Intramolecular Energy</span>
							<span class="visible-xs">E<sub>intra</sub> (kcal/mol)</span>
						</th>
						<th>
							RMSD
							<span class="visible-xs"> (&Acirc;)</span>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${result.solutions}" var="solution">
						<tr>
							<td>
								<a href='<c:url value="../../${result.taskId}/result/${result.run}/${solution.id}?token=${param.token}" />'>
										${solution.id}
								</a>
							</td>
							<!--
							<td>${result.taskId}</td>
							<td>${result.run}</td>
							-->
							<td>
								<c:if test="${solution.finalBindingEnergy != null}">
									${solution.finalBindingEnergy}
									<span class="hidden-xs"> kcal/mol</span>
								</c:if>
							</td>
							<td>
								<c:if test="${solution.intermolecularEnergy != null}">
									${solution.intermolecularEnergy}
									<span class="hidden-xs"> kcal/mol</span>
								</c:if>
							</td>
							<td>
								<c:if test="${solution.intramolecularEnergy != null}">
									${solution.intramolecularEnergy}
									<span class="hidden-xs"> kcal/mol</span>
								</c:if>
							</td>
							<td>
								<c:if test="${solution.rmsd != null}">
									${solution.rmsd}
									<span class="hidden-xs"> &Acirc;</span>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	
		<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	
	</div>	
</body>
</html>
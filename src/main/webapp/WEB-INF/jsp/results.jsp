<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>
</head>
<c:set var="results" value='${it}' />
<c:set var="first" value='${results.resultList[0]}' />
<body>

	<jsp:include page="/WEB-INF/jsp/header.jsp">
		<jsp:param name="page" value="none" />
	</jsp:include>

	<div class="container">

		<ol class="breadcrumb">
			<li><a href='<c:url value="/." />'>Home</a></li>
			<li><a href='<c:url value="/task.jsp" />'>Task</a></li>
			<li>
				<a href='<c:url value="../${first.taskId}?token=${param.token}" />'>${first.taskId}</a>
			</li>
			<li class="active">Results</li>
		</ol>

		<div class="panel panel-default">
			<div class="panel-body">

				<div class="page-header">
					<h2>Results<!-- <small><c:out value="${first.taskId}" /></small>--></h2>
				</div>

				<div>
					<table class="table table-striped table-condensed">
						<tr>
							<td class="col-md-6"><strong>Objectives:</strong></td>
							<td class="col-md-6">
								<c:out value="${first.solutions[0].objectives[0]}" />
								<c:if test="${first.solutions[0].objectives[1]!=null}">
									<br/>
									<c:out value="${first.solutions[0].objectives[1]}" />
								</c:if>
							</td>
						</tr>
					</table>
				</div>

				<div style="margin-bottom: 20px;">
					<a href='<c:url value="../${first.taskId}/result/minimumEnergy?token=${param.token}" />'>Solution with the minimum final binding energy</a>
					<c:if test="${first.solutions[0].rmsd != null}">
						<br/>
						<a href='<c:url value="../${first.taskId}/result/minimumRMSDscore?token=${param.token}" />'>Solution with the minimum RMSD score</a>
					</c:if>
				</div>

				<div>
					<table  class="table table-striped table-condensed">
						<thead>
							<tr>
								<!--
								<th>Task ID</th>
								-->
								<th>Run</th>
								<th>ID</th>
								<th>
									<span class="hidden-xs">Final Binding Energy</span>
									<span class="visible-xs">
										E<sub>final</sub>
										<small>(kcal/mol)</small>
									</span>
								<th>
									<span class="hidden-xs">Intermolecular Energy</span>
									<span class="visible-xs">
										E<sub>intra</sub>
										<small>(kcal/mol)</small>
									</span>
								</th>
								<th>
									<span class="hidden-xs">Intramolecular Energy</span>
									<span class="visible-xs">
										E<sub>intra</sub>
										<small>(kcal/mol)</small>
									</span>
								</th>
								<th>
									RMSD
									<span class="visible-xs">
										<small>(&Acirc;)</small>
									</span>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${results.resultList}" var="result">
								<c:forEach items="${result.solutions}" var="solution">
									<tr>
										<!--
										<td>${result.id}</td>
										<td>${result.taskId}</td>
										-->
										<td>
											<a href='<c:url value="../${first.taskId}/result/${result.run}?token=${param.token}" />'>
													${result.run}
											</a>
										</td>
										<td>
											<a href='<c:url value="../${first.taskId}/result/${result.run}/${solution.id}?token=${param.token}" />'>
													${solution.id}
											</a>
										</td>
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
												<span class="hidden-xs"> &Aring;</span>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>

				</div>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>
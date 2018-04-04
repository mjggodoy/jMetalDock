<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>
<c:set var="result" value='${it}' />
<body>

	<jsp:include page="/WEB-INF/jsp/header.jsp">
		<jsp:param name="page" value="none" />
	</jsp:include>

	<div class="container">

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

		<div class="panel panel-default">
			<div class="panel-body">

				<div class="page-header">
					<h2>Run</h2>
				</div>

				<table class="table table-striped table-condensed">
					<tr>
						<td class="col-md-6"><strong>Run:</strong></td>
						<td class="col-md-6">${result.run}</td>
					</tr>
					<tr>
						<td class="col-md-6"><strong>Objectives:</strong></td>
						<td class="col-md-6">
							<c:out value="${result.solutions[0].objectives[0]}" />
							<br/>
							<c:if test="${result.solutions[0].objectives[1]!=null}">
								<c:out value="${result.solutions[0].objectives[1]}" />
							</c:if>
						</td>
					</tr>
				</table>

				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active">
						<a href="#table-tab-container" aria-controls="table-tab-container" role="tab" data-toggle="tab">Table</a>
					</li>
					<li role="presentation" class="">
						<a href="#graph-tab-container" aria-controls="graph-tab-container" role="tab" data-toggle="tab">Graph</a>
					</li>
				</ul>

				<!--
				<div id="tab" class="btn-group" data-toggle="buttons">
					<a href="#table-tab-container" class="btn btn-default active" data-toggle="tab">
						<input type="radio" />Table
					</a>
					<a href="#graph-tab-container" class="btn btn-default" data-toggle="tab">
						<input type="radio" />Graph
					</a>
				</div>
				-->

				<div class="tab-content">

					<div role="tabpanel" class="tab-pane active" id="table-tab-container">

						<br />

						<table  class="table table-striped table-condensed">
							<thead>
								<tr>

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
								<c:forEach items="${result.solutions}" var="solution">
									<tr>
										<td>
											<a href='<c:url value="../../${result.taskId}/result/${result.run}/${solution.id}?token=${param.token}" />'>
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
							</tbody>
						</table>
					</div>

					<div role="tabpanel" class="tab-pane" id="graph-tab-container">
						<div class="row">
							<div id="filter_div0" class="col-sm-6"></div>
							<div id="filter_div1" class="col-sm-6"></div>
							<div id="chart_div" class="col-sm-12" style="height: 600px;"></div>
						</div>
						<!--
						<div id="filter_div1"></div>
						<div id="chart_div" style="width: 100%; height: 600px;"></div>
						-->
					</div>

				</div>

			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>


<script type="text/javascript">

	$("a[href='#graph-tab-container']").on('shown.bs.tab', function(e) {
		google.charts.load('current', {'packages':['corechart', 'controls']});
		google.charts.setOnLoadCallback(initChart);
	});

	$(window).resize(function() {
		if(this.resizeTO) clearTimeout(this.resizeTO);
		this.resizeTO = setTimeout(function() {
			$(this).trigger('resizeEnd');
		}, 500);
	});

	$(window).on('resizeEnd', function() {
		drawChart();
	});

	var dashboard = undefined;
	var arrayData = undefined;

	function drawChart() {
		if (arrayData != undefined)	dashboard.draw(arrayData);
	}

	function initChart() {

		$.get('<c:url value="/rest/task/${result.taskId}/result/${result.run}?token=${param.token}" />', function( data ) {

			dashboard = new google.visualization.Dashboard(document.getElementById('graph-tab-container'));

			var input = [data.solutions[0].objectives];
			if (input[0][0]=="Total Binding Energy") {
				obj1 = "finalBindingEnergy";
				obj2 = "rmsd";
			} else {
				obj1 = "intermolecularEnergy";
				obj2 = "intramolecularEnergy";
			}
			for (var i in data.solutions) {
				var x = data.solutions[i][obj1];
				var y = data.solutions[i][obj2];
				input.push([x,y]);
			}

			arrayData = google.visualization.arrayToDataTable(input);
			var options = {
				title: 'Solutions',
				hAxis : {title: input[0][0]},
				vAxis : {title: input[0][1]},
				legend: 'none',
				pointSize: 5,
				colors: ['#EB6909']
			};
			var chart = new google.visualization.ChartWrapper({
				'chartType': 'ScatterChart',
				'containerId': 'chart_div',
				'options': options
			});

			var slider0 = new google.visualization.ControlWrapper({
				'controlType': 'NumberRangeFilter',
				'containerId': 'filter_div0',
				'options' : {
					'filterColumnIndex':0,
					'ui' : {
						'labelStacking': 'vertical',
						'cssClass': 'khaos-chart'
					}
				}
			});

			var slider1 = new google.visualization.ControlWrapper({
				'controlType': 'NumberRangeFilter',
				'containerId': 'filter_div1',
				'options' : {
					'filterColumnIndex':1,
					'ui' : {
						'labelStacking': 'vertical',
						'cssClass': 'khaos-chart'
				}
				}
			});

			// The select handler. Call the chart's getSelection() method
			function selectHandler() {
				var selectedItem = chart.getChart().getSelection()[0];

				if (selectedItem) {
					solution = data.solutions[selectedItem.row];
					var value = arrayData.getValue(selectedItem.row, selectedItem.column);
					window.location.href =
						'<c:url value="/rest/task/${result.taskId}/result/${result.id}/" />'
							+ solution.id + '?token=${param.token}';
				}
			}

			function onReady() {
				google.visualization.events.addListener(chart.getChart(), 'select', selectHandler);
			}

			google.visualization.events.addListener(chart, 'ready', onReady);
			dashboard.bind([slider0, slider1], chart);
			drawChart();
		});
	}

</script>

</html>
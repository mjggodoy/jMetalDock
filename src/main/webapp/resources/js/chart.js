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

	$.get(url, function( data ) {

		dashboard = new google.visualization.Dashboard(document.getElementById('graph-tab-container'));

		arrayData = formatResponse(data);

		var options = {
			title: 'Solutions',
			hAxis : {title: arrayData.getColumnLabel(0)},
			vAxis : {title: arrayData.getColumnLabel(1)},
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
			if (selectedItem) getSolution(data, selectedItem.row);
		}

		function onReady() {
			google.visualization.events.addListener(chart.getChart(), 'select', selectHandler);
		}

		google.visualization.events.addListener(chart, 'ready', onReady);
		dashboard.bind([slider0, slider1], chart);
		drawChart();
	});
}
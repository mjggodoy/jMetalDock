<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>
</head>

<c:set var="solution" value='${it}' />

<body>

	<jsp:include page="/WEB-INF/jsp/header.jsp">
		<jsp:param name="page" value="none" />
	</jsp:include>

	<div class="container">

		<ol class="breadcrumb">
			<li><a href='<c:url value="/." />'>Home</a></li>
			<li><a href='<c:url value="/task.jsp" />'>Task</a></li>
			<li>
				<a href='<c:url value="/rest/task/${solution.taskId}?token=${param.token}" />'>${solution.taskId}</a>
			</li>
			<li>
				<a href='<c:url value="/rest/task/${solution.taskId}/result?token=${param.token}" />'>Results</a>
			</li>
			<li>
				<a href='<c:url value="/rest/task/${solution.taskId}/result/${solution.run}?token=${param.token}" />'>
					Run ${solution.run}
				</a>
			</li>
			<li>
				<a href='<c:url value="/rest/task/${solution.taskId}/result/${solution.run}/${solution.id}?token=${param.token}" />'>
					Solution ${solution.id}
				</a>
			</li>
			<li class="active">Protein viewer</li>
		</ol>

		<div class="panel panel-default">
			<div class="panel-body">

				<div class="page-header">
					<h2>Protein viewer</h2>
				</div>

				<div id=inspector>
					<h4>Choose Style</h4>
					<ul>
						<li id=preset>Preset</li>
						<li id=cartoon>Cartoon</li>
						<li id=tube>Tube</li>
						<li id=lines>Lines</li>
						<li id=line-trace>Line Trace</li>
						<li id=sline>Smooth Line Trace</li>
						<li id=trace>Trace</li>
					</ul>
				</div>
				<div id=gl></div>
				<div id=status>Loading...</div>
			</div>
		</div>
		
	</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>

</body>

<script src='<c:url value="/vendors/pv/pv.min.js" />'></script>
<script>
	var structure;
	var viewer = pv.Viewer(
		document.getElementById('gl'),
		{
			quality : 'medium',
			width: 'auto',
			height : '600',
			antialias : true,
			outline : true
		}
	);

	function lines() {
		viewer.clear();
		viewer.lines('structure', structure);
	}
	function cartoon() {
		viewer.clear();
		viewer.cartoon('structure', structure, { color: color.ssSuccession() });
	}
	function sline() {
		viewer.clear();
		viewer.sline('structure', structure);
	}
	function tube() {
		viewer.clear();
		viewer.tube('structure', structure);
	}
	function trace() {
		viewer.clear();
		viewer.trace('structure', structure);
	}

	function preset() {
		$.get( '<c:url value="/rest/task/${solution.taskId}/result/${solution.run}/${solution.id}/ligand?token=${param.token}" />', function( data ) {
			viewer.clear();
			console.log(data);
			var ligandId = data;
			var ligand = structure.select({rnames : [ligandId]});
			viewer.ballsAndSticks('ligand', ligand);
			viewer.cartoon('protein', structure);
			viewer.centerOn(structure);
		});
	}

	function lineTrace() {
		$.get( '<c:url value="/rest/task/${solution.taskId}/result/${solution.run}/${solution.id}/ligand?token=${param.token}" />', function( data ) {
			console.log(data);
			var ligandId = data;
			var ligand = structure.select({rnames : [ligandId]});
			viewer.clear();
			viewer.lines('ligand', ligand);
			viewer.lineTrace('structure', structure);
			viewer.centerOn(structure);
		});
	}

	function load(pdb_id) {
		document.getElementById('status').innerHTML ='loading '+pdb_id;
		var xhr = new XMLHttpRequest();
		xhr.open('GET', '<c:url value="/viewer/pdbs/" />'+pdb_id+'.pdb');
		xhr.setRequestHeader('Content-type', 'application/x-pdb');
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				structure = pv.io.pdb(xhr.responseText);
				preset();
				viewer.centerOn(structure);
			}
			document.getElementById('status').innerHTML = '';
		}
		xhr.send();
	}

	function loadMacroandLigand(){
		var path  = '<c:url value="/rest/task/${solution.taskId}/result/${solution.run}/${solution.id}/pdbqtMacro?token=${param.token}" />';
		pv.io.fetchPdb(path, function(structureResponse) {
			structure = structureResponse;
			viewer.cartoon('protein', structure);
			preset();
			document.getElementById('status').innerHTML = '';
		});
	}

	function loadMacroandLigandLines(){
		var path  = '<c:url value="/rest/task/${solution.taskId}/result/${solution.run}/${solution.id}/pdbqtMacro?token=${param.token}" />';
		pv.io.fetchPdb(path, function(structureResponse) {
			structure = structureResponse;
			viewer.cartoon('protein', structure);
			lineTrace();
		});
	}

	function transferase() {
		loadMacroandLigand();
	}

	document.getElementById('cartoon').onclick = cartoon;
	document.getElementById('line-trace').onclick = lineTrace;
	document.getElementById('preset').onclick = preset;
	document.getElementById('lines').onclick = lines;
	document.getElementById('trace').onclick = trace;
	document.getElementById('sline').onclick = sline;
	document.getElementById('tube').onclick = tube;
	window.onresize = function(event) {
		viewer.fitParent();
	}
	document.addEventListener('DOMContentLoaded', transferase);
</script>
	
</html>
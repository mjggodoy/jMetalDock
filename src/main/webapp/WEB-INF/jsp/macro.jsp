<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>
<link href='<c:url value="/css/styleVisualization.css" />' rel="stylesheet">

</head>

<c:set var="task" value='${it}' />
<c:set var="macro" value='${it}' />


<body>

	<jsp:include page="/WEB-INF/jsp/header.jsp">
		<jsp:param name="page" value="none" />
	</jsp:include>

	<div class="container">
<br/>
<br/>

<div id=gl>
  </div>
  <div id=inspector>
    <h1>Choose Style</h1>
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
  <div id=status>initialising...</div>
		
</div>
</body>

	<script src='<c:url value="/viewer/js/pv.min.js" />'></script>
	<script>
    var viewer = pv.Viewer(document.getElementById('gl'), 
                           { quality : 'medium', width: 'auto', height : 'auto',
                             antialias : true, outline : true});
    var structure;
    function lines() {
      viewer.clear();
      viewer.lines('structure', structure);
    }
    function cartoon() {
      viewer.clear();
      viewer.cartoon('structure', structure, { color: color.ssSuccession() });
    }
    function lineTrace() {
      viewer.clear();
      viewer.lineTrace('structure', structure);
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
      viewer.clear();
      var ligand = structure.select({rnames : ['U0E']});
      viewer.ballsAndSticks('ligand', ligand);
      viewer.cartoon('protein', structure);
    }
    function load(pdb_id) {
      document.getElementById('status').innerHTML ='loading '+pdb_id;
      var xhr = new XMLHttpRequest();
      //xhr.open('GET', '<c:url value="/viewer/pdbs/" />' +pdb_id+'.pdb');
      xhr.open('GET', '<c:url value="/viewer/pdbs/" />' +pdb_id);
      //console.log("PUA PDB?");
      xhr.setRequestHeader('Content-type', 'application/x-pdb');
      xhr.onreadystatechange = function() {
      //console.log("PUA RESPUESTA!");
        if (xhr.readyState == 4) {
          structure = pv.io.pdb(xhr.responseText);
          preset();
          viewer.centerOn(structure);
        }
        document.getElementById('status').innerHTML = '';
      }
      xhr.send();
    }
    function transferase() {	 	
    //console.log("PUA LOAD");
      load('<c:out value="'${macro.macro}"/>);
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
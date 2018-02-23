<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>
<link href='<c:url value="/css/styleVisualization.css" />' rel="stylesheet">

</head>


<c:set var="solution" value='${it}' />



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

    /* function preset() {
    	    var ligand = data;

    	viewer.clear();
    	$.get( '<c:url value="/rest/task/${task.id}/ligand?token=${param.token}" />', function( data ) {
    		console.log(data);
    		var ligand = data;
    		console.log(ligand);
    		viewer.ballsAndSticks('ligand', ligand);
    		viewer.cartoon('protein', structure);
    		viewer.centerOn(structure);
    	});
    } */
    
     function load(pdb_id) {
        document.getElementById('status').innerHTML ='loading '+pdb_id;
        var xhr = new XMLHttpRequest();
        xhr.open('GET', '<c:url value="/viewer/pdbs/" />'+pdb_id+'.pdb');
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
    
   /*  function load2() {
    	
    	console.log("LOAD2");
        
    	document.getElementById('status').innerHTML ='loading ';
        var xhr = new XMLHttpRequest();
        var path  = '<c:url value="/rest/task/${task.id}/macro?token=${param.token}" />';
		xhr.open('GET', path);
	    //xhr.setRequestHeader('Content-type', 'application/x-pdb');
	    
	    xhr.onreadystatechange = function() {
	    	console.log("XHR=");
	    	console.log(xhr);
	          if (xhr.readyState == 4) {
	            structure = pv.io.pdb(xhr.responseText);
	            console.log("STRUCTURE = ");
	            console.log(structure);
	            //viewer.cartoon('protein', structure);
	            preset();
	            viewer.centerOn(structure);
	          }
	          document.getElementById('status').innerHTML = '';
	        }
	    xhr.send();
    } 
     */
    
     function loadMacroandLigand(){
        var path  = '<c:url value="/rest/task/${solution.taskId}/result/${solution.run}/${solution.id}/pdbqtMacro?token=${param.token}" />';
        //macro?token=${param.token}" />';
        pv.io.fetchPdb(path, function(structureResponse) {
            console.log("XUXA1");
        	structure = structureResponse;
        	viewer.cartoon('protein', structure);
        	console.log("STRUCTURE = ");
            console.log(structure);
        	preset();
      });
          
        
        //http://localhost:8080/docking-service/rest/task/633/result/1/2552/pdbqtMacro?token=q2cbohjj7gim7oh3sc6u71amu3
       console.log("END!")
        
     }
    
    
    function transferase() {
  	
    	loadMacroandLigand();
    	//load(pua2);
     
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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.2/css/bootstrap-select.min.css" />
<link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0-rc1/css/bootstrap.min.css" rel="stylesheet">

 <%@ include file="/WEB-INF/jsp/header.jsp"%>

</head>

<body>
	<div class="container">
		<div class="page-header"><h3><strong>NEW TASK:<br /></strong></h3></div>
		
			<form id="newTaskForm" name="newTaskForm" action="rest/task"
			method="post" , enctype="multipart/form-data">
			
   				 	<label for="exampleInputFile">File Input: </label>
   				 	<input type="file" id="exampleInputFile">
    				<p class="help-block">Please, select a .zip in which all your  macromolecule and ligand files are prepared (.pdbqt) 
					and also the output files from AutoGrid software (.map).</p>
					
			<div class="form-group">
						
				<label for="instance" class="control-label">Instance:<br/></label>
   					 <div>
						<input type="text" class="form-control input-restricted-width" name="instance" />
					 </div>
			</div>
			
			<div class="form-group">
				<label for="algorithm" class="control-label">Algorithm:*<br/> </label>
				<select class="selectpicker">
  					<optgroup label="MonoObjective">
   					<option>PSO</option>
    				<option>DE</option>
    				<option>ssGA</option>
    				<option>gGA</option>
 					</optgroup>
  					<optgroup label="MultiObjective">
   					<option>NSGAII</option>
    				<option>GDE3</option>
    				<option>MOEA/D</option>
    				<option>SMS-EMOA</option>
  					</optgroup>
				</select>
			</div>
			
			<div class="form-group">
		
			<label for="instance" class="control-label">Number of runs:<br/></label>
   			
   			<div class="input-group spinner">
    			<input type="text" class="form-control" value="42">
    				<div class="input-group-btn-vertical">
      				<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>
      				<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>
    			</div>
  			</div>
  			</div>
			</form>
			
			<br />
		
			
		
		
	
			
			
			<!-- <div class="col-xs-4">
				<label for="runs" class="control-label">Number of Runs:<br/></label> <input class="form-control"
					type="text" name="runs" />
			</div>
			<div class="col-xs-4">
				<label for="population_size" class="control-label">Population size:<br/></label> <input
					type="text" name="population_size" />
			</div>
			<div class="col-xs-4">
				<label for="evaluations" class="control-label">Evaluations:<br/></label> <input
					type="text" name="evaluations" />
			</div>
			<div class="col-xs-4">
				<label for="use_rmsd_as_obj" class="control-label">RMSD to optimize:<br/></label> <input
					type="text" name="use_rmsd_as_obj" />
			</div> -->
			


  


	</div>
	<br/>
		<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	

</body>



</html>

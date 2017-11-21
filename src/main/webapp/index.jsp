<!DOCTYPE html>
<!-- saved from url=(0057)https://v4-alpha.getbootstrap.com/examples/justified-nav/ -->

<html lang="en">

<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
</head>


<body>

	<div class="container">

		<h1 class="text-muted">jMetalDock</h1>

		<div class="container">
			<div class="navbar">
				<ul class="nav nav-justified">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="tutorial.jsp">Tutorials</a></li>
					<li><a href="benchmark.jsp">Benchmark</a></li>
					<li><a href="task.jsp">Task</a></li>
					<li><a href="references.jsp">Publications</a></li>
				</ul>
			</div>
		</div>

		<div class="container">		
			<div id="myCarousel" class="carousel slide" data-ride="carousel">
				<!-- Indicators -->
				<ol class="carousel-indicators">
					<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
					<li data-target="#myCarousel" data-slide-to="1"></li>
					<li data-target="#myCarousel" data-slide-to="2"></li>
				</ol>

				<!-- Wrapper for slides -->
				<div class="carousel-inner" role="listbox">
					<div class="item active">
						<img src="images/macro-ligand.png" alt="Chania" width="460" height="345">
						<div class="carousel-caption">
							<h3>jMetalDock</h3>
							<p class=text>This is a web-service that provides mono- and multi-objective approaches to solve the molecular docking problem.</p>
						</div>
					</div>
					<div class="item">
        				<img src="images/references.png" alt="Chania" width="460" height="345">
        					<div class="carousel-caption">
          					<h3>Objectives</h3>
          					<p class=text>Intermolecular, Intramolecular energies and RMSD scores can be optimized.</p>
        					</div>
      				</div>
      				<div class="item">
        				<img src="images/macrointeractions.png" alt="Chania" width="460" height="345">
        					<div class="carousel-caption">
          					<h3>Algorithms</h3>
          					<p class=text>Users can select the mono- and multi- metaheuristics to perform their experiments. </p>
        					</div>
      				</div>
				</div>
								
				<!-- Left and right controls -->
				<a class="left carousel-control" href="#myCarousel" role="button"
					data-slide="prev"> <span
					class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a> <a class="right carousel-control" href="#myCarousel" role="button"
					data-slide="next"> <span
					class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
		</div>


		<!--      <img src="images/references2.png" class="resize" class="center-block" alt="Image docking font">
 -->

		<!-- Example row of columns -->
		<div class="row">
			<div class="col-md-12">
				<h3>Overview</h3>
				<p class="h5">
				The ligand-protein docking has an important role in pharmacological industry to
				know the intricate aspect of intermolecular recognition between a ligand and receptor. 
				These computational approaches correspond to studies <em>in silico </em>, 
				which are a complement to the laboratory experiments in order to figure out 
				how a given drug interacts to a therapeutic target. 
				</p>
				<p class="h5">
				In previous studies, we have introduced several approaches based on the application of metaheuristics
				from a mono- or multiobjective approach. The performance of the algorithms has been very successful being better
				than the algorithms used in the state-of-the art as is shown in some publications <a href="#Camacho">[1]</a>,<a href="#Godoy">[2]</a>.</p>
				
				When you use this service, please cite the following references:
				
				<blockquote>
				<p class="h5" id="Camacho">Esteban López-Camacho and María Jesús García Godoy and José García-Nieto and Antonio J. Nebro and José Francisco Aldana Montes
				Solving molecular flexible docking problems with metaheuristics: A comparative study <em>Appl. Soft Comput.</em> 28: 379--393 (2015) 
				<a href="doi:10.1016/j.asoc.2014.10.049" target="_blank">doi:10.1016/j.asoc.2014.10.049</a></p>
				<p class="h5" id="Godoy">María Jesús García Godoy, Esteban López-Camacho, José García-Nieto, Antonio J. Nebro and José F. Aldana-Montes: 
				Solving molecular docking problems with multi-objective metaheuristics <em>Molecules</em> 20(6): 10154-10183
				<a href="http://www.mdpi.com/1420-3049/20/6/10154" target="_blank">doi:10.3390/molecules200610154</a></p>
				</blockquote>
				
				<p class="h5">
				<h3>The jMetalDock Web Server</h3>
				<p class="h5">
				jMetalDock is a web-service that provides mono- and multi-objectives approaches 
				applied to solve the problem of molecular
				docking. In case of resolving the docking problem from a
				mono-objective approach, there are several available algorithms
				such as gGA, ssGA, DE and PSO. The total free binding energy
				(measured in kcal/mol) is the objective to optimize. When the user
				tries to solve using a multi-objetive approach, there are more than
				one objective to optimize, which are the Intermolecular, Intramolecular energy and the RMSD score. 
				The first corresponds to the ligand-receptor energy, the second to the energy related 
				to the ligand's structure through the molecular docking simulation and the third to the distance 
				between the ligand and receptor.
				Algorithms like NSGA-II, GDE3, SMPSO,
				SMSEMOA and MOEA/D. The energy function used to evaluate all the
				solutions returned by the algorithms is the AutoDock 4.2 energy
				function. </p>
				<h3>Why jMetalDock Web Server?</h3>
				<p class="h5">Most of the proposed approaches that can be found in the
				literature only optimize an objective that is the final binding
				energy. jMetalDock allows users to execute one or more tasks in
				which the algorithm is selected, the algorithm parameters can be
				set up as well as the objectives to optimize. The algorithm
				parameters are the population (number of individuals which has a
				default, maximum and minimum values), the number of
				runs (default, maximum and minimum values) and the number of
				evaluations (default, minimum and maximum values). Further details
				are given in the Tutorial section by clicking the menu bar.</p>
	
			</div>
		</div>
		
		<a href="#" id="pop">
			<img  id= "serverDocking" class= "resize2" src="images/serverDocking.png" alt="jMetalDock_Server">
		</a>
		
		<!-- Creates the bootstrap modal where the image will appear -->
		<div class="modal fade" id="imagemodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Image of how jMetalDock works on server</h4>
					</div>
					<div class="modal-body">
						<figure>
						<img src="" id="imagepreview" class= "resize3">
						<figcaption class="textCaption">The execution parameters of a given experiment are configured using a docking parameter file
						containing the algorithm's settings. As metaheuristics are iterative algorithms that work with sets of tentative solutions 
						that are modified according to a number of operators (e.g. in the case of GAs the operators are selection, crossover and mutation), 
						whenever a new solution has to be evaluated, it is sent back to AutoDock 4.2 to apply its scoring function. 
						In the case of a multi-objective approach, two objectives can be minimized: the intermolecular and intramolecular energies 
						or the RMSD score and the intermolecular energy.</figcaption>
						</figure>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>

		<%@ include file="/WEB-INF/jsp/footer.jsp"%>


	</div>
	<!-- /container -->
</body>

<script src="resources/js/imageSpan.js"></script>


</html>
<!DOCTYPE html>
<!-- saved from url=(0057)https://v4-alpha.getbootstrap.com/examples/justified-nav/ -->
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="images/liferay.ico">

<title>jMetalDock: A web-service that provides single and
	multi-objetive approaches to molecular docking</title>

<!-- Bootstrap core CSS -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="resources/css/bootstrap.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


</head>


<body>

	<div class="container">

		<h3 class="text-muted">jMetalDock</h3>

		<div class="container">
			<div class="navbar">
				<ul class="nav nav-justified" id="myNav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#">Tutorials</a></li>
					<li><a href="#">Benchmark</a></li>
					<li><a href="#">Downloads</a></li>
					<li><a href="references.jsp">Publications</a></li>
				</ul>
			</div>
		</div>

		<div class="container">
			<br>
			<div id="myCarousel" class="carousel slide" data-ride="carousel">
				<!-- Indicators -->
				<ol class="carousel-indicators">
					<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
					<li data-target="#myCarousel" data-slide-to="1"></li>
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
          					<p class=text>Intermolecular, intramolecular energies and RMSD scores can be optimized.</p>
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
			<div class="col-lg-4">
				<h2>Welcome to jMetalDock Web Server</h2>
				<p>MetalDock provides a set of web-services that offers mono-
					and multi-objectives approaches applied to the problem of molecular
					docking. In case of resolving the docking problem from a
					mono-objective approach, there are several available algorithms
					such as gGA, ssGA, DE and PSO. The total free binding energy
					(measured in kcal/mol) is the objective to optimize. When the user
					tries to solve using a multi-objetive approach, there are more than
					one objectives to optimize. Algorithms like NSGA-II, GDE3, SMPSO,
					SMSEMOA and MOEA/D. The energy function used to evaluare all the
					solutions returned by the algorithms is the AutoDock 4.2 energy
					function</p>
				<h2>Why jMetalDock Web Server?</h2>
				<p>Most of the proposed approaches that can be found in the
					literature only optimize an objective that is the final binding
					energy. jMetalDock allows users to execute one or more tasks in
					which the algorithm is selected, the algorithm parameters have been
					set and the objectives to optimize selected. The algorithm
					parameters are the population (number of individuals which has a
					default values and a maximum and minimum values), the number of
					runs (default, maximum and minimun values) and the number of
					evaluations (default, minimum and maximum values). Further details
					are given above in the Tutorial section in the menu bar.</p>
			</div>
		</div>

		<!-- Site footer -->
		<footer class="footer">
	    <div class="container">
			<div class="panel panel-default panel-ecompass-footer">
				<div class="panel-body">
					<div class="footer-container">
						Copyright © 2017 <a href="http://www.uma.es/" target="_blank">University of Málaga</a>. All rights reserved. Powered by <a href="http://khaos.uma.es" target="_blank">Khaos Research</a>.
					</div>
				</div>
			</div>
		</div>
		</footer>

	</div>
	<!-- /container -->
</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<head>
  <title>jMetalDock: ----A web-service that provides single and multi-objective approaches to molecular docking</title>
  <meta name="description" content="This web service provides mono- and multi-objective approaches to solve the problem of molecular docking. 
  The molecular docking has as objetive to optimize the ligand's pose to the macromolecule (receptor) with the minimum binding energy. 
  The molecular docking problem can be regarded either as a mono-objective or multi-objective problem." />
  <meta name="keywords" content="molecular docking multiobjective approach intermolecular energy intramolecular energy RMSD" />
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=9" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <script type="text/javascript" src="js/jquery.min.js"></script>
  <script type="text/javascript" src="js/image_slide.js"></script>
</head>

<body>
  <div id="main">
     <div id="header">
	  <div id="banner">
	    <div id="welcome">
	      <h1>jMetalDock Web Service</h1> 
	    </div><!--close welcome-->
	    <div id="menubar">
          <ul id="menu">
            <li class="current"><a href="index.html">Home</a></li>
            <li><a href="file.html">Run</a></li>
            <li><a href="projects.html">Tutorial</a></li>
            <li><a href="projects.html">Benchmark</a></li>
            <li><a href="references.jsp">References</a></li>
          </ul>
        </div><!--close menubar-->	  
	  </div><!--close banner-->	
    </div><!--close header-->	
    
	<div id="site_content">		
      <div class="slideshow">  
		<ul class="slideshow">
          <li class="show"><img width="500" height="350" src="images/macro-ligand.png" alt="&quot;Enter your caption here&quot;" /></li>
          <li><img width="1100" height="600" src="images/macrointeractions.png" alt="&quot;Enter your caption here&quot;" /></li>
        </ul> 
      </div><!--close slideshow-->		
	  	 
	  <div id="content">
        <div class="content_item">
		  <h1>Welcome to jMetalDock Web Server</h1> 
		</div>	
          <p>jMetalDock provides a set of web-services that offers mono- and multi-objectives approaches applied to the problem of molecular docking. 
          In case of resolving the docking problem from a mono-objective approach, there are several available algorithms such as gGA, ssGA, DE and PSO. 
          The total free binding energy (measured in kcal/mol) is the objective to optimize. When the user tries to solve using a multi-objetive approach, there
          are more than one objectives to optimize. Algorithms like NSGA-II, GDE3, SMPSO, SMSEMOA and MOEA/D. The energy function used to evaluare all the solutions returned by
          the algorithms is the AutoDock 4.2 energy function.</p> 
          
         <div class="content_item">
		  <h2>Why jMetalDock Web Server?</h2> 
		  <p>Most of the proposed approaches that can be found in the literature only optimize an objective that is the final binding energy. 
		  jMetalDock allows users to execute one or more tasks in which the algorithm is selected, the algorithm parameters have been set and the objectives to optimize selected. The algorithm parameters 
		  are the population (number of individuals which has a default values and a maximum and minimum values), the number of runs (default, maximum and minimun values) and the number of evaluations 
		  (default, minimum and maximum values). Further details are given above in the Tutorial section in the menu bar.
		   </p>
		</div>    
		</div><!--close content_item-->
      </div><!--close content--> 
    </div><!--close main-->
    
    
   
  <div id="footer_container">
    <div id="footer">
	  <p>Copyright © 2017 Powered by <a href="http://khaos.uma.es">Khaos Research</a></p>
    </div><!--close footer-->  
  </div>
  
</body>
</html>

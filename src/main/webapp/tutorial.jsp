<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/header.jsp">
		<jsp:param name="page" value="tutorial" />
	</jsp:include>

	<div class="container">

		<div class="panel panel-default">
			<div class="panel-body">

				<div class="page-header">
					<h2>Tutorial</h2>
				</div>
				
				<h3>Input</h3>
				<p>
				The users uploads the set of resulting files from AutoDock. This set of files includes:
				</p>
				<ol>
				
				<li>The ligand in PDBQT format, which is widely 
				known by users that work on molecular docking simulations 
				(see <a href="http://autodock.scripps.edu/faqs-help/faq/what-is-the-format-of-a-pdbqt-file" target="_blank">PDBQT</a> for further details about PDBQT format)
				PDBQT ligand format</li>
			
				<li>The macromolecule in PDBQT format if the user wants to visualize the macromolecule-ligand 
				complex when the docking solution is obtained. The macro PDBQT file is larger (protein) than the ligand PDBQT file (chemical compound or peptide). 
				(Further specifications about PDBQT format are available at <a href="http://autodock.scripps.edu/faqs-help/faq/what-is-the-format-of-a-pdbqt-file" target="_blank">PDBQT</a>)
				</li>
				
				<li>If flexibility is applied to the side chains of the macromolecule's residues, the PDBQT file with the specified flexible residues must be submitted.
				</li>
				
				<li>The .map files calculated by AutoGrid4 must be included with the rest of files.
				</li>
				
				<li>A DPF file where the name of the macromolecule, ligand and flexible macromolecule must be included.
				</li>
				
				<li> All the files specified above must be compressed in a zip. Modifications depending on the algorithm and parameters chosen by the user will be lately included on the server. 
				</li>
				</ol>
				<p>If the user does not provide the .zip with all the files, in the instance file field of the Task form, a list of instances from the benchmark can be chosen.
				 These instances correspond to the benchmark used by Morris <em>et al.</em>to validate the AutoDock 4.2 energy function <a href="#Morris">[1]</a>. The instances are a set of HIV-proteases and inhibitors with different sizes (small, medium, large size and urea cycle inhibitors). 
				 </p>
				 <ol>
				 <li id="#Morris">Morris, Garrett M. and Huey, Ruth and Lindstrom, William and Sanner, Michel F. and Belew, Richard K. 
				 and Goodsell, David S. and Olson, Arthur J. AutoDock4 and AutoDockTools4: Automated docking with selective receptor flexibility<em>J. Comput. Chem.</em> 30(16): 2785--2791
								<a href="https://www.ncbi.nlm.nih.gov/pubmed/19399780" target="_blank">doi:10.1002/jcc.21256</a>
				</li>
				 </ol>
				<h3>How to Submit a Job</h3>
				
				<h3>Output</h3>
				

			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>

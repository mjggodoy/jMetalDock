<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>

</head>

<body>

	<jsp:include page="/WEB-INF/jsp/header.jsp">
		<jsp:param name="page" value="benchmark" />
	</jsp:include>

	<div class="container">

		<div class="panel panel-default">
			<div class="panel-body">
				<div class="page-header">

					<h2>
						<strong>Benchmark</strong>
					</h2>

				</div>

				<h4>Ligand-Receptor Docking: Instances Used in Mono-Objective
					and Multi-objective Approaches</h4>
				<p>
					To test the mono-objective and the multi-objective approaches, we
					have used a set of flexible protein-ligand complexes. These
					structures have been taken from the Protein Data Bank (PDB)
					database <a href="#PDB">[1]</a> and also used to test the AutoDock
					4.2 energy function <a href="#Morris">[2]</a>:
				</p>

				<div class="row">
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1A94.zip"
							onclick="ga('send','event','downloads','1A94.zip')">1A94</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1A9M.zip"
							onclick="ga('send','event','downloads','1A9M.zip')">1A9M</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1AAQ.zip"
							onclick="ga('send','event','downloads','1AAQ.zip')">1AAQ</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1AJV.zip"
							onclick="ga('send','event','downloads','1AJV.zip')">1AJV</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1AJX.zip"
							onclick="ga('send','event','downloads','1AJX.zip')">1AJX</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1B6J.zip"
							onclick="ga('send','event','downloads','1B6J.zip')">1B6J</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1B6K.zip"
							onclick="ga('send','event','downloads','1B6K.zip')">1B6K</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1B6L.zip"
							onclick="ga('send','event','downloads','1B6L.zip')">1B6L</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1B6M.zip"
							onclick="ga('send','event','downloads','1B6M.zip')">1B6M</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1B6P.zip"
							onclick="ga('send','event','downloads','1B6P.zip')">1B6P</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1BDL.zip"
							onclick="ga('send','event','downloads','1BDL.zip')">1BDL</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1BDQ.zip"
							onclick="ga('send','event','downloads','1BDQ.zip')">1BDQ</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1BDR.zip"
							onclick="ga('send','event','downloads','1BDR.zip')">1BDR</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1BV7.zip"
							onclick="ga('send','event','downloads','1BV7.zip')">1BV7</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1BV9.zip"
							onclick="ga('send','event','downloads','1BV9.zip')">1BV9</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1BWA.zip"
							onclick="ga('send','event','downloads','1BWA.zip')">1BWA</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1BWB.zip"
							onclick="ga('send','event','downloads','1BWB.zip')">1BWB</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1D4K.zip"
							onclick="ga('send','event','downloads','1D4K.zip')">1D4K</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1D4L.zip"
							onclick="ga('send','event','downloads','1D4L.zip')">1D4L</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1DMP.zip"
							onclick="ga('send','event','downloads','1DMP.zip')">1DMP</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1G2K.zip"
							onclick="ga('send','event','downloads','1G2K.zip')">1G2K</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1G35.zip"
							onclick="ga('send','event','downloads','1G35.zip')">1G35</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1GNM.zip"
							onclick="ga('send','event','downloads','1GNM.zip')">1GNM</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1GNN.zip"
							onclick="ga('send','event','downloads','1GNN.zip')">1GNN</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1GNO.zip"
							onclick="ga('send','event','downloads','1GNO.zip')">1GNO</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HBV.zip"
							onclick="ga('send','event','downloads','1HBV.zip')">1HBV</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HEF.zip"
							onclick="ga('send','event','downloads','1HEF.zip')">1HEF</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HEG.zip"
							onclick="ga('send','event','downloads','1HEG.zip')">1HEG</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HIH.zip"
							onclick="ga('send','event','downloads','1HIH.zip')">1HIH</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HIV.zip"
							onclick="ga('send','event','downloads','1HIV.zip')">1HIV</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HOS.zip"
							onclick="ga('send','event','downloads','1HOS.zip')">1HOS</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HPO.zip"
							onclick="ga('send','event','downloads','1HPO.zip')">1HPO</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HPS.zip"
							onclick="ga('send','event','downloads','1HPS.zip')">1HPS</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HPV.zip"
							onclick="ga('send','event','downloads','1HPV.zip')">1HPV</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HPX.zip"
							onclick="ga('send','event','downloads','1HPX.zip')">1HPX</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HSG.zip"
							onclick="ga('send','event','downloads','1HSG.zip')">1HSG</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HTE.zip"
							onclick="ga('send','event','downloads','1HTE.zip')">1HTE</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HTF.zip"
							onclick="ga('send','event','downloads','1HTF.zip')">1HTF</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HTG.zip"
							onclick="ga('send','event','downloads','1HTG.zip')">1HTG</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HVH.zip"
							onclick="ga('send','event','downloads','1HVH.zip')">1HVH</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HVI.zip"
							onclick="ga('send','event','downloads','1HVI.zip')">1HVI</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HVJ.zip"
							onclick="ga('send','event','downloads','1HVJ.zip')">1HVJ</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HVK.zip"
							onclick="ga('send','event','downloads','1HVK.zip')">1HVK</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HVL.zip"
							onclick="ga('send','event','downloads','1HVL.zip')">1HVL</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HVS.zip"
							onclick="ga('send','event','downloads','1HVS.zip')">1HVS</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HWR.zip"
							onclick="ga('send','event','downloads','1HWR.zip')">1HWR</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1HXW.zip"
							onclick="ga('send','event','downloads','1HXW.zip')">1HXW</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1IZH.zip"
							onclick="ga('send','event','downloads','1IZH.zip')">1IZH</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1IZI.zip"
							onclick="ga('send','event','downloads','1IZI.zip')">1IZI</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1JLD.zip"
							onclick="ga('send','event','downloads','1JLD.zip')">1JLD</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1K6C.zip"
							onclick="ga('send','event','downloads','1K6C.zip')">1K6C</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1K6P.zip"
							onclick="ga('send','event','downloads','1K6P.zip')">1K6P</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1K6T.zip"
							onclick="ga('send','event','downloads','1K6T.zip')">1K6T</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1K6V.zip"
							onclick="ga('send','event','downloads','1K6V.zip')">1K6V</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1KZK.zip"
							onclick="ga('send','event','downloads','1KZK.zip')">1KZK</a>
					</div>
					<div class="col-xs-2">
						<a
							href="Dhttp://khaos.uma.es/autodockjmetal/ownloadFromFtp?file=1MES.zip"
							onclick="ga('send','event','downloads','1MES.zip')">1MES</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1MEU.zip"
							onclick="ga('send','event','downloads','1MEU.zip')">1MEU</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1MTR.zip"
							onclick="ga('send','event','downloads','1MTR.zip')">1MTR</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1MUI.zip"
							onclick="ga('send','event','downloads','1MUI.zip')">1MUI</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1ODY.zip"
							onclick="ga('send','event','downloads','1ODY.zip')">1ODY</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1PRO.zip"
							onclick="ga('send','event','downloads','1PRO.zip')">1PRO</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1QBR.zip"
							onclick="ga('send','event','downloads','1QBR.zip')">1QBR</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1QBT.zip"
							onclick="ga('send','event','downloads','1QBT.zip')">1QBT</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1QBU.zip"
							onclick="ga('send','event','downloads','1QBU.zip')">1QBU</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1SBG.zip"
							onclick="ga('send','event','downloads','1SBG.zip')">1SBG</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1TCX.zip"
							onclick="ga('send','event','downloads','1TCX.zip')">1TCX</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1VB9.zip"
							onclick="ga('send','event','downloads','1VB9.zip')">1VB9</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1VIJ.zip"
							onclick="ga('send','event','downloads','1VIJ.zip')">1VIJ</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1VIK.zip"
							onclick="ga('send','event','downloads','1VIK.zip')">1VIK</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1Z1H.zip"
							onclick="ga('send','event','downloads','1Z1H.zip')">1Z1H</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1Z1R.zip"
							onclick="ga('send','event','downloads','1Z1R.zip')">1Z1R</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=2BPV.zip"
							onclick="ga('send','event','downloads','2BPV.zip')">2BPV</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=2BPX.zip"
							onclick="ga('send','event','downloads','2BPX.zip')">2BPX</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=2UPJ.zip"
							onclick="ga('send','event','downloads','2UPJ.zip')">2UPJ</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=3AID.zip"
							onclick="ga('send','event','downloads','3AID.zip')">3AID</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=3TLH.zip"
							onclick="ga('send','event','downloads','3TLH.zip')">3TLH</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=4HVP.zip"
							onclick="ga('send','event','downloads','4HVP.zip')">4HVP</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=4PHV.zip"
							onclick="ga('send','event','downloads','4PHV.zip')">4PHV</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=5HVP.zip"
							onclick="ga('send','event','downloads','5HVP.zip')">5HVP</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=7HVP.zip"
							onclick="ga('send','event','downloads','7HVP.zip')">7HVP</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=7UPJ.zip"
							onclick="ga('send','event','downloads','7UPJ.zip')">7UPJ</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=8HVP.zip"
							onclick="ga('send','event','downloads','8HVP.zip')">8HVP</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=9HVP.zip"
							onclick="ga('send','event','downloads','9HVP.zip')">9HVP</a>
					</div>
				</div>

				<br />
				<p>
					Users can also download all the instances in just one compressed
					file: <img src="resources/img/zip_gold.png" alt="zip"
						style="width: 120px;" /> <a
						href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=all.zip"
						style="margin-left: 25px;"></a>
				</p>
				<div>

					<br />
				</div>

				<p style="text-align: justify;">
					Instances used in the Drug Discovery section of <a
						href="http://www.mdpi.com/1420-3049/20/6/10154" target="_blank">
						Solving molecular docking problems with multi-objective
						metaheuristics</a>:
				</p>

				<div class="row">
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=1M17.zip">1M17</a>
					</div>
					<div class="col-xs-2">
						<a
							href="http://khaos.uma.es/autodockjmetal/DownloadFromFtp?file=YY92.zip"
							onclick="ga('send','event','downloads','YY92.zip')">YY92</a>
					</div>
				</div>

				<br /> <strong>References:</strong>

				<ol class="h5">
					<li id="Morris"><cite>Morris GM, Huey R, Lindstrom W,
							et al.: AutoDock4 and AutoDockTools4: Automated Docking with
							Selective Receptor Flexibility<br /> Algorithms for Computational
							Journal of computational chemistry. 2009;30(16):. 2785-2791<a
							href="http://dx.doi.org/10.1002/jcc.21256" 
							target="_blank">doi:10.1002/jcc.21256</a></cite>
					</li>
					<li id="PDB">Helen M. Berman, John Westbrook, Zukang Feng,
						Gary Gilliland et al.: The Protein Data Bank <br /> Nucleic Acids
						Res Journal 2000; 28: 235--242<cite> <a
							href="http://dx.doi.org/10.1007/978-1-4939-7000-1_26"
							target="_blank">doi:10.1007/978-1-4939-7000-1_26</a></cite>
					</li>
				</ol>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>

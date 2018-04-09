
(function ($) { 
	
	$('#algorithm').on('change',function(){
		if (
			$(this).val()==="PSO" ||
			$(this).val()==="DE" ||
			$(this).val()==="ssGA" ||
			$(this).val()==="gGA")
		{
			$("#monoobjective").show();
			$("#multiobjective").hide();
		} else if(
			$(this).val()==="NSGAII" ||
			$(this).val()==="ssNSGAII" ||
			$(this).val()==="GDE3" ||
			$(this).val()==="MOEAD" ||
			$(this).val()==="SMPSO" ||
			$(this).val()==="OMOPSO" ||
			$(this).val()==="SMPSOD" ||
			$(this).val()==="SMPSOhv" ||
			$(this).val()==="SMPSO_COS" ||
			$(this).val()==="SMSEMOA")
		{
			$("#multiobjective").show();
			$("#monoobjective").hide();
		} else{
			$("#multiobjective").hide();
			$("#monoobjective").hide();
		}
	});

    $('#algorithm').change();

	   $('#optInput').on('change',function(){
		   
		   if($(this).val()==="file"){
			   
	        	$("#inputFile").show();
	        	$("#inputInstance").hide();
	        	
		   }else if( $(this).val()==="instance") {   
			   
	        	$("#inputInstance").show();		   
	        	$("#inputFile").hide();
		   }
		   
	   });

    $('#optInput').change();

})(jQuery); 

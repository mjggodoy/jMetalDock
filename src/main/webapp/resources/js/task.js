
(function ($) { 
	
	   $('#algorithm').on('change',function(){
	        if( $(this).val()==="PSO" || $(this).val()==="DE" || $(this).val()==="ssGA" || $(this).val()==="gGA"){
	        	
	        	$("#monoobjective").show();
	        	$("#multiobjective").hide();

	       
	        }else if($(this).val()==="NSGAII" || $(this).val()==="GDE3" || $(this).val()==="MOEAD" || $(this).val()==="SMSEMOA"){
	        	
	        	$("#multiobjective").show();
	        	$("#monoobjective").hide();

	        	
	        }else{
	        	
	        	$("#objectives").hide()
	        
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
	   
	   
	
	/*$("input[name='demo1']").TouchSpin({
        min: 1,
        max: 100,
        step: 0.1,
        decimals: 2,
        boostat: 5,
        maxboostedstep: 10,
        postfix: '%'
    });*/
		
	/*
       $('.spinner input').keydown(function(e){
    	
    	var inputElem = $(this);
    	console.log(inputElem);

    	
		if($(inputElem).val() > $(inputElem).data("max-value") ){
			e.preventDefault();
			return false;
        
		}
		
		if($(inputElem).val() < $(inputElem).data("min-value") ){
	        e.preventDefault();
	        return false;
		}    
    });
    

	$('.spinner .btn:first-of-type').on("wheel", function() {
		
		var inputElem = $(this).parent().parent().children('input');
		inputElem.TouchSpin();
    	console.log(inputElem);

    	
		if($(inputElem).val() == $(inputElem).data("max-value") ){
			return false;
		}else{		
			$(inputElem).val( parseInt($(inputElem).val(), 10) + 1);			
		}
	}); 
		
	$('.spinner .btn:last-of-type').on('click', function() {
		
		var inputElem = $(this).parent().parent().children('input');

		if($(inputElem).val() == $(inputElem).data("min-value") ){
			return false;
		}else{
			
			$(inputElem).val( parseInt($(inputElem).val(), 10) - 1);	
		}	
	}); */

})(jQuery); 

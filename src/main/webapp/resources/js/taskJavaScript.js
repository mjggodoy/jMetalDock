(function ($) { 
		
    var minNumber = 1;
    var maxNumber = 50;
    
    $('.spinner input').keydown(function(e){
    	
		if($('.spinner input').val() >= maxNumber ){
        e.preventDefault();
        return false;
        
		}
		
		if($('.spinner input').val() < minNumber ){
	        e.preventDefault();
	        return false;
		}    
    });
    
    

	$('.spinner .btn:first-of-type').on('click', function() {
				
		if($('.spinner input').val() == maxNumber ){
			return false;
		}else{		
			$('.spinner input').val( parseInt($('.spinner input').val(), 10) + 1);			
		}
	}); 
		
	$('.spinner .btn:last-of-type').on('click', function() { 

		if($('.spinner input').val() == minNumber ){
			return false;
		}else{
			
			$('.spinner input').val( parseInt($('.spinner input').val(), 10) - 1);	
		}	
	}); 

})(jQuery); 

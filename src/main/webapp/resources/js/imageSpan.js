$("#pop").on("click", function() {
   $('#imagepreview').attr('src', $('#serverDocking').attr('src')); 
   // here asign the image to the modal when the user click the enlarge link
   $('#imagemodal').modal('show'); 
   // imagemodal is the id attribute assigned to the bootstrap modal, then i use the show function
});

$("#pop2").on("click", function() {
	   $('#imagepreview').attr('src', $('#form').attr('src')); 
	   // here asign the image to the modal when the user click the enlarge link
	   $('#imagemodal2').modal('show'); 
	   // imagemodal is the id attribute assigned to the bootstrap modal, then i use the show function
	});

$("#pop3").on("click", function() {
	   $('#imagepreview').attr('src', $('#form2').attr('src')); 
	   // here asign the image to the modal when the user click the enlarge link
	   $('#imagemodal3').modal('show'); 
	   // imagemodal is the id attribute assigned to the bootstrap modal, then i use the show function
	});
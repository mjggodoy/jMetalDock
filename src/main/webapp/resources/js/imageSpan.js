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


$(document).ready(function() {
    var $lightbox = $('#lightbox');
    $('[data-target="#lightbox"]').on('click', function(event) {
        var $img = $(this).find('img'), 
            src = $img.attr('src'),
            alt = $img.attr('alt'),
            css = {
                'maxWidth': $(window).width() - 100,
                'maxHeight': $(window).height() - 100
            };
        $lightbox.find('img').attr('src', src);
        $lightbox.find('img').attr('alt', alt);
        $lightbox.find('img').css(css);
    });
    $lightbox.on('shown.bs.modal', function (e) {
        var $img = $lightbox.find('img');
        $lightbox.find('.modal-dialog').css({'width': $img.width()});
        $lightbox.find('.close').removeClass('hidden');
    });
});
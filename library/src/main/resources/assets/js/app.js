$(function(){
	$('tr').each(function(){
	if ($(this).find('.status').text() == "lost")
		$(this).find('button').prop("disabled", "true");
});
});


$(":button").click(function() {
    var btn = $(this),
	tr = btn.closest('tr'),
    isbn = tr.attr("data-id");
    console.log('About to report lost on ISBN ' + isbn);    

    
    $.ajax({
        url: '/library/v1/books/'+isbn+'?status=lost',
        type: 'PUT',
        contentType: 'application/json',
        success: function(result) {
		btn.prop('disabled', 'true');
		tr.find('.status').html('lost');
     
        	//$('#'+isbn).prop('disabled', true);
        	//location.reload();
        	
        	//jQuery('#'+isbn).prop("disabled", true);
        	//alert('Successful hit');
        	
        	/*$('#'+isbn).click(function(event) {
        		  event.preventDefault();
        		});*/
        	
        	//$(this).prop('disabled', true);
        	//$(this).hide().delay(30000);
        }
    })
});

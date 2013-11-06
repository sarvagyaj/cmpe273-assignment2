
$(":button").click(function() {
    var isbn = this.id;
    alert('About to report lost on ISBN ' + isbn);    
    
    $.ajax({
        url: '/library/v1/books/'+isbn+'?status=lost',
        type: 'PUT',
        contentType: 'application/json',
        success: function(result) {     
        	//$('#'+isbn).prop('disabled', true);
        	location.reload();
        	
        	//jQuery('#'+isbn).prop("disabled", true);
        	//alert('Successful hit');
        	
        	/*$('#'+isbn).click(function(event) {
        		  event.preventDefault();
        		});*/
        	
        	//$(this).prop('disabled', true);
        	//$(this).hide().delay(30000);
        }
    })
    $(this).prop('disabled', true);
});
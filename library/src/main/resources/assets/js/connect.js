/**
 * 
 */
$(document).ready(function() {
	var url = "ws://54.215.210.214:61623";
	var login = "admin";
	var password = "password";
	var destination = "/topic/69377.book.computer";
	var client = Stomp.client(url);
	client.debug = function(str) {
		$("#debug").append(str + "\n");
	};
	client.connect(login, password, function(frame) {
		alert("inside connect");
		client.subscribe(destination, function(message) {
			//alert(message.headers.isbn + " s name is " + message.headers.coverimage);
			location.reload();
			
			
			 /*$.ajax({
			        url: '/library/v1/books/'+message.headers.isbn+'?status=available&category='+message.headers.category+'&title='+message.headers.title+'&coverimage='+message.headers.coverimage ,
			        type: 'PUT',
			        contentType: 'application/json',
			        success: function(result) {        	
			        	location.reload();			        	
			        }
			    })*/
		});

	});
});
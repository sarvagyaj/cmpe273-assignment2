/**
 * 
 */

function renderBook(data){
        data.coverimage = decodeURIComponent(data.coverimage);
	data.status = data.status || "available";
	var vw = Mustache.render('<tr id={{isbn}}  data-id={{isbn}}><td class="isbn">{{isbn}}</td><td class="img"><img class="img-thumbnail" height="92" width="72" src="{{coverimage}}"></td><td class="title">{{title}}</td><td class="category">{{category}}</td><td class="status">{{status}}</td><td class="cmd"><button type="button" class="btn btn-primary">Report Lost</button></td></tr>', data);
	
	if ($('#' + data.isbn).length){
		$('#' + data.isbn).replaceWith(vw);	
	}
	else{
		$('#tbody').append(vw);
	}
	
	updateStatus();
}

$(document).ready(function() {
	var url = "ws://54.215.210.214:61623";
	var login = "admin";
	var password = "password";
	var destination=$("#topic").val();
		
	var client = Stomp.client(url);
	client.debug = function(str) {
		$("#debug").append(str + "\n");
	};
	client.connect(login, password, function(frame) {
		console.debug("inside connect");
		client.subscribe(destination, function(message) {
			renderBook(message.headers);
			
		});

	});
});

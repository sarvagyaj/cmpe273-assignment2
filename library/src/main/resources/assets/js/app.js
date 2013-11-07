$(function() {
	updateStatus();
});

function updateStatus() {
	$('tr').each(function() {
		if ($(this).find('.status').text() == "lost") {
			$(this).find('.status').css("color", "red");
			$(this).find('button').prop("disabled", "true");
		} else {
			$(this).find('button').removeAttr("disabled");
			$(this).find('.status').css("color", "green");
		}
	});

}

$(document).delegate(':button', 'click', function() {
	var btn = $(this), tr = btn.closest('tr'), isbn = tr.attr("data-id");
	console.log('About to report lost on ISBN ' + isbn);

	$.ajax({
		url : '/library/v1/books/' + isbn + '?status=lost',
		type : 'PUT',
		contentType : 'application/json',
		success : function(result) {
			tr.find('.status').html('lost');
			updateStatus();
		}
	})
});

$(function() {

	var idval = $("#idval").val();

	$.ajax({
		type : 'POST',
		url : 'PositionServlet',
		data : {
			type : 'object',
			id : idval
		},
		success : callBack,
		dataType : 'json'
	});

	function callBack(data) {

		var result = data.result;

		var name = result.name;
		var note = result.note;

		$("#name").val(name);
		$("#note").html(note);

	}

});
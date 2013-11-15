$(function() {
	var idval = $("#idval").val();
	$.ajax({
		type : 'POST',
		url : 'OrganizationServlet',
		data : {
			type : 'object',
			id : idval
		},
		success : re,
		dataType : 'json'
	});

	function re(data) {
		var result = data.result;
		var name = result.name;
		var address = result.address;
		var super_id = result.super_id;
		$("#name").val(name);
		$("#address").html(address);
		$("#ini").html(result.super_name);
		$("#option option").each(function() {
			if ($(this).val() == super_id) {
				$(this).prependTo($("#option"));
				$(this).attr("selected", "selected");
			}
		});
	}

});
$(function() {
	var idval = $("#idval").val();
	$.ajax({
		type : 'POST',
		url : 'StaffServlet',
		data : {
			type : 'object',
			id : idval
		},
		success : re,
		dataType : 'json',
		async: true
	});

	function re(data) {
		
		var result = data.result;

		var id = result.id;
		var name = result.name;
		var mobile = result.mobile;
		var phone = result.phone;
		var organization_id = result.organization_id;
		var department_id = result.department_id;
		var position_id = result.position_id;
		var is_administrator = result.is_administrator;
		var is_leader = result.is_leader;
		var is_hipe = result.is_hipe;
		var is_departure = result.is_departure;
		var is_warrant = result.is_warrant;
		var password = result.password;

		$("#name").val(name);
		$("#mobile").val(mobile);
		$("#phone").val(phone);
		if (mobile == 'admin') {
			$("#mobile").attr({
				readonly : 'true'
			});
			$("#mobile").css("background", "#F00");
		}
		if (is_administrator == 0) {
			$("<option  value=" + is_administrator + ">否</option>").appendTo(
					$("#is_administrator"));
			$("<option  value=" + 1 + ">是</option>").appendTo(
					$("#is_administrator"));
		} else {
			$("<option  value=" + 1 + ">是</option>").appendTo(
					$("#is_administrator"));
			$("<option  value=" + 0 + ">否</option>").appendTo(
					$("#is_administrator"));
		}

		if (is_leader == 0) {
			$("<option  value=" + is_leader + ">否</option>").appendTo(
					$("#is_leader"));
			$("<option  value=" + 1 + ">是</option>").appendTo($("#is_leader"));
		} else {
			$("<option  value=" + 1 + ">是</option>").appendTo($("#is_leader"));
			$("<option  value=" + 0 + ">否</option>").appendTo($("#is_leader"));
		}

		if (is_hipe == 0) {
			$("<option  value=" + is_hipe + ">否</option>").appendTo(
					$("#is_hipe"));
			$("<option  value=" + 1 + ">是</option>").appendTo($("#is_hipe"));
		} else {
			$("<option  value=" + 1 + ">是</option>").appendTo($("#is_hipe"));
			$("<option  value=" + 0 + ">否</option>").appendTo($("#is_hipe"));
		}

		if (is_departure == 0) {
			$("<option  value=" + is_departure + ">否</option>").appendTo(
					$("#is_departure"));
			$("<option  value=" + 1 + ">是</option>").appendTo(
					$("#is_departure"));
		} else {
			$("<option  value=" + 1 + ">是</option>").appendTo(
					$("#is_departure"));
			$("<option  value=" + 0 + ">否</option>").appendTo(
					$("#is_departure"));
		}

		if (is_warrant == 0) {
			$("<option  value=" + is_warrant + ">否</option>").appendTo(
					$("#is_warrant"));
			$("<option  value=" + 1 + ">是</option>").appendTo($("#is_warrant"));
		} else {
			$("<option  value=" + 1 + ">是</option>").appendTo($("#is_warrant"));
			$("<option  value=" + 0 + ">否</option>").appendTo($("#is_warrant"));
		}
		// alert(name);
		// var address=result.address;
		// $("#name").val(name);
		// $("#address").html(address);
		// $("<option
		// value="+result.id+">"+result.name+"</option>").appendTo($("#option"));

		// alert(organization_id+" "+department_id+" "+position_id);

		$("#organization_id").val(organization_id);
		$("#department_id").val(department_id);
		$("#position_id").val(position_id);
	}

});

function checkPassword() {
	var password1 = $("#password1").val();
	var password2 = $("#password2").val();
	if (password1 == password2) {
		return true;
	}
	alert("密码不一致");
	return false;
}
$(function() {
	// 这里是机构的下拉列表
	$.ajax({
		type : "POST",
		url : "OrganizationServlet",
		data : { type : "list" , login:"no"	},
		success : OrganizationList,
		dataType : "json",
		async: false
	});

	function OrganizationList(data) {
		var result = data.result;
		var count = "";
		if (result != "") {
			$.each(result, function(i) {
				count += "<option  value=" + result[i].id + ">"
						+ result[i].name + "</option>";
			});
		}
		$(count).appendTo($("#organization_id"));
	}

	// 这里是部门下拉列表
	$.ajax({
		type : "POST",
		url : "DepartmentServlet",
		data : { type : "list", login:"no"	},
		success : DepartmentList,
		dataType : "json",
		async: false
	});
	
	function DepartmentList(data) {
		var result = data.result;
		var count = "";
		if (result != "") {
			$.each(result, function(i) {
				count += "<option  value=" + result[i].id + ">"
						+ result[i].name + "</option>";
			});
		}
		$(count).appendTo($("#department_id"));
	}

	// 这里是职位的下拉列表
	$.ajax({
		type : "POST",
		url : "PositionServlet",
		data : { type : "list" , login:"no"	},
		success : PositionList,
		dataType : "json",
		async: false
	});
	
	function PositionList(data) {
		var result = data.result;
		var count = "";
		if (result != "") {
			$.each(result, function(i) {
				count += "<option  value=" + result[i].id + ">"
						+ result[i].name + "</option>";
			});
		}
		$(count).appendTo($("#position_id"));
	}
});

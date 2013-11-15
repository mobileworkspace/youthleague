$(function() {
	
	var id = $("#id").val();
	
	$("#paging").myPagination({
		currPage : 1,
		pageCount : 10,
		pageSize : 15,
		cssStyle : 'MSDN',
		ajax : {
			on : true, // 开启状态
			callback : 'testCallBack', // 回调函数，注，此 ajaxCallBack 函数，必须定义在
			// $(function() {}); 外面
			url : "StaffServlet", // 访问服务器地址
			dataType : 'json', // 返回类型
			param : {
				on : true,
				page : 1,
				pageSize : 15,
				type : "nextPage",
				find_id : id
			}
		// 参数列表，其中 on 必须开启，page 参数必须存在，其他的都是自定义参数，如果是多条件查询，可以序列化表单，然后增加 page 参数
		}
	});
});

// 自定义 回调函数
function testCallBack(data) {
	// alert(data.result); //显示服务器返回信息
	var insetViewData = ""; // 视图数据

	// var result = eval("("+data.result+")");
	var result = data.result;
	if (result !== "") {
		$.each(result, function(i) {
			insetViewData += createTR(result[i].id, result[i].name,
					result[i].mobile, result[i].phone,
					result[i].organization_Name, result[i].department_Name,
					result[i].position_Name, result[i].is_administrator,
					result[i].is_departure, result[i].is_warrant);
		});
	}
	$("#content").html(insetViewData);
}

function createTR(id, name, mobile, phone, organization_Name, department_Name,
		position_Name, is_administrator, is_departure, is_warrant) {
	var tr = "<tr>";
	tr += "<td style='display:none'>" + id + "</td>";
	tr += "<td>" + name + "</td>";
	tr += "<td>" + mobile + "</td>";
	tr += "<td>" + phone + "</td>";
	tr += "<td>" + organization_Name + "</td>";
	tr += "<td>" + department_Name + "</td>";
	tr += "<td>" + position_Name + "</td>";
	if (is_administrator === 0) {
		tr += "<td>否</td>";
	} else {
		tr += "<td>是</td>";
	}
	if (is_departure === 0) {
		tr += "<td>否</td>";
	} else {
		tr += "<td>是</td>";
	}
	if (is_warrant === 0) {
		tr += "<td>否</td>";
	} else {
		tr += "<td>是</td>";
	}
	tr += "<td><a  href='admin/staff/AmendStaff.jsp?id=" + id + "'>" + '修改'
			+ "</a></td>";
	tr += "<td><a  href='StaffServlet?type=delete&id=" + id
			+ "' onclick='return del();'>" + '删除' + "</a></td>";
	tr += "</tr>";
	return tr;
}

// 提示用户确认删除
function del() {
	return window.confirm("你确认要删除吗?");
}

// 显示所有
function show_all() {
	window.open('/youthleague/admin/staff/Staff.jsp', '_self');
}

// str.result.name
function getOrganizationName(val) {
	var value = "";
	$.getJSON("OrganizationServlet?type=object&id=" + val, function(str) {
		alert(str.result.name);
		value += str.result.name;
		alert(value);
	});
	return value + "";
}

function getDepartmentName(val) {
	$.getJSON("DepartmentServlet?type=object&id=" + val, function(str) {
		alert(str.result.name);
	});
}

function getPositionName(val) {
	$.getJSON("PositionServlet?type=object&id=" + val, function(str) {
		alert(str.result.name);
	});
}

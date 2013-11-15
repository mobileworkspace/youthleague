$.post("DatebaseFileServlet", {
	type : ""
}, function(data) {
	$("#filecreatetime").html(data);
});

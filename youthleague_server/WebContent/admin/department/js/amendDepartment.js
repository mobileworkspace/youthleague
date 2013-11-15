$(function() {
	var idval=$("#idval").val();
	
	$.ajax({
		  type: 'POST',
		  url: 'DepartmentServlet',
		  data: {type:'object',id:idval},
		  success: re,
		  dataType: 'json'
		});

	function re(data){
	
		var result = data.result;
    
	   var name=result.name;
	   var note=result.note;
	   $("#name").val(name);
	   $("#note").html(note);
	   
    }

});
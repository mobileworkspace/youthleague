$(function() {
	var idval=$("#idval").val();
	$.ajax({
		  type: 'POST',
		  url: 'OrganizationServlet',
		  data: { type:'list_all' },
		  success: re,
		  dataType: 'json'
		});

	function re(data){
		var result = data.result;
		var provincecount;
		 if(result!=""){
	         $.each(result, function(i) {
	        	 provincecount+="<option  value="+result[i].id+">"+result[i].name+"</option>";
	         });
         }
		 $(provincecount).appendTo($("#option"));
        }

});
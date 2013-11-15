$(function() {
    $("#paging").myPagination({
      currPage: 1,
      pageCount: 10,
      pageSize: 10,
      cssStyle: 'MSDN',
      ajax: {
    	  on: true,                        //开启状态
    	  callback: 'testCallBack',    //回调函数，注，此 ajaxCallBack 函数，必须定义在 $(function() {}); 外面
    	  url: "OrganizationServlet",            //访问服务器地址
    	  dataType: 'json',                //返回类型
    	  param:{on:true,page:1,pageSize:10,type:"nextPage"}	//参数列表，其中  on 必须开启，page 参数必须存在，其他的都是自定义参数，如果是多条件查询，可以序列化表单，然后增加 page 参数
    }
    });
});

//自定义 回调函数
function testCallBack(data) {
	var insetViewData = "";	//视图数据
	var result = data.result;
	if(result!=""){
		$.each(result, function(i) {
			if(result[i].id!=-1){
				insetViewData += createTR(result[i].id,result[i].name,result[i].address,result[i].super_name);
				}
			});
		}
	$("#content").html(insetViewData);
}

function createTR(id,name,address,super_name){
	var tr = "<tr>";
	tr += "<td>"+name+"</td>";
	if(address.length>10){
		address=address.substring(0, 9);
		address+="....";
	}
	tr += "<td>"+address+"</td>";
	tr += "<td>"+super_name+"</td>";
	tr += "<td><a  href='admin/organization/AmendOrganization.jsp?id="+id+"'>"+'修改'+"</a></td>";
	tr += "<td><a  href='OrganizationServlet?type=delete&id="+id+"' onclick='return del();'>"+'删除'+"</a></td>";
	tr += "</tr>";
	return tr;
}

//提示用户确认删除
function del(){
	return window.confirm("你确认要删除吗?");
}
$('#table').bootstrapTable({
	    url: 'Servlet04?op=BootstrapTable',
	    dataType:'json',
	    columns: [{field:'checkbox',checkbox:true},
	    {field: 'deptno',title: 'DEPTNO',sortable:true,align:'center'},
	    {field: 'dname',title: 'DEPT NAME',align:'center'}, 
	    {field: 'loc',title: 'LOCATION',align:'center'},
	    {field: 'operate',title: 'OPERATE',align:'center',formatter:function(){
	    	return '<a title="" class="select" href="javascript:void(0)"><i class="glyphicon glyphicon-search"></i></a><a title="Remove" class="remove" href="javascript:void(0)"><i class="glyphicon glyphicon-remove"></i></a>';
	    }},
	    ],
	    striped:true,//隔行变色
	    pagination:true,//开启分页
	    sidePagination: 'server',//在哪分页
	    pageList:[2,5,10,20],
		search:true,//
		showColumns:true,
		showToggle:true,
		showRefresh:true,
		showPaginationSwitch:true,
		showExport:true,
		detailView:true,
		detailFormatter:function(index,row){
			return"asdhg";
		},
		toolbar:'#toolbar',
	});
//$(function(){
//	data=[{"id":"900","name":"abc","price":"1.00"},{"id":"800","name":"abc","price":"1.00"}];
//	$('#btn_add').on("click",function(){
//		$("#table").bootstrapTable('append',data);
//	})
//	$('#btn_delete').on("click",function(){
//		$("#table").bootstrapTable('getSelections');
//	})
////	alert($("#table").bootstrapTable('getRowByUniqueId',1));
//});

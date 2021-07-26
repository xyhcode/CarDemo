<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="JS/jquery-1.10.2.js"></script>
<script src="layui/layui.js"></script>
<link rel="stylesheet" href="layui/css/layui.css" media="all">
<link rel="stylesheet" href="CSS/index.css">
<script type="text/javascript">
var dataall;
layui.use('table', function(){
	  var table = layui.table;
	  var tableIns=table.render({
	    elem: '#a1'
	    ,height: 500
	    ,toolbar:'#toolbarDemo'
	    ,defaultToolbar: ['filter', 'exports', 'print']
	    ,url: 'test' //数据接口
	    ,page: true //开启分页
	    ,cols: [[ //表头
	      {field: 'cname', title: '品牌', width:400, sort: true,align:'center',edit: 'text'}
	      ,{field: 'cnumber', title: '车牌', width:400,align:'center',edit: 'text'}
	      ,{field: 'ccolor', title: '颜色', width:400, sort: true,align:'center',edit: 'text'}
	      ,{title:'操作',align:'center',toolbar : '#bar'}
	    ]]
	  ,id: 'testReload'
	  });
	  
	  $('#sear').on('click', function(){
		  var cf=$("#cpk").val();
		  tableIns.reload({
			  where: { //设定异步数据接口的额外参数，任意设
				  cnumber:cf
			    //…
			  }
			  ,page: {
			    curr: 1 //重新从第 1 页开始
			  }
			});
		   
		  });
	  
	  
	/*   var $ = layui.$, active = {
			    reload: function(){
			      var demoReload = $('#cpk');
			      //执行重载
			      table.reload('testReload', {
			        page: {
			          curr: 1 //重新从第 1 页开始
			        }
			        ,where: {
			          key: {
			        	  cnumber: demoReload.val()
			          }
			        }
			      });
			    }
			  };
 */	  
	  /* $('.layui-form-item .layui-btn').on('click', function(){
		  var sername = $('#cpk').val();
		  $.post("test",{"type":"search","sename":sername},function(ser){
			  layer.alert(ser);
			  var table = layui.table;
			  table.render({
			    elem: '#a1'
			    ,height: 500
			    ,toolbar:'#toolbarDemo'
			    ,defaultToolbar: ['filter', 'exports', 'print']
			    ,url: 'test' //数据接口
			    ,page: true //开启分页
			    ,cols: [[ //表头
			      {field: 'cname', title: '品牌', width:400, sort: true,align:'center',edit: 'text'}
			      ,{field: 'cnumber', title: '车牌', width:400,align:'center',edit: 'text'}
			      ,{field: 'ccolor', title: '颜色', width:400, sort: true,align:'center',edit: 'text'}
			      ,{title:'操作',align:'center',toolbar : '#bar'}
			    ]]
			  ,id: 'testReload'
			  });
			}); 
		  }); */
	 //表格编辑修改
	 table.on('edit(test)', function(obj){
		   var value = obj.value; //得到修改后的值
		   var data = obj.data; //得到所在行所有键值
		   var field = obj.field; //得到字段
		   $.post("test",{"type":"edit","id":data.id,"cna":data.cname,"cum":data.cnumber,"co":data.ccolor},function(up){
			   if(up=="u3"){
				   var ii = layer.load();
				    //此处用setTimeout演示ajax的回调
				    setTimeout(function(){
				      layer.close(ii);
				    }, 500);
				    layer.closeAll(function(){ //关闭所有层并执行回调
				    	layer.msg("编辑成功！");
						//刷新
						cx();
				    }); 
			   }
		   })
		     
		  });
	 
	  table.on('tool(test)', function(obj) {
		    dataall=obj.data;//可以得到当前行的所有数据 全局变量
			var data = obj.data;//获得当前行数据
		    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
			var tr = obj.tr; //获得当前行 tr 的DOM对象
			//编辑
			if (layEvent === 'edit') {
				$("#pp").val(data.cname),//传车名
				$("#cp").val(data.cnumber),//传车牌
				$("#ys").val(data.ccolor),//传颜色
				//弹出一个修改层
				layer.open({
					type : 1,
					title : '编辑车辆消息',
					area : [ '600px', '340px' ],//宽高
					shadeClose : true, //点击遮罩关闭
					content : $('#df'),
				});	
			} else if (layEvent === 'del') {
				//删除
				var tb=data.id;//得到id
				layer.confirm('确认删除嘛？', function(index){
					//
					$.post("test",{"type":"del","id":tb},function(da){
						 var ii = layer.load();
						    //此处用setTimeout演示ajax的回调
						    setTimeout(function(){
						      layer.close(ii);
						    }, 500);
						layer.msg("删除成功！");
						//刷新
						cx();
					});
			      });	
			}
		});
	});
	
   layui.use('form', function(){
	  var form = layui.form;
	  
	  //编辑提交
	  form.on('submit(update_submit)', function(){
		  //ID
		  var id=dataall.id;
		  //品牌
		  var cname=$("#pp").val();
		  //车牌
		  var cnumber=$("#cp").val();
		  //颜色
		  var color=$("#ys").val();
		  $.post("test",{"type":"edit","id":id,"cna":cname,"cum":cnumber,"co":color},function(ed){
			  if(ed=="u3"){
				  var ii = layer.load();
				    //此处用setTimeout演示ajax的回调
				    setTimeout(function(){
				      layer.close(ii);
				    }, 500);
				    layer.closeAll(function(){ //关闭所有层并执行回调
				    	layer.msg("编辑成功！");
						//刷新
						cx();
				    }); 
				
			  }
		  });
	  });
	  
	  //添加车辆的提交
	  form.on('submit(add_submit)', function(data){
		  
		  var na=$("#tjcname").val();
		  var cnum=$("#tjcnumber").val();
		  var colo=$("#tjccolor").val();
		  $.post("test",{"type":"add","acna":na,"acum":cnum,"aco":colo},function(add){
			  if(add=="u4"){
				  var ii = layer.load();
				    //此处用setTimeout演示ajax的回调
				    setTimeout(function(){
				      layer.close(ii);
				    }, 500);
				    layer.closeAll(function(){ //关闭所有层并执行回调
				    	layer.msg("添加成功！");
						//刷新
						cx();
						$("#tjcname").val("");
						$("#tjcnumber").val("");
						$("#tjccolor").val("");
				    }); 
				
			  }
		  });
	   });
	  
	  
	});
	
	//搜索框重置
	function res(){
		$("#cpk").val("");
	}
	
	//刷新
	function cx(){
		layui.use('table', function(){
			  var table = layui.table;
			  table.render({
			    elem: '#a1'
			    ,toolbar:'#toolbarDemo'
			    ,defaultToolbar: ['filter', 'exports', 'print']
			    ,height: 500
			    ,url: 'test' //数据接口
			    ,page: true //开启分页
			    ,cols: [[ //表头
			      {field: 'cname', title: '品牌', width:400, sort: true,align:'center',edit: 'text'}
			      ,{field: 'cnumber', title: '车牌', width:400,align:'center',edit: 'text'}
			      ,{field: 'ccolor', title: '颜色', width:400, sort: true,align:'center',edit: 'text'}
			      ,{title:'操作',align:'center',toolbar : '#bar'}
			    ]]
			  ,id: 'testReload'
			  });
		});
	}
	
	//添加车辆
	function tj(){
		layer.open({
			type : 1,
			title : '添加车辆消息',
			area : [ '600px', '340px' ],
			shadeClose : true, //点击遮罩关闭
			content : $('#df2'),//添加层
		});
	}
	
	//添加重置
	function czk(){
		$("#df2 input").val("");
	}
	
	//更新车重置
	function updare(){
		$("#pp").val(dataall.cname);//传车名
		$("#cp").val(dataall.cnumber);//传车牌
		$("#ys").val(dataall.ccolor);//传颜色
	}
	
	 
</script>

<script type="text/html" id="bar">
<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script type="text/html" id="toolbarDemo" >
 <div style="margin: 10px 0 0px 1%;width: 99%" id="toolbarDemo">
    <div style="display: table-cell">
        
            <div class="layui-input-block" style="display: table-cell">
                <!--            <label>&emsp;接&emsp;口&emsp;</label>-->
                搜索车牌：<div class="layui-input-inline" style="width: 260px">
                  <input type="text" id="cpk" name="cnumber" placeholder="请输入车牌 支持模糊查询"
                           autocomplete="off" class="layui-input">
                </div>
         
            </div>
            <div style="display: table-cell" class="layui-form-item">
                <button class="layui-btn layui-btn-sm layui-btn-danger" data-type="reload" id="sear" lay-submit="" lay-filter="search"
                        style="margin-left: 15px"><i class="layui-icon" >&#xe615;</i>搜&emsp;索
                </button>
                <button type="reset" class="layui-btn layui-btn-primary layui-btn-sm" onclick="res()">
                    <i class="layui-icon">&#xe631;</i>重&emsp;置
                </button>
            </div>
      
    </div>
    <div style="display: table-cell">
        <div style="display: table">
            <!--新增-->
            <div style="display: table-cell">
                <button id="add" onclick="tj()" class="layui-btn layui-btn-sm" style="margin-left: 15px">
                    <i class="layui-icon">&#xe608;</i> 新&emsp;增
                </button>
            </div>
            <!--刷新-->
            <div style="display: table-cell;">
                <button id="refresh" onclick="cx()" class="layui-btn layui-btn-normal layui-btn-sm" style="margin-left: 15px">
                    <i class="layui-icon">&#xe669;</i> 刷&emsp;新
                </button>
            </div>
        </div>
    </div>
</div>
</script>
</head>
<body>
<h1>汽车销售中心</h1>
    <div id="d1">
		<table id="a1" lay-filter="test" lay-data="{id: 'idTest'}"></table>
	</div>
</body>
<!-- 编辑车辆 -->
<div style="padding: 20px; display: none" id="df">
	<div class="layui-form-item" style="display: table-cell; width: 50%">
		<label class="layui-form-label">车辆品牌：</label>
		<div class="layui-input-block">
			<input type="text" name="name" id="pp" placeholder="请输入车辆品牌"
				autocomplete="off" class="layui-input" lay-verify="required">
		</div>
	</div>
	<br>
	<div class="layui-form-item" style="display: table-cell; width: 50%">
		<label class="layui-form-label">车辆车牌：</label>
		<div class="layui-input-block">
			<input type="text" name="name" id="cp" placeholder="请输入车辆车牌"
				autocomplete="off" class="layui-input" lay-verify="required">
		</div>
	</div>
	<br>
	<div class="layui-form-item" style="display: table-cell; width: 50%">
		<label class="layui-form-label">车辆颜色：</label>
		<div class="layui-input-block">
			<input type="text" name="name" id="ys" placeholder="请输入车辆颜色"
				autocomplete="off" class="layui-input" lay-verify="required">
		</div>
	</div>
	<div class="layui-form-item" style="margin-left: 40%" id="bu3">
		<div class="layui-btn-group">
			<button class="layui-btn" lay-submit="" lay-filter="update_submit">提&ensp;交</button>
			<button type="reset" class="layui-btn layui-btn-primary" onclick="updare()">重&ensp;置</button>
		</div>
	</div>
</div>

<!-- 添加车辆 -->
<div style="padding: 20px; display: none" id="df2">
	<div class="layui-form-item" style="display: table-cell; width: 50%">
		<label class="layui-form-label">车辆品牌：</label>
		<div class="layui-input-block">
			<input type="text" name="name" id="tjcname" placeholder="请输入车辆品牌"
				autocomplete="off" class="layui-input" lay-verify="required">
		</div>
	</div>
	<br>
	<div class="layui-form-item" style="display: table-cell; width: 50%">
		<label class="layui-form-label">车辆车牌：</label>
		<div class="layui-input-block">
			<input type="text" name="name" id="tjcnumber" placeholder="请输入车辆车牌"
				autocomplete="off" class="layui-input" lay-verify="required">
		</div>
	</div>
	<br>
	<div class="layui-form-item" style="display: table-cell; width: 50%">
		<label class="layui-form-label">车辆颜色：</label>
		<div class="layui-input-block">
			<input type="text" name="name" id="tjccolor" placeholder="请输入车辆颜色"
				autocomplete="off" class="layui-input" lay-verify="required">
		</div>
	</div>
	<div class="layui-form-item" style="margin-left: 40%" id="bu3">
		<div class="layui-btn-group">
			<button class="layui-btn" lay-submit="" lay-filter="add_submit">添&ensp;加</button>
			<button type="reset" class="layui-btn layui-btn-primary" onclick="czk()">重&ensp;置</button>
		</div>
	</div>
</div>
</html>
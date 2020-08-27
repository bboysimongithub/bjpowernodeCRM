<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" +
request.getServerName() + ":" + request.getServerPort() +
request.getContextPath() + "/";
%>
<html>
<head>
	<base href="<%=basePath%>" />
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	$(function(){

		$("#deleteBttn").click(function () {

			var $xz = $("input[name = xz]:checked");

			if ($xz.length == 0) {
				alert("请选择需要删除的记录");
			}else {

				if (confirm("你确定删除所选项吗？")) {

					var parm = "";

					for (var i = 0; i < $xz.length; i ++) {
						parm += "id="+$($xz[i]).val();
						if (i < $xz.length - 1) {
							parm += "&";
						}
					}

					$.ajax({
						url:"workbench/activity/delete.do",
						data:parm,
						type:"get",
						dataType:"json",
						success:function (reps) {
							if (reps.success) {
								pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
							}else {
								alert("删除失败")
							}

						}
					})

				}

			}
		})

		$(".time").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});
		
		$("#addBttn").click(function (){

			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});


			$.ajax({
				url:"workbench/activity/getUserList.do",
				data:{},
				type:"get",
				dataType:"json",
				success:function (reps){
					$("#create-owner").empty();
					$.each(reps,function (index,element){
						$("#create-owner").append("<option value='"+element.id+"'>"+element.name+"</option>");
					})
					var id = "${user.id}";
					$("#create-owner").val(id);
				}
			})
			$("#createActivityModal").modal("show");
		})

		$("#saveBttn").click(function () {

			$.ajax({
				url:"workbench/activity/save.do",
				data:{


					"owner"						:$.trim($("#create-owner").val()),
					"name"						:$.trim($("#create-name").val()),
					"startDate"					:$.trim($("#create-startDate").val()),
					"endDate"					:$.trim($("#create-endDate").val()),
					"cost"						:$.trim($("#create-cost").val()),
					"description"				:$.trim($("#create-description").val())



				},
				type: "post",
				dataType: "json",
				success:function (reps) {

					$("#resetKey")[0].reset();
					if (reps.success) {
						alert("添加成功");
						pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

						$("#createActivityModal").modal("hide");
					}else {
						alert("添加失败");
					}
				}
			})
		})
		pageList(1,5);

		$("#searchBttn").click(function () {

			$("#hidden-name").val($.trim($("#search-name").val()));
			$("#hidden-owner").val($.trim($("#search-owner").val()));
			$("#hidden-startDate").val($.trim($("#search-startDate").val()));
			$("#hidden-endDate").val($.trim($("#search-endDate").val()));

			pageList(1,5);
		})

		$("#editbttn").click(function () {

			var $xz = $("input[name = xz]:checked");
			if ($xz.length == 0) {
				alert("请选择要修改的记录");
			}else if ($xz.length > 1) {
				alert("只能选择一条记录进行修改");
				pageList(1,2);
			}else {
				var id = $xz.val();
				$.ajax({
					url:"workbench/activity/getUserAndActivity.do",
					data:{
						"id":id,
					},
					type:"get",
					dataType:"json",
					success:function (reps) {

						var html = "<option></option>"
						$.each(reps.uList,function (index,element) {

							html += "<option value='"+element.id+"'>"+element.name+"</option>"

						})
						$("#edit-owner").html(html);

						//处理单条activity
						$("#edit-name").val(reps.a.name);
						$("#edit-owner").val(reps.a.owner);
						$("#edit-startDate").val(reps.a.startDate);
						$("#edit-endDate").val(reps.a.endDate);
						$("#edit-cost").val(reps.a.cost);
						$("#edit-description").val(reps.a.description);
						$("#edit-id").val(reps.a.id);

						$("#editActivityModal").modal("show");
					}
				})
			}

		})

		$("#updateBttn").click(function () {
			$.ajax({
				url:"workbench/activity/update.do",
				data:{

					"id"						:$.trim($("#edit-id").val()),
					"owner"						:$.trim($("#edit-owner").val()),
					"name"						:$.trim($("#edit-name").val()),
					"startDate"					:$.trim($("#edit-startDate").val()),
					"endDate"					:$.trim($("#edit-endDate").val()),
					"cost"						:$.trim($("#edit-cost").val()),
					"description"				:$.trim($("#edit-description").val())



				},
				type: "post",
				dataType: "json",
				success:function (reps) {

					if (reps.success) {
						alert("修改成功");
						pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
								,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

						$("#editActivityModal").modal("hide");
					}else {
						alert("修改失败");
					}
				}
			})
		})
	});

	function pageList (pageNo, pageSize) {

		//每次刷新列表把全选勾去掉
		$("#qx").prop("checked", false);


		$("#search-name").val($.trim($("#hidden-name").val()));
		$("#search-owner").val($.trim($("#hidden-owner").val()));
		$("#search-startDate").val($.trim($("#hidden-startDate").val()));
		$("#search-endDate").val($.trim($("#hidden-endDate").val()));


		$.ajax({

			url:"workbench/activity/pageList.do",
			data:{

				"pageNo":pageNo,
				"pageSize":pageSize,
				"name" : $.trim($("#search-name").val()),
				"owner" : $.trim($("#search-owner").val()),
				"startDate" : $.trim($("#search-startDate").val()),
				"endDate" : $.trim($("#search-endDate").val())

			},
			type:"get",
			dataType:"json",
			success : function (data) {

				/*

					data

						我们需要的：市场活动信息列表
						[{市场活动1},{2},{3}] List<Activity> aList
						一会分页插件需要的：查询出来的总记录数
						{"total":100} int total

						{"total":100,"dataList":[{市场活动1},{2},{3}]}

				 */
				var html = "";
				$.each(data.datalist,function (index,element) {

					html += '<tr class="active">';
					html += '<td><input type="checkbox" name="xz" value="'+element.id+'"/></td>';
					html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id='+element.id+'\';">'+element.name+'</a></td>';
					html += '<td>'+element.owner+'</td>';
					html += '<td>'+element.startDate+'</td>';
					html += '<td>'+element.endDate+'</td>';
					html += '</tr>';

				})
				$("#activityBody").html(html);

				//计算总页数
				var totalPages = data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1

				$("#activityPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});

			}
		})

		$("#qx").click(function () {
			$("input[name = xz]").prop("checked", this.checked);
		})

		$("#activityBody").on("click", $("input[name = xz]"), function () {

			$("#qx").prop("checked", $("input[name = xz]").length == $("input[name = xz]:checked").length);

		})
	}
	
</script>
</head>
<body>

	<input type="hidden" id="hidden-name"/>
	<input type="hidden" id="hidden-owner"/>
	<input type="hidden" id="hidden-startDate"/>
	<input type="hidden" id="hidden-endDate"/>


	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="resetKey">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 250px;">
								<select class="form-control" id="create-owner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 250px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 250px;">
								<input type="text" class="form-control time" id="create-startDate" readonly/>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 250px;">
								<input type="text" class="form-control time" id="create-endDate" readonly/>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBttn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">

						<input type="hidden" id="edit-id">
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 250px;">
								<select class="form-control" id="edit-owner">

								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 250px;">
                                <input type="text" class="form-control" id="edit-name" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 250px;">
								<input type="text" class="form-control time" id="edit-startDate">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 250px;">
								<input type="text" class="form-control time" id="edit-endDate" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateBttn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control time" type="text" id="search-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control time" type="text" id="search-endDate">
				    </div>
				  </div>
				  
				  <button type="button" id="searchBttn" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="addBttn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editbttn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBttn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx"/></td>
							<td>名称123</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">

					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">

				<div id="activityPage">

				</div>

			</div>
			
		</div>
		
	</div>
</body>
</html>
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
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script>

		$(function () {

			/*每次加载页面删除之前的登陆记录*/
			$("#loginAct").val("")

			/*获取用户焦点*/
			$("#loginAct").focus();

			/*为登陆键绑定鼠标单击事件*/
			$("#submitbttn").click(function () {
				login();
			})

			/*输入确定键，执行登陆操作*/
			$(window).keydown(function (index) {
				/*alert(index.keyCode);*/
				if (index.keyCode == 13) {
					login();
				}
			})


		})

		function login() {
			/*alert("执行登陆操作...");*/

			/*验证账号密码不能为空*/
			var loginAct = $.trim($("#loginAct").val());
			var loginPwd = $.trim($("#loginPwd").val());

			if (loginAct == "" || loginPwd == "") {
				$("#msg").html("账号密码不能为空");
				return false;
			}

			/*程序执行到这里访问后台验证账号密码，使用ajax请求*/
			$.ajax({

				url:"user/login.do",
				date:{
					loginAct,
					loginPwd
				},
				type:"post",
				dataType:"json",
				success:function (resp) {

					if (resp.success) {
						/*登陆成功，跳转到form表单提交的页面*/
						window.location.href = "workbench/index.html";
					}else {
						/*登陆失败，给msg输出原因*/
						$("#msg").html(resp.msg);
					}


				}
			})
		}
	</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;我的第一个CRM</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.html" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" id="loginAct">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" id="loginPwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						
							<span id="msg" style="color: red"></span>
						
					</div>
					<button type="button" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;" id="submitbttn">登录...</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
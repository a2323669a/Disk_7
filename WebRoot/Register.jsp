<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	import="java.sql.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<link href="videojs/video-js.css" rel="stylesheet">
<script src="videojs/video.js"></script>

<script type="text/javascript">
	function change(name) {
		var register = document.getElementById("register");
		var login = document.getElementById("login");

		if (name == 'register') {
			register.style.display = "block";
			login.style.display = "none";
			$("#treg").addClass("active");
			$("#tlog").removeClass("active");
		} else if (name == 'login') {
			register.style.display = "none";
			login.style.display = "block";
			$("#tlog").addClass("active");
			$("#treg").removeClass("active");

		}
	}
</script>

<title>My JSP 'Register.jsp' starting page</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- CSS -->
<link rel="stylesheet" href="assets/css/reset.css">
<link rel="stylesheet" href="assets/css/supersized.css">
<link rel="stylesheet" href="assets/css/style.css">


<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<div class="page-container">
		<font color="red" size="4"><s:property value="result" /> </font> <font
			color="red" size="4"><s:fielderror /> </font>
		<s:if test="result != null">
			<br>
			<br>
		</s:if>
		<table id="table" border="1">
			<tr>
				<td class="active" id="tlog"><h1 onclick="change('login')">登录</h1></td>
				<td id="treg"><h1 onclick="change('register')">注册</h1></td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="register" style="display: none">
						<s:form action="register.action">
							<s:textfield placeholder="用户名" name="name"></s:textfield>
							<s:textfield placeholder="密码" name="password"></s:textfield>
							<s:textfield placeholder="重复密码" name="password2"></s:textfield>
							<s:textfield placeholder="邮箱" name="email"></s:textfield>
							<s:submit value="注册" class="s-submit"></s:submit>
						</s:form>
					</div>
					<div id="login">
						<s:form action="login.action">
							<s:textfield placeholder="用户名" name="name"></s:textfield>
							<s:textfield placeholder="密码" name="password"></s:textfield>
							<s:submit value="登录" class="s-submit"></s:submit>
						</s:form>
					</div>
				</td>
			</tr>
		</table>
		<div class="connect">
			<p>Or connect with:</p>
			<p>
				<a class="facebook" href="Deal.jsp"></a> <a class="twitter" href=""></a>
			</p>
		</div>
		<center>
			<%-- <video id="my_video_1" class="video-js vjs-default-skin" controls
				preload="auto" width="640" height="264"
				data-setup="{}"> <source src="db/t.mp4" type="video/mp4"></video> --%>
			
			
			
			<%-- <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
				codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0"
				width="490" height="390">

				<param name="movie" value="db/Flvplayer.swf">

				<param name="quality" value="high">

				<param name="allowFullScreen" value="true" />

				<param name="FlashVars" value="vcastr_file=<%=basePath%>db/test.flv" />

				<embed src="db/Flvplayer.swf" allowFullScreen="true"
					FlashVars="vcastr_file=<%=basePath%>db/test.flv" quality="high"
					pluginspage="http://www.macromedia.com/go/getflashplayer"
					type="application/x-shockwave-flash" width="490" height="390"></embed>

			</object> --%>
		</center>

	</div>
	<!-- Javascript -->
	<script src="assets/js/jquery-1.8.2.min.js"></script>
	<script src="assets/js/supersized.3.2.7.min.js"></script>
	<script src="assets/js/supersized-init.js"></script>
	<script src="assets/js/scripts.js"></script>

</body>
</html>

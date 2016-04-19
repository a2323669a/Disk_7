<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="Check.jsp"%>
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
<meta charset="UTF-8">

<title>My JSP 'index.jsp' starting page</title>

<script type="text/javascript" src="./js/jquery-1.4.min.js"></script>
<script>
	var id = 0;
	function addressAction() {
		$.post('progress.action', function(data) {

			if (data.currentItem == 0) {
				$("#m").text('0%');
			} else if (data.state.rate != 100) {
				$("#m").text(
						data.state.readedBytes + '/' + data.state.totalBytes
								+ ':' + data.state.rate + '%');
			} else {
				$("#m").text(
						data.state.readedBytes + '/' + data.state.totalBytes
								+ ':' + '100% 上传完成!');
				window.clearInterval(id);
			}
			$("#img").html("");
			var num = data.state.rate / 10;

			for (var i = 1; i <= num; i++) {
				$("#img").append("<img src='./images/grid.gif' />");
			}
			for (var j = 1; j <= 10 - num; j++) {
				$("#img").append("<img src='./images/gray.gif' />");
			}
		}, 'json');
	}

	function submitForm() {
		if ($("#f1").val() == "") {
			alert('上传文件为空!!!');
			return;
		}
		id = window.setInterval(addressAction, 1000);
		$("form:first").submit();/*提交第一个表单*/
	}
</script>

<script type="text/javascript">
	function addFile() {
		var uploadHTML = document.createElement("input");
		uploadHTML.type = "file";
		uploadHTML.name = "upload";
		document.getElementById("files").appendChild(uploadHTML);
		uploadHTML.insertAdjacentHTML("beforeBegin", "上传文件 : ");

		uploadHTML = document.createElement("input");
		uploadHTML.type = "text";
		uploadHTML.name = "fileName";
		document.getElementById("files").appendChild(uploadHTML);
		uploadHTML.insertAdjacentHTML("beforeBegin", " &nbsp;&nbsp;文件名 : ");
		uploadHTML.insertAdjacentHTML("afterEnd", "<br>");
	}
</script>


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
	<s:fielderror></s:fielderror>

	<center>
		<s:iterator value="fieldErrors.upload">
			<s:property />
			<br>
		</s:iterator>
	</center>
	<button onclick="addFile()">添加文件</button>
	<form action="fileUpload" method="post" enctype="multipart/form-data">
		<input type="hidden" name="userid"
			value=<s:property value="#session.user.id"/>> <input
			type="hidden" name="did" value='<s:property value="dir.id"/>'>
		<span id="files"> 上传文件 : <input type="file" name="upload"
			id="f1">&nbsp;&nbsp;&nbsp; 文件名 : <input type="text"
			name="fileName"><br> <span id="m"></span><br /> <span
			id="img"></span>
		</span><input type="button" value="上传" onclick="submitForm()">
	</form>
	<form action="folder">
		<input type="hidden" type="hidden" name="directoryId"
			value='<s:property value="dir.id"/>'> <input type="text"
			name="name"><input type="submit" value="新建文件夹">
	</form>
	<a href='task?did=<s:property value="dir.id"/>'>离线下载</a>
	<center>
		<s:property value="result" />
		<br> <br>
	</center>
	<font size="5"> 
		<a href='index?id=<s:property value="dir.pdir.id" />'><s:property value="dir.path" /></a><s:property value="dir.dir" />/
	</font>
	<br>
	<br>
	<br>
	<center>
		<font size="4"> 
		<s:iterator value="dirs" var="c">
			<table align="center" border="0">
					<tr>
						<s:if test="#c.folder">
							<td><a href='index?id=<s:property value="#c.id" />'> <s:property
										value="#c.dir" /></a></td>
							<td>&nbsp;&nbsp;&nbsp; <a
								href='delete?id=<s:property value="#c.id"/>&pid=<s:property value="[1].dir.id"/>'>删除</a>
							</td>
						</s:if>
						<s:else>
							<td><s:if test="#c.category()">
									<a href='deal?id=<s:property value="#c.id" />'><s:property
											value="#c.dir" /></a>
								</s:if> <s:else>
									<s:property value="#c.dir" />
								</s:else></td>
							<td>&nbsp;&nbsp;&nbsp; <a
								href='delete?id=<s:property value="#c.id"/>&pid=<s:property value="[1].dir.id"/>'>删除</a>
							</td>
							<td>&nbsp;&nbsp;&nbsp; <a
								href='download?id=<s:property value="#c.id" />'>下载</a>
							</td>
						</s:else>
					</tr>
					<br>
			</table>
		</s:iterator>
		</font>
	</center>
	<s:debug></s:debug>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<title>My JSP 'OutlineDowload.jsp' starting page</title>

<meta charset = "utf-8">
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
	<center>
		<s:property value="result" />
		<form name="form" action="outlineDowload">
			<input type="hidden" name="did" value='<s:property value="did"/>'>
			<input type="text" name="link" style="width: 342px; height: 106px">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="submit" value="下载"
				style="width: 124px; height: 39px">
		</form>
	</center>
	<hr>
		<s:iterator value="tasks" var="t">
		<table border="0">
			<br>
			<tr>
				<td align="center" valign="center" style="width: 121px; ">
					<s:if test="#t.success">
						<img alt="complete" src="images/complete.png">
					</s:if>
				</td>
				<td style="width: 276px; ">
					<font size=4> 
						<s:property	value="#t.name" />
					</font>
				</td>
				<td style="width: 260px; "><s:property value="#c.link" /></td>
				<td style="width: 363px; "><s:property value="#t.current" />MiB/<s:property
						value="#t.size" />MiB&nbsp;&nbsp; <s:property value="#t.speed" />kb/s&nbsp;&nbsp;
					预计还有 : <s:property value="#t.ctime" />&nbsp;&nbsp;</td>
			</tr>
			<br>
		</table>
		<hr>
		</s:iterator>
</body>
</html>

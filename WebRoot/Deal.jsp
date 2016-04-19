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

<title>My JSP 'Deal.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body bgcolor="#101010">
	<%-- <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
		codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0"
		width="1000" height="800">

		<param name="movie" value="db/Flvplayer.swf">

		<param name="quality" value="high">

		<param name="allowFullScreen" value="true" />

		<param name="FlashVars" value='vcastr_file=<%=basePath%><s:property value="file" />' />

		<embed src="db/Flvplayer.swf" allowFullScreen="true"
			FlashVars='vcastr_file=<%=basePath%><s:property value="file" />' quality="high"
			pluginspage="http://www.macromedia.com/go/getflashplayer"
			type="application/x-shockwave-flash" width="490" height="390"></embed>

	</object>
	<%=basePath%><s:property value="file" /> --%>
	<font size="4" color="white">
		<center>
			<s:property value="result" /><br>
		</center>
		<s:property value="dir" />
	</font>
	<br>
	<center>
		<video id="my_video_1" class="video-js vjs-default-skin" controls
			preload="auto" width = "800" height = "600" data-setup="{}"> <source
			src='<s:property value="fileName" />' type="video/mp4"></video>
	</center>
</body>
</html>

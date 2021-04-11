<%@ include file="/web/common/includes/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html dir="<fmt:message key="commons.language.dir"/>" xml:lang="arabic" lang="arabic" >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="commons.caption.client"/></title>
<link rel="stylesheet" href="/Requests/web/common/css/all.css">
<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/css/autoComplete.css">
<link type="text/css" href="/Requests/web/common/css/main.css"
	rel="stylesheet" media="screen" />
<link href="/Requests/web/common/css/tab_en.css" rel="stylesheet" type="text/css" />
<link href="/Requests/web/common/css/tab_ar.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="/Requests/web/common/js/toolTipContent.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/Requests/web/common/js/popup.js"></SCRIPT>
<script type="text/javascript" src="/Requests/web/common/js/myLiveSearch.js"></script>
<script type="text/javascript" src="/Requests/web/common/js/Tokenizer.js"></script>
<script type="text/javascript" src="/Requests/web/common/js/jquery.min.js"></script>
   <script type="text/javascript" src="/Requests/web/common/js/jquery.calendars.js"></script> 
   <script type="text/javascript" src="/Requests/web/common/js/jquery.calendars.plus.js"></script>
   <link  rel="stylesheet" type="text/css" href="/Requests/web/common/css/jquery.calendars.picker.css"/>
   <script type="text/javascript" src="/Requests/web/common/js/jquery.calendars.picker.js"></script>
   <script type="text/javascript" src="/Requests/web/common/js/jquery.calendars.islamic.js"></script>
  
<script type="text/javascript" src="/Requests/web/common/js/extAjax.js"></script>
<%
	String locale = ((String) request.getSession().getAttribute(
			"locale"));
	if (locale != null && locale.equalsIgnoreCase("en")) {
%>
<link href="/Requests/web/common/css/tables_en.css" rel="stylesheet"	type="text/css" />
<%
	} else {
%>
<link href="/Requests/web/common/css/tables_ar.css" rel="stylesheet"	type="text/css" />
<%
	}
%>
</head>
<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onload="liveSearchInit()">
<table width="100%"  border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td >
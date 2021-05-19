<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter"%>
<%@ page
	import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page import="org.acegisecurity.AuthenticationException"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html dir="<fmt:message key="commons.language.dir"/>"
	xml:lang="<fmt:message key="commons.language.code"/>"
	lang="<fmt:message key="language.dir"/>">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><abc:i18n property="commons.caption.client" /> <fmt:message
	key="commons.caption.client" /></title>
<link rel="stylesheet" href="/Requests/web/common/css/all.css">
<link href="/Requests/web/common/css/tables_ar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="/Requests/web/common/css/ddsmoothmenu_ar.css" />
<script type="text/javascript"
	src="/Requests/web/common/js/ddsmoothmenu_ar.js"></script>
<link href="/Requests/web/common/css/vertical_menu_ar.css" rel="stylesheet"
	type="text/css" />
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="120" colspan="7" valign="top" align="center"><img
			src="/Requests/web/common/images/Requests_header.jpg" width="95%" height="120" /></td>
	</tr>
</table>

<BR>
<BR>
<BR>
<BR>
<BR>
<BR>

<form id="login" name="login" method="POST"><input type="hidden"
	name="activeLink" value="linkOne" />

<table align=center width=250 cellpadding="0" cellspacing="0" border="0">
	<!--tr>
		<td colspan=2 bgcolor="#6A6A6A" height=1></td>
	</tr-->
	<tr>

		<table rules="all" align="center" width="250" class="sofT">
			<tr>
				<td class="formBodControl" rowspan="5"><img
					src="/Requests/web/common/images/login-keys2.gif"></td>
				<td>
			</tr>
			<tr>
				<td colspan=2 class="formBodControl">
				<div style="text-align: center;"><font
					style="color: #0066CC; font-size: 14px; font-weight: 600;">
				<abc:i18n property="commons.caption.welcome" /> <fmt:message
					key="commons.caption.welcome" /> </font><br>
				<font style="color: #000066;"> <abc:i18n
					property="commons.caption.pleaseEnterEmail" /> <fmt:message
					key="commons.caption.pleaseEnterEmail" /> </font></div>
				</td>
			</tr>
			<tr>
				<td colspan=2 class="formBodControl"><spring:bind path="user.*">
					<c:if test="${not empty status.errorMessages}">
						<div><c:forEach var="error" items="${status.errorMessages}">
							<font color="red"> <c:out value="${error}"
								escapeXml="false" /><br />
							</font>
						</c:forEach></div>
					</c:if>
				</spring:bind></td>
			</tr>
			<tr>
				<td width="10%" nowrap class="formReq"><abc:i18n
					property="commons.caption.email" /> <fmt:message
					key="commons.caption.email" /></td>
				<td class="formBodControl"><spring:bind
					path="user.employee.email">
					<input type="text" size=30 maxlength=40 name="${status.expression}"
						value="${status.value}" />
				</spring:bind></td>
			</tr>
			<tr>
				<td colspan="2"  class="formBodControl">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<abc:i18n property="commons.button.enter" /> <input
					type="submit" name="submit"
					value="<fmt:message key="commons.button.enter"/>" class="button">
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<!--tr>
		<td height="1" colspan="2" bgcolor="#6A6A6A"></td>
	</tr-->
</table>
</form>
<br>
<br>
<br>
<br>
<br>
<br>
<br>


<table width="100%" border="0" cellspacing="0" cellpadding="0"
	bgcolor="#91C8FF">
	<tr>
		<td></td>
	</tr>
	<tr>
		<td class="englishCopyRight"><abc:i18n
			property="commons.footer.english" /> <fmt:message
			key="commons.footer.english" /></td>
	</tr>
	<tr>
		<td class="arabicCopyRight"><abc:i18n
			property="commons.footer.arabic" /> <fmt:message
			key="commons.footer.arabic" /></td>

	</tr>
</table>
</body>
</html>

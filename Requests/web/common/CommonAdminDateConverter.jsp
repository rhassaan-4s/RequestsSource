<%@ include file="/web/common/includes/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/web/common/includes/popupheader.jsp"%>
<html dir="<fmt:message key="commons.language.dir"/>"
	xml:lang="<fmt:message key="commons.language.code"/>"
	lang="<fmt:message key="commons.language.code"/>">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="commons.caption.name" /></title>
<link rel="stylesheet" href="/Requests/web/common/css/all.css">
</head>

<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0"
	marginwidth="0" marginheight="0">
<SCRIPT LANGUAGE="JavaScript" SRC="/Requests/web/common/js/popup.js"></SCRIPT>
<abc:security property="280" />
<br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<!--tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	
	<tr>
		<td class="tableHeader"> <abc:i18n property="commons.header.dateConverter"/><fmt:message key="commons.header.dateConverter"/></td><td align="left"></td>
	</tr>

	<tr>
		<td colspan="2" bgcolor="#5A718B" height="2"></td>
	</tr>

	<tr>
		<td colspan="2" height="1"></td>
	</tr>

	<tr>
		<td colspan="2" height="20"></td>
	</tr-->

	<tr>
		<td><spring:bind path="dateConverter.*">
			<c:if test="${not empty status.errorMessages}">
				<div><c:forEach var="error" items="${status.errorMessages}">
					<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
					</font>
				</c:forEach></div>
			</c:if>
		</spring:bind></td>
	</tr>

	<tr>
		<td colspan="2">
		<form id="dateConverter" name="dateConverter" method="POST"
			action="<c:url value="/common/commonAdminDateConverter.html"/>">

		<table rules="all" align="center" width="70%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap><abc:i18n
					property="commons.header.dateConverter" /><fmt:message
					key="commons.header.dateConverter" /></td>
			</tr>
			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="commons.caption.date" /><fmt:message
					key="commons.caption.date" /></td>
				<td class="formBodControl"><spring:bind
					path="dateConverter.date">
					<input type="text" size="10" maxlength=10
						name="${status.expression}" value="${dateConverter.date}" />
				</spring:bind></td>
			</tr>
			
			<tr>
				<td nowrap class="formBod"><abc:i18n
					property="commons.caption.convertedDate" /><fmt:message
					key="commons.caption.convertedDate" /></td>
				<td class="formBodControl">${dateConverter.convertedDate}</td>
			</tr>

			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>


			<tr id="btn">
				<td colspan="2" align=center><abc:i18n
					property="commons.button.convertDate" /> <input type="submit"
					name="save"
					value=" <fmt:message key="commons.button.convertDate"/>"
					class="button">&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</table>
		</form>

		</td>

	</tr>

</table>

</body>
</html>

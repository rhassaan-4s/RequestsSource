<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ include file="/web/common/includes/popupheader.jsp"%>


<script language="JavaScript">
</script>

<abc:security property="289" />
<br>
<table rules="all" align="center" width="70%" class="sofT" id="tblData">
	<tr id="head_1_ep">
		<td class="helpTitle" colspan="7" nowrap><abc:i18n
			property="commons.button.ipAdd" /> <fmt:message
			key="commons.button.ipAdd" /></td>
	</tr>
	<tr>
		<td nowrap class="helpHed">&nbsp; <abc:i18n property="commons.button.ipAdd" />
		<fmt:message key="commons.button.ipAdd" />&nbsp;</td>
		

		<td class="helpHed">&nbsp;</td>

	</tr>
	<c:forEach var="result" items="${ip}">
		<tr>
			<td class="helpBod">${result.ip}</td>
			 <TD class="helpBod">&nbsp; <abc:i18n property="commons.button.delete" /> <a
				href="ipView.html?userId=${userId}&deleteId=${result.id}"
				class="actionLink"><fmt:message key="commons.button.delete" /></a></TD>
		</tr>
	</c:forEach>
</table>
<%@ include file="/web/common/includes/popupfooter.jsp"%>


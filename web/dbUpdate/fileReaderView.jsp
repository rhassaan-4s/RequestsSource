<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>

<br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n property="dbUpdate.caption.updateDB" />
		<fmt:message key="dbUpdate.caption.updateDB" /></td>
		<td align="left"></td>
	</tr>
	<tr>
		<td colspan="2" bgcolor="#5A718B" height="2"></td>
	</tr>
	<tr>
		<td colspan="2" height="1"></td>
	</tr>

	<tr>
		<td colspan="2" height="20"></td>
	</tr>

	<tr>
		<td colspan="2">


		<table width="100%" border=0 cellpadding=0 cellspacing=0
			class="listTable">
			<TR>
				<TD COLSPAN="15" class="blackLine"><IMG
					SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
			</TR>
			<tr height="20" bgcolor="#DDDDDD">

				<td nowrap>&nbsp; <abc:i18n property="commons.caption.Tokenz" />
				<fmt:message key="commons.caption.totalNumber" />&nbsp;</td>
				<TD WIDTH=1 NOWRAP><IMG
					SRC="<c:url value="/web/common/images/s.gif"/>"></TD>

			</tr>
			<TR>
				<TD COLSPAN="15 " class="blackLine"><IMG
					SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
			</TR>


			<td valign=TOP style="padding:0px 3px 0px 3px;">${Tokenz}</td>
			<TD BGCOLOR="#666666" WIDTH=1 NOWRAP><IMG
				SRC="<c:url value="/web/common/images/s.gif"/>"></TD>



			<TR>
				<TD COLSPAN="15" class="blackLine"><IMG
					SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
			</TR>
		</table>

		<table>
			<tr>
			</tr>

		</table>


		</td>
	</tr>
</table>

<%@ include file="/web/common/includes/footer.jsp"%>
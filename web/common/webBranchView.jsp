<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<abc:security property="117" />
<br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
			property="stores.header.branches" /> <fmt:message
			key="stores.header.branches" /></td>
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


		<table width="50%" border=0 cellpadding=0 cellspacing=0
			class="listTable">
			<TR>
				<TD COLSPAN="6" class="blackLine"><IMG
					SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
			</TR>
			<tr height="20" bgcolor="#DDDDDD">

				<td nowrap>&nbsp; <abc:i18n property="commons.caption.description" />
				<fmt:message key="commons.caption.description" />&nbsp;</td>
				<TD WIDTH=1 NOWRAP><IMG
					SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
					
				<td nowrap>&nbsp; <abc:i18n property="commons.caption.address" />
				<fmt:message key="commons.caption.address" />&nbsp;</td>
				<TD WIDTH=1 NOWRAP><IMG
					SRC="<c:url value="/web/common/images/s.gif"/>"></TD>

				<td nowrap colspan="3">&nbsp;&nbsp;</td>

			</tr>
			<TR>
				<TD COLSPAN="6" class="blackLine"><IMG
					SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
			</TR>

			<c:forEach var="result" items="${branches}">
				<tr height=20 bgcolor="#F8F8F8">
					<td valign=TOP style="padding:0px 3px 0px 3px;">${result.name}</td>
					<TD BGCOLOR="#666666" WIDTH=1 NOWRAP><IMG
						SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
					<td valign=TOP style="padding:0px 3px 0px 3px;">
						<iframe id="latlongmape" width="450" height="250" style="border:0;width:98%" class="shadow"
						        src='https://www.google.com/maps/embed/v1/place?key=AIzaSyBeCCPQ7VdCQiJxjXGfVO98LyirL1-hC74&maptype=roadmap&q=${result.branchLat},${result.branchLong}' allowfullscreen>
						</iframe>
					</td>	
					<TD BGCOLOR="#666666" WIDTH=1 NOWRAP><IMG
						SRC="<c:url value="/web/common/images/s.gif"/>"></TD>

					<td valign=TOP width="10%"><NOBR>&nbsp; <abc:i18n
						property="commons.button.edit" /> <a
						href="webBranchForm.html?branchId=${result.branch}"
						class="actionLink"> <fmt:message key="commons.button.edit" /></a>&nbsp;</NOBR></td>

				</tr>
			</c:forEach>

			<TR>
				<TD COLSPAN="6" class="blackLine"><IMG
					SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
			</TR>
		</table>

		<table>
			<tr>
			</tr>

			<tr>
				<td align="center"><abc:i18n property="commons.button.add" /> <input
					type="button" name="add"
					value=" <fmt:message key="commons.button.add"/> " class="button"
					onclick="window.location='webBranchForm.html?companyId=${companyId}'"></td>
			</tr>
		</table>


		</td>
	</tr>
</table>

<%@ include file="/web/common/includes/footer.jsp"%>
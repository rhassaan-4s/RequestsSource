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
			property="commons.caption.empBasicView" /> <fmt:message
			key="commons.caption.empBasicView" /></td>
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
				<TD COLSPAN="8" class="blackLine"><IMG
					SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
			</TR>
			<tr height="20" bgcolor="#DDDDDD">

					<td nowrap>&nbsp; <abc:i18n property="commons.caption.empCode" />
						<fmt:message key="commons.caption.empCode" />&nbsp;
					</td>
					<TD WIDTH=1 NOWRAP><IMG
						SRC="<c:url value="/web/common/images/s.gif"/>"></TD>

					<td nowrap>&nbsp; <abc:i18n property="commons.caption.name" />
						<fmt:message key="commons.caption.name" />&nbsp;
					</td>
					<TD WIDTH=1 NOWRAP><IMG
						SRC="<c:url value="/web/common/images/s.gif"/>"></TD>

					<td nowrap>&nbsp; <abc:i18n
							property="commons.caption.englishName" /> <fmt:message
							key="commons.caption.englishName" />&nbsp;
					</td>
					<TD WIDTH=1 NOWRAP><IMG
						SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
					<td nowrap colspan="3">&nbsp;&nbsp;</td>

				</tr>
			<TR>
				<TD COLSPAN="8" class="blackLine"><IMG
					SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
			</TR>

			<c:forEach var="result" items="${employees}">
			<c:if test="${result.empCode!='00000001'}">
				<tr height=20 bgcolor="#F8F8F8">
					<td valign=TOP style="padding:0px 3px 0px 3px;">${result.empCode}</td>
					<TD BGCOLOR="#666666" WIDTH=1 NOWRAP><IMG
						SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
						
					<td valign=TOP style="padding:0px 3px 0px 3px;">${result.empName}</td>
					<TD BGCOLOR="#666666" WIDTH=1 NOWRAP><IMG
						SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
						
						<td valign=TOP style="padding:0px 3px 0px 3px;">${result.e_emp_name}</td>
					<TD BGCOLOR="#666666" WIDTH=1 NOWRAP><IMG
						SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
						
					<TD BGCOLOR="#666666" WIDTH=1 NOWRAP><IMG
						SRC="<c:url value="/web/common/images/s.gif"/>"></TD>

					<td valign=TOP width="10%"><NOBR>&nbsp; <abc:i18n
						property="commons.button.edit" /> <a
						href="commonAdminEmpBasic.html?empCodeHidden=${result.empCode}"
						class="actionLink"> <fmt:message key="commons.button.edit" /></a>&nbsp;</NOBR></td>

				</tr>
				</c:if>
			</c:forEach>

			<TR>
				<TD COLSPAN="8" class="blackLine"><IMG
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
					onclick="window.location='commonAdminEmpBasic.html'"></td>
			</tr>
		</table>


		</td>
	</tr>
</table>

<%@ include file="/web/common/includes/footer.jsp"%>
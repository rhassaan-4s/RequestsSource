<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<abc:security property="291"/><br>
<table width="90%"  border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader">
		<abc:i18n property="security.header.fields"/>
		<fmt:message key="security.header.fields"/></td><td align="left"></td>
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
	
	
			<table width="60%" border=0 cellpadding=0 cellspacing=0  class="listTable">
				<TR><TD COLSPAN="9" class="blackLine"><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD></TR>
				<tr height="20" bgcolor="#DDDDDD">
	
					<td nowrap colspan=2>&nbsp;
					<abc:i18n property="security.caption.fieldName"/>
					<fmt:message key="security.caption.fieldName"/>&nbsp;</td>
					<TD WIDTH=1 NOWRAP><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
					
				</tr>

			<TR><TD COLSPAN="9" class="blackLine"><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD></TR>				
			<c:forEach var="result" items="${fields}" >
					<tr height=20 bgcolor="#F8F8F8">
					<TD  BGCOLOR="#666666" WIDTH=1 NOWRAP ><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
						<td valign=TOP style="padding:0px 3px 0px 3px;" width="99%"><a href="javascript:createWindow('editSecurity.html?fieldId=${result.id}')" class="actionLink">${result.name}</a></td>
						
					</tr>		
				</c:forEach>
				<TR><TD COLSPAN="9" class="blackLine"><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD></TR>				
			</table>
		</td>
</table>


<%@ include file="/web/common/includes/footer.jsp" %>
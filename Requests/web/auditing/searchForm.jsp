<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<abc:security property="282"/><br>
<table width="90%"  border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1" colspan="2"></td>
	</tr>
	<tr>
		<td class="tableHeader">
		<abc:i18n property="auditing.header.auditingSearch"/>
		<fmt:message key="auditing.header.auditingSearch"/></td>
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
			<table width="70%" border="0" cellpadding="0" cellspacing="0"  class="listTable">
				<TR><TD COLSPAN="12" class="blackLine"><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD></TR>
				<tr height="20" bgcolor="#DDDDDD">
	
					<td nowrap>&nbsp;
					<abc:i18n property="auditing.caption.actions"/>
					<fmt:message key="auditing.caption.actions"/>&nbsp;</td>
					<TD WIDTH=1 NOWRAP><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
		
					<td nowrap>&nbsp;
					<abc:i18n property="commons.caption.username"/>
					<fmt:message key="commons.caption.username"/>&nbsp;</td>
					<TD WIDTH=1 NOWRAP><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
		
					<td nowrap>&nbsp;
					<abc:i18n property="commons.caption.date"/>
					<fmt:message key="commons.caption.date"/>&nbsp;</td>
					<TD WIDTH=1 NOWRAP><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
		
					<td nowrap>&nbsp;
					<abc:i18n property="auditing.caption.class"/>
					<fmt:message key="auditing.caption.class"/>&nbsp;</td>
					<TD WIDTH=1 NOWRAP><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
					
					<td nowrap>&nbsp;
					<abc:i18n property="auditing.caption.classDetails"/>
					<fmt:message key="auditing.caption.classDetails"/>&nbsp;</td>
					<TD WIDTH=1 NOWRAP><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
					
					<td nowrap>&nbsp;
					<abc:i18n property="auditing.caption.details"/>
					<fmt:message key="auditing.caption.details"/>&nbsp;</td>
					<TD WIDTH=1 NOWRAP><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
		
				</tr>
				<TR><TD COLSPAN="12 " class="blackLine"><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD></TR>
	
				<c:forEach var="result" items="${search}" >
					<tr height=20 bgcolor="#F8F8F8">
						<td valign=TOP style="padding:0px 3px 0px 3px;" >${result.message}</td>
						<TD  BGCOLOR="#666666" WIDTH=1 NOWRAP ><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
		
						<td valign=TOP style="padding:0px 3px 0px 3px;" >${result.userName}</td>
						<TD BGCOLOR="#666666" WIDTH=1 NOWRAP ><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
						
						<td valign=TOP style="padding:0px 3px 0px 3px;" >${result.created}</td>
						<TD BGCOLOR="#666666" WIDTH=1 NOWRAP ><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
						
						<td valign=TOP style="padding:0px 3px 0px 3px;" >${result.entityClassName}</td>
						<TD BGCOLOR="#666666" WIDTH=1 NOWRAP ><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
						
						<td valign=TOP style="padding:0px 3px 0px 3px;" >${result.entityDisplayName}</td>
						<TD BGCOLOR="#666666" WIDTH=1 NOWRAP ><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD>
						
						<td>
							<table border="1" cellpadding="0" cellspacing="0">
							<tr bgcolor="#CCCCCC">
							<td >
							
							<abc:i18n property="auditing.caption.propertyName"/>
							<fmt:message key="auditing.caption.propertyName"/>
							</td>
							<td>
							<abc:i18n property="auditing.caption.oldValue"/>
							<fmt:message key="auditing.caption.oldValue"/>
							</td>
							<td>
							<abc:i18n property="auditing.caption.newValue"/>
							<fmt:message key="auditing.caption.newValue"/>
							</td>
							</tr>
								<c:forEach var="reportProperty" items="${result.auditLogDetails}" >
								
									<tr>
										<TD>${reportProperty.propertyName}</TD>
										<TD>${reportProperty.removed}</TD>
										<TD>${reportProperty.added}</TD>
									</tr>
								
								</c:forEach>
							</table>
						</TD>
						
					</tr>		
					<TR><TD COLSPAN="12" class="blackLine"><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD></TR>
				</c:forEach>
				
			</table>
		</td>
	</tr>
</table>

<%@ include file="/web/common/includes/footer.jsp" %>
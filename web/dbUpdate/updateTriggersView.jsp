<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	

	<tr>
		<td colspan="2">
		<br /><br />
		<table rules="all" align="center" width="70%" class="sofT">
			<tr id="head_1_ep">
				<td class="helpTitle" colspan=4 nowrap><abc:i18n
					property="dbUpdate.header.updateTriggersScript" /><fmt:message
					key="dbUpdate.header.updateTriggersScript" /></td>
			</tr>
			
			<tr>
				<TD colspan="100" align="center" class="formBodControl"><c:if
					test="${noErrors=='true'}">
					<abc:i18n property="dbUpdate.caption.noErrors" />
					<fmt:message key="dbUpdate.caption.noErrors" />
				</c:if> <c:if test="${noErrors=='false'}">
					<font color="red"> <abc:i18n
						property="dbUpdate.caption.errorInBlockNo" /> <fmt:message
						key="dbUpdate.caption.errorInBlockNo" /> &nbsp;${currentIndex + 1}
					
					</font>
				</c:if></TD>
			</tr>
		</table>
	</td>
	</tr>



	<!--tr>
		<td colspan="2">
		<br /><br />
		<table rules="all" align="center" width="70%" class="sofT">
			<tr id="head_1_ep">
				<td class="helpTitle" colspan=4 nowrap><abc:i18n
					property="dbUpdate.header.updateDBByCode" /><fmt:message
					key="dbUpdate.header.updateDBByCode" /></td>
			</tr>
			<c:forEach items="${codeMigrationList}" var="codeMig">
				<tr>
					<td nowrap class="requiredInput"><abc:i18n
						property="dbUpdate.caption.migrationDescription" /> <fmt:message
						key="dbUpdate.caption.migrationDescription" /></td>
					<td>&nbsp;${codeMig.description}</td>
				</tr>

				<tr>
					<td nowrap class="requiredInput"><abc:i18n
						property="dbUpdate.caption.migrationComment" /> <fmt:message
						key="dbUpdate.caption.migrationComment" /></td>
					<td>&nbsp;${codeMig.comment}</td>
				</tr>
				<tr>
					<td colspan=5>&nbsp;</td>
				</tr>
			</c:forEach>
		</table>
		</td>
	</tr-->

	<tr>
		<td colspan="2" align="left" style="font-size: 15px; color: #990000; font-weight: bold;"><br /><br /><abc:i18n
			property="dbUpdate.caption.inCaseOfAnyErrorsPleaseSaveThisPage" /> <fmt:message
			key="dbUpdate.caption.inCaseOfAnyErrorsPleaseSaveThisPage" /></td>
	</tr>

</table>

<%@ include file="/web/common/includes/footer.jsp"%>

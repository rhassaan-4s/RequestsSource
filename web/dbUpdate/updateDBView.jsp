<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<!--tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n property="dbUpdate.header.updateDB" />
		<fmt:message key="dbUpdate.header.updateDB"/></td>
		<td align="left"></td>
	</tr>
	<tr>
		<td colspan="2" bgcolor="#5A718B" height="2"></td>
	</tr>
	<tr>
		<td colspan="2" height="1"></td>
	</tr-->

	<tr>
		<td colspan="2">
		<br /><br />
		<table rules="all" align="center" width="70%" class="sofT">
			<tr id="head_1_ep">
				<td class="helpTitle" colspan=4 nowrap><abc:i18n
					property="dbUpdate.header.updateDBByScript" /><fmt:message
					key="dbUpdate.header.updateDBByScript" /></td>
			</tr>
			<tr>
				<td nowrap class="formReq" width="50%"><abc:i18n
					property="dbUpdate.caption.oldIndex" /> <fmt:message
					key="dbUpdate.caption.oldIndex" /></td>
				<td class="formBodControl">&nbsp;${oldIndex}</td>
			</tr>

			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="dbUpdate.caption.lastDesiredIndex" /> <fmt:message
					key="dbUpdate.caption.lastDesiredIndex" /></td>
				<td class="formBodControl">&nbsp;${lastDesiredIndex}</td>
			</tr>

			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="dbUpdate.caption.currentIndex" /> <fmt:message
					key="dbUpdate.caption.currentIndex" /></td>
				<td class="formBodControl">&nbsp;${currentIndex}</td>
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
					<p><abc:i18n property="dbUpdate.caption.scriptStopedAtBlockNo" />
					<fmt:message key="dbUpdate.caption.scriptStopedAtBlockNo" />
					&nbsp;${currentIndex}
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

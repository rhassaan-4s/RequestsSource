<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<script type="text/javascript">
	
</script>

<form id="activityForm" name="activityForm" method="POST"
	action="<c:url value="/timesheet/activityForm.html"/>">

<form:form method="POST"
					action="/Requests/timesheet/activityForm.html"
					modelAttribute="activity">


<input type="hidden" id="code" name="code"
			value="${activityCode}" />
			
	<table width="90%" border="0" cellspacing="0" cellpadding="0" 
		style="padding-right: 10px">

		<tr>
			<td colspan="2">
			<h2 style="color: red;">
					<form:errors path="activity.*" />
				</h2>
			
			<spring:bind path="activity.*">
					<c:if test="${not empty status.errorMessages}">
						<div>
							<c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}"
										escapeXml="false" /><br />
								</font>
							</c:forEach>
						</div>
					</c:if>
				</spring:bind></td>
		</tr>

		<tr>
			<table align="center" width="66%" class="sofT">
				<tr id="head_1_ep">
					<td class="helpTitle" colspan="2" nowrap><abc:i18n
							property="timesheet.header.activity" /> <fmt:message
							key="timesheet.header.activity" /></td>
				</tr>

				<tr>

					<c:set var="check" value="" />
					<c:if test="${activityCode!=null && activityCode!=''}">
						<c:set var="check" value="disabled" />
					</c:if>

					<c:if test="${check=''}">
						<td nowrap class="formReq" width="30%"><abc:i18n
								property="timesheet.caption.activityCode" /> <fmt:message
								key="timesheet.caption.activityCode" /></td>


						<td class="formBodControl"><spring:bind
								path="activity.activity">
								<input size="8" maxlength="8" type="text"
									name="${status.expression}" value="${status.value}"
									readonly="readonly" />
							</spring:bind></td>
					</c:if>
				</tr>

				<tr>

					<td nowrap class="formReq" width="30%"><abc:i18n
							property="timesheet.caption.activityName" /> <fmt:message
							key="timesheet.caption.activityName" /></td>

					<td class="formBodControl" width="70%"><spring:bind
							path="activity.name">
							<input size="40" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
				</tr>


				<tr>
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="timesheet.caption.activityEnName" /> <fmt:message
							key="timesheet.caption.activityEnName" /></td>

					<td class="formBodControl" width="70%"><spring:bind
							path="activity.ename">
							<input size="40" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
				</tr>

				<tr id="btn">
					<td colspan="2" align="center"><abc:i18n
							property="commons.button.save" /><input type="submit"
						name="save" value="<fmt:message key="commons.button.save"/>"
						class="button" />&nbsp;&nbsp;&nbsp; <abc:i18n
							property="commons.button.cancel" /><input type="reset"
						name="cancel" value="<fmt:message key="commons.button.cancel"/>"
						class="button" /></td>
				</tr>
			</table>
		</tr>

	</table>
</form:form>

<%@ include file="/web/common/includes/footer.jsp"%>

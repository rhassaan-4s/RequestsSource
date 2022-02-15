<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<script type="text/javascript">
	
</script>

<form id="timesheetSpecs" name="timesheetSpecs" method="POST"
	action="<c:url value="/timesheet/timesheetSpecs.html"/>">

	<table width="90%" border="0" cellspacing="0" cellpadding="0"
		style="padding-right: 10px">

		<tr>
			<td colspan="2"><spring:bind path="specs.*">
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
					<td class="helpTitle" colspan="4" nowrap><abc:i18n
							property="timesheet.header.timesheetSpecs" /> <fmt:message
							key="timesheet.header.timesheetSpecs" /></td>
				</tr>
				<tr>
					<td nowrap class="formReq" width="30%">&nbsp;</td>
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="timesheet.caption.isUsed" /> <fmt:message
							key="timesheet.caption.isUsed" /></td>
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="timesheet.caption.name" /> <fmt:message
							key="timesheet.caption.name" /></td>
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="timesheet.caption.ename" /> <fmt:message
							key="timesheet.caption.ename" /></td>
					
				</tr>
				<tr>

					<td nowrap class="formBodControl"><abc:i18n
							property="timesheet.caption.part1" /> <fmt:message
							key="timesheet.caption.part1" /></td>
					<td class="formBod"><spring:bind path="specs.is_used1">
							<input type="checkbox" name="${status.expression}"
								${status.value=='1' ? 'checked' : ''} />
						</spring:bind></td>
					<td class="formBodControl" width="70%"><spring:bind
							path="specs.part1_name">
							<input size="40" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>

					<td class="formBodControl" width="70%"><spring:bind
							path="specs.part1_ename">
							<input size="40" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
				</tr>


				<tr>

					<td nowrap class="formBodControl"><abc:i18n
							property="timesheet.caption.part2" /> <fmt:message
							key="timesheet.caption.part2" /></td>
					<td class="formBod"><spring:bind path="specs.is_used2">
							<input type="checkbox" name="${status.expression}"
								${status.value=='1' ? 'checked' : ''} />
						</spring:bind></td>
					<td class="formBodControl" width="70%"><spring:bind
							path="specs.part2_name">
							<input size="40" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>

					<td class="formBodControl" width="70%"><spring:bind
							path="specs.part2_ename">
							<input size="40" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
				</tr>

				<tr>

					<td nowrap class="formBodControl"><abc:i18n
							property="timesheet.caption.part3" /> <fmt:message
							key="timesheet.caption.part3" /></td>
					<td class="formBod"><spring:bind path="specs.is_used3">
							<input type="checkbox" name="${status.expression}"
								${status.value=='1' ? 'checked' : ''} />
						</spring:bind></td>
					<td class="formBodControl" width="70%"><spring:bind
							path="specs.part3_name">
							<input size="40" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>

					<td class="formBodControl" width="70%"><spring:bind
							path="specs.part3_ename">
							<input size="40" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
				</tr>

				<tr id="btn">
					<td colspan="4" align="center"><abc:i18n
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
</form>


<%@ include file="/web/common/includes/footer.jsp"%>

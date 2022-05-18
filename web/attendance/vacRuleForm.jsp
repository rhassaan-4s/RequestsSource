<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<script type="text/javascript">
	
</script>

<style>
.remove_user {
	width: 24px;
	height: 24px;
	float: left;
	cursor: pointer;
	background-repeat: no-repeat;
	background-position: center center;
	background-image: url("/Requests/web/common/images/publish_r.jpg");
}

.add_user {
	width: 24px;
	height: 24px;
	float: left;
	cursor: pointer;
	background-repeat: no-repeat;
	background-position: center center;
	background-image: url("/Requests/web/common/images/icon-16-add.png");
}

.edit_group {
	width: 24px;
	height: 24px;
	float: left;
	cursor: pointer;
	background-repeat: no-repeat;
	background-position: center center;
	background-image: url("/Requests/web/common/images/edit_button.gif");
}
</style>

<form id="vacRuleForm" name="vacRuleForm" method="POST"
	action="<c:url value="/attendance/vacRuleForm.html"/>">


	<table width="90%" border="0" cellspacing="0" cellpadding="0"
		style="padding-right: 10px">

		<tr>
			<td><spring:bind path="rules.*">
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
			<td>
				<table align="center" class="sofT">
					<tr id="head_1_ep">
						<td class="helpTitle" colspan="4" nowrap><abc:i18n
								property="attendance.header.vacRules" /> <fmt:message
								key="attendance.header.vacRules" /></td>
					</tr>


					<tr>
						<td>
							<table>
								<tr>
									<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="attendance.caption.vacCode" /> <fmt:message
											key="attendance.caption.vacCode" /></td>
									<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="attendance.caption.vacName" /> <fmt:message
											key="attendance.caption.vacName" /></td>
									<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="attendance.caption.serviceYears" /> <fmt:message
											key="attendance.caption.serviceYears" /></td>
									<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="attendance.caption.entitled" /> <fmt:message
											key="attendance.caption.entitled" /></td>
									<td class="helpHed"></td>

								</tr>

								<c:forEach items="${rules.rules}" var="record" varStatus="loop">
									<tr height=20 bgcolor="#F8F8F8">
										<td class="helpBod" nowrap>${record.vacation.vacation}</td>

										<td class="helpBod" nowrap><spring:bind
												path="rules.rules[${loop.index}].vacation">
												<select name="vacation" id="${status.expression}">
													<option value=""><fmt:message
															key="commons.caption.select" /></option>
													<c:forEach items="${vacList}" var="vac">
														<option value="${vac.vacation}"
															${vac.vacation== rules.rules[loop.index].vacation.vacation?'selected':''}>${vac.name}</option>
													</c:forEach>
												</select>
											</spring:bind></td>

										<td class="helpBod" nowrap><spring:bind
												path="rules.rules[${loop.index}].srvYear">
												<input size="3" type="text" name="${status.expression}"
													value="${status.value}" />
											</spring:bind></td>
										<td class="helpBod" nowrap><spring:bind
												path="rules.rules[${loop.index}].entitled">
												<input size="2" type="text" name="${status.expression}"
													value="${status.value}" />
											</spring:bind></td>
										<td align="center" class="helpBod"><span
											class="remove_user"
											onclick="removeRule(${record.vacation.vacation},${record.srvYear});"
											title="<fmt:message
										key="commons.button.delete" />">
										</span></td>

									</tr>
								</c:forEach>


								<tr id="btn">
									<td colspan="4" align="center"><abc:i18n
											property="commons.button.add" /> <input type="button"
										name="add" value="<fmt:message key="commons.button.add"/>"
										class="button" onclick="addRow();" /> &nbsp;&nbsp;&nbsp; <input
										type="submit" name="save"
										value="<fmt:message key="commons.button.save"/>"
										class="button" />&nbsp;&nbsp;&nbsp; <abc:i18n
											property="commons.button.cancel" /><input type="reset"
										name="cancel"
										value="<fmt:message key="commons.button.cancel"/>"
										class="button" /></td>
								</tr>
							</table>
					</tr>

				</table>
			</td>
		</tr>
</form>


<%@ include file="/web/common/includes/footer.jsp"%>

<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<script type="text/javascript">
	
</script>

<form id="vacationForm" name="vacationForm" method="POST"
	action="<c:url value="/attendance/vacationForm.html"/>">

<input type="hidden" id="vacationCode" name="vacationCode"
			value="${vacationCode}" />
			
	<table width="90%" border="0" cellspacing="0" cellpadding="0" 
		style="padding-right: 10px">

		<tr>
			<td colspan="2"><spring:bind path="vacation.*">
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
							property="attendance.header.vacation" /> <fmt:message
							key="attendance.header.vacation" /></td>
				</tr>

			<!-- 	<tr>

					<c:set var="check" value="" />
					<c:if test="${code!=null && code!=''}">
						<c:set var="check" value="disabled" />
					</c:if>

						<td nowrap class="formReq" width="30%"><abc:i18n
								property="attendance.caption.vacationCode" /> <fmt:message
								key="attendance.caption.vacationCode" /></td>


						<td class="formBodControl">
								<input size="8" maxlength="8" type="text"
									value="${vacation.vacation}"
									readonly="readonly" />
							</td>
				</tr>
 -->

				<tr>

					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.vacationType" /> <fmt:message
							key="attendance.caption.vacationType" /></td>
					<td class="formBod"><spring:bind path="vacation.type">
							<select name="${status.expression}" id="${status.expression}">
								<option value=""><fmt:message
										key="commons.caption.select" /></option>
								<option value="A" ${status.value=='A'?'selected':''} ><fmt:message
										key="attendance.caption.journalVacation" /></option>
								<option value="B" ${status.value=='B'?'selected':''} ><fmt:message
										key="attendance.caption.specialVacation" /></option>
							</select>
						</spring:bind></td>


					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.monthsallowance" /> <fmt:message
							key="attendance.caption.monthsallowance" /></td>
					<td class="formBodControl" width="70%"><spring:bind path="vacation.may_transfer">
							<input type="checkbox" name="${status.expression}"
								${status.value=='1'?'checked':''} />
						</spring:bind></td>
				</tr>

				<tr>

					<td nowrap class="formReq" width="30%"><abc:i18n
							property="commons.caption.arName" /> <fmt:message
							key="commons.caption.arName" /></td>

					<td colspan="3" class="formBodControl" width="70%"><spring:bind
							path="vacation.name">
							<input size="20" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
				</tr>


				<tr>
					<td  nowrap class="formReq" width="30%"><abc:i18n
							property="commons.caption.enName" /> <fmt:message
							key="commons.caption.enName" /></td>

					<td colspan="3" class="formBodControl" width="70%"><spring:bind
							path="vacation.ename">
							<input size="20" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
				</tr>
				
				<tr>
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.monthsentitled" /> <fmt:message
							key="attendance.caption.monthsentitled" /></td>

					<td colspan="3" class="formBodControl" width="70%"><spring:bind
							path="vacation.monthes_zero_entitled">
							<input size="3" type="text" name="${status.expression}"
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

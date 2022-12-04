<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<abc:security property="1025"/>
<script type="text/javascript">
	
</script>

<form id="departmentForm" name="departmentForm" method="POST"
	action="<c:url value="/attendance/departmentForm.html"/>">

<input type="hidden" id="departmentCode" name="departmentCode"
			value="${departmentCode}" />
			
	<table width="90%" border="0" cellspacing="0" cellpadding="0" 
		style="padding-right: 10px">

		<tr>
			<td colspan="2"><spring:bind path="department.*">
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
							property="attendance.header.department" /> <fmt:message
							key="attendance.header.department" /></td>
				</tr>

<!-- 				<tr>

					<c:set var="check" value="" />
					<c:if test="${departmentCode!=null && departmentCode!=''}">
						<c:set var="check" value="disabled" />
					</c:if>

						<td nowrap class="formReq" width="30%"><abc:i18n
								property="attendance.caption.departmentCode" /> <fmt:message
								key="attendance.caption.departmentCode" /></td>


						<td class="formBodControl">
								<input size="8" maxlength="8" type="text"
									 value="${department.location}"
									readonly="readonly" />
						</td>
				</tr> -->

				<tr>

					<td nowrap class="formReq" width="30%"><abc:i18n
							property="commons.caption.arName" /> <fmt:message
							key="commons.caption.arName" /></td>

					<td class="formBodControl" width="70%"><spring:bind
							path="department.name">
							<input size="40" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
				</tr>


				<tr>
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="commons.caption.enName" /> <fmt:message
							key="commons.caption.enName" /></td>

					<td class="formBodControl" width="70%"><spring:bind
							path="department.ename">
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
</form>


<%@ include file="/web/common/includes/footer.jsp"%>

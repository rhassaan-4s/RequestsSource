<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<script type="text/javascript">
	
</script>

	<form:form method="POST"
					action="/Requests/timesheet/partForm.html"
					modelAttribute="part">

<input type="hidden" id="partNo" name="partNo"
			value="${partNo}" />
	<input type="hidden" id="partcode" name="partcode"
			value="${code}" />
			
	<table width="90%" border="0" cellspacing="0" cellpadding="0" 
		style="padding-right: 10px">

		<tr>
			<td colspan="2">
			<h2 style="color: red;">
					<form:errors path="part.*" />
				</h2>
				
			<spring:bind path="part.*">
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
					<td class="helpTitle" colspan="2" nowrap><%
				Object partNo = session.getAttribute("partNo");
				Object partName = session.getAttribute("partName"+(String)partNo);
				out.println(partName); %></td>
				</tr>

				<tr>


					<c:if test="${partcode!=null && partcode!=''}">
						<td nowrap class="formReq" width="30%"><abc:i18n
								property="timesheet.caption.partCode" /> <fmt:message
								key="timesheet.caption.partCode" /></td>


						<td class="formBodControl">
								<input size="8" maxlength="8" type="text"
									value="${code}"
									readonly="readonly" disabled="disabled" />
							</td>
					</c:if>
				</tr>

				<tr>

					<td nowrap class="formReq" width="30%"><abc:i18n
							property="timesheet.caption.partName" /> <fmt:message
							key="timesheet.caption.partName" /></td>

					<td class="formBodControl" width="70%"><spring:bind
							path="part.name">
							<input size="40" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
				</tr>


				<tr>
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="timesheet.caption.partEnName" /> <fmt:message
							key="timesheet.caption.partEnName" /></td>

					<td class="formBodControl" width="70%"><spring:bind
							path="part.ename">
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

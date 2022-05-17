<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<script type="text/javascript">
	
</script>

<form id="titleForm" name="titleForm" method="POST"
	action="<c:url value="/attendance/titleForm.html"/>">

<input type="hidden" id="titleCode" name="titleCode"
			value="${titleCode}" />
			
	<table width="90%" border="0" cellspacing="0" cellpadding="0" 
		style="padding-right: 10px">

		<tr>
			<td colspan="2"><spring:bind path="title.*">
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
							property="attendance.header.title" /> <fmt:message
							key="attendance.header.title" /></td>
				</tr>

			<!-- 	<tr>

					<c:set var="check" value="" />
					<c:if test="${code!=null && code!=''}">
						<c:set var="check" value="disabled" />
					</c:if>

						<td nowrap class="formReq" width="30%"><abc:i18n
								property="attendance.caption.titleCode" /> <fmt:message
								key="attendance.caption.titleCode" /></td>


						<td class="formBodControl">
								<input size="8" maxlength="8" type="text"
									value="${title.title}"
									readonly="readonly" />
							</td>
				</tr>
 -->
				<tr>

					<td nowrap class="formReq" width="30%"><abc:i18n
							property="commons.caption.arName" /> <fmt:message
							key="commons.caption.arName" /></td>

					<td class="formBodControl" width="70%"><spring:bind
							path="title.name">
							<input size="40" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
				</tr>


				<tr>
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="commons.caption.enName" /> <fmt:message
							key="commons.caption.enName" /></td>

					<td class="formBodControl" width="70%"><spring:bind
							path="title.ename">
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

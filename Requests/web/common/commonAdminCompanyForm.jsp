<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>


<script type="text/javascript">

</script>

<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right:10px ">
	<!--tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
			property="commons.caption.companyForm" /> <fmt:message
			key="commons.caption.companyForm" /></td>
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
	</tr-->

	<tr>
		<td><spring:bind path="company.*">
			<c:if test="${not empty status.errorMessages}">
				<div><c:forEach var="error" items="${status.errorMessages}">
					<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
					</font>
				</c:forEach></div>
			</c:if>
		</spring:bind> <spring:bind path="company.id">
			<input type="hidden" size=20 maxlength=40 name="copmanyId"
				value="${status.value}" />
		</spring:bind></td>
	</tr>

	<tr>
		<td colspan="2">

		<form id="company" name="company" method="POST"
			action="<c:url value="/common/commonAdminCompanyForm.html"/>">
		<br /><br />
		<table rules="all" align="center" width="60%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap><abc:i18n
					property="commons.caption.companyForm" /> <fmt:message
					key="commons.caption.companyForm" /></td>
			</tr>

			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="commons.caption.description" /> <fmt:message
					key="commons.caption.description" /></td>
				<td class="formBodControl"><spring:bind path="company.description">
					<input type="text" size="40" name="${status.expression}"
						value="${status.value}"></input>
				</spring:bind></td>
			</tr>

			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>


			<tr id="btn">
				<td colspan="2" align=center><abc:i18n property="commons.button.save" />
				<input type="submit" name="save"
					value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp;

				<abc:i18n property="commons.button.cancel" /> <input type="reset"
					name="cancel" value="<fmt:message key="commons.button.cancel"/>"
					class="button" /></td>
			</tr>
			</td>
			</tr>
		</table>
		</td>
	</tr>
</table>



<%@ include file="/web/common/includes/footer.jsp"%>

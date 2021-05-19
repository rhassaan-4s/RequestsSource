<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<abc:security property="274" />
<br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<!--tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
			property="commons.header.editCountry" /><fmt:message
			key="commons.header.editCountry" /></td>
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
		<td><spring:bind path="country.*">
			<c:if test="${not empty status.errorMessages}">
				<div><c:forEach var="error" items="${status.errorMessages}">
					<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
					</font>
				</c:forEach></div>
			</c:if>
		</spring:bind></td>
	</tr>

	<tr>
		<td colspan="2">

		<form id="country" name="country" method="POST"
			action="<c:url value="/common/commonAdminEditCountry.html"/>">

		<spring:bind path="country.id">
			<input type="hidden" size=20 maxlength=40 name="countryId"
				value="${status.value}" />
		</spring:bind>

		<table rules="all" align="center" width="70%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap><abc:i18n
					property="commons.header.editCountry" /><fmt:message
					key="commons.header.editCountry" /></td>
			</tr>
			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="commons.caption.description" /><fmt:message
					key="commons.caption.description" /></td>
				<td class="formBodControl"><spring:bind path="country.description">
					<input type="text" size="40" name="${status.expression}"
						value="${status.value}"></input>
				</spring:bind></td>
			</tr>
			<tr>
				<td nowrap class="formBod"><abc:i18n
					property="commons.caption.maleNationality" /><fmt:message
					key="commons.caption.maleNationality" /></td>
				<td class="formBodControl"><spring:bind path="country.maleNationality">
					<input type="text" size="40" name="${status.expression}"
						value="${status.value}"></input>
				</spring:bind></td>
			</tr>
			<tr>
				<td nowrap class="formBod"><abc:i18n
					property="commons.caption.femaleNationality" /><fmt:message
					key="commons.caption.femaleNationality" /></td>
				<td class="formBodControl"><spring:bind path="country.femaleNationality">
					<input type="text" size="40" name="${status.expression}"
						value="${status.value}"></input>
				</spring:bind></td>
			</tr>

			<tr>
				<td nowrap class="formBod"><abc:i18n
					property="commons.caption.isDefault" /> <fmt:message
					key="commons.caption.isDefault" /></td>
				<td class="formBodControl"><input type="checkbox" name="default" value="true"
					${country.isDefault== 'true' ? ' checked':''}/></td>
			</tr>

			<tr>
				<td colspan=5>&nbsp;</td>
			</tr>

			<tr id="btn">
				<td colspan=5 align=center><abc:i18n
					property="commons.button.save" /><input type="submit" name="save"
					value="<fmt:message key="commons.button.save"/>" class="button">&nbsp;&nbsp;&nbsp;
				<c:if test="${not empty country.id}">
					<!--	<input type="submit" name="delete" value="<fmt:message key="commons.button.delete"/>" class="button"/>&nbsp;&nbsp;&nbsp;-->
				</c:if> <abc:i18n property="commons.button.cancel" /><input type="reset"
					name="cancel" value="<fmt:message key="commons.button.cancel"/>"
					class="button"></td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>




<%@ include file="/web/common/includes/footer.jsp"%>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<abc:security property="270" />
<br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<!--tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
			property="commons.caption.country" /><fmt:message
			key="commons.caption.country" /> ${results.description}: <abc:i18n
			property="commons.header.countries" /><fmt:message
			key="commons.header.cities" /></td>
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
		<td colspan="2">
		<table rules="all" align="center" width="70%" class="sofT">
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="6" nowrap><abc:i18n
				property="commons.caption.country" /><fmt:message
					key="commons.caption.country" /> ${results.description}: <abc:i18n
					property="commons.header.countries" /><fmt:message
					key="commons.header.cities" /></td>
			</tr>
			<tr>
				<td nowrap class="helpHed">&nbsp;<abc:i18n property="commons.caption.city" /><fmt:message
					key="commons.caption.city" />&nbsp;</td>
				<td nowrap class="helpHed">&nbsp;<abc:i18n
					property="commons.caption.englishDescription" /><fmt:message
					key="commons.caption.englishDescription" />&nbsp;</td>
				<td nowrap class="helpHed">&nbsp; <abc:i18n
					property="commons.caption.isDefault" /> <fmt:message
					key="commons.caption.isDefault" />&nbsp;</td>
				<td nowrap class="helpHed">&nbsp;&nbsp;</td>

			</tr>
			<c:forEach var="result" items="${results.cities}">
				<tr>
					<td class="helpBod">${result.description}</td>
					<td class="helpBod">${result.englishDescription}</td>
					<td class="helpBod"><c:if
						test="${result.isDefault == 'true'}">
						<fmt:message key="commons.caption.yes" />
					</c:if></td>
					<td class="helpBod"><NOBR>&nbsp;<abc:i18n
						property="commons.button.edit" /><a
						href="commonAdminEditCity.html?cityId=${result.id}"
						class="actionLink"><fmt:message key="commons.button.edit" /></a>&nbsp;</NOBR>
					</td>

				</tr>
			</c:forEach>
		</table>
		</td>
	</tr>
</table>
<br />
<table>
	<tr>
		<td align="center"><abc:i18n property="commons.button.add" /><input
			type="button" name="add"
			value=" <fmt:message key="commons.button.add"/> " class="button"
			onclick="window.location='commonAdminEditCity.html?countryId=${results.id}'"></td>
	</tr>
</table>

<%@ include file="/web/common/includes/footer.jsp"%>
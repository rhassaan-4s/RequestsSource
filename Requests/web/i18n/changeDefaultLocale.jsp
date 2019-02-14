<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<abc:security property="284"/><br>
<!--table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader">
		<abc:i18n property="i18n.header.editDefaultLocale"/>
		<fmt:message key="i18n.header.editDefaultLocale" /></td>
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
	</tr>

	<tr>
		<td colspan="2"-->

		<form id="defaults" name="defaults" method="POST"action="<c:url value="/i18n/changeDefaultLocale.html"/>">

		<table rules="all" align="center" width="50%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" nowrap colspan="2">
				<abc:i18n property="i18n.header.editDefaultLocale"/>
				<fmt:message key="i18n.header.editDefaultLocale" />
				</td>
			</tr>
			<tr>
				<td nowrap class="formReq" width="30%">
				<abc:i18n property="commons.caption.language"/>
				<fmt:message key="commons.caption.language" /></td>
				<td class="formBodControl">
					<select name="selectLanguage">
						<c:forEach items="${languages}" var="defaultLocale" varStatus="loop">
							<option value="${defaultLocale.id}" ${defaultLocale.id==myLocale.id ? ' selected' : ''}>${defaultLocale.language}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr id="btn">
				<td align="center" colspan="2">
				<abc:i18n property="commons.button.save"/>
				<input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />
					&nbsp;&nbsp;&nbsp;
					<abc:i18n property="commons.button.cancel"/>
					<input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button" />
				</td>
			</tr>
			
		</table>
		</form>
		<!--/td>
	</tr>
</table-->

<%@ include file="/web/common/includes/footer.jsp"%>

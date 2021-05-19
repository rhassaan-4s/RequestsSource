<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ include file="/web/common/includes/popupheader.jsp"%>

<abc:security property="300" />
<br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<!--tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
			property="i18n.header.editLocale" /> <fmt:message
			key="i18n.header.editLocale" /></td>
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
		<td><spring:bind path="myLocale.*">
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
		<form id="fields" name="fields" method="POST"
			action="<c:url value="/i18n/addNewLocale.html"/>">

		<table rules="all" align="center" width="70%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" nowrap colspan="2"><abc:i18n
					property="i18n.header.editLocale" /> <fmt:message
					key="i18n.header.editLocale" /></td>
			</tr>
			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="commons.caption.language" /> <fmt:message
					key="commons.caption.language" /></td>
				<td class="formBodControl"><spring:bind path="myLocale.language">
					<input type="text" size="20" name="${status.expression}"
						value="${status.value}"></input>
				</spring:bind><spring:bind path="myLocale.id">
					<input type="text" size=20 maxlength=40 name="localeId"
						value="${status.value}"
						style="visibility: hidden; position: absolute;" />
				</spring:bind></td>
			</tr>

			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="commons.caption.country" /> <fmt:message
					key="commons.caption.country" /></td>
				<td class="formBodControl"><spring:bind path="myLocale.country">
					<select name="${status.expression}">
						<option value=""><fmt:message
							key="commons.caption.select" /></option>
						<c:forEach items="${countries}" var="country">
							<option value="${country.description}">${country.description}</option>
						</c:forEach>
					</select>
				</spring:bind></td>
			</tr>
			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="i18n.caption.code" /> <fmt:message
					key="i18n.caption.code" /></td>
				<td class="formBodControl"><spring:bind path="myLocale.code">
					<input type="text" size="20" name="${status.expression}"
						value="${status.value}"></input>
				</spring:bind></td>
			</tr>

			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="i18n.caption.clonedLocale" /> <fmt:message
					key="i18n.caption.clonedLocale" /></td>
				<td class="formBodControl"><select name="clonedLocale">
					<c:forEach items="${locales}" var="locale" varStatus="loop">
						<option value="${locale.id}">${locale.language}</option>
					</c:forEach>
				</select></td>
			</tr>
			
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>

			<tr id="btn">
				<td colspan="2" align=center><abc:i18n
					property="commons.button.save" /> <input type="submit" name="save"
					value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp;
				<abc:i18n property="commons.button.cancel" /> <input type="reset"
					name="cancel" value="<fmt:message key="commons.button.cancel"/>"
					class="button" />
			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>

<%@ include file="/web/common/includes/popupfooter.jsp"%>

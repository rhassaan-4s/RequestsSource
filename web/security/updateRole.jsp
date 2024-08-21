<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ include file="/web/common/includes/popupheader.jsp"%>
<abc:security property="292" />
<br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<!--tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
			property="security.header.editRole" /> <fmt:message
			key="security.header.editRole" /></td>
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
		<td><spring:bind path="role.*">
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
			action="<c:url value="/security/updateRole.html"/>">

		<table rules="all" align="center" width="70%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap><abc:i18n
					property="security.header.editRole" /> <fmt:message
					key="security.header.editRole" /></td>
			</tr>

			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="security.caption.roleName" /> <fmt:message
					key="security.caption.roleName" /></td>
				<td class="formBodControl"><spring:bind path="role.rolename">
					<input type="text" size="40" name="${status.expression}"
						value="${status.value}" style="width: 250px;"></input>
				</spring:bind></td>
			</tr>
			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="security.caption.defaultPage" /> <fmt:message
					key="security.caption.defaultPage" /></td>
				<td class="formBodControl"><spring:bind path="role.defaultPage">
					<input type="text" size="40" name="${status.expression}"
						value="${status.value}" style="width: 250px;"></input>
				</spring:bind></td>
			</tr>
			<tr>
				<td nowrap class="formBod"><abc:i18n
					property="security.caption.privileges" /> <fmt:message
					key="security.caption.privileges" /></td>
				<TD class="formBodControl"><select id="s2" name="permissions" size="20" multiple  style="width: 350px; text-align: left;">
					<c:forEach items="${model.permessions}" var="permession">
						<c:set var="isSelected" value="" />
						<c:forEach items="${role.permissions}" var="sel_per">

							<c:if test="${sel_per.id == permession.id}">
								<c:set var="isSelected" value="selected" />
							</c:if>
						</c:forEach>
						<c:if test="${permession.selected==true}">
						<option value="${permession.id}"${isSelected}>${permession.permissionDescription}</option>
						</c:if>
					</c:forEach>
				</select></TD>
			</tr>

			<input type="hidden" size="40" name="roleId" value="${roleId}"></input>

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

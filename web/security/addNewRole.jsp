<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ include file="/web/common/includes/popupheader.jsp"%>
<script type='text/javascript'
	src='/Requests/dwr/interface/securityManager.js'></script>
<script type='text/javascript' src='/Requests/dwr/engine.js'></script>
<script type='text/javascript' src='/Requests/dwr/util.js'></script>
<abc:security property="288" />
<br>
<script type="text/javascript">
	function getDefaultPage() {
		var applicationSelect = document.getElementById("application");
		var application = applicationSelect.value;
		//alert("application "+application);
		//alert("securityManager "+securityManager);
		securityManager.getApplicationDefaultPage(fillDefaultPage, application);
	}
	function fillDefaultPage(data) {
		//alert(data);
		var defaultPage = document.getElementById("defaultPage");
		defaultPage.value = data;
	}
	window.onload = getDefaultPage;
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<!--tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n property="security.header.addRole" />
		<fmt:message key="security.header.addRole" /></td>
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
			action="<c:url value="/security/addNewRole.html"/>">
		<br />
		<br />
		<table rules="all" align="center" width="70%" class="sofT">
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap><abc:i18n
					property="security.header.addRole" /> <fmt:message
					key="security.header.addRole" /></td>
			</tr>

			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="security.caption.roleName" /> <fmt:message
					key="security.caption.roleName" /></td>
				<td class="formBodControl"><spring:bind path="role.rolename">
					<input type="text" size="40" name="${status.expression}"
						value="${status.value}"></input>
				</spring:bind></td>
			</tr>


			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="security.caption.application" /> <fmt:message
					key="security.caption.application" /></td>
				<td class="formBodControl"><select name="selectApplication" id="application"
					onChange="getDefaultPage();">

					<c:forEach items="${applications}" var="app" varStatus="loop">
						<option value="${app.id}">${app.name}</option>

					</c:forEach>
				</select></td>
			</tr>

			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="security.caption.defaultPage" /> <fmt:message
					key="security.caption.defaultPage" /></td>
				<td class="formBodControl"><spring:bind path="role.defaultPage">
					<input type="text" size="40" id="defaultPage"
						name="${status.expression}" value="${status.value}"></input>
				</spring:bind></td>
			</tr>
			<tr>
				<td class="formBod"><abc:i18n property="security.caption.clonedRole" /> <fmt:message
					key="security.caption.clonedRole" /></td>
				<td class="formBodControl"><select name="clonedRole">
					<option value="">Select Role</option>
					<c:forEach items="${roles}" var="role" varStatus="loop">
						<option value="${role.id}">${role.rolename}</option>


					</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td colspan="4"><br />
				</td>
			</tr>
			<tr id="btn">
				<td colspan=5 align=center><abc:i18n
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
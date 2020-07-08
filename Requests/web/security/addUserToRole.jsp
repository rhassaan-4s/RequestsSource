<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ include file="/web/common/includes/popupheader.jsp"%>


<script language="JavaScript">
<!--
	function disableOther(selectedRadio) {
		if (selectedRadio == 'isManager') {
			var isDepartmentManagerTrue = document
					.getElementById("isDepartmentManagerTrue");
			var isDepartmentManagerFalse = document
					.getElementById("isDepartmentManagerFalse");
			/*isDepartmentManagerTrue.checked = false;*/
			isDepartmentManagerFalse.checked = true;
		} else if (selectedRadio == 'isDepartmentManager') {
			var isManagerTrue = document.getElementById("isManagerTrue");
			var isManagerFalse = document.getElementById("isManagerFalse");
			/*isManagerTrue.checked = false;*/
			isManagerFalse.checked = true;
		}
	}

	function canSeePrivateGLAccountsChanged() {
		//alert(document.forms[0].canSeePrivateGLAccounts.name);
		var canSeePrivate = document.getElementById("canSeePrivateGLAccounts");
		//alert("checked" +canSeePrivate.checked);
		//alert("value" + canSeePrivate.value);
		if (canSeePrivate.checked == true) {
			canSeePrivate.value = true;
		} else {
			canSeePrivate.value = false;
		}
		//alert("value" + canSeePrivate.value);
	}

	function changeCase() {
		var canSeeStore = document.getElementById("canSeeAllStore");

		if (canSeeStore.checked == true) {

			canSeeStore.value = true;
		} else {

			canSeeStore.value = false;
		}
	}

	function refreshParent() {
		self.close();
	}
	function reload() {
		var optionList = document.forms[0].zeft.options[document.forms[0].zeft.selectedIndex].index;
		var option = document.forms[0].zeft.options[optionList].value;
		createWindow('addUserToRole.html?option=' + option + "&userId="
				+ document.forms[0].userId.value + "&my="
				+ document.forms[0].my.value);

	}
	function validate(chk) {
		if (chk.checked == 1) {
			var optionList = document.forms[0].zeft.options[document.forms[0].zeft.selectedIndex].index;
			var myCheck = document.forms[0].zeft.options[optionList].value;
			document.forms[0].my.value = myCheck;
		}
	}

	function init() {
		if (document.forms[0].userId.value != "") {
			document.forms[0].zeft.disabled = false;
		} else {
			document.forms[0].zeft.disabled = true;
		}
	}

	function disableAttendanceCode() {
		var attendanceCode = document.getElementById("attendanceCode");
		attendanceCode.value = null;
		attendanceCode.disabled = true;
	}

	function enableAttendanceCode() {
		var attendanceCode = document.getElementById("attendanceCode");
		attendanceCode.disabled = false;
		//attendanceCode.value = aCode;
	}

	window.onload = init;
//-->
</script>

<abc:security property="289" />
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<!--tr>
		<td class="tableHeader" height="1">
	</td>
	</tr>
	<tr>
		<td class="tableHeader">
		<abc:i18n property="security.header.editUserDetails"/>
		<fmt:message
			key="security.header.editUserDetails" /></td>
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
		<td><spring:bind path="user.*">
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
		<td colspan="2">
			<form id="fields" name="fields" method="POST"
				action="<c:url value="/security/addUserToRole.html"/>">
				<input type="hidden" size=20 maxlength=40 name="option"
					value="${option}" /> <br /> <br /> <br />
				<table rules="all" align="center" width="70%" class="sofT">
					<tr id="head_1_ep">
						<td class="helpTitle" colspan="4" nowrap><abc:i18n
								property="security.header.editUserDetails" /> <fmt:message
								key="security.header.editUserDetails" />
							<spring:bind path="user.id">
								<input type="text" size=20 maxlength=40 name="userId"
									value="${status.value}"
									style="visibility: hidden; position: absolute;" />
							</spring:bind>
							<spring:bind path="user.id">
								<input type="hidden" size=20 maxlength=40 name="my"
									value="${my}" />
							</spring:bind></td>
					</tr>
					<tr>
						<td nowrap class="formReq"><abc:i18n
								property="commons.caption.firstName" /> <fmt:message
								key="commons.caption.firstName" /></td>
						<td class="formBodControl"><spring:bind
								path="user.employee.firstName">
								<input type="text" size=20 maxlength=40
									name="${status.expression}" readonly="readonly"
									value="${status.value}" />
							</spring:bind></td>

						<td nowrap class="formReq"><abc:i18n
								property="commons.caption.email" /> <fmt:message
								key="commons.caption.email" /></td>
						<td class="formBodControl"><spring:bind
								path="user.employee.email">
								<input type="text" size=20 maxlength=40
									name="${status.expression}" value="${status.value}" />
							</spring:bind></td>
					</tr>
					<tr>

						<td nowrap class="formReq"><abc:i18n
								property="commons.caption.userCode" /> <fmt:message
								key="commons.caption.userCode" /></td>
						<td class="formBodControl"><spring:bind path="user.username">
								<input type="text" size="20" name="${status.expression}"
									readonly="readonly" value="${status.value}"></input>
							</spring:bind></td>

						<td nowrap class="formReq"><abc:i18n
								property="commons.caption.password" /> <fmt:message
								key="commons.caption.password" /></td>
						<td class="formBodControl"><spring:bind path="user.password">
								<input type="password" size="20" name="${status.expression}"
									value="${status.value}"></input>
							</spring:bind></td>
					</tr>

					<tr>
						<td nowrap class="formReq" rowspan="2"><abc:i18n
								property="security.caption.application" /> <fmt:message
								key="security.caption.application" /></td>

						<td class="formBodControl" rowspan="2"><select name="zeft"
							onchange="return reload();">
								<c:forEach items="${applications}" var="app" varStatus="loop">
									<option value="${app.id}" ${option==app.id?' selected' : ''}>${app.name}</option>

								</c:forEach>
						</select></td>

						<td nowrap class="formReq" rowspan="2"><abc:i18n
								property="security.caption.roles" /> <fmt:message
								key="security.caption.roles" /></td>

						<TD class="formBodControl" rowspan="2"><select
							name="userRoles" size="3" multiple>
								<c:forEach items="${roles}" var="role">

									<c:if test="${role.id!=6}">
										<!-- Hiding 4s Administration  -->
										<c:set var="isSelected" value="" />
										<c:forEach items="${user.roles}" var="userRole">

											<c:if test="${role.id == userRole.id}">
												<c:set var="isSelected" value="selected" />
											</c:if>
										</c:forEach>

										<option value="${role.id}" ${isSelected}>${role.rolename}</option>
									</c:if>
								</c:forEach>
						</select></TD>
					</tr>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>

					<tr id="btn">
						<td colspan="4" align="center"><abc:i18n
								property="commons.button.save" /> <input type="submit"
							name="save" value="<fmt:message key="commons.button.save"/>"
							class="button" />&nbsp;&nbsp;&nbsp; <abc:i18n
								property="commons.button.cancel" /> <input type="reset"
							name="cancel" value="<fmt:message key="commons.button.cancel"/>"
							class="button" /> <abc:i18n property="commons.button.close" />
							<input type="button" name="Close"
							value="<fmt:message key="commons.button.close"/>" class="button"
							onclick="opener.location.reload();self.close()" /></td>

					</tr>

				</table>
			</form>
		</td>
	</tr>
</table>
<%@ include file="/web/common/includes/popupfooter.jsp"%>


<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true" />


<!--<script type='text/javascript' src='//Requests/web/common/dwr/interface/communicationManager.js'></script>-->
<!--<script type='text/javascript' src='//Requests/web/common/dwr/engine.js'></script>-->
<!--<script type='text/javascript' src='//Requests/web/common/dwr/util.js'></script>-->
<!--<script type='text/javascript'>-->
<!--function update()-->
<!--{-->
<!--   communicationManager.getCitiesByCountry(createList,$("country").value);-->
<!--}-->
<!--function createList(data)-->
<!--{-->
<!--	alert("data "+data);-->
<!--    DWRUtil.removeAllOptions("city");-->
<!--    DWRUtil.addOptions("city", data);-->
<!-- }-->

<!--</script>-->
<abc:security property="278" />
<br>

<form:form method="POST" modelAttribute="employee"
	action="/Requests/common/commonAdminEditEmployee.html">
	<table width="90%" border="0" cellspacing="0" cellpadding="0"
		style="padding-right: 10px">
		<tr>
			<td class="tableHeader" height="1"></td>
		</tr>
		<tr>
			<td class="tableHeader"><abc:i18n
					property="communication.pageHeading.editEmployee" /> <fmt:message
					key="communication.pageHeading.editEmployee" /></td>
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
			<td colspan="2"><spring:bind path="employee.*">
					<c:if test="${not empty status.errorMessages}">
						<div>
							<c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}"
										escapeXml="false" /><br />
								</font>
							</c:forEach>
						</div>
					</c:if>
				</spring:bind> <spring:bind path="employee.id">
					<input type="hidden" size=20 maxlength=40 name="employeeId"
						value="${status.value}" />
				</spring:bind>

				<table border=0 cellspacing=1 cellpadding=0 id="ep"
					style="margin-right: 40px">

					<tr>
						<td colspan=5>&nbsp;</td>
					</tr>
					<tr id="head_1_ep">
						<td class="bodyBold" colspan=4 nowrap><abc:i18n
								property="commons.pageHeading.editEmployee" /> <fmt:message
								key="commons.pageHeading.editEmployee" /></td>
						<td nowrap colspan=1 align=left><img
							src="/Requests/web/common/images/required_icon.gif" border="0"
							alt="Required Information" title="Required Information" width=18
							height=18 align="texttop"><span class="bodySmallBold">
								= <fmt:message key="commons.caption.requiredInformation" />
						</span></td>
					</tr>
					<TR>
						<TD CLASS="blackLine" COLSPAN=5><img
							src="/Requests/web/common/images/s.gif"></TD>
					</TR>

					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.firstName" /> <fmt:message
								key="commons.firstName" /></td>
						<td><spring:bind path="employee.firstName">
								<input type="text" size="40" name="${status.expression}"
									value="${status.value}"></input>
							</spring:bind></td>
					</tr>

					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.lastName" /> <fmt:message
								key="commons.lastName" /></td>
						<td><spring:bind path="employee.lastName">
								<input type="text" size="40" name="${status.expression}"
									value="${status.value}"></input>
							</spring:bind></td>
					</tr>

					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.jobTitle" /> <fmt:message
								key="commons.jobTitle" /></td>
						<td><spring:bind path="employee.jobTitle">
								<input type="text" size="40" name="${status.expression}"
									value="${status.value}"></input>
							</spring:bind></td>
					</tr>

					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.department" /> <fmt:message
								key="commons.department" /></td>
						<td><spring:bind path="employee.department">
								<select name="${status.expression}">
									<c:forEach items="${departments}" var="aDepartment">
										<option value="${aDepartment.id}"
											${aDepartment.id == employee.department.id ? ' selected' : ''}>${aDepartment.description}</option>
									</c:forEach>
								</select>
							</spring:bind></td>
					</tr>



					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.address" /> <fmt:message
								key="commons.address" /></td>
						<td><spring:bind path="employee.address">
								<input type="text" size="40" name="${status.expression}"
									value="${status.value}"></input>
							</spring:bind></td>
					</tr>

					<!--<tr>-->
					<!--	<td nowrap class="requiredInput"><abc:i18n property="commons.country"/><fmt:message key="commons.country"/></td>-->
					<!--	<td>-->
					<!--			<select name="country"  id="country" onchange="update();">-->
					<!--				<c:forEach items="${countries}" var="acountry">-->
					<!--					<option value="${acountry.id}" ${acountry.id == employee.city.country.id ? ' selected' : ''}>${acountry.description}</option>-->
					<!--   				</c:forEach>-->
					<!--			</select>-->
					<!--	</td>-->
					<!--</tr>-->

					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.city" /> <fmt:message key="commons.city" /></td>
						<td><spring:bind path="employee.city">
								<select name="${status.expression}" id="${status.expression}">
									<c:forEach items="${cities}" var="acity">
										<option value="${acity.id}"
											${acity.id == employee.city.id ? ' selected' : ''}>${acity.description}</option>
									</c:forEach>
								</select>

							</spring:bind></td>
					</tr>

					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.tel" /> <fmt:message key="commons.tel" /></td>
						<td><spring:bind path="employee.tel">
								<input type="text" size=15 name="${status.expression}"
									value="${status.value}" />
							</spring:bind></td>
					</tr>

					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.ext" /> <fmt:message key="commons.ext" /></td>
						<td><spring:bind path="employee.ext">
								<input type="text" size="40" name="${status.expression}"
									value="${status.value}"></input>
							</spring:bind></td>
					</tr>


					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.email" /> <fmt:message key="commons.email" /></td>
						<td><spring:bind path="employee.email">
								<input type="text" size="40" name="${status.expression}"
									value="${status.value}"></input>
							</spring:bind></td>
					</tr>


					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="security.userName" /> <fmt:message
								key="security.userName" /></td>
						<td><spring:bind path="employee.user.username">
								<input type="text" size="40" name="${status.expression}"
									value="${status.value}"></input>
							</spring:bind></td>
					</tr>

					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="security.password" /> <fmt:message
								key="security.password" /></td>
						<td><spring:bind path="employee.user.password">
								<input type="password" size="40" name="${status.expression}"
									value="${status.value}"></input>
							</spring:bind></td>
					</tr>


					<tr>
						<td colspan=5>&nbsp;</td>
					</tr>

					<tr id="btn">
						<td colspan=5 align=center><abc:i18n
								property="commons.button.save" /><input type="submit"
							name="save" value="<fmt:message key="commons.button.save"/>"
							class="button">&nbsp;&nbsp;&nbsp; <c:if
								test="${not empty employee.id}">
								<!-- 	<input type="submit" name="delete" value="<fmt:message key="commons.button.delete"/>" class="button"/>&nbsp;&nbsp;&nbsp; -->
							</c:if> <c:if test="${not empty employee.id}">
								<abc:i18n property="commons.button.roles" />
								<input type="button" name="roles"
									value="<fmt:message key="commons.button.roles"/>"
									class="button"
									onclick="window.location='securityAdminEditAccount.html?accountId=${employee.user.id}'">&nbsp;&nbsp;&nbsp;
		</c:if> <abc:i18n property="commons.caption.cancel" /><input type="reset"
							name="cancel" value="<fmt:message key="commons.caption.cancel"/>"
							class="button"></td>
					</tr>
				</table></td>
		</tr>
	</table>
</form:form>


<%@ include file="/web/common/includes/footer.jsp"%>
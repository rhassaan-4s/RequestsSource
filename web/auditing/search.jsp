<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<script type='text/javascript' src='/Requests/dwr/interface/testDwr.js'></script>
<script type='text/javascript' src='/Requests/dwr/engine.js'></script>
<script type='text/javascript' src='/Requests/dwr/util.js'></script>

<script language="JavaScript">
<!--
	function Check() {

		var sel;
		sel = document.getElementById("className");
		var el;
		el = document.getElementById("classDetail");
		if (el != null) {
			DWRUtil.removeAllOptions(el);
		}
		if (sel.value != "") {
			testDwr.getListByClass(createList, sel.value);
		}
	}

	function createList(data) {
		var el;
		el = document.getElementById("classDetail");

		//DWRUtil.removeAllOptions(el);

		DWRUtil.addOptions(el, data, "id", "name");
	}
</script>
<abc:security property="281" />
<br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
				property="auditing.header.auditingSearch" /> <fmt:message
				key="auditing.header.auditingSearch" /></td>
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
		<td colspan="2"><spring:bind path="cmd.*">
				<c:if test="${not empty status.errorMessages}">
					<div>
						<c:forEach var="error" items="${status.errorMessages}">
							<font color="red"> <c:out value="${error}"
									escapeXml="false" /><br />
							</font>
						</c:forEach>
					</div>
				</c:if>
			</spring:bind> <form:form method="POST" modelAttribute="cmd"
				action="/Requests/auditing/search.html">

				<input type="hidden" size=20 maxlength=40 name="option"
					value="${option}" />

				<table border="0" cellspacing="1" cellpadding="0" id="ep"
					style="margin-right: 40px" width="80%">

					<tr>
						<td width="30%"><abc:i18n property="auditing.caption.user" />
							<fmt:message key="auditing.caption.user" /></td>
						<td width="20%">
							<!--				<select name="selectUser">--> <!--					<option value="">All Users</option>-->
							<!--					<c:forEach items="${users}" var="user">--> <!--						<option value="${user.id}">${user.username}</option>-->
							<!--					</c:forEach>--> <!--				</select>--> <spring:bind
								path="cmd.user">
								<select name="${status.expression}" id="${status.expression}">
									<option value=""><fmt:message
											key="auditing.caption.allUsers" /></option>
									<c:forEach items="${users}" var="user">
										<option value="${user.id}"
											${cmd.user == user.id ? ' selected' : ''}>${user.username}</option>
									</c:forEach>
								</select>
							</spring:bind>
						</td>
						<td width="30%"><abc:i18n property="auditing.caption.action" />
							<fmt:message key="auditing.caption.action" /></td>
						<td width="20%"><spring:bind path="cmd.action">
								<select name="${status.expression}" id="${status.expression}">
									<option value=""><fmt:message
											key="auditing.caption.allActions" /></option>
									<option value="create"
										${cmd.action == 'create' ? ' selected' : ''}><fmt:message
											key="auditing.caption.create" /></option>
									<option value="update"
										${cmd.action == 'update' ? ' selected' : ''}><fmt:message
											key="auditing.caption.update" /></option>
								</select>
							</spring:bind></td>

					</tr>


					<tr>
						<td width="30%"><abc:i18n property="commons.caption.fromDate" />
							<fmt:message key="commons.caption.fromDate" /></td>

						<td width="20%">
							<!--				<input type="text" name="dateFrom" va>--> <spring:bind
								path="cmd.fromDate">
								<input type="text" size="30" name="${status.expression}"
									value="${status.value}"></input>
							</spring:bind>
						</td>
						<td width="30%"><abc:i18n property="commons.caption.toDate" />
							<fmt:message key="commons.caption.toDate" /></td>

						<td width="20%">
							<!--				<input type="text" name="dateTo">--> <spring:bind
								path="cmd.toDate">
								<input type="text" size="30" name="${status.expression}"
									value="${status.value}"></input>
							</spring:bind>
						</td>
					</tr>
					<tr>
						<td><abc:i18n property="auditing.caption.class" /> <fmt:message
								key="auditing.caption.class" /></td>
						<td><spring:bind path="cmd.className">
								<select name="${status.expression}" id="${status.expression}"
									onchange="Check();" id="selectClass">
									<option value=""><fmt:message
											key="auditing.caption.allClasses" /></option>
									<c:forEach items="${classNames}" var="classNames">
										<option value="${classNames.entityClass}"
											${cmd.className == classNames.entityClass ? ' selected' : ''}>${classNames.name}</option>
									</c:forEach>
								</select>
							</spring:bind> <!--				<select name="selectClass" onchange="Check();" id="selectClass">-->
							<!--					<option value="">All Classes</option>--> <!--					<c:forEach items="${classNames}" var="className">-->
							<!--						<option value="${className.entityClass}">${className.name}</option>-->
							<!--					</c:forEach>--> <!--				</select>--></td>
						<td><abc:i18n property="auditing.caption.classDetails" /> <fmt:message
								key="auditing.caption.classDetails" /></td>

						<td><select name="classDetail" id="classDetail">
								<option value=""></option>
						</select></td>
					</tr>

					<tr id="btn">
						<td colspan=5 align=center><br> <abc:i18n
								property="commons.button.search" /> <input type="submit"
							name="search" value="<fmt:message key="commons.button.search"/>"
							class="button" />&nbsp;&nbsp;&nbsp; <abc:i18n
								property="commons.button.cancel" /> <input type="reset"
							name="cancel" value="<fmt:message key="commons.button.cancel"/>"
							class="button" /></td>

					</tr>
				</table>
				</form:form></td>
	</tr>
</table>

<script type="text/javascript">
<!--
Check();
//-->
</script>

<%@ include file="/web/common/includes/footer.jsp"%>
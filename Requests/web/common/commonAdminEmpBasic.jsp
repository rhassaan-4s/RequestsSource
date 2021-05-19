<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>



<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
			property="commons.caption.empBasicForm" /> <fmt:message
			key="commons.caption.empBasicForm" /></td>
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
		<td colspan="2">

		<form id="empBasic" name="empBasic" method="POST"
			action="<c:url value="/common/commonAdminEmpBasic.html"/>"><spring:bind
			path="empBasic.*">
			<c:if test="${not empty status.errorMessages}">
				<div><c:forEach var="error" items="${status.errorMessages}">
					<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
					</font>
				</c:forEach></div>
			</c:if>
		</spring:bind> 
		
		<input type="hidden" size=12 maxlength=8 name="empCodeHidden" value="${eCode}" />

		<table border=0 cellspacing=1 cellpadding=0 id="ep"
			style="margin-right:40px">

			<tr>
				<td colspan=5>&nbsp;</td>
			</tr>
			<tr id="head_1_ep">
				<td class="bodyBold" colspan=4 nowrap><abc:i18n
					property="commons.caption.empBasicForm" /> <fmt:message
					key="commons.caption.empBasicForm" /></td>
				<td nowrap colspan=1 align=left><img
					src="<c:url value="/web/common/images/required_icon.gif"/>"
					border="0" alt="Required Information" title="Required Ination"
					width=18 height=18 align="texttop"><span class="bodySmallBold"> = <abc:i18n
					property="commons.caption.empBasicForm" /><fmt:message
					key="commons.caption.empBasicForm" /></span></td>
			</tr>
			<TR>
				<TD CLASS="blackLine" COLSPAN=5><img
					src="<c:url value="/web/common/images/s.gif"/>"></TD>
			</TR>
					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.caption.empCode" /> <fmt:message
								key="commons.caption.empCode" /></td>
						<td><spring:bind path="empBasic.empCode">
								<input type="text" size=8 maxlength=8 name="${status.expression}" value="${status.value}"  ${empCodeHidden!='' && empCodeHidden!= null ? 'disabled':'' } />
							</spring:bind></td>
					</tr>
					
					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.caption.name" /> <fmt:message
								key="commons.caption.name" /></td>
				<td><spring:bind path="empBasic.empName">
					<input type="text" size="40" name="${status.expression}"
									value="${status.value}"></input>
				</spring:bind></td>
			</tr>
			
			<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.caption.englishName" /> <fmt:message
								key="commons.caption.englishName" /></td>
				<td><spring:bind path="empBasic.e_emp_name">
					<input type="text" size="40" name="${status.expression}"
									value="${status.value}"></input>
				</spring:bind></td>
			</tr>
			
			<tr>
				<td colspan=5>&nbsp;</td>
			</tr>


			<tr id="btn">
				<td colspan=5 align=center><abc:i18n property="commons.button.save" />
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

<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<abc:security property="1034"/>

<c:if test="${messagNo==2}"><font color="red" size="3"> 
<br /><br />
       <abc:i18n	property="requestsApproval.changeProfileForm.wrongpass" /><fmt:message
				key="requestsApproval.changeProfileForm.wrongpass" />
 </font>
		<br />	</c:if>
		
		<c:if test="${messagNo==3}"><font color="blue" size="5"> 

       <abc:i18n	property="requestsApproval.loginUsersRequests.saveSuccess" /><fmt:message
				key="requestsApproval.loginUsersRequests.saveSuccess" />
 </font>
 </c:if>
<form id="accessLevelsForm" name="accessLevelsForm" method="POST"
	action="<c:url value="/requestsApproval/changeProfileForm.html"/>">
	
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">

	<tr>
		<td colspan="2">

		<spring:bind path="accessLevel.*">
			<c:if test="${not empty status.errorMessages}">
				<div><c:forEach var="error" items="${status.errorMessages}">
					<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
					</font>
				  </c:forEach>
				</div>
			</c:if>
		</spring:bind></td>
	</tr>

	<table align="center" width="66%" class="sofT">
		<tr id="head_1_ep">
			<td class="helpTitle" colspan="3" nowrap><abc:i18n
				property="requestsApproval.header.changePassword" /><fmt:message
				key="requestsApproval.header.changePassword" /></td>
		</tr>


		<tr>

			<td nowrap class="formReq" width="30%" colspan="2" ><abc:i18n
				property="requestsApproval.changeProfileForm.currentPassword" /> <fmt:message
				key="requestsApproval.changeProfileForm.currentPassword" /></td>

			<td class="formBodControl" width="70%"><input size="40"
				type="password" name="currentPassword" value="" /></td>
		</tr>
		<tr>

			<td nowrap class="formReq" width="30%" colspan="2" ><abc:i18n
				property="requestsApproval.changeProfileForm.newPassword" /> <fmt:message
				key="requestsApproval.changeProfileForm.newPassword" /></td>

			<td class="formBodControl" width="70%"><input size="40"
				type="password" name="newPassword" value="" /></td>
		</tr>
		
		<tr id="btn">
			<td colspan="3" align="center"><abc:i18n
				property="commons.button.save" /><input type="submit" name="save"
				value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp;
			<abc:i18n property="commons.button.cancel" /><input type="reset"
				name="cancel" value="<fmt:message key="commons.button.cancel"/>"
				class="button" " /> <br>
			</td>
		</tr>

	</table>



</table>
</form>
<%@ include file="/web/common/includes/footer.jsp"%>

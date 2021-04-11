<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ page import="java.util.*,java.text.SimpleDateFormat"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<abc:security property="106"/>
<html>
<head>

<title>Insert title here</title>
<script type='text/javascript' src='/Requests/dwr/interface/requestsDwr.js'></script>
<script type='text/javascript' src='/Requests/dwr/engine.js'></script>


</head>
<body>
<script type="text/javascript">
		
</script>
<%
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String nowDate=sdf.format(cal.getTime());
	%>
<table width="90%"  border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n property="requestsApproval.header.AttendanceRequestsApprovalEdit"/><fmt:message key="requestsApproval.header.AttendanceRequestsApprovalEdit"/></td><td align="left"></td>
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
			<spring:bind path="req.*">
				<c:if test="${not empty status.errorMessages}">
					<div><c:forEach var="error" items="${status.errorMessages}">
						<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
						</font>
					</c:forEach></div>
				</c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td>
			<c:if test="${done==true}"><font color="blue" size="5"> 
	        <abc:i18n	property="requestsApproval.loginUsersRequests.saveSuccess" /><fmt:message
					key="requestsApproval.loginUsersRequests.saveSuccess" />
	 		</font>
	 		</c:if>
		</td>
		
	</tr>

	<tr>
		<td>
			<form id="attendanceRequestsApprovalEdit" name="attendanceRequestsApprovalEdit"	method="POST" action="<c:url value="/requestsApproval/attendanceRequestsApprovalEdit.html"/>">
					 <c:set var="dateNow" value="<%=nowDate%>" />
					 
					 <input type="hidden" name="reqId" id="reqId" value="${reqId}"/>
					 
					 <input type="hidden" name="empCodeQ" id="empCodeQ" value="${empCodeQ}"/>
					 <input type="hidden" name="status" id="status" value="${status}"/>
					 <input type="hidden" name="request_date_from" id="request_date_from" value="${request_date_from}"/>
					 <input type="hidden" name="request_date_to" id="request_date_to" value="${request_date_to}"/>
					 <input type="hidden" name="codeFrom" id="codeFrom" value="${codeFrom}"/>
					 <input type="hidden" name="codeTo" id="codeTo" value="${codeTo}"/>
					 
					 
					 
					<table border=0 cellspacing=1 cellpadding=0 id="ep" style="margin-right:40px">
						<tr id="head_1_ep">
							<td class="bodyBold" colspan=4 nowrap>
							<abc:i18n property="requestsApproval.header.AttendanceRequestsApprovalEdit"/><fmt:message key="requestsApproval.header.AttendanceRequestsApprovalEdit"/></td>
							<td nowrap colspan=1 align=left> <abc:i18n property="commons.caption.requiredInformation"/><span class="bodySmallBold"><fmt:message key="commons.caption.requiredInformation"/></span></td>
						</tr>

						<tr class="showClass">
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.userCode"/>
								<fmt:message key="requestsApproval.caption.userCode"/>
							</td>						
							<td  class="formBodControl"> 
							${req.empCode}
							</td>
						
					  	<!-- 	<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.userName"/>
								<fmt:message key="requestsApproval.caption.userName"/>
							</td>							
	 						<td class="formBodControl" id="employeeName" > 
							&nbsp;
							${employeeName}
							</td>	 -->												
						</tr>
						
						<tr>	
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.historicDate"/>
								<fmt:message key="requestsApproval.caption.historicDate"/>
							</td>
							<td  class="formBodControl">
									<_4s_:formatMiladiDate	value="${from_date_history}" /> - <_4s_:timeString value="${from_date_history}" />
							</td>
						</tr>	
						
						<tr>	
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.requestDate"/>
								<fmt:message key="requestsApproval.caption.requestDate"/>
							</td>
							<td  class="formBodControl">
								<spring:bind path="req.from_date">
									<input type="text"  class="MM_from" autocomplete="off" dir="ltr"  name="${status.expression}" id="${status.expression}" value="${status.value}" />
								</spring:bind>
							</td>
						</tr>		
						
						<tr>
					  		<td nowrap class="formReq" >
								<abc:i18n property="commons.caption.notes"/>
								<fmt:message key="commons.caption.notes"/>
							</td>
							<td  class="formBodControl" >
								<spring:bind path="req.notes">
									<input type="text"  name="${status.expression}" id="${status.expression}" value="${status.value}" />
								</spring:bind>
							</td>
						</tr>						
						
						<tr id="btn">
							<td colspan=4>
								<abc:i18n property="commons.button.submit"/><input type="submit" name="save" value="<fmt:message key="commons.button.submit"/>" class="button"/>&nbsp;&nbsp;&nbsp;
								<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button" >
							</td>
						</tr>					
					</table>
			</form>
		</td>
	</tr>
</table>

<script language="JavaScript" type="text/javascript" src="/Orders/web/common/js/wz_tooltip.js"></script>

<%@ include file="/web/common/includes/footer.jsp" %>
</body>
</html>
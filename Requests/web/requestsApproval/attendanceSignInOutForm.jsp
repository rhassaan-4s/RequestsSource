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

var options = {
		  enableHighAccuracy: true,
		  timeout: 10000,
		  maximumAge: 0
		};

		function success(pos) {
		  var crd = pos.coords;

		  //alert (crd.latitude+","+crd.longitude+","+crd.accuracy);
		  document.getElementById("longitude").setAttribute("value", crd.longitude);
		  document.getElementById("latitude").setAttribute("value", crd.latitude);
		  document.getElementById("accuracy").setAttribute("value", crd.accuracy);
		  
		//  console.log('Your current position is:');
		//  console.log('Latitude : ${crd.latitude}');
		//  console.log('Longitude: ${crd.longitude}');
		//  console.log('More or less ${crd.accuracy} meters.');
		}

		function error(err) {
		  //alert(err.code + " - " + err.message);
		}

		navigator.geolocation.getCurrentPosition(success, error, options);
		
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
		<td class="tableHeader"><abc:i18n property="requestsApproval.header.AttendanceSignInOut"/><fmt:message key="requestsApproval.header.AttendanceSignInOut"/></td><td align="left"></td>
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
			<spring:bind path="loginUsersRequests.*">
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
			<form id="attendanceRequestForm" name="attendanceRequestForm"	method="POST" action="<c:url value="/requestsApproval/attendanceSignInOutForm.html"/>">
					 <c:set var="dateNow" value="<%=nowDate%>" />
					 
					  <input type="hidden" name="longitude" id="longitude" value=""/>
					 <input type="hidden" name="latitude" id="latitude" value=""/>
					 <input type="hidden" name="accuracy" id="accuracy" value=""/>
					 
					 <input type="hidden"  id="empRequestTypeId" name="empRequestTypeId" value="${empRequestTypeId }"/>
					<table border=0 cellspacing=1 cellpadding=0 id="ep" style="margin-right:40px">
						<tr id="head_1_ep">
							<td class="bodyBold" colspan=4 nowrap>
							<abc:i18n property="requestsApproval.header.AttendanceSignInOut"/><fmt:message key="requestsApproval.header.AttendanceSignInOut"/></td>
							<td nowrap colspan=1 align=left> <abc:i18n property="commons.caption.requiredInformation"/><span class="bodySmallBold"><fmt:message key="commons.caption.requiredInformation"/></span></td>
						</tr>
						<c:set var="check"  value=""/>
				  		<c:if test="${empRequestTypeId!=null && empRequestTypeId!='' }">
							 <c:set var="check"  value="disabled"/>
						</c:if>
						

						<tr class="showClass">
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.userCode"/>
								<fmt:message key="requestsApproval.caption.userCode"/>
							</td>						
							<td  class="formBodControl"> 
							<input type="hidden" name="empCode" id="empCode" value="${employeeCode}"/>
							${employeeCode}
							</td>
						
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.userName"/>
								<fmt:message key="requestsApproval.caption.userName"/>
							</td>							
	 						<td class="formBodControl" id="employeeName" > 
							&nbsp;
							${employeeName}
							</td>													
						</tr>
						
						<tr>	
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.requestDate"/>
								<fmt:message key="requestsApproval.caption.requestDate"/>
							</td>
							<td  class="formBodControl">
								<spring:bind path="loginUsersRequests.request_date">
									<input type="text"  readonly="readonly"  dir="ltr"  name="${status.expression}" id="${status.expression}"  value="${dateNow}" />
								</spring:bind>
							</td>
						</tr>								
						<tr>
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.requestType"/>
								<fmt:message key="requestsApproval.caption.requestType"/>
							</td>
							<td  class="formBodControl">
								<spring:bind path="loginUsersRequests.request_id">
									<select name="${status.expression}" id="${status.expression}">
										<option value=""><fmt:message key="commons.caption.select" /></option>
											<c:forEach items="${requestTypeList}" var="request">
												<option value="${request.id}" ${request.id == loginUsersRequests.request_id.id ?' selected' : ''}>${request.description}</option>
											</c:forEach>	
<!--											
										<option value="10"><fmt:message key="requestsApproval.caption.in"/></option>
										<option value="11"><fmt:message key="requestsApproval.caption.out"/></option>
										-->
									</select>
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
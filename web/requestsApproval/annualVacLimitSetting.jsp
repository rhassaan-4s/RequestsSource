<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Insert title here</title>
</head>
<body>
<abc:security property="1035"/>
<script type="text/javascript">

function fillLimit(){
	var vac_period =document.getElementById('vac_period').value;
	document.getElementById('vac_limit').value=vac_period;
}
</script>
<table width="90%"  border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n property="requestsApproval.header.annualVacLimitSetting"/><fmt:message key="requestsApproval.header.annualVacLimitSetting"/></td><td align="left"></td>
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
			<spring:bind path="annualVacLimit.*">
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
			<form id="annualVacLimitSetting" name="annualVacLimitSetting"	method="POST" action="<c:url value="/requestsApproval/annualVacLimitSetting.html"/>">
					
					<table border=0 cellspacing=1 cellpadding=0 id="ep" style="margin-right:40px">
						<tr id="head_1_ep">
							<td class="bodyBold" colspan=4 nowrap>
							<abc:i18n property="requestsApproval.header.annualVacLimitSetting"/><fmt:message key="requestsApproval.header.annualVacLimitSetting"/></td>
						</tr>
		
						<tr>
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.vacationType"/>
								<fmt:message key="requestsApproval.caption.vacationType"/>
							</td>						
							<td  class="formBodControl"> 
								<spring:bind path="annualVacLimit.vac_id">
									<input type="text" name="${status.expression}" id="${status.expression}" value="${vac_id}" readonly="readonly" />
								</spring:bind>
							</td>
						</tr>
						
						<tr>	
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.vacPeriod"/>
								<fmt:message key="requestsApproval.caption.vacPeriod"/>
							</td>
							<td  class="formBodControl" >
								<spring:bind path="annualVacLimit.vac_period">
									<select name="${status.expression}" id="${status.expression}" onchange="fillLimit(this);">
										<option value="" onchange="fillLimit();" onclick="fillLimit(this);"><fmt:message key="commons.caption.select" /></option>						
										<option value="1" ${vac_period.value == "day" ?' selected' : ''}"  onchange="fillLimit(this);" onclick="fillLimit(this);">يوم</option>
										<option value="2" ${vac_period.value == "more" ?' selected' : ''}" onchange="fillLimit(this);"  onclick="fillLimit(this);">يومان او أكثر</option>
									</select>
								</spring:bind>
							</td>													
						</tr>
						
						
						<tr>	
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.vacLimit"/>
								<fmt:message key="requestsApproval.caption.vacLimit"/>
							</td>
							<td  class="formBodControl" >
								<spring:bind path="annualVacLimit.vac_limit">
									<input type="text" name="${status.expression}" id="${status.expression}" value="" />
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
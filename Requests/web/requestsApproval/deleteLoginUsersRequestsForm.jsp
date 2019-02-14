<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Insert title here</title>
</head>
<body>
<abc:security property="1034"/>
<script type="text/javascript">
function searchForm (){
	if(document.getElementById("empCode").value!=null){
		var empCode=document.getElementById("empCode").value;
	} 
 
	//alert('from---'+document.getElementById("from").value);
	if(document.getElementById("request_date_from").value!=null){
		var dateFrom=document.getElementById("request_date_from").value;
		//alert('---dateFrom----'+dateFrom);
	}
	if(document.getElementById("request_date_to").value!=null){
		var dateTo=document.getElementById("request_date_to").value;
	}
	if(document.getElementById("request_id").value!=null){
		var requestType=document.getElementById("request_id").value;
	}
	if(document.getElementById("requestNumber").value!=null){
		var requestNumber=document.getElementById("requestNumber").value;
	}
	var URL='deleteLoginUsersRequestsForm.html?empCode='+empCode+'&dateFrom='+dateFrom+'&dateTo='+dateTo+'&requestType='+requestType+'&requestNumber='+requestNumber;
	window.location.href=URL;
}
</script>
<table width="90%"  border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n property="requestsApproval.header.deleteLoginUsersRequestsForm"/><fmt:message key="requestsApproval.header.deleteLoginUsersRequestsForm"/></td><td align="left"></td>
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
		<c:choose>
			<c:when test="${done==true}"><font color="blue" size="5"> 
	        <abc:i18n	property="requestsApproval.caption.deleteSuccess" /><fmt:message
					key="requestsApproval.caption.deleteSuccess" />
	 		</font>
	 		</c:when>
	 		
	 		<c:when test="${done==false}"><font color="blue" size="5"> 
	        <abc:i18n	property="requestsApproval.errors.requestToBeDeletedIsAlreadyApproved" /><fmt:message
					key="requestsApproval.errors.requestToBeDeletedIsAlreadyApproved" />
	 		</font>
	 		</c:when>
	 	</c:choose>
		</td>
	</tr>	
	<tr>
		<td>
			<form id="deleteLoginUsersRequestsForm" name="deleteLoginUsersRequestsForm"	method="POST" action="<c:url value="/requestsApproval/deleteLoginUsersRequestsForm.html"/>">
				   <input type="hidden"  id="empRequestTypeId" name="empRequestTypeId" value="${empRequestTypeId}"/>
				   <input type="hidden"  id="requestType" name="requestType" value="${requestType}"/>
					
					<table border=0 cellspacing=1 cellpadding=0 id="ep" style="margin-right:40px">
						<tr id="head_1_ep">
							<td class="bodyBold" colspan=4 nowrap>
							<abc:i18n property="requestsApproval.header.deleteLoginUsersRequestsForm"/><fmt:message key="requestsApproval.header.deleteLoginUsersRequestsForm"/></td>
							<td nowrap colspan=1 align=left> <abc:i18n property="commons.caption.requiredInformation"/><span class="bodySmallBold"><fmt:message key="commons.caption.requiredInformation"/></span></td>
						</tr>
						
					<c:set var="check"  value=""/>
					<c:if test="${empRequestTypeId!=null && empRequestTypeId!=''}">
					<c:set var="check"  value="disabled"/>
					</c:if>
		
						<tr>
					  		<td nowrap class="formBodControl" >
								<abc:i18n property="requestsApproval.caption.userCode"/>
								<fmt:message key="requestsApproval.caption.userCode"/>
							</td>						
							<td  class="formBod"> 
								<input type="text" name="empCode" id="empCode" value="${empCode }" readonly="readonly" />
							</td>
							
					  		<td nowrap class="formBodControl" >
								<abc:i18n property="requestsApproval.caption.requestType"/>
								<fmt:message key="requestsApproval.caption.requestType"/>
							</td>
							<td  class="formBodControl" >
								<select name="request_id" id="request_id">
									<option value=""><fmt:message key="commons.caption.select" /></option>						
										<c:forEach items="${requestTypeList}" var="request">
											<option value="${request.id}" ${request.id == loginUsersRequests.request_id.id ?' selected' : ''}">${request.description}</option>
										</c:forEach>
								</select>
							</td>													
						</tr>
						
						<tr>
					  		<td nowrap class="formBodControl" >
								<abc:i18n property="requestsApproval.caption.requestNumber"/>
								<fmt:message key="requestsApproval.caption.requestNumber"/>
							</td>
							<td  class="formBodControl">
								<input type="text" name="requestNumber" id="requestNumber" value="" />
							</td>
						</tr>
						
						<tr>
							<td nowrap class="formBodControl">
								<abc:i18n property="requestsApproval.caption.requestDate"/>
								<fmt:message key="requestsApproval.caption.requestDate"/>
							</td>
						</tr>						
						<tr>
					  		<td nowrap class="formBodControl">
								<abc:i18n property="commons.caption.from"/>
								<fmt:message key="commons.caption.from"/>
							</td>
							<td  class="formBodControl" >
								<input type="text"  class="MM_from_d" class="timepac"  title="ccc" readonly="readonly" autocomplete="off" dir="ltr" name="request_date_from" id="request_date_from"/>
							</td>

					  		<td nowrap class="formBodControl" >
								<abc:i18n property="commons.caption.to"/>
								<fmt:message key="commons.caption.to"/>
							</td>
							<td  class="formBodControl" >
								<input type="text"  class="timepac"  title="ccc" class="MM_to_d" readonly="readonly" autocomplete="off" dir="ltr" name="request_date_to" id="request_date_to"/>
							</td>
						</tr>
						
						<tr>
							<td>
								<abc:i18n property="commons.button.search"/>
								<input type="button" name="aaa" onclick="searchForm()" value="<fmt:message key="commons.button.search"/> " class="button"/>
								&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						
						<tr height="10">
						</tr>

						<tr>
							<td>
								
							</td>
						</tr>
						<tr>
						</tr>
						<tr>
						</tr>																						
					</table>
					<table rules="all" align="center" width="70%" class="sofT">
									<tr>
										<td colspan="14" class="helpTitle">
											<abc:i18n property="requestsApproval.header.deleteLoginUsersRequestsForm" />
											<fmt:message key="requestsApproval.header.deleteLoginUsersRequestsForm" />
										</td>
									</tr>
									
									<tr>
								  		<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.requestNumber"/>
											<fmt:message key="requestsApproval.caption.requestNumber"/>
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.userCode" />
											<fmt:message key="requestsApproval.caption.userCode" />
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.userName" />
											<fmt:message key="requestsApproval.caption.userName" />
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.requestName" />
											<fmt:message key="requestsApproval.caption.requestName" />
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.requestDate" />
										 	<fmt:message key="requestsApproval.caption.requestDate" />
										</td>
										<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="commons.caption.fromDate" /> <fmt:message
											key="commons.caption.fromDate" /></td>
										<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="commons.caption.toDate" /> <fmt:message
											key="commons.caption.toDate" /></td>
										<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="requestsApproval.caption.vacPeriod" /> <fmt:message
											key="requestsApproval.caption.vacPeriod" /></td>											
																					
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="commons.caption.from" />
										 	<fmt:message key="commons.caption.from" />
										</td>
											
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="commons.caption.to" />
										 	<fmt:message key="commons.caption.to" />
										</td>
										
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.reply" />
										 	<fmt:message key="requestsApproval.caption.reply" />
										</td>
										<td class="helpHed">
										</td>	
										<td class="helpHed">
										</td>										
									</tr>
						
									
									
								<c:forEach varStatus="loop" var="record" items="${loginUserReqs}">
									<tr height=20 bgcolor="#F8F8F8">									
										<td class="helpBod" nowrap>
											${record.requestNumber}
										</td>						
										<td class="helpBod" nowrap>
											${record.empCode}
										</td>													
										<td class="helpBod" nowrap>
											${record.login_user.name}
										</td>
										<td class="helpBod" nowrap>
											${record.request_id.description}
										</td>
										<td class="helpBod" nowrap>
									         <_4s_:formatMiladiDate value="${record.request_date}"/> 
										</td>
										
										<td class="helpBod" nowrap>
									        <_4s_:formatMiladiDate value="${record.from_date}"/>
										</td>
									
										<td class="helpBod" nowrap>
									        <_4s_:formatMiladiDate value="${record.to_date}"/>
										</td>
										<td class="helpBod" nowrap>
									        ${record.withdrawDays}
										</td>										
										<td class="helpBod" dir="ltr" nowrap>
											<_4s_:formatMiladiDate value="${record.period_from}"/>
											<_4s_:timeString value="${record.period_from}"/>
										</td>
										<td class="helpBod" dir="ltr" nowrap>
											<_4s_:formatMiladiDate value="${record.period_to}"/>
											<_4s_:timeString value="${record.period_to}"/>
										</td>
										<td class="helpBod" nowrap>
											${record.reply}
										</td>
<!-- 
										<td class="helpBod" nowrap><abc:i18n
											property="commons.button.delete" />
											<a href="deleteLoginUsersRequestsForm.html?requestId=${record.id}&empCode=${record.empCode}&dateFrom=${dateFrom}&dateTo=${dateTo}&requestType=${record.request_id.id}&requestNumber=${record.requestNumber}"><fmt:message
											key="commons.button.delete" /></a>
											
										</td>
										 -->
										<td class="helpBod" nowrap>
										
										<input type="checkbox" name="requestId" id="requestId" value="${record.id}">
											
											
										</td>						
									</tr>
								</c:forEach>	
								<tr>
									<td colspan="12" align="center">
										<abc:i18n property="commons.button.delete"/>
										<input type="submit" value="<fmt:message key="commons.button.delete"/>" name="delete" class="button"></input>
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
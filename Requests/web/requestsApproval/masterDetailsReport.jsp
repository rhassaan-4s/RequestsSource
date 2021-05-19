<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<abc:security property="1034"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
function searchForm (){
	if(document.getElementById("empCode").value!=null){
		var empCode=document.getElementById("empCode").value;
	} 

	if(document.getElementById("statusId").value!=null){
		var statusId=document.getElementById("statusId").value;
		//alert ('-----statusId---'+statusId);
	}

	
//	if(document.getElementById("namehidden").value!=null){
	//	var empName=document.getElementById("namehidden").value;
	//} 
	//alert('from---'+document.getElementById("from").value);
	if(document.getElementById("request_date_from").value!=null){
		var dateFrom=document.getElementById("request_date_from").value;
		//alert('---dateFrom----'+dateFrom);
	}
	if(document.getElementById("request_date_to").value!=null){
		var dateTo=document.getElementById("request_date_to").value;
	}
	if(document.getElementById("requestType").value!=null){
		var requestType=document.getElementById("requestType").value;
	}

	if(document.getElementById("codeFrom").value!=null){
		var codeFrom=document.getElementById("codeFrom").value;
		//alert('---dateFrom----'+dateFrom);
	}
	if(document.getElementById("codeTo").value!=null){
		var codeTo=document.getElementById("codeTo").value;
	}
	var URL='masterDetailsReport.html?empCode='+empCode+'&dateFrom='+dateFrom+'&dateTo='+dateTo+'&requestType='+requestType+'&codeFrom='+codeFrom+'&codeTo='+codeTo+'&statusId='+statusId;
	window.location.href=URL;
}

function printthis(which) {
	var tds = document.getElementsByTagName("div");
	for(i=0;i<tds.length;i++)
	tds.item(i).style.display = "none";
	document.getElementById(which).style.display = "block";
	window.print();
	for(i=0;i<tds.length;i++)
	tds.item(i).style.display = "block";
}

$('.MM_from_d').datetimepicker( "option", "dateFormat", "dd/mm/yy" );
$('.MM_to_d').datetimepicker( "option", "dateFormat", "dd/mm/yy" );
</script>
<style type="text/css">
	@media print {
	input#btnPrint {
	display: none;
	}
	tr#btnPrint{
	display: none;
	}
	td#btnPrint{
	display: none;
	}
}
</style>
<table width="90%"  border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>

	<tr>
		<td class="tableHeader"><abc:i18n property="requestsApproval.header.masterDetailsReport"/><fmt:message key="requestsApproval.header.masterDetailsReport"/></td><td align="left"></td>
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
			<form id="masterDetailsReport" name="masterDetailsReport"	method="POST" action="<c:url value="/requestsApproval/masterDetailsReport.html"/>">
				    <input type="hidden"  id="requestType" name="requestType" value="${requestType}"/>
					<div id="result">
					<table border=0 cellspacing=1 cellpadding=0 id="ep" style="margin-right:40px">
						<tr id="head_1_ep">
							<td class="bodyBold" colspan=4 nowrap>
							<abc:i18n property="requestsApproval.header.masterDetailsReport"/><fmt:message key="requestsApproval.header.masterDetailsReport"/>
							</td>
						</tr>
						
						<tr height="10">
						</tr>

						<tr>
						</tr>										
					</table>
					<table rules="all" align="center" class="sofT">
									<tr>
									<c:if test="${requestType=='1'}">
										<td colspan="14" class="helpTitle">
											<abc:i18n property="requestsApproval.header.empSpecialVacationsReportsForm" />
											<fmt:message key="requestsApproval.header.empSpecialVacationsReportsForm" />
										</td>
									</c:if>
									<c:if test="${requestType=='2'}">
										<td colspan="14" class="helpTitle">
											<abc:i18n property="requestsApproval.header.empAnnualVacationsReportsForm" />
											<fmt:message key="requestsApproval.header.empAnnualVacationsReportsForm" />
										</td>
									</c:if>
									<c:if test="${requestType=='3'}">
										<td colspan="14" class="helpTitle">
											<abc:i18n property="requestsApproval.header.empRequestsReportsForm3" />
											<fmt:message key="requestsApproval.header.empRequestsReportsForm3" />
										</td>
									</c:if>									
									</tr>
									
									<tr>									
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.userCode" />
											<fmt:message key="requestsApproval.caption.userCode" />
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.userName" />
											<fmt:message key="requestsApproval.caption.userName" />
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.requestNumber"/>
											<fmt:message key="requestsApproval.caption.requestNumber"/>
										</td>										
									</tr>
									
								<c:forEach varStatus="loop" var="record" items="${loginUserReqs}">
									<tr height=20 bgcolor="#F8F8F8">
										<td  nowrap>
											${record.empCode}
										</td>
										<td  nowrap>
											${record.login_user.name}
										</td>
										<td  nowrap>
											${record.requestNumber}										
										</td>
									</tr>
								</c:forEach>									
							</table>
							<table rules="all" align="center" class="sofT">
								<tr>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="requestsApproval.caption.requestNumber"/>
										<fmt:message key="requestsApproval.caption.requestNumber"/>
									</td>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="requestsApproval.caption.requestType" />
										<fmt:message key="requestsApproval.caption.requestType" />
									</td>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="requestsApproval.caption.requestDate" />
									 	<fmt:message key="requestsApproval.caption.requestDate" />
									</td>
							  		<td nowrap class="helpHed" nowrap="nowrap">
										<abc:i18n property="commons.caption.fromDate"/>
										<fmt:message key="commons.caption.fromDate"/>
									</td>										
							  		<td nowrap class="helpHed" nowrap="nowrap">
										<abc:i18n property="commons.caption.toDate"/>
										<fmt:message key="commons.caption.toDate"/>
									</td>
									<td nowrap class="helpHed" nowrap="nowrap">
										<abc:i18n property="requestsApproval.caption.vacPeriod"/>
										<fmt:message key="requestsApproval.caption.vacPeriod"/>
									</td>	
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="commons.caption.from" />
									 	<fmt:message key="commons.caption.from" />
									</td>
										
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="commons.caption.to" />
									 	<fmt:message key="commons.caption.to" />
									</td>
									<td class="helpHed" nowrap="nowrap">
									<abc:i18n
									property="requestsApproval.requestsApprovalForm.reqStatus" /> <fmt:message
									key="requestsApproval.requestsApprovalForm.reqStatus" /> 
									</td>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="requestsApproval.caption.reply" />
									 	<fmt:message key="requestsApproval.caption.reply" />
									</td>
									
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="requestsApproval.button.requestStatus" />
									 	<fmt:message key="requestsApproval.button.requestStatus" />
									</td>
								</tr>
										
								<c:forEach varStatus="loop" var="record" items="${loginUserReqs}">

										
									<c:forEach varStatus="loop" var="item" items="${statusList}">
										<tr>			
											<td  nowrap>
												${record.requestNumber}										
											</td>																	
											<td  nowrap>
												${record.request_id.description}
											</td>
											<td  nowrap>
												 <_4s_:formatMiladiDate value="${record.request_date}"/>
											</td>
											<td  nowrap>
												 <_4s_:formatMiladiDate value="${record.from_date}"/>
											</td>
											<td  nowrap>
												 <_4s_:formatMiladiDate value="${record.to_date}"/>
											</td>
											<td  nowrap>
												${record.withdrawDays}
											</td>																														
											<td  nowrap>
												<_4s_:formatMiladiDate value="${record.period_from}"/>
											<_4s_:timeString value="${record.period_from}"/>
											</td>
											<td  nowrap>
												<_4s_:formatMiladiDate value="${record.period_to}"/>
											<_4s_:timeString value="${record.period_to}"/>
											</td>
											<td   nowrap>		
											<c:if test="${record.approved==1}">					
											
											<abc:i18n
												property="requestsApproval.requestsApprovalForm.reqApproval" />
											<fmt:message
												key="requestsApproval.requestsApprovalForm.reqApproval" />
											</c:if>
											<c:if test="${record.approved==99}">					
											
											<abc:i18n
												property="requestsApproval.requestsApprovalForm.reqRejected" />
											<fmt:message
												key="requestsApproval.requestsApprovalForm.reqRejected" />
											</c:if>
											<c:if test="${record.approved==0}">
											
										    	لم تكتمل
											
											</c:if>
											</td>										
											<td  nowrap>
												${record.reply}
											</td>
											
											<td  nowrap>
												
											</td>

										</tr>
										
									</c:forEach>
								</c:forEach>									
							</table>
								</div>
								<table align="center">
									<tr>
										<td colspan="2" align="center">
											<abc:i18n property="commons.button.print"/>
											<input type="button" id="btnPrint" class="button" value="<fmt:message key="commons.button.print"/>" onClick="printthis('result')"></input>
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
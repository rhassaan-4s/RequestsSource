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
	
	var dateFrom=document.getElementById("request_date_from").value;
	//alert ('-----dateFrom---'+dateFrom);
	var dateTo=document.getElementById("request_date_to").value;
	//alert ('-----dateTo---'+dateTo);
//	if(document.getElementById("namehidden").value!=null){
	//	var empName=document.getElementById("namehidden").value;
	//} 
	//alert('from---'+document.getElementById("from").value);


	
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
	
	var URL='attendanceRequestsReports.html?empCode='+empCode+'&dateFrom='+dateFrom+'&dateTo='+dateTo+'&codeFrom='+codeFrom+'&codeTo='+codeTo+'&statusId='+statusId;
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
		<td class="tableHeader"><abc:i18n property="requestsApproval.header.attendanceRequestsReports"/><fmt:message key="requestsApproval.header.attendanceRequestsReports"/></td><td align="left"></td>
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
			
		</td>
	</tr>
	<tr>
		<td>
			<form id="attendanceRequestsReports" name="attendanceRequestsReports"	method="POST" action="<c:url value="/requestsApproval/attendanceRequestsReports.html"/>">
				    <input type="hidden"  id="requestType" name="requestType" value="${requestType}"/>
				    <input type="hidden"  id="errand" name="errand" value="${errand}"/>
					<div id="result">
					<table border=0 cellspacing=1 cellpadding=0 id="ep" style="margin-right:40px">
						<tr id="head_1_ep">
						
							<td class="bodyBold" colspan=4 nowrap>
							<abc:i18n property="requestsApproval.header.attendanceRequestsReports"/><fmt:message key="requestsApproval.header.attendanceRequestsReports"/>
							</td>
						</tr>

		
						<tr id="btnPrint">
					  		<td nowrap class="formBodControl">
								<abc:i18n property="requestsApproval.caption.userCode"/>
								<fmt:message key="requestsApproval.caption.userCode"/>
							</td>						
							<td  class="formBod"> 
								<abc:autocomplete 
									inputId="empCode" 
									inputName="empCode" 
									table="login_users" 
									firstKey="commons.caption.code"
									secondKey="commons.caption.name"
									firstParam="empCode"
									secondParam="name"
									bindById="true"
									valueString="${empCode}"
									valueId=""/>
							</td>
							
							<td nowrap class="formBodControl" >
								<abc:i18n property="requestsApproval.requestsApprovalForm.reqStatus"/>
								<fmt:message key="requestsApproval.requestsApprovalForm.reqStatus"/>
							</td>
							<td  class="formBodControl" >
								<select name="statusId" id="statusId">
									<option value="" ${status == null?'selected':''}><fmt:message key="commons.caption.select" /></option>
									<option value="0" ${status==0?'selected':''}>لم تكتمل</option>
									<option value="1" ${status==1?'selected':''}>موافق</option>
									<option value="99" ${status==99?'selected' : ''}>مرفوض</option>
								</select>
							</td>																				
						</tr>
						
						<tr id="btnPrint">
							<td nowrap class="formBodControl" >
								<abc:i18n property="requestsApproval.caption.requestDate"/>
								<fmt:message key="requestsApproval.caption.requestDate"/>
							</td>
						</tr>
							<tr id="btnPrint">
								<td nowrap class="formBodControl"><abc:i18n
										property="commons.caption.from" /> <fmt:message
										key="commons.caption.from" /></td>
								<td class="formBodControl"><input type="text"
									class="calendar" class="MM_from_d" title="ccc"
									readonly="readonly" autocomplete="off" dir="ltr"
									name="request_date_from" id="request_date_from"
									value="${request_date_from}" /></td>
								<td nowrap class="formBodControl"><abc:i18n
										property="commons.caption.to" /> <fmt:message
										key="commons.caption.to" /></td>

								<td class="formBodControl"><input type="text"
									class="calendar" title="ccc" class="MM_to_d"
									readonly="readonly" autocomplete="off" dir="ltr"
									name="request_date_to" id="request_date_to"
									value="${request_date_to}" /></td>


							</tr>
							<tr id="btnPrint">
					  		<td nowrap class="formBodControl">
								<abc:i18n property="requestsApproval.caption.codeFrom"/>
								<fmt:message key="requestsApproval.caption.codeFrom"/>
							</td>
							<td  class="formBodControl" >
								<input type="text"  name="codeFrom" id="codeFrom"/>
							</td>
		
					  		<td nowrap class="formBodControl" >
								<abc:i18n property="requestsApproval.caption.codeTo"/>
								<fmt:message key="requestsApproval.caption.codeTo"/>
							</td>
							<td  class="formBodControl" >
								<input type="text"  name="codeTo" id="codeTo"/>
							</td>
						</tr>
												
						<tr height="10">
						</tr>

						<tr>
						</tr>
						<tr id="btnPrint">
							<td>
								<abc:i18n property="commons.button.search"/>
								<input type="submit" name="aaa"  value="<fmt:message key="commons.button.search"/> " class="button"/>
								&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						
						<tr height="10">
						</tr>

						<tr>
						</tr>										
					</table>
					<abc:paging url="attendanceRequestsReports.html" 
					parametersString="empCode=${empCode}&statusId=${status}&date_from=${request_date_from}&date_to=${request_date_to}&codeFrom=${codeFrom}&codeTo=${codeTo}"/>
					<table rules="all" align="center" class="sofT">
									<tr>
										<td colspan="14" class="helpTitle">
											<abc:i18n property="requestsApproval.header.attendanceRequestsReports" />
											<fmt:message key="requestsApproval.header.attendanceRequestsReports" />
										</td>
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
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.requestType" />
											<fmt:message key="requestsApproval.caption.requestType" />
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.requestDate" />
										 	<fmt:message key="requestsApproval.caption.requestDate" />
										</td>
										
								  		<td nowrap class="helpHed" nowrap="nowrap">
											<abc:i18n property="commons.caption.inDate"/>
											<fmt:message key="commons.caption.inDate"/>
										</td>										
										
										<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="requestsApproval.caption.location" /> <fmt:message
											key="requestsApproval.caption.location" /></td>
										<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="commons.caption.address" /> <fmt:message
											key="commons.caption.address" /></td>
											
											<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.InputTypeIn" /> <fmt:message
										key="requestsApproval.caption.InputTypeIn" /></td>
								  		
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n
											property="requestsApproval.caption.requestReason" /> <fmt:message
											key="requestsApproval.caption.requestReason" /> 
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
										<td class="helpHed" id="btnPrint">
										</td>
										
										<td class="helpHed" id="btnPrint">
										</td>										
									</tr>
									
								<c:forEach varStatus="loop" var="record" items="${results}">
									<tr height=20 bgcolor="#F8F8F8">
										<td  nowrap>
											${record.empCode}
										</td>
										<td  nowrap>
											${record.name}
										</td>
										<td  nowrap>
										<c:if test="${record.approved!=0}">
											<a href="attendanceRequestsApprovalForm.html?reqId=${record.id}">${record.requestNumber}</a>
										</c:if>
										<c:if test="${record.approved==0}">
											${record.requestNumber}
										</c:if>										
										</td>										
										<td  nowrap>
											${record.request_id.description}
										</td>
										<td  nowrap>
											 <_4s_:formatMiladiDate value="${record.request_date}"/>
										</td>
																													
										<td  nowrap>
											<_4s_:formatMiladiDate value="${record.period_from}"/>
											<_4s_:timeString value="${record.period_from}"/>
										</td>
										
										<td  nowrap>
							<c:choose>
								<c:when test="${record.isInsideCompany==true}">
									<fmt:message key="requestsApproval.caption.insideCompany" />
								</c:when>
								<c:otherwise>
									<fmt:message key="requestsApproval.caption.outsideCompany" />
								</c:otherwise>
							</c:choose>
								
							</td>
							
							<td >
								${record.locationAddress}
							</td>
							<td  nowrap>
									<c:choose>
									<c:when test="${record.inputType==0}">
										<fmt:message key="requestsApproval.caption.webAttendance" />
									</c:when>
									<c:when test="${record.inputType==1}">
										<fmt:message key="requestsApproval.caption.requestAttendance" />
									</c:when>
									<c:when test="${record.inputType==2}">
										<fmt:message key="requestsApproval.caption.androidAttendance" />
									</c:when>
									<c:otherwise>${record.inputType}</c:otherwise>
									</c:choose>
									</td>
										
										<td >
											${record.notes}
										</td>
										<td   nowrap>	
										<c:choose>
											<c:when test="${record.approval==1 || record.approval==2}">

												<abc:i18n
													property="requestsApproval.requestsApprovalForm.reqApproval" />
												<fmt:message
													key="requestsApproval.requestsApprovalForm.reqApproval" />
											</c:when>
											<c:when test="${record.approval==0}">

												<abc:i18n
													property="requestsApproval.requestsApprovalForm.reqRejected" />
												<fmt:message
													key="requestsApproval.requestsApprovalForm.reqRejected" />
											</c:when>
											<c:otherwise>
										
									    	لم تكتمل
									    	<br>
												<input type="checkbox" name="approve${loop.index}"
													id="approve${loop.index}" />

											</c:otherwise>
										</c:choose>
									</td>										
										<td  nowrap>
											${record.reply}
										</td>
									
										<c:choose>
										<c:when
											test="${record.approved==0 && settings.managerCanModifyAttendance=='true' && record.from_date_history==null}">
											<td id="btnPrint" nowrap><abc:i18n
													property="commons.button.edit" /><a
												href="attendanceRequestsApprovalEdit.html?reqId=${record.id}&empCodeQ=${empCode}&statusId=${status}&date_from=${request_date_from}&date_to=${request_date_to}&codeFrom=${codeFrom}&codeTo=${codeTo}"><fmt:message
														key="commons.button.edit" /></a></td>
										</c:when>
										<c:otherwise>
												<td id="btnPrint" nowrap>&nbsp;</td>
											</c:otherwise>
										</c:choose>
									<c:choose>
										<c:when test="${record.approval==0 || record.approval==null}">
											<td id="btnPrint" nowrap><abc:i18n
													property="commons.button.approve" />
													<a
												href="attendanceRequestsApprovalForm.html?reqId=${record.id}"><fmt:message
														key="commons.button.approve" /></a></td>
										</c:when>
										<c:otherwise>
											<td id="btnPrint" nowrap>&nbsp;</td>
										</c:otherwise>
									</c:choose>
								</tr>
								</c:forEach>									
								</table>
								</div>
								<table align="center">
									<tr>
										<td colspan="2" align="center">
											<abc:i18n property="commons.button.print"/>
											<input type="button" id="btnPrint" class="button" value="<fmt:message key="commons.button.print"/>" onClick="printthis('result')"></input>
										</td>
										<td colspan="2" align="center">
											<abc:i18n property="requestsApproval.button.approveAll"/>
											<input type="submit" id="approveAll" name="approveAll" class="button" value="<fmt:message key="requestsApproval.button.approveAll"/>" onClick="approveAll()"></input>
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
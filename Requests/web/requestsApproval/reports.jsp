<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<abc:security property="1034"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Insert title here</title>
<script type='text/javascript' src='/Requests/dwr/interface/requestsDwr.js'></script>
<script type='text/javascript' src='/Requests/dwr/engine.js'></script>
</head>
<body>
<script type="text/javascript">
	
function exportExcel() {
	alert("test");
	var fromDate = document.getElementById('request_date_from').value;
	var toDate = document.getElementById('request_date_to').value;
	var requestId = document.getElementById('request_id').value;
	var codeFrom = document.getElementById('codeFrom').value;
	document.getElementById('export').value="true";
	alert(document.getElementById('export').value);
	var codeTo = document.getElementById('codeTo').value;
	document.forms[0].export.value = "true";
	alert(document.forms[0].export.value);
	//var link = '/Requests/requestsApproval/reports.html?export=true&dateFrom='+fromDate+'&dateTo='+toDate+'&requestType='+requestId+'&codeFrom='+codeFrom+'&codeTo='+codeTo;
	document.getElementById("reports").submit();
	//window.open(link);
}

function getRequestStatus(id){
	//alert('entered');
	//alert('--id value---'+id.id);
	var req_id =id.id;
		//document.getElementById('empRequestTypeId').value;
	//alert('req_id= '+req_id);
	requestsDwr.getRequestStatus(returnedData,req_id);	
}

function  returnedData(data){
	//alert('entered after');
	 if (data!=null){
		 //alert('not null');
		 var st='';

		// alert('---- i< data.length---'+data.length);
		 for ( var i= 0; i < data.length; i++) {
			 //alert('---for- i< data.length---'+data.length);
			var ob=data[i];
			if(ob.approval==1){
				st='موافق';
			}else if(ob.approval==0){
				st='مرفوض\n سبب الرفض: '+ob.note;
			}
			alert(ob.user_id.name+"--"+st);
		}
			if(st==''){
				alert('لم يتم الموافقه عليه');
			}
				//st='لم يتم الموافقه عليه ';
			

	 }
}

function searchForm (){

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
	if(document.getElementById("request_id").value!=null){
		var requestType=document.getElementById("request_id").value;
	}

	if(document.getElementById("codeFrom").value!=null){
		var codeFrom=document.getElementById("codeFrom").value;
		//alert('---dateFrom----'+dateFrom);
	}
	if(document.getElementById("codeTo").value!=null){
		var codeTo=document.getElementById("codeTo").value;
	}
	document.forms[0].export.value = "";
	var URL='reports.html?dateFrom='+dateFrom+'&dateTo='+dateTo+'&requestType='+requestType+'&codeFrom='+codeFrom+'&codeTo='+codeTo;
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
		<td class="tableHeader"><abc:i18n property="requestsApproval.menu.allReports"/><fmt:message key="requestsApproval.menu.allReports"/></td><td align="left"></td>
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
			<form id="reports" name="reports"	method="POST" action="<c:url value="/requestsApproval/reports.html"/>">
				     <input type="hidden"  id="export" name="export" value="${export}"/>
					<div id="result">

					<table>
						<tr id="head_1_ep">
						
							<td class="bodyBold" colspan=4 nowrap>
							<abc:i18n property="requestsApproval.menu.allReports"/><fmt:message key="requestsApproval.menu.allReports"/>
							</td>											 
							<td id="btnPrint" nowrap colspan=1 align=left> <abc:i18n property="commons.caption.requiredInformation"/><span class="bodySmallBold"><fmt:message key="commons.caption.requiredInformation"/></span></td>
						</tr>
						<tr id="btnPrint">
					  		<td nowrap class="formBodControl" >
								<abc:i18n property="requestsApproval.caption.requestType"/>
								<fmt:message key="requestsApproval.caption.requestType"/>
							</td>
							<td  class="formBodControl" >
									<select name="requestType" id="requestType">
										<option value=""><fmt:message key="commons.caption.select" /></option>						
											<c:forEach items="${requestTypeList}" var="request">
												<option value="${request.id}" ${request.id == requestType ?'selected':''}>${request.description}</option>
											</c:forEach>
									</select>
							</td>													
						</tr>
		
						<tr id="btnPrint"> 
							<td nowrap class="formBodControl">
								<abc:i18n property="requestsApproval.caption.requestDate"/>
								<fmt:message key="requestsApproval.caption.requestDate"/>
							</td>
						</tr>						
						<tr id="btnPrint">
					  		<td nowrap class="formBodControl">
								<abc:i18n property="commons.caption.from"/>
								<fmt:message key="commons.caption.from"/>
							</td>
							<td  class="formBodControl" >
								<input type="text"  class="calendar"   readonly="readonly" autocomplete="off" dir="ltr" name="request_date_from" id="request_date_from" value="${request_date_from}"/>
							</td>
		
					  		<td nowrap class="formBodControl" >
								<abc:i18n property="commons.caption.to"/>
								<fmt:message key="commons.caption.to"/>
							</td>
							<td  class="formBodControl" >
								<input type="text"  class="calendar"  title="ccc"  readonly="readonly" autocomplete="off" dir="ltr" name="request_date_to" id="request_date_to" value="${request_date_to}"/>
							</td>
						</tr>
						
						<tr id="btnPrint">
					  		<td nowrap class="formBodControl">
								<abc:i18n property="requestsApproval.caption.codeFrom"/>
								<fmt:message key="requestsApproval.caption.codeFrom"/>
							</td>
							<td  class="formBodControl" >
								<abc:autocomplete 
									inputId="codeFrom" 
									inputName="${status.expression}" 
									table="login_users" 
									firstKey="commons.caption.code"
									secondKey="commons.caption.name"
									firstParam="empCode"
									secondParam="name"
									bindById="true"
									valueString="${codeFrom}"
									valueId="${empId}" />
							</td>
								
		
					  		<td nowrap class="formBodControl" >
								<abc:i18n property="requestsApproval.caption.codeTo"/>
								<fmt:message key="requestsApproval.caption.codeTo"/>
							</td>
							<td  class="formBodControl" >
								<abc:autocomplete 
									inputId="codeTo" 
									inputName="${status.expression}" 
									table="login_users" 
									firstKey="commons.caption.code"
									secondKey="commons.caption.name"
									firstParam="empCode"
									secondParam="name"
									bindById="true"
									valueString="${codeTo}"
									valueId="${empId}" />
							</td>
						</tr>
												
						<tr>
							<td>
								<abc:i18n property="commons.button.search"/>
								<input type="submit" id="btnPrint" name="aaa"  value="<fmt:message key="commons.button.search"/> " class="button"/>
								&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr height="10">
						</tr>
					</table>	
					
					<abc:paging url="reports.html" 
					parametersString="requestType=${requestType}&request_date_from=${request_date_from}&request_date_to=${request_date_to}&codeFrom=${codeFrom}&codeTo=${codeTo}"/>		
					<table rules="all" align="center" width="70%" class="sofT">
								
						<tr>						
						    <td class="helpHed" nowrap="nowrap"><abc:i18n
								property="requestsApproval.caption.userCode" /> <fmt:message
								key="requestsApproval.caption.userCode" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="requestsApproval.caption.userName" /> <fmt:message
								key="requestsApproval.caption.userName" /></td>
					  		<td class="helpHed" nowrap="nowrap">
								<abc:i18n property="requestsApproval.caption.requestNumber"/>
								<fmt:message key="requestsApproval.caption.requestNumber"/>
							</td>								
							<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="requestsApproval.caption.requestType" /> <fmt:message
								key="requestsApproval.caption.requestType" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="requestsApproval.caption.requestDate" /> <fmt:message
								key="requestsApproval.caption.requestDate" /></td>								
							<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="commons.caption.fromDate" /> <fmt:message
								key="commons.caption.fromDate" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="commons.caption.toDate" /> <fmt:message
								key="commons.caption.toDate" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="commons.caption.from" /> <fmt:message
								key="commons.caption.from" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="commons.caption.to" /> <fmt:message
								key="commons.caption.to" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="requestsApproval.caption.location" /> <fmt:message
								key="requestsApproval.caption.location" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="commons.caption.address" /> <fmt:message
											key="commons.caption.address" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="requestsApproval.requestsApprovalForm.reqStatus" /> <fmt:message
									key="requestsApproval.requestsApprovalForm.reqStatus" /></td>
							<td class="helpHed" nowrap="nowrap">
								<abc:i18n property="requestsApproval.caption.reply" />
							 	<fmt:message key="requestsApproval.caption.reply" />
							</td>	
							<td class="helpHed" nowrap="nowrap">
								&nbsp;
							</td>																														
						</tr>
					<c:forEach items="${results}" var="record">
						<tr height=20 bgcolor="#F8F8F8"> 							        
							<td  nowrap>
						         ${record.empCode }
							</td>
							  
							<td  nowrap>
						         ${record.login_user.name }
							</td>
									<td nowrap>${record.requestNumber}</td>
									<c:choose>
										<c:when test="${record.vacation==null}">
											<td nowrap>${record.request_id.description}</td>
										</c:when>
										<c:otherwise>
											<td nowrap>${record.vacation.name}</td>
										</c:otherwise>
									</c:choose>

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
						         <_4s_:formatMiladiDate value="${record.period_from}"/>
											<_4s_:timeString value="${record.period_from}"/>
							</td>
						
							<td  nowrap>
						         <_4s_:formatMiladiDate value="${record.period_to}"/>
											<_4s_:timeString value="${record.period_to}"/>
							</td>


									<c:choose>
										<c:when test="${record.isInsideCompany==true}">
											<td nowrap title="${record.locationAddress}"><fmt:message
													key="requestsApproval.caption.insideCompany" /></td>
										</c:when>
										<c:otherwise>
											<td nowrap title="${record.locationAddress}" style="font: bold; color:red;"><fmt:message
													key="requestsApproval.caption.outsideCompany" /></td>
										</c:otherwise>
									</c:choose>
									
									<td  >
										${record.locationAddress}
									</td>

						<c:if test="${record.approved==1}">					
							<td  nowrap>
								<abc:i18n
									property="requestsApproval.requestsApprovalForm.reqApproval" />
								<fmt:message
									key="requestsApproval.requestsApprovalForm.reqApproval" />
							</td>
							</c:if>
														
							<c:if test="${record.approved==99}">
							<td  nowrap>
						    	<abc:i18n
									property="requestsApproval.requestsApprovalForm.reqRejected" />
								<fmt:message
									key="requestsApproval.requestsApprovalForm.reqRejected" />
							</td>
							</c:if>

							<c:if test="${record.approved==0}">
							<td  nowrap>
						    	
							</td>
							</c:if>
							<td class="helpBod" nowrap>
								${record.reply}
							</td>
							
							<td  nowrap id="btnPrint">
								<input type="button" id="${record.id}" value="<fmt:message key="requestsApproval.button.requestStatus"/>" name="requestStatus" class="button"	onclick="getRequestStatus(this)"></input>
							</td>
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
						<abc:i18n property="commons.button.export"/>
						<input type="submit" id="btnexport" onselect="exportExcel();" class="button" value="<fmt:message key="commons.button.export"/>" onClick="exportExcel();"></input><!--   -->
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
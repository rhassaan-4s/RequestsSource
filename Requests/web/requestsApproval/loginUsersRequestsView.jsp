<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<abc:security property="106"/>

<script type='text/javascript' src='/Requests/dwr/interface/requestsDwr.js'></script>
<script type='text/javascript' src='/Requests/dwr/engine.js'></script>

<script type="text/javascript">
function searchForm (){
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
	var URL='loginUsersRequestsView.html?dateFrom='+dateFrom+'&dateTo='+dateTo+'&requestType='+requestType;
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

function getRequestStatus(id){
	//alert('entered');
	//alert('--id value---'+id.id);
	var req_id =id.id;
		//document.getElementById('empRequestTypeId').value;
	//alert('req_id= '+req_id);
	requestsDwr.getRequestStatus(returnedData,req_id);	
}

function returnedData(data){
	//alert('entered after');
	if (data!=null){
		 //alert('not null');
		var st='';
		var result='';
		 //<form action="file.php" method="post" target="foo" onSubmit="window.open('', 'foo', 'width=450,height=300,status=yes,resizable=yes,scrollbars=yes')">
		// alert('---- i< data.length---'+data.length);
		for ( var i= 0; i < data.length; i++) {
			 //alert('---for- i< data.length---'+data.length);
			var ob=data[i];
			if(ob.approval==1){
				st='موافق';
			}else if(ob.approval==0){
				st='مرفوض\n سبب الرفض: '+ob.note;
			}
			result +="\n"+ob.level_id.order+" . "+ob.user_id.name+" -- "+st;
			
			//alert(ob.user_id.name+"--"+st);
		}
			//alert('-----out----');
		if(st=='' && result==''){
			result='لم يتم الموافقه عليه';
		}
		alert(result);
		document.getElementById("resultList").value=result;
	 }
}
</script>
<script type="text/javascript">

function printSelection(node){

  var content=node.innerHTML
  var pwin=window.open('','print_content','width=100,height=100');

  pwin.document.open();
  pwin.document.write('<html><body onload="window.print()">'+content+'</body></html>');
  pwin.document.close();
 
  setTimeout(function(){pwin.close();},1000);

}
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

<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   <form id="loginUsersRequestsView" name="loginUsersRequestsView" method="POST"  action="<c:url value="/requestsApproval/loginUsersRequestsView.html"/>">
		 <input type="hidden"  id="empRequestTypeId" name="empRequestTypeId" value="${empRequestTypeId }"/>
		 <input type="hidden"  id="resultList" name="resultList" value=""/>
 
		<table border=0 cellspacing=1 cellpadding=0 id="ep" style="margin-right:40px">
			<tr>
				<td class="tableHeader" colspan=4 nowrap>
					<abc:i18n property="requestsApproval.header.loginUsersRequestsView"/><fmt:message key="requestsApproval.header.loginUsersRequestsView"/> : &nbsp;${employee.firstName}
				</td>	
			</tr>
			
			<tr>
			</tr>
			<tr>
				<td>				
					<table>
						<tr id="btnPrint">
					  		<td nowrap class="formBodControl" >
								<abc:i18n property="requestsApproval.caption.requestType"/>
								<fmt:message key="requestsApproval.caption.requestType"/>
							</td>
							<td  class="formBodControl" >
								<select name="request_id" id="request_id">
									<option value=""><fmt:message key="commons.caption.select" /></option>						
										<c:forEach items="${requestTypeList}" var="request"> 
											<option value="${request.id}" ${request.id == request_id ?' selected' : ''}">${request.description}</option>
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
								<input type="text"  class="calendar" readonly="readonly" autocomplete="off" dir="ltr" name="request_date_from" id="request_date_from" value="${request_date_from}" />
							</td>
		
					  		<td nowrap class="formBodControl" >
								<abc:i18n property="commons.caption.to"/>
								<fmt:message key="commons.caption.to"/>
							</td>
							<td  class="formBodControl" >
								<input type="text"  class="calendar"  readonly="readonly" autocomplete="off" dir="ltr" name="request_date_to" id="request_date_to" value="${request_date_to}" />
							</td>
						</tr>
						
						
						<tr>
							<td>
								<abc:i18n property="commons.button.search"/>
								<input type="button" id="btnPrint" name="aaa" onclick="searchForm()" value="<fmt:message key="commons.button.search"/> " class="button"/>
								&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr height="10">
						</tr>
					</table>			
				</td>
			</tr>

			<tr>
				<td>
				<div id="result">
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
								property="requestsApproval.requestsApprovalForm.reqPeriod" /> <fmt:message
								key="requestsApproval.requestsApprovalForm.reqPeriod" /></td>								
							<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="commons.caption.from" /> <fmt:message
								key="commons.caption.from" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="commons.caption.to" /> <fmt:message
								key="commons.caption.to" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="requestsApproval.requestsApprovalForm.reqStatus" /> <fmt:message
									key="requestsApproval.requestsApprovalForm.reqStatus" /></td>
							<td class="helpHed" nowrap="nowrap">
								<abc:i18n property="requestsApproval.caption.reply" />
							 	<fmt:message key="requestsApproval.caption.reply" />
							</td>																														
							<td class="helpHed">
							</td>
						</tr>
					<c:forEach items="${records}" var="record">
						<tr height=20 bgcolor="#F8F8F8"> 							        
							<td  nowrap>
						         ${record.empCode }
							</td>
							  
							<td  nowrap>
						         ${record.login_user.name }
							</td>
							<td  nowrap>
								${record.requestNumber}
							</td>
							<c:choose>
							<c:when test="${record.request_id.id==1 && record.vacation.vacation=='999'}">
							<td  nowrap>
						         مأموريه
							</td>
							</c:when>
							<c:otherwise>
							<td  nowrap>
						         ${record.request_id.description }
							</td>
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
						    	لم تكتمل
							</td>
							</c:if>
							<td class="helpBod">
								${record.reply}
							</td>
							
							<c:choose>
								<c:when test="${record.approved==0}">	
									<c:choose>
										<c:when test="${record.request_id.id==10 || record.request_id.id==11}">								
											<td  nowrap id="btnPrint"><abc:i18n
											property="commons.button.edit" /><a
											href="attendanceRequestForm.html?request_date=${record.request_date }&empRequestTypeId=${record.id}"><fmt:message
											key="commons.button.edit" /></a>
											</td>	
										</c:when>
										<c:otherwise>
											<c:if test="${record.approved==0}">																							  
												<td  nowrap id="btnPrint"><abc:i18n
													property="commons.button.edit" /><a
													href="loginUsersRequestsForm.html?request_date=${record.request_date }&empRequestTypeId=${record.id}"><fmt:message
													key="commons.button.edit" /></a>
												</td>
											</c:if>				
										</c:otherwise>
									</c:choose>	
								</c:when>
							<c:otherwise>
							<td  nowrap >
							</td>
							</c:otherwise>
							</c:choose>														
							<td  nowrap id="btnPrint">
							<input type="button" id="${record.id}" value="<fmt:message key="requestsApproval.button.requestStatus"/>" name="requestStatus" class="button"	onclick="getRequestStatus(this)"></input>
							</td>
						</tr>
						
					</c:forEach>
						
					</table>
				</div>
				</td>
			</tr>
			
	</table>
	
	<table align="center">
		<tr>
			<td colspan="2" align="center">
			<br>
				<abc:i18n property="commons.button.add"/>
				<input type="button" id="btnPrint" value="<fmt:message key="commons.button.add"/>" name="add" class="button"	onclick="window.location='loginUsersRequestsForm.html'"></input>
			</td>			
	
			<td colspan="2" align="center"> 
			<br>
				<abc:i18n property="commons.button.delete"/>
				<input type="button" id="btnPrint" value="<fmt:message key="commons.button.delete"/>" name="delete" class="button"	onclick="window.location='deleteLoginUsersRequestsForm.html'"></input>
			</td>
			<td colspan="2" align="center">
			<br>
				<abc:i18n property="commons.button.print"/>
				<input type="button" id="btnPrint" class="button" value="<fmt:message key="commons.button.print"/>" onClick="printthis('result')"></input>
			</td>
		</tr>
	</table>
   </form>

</table>

	
			
<%@ include file="/web/common/includes/footer.jsp" %>
   
<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<abc:security property="106" />

<script type='text/javascript'
	src='/Requests/dwr/interface/requestsDwr.js'></script>
<script type='text/javascript' src='/Requests/dwr/engine.js'></script>

<script type="text/javascript">
function searchForm (){
	var activity=document.getElementById("activity");
	var costcenter=document.getElementById("costcenter");
	
	var part1=document.getElementById("part1");
	var part2=document.getElementById("part2");
	var part3=document.getElementById("part3");
	
	var partVal1= "";
	var partVal2= "";
	var partVal3= "";
	
	if(document.getElementById("dateFrom").value!=null){
		var dateFrom=document.getElementById("dateFrom").value;
		//alert('---dateFrom----'+dateFrom);
	}
	if(document.getElementById("dateTo").value!=null){
		var dateTo=document.getElementById("dateTo").value;
	}
	if(activity.value!=null){
		var activityVal=activity.value;
	}
	if(costcenter.value!=null){
		var costcenterVal=costcenter.value;
	}
	
	if(part1!=null && part1.value!=null){
		partVal1=part1.value;
	}
	if(part2!=null && part2.value!=null){
		partVal2=part2.value;
	}
	if(part3!=null && part3.value!=null){
		partVal3=part3.value;
	}
	var URL='timesheetTrans.html?dateFrom='+dateFrom+'&dateTo='+dateTo+'&activity='+activityVal+'&costcenter='+costcenterVal+'&part1='+partVal1+'&part2='+partVal2+'&part3='+partVal3;
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
	tr#btnPrint {
		display: none;
	}
	td#btnPrint {
		display: none;
	}
}
</style>

<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<form id="timesheetTrans" name="timesheetTrans" method="POST"
		action="<c:url value="/timesheet/timesheetTrans.html"/>">

		<table border=0 cellspacing=1 cellpadding=0 id="ep"
			style="margin-right: 40px">
			<tr>
				<td class="tableHeader" colspan=4 nowrap><abc:i18n
						property="timesheet.header.timesheetTrans" /> <fmt:message
						key="timesheet.header.timesheetTrans" /> :
					&nbsp;${employee.firstName}</td>
			</tr>

			<tr>
			</tr>
			<tr>
				<td>
					<table>

						<tr id="btnPrint">
							<td nowrap class="formBodControl"><abc:i18n
									property="requestsApproval.caption.requestDate" /> <fmt:message
									key="requestsApproval.caption.requestDate" /></td>
						</tr>
						<tr>
							<td nowrap class="formBodControl"><abc:i18n
									property="commons.caption.fromDate" /> <fmt:message
									key="commons.caption.fromDate" /></td>
							<td class="formBodControl"><input type="text"
								class="calendar" readonly="readonly" autocomplete="off"
								name="dateFrom" id="dateFrom" value="${dateFrom}" /></td>
							<td nowrap class="formBodControl"><abc:i18n
									property="commons.caption.toDate" /> <fmt:message
									key="commons.caption.toDate" /></td>
							<td class="formBodControl"><input type="text"
								class="calendar" readonly="readonly" autocomplete="off"
								name="dateTo" id="dateTo" value="${dateTo}" /></td>

						</tr>

						<tr>
							<td nowrap class="formBodControl"><abc:i18n
									property="timesheet.caption.activityName" /> <fmt:message
									key="timesheet.caption.activityName" /></td>
							<td class="formBodControl"><select name="activity"
								id="activity">
									<option value=""><fmt:message
											key="commons.caption.select" /></option>
									<c:forEach items="${activityList}" var="activity">
										<option value="${activity.activity}"
											${activity.activity == selectedActivity ?'selected':''}>${activity.name}</option>
									</c:forEach>
							</select></td>
						</tr>

						<tr>
							<td nowrap class="formBodControl"><abc:i18n
									property="timesheet.caption.costcenterName" /> <fmt:message
									key="timesheet.caption.costcenterName" /></td>
							<td class="formBodControl"><select name="costcenter"
								id="costcenter">
									<option value=""><fmt:message
											key="commons.caption.select" /></option>
									<c:forEach items="${costcenterList}" var="costcenter">
										<option value="${costcenter.costCode}"
											${costcenter.costCode == selectedCostcenter ?'selected':''}>${costcenter.costCode}-${costcenter.costName}</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
						<c:choose>
							<c:when
								test="${specs!=null && specs!='' && specs.part1_name!=null && specs.part1_name!=''}">
								
									<td nowrap class="formBodControl">${specs.part1_name}</td>
									<td class="formBodControl"><select name="part1" id="part1">
											<option value=""><fmt:message
													key="commons.caption.select" /></option>
											<c:forEach items="${partList1}" var="part1">
												<option value="${part1.code}"
													${part1.code == selectedPart1 ?'selected':''}>${part1.name}</option>
											</c:forEach>
									</select></td>
							</c:when>
						</c:choose>
						<c:choose>
							<c:when
								test="${specs!=null && specs!='' && specs.part2_name!=null && specs.part2_name!=''}">
									<td nowrap class="formBodControl">${specs.part2_name}</td>
									<td class="formBodControl"><select name="part2" id="part2">
											<option value=""><fmt:message
													key="commons.caption.select" /></option>
											<c:forEach items="${partList2}" var="part2">
												<option value="${part2.code}"
													${part2.code == selectedPart2 ? 'selected':''}>${part2.name}</option>
											</c:forEach>
									</select></td>
							</c:when>
						</c:choose>
						<c:choose>
							<c:when
								test="${specs!=null && specs!='' && specs.part3_name!=null && specs.part3_name!=''}">
									<td nowrap class="formBodControl">${specs.part3_name}</td>
									<td class="formBodControl"><select name="part3" id="part3">
											<option value=""><fmt:message
													key="commons.caption.select" /></option>
											<c:forEach items="${partList3}" var="part3">
												<option value="${part3.code}"
													${part3.code == selectedPart3 ?'selected':''}>${part3.name}</option>
											</c:forEach>
									</select></td>
							</c:when>
						</c:choose>
</tr>
						<tr>
							<td><abc:i18n property="commons.button.search" /> <input
								type="button" id="btnPrint" name="aaa" onclick="searchForm()"
								value="<fmt:message key="commons.button.search"/> "
								class="button" /> &nbsp;&nbsp;&nbsp;</td>
						</tr>
						<tr height="10">
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td>
					<div id="result">
						<abc:paging url="timesheetTrans.html"
							parametersString="dateFrom=${dateFrom}&dateTo=${dateTo}" />
						<table rules="all" align="center" width="70%" class="sofT">

							<tr>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.userCode" /> <fmt:message
										key="requestsApproval.caption.userCode" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.requestDate" /> <fmt:message
										key="requestsApproval.caption.requestDate" /></td>

								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="timesheet.caption.activityName" /> <fmt:message
										key="timesheet.caption.activityName" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="timesheet.caption.costcenterName" /> <fmt:message
										key="timesheet.caption.costcenterName" /></td>
								<c:choose>
									<c:when
										test="${specs!=null && specs!='' && specs.part1_name!=null && specs.part1_name!=''}">
										<td class="helpHed" nowrap="nowrap">${specs.part1_name}</td>
									</c:when>
									<c:when test="${specs==null || specs==''}">

										<td class="helpHed" nowrap="nowrap">Part1 (Please insert
											timesheet transaction specifications)</td>

									</c:when>
								</c:choose>
								<c:choose>
									<c:when
										test="${specs!=null && specs!='' && specs.part2_name!=null && specs.part2_name!=''}">
										<td class="helpHed" nowrap="nowrap">${specs.part2_name}</td>
									</c:when>
									<c:when test="${specs==null || specs==''}">

										<td class="helpHed" nowrap="nowrap">Part2 (Please insert
											timesheet transaction specifications)</td>

									</c:when>
								</c:choose>
								<c:choose>
									<c:when
										test="${specs!=null && specs!='' && specs.part3_name!=null && specs.part3_name!=''}">
										<td class="helpHed" nowrap="nowrap">${specs.part3_name}</td>
									</c:when>
									<c:when test="${specs==null || specs==''}">

										<td class="helpHed" nowrap="nowrap">Part3 (Please insert
											timesheet transaction specifications)</td>

									</c:when>
								</c:choose>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="commons.caption.time" /> <fmt:message
										key="commons.caption.time" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="commons.caption.notes" /> <fmt:message
										key="commons.caption.notes" /></td>
										
										
							</tr>
							<c:forEach items="${Results}" var="record">
								<tr height=20 bgcolor="#F8F8F8">
									<td nowrap>${record.empCode}</td>

									<td nowrap>${record.indate}</td>
									<td nowrap>${record.name}<!-- activity name -->
									</td>
									<td nowrap>${record.costName}</td>
									<c:if
										test="${specs!=null && specs!='' && specs.part1_name!=null && specs.part1_name!=''}">
										<td nowrap>${record.partName1}</td>
									</c:if>
									<c:if
										test="${specs!=null && specs!='' && specs.part2_name!=null && specs.part2_name!=''}">
										<td nowrap>${record.partName2}</td>
									</c:if>
									<c:if
										test="${specs!=null && specs!='' && specs.part3_name!=null && specs.part3_name!=''}">
										<td nowrap>${record.partName3}</td>
									</c:if>
									<td>${record.cHour}:${record.cMinute}</td>
									<td nowrap>${record.remark}</td>
									

								</tr>

							</c:forEach>

						</table>
					</div>
				</td>
			</tr>

		</table>
		<!-- 
		<table align="center">
			<tr>
				<td colspan="2" align="center"><br> <abc:i18n
						property="commons.button.add" /> <input type="button"
					id="btnPrint" value="<fmt:message key="commons.button.add"/>"
					name="add" class="button"
					onclick="window.location='loginUsersRequestsForm.html'"></input></td>

				<td colspan="2" align="center"><br> <abc:i18n
						property="commons.button.delete" /> <input type="button"
					id="btnPrint" value="<fmt:message key="commons.button.delete"/>"
					name="delete" class="button"
					onclick="window.location='deleteLoginUsersRequestsForm.html?requestType=${request_id}&dateFrom=${request_date_from}&dateTo=${request_date_to}'"></input>
				</td>
				<td colspan="2" align="center"><br> <abc:i18n
						property="commons.button.print" /> <input type="button"
					id="btnPrint" class="button"
					value="<fmt:message key="commons.button.print"/>"
					onClick="printthis('result')"></input></td>
				<td colspan="2" align="center"><br> <abc:i18n
						property="commons.button.export" /> <input type="button"
					id="btnexport" class="button"
					value="<fmt:message key="commons.button.export"/>"
					onClick="exportExcel();"></input></td>
			</tr>
		</table>
		 -->
	</form>

</table>



<%@ include file="/web/common/includes/footer.jsp"%>

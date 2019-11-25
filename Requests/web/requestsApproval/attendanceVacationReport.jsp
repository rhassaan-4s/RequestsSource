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
function exportExcel() {
	//alert("export");
	var fromDate = document.getElementById('fromDate').value;
	var toDate = document.getElementById('toDate').value;
	var empCode = document.getElementById('empCode').value;
	var link = 'attendanceVacationReport.html?export=true&fromDate='+fromDate+'&toDate='+toDate+'&empCode='+empCode;
	//alert(link);
	window.open(link);
}

function searchForm (){
//	if(document.getElementById("empCode").value!=null){
//		var empCode=document.getElementById("empCode").value;
//	} 
 
	//alert('from---'+document.getElementById("from").value);
	if(document.getElementById("fromDate").value!=null){
		var fromDate=document.getElementById("fromDate").value;
		//alert('---inDate----'+inDate);
	}
	if(document.getElementById("toDate").value!=null){
		var toDate=document.getElementById("toDate").value;
		//alert('---vacType----'+vacType);
	}
	if(document.getElementById("empCode").value!=null){
		var empCode=document.getElementById("empCode").value;
	}

	var URL='attendanceVacationReport.html?fromDate='+fromDate+'&toDate='+toDate+'&empCode='+empCode;
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

	<table width="90%" border="0" cellspacing="0" cellpadding="0"
		style="padding-right: 10px">
		<tr>
			<td class="tableHeader" height="1"></td>
		</tr>
		<tr>
			<td class="tableHeader"><abc:i18n
					property="requestsApproval.header.attendanceVacationReport" />
				<fmt:message key="requestsApproval.header.attendanceVacationReport" /></td>
			<td align="left"></td>
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
			<td></td>
		</tr>
		<tr>
			<td>
				<form id="attendanceVacationReport" name="attendanceVacationReport"
					method="POST"
					action="<c:url value="/requestsApproval/attendanceVacationReport.html"/>">
					<div id="result">
						<table border=0 cellspacing=1 cellpadding=0 id="ep"
							style="margin-right: 40px">
							<tr id="head_1_ep">
								<td class="bodyBold" colspan=4 nowrap><abc:i18n
										property="requestsApproval.header.attendanceVacationReport" />
									<fmt:message
										key="requestsApproval.header.attendanceVacationReport" /></td>
							</tr>

							<tr>
								<td nowrap class="formBodControl"><abc:i18n
										property="requestsApproval.caption.userCode" /> <fmt:message
										key="requestsApproval.caption.userCode" /></td>
								<!-- <td  class="formBod"> 
								<input type="text" name="empCode" id="empCode" value="${empCode}"  />
							</td>-->
								<td class="formBod"><abc:autocomplete inputId="empCode"
										inputName="empCode" table="login_users"
										firstKey="commons.caption.code"
										secondKey="commons.caption.name" firstParam="empCode"
										secondParam="name" bindById="true" valueString="${empCode}"
										valueId="" /></td>


								<!-- <td nowrap class="formBodControl" >
								<abc:i18n property="requestsApproval.caption.userName"/>
								<fmt:message key="requestsApproval.caption.userName"/>
							</td>						
							<td  class="formBod"> 
								<input type="text" name="userName" id="userName" value="${emp.firstName}" readonly="readonly" />
							</td> -->
							</tr>


							<tr>
								<td nowrap class="formBodControl"><abc:i18n
										property="commons.caption.fromDate" /> <fmt:message
										key="commons.caption.fromDate" /></td>
								<c:choose>
									<c:when test="${fromDate ==null || fromDate==''}">
										<td class="formBodControl"><input type="text"
											class="calendar" readonly="readonly" autocomplete="off"
											name="fromDate" id="fromDate" value="${firstDay}" /></td>
									</c:when>
									<c:when test="${fromDate !=null || fromDate!=''}">
										<td class="formBodControl"><input type="text"
											class="calendar" readonly="readonly" autocomplete="off"
											name="fromDate" id="fromDate" value="${fromDate}" /></td>
									</c:when>

								</c:choose>
								<td nowrap class="formBodControl"><abc:i18n
										property="commons.caption.toDate" /> <fmt:message
										key="commons.caption.toDate" /></td>
								<c:choose>
									<c:when test="${toDate ==null || toDate==''}">
										<td class="formBodControl"><input type="text"
											class="calendar" readonly="readonly" autocomplete="off"
											name="toDate" id="toDate" value="${today}" /></td>
									</c:when>
									<c:when test="${toDate !=null || toDate!=''}">
										<td class="formBodControl"><input type="text"
											class="calendar" readonly="readonly" autocomplete="off"
											name="toDate" id="toDate" value="${toDate}" /></td>
									</c:when>

								</c:choose>
							</tr>

							<tr>
								<td id="btnPrint"><abc:i18n
										property="commons.button.search" /> <input type="button"
									name="aaa" onclick="searchForm()"
									value="<fmt:message key="commons.button.search"/> "
									class="button" /> &nbsp;&nbsp;&nbsp;</td>
							</tr>

							<tr height="10">
							</tr>

							<tr>
								<td></td>
							</tr>
							<tr>
							</tr>
							<tr>
							</tr>
						</table>
						<table rules="all" align="center" width="70%" class="sofT">
							<tr>
								<td colspan="14" class="helpTitle"><abc:i18n
										property="requestsApproval.header.attendanceVacationReport" />
									<fmt:message
										key="requestsApproval.header.attendanceVacationReport" /></td>
							</tr>

							<tr>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.userCode" /> <fmt:message
										key="requestsApproval.caption.userCode" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.userName" /> <fmt:message
										key="requestsApproval.caption.userName" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.date" /> <fmt:message
										key="requestsApproval.caption.date" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="commons.caption.date" /> <fmt:message
										key="commons.caption.date" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.in" /> <fmt:message
										key="requestsApproval.caption.in" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.out" /> <fmt:message
										key="requestsApproval.caption.out" /></td>
							</tr>


							<c:forEach varStatus="loop" var="record" items="${records}">
								<tr>
									<td class="helpBod" nowrap>${record.employee}</td>
									<td class="helpBod" nowrap>${record.empName}</td>
									<td class="helpBod" nowrap>${record.dayString }</td>
									<td class="helpBod" nowrap>${record.day }</td>

									<td class="helpBod" nowrap>${record.timeIn }</td>

									<td class="helpBod" nowrap>${record.timeOut }</td>


								</tr>
							</c:forEach>

						</table>

						<table rules="all" align="center" width="70%" class="sofT">

							<tr>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.userCode" /> <fmt:message
										key="requestsApproval.caption.userCode" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.userName" /> <fmt:message
										key="requestsApproval.caption.userName" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="commons.caption.from" /> <fmt:message
										key="commons.caption.from" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="commons.caption.to" /> <fmt:message
										key="commons.caption.to" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.vacPeriod" /> <fmt:message
										key="requestsApproval.caption.vacPeriod" /></td>


							</tr>


							<c:forEach varStatus="loop" var="record" items="${days1}">
								<tr height="30">
									<td class="helpBod" nowrap>${record.empCode }</td>
									<td class="helpBod" nowrap>${record.fName}</td>
									<td class="helpBod" nowrap><_4s_:formatMiladiDate
											value="${record.fr_date }" /></td>
									<td class="helpBod" nowrap><_4s_:formatMiladiDate
											value="${record.to_date}" /></td>

									<td class="helpBod" nowrap>${record.withdr}</td>


								</tr>
							</c:forEach>

						</table>
						<br> <br>


					</div>
					<table align="center">
						<tr>
							<td colspan="2" align="center"><abc:i18n
									property="commons.button.print" /> <input type="button"
								id="btnPrint" class="button"
								value="<fmt:message key="commons.button.print"/>"
								onClick="printthis('result')"></input></td>
							<td colspan="2" align="center"><abc:i18n
									property="commons.button.export" /> <input type="button"
								id="btnexport" class="button"
								value="<fmt:message key="commons.button.export"/>"
								onClick="exportExcel();"></input></td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>
	<script language="JavaScript" type="text/javascript"
		src="/Orders/web/common/js/wz_tooltip.js"></script>
	<%@ include file="/web/common/includes/footer.jsp" %>
</body>
</html>
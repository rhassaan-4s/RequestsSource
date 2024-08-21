<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true" />


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Insert title here</title>
<link type="text/css" rel="stylesheet"
		href="/Requests/web/common/timepicker/jquery.multiselect.css" />
	<link type="text/css" rel="stylesheet"
		href="/Requests/web/common/timepicker/jquery.multiselect.filter.css" />
	<script type="text/javascript"
		src="/Requests/web/common/timepicker/jquery.multiselect.min.js"></script>
	<script type="text/javascript"
		src="/Requests/web/common/timepicker/jquery.multiselect.filter.min.js"></script>
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
</head>
<body>
	<abc:security property="1034" />
	
	<script type='text/javascript'
		src='/Requests/dwr/interface/requestsDwr.js'></script>
	<script type='text/javascript' src='/Requests/dwr/engine.js'></script>
	<script type="text/javascript">
		
	
	function selectGroup() {
			var groupId = document.getElementById('groupId').value;

			/*if (groupId!=''){
			requestsDwr.getEmployeesByGroup(fillEmp,groupId);
			}*/
			var link = 'timeAttendanceReport.html?groupId=' + groupId;
			window.location.href = link;
		}
		function fillEmp(data) {
			var el;
			el = document.getElementById("loginUser");

			//DWRUtil.removeAllOptions(el);

			DWRUtil.addOptions(el, data, "id", "name");
		}
		
		
		
		function exportExcel() {
			//alert("export");
			var emp = document.getElementById("loginUser");
			var selectedArray = [];
			if (emp != null && emp != '') {
				var ops = emp.options;
				var size = ops.length;
				for (i = 0; i < size; i++) {
					if (ops[i].selected == true) {
						selectedArray.push(ops[i].value);
					}
				}
			}
			if (document.getElementById("fromDate").value != null) {
				var fromDate = document.getElementById("fromDate").value;
			}
			if (document.getElementById("toDate").value != null) {
				var toDate = document.getElementById("toDate").value;
			}

			var g = document.getElementById('groupId');
			var groupId = '';
			if (g != null && g != '') {
				groupId = g.value;
			}
			var link = 'timeAttendanceReport.html?export=true&fromDate='
					+ fromDate + '&toDate=' + toDate +  '&groupId=' + groupId + "&empCode="
					+ selectedArray;
			//alert(link);
			window.open(link);
		}
		
		
		function searchForm() {

			var emp = document.getElementById("loginUser");
			var selectedArray = [];
			if (emp != null && emp != '') {
				var ops = emp.options;
				var size = ops.length;
				for (i = 0; i < size; i++) {
					if (ops[i].selected == true) {
						selectedArray.push(ops[i].value);
					}
				}
			}
			if (document.getElementById("fromDate").value != null) {
				var fromDate = document.getElementById("fromDate").value;
			}
			if (document.getElementById("toDate").value != null) {
				var toDate = document.getElementById("toDate").value;
			}

			var g = document.getElementById('groupId');
			var groupId = '';
			if (g != null && g != '') {
				groupId = g.value;
			}
			var URL = 'timeAttendanceReport.html?fromDate=' + fromDate
					+ '&toDate=' + toDate + '&groupId=' + groupId + "&empCode="
					+ selectedArray;//+'&empCode='+empCode
			window.location.href = URL;

		}

		function printthis(which) {
			//var tds = document.getElementsByTagName("div");
			//alert(which);
			var tds = document.getElementById(which);
			//alert(tds);
			for (i = 0; i < tds.length; i++)
				tds.item(i).style.display = "none";
			document.getElementById(which).style.display = "block";
			window.print();
			for (i = 0; i < tds.length; i++)
				tds.item(i).style.display = "block";
		}
	</script>
	

	<table width="90%" border="0" cellspacing="0" cellpadding="0"
		style="padding-right: 10px">
		<tr>
			<td class="tableHeader" height="1"></td>
		</tr>
		<tr>
			<td class="tableHeader"><abc:i18n
					property="requestsApproval.header.timeAttendanceReport" /> <fmt:message
					key="requestsApproval.header.timeAttendanceReport" /></td>
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
				<form id="timeAttendanceReport" name="timeAttendanceReport"
					method="POST"
					action="<c:url value="/requestsApproval/timeAttendanceReport.html"/>">
					<div id="result">
						<input type="hidden" id="differentEmp" value="${differentEmp}" />
						<input type="hidden" id="logUser" value="${logUser}" />
						<table border=0 cellspacing=1 cellpadding=0 id="ep"
							style="margin-right: 40px">
							<c:if test="${!groups.isEmpty()}">
								<tr id="head_1_ep">
									<td class="bodyBold" colspan=4 nowrap><abc:i18n
											property="requestsApproval.header.timeAttendanceReport" /> <fmt:message
											key="requestsApproval.header.timeAttendanceReport" /></td>
								</tr>
								<tr>
									<td nowrap class="formBodControl"><abc:i18n
											property="requestsApproval.caption.reponsibles" /> <fmt:message
											key="requestsApproval.caption.reponsibles" /></td>

									<td class="formBodControl" colspan="3"><select
										name="groupId" id="groupId" onchange="selectGroup();">
											<option value="" ${option==''?'selected':''}><abc:i18n
													property="commons.caption.select" />
												<fmt:message key="commons.caption.select" /></option>
											<c:forEach items="${groups}" var="group">
												<option value="${group.level_id.id}"
													${groupId==group.level_id.id?' selected' : ''}>${group.level_id.title}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr>
									<td nowrap class="formBodControl"><abc:i18n
											property="requestsApproval.caption.userName" /> <fmt:message
											key="requestsApproval.caption.userName" /></td>
									<td class="formBodControl" colspan="3"><select
										name="loginUser" class="multi" id="loginUser"
										multiple="multiple">
											<c:forEach items="${employees}" var="loginUser">
												<c:set var="isSelected" value="" />
												<c:forEach items="${empCodes}" var="code">
	
													<c:if test="${code == loginUser.id}">
														<c:set var="isSelected" value="selected" />
													</c:if>
												</c:forEach>
												<option value="${loginUser.id}" ${isSelected}>${loginUser.name}</option>
											</c:forEach>
									</select></td>
								</tr>
							</c:if>
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
					</div>
				</form>
				<div>
					<table rules="all" align="center" width="70%" class="sofT">
						<tr>
							<td colspan="14" class="helpTitle"><abc:i18n
									property="requestsApproval.header.timeAttendanceReport" /> <fmt:message
									key="requestsApproval.header.timeAttendanceReport" /></td>
						</tr>

						<tr>
							<c:if test="${!groups.isEmpty()}">
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.userName" /> <fmt:message
										key="requestsApproval.caption.userName" /></td>
							</c:if>
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


							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="requestsApproval.caption.InputTypeIn" /> <fmt:message
									key="requestsApproval.caption.InputTypeIn" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="requestsApproval.caption.InputTypeOut" /> <fmt:message
									key="requestsApproval.caption.InputTypeOut" /></td>


							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="requestsApproval.caption.locationIn" /> <fmt:message
									key="requestsApproval.caption.locationIn" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="requestsApproval.caption.locationOut" /> <fmt:message
									key="requestsApproval.caption.locationOut" /></td>

							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="requestsApproval.caption.netTime" /> <fmt:message
									key="requestsApproval.caption.netTime" /></td>
						</tr>

						<c:forEach varStatus="loop" var="record" items="${records}">
							<tr>
								<c:if test="${!groups.isEmpty()}">
									<td class="helpBod" nowrap>${record.empName }</td>
								</c:if>
								<td class="helpBod" nowrap>${record.dayString }</td>
								<td class="helpBod" nowrap>${record.day }</td>

								<td class="helpBod" nowrap>${record.timeIn }</td>
								<td class="helpBod" nowrap>${record.timeOut }</td>

								<td class="helpBod" nowrap><c:choose>
										<c:when test="${record.inputType1=='Web_Attendance'}">
											<fmt:message key="requestsApproval.caption.webAttendance" />
										</c:when>
										<c:when test="${record.inputType1=='Request_Attendance'}">
											<fmt:message key="requestsApproval.caption.requestAttendance" />
										</c:when>
										<c:when test="${record.inputType1=='Android_Attendance'}">
											<fmt:message key="requestsApproval.caption.androidAttendance" />
										</c:when>
										<c:when test="${record.inputType1=='Fingerprint_Attendance'}">
											<fmt:message
												key="requestsApproval.caption.fingerprintAttendance" />
										</c:when>
										<c:otherwise>${record.inputType1}</c:otherwise>
									</c:choose></td>

								<td class="helpBod" nowrap><c:choose>
										<c:when test="${record.inputType2=='Web_Attendance'}">
											<fmt:message key="requestsApproval.caption.webAttendance" />
										</c:when>
										<c:when test="${record.inputType2=='Request_Attendance'}">
											<fmt:message key="requestsApproval.caption.requestAttendance" />
										</c:when>
										<c:when test="${record.inputType2=='Android_Attendance'}">
											<fmt:message key="requestsApproval.caption.androidAttendance" />
										</c:when>
										<c:when test="${record.inputType2=='Fingerprint_Attendance'}">
											<fmt:message
												key="requestsApproval.caption.fingerprintAttendance" />
										</c:when>
										<c:otherwise>${record.inputType2}</c:otherwise>
									</c:choose></td>
								<td class="helpBod">${record.address1}</td>
								<td class="helpBod">${record.address2}</td>
								<td class="helpBod" nowrap>${record.diffMins }:
									${record.diffHrs }</td>
							</tr>
						</c:forEach>
						<tr>
							<td class="helpHed" nowrap="nowrap" colspan="8"><abc:i18n
									property="requestsApproval.caption.netTotalTime" /> <fmt:message
									key="requestsApproval.caption.netTotalTime" /></td>
							<td class="helpBod" nowrap><c:if
									test="${totalMins!=null && totalHrs!=null}">
										 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${totalMins} : ${totalHrs}
									 </c:if></td>
					</table>
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
			</td>
		</tr>
	</table>
	<script language="JavaScript" type="text/javascript"
		src="/Orders/web/common/js/wz_tooltip.js"></script>
	<%@ include file="/web/common/includes/footer.jsp"%>
</body>
</html>
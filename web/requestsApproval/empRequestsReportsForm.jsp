<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<abc:security property="1034" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
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
<title>Insert title here</title>
</head>
<body>

	
	<script language="JavaScript" type="text/javascript"
		src="/Orders/web/common/js/wz_tooltip.js"></script>
	<script type="text/javascript">

	function selectAllFn() {
		var sel = document.getElementById('selects');
		var inputs = document.getElementById("empRequestsReportsForm").getElementsByTagName("input");
		var ele = [];
	    for(var i=0, len=inputs.length; i<len; i++){
	        if(inputs[i].name.match(/^approve\d+$/)){
	            ele.push(inputs[i]);
	        }
	    }
		for (var i = 0; i < ele.length; i++) {
			if (ele[i].type == 'checkbox') {
				if (sel.checked == true) {
					ele[i].checked = 'checked';
				} else {
					ele[i].checked = '';
				}

			}
		}
	}

	function searchForm() {
		if (document.getElementById("empCode").value != null) {
			var empCode = document.getElementById("empCode").value;
		}

		if (document.getElementById("statusId").value != null) {
			var statusId = document.getElementById("statusId").value;
			//alert ('-----statusId---'+statusId);
		}

		var dateFrom = document.getElementById("request_date_from").value;
		//alert ('-----dateFrom---'+dateFrom);
		var dateTo = document.getElementById("request_date_to").value;
		//alert ('-----dateTo---'+dateTo);
		//	if(document.getElementById("namehidden").value!=null){
		//	var empName=document.getElementById("namehidden").value;
		//} 

		var exactDateFrom = document.getElementById("exact_request_date_from").value;
		//	alert ('-----exactDateFrom---'+exactDateFrom);
		var exactDateTo = document.getElementById("exact_request_date_to").value;
		//	alert ('-----exactDateTo---'+exactDateTo);

		if (document.getElementById("requestType").value != null) {
			var requestType = document.getElementById("requestType").value;
		}

		if (document.getElementById("codeFrom").value != null) {
			var codeFrom = document.getElementById("codeFrom").value;
			//alert('---dateFrom----'+dateFrom);
		}
		if (document.getElementById("codeTo").value != null) {
			var codeTo = document.getElementById("codeTo").value;
		}

		if (document.getElementById("errand").value != null) {
			var errand = document.getElementById("errand").value;
		}

		var URL = 'empRequestsReportsForm.html?empCode=' + empCode
				+ '&dateFrom=' + dateFrom + '&exactDateFrom=' + exactDateFrom
				+ '&exactDateTo=' + exactDateTo + '&dateTo=' + dateTo
				+ '&requestType=' + requestType + '&codeFrom=' + codeFrom
				+ '&codeTo=' + codeTo + '&statusId=' + statusId + '&errand='
				+ errand;
		window.location.href = URL;
	}

	function printthis(which) {
		var tds = document.getElementsByTagName("div");
		for (i = 0; i < tds.length; i++)
			tds.item(i).style.display = "none";
		document.getElementById(which).style.display = "block";
		window.print();
		for (i = 0; i < tds.length; i++)
			tds.item(i).style.display = "block";
	}

	$('.MM_from_d').datetimepicker("option", "dateFormat", "dd/mm/yy");
	$('.MM_to_d').datetimepicker("option", "dateFormat", "dd/mm/yy");

	let map;

	function initMap(latitude, longitude) {
		map = new google.maps.Map(document.getElementById("map"), {
			center : {
				lat : latitude,
				lng : longitude
			},
			zoom : 8,
		});
	}
</script>



	<table width="90%" border="0" cellspacing="0" cellpadding="0"
		style="padding-right: 10px">
		<tr>
			<td class="tableHeader" height="1"></td>
		</tr>

		<tr>
			<td class="tableHeader"><abc:i18n
					property="requestsApproval.header.empRequestsReportsForm" />
				<fmt:message key="requestsApproval.header.empRequestsReportsForm" /></td>
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
			<td colspan="2"></td>
		</tr>
		<tr>
			<td colspan="2"><c:if test="${not empty errors}">
					<div>
						<c:forEach var="error" items="${errors}">
							<font color="red"> <c:out value="${error}"
									escapeXml="false" /><br />
							</font>
						</c:forEach>
					</div>
				</c:if></td>
		</tr>
		<tr>
			<td>
				<form id="empRequestsReportsForm" name="empRequestsReportsForm"
					method="POST"
					action="<c:url value="/requestsApproval/empRequestsReportsForm.html"/>">
					<input type="hidden" id="requestType" name="requestType"
						value="${requestType}" /> <input type="hidden" id="errand"
						name="errand" value="${errand}" />

					<div id="result">
						<table border=0 cellspacing=1 cellpadding=0 id="ep"
							style="margin-right: 40px">
							<tr id="head_1_ep">
								<c:if test="${requestType=='1' && errand!='true'}">
									<td class="bodyBold" colspan=4 nowrap><abc:i18n
											property="requestsApproval.header.empSpecialVacationsReportsForm" />
										<fmt:message
											key="requestsApproval.header.empSpecialVacationsReportsForm" />
									</td>
								</c:if>
								<c:if test="${requestType=='1' && errand=='true'}">
									<td class="bodyBold" colspan=4 nowrap><abc:i18n
											property="requestsApproval.header.empErrandsReportsForm" />
										<fmt:message
											key="requestsApproval.header.empErrandsReportsForm" /></td>
								</c:if>
								<c:if test="${requestType=='2'}">
									<td class="bodyBold" colspan=4 nowrap><abc:i18n
											property="requestsApproval.header.empAnnualVacationsReportsForm" />
										<fmt:message
											key="requestsApproval.header.empAnnualVacationsReportsForm" />
									</td>
								</c:if>
								<c:if test="${requestType=='3'}">
									<td class="bodyBold" colspan=4 nowrap><abc:i18n
											property="requestsApproval.header.empRequestsReportsForm3" />
										<fmt:message
											key="requestsApproval.header.empRequestsReportsForm3" /></td>
								</c:if>
								<td id="btnPrint" nowrap colspan=1 align=left><abc:i18n
										property="commons.caption.requiredInformation" /><span
									class="bodySmallBold"><fmt:message
											key="commons.caption.requiredInformation" /></span></td>
							</tr>


							<tr id="btnPrint">
								<td nowrap class="formBodControl"><abc:i18n
										property="requestsApproval.caption.userCode" /> <fmt:message
										key="requestsApproval.caption.userCode" /></td>
								<td class="formBod"><abc:autocomplete inputId="empCode"
										inputName="empCode" table="login_users"
										firstKey="commons.caption.code"
										secondKey="commons.caption.name" firstParam="empCode"
										secondParam="name" bindById="true"
										valueString="${employeeCode}" valueId="" /></td>

								<td nowrap class="formBodControl"><abc:i18n
										property="requestsApproval.requestsApprovalForm.reqStatus" />
									<fmt:message
										key="requestsApproval.requestsApprovalForm.reqStatus" /></td>
								<td class="formBodControl"><select name="statusId"
									id="statusId">
										<option value="" ${status==null||status==-1?'selected':''}><fmt:message
												key="commons.caption.select" /></option>
										<option value="0" ${status==0?'selected':''}>لم تكتمل</option>
										<option value="1" ${status==1?'selected':''}>موافق</option>
										<option value="99" ${status==99?'selected' : ''}>مرفوض</option>
								</select></td>

							</tr>

							<tr id="btnPrint">
								<td nowrap class="formBodControl"><abc:i18n
										property="requestsApproval.caption.requestDate" /> <fmt:message
										key="requestsApproval.caption.requestDate" /></td>
							</tr>
							<tr id="btnPrint">
								<td nowrap class="formBodControl"><abc:i18n
										property="commons.caption.from" /> <fmt:message
										key="commons.caption.from" /></td>
								<c:choose>
									<c:when
										test="${request_date_from ==null || request_date_from==''}">
										<td class="formBodControl"><input type="text"
											class="calendar" class="MM_from_d" title="ccc"
											readonly="readonly" autocomplete="off" dir="ltr"
											name="request_date_from" id="request_date_from"
											value="${firstDay}" /></td>
									</c:when>
									<c:when
										test="${request_date_from !=null || request_date_from!=''}">
										<td class="formBodControl"><input type="text"
											class="calendar" class="MM_from_d" title="ccc"
											readonly="readonly" autocomplete="off" dir="ltr"
											name="request_date_from" id="request_date_from"
											value="${request_date_from}" /></td>
									</c:when>
								</c:choose>
								<td nowrap class="formBodControl"><abc:i18n
										property="commons.caption.to" /> <fmt:message
										key="commons.caption.to" /></td>
								<c:choose>
									<c:when test="${request_date_to ==null || request_date_to==''}">
										<td class="formBodControl"><input type="text"
											class="calendar" class="MM_from_d" title="ccc"
											readonly="readonly" autocomplete="off" dir="ltr"
											name="request_date_to" id="request_date_to" value="${today}" />
										</td>
									</c:when>
									<c:when test="${request_date_to !=null || request_date_to!=''}">
										<td class="formBodControl"><input type="text"
											class="calendar" title="ccc" class="MM_to_d"
											readonly="readonly" autocomplete="off" dir="ltr"
											name="request_date_to" id="request_date_to"
											value="${request_date_to}" /></td>
									</c:when>
								</c:choose>

							</tr>

							<tr id="btnPrint">
								<td nowrap class="formBodControl"><abc:i18n
										property="requestsApproval.caption.exactRequestDate" /> <fmt:message
										key="requestsApproval.caption.exactRequestDate" /></td>
							</tr>
							<tr id="btnPrint">
								<td nowrap class="formBodControl"><abc:i18n
										property="commons.caption.from" /> <fmt:message
										key="commons.caption.from" /></td>
								<c:choose>
									<c:when
										test="${exact_request_date_from ==null || exact_request_date_from==''}">
										<td class="formBodControl"><input type="text"
											class="calendar" class="MM_from_d" title="ccc"
											readonly="readonly" autocomplete="off" dir="ltr"
											name="exact_request_date_from" id="exact_request_date_from"
											value="" /></td>
									</c:when>
									<c:when
										test="${exact_request_date_from !=null || exact_request_date_from!=''}">
										<td class="formBodControl"><input type="text"
											class="calendar" class="MM_from_d" title="ccc"
											readonly="readonly" autocomplete="off" dir="ltr"
											name="exact_request_date_from" id="exact_request_date_from"
											value="${exact_request_date_from}" /></td>
									</c:when>
								</c:choose>
								<td nowrap class="formBodControl"><abc:i18n
										property="commons.caption.to" /> <fmt:message
										key="commons.caption.to" /></td>
								<c:choose>
									<c:when
										test="${exact_request_date_to ==null || exact_request_date_to==''}">
										<td class="formBodControl"><input type="text"
											class="calendar" class="MM_from_d" title="ccc"
											readonly="readonly" autocomplete="off" dir="ltr"
											name="exact_request_date_to" id="exact_request_date_to"
											value="" /></td>
									</c:when>
									<c:when
										test="${exact_request_date_to !=null || exact_request_date_to!=''}">
										<td class="formBodControl"><input type="text"
											class="calendar" title="ccc" class="MM_to_d"
											readonly="readonly" autocomplete="off" dir="ltr"
											name="exact_request_date_to" id="exact_request_date_to"
											value="${exact_request_date_to}" /></td>
									</c:when>
								</c:choose>

							</tr>

							<tr id="btnPrint">
								<td nowrap class="formBodControl"><abc:i18n
										property="requestsApproval.caption.codeFrom" /> <fmt:message
										key="requestsApproval.caption.codeFrom" /></td>
								<td class="formBodControl"><input type="text"
									value="${codeFrom}" name="codeFrom" id="codeFrom" /></td>

								<td nowrap class="formBodControl"><abc:i18n
										property="requestsApproval.caption.codeTo" /> <fmt:message
										key="requestsApproval.caption.codeTo" /></td>
								<td class="formBodControl"><input type="text"
									value="${codeTo}" name="codeTo" id="codeTo" /></td>
							</tr>

							<tr height="10">
							</tr>

							<tr>
							</tr>
							<tr id="btnPrint">
								<td><abc:i18n property="commons.button.search" /> <input
									type="submit" name="aaa"
									value="<fmt:message key="commons.button.search"/> "
									class="button" /> &nbsp;&nbsp;&nbsp;</td>
							</tr>

							<tr height="10">
							</tr>

							<tr>
							</tr>
						</table>

						<abc:paging url="empRequestsReportsForm.html"
							parametersString="requestType=${requestType}&empCode=${empCode}&statusId=${status}&request_date_from=${request_date_from}&request_date_to=${request_date_to}&exact_request_date_from=${exact_request_date_from}&exact_request_date_to=${exact_request_date_to}&codeFrom=${codeFrom}&codeTo=${codeTo}" />
						<table rules="all" align="center" class="sofT">
							<tr>
								<c:if test="${requestType=='1' && errand!='true'}">
									<td colspan="16" class="helpTitle"><abc:i18n
											property="requestsApproval.header.empSpecialVacationsReportsForm" />
										<fmt:message
											key="requestsApproval.header.empSpecialVacationsReportsForm" />
									</td>
								</c:if>
								<c:if test="${requestType=='1' && errand=='true'}">
									<td colspan="16" class="helpTitle"><abc:i18n
											property="requestsApproval.header.empErrandsReportsForm" />
										<fmt:message
											key="requestsApproval.header.empErrandsReportsForm" /></td>
								</c:if>
								<c:if test="${requestType=='2'}">
									<td colspan="16" class="helpTitle"><abc:i18n
											property="requestsApproval.header.empAnnualVacationsReportsForm" />
										<fmt:message
											key="requestsApproval.header.empAnnualVacationsReportsForm" />
									</td>
								</c:if>
								<c:if test="${requestType=='3'}">
									<td colspan="16" class="helpTitle"><abc:i18n
											property="requestsApproval.header.empRequestsReportsForm3" />
										<fmt:message
											key="requestsApproval.header.empRequestsReportsForm3" /></td>
								</c:if>
							</tr>

							<tr>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.userCode" /> <fmt:message
										key="requestsApproval.caption.userCode" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.userName" /> <fmt:message
										key="requestsApproval.caption.userName" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.requestNumber" /> <fmt:message
										key="requestsApproval.caption.requestNumber" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.requestType" /> <fmt:message
										key="requestsApproval.caption.requestType" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.requestDate" /> <fmt:message
										key="requestsApproval.caption.requestDate" /></td>
								<c:if test="${requestType=='1'||requestType=='2'}">
									<td nowrap class="helpHed" nowrap="nowrap"><abc:i18n
											property="commons.caption.fromDate" /> <fmt:message
											key="commons.caption.fromDate" /></td>
									<td nowrap class="helpHed" nowrap="nowrap"><abc:i18n
											property="commons.caption.toDate" /> <fmt:message
											key="commons.caption.toDate" /></td>
									<td nowrap class="helpHed" nowrap="nowrap"><abc:i18n
											property="requestsApproval.requestsApprovalForm.reqPeriod" />
										<fmt:message
											key="requestsApproval.requestsApprovalForm.reqPeriod" /></td>
									<c:if test="${requestType=='2'}">
										<td nowrap class="helpHed" nowrap="nowrap"><abc:i18n
												property="commons.button.getVacCredit" /> <fmt:message
												key="commons.button.getVacCredit" /></td>
									</c:if>
								</c:if>
								<c:if test="${requestType=='3'}">
									<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="commons.caption.from" /> <fmt:message
											key="commons.caption.from" /></td>

									<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="commons.caption.to" /> <fmt:message
											key="commons.caption.to" /></td>
								</c:if>

								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="commons.caption.address" /> <fmt:message
										key="commons.caption.address" /></td>

								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.requestsApprovalForm.reqStatus" />
									<fmt:message
										key="requestsApproval.requestsApprovalForm.reqStatus" /> <br>
									<input type="checkbox" id="selects" name="selects" onclick="selectAllFn()" /></td>
								<td class="helpHed" nowrap="nowrap"><abc:i18n
										property="requestsApproval.caption.reply" /> <fmt:message
										key="requestsApproval.caption.reply" /></td>

								<c:if test="${requestType=='1'}">
									<td class="helpHed" nowrap><abc:i18n
											property="commons.caption.altDate" />
										<fmt:message key="commons.caption.altDate" /></td>
								</c:if>

								<td class="helpHed" nowrap><abc:i18n
										property="commons.caption.notes" />
									<fmt:message key="commons.caption.notes" /></td>

								<td class="helpHed" nowrap>&nbsp;</td>

							</tr>

							<c:forEach varStatus="loop" var="record" items="${results}">
								<tr height=20 bgcolor="#F8F8F8">
									<td nowrap>${record.empCode}</td>
									<td nowrap>${record.name}</td>
									<td nowrap><c:choose>
											<c:when
												test="${(record.approval==null || record.approval=='')  && (record.approved==1 || record.approved==0)}">
												<a
													href="requestsApprovalForm.html?reqId=${record.id}&requestType=${requestType}&errand=${errand}">${record.requestNumber}</a>
											</c:when>
											<c:otherwise>
											${record.requestNumber}
										</c:otherwise>
										</c:choose></td>
									<c:choose>
										<c:when test="${errand=='true'}">
											<td nowrap>مأموريه</td>
										</c:when>
										<c:otherwise>
											<td nowrap><c:choose>
													<c:when test="${record.vacation==null}">
														${record.description}
													</c:when>
													<c:otherwise>
														${record.vacName}
													</c:otherwise>
												</c:choose></td>
										</c:otherwise>
									</c:choose>
									<td nowrap><_4s_:formatMiladiDate
											value="${record.request_date}" /></td>
									<c:if test="${requestType=='1'||requestType=='2'}">
										<td nowrap><_4s_:formatMiladiDate
												value="${record.from_date}" /></td>
										<td nowrap><_4s_:formatMiladiDate
												value="${record.to_date}" /></td>
										<td nowrap>${record.withdrawDays}</td>
										<c:if test="${requestType=='2'}">
											<td nowrap>${record.vacCredit}</td>
										</c:if>
									</c:if>
									<c:if test="${requestType=='3'}">
										<td nowrap><_4s_:formatMiladiDate
												value="${record.period_from}" /> <_4s_:timeString
												value="${record.period_from}" /></td>
										<td nowrap><_4s_:formatMiladiDate
												value="${record.period_to}" /> <_4s_:timeString
												value="${record.period_to}" /></td>
									</c:if>
									<td>${record.locationAddress}</td>
									<td nowrap>
										<!--  record.approval:${record.approval}-record.approved:${record.approved}-->
										<c:choose>
											<c:when test="${record.approval==0 || record.approved==99}">
												<abc:i18n
													property="requestsApproval.requestsApprovalForm.reqRejected" />
												<fmt:message
													key="requestsApproval.requestsApprovalForm.reqRejected" />
											</c:when>
											<c:when
												test="${(record.approval==null || record.approval=='' || record.approval=='2')  && (record.approved==1 || record.approved==0)}"> <!--  || record.approval=='2' -->
									    	لم تكتمل
									    	<br>
									    	<c:if test="${record.approval!=2}">
												<input type="checkbox" name="approve${loop.index}"
													id="approve${loop.index}" />
											</c:if>
											</c:when>
											<c:when test="${record.approval==1 && record.approved==1}">

												<abc:i18n
													property="requestsApproval.requestsApprovalForm.reqApproval" />
												<fmt:message
													key="requestsApproval.requestsApprovalForm.reqApproval" />
											</c:when>
										</c:choose>
									</td>
									<td nowrap>${record.reply}</td>

									<c:if test="${requestType=='1'}">
										<td nowrap>${record.altDate}</td>
									</c:if>

									<td>${record.notes}</td>

									<c:choose>
										<c:when test="${record.approval!=null || record.approved==99}">
											<td nowrap>&nbsp;</td>
										</c:when>
										<c:otherwise>
											<td id="btnPrint" nowrap><abc:i18n
													property="commons.button.approve" /><a
												href="requestsApprovalForm.html?reqId=${record.id}&requestType=${requestType}"><fmt:message
														key="commons.button.approve" /></a></td>
										</c:otherwise>
									</c:choose>

								</tr>
							</c:forEach>
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
									property="requestsApproval.button.approveAll" /> <input
								type="submit" id="approveAll" name="approveAll" class="button"
								value="<fmt:message key="requestsApproval.button.approveAll"/>"
								onClick="approveAll()"></input></td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>

	<%@ include file="/web/common/includes/footer.jsp"%>
</body>
</html>
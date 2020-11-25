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
	var inDate = document.getElementById('inDate').value;
	var vacType = document.getElementById('vacType').value;
	var link = 'annualVacationBalance.html?export=true&inDate='+inDate+'&vacType='+vacType;
	//alert(link);
	window.open(link);
}
function searchForm (){
	if(document.getElementById("empCode").value!=null){
		var empCode=document.getElementById("empCode").value;
	} 
 
	//alert('from---'+document.getElementById("from").value);
	if(document.getElementById("inDate").value!=null){
		var inDate=document.getElementById("inDate").value;
		//alert('---inDate----'+inDate);
	}
	if(document.getElementById("vacType").value!=null){
		var vacType=document.getElementById("vacType").value;
		//alert('---vacType----'+vacType);
	}

	var URL='annualVacationBalance.html?inDate='+inDate+'&vacType='+vacType+'&empCode='+empCode;
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


<table width="90%"  border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n property="requestsApproval.header.annualVacationBalance2"/><fmt:message key="requestsApproval.header.annualVacationBalance2"/></td><td align="left"></td>
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

		</td>
	</tr>	
	<tr>
		<td>
			<form id="annualVacationBalance" name="annualVacationBalance"	method="POST" action="<c:url value="/requestsApproval/annualVacationBalance.html"/>">
				   <input type="hidden"  id="empRequestTypeId" name="empRequestTypeId" value="${empRequestTypeId}"/>
				   <input type="hidden"  id="requestType" name="requestType" value="${requestType}"/>
				   <input type="hidden"  id="inDate" name="inDate" value="${inDate}"/>
				   <input type="hidden"  id="vacId" name="vacId" value="${vacId}"/>
					<div id="result">
					<table border=0 cellspacing=1 cellpadding=0 id="ep" style="margin-right:40px">
						<tr id="head_1_ep">
							<td class="bodyBold" colspan=4 nowrap>
							<abc:i18n property="requestsApproval.header.annualVacationBalance2"/><fmt:message key="requestsApproval.header.annualVacationBalance2"/></td>
						</tr>
						
					<c:set var="check"  value=""/>
					<c:if test="${empRequestTypeId!=null && empRequestTypeId!=''}">
					<c:set var="check"  value="disabled"/>
					</c:if>
		
						<tr>
							<td nowrap class="formBodControl"><abc:i18n
												property="requestsApproval.caption.userCode" /> <fmt:message
												key="requestsApproval.caption.userCode" /></td>
										<td class="formBod"><abc:autocomplete inputId="empCode"
												inputName="empCode" table="login_users"
												firstKey="commons.caption.code"
												secondKey="commons.caption.name" firstParam="empCode"
												secondParam="name" bindById="true"
												valueString="${emp.empCode}" valueId=""/></td>
							
					  		<td nowrap class="formBodControl" id="btnPrint">
								<abc:i18n property="requestsApproval.caption.vacationType"/>
								<fmt:message key="requestsApproval.caption.vacationType"/>
							</td>
							<td  class="formBodControl"  id="btnPrint">
								<select name="vacType" id="vacType" value="${vacId}">
									<option value=""><fmt:message key="commons.caption.select" /></option>						
										<c:forEach items="${annualVacList}" var="vac">
											<option value="${vac.vacation}" ${vac.vacation == vacId ? 'selected' : ''}>${vac.name}</option>
										</c:forEach>
								</select>
							</td>													
						</tr>
						
																		
						<tr>
					  		<td nowrap class="formBodControl">
								<abc:i18n property="commons.caption.inDate"/>
								<fmt:message key="commons.caption.inDate"/>
							</td>
							<td  class="formBodControl" >
								<input type="text"   class="calendar"   readonly="readonly" autocomplete="off" name="inDate" id="inDate" value="<_4s_:formatMiladiDate value="${inDate}"/>" />
							</td>
						</tr>
						
						<tr>
							<td id="btnPrint">
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
											<abc:i18n property="requestsApproval.header.annualVacationBalance2" />
											<fmt:message key="requestsApproval.header.annualVacationBalance2" />
										</td>
									</tr>
									
									<tr>
										
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.userName" />
											<fmt:message key="requestsApproval.caption.userName" />
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="commons.caption.inDate" />
										 	<fmt:message key="commons.caption.inDate" />
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.vacationType" />
											<fmt:message key="requestsApproval.caption.vacationType" />
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="commons.button.getVacCredit" />
										 	<fmt:message key="commons.button.getVacCredit" />
										</td>
									
									</tr>
										
															
							
									<tr height=20 bgcolor="#F8F8F8">									
																
										<td class="helpBod" nowrap>
											${empName}
										</td>													
										<td class="helpBod" nowrap>
											<_4s_:formatMiladiDate value="${inDate}"/>
										</td>
										<c:if test="${vacType==null || vacType==''}">
										<td class="helpBod" nowrap>
											${name1}<br><br>
											&nbsp; ${name2}
										</td>
										<td class="helpBod" nowrap>
									        ${balance1}<br><br>
									        &nbsp;${balance2}
										</td>
										</c:if>
										
										<c:if test="${vacType!=null && vacType!=''}">
										<td class="helpBod" nowrap>
											${vacType}
										</td>
										<td class="helpBod" nowrap>
									        ${balance}
										</td>
										</c:if>
									</tr>
									<c:if test="${vacType==null || vacType==''}">
									<tr  height=20 bgcolor="#F8F8F8">
									
										<td class="helpHed" nowrap="nowrap"  colspan="2">&nbsp;&nbsp;
											<abc:i18n property="requestsApproval.caption.totalBalance" />
										 	<fmt:message key="requestsApproval.caption.totalBalance" />
										</td>
									
										<td class="helpBod" nowrap  colspan="2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;${balance1+balance2}
										</td>
									</tr>
									</c:if>
								
								</table>
								
								
								<c:if test="${daysEnabled==true}">
								
																<br>
								<br>
								
								<c:if test="${vacType==null || vacType==''}">
								<table rules="all" align="center" width="70%" class="sofT">
									<tr>
										<td colspan="14" class="helpTitle">
											<abc:i18n property="requestsApproval.header.annualVacation" />
											<fmt:message key="requestsApproval.header.annualVacation" />
										</td>
									</tr>
									<tr>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="commons.caption.from" />
											<fmt:message key="commons.caption.from" />
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="commons.caption.to" />
											<fmt:message key="commons.caption.to" />
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.vacPeriod" />
										 	<fmt:message key="requestsApproval.caption.vacPeriod" />
										</td>
										<td class="helpHed" nowrap="nowrap" width="200">
											<abc:i18n property="requestsApproval.caption.empSign" />
										 	<fmt:message key="requestsApproval.caption.empSign" />
										</td>
									</tr>
						
									
									<c:forEach varStatus="loop" var="record" items="${days1}">
										<tr height="30">		
											<td class="helpBod" nowrap>
												<_4s_:formatMiladiDate value="${record.fr_date }" />
											</td>
											<td class="helpBod" nowrap>
												<_4s_:formatMiladiDate value="${record.to_date}" />
											</td>
											
											<td class="helpBod" nowrap>
												${record.withdr }
											</td>
											
											<td class="helpBod" nowrap>
												
											</td>	
										</tr>
									</c:forEach>
									
								</table>
								<br>
								<br>
								<table rules="all" align="center" width="70%" class="sofT">
									<tr>
										<td colspan="14" class="helpTitle">
											<abc:i18n property="requestsApproval.header.specialVacation" />
											<fmt:message key="requestsApproval.header.specialVacation" />
										</td>
									</tr>
									<tr>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="commons.caption.from" />
											<fmt:message key="commons.caption.from" />
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="commons.caption.to" />
											<fmt:message key="commons.caption.to" />
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.vacPeriod" />
										 	<fmt:message key="requestsApproval.caption.vacPeriod" />
										</td>
										<td class="helpHed" nowrap="nowrap" width="200">
											<abc:i18n property="requestsApproval.caption.empSign" />
										 	<fmt:message key="requestsApproval.caption.empSign" />
										</td>
									</tr>
						
									
									<c:forEach varStatus="loop" var="record" items="${days2}">
										<tr height="30">		
											<td class="helpBod" nowrap>
												<_4s_:formatMiladiDate value="${record.fr_date }" />
											</td>
											<td class="helpBod" nowrap>
												<_4s_:formatMiladiDate value="${record.to_date}" />
											</td>
											
											<td class="helpBod" nowrap>
												${record.withdr }
											</td>	
											
											<td class="helpBod" nowrap>
												
											</td>	
										</tr>
									</c:forEach>
									
								</table>
								
								</c:if>
								
								<c:if test="${vacId!=null}">
								<table rules="all" align="center" width="70%" class="sofT">
									<tr>
										<td colspan="14" class="helpTitle">
											${vacType}
										</td>
									</tr>
									<tr>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="commons.caption.from" />
											<fmt:message key="commons.caption.from" />
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="commons.caption.to" />
											<fmt:message key="commons.caption.to" />
										</td>
										<td class="helpHed" nowrap="nowrap">
											<abc:i18n property="requestsApproval.caption.vacPeriod" />
										 	<fmt:message key="requestsApproval.caption.vacPeriod" />
										</td>
									</tr>
						
									
									<c:forEach varStatus="loop" var="record" items="${days}">
										<tr>		
											<td class="helpBod" nowrap>
												<_4s_:formatMiladiDate value="${record.fr_date }" />
											</td>
											<td class="helpBod" nowrap>
												<_4s_:formatMiladiDate value="${record.to_date}" />
											</td>
											
											<td class="helpBod" nowrap>
												${record.withdr }
											</td>	
										</tr>
									</c:forEach>
									
								</table>
								<br>
								<br>
								</c:if>
								
								
								</c:if>
								
								
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
<script language="JavaScript" type="text/javascript" src="/Orders/web/common/js/wz_tooltip.js"></script>
<%@ include file="/web/common/includes/footer.jsp" %>
</body>
</html>
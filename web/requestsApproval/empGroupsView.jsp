<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true" />

<abc:security property="1035" />
<!DOCTYPE PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
	window.addEventListener('focus', function() {
		var empCode=document.getElementById("empCodeHidden").value;
		var empId=document.getElementById("empIdHidden").value;
		//alert("emp id " + empId + " - emp code " + empCode);
		if (empCode != null && empCode!='' && empId != null && empId!='') {
		document.getElementById('empGroupsView').submit();
	}
	});

function searchForm(){
	var empId=document.getElementById("empCodehidden").value;
	//var name =document.getElementById("mm_name").value;
	var empCode=document.getElementById("empCode").value;
	//alert('---empCodehidden---'+empId);
	var URL='empGroupsView.html?empId='+empId+'&empCode='+empCode;
	window.location.href=URL;
}

function deleteForm(requestId,groupId){
	var empId=document.getElementById("empIdHidden").value;
	var empCode=document.getElementById("empCodeHidden").value;
	var URL='empGroupsView.html?save='+false+'&delete='+true+'&empId='+empId+'&empCode='+empCode+'&requestId='+requestId+'&groupId='+groupId;
	window.location.href=URL;
}

function editForm(requestId,groupId){
//	alert(requestId+"-"+groupId);
	var empId=document.getElementById("empIdHidden").value;
	var empCode=document.getElementById("empCodeHidden").value;
	var URL='empGroupsView.html?save='+false+'&edit='+true+'&delete='+false+'&empId='+empId+'&empCode='+empCode+'&requestId='+requestId+'&groupId='+groupId;
	window.location.href=URL;
}

function saveForm(requestId,accId){
//	alert(requestId+"-"+groupId);
	var empId=document.getElementById("empIdHidden").value;
	var empCode=document.getElementById("empCodeHidden").value;
	var group=document.getElementById("groupId").value;
	//alert(group);
	var URL='empGroupsView.html?save='+true+'&edit='+false+'&delete='+false+'&empId='+empId+'&empCode='+empCode+'&requestId='+requestId+'&groupId='+group+'&empReqTypeAcc='+accId;
	window.location.href=URL;
}
</script>

	<table width="90%" border="0" cellspacing="0" cellpadding="0"
		style="padding-right: 10px">
		<tr>
			<td colspan="2" class="tableHeader" height="1"></td>
		</tr>
		<tr>
			<td class="tableHeader"><abc:i18n
					property="requestsApproval.header.empGroupsView" /> <fmt:message
					key="requestsApproval.header.empGroupsView" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td colspan="2" bgcolor="#5A718B" height="2"></td>
		</tr>
		<tr>
			<td colspan="2" height="1"></td>
		</tr>
		<tr>
			<td><c:if test="${record.done==true}">
					<font color="blue" size="5"> <abc:i18n
							property="requestsApproval.caption.deleteSuccess" />
						<fmt:message key="requestsApproval.caption.deleteSuccess" />
					</font>
				</c:if></td>
		</tr>
		<tr>
			<td colspan="2" height="20"></td>
		</tr>
		<tr>
			<td colspan="2"><c:if test="${not empty status.errorMessages}">
					<div>
						<c:forEach var="error" items="${status.errorMessages}">
							<font color="red"> <c:out value="${error}"
									escapeXml="false" /><br />
							</font>
						</c:forEach>
					</div>
				</c:if></td>
		</tr>
		<tr>
			<td colspan="2">
				<form id="empGroupsView" name="empGroupsView" method="POST"
					action="<c:url value="/requestsApproval/empGroupsView.html"/>">
					<!-- /////////////////START to enable authentication with tokens with spring security 5//////////////////// -->
						<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}"/>
					<!-- /////////////////END to enable authentication with tokens with spring security 5  //////////////////// -->
				
					<input type="hidden" name="empCodeHidden" id="empCodeHidden" value="${employeeCode}" />
					<input type="hidden" name="empIdHidden" id="empIdHidden" value="${empId}" />
					<input type="hidden" name="empNameHidden" id="empNameHidden" value="${mm_name}">
					
					<table border=0 cellspacing=1 cellpadding=0 id="ep"
						style="margin-right: 40px">
						<tr id="head_1_ep">
							<td class="bodyBold" colspan="3" nowrap><abc:i18n
									property="requestsApproval.header.empGroupsView" />
								<fmt:message key="requestsApproval.header.empGroupsView" /></td>
							<td nowrap align=left><abc:i18n
									property="commons.caption.requiredInformation" /><span
								class="bodySmallBold"> <fmt:message
										key="commons.caption.requiredInformation" /></span></td>
						</tr>

						<tr>
							<td nowrap class="formReq"><abc:i18n
									property="requestsApproval.caption.userCode" /> <fmt:message
									key="requestsApproval.caption.userCode" /></td>
							<td class="formBodControl">
							
							<abc:autocomplete
									inputId="empCode" inputName="empCode"
									table="login_users" firstKey="commons.caption.code"
									secondKey="commons.caption.name" firstParam="empCode"
									secondParam="name" bindById="true"
									valueString="${employeeCode}" valueId="${empId}" /></td>

							<td nowrap class="formReq"><abc:i18n
									property="requestsApproval.caption.userName" /> <fmt:message
									key="requestsApproval.caption.userName" /></td>
							<td class="formBodControl" id="mm_name" name="empName">
								${mm_name} &nbsp;</td>
						</tr>
						<tr><td  colspan="4">&nbsp;</td></tr>
						<tr id="btn">
							<td colspan="4"><abc:i18n property="commons.button.search" /><input
								type="search" name="search" onclick="searchForm()"
								value="<fmt:message key="commons.button.search"/>"
								class="button" />&nbsp;&nbsp;&nbsp;</td>
						</tr>
	<tr>
	<td  colspan="4">&nbsp;</td></tr>
	<c:if test="${(delete==''||delete==null || delete=='false') && (edit =='' || edit ==null || edit=='false')}">
					<c:forEach items="${info}" var="rec">
					
						<tr style="border: 2px solid #91C8FF;" class="">
							
								<td style="border-bottom: 1px solid #91C8FF;"
												class="formBodControl">
									<input type="hidden" name="requestId" value="${rec.title.id}" id="requestId"/>
									${rec.title.description}</td>
							

							<td style="border-bottom: 1px solid #91C8FF;"
								style="text-align: center;" width="40%" colspan="2">

								<table width="100%" cellspacing="2">
											<c:forEach items="${rec.list}" var="type">
												<tr>
													<td style="border-bottom: 1px solid #91C8FF;"
														class="formBodControl">${type.group_id.title}</td>

													<td><br> <abc:i18n property="commons.button.edit" />
														<input type="button"
														value="<fmt:message key="commons.button.edit"/>"
														name="edit" class="button"
														onclick="editForm(${type.req_id.id},${type.group_id.id});"></input></td>

													<td><br> <abc:i18n
															property="commons.button.delete" /> <input type="button"
														value="<fmt:message key="commons.button.delete"/>"
														name="delete" id="delete"
														class="button"
														onclick="deleteForm(${type.req_id.id},${type.group_id.id});"></input></td>
												</tr>
											</c:forEach>

										</table>
									</td>
							<td></td>
						</tr>

					</c:forEach>
					</c:if>
					<c:forEach items="${toBeEdited}" var="typeEdit">
										<tr  class="">
											<td style="border-bottom: 1px solid #91C8FF;"
												class="formBodControl">${typeEdit.req_id.description}</td>
											<td style="border-bottom: 1px solid #91C8FF;"
												class="formBodControl"><select name="groupId" id="groupId" >
													<c:forEach items="${groups}" var="group">
														<option value="${group.id}" ${group.id == typeEdit.group_id.id ?' selected' : ''}>${group.title}</option>
													</c:forEach>	
												</select></td>
											

												<td><abc:i18n property="commons.button.save" /> <input
										type="button" value="<fmt:message key="commons.button.save"/>"
										name="save" class="button" onclick="saveForm(${typeEdit.req_id.id},${typeEdit.id})"></input></td>
										</tr>
									</c:forEach>
				<c:if test="${(delete==''||delete==null) && (edit =='' || edit ==null) && info!=null && info.size()>0}">
					<tr>
						<td colspan="4">
							<table>
								<tr>
									<td><abc:i18n property="commons.button.add" /> <input
										type="button"
										value="<fmt:message key="commons.button.add"/>"
										name="add" onclick="javascript:createWindow('addNewEmpGroup.html?empId=${empId}&empCode=${employeeCode}',300,620)"  class="button"></input></td>
									
								</tr>
							</table>
						</td>
					</tr>
					</c:if>
				</table>
				</form>
				</td>
				</tr>
				</table>

	<script language="JavaScript" type="text/javascript"
		src="/Orders/web/common/js/wz_tooltip.js"></script>
	<%@ include file="/web/common/includes/footer.jsp"%>
</body>
</html>
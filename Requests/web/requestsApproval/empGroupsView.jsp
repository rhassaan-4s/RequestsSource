<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>

<abc:security property="1035"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">

function searchForm(){
	var empId=document.getElementById("empCodehidden").value;
	//var name =document.getElementById("mm_name").value;
	var empCode=document.getElementById("empCode").value;
	//alert('---empCodehidden---'+empId);
	var URL='empGroupsView.html?empId='+empId+'&empCode='+empCode;
	window.location.href=URL;
}

function deleteForm(reqType){
//alert('-----dddd--'+document.getElementById("empCode").value);
//alert(reqType.name);
//	var groupId=document.getElementById("groupId").value;
	var requestId=reqType.id;
	var groupId=reqType.name;
	var empId=document.getElementById("empCodehidden").value;
	var URL='empGroupsView.html?delete='+true+'&empIdDel='+empId+'&requestId='+requestId+'&groupId='+groupId;
	//window.location.href=URL;
}

function editForm(){
	//var empId=document.getElementById("empCodehidden").value;
	//var URL='empGroupsView.html?edit='+true+'&delete='+false+'&empIdDel='+empId;
	//window.location.href=URL;
}
</script>

<table width="90%"  border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n property="requestsApproval.header.empGroupsView"/><fmt:message key="requestsApproval.header.empGroupsView"/></td><td align="left"></td>
	</tr>
	<tr>
		<td colspan="2" bgcolor="#5A718B" height="2"></td>
	</tr>
	<tr>
		<td colspan="2" height="1"></td>
	</tr>
	<tr>
		<td>
			<c:if test="${done==true}"><font color="blue" size="5"> 
	        <abc:i18n	property="requestsApproval.caption.deleteSuccess" /><fmt:message
					key="requestsApproval.caption.deleteSuccess" />
	 		</font>
	 		</c:if>
		</td>
	</tr>	
	<tr>
		<td colspan="2" height="20"></td>
	</tr>
	<tr>
		<td colspan="2">
		
				<c:if test="${not empty status.errorMessages}">
					<div><c:forEach var="error" items="${status.errorMessages}">
						<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
						</font>
					</c:forEach></div>
				</c:if>
			
		</td>
	</tr>
	<tr>
		<td>
			<form id="empGroupsView" name="empGroupsView"	method="POST" action="<c:url value="/requestsApproval/empGroupsView.html"/>">
					<table border=0 cellspacing=1 cellpadding=0 id="ep" style="margin-right:40px">
						<tr id="head_1_ep">
							<td class="bodyBold" colspan=4 nowrap>
							<abc:i18n property="requestsApproval.header.empGroupsView"/><fmt:message key="requestsApproval.header.empGroupsView"/></td>
							<td nowrap colspan=1 align=left> 
							
							<abc:i18n property="commons.caption.requiredInformation"/><span class="bodySmallBold">
							<fmt:message key="commons.caption.requiredInformation"/></span>
							
							</td>
						</tr>
		
						<tr>
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.userCode"/>
								<fmt:message key="requestsApproval.caption.userCode"/>
							</td>						
							<td  class="formBodControl">
								<abc:autocomplete 
									inputId="empCode" 
									inputName="${status.expression}" 
									table="login_users" 
									firstKey="commons.caption.code"
									secondKey="commons.caption.name"
									firstParam="empCode"
									secondParam="name"
									bindById="true"
									valueString="${employeeCode}"
									valueId="${empId}" />
							</td>
						
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.userName"/>
								<fmt:message key="requestsApproval.caption.userName"/>
							</td>							
	 						<td class="formBodControl" id="mm_name" name="empName"> 
							${mm_name}
							&nbsp;
							</td>													
						</tr>					
												
						<tr id="btn">
							<td colspan=4>
								<abc:i18n property="commons.button.search"/><input type="search" name="search" onclick="searchForm()" value="<fmt:message key="commons.button.search"/>" class="button"/>&nbsp;&nbsp;&nbsp;
								
							</td>
						</tr>					
					</table>

				<table width="80%" cellspacing="2">
					<tr>
						<td></td>
					</tr>
					
					<c:forEach items="${info}" var="record">
						<tr style="border: 2px solid #91C8FF;" class="">
						<td></td><td></td><td></td>
							<td style="border-bottom: 1px solid #91C8FF;" class="formReq" >
							<input type="hidden" name="requestId" value="${record.title.id}" id="requestId">
								${record.title.description}
							</td>
													
							<td style="border-bottom: 1px solid #91C8FF;" style="text-align: center;" width="40%">
						
								<table width="100%" cellspacing="2">	
							  	<c:forEach items="${record.list}" var="type">
									<tr style="border-bottom: 1px solid #91C8FF;" class="">
										<td style="border-bottom: 1px solid #91C8FF;" class="formBodControl" >
										${type.group_id.title}
										</td>
										
										<td>
										<br><input type="checkbox" name="empRecAccId" id="empRecAccId" value="${type.id}">
											<!--
											<abc:i18n property="commons.button.delete"/>
											<input type="button" value="<fmt:message key="commons.button.delete"/>" name="${type.group_id.id}" id="${record.title.id }" class="button" onclick="deleteForm(this)"></input>
											-->
										</td>																																
									</tr>
								</c:forEach>
								</table>
							</td>
							<td>
							</td>
							<td>
							<br><abc:i18n property="commons.button.delete"/>
								<input type="submit" value="<fmt:message key="commons.button.delete"/>" name="delete"  class="button" ></input>
							</td>
							<!-- 
							<td>
							<br>
								<abc:i18n property="commons.button.edit"/>
								<input type="button" value="<fmt:message key="commons.button.edit"/>" name="edit" class="button" onclick="editForm()"></input>
							</td>
							 -->									
						</tr>
					</c:forEach>

				</table>
				
			</form>
		</td>
	</tr>
</table>
<script language="JavaScript" type="text/javascript" src="/Orders/web/common/js/wz_tooltip.js"></script>
<%@ include file="/web/common/includes/footer.jsp" %>
</body>
</html>
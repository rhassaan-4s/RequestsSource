<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<script type='text/javascript' src='/Requests/dwr/interface/hrManager.js'></script>

<abc:security property=""/><br>
<script type="text/javascript">

function deleteMyRow(deleteId,employeeId)
{

	var answer = window.confirm('<fmt:message key="commons.button.confirmDelete"/>');
	if(answer==false)
	{
		return;
	}
	else
	{
         
		 var URL='employeeAllocationView.html?formType=${formType}&deleteId='+deleteId+'&employeeId='+employeeId;
		
			window.location.href=URL;
			// var tr =tbl.deleteRow(lastRow-1);
			

	}

}
</script>

<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   
  <form method="post" action="<c:url value="employeeVacationAtInstallationForm.html?add=false"/>">
  
 	<tr>
		<TD><spring:bind path="hrEmployee.*">
						<c:if test="${not empty status.errorMessages}">
							<div><c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
								</font>
							</c:forEach></div>
						</c:if>
			</spring:bind></TD>
	</tr>
			<tr>
			<td>
			<table>
			<tr>
			<td class="formBod">
			<fmt:message key="commons.caption.code"></fmt:message>
			</td>
			   <td  class="formBod" colspan="2">
			      
				<abc:autocomplete 
					inputId="empCode" 
					inputName="employeeCode" 
					table="hr_employee" 
					firstKey="commons.caption.code"
					secondKey="commons.caption.name"
					firstParam="empCode"
					secondParam="name"
					bindById="false"
					valueString="${employeeCode}"
					valueId="${employeeCode}"				
				/>
				
			   </td>
			  
			</tr>
			</table>
			</td>
			</tr>
			
			<tr>
			<td>
			<table>
			<tr>
			<td class="formBod">
			<fmt:message key="commons.caption.name"></fmt:message>
			</td>
			   <td  class="formBod" colspan="3">
			      
				<abc:autocomplete 
					inputId="name" 
					inputName="employeeName" 
					table="hr_employee" 
					firstKey="commons.caption.name"
					secondKey="commons.caption.code"
					firstParam="name"
					secondParam="empCode"
					bindById="false"
					valueString="${employeeName}"
					valueId="${employeeName}"					
				/>
				
			   </td>
			 
			</tr>
			</table>
			</td>
			 
			</tr>
			
			<tr>
			<td>
			<table>
			<tr>
			      <td class="formReq">
			<fmt:message key="hr.caption.vacation"></fmt:message>
			</td>
			   <td  class="formBod" colspan="3">
			      
				<select name="vacation" id="vacation">
				<option value=""><fmt:message key="commons.caption.select" /></option>
					<c:forEach items="${vacationList}" var="type">
						<option value="${type.id}" ${type.id == vacation.id ?' selected' : ''} / >${type.name}</option>
					</c:forEach>
				</select>
				
			   </td>
			   
			   
			   
			   <td>
			      <abc:i18n property="commons.button.submit"/><input type="submit" name="submit" value="<fmt:message key="commons.button.submit"/> " class="button"
						/>&nbsp;&nbsp;&nbsp;
		
			   </td>
			   </tr>
			   </td>
			   </table>
			</tr>
		
			
			<tr height="10">
				</tr>
		
			
			</form>
			
	
		


     	<tr>
	<td colspan="2">
<form id="employeeVacationAtInstallationForm" name="employeeVacationAtInstallationForm" method="POST" action="<c:url value="employeeVacationAtInstallationForm.html"/>">
	<table width="50%" border=1 cellpadding=0 cellspacing=0>
			
		
			<input type="hidden" name="vacation"  value="${vacation.id }" />
			<input type="hidden" name="employeeName"  value="${employeeName }" />
			<input type="hidden" name="employeeId"  value="${employeeId }" />
			
			
					<table border="0">
						<tr>
							<td colspan="3" class="helpTitle">
							<table>
							<tr>
							<td>
							<abc:i18n
								property="hr.header.employeeVacationAtInstallation" /><fmt:message
								key="hr.header.employeeVacationAtInstallation" /></td>
						</tr>
						
					
					<tr height="20" >
							
							<td class="formBod">
							<table    rules="all" >
							<tr>
							<td class="formBod">
								<abc:i18n property="commons.caption.code" /> 
								<fmt:message key="commons.caption.code" />
							</td>
							<td class="formBod">
								<abc:i18n property="commons.caption.name" /> 
								<fmt:message key="commons.caption.name" />
							</td>
							<td class="formBod">
								<abc:i18n property="hr.caption.balance" /> 
								<fmt:message key="hr.caption.balance" /> 
							</td>
							</tr>
							
							<tr>
						  <c:forEach varStatus="loop" var="emp" items="${employees}">
							<tr bgcolor="#F8F8F8"> 
								
							
							<td >
								${emp.empCode}	
								
							</td>
							<td>
								
									${emp.name}
									
							</td>
							<td >
								<input type="text" name="balance${loop.index }" id="balance${loop.index }" value="${emp.balance }">	
				
							</td>
							
							</tr>
						</c:forEach>
						</tr>
							</table>
							</td>
							
						</tr>
						
						</table>
						</td>
						</tr>
						
						
						
					</table>
		
				
					<tr>
				<td  >
				<table>
				<tr>
				<td>
					<abc:i18n property="commons.button.save"/>
					<input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button"/>&nbsp;&nbsp;&nbsp;
				</td>
				
				<td  >
				<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
				</td>
				</tr>
				</table>
				</td>
			</tr>		
					
			
		
			
	</table>
	</form>	
	</td>
	</tr>

</table>
	

<%@ include file="/web/common/includes/footer.jsp" %>
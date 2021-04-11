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
   
  <form method="post" action="<c:url value="serviceLengthCalculationForm.html?add=false"/>">
  
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
			   
			    <td>
			      <abc:i18n property="commons.button.submit"/><input type="submit" name="submit" value="<fmt:message key="commons.button.submit"/> " class="button"
						/>&nbsp;&nbsp;&nbsp;
		
			   </td>
			 
			</tr>
			</table>
			</td>
			 
			</tr>
			
			
			
			<tr height="10">
				</tr>
		
			
			</form>
			
	
		


     	<tr>
	<td colspan="2">
<form id="employeeVacationAtInstallationForm" name="serviceLengthCalculationForm" method="POST" action="<c:url value="serviceLengthCalculationForm.html"/>">
	<table width="50%" border=1 cellpadding=0 cellspacing=0>
			
		
			
			<input type="hidden" name="employeeName"  value="${employeeName }" />
			<input type="hidden" name="employeeId"  value="${employeeId }" />
			
			
					<table border="0">
						<tr>
							<td colspan="3" class="helpTitle">
							<table>
							<tr>
							<td>
							<abc:i18n
								property="hr.header.employeeServiceLength" /><fmt:message
								key="hr.header.employeeServiceLength" /></td>
						</tr>
						
					
					<tr height="20" >
							
							<td class="formBod">
							<table    rules="all">
							<tr>
							<td class="formBod">
							<table rules="all" >
							<tr>
							<td class="formBod">
							   <abc:i18n property="commons.caption.code" /> 
								<fmt:message key="commons.caption.code" />
							</td>
							<td class="formBod">
								<abc:i18n property="commons.caption.name" /> 
								<fmt:message key="commons.caption.name" />
							</td>
							</tr>
							
							 <c:forEach varStatus="loop" var="emp" items="${employees}">
							<tr bgcolor="#F8F8F8"> 
								
							
							<td nowrap="nowrap">
								${emp.empCode}	
								
							</td>
							<td nowrap="nowrap">
								
									${emp.name}
									
							</td>
							
							</tr>
							</c:forEach>
							</table>
								
						</td>
						
							
								<td nowrap="nowrap" class="formBod">
							   <table  rules="all"  >
								   <tr>
								      	<td class="formBod">
											<abc:i18n property="hr.caption.year" /> 
											<fmt:message key="hr.caption.year" /> 
										</td>
										
										<td class="formBod">
											<abc:i18n property="hr.caption.month" /> 
											<fmt:message key="hr.caption.month" /> 
										</td>
										<td class="formBod">
											<abc:i18n property="hr.caption.day" /> 
											<fmt:message key="hr.caption.day" /> 
										</td>
								   </tr>
								   
								       
						 <c:forEach varStatus="loop" var="emp" items="${employees}">
							<tr bgcolor="#F8F8F8"> 
								
								       <td>
								        <input type="text" size="5" name="year${loop.index }" id="year${loop.index }" value="${emp.year }">
								       </td>
								         
								        <td>
								           <input type="text" size="5" name="month${loop.index }" id="month${loop.index }" value="${emp.month }">
								       </td>
								       
								        <td>
								           <input type="text" size="5" name="day${loop.index }" id="day${loop.index }" value="${emp.day }">
								       </td>
							
							
							</tr>
						</c:forEach>
							   
							   </table>
							</td>
							
						
							<td nowrap="nowrap"  class="formBod">
							   <table  rules="all" >
								   <tr>
							  <td class="formBod">
								<abc:i18n property="hr.caption.experience" /> 
								<fmt:message key="hr.caption.experience" /> 
							</td>
							</tr>
							  <c:forEach varStatus="loop" var="emp" items="${employees}">
							<tr bgcolor="#F8F8F8"> 
						
							
							 <td nowrap="nowrap" >
								<input type="text" name="date${loop.index }" id="date${loop.index }" value="<_4s_:formatMiladiDate value="${emp.serviceDate }"/>">	
				
							</td>
							</tr>
							</c:forEach>
							</table>
						
						
						
						</td>
						</tr>
					
					</table>
		           </td>
		           </tr>
		         
				
					
				
				
			
	
			
	</table>
	
	
	</td>
	</tr>
	
	<tr>
	<td>
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
	
	
</form>	
</table>
	</table>
	</form>
	
	</td>
	</tr>
	</table>
	

<%@ include file="/web/common/includes/footer.jsp" %>
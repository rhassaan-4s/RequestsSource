<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>

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
         
		 var URL='employeeStatusView.html?formType=${formType}&deleteId='+deleteId+'&employeeId='+employeeId;
		
			window.location.href=URL;
			// var tr =tbl.deleteRow(lastRow-1);
			

	}

}
</script>

<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   
  <form method="post" action="<c:url value="employeeStatusView.html?add=false"/>">
  <c:if test="${formType == 'transaction'}">
 
			 <c:if test="${errorMessage!=null && errorMessage!='' }">
		<b><font color="red"><fmt:message key="hr.errors.noEmployeeSelected"/> </font></b>
		
		</c:if>
		
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
		</c:if>
			
			</form>
			
	
			<tr>
				<td>
					<table rules="all" align="center" width="70%" class="sofT">
			<tr>
				<td colspan="8" class="helpTitle"><abc:i18n
					property="hr.header.employeeStatusView" /><fmt:message
					key="hr.header.employeeStatusView" /></td>
			</tr>
			
			

			<tr>

				<td class="helpHed" nowrap="nowrap"> <fmt:message
					key="commons.caption.name" /> </td>
			    <td class="helpHed" nowrap="nowrap"><abc:i18n
					property="hr.caption.employeeStatus" /> <fmt:message
					key="hr.caption.employeeStatus" /></td>
					<td class="helpHed" nowrap="nowrap"><abc:i18n
					property="hr.caption.inDate" /> <fmt:message
					key="hr.caption.inDate" /></td>
				<td class="helpHed" nowrap="nowrap"> </td>
				<td class="helpHed" nowrap="nowrap"> </td>
				
				
			</tr>
						   <c:forEach items="${empStatusList}" var="record">
							   <tr height=20 bgcolor="#F8F8F8">
                                   
                                    <td class="helpBod" nowrap>
							         ${record.employee.name }
							
								  </td>
								  
                                   
								  <td class="helpBod" nowrap>
							         ${record.employeeStatus.name }
							
								  </td>
								  
								  
								    <td class="helpBod" nowrap>
							   <_4s_:formatMiladiDate value="${record.statusStartDate }"/>      
							
								  </td>
								  
								   
								  
								 
								  
								  <td class="helpBod" nowrap><abc:i18n
									property="commons.button.edit" /><a
									href="employeeStatusForm.html?empStatusId=${record.id}&employeeId=${record.employee.id }&formType=${formType }"><fmt:message
									key="commons.button.edit" /></a></td>
									
									  <td class="helpBod" nowrap><abc:i18n
									property="commons.button.delete" /><a
									href="" onclick="deleteMyRow(${record.id},${record.employee.id });"><fmt:message
									key="commons.button.delete" /></a></td>
									        </tr>
						   </c:forEach>
					
					</table>
				
				</td>
			</tr>
			
			<tr>
		<td colspan="2" align="center">
		<br>
			<abc:i18n property="commons.button.add"/>
				<c:if test="${formType=='employee'}">
		    <input type="button"  value="<fmt:message key="commons.button.add"/>" name="add" class="button"	onclick="window.location='employeeStatusForm.html?employeeName=${employeeName}&employeeCode=${employeeCode }&formType=${formType }&employeeId=${employeeId}&add=true'"></input>
		    </c:if>
		    
		    <c:if test="${formType=='transaction'}">
		    <input type="button"  value="<fmt:message key="commons.button.add"/>" name="add" class="button"	onclick="window.location='employeeStatusView.html?employeeName=${employeeName}&employeeCode=${employeeCode }&formType=${formType }&add=true'"></input>
		    </c:if>	 
		  
		   
		</td>
	</tr>
			
			
	</table>
   
 

</table>

	

<%@ include file="/web/common/includes/footer.jsp" %>
 <jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree"%>


<abc:security property=""/><br>
<script type="text/javascript">



function deleteAddedRow()
{
	//alert('inside deleteRow');
	y = document.getElementById("counter").value;
	//alert(y);
	var tbl = document.getElementById("table1");
	var lastRow = tbl.rows.length;
	var id=document.getElementById("id"+(lastRow-1));
	
	if(id==null ||id=='')
	{
	  var tr =tbl.deleteRow(lastRow-1);
	}

	y--;
	document.getElementById("counter").value = y;
	
}
</script>

<form id="empRelative" name="empRelative" method="POST"	action="<c:url value="employeeKinshipsDataForm.html"/>" >

<table width="90%" class="sofT" >
<input type="hidden" id="employeeId" name="employeeId" value="${employeeId }"/>
<input type="hidden" id="formType" name="formType" value="${formType}"/>
<input type="hidden"  id="empRelativeId" name="empRelativeId" value="${empRelativeId }"/>

<tr>
		<td colspan="2">
			<spring:bind path="hrEmployeeRelative.*">
						<c:if test="${not empty status.errorMessages}">
							<div><c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
								</font>
							</c:forEach></div>
						</c:if>
			</spring:bind>
			</td>
  </tr>
<tr height="20">
	
	      <td class="helpTitle" >
	      
	          <b> <fmt:message	key="hr.header.employeeKinshipsDataForm" /> </b>
	      </td>
		</tr>
<tr>
		<td colspan="3" valign="top">

		<table cellspacing="0" cellpadding="0" border="0" align="right"
			width="90%">
			
			
				
				<tr>
			<td>
	
			<table cellspacing="0" cellpadding="0" border="0" width="50%" >
	
		       <tr height="5">
					</tr> 
					
					<tr>
					<td class="formReq">
					<b><fmt:message key="commons.caption.name" /></b>&nbsp;
					</td>
					
					    <td>
					      &nbsp;
					   </td>
					   
					   
					  <td>
					        <spring:bind path="hrEmployeeRelative.relativeName">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					 
					   </td>
					   
					  </tr>
					  
					 
					<tr height="10">
					</tr>
					
					
						<tr>
					<td class="formReq">
					<b><fmt:message key="hr.caption.kinshipType" /></b>&nbsp;
					</td>
					
					    <td>
					      &nbsp;
					   </td>
					   
					   
					  <td>
					        <spring:bind path="hrEmployeeRelative.kinshipType">
					                  <select name="${status.expression}" value="${status.value}">
										<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
									
										
										 <c:forEach var="kinshipType"  items="${kinshipTypes}">
										 <option value="${kinshipType.id }" ${kinshipType.id == hrEmployeeRelative.kinshipType.id ? 'selected' :'' }>
										 ${kinshipType.name}
										  </option>
										 </c:forEach>
										
								    </select>
					        </spring:bind>
					 
					   </td>
					   
					  </tr>
					  
					 
					<tr height="10">
					</tr> 
					  
					  <tr>
					  <td class="formBod">
					  	   <b> <fmt:message	key="hr.caption.relationshipDate" /> </b>
					   </td> 
					  </tr>
					  
					  <tr height="5">
					</tr> 
					
					  
					  
					  <tr> 
					   
					   <td nowrap="nowrap" class="formReq">
					  
					   <b> <fmt:message	key="commons.caption.from" /> </b>
					   
					   </td>
					   <td>
					      &nbsp;
					   </td>
					   <td>
					     <spring:bind path="hrEmployeeRelative.relationshipStartDate">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					   </td>
					   
 
					   
					   <td nowrap="nowrap" class="formReq">
					  
					   <b> <fmt:message	key="commons.caption.to" /> </b>
					   
					   </td>
					   <td>
					      &nbsp;
					   </td>
					   <td>
					     <spring:bind path="hrEmployeeRelative.relationshipEndDate">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					   </td>
					   
					  
					</tr>
					
					<tr height="10" >
					</tr>
					
					
					  <tr id="btn">
					  
							<td colspan="3"  align="center">
							<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" onclick="detectForm();"/>&nbsp;&nbsp;&nbsp;
							</td >
							<td colspan="3">
								<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
							</td>
							
					</tr>
					
			</table>
			</td>
			</tr>
			</table>
			</td>
			</tr>
			</table>
					
		</form>			
<%@ include file="/web/common/includes/footer.jsp"%>
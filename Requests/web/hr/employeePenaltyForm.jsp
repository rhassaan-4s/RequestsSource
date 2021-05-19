 <jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree"%>


<abc:security property=""/><br>
<script type="text/javascript">

function checkStatus()
{
	var yes=document.getElementById("YesRadio");
	var no=document.getElementById("NoRadio");
	var penaltyRemoveDateTR=document.getElementById("penaltyRemoveDateTR");
	if(yes.checked)
	{
		penaltyRemoveDateTR.className='hideClass';
	}
	else if(no.checked)
	{
		penaltyRemoveDateTR.className='showClass';
	}
}
</script>

<form id="empPenalty" name="empPenalty" method="POST"	action="<c:url value="employeePenaltyForm.html"/>" >

<table width="90%" >
<input type="hidden" id="employeeId" name="employeeId" value="${employeeId }"/>
<input type="hidden" id="formType" name="formType" value="${formType}"/>
<input type="hidden"  id="empPenaltyId" name="empPenaltyId" value="${empPenaltyId }"/>
<tr>
		<td colspan="2">
			<spring:bind path="hrEmployeePenalty.*">
						<c:if test="${not empty status.errorMessages}">
							<div><c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
								</font>
							</c:forEach></div>
						</c:if>
			</spring:bind>
			</td>
  </tr>
  
<tr>
		<td colspan="2" valign="top">

		<table cellspacing="0" cellpadding="0" border="0" align="right"
			width="90%">
				
				<tr>
			<td>
	
			<table cellspacing="0" cellpadding="0" border="0" width="50%" class="sofT" >
    	<tr height="20">
	
	      <td class="helpTitle" colspan="2">
	          <b> <fmt:message	key="hr.header.employeePenaltyForm" /> </b> 
	      </td>
		</tr>
		
			<tr>
					  
				   
				   <td class="formReq">
				  
				   <b> <fmt:message	key="hr.caption.decisionNumber" /> </b>
				   
				   </td>
				   <td class="formBodControl">
				     <spring:bind path="hrEmployeePenalty.decisionNumber">
				    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
				     </spring:bind>
				   </td>
				   
					  
			</tr>
					
		 <tr>
		 <td nowrap="nowrap" class="formReq">
			  <b> <fmt:message	key="hr.caption.penalty" /> </b> 
		 </td>
		 <td class="formBodControl">
		 
		   <spring:bind path="hrEmployeePenalty.penalty">
					   <select name="${status.expression}" value="${status.value}">
					<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				
					
					 <c:forEach var="pen"  items="${penaltyList}">
					 <option value="${pen.id }" ${pen.id == hrEmployeePenalty.penalty.id ? 'selected' :'' }>
					 ${ pen.name}
					  </option>
					 </c:forEach>
					
					 </select>
		  </spring:bind>
					 
		 </td>
					
			</tr>
			
					
					
					<tr>
					  
					   
					   <td class="formReq">
					  
					   <b> <fmt:message	key="hr.caption.penaltyDate" /> </b>
					   
					   </td>
					   <td class="formBodControl">
					     <spring:bind path="hrEmployeePenalty.penaltyDate">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					   </td>
					  
					  
					</tr>
					
					
					<tr>
					  
					   
					   <td class="formReq">
					  
					   <b> <fmt:message	key="hr.caption.implementBeginDate" /> </b>
					   
					   </td>
					   <td class="formBodControl">
					     <spring:bind path="hrEmployeePenalty.implementBeginDate">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					   </td>
					  
					</tr>
					
					
					<tr>
					  
					   
					   <td class="formReq">
					  
					   <b> <fmt:message	key="hr.caption.discountDays" /> </b>
					   
					   </td>
					   <td class="formBodControl">
					     <spring:bind path="hrEmployeePenalty.discountDays">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					   </td>
					  
					</tr>
					
						<tr>
					  
					   
					   <td class="formReq">
					  
					   <b> <fmt:message	key="hr.caption.discountMonths" /> </b>
					   
					   </td>
					   <td class="formBodControl">
					     <spring:bind path="hrEmployeePenalty.discountMonths">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					   </td>
					  
					</tr>
					<tr>
					  
					   
					   <td class="formReq">
					  
					   <b> <fmt:message	key="hr.caption.confirmed" /> </b>
					   
					   </td>
					   <td class="formBodControl">
					     <spring:bind path="hrEmployeePenalty.confirmed">
					    <input   type="radio" id="YesRadio" onchange="checkStatus();" name ="${status.expression}" ${status.value == "Yes" ? ' checked':''} value="Yes"><abc:i18n property="commons.caption.yes"/><fmt:message key="commons.caption.yes"/></input>
					    <input   type="radio" id="NoRadio" onchange="checkStatus();" name ="${status.expression}" ${status.value == "No" ? ' checked':''} value="No"><abc:i18n property="commons.caption.no"/><fmt:message key="commons.caption.no"/></input>
					     </spring:bind>
					   </td>
					  
					</tr>
					
					<tr id="penaltyRemoveDateTR" class="hideClass">
					  
					   
					   <td class="formReq">
					  
					   <b> <fmt:message	key="hr.caption.penaltyRemoveDate" /> </b>
					   
					   </td>
					   <td class="formBodControl">
					     <spring:bind path="hrEmployeePenalty.penaltyRemoveDate">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					   </td>
					  
					</tr>
					
					<tr height="10" >
					<td colspan="2">
					</td>
					</tr>
					
					
					  <tr id="btn">
							<td colspan="3" align="center">
							<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button"/>&nbsp;&nbsp;&nbsp; 
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
 <jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree"%>


<abc:security property=""/><br>
<script type="text/javascript">


</script>

<form id="empErrand" name="empErrand" method="POST"	action="<c:url value="employeeErrandForm.html"/>" >

<table width="90%" >
<input type="hidden" id="employeeId" name="employeeId" value="${employeeId }"/>
<input type="hidden" id="formType" name="formType" value="${formType}"/>
<input type="hidden"  id="empErrandId" name="empErrandId" value="${empErrandId }"/>
<tr>
		<td colspan="2">
			<spring:bind path="hrEmployeeErrand.*">
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
	
	      <td class="helpTitle" colspan="4">
	          <b> <fmt:message	key="hr.header.employeeErrandForm" /> </b> 
	      </td>
		</tr>
		
		
		<tr>
					   
					   <td class="formReq">
					  
					   <b> <fmt:message	key="hr.caption.fromDate" /> </b>
					   
					   </td>
				
					   	<td class="formBodControl">
					   <c:if test="${hrEmployeeErrand.fromDate!=null && hrEmployeeErrand.fromDate!=''}">
					     <spring:bind path="hrEmployeeErrand.fromDate">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					     </c:if>
					     
					     <c:if test="${hrEmployeeErrand.fromDate == null || hrEmployeeSpecialVacation.fromDate ==''}">
					        <spring:bind path="hrEmployeeErrand.fromDate">
					      <input type="text" id="${status.expression }" name="${status.expression }" value="<_4s_:formatMiladiDate value="${dayDate}"/>"> 
					       </spring:bind>
					     </c:if>
					   </td>
					   
					   <td class="formReq">
					  
					   <b> <fmt:message	key="hr.caption.toDate" /> </b>
					   
					   </td>
					  <td class="formBodControl">
					   <c:if test="${hrEmployeeSpecialVacation.toDate!=null && hrEmployeeSpecialVacation.toDate!=''}">
					     <spring:bind path="hrEmployeeErrand.toDate">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					     </c:if>
					     
					     <c:if test="${hrEmployeeSpecialVacation.toDate == null || hrEmployeeSpecialVacation.toDate ==''}">
					        <spring:bind path="hrEmployeeErrand.toDate">
					      <input type="text" id="${status.expression }" name="${status.expression }" value="<_4s_:formatMiladiDate value="${dayDate}"/>"> 
					       </spring:bind>
					     </c:if>
					   </td>
					   
					  
					</tr>
		
			<tr>
					  
				   
				   <td class="formReq">
				  
				   <b> <fmt:message	key="hr.caption.observations" /> </b>
				   
				   </td>
				   <td class="formBodControl">
				     <spring:bind path="hrEmployeeErrand.observations">
				    <input size="50" type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
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
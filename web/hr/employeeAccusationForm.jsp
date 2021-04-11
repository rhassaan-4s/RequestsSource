 <jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree"%>


<abc:security property=""/><br>
<script type="text/javascript">


</script>

<form id="empAccusation" name="empAccusation" method="POST"	action="<c:url value="employeeAccusationForm.html"/>" >

<table width="90%" >
<input type="hidden" id="employeeId" name="employeeId" value="${employeeId }"/>
<input type="hidden" id="formType" name="formType" value="${formType}"/>
<input type="hidden"  id="empAccusationId" name="empAccusationId" value="${empAccusationId }"/>
<tr>
		<td colspan="2">
			<spring:bind path="hrEmployeeAccusation.*">
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
	          <b> <fmt:message	key="hr.header.employeeAccusationForm" /> </b> 
	      </td>
		</tr>
		
			<tr>
					  
				   
				   <td class="formReq">
				  
				   <b> <fmt:message	key="hr.caption.accusationNumber" /> </b>
				   
				   </td>
				   <td class="formBodControl">
				     <spring:bind path="hrEmployeeAccusation.accusationNumber">
				    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
				     </spring:bind>
				   </td>
				   
					  
			</tr>
					
		 <tr>
		 <td nowrap="nowrap" class="formReq">
			  <b> <fmt:message	key="hr.caption.accusationSummary" /> </b> 
		 </td>
		 <td class="formBodControl">
		 
		   <spring:bind path="hrEmployeeAccusation.accusationSummary">
				 <textarea cols="28" id="${status.expression }" name="${status.expression }" >${status.value }</textarea>   
		  </spring:bind>
					 
		 </td>
					
			</tr>
			
					
					
					<tr>
					  
					   
					   <td class="formReq">
					  
					   <b> <fmt:message	key="hr.caption.accusationDate" /> </b>
					   
					   </td>
					   <td class="formBodControl">
					   <c:if test="${hrEmployeeAccusation.accusationDate!=null && hrEmployeeAccusation.accusationDate!=''}">
					     <spring:bind path="hrEmployeeAccusation.accusationDate">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					     </c:if>
					     
					     <c:if test="${hrEmployeeAccusation.accusationDate == null || hrEmployeeAccusation.accusationDate ==''}">
					        <spring:bind path="hrEmployeeAccusation.accusationDate">
					      <input type="text" id="${status.expression }" name="${status.expression }" value="<_4s_:formatMiladiDate value="${dayDate}"/>"> 
					       </spring:bind>
					     </c:if>
					   </td>
					  
					  
					</tr>
					
					
					<tr>
					  
					   
					   <td class="formReq">
					  
					   <b> <fmt:message	key="hr.caption.executionDate" /> </b>
					   
					   </td>
					   <td class="formBodControl">
					     <spring:bind path="hrEmployeeAccusation.executionDate">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					   </td>
					  
					</tr>
					
					
					<tr>
					  
					   
					   <td class="formReq">
					  
					   <b> <fmt:message	key="hr.caption.finalAction" /> </b>
					   
					   </td>

					   <td class="formBodControl">
					     <spring:bind path="hrEmployeeAccusation.finalAction">
						    <select name="${status.expression}" value="${status.value}">
						<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
					
						
						 <c:forEach var="fAction"  items="${finalActionList}">
						 <option value="${fAction.id }" ${fAction.id == hrEmployeeAccusation.finalAction.id ? 'selected' :'' }>
						 ${ fAction.name}
						  </option>
						 </c:forEach>
						
						 </select>
					     </spring:bind>
					   </td>
					  
					</tr>
					
						<tr>
					  
					   
					   <td class="formReq">
					  
					   <b> <fmt:message	key="hr.caption.policeMinutesNumber" /> </b>
					   
					   </td>
					   <td class="formBodControl">
					     <spring:bind path="hrEmployeeAccusation.policeMinutesNumber">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					   </td>
					  
					</tr>
					
					
					<tr >
					  
					   
					   <td class="formReq">
					  
					   <b> <fmt:message	key="hr.caption.judge" /> </b>
					   
					   </td>
					   <td class="formBodControl">
					     <spring:bind path="hrEmployeeAccusation.judge">
					    <textarea cols="28"  id="${status.expression }" name="${status.expression }" >${status.value } </textarea>
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
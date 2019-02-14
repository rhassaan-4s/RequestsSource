<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<script type="text/javascript">

</script>


<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   <form id="requestTypesForm" name="requestTypesForm" method="POST"	action="<c:url value="/requestsApproval/requestTypesForm.html"/>">
   
	<input type="hidden"  id="requestTypeId" name="requestTypeId" value="${requestTypeId}"/>
	<tr>
		<td colspan="2">
			<spring:bind path="requestType.*">
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
  	<table  align="center" width="66%" class="sofT" >
		<tr id="head_1_ep">
			<td class="helpTitle" colspan="2" nowrap><abc:i18n
				property="requestsApproval.header.requestTypesForm" /><fmt:message
				key="requestsApproval.header.requestTypesForm" /></td>
		</tr>
		
	    <tr>
	  
	  	<c:set var="check"  value=""/>
	  	<c:if test="${requestTypeId!=null && requestTypeId!=''}">
			<c:set var="check"  value="disabled"/>
		</c:if>

	
	        <td nowrap class="formReq" width="30%">
				<abc:i18n property="requestsApproval.caption.requestCode"/>
				<fmt:message key="requestsApproval.caption.requestCode"/>
			</td>
				
									
			<td class="formBodControl">
				<spring:bind path="requestType.code">
					<input size="8" maxlength="8" type="text"	name="${status.expression}" value="${status.value}" onchange="fillCode(this);" ${check}" readonly="readonly"/> 			
				</spring:bind>
			</td>
	  	</tr>
		  
	   	<tr>
	   
	    	<td nowrap class="formReq" width="30%">
				<abc:i18n property="requestsApproval.caption.requestName"/>
				<fmt:message key="requestsApproval.caption.requestName"/>
			</td>
		
			<td class="formBodControl"width="70%">
				<spring:bind path="requestType.request_name">
				 	<input size="40" type="text"	name="${status.expression}" value="${status.value}"/> 		
				</spring:bind>
			</td>
	  	</tr>
		  
		  
	  	<tr>
	   		<td nowrap class="formReq" width="30%">
				<abc:i18n property="requestsApproval.caption.employeesRelation"/>
				<fmt:message key="requestsApproval.caption.employeesRelation"/>
			</td>
			
			<td class="formBodControl" width="70%">
				<spring:bind path="requestType.request_type">
					<select name="${status.expression}" id="${status.expression}" >
						<option value=""><fmt:message key="commons.caption.select" /></option>						
							<c:forEach items="${requestTypeList}" var="request">
								<option value="${request.id}" ${request.id == requestType.request_type.id ?' selected' : ''}>${request.description}</option>
							</c:forEach>
					</select>
				</spring:bind>
			</td>
		</tr>		  
		  
		<tr id="btn">
			<td colspan="2" align="center">
				<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp; 
				<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
			</td>
		</tr>
	</table>
	</tr>
</form>

</table>

	
			
<%@ include file="/web/common/includes/footer.jsp" %>

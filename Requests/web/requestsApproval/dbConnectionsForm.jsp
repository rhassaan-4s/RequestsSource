<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<script type="text/javascript">

</script>


<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   <form id="dbConnectionsForm" name="dbConnectionsForm" method="POST"	action="<c:url value="/requestsApproval/dbConnectionsForm.html"/>">
   
	<input type="hidden"  id="connectionId" name="connectionId" value="${connectionId}"/>
	<tr>
		<td colspan="2">
			<spring:bind path="dbConnections.*">
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
				property="requestsApproval.header.DBConnectionsForm" /><fmt:message
				key="requestsApproval.header.DBConnectionsForm" /></td>
		</tr>
		
	    <tr>
	  
	  	<c:set var="check"  value=""/>
	  	<c:if test="${connectionId!=null && connectionId!=''}">
			<c:set var="check"  value="disabled"/>
		</c:if>

	
	        <td nowrap class="formReq" width="30%">
				<abc:i18n property="commons.caption.description"/>
				<fmt:message key="commons.caption.description"/>
			</td>
				
									
			<td class="formBodControl">
				<spring:bind path="dbConnections.description">
					<input type="text"	name="${status.expression}" value="${status.value}"  readonly="readonly"/> 			
				</spring:bind>
			</td>
	  	</tr>
		  
	   	<tr>
	   
	    	<td nowrap class="formReq" width="30%">
				<abc:i18n property="requestsApproval.caption.hostName"/>
				<fmt:message key="requestsApproval.caption.hostName"/>
			</td>
		
			<td class="formBodControl"width="70%">
				<spring:bind path="dbConnections.host_name">
				 	<input type="text" name="${status.expression}" value="${status.value}"/> 		
				</spring:bind>
			</td>
	  	</tr>
		  
		  
	  	<tr>
	   		<td nowrap class="formReq" width="30%">
				<abc:i18n property="requestsApproval.caption.serviceId"/>
				<fmt:message key="requestsApproval.caption.serviceId"/>
			</td>
			
			<td class="formBodControl" width="70%">
				<spring:bind path="dbConnections.service_id">
				 	<input type="text" name="${status.expression}" value="${status.value}"/> 		
				</spring:bind>
			
<!--				
					<select name="${status.expression}" id="${status.expression}" >
						<option value=""><fmt:message key="commons.caption.select" /></option>						
							<c:forEach items="${requestTypeList}" var="request">
								<option value="${request.id}" ${request.id == requestType.request_type.id ?' selected' : ''}>${request.description}</option>
							</c:forEach>
					</select>
				-->
			</td>
		</tr>		  

	  	<tr>
	   		<td nowrap class="formReq" width="30%">
				<abc:i18n property="requestsApproval.caption.userName"/>
				<fmt:message key="requestsApproval.caption.userName"/>
			</td>
			
			<td class="formBodControl" width="70%">
				<spring:bind path="dbConnections.user_name">
				 	<input type="text" name="${status.expression}" value="${status.value}"/> 		
				</spring:bind>
			</td>
		</tr>

	  	<tr>
	   		<td nowrap class="formReq" width="30%">
				<abc:i18n property="requestsApproval.caption.password"/>
				<fmt:message key="requestsApproval.caption.password"/>
			</td>
			
			<td class="formBodControl" width="70%">
				<spring:bind path="dbConnections.db_pass">
				 	<input type="text" name="${status.expression}" value="${status.value}"/> 		
				</spring:bind>
			</td>
		</tr>

	  	<tr>
	   		<td nowrap class="formReq" width="30%">
				<abc:i18n property="requestsApproval.caption.isActive"/>
				<fmt:message key="requestsApproval.caption.isActive"/>
			</td>
			
			<td class="formBodControl" width="70%">
				<spring:bind path="dbConnections.is_active">
				 	<input type="checkbox" name="${status.expression}" value="${status.value}"/> 		
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

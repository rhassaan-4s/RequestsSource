<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>

<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   <form id="requestTypesView" name="requestTypesView" method="POST" action="<c:url value="/requestsApproval/requestTypesView.html"/>">
 
		<table border=0 cellspacing=1 cellpadding=0 id="ep" style="margin-right:40px">
			<tr>
				<td class="tableHeader" colspan=4 nowrap>
					<abc:i18n property="requestsApproval.header.requestTypesView"/><fmt:message key="requestsApproval.header.requestTypesView"/></td>
			</tr>	
			
			<tr>
				<td>
					<table rules="all" align="center" width="70%" class="sofT">			
						<tr>
						    <td class="helpHed" nowrap="nowrap"><abc:i18n
								property="requestsApproval.caption.requestCode" /> <fmt:message
								key="requestsApproval.caption.requestCode" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="requestsApproval.caption.requestName" /> <fmt:message
								key="requestsApproval.caption.requestName" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="requestsApproval.caption.employeesRelation" /> <fmt:message
								key="requestsApproval.caption.employeesRelation" /></td>
							
							<td class="helpHed">
							</td>
						</tr>
					<c:forEach items="${records}" var="record">
						<tr height=20 bgcolor="#F8F8F8">         
								<td class="helpBod" nowrap>
						         ${record.code }
							</td>
							  
							<td class="helpBod" nowrap>
						         ${record.request_name }
						
							</td>
							  
							<td class="helpBod" nowrap>
						         ${record.request_type.description }
						
							</td>
							  
							<td class="helpBod" nowrap><abc:i18n
								property="commons.button.edit" /><a
								href="requestTypesForm.html?requestTypeId=${record.id}"><fmt:message
								key="commons.button.edit" /></a>
							</td>
						</tr>
					</c:forEach>
						
					</table>
				
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">
				<br>
					<abc:i18n property="commons.button.add"/>
					<input type="button" value="<fmt:message key="commons.button.add"/>" name="add" class="button"	onclick="window.location='requestTypesForm.html'"></input>
				</td>
			</tr>
			
			
	</table>
   
   </form>

</table>

	
			
<%@ include file="/web/common/includes/footer.jsp" %>
   
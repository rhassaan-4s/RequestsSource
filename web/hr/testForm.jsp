
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>

<abc:security property=""/><br>




<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   <form id="testForm" name="testForm" method="POST"	action="<c:url value="/hr/test.html"/>">
  
  
  	<table  align="center" width="66%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap>Test</td>
			</tr>
			
		
		  
		   <tr>
		   
		    <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.name"/>
								<fmt:message key="commons.caption.name"/></td>
			
				<td class="formBodControl" width="70%">
					<spring:bind path="test.name">
					 <input size="51" type="text"	name="${status.expression}" value="${status.value}"/> 		
					</spring:bind>
					</td>
		  </tr>
		  
		  
		
		  
		    <tr>
	    
		        <td nowrap class="formReq" width="30%">
								<abc:i18n property="hr.caption.salaryAtAppointment"/>
								<fmt:message key="hr.caption.salaryAtAppointment"/></td>
					
										
				<td class="formBodControl">
					<spring:bind path="test.salary">
						<input  type="text"	name="${status.expression}" value="${status.value}" onchange="fillCode(this);"  ${check}"/> 			
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

</form>

</table>

	
			
<%@ include file="/web/common/includes/footer.jsp" %>

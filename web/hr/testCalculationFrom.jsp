
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>

<abc:security property=""/><br>




<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   <form id="testCalculationForm" name="testCalculationForm" method="POST"	action="<c:url value="/hr/testCalculationForm.html"/>">
  
  
  	<table  align="center" width="66%" class="sofT" >
	
		  
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

<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>

<abc:security property=""/><br>

<!--  <STYLE type='text/css'>
textarea {overflow-x: hidden; overflow-y: scroll}
</STYLE>-->
<script language="JavaScript">
  function fillCode(text)
  {
     // alert('inside fillCode');
	 // alert('text.length'+text.value.length);
	  var maxLength = parseInt(text.getAttribute('maxlength')); 
	 // alert('text.maxlength'+maxLength );
	if(text.value.length<maxLength)
	{
		//alert('inside length !=maxlength');
		var diff=maxLength-text.value.length;
		//alert('diff>>>'+diff);
		
		var num=0;

		for(j=0;j<diff-1;j++)
		{
		  num+='0';  
		}
		
		text.value=num+text.value;
	}
  }
</script>


<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   <form id="additionalForm" name="additionalForm" method="POST"	action="<c:url value="/hr/additionalForm.html"/>">
   
   
   <tr>
		<td colspan="2">
			<spring:bind path="additional.*">
						<c:if test="${not empty status.errorMessages}">
							<div><c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
								</font>
							</c:forEach></div>
						</c:if>
			</spring:bind>
			</td>
  </tr>
  
  	<table  align="center" width="66%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="10" nowrap><abc:i18n
					property="hr.header.additionalForm" /><fmt:message
					key="hr.header.additionalForm" /></td>
			</tr>
			
			
			<tr>
			
			<td nowrap class="helpHed" width="30%">
			&nbsp;
			</td>
			
			<td nowrap class="helpHed" width="30%">
				<abc:i18n property="hr.caption.applied"/>
				<fmt:message key="hr.caption.applied"/>
			</td>
			
			<td nowrap class="helpHed" width="30%">
				<abc:i18n property="hr.caption.everyHour"/>
				<fmt:message key="hr.caption.everyHour"/>
			</td>
			
            <td nowrap class="helpHed" width="30%">
				<abc:i18n property="hr.caption.everyDay"/>
				<fmt:message key="hr.caption.everyDay"/>
			</td>
			
			 <td nowrap class="helpHed" width="30%" >
			<font color="red"> *</font>
			<abc:i18n property="hr.caption.additionalCalcWay"/>
			<fmt:message key="hr.caption.additionalCalcWay"/>
			</td>
			</tr>
			
			
		    <tr>
		  
		        <td nowrap class="helpHed" width="30%">
								<abc:i18n property="hr.caption.additionalDay"/>
								<fmt:message key="hr.caption.additionalDay"/></td>
								
					 <td class="formBodControl">
					<spring:bind path="additional.dayApply">
						<input  type="checkbox"	name="dayApply" ${additional.dayApply == true ? ' checked':""}> 			
					</spring:bind>
					</td>
										
				 <td class="formBodControl">
					<spring:bind path="additional.dayHour">
						<input size="6" maxlength="4" type="text"	name="${status.expression}" value="${status.value}" /> 			
					</spring:bind>
					</td>
					 <td class="formBodControl">
					<spring:bind path="additional.dayDay">
						<input size="6" maxlength="4" type="text"	name="${status.expression}" value="${status.value}" /> 			
					</spring:bind>
					</td>
					
					<td class="formBodControl" width="70%">
					<spring:bind path="additional.additionalCalcWayDay">
					 <select name="${status.expression}" value="${status.value}">
					<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				
					
					 <c:forEach var="addCalcWay"  items="${additionalCalcWay}">
					 <option value="${addCalcWay.id }" ${addCalcWay.id == additional.additionalCalcWayDay.id ? 'selected' :'' }>
					 ${ addCalcWay.name}
					  </option>
					 </c:forEach>
	
					 </select>
					</spring:bind>
					</td>
		
		     
		  </tr>
		  
		  	    <tr>
		  
	
	    
		        <td nowrap class="helpHed" width="30%">
								<abc:i18n property="hr.caption.additionalNight"/>
								<fmt:message key="hr.caption.additionalNight"/></td>
								
					 <td class="formBodControl">
					<spring:bind path="additional.nightApply">
						<input  type="checkbox" name="nightApply"	${additional.nightApply == true ? ' checked':""}> 			
					</spring:bind>
					</td>
										
				 <td class="formBodControl">
					<spring:bind path="additional.nightHour">
						<input size="6" maxlength="4" type="text"	name="${status.expression}" value="${status.value}" /> 			
					</spring:bind>
					</td>
					 <td class="formBodControl">
					<spring:bind path="additional.nightDay">
						<input size="6" maxlength="4" type="text"	name="${status.expression}" value="${status.value}" /> 			
					</spring:bind>
					</td>
		    
		    	<td class="formBodControl" width="70%">
					<spring:bind path="additional.additionalCalcWayNight">
					 <select name="${status.expression}" value="${status.value}">
					<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				
					
					 <c:forEach var="addCalcWay"  items="${additionalCalcWay}">
					 <option value="${addCalcWay.id }" ${addCalcWay.id == additional.additionalCalcWayNight.id ? 'selected' :'' }>
					 ${ addCalcWay.name}
					  </option>
					 </c:forEach>
	
					 </select>
					</spring:bind>
					</td>
		     
		  </tr>
		  
		  	    <tr>
		  
	
	    
		        <td nowrap class="helpHed" width="30%">
								<abc:i18n property="hr.caption.additionalVacation"/>
								<fmt:message key="hr.caption.additionalVacation"/></td>
								
					 <td class="formBodControl">
					<spring:bind path="additional.vacationApply">
						<input  type="checkbox"	name="vacationApply" ${additional.vacationApply == true ? ' checked':""}> 			
					</spring:bind>
					</td>
										
				 <td class="formBodControl">
					<spring:bind path="additional.vacationHour">
						<input size="6" maxlength="4" type="text"	name="${status.expression}" value="${status.value}" /> 			
					</spring:bind>
					</td>
					 <td class="formBodControl">
					<spring:bind path="additional.vacationDay">
						<input size="6" maxlength="4" type="text"	name="${status.expression}" value="${status.value}" /> 			
					</spring:bind>
					</td>
		
					<td class="formBodControl" width="70%">
					<spring:bind path="additional.additionalCalcWayVacation">
					 <select name="${status.expression}" value="${status.value}">
					<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				
					
					 <c:forEach var="addCalcWay"  items="${additionalCalcWay}">
					 <option value="${addCalcWay.id }" ${addCalcWay.id == additional.additionalCalcWayVacation.id ? 'selected' :'' }>
					 ${ addCalcWay.name}
					  </option>
					 </c:forEach>
	
					 </select>
					</spring:bind>
					</td>
		     
		  </tr>
		  
		 
 	    <tr>
		  
	
		        <td nowrap class="helpHed" width="30%">
								<abc:i18n property="hr.caption.additionalCalcBegin"/>
								<fmt:message key="hr.caption.additionalCalcBegin"/></td>
								
				<td class="formBodControl">
				&nbsp;
				</td>
					
					 <td nowrap class="dataLabel" width="30%">
					 <table >
					 <tr >
					<td nowrap class="dataLabel" width="30%">
					<abc:i18n property="hr.caption.from"/>
					<fmt:message key="hr.caption.from"/></td>
					
						 <td class="formBodControl">
						<spring:bind path="additional.calcNightBegin">
							<input size="6" maxlength="4" type="text"	name="${status.expression}" value="${status.value}" />			
						</spring:bind>
						</td>
						
					 <td>
					<abc:i18n property="hr.caption.to"/>
					<fmt:message key="hr.caption.to"/></td>	
									
				 <td class="formBodControl">
					<spring:bind path="additional.calcNightEnd">
						<input size="6" maxlength="4" type="text"	name="${status.expression}" value="${status.value}" /> 			
					</spring:bind>
					</td>
					</tr>
					</table>
		     </td>
		     <td class="formBodControl" colspan="5">
		      &nbsp;
		     </td>
		  </tr>
		  
		    <tr>
		  
	
		        <td nowrap class="helpHed" width="30%">
								<abc:i18n property="hr.caption.additionalMaxValue"/>
								<fmt:message key="hr.caption.additionalMaxValue"/></td>
								
				<td class="formBodControl" colspan="2">
					&nbsp;
					</td>
								
				<td class="formBodControl">
					<spring:bind path="additional.maxValue">
						<input size="6" maxlength="4" type="text"	name="${status.expression}" value="${status.value}" /> 			
					</spring:bind>
					</td>
					
					
					<td class="formBodControl" width="70%">
					<spring:bind path="additional.additionalCalcMaxValue">
					 <select name="${status.expression}" value="${status.value}">
					<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				
					
					 <c:forEach var="addCalcWay"  items="${additionalCalcWay}">
					 <option value="${addCalcWay.id }" ${addCalcWay.id == additional.additionalCalcMaxValue.id ? 'selected' :'' }>
					 ${ addCalcWay.name}
					  </option>
					 </c:forEach>
	
					 </select>
					</spring:bind>
					</td>
					
					
			
		  </tr>

		  
		  
               <tr id="btn">
               <td>
               <table>
               <tr>
					<td colspan="2" align="center" nowrap="nowrap">
					<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp; 
						<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
					</td>
				</tr>
				</table>
				</td>
						</tr>
						
</table>

</form>

</table>

	
			
<%@ include file="/web/common/includes/footer.jsp" %>

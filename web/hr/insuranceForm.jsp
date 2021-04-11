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
   <form id="insuranceForm" name="insuranceForm" method="POST"	action="<c:url value="/hr/insuranceForm.html"/>">
   
   <input type="hidden"  id="insuranceId" name="insuranceId" value="${insuranceId }"/>
   <tr>
		<td colspan="2">
			<spring:bind path="insurance.*">
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
				<td class="helpTitle" colspan="2" nowrap><abc:i18n
					property="hr.header.addInsurance" /><fmt:message
					key="hr.header.addInsurance" /></td>
			</tr>
			
		    <tr>
		  <c:set var="check"  value=""/>
		  <c:if test="${insuranceId!=null && insuranceId!='' }">
					 <c:set var="check"  value="disabled"/>
		</c:if>
	    
		        <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.code"/>
								<fmt:message key="commons.caption.code"/></td>
					
										
				<td class="formBodControl">
					<spring:bind path="insurance.code">
						<input size="8" maxlength="4" type="text"	name="${status.expression}" value="${status.value}" onchange="fillCode(this);"  ${check}" readonly="readonly"/> 			
					</spring:bind>
					</td>
		
		     
		  </tr>
		  
		   <tr>
		   
		    <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.name"/>
								<fmt:message key="commons.caption.name"/></td>
			
				<td class="formBodControl" width="70%">
					<spring:bind path="insurance.name">
					 <input size="51" type="text"	name="${status.expression}" value="${status.value}"/> 		
					</spring:bind>
					</td>
		  </tr>
		  
		  
		  <tr>
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.englishName"/>
								<fmt:message key="commons.caption.englishName"/></td>
			
				<td  class="formBodControl"  width="70%">
					<spring:bind path="insurance.ename">
					 <input size="51"  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/> 				
					</spring:bind>
					</td>
		  </tr>
		  <tr>
			  <td colspan="4">
			   <table>
			   
			         <tr>
					    	  <td class="helpHed">
						          &nbsp;
						      </td>
						      <td class="helpHed">
						          <abc:i18n property="hr.caption.maxVlaue"/>
								<fmt:message key="hr.caption.maxValue"/>
						      </td>
						      <td class="helpHed">
						          <abc:i18n property="hr.caption.minValue"/>
								<fmt:message key="hr.caption.minValue"/>
						      </td>
						      <td class="helpHed">
						          <abc:i18n property="hr.caption.companyRatio"/>
								<fmt:message key="hr.caption.companyRatio"/>
						      </td>
						      <td class="helpHed">
						          <abc:i18n property="hr.caption.employeeRatio"/>
								<fmt:message key="hr.caption.employeeRatio"/>
						      </td>
					   </tr>
					   
					   <tr>
					    	  <td class="helpHed">
						          <abc:i18n property="hr.caption.fixed"/>
								<fmt:message key="hr.caption.fixed"/>
						      </td>
						      <td class="formBodControl">
						      <spring:bind path="insurance.maxValueFixed"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						          <spring:bind path="insurance.minValueFixed"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						          <spring:bind path="insurance.companyRatioFixed"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						          <spring:bind path="insurance.employeeRatioFixed"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
					   </tr>
					   
					   <tr>
					    	  <td class="helpHed">
						          <abc:i18n property="hr.caption.changable"/>
								 <fmt:message key="hr.caption.changable"/>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="insurance.maxValueChanged"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="insurance.minValueChanged"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						           <spring:bind path="insurance.companyRatioChanged"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind> 
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="insurance.employeeRatioChanged"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
					   </tr>
			   
			   </table>
			  
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

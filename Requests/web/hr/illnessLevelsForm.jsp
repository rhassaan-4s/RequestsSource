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
   <form id="illnessLevelsForm" name="illnessLevelsForm" method="POST"	action="<c:url value="/hr/illnessLevelsForm.html"/>">
   
   <input type="hidden"  id="illnessLevelsId" name="illnessLevelsId" value="${illnessLevelsId }"/>
   <tr>
		<td colspan="2">
			<spring:bind path="illnessLevels.*">
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
					property="hr.header.illnessLevels" /><fmt:message
					key="hr.header.illnessLevels" /></td>
			</tr>
		
		  
		  <tr>
			  <td colspan="4">
			   <table>
			   
			         <tr>
					    	  
						      <td class="helpHed">
						          <abc:i18n property="hr.caption.category"/>
								<fmt:message key="hr.caption.category"/>
						      </td>
						      <td class="helpHed">
						          <abc:i18n property="hr.caption.minValue"/>
								<font color="red" >*</font><fmt:message key="hr.caption.minValue"/>
						      </td>
						      <td class="helpHed">
						          <abc:i18n property="hr.caption.maxValue"/>
								<font color="red" >*</font><fmt:message key="hr.caption.maxValue"/>
						      </td>
						      <td class="helpHed">
						          <abc:i18n property="hr.caption.ratioFromPayment"/>
								<font color="red" >*</font><fmt:message key="hr.caption.ratioFromPayment"/>
						      </td>
						       <td class="helpHed">
						          <abc:i18n property="hr.caption.ratioFromChangable"/>
								<fmt:message key="hr.caption.ratioFromChangable"/>
						      </td>
					   </tr>
					   
					   <tr>
					    	  <td class="helpHed" nowrap="nowrap">
						          <abc:i18n property="hr.caption.first"/>
								<fmt:message key="hr.caption.first"/>
						      </td>
						      <td class="formBodControl">
						      <spring:bind path="illnessLevels.min1"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						          <spring:bind path="illnessLevels.max1"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						          <spring:bind path="illnessLevels.p1"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						        <td class="formBodControl">
						          <spring:bind path="illnessLevels.pv1"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						     
					   </tr>
					   
					   <tr>
					    	  <td class="helpHed" nowrap="nowrap">
						          <abc:i18n property="hr.caption.second"/>
								 <fmt:message key="hr.caption.second"/>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="illnessLevels.min2"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="illnessLevels.max2"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						           <spring:bind path="illnessLevels.p2"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind> 
						      </td>
						        <td class="formBodControl">
						          <spring:bind path="illnessLevels.pv2"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						     
					   </tr>
					   
					   
					   <tr>
					    	  <td class="helpHed" nowrap="nowrap">
						          <abc:i18n property="hr.caption.third"/>
								 <fmt:message key="hr.caption.third"/>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="illnessLevels.min3"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="illnessLevels.max3"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						           <spring:bind path="illnessLevels.p3"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind> 
						      </td>
						        <td class="formBodControl">
						          <spring:bind path="illnessLevels.pv3"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						     
					   </tr>
					   
					   
					   <tr>
					    	  <td class="helpHed" nowrap="nowrap">
						          <abc:i18n property="hr.caption.forth"/>
								 <fmt:message key="hr.caption.forth"/>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="illnessLevels.min4"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="illnessLevels.max4"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						           <spring:bind path="illnessLevels.p4"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind> 
						      </td>
						        <td class="formBodControl">
						          <spring:bind path="illnessLevels.pv4"> 
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

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
   <form id="hallmarkForm" name="hallmarkForm" method="POST"	action="<c:url value="/hr/hallmarkForm.html"/>">
   
   <input type="hidden"  id="hallmarkId" name="hallmarkId" value="${hallmarkId }"/>
   <tr>
		<td colspan="2">
			<spring:bind path="hallmark.*">
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
					property="hr.header.addHallmark" /><fmt:message
					key="hr.header.addHallmark" /></td>
			</tr>
			
		    <tr>
		  <c:set var="check"  value=""/>
		  <c:if test="${hallmarkId!=null && hallmarkId!='' }">
					 <c:set var="check"  value="disabled"/>
		</c:if>
	    
		        <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.code"/>
								<fmt:message key="commons.caption.code"/></td>
					
										
				<td class="formBodControl">
					<spring:bind path="hallmark.effCode">
						<input size="8" maxlength="4" type="text"	name="${status.expression}" value="${status.value}" onchange="fillCode(this);"  ${check}" readonly="readonly"/> 			
					</spring:bind>
					</td>
		
		     
		  </tr>
		  
		   <tr>
		   
		    <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.name"/>
								<fmt:message key="commons.caption.name"/></td>
			
				<td class="formBodControl" width="70%">
					<spring:bind path="hallmark.effName">
					 <input size="51" type="text"	name="${status.expression}" value="${status.value}"/> 		
					</spring:bind>
					</td>
		  </tr>
		  
		  
		  <tr>
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.englishName"/>
								<fmt:message key="commons.caption.englishName"/></td>
			
				<td  class="formBodControl"  width="70%">
					<spring:bind path="hallmark.eng_name">
					 <input size="51"  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/> 				
				</spring:bind>
					</td>
		  </tr>
		  
		   <tr>
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="hr.caption.discount"/>
								<fmt:message key="hr.caption.discount"/></td>
			
				<td  class="formBodControl"  width="70%">
					<spring:bind path="hallmark.discount">
					 <input size="51"  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/> 				
				</spring:bind>
					</td>
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
								<fmt:message key="hr.caption.minValue"/>
						      </td>
						      <td class="helpHed">
						          <abc:i18n property="hr.caption.maxValue"/>
								<fmt:message key="hr.caption.maxValue"/>
						      </td>
						      <td class="helpHed">
						          <abc:i18n property="hr.caption.ratio"/>
								<fmt:message key="hr.caption.ratio"/>
						      </td>
					   </tr>
					   
					   <tr>
					    	  <td class="helpHed">
						          <abc:i18n property="hr.caption.first"/>
								<fmt:message key="hr.caption.first"/>
						      </td>
						      <td class="formBodControl">
						      <spring:bind path="hallmark.min1"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						          <spring:bind path="hallmark.max1"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						          <spring:bind path="hallmark.p1"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						     
					   </tr>
					   
					   <tr>
					    	  <td class="helpHed">
						          <abc:i18n property="hr.caption.second"/>
								 <fmt:message key="hr.caption.second"/>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="hallmark.min2"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="hallmark.max2"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						           <spring:bind path="hallmark.p2"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind> 
						      </td>
						     
					   </tr>
					   
					   
					   <tr>
					    	  <td class="helpHed">
						          <abc:i18n property="hr.caption.third"/>
								 <fmt:message key="hr.caption.third"/>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="hallmark.min3"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="hallmark.max3"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						           <spring:bind path="hallmark.p3"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind> 
						      </td>
						     
					   </tr>
					   
					   
					   <tr>
					    	  <td class="helpHed">
						          <abc:i18n property="hr.caption.forth"/>
								 <fmt:message key="hr.caption.forth"/>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="hallmark.min4"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="hallmark.max4"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						           <spring:bind path="hallmark.p4"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind> 
						      </td>
						     
					   </tr>
					   
					   
					   <tr>
					    	  <td class="helpHed">
						          <abc:i18n property="hr.caption.fifth"/>
								 <fmt:message key="hr.caption.fifth"/>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="hallmark.min5"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						            <spring:bind path="hallmark.max5"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind>
						      </td>
						      <td class="formBodControl">
						           <spring:bind path="hallmark.p5"> 
						          <input  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
						        </spring:bind> 
						      </td>
						     
					   </tr>
					   
					   
						 
			   
			   </table>
			  
			  </td>
		  </tr>
		  
		 
		  <tr>
		   <td class="formBodControl" nowrap="nowrap">
		                     <abc:i18n property="hr.caption.employeePartOfInsuranceOn"/>
								<fmt:message key="hr.caption.employeePartOfInsuranceOn"/>
		   </td>
		    <td class="formBodControl">
		      <spring:bind path="hallmark.insu_rule">
					 <select name="${status.expression}" value="${status.value}">
					<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				
					
					 <c:forEach var="insuRule"  items="${insuranceRulesList}">
					 <option value="${insuRule.id }" ${insuRule.id == hallmark.insu_rule.id ? 'selected' :'' }>
					 ${ insuRule.name}
					  </option>
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

</form>

</table>

	
			
<%@ include file="/web/common/includes/footer.jsp" %>

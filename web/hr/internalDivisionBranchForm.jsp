<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>

<abc:security property=""/><br>

<!--  <STYLE type='text/css'>
textarea {overflow-x: hidden; overflow-y: scroll}
</STYLE>-->
<script type="text/javascript">



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
   <form id="internalDivisionBranchForm" name="internalDivisionBranchForm" method="POST"	action="<c:url value="/hr/internalDivisionBranchForm.html"/>">
   
   <input type="hidden"  id="internalDivisionBranchId" name="internalDivisionBranchId" value="${internalDivisionBranchId}"/>
   <input type="hidden" value="${typeId}" name="typeId" id="typeId">
   <tr>
		<td colspan="2">
			<spring:bind path="internalDivisionBranch.*">
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
					property="hr.header.addOrEdit" /><fmt:message
					key="hr.header.addOrEdit" />${type.nameForCertainLocale }</td>
			</tr>
			
		    <tr>
		  
		  <c:set var="check"  value=""/>
		  <c:if test="${internalDivisionBranchId!=null && internalDivisionBranchId!=''}">
					 <c:set var="check"  value="disabled"/>
		</c:if>
	
		
		        <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.code"/>
								<fmt:message key="commons.caption.code"/></td>
					
										
				<td class="formBodControl">
					<spring:bind path="internalDivisionBranch.internal_division_branch">
						<input size="8" maxlength="3" type="text"	name="${status.expression}" value="${status.value}" onchange="fillCode(this);" ${check}"/> 			
					</spring:bind>
					</td>
		  </tr>
		  
		   <tr>
		   
		    <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.name"/>
								<fmt:message key="commons.caption.name"/></td>
			
				<td class="formBodControl" width="70%">
					<spring:bind path="internalDivisionBranch.name">
					 <input size="40" type="text"	name="${status.expression}" value="${status.value}"/> 		
					</spring:bind>
					</td>
		  </tr>
		  
		  
		  <tr>
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.englishName"/>
								<fmt:message key="commons.caption.englishName"/></td>
			
				<td  class="formBodControl" width="70%">
					<spring:bind path="internalDivisionBranch.ename">
					 <input size="40"  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/> 				
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

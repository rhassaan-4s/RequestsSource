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
   <form id="documentsForm" name="documentsForm" method="POST"	action="<c:url value="/hr/documentsForm.html"/>">
   
   <input type="hidden"  id="hrDocumentsId" name="hrDocumentsId" value="${hrDocumentsId }"/>
   <tr>
		<td colspan="2">
			<spring:bind path="hrDocuments.*">
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
					property="hr.header.addDocument" /><fmt:message
					key="hr.header.addDocument" /></td>
			</tr>
			
		    <tr>
		  <c:set var="check"  value=""/>
		  <c:if test="${hrDocumentsId!=null && hrDocumentsId!='' }">
					 <c:set var="check"  value="disabled"/>
		</c:if>
	
		
		        <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.code"/>
								<fmt:message key="commons.caption.code"/></td>
					
												
										
				<td class="formBodControl">
					<spring:bind path="hrDocuments.code">
						<input size="3" maxlength="3" type="text"	name="${status.expression}" value="${status.value}" onchange="fillCode(this);" ${check}" readonly="readonly"/> 			
					</spring:bind>
					</td>
		  </tr>
		  
		   <tr>
		   
		    <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.name"/>
								<fmt:message key="commons.caption.name"/></td>
			
				<td class="formBodControl" width="70%">
					<spring:bind path="hrDocuments.name">
					 <input size="51" type="text"	name="${status.expression}" value="${status.value}"/> 		
					</spring:bind>
					</td>
		  </tr>
		  
		  
		  <tr>
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.englishName"/>
								<fmt:message key="commons.caption.englishName"/></td>
			
				<td  class="formBodControl" width="70%">
					<spring:bind path="hrDocuments.ename">
					 <input size="51"  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/> 				
					</spring:bind>
					</td>
		  </tr>
		 
		  <tr>
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="hr.caption.hasExpiryDate"/>
								<fmt:message key="hr.caption.hasExpiryDate"/></td>
			
				<td   class="formBodControl" width="70%">
				<table border="0">
		        <tr>
		         <td >
					<spring:bind path="hrDocuments.hasExpiryDate">
					           <td  nowrap="nowrap">
									<input   type="radio" id="yesRadio"  name ="${status.expression}" ${status.value == "true" ? ' checked':''} value="1"><abc:i18n property="commons.caption.yes"/><fmt:message key="commons.caption.yes"/></input>
								</td>
								<td  nowrap="nowrap">
									<input  type="radio" id="noRadio"  name ="${status.expression}" ${status.value == "false" ? ' checked':''} value="0"><abc:i18n property="commons.caption.no"/><fmt:message key="commons.caption.no"/></input>
								</td> 				
					</spring:bind>
				</td>
					</tr>
					</table>
					</td>
					
					
		  </tr>
		  
		  
		  <tr>
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="hr.caption.dateType"/>
								<fmt:message key="hr.caption.dateType"/></td>
			
				<td   class="formBodControl" width="70%">
				<table border="0">
		        <tr>
		         <td >
					<spring:bind path="hrDocuments.dateType">
					           <td  nowrap="nowrap">
									<input   type="radio" id="yesRadio"  name ="${status.expression}" ${status.value == "higiri" ? ' checked':''} value="higiri"><abc:i18n property="hr.caption.higiri"/><fmt:message key="hr.caption.higiri"/></input>
								</td>
								<td  nowrap="nowrap">
									<input  type="radio" id="noRadio"  name ="${status.expression}" ${status.value == "miladi" ? ' checked':''} value="miladi"><abc:i18n property="hr.caption.miladi"/><fmt:message key="hr.caption.miladi"/></input>
								</td> 				
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

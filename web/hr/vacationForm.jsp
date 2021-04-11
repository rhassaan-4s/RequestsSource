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


  function showMigrationAllowedRow()
  {
  	//alert('111111111111');
  	var vacationType=document.getElementById("vacationType");
  	if(vacationType.value==0)
  	{
  		document.getElementById("migrationAllowedRow").className ='showClass';
  		document.getElementById("monthsRow").className ='showClass';
  		
  	}

  	else if(vacationType.value!=0 )
  	{
  		document.getElementById("migrationAllowedRow").className ='hideClass';
  		document.getElementById("monthsRow").className ='hideClass';
  	}
  }

  function checkForAllowMigration()
  {
	  //alert('2222222222');
	 var migAllowedCheck=document.getElementById("isMigrationAllowed");
	 if(migAllowedCheck.checked==true)
	 {
	  document.getElementById("migratedToRow").className ='showClass';
	 }

	 else
	 {
	  document.getElementById("migratedToRow").className ='hideClass';

	 }
  }

</script>

 <form id="vacationForm" name="vacationForm" method="POST"	action="<c:url value="/hr/vacationForm.html"/>">
<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
  
   
   <input type="hidden"  id="vacationId" name="vacationId" value="${vacationId }"/>
   
   <tr>
		<td colspan="2">
			<spring:bind path="vacation.*">
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
  <td>
  
  	<table  align="center" width="66%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap><abc:i18n
					property="hr.header.addVacation" /><fmt:message
					key="hr.header.addVacation" /></td>
			</tr>
			
		    <tr>
		  <c:set var="check"  value=""/>
		  <c:if test="${vacationId!=null && vacationId!='' }">
					 <c:set var="check"  value="disabled"/>
		</c:if>
	
		  
		        <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.code"/>
								<fmt:message key="commons.caption.code"/></td>
					
										
				<td class="formBodControl">
					<spring:bind path="vacation.code">
						<input size="8" maxlength="4" type="text"	name="${status.expression}" value="${status.value}" onchange="fillCode(this);"  ${check}" readonly="readonly"/> 			
					</spring:bind>
					</td>
		     
		  </tr>
		  
		   <tr>
		   
		    <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.name"/>
								<fmt:message key="commons.caption.name"/></td>
			
				<td class="formBodControl" width="70%">
					<spring:bind path="vacation.name">
					 <input size="51" type="text"	name="${status.expression}" value="${status.value}"/> 		
					</spring:bind>
					</td>
		  </tr>
		  
		  
		  <tr>
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.englishName"/>
								<fmt:message key="commons.caption.englishName"/></td>
			
				<td  class="formBodControl" width="70%">
					<spring:bind path="vacation.ename">
					 <input size="51"  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/> 				
					</spring:bind>
					</td>
		  </tr>
		  
		  <tr>
		   <td nowrap class="formReq" width="30%">
				<abc:i18n property="hr.caption.vacationType"/>
				<fmt:message key="hr.caption.vacationType"/></td>
				<td class="formBodControl" width="70%">
					<spring:bind path="vacation.vacationType">
					 <select name="${status.expression}" id="${status.expression}" value="${status.value}" onclick="showMigrationAllowedRow()">
						<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
					 	<c:forEach var="vacType"  items="${vacationTypeList}">
					 	<option value="${vacType.id }" ${vacType.id == vacation.vacationType.id ? 'selected' :'' }>
					 	${ vacType.name}
					  	</option>
						</c:forEach>
					
					 </select>
					</spring:bind>
					</td>
		  </tr>
		  
		  <c:if test="${vacation.vacationType.id==0 }">
		    <tr id="migrationAllowedRow" class="showClass">
		    </c:if>
		   <c:if test="${vacation.vacationType.id!=0}">
		    <tr id="migrationAllowedRow" class="hideClass">
		    </c:if> 
		   <td nowrap class="formReq" width="30%">
				<abc:i18n property="hr.caption.isMigrationAllowed"/>
				<fmt:message key="hr.caption.isMigrationAllowed"/></td>
				<td class="formBodControl" width="70%">
					<spring:bind path="vacation.isMigrationAllowed">
				   <input type="checkbox" name="${status.expression}" id="${status.expression}"  ${vacation.isMigrationAllowed == true ? ' checked':""} onchange="checkForAllowMigration();"/>
					</spring:bind>
					</td>
		  </tr>
		  
		  <c:if test="${vacation.vacationType.id==0 && vacation.isMigrationAllowed==true}">
		   <tr id="migratedToRow" class="showClass">
		   </c:if>
		    <c:if test="${vacation.vacationType.id!=0 || vacation.isMigrationAllowed==false}">
		   <tr id="migratedToRow" class="hideClass">
		   </c:if>
		   <td colspan="2">
		   <table>
		   <tr>
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="hr.caption.maxNumberForMigration"/>
								<fmt:message key="hr.caption.maxNumberForMigration"/></td>
				<td class="formBodControl" width="70%">
					<spring:bind path="vacation.maxNumberForMigration">
				   <input type="text" name="${status.expression}" value="${status.value}" />
					</spring:bind>
					</td>
		  
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="hr.caption.migratedTo"/>
								<fmt:message key="hr.caption.migratedTo"/></td>
				<td class="formBodControl" width="70%">
					<spring:bind path="vacation.migratedTo">
				    <select name="${status.expression}" value="${status.value}">
					<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				
					
					 <c:forEach var="vac"  items="${vacationList}">
					 <option value="${vac.id }" ${vac.id == vacation.id ? 'selected' :'' }>
					 ${ vac.name}
					  </option>
					 </c:forEach>
					
					 </select>
					 
					</spring:bind>
					</td>
					</tr>
					</table>
					</td>
					
		  </tr>
		  
		  <c:if test="${vacation.vacationType.id==0}">
		   <tr id="monthsRow" class="showClass">
		   </c:if>
		   
		    <c:if test="${vacation.vacationType.id!=0}">
		   <tr id="monthsRow" class="hideClass">
		   </c:if>
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="hr.caption.monthsBeforeLeaveEntitlement"/>
								<fmt:message key="hr.caption.monthsBeforeLeaveEntitlement"/></td>
				<td class="formBodControl" width="70%">
					<spring:bind path="vacation.monthsBeforeLeaveEntitlement">
				   <input type="text" name="${status.expression}" value="${status.value}" />
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
</td>
</tr>
</table>

	</form>		
		
<%@ include file="/web/common/includes/footer.jsp" %>

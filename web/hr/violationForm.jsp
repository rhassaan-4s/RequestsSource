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


  function showRow()
  {
  	
  	var violationReason=document.getElementById("violationReason");
  	//alert("violationReason.value"+violationReason.value);
	//alert("violationReason.value"+${violation.violationReason.id});
  	if((violationReason.value==0))
  	{
  	  	
  		//document.getElementById("discountOfLateness").className ='showClass';
  		//document.getElementById("discountOfLeaving").className ='hideClass';
  	//	document.getElementById("dayAbsence").className ='hideClass';
  		document.getElementById("defaultViolation").className ='showClass';
  		document.getElementById("defaultViolation1").className ='hideClass';
  		document.getElementById("defaultViolation2").className ='hideClass';	
  		
  	}

	else if((violationReason.value==1))
  	{
  		//document.getElementById("dayAbsence").className ='showClass';
  		//document.getElementById("discountOfLeaving").className ='hideClass';
  		//document.getElementById("discountOfLateness").className ='hideClass';
  		document.getElementById("defaultViolation2").className ='showClass';
  		document.getElementById("defaultViolation").className ='hideClass';
  		document.getElementById("defaultViolation1").className ='hideClass';
  	}

  	else if((violationReason.value==2)  )
  	{
  		//document.getElementById("discountOfLeaving").className ='showClass';
  		//document.getElementById("discountOfLateness").className ='hideClass';
  		//document.getElementById("dayAbsence").className ='hideClass';
  		document.getElementById("defaultViolation1").className ='showClass';
  		document.getElementById("defaultViolation").className ='hideClass';
  		document.getElementById("defaultViolation2").className ='hideClass'; 
  	}


  
  	else if(violationReason.value=='null')
  	{
  		//document.getElementById("discountOfLateness").className ='hideClass';
  		//document.getElementById("discountOfLeaving").className ='hideClass';
  		//document.getElementById("dayAbsence").className ='hideClass';
  		document.getElementById("defaultViolation").className ='hideClass';
  		document.getElementById("defaultViolation1").className ='hideClass';
  		document.getElementById("defaultViolation2").className ='hideClass';	
  	}
  }

  

</script>

 <form id="violationForm" name="violationForm" method="POST"	action="<c:url value="/hr/violationForm.html"/>">
 
<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
    
   <input type="hidden"  id="violationId" name="violationId" value="${violationId }"/>
   <tr>
		<td colspan="2">
			<spring:bind path="violation.*">
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
				<td class="helpTitle" colspan="4" nowrap><abc:i18n
					property="hr.header.addViolation" /><fmt:message
					key="hr.header.addViolation" /></td>
			</tr>
			
		    <tr>
		  <c:set var="check"  value=""/>
		  <c:if test="${violationId!=null && violationId!='' }">
					 <c:set var="check"  value="disabled"/>
		</c:if>
	
		  
		        <td colspan="2" nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.code"/>
								<fmt:message key="commons.caption.code"/></td>
					
										
				<td  colspan="2" class="formBodControl">
					<spring:bind path="violation.code">
						<input size="8" maxlength="3" type="text"	name="${status.expression}" value="${status.value}" onchange="fillCode(this);"  ${check}" readonly="readonly"/> 			
					</spring:bind>
					</td>
		     
		  </tr>
		  
		   <tr>
		   
		    <td colspan="2"  nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.name"/>
								<fmt:message key="commons.caption.name"/></td>
			
				<td colspan="2"  class="formBodControl" width="70%">
					<spring:bind path="violation.name">
					 <input size="51" type="text"	name="${status.expression}" value="${status.value}"/> 		
					</spring:bind>
					</td>
		  </tr>
		  
		  
		  <tr>
		   <td colspan="2"  nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.englishName"/>
								<fmt:message key="commons.caption.englishName"/></td>
			
				<td colspan="2"  class="formBodControl" width="70%">
					<spring:bind path="violation.ename">
					 <input size="51"  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/> 				
					</spring:bind>
					</td>
		  </tr>
		  
		  <tr>
		   <td colspan="2" nowrap class="formReq" width="30%">
								<abc:i18n property="hr.caption.calculationMethod"/>
								<fmt:message key="hr.caption.calculationMethod"/></td>
				<td colspan="2"  class="formBodControl" width="70%">
					<spring:bind path="violation.calculationMethod">
					 <select name="${status.expression}" id="${status.expression}" value="${status.value}" >
					<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				
					
					 <c:forEach var="calcMethod"  items="${calculationMethodList}">
					 <option value="${calcMethod.id }" ${calcMethod.id == violation.calculationMethod.id ? 'selected' :'' }>
					 ${ calcMethod.name}
					  </option>
					 </c:forEach>
					
					 </select>
					</spring:bind>
					</td>
		  </tr>
		  
		  
		    <tr >
		   <td colspan="2" nowrap class="formReq" width="30%">
								<abc:i18n property="hr.caption.violationReason"/>
								<fmt:message key="hr.caption.violationReason"/></td>
				<td colspan="2"  class="formBodControl" width="70%">
					<spring:bind path="violation.violationReason">
					 <select name="${status.expression}" id="${status.expression}" value="${status.value}" onchange="showRow()">
					<option value="null"><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				
					
					 <c:forEach var="vioReason"  items="${violationReasonList}">
					 <option value="${vioReason.id }" ${vioReason.id == violation.violationReason.id ? 'selected' :'' }>
					   ${vioReason.name}
					  </option>
					 </c:forEach>
					
					 </select>
					</spring:bind>
					</td>
		  </tr>
		  
		  <c:set var="check" value=""/>
          <c:set var="check1" value=""/>
            <c:set var="check2" value=""/>
          <c:set var="check3" value=""/>
          <c:set var="check4" value=""/>
          <c:set var="check5" value=""/>
       
				<c:if test="${violation.defaultViolation==true && violation.violationReason.id==0 }">
					<c:set var="check" value="checked"/>
				</c:if>
				<c:if test="${violation.defaultViolation==true && violation.violationReason.id==2 }">
					<c:set var="check4" value="checked"/>
				</c:if>
				<c:if test="${violation.defaultViolation==true && violation.violationReason.id==1 }">
					<c:set var="check5" value="checked"/>
				</c:if>
                <c:if test="${violation.discountOfLatenessFromSalary==true}">
					<c:set var="check1" value="checked"/>
				</c:if>

              <c:if test="${violation.discountLeaveEarlyFromSalary==true}">
					<c:set var="check2" value="checked"/>
				</c:if>
               <c:if test="${violation.dayAbsenceWithoutPay==true}">
					<c:set var="check3" value="checked"/>
				</c:if>
		  
		  
		   <c:if test="${violation.violationReason.id==0}">
            <tr  id="defaultViolation" class="showClass" >
           </c:if>
           <c:if test="${violation.violationReason.id!=0}">
		       <tr  id="defaultViolation" class="hideClass" >
             </c:if>
		        <td nowrap class="formReq" width="30%">
								<abc:i18n property="hr.caption.defaultViolation"/>
								<fmt:message key="hr.caption.defaultViolation"/></td>
				<td class="formBodControl" width="70%">
					
				  <input type="checkbox" ${check} name="defaultViolation" value="true" />
				
					</td>
					
				<td nowrap class="formReq" width="30%" >
								<abc:i18n property="hr.caption.discountOfLatenessFromSalary"/>
								<fmt:message key="hr.caption.discountOfLatenessFromSalary"/></td>
				<td class="formBodControl" width="70%">
				  <input type="checkbox" ${check1} name="discountOfLatenessFromSalary" value="true" />
					</td>
				
			</tr>
					
		 
		      <c:if test="${violation.violationReason.id==2}">
            <tr  id="defaultViolation1" class="showClass" >
           </c:if>
           <c:if test="${violation.violationReason.id!=2}">
		       <tr  id="defaultViolation1" class="hideClass" >
             </c:if>
		        <td nowrap class="formReq" width="30%">
								<abc:i18n property="hr.caption.defaultViolation"/>
								<fmt:message key="hr.caption.defaultViolation"/></td>
				<td class="formBodControl" width="70%">
				   <input type="checkbox" ${check4} name="defaultViolation1" value="true" />
					
					</td>
				
					
					

				<td nowrap class="formReq" width="30%" >
								<abc:i18n property="hr.caption.discountOfLeavingEarlyFromSalary"/>
								<fmt:message key="hr.caption.discountOfLeavingEarlyFromSalary"/></td>
				<td class="formBodControl" width="70%">
				  <input type="checkbox"  ${check2} name="discountLeaveEarlyFromSalary" value="true" />
					
			</td>
			</tr>
				
			
		      <c:if test="${violation.violationReason.id==1}">
            <tr  id="defaultViolation2" class="showClass" >
           </c:if>
           <c:if test="${violation.violationReason.id!=1}">
		       <tr  id="defaultViolation2" class="hideClass" >
             </c:if>
		        <td nowrap class="formReq" width="30%">
								<abc:i18n property="hr.caption.defaultViolation"/>
								<fmt:message key="hr.caption.defaultViolation"/></td>
				<td class="formBodControl" width="70%">
				  <input type="checkbox" ${check5} name="defaultViolation2" value="true"/>
					</td>
					
				<td nowrap class="formReq" width="30%">
								<abc:i18n property="hr.caption.dayAbsenceWithoutPay"/>
								<fmt:message key="hr.caption.dayAbsenceWithoutPay"/></td>
				<td class="formBodControl" width="70%">
					
				   <input type="checkbox" ${check3} name="dayAbsenceWithoutPay" value="true" />
					</td>
		   
	</tr>
		  
		  
               <tr id="btn">
							<td colspan="2" align="center">
							<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp; 
								<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
							</td>
						</tr>
						
					
</table>
  </table>
</form>



		
			
<%@ include file="/web/common/includes/footer.jsp" %>

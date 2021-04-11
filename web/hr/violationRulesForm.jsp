<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<script type='text/javascript' src='/Requests/dwr/interface/hrManager.js'></script>
<script type='text/javascript' src='/Requests/dwr/engine.js'></script>
<script type='text/javascript' src='/Requests/dwr/util.js'></script>

<abc:security property=""/><br>


<script language="JavaScript">
function generateRow()
{

    //alert ('inside generaterow');
	y = document.getElementById("counter").value;

	var violationTbl=document.getElementById("violationTbl");
	var violationTimesTbl=document.getElementById("violationTimesTbl");
	var violationResultTbl=document.getElementById("violationResultTbl");
	var financialImpactTbl=document.getElementById("financialImpactTbl");
	var vioPeriodCalcTbl=document.getElementById("vioPeriodCalcTbl");

	var lastRow1 = violationTbl.rows.length;
	var lastRow2 = violationTimesTbl.rows.length;
	var lastRow3 = violationResultTbl.rows.length;
	var lastRow4 = financialImpactTbl.rows.length;
	var lastRow5 = vioPeriodCalcTbl.rows.length;
	

	var titleRow1 =violationTbl.insertRow(lastRow1);
	var titleRow2 =violationTimesTbl.insertRow(lastRow2);
	var titleRow3 =violationResultTbl.insertRow(lastRow3);
	var titleRow4 =financialImpactTbl.insertRow(lastRow4);
	var titleRow5 =vioPeriodCalcTbl.insertRow(lastRow5);

	titleRow1.setAttribute('id','row'+y);
	titleRow2.setAttribute('id','row'+y);
	titleRow3.setAttribute('id','row'+y);
	titleRow4.setAttribute('id','row'+y);
	titleRow5.setAttribute('id','row'+y);
	
	var cell1=titleRow1.insertCell(0);
	var cell2=titleRow2.insertCell(0);
	var cell3=titleRow2.insertCell(1);
	var cell4=titleRow5.insertCell(0);
	var cell5=titleRow5.insertCell(1);
	var cell6=titleRow4.insertCell(0);
	var cell7=titleRow4.insertCell(1);
	var cell8=titleRow4.insertCell(2);
	var cell9=titleRow3.insertCell(0);
	var cell10=titleRow3.insertCell(1);
	
		
	var violation = document.getElementById("violation0");
  	var violationSelect = violation.cloneNode(true);
  	violationSelect.setAttribute('name','violations['+y+'].violation');
  	violationSelect.setAttribute('id', 'violation'+y);
  	violationSelect.selectedIndex = 0;
  	cell1.setAttribute('class','formBodControl');
	cell1.appendChild(violationSelect);
	
	var e2=document.createElement('input');
	e2.setAttribute('type','text');
	e2.setAttribute('size','10');
	e2.setAttribute('name','violations['+y+'].from_count');
	e2.setAttribute('id','from_count'+y);
	
	cell2.setAttribute('class','formBodControl');
	cell2.appendChild(e2);

	var e3=document.createElement('input');
	e3.setAttribute('type','text');
	e3.setAttribute('size','10');
	e3.setAttribute('name','violations['+y+'].to_count');
	e3.setAttribute('id','to_count'+y);
	
	cell3.setAttribute('class','formBodControl');
	cell3.appendChild(e3);

	var e4=document.createElement('input');
	e4.setAttribute('type','text');
	e4.setAttribute('size','10');
	e4.setAttribute('name','violations['+y+'].period');
	e4.setAttribute('id','period'+y);
	cell4.setAttribute('class','formBodControl');
	cell4.appendChild(e4);

	

	var periodType = document.getElementById("periodType0");
  	var periodTypeSelect = periodType.cloneNode(true);
  	periodTypeSelect.setAttribute('name','violations['+y+'].periodType');
  	periodTypeSelect.setAttribute('id', 'periodType'+y);
  	periodTypeSelect.selectedIndex = 0;
  	cell5.setAttribute('class','formBodControl');
	cell5.appendChild(periodTypeSelect);

	var calculationMethod = document.getElementById("calculationMethod0");
  	var calculationMethodSelect = calculationMethod.cloneNode(true);
  	calculationMethodSelect.setAttribute('name','violations['+y+'].calculationMethod');
  	calculationMethodSelect.setAttribute('id', 'calculationMethod'+y);
  	calculationMethodSelect.selectedIndex = 0;
  	cell6.setAttribute('class','formBodControl');
	cell6.appendChild(calculationMethodSelect);

	var e7=document.createElement('input');
	e7.setAttribute('type','text');
	e7.setAttribute('size','10');
	e7.setAttribute('name','violations['+y+'].val');
	e7.setAttribute('id','val'+y);
	cell7.setAttribute('class','formBodControl');
	cell7.appendChild(e7);

	var e8=document.createElement('input');
	e8.setAttribute('type','text');
	e8.setAttribute('size','10');
	e8.setAttribute('name','violations['+y+'].max_val');
	e8.setAttribute('id','max_val'+y);
	cell8.setAttribute('class','formBodControl');
	cell8.appendChild(e8);
	
	var violationResult = document.getElementById("violationResult0");
  	var violationResultSelect = violationResult.cloneNode(true);
  	violationResultSelect.setAttribute('name','violations['+y+'].violationResult');
  	violationResultSelect.setAttribute('id', 'violationResult'+y);
  	violationResultSelect.selectedIndex = 0;
  	cell9.setAttribute('class','formBodControl');
	cell9.appendChild(violationResultSelect);

	var e6=document.createElement('input');
	e6.setAttribute('type','button');
	e6.setAttribute('name','deleteRow'+y);
	//e6.setAttribute('class','button');
	e6.setAttribute('value','<fmt:message key="hr.button.deleteRow"/>');
	e6.setAttribute('onclick','deleteAddedRow()');
	cell10.setAttribute('class','formBodControl');
	cell10.appendChild(e6);
	
	y++;
	document.getElementById("counter").value = y;
	//alert('counter>>>>>>>>>>>>>>'+y);
}

	function deleteAddedRow()
	{
		//alert('inside deleteRow');
		y = document.getElementById("counter").value;
		//alert('counter>>>>>>>>>>>.'+y);
		var tbl = document.getElementById("table1");
		var lastRow = tbl.rows.length;
		var id=document.getElementById("id"+(lastRow-1));
		
		if(id==null ||id=='')
		{
		  var tr =tbl.deleteRow(lastRow-1);
		}

		y--;
		document.getElementById("counter").value = y;
		
	}
	
	
	


var globalIndex;
function deleteMyRow(id,index)
{
	//alert('id'+id);
	//alert('index'+index);
	globalIndex=index;
	//alert('globalIndex>>>>>>'+globalIndex);
	var y = document.getElementById("counter").value;
	var tbl = document.getElementById("table1");
  
	var answer = window.confirm('<fmt:message key="commons.button.confirmDelete"/>');
	if(answer==false)
	{
		return;
	}
	else
	{
		 var deleteBool=false;
      //  hrManager.deleteVacationRules(deleteResult,id);
		var deleteId = document.getElementById("deleteId");	
		 window.location.href='violationRulesForm.html?selectedDeleteId='+deleteId.value;
	}


	function deleteResult(data)
	{
		//alert ('inside delete result');
		//alert ('data>>>>>>'+data);
		var tbl = document.getElementById("table1");
		var lastRow = tbl.rows.length;
		if(data == true){
		//alert('globalIndex>>>>>>>>>>>>>>'+globalIndex);
			var tr =tbl.deleteRow(globalIndex+2);
			
		}
		
	}
	 
	y--;
	document.getElementById("counter").value = y;
	//alert('counter>>>>>>>>>>>>>>'+y);
}


function getData(data)
{
	//alert('globalIndex>>>>>>'+globalIndex);
	document.getElementById("violationName"+globalIndex).value=data.name;
}

</script>

 <form id="violationRulesForm" name="violationRulesForm" method="POST"	action="<c:url value="/hr/violationRulesForm.html"/>">
 
<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
    
   <input type="hidden"  id="violationRulesId" name="violationRulesId" value="${violationRulesId }"/>
   <tr>
		<td colspan="2">
			<spring:bind path="violationRulesCommand.*">
						<c:if test="${not empty status.errorMessages}">
							<div><c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
								</font>
							</c:forEach></div>
						</c:if>
			</spring:bind>
			</td>
  </tr>
  <table>
  <tr>
  <td>
  	<table id="table1"  align="center" width="66%" class="sofT" >
  	
  	<c:set var="counterVar" value="0" />
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="6" nowrap><abc:i18n
					property="hr.header.violationRules" /><fmt:message
					key="hr.header.violationRules" /></td>
			</tr>
		
			
			<tr>
			
			     <td  nowrap>
			      <table id="violationTbl" rules="all" border="1">
				 <tr height="20">
				  <td  nowrap class="formReq" width="30%">
								<abc:i18n property="hr.caption.violation"/>
								<fmt:message key="hr.caption.violation"/></td>
					</tr>
					<tr height="25">
					  <td class="formReq" width="70%">
					  </td>
					</tr>
					
					<c:forEach items="${violationRulesCommand.violations}" var="violation" varStatus="loop">
		
					<tr>
					<td class="formBodControl" width="70%">
					   <spring:bind path="violationRulesCommand.violations[${loop.index}].violation">
					  <select  id="violation${loop.index}" name="${status.expression}" value="${status.value}" onchange="">
					<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				 
					
					 <c:forEach var="vac"  items="${violationList}" varStatus="loop2">
					 <option value="${vac.id }" ${vac.id == violationRulesCommand.violations[loop.index].violation.id ? 'selected' :'' }>
					  ${ vac.name}
					  </option>
					 </c:forEach>
					
					 </select> 		
					</spring:bind>
					
					</td>
					
					</tr>
					</c:forEach>
					</table>
					</td>			
				
				
				 <td  nowrap >
				 <table id="violationTimesTbl" rules="all" border="1">
				 <tr height="25">
				 <td class="formReq" width="30%" colspan="2">
				<abc:i18n property="hr.caption.violationTimes"/>
				<fmt:message key="hr.caption.violationTimes"/>
				</td>
				
				</tr>
					<tr>
					    <td  nowrap class="formReq" width="30%">
						<abc:i18n property="common.caption.from"/>
						<fmt:message key="common.caption.from"/></td>
						
						 <td  nowrap class="formReq" width="30%">
						<abc:i18n property="common.caption.to"/>
						<fmt:message key="common.caption.to"/></td>
					</tr>
					
					<c:forEach items="${violationRulesCommand.violations}" var="violation" varStatus="loop">
		
					<tr>
						  <td class="formBodControl" width="70%">
						<spring:bind path="violationRulesCommand.violations[${loop.index}].from_count">
						 <input size="10" type="text"  	name="${status.expression}" value="${status.value }"/>
						 </spring:bind>
						</td>
						
						<td class="formBodControl" width="70%">
						<spring:bind path="violationRulesCommand.violations[${loop.index}].to_count">
						 <input size="10" type="text"  	name="${status.expression}" value="${status.value }"/>
						 </spring:bind>
						</td>
					</tr>
				</c:forEach>
				</table>
				
				
				</td>
				
				
				 <td  nowrap >
				 <table id="vioPeriodCalcTbl" rules="all" border="1">
				 <tr height="25">
				 <td class="formReq" width="30%" colspan="2">
				<abc:i18n property="hr.caption.violationPeriodCalculation"/>
				<fmt:message key="hr.caption.violationPeriodCalculation"/>
				</td>
				
				</tr>
					<tr>
					    <td  nowrap class="formReq" width="30%">
						<abc:i18n property="hr.caption.numOfMonths"/>
						<fmt:message key="hr.caption.numOfMonths"/></td>
						
						 <td  nowrap class="formReq" width="30%">
						<abc:i18n property="hr.caption.periodType"/>
						<fmt:message key="hr.caption.periodType"/></td>
					</tr>
					
					<c:forEach items="${violationRulesCommand.violations}" var="violation" varStatus="loop">
		
					<tr>
						  <td class="formBodControl" width="70%">
						<spring:bind path="violationRulesCommand.violations[${loop.index}].period">
						 <input size="10" type="text" 	name="${status.expression}" value="${status.value }"/>
						 </spring:bind>
						</td>
						
						<td class="formBodControl" width="70%">
						<spring:bind path="violationRulesCommand.violations[${loop.index}].periodType">
						 <select  id="periodType${loop.index}" name="${status.expression}" value="${status.value}" onchange="">
					<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				 
					
					 <c:forEach var="per"  items="${periodTypeList}" varStatus="loop2">
					 <option value="${per.id }" ${per.id == violationRulesCommand.violations[loop.index].periodType.id ? 'selected' :'' }>
					  ${ per.name}
					  </option>
					 </c:forEach>
					
					 </select>
						 </spring:bind>
						</td>
					</tr>
				</c:forEach>
				</table>
				
				
				</td>
				
				
				 <td  nowrap >
				 <table id="financialImpactTbl" rules="all" border="1">
				 <tr height="25">
				 <td  class="formReq" width="30%" colspan="3">
				<abc:i18n property="hr.caption.financialImpact"/>
				<fmt:message key="hr.caption.financialImpact"/>
				</td>
				
				</tr>
					<tr>
					    <td  nowrap class="formReq" width="30%">
						<abc:i18n property="hr.caption.deductionFromPay"/>
						<fmt:message key="hr.caption.deductionFromPay"/></td>
						
						 <td  nowrap class="formReq" width="30%">
						<abc:i18n property="hr.caption.value"/>
						<fmt:message key="hr.caption.value"/></td>
						
						<td  nowrap class="formReq" width="30%">
						<abc:i18n property="hr.caption.maxValue"/>
						<fmt:message key="hr.caption.maxValue"/></td>
						
					</tr>
					
					<c:forEach items="${violationRulesCommand.violations}" var="violation" varStatus="loop">
		
					<tr>
						
						
						<td class="formBodControl" width="70%">
						<spring:bind path="violationRulesCommand.violations[${loop.index}].calculationMethod">
						 <select  id="calculationMethod${loop.index}" name="${status.expression}" value="${status.value}" onchange="">
					<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				 
					
					 <c:forEach var="calc"  items="${calculationMethodList}" varStatus="loop2">
					 <option value="${calc.id }" ${calc.id == violationRulesCommand.violations[loop.index].calculationMethod.id ? 'selected' :'' }>
					  ${ calc.name}
					  </option>
					 </c:forEach>
					
					 </select>
						 </spring:bind>
						</td>
						
						
						  <td class="formBodControl" width="70%">
						<spring:bind path="violationRulesCommand.violations[${loop.index}].val">
						 <input size="10" type="text"  	name="${status.expression}" value="${status.value }"/>
						 </spring:bind>
						</td>
						
						  <td class="formBodControl" width="70%">
						<spring:bind path="violationRulesCommand.violations[${loop.index}].max_val">
						 <input size="10" type="text"  	name="${status.expression}" value="${status.value }"/>
						 </spring:bind>
						</td>
					</tr>
				</c:forEach>
				</table>
				
				
				</td>
				
				<td  nowrap >
				 <table id="violationResultTbl" rules="all" border="1">
				 <tr >
				 <td class="formReq" width="30%" colspan="6">
				<abc:i18n property="hr.caption.violationResult"/>
				<fmt:message key="hr.caption.violationResult"/>
				</td>
				
				</tr>
					<tr height="21">
					    <td  nowrap class="formReq" width="30%" colspan="6">
						</td>
						
						
						
					</tr>
					
					<c:forEach items="${violationRulesCommand.violations}" var="violation" varStatus="loop">
		
					<tr>
						
						
						<td class="formBodControl" width="70%">
						<spring:bind path="violationRulesCommand.violations[${loop.index}].violationResult">
					  <select  id="violationResult${loop.index}" name="${status.expression}" value="${status.value}" onchange="">
					<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				 
					
					 <c:forEach var="vioResult"  items="${violationResultList}" varStatus="loop2">
					 <option value="${vioResult.id }" ${vioResult.id == violationRulesCommand.violations[loop.index].violationResult.id ? 'selected' :'' }>
					 ${ vioResult.name}
					  </option>
					 </c:forEach>
					
					 </select> 		
					</spring:bind>
						</td>
						
				
					
					
					
					<input size="20" type="hidden"  id="deleteId"	name="deleteId" value="${violationRulesCommand.violations[loop.index].id}"/>
					
					
						<td nowrap="nowrap" align="center" class="formBodControl" width="30%">
								<c:if test="${(violationRulesCommand.violations[loop.index].id == null || violationRulesCommand.violations[loop.index].id=='') && (counterVar!=0 )}">
								<input type="button" name="delete" value="<fmt:message key="hr.button.deleteRow"/>"  onclick="deleteAddedRow()" />&nbsp;&nbsp;&nbsp;
								</c:if>
								<c:if test="${violationRulesCommand.violations[loop.index].id !=null && violationRulesCommand.violations[loop.index].id!=''}">
								<input type="button" name="delete" value="<fmt:message key="commons.button.delete"/>"  onclick="deleteMyRow(${violationRulesCommand.violations[loop.index].id},${loop.index });" />&nbsp;&nbsp;&nbsp;	
								</c:if>
								</td>	
									
						
		<INPUT type="hidden" value="${violation.id}"
										name="id${loop.index}" id="id${loop.index}" />
			
		 
        	<c:set var="counterVar" value="${counterVar + 1}" />
					</tr>
					
				</c:forEach>
				</table>
				
				
				</td>
				
				 
			
			
		
				
				
		</tr>
		 
		
					
		<input type="hidden" size=20 maxlength=40 name="counter" id="counter" value="${counterVar}" />
						
			</table>	
			</td>	
		  </tr>
		  <tr>
		  <td>
		  <table>
               <tr id="btn">
              			
							<td colspan="2" align="center">
							 <input type="button" name="addRow" value="<fmt:message key="commons.button.add"/>" class="button" onclick="generateRow();" />&nbsp;&nbsp;&nbsp;
			
							<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp; 
								<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
							</td>
						</tr>
		</table>
		</td>				
		</tr>			
</table>



</table>

	</form>	
			
<%@ include file="/web/common/includes/footer.jsp" %>

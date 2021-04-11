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
	var tbl = document.getElementById("table1");
	var lastRow = tbl.rows.length;
	var titleRow =tbl.insertRow(lastRow);
	
	
	titleRow.setAttribute('id','row'+y);

	var cell1=titleRow.insertCell(0);
	var cell2=titleRow.insertCell(1);
	var cell3=titleRow.insertCell(2);
	var cell4=titleRow.insertCell(3);
	var cell5=titleRow.insertCell(4);
	var cell6=titleRow.insertCell(5);
	var cell7=titleRow.insertCell(6);
	var cell8=titleRow.insertCell(7);
		
	var vacation = document.getElementById("vacation0");
  	var vacationSelect = vacation.cloneNode(true);
  	vacationSelect.setAttribute('name','vacations['+y+'].vacation');
  	vacationSelect.setAttribute('id', 'vacation'+y);
  	vacationSelect.setAttribute('onchange','addVacationName(this.value,'+y+')');
  	vacationSelect.selectedIndex = 0;
  	cell1.setAttribute('class','formBodControl');
	cell1.appendChild(vacationSelect)
	
	var e2=document.createElement('input');
	e2.setAttribute('type','text');
	e2.setAttribute('readonly','readonly');
	e2.setAttribute('size','20');
	e2.setAttribute('name','vacationName'+y);
	e2.setAttribute('id','vacationName'+y);
	
	cell2.setAttribute('class','formBodControl');
	cell2.appendChild(e2);

	var e3=document.createElement('input');
	e3.setAttribute('type','text');
	e3.setAttribute('size','10');
	e3.setAttribute('name','vacations['+y+'].serviceLength');
	e3.setAttribute('id','serviceLength'+y);
	
	cell3.setAttribute('class','formBodControl');
	cell3.appendChild(e3);

	var e4=document.createElement('input');
	e4.setAttribute('type','text');
	e4.setAttribute('size','10');
	e4.setAttribute('name','vacations['+y+'].due');
	e3.setAttribute('id','due'+y);
	cell4.setAttribute('class','formBodControl');
	cell4.appendChild(e4);

	var e6=document.createElement('input');
	e6.setAttribute('type','button');
	e6.setAttribute('name','deleteRow'+y);
	e6.setAttribute('class','button');
	e6.setAttribute('value','<fmt:message key="hr.button.deleteRow"/>');
	e6.setAttribute('onclick','deleteAddedRow()');
	cell5.setAttribute('class','formBodControl');
	cell5.appendChild(e6);

	
	
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
		 window.location.href='vacationRulesForm.html?selectedDeleteId='+deleteId.value;
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

function addVacationName(id,index)
{
	globalIndex=index;
	hrManager.getVacation(getData,id)
}

function getData(data)
{
	//alert('globalIndex>>>>>>'+globalIndex);
	document.getElementById("vacationName"+globalIndex).value=data.name;
}

</script>

 <form id="vacationRulesForm" name="vacationRulesForm" method="POST"	action="<c:url value="/hr/vacationRulesForm.html"/>">
 
<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
    
   <input type="hidden"  id="vacationRulesId" name="vacationRulesId" value="${vacationRulesId }"/>
   <tr>
		<td colspan="2">
			<spring:bind path="vacationRulesCommand.*">
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
					property="hr.header.vacationRules" /><fmt:message
					key="hr.header.vacationRules" /></td>
			</tr>
		
			
			<tr>
			
			     <td  nowrap class="formReq" width="30%">
								<abc:i18n property="hr.caption.vacationCode"/>
								<fmt:message key="hr.caption.vacationCode"/></td>
								
				 <td  nowrap class="formReq" width="30%">
				<abc:i18n property="hr.caption.vacationName"/>
				<fmt:message key="hr.caption.vacationName"/></td>
				
				 <td  nowrap class="formReq" width="30%">
				<abc:i18n property="hr.caption.serviceLength"/>
				<fmt:message key="hr.caption.serviceLength"/></td>
				
				
				 <td  nowrap class="formReq" width="30%">
				<abc:i18n property="hr.caption.due"/>
				<fmt:message key="hr.caption.due"/></td>
				
				<td  nowrap class="formReq" width="30%">
				</td>
				
				<td  nowrap class="formReq" width="30%">
				</td>
				
				
		</tr>
		  
		  <c:forEach items="${vacationRulesCommand.vacations}" var="vacation" varStatus="loop">
		   <tr>
		   
		 
				<td class="formBodControl" width="70%">
				<INPUT type="hidden" value="${vacation.id}"
										name="id${loop.index}" id="id${loop.index}" />
				<spring:bind path="vacationRulesCommand.vacations[${loop.index}].vacation">
					  <select  id="vacation${loop.index}" name="${status.expression}" value="${status.value}" onchange="addVacationName(this.value,${loop.index });">
					<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				 
					
					 <c:forEach var="vac"  items="${vacationList}" varStatus="loop2">
					 <option value="${vac.id }" ${vac.id == vacationRulesCommand.vacations[loop.index].vacation.id ? 'selected' :'' }>
					  ${ vac.code}-${ vac.name}
					  </option>
					 </c:forEach>
					
					 </select> 		
					</spring:bind>
					</td>
					
					
					<td class="formBodControl" width="70%">
				    <input size="20" type="text" readonly id="vacationName${loop.index }"	name="vacationName${loop.index }" value="${vacationRulesCommand.vacations[loop.index].vacation.name}"/> 		
				   </td>
					<input size="20" type="hidden"  id="deleteId"	name="deleteId" value="${vacationRulesCommand.vacations[loop.index].id}"/>
					<td class="formBodControl" width="70%">
					<spring:bind path="vacationRulesCommand.vacations[${loop.index}].serviceLength">
					 <input size="10" type="text"  	name="${status.expression}" value="${status.value }"/>
					 </spring:bind>
					</td>
					
					<td class="formBodControl" width="70%">
					<spring:bind path="vacationRulesCommand.vacations[${loop.index}].due">
					 <input size="10" type="text"  	name="${status.expression}" value="${status.value }"/>
					 </spring:bind>
					</td>
					
					<c:if test="${vacationRulesCommand.vacations[loop.index].id !=null && vacationRulesCommand.vacations[loop.index].id!=''}">
								<td nowrap="nowrap" align="center" class="formBodControl" width="30%">
								<input type="button" name="delete" value="<fmt:message key="commons.button.delete"/>" class="button" onclick="deleteMyRow(${vacationRulesCommand.vacations[loop.index].id},${loop.index });" />&nbsp;&nbsp;&nbsp;
								</td>	
								</c:if>	
								
						<c:if test="${vacationRulesCommand.vacations[loop.index].id ==null ||vacationRulesCommand.vacations[loop.index].id==''}">
								<td nowrap="nowrap" align="center" class="formBodControl" width="30%">
								<input type="button" name="delete" value="<fmt:message key="hr.button.deleteRow"/>" class="button" onclick="deleteAddedRow()" />&nbsp;&nbsp;&nbsp;
								</td>	
									
						</c:if>
		  </tr>
		 
        	<c:set var="counterVar" value="${counterVar + 1}" />
		</c:forEach>
					
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

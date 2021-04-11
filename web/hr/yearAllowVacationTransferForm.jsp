<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<script type='text/javascript' src='/Requests/dwr/interface/hrManager.js'></script>
<script type='text/javascript' src='/Requests/dwr/engine.js'></script>
<script type='text/javascript' src='/Requests/dwr/util.js'></script>

<abc:security property=""/><br>

<!--  <STYLE type='text/css'>
textarea {overflow-x: hidden; overflow-y: scroll}
</STYLE>-->
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
		
	
	
	var e2=document.createElement('input');
	e2.setAttribute('type','text');
	e2.setAttribute('size','10');
	e2.setAttribute('name','year'+y);
	
	cell1.setAttribute('class','formBodControl');
	cell1.appendChild(e2);

	var e6=document.createElement('input');
	e6.setAttribute('type','button');
	e6.setAttribute('name','deleteRow'+y);
	e6.setAttribute('class','button');
	e6.setAttribute('value','<fmt:message key="hr.button.deleteRow"/>');
	e6.setAttribute('onclick','deleteAddedRow()');
	cell2.setAttribute('class','formBodControl');
	cell2.appendChild(e6);
	
	
	y++;
	document.getElementById("counter").value = y;
	//alert('counter>>>>>>>>>>>>>>'+y);
}

	function deleteAddedRow()
	{
		//alert('inside deleteRow');
		y = document.getElementById("counter").value;
		alert('counter>>>>>>>>>>>.'+y);
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

			
			
			  hrManager.deleteYear(deleteResult,id);
			

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
	alert('counter>>>>>>>>>>>>>>'+y);
}

</script>

 <form id="yearAllowVacationTransferForm" name="yearAllowVacationTransferForm" method="POST"	action="<c:url value="/hr/yearAllowVacationTransferForm.html"/>">
 
<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
    
   <input type="hidden"  id="yearAllowVacationTransferId" name="yearAllowVacationTransferId" value="${yearAllowVacationTransferId }"/>
   <tr>
		<td colspan="2">
			<spring:bind path="yearCommand.*">
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
				<td class="helpTitle" colspan="3" nowrap><abc:i18n
					property="hr.header.yearsNotAllowedToMigrateVacationsToItFromPreviousYear" /><fmt:message
					key="hr.header.yearsNotAllowedToMigrateVacationsToItFromPreviousYear" /></td>
			</tr>
		
			
			<tr>
			
			     <td colspan="3" nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.year"/>
								<fmt:message key="commons.caption.year"/></td>
			</tr>
		  
		  <c:forEach items="${yearCommand.years}" var="year" varStatus="loop">
		   <tr>
		   <INPUT type="hidden" value="${year.id}"
										name="id${loop.index}" id="id${loop.index}" />
		 
			
				<td class="formBodControl" width="70%">
					<spring:bind path="yearCommand.years[${loop.index}].year">
					 <input size="10" type="text"	name="year${loop.index }" value="${status.value}"/> 		
					</spring:bind>
					</td>
					
					<c:if test="${yearCommand.years[loop.index].id !=null && yearCommand.years[loop.index].id!=''}">
								<td nowrap="nowrap" align="center" class="formBodControl" width="30%">
								<input type="button" name="delete" value="<fmt:message key="commons.button.delete"/>" class="button" onclick="deleteMyRow(${yearCommand.years[loop.index].id},${loop.index });" />&nbsp;&nbsp;&nbsp;
								</td>	
								</c:if>	
								
						<c:if test="${yearCommand.years[loop.index].id ==null ||yearCommand.years[loop.index].id==''}">
								<td nowrap="nowrap" align="center" class="formBod" width="30%">
								<input type="button" name="delete" value="<fmt:message key="commons.button.delete"/>" class="button" onclick="deleteMyRow(${yearCommand.years[loop.index].id},${loop.index });" />&nbsp;&nbsp;&nbsp;
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
		<td>				
		</tr>			
</table>



</table>

	</form>	
			
<%@ include file="/web/common/includes/footer.jsp" %>

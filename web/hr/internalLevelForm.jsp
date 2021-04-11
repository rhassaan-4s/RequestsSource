<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<script type='text/javascript' src='/Requests/dwr/interface/hrManager.js'></script>
<script type='text/javascript' src='/Requests/dwr/engine.js'></script>
<script type='text/javascript' src='/Requests/dwr/util.js'></script>
<abc:security property="76" />
<script type="text/javascript"><!--
function detectForm()
{
	//alert('inside onsubmitform');
	var formType=document.forms[0].elements['formType'].value;
  if(formType == 'internal')
  {
   document.certificate.action ="internalLevelForm.html";
  }
 else if(formType == 'geographical')
  {
    document.certificate.action ="geographicalLevelForm.html";
  }
 else if(formType == 'qualification')
 {
   document.certificate.action ="qualificationLevelForm.html";
 }
 else if(formType == 'specialty')
 {
   document.certificate.action ="specialtyLevelForm.html";
 }
  
}
function generateRow()
{
	//alert ('inside generaterow');
	y = document.getElementById("counter").value;
	var formType=document.forms[0].elements['formType'].value;
	var tbl = document.getElementById("table1");
	var lastRow = tbl.rows.length;
	if(y<10)
	{
	var titleRow =tbl.insertRow(lastRow);
	
	/*for(i=0; i<document.forms[0].elements.length; i++){
		   alert(document.forms[0].elements[i].name);
		   alert(document.forms[0].elements[i].value);
		   }*/
	
	titleRow.setAttribute('id','row'+y);

	var cell1=titleRow.insertCell(0);
	var cell2=titleRow.insertCell(1);
	var cell3=titleRow.insertCell(2);
	var cell4= titleRow.insertCell(3);
	var cell5= titleRow.insertCell(4);
	var cell6=titleRow.insertCell(5);
		
	cell1.innerHTML ="<fmt:message key='commons.caption.name'/>&nbsp;";
	cell1.setAttribute('class','formBod');
	cell1.setAttribute('width','30%');
	cell1.setAttribute('nowrap','nowrap');
	var e1=document.createElement('input');
	e1.setAttribute('type','text');
	e1.setAttribute('size','20');
	e1.setAttribute('name','name'+y);
	
	cell2.setAttribute('class','formBodControl');
	cell2.appendChild(e1);
	
	cell3.innerHTML ="<fmt:message key='commons.caption.englishName'/> &nbsp;"
	cell3.setAttribute('class','formBod');
	cell3.setAttribute('width','30%');
	cell3.setAttribute('nowrap','nowrap');
	var e2=document.createElement('input');
	e2.setAttribute('type','text');
	e2.setAttribute('size','20');
	e2.setAttribute('name','ename'+y);
	
	cell4.setAttribute('class','formBodControl');
	cell4.appendChild(e2);

	cell5.innerHTML ="<fmt:message key='hr.caption.rank'/> &nbsp;"
		cell5.setAttribute('class','formBod');
		cell5.setAttribute('width','30%');
		cell5.setAttribute('nowrap','nowrap');
		var e3=document.createElement('input');
		e3.setAttribute('type','text');
		e3.setAttribute('size','5');
		e3.setAttribute('name','levelNo'+y);
		
		cell6.setAttribute('class','formBodControl');
		cell6.appendChild(e3);


		if(formType =='internal')
		{
			var cell7= titleRow.insertCell(6);
			var cell8= titleRow.insertCell(7);
		
		    cell7.innerHTML ="<fmt:message key='hr.caption.hasFunctionalGroup'/> &nbsp;"
			cell7.setAttribute('class','formBod');
			cell7.setAttribute('width','30%');
			cell7.setAttribute('nowrap','nowrap');
			var e4=document.createElement('input');
			e4.setAttribute('type','checkbox');
			e4.setAttribute('name','FG'+y);
			
			cell8.setAttribute('class','formBodControl');
			cell8.appendChild(e4);
		}
		else if(formType =='geographical')
          {//alert('formType geographical');
               
			var cell9= titleRow.insertCell(6);
			var cell10= titleRow.insertCell(7);
		
			    cell9.innerHTML ="<fmt:message key='hr.caption.isNationalityCountry'/> &nbsp;"
				cell9.setAttribute('class','formBod');
				cell9.setAttribute('width','30%');
				cell9.setAttribute('nowrap','nowrap');
				var e5=document.createElement('input');
				e5.setAttribute('type','checkbox');
				e5.setAttribute('name','NC'+y);
				
				cell10.setAttribute('class','formBodControl');
				cell10.appendChild(e5);
          }

		var e6=document.createElement('input');
		e6.setAttribute('type','button');
		e6.setAttribute('name','deleteRow'+y);
		e6.setAttribute('class','button');
		e6.setAttribute('value','<fmt:message key="hr.button.deleteRow"/>');
		e6.setAttribute('onclick','deleteAddedRow()');
		if(formType =='geographical' || formType =='internal')
		{
		 var cell11= titleRow.insertCell(8);
		 cell11.setAttribute('class','formBodControl');
			cell11.appendChild(e6);
		  
		}
		else
		{
			var cell11= titleRow.insertCell(6);
			 cell11.setAttribute('class','formBodControl');
				cell11.appendChild(e6);
				
		}
		
	
	y++;
	document.getElementById("counter").value = y;
	}
	
	
	
}
var globalIndex;
function deleteMyRow(id,index)
{
	//alert('id'+id);
	//alert('index'+index);
	globalIndex=index;
	//alert('globalIndex>>>>>>'+globalIndex);
	var y = document.getElementById("counter").value;
	var formType=document.forms[0].elements['formType'].value;
	var tbl = document.getElementById("table1");
	var lastRow = tbl.rows.length;
  
	var answer = window.confirm('<fmt:message key="commons.button.confirmDelete"/>');
	if(answer==false)
	{
		return;
	}
	else
	{
		 var deleteBool=false;

			if(formType=='internal')
			{
				//alert('inside internal');
				var deleteId = document.getElementById("myDeleteId");	
		     window.location.href='internalLevelForm.html?selectedDeleteId='+id;
		
			}
			if(formType=='geographical')
			{//alert('inside geogr');
				var deleteId = document.getElementById("deleteId");	
				 window.location.href='geographicalLevelForm.html?selectedDeleteId='+id;

			}
			if(formType=='qualification')
			{
				var deleteId = document.getElementById("deleteId");	
				 window.location.href='qualificationLevelForm.html?selectedDeleteId='+id;

			}
			if(formType=='specialty')
			{
				var deleteId = document.getElementById("deleteId");	
				 window.location.href='specialtyLevelForm.html?selectedDeleteId='+id;

			}
			if(deleteBool==true)
			{
			  var tr =tbl.deleteRow(lastRow-1);
			}

	}

	function deleteResult(data)
	{
		//alert ('inside delete result');
		//alert ('data>>>>>>'+data);
		var tbl = document.getElementById("table1");
		var lastRow = tbl.rows.length;
		if(data == true){
		//alert('inside data==true');
			var tr =tbl.deleteRow(globalIndex);
		}
		
	}
	 
	y--;
	document.getElementById("counter").value = y;
	
	}

function deleteAddedRow()
{
	//alert('inside deleteRow');
	y = document.getElementById("counter").value;
	//alert(y);
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

/*function init(){
if(document.levels.canEdit.value=='false'){document.levels.noOfLevels1.disabled=true;
document.levels.but1.disabled=true;
}
	document.certificate.noOfLevels.value=document.levels.noOfLevels1.value;

}
window.onload=init;*/
</script>
<form id="certificate" name="certificate" method="POST"	action="<c:url value=""/>" onLoad="return init();">
					
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right:10px ">


	<tr>
		<td colspan="2">
				<table  align="center" width="100%" class="sofT" >
			<tr id="head_1_ep">
		<td class="helpTitle" colspan="2" nowrap>	
			<c:if test="${formType=='internal'}">
		     <fmt:message
			key="hr.header.internalDivisionTree" />
			</c:if>
		 <c:if test="${formType=='qualification'}">
			<fmt:message
			key="hr.header.qualificationDivisionTree" />
		 </c:if>
		 <c:if test="${formType=='geographical'}">
	       <fmt:message
			key="hr.header.geographicalDivisionTree" />
		</c:if>
		 <c:if test="${formType=='specialty'}">
	       <fmt:message
			key="hr.header.specialtyDivisionTree" />
		</c:if>
		
		</td>
			</tr>
		
				
				<tr>
							
					<td colspan="2">
					
					
					
						<spring:bind path="levelsCommand.*">
							<c:if test="${not empty status.errorMessages}">
								<div><c:forEach var="error" items="${status.errorMessages}">
									<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
									</font>
								</c:forEach></div>
							</c:if>
						</spring:bind>
					</td>	
						
				<INPUT type="hidden" size="20" name="noOfLevels" value=""/>
				<INPUT type="hidden" size="20" name="formType" value="${formType}"/>
				
				<TABLE id="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<c:set var="counterVar" value="0" />
			       <c:if test="${edit == null || edit==''}">
					<c:forEach var="lvl"  items="${levelsCommand.levels}"
							varStatus="loop">
							<tr id="levelDatas${loop.index}">
									<INPUT type="hidden" value="${lvl.id}"
										name="id${loop.index}" id="id${loop.index}" />
										
											<INPUT type="hidden" value="${levelsCommand.levels[loop.index].id}"
										name="myDeleteId" id="myDeleteId" />
								<td  nowrap="nowrap" align="center" class="formBod" width="30%">
								
								<fmt:message key='commons.caption.name'/> &nbsp;
								</td>
								<td align="center" class="formBodControl">
							    <spring:bind path="levelsCommand.levels[${loop.index}].name">
								<INPUT type="text" size="20" value="${status.value }"
									name="name${loop.index}" /> 
								</spring:bind>	
									
								</td>
								
								<td nowrap="nowrap" align="center" class="formBod" width="30%">
								  <fmt:message key='commons.caption.englishName'/> &nbsp;
								</td>
								
								<td nowrap="nowrap" align="center" class="formBodControl">
								<spring:bind path="levelsCommand.levels[${loop.index}].ename">
								  <INPUT type="text" size="20" value="${status.value}"
									name="ename${loop.index}" />
								</spring:bind>
								</td>
								
								<td nowrap="nowrap" align="center" class="formBod" width="30%">
								  <fmt:message key='hr.caption.rank'/> &nbsp;
								</td>
								
								<td align="center" class="formBodControl">
								<spring:bind path="levelsCommand.levels[${loop.index}].levelNo">
								  <INPUT type="text" size="5" value="${status.value}"
									name="levelNo${loop.index}" />
								</spring:bind>
								</td>
								<c:if test="${formType=='internal'}">
								<td nowrap="nowrap" align="center" class="formBod" width="30%">
								<fmt:message key='hr.caption.hasFunctionalGroup'/>&nbsp;
								</td>
								<td align="center" class="formBodControl">
								<spring:bind path="levelsCommand.levels[${loop.index}].hasFunctionalGroup">
								<input type="checkbox" name="FG${loop.index}"  ${lvl.hasFunctionalGroup == true ? ' checked':""}>
				                 </spring:bind>
								</td>
								</c:if>
								
								<c:if test="${formType=='geographical'}">
								<td nowrap="nowrap" align="center" class="formBod" width="30%">
								<fmt:message key='hr.caption.isNationalityCountry'/>&nbsp;
								</td>
								<td align="center" class="formBodControl">
								<spring:bind path="levelsCommand.levels[${loop.index}].isNationalityCountry">
								<input type="checkbox" name="NC${loop.index}" id="${status.expression }" ${lvl.isNationalityCountry == true ? ' checked':""}>
				                 </spring:bind>
								</td>
								</c:if>	
								<c:if test="${levelsCommand.levels[loop.index].id !=null ||levelsCommand.levels[loop.index].id!=''}">
								<td nowrap="nowrap" align="center" class="formBod" width="30%">
								<input type="button" name="delete" value="<fmt:message key="commons.button.delete"/>" class="button" onclick="deleteMyRow(${levelsCommand.levels[loop.index].id},${loop.index });" />&nbsp;&nbsp;&nbsp;
								</td>	
								</c:if>																
							</tr>
							
							<c:set var="counterVar" value="${counterVar + 1}" />
						</c:forEach>
						</c:if>
						<input type="hidden" size=20 maxlength=40 name="counter" id="counter" value="${counterVar}" />
						
					
				
				
					
				</TABLE>
				<table align="center">
				<tr id="btn">
						<td colspan=5 align=center>
						<input type="button" name="addRow" value="<fmt:message key="hr.button.addRow"/>" class="button" onclick="generateRow();" />&nbsp;&nbsp;&nbsp;
						<!-- <input type="button" name="deleteRow" value="<fmt:message key="hr.button.deleteRow"/>" class="button" onclick="deleteAddedRow();" />&nbsp;&nbsp;&nbsp; -->
							<abc:i18n property="commons.button.save" />	
							<input type="submit" name="save" id="save" value="<fmt:message key="commons.button.save"/>" class="button" onclick="detectForm();" />&nbsp;&nbsp;&nbsp; 
							<abc:i18n property="commons.button.cancel" />
							<input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	/>
						</td>
						</tr></table>
				
							
		</tr>
	
	</table>
	</form>			
	
<%@ include file="/web/common/includes/footer.jsp" %>
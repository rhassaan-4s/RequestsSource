<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<abc:security property="79" />
<script type="text/javascript">
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
		
	
	
	var e2=document.createElement('input');
	e2.setAttribute('type','text');
	e2.setAttribute('name','userCode'+y);
	e2.setAttribute('id','userCode'+y);
	
	cell1.setAttribute('class','formBodControl');
	cell1.appendChild(e2);

	var preference = document.getElementById("preference0");
  	var preferenceSelect = preference.cloneNode(true);
  	preferenceSelect.setAttribute('name','preference'+y);
  	preferenceSelect.setAttribute('id', 'preference'+y);
	preferenceSelect.setAttribute('onchange', 'fillCode(this.value);');
  	preferenceSelect.selectedIndex = 0;
  	cell2.setAttribute('class','formBodControl');
	cell2.appendChild(preferenceSelect)
	
	var choice = document.getElementById("choice0");
  	var choiceSelect = choice.cloneNode(true);
  	preferenceSelect.setAttribute('name','choice'+y);
  	preferenceSelect.setAttribute('id', 'choice'+y);
  	preferenceSelect.selectedIndex = 0;
  	cell3.setAttribute('class','formBodControl');
	cell3.appendChild(choiceSelect)
	
	
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
	
	function fillCode(id)
	{
		var y = document.getElementById("counter").value;
	  document.getElementById("userCode"+(y-1)).value=id;
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

function init(){
if(document.levels.canEdit.value=='false'){document.levels.noOfLevels1.disabled=true;
document.levels.but1.disabled=true;
}

	document.certificate.noOfLevels.value=document.levels.noOfLevels1.value;

}
window.onload=init;
</script>
<form id="preferences" name="preferences" method="get">
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right:10px ">


	<tr>
		<td colspan="2">
				<table rules="all" align="center" width="70%" class="sofT" >
			<tr id="head_1_ep">
			<td class="helpTitle" colspan="2" nowrap>
       <c:if test="${type==1}">		
		<fmt:message key="hr.header.systemPreferences" />
		</c:if>
		 <c:if test="${type==2}">		
		<fmt:message key="hr.header.userPreferences" />
		</c:if>
		 <c:if test="${type==4}">		
		<fmt:message key="hr.header.operatingPreferences" />
		</c:if>
			</td>
			</tr>
			<tr>
			<td align="right">
			
			  <spring:bind path="preferences.*">
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
					
					
					<table   border=0 cellspacing=0 cellpadding=0 id="ep"	 width="100%">
						
							<tr align="right">
							
								  <td nowrap class="formReq" width="40%"><abc:i18n property="hr.caption.code" /><fmt:message key="hr.caption.code"/></td>
								<td nowrap class="formReq" width="40%"><abc:i18n property="hr.caption.property" /><fmt:message key="hr.caption.property"/></td>
                                <td nowrap class="formReq" width="40%"><abc:i18n property="hr.caption.choice" /><fmt:message key="hr.caption.choice"/></td>

							</tr>
		
					</table>
					</form>
			<	</td>
				
				</tr>
				<tr>
							
					<td colspan="2">
					<form id="certificate" name="certificate" method="POST"	action="<c:url value="/hr/preferencesForm.html"/>" onLoad="return init();">
						
						
						
				<INPUT type="hidden" size="20" name="type" value="${type}"/>
				
				<TABLE id="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<c:set var="counterVar" value="0" />
						<c:forEach var="pref" items="${preferences.preferences}"
							varStatus="loop">
							<tr id="preferenceDatas${loop.index}">
									<INPUT type="hidden" value="${pref.id}"
										name="id${loop.index}" id="id${loop.index}" />
							 	<td align="center" class="formBod" width="20%">
								<input type="text" id="userCode${loop.index }" name="userCode${loop.index }" value="${pref.property }" />
								</td>
								<td align="center" class="formBod" width="20%">
								<select name="preference${loop.index}" id="preference${loop.index}" onchange="fillCode(this.value);">
								<option value=""><fmt:message key="commons.caption.select" /></option>						
								<c:forEach items="${defienedPreferences}" var="defienedPref" >
									<option value="${defienedPref.id.property}" ${defienedPref.id.property == pref.property ?' selected' : ''}>${defienedPref.id.property} &nbsp; ${defienedPref.descr}</option>
								</c:forEach>
							   </select>			
								</td>
								
								<td align="center" class="formBod" width="20%">
								  <select name="choice${loop.index}" id="choice${loop.index}">
								<option value=""><fmt:message key="commons.caption.select" /></option>						
								<option value="no" ${pref.pvalue == "no"?' selected' : ''}><fmt:message key="commons.caption.no" /></option>
								<option value="yes" ${pref.pvalue == "yes"?' selected' : ''}><fmt:message key="commons.caption.yes" /></option>
							   </select>
								</td>
																										
							</tr>
							<c:set var="counterVar" value="${counterVar + 1}" />
						</c:forEach>
					<input type="hidden" size=20 maxlength=40 name="counter" id="counter" value="${counterVar}" />
					
				</TABLE>
				<table align="center">
				<tr id="btn">
						<td colspan=5 align=center>
						<input type="button" name="addRow" value="<fmt:message key="hr.button.addRow"/>" class="button" onclick="generateRow();" />&nbsp;&nbsp;&nbsp;
						<input type="button" name="deleteRow" value="<fmt:message key="hr.button.deleteRow"/>" class="button" onclick="deleteAddedRow();" />&nbsp;&nbsp;&nbsp;
							<abc:i18n property="commons.button.save" />	
							<input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp; 
							<abc:i18n property="commons.button.cancel" />
							<input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	/>
						</td>
						</tr></table>
				
							
						</form>	
	
<%@ include file="/web/common/includes/footer.jsp" %>
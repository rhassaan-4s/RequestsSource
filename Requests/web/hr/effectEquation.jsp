<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<abc:security property="79" />

<script>
function generateRow(){
	//alert ('inside generaterow');
	
	y = document.getElementById("counter").value;

	var tbl = document.getElementById("table1");
	var lastRow = tbl.rows.length;
	var titleRow =tbl.insertRow(lastRow);
		
	titleRow.setAttribute('id','row'+y);

	var cell1=titleRow.insertCell(0);
	var equationParm = document.getElementById("equationParm");
  	var equationParmSelect = equationParm.cloneNode(true);

  	equationParmSelect.setAttribute('name','equationParm'+y);
  	equationParmSelect.setAttribute('id',y);
  	equationParmSelect.setAttribute('onchange', 'changeField(this.value,this.id);');
  	equationParmSelect.selectedIndex = 0;
  	cell1.setAttribute('class','formBodControl');
	cell1.appendChild(equationParmSelect)
	
	//alert('counter>>>>>>>>>>>>>>'+y);
}

function generateRowBefore(){
    //alert ('inside generaterowbefore');
   
	var y = document.getElementById("counter").value;
	var tbl = document.getElementById("table1");
	var lastRow = tbl.rows.length;
	var titleRow=null;

	alert('lastRow>>>>>'+lastRow);

	if(lastRow >0){
		titleRow =tbl.insertRow((lastRow-1));
	}else{
		//alert(lastRow);
		titleRow =tbl.insertRow((lastRow));
	}
	
	titleRow.setAttribute('id','row'+y);

	var cell1=titleRow.insertCell(0);
	var equationParm = document.getElementById("equationParm");
  	var equationParmSelect = equationParm.cloneNode(true);

  	equationParmSelect.setAttribute('name','equationParm'+y);
  	equationParmSelect.setAttribute('id', y);
  	equationParmSelect.setAttribute('onchange', 'changeField(this.value,this.id);');
  	equationParmSelect.selectedIndex = 0;
  	cell1.setAttribute('class','formBodControl');
	cell1.appendChild(equationParmSelect)
	y++;
	document.getElementById("counter").value = y;
	
	//alert('counter>>>>>>>>>>>>>>'+y);
}

function changeField(field,id){
	var y =id;
	var tbl = document.getElementById("table1");
	var lastRow = document.getElementById("row"+y);
	var cell=lastRow.insertCell(1);

	var e2=document.createElement('input');
		e2.setAttribute('type','text');
		e2.setAttribute('name','number'+y);
		e2.setAttribute('id','number'+y);

	var sign = document.getElementById("sign");
	var signSelect = sign.cloneNode(true);
	  	signSelect.setAttribute('name','sign'+y);
	  	signSelect.setAttribute('id', 'sign'+y);
	  	signSelect.selectedIndex = 0;

	if(field=='number'){
		lastRow.deleteCell(1);

		var cell2=lastRow.insertCell(1);

	 	cell2.innerHTML=e2;
		cell2.setAttribute('class','formBodControl');
		cell2.appendChild(e2);
	}else{
		alert('inside else>>>>>>>>>');	
		lastRow.deleteCell(1);

		var cell2=lastRow.insertCell(1);

	  	cell2.innerHTML=signSelect;
  		cell2.setAttribute('class','formBodControl');
		cell2.appendChild(signSelect)
		alert('end else>>>>>>>>>');	
	}
}

function changeFieldForExistRow(field){
	if(field=='sign'){
		showSign();
	}else{
		hideSign();
	}	
}

function hideSign(){
	var div_sign = document.all("signDiv");

	div_sign.style.visibility = "hidden";

	var div_number = document.all("numberDiv");

	div_number.style.visibility = "visible";
}

function showSign() {
	var div_sign = document.all("signDiv");

	div_sign.style.visibility = "visible";

	var div_number = document.all("numberDiv");

	div_number.style.visibility = "hidden";
}

function deleteAddedRow(){
	//alert('inside deleteRow');
	
	y = document.getElementById("counter").value;

	//alert('counter>>>>>>>>>>>.'+y);
	
	var tbl = document.getElementById("table1");
	var lastRow = tbl.rows.length;
	var id=document.getElementById("id"+(lastRow-1));
	
	if(id==null ||id==''){
	  var tr =tbl.deleteRow(lastRow-1);
	}

	y--;
	document.getElementById("counter").value = y;
}
	
function fillCode(id){
	var y = document.getElementById("counter").value;

	document.getElementById("userCode"+(y-1)).value=id;
}

var globalIndex;

function deleteMyRow(id,index){
	//alert('id'+id);
	//alert('index'+index);
	
	globalIndex=index;

	//alert('globalIndex>>>>>>'+globalIndex);
	var y = document.getElementById("counter").value;
	var tbl = document.getElementById("table1");
	var answer = window.confirm('<fmt:message key="commons.button.confirmDelete"/>');

	if(answer==false){
		return;
	}else{
		var deleteBool=false;

      	//hrManager.deleteVacationRules(deleteResult,id);
		
		var deleteId = document.getElementById("deleteId");	

		window.location.href='vacationRulesForm.html?selectedDeleteId='+deleteId.value;
	}

	function deleteResult(data){
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
</script>

<form id="preferences" name="preferences" method="get">
	<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
		<tr>
			<td colspan="2">
				<table rules="all" align="center" width="70%" class="sofT" >
					<tr id="head_1_ep">
						<td class="helpTitle" colspan="2" nowrap>
      			 			<c:if test="${type=='minValue'}">		
								<fmt:message key="hr.caption.minValue" />
							</c:if>
						 	<c:if test="${type=='maxValue'}">		
								<fmt:message key="hr.caption.maxValue" />
							</c:if>
							<c:if test="${type=='taxDiscount'}">		
								<fmt:message key="hr.header.discountAnnualTax" />
							</c:if>
							<c:if test="${type=='equation'}">		
								<fmt:message key="hr.header.effectEquation" />
							</c:if>
						</td>
					</tr>
					
					<tr>
						<td align="right">
							<spring:bind path="preferences.*">
								<c:if test="${not empty status.errorMessages}">
									<div>
										<c:forEach var="error" items="${status.errorMessages}">
											<font color="red"> <c:out value="${error}" escapeXml="false" />
												<br/>
											</font>
										</c:forEach>
									</div>
								</c:if>
							</spring:bind>
						</td>
					</tr>
			
					<tr id="clonedRow" class="hideClass">
						<td align="center" class="formBod" width="20%">
							<select name="equationParm" id="equationParm" onchange="changeField(this.value);">
								<option value="">
									<fmt:message key="commons.caption.select" />
								</option>						
								<option value="number" ${effEquation.nnn != null?' selected' : ''}>
									<fmt:message key="hr.caption.number" />
								</option>
								<option value="sign" ${ effEquation.operator !=null ?' selected' : ''}>
									<fmt:message key="hr.caption.sign" />
								</option>
						   	</select>
						</td>
						
						<td align="center" class="formBod" width="20%">
							<input type="text" id="number" name="number" />
						</td>
						
						<td align="center" class="formBod" width="20%">
							<select name="sign" id="sign" >
								<option value="">
									<fmt:message key="commons.caption.select" />
								</option>						
								<option value="+" ${effEquation.entry == '+' ?' selected' : ''}>+</option>
								<option value="-" ${effEquation.entry == '-' ?' selected' : ''}>-</option>
								<option value="/" ${effEquation.entry == '/' ?' selected' : ''}>/</option>
								<option value="*" ${effEquation.entry == '*' ?' selected' : ''}>*</option>
						   	</select>			
						</td>
					</tr>

					<tr>
						<td colspan="2">
							<form id="certificate" name="certificate" method="POST"	action="<c:url value="/hr/preferencesForm.html"/>" onLoad="return init();">
								
								<INPUT type="hidden" size="20" name="type" value="${type}"/>
								<INPUT type="hidden" size="20" name="effectId" value="${effectId}"/>
								
								<TABLE id="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
									<c:set var="counterVar" value="0" />
									<c:forEach var="effEquation" items="${preferences.preferences}" varStatus="loop">
										<tr id="effEquationDatas${loop.index}">
											<INPUT type="hidden" value="${effEquation.id}" name="id${loop.index}" id="id${loop.index}" />
											
											<td align="center" class="formBod" width="20%">
												<select name="equationParm${loop.index}" id="equationParm${loop.index}" onchange="changeFieldForExistRow(this.value);">
													<option value="">
														<fmt:message key="commons.caption.select" />
													</option>						
													<option value="number" ${effEquation.nnn != null?' selected' : ''}>
														<fmt:message key="hr.caption.number" />
													</option>
													<option value="sign" ${ effEquation.operator !=null ?' selected' : ''}>
														<fmt:message key="hr.caption.sign" />
													</option>
										  		</select>
											</td>
											
											<div id="numberDiv" class="hideClass">
												<td align="center" class="formBod" width="20%">
													<input type="text" id="number${loop.index }" name="number${loop.index }" />
												</td>
											</div>
											
											<div id="signDiv" class="hideClass">
												<td align="center" class="formBod" width="20%">
													<select name="sign${loop.index}" id="sign${loop.index}" >
														<option value="">
															<fmt:message key="commons.caption.select" />
														</option>						
														<option value="+" ${effEquation.entry == '+' ?' selected' : ''}>+</option>
														<option value="-" ${effEquation.entry == '-' ?' selected' : ''}>-</option>
														<option value="/" ${effEquation.entry == '/' ?' selected' : ''}>/</option>
														<option value="*" ${effEquation.entry == '*' ?' selected' : ''}>*</option>
													</select>			
												</td>
											</div>
										</tr>
										
										<c:set var="counterVar" value="${counterVar + 1}" />
									</c:forEach>
									
									<input type="hidden" size=20 maxlength=40 name="counter" id="counter" value="${counterVar}" />
								</TABLE>
								
								<table align="center">
									<tr id="btn">
										<td colspan=5 align=center>
											<input type="button" name="addRowBefore" value="<fmt:message key="hr.button.addRowBefore"/>" class="button" onclick="generateRowBefore();" />&nbsp;&nbsp;&nbsp;
											<input type="button" name="addRowAfter" value="<fmt:message key="hr.button.addRowAfter"/>" class="button" onclick="generateRow();" />&nbsp;&nbsp;&nbsp;
											<input type="button" name="deleteRow" value="<fmt:message key="hr.button.deleteRow"/>" class="button" onclick="deleteAddedRow();" />&nbsp;&nbsp;&nbsp;
											
											<abc:i18n property="commons.button.save" />	
											<input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp; 
											<abc:i18n property="commons.button.cancel" />
											<input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	/>
										</td>
									</tr>
								</table>
							</form>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>	
</form>	
	
<%@ include file="/web/common/includes/footer.jsp" %>
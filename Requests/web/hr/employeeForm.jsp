<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree"%>

<abc:security property="85"/><br>
<script language="JavaScript" type="text/javascript">
function  textCounter( field, countfield, maxlimit ) {

	  if ( field.value.length > maxlimit )
	  {
	    field.value = field.value.substring( 0, maxlimit );
	    alert( '<fmt:message key="hr.caption.only250CharachtersAreAllowed"/>' );	
	    return false;
	  }
	  else
	  {
	    countfield.value = maxlimit - field.value.length;
	  }
	}

function generateRow()
{
	y = document.getElementById("counter").value;
	maxy = document.getElementById("maxCounter").value;
	var tbl = document.getElementById("kindshipTable");
	var lastRow = tbl.rows.length;
	var tr =tbl.insertRow(lastRow);

	
	tr.setAttribute('id','row'+maxy);
	
	var td1 = tr.insertCell(0);
	td1.setAttribute('align','center');

	var relativeName = document.createElement ("input");
	relativeName.setAttribute('type','text');
	relativeName.setAttribute('name','employeeRelatives['+y+'].relativeName');
	relativeName.setAttribute('id','relativeName'+y);
	relativeName.setAttribute('size','40');
	td1.appendChild(relativeName);

	var td2 = tr.insertCell(1);
	td2.setAttribute('align','center');

	var kinshipType = document.createElement ("input");
	kinshipType.setAttribute('type','text');
	kinshipType.setAttribute('name','employeeRelatives['+y+'].kinshipType');
	kinshipType.setAttribute('id','kinshipType'+y);
	kinshipType.setAttribute('size','40');
	td2.appendChild(kinshipType);

	
	y++;
	document.getElementById("counter").value = y;
	
	maxy++;
	document.getElementById("maxCounter").value = maxy;
	
	
}

function deleteAddedRow()
{
	//alert('inside deleteRow');
	y = document.getElementById("counter").value;
	//alert(y);
	maxy = document.getElementById("maxCounter").value;
	//alert(maxy);
	var tbl = document.getElementById("kindshipTable");
	var lastRow = tbl.rows.length;
	var tr =tbl.deleteRow(lastRow-1);

	y--;
	document.getElementById("counter").value = y;
	
	maxy--;
	document.getElementById("maxCounter").value = maxy;	
}

function getMain(){
    var tabNo=document.getElementById("tabNo1");
  //  alert('before'+tabNo.value);
    tabNo.value=1;
   // alert(tabNo.value);
	document.getElementById("tab_1_contents").className ='showClass';
	document.getElementById("tab_2_contents").className ='hideClass';
	document.getElementById("tab_3_contents").className ='hideClass';
	document.getElementById("tab_4_contents").className ='hideClass';
	document.getElementById("tab_5_contents").className ='hideClass';
	document.getElementById("main").className ='current';
	document.getElementById("tab2").className ='';
	document.getElementById("tab3").className ='';	
	document.getElementById("tab4").className ='';
	document.getElementById("tab5").className ='';

}

function getTab2(){
var tabNo=document.getElementById("tabNo1");
//alert('before'+tabNo.value);
tabNo.value=2;
// alert(tabNo.value);
document.getElementById("tab_2_contents").className ='showClass';
document.getElementById("tab_1_contents").className ='hideClass';
document.getElementById("tab_3_contents").className ='hideClass';
document.getElementById("tab_4_contents").className ='hideClass';
document.getElementById("tab_5_contents").className ='hideClass';
document.getElementById("tab2").className ='current';
document.getElementById("main").className ='';
document.getElementById("tab3").className ='';
document.getElementById("tab4").className ='';
document.getElementById("tab5").className ='';
}

function getTab3(){

var tabNo=document.getElementById("tabNo1");
//alert('before'+tabNo.value);
tabNo.value=3;
//  alert(tabNo.value);
document.getElementById("tab_3_contents").className ='showClass';
document.getElementById("tab_1_contents").className ='hideClass';
document.getElementById("tab_2_contents").className ='hideClass';
document.getElementById("tab_4_contents").className ='hideClass';
document.getElementById("tab_5_contents").className ='hideClass';
document.getElementById("tab3").className ='current';
document.getElementById("main").className ='';
document.getElementById("tab2").className ='';
document.getElementById("tab4").className ='';
document.getElementById("tab5").className ='';
}

function getTab4(){

var tabNo=document.getElementById("tabNo1");
//alert('before'+tabNo.value);
tabNo.value=4;
//  alert(tabNo.value);
document.getElementById("tab_4_contents").className ='showClass';
document.getElementById("tab_3_contents").className ='hideClass';
document.getElementById("tab_2_contents").className ='hideClass';
document.getElementById("tab_1_contents").className ='hideClass';
document.getElementById("tab_5_contents").className ='hideClass';
document.getElementById("tab4").className ='current';
document.getElementById("main").className ='';
document.getElementById("tab2").className ='';
document.getElementById("tab3").className ='';
document.getElementById("tab5").className ='';
}

function getTab5(){
var tabNo=document.getElementById("tabNo1");
//alert('before'+tabNo.value);
tabNo.value=5;
//alert(tabNo.value);
document.getElementById("tab_5_contents").className ='showClass';
document.getElementById("tab_4_contents").className ='hideClass';
document.getElementById("tab_3_contents").className ='hideClass';
document.getElementById("tab_2_contents").className ='hideClass';
document.getElementById("tab_1_contents").className ='hideClass';
document.getElementById("tab5").className ='current';
document.getElementById("main").className ='';
document.getElementById("tab2").className ='';
document.getElementById("tab3").className ='';
document.getElementById("tab4").className ='';
}


function checkStatus()
{
	var male=document.getElementById("maleRadio");
	var female=document.getElementById("femaleRadio");
	var militaryService=document.getElementById("militaryService");
	if(male.checked)
	{
		militaryService.disabled='';
	}
	else if(female.checked)
	{
		militaryService.disabled='disabled';
	}
}

function showMilitaryServiceDateRow()
{
	
	var militaryService=document.getElementById("militaryService");
	if(militaryService.value==2)
	{
		document.getElementById("mServ").className ='showClass';
		
	}

	else if(militaryService.value!=2)
	{
		document.getElementById("mServ").className ='hideClass';
		
	}
}


function init()
{
	var tabNo=document.getElementById("tabNo");

	if(tabNo.value==null || tabNo.value=='')
	{
		getMain();
	}
	
	if(tabNo.value==2)
	{
		getTab2();
	}

	else if(tabNo.value==3)
	{
		getTab3();
	}

	else if(tabNo.value==4)
	{
		getTab4();
	}
	else if(tabNo.value==5)
	{
		getTab5();
	}
}
</script>

<!-- <STYLE type='text/css'>

.glossymenu{
	padding: 0;
	width: 690px;
	border-bottom-width: 0px;
	border-top-width: 0px;
	border-right-width: 0px;
	border-left-width: 0px;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	border-bottom-color: #4a849e;
	margin-top: 0px;
	margin-right: 0;
	margin-bottom: 0px;
	margin-left: 0;
	outline-width: 2;
	outline-style: groove;
	outline-color:  #3A66A7;
}
</STYLE> -->



   
   <body>
 <form id="employeeForm"  name="employeeForm" enctype="multipart/form-data" method="POST" action="<c:url value="/hr/employeeForm.html"/>">
	<input type="hidden" name="rowId" id="rowId" value="">
	 <input type="hidden"  id="employeeId" name="employeeId" value="${employeeId }"/>
   <input type="hidden"  id="employeeId" name="employeeId" value="${employeeId }"/>
     <input type="hidden"  id="tabNo" name="tabNo" value="${tabNo}"/>
      <input type="hidden"  id="internalDivisionId" name="internalDivisionId" value="${internalDivisionId}"/>
       <input type="hidden"  id="geographicalDivisionId" name="geographicalDivisionId" value="${geographicalDivisionId}"/>
        <input type="hidden"  id="qualificationDivisionId" name="qualificationDivisionId" value="${qualificationDivisionId}"/>
     <input type="hidden"  id="tabNo1" name="tabNo1" value="${tabNo1 }"/>
   
<div id="tabs_container" align="center">
<ul class="glossymenu blue">
	<li id="main" class="current"><a onclick="getMain();"><b><abc:i18n
							property="hr.caption.personalData" /><fmt:message
							key="hr.caption.personalData" /></b></a></li>
	<li id="tab2" ><a onclick="getTab2();"><b><abc:i18n
							property="hr.caption.howToContact" /><fmt:message
							key="hr.caption.howToContact" /></b></a></li>
	<li id="tab3" ><a onclick="getTab3();"><b><abc:i18n
							property="hr.caption.employeeTransactions" /><fmt:message
							key="hr.caption.employeeTransactions" /></b></a></li>
	<li id="tab4" ><a onclick="getTab4();"><b><abc:i18n
							property="hr.caption.employeeCostCenter" /><fmt:message
							key="hr.caption.employeeCostCenter" /></b></a></li>
	<li id="tab5" ><a onclick="getTab5();"><b><abc:i18n
							property="hr.caption.employeesCodes" /><fmt:message
							key="hr.caption.employeesCodes" /></b></a></li>
</ul>
	

<div id="tab_1_contents" style="border: solid 3px #005F8C; padding: 5px; width:87%">
<table border="0" cellspacing="0" cellpadding="0">
	
	<tr>
		<TD>
			<spring:bind path="hrEmployee.*">
				<c:if test="${not empty status.errorMessages}">
					<div>
						<c:forEach var="error" items="${status.errorMessages}">
							<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
							</font>
						</c:forEach>
					</div>
				</c:if>
			</spring:bind>
		</TD>
	</tr>
		
	<tr>
		<td colspan="2">
			<table rules="all" align="center" width="70%" class="sofT">
				<tr id="head_1_ep">
					<td class="helpTitle" colspan="2" nowrap="nowrap"><abc:i18n
						property="hr.header.employeeForm" /><fmt:message
						key="hr.header.employeeForm" />
					</td>
				</tr>
				
				<tr>
			    	<td nowrap class="formReq" colspan="1" width="30%">
						<abc:i18n property="commons.caption.code"/>
						<fmt:message key="commons.caption.code"/>
							
					</td>						
					<td class="formBodControl" >
						<spring:bind path="hrEmployee.empCode">
							<input size="8" maxlength="8" type="text" readonly="readonly"	name="${status.expression}" value="${status.value}"  ${check}"/> 			
						</spring:bind>
					</td>
				</tr>
				
				<tr>
			    	<td nowrap class="formReq"  width="30%">
						<abc:i18n property="commons.caption.name"/>
						<fmt:message key="commons.caption.name"/>
					</td>
					<td class="formBodControl" >
						<spring:bind path="hrEmployee.name">
						 <input size="60" type="text"	name="${status.expression}" value="${status.value}"/> 		
						</spring:bind>
					</td>
			  	</tr>
			  
			  	<tr>
			   		<td nowrap class="formReq" width="30%">
						<abc:i18n property="commons.caption.englishName"/>
						<fmt:message key="commons.caption.englishName"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.ename">
							 <input size="60"  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/> 				
						</spring:bind>
					</td>
			  	</tr>
			  
			  	<tr>
			   		<td nowrap class="formReq" width="30%">
						<abc:i18n property="commons.caption.gender"/>
						<fmt:message key="commons.caption.gender"/>
					</td>
					<td class="formBodControl" width="70%">
						<table border="0">
							<tr>
								<td>
									<spring:bind path="hrEmployee.gender">
							           <td nowrap="nowrap">
											<input   type="radio" id="maleRadio" onchange="checkStatus();" name ="${status.expression}" ${status.value == "Male" ? ' checked':''} value="Male"><abc:i18n property="commons.caption.males"/><fmt:message key="commons.caption.males"/></input>
										</td>
										<td nowrap="nowrap">
											<input  type="radio" id="femaleRadio" onchange="checkStatus();" name ="${status.expression}" ${status.value == "Female" ? ' checked':''} value="Female"><abc:i18n property="commons.caption.females"/><fmt:message key="commons.caption.females"/></input>
										</td> 				
									</spring:bind>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			  
				<tr>
				   	<td nowrap class="formReq" width="30%">
						<abc:i18n property="hr.caption.title"/>
						<fmt:message key="hr.caption.title"/>
					</td>
					<td class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.title">
							<select name="${status.expression}" id="${status.expression}">
								<option value=""><fmt:message key="commons.caption.select" /></option>						
									<c:forEach items="${titles}" var="title">
										<option value="${title.id}" ${title.id == hrEmployee.title.id ?' selected' : ''}>${title.name}</option>
									</c:forEach>
							</select>			
						</spring:bind>
					</td>
				</tr>
			   	<tr>
			       	<td nowrap class="formBodControl" width="30%">
						<abc:i18n property="hr.caption.employeePhoto"/>
						<fmt:message key="hr.caption.employeePhoto"/>
					</td>
					<td   class="formBodControl" width="70%">
						<input type="file" size="40" name="employeePhotoFile" id="employeePhotoFile"/>
							<table  align="left">
								<tr>
									<td>
										<img src="viewEmployeePhoto.html?hrEmployeeId=${hrEmployee.id}" width=100 height=100/>
									</td>
								</tr>
						  	</table>
				  	 </td>
			  	</tr>
			  	
				<tr>
					<td nowrap class="formReq" width="30%">
						<abc:i18n property="commons.caption.nationality"/>
						<fmt:message key="commons.caption.nationality"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.nationality">
							<select name="${status.expression}" id="empNationality">
								<option value=""><fmt:message key="commons.caption.select" /></option>						
									<c:forEach items="${nationCountryList}" var="country">
										<option value="${country.id}" ${country.id == hrEmployee.nationality.id ?' selected' : ''}>${country.ardesc}</option>
									</c:forEach>
							</select>	 				
						</spring:bind>
					</td>	
				</tr>
			  
			  	<tr>
			   		<td nowrap class="formReq" width="30%">
						<abc:i18n property="commons.caption.religion"/>
						<fmt:message key="commons.caption.religion"/>
					</td>
					<td class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.empReligion">
							<select name="${status.expression}" id="empReligion">
								<option value=""><fmt:message key="commons.caption.select" /></option>						
									<c:forEach items="${religions}" var="religion">
										<option value="${religion.id}" ${religion.id == hrEmployee.empReligion.id ?' selected' : ''}>${religion.name}</option>
									</c:forEach>
							</select>			
						</spring:bind>
					</td>
			  	</tr>
			  
			  	<tr>
			   		<td nowrap class="formReq" width="30%">
						<abc:i18n property="hr.caption.militaryService"/>
						<fmt:message key="hr.caption.militaryService"/>
					</td>
					<td class="formBodControl" nowrap="nowrap">
						<spring:bind path="hrEmployee.militaryService">
							<select name="${status.expression}" id="militaryService" onchange="showMilitaryServiceDateRow();">
								<option value=""><fmt:message key="commons.caption.select" /></option>						
									<c:forEach items="${militaryServices}" var="militarySer">
										<option value="${militarySer.id}" ${militarySer.id == hrEmployee.militaryService.id ?' selected' : ''}>${militarySer.name}</option>
									</c:forEach>
							</select>	
						</spring:bind>
					</td>		
			  	</tr>
			 	<tr id="mServ" class="hideClass">
			    	<td nowrap class="formBodControl" width="30%">
						<abc:i18n property="hr.caption.endOfPostponementDate"/>
						<fmt:message key="hr.caption.endOfPostponementDate"/></td>
				 	<td class="formBodControl" width="70%">
				       <input type="text"  size="30" name="${status.expression}" id="${status.expression}" value="${status.value}" />
				 	</td>
			  	</tr>
			</table>
		</td>
	</tr>
</table>
</div>


<div id="tab_2_contents" style="border: solid 3px #005F8C; padding: 5px; width:90%" class="hideClass">
<table border="0" cellspacing="0" cellpadding="0">
	
	<tr>
		<TD>
			<spring:bind path="hrEmployee.*">
				<c:if test="${not empty status.errorMessages}">
					<div><c:forEach var="error" items="${status.errorMessages}">
						<font color="red"> <c:out value="${error}" escapeXml="false" /><br /></font>
							</c:forEach>
					</div>
				</c:if>
			</spring:bind>
		</TD>
	</tr>
		
	<tr>
		<td colspan="2">	
			<table rules="all" align="center" width="70%" class="sofT">
				<tr id="head_1_ep">
					<td class="helpTitle" colspan="2" nowrap="nowrap"><abc:i18n
						property="hr.header.employeeForm" /><fmt:message
						key="hr.header.employeeForm" /></td>
						
				</tr>
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" border="0" width="100%">
							<c:set var="i" value="geoDivisionTree" />
							<tree:tree tree="${i}" node="tree.node" includeRootNode="false">
							<tr>
								<td>
									<table cellspacing="0" cellpadding="0" border="0">
										<tr>
											<td valign="top"><tree:nodeIndent node="tree.node"
												indentationType="type"><tree:nodeIndentVerticalLine 
												indentationType="type"><img src="/Requests/web/gl/images/verticalLine.gif" 
												border="0"></tree:nodeIndentVerticalLine><tree:nodeIndentBlankSpace 
												indentationType="type"><img src="/Requests/web/gl/images/blankSpace.gif" 
												border="0"></tree:nodeIndentBlankSpace></tree:nodeIndent>
											</td>
	
											<td valign="top" >
												<tree:nodeMatch node="tree.node" hasChildren="true"
													expanded="false" isLastChild="false">
													<c:choose>
														<c:when test="${geographicalDivision.id != null}">
															<a
																href="employeeForm.html?expand=<tree:nodeId node="tree.node"/>&geographicalDivisionId=${geographicalDivision.id}&employeeId=${employeeId}&tabNo=2"><img
																src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
														</c:when>
														<c:otherwise>
															<a
																href="employeeForm.html?expand=<tree:nodeId node="tree.node"/>&employeeId=${employeeId}&tabNo=2"><img
																src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
														</c:otherwise>
													</c:choose>
												</tree:nodeMatch>
												
												<tree:nodeMatch node="tree.node"
													hasChildren="true" expanded="true" isLastChild="false">
													<c:choose>
														<c:when test="${geographicalDivision.id != null}">
															<a
																href="employeeForm.html?geographicalDivisionId=${geographicalDivision.id}&employeeId=${employeeId}&tabNo=2&collapse=<tree:nodeId node="tree.node"/>"><img
																src="/Requests/web/gl/images/ar_expandedMidNode.gif" border="0"></a>
														</c:when>
														<c:otherwise>
															<a
																href="employeeForm.html?tabNo=2&employeeId=${employeeId}&collapse=<tree:nodeId node="tree.node"/>"><img
																src="/Requests/web/gl/images/ar_expandedMidNode.gif" border="0"></a>
														</c:otherwise>
													</c:choose>
												</tree:nodeMatch>
												
												<tree:nodeMatch node="tree.node"
													hasChildren="true" expanded="false" isLastChild="true">
													<c:choose>
														<c:when test="${geographicalDivision.id != null}">
															<a
																href="employeeForm.html?geographicalDivisionId=${geographicalDivision.id}&employeeId=${employeeId}&tabNo=2&expand=<tree:nodeId node="tree.node"/>"><img
																src="/Requests/web/gl/images/ar_collapsedLastNode.gif" border="0"></a>
														</c:when>
														<c:otherwise>
															<a
																href="employeeForm.html?tabNo=2&employeeId=${employeeId}&expand=<tree:nodeId node="tree.node"/>"><img
																src="/Requests/web/gl/images/ar_collapsedLastNode.gif" border="0"></a>
														</c:otherwise>
													</c:choose>
												</tree:nodeMatch>
											
												<tree:nodeMatch node="tree.node"
													hasChildren="true" expanded="true" isLastChild="true">
													<c:choose>
														<c:when test="${geographicalDivision.id != null}">
															<a
																href="employeeForm.html?geographicalDivisionId=${geographicalDivision.id}&employeeId=${employeeId}&tabNo=2&collapse=<tree:nodeId node="tree.node"/>"><img
																src="/Requests/web/gl/images/ar_expandedLastNode.gif" border="0"></a>
														</c:when>
														<c:otherwise>
															<a
																href="employeeForm.html?tabNo=2&employeeId=${employeeId}&collapse=<tree:nodeId node="tree.node"/>"><img
																src="/Requests/web/gl/images/ar_expandedLastNode.gif" border="0"></a>
														</c:otherwise>
													</c:choose>
												</tree:nodeMatch>
												
												<tree:nodeMatch node="tree.node"
													hasChildren="false" isLastChild="false">
													<img src="/Requests/web/gl/images/ar_noChildrenMidNode.gif"
													border="0">
												</tree:nodeMatch>
												
												<tree:nodeMatch node="tree.node"
													hasChildren="false" isLastChild="true">
													<img src="/Requests/web/gl/images/ar_noChildrenLastNode.gif"
													border="0">
												</tree:nodeMatch>
											</td>
	
											<td valign="top">
												<tree:nodeMatch node="tree.node"
													hasChildren="true" expanded="true"><img src="/Requests/web/gl/images/openFolder.gif" border="0">
												</tree:nodeMatch>
												
												<tree:nodeMatch node="tree.node"
													hasChildren="true" expanded="false"><img src="/Requests/web/gl/images/ar_closedFolder.gif" border="0">
												</tree:nodeMatch>
												
												<tree:nodeMatch node="tree.node"
													hasChildren="false"><img src="/Requests/web/gl/images/nonFolder.gif" border="0">
												</tree:nodeMatch>
											</td>
	
											<td valign="top">
												<tree:nodeMatch node="tree.node">
													&nbsp;
													<a
														href="employeeForm.html?geographicalDivisionId=<tree:nodeId node="tree.node"/>&employeeId=${employeeId}&tabNo=2&select=<tree:nodeId node="tree.node"/>">
														<span style="Font-Size: 14px;">
															<tree:nodeName node="tree.node"/>
														</span>
													</a>
												</tree:nodeMatch>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							</tree:tree>
						</table>
					</td>
					<td>
						<c:if test="${geographicalDivision==null}">
					  		<input size="30"  type="text"	 readonly="readonly" name="${status.expression}" value=" ${hrEmployee.empAddress.geographicalDivision.ardesc }"/>
					  	</c:if>
					  	<c:if test="${geographicalDivision!=null}">
					  		<input size="30"  type="text"	 readonly="readonly" name="${status.expression}" value=" ${geographicalDivision.ardesc }"/>
					  	</c:if>
					</td>
				</tr>
			
			    <tr>
			   		<td nowrap class="formReq" width="30%">
						<abc:i18n property="commons.caption.address"/>
						<fmt:message key="commons.caption.address"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.empAddress.detailedAddress">
							<input size="30"  type="text"	 name="${status.expression}" value="${status.value}"/> 				
						</spring:bind>
					</td>
			  	</tr>
		  
			  	<tr>
			   		<td nowrap class="formReq" width="30%">
						<abc:i18n property="commons.caption.phone"/>
						<fmt:message key="commons.caption.phone"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.phone">
							<input size="30"  type="text"	 name="${status.expression}" value="${status.value}"/> 				
						</spring:bind>
					</td>
					</tr>
			
			   	<tr>
		   		<td nowrap class="formReq" width="30%">
					<abc:i18n property="hr.caption.emergencyPhone"/>
					<fmt:message key="hr.caption.emergencyPhone"/>
				</td>
				<td class="formBodControl" width="70%">
					<spring:bind path="hrEmployee.emergencyPhone">
						<input size="30"  type="text"	 name="${status.expression}" value="${status.value}"/> 				
					</spring:bind>
				</td>
		  		</tr>
			   	<tr>
			   		<td nowrap class="formReq" width="30%">
						<abc:i18n property="commons.caption.mobile"/>
						<fmt:message key="commons.caption.mobile"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.mobile">
							<input size="30"  type="text"	 name="${status.expression}" value="${status.value}"/> 				
						</spring:bind>
					</td>	
			  	</tr>
			
				<tr>
			   		<td nowrap class="formReq" width="30%">&nbsp;
						<abc:i18n property="commons.caption.email"/>
						<fmt:message key="commons.caption.email"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
					</td>
					<td class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.email">
							<input size="50"  type="text"	 name="${status.expression}" value="${status.value}"/>			
						</spring:bind>
					</td>
			  	</tr>
			</table>
		</td>
	</tr>
</table>
</div>

<div id="tab_3_contents" style="border: solid 3px #005F8C; padding: 5px; width:90%" class="hideClass">
<table border="0" cellspacing="0" cellpadding="0" width="100%">
	
	<tr>
		<TD>
			<spring:bind path="hrEmployee.*">
				<c:if test="${not empty status.errorMessages}">
					<div>
					<c:forEach var="error" items="${status.errorMessages}">
						<font color="red"> <c:out value="${error}" escapeXml="false" /><br/></font>
					</c:forEach>
					</div>
				</c:if>
			</spring:bind>
		</TD>
	</tr>
		
	<tr>
		<td colspan="2">
			<table rules="all" align="center" width="70%" class="sofT">
				<tr id="head_1_ep">
					<td class="helpTitle" colspan="2" nowrap="nowrap"><abc:i18n
						property="hr.header.employeeForm" /><fmt:message
						key="hr.header.employeeForm" /></td>
						
				</tr>
				
				<tr>
					<td nowrap="nowrap"  class="formBodControl" width="70%">
						<a
							href="qualificationsTransactionView.html?employeeId=${employeeId}&formType=employee/>"><fmt:message key="hr.header.qualificationsTransactionView" /></a>
	                 </td>				
			     </tr>
			     
			     <tr>
					<td nowrap="nowrap"  class="formBodControl" width="70%">
						<a
							href="employeeAllocationView.html?employeeId=${employeeId}&formType=employee/>"><fmt:message key="hr.header.employeeAllocationView" /></a>
	                </td>
			     </tr>
			     
			     <tr>
					<td nowrap="nowrap"  class="formBodControl" width="70%">
						<a
							href="employeeMaritalStatusView.html?employeeId=${employeeId}&formType=employee/>"><fmt:message key="hr.header.employeeMaritalStatusView" /></a>
	                </td>
			     </tr>
			     
			     <tr>
					<td nowrap="nowrap"  class="formBodControl" width="70%">
						<a
							href="employeeKinshipsDataView.html?employeeId=${employeeId}&formType=employee/>"><fmt:message key="hr.header.employeeKinshipsDataView" /></a>
	                </td>
			     </tr>
			     
			     <tr>
					<td nowrap="nowrap"  class="formBodControl" width="70%">
						<a
							href="employeeStatusView.html?employeeId=${employeeId}&formType=employee/>"><fmt:message key="hr.header.employeeStatusView" /></a>
	                </td>
			     </tr>
			     
			     <tr>
					<td nowrap="nowrap"  class="formBodControl" width="70%">
						<a
							href="employeeDocumentsView.html?employeeId=${employeeId}&formType=employee/>"><fmt:message key="hr.header.employeeDocumentsView" /></a>
	                </td>
			     </tr>
			     
			     <tr>
			     	<td nowrap="nowrap"  class="formBodControl" width="70%">
						<a
							href="employeeSponsorsView.html?employeeId=${employeeId}&formType=employee/>"><fmt:message key="hr.header.employeeSponsorsView" /></a>
	                 </td>		
			     </tr>
			</table>
		</td>
	</tr>
</table>
</div>

<div id="tab_4_contents" style="border: solid 3px #005F8C; padding: 5px; width:87%" class="hideClass">
<table border="0" cellspacing="0" cellpadding="0">
	
	<tr>
		<TD>
			<spring:bind path="hrEmployee.*">
				<c:if test="${not empty status.errorMessages}">
					<div>
					<c:forEach var="error" items="${status.errorMessages}">
						<font color="red"> <c:out value="${error}" escapeXml="false" /><br/></font>
					</c:forEach>
					</div>
				</c:if>
			</spring:bind>
		</TD>
	</tr>
		
	<tr>
		<td colspan="10">
			<table rules="all" align="center" width="70%" class="sofT">
				<tr id="head_1_ep">
					<td class="helpTitle" colspan="10" nowrap="nowrap"><abc:i18n
						property="hr.header.employeeForm" /><fmt:message
						key="hr.header.employeeForm" />
					</td>
				</tr>
			   	<tr>
			   		<td nowrap class="formReq" width="30%">
						<abc:i18n property="hr.caption.costCenter"/>
						<fmt:message key="hr.caption.costCenter"/>
					</td>
					<td  class="formBodControl" width="70%">
							<select name="costCenter" id="costCenter">
								<option value=""><fmt:message key="commons.caption.select" /></option>						
									<c:forEach items="${costCenters}" var="costCenter">
										<option value="${costCenter.id}" ${costCenter.id == employeeCostCenters[0].costCenter.id ?' selected' : ''}>${costCenter.arDesc}</option>
									</c:forEach>
							</select>
					</td>
					<td nowrap class="formReq" width="30%">
						<abc:i18n property="hr.caption.fromDate"/>
						<fmt:message key="hr.caption.fromDate"/></td>
				    <td class="formBodControl" width="70%">
				       <input type="text"  name="fromDate" id="fromDate" value="<_4s_:formatMiladiDate value="${employeeCostCenters[0].fromDate }"/>" />
				    </td>
			     
				    <td nowrap class="formReq" width="30%">
					 	<abc:i18n property="hr.caption.toDate"/>
						<fmt:message key="hr.caption.toDate"/>
					</td>
				    <td class="formBodControl" width="70%">
				       <input type="text"  name="toDate" id="toDate" value="<_4s_:formatMiladiDate value="${employeeCostCenters[0].toDate }"/>" />
				    </td>	
		  		</tr>
			  
			   	<tr>
				   	<td nowrap class="formReq" width="30%">
						<abc:i18n property="hr.caption.anotherCostCenter"/>
						<fmt:message key="hr.caption.anotherCostCenter"/>
					</td>
					<td class="formBodControl" width="70%">
						<select name="costCenter2" id="costCenter2">
							<option value=""><fmt:message key="commons.caption.select" /></option>						
								<c:forEach items="${costCenters}" var="costCenter">
									<option value="${costCenter.id}" ${costCenter.id == employeeCostCenters[1].costCenter.id ?' selected' : ''}>${costCenter.arDesc}</option>
								</c:forEach>
						</select>
					</td>
					
				    <td nowrap class="formReq" width="30%">
						<abc:i18n property="hr.caption.fromDate"/>
						<fmt:message key="hr.caption.fromDate"/>
					</td>
				    <td class="formBodControl" width="70%">
				       <input type="text"   name="fromDate2" id="fromDate2" value="<_4s_:formatMiladiDate value="${employeeCostCenters[1].fromDate }"/>" />
				    </td>
				     
				    <td nowrap class="formReq" width="30%">
						<abc:i18n property="hr.caption.toDate"/>
						<fmt:message key="hr.caption.toDate"/>
					</td>
				    <td class="formBodControl" width="70%">
				    	<input type="text"   name="toDate2" id="toDate2"  value="<_4s_:formatMiladiDate value="${employeeCostCenters[1].toDate }"/>" >
				    </td>	
			  	</tr> 
			</table>
		</td>
	</tr>
</table>
</div>

<div id="tab_5_contents" style="border: solid 3px #005F8C; padding: 5px; width:100%" class="hideClass">
<table border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<TD>
			<spring:bind path="hrEmployee.*">
				<c:if test="${not empty status.errorMessages}">
					<div>
					<c:forEach var="error" items="${status.errorMessages}">
						<font color="red"> <c:out value="${error}" escapeXml="false" /><br/></font>
					</c:forEach>
					</div>
				</c:if>
			</spring:bind>
		</TD>
	</tr>
		
	<tr>
		<td colspan="2">
			<table rules="all" align="center" width="100%" class="sofT">
				<tr id="head_1_ep">
					<td class="helpTitle" colspan="8" nowrap="nowrap">
					<abc:i18n property="hr.header.employeeForm" /><fmt:message key="hr.header.employeeForm" />
					</td>	
				</tr>
				
				<tr>
			  		<td nowrap class="formBodControl" width="30%">
						<abc:i18n property="hr.caption.isPermenant"/>
						<fmt:message key="hr.caption.isPermenant"/>
					</td>
				
					<td class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.permenant">
							<input type="checkbox"	name="${status.expression}" ${hrEmployee.permenant == on ? ' checked':""}>
						</spring:bind>			
					</td>
					
				</tr>
							
				<tr>
					<td nowrap class="formReq" width="30%" >
						<abc:i18n property="hr.caption.dateOfAppointment"/>
						<fmt:message key="hr.caption.dateOfAppointment"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.empldate">
							<input type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>
					</td>
					
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.tax"/>
						<fmt:message key="hr.caption.tax"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.tax_code">
							<select name="${status.expression}" id="${status.expression}">
								<option value=""><fmt:message key="commons.caption.select" /></option>						
									<c:forEach items="${taxList}" var="taxcode">
										<option value="${taxcode.id}" ${taxcode.id == hrEmployee.tax_code.id ?' selected' : ''}>${taxcode.effName}</option>
									</c:forEach>
							</select>
						</spring:bind>
					</td>
					
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.job_join"/>
						<fmt:message key="hr.caption.job_join"/>
					</td>
					<td class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.job_join">
							<input type="text" name="${status.expression}" value="${status.value}"/>
						</spring:bind>
					</td>
				</tr>
				
				<tr>
					<td nowrap class="formReq" width="30%">
						<abc:i18n property="commons.caption.insuranceCode"/>
						<fmt:message key="commons.caption.insuranceCode"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.insurCode">
							<select name="${status.expression}" id="${status.expression}">
								<option value="">
									<fmt:message key="commons.caption.select" />
								</option>						
								<c:forEach items="${insuranceCodes}" var="insuranceCode">
									<option value="${insuranceCode.id}" ${insuranceCode.id == hrEmployee.insurCode ?' selected' : ''}>${insuranceCode.name}</option>
								</c:forEach>
							</select>			
						</spring:bind>
					</td>
					<td nowrap class="formReq" width="30%">
						<abc:i18n property="hr.caption.birthDate"/>
						<fmt:message key="hr.caption.birthDate"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.birthDate">
							<input type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>
					</td>	
				</tr>
				
				<tr>
					<td nowrap class="formBodControl" width="30%">
						<abc:i18n property="hr.caption.insuranceNumber"/>
							<fmt:message key="hr.caption.insuranceNumber"/>
					</td>
				
					<td class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.insurNo">
							<input type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>
					</td>
					
					<td nowrap class="formBodControl" width="30%">
						<abc:i18n property="hr.caption.end_serv"/>
							<fmt:message key="hr.caption.end_serv"/>
					</td>
				
					<td class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.end_serv">
							<input type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>
					</td>
												
					<td nowrap class="formBodControl" width="30%">
						<abc:i18n property="hr.caption.insuranceDate"/>
							<fmt:message key="hr.caption.insuranceDate"/>
					</td>
				
					<td class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.insuranceDate">
							<input type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>
					</td>
	
				</tr>
				
				<tr>
					<td nowrap class="formBodControl" width="30%">
						<abc:i18n property="hr.caption.insuranceEndDate"/>
							<fmt:message key="hr.caption.insuranceEndDate"/>
					</td>
				
					<td class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.insuranceEnd">
							<input type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>
					</td>
	
				</tr>
				
				<tr>
			  		 <td nowrap class="formBodControl" width="30%">
						<abc:i18n property="hr.caption.salaryAtAppointment"/>
						<fmt:message key="hr.caption.salaryAtAppointment"/>
					</td>
				
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.start_sal">
							<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>
					</td>
					
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.flagExp"/>
						<fmt:message key="hr.caption.flagExp"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.flag_exp">
							<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>
					</td>
					
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.basicSalary"/>
						<fmt:message key="hr.caption.basicSalary"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.basic">
							<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>				
					</td>
				</tr>
				<tr>			
					<td colspan="1">
						<table align="center" width="50%" border="0" cellpadding="0" cellspacing="10" class="sofT" rules="all">
							<tr id="head_1_ep">
								<td class="helpTitle" colspan="8" nowrap="nowrap">
									<abc:i18n property="hr.caption.effectInsurance" /><fmt:message key="hr.caption.effectInsurance" />
								</td>	
							</tr>
						
							<tr>
								<td class="formBodControl" width="30%" nowrap="nowrap">
								<abc:i18n property="hr.caption.basicInsurance"/>
								<fmt:message key="hr.caption.basicInsurance"/>
								</td>
								<td  class="formBodControl" width="70%">
									<spring:bind path="hrEmployee.basicti">
										<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
									</spring:bind>	
								</td>
							</tr>
							<tr>
								<td class="formBodControl" width="30%" nowrap="nowrap">
								<abc:i18n property="hr.caption.notSubjectSalRaise"/>
								<fmt:message key="hr.caption.notSubjectSalRaise"/>
								</td>
								<td  class="formBodControl" width="70%">
									<spring:bind path="hrEmployee.basicri">
										<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
									</spring:bind>	
								</td>
							</tr>
						
							<tr>
								<td class="formBodControl" width="30%" nowrap="nowrap">
								<abc:i18n property="hr.caption.otherNotSubject"/>
								<fmt:message key="hr.caption.otherNotSubject"/>
								</td>
								<td  class="formBodControl" width="70%">
									<spring:bind path="hrEmployee.basicai">
										<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
									</spring:bind>	
								</td>
							</tr>
						</table>
					</td>
					<td colspan="1">
						<table align="center" width="50%" class="sofT" rules="all" border="0" cellpadding="0" cellspacing="0" >
							<tr id="head_1_ep">
								<td class="helpTitle" colspan="8" nowrap="nowrap">
									<abc:i18n property="hr.caption.effectTaxs" /><fmt:message key="hr.caption.effectTax" />
								</td>	
							</tr>
						
							<tr>
								<td class="formBodControl" width="30%" nowrap="nowrap">
								<abc:i18n property="hr.caption.basicTax"/>
								<fmt:message key="hr.caption.basicTax"/>
								</td>
								<td  class="formBodControl" width="70%">
									<spring:bind path="hrEmployee.basictt">
										<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
									</spring:bind>	
								</td>
							</tr>
						
							<tr>
								<td class="formBodControl" width="30%" nowrap="nowrap">
								<abc:i18n property="hr.caption.notSubjectSalRaise"/>
								<fmt:message key="hr.caption.notSubjectSalRaise"/>
								</td>
								<td  class="formBodControl" width="70%">
									<spring:bind path="hrEmployee.basicrt">
										<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
									</spring:bind>	
								</td>
							</tr>
						
							<tr>
								<td class="formBodControl" width="30%" nowrap="nowrap">
								<abc:i18n property="hr.caption.otherNotSubject"/>
								<fmt:message key="hr.caption.otherNotSubject"/>
								</td>
								<td  class="formBodControl" width="70%">
									<spring:bind path="hrEmployee.basicat">
										<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
									</spring:bind>	
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.hourPrice"/>
						<fmt:message key="hr.caption.hourPrice"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.basichour">
							<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>	
					</td>
				</tr>
				
				<tr>
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.hasGovSalRaise"/>
						<fmt:message key="hr.caption.hasGovSalRaise"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.no_govern_raise">
							<input size="30"  type="text"	 name="${status.expression}" value="${status.value}"/>			
						</spring:bind>	
					</td>
					
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.newlyHired"/>
						<fmt:message key="hr.caption.newlyHired"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.first_employment">
							<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>	
					</td>
					
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.noVariableInsurance"/>
						<fmt:message key="hr.caption.noVariableInsurance"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.no_var_insu">
							<input size="30"  type="text"	 name="${status.expression}" value="${status.value}"/>			
						</spring:bind>	
					</td>
				</tr>
				
				<tr>
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.halftime"/>
						<fmt:message key="hr.caption.halftime"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.halftime">
							<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>	
					</td>
				</tr>	
				
				<tr>
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.displayFlag"/>
						<fmt:message key="hr.caption.displayFlag"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.display_flag">
							<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>	
					</td>
					
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.apply_overtime"/>
						<fmt:message key="hr.caption.apply_overtime"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.apply_overtime">
							<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>	
					</td>				
				</tr>
				
				
				<tr>
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.total_salary"/>
						<fmt:message key="hr.caption.total_salary"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.total_salary">
							<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>	
					</td>
					
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.net_sal"/>
						<fmt:message key="hr.caption.net_sal"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.net_sal">
							<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>	
					</td>				
				</tr>
				
				<tr>
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.contract_salary"/>
						<fmt:message key="hr.caption.contract_salary"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.contract_salary">
							<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>	
					</td>
					
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.workingd"/>
						<fmt:message key="hr.caption.workingd"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.workingd">
							<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>	
					</td>				
				</tr>
				
				
				<tr>
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.workingh"/>
						<fmt:message key="hr.caption.workingh"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.workingh">
							<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>	
					</td>
					
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.total_no_hour"/>
						<fmt:message key="hr.caption.total_no_hour"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.total_no_hour">
							<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>	
					</td>				
				</tr>			
				
				<tr>
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="commons.caption.casher"/>
						<fmt:message key="commons.caption.casher"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.casher">
							<select name="${status.expression}" id="${status.expression}">
								<option value=""><fmt:message key="commons.caption.select" /></option>						
									<c:forEach items="${banksList}" var="bank">
										<option value="${bank.id}" ${bank.id == hrEmployee.casher.id ?' selected' : ''}>${bank.name}</option>
									</c:forEach>
							</select>
						</spring:bind>
					</td>	
					
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="commons.caption.region"/>
						<fmt:message key="commons.caption.region"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.region">
							<select name="${status.expression}" id="${status.expression}">
								<option value=""><fmt:message key="commons.caption.select" /></option>						
									<c:forEach items="${regionsList}" var="region">
										<option value="${region.id}" ${region.id == hrEmployee.region.id ?' selected' : ''}>${region.name}</option>
									</c:forEach>
							</select>
						</spring:bind>
					</td>
					
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="commons.caption.location"/>
						<fmt:message key="commons.caption.location"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.location">
							<select name="${status.expression}" id="${status.expression}">
								<option value=""><fmt:message key="commons.caption.select" /></option>						
									<c:forEach items="${locationsList}" var="loc">
										<option value="${loc.id}" ${loc.id == hrEmployee.degCode.id ?' selected' : ''}>${loc.name}</option>
									</c:forEach>
							</select>
						</spring:bind>
					</td>	
					
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="commons.caption.degree"/>
						<fmt:message key="commons.caption.degree"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.degCode">
							<select name="${status.expression}" id="${status.expression}">
								<option value=""><fmt:message key="commons.caption.select" /></option>						
									<c:forEach items="${degreesList}" var="degree">
										<option value="${degree.id}" ${degree.id == hrEmployee.degCode.id ?' selected' : ''}>${degree.name}</option>
									</c:forEach>
							</select>
						</spring:bind>
					</td>
				</tr>
	
				<tr>
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="hr.caption.members"/>
						<fmt:message key="hr.caption.members"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.members">
							<input type="text" name="${status.expression}" value="${status.value}"/>
						</spring:bind>			
					</td>				
								
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="commons.caption.sector"/>
						<fmt:message key="commons.caption.sector"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.sector">
							<select name="${status.expression}" id="${status.expression}">
								<option value=""><fmt:message key="commons.caption.select" /></option>						
									<c:forEach items="${sectorsList}" var="sector">
										<option value="${sector.id}" ${sector.id == hrEmployee.sector.id ?' selected' : ''}>${sector.name}</option>
									</c:forEach>
							</select>
						</spring:bind>
					</td>					
	
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="commons.caption.syndicate"/>
						<fmt:message key="commons.caption.syndicate"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.syndicate">
							<select name="${status.expression}" id="${status.expression}">
								<option value=""><fmt:message key="commons.caption.select" /></option>						
									<c:forEach items="${syndicatesList}" var="syndicate">
										<option value="${syndicate.id}" ${syndicate.id == hrEmployee.syndicate.id ?' selected' : ''}>${syndicate.name}</option>
									</c:forEach>
							</select>
						</spring:bind>
					</td>
				</tr>		
				
				<tr>
					<td class="formReq" width="30%" nowrap="nowrap">
						<abc:i18n property="commons.caption.remark"/>
						<fmt:message key="commons.caption.remark"/>
					</td>
					<td  class="formBodControl" width="70%">
						<spring:bind path="hrEmployee.remark">
							<input size="30"  type="text" name="${status.expression}" value="${status.value}"/>			
						</spring:bind>	
					</td>
				</tr>	
			</table>
		</td>
	</tr>
</table>
</div>


<table border="0">
	<tr>

		<td id="save" class="showClass"><abc:i18n
			property="commons.button.save" /> <input type="submit" name="saveButton"
			value="<fmt:message key="commons.button.save"/>" class="button" /></td>

		<td>&nbsp;&nbsp;&nbsp; <abc:i18n property="commons.button.cancel" />
		<input type="reset" name="cancel"
			value="<fmt:message key="commons.button.cancel"/>" class="button" /></td>
	</tr>
</table>
</div>
 
</form>
</body>


   <script language="JavaScript" type="text/javascript"> window.onload=init();
   </script>
<%@ include file="/web/common/includes/footer.jsp" %>
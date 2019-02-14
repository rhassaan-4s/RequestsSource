 <jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree"%>


<abc:security property=""/><br>
<script type="text/javascript">

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
		var cell11= titleRow.insertCell(8);
		cell11.setAttribute('class','formBodControl');
		cell11.appendChild(e6);
	
	
	y++;
	document.getElementById("counter").value = y;
	}
	
	
	
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
</script>

<form id="empJob" name="empJob" method="POST"	action="<c:url value="employeeAllocationForm.html"/>" >

<table width="90%" class="sofT" >
<input type="hidden" id="employeeId" name="employeeId" value="${employeeId }"/>
<input type="hidden" id="formType" name="formType" value="${formType}"/>
<input type="hidden"  id="empJobId" name="empJobId" value="${empJobId }"/>
<input type="hidden"  id="intDivId" name="intDivId" value="${intDiv.id }"/>

<tr>
		<td colspan="2">
			<spring:bind path="hrEmployeeJob.*">
						<c:if test="${not empty status.errorMessages}">
							<div><c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
								</font>
							</c:forEach></div>
						</c:if>
			</spring:bind>
			</td>
  </tr>

<tr height="20">
	
	      <td class="helpTitle" >
	      
	          <b> <fmt:message	key="hr.header.employeeAllocationForm" /> </b>
	      </td>
		</tr>
<tr>
		<td colspan="3" valign="top">

		<table cellspacing="0" cellpadding="0" border="0" align="right"
			width="90%">
			
			
				
				<tr>
			<td>
	
			<table cellspacing="0" cellpadding="0" border="0" width="50%" >
		<c:set var="i" value="internalDivisionTree" />
		<c:set var="link" value="employeeAllocationForm.html" />
		
		<tr height="20">
		</tr>
		<tr>
		<td align="right">
		
		<table align="right"   class="sofT">
		<tr>
		<td>
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
										border="0"></tree:nodeIndentBlankSpace></tree:nodeIndent></td>

									<td valign="top">
									<tree:nodeMatch node="tree.node" hasChildren="true"
										expanded="false" isLastChild="false">
										<c:choose>
											<c:when test="${result.id != null}">
												<a
													href="${link}?expand=<tree:nodeId node="tree.node"/>&resultId=${result.id}&intDivisionId=<tree:nodeId node="tree.node"/>&empJobId=${empJobId}&employeeId=${employeeId }&formType=${formType}"><img
													src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="${link}?expand=<tree:nodeId node="tree.node"/>&intDivisionId=<tree:nodeId node="tree.node"/>&empJobId=${empJobId}&employeeId=${employeeId }&formType=${formType}"><img
													src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
											</c:otherwise>
										</c:choose>
									</tree:nodeMatch><tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="true" isLastChild="false">
										<c:choose>
											<c:when test="${result.id != null}">
												<a
													href="${link}?resultId=${result.id}&collapse=<tree:nodeId node="tree.node"/>&intDivisionId=<tree:nodeId node="tree.node"/>&empJobId=${empJobId}&employeeId=${employeeId }&formType=${formType}"><img
													src="/Requests/web/gl/images/ar_expandedMidNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="${link}?collapse=<tree:nodeId node="tree.node"/>&intDivisionId=<tree:nodeId node="tree.node"/>&empJobId=${empJobId}&employeeId=${employeeId }&formType=${formType}"><img
													src="/Requests/web/gl/images/ar_expandedMidNode.gif" border="0"></a>
											</c:otherwise>
										</c:choose>
									</tree:nodeMatch>
									<tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="false" isLastChild="true">
										<c:choose>
											<c:when test="${result.id != null}">
												<a
													href="${link}?resultId=${result.id}&expand=<tree:nodeId node="tree.node"/>&intDivisionId=<tree:nodeId node="tree.node"/>&empJobId=${empJobId}&employeeId=${employeeId }&formType=${formType}"><img
													src="/Requests/web/gl/images/ar_collapsedLastNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="${link}?expand=<tree:nodeId node="tree.node"/>&intDivisionId=<tree:nodeId node="tree.node"/>&empJobId=${empJobId}&employeeId=${employeeId }&formType=${formType}"><img
													src="/Requests/web/gl/images/ar_collapsedLastNode.gif" border="0"></a>
											</c:otherwise>
										</c:choose>
									</tree:nodeMatch>
									<tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="true" isLastChild="true">
										<c:choose>
											<c:when test="${result.id != null}">
												<a
													href="${link}?resultId=${result.id}&collapse=<tree:nodeId node="tree.node"/>&intDivisionId=<tree:nodeId node="tree.node"/>&empJobId=${empJobId}&employeeId=${employeeId }&formType=${formType}"><img
													src="/Requests/web/gl/images/ar_expandedLastNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="${link}?collapse=<tree:nodeId node="tree.node"/>&intDivisionId=<tree:nodeId node="tree.node"/>&empJobId=${empJobId}&employeeId=${employeeId }&formType=${formType}"><img
													src="/Requests/web/gl/images/ar_expandedLastNode.gif" border="0"></a>
											</c:otherwise>
										</c:choose>
									</tree:nodeMatch>
									<tree:nodeMatch node="tree.node"
										hasChildren="false" isLastChild="false">
										<img src="/Requests/web/gl/images/ar_noChildrenMidNode.gif"
											border="0">
									</tree:nodeMatch> <tree:nodeMatch node="tree.node"
										hasChildren="false" isLastChild="true">
										<img src="/Requests/web/gl/images/ar_noChildrenLastNode.gif"
											border="0">
									</tree:nodeMatch></td>

									<td valign="top">
									<tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="true"><img src="/Requests/web/gl/images/openFolder.gif" 
										border="0">
									</tree:nodeMatch>
									<tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="false"><img src="/Requests/web/gl/images/ar_closedFolder.gif" border="0">
									</tree:nodeMatch> <tree:nodeMatch node="tree.node"
										hasChildren="false"><img src="/Requests/web/gl/images/nonFolder.gif" border="0">
									</tree:nodeMatch></td>

									<td valign="top"><tree:nodeMatch node="tree.node">
										&nbsp;
										<a
											href="${link}?intDivisionId=<tree:nodeId node="tree.node"/>&select=<tree:nodeId node="tree.node"/>&empJobId=${empJobId}&employeeId=${employeeId }&formType=${formType}">
										<span style="Font-Size: 14px;"> <tree:nodeName
											node="tree.node" /> </span> </a>
									</tree:nodeMatch></td>
								</tr>
							</table>
							</td>
						</tr>
					</tree:tree>
					</td>
					</tr>
					</table>
					
					</td>
				</tr>
					
					<tr height="20">
					</tr>
				
					<tr>
					<td class="formBod">
					<b><fmt:message key="hr.caption.job" /></b>&nbsp;
					</td>
					
					  <td>
					      &nbsp;
					   </td>
					   
					   <td >
					   
					      <c:if test="${intDiv==null || intDiv==''}">
					   <spring:bind path="hrEmployeeJob.job.ardesc">
					     <input type="text" id="${status.expression }" name="${status.expression }" readonly="readonly" value="${status.value}">
					     </spring:bind>
					     </c:if>
					     
					      <c:if test="${intDiv!=null && intDiv!=''}">
					     <input type="text" id="${status.expression }" name="${status.expression }" readonly="readonly" value="${intDiv.ardesc}">
					   
					     </c:if>
					   </td>
					   
					   
					   <td nowrap="nowrap" class="formBod">
					  
					   <b> <fmt:message	key="hr.caption.jobStartDate" /> </b>
					   
					   </td>
					   <td>
					      &nbsp;
					   </td>
					   <td>
					     <spring:bind path="hrEmployeeJob.job_start_date">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					   </td>
					   
					  
					</tr>
					
					<tr height="10" >
					</tr>
					
					
					  <tr id="btn">
					  
							<td colspan="3"  align="center">
							<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" onclick="detectForm();"/>&nbsp;&nbsp;&nbsp;
							</td >
							<td colspan="3">
								<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
							</td>
							
					</tr>
					
			</table>
			</td>
			</tr>
			</table>
			</td>
			</tr>
			</table>
					
		</form>			
<%@ include file="/web/common/includes/footer.jsp"%>
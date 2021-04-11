 <jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree"%>
<script type='text/javascript' src='/Requests/dwr/interface/hrManager.js'></script>
<script type='text/javascript' src='/Requests/dwr/engine.js'></script>
<script type='text/javascript' src='/Requests/dwr/util.js'></script>

<abc:security property="78"/><br>

<!--  <STYLE type='text/css'>
textarea {overflow-x: hidden; overflow-y: scroll}
</STYLE>-->
<script type="text/javascript"><!--
function detectForm()
{
	//alert('inside onsubmitform');
	var formType=document.forms[0].elements['formType'].value;
	//alert('formType>>>>>>'+formType);
  if(formType == 'internal')
  {
   document.myForm.action ="internalDivisionForm.html";
  }
 else if(formType == 'geographical')
  {
    document.myForm.action ="geographicalDivisionForm.html";
  }
 else if(formType == 'qualification')
 {
   document.myForm.action ="qualificationDivisionForm.html";
 }
 else if(formType == 'specialty')
 {
   document.myForm.action ="specialtyDivisionForm.html";
 }
  
}


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

function changeList()
{
	var parent=document.getElementById("parent");
	var leafsList=document.getElementById("leafsList");
	var formType=document.getElementById("formType");
	//alert('leafsList>>>>>>>>>>'+leafsList);
	if(parent.value !="")
	{	
		DWRUtil.removeAllOptions(leafsList);
		if(formType.value=='internal')
		{
		  hrManager.getLeafsByParentId(putList,parent.value,'HRInternalDivision');
	    }
		else if(formType.value=='qualification')
		{
			  hrManager.getLeafsByParentId(putList,parent.value,'HRQualificationDivision');
		}

		else if(formType.value=='geographical')
		{
			  hrManager.getLeafsByParentId(putList,parent.value,'HRGeographicalDivision');
	     }

		else if(formType.value=='specialty')
		{
			  hrManager.getLeafsByParentId(putList,parent.value,'HRSpecialtyDivision');
	     }
	}
	else
	{
		DWRUtil.removeAllOptions(leafsList);
	}	
	
}
function putList(data)
{	
	var leafsList = document.getElementById("leafsList");
	for(i=0;i<data.length;i++)
	{
		var elOptNew = document.createElement('option');
		
		elOptNew.text = data[i].ardesc;
		elOptNew.value = data[i].id ;
		
		leafsList.add(elOptNew,null);
	}
}



</script>
<table >
<tr>
		<td colspan="2" valign="top">

		<table cellspacing="0" cellpadding="0" border="0" align="right"
			width="100%">
				
				<tr>
			<td>
			<table cellspacing="0" cellpadding="0" border="0" align="right">
			       <c:if test="${formType=='internal'}">
							<c:set var="i" value="internalDivisionTree" />
							<c:set var="link" value="internalDivisionTree.html" />
							<c:set var="formLink" value="internalDivisionForm.html?parentId=${parentId}" />
						</c:if>
						 <c:if test="${formType=='qualification'}">
							<c:set var="i" value="qualificationDivisionTree" />
							<c:set var="link" value="qualificationDivisionTree.html" />
							<c:set var="formLink" value="qualificationDivisionForm.html?parentId=${parentId}" />
						</c:if>
						 <c:if test="${formType=='geographical'}">
							<c:set var="i" value="geographicalDivisionTree" />
							<c:set var="link" value="geographicalDivisionTree.html" />
							<c:set var="formLink" value="geographicalDivisionForm.html?parentId=${parentId}" />
						</c:if>
						 <c:if test="${formType=='specialty'}">
							<c:set var="i" value="specialtyDivisionTree" />
							<c:set var="link" value="specialtyDivisionTree.html" />
							<c:set var="formLink" value="specialtyDivisionForm.html?parentId=${parentId}" />
						</c:if>
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
													href="${link}?expand=<tree:nodeId node="tree.node"/>&resultId=${result.id}"><img
													src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="${link}?expand=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
											</c:otherwise>
										</c:choose>
									</tree:nodeMatch><tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="true" isLastChild="false">
										<c:choose>
											<c:when test="${result.id != null}">
												<a
													href="${link}?resultId=${result.id}&collapse=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_expandedMidNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="${link}?collapse=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_expandedMidNode.gif" border="0"></a>
											</c:otherwise>
										</c:choose>
									</tree:nodeMatch>
									<tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="false" isLastChild="true">
										<c:choose>
											<c:when test="${result.id != null}">
												<a
													href="${link}?resultId=${result.id}&expand=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_collapsedLastNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="${link}?expand=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_collapsedLastNode.gif" border="0"></a>
											</c:otherwise>
										</c:choose>
									</tree:nodeMatch>
									<tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="true" isLastChild="true">
										<c:choose>
											<c:when test="${result.id != null}">
												<a
													href="${link}?resultId=${result.id}&collapse=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_expandedLastNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="${link}?collapse=<tree:nodeId node="tree.node"/>"><img
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
											href="${link}?resultId=<tree:nodeId node="tree.node"/>&select=<tree:nodeId node="tree.node"/>">
										<span style="Font-Size: 14px;"> <tree:nodeName
											node="tree.node" /> </span> </a>
									</tree:nodeMatch></td>
								</tr>
							</table>
							</td>
						</tr>
					</tree:tree>
					</table>
					</td>
		

<td valign="top">

<form id="myForm" name="myForm" method="POST"	action="<c:url value=""/>">

 <input type="hidden"  id="resultId" name="resultId" value="${resultId}"/>
   <input type="hidden" size=20 maxlength=40 name="parentId" value="${parentId}" />
		<input type="hidden" size=20 maxlength=40 name="long" value="${long}" />
		<input type="hidden" size=20 maxlength=40 name="isLeaf" value="${isLeaf}" />
		<input type="hidden" size=20 maxlength=40 name="getIsLast" value="${getIsLast}" />
		<input type="hidden" size=20 maxlength=40 name="copyId" value="${copyId}" />
		<INPUT type="hidden" size="20" name="formType" value="${formType}"/>

<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px" align="left" >
   
   
  	
   <tr >
		<td colspan="2">
		<table >
		<tr>
		<td>
			<spring:bind path="result.*">
						<c:if test="${not empty status.errorMessages}">
							<div><c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
								</font>
							</c:forEach></div>
						</c:if>
			</spring:bind>
			</td>
			</tr>
			</table>
			</td>
  </tr>
  <tr>
  <td>
  	<table  align="left" width="66%" class="sofT"  >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap>
				<c:if test="${formType=='internal'}">
				<abc:i18n property="hr.header.addInternalDivision" />
				<fmt:message key="hr.header.addInternalDivision" />
				</c:if>	
				
				<c:if test="${formType=='geographical'}">
				<abc:i18n property="hr.header.addGeographicalDivision" />
				<fmt:message key="hr.header.addGeographicalDivision" />
				</c:if>	
				<c:if test="${formType=='qualification'}">
				<abc:i18n property="hr.header.addQualificationDivision" />
				<fmt:message key="hr.header.addQualificationDivision" />
				</c:if>
				
				<c:if test="${formType=='specialty'}">
				<abc:i18n property="hr.header.addSpecialtyDivision" />
				<fmt:message key="hr.header.addSpecialtyDivision" />
				</c:if>		
			</td>
			</tr>
		  <c:if test="${copyId ==null || copyId==''}">	
		    <tr>
		
		  <c:set var="check"  value=""/>
		  <c:if test="${resultId!=null && resultId!=''}">
					 <c:set var="check"  value="disabled"/>
		</c:if>
	
		
		        <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.code"/>
								<fmt:message key="commons.caption.code"/></td>
					
										
				<td class="formBodControl">
					<spring:bind path="result.code">
						<input size="8" maxlength="3" type="text"	name="${status.expression}" value="${status.value}"  ${check}"/>&nbsp;${parentLongCode}			
					</spring:bind>
					</td>
		  </tr>
		 
		    <tr>
		   
		    <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.name"/>
								<fmt:message key="commons.caption.name"/></td>
			
				<td class="formBodControl" width="70%">
					<spring:bind path="result.ardesc">
					 <input size="40" type="text"	name="${status.expression}" value="${status.value}"/> 		
					</spring:bind>
					</td>
		  </tr>
		 
		
		  <tr>
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.englishName"/>
								<fmt:message key="commons.caption.englishName"/></td>
			
				<td  class="formBodControl" width="70%">
					<spring:bind path="result.endesc">
					 <input size="40"  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/> 				
					</spring:bind>
					</td>
		  </tr>
		
		  
		  <tr>
		  <td nowrap class="formReq" width="30%">
		      <fmt:message key="hr.caption.level"/>
		  </td>
		  <td class="formBodControl" width="70%">
		  
		  <c:if test="${parent!=null}">
		  <c:if test="${(result.id==null || result.id=='') && (parent.divisionLevel!=null)}">
		      <spring:bind path="result.divisionLevel">
					 <select name="${status.expression}" value="${status.value}">
					<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				
					
					 <c:forEach var="level"  items="${divisionLevels}">
					 <option value="${level.id }" ${level.id == result.divisionLevel.id ? 'selected' :'' }>
					 ${ level.name}
					  </option>
					 </c:forEach>
					</select> 				
			</spring:bind>
		</c:if>	
		
		<c:if test="${parent.divisionLevel==null}">
		  ${parent.ardesc }
		
		</c:if>
		</c:if>
		<c:if test="${(parent==null || parent=='') && (result.id==null || result.id=='')}">
		
		  <spring:bind path="result.divisionLevel">
					 <select name="${status.expression}" value="${status.value}">
					<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				
					
					 <c:forEach var="level"  items="${divisionLevels}">
					 <option value="${level.id }" ${level.id == result.divisionLevel.id ? 'selected' :'' }>
					 ${ level.name}
					  </option>
					 </c:forEach>
					</select> 				
			</spring:bind>
		</c:if>
		<c:if test="${result!=null && result!=''}">
		  ${result.divisionLevel.name}   
		</c:if>		
		  </td>  
		  </tr>
		  
        <!--   <c:if test="${isLeaf != 'true'}"> 
						
							<tr>
								<td nowrap class="formReq">
								<abc:i18n property="hr.caption.isLast"/>
								<fmt:message key="hr.caption.isLast"/></td>
								<td class="formBodControl">
								<spring:bind path="result.isLast">
									<abc:i18n property="gl.caption.false"/>
									<input  type="radio" name ="${status.expression}" id ="${status.expression}" value=false ${ result.isLast == 'false' ? ' checked' : ''} ><fmt:message key="gl.caption.false"/></input>
		
									<abc:i18n property="gl.caption.true"/>
									<input  type="radio" name ="${status.expression}" id ="${status.expression}"  value=true ${ result.isLast == 'true' ? ' checked' : ''} ><fmt:message key="gl.caption.true"/></input>
								</spring:bind></td>
							</tr>
						</c:if>
						
                    -->
           </c:if>
           
           <c:if test="${copyId!=null && copyId!=''}">
              
              <tr>
			<td nowrap class="formReq">
				<abc:i18n property="hr.caption.copyFrom" />
				<fmt:message key="hr.caption.copyFrom" />
			</td>
			<td class="formBodControl">
				
				<select name="parent" id="parent" onChange="changeList()">
				<option value=""><fmt:message key="commons.caption.select" /></option>
					<c:forEach items="${dataList}" var="dataDiv">
						<option value="${dataDiv.id}" ${dataDiv.id == trainingExam.trainingProgram.id ?' selected' : ''}>${dataDiv.ardesc}</option>
					</c:forEach>
				</select>
				
			</td>			
		</tr>
		
		
		  <tr>
			<td nowrap class="formReq">
				<abc:i18n property="hr.caption.branchesToBeAdded" />
				<fmt:message key="hr.caption.branchesToBeAdded" />
			</td>
			<td class="formBodControl">
				<select multiple size="6" name="leafsList" id="leafsList" >
					<c:forEach items="${leafs}" var="leaf">
						<c:set var="isSelected" value=""/>
						<c:forEach items="${result.childs}" var="child">
							<c:if test="${child.id==leaf.id}">
								<c:set var="isSelected" value="selected"/>
							</c:if>
						</c:forEach>
						<option value="${leaf.id}" ${isSelected}>${leaf.ardesc}</option>
					</c:forEach>
				</select>
			</td>
		</tr> 
           
           </c:if>
		  
		  
               <tr id="btn">
							<td colspan="2" align="center">
							<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" onclick="detectForm();"/>&nbsp;&nbsp;&nbsp; 
								<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
							</td>
							
					</tr>
</table>
</td>
</tr>
</form>

</table>
</td>


</tr>
</table>
</td>
</tr>
</table>




		
<%@ include file="/web/common/includes/footer.jsp"%>

 
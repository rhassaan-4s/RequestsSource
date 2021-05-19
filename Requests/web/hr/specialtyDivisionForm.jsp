 <jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree"%>


<abc:security property="84"/><br>

<!--  <STYLE type='text/css'>
textarea {overflow-x: hidden; overflow-y: scroll}
</STYLE>-->
<script type="text/javascript">



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
	//alert('leafsList>>>>>>>>>>'+leafsList);
	if(parent.value !="")
	{	
		DWRUtil.removeAllOptions(leafsList);
		hrManager.getLeafsByParentId(putList,parent.value,'HRQualificationDivision');
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
			
					<c:set var="i" value="coursesDivTree" />
		
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
													href="coursesDivisionTree.html?expand=<tree:nodeId node="tree.node"/>&coursesDivisionId=${result.id}"><img
													src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="coursesDivisionTree.html?expand=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
											</c:otherwise>
										</c:choose>
									</tree:nodeMatch><tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="true" isLastChild="false">
										<c:choose>
											<c:when test="${result.id != null}">
												<a
													href="coursesDivisionTree.html?coursesDivisionId=${result.id}&collapse=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_expandedMidNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="coursesDivisionTree.html?collapse=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_expandedMidNode.gif" border="0"></a>
											</c:otherwise>
										</c:choose>
									</tree:nodeMatch>
									<tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="false" isLastChild="true">
										<c:choose>
											<c:when test="${result.id != null}">
												<a
													href="coursesDivisionTree.html?coursesDivisionId=${result.id}&expand=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_collapsedLastNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="coursesDivisionTree.html?expand=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_collapsedLastNode.gif" border="0"></a>
											</c:otherwise>
										</c:choose>
									</tree:nodeMatch>
									<tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="true" isLastChild="true">
										<c:choose>
											<c:when test="${result.id != null}">
												<a
													href="coursesDivisionTree.html?coursesDivisionId=${result.id}&collapse=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_expandedLastNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="coursesDivisionTree.html?collapse=<tree:nodeId node="tree.node"/>"><img
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
											href="coursesDivisionTree.html?coursesDivisionId=<tree:nodeId node="tree.node"/>&select=<tree:nodeId node="tree.node"/>">
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
<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px" align="left" >
   <form id="coursesDivisionForm" name="coursesDivisionForm" method="POST"	action="<c:url value="/hr/coursesDivisionForm.html"/>">
   
   <input type="hidden"  id="coursesDivisionId" name="coursesDivisionId" value="${coursesDivisionId}"/>
   <input type="hidden" size=20 maxlength=40 name="parentId" value="${parentId}" />
		<input type="hidden" size=20 maxlength=40 name="long" value="${long}" />
		<input type="hidden" size=20 maxlength=40 name="isLeaf" value="${isLeaf}" />
		<input type="hidden" size=20 maxlength=40 name="getIsLast" value="${getIsLast}" />
	
   <tr>
		<td colspan="2">
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
  
  	<table  align="left" width="66%" class="sofT"  >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap><abc:i18n
					property="hr.header.addInternalDivision" /><fmt:message
					key="hr.header.addInternalDivision" /></td>
			</tr>
	 <c:if test="${copyId == null || copyId==''}">
		    <tr>
		  
		  <c:set var="check"  value=""/>
		  <c:if test="${coursesDivisionId!=null && coursesDivisionId!=''}">
					 <c:set var="check"  value="disabled"/>
		</c:if>
	
		
		        <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.code"/>
								<fmt:message key="commons.caption.code"/></td>
					
										
				<td class="formBodControl">
					<spring:bind path="result.code">
						<input size="8" maxlength="3" type="text"	name="${status.expression}" value="${status.value}" ${check}"/> &nbsp;${parentLongCode}				
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
		  
		  
          <c:if test="${isLeaf != 'true'}"> 
						
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
							<c:forEach items="${courseDivList}" var="courseDiv">
								<option value="${courseDiv.id}" ${courseDiv.id == trainingExam.trainingProgram.id ?' selected' : ''}>${courseDiv.ardesc}</option>
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
								<c:forEach items="${coursesDivision.childs}" var="child">
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
							<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp; 
								<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
							</td>
							
					</tr>
</table>

</form>

</table>
</td>


</tr>
</table>



		
<%@ include file="/web/common/includes/footer.jsp"%>

 
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
<form id="myForm" name="myForm" method="POST"	action="<c:url value="divisionOrder.html?formType=${formType }"/>">
<table cellspacing="0" cellpadding="0" border="0" align="right">
			     
							<c:if test="${formType=='internal'}">
							<c:set var="i" value="internalDivisionTree" />
							<c:set var="link" value="divisionOrder.html" />
						
						</c:if>
						 <c:if test="${formType=='qualification'}">
							<c:set var="i" value="qualificationDivisionTree" />
							<c:set var="link" value="divisionOrder.html" />
						
						</c:if>
						 <c:if test="${formType=='geographical'}">
							<c:set var="i" value="geographicalDivisionTree" />
							<c:set var="link" value="divisionOrder.html" />
							
						</c:if>
						 <c:if test="${formType=='specialty'}">
							<c:set var="i" value="specialtyDivisionTree" />
							<c:set var="link" value="divisionOrder.html" />
						
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
										
												<a
													href="${link}?expand=<tree:nodeId node="tree.node"/>&orderId=<tree:nodeId node="tree.node"/>&formType=${formType }"><img
													src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
											
									</tree:nodeMatch><tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="true" isLastChild="false">
										
												<a
													href="${link}?orderId=<tree:nodeId node="tree.node"/>&collapse=<tree:nodeId node="tree.node"/>&formType=${formType }"><img
													src="/Requests/web/gl/images/ar_expandedMidNode.gif" border="0"></a>
											
									</tree:nodeMatch>
									<tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="false" isLastChild="true">
												<a
													href="${link}?expand=<tree:nodeId node="tree.node"/>&formType=${formType }"><img
													src="/Requests/web/gl/images/ar_collapsedLastNode.gif" border="0"></a>
										
									</tree:nodeMatch>
									<tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="true" isLastChild="true">
									
											
												<a
													href="${link}?collapse=<tree:nodeId node="tree.node"/>&formType=${formType }"><img
													src="/Requests/web/gl/images/ar_expandedLastNode.gif" border="0"></a>
											
										
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
											href="${link}?orderId=<tree:nodeId node="tree.node"/>&select=<tree:nodeId node="tree.node"/>&formType=${formType }">
										<span style="Font-Size: 14px;"> <tree:nodeName
											node="tree.node" /> </span> </a>
									</tree:nodeMatch></td>
								</tr>
							</table>
							</td>
						</tr>
					</tree:tree>
					</table>

<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px" align="center" >
   
   
  	
   <tr>
		<td colspan="2">
			<spring:bind path="orderCommand.*">
						<c:if test="${not empty status.errorMessages}">
							<div><c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
								</font>
							</c:forEach></div>
						</c:if>
			</spring:bind>
			</td>
  </tr>
  
  
  	<table  align="center" width="66%" class="sofT"  >
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
			
			<tr>
			<td>
	<table >
           <tr>
		<td colspan="2" valign="top">

		<table cellspacing="0" cellpadding="0" border="0" align="right"
			width="100%">
				
				<tr>
			<td>
			
					</td>
		
</tr>
<tr>
<td valign="top">



 <input type="hidden"  id="orderId" name="orderId" value="${orderId}"/>
   <input type="hidden" size=20 maxlength=40 name="parentId" value="${parentId}" />
		<input type="hidden" size=20 maxlength=40 name="long" value="${long}" />
		<input type="hidden" size=20 maxlength=40 name="isLeaf" value="${isLeaf}" />
		<input type="hidden" size=20 maxlength=40 name="getIsLast" value="${getIsLast}" />
		<input type="hidden" size=20 maxlength=40 name="copyId" value="${copyId}" />
		<INPUT type="hidden" size="20" name="formType" value="${formType}"/>
		
		</td>
		</tr>
		
			   <c:forEach var="division"  items="${orderCommand.divisionObjects}" varStatus="loop">
					
		        <tr>
		  			<td>
					<input type="text"  readonly name="ardesc" value="${ division.ardesc}" />
					  
					 
			       </td>
			       <td>
			         <spring:bind path="orderCommand.divisionObjects[${loop.index}].order">
					<input type="text"  id="${status.expression }"  name="${status.expression }" value="${(loop.index+1)*10}" />
				   </spring:bind> 
					 
			       </td>
			    </tr>
		      </c:forEach>
		      <tr height="10">
		         
		      </tr>
               <tr id="btn">
							<td colspan="2" align="center" nowrap="nowrap"> 
							<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" onclick="detectForm();"/>&nbsp;&nbsp;&nbsp; 
								<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
							</td>
							
			</tr>
</table>

</td>
</tr>

</table>


</table>

</form>


		
<%@ include file="/web/common/includes/footer.jsp"%>

 
<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree"%>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>

<abc:security property="77" />
<br>
<script type="text/javascript">
function changeColor()
{
	//alert ('inside changeColor');
	var mySpan=document.getElementById("mySpan");
   var nodeId=document.getElementById("nodeId");
  // alert('nodeId>>>>>>>>>>'+nodeId);
}

</script>
	<div style="width: 650px; height: 250px;overflow: scroll;">
	<table rules="all" align="center" width="70%" class="sofT" >
	
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap>
		<c:if test="${formType=='internal'}">
		<abc:i18n property="hr.header.internalDivisionTree" />
		<fmt:message key="hr.header.internalDivisionTree" />
		</c:if>	
		
		<c:if test="${formType=='qualification'}">
		<abc:i18n property="hr.header.qualificationDivisionTree" />
		<fmt:message key="hr.header.qualificationDivisionTree" />
	
		</c:if>
		
		<c:if test="${formType=='geographical'}">
		<abc:i18n property="hr.header.geographicalDivisionTree" />
		<fmt:message key="hr.header.geographicalDivisionTree" />
	
		</c:if>	
		
		<c:if test="${formType=='specialty'}">
		<abc:i18n property="hr.header.specialtyDivisionTree" />
		<fmt:message key="hr.header.specialtyDivisionTree" />
	
		</c:if>		
		</td>
			</tr>
	<tr>
	
		<td colspan="2" valign="top">

		<table cellspacing="0" cellpadding="0" border="0" width="80%">
			
				<tr>
					<td >
					
					<abc:i18n property="hr.button.addRoot" />
					<c:if test="${formType=='internal'}">
					 <input
						type="button" value="<fmt:message key="hr.button.addRoot"/>"
						name="addRoot" class="button"
						onclick="window.location='internalDivisionForm.html'"></input>
						</c:if>
						<c:if test="${formType=='geographical'}">
						 <input
						type="button" value="<fmt:message key="hr.button.addRoot"/>"
						name="addRoot" class="button"
						onclick="window.location='geographicalDivisionForm.html'"></input>
						</c:if>
						
						<c:if test="${formType=='qualification'}">
						 <input
						type="button" value="<fmt:message key="hr.button.addRoot"/>"
						name="addRoot" class="button"
						onclick="window.location='qualificationDivisionForm.html'"></input>
						</c:if>
						
						<c:if test="${formType=='specialty'}">
						 <input
						type="button" value="<fmt:message key="hr.button.addRoot"/>"
						name="addRoot" class="button"
						onclick="window.location='specialtyDivisionForm.html'"></input>
						</c:if>
						
					</td>
					
					<td>
					    <input	type="button" value="<fmt:message key="hr.button.expandAll"/>"	name="expand" class="button" onclick="window.location='${link }?expand=true'"></input>
					</td>
				</tr>
			
			<tr>
				<td width="70%" valign="top">
					<table cellspacing="0" cellpadding="0" border="0" width="100%">
					    <c:if test="${formType=='internal'}">
							<c:set var="i" value="internalDivisionTree" />
							<c:set var="link" value="internalDivisionTree.html" />
							<c:set var="formLink" value="internalDivisionForm.html" />
							<c:set var="className" value="HRInternalDivision" />
						</c:if>
						 <c:if test="${formType=='qualification'}">
							<c:set var="i" value="qualificationDivisionTree" />
							<c:set var="link" value="qualificationDivisionTree.html" />
							<c:set var="formLink" value="qualificationDivisionForm.html" />
							<c:set var="className" value="HRQualificationDivision" />
						</c:if>
						 <c:if test="${formType=='geographical'}">
							<c:set var="i" value="geographicalDivisionTree" />
							<c:set var="link" value="geographicalDivisionTree.html" />
							<c:set var="formLink" value="geographicalDivisionForm.html" />
							<c:set var="className" value="HRGeographicalDivision" />
						</c:if>
						<c:if test="${formType=='specialty'}">
							<c:set var="i" value="specialtyDivisionTree" />
							<c:set var="link" value="specialtyDivisionTree.html" />
							<c:set var="formLink" value="specialtyDivisionForm.html" />
							<c:set var="className" value="HRSpecialtyDivision" />
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
											border="0"></tree:nodeIndentBlankSpace></tree:nodeIndent>
										</td>

										<td valign="top" >
										<div id="drag">
											<tree:nodeMatch node="tree.node" hasChildren="true"
												expanded="false" isLastChild="false">
												<!-- this is for drawing the folder + of nodes that is not root -->
												<c:choose>
													<c:when test="${result.id != null}">
													
														<a 
															href="${link}?expand=<tree:nodeId node="tree.node"/>&resultId=${result.id}"><img
															src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
													</c:when>
													<c:otherwise>
														<a 
															href="${link}?expand=<tree:nodeId node="tree.node"/>"><img
															src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"> </a>
													</c:otherwise>
												</c:choose>
											</tree:nodeMatch>
											</div>
											
											<tree:nodeMatch node="tree.node"
												hasChildren="true" expanded="true" isLastChild="false">
												<!-- this is for drawing the folder - of nodes that is not root -->
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
												<!-- this is for drawing the folder + of root node -->
												<c:choose>
													<c:when test="${result.id != null}">
														<a 
															href="${link}?resultId=${result.id}&expand=<tree:nodeId node="tree.node"/>" > <img
															src="/Requests/web/gl/images/ar_collapsedLastNode.gif"  border="0"></a>
													</c:when>
													<c:otherwise>
													
														<a  
															href="${link}?expand=<tree:nodeId node="tree.node"/>" ><img
															src="/Requests/web/gl/images/ar_collapsedLastNode.gif"  border="0"></a>
													</c:otherwise>
												</c:choose>
											</tree:nodeMatch>
										
											<tree:nodeMatch node="tree.node"
												hasChildren="true" expanded="true" isLastChild="true">
												<!-- this is for drawing the folder - of root node -->
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
												<!-- this is for drawing the lines  between nodes node -->
												<img src="/Requests/web/gl/images/ar_noChildrenMidNode.gif"
												border="0">
											</tree:nodeMatch>
											
											<tree:nodeMatch node="tree.node"
												hasChildren="false" isLastChild="true">
												<!-- this is for drawing the lines  between nodes node -->
												<img src="/Requests/web/gl/images/ar_noChildrenLastNode.gif"
												border="0">
											</tree:nodeMatch>
										</td>

										<td valign="top">
											<tree:nodeMatch node="tree.node"
												hasChildren="true" expanded="true">
													<!-- this is for drawing the open folder image  of node -->
												<img src="/Requests/web/gl/images/openFolder.gif" border="0">
											</tree:nodeMatch>
											
											<tree:nodeMatch node="tree.node"
												hasChildren="true" expanded="false">
												<!-- this is for drawing the closed folder image  of node -->
												<img src="/Requests/web/gl/images/ar_closedFolder.gif" border="0">
											</tree:nodeMatch>
											
											<tree:nodeMatch node="tree.node"
												hasChildren="false">
												<!-- this is for drawing the non folder image(has no children)  of node -->
												<img src="/Requests/web/gl/images/nonFolder.gif" border="0">
											</tree:nodeMatch>
										</td>

										<td valign="top">
											<tree:nodeMatch node="tree.node">
												&nbsp;
												<!-- this is for drawing the links  of nodes -->
												<a title="<tree:nodeToolTip node="tree.node"/>"
													href="${link}?resultId=<tree:nodeId node="tree.node"/>&select=<tree:nodeId node="tree.node"/>" onclick="changeColor();">
														
											           <input type="hidden" id="myInput" value="<tree:nodeId node="tree.node"/>" >
											           <c:set  var="nodeId" value=""  />
											          
											
														<span id="mySpan1" style="Font-Size: 14px; font-family: fantasy ;">
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
			
			<td valign="top" align="center">
				<c:if test="${result.id != null}">
					<table cellpadding="0" cellspacing="1" border="0">	
						<tr valign="top">
							<td bgcolor="#DDDDDD"><abc:i18n property="gl.caption.longCode" /> <fmt:message
								key="gl.caption.code" />
							</td>
							<td>&nbsp;&nbsp; ${result.longCode}<br>
							</td>
						</tr>
						<tr valign="top">
							<td bgcolor="#DDDDDD"><abc:i18n
								property="commons.caption.description" /> <fmt:message
								key="commons.caption.description" />
							</td>
							<td>&nbsp;&nbsp; ${result.ardesc}<br>
							</td>
						</tr>
					
					
					<!--  	<c:if test="${isLeaf != 'true'}">
							<tr valign="top">
								<td bgcolor="#DDDDDD"><abc:i18n
									property="hr.caption.isLast" /> <fmt:message
									key="hr.caption.isLast" />
								</td>
								<td>&nbsp;&nbsp; <c:if test ="${result.isLast == true}"><fmt:message key ="gl.caption.true"/></c:if>
									<c:if test ="${result.isLast == false}"><fmt:message key ="gl.caption.false"/></c:if>
									<br>
								</td>
							</tr>
						</c:if>-->
						
						
						
				
					</table>
					</c:if>
					
								<form action="html_form_action.asp" method="get">
									<table>
									<input type="hidden" name="formType" id="formType" value="${formType}" />
					<tr valign="top">
							<td nowrap align="left">
									<c:if test="${resultId!=null ||!resultId==''}">
										<c:choose>
											<c:when test="${result.isLast == 'true'}">
												<abc:i18n property="commons.button.add" />
												<input type="button"  
													value="<fmt:message key="commons.button.add"/>" name="edit"
													class="button"
													onclick="window.location='${formLink}?parentId=${result.id}&isLeaf=true'"></input>
											</c:when>
											<c:otherwise>
												
												<input type="button"
													value="<fmt:message key="commons.button.add"/>" name="edit"
													class="button"
													onclick="window.location='${formLink}?parentId=${result.id}&isLeaf=false'"></input>
												
											</c:otherwise>
										</c:choose>
							
		
								
										<abc:i18n property="commons.button.edit" />
										<input type="button" value="<fmt:message key="commons.button.edit"/>"
											name="edit" class="button"
											onclick="window.location='${formLink}?resultId=${result.id}&isLeaf=${isLeaf}'"></input>

										<abc:i18n property="commons.button.delete" />
										<input
											type="button" value="<fmt:message key="commons.button.delete"/>"
											name="delete" class="button"
											onclick="window.location='${link}?deleteId=${result.id}'"></input>
								
										       <abc:i18n property="hr.button.copyLeafs" />
												<input
													type="button" value="<fmt:message key="hr.button.copyLeafs"/>"
													name="delete" class="button"
													onclick="window.location='copyController.html?copyId=${result.id}&className=${className}'"></input>
										
										
										<tr>
									    <td align="center">
									   <input
											type="button" value="<fmt:message key="hr.button.order"/>"
											name="order" class="button"
											onclick="window.location='divisionOrder.html?orderId=${result.id}&formType=${formType }'"></input>
						              </td>
									</tr>
									</c:if>
									
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
</div>
<%@ include file="/web/common/includes/footer.jsp"%>
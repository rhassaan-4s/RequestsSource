<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree"%>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>

<abc:security property="80" />
<br>

	<table rules="all" align="center" width="70%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap>
			
		<abc:i18n property="hr.header.geographicalDivisionTree" />
		<fmt:message key="hr.header.geographicalDivisionTree" />
				
		</td>
			</tr>
	<tr>
	
		<td colspan="2" valign="top">

		<table cellspacing="0" cellpadding="0" border="0" width="80%">
			
				<tr>
					<td colspan="2">
					<c:if test="${firstTime==true}">
					<abc:i18n property="hr.button.addRoot" /> <input
						type="button" value="<fmt:message key="hr.button.addRoot"/>"
						name="addRoot" class="button"
						onclick="window.location='geographicalDivisionForm.html'"></input>
						</c:if>
					</td>
				</tr>
			
			<tr>
				<td width="70%" valign="top">
					<table cellspacing="0" cellpadding="0" border="0" width="100%">
							<c:set var="i" value="geographicalDivisionTree" />
						

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
													<c:when test="${result.id != null}">
														<a
															href="geographicalDivisionTree.html?expand=<tree:nodeId node="tree.node"/>&isFinancial=${isFinancial}&geographicalDivisionId=${result.id}"><img
															src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
													</c:when>
													<c:otherwise>
														<a
															href="geographicalDivisionTree.html?expand=<tree:nodeId node="tree.node"/>&isFinancial=${isFinancial}"><img
															src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
													</c:otherwise>
												</c:choose>
											</tree:nodeMatch>
											
											<tree:nodeMatch node="tree.node"
												hasChildren="true" expanded="true" isLastChild="false">
												<c:choose>
													<c:when test="${result.id != null}">
														<a
															href="geographicalDivisionTree.html?geographicalDivisionId=${result.id}&isFinancial=${isFinancial}&collapse=<tree:nodeId node="tree.node"/>"><img
															src="/Requests/web/gl/images/ar_expandedMidNode.gif" border="0"></a>
													</c:when>
													<c:otherwise>
														<a
															href="geographicalDivisionTree.html?collapse=<tree:nodeId node="tree.node"/>&isFinancial=${isFinancial}"><img
															src="/Requests/web/gl/images/ar_expandedMidNode.gif" border="0"></a>
													</c:otherwise>
												</c:choose>
											</tree:nodeMatch>
											
											<tree:nodeMatch node="tree.node"
												hasChildren="true" expanded="false" isLastChild="true">
												<c:choose>
													<c:when test="${result.id != null}">
														<a
															href="geographicalDivisionTree.html?geographicalDivisionId=${result.id}&expand=<tree:nodeId node="tree.node"/>"><img
															src="/Requests/web/gl/images/ar_collapsedLastNode.gif" border="0"></a>
													</c:when>
													<c:otherwise>
														<a
															href="geographicalDivisionTree.html?expand=<tree:nodeId node="tree.node"/>"><img
															src="/Requests/web/gl/images/ar_collapsedLastNode.gif" border="0"></a>
													</c:otherwise>
												</c:choose>
											</tree:nodeMatch>
										
											<tree:nodeMatch node="tree.node"
												hasChildren="true" expanded="true" isLastChild="true">
												<c:choose>
													<c:when test="${result.id != null}">
														<a
															href="geographicalDivisionTree.html?geographicalDivisionId=${result.id}&collapse=<tree:nodeId node="tree.node"/>"><img
															src="/Requests/web/gl/images/ar_expandedLastNode.gif" border="0"></a>
													</c:when>
													<c:otherwise>
														<a
															href="geographicalDivisionTree.html?collapse=<tree:nodeId node="tree.node"/>&isFinancial=${isFinancial}"><img
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
													href="geographicalDivisionTree.html?geographicalDivisionId=<tree:nodeId node="tree.node"/>&select=<tree:nodeId node="tree.node"/>">
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
					
					
						<c:if test="${isLeaf != 'true'}">
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
						</c:if>
						
						
					
					</table>
					</c:if>
					
								<form action="html_form_action.asp" method="get">
									<table>
					<tr valign="top">
							<td nowrap align="center">
									<c:if test="${geographicalDivisionId!=null ||geographicalDivisionId==''}">
										<c:choose>
											<c:when test="${result.isLast == 'true'}">
												<abc:i18n property="commons.button.add" />
												<input type="button"  
													value="<fmt:message key="commons.button.add"/>" name="edit"
													class="button"
													onclick="window.location='geographicalDivisionForm.html?parentId=${result.id}&isLeaf=true'"></input>
											</c:when>
											<c:otherwise>
												
												<input type="button"
													value="<fmt:message key="commons.button.add"/>" name="edit"
													class="button"
													onclick="window.location='geographicalDivisionForm.html?parentId=${result.id}&isLeaf=false'"></input>
												
											</c:otherwise>
										</c:choose>
							
		
								
										<abc:i18n property="commons.button.edit" />
										<input type="button" value="<fmt:message key="commons.button.edit"/>"
											name="edit" class="button"
											onclick="window.location='geographicalDivisionForm.html?geographicalDivisionId=${result.id}&isLeaf=${isLeaf}'"></input>

										<abc:i18n property="commons.button.delete" />
										<input
											type="button" value="<fmt:message key="commons.button.delete"/>"
											name="delete" class="button"
											onclick="window.location='geographicalDivisionTree.html?deleteId=${result.id}'"></input>
											
											<c:if test="${canCopyLeafs=='true'}">
										       <abc:i18n property="hr.button.copyLeafs" />
												<input
													type="button" value="<fmt:message key="hr.button.copyLeafs"/>"
													name="delete" class="button"
													onclick="window.location='geographicalDivisionForm.html?copyId=${result.id}'"></input>
										</c:if>
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
<%@ include file="/web/common/includes/footer.jsp"%>
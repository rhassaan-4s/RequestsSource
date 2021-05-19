<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>

<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree"%>

<abc:security property=""/><br>

<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   <form id="internalDivisionBranchesView" name="internalDivisionBranchesView" method="POST" action="<c:url value="/hr/internalDivisionBranchesView.html"/>">
 
			<input type="hidden" value="${typeId} name="typeId" id="typeId">
			
			<tr>
				<td>
					<table rules="all" align="center" width="70%" class="sofT">
			<tr>
				<td colspan="8" class="helpTitle"><abc:i18n
					property="hr.header.codes" /><fmt:message
					key="hr.header.codes" /> ${type.nameForCertainLocale }
					</td>
			</tr>

			<tr>

			
				<td class="helpHed" nowrap="nowrap"><abc:i18n
					property="commons.caption.code" /> <fmt:message
					key="commons.caption.code" /></td>
				<td class="helpHed" nowrap="nowrap"><abc:i18n
					property="commons.caption.name" /> <fmt:message
					key="commons.caption.name" /></td>
				<td class="helpHed" nowrap="nowrap"><abc:i18n
					property="commons.caption.englishName" /> <fmt:message
					key="commons.caption.englishName" /></td>
				
				<td class="helpHed">
				</td>
			</tr>
						   <c:forEach items="${records}" var="record">
							   <tr height=20 bgcolor="#F8F8F8">
                                  
							      <td class="helpBod" nowrap>
							         ${record.internal_division_branch }
							
								  </td>
								  
								  <td class="helpBod" nowrap>
							         ${record.name }
							
								  </td>
								  
								   <td class="helpBod" nowrap>
							         ${record.ename }
							
								  </td>
								  
								  <td class="helpBod" nowrap><abc:i18n
									property="commons.button.edit" /><a
									href="internalDivisionBranchForm.html?internalDivisionBranchId=${record.id}&typeId=${typeId }"><fmt:message
									key="commons.button.edit" /></a></td>
									        </tr>
						   </c:forEach>
					
					</table>
				
				</td>
			</tr>
			
			<tr>
		<td colspan="2" align="center">
		<br>
			<abc:i18n property="commons.button.add"/>
			<input type="button" value="<fmt:message key="commons.button.add"/>" name="add" class="button"	onclick="window.location='internalDivisionBranchForm.html?typeId=${typeId}'"></input>
		</td>
	</tr>
			
			
	</table>
   
   </form>

</table>





	
	<table rules="all" align="center" width="70%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap>
				<c:if test="${isFinancial == 'true'}">
		<abc:i18n property="gl.header.viewAccounts" />
		<fmt:message key="gl.header.viewAccounts" />
				</c:if>
		<c:if test="${isFinancial != 'true'}">
		<abc:i18n property="gl.header.viewAnalysis" />
		<fmt:message key="gl.header.viewAnalysis" />
		</c:if>
		</td>
			</tr>
	<tr>
	
		<td colspan="2" valign="top">

		<table cellspacing="0" cellpadding="0" border="0" width="80%">
			<c:if test="${canAddCategoryAccount == 'true'}">
			<c:if test="${isFinancial == 'true'}">
				<tr>
					<td colspan="2"><abc:i18n property="gl.button.addRootAccount" /> <input
						type="button" value="<fmt:message key="gl.button.addRootAccount"/>"
						name="addRoot" class="button"
						onclick="window.location='accountForm.html?isFinancial=${isFinancial}'"></input>
					</td>
				</tr>
			</c:if>
			<c:if test="${isFinancial != 'true'}">
				<tr>
					<td colspan="2"><abc:i18n property="gl.button.addRootAnalysis" /> <input
						type="button" value="<fmt:message key="gl.button.addRootAnalysis"/>"
						name="addRoot" class="button"
						onclick="window.location='accountForm.html?isFinancial=${isFinancial}'"></input>
					</td>
				</tr>
			</c:if>
			</c:if>
			<tr>
				<td width="70%" valign="top">
					<table cellspacing="0" cellpadding="0" border="0" width="100%">
						<c:if test="${isFinancial=='true'}">
							<c:set var="i" value="financialTree" />
						</c:if>
						<c:if test="${isFinancial=='false'}">
							<c:set var="i" value="analysisTree" />
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
											<tree:nodeMatch node="tree.node" hasChildren="true"
												expanded="false" isLastChild="false">
												<c:choose>
													<c:when test="${result.id != null}">
														<a
															href="account.html?expand=<tree:nodeId node="tree.node"/>&isFinancial=${isFinancial}&accountId=${result.id}"><img
															src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
													</c:when>
													<c:otherwise>
														<a
															href="account.html?expand=<tree:nodeId node="tree.node"/>&isFinancial=${isFinancial}"><img
															src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
													</c:otherwise>
												</c:choose>
											</tree:nodeMatch>
											
											<tree:nodeMatch node="tree.node"
												hasChildren="true" expanded="true" isLastChild="false">
												<c:choose>
													<c:when test="${result.id != null}">
														<a
															href="account.html?accountId=${result.id}&isFinancial=${isFinancial}&collapse=<tree:nodeId node="tree.node"/>"><img
															src="/Requests/web/gl/images/ar_expandedMidNode.gif" border="0"></a>
													</c:when>
													<c:otherwise>
														<a
															href="account.html?collapse=<tree:nodeId node="tree.node"/>&isFinancial=${isFinancial}"><img
															src="/Requests/web/gl/images/ar_expandedMidNode.gif" border="0"></a>
													</c:otherwise>
												</c:choose>
											</tree:nodeMatch>
											
											<tree:nodeMatch node="tree.node"
												hasChildren="true" expanded="false" isLastChild="true">
												<c:choose>
													<c:when test="${result.id != null}">
														<a
															href="account.html?accountId=${result.id}&isFinancial=${isFinancial}&expand=<tree:nodeId node="tree.node"/>"><img
															src="/Requests/web/gl/images/ar_collapsedLastNode.gif" border="0"></a>
													</c:when>
													<c:otherwise>
														<a
															href="account.html?expand=<tree:nodeId node="tree.node"/>&isFinancial=${isFinancial}"><img
															src="/Requests/web/gl/images/ar_collapsedLastNode.gif" border="0"></a>
													</c:otherwise>
												</c:choose>
											</tree:nodeMatch>
										
											<tree:nodeMatch node="tree.node"
												hasChildren="true" expanded="true" isLastChild="true">
												<c:choose>
													<c:when test="${result.id != null}">
														<a
															href="account.html?accountId=${result.id}&isFinancial=${isFinancial}&collapse=<tree:nodeId node="tree.node"/>"><img
															src="/Requests/web/gl/images/ar_expandedLastNode.gif" border="0"></a>
													</c:when>
													<c:otherwise>
														<a
															href="account.html?collapse=<tree:nodeId node="tree.node"/>&isFinancial=${isFinancial}"><img
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
													href="account.html?isFinancial=${isFinancial}&accountId=<tree:nodeId node="tree.node"/>&select=<tree:nodeId node="tree.node"/>">
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
							<td>&nbsp;&nbsp; ${result.arDesc}<br>
							</td>
						</tr>
						<tr valign="top">
							<td bgcolor="#DDDDDD"><abc:i18n
								property="gl.caption.accountStatus" /> <fmt:message
								key="gl.caption.accountStatus" />
							</td>
							<td>&nbsp;&nbsp; <c:if test="${result.isLocked == true }"><fmt:message key="gl.caption.locked"/></c:if>
								<c:if test="${result.isLocked == false }"> <fmt:message key="gl.caption.unlocked"/></c:if><br>
							</td>
						</tr>
						<c:if test="${isFinancial == 'true'}">
								<tr valign="top">
									<td bgcolor="#DDDDDD"><abc:i18n
										property="gl.caption.closureAccount" /> <fmt:message
										key="gl.caption.closureAccount" />
									</td>
									<td>&nbsp;&nbsp;<c:if test ="${result.closureAccount == true}"><fmt:message key ="gl.caption.true"/></c:if>
									<c:if test ="${result.closureAccount == false}"><fmt:message key ="gl.caption.false"/></c:if>
									</td>
								</tr>
								<tr valign="top">
									<td bgcolor="#DDDDDD"><abc:i18n
										property="gl.caption.accountType" /> <fmt:message
										key="gl.caption.accountType" />
									</td>
									<td>&nbsp;&nbsp;${result.accountType.description}
									</td>
								</tr>
							</c:if>
						<c:if test="${isLeaf != 'true'}">
							<tr valign="top">
								<td bgcolor="#DDDDDD"><abc:i18n
									property="gl.caption.isLastAccount" /> <fmt:message
									key="gl.caption.isLastAccount" />
								</td>
								<td>&nbsp;&nbsp; <c:if test ="${result.isLast == true}"><fmt:message key ="gl.caption.true"/></c:if>
									<c:if test ="${result.isLast == false}"><fmt:message key ="gl.caption.false"/></c:if>
									<br>
								</td>
							</tr>
						</c:if>
						<c:if test="${isLeaf == 'true'}">
							
							<c:if test="${isFinancial == 'true'}">
								<tr valign="top">
									<td bgcolor="#DDDDDD"><abc:i18n
										property="gl.caption.canBeAnalyzed" /> <fmt:message
										key="gl.caption.canBeAnalyzed" />
									</td>
									<td>&nbsp;&nbsp; ${result.canBeAnalyzed.description}<br>
									</td>
								</tr>
								<tr valign="top">
									<td bgcolor="#DDDDDD"><abc:i18n
										property="gl.caption.privateAccount" /> <fmt:message
										key="gl.caption.privateAccount" />
									</td>
									<td>&nbsp;&nbsp; <c:if test ="${result.isPrivate == true}"><fmt:message key ="gl.caption.true"/></c:if>
										<c:if test ="${result.isPrivate == false}"><fmt:message key ="gl.caption.false"/></c:if>
										<br>
									</td>
								</tr>
							</c:if>
							
							
						</c:if>
						
						
						
					
						<!--  <c:if test="${isLeaf=='true'}">
							<tr valign="top">
								<td bgcolor="#DDDDDD"><abc:i18n
									property="gl.caption.reportType" /> <fmt:message
									key="gl.caption.reportType" />
								</td>
								<td>&nbsp;&nbsp; ${result.reportType.description}<br>
								</td>
							</tr>
						</c:if>-->
					</table>
					</c:if>
					
								<form action="html_form_action.asp" method="get">
									<table>
					<tr valign="top">
							<td nowrap align="center">	
									<c:if test="${canAddChildAccount == 'true'}">
										<c:choose>
											<c:when test="${result.isLast == 'true'}">
												<abc:i18n property="commons.button.add" />
												<input type="button"  
													value="<fmt:message key="commons.button.add"/>" name="edit"
													class="button"
													onclick="window.location='accountForm.html?parentCategoryId=${result.id}&isFinancial=${isFinancial}&isLeaf=true'"></input>
											</c:when>
											<c:otherwise>
												<c:if test="${isFinancial == true && (result.closureAccount == null || result.closureAccount == false)}">
												<input type="button"
													value="<fmt:message key="commons.button.add"/>" name="edit"
													class="button"
													onclick="window.location='accountForm.html?parentCategoryId=${result.id}&isFinancial=${isFinancial}&isLeaf=false'"></input>
												</c:if>
											</c:otherwise>
										</c:choose>
									</c:if>
		
									<c:if test="${canBeEdited == 'true'}">
										<abc:i18n property="commons.button.edit" />
										<input type="button" value="<fmt:message key="commons.button.edit"/>"
											name="edit" class="button"
											onclick="window.location='accountForm.html?accountId=${result.id}&isFinancial=${isFinancial}&isLeaf=${isLeaf}'"></input>

										<abc:i18n property="commons.button.delete" />
										<input
											type="button" value="<fmt:message key="commons.button.delete"/>"
											name="delete" class="button"
											onclick="window.location='account.html?deleteId=${result.id}&isFinancial=${isFinancial}'"></input>
									</c:if>
									
									
									</td>	
									</tr>
									<tr valign="top">
									<TD nowrap="nowrap" align="center">
									
									<c:if test="${result.isLocked == true }">
										<abc:i18n property="gl.button.unlock" />
										<input type="button" value="<fmt:message key="gl.button.unlock"/>"
											name="unlock" class="button"
											onclick="window.location='account.html?unlockAccountId=${result.id}&isFinancial=${isFinancial}'"></input>
									</c:if>
							
									<c:if test="${result.isLocked == false }">
										<abc:i18n property="gl.button.lock" />
										<input type="button" value="<fmt:message key="gl.button.lock"/>"
											name="lock" class="button"
											onclick="window.location='account.html?lockAccountId=${result.id}&isFinancial=${isFinancial}'"></input>
									</c:if>
									
									</TD>				
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
	
			
<%@ include file="/web/common/includes/footer.jsp" %>
   
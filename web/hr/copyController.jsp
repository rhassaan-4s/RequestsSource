<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree"%>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<script type="text/javascript">
function getSelectedId()
{
	var copyTo=document.getElementById("parent");
//	alert('copyTo>>>>>>>>>>>>>>'+copyTo.value);
	window.location.href='copyFormController.html?copyId=${copyId}&className=${className}&copyTo='+copyTo.value;
	
}
</script>
<form id="myForm" name="myForm" method="POST"	action="<c:url value=""/>">

 <input type="hidden"  id="resultId" name="resultId" value="${resultId}"/>
   <input type="hidden" size=20 maxlength=40 name="parentId" value="${parentId}" />
		<input type="hidden" size=20 maxlength=40 name="long" value="${long}" />
		<input type="hidden" size=20 maxlength=40 name="isLeaf" value="${isLeaf}" />
		<input type="hidden" size=20 maxlength=40 name="getIsLast" value="${getIsLast}" />
		<input type="hidden" size=20 maxlength=40 name="copyId"  id="copyId" value="${copyId}" />
		<INPUT type="hidden" size="20" name="formType" value="${formType}"/>

<table style="padding-right:10px"  align="center" border="0"  class="sofT" width="60%">

                      <c:if test="${className=='HRInternalDivision'}">
							<c:set var="i" value="internalDivisionTree" />
							<c:set var="link" value="copyFormController.html" />
							<c:set var="formLink" value="internalDivisionForm.html" />
							
						</c:if>
						 <c:if test="${className=='HRQualificationDivision'}">
							<c:set var="i" value="qualificationDivisionTree" />
							<c:set var="link" value="copyController.html" />
							<c:set var="formLink" value="qualificationDivisionForm.html" />
							
						</c:if>
						 <c:if test="${className=='HRGeographicalDivision'}">
							<c:set var="i" value="geographicalDivisionTree" />
							<c:set var="link" value="copyController.html" />
							<c:set var="formLink" value="geographicalDivisionForm.html" />
					
						</c:if>
						 <c:if test="${className=='HRSpecialtyDivision'}">
							<c:set var="i" value="specialtyDivisionTree" />
							<c:set var="link" value="copyController.html" />
							<c:set var="formLink" value="specialtyDivisionForm.html" />
							
						</c:if>
						
						<tr height="20">


                        </tr>
                  <tr>
                  <td>
                    <table align="right">
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
													href="${link}?expand=<tree:nodeId node="tree.node"/>&resultId=${result.id}&className=${className}&copyId=${copyId}&copyTo=<tree:nodeId node="tree.node"/>" > <img
													src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="${link}?expand=<tree:nodeId node="tree.node"/>&className=${className}&copyId=${copyId}&copyTo=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_collapsedMidNode.gif" border="0"></a>
											</c:otherwise>
										</c:choose>
									</tree:nodeMatch><tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="true" isLastChild="false">
										<c:choose>
											<c:when test="${result.id != null}">
												<a
													href="${link}?resultId=${result.id}&collapse=<tree:nodeId node="tree.node"/>&className=${className}&copyId=${copyId}&copyTo=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_expandedMidNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="${link}?collapse=<tree:nodeId node="tree.node"/>&className=${className}&copyId=${copyId}&copyTo=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_expandedMidNode.gif" border="0"></a>
											</c:otherwise>
										</c:choose>
									</tree:nodeMatch>
									<tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="false" isLastChild="true">
										<c:choose>
											<c:when test="${result.id != null}">
												<a
													href="${link}?resultId=${result.id}&expand=<tree:nodeId node="tree.node"/>&className=${className}&copyId=${copyId}&copyTo=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_collapsedLastNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="${link}?expand=<tree:nodeId node="tree.node"/>&className=${className}&copyId=${copyId}&copyTo=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_collapsedLastNode.gif" border="0"></a>
											</c:otherwise>
										</c:choose>
									</tree:nodeMatch>
									<tree:nodeMatch node="tree.node"
										hasChildren="true" expanded="true" isLastChild="true">
										<c:choose>
											<c:when test="${result.id != null}">
												<a
													href="${link}?resultId=${result.id}&collapse=<tree:nodeId node="tree.node"/>&className=${className}&copyId=${copyId}&copyTo=<tree:nodeId node="tree.node"/>"><img
													src="/Requests/web/gl/images/ar_expandedLastNode.gif" border="0"></a>
											</c:when>
											<c:otherwise>
												<a
													href="${link}?collapse=<tree:nodeId node="tree.node"/>&className=${className}&copyId=${copyId}&copyTo=<tree:nodeId node="tree.node"/>"><img
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
											href="${link}?resultId=<tree:nodeId node="tree.node"/>&select=<tree:nodeId node="tree.node"/>&className=${className}&copyId=${copyId}&copyTo=<tree:nodeId node="tree.node"/>">
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
					
					
			<!--  		<td>
					<table>
					<tr>
					 <td nowrap class="formReq">
				<abc:i18n property="hr.caption.copyTo" />
				<fmt:message key="hr.caption.copyTo" />
			</td>
			
	      <td class="">
				
				<select name="parent" id="parent" >
				<option value=""><fmt:message key="commons.caption.select" /></option>
					<c:forEach items="${higherDivisions}" var="dataDiv">
						<option value="${dataDiv.id}" ${dataDiv.id == trainingExam.trainingProgram.id ?' selected' : ''}>${dataDiv.ardesc}-(${dataDiv.parent.parent.ardesc })</option>
					</c:forEach>
				</select>
				
			</td>
			
					</tr>
					</table>
					</td> -->
					</tr>
					
					
					<tr height="10">
					</tr>


	 <tr align="center">
	
	<td align="left"> &nbsp;
	
	 </td>

	    <td align="center">
	  <!-- <input
			type="button" value="<fmt:message key="hr.button.copyTo"/>"
			name="order" class="button"
			onclick="getSelectedId();"></input>
            </td>
			-->						
	 </tr> 

</table>


</form>








<%@ include file="/web/common/includes/footer.jsp"%>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<abc:security property=""/><br>
<table width="90%"  border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
	<!-- <tr>
		<td class="tableHeader" height="1" colspan="2"></td>
	</tr>
	<tr>
		<td class="tableHeader">	
			<abc:i18n property="${typeDescription}"/>
			<fmt:message key="commons.caption.browse" />&nbsp;${arDesc}
		</td>
		<td align="left"></td>
	</tr>
	<tr>
		<td colspan="2" bgcolor="#5A718B" height="2"></td>
	</tr>
	<tr>
		<td colspan="2" height="1"></td>
	</tr>

	<tr>
		<td colspan="2" height="20"></td>
	</tr>
	 -->
	<tr>
		<td colspan="2">
			<table rules="all" align="center" width="60%" class="sofT">
			<tr>
				<td colspan="3" class="helpTitle"><abc:i18n
					property="${typeDescription}" /><fmt:message
					key="commons.caption.browse" />&nbsp;${arDesc}</td>
			</tr>
				<tr >
	
					<td class="helpTitle" nowrap>&nbsp;
						<abc:i18n property="commons.caption.description"/>
						<fmt:message key="commons.caption.description" />
									&nbsp;</td>
					<td class="helpTitle" nowrap>&nbsp;</td>
					
					
				</tr>
	
				<c:forEach var="result" items="${typesData}" >
					<tr >
						<td class="helpBod" nowrap="nowrap" >${result.description}</td>
		
						
						<TD class="helpBod">&nbsp;
						<abc:i18n property="commons.button.edit"/>
						<a href="typesDataFormController.html?typeDataId=${result.id}&typeId=${typeId}" class="actionLink"><fmt:message key="commons.button.edit"/></a></TD>
						
						
						
					</tr>		
				
				</c:forEach>
			</table>
			<table>
				<tr>
					<td align="center">
					<br>
					<abc:i18n property="commons.button.add"/>
					<input type="button" name="add" value=" <fmt:message key="commons.button.add"/> " class="button" onclick="window.location='typesDataFormController.html?typeId=${typeId}'"></td>
				</tr>
</table>		
		</td>
	</tr>
</table>

<%@ include file="/web/common/includes/footer.jsp" %>
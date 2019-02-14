<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<abc:security property="291"/><br>
<table rules="all" align="center" width="70%" class="sofT">
	<tr>
		<td nowrap class="helpHed">&nbsp; <abc:i18n
			property="security.caption.roleName" /> <fmt:message
			key="security.caption.roleName" />&nbsp;</td>
		<td class="helpHed"></td>
	</tr>
	<c:forEach var="result" items="${roles}">
		<tr>
			<td class="helpBod">${result.rolename}</td>
			 
			<TD class="helpBod">&nbsp; <abc:i18n
				property="commons.button.edit" /> <a
				href="javascript:createWindow('updateRole.html?roleId=${result.id}',600,700)"
				class="actionLink"><fmt:message key="commons.button.edit" /></a></TD>
			
		</tr>
	</c:forEach>
</table>


<table>
	<tr>
		<td align="center">
		<br>
		<abc:i18n property="commons.button.add"/>
		<input type="button" name="add" value=" <fmt:message key="commons.button.add"/> " class="button" onclick="javascript:createWindow('addNewRole.html',300,620)"></td>
	</tr>
</table>		

<%@ include file="/web/common/includes/footer.jsp" %>
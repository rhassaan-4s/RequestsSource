<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ include file="/web/common/includes/popupheader.jsp"%>


<script language="JavaScript">
<!--
function refreshParent() {
    self.close(); 
}
function reload()
{
var optionList=document.forms[0].zeft.options[document.forms[0].zeft.selectedIndex].index;
var fieldId=document.forms[0].fieldId.value;
createWindow('editSecurity.html?fieldId='+fieldId+'&option='+optionList);
}


//-->
</script>

<abc:security property="286"/><br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader">
		<abc:i18n property="security.header.editSecurity"/>
		<fmt:message
			key="security.header.editSecurity" /></td>
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

	<tr>
		<td colspan="2">

		<form id="fields" name="fields" method="POST"
			action="<c:url value="/security/editSecurity.html"/>">
			
			<input type="text" size=20 maxlength=40 name="option" value="${option}" style="visibility:hidden; position:absolute; " />

		<table border=0 cellspacing=1 cellpadding=0 id="ep"
			style="margin-right:40px">

			<tr>
				<td colspan=5>&nbsp;<input type="text" size=20 maxlength=40 name="fieldId" value="${fieldId}" style="visibility:hidden; position:absolute; " /> </td>
			</tr>
			<tr id="head_1_ep">
				<td class="bodyBold" colspan=4 nowrap>
				<abc:i18n property="security.header.editSecurity"/>
				<fmt:message
					key="security.header.editSecurity" /></td>
				<td nowrap colspan=1 align=left><img src="<c:url value="/web/common/images/required_icon.gif"/>"
					border="0" alt="Required Information" title="Required Information"
					width=18 height=18 align="texttop"><span class="bodySmallBold"> = <abc:i18n property="commons.caption.requiredInformation"/><fmt:message
					key="commons.caption.requiredInformation" /></span></td>
			</tr>
			<TR>
				<TD CLASS="blackLine" COLSPAN=5><img src="<c:url value="/web/common/images/s.gif"/>"></TD>
			</TR>

			<tr>
				<td nowrap class="requiredInput">
				<abc:i18n property="security.caption.permission"/>
				<fmt:message
					key="security.caption.permission" /></td>
				<td><select id="s1" name="zeft" onchange="return reload();">

					<c:forEach items="${field.permissions}" var="perm" varStatus="loop">
						<option value="${perm.id}" ${option==loop.index ? ' selected' : ''}>${perm.permessionname}</option>

					</c:forEach>
				</select></td>


				<td nowrap class="requiredInput">
				<abc:i18n property="security.caption.roles"/>
				<fmt:message
					key="security.caption.roles" /></td>

				<TD><select id="s2" name="permissionRoles" size="3" multiple>
					<c:forEach items="${roles}" var="role">
						<c:set var="isSelected" value="" />
						<c:forEach items="${field.permissions[option].roles}"
							var="permissionRole">

							<c:if test="${role.id == permissionRole.id}">
								<c:set var="isSelected" value="selected" />
							</c:if>
						</c:forEach>
						<option value="${role.id}" ${isSelected}>${role.rolename}</option>

					</c:forEach>
				</select></TD>

			</tr>






			<tr id="btn">
				<td colspan=5 align=center>
				<abc:i18n property="commons.button.save"/>
				<input type="submit" name="save"
					value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp;
				<abc:i18n property="commons.button.cancel"/>
				<input type="reset" name="cancel"
					value="<fmt:message key="commons.button.cancel"/>" class="button" />
				<abc:i18n property="commons.button.close"/>
				<input type="button" name="Close"
					value="<fmt:message key="commons.button.close"/>" class="button"
					onclick="refreshParent()" /></td>

			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>

<%@ include file="/web/common/includes/popupfooter.jsp"%>

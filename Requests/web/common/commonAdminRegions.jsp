<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<abc:security property="779"/><br>

<table width="90%"  border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
<tr>
<td class="tableHeader" height="1"></td>
</tr>
<tr>
<td class="tableHeader"><abc:i18n property="commons.header.regions"/><fmt:message key="commons.header.regions"/></td><td align="left"></td>
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
	
	
<table border=0 cellpadding=0 cellspacing=0  class="listTable">
	<TR><TD COLSPAN="15" class="blackLine"><IMG SRC="/Requests/web/common/images/s.gif"></TD></TR>
	<tr height="20" bgcolor="#DDDDDD">		
	
		<td nowrap>&nbsp;<abc:i18n property="commons.caption.region"/><fmt:message key="commons.caption.region"/>&nbsp;</td>
		<TD WIDTH=1 NOWRAP><IMG SRC="/Requests/web/common/images/s.gif"></TD>
				
		<td nowrap>&nbsp;&nbsp;</td>

	</tr>
	<TR><TD COLSPAN="15 " class="blackLine"><IMG SRC="/Requests/web/common/images/s.gif"></TD></TR>
	
	<c:forEach var="result" items="${results}" >
	<tr height=20 bgcolor="#F8F8F8">
	
		<td valign=TOP style="padding:0px 3px 0px 3px;" >${result.description}</td>
		<TD BGCOLOR="#666666" WIDTH=1 NOWRAP ><IMG SRC="/Requests/web/common/images/s.gif"></TD>
				
		<td valign=TOP style="padding:0px 3px 0px 3px;" >
		<NOBR>&nbsp;<abc:i18n property="commons.button.edit"/><a href="commonAdminEditRegion.html?regionId=${result.id}" class="actionLink"><fmt:message key="commons.button.edit"/></a>&nbsp;</NOBR>
		</td>
		
	</tr>
	</c:forEach>
	
	<TR><TD COLSPAN="15" class="blackLine"><IMG SRC="/Requests/web/common/images/s.gif"></TD></TR>
</table>
	
	
<table>
	<tr>
	</tr>
	<tr>
		<td align="center">
		<abc:i18n property="commons.button.add"/><input type="button" name="add" value=" <fmt:message key="commons.button.add"/> " class="button" onclick="window.location='commonAdminEditRegion.html'"></td>
	</tr>
</table>		
	
	
</td>
</tr>
</table>	

<%@ include file="/web/common/includes/footer.jsp" %>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ include file="/web/common/includes/popupheader.jsp"%>

<script language="JavaScript">
<!--
function refreshParent() {
 opener.location.reload(true);
    self.close(); 
}
//-->
</script>
<abc:security property="283"/><br>
<table width="90%"  border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
<tr>
<td class="tableHeader" height="1"></td>
</tr>
<tr>
<td class="tableHeader">
<abc:i18n property="i18n.header.editMessage"/>
<fmt:message key="i18n.header.editMessage"/></td><td align="left"></td>
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
	
<form 	id="messages"
		name="messages"
		method="POST" 
		action="<c:url value="/i18n/editMessage.html"/>"
		>
		
<spring:bind path="msg.*">
	<c:if test="${not empty status.errorMessages}">
	<div>
		<c:forEach var="error" items="${status.errorMessages}">
		<font color="red">
			<c:out value="${error}" escapeXml="false"/><br/>
		</font>
		</c:forEach>
	</div>
	</c:if>
</spring:bind>	
<input  type="text" size=20 maxlength=40 
						name="myKey" 
						value="${myKey}" style="visibility:hidden ; position:absolute;"/>
<spring:bind path="msg.id">
	<input  type="text" size=20 maxlength=40 
				name="messageId" 
				value="${status.value}" style="visibility:hidden ; position:absolute;"/>	
</spring:bind>


<table border=0 cellspacing=1 cellpadding=0 id="ep" style="margin-right:40px">

<tr>
	<td colspan=5>&nbsp;</td>
</tr>
<tr id="head_1_ep">
	<td class="bodyBold" colspan=4 nowrap>
	<abc:i18n property="i18n.header.editMessage"/>
	<fmt:message key="i18n.header.editMessage"/></td>
	<td nowrap colspan=1 align=left><img src="<c:url value="/web/common/images/required_icon.gif"/>" border="0" alt="Required Information" title="Required Information" width=18 height=18 align="texttop"><span class="bodySmallBold"> = <abc:i18n property="commons.caption.requiredInformation"/><fmt:message key="commons.caption.requiredInformation"/></span></td>
</tr>
<TR >
	<TD CLASS="blackLine" COLSPAN=5><img src="<c:url value="/web/common/images/s.gif"/>"></TD>
</TR>

<tr>
	<td nowrap class="requiredInput">
	<abc:i18n property="i18n.caption.message"/>
	<fmt:message key="i18n.caption.message"/></td>
	<td>
		<spring:bind path="msg.message">
			<input  type="text"  size="40"
				name="${status.expression}" 
				value="${status.value}"></input>
		</spring:bind>
	</td>
</tr>

<tr id="btn">
	<td colspan=5 align=center>
		<abc:i18n property="commons.button.save"/>
		<input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp;
		<abc:i18n property="commons.button.close"/>
		<input type="button" name="close" value="<fmt:message key="commons.button.close"/>" class="button" onclick="self.close()"/>
	</td>
	
</tr>
</table>
</form>
<%@ include file="/web/common/includes/popupfooter.jsp"%>
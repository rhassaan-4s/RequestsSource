<%@ attribute name="property" required="true" %>
<%@ taglib uri="http://acegisecurity.org/authz" prefix="authz" %>
<authz:authorize ifAnyGranted='CAN_EDIT_message'>
	<b onclick="javascript:createWindow('/Requests/i18n/editMessage.html?myKey=${property}')" style="cursor:pointer;color:blue;font-size:12px;">*</b>
</authz:authorize>


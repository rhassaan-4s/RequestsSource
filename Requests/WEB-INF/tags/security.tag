<%@ attribute name="property" required="true" %>
<%@ taglib uri="http://acegisecurity.org/authz" prefix="authz" %>
<authz:authorize ifAnyGranted='CAN_EDIT_security'>
	<a href="javascript:createWindow('/Requests/security/editSecurity.html?fieldId=${property}')">
		#
	</a>
</authz:authorize>


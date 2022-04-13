<%@ attribute name="property" required="true" %>
 <%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags" %>
 <%@ taglib uri="http://www.springframework.org/security/tags" prefix="authz" %>

<authz:authorize access="hasRole('CAN_EDIT_security')">
	<a href="javascript:createWindow('/Requests/security/editSecurity.html?fieldId=${property}')">
		#
	</a>
</authz:authorize>


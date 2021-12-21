<%@ attribute name="property" required="true" %>
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access='CAN_EDIT_security'>
	<a href="javascript:createWindow('/Requests/security/editSecurity.html?fieldId=${property}')">
		#
	</a>
</sec:authorize>


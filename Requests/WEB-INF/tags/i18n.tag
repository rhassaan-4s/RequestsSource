<%@ attribute name="property" required="true" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted='CAN_EDIT_message'>
	<b onclick="javascript:createWindow('/Requests/i18n/editMessage.html?myKey=${property}')" style="cursor:pointer;color:blue;font-size:12px;">*</b>
</sec:authorize>


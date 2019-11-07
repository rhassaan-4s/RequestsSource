<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/css/all.css">
<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/css/autoComplete.css">
<link type="text/css" href="/Requests/web/common/css/main.css"
	rel="stylesheet" media="screen" />
<link rel="stylesheet" type="text/css" media="print, handheld"
	href="/Requests/web/common/css/print.css">


<div id="smoothmenu1" class="ddsmoothmenu">

<ul>
	<li><a href="#"><fmt:message key="security.menu.security" /></a>
	<ul>
		<li><a href="/Requests/security/userDetails.html"><abc:i18n
			property="security.menu.users" /> <fmt:message
			key="security.menu.users" /></a></li>
		<li><a href="/Requests/security/roleDetails.html"><abc:i18n
			property="security.menu.roles" /> <fmt:message
			key="security.menu.roles" /></a></li>
			<li><a href="/Requests/common/commonAdminEmpBasicView.html"><abc:i18n
			property="common.menu.empBasic" /> <fmt:message
			key="common.menu.empBasic" /></a></li>
	</ul>
	</li>
	<li><a href="#"><fmt:message key="commons.menu.common" /></a>
	<ul>
	<sec:authorize ifAllGranted="CAN_UPDATE_settings">
		<li><a href="/Requests/common/settingsForm.html"><abc:i18n
			property="commons.caption.settings" /> <fmt:message
			key="commons.caption.settings" /></a></li>
	</sec:authorize>
		<li><a href="/Requests/dbUpdate/updateDBView.html"><abc:i18n
			property="common.menu.dbUpdate" /> <fmt:message
			key="common.menu.dbUpdate" /></a></li>
			
	</ul>
	</li>
	<li><a href="/Requests/security/logout.html"><fmt:message
		key="commons.menu.logout" /></a></li>
</ul>
<br style="clear: left" />
<br style="clear: left" />
</div>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz"%>
<div id="smoothmenu1" class="ddsmoothmenu">

<ul>
	<li><a href="#"><fmt:message key="commons.menu.preferences" /></a>
	<ul>
		<li><a href="/Requests/security/changeUserApplication.html"><abc:i18n
			property="security.header.editUserApplication" /> <fmt:message
			key="security.header.editUserApplication" /></a></li>
		<li><a href="/Requests/i18n/changeUserLanguage.html"><abc:i18n
			property="i18n.caption.userDefaultLocale" /> <fmt:message
			key="i18n.caption.userDefaultLocale" /></a></li>
		<li><a href="/Requests/security/changeUserPassword.html"><abc:i18n
			property="security.header.changeUserPassword" /> <fmt:message
			key="security.header.changeUserPassword" /></a></li>
	</ul>
	</li>
	<li><a href="/Requests/security/logout.html"><fmt:message
		key="commons.menu.logout" /></a></li>
</ul>
<br style="clear: left" />
<br style="clear: left" />
</div>
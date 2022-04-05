<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html dir="<fmt:message key="commons.language.dir"/>" xml:lang="<fmt:message key="commons.language.code"/>" lang="<fmt:message key="commons.language.code"/>" >
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>
			<abc:i18n property="commons.caption.client"/>
			<fmt:message key="commons.caption.client"/>
		</title>
		<link rel="stylesheet" href="/Requests/web/common/css/all.css">
		<link href="/Requests/web/common/css/tables_ar.css" rel="stylesheet"	type="text/css" />
		<link rel="stylesheet" type="text/css" href="/Requests/web/common/css/ddsmoothmenu_ar.css" />
		<script type="text/javascript" src="/Requests/web/common/js/ddsmoothmenu_ar.js"></script>
		<link href="/Requests/web/common/css/vertical_menu_ar.css" rel="stylesheet" type="text/css" />
	</head>

	<body>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
	  		<tr>
				<td height="80" colspan="7" valign="top" align="center"><img
					src="/Requests/web/common/images/erp_header.jpg" width="100%"
					height="80" /></td>
			</tr>
		</table>	  
		
		<BR>
		<BR>
		<BR>
		<BR>
		<BR>
		<BR>

		<form 	id="login"
				name="login"
				method="POST" 
				action="perform_login">

				<input type="hidden" name="activeLink" value="linkOne"/>
				
				<!-- /////////////////START to enable authentication with tokens with spring security 5//////////////////// -->
				<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}"/>
				<!-- /////////////////END to enable authentication with tokens with spring security 5  //////////////////// -->
				
				
				<table rules="all" align="center" width="400" class="sofT" >
					<!--tr>
						<td colspan=2 bgcolor="#6A6A6A" height=1></td>
					</tr-->

					<tr>
						<td class="formBodControl"><img src="/Requests/web/common/images/login-keys2.gif"></td>
						<td>
							<table width="100%"  cellpadding="2" cellspacing="0" border="0">
								<tr>
									<td colspan=2 class="formBodControl">
										<div style="text-align:center;">
										<font style="color: #0066CC; font-size: 14px; font-weight: 600;">
											<abc:i18n property="commons.caption.welcome"/>
											<fmt:message key="commons.caption.welcome"/>
										</font><br>
										<font style="color: #000066;">
											<abc:i18n property="commons.caption.pleaseEnterUsernameAndPassword"/>
											<fmt:message key="commons.caption.pleaseEnterUsernameAndPassword"/>
										</font>
										</div>
									</td>
								</tr>

								<tr>
									<c:if test = "${invalidUsernameOrPassword == 'true'}">
										<td nowrap="nowrap" colspan="3" class="formBodControl">
	      									<abc:i18n property="commons.errors.invalidUsernameOrPassword"/>
	      									<font color="red">
	        									<fmt:message key="commons.errors.invalidUsernameOrPassword"/><BR>
	     									</font>
	    								</td>
    								</c:if>
									<!-- here -->

									
									<c:if test = "${concurrentLoginException == 'true'}">
										<td nowrap="nowrap" colspan="3" class="formBodControl">
	      									<abc:i18n property="commons.errors.concurrentLoginException"/>
	      									<font color="red">
	        									<fmt:message key="commons.errors.concurrentLoginException"/><BR>
	     									</font>
	     									<P></P>

	      									<abc:i18n property="commons.errors.userCannotLoginFromAnotherComputer"/>
	      									<font color="red">
	        									<fmt:message key="commons.errors.userCannotLoginFromAnotherComputer"/><BR>
	     									</font>
	    								</td>
    								</c:if>
								</tr>

								<!--tr>
									<td colspan="2" bgcolor="#6A6A6A" height=1></td>
								</tr-->
								<tr>
									<td width="10%" nowrap class="formReq">
										<div style="text-align:center;">
										<abc:i18n property="commons.caption.userCode"/>
										<fmt:message key="commons.caption.userCode"/>
										</div>
									</td>
									<td class="formBodControl"><input   size="30" class="flat" type='text' name='username'></td>
								</tr>
								<tr>
									<td class="formReq">
										<div style="text-align:center;">
										<abc:i18n property="commons.caption.password"/>
										<fmt:message key="commons.caption.password"/>
										</div>
									</td>
									<td class="formBodControl"><input type='password' name='password'  size="30" class="flat"></td>
								</tr>
								<tr>
									<td colspan="2" align="center" class="formBodControl">
										<abc:i18n property="commons.caption.login"/>
										<input type="submit" name="submit" value="<fmt:message key="commons.caption.login"/>" class="button">
										<abc:i18n property="commons.caption.forgetPassword"/> <a
										href="forgetPassword.html"><fmt:message
										key="commons.caption.forgetPassword" /></a>
<!--										<a href="Requests/../../P01.pdf">file</a>-->
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<!--tr>
						<td height="1" colspan="2" bgcolor="#6A6A6A"></td>
					</tr-->
				</table>
			</form>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>


		<table width="100%"  border="0" cellspacing="0" cellpadding="0" bgcolor="#91C8FF">
			  <tr>
				<td ></td>
			  </tr>
			  <tr>
				<td class="englishCopyRight" >
				<abc:i18n property="commons.footer.english"/>
				<fmt:message key="commons.footer.english"/></td>
			  </tr>
			  <tr>
				<td class="arabicCopyRight">
				<abc:i18n property="commons.footer.arabic"/>
				<fmt:message key="commons.footer.arabic"/></td>
		
			  </tr>
		</table>
	</body>
</html>

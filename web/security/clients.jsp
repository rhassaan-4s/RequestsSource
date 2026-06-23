<%@ include file="/web/common/includes/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html dir="<fmt:message key="commons.language.dir"/>" lang="<fmt:message key="commons.language.code"/>" >
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>
			<fmt:message key="commons.caption.client"/>
		</title>
		<link rel="stylesheet" href="/Requests/web/common/css/all.css">
		<link href="/Requests/web/common/css/tables_ar.css" rel="stylesheet"	type="text/css" />
	</head>

	<script type="text/javascript">
	</script>
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
		<form:form method="POST" 
				action="/Requests/security/clients.html">

				<input type="hidden" name="activeLink" value="linkOne"/>
				
				<!-- /////////////////START to enable authentication with tokens with spring security 5//////////////////// -->
				<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}"/>
				<!-- /////////////////END to enable authentication with tokens with spring security 5  //////////////////// -->
				
				
				<input type="hidden" name="errorMsg" id="errorMsg" value=""/>

				<table rules="all" align="center" width="400" class="sofT" >
					<!--tr>
						<td colspan=2 bgcolor="#6A6A6A" height=1></td>
					</tr-->

					<tr>
						<td>
							<table width="100%"  cellpadding="2" cellspacing="0" border="0">
								<tr>
									<td colspan=2 class="formBodControl">
										<div style="text-align:center;">
										<font style="color: #0066CC; font-size: 14px; font-weight: 600;">
											<fmt:message key="commons.caption.welcome"/>
										</font><br>
										</div>
									</td>
								</tr>


								<tr>
									<td width="10%" nowrap class="formReq">
										<div style="text-align:center;">
										<fmt:message key="commons.caption.clients"/>
										</div>
									</td>
									<td class="formBodControl"><select name="client" id="client">
										<option value=""><fmt:message key="commons.caption.select" /></option>						
											<c:forEach items="${model.clients}" var="client">
												<option value="${client.id}">${client.clientName}</option>
											</c:forEach>
									</select></td>
								</tr>
								
								<tr>
									<td colspan="2" align="center" class="formBodControl">
										<input type="submit" name="submit" value="<fmt:message key="commons.caption.next"/>" class="button">
										
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<!--tr>
						<td height="1" colspan="2" bgcolor="#6A6A6A"></td>
					</tr-->
				</table>
			</form:form>
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
				<fmt:message key="commons.footer.english"/></td>
			  </tr>
			  <tr>
				<td class="arabicCopyRight">
				<fmt:message key="commons.footer.arabic"/></td>
		
			  </tr>
		</table>
	</body>
</html>

<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>

<abc:security property="1021"/>
<form id="settings" name="settings" method="POST" action="<c:url value="/common/settingsForm.html"/>">
	
	
	<table border="0" width="50%" cellspacing="0" cellpadding="0" style="padding-right:10px ">
		<tr>
			<td class="tableHeader" height="1"></td>
		</tr>
		
		
		<tr>
			<td>
				<spring:bind path="settings.id">
					<input type="hidden" name="${status.expression}" value="${status.value}"/>
				</spring:bind>
			</td>
		</tr>
		
			
			<tr>
				<td class="tableHeader" nowrap="nowrap">
					<abc:i18n property="commons.caption.settings" />
					<fmt:message key="commons.caption.settings" />
				</td>
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
			
			<tr id="head_1_ep">
				<td class="bodyBold" colspan=4 nowrap>
					<abc:i18n property="commons.caption.settings" />
					<fmt:message key="commons.caption.settings" />
				</td>
			</tr>
			
			<tr>
				<td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.company" />
					<fmt:message key="commons.caption.company" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.company.description">
						<input type="text" name="${status.expression}" value="${status.value}"/>
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			
			<tr>
				<td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.salaryFromDay" />
					<fmt:message key="commons.caption.salaryFromDay" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.salaryFromDay">
						<input type="text" name="${status.expression}" value="${status.value}"/>
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			<tr>
				<td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.requestsDeadline" />
					<fmt:message key="commons.caption.requestsDeadline" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.requestsDeadline">
						<input type="text" name="${status.expression}" value="${status.value}"/>
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			
			<tr>
				<td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.maxEmpBasic" />
					<fmt:message key="commons.caption.maxEmpBasic" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.maxEmp">
						<input type="text" name="${status.expression}" value="${status.value}"/>
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			
			<tr><td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.annualVacBalDaysEnabled" />
					<fmt:message key="commons.caption.annualVacBalDaysEnabled" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.annualVacBalDaysEnabled">
						<input type="checkbox" name="${status.expression}" ${status.value==	true ? 'checked' : ''} value="true" />
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			
	<tr><td nowrap class="formBodControl" >
					<abc:i18n property="requestsApproval.header.AttendanceRequest" />
					<fmt:message key="requestsApproval.header.AttendanceRequest" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.attendanceRequestEn">
						<input type="checkbox" name="${status.expression}" ${status.value==	true ? 'checked' : ''} value="true" />
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			
			<tr><td nowrap class="formBodControl" >
					<abc:i18n property="requestsApproval.header.automaticValidation" />
					<fmt:message key="requestsApproval.header.automaticValidation" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.automaticRequestsValidation">
						<input type="checkbox" name="${status.expression}" ${status.value==	true ? 'checked' : ''} value="true" />
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			
	<tr>
				<td class="tableHeader" height="20"></td>
			</tr>
			<tr id="head_1_ep">
				<td class="bodyBold" colspan=4 nowrap>
					<abc:i18n property="commons.caption.serverSettings" />
					<fmt:message key="commons.caption.serverSettings" />
				</td>
			</tr>
			<tr>
				<td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.server" />
					<fmt:message key="commons.caption.server" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.server">
						<input type="text"  name="${status.expression}" value="${status.value}"/>
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			<tr>
				<td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.service" />
					<fmt:message key="commons.caption.service" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.service">
						<input type="text"  name="${status.expression}" value="${status.value}"/>
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			<tr>
				<td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.username" />
					<fmt:message key="commons.caption.username" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.username">
						<input type="text" name="${status.expression}" value="${status.value}"/>
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			<tr>
				<td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.password" />
					<fmt:message key="commons.caption.password" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.password">
						<input type="text" name="${status.expression}" value="${status.value}"/>
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			<tr>
				<td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.loginUrl" />
					<fmt:message key="commons.caption.loginUrl" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.loginUrl">
						<input type="text"  size="50" name="${status.expression}" value="${status.value}"/>
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			
			<tr>
				<td class="tableHeader" height="20"></td>
			</tr>
			<tr id="head_1_ep">
				<td class="bodyBold" colspan=4 nowrap>
					<abc:i18n property="commons.caption.email" />
					<fmt:message key="commons.caption.email" />
				</td>
			</tr>
			<tr>
				<td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.mailMgr" />
					<fmt:message key="commons.caption.mailMgr" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.mailMgr">
						<input type="text" name="${status.expression}" value="${status.value}"/>
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			<tr>
				<td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.mailPass" />
					<fmt:message key="commons.caption.mailPass" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.mailPass">
						<input type="text" name="${status.expression}" value="${status.value}"/>
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			<tr>
				<td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.SMTPServer" />
					<fmt:message key="commons.caption.SMTPServer" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.SMTPServer">
						<input type="text" name="${status.expression}" value="${status.value}"/>
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			<tr>
				<td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.portServer" />
					<fmt:message key="commons.caption.portServer" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.portServer">
						<input type="text" name="${status.expression}" value="${status.value}"/>
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			
			
			<tr>
				<td class="tableHeader" height="20"></td>
			</tr>
			<tr id="head_1_ep">
				<td class="bodyBold" colspan=4 nowrap>
					<abc:i18n property="commons.caption.androidSettings" />
					<fmt:message key="commons.caption.androidSettings" />
				</td>
			</tr>
			<tr>
				<td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.locationAccuracy" />
					<fmt:message key="commons.caption.locationAccuracy" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.locationAccuracy">
						<input type="text" maxlength="3" name="${status.expression}" value="${status.value}"/>
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			
			<tr><td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.isLocationAccuracyEnabled" />
					<fmt:message key="commons.caption.isLocationAccuracyEnabled" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.isLocationAccuracyEnabled">
						<input type="checkbox" name="${status.expression}" ${status.value==	true ? 'checked' : ''} value="true" />
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			
			<!-- //////////////////////////// -->
			<tr><td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.showAddressOnForm" />
					<fmt:message key="commons.caption.showAddressOnForm" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.showAddressOnForm">
						<input type="checkbox" name="${status.expression}" ${status.value==	true ? 'checked' : ''} value="true" />
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			<tr><td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.showRequestsOnCalendar" />
					<fmt:message key="commons.caption.showRequestsOnCalendar" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.showRequestsOnCalendar">
						<input type="checkbox" name="${status.expression}" ${status.value==	true ? 'checked' : ''} value="true" />
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			<tr><td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.errandTimeFromSystem" />
					<fmt:message key="commons.caption.errandTimeFromSystem" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.errandTimeFromSystem">
						<input type="checkbox" name="${status.expression}" ${status.value==	true ? 'checked' : ''} value="true" />
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			<tr><td nowrap class="formBodControl" >
					<abc:i18n property="commons.caption.obligateNotes" />
					<fmt:message key="commons.caption.obligateNotes" />
				</td>
				<td  class="formBod"> 
					<spring:bind path="settings.obligateNotes">
						<input type="checkbox" name="${status.expression}" ${status.value==	true ? 'checked' : ''} value="true" />
					</spring:bind> 
				</td>
				<TD width="50%">&nbsp;</TD>
			</tr>
			
	<tr>
	
			
			
			<tr>
				<td class="tableHeader" height="20"></td>
			</tr>
			<tr id="btn">
				<td colspan=11 align=center>
					<input type="submit" name="search" value="<fmt:message key="commons.button.save"/>" class="button" />
				</td>
			</tr>

		
		
	</table>
</form>


<script language="JavaScript" type="text/javascript" src="/4s/web/common/js/wz_tooltip.js"></script>
<%@ include file="/web/common/includes/footer.jsp" %>

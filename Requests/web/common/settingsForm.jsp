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

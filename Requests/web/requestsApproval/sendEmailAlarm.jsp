<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   <form id="sendEmailAlarm" name="sendEmailAlarm" method="POST" action="<c:url value="/requestsApproval/sendEmailAlarm.html"/>">
 
			<br><br><br><h3 align="center">
				Email sent 
   			</h3>
   			<br><br>
   			
   			
   			<table>
 				<tr id="btn">
					<td colspan=4>
						<abc:i18n property="commons.button.submit"/><input type="submit" name="save" value="<fmt:message key="commons.button.submit"/>" class="button"/>&nbsp;&nbsp;&nbsp;
						<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button" >
					</td>
				</tr>					
			</table>
   </form>

</table>

	
			
<%@ include file="/web/common/includes/footer.jsp" %>
   
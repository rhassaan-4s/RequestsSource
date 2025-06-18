<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ include file="/web/common/includes/popupheader.jsp"%>

<script type="text/javascript">
	window.addEventListener('load',  () => {
    // Close the popup window
   // alert("loaded");
    var close=document.getElementById("close").value;
  //  alert(close);
    if (close!=null && close!='') 
		window.close();
	});
</script>
<abc:security property="300" />
<br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">

	<tr>
		<td><h2 style="color: red;">
					<form:errors path="empGroup.*" />
				</h2></td>
	</tr>

	<tr>
		<td colspan="2">
			<form id="empGroupForm" name="empGroupForm" method="POST"
				action="<c:url value="/requestsApproval/addNewEmpGroup.html"/>">
<input type="hidden" name="empCodeHidden" id="empCodeHidden" value="${empCodeHidden}" />
					<input type="hidden" name="empIdHidden" id="empIdHidden" value="${empIdHidden}" />
					<input type="hidden" name="close" id="close" value="${close}" />
				<!-- /////////////////START to enable authentication with tokens with spring security 5//////////////////// -->
				<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}"/>
				<!-- /////////////////END to enable authentication with tokens with spring security 5  //////////////////// -->
				
				<table rules="all" align="center" width="70%" class="sofT">
					<tr id="head_1_ep">
						<td class="helpTitle" nowrap colspan="2"><abc:i18n
								property="requestsApproval.header.newEmpGroup" /> <fmt:message
								key="requestsApproval.header.newEmpGroup" /></td>
					</tr>
					
					<tr class="">
						<td style="border-bottom: 1px solid #91C8FF;"
							class="formBodControl"><select name="reguestType" id="reguestType">
							<option value=""><fmt:message key="commons.caption.select" /></option>	
								<c:forEach items="${model.requestTypes}" var="reqType">
									<option value="${reqType.id}"
										${reqType.id == empGroup.req_id.id ?' selected' : ''}>${reqType.description}</option>
								</c:forEach>
						</select></td>

					</tr>
					
					<tr class="">
						<td style="border-bottom: 1px solid #91C8FF;"
							class="formBodControl"><select name="groupId" id="groupId">
							<option value=""><fmt:message key="commons.caption.select" /></option>	
								<c:forEach items="${model.groups}" var="group">
									<option value="${group.id}"
										${group.id == empGroup.group_id.id ?' selected' : ''}>${group.title}</option>
								</c:forEach>
						</select></td>

					</tr>

					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>

					<tr id="btn">
						<td colspan="2" align=center><abc:i18n
								property="commons.button.save" /> <input type="submit"
							name="save" value="<fmt:message key="commons.button.save"/>"
							class="button" />&nbsp;&nbsp;&nbsp; <abc:i18n
								property="commons.button.cancel" /> <input type="reset"
							name="cancel" value="<fmt:message key="commons.button.cancel"/>"
							class="button" />
					</tr>
				</table>
			</form>
		</td>
	</tr>
</table>

<%@ include file="/web/common/includes/popupfooter.jsp"%>

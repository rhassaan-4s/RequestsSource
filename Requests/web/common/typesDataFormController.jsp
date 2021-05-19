
<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>


<abc:security property=""/><br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right:10px ">
	<!-- <tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader">
		
			
			<fmt:message key="commons.caption.addEdit" />&nbsp;${arDesc}
		
			
		<td align="left"></td>
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
-->
	<tr>
		<td colspan="2">
				
			<spring:bind path="typesData.*">
				<c:if test="${not empty status.errorMessages}">
				<div>
					<c:forEach var="error" items="${status.errorMessages}">
					<font color="red">
						<c:out value="${error}" escapeXml="false"/><br/>
					</font>
					</c:forEach>
				</div>
				</c:if>
			</spring:bind>	
			
		<form id="fields" name="fields" method="POST"
			action="<c:url value="typesDataFormController.html"/>">

		<table rules="all" align="center" width="70%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap><abc:i18n
					property="commons.caption.addEdit" /><fmt:message
					key="commons.caption.addEdit" />&nbsp;${arDesc}</td>
					
					
					<input type="hidden" size=20 maxlength=40 name="typeId"
						value="${typeId}" style="visibility:text; position:absolute; " />
						
						<input type="hidden" size=20 maxlength=40 name="typeDataId"
						value="${typeDataId}" style="visibility:text; position:absolute; " />
			</tr>

				
						
			<tr>
				<td nowrap class="formReq">
				<abc:i18n property="commons.caption.description"/>
				<fmt:message key="commons.caption.description" /></td>
				<td class="formBodControl">
					<spring:bind path="typesData.description">
						<input type="text" size=20 maxlength=40 name="${status.expression}" value="${status.value}" />
					</spring:bind>
				</td>
			
			</tr>
			
			<tr id="btn">
				<td colspan="6" align="center" >
				<abc:i18n property="commons.button.save"/>
				<input type="submit"  name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp;
				<abc:i18n property="commons.button.cancel"/>
				<input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button" />
				
				</td>

			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>

<%@ include file="/web/common/includes/footer.jsp"%>


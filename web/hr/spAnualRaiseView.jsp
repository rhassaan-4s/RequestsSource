<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>

<abc:security property=""/><br>

<script type="text/javascript">
function search(){
	var URL='spAnualRaiseView.html?formType=true&search=true';
	window.location.href=URL;
}
</script>

<form method="post" action="<c:url value="spAnualRaiseView.html?formType=true"/>">
	<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
		
		<c:if test="${errorMessage!=null && errorMessage!='' }">
			<b><font color="red"><fmt:message key="hr.errors.employeeCodeExists"/> </font></b>
		</c:if>
		
		<tr>
			<td>
				<table>
					<tr>
						<td class="formBod">
							<fmt:message key="hr.caption.employeeCodeFrom"></fmt:message>
						</td>
						<td  class="formBod" colspan="2"> 
							<abc:autocomplete 
								inputId="empCodeFrom" 
								inputName="employeeCodeFrom" 
								table="hr_employee" 
								firstKey="commons.caption.code"
								secondKey="commons.caption.name"
								firstParam="empCode"
								secondParam="name"
								bindById="false"
								valueString="${employeeCodeFrom}"
								valueId="${employeeCodeFrom}"/>
						</td>
						<td>
							&nbsp;
						</td>
						<td class="formBod">
							<fmt:message key="hr.caption.employeeCodeTo"></fmt:message>
						</td>
						<td  class="formBod" colspan="2">
							<abc:autocomplete 
								inputId="empCodeTo" 
								inputName="employeeCodeTo" 
								table="hr_employee" 
								firstKey="commons.caption.code"
								secondKey="commons.caption.name"
								firstParam="empCode"
								secondParam="name"
								bindById="false"
								valueString="${employeeCodeTo}"
								valueId="${employeeCodeTo}"/>
						</td>
					</tr>
					
					<tr>
						<td class="formBod">
							<fmt:message key="commons.caption.name"></fmt:message>
						</td>
						<td  class="formBod" colspan="3"> 
							<abc:autocomplete 
								inputId="name" 
								inputName="employeeName" 
								table="hr_employee" 
								firstKey="commons.caption.name"
								secondKey="commons.caption.code"
								firstParam="name"
								secondParam="empCode"
								bindById="false"
								valueString="${employeeName}"
								valueId="${employeeName}"/>
						</td>
						<td>
							<abc:i18n property="commons.button.search"/>
							<input type="button" name="aaa" onclick="search()" value="<fmt:message key="commons.button.search"/> " class="button"/>

<!--							<abc:i18n property="commons.button.submit"/><input type="submit" name="submit" value="<fmt:message key="commons.button.submit"/> " class="button"/>&nbsp;&nbsp;&nbsp;-->
						</td>
			   		</tr>
				</table>
			</td>
		</tr>
		
		<tr height="10">
		</tr>
		
		<tr>
			<td>
				<table rules="all" align="center" width="70%" class="sofT">
					<tr>
						<td colspan="8" class="helpTitle">
							<abc:i18n property="hr.header.spAnualRaise" />
							<fmt:message key="hr.header.spAnualRaise" />
						</td>
					</tr>
					
					<tr>
						<td class="helpHed" nowrap="nowrap">
							<fmt:message key="commons.caption.code" />
						</td>
						<td class="helpHed" nowrap="nowrap">
							<abc:i18n property="hr.caption.employeeName" />
							<fmt:message key="hr.caption.employeeName" />
						</td>
						<td class="helpHed" nowrap="nowrap">
							<abc:i18n property="hr.caption.year" />
						 	<fmt:message key="hr.caption.year" />
						</td>	
						<td class="helpHed" nowrap="nowrap">
							<abc:i18n property="hr.caption.raise" />
							<fmt:message key="hr.caption.raise" />
						</td>
					</tr>
					
					<c:forEach items="${results}" var="record" varStatus="loop">
						<tr height=20 bgcolor="#F8F8F8">
							<td class="helpBod" nowrap>
								${record.empCode}
							</td>
							<td class="helpBod" nowrap>
								${record.empName}
							</td>
							<td class="helpBod" nowrap>
								<input type="text" id="year${loop.index}" name="year${loop.index}" value="${record.year}" />     
							</td>
							<td class="helpBod" nowrap>
								<input type="text" id="val${loop.index}" name="val${loop.index}" value="${record.val}" />     
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table align="center">
					<tr id="btn">
						<td colspan=5 align=center>
							<abc:i18n property="commons.button.save" />	
							<input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button"  />&nbsp;&nbsp;&nbsp; 
							<abc:i18n property="commons.button.cancel" />
							<input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	/>
						</td>
					</tr>
				</table>
			</td>
		</tr>	
	</table>
</form>

<%@ include file="/web/common/includes/footer.jsp" %>
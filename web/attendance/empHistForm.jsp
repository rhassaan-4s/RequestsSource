<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<abc:security property="1026"/>
<script type="text/javascript">
	
</script>
<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/timepicker/jquery.multiselect.css" />
<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/timepicker/jquery.multiselect.filter.css" />
<script type="text/javascript"
	src="/Requests/web/common/timepicker/jquery.multiselect.min.js"></script>
<script type="text/javascript"
	src="/Requests/web/common/timepicker/jquery.multiselect.filter.min.js"></script>
	
<form:form method="POST" modelAttribute="empHist"
	action="/Requests/attendance/empHistForm.html">

<input type="hidden" id="empCode" name="empCode"
			value="${empCode}" />
			
	<table width="90%" border="0" cellspacing="0" cellpadding="0" 
		style="padding-right: 10px">

		<tr>
			<td colspan="2"><spring:bind path="empHist.*">
					<c:if test="${not empty status.errorMessages}">
						<div>
							<c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}"
										escapeXml="false" /><br />
								</font>
							</c:forEach>
						</div>
					</c:if>
				</spring:bind></td>
		</tr>

		<tr>
			<table align="center" width="66%" class="sofT">
				<tr id="head_1_ep">
					<td class="helpTitle" colspan="2" nowrap><abc:i18n
							property="requestsApproval.header.employmentHistory" /> <fmt:message
							key="requestsApproval.header.employmentHistory" /></td>
				</tr>
				<tr>
					<td class="formBodControl" ><abc:i18n
							property="requestsApproval.caption.userCode" /> <fmt:message
							key="requestsApproval.caption.userCode" /></td>
					<td class="formBodControl"><spring:bind path="empHist.empCode">
							<select name="${status.expression}" id="${status.expression}" disabled="${empHist.empCode!=null?'disabled':''}">
								<option value=""><fmt:message
										key="commons.caption.select" /></option>
								<c:forEach items="${employees}" var="emp">
									<option value="${emp.empCode}"
										${empHist.empCode.empCode==emp.empCode?'selected':''}>${emp.empCode}-${emp.firstName}</option>
								</c:forEach>
							</select>
						</spring:bind></td>
				</tr>

				<tr>
					<td colspan="2"><table>
							<tr>
								
								<td class="formBodControl"><abc:i18n
										property="attendance.caption.sector" /> <fmt:message
										key="attendance.caption.sector" /></td>
										
								<td class="formBodControl"><abc:i18n
										property="attendance.caption.title" /> <fmt:message
										key="attendance.caption.title" /></td>	
								<td class="formBodControl"><abc:i18n
										property="attendance.caption.degree" /> <fmt:message
										key="attendance.caption.degree" /></td>
									
								<td class="formBodControl"><abc:i18n
										property="attendance.caption.region" /> <fmt:message
										key="attendance.caption.region" /></td>	
								<td class="formBodControl"><abc:i18n
										property="commons.caption.from" /> <fmt:message
										key="commons.caption.from" /></td>	
							</tr>
							<c:forEach items="${history}" var="hist">
							<tr height=20 bgcolor="#F8F8F8">
							    
								<td class="helpBod" nowrap>${hist.sector.name}</td>
								<td class="helpBod" nowrap>${hist.title.name}</td>
								<td class="helpBod" nowrap>${hist.degree.name}</td>
								<td class="helpBod" nowrap>${hist.region.name}</td>
								<td class="helpBod" nowrap><_4s_:formatMiladiDate value="${hist.fr_date}"/></td>
							</tr>
							</c:forEach>
							
							<tr>			
								<td class="formBodControl"><spring:bind
										path="empHist.sector">
										<select name="${status.expression}" id="${status.expression}">
											<option value=""><fmt:message
													key="commons.caption.select" /></option>
											<c:forEach items="${sectors}" var="sector">
												<option value="${sector.sector}"
													${empHist.sector.sector== sector.sector?'selected':''}>${sector.name}</option>
											</c:forEach>
										</select>
									</spring:bind></td>
								
								<td class="formBodControl"><spring:bind
										path="empHist.title">
										<select name="${status.expression}" id="${status.expression}">
											<option value=""><fmt:message
													key="commons.caption.select" /></option>
											<c:forEach items="${titles}" var="title">
												<option value="${title.title}"
													${empHist.title.title== title.title?'selected':''}>${title.name}</option>
											</c:forEach>
										</select>
									</spring:bind></td>
								
								<td class="formBodControl"><spring:bind
										path="empHist.degree">
										<select name="${status.expression}" id="${status.expression}">
											<option value=""><fmt:message
													key="commons.caption.select" /></option>
											<c:forEach items="${degrees}" var="degree">
												<option value="${degree.degree}"
													${empHist.degree.degree== degree.degree?'selected':''}>${degree.name}</option>
											</c:forEach>
										</select>
									</spring:bind></td>
								
								<td class="formBodControl"><spring:bind
										path="empHist.region">
										<select name="${status.expression}" id="${status.expression}">
											<option value=""><fmt:message
													key="commons.caption.select" /></option>
											<c:forEach items="${regions}" var="region">
												<option value="${region.region}"
													${empHist.region.region== region.region?'selected':''}>${region.name}</option>
											</c:forEach>
										</select>
									</spring:bind></td>
								<td class="formBodControl"><_4s_:formatMiladiDate value="${empHist.fr_date}"/></td>
							</tr>
							<tr id="btn">
								<td colspan="2" align="center"><abc:i18n
										property="commons.button.save" /><input type="submit"
									name="save" value="<fmt:message key="commons.button.save"/>"
									class="button" />&nbsp;&nbsp;&nbsp; <abc:i18n
										property="commons.button.cancel" /><input type="reset"
									name="cancel"
									value="<fmt:message key="commons.button.cancel"/>"
									class="button" /></td>
							</tr>
						</table></td>
				</tr>
			</table>
		</tr>

	</table>
</form:form>
<script type="text/javascript">
$("select").multiselect({
	  header: "",
	  selectedList: 4,
	   show: ["bounce", 200],
	   hide: ["explode", 1000]}).multiselectfilter();
</script>
<%@ include file="/web/common/includes/footer.jsp"%>

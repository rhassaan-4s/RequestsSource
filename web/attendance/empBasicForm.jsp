<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<script type="text/javascript">
	
</script>

<form id="empBasicForm" name="empBasicForm" method="POST"
	action="<c:url value="/attendance/empBasicForm.html"/>">

	<input type="hidden" id="empCode" name="empCode" value="${empCode}" />

	<table width="90%" border="0" cellspacing="0" cellpadding="0"
		style="padding-right: 10px">

		<tr>
			<td colspan="2"><spring:bind path="empBasic.*">
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
					<td class="helpTitle" colspan="8" nowrap><abc:i18n
							property="attendance.header.empBasic" /> <fmt:message
							key="attendance.header.empBasic" /></td>
				</tr>

				<tr>

				<!-- 	<c:set var="check" value="" />
					<c:if test="${code!=null && code!=''}">
						<c:set var="check" value="disabled" />
					</c:if>

					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.empCode" /> <fmt:message
							key="attendance.caption.empCode" /></td>

					<td class="formBodControl"><input size="8" maxlength="8"
						type="text" value="${empBasic.empCode}" readonly="readonly" /></td>
 -->
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="commons.caption.arName" /> <fmt:message
							key="commons.caption.arName" /></td>

					<td class="formBodControl" colspan="2"><spring:bind
							path="empBasic.empName">
							<input size="40" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>

					<td nowrap class="formReq" width="30%"><abc:i18n
							property="commons.caption.enName" /> <fmt:message
							key="commons.caption.enName" /></td>

					<td class="formBodControl" colspan="2"><spring:bind
							path="empBasic.e_emp_name">
							<input size="40" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
				</tr>
				<tr>
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.arAddress" /> <fmt:message
							key="attendance.caption.arAddress" /></td>


					<td class="formBodControl"  colspan="3"><input size="75" maxlength="90"
						type="text" value="${empBasic.address}" /></td>

					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.enAddress" /> <fmt:message
							key="attendance.caption.enAddress" /></td>

					<td class="formBodControl" colspan="3"><spring:bind
							path="empBasic.eAddress">
							<input size="75" maxlength="45" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
				</tr>
				<tr>
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.phone" /> <fmt:message
							key="attendance.caption.phone" /></td>
					<td class="formBodControl" ><spring:bind
							path="empBasic.phone">
							<input maxlength="15" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
						
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.nationalID" /> <fmt:message
							key="attendance.caption.nationalID" /></td>
					<td class="formBodControl"><spring:bind
							path="empBasic.natnl_no">
							<input maxlength="20" type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>

					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.birthdate" /> <fmt:message
							key="attendance.caption.birthdate" /></td>

					<td class="formBodControl"><spring:bind
							path="empBasic.birthdate">
							<input type="text" class="calendar" class="MM_from_d" title="ccc"  readonly="readonly"
								autocomplete="off" dir="ltr" name="${status.expression}"
								id="${status.expression}" value="${status.value}" />
						</spring:bind></td>
						
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.recruitmentDate" /> <fmt:message
							key="attendance.caption.recruitmentDate" /></td>

					<td class="formBodControl"><spring:bind
							path="empBasic.emplDate">
							<input type="text" class="calendar" class="MM_from_d" title="ccc"  readonly="readonly"
								autocomplete="off" dir="ltr" name="${status.expression}"
								id="${status.expression}" value="${status.value}" />
						</spring:bind></td>

				</tr>
				
				<tr>
					<td nowrap class="formReq" colspan="2" width="30%"><abc:i18n
							property="attendance.caption.maritalStatus" /> <fmt:message
							key="attendance.caption.maritalStatus" /></td>
					<td  class="formBodControl" colspan="2">
								<spring:bind path="empBasic.maritalStatus">
									<select name="maritalStatus" id="${status.expression}" >
										<option value=""><fmt:message key="commons.caption.select" /></option>						
											<c:forEach items="${maritalList}" var="marital">
												<option value="${marital.maritalCode}" ${marital.maritalCode== empBasic.maritalStatus.maritalCode?'selected':''}>${marital.maritalName}</option>
											</c:forEach>	
									</select>
								</spring:bind>
					</td>
					
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.religion" /> <fmt:message
							key="attendance.caption.religion" /></td>
					<td  class="formBodControl">
								<spring:bind path="empBasic.eldiana">
									<select name="eldiana" id="${status.expression}" >
										<option value=""><fmt:message key="commons.caption.select" /></option>						
											<c:forEach items="${religionList}" var="rel">
												<option value="${rel.religion}" ${rel.religion == empBasic.eldiana.religion?'selected':''}>${rel.name}</option>
											</c:forEach>	
									</select>
								</spring:bind>
					</td>
					
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.gender" /> <fmt:message
							key="attendance.caption.gender" /></td>		
					<td  class="formBodControl">
								<spring:bind path="empBasic.sex">
									<select name="sex" id="${status.expression}" >
										<option value=""><fmt:message key="commons.caption.select" /></option>						
										<option value="1" ${empBasic.sex==1?'selected':''}><fmt:message key="commons.caption.males" /></option>
										<option value="2" ${empBasic.sex==2?'selected':''}><fmt:message key="commons.caption.females" /></option>
									</select>
								</spring:bind>
					</td>		
							
				</tr>
				
				<tr>
					<td nowrap class="formReq" colspan="2" width="30%"><abc:i18n
							property="attendance.caption.qualification" /> <fmt:message
							key="attendance.caption.qualification" /></td>
					<td  class="formBodControl" colspan="2">
					<spring:bind path="empBasic.qual">
									<select name="qual" id="${status.expression}" >
										<option value=""><fmt:message key="commons.caption.select" /></option>						
											<c:forEach items="${qualificationList}" var="qual">
												<option value="${qual.qual}" ${qual.qual==empBasic.qual.qual?'selected':''}>${qual.name}</option>
											</c:forEach>	
									</select>
								</spring:bind>
					</td>
					
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.department" /> <fmt:message
							key="attendance.caption.department" /></td>
					<td  class="formBodControl">
								<spring:bind path="empBasic.department">
									<select name="department" id="${status.expression}" >
										<option value=""><fmt:message key="commons.caption.select" /></option>						
											<c:forEach items="${departmentList}" var="dep">
												<option value="${dep.location}" ${dep.location == empBasic.department.location?'selected' : ''}>${dep.name}</option>
											</c:forEach>	
									</select>
								</spring:bind>
					</td>
					
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.title" /> <fmt:message
							key="attendance.caption.title" /></td>
					<td  class="formBodControl">
								<spring:bind path="empBasic.title">
									<select name="title" id="${status.expression}" >
										<option value=""><fmt:message key="commons.caption.select" /></option>						
											<c:forEach items="${titleList}" var="title">
												<option value="${title.title}" ${title.title==empBasic.title.title?'selected':''}>${title.name}</option>
											</c:forEach>	
									</select>
								</spring:bind>
					</td>
					
			</tr>
			<tr>
			<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.permanent" /> <fmt:message
							key="attendance.caption.permanent" /></td>
				<td  class="formBod"> 
					<spring:bind path="empBasic.permenant">
						<input type="checkbox" name="${status.expression}" ${status.value=='T'?'checked':''} />
					</spring:bind> 
				</td>
				<td  class="formBodControl" colspan="6"></td>
			</tr>
				<tr id="btn">
					<td colspan="8" align="center"><abc:i18n
							property="commons.button.save" /><input type="submit"
						name="save" value="<fmt:message key="commons.button.save"/>"
						class="button" />&nbsp;&nbsp;&nbsp; <abc:i18n
							property="commons.button.cancel" /><input type="reset"
						name="cancel" value="<fmt:message key="commons.button.cancel"/>"
						class="button" /></td>
				</tr>
			</table>
		</tr>

	</table>
</form>


<%@ include file="/web/common/includes/footer.jsp"%>

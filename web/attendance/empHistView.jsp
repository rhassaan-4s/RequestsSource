<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<abc:security property="1026"/>

<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
		<table border=0 cellspacing=1 cellpadding=0 id="ep"
			style="margin-right: 40px">
			<tr>
				<td class="tableHeader" colspan=4 nowrap><abc:i18n
						property="requestsApproval.header.employmentHistory" />
					<fmt:message key="requestsApproval.header.employmentHistory" /></td>
			</tr>

			<tr>
				<td>
					<table rules="all" align="center" width="70%" class="sofT">
						<tr>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="commons.caption.empCode" /> <fmt:message
									key="commons.caption.empCode" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="requestsApproval.caption.userName" /> <fmt:message
									key="requestsApproval.caption.userName" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="attendance.caption.sector" /> <fmt:message
									key="attendance.caption.sector" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="attendance.caption.title" /> <fmt:message
									key="attendance.caption.title" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="attendance.caption.degree" /> <fmt:message
									key="attendance.caption.degree" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="attendance.caption.region" /> <fmt:message
									key="attendance.caption.region" /></td>
							

							<td class="helpHed"></td>
						</tr>
						<c:forEach items="${records}" var="record">
							<tr height=20 bgcolor="#F8F8F8">
								<td class="helpBod" nowrap><a
									href="empHistForm.html?empCode=${record.empCode.empCode}">${record.empCode.empCode}</a></td>
								<td class="helpBod" nowrap>${record.empCode.firstName}</td>
								
								<td class="helpBod" nowrap>${record.sector.name}</td>
								<td class="helpBod" nowrap>${record.title.name}</td>

								<td class="helpBod" nowrap>${record.degree.name}</td>
								
								<td class="helpBod" nowrap>${record.region.name}</td>

							</tr>
						</c:forEach>

					</table>

				</td>
			</tr>

			<tr>
				<td colspan="2" align="center"><br> <abc:i18n
						property="commons.button.add" /> <input type="button"
					value="<fmt:message key="commons.button.add"/>" name="add"
					class="button" onclick="window.location='empHistForm.html'"></input>
				</td>
			</tr>


		</table>

</table>



<%@ include file="/web/common/includes/footer.jsp"%>

<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<abc:security property="1027"/>

<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
		<table border=0 cellspacing=1 cellpadding=0 id="ep"
			style="margin-right: 40px">
			<tr>
				<td class="tableHeader" colspan=4 nowrap><abc:i18n
						property="attendance.header.empBasic" />
					<fmt:message key="attendance.header.empBasic" /></td>
			</tr>

			<tr>
				<td>
					<table rules="all" align="center" width="70%" class="sofT">
						<tr >
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="attendance.caption.empCode" /> <fmt:message
									key="attendance.caption.empCode" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="commons.caption.arName" /> <fmt:message
									key="commons.caption.arName" /></td>
							
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="attendance.caption.nationalID" /> <fmt:message
									key="attendance.caption.nationalID" /></td>
							
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="attendance.caption.recruitmentDate" /> <fmt:message
									key="attendance.caption.recruitmentDate" /></td>
									
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="attendance.caption.religion" /> <fmt:message
									key="attendance.caption.religion" /></td>
									
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="attendance.caption.department" /> <fmt:message
									key="attendance.caption.department" /></td>
									
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="attendance.caption.title" /> <fmt:message
									key="attendance.caption.title" /></td>
									
							<td class="helpHed"></td>
						</tr>
						<tr>
						
						</tr>
						<c:forEach items="${records}" var="record">
							<tr height=20 bgcolor="#F8F8F8">
								<td class="helpBod" nowrap>${record.empCode}</td>

								<td class="helpBod" nowrap>${record.empName}</td>

								<td class="helpBod" nowrap>${record.natnl_no}</td>
								
								<td class="helpBod" nowrap><_4s_:formatMiladiDate value="${record.emplDate}"/></td>
								
								<td class="helpBod" nowrap>${record.eldiana.name}</td>
								
								<td class="helpBod" nowrap>${record.department.name}</td>
								
								<td class="helpBod" nowrap>${record.title.name}</td>

								<td class="helpBod" nowrap><abc:i18n
										property="commons.button.edit" /><a
									href="empBasicForm.html?empCode=${record.empCode}"><fmt:message
											key="commons.button.edit" /></a></td>
							</tr>
						</c:forEach>

					</table>

				</td>
			</tr>

			<tr>
				<td colspan="2" align="center"><br> <abc:i18n
						property="commons.button.add" /> <input type="button"
					value="<fmt:message key="commons.button.add"/>" name="add"
					class="button" onclick="window.location='empBasicForm.html'"></input>
				</td>
			</tr>


		</table>

</table>



<%@ include file="/web/common/includes/footer.jsp"%>

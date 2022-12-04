<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<abc:security property="1030"/>

<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
		<table border=0 cellspacing=1 cellpadding=0 id="ep"
			style="margin-right: 40px">
			<tr>
				<td class="tableHeader" colspan=4 nowrap><abc:i18n
						property="attendance.header.vacation" />
					<fmt:message key="attendance.header.vacation" /></td>
			</tr>

			<tr>
				<td>
					<table rules="all" align="center" width="70%" class="sofT">
					<tr>
						<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.vacationType" /> <fmt:message
								key="attendance.caption.vacationType" /></td>
						<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.vacationCode" /> <fmt:message
								key="attendance.caption.vacationCode" /></td>
						<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="commons.caption.arName" /> <fmt:message
								key="commons.caption.arName" /></td>
						<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="commons.caption.enName" /> <fmt:message
								key="commons.caption.enName" /></td>
						
						<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.monthsallowance" /> <fmt:message
								key="attendance.caption.monthsallowance" /></td>
						<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.monthsentitled" /> <fmt:message
								key="attendance.caption.monthsentitled" /></td>

						<td class="helpHed"></td>
					</tr>
					<c:forEach items="${records}" var="record">
						<tr height=20 bgcolor="#F8F8F8">
							<td class="helpBod" nowrap><c:choose>
									<c:when test="${record.type=='A'}">
										<fmt:message key="attendance.caption.journalVacation" />
									</c:when>
									<c:when test="${record.type=='B'}">
										<fmt:message key="attendance.caption.specialVacation" />
									</c:when>
								</c:choose></td>
							<td class="helpBod" nowrap>${record.vacation}</td>

							<td class="helpBod" nowrap>${record.name}</td>

							<td class="helpBod" nowrap>${record.ename}</td>

							<td class="helpBod" nowrap><input type="checkbox"
								${record.may_transfer=='1'?'checked':''} disabled="disabled" /></td>
							<td class="helpBod" nowrap>${record.monthes_zero_entitled}</td>

							<td class="helpBod" nowrap><abc:i18n
									property="commons.button.edit" /><a
								href="vacationForm.html?vacationCode=${record.vacation}"><fmt:message
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
					class="button" onclick="window.location='vacationForm.html'"></input>
				</td>
			</tr>


		</table>

</table>



<%@ include file="/web/common/includes/footer.jsp"%>

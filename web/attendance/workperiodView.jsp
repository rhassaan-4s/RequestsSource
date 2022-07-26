<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>

<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
		<table border=0 cellspacing=1 cellpadding=0 id="ep"
			style="margin-right: 40px">
			<tr>
				<td class="tableHeader" colspan=4 nowrap><abc:i18n
						property="attendance.header.workperiods" />
					<fmt:message key="attendance.header.workperiods" /></td>
			</tr>

			<tr>
				<td>
					<table rules="all" align="center" width="70%" class="sofT">
					<tr>
						<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.periodcode" /> <fmt:message
								key="attendance.caption.periodcode" /></td>
						<td class="helpHed" nowrap="nowrap"><abc:i18n
							property="commons.caption.arName" /> <fmt:message
							key="commons.caption.arName" /></td>
						<td class="helpHed" nowrap="nowrap"><abc:i18n
							property="commons.caption.enName" /> <fmt:message
							key="commons.caption.enName" /></td>
						<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.defaultperiod" /> <fmt:message
								key="attendance.caption.defaultperiod" /></td>
						<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.start" /> <fmt:message
								key="attendance.caption.start" /></td>
						<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.end" /> <fmt:message
								key="attendance.caption.end" /></td>
						<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.periodstartdate" /> <fmt:message
								key="attendance.caption.periodstartdate" /></td>
						<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.periodduration" /> <fmt:message
								key="attendance.caption.periodduration" /></td>
						
						<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.delaycalculated" /> <fmt:message
								key="attendance.caption.delaycalculated" /></td>
						<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.earlydismissalcalculated" /> <fmt:message
								key="attendance.caption.earlydismissalcalculated" /></td>
								
						<td class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.overtimecalculated" /> <fmt:message
								key="attendance.caption.overtimecalculated" /></td>

						<td class="helpHed"></td>
					</tr>
					<c:forEach items="${records}" var="record">
						<tr height=20 bgcolor="#F8F8F8">
							<td class="helpBod" nowrap>
									${record.workperiods.workperiods}</td>
							<td class="helpBod" nowrap>${record.workperiods.name}</td>
							<td class="helpBod" nowrap>${record.workperiods.ename}</td>
							<td class="helpBod" nowrap><input type="checkbox"
								${record.is_default=='1'?'checked':''} disabled="disabled" /></td>
							<td class="helpBod" nowrap><_4s_:formatMiladiDate value="${record.start_date}"/></td>
							<td class="helpBod" nowrap><_4s_:formatMiladiDate value="${record.end_date}"/></td>
							<td class="helpBod" nowrap><_4s_:timeString value="${record.time_in}"/></td>

							<td class="helpBod" nowrap>${record.required_hours}</td>

							<td class="helpBod" nowrap><input type="checkbox"
								${record.consider_delay==1?'checked':''} disabled="disabled" /></td>
							<td class="helpBod" nowrap><input type="checkbox"
								${record.consider_early==1?'checked':''} disabled="disabled" /></td>
							<td class="helpBod" nowrap><input type="checkbox"
								${record.consider_overtime==1?'checked':''} disabled="disabled" /></td>

							<td class="helpBod" nowrap><abc:i18n
									property="commons.button.edit" /><a
								href="workperiodForm.html?workperiodCode=${record.workperiods.workperiods}"><fmt:message
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
					class="button" onclick="window.location='workperiodForm.html'"></input>
				</td>
			</tr>


		</table>

</table>



<%@ include file="/web/common/includes/footer.jsp"%>

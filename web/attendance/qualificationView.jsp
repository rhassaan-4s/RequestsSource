<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<abc:security property="1024"/>

<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
		<table border=0 cellspacing=1 cellpadding=0 id="ep"
			style="margin-right: 40px">
			<tr>
				<td class="tableHeader" colspan=4 nowrap><abc:i18n
						property="attendance.header.qualification" />
					<fmt:message key="attendance.header.qualification" /></td>
			</tr>

			<tr>
				<td>
					<table rules="all" align="center" width="70%" class="sofT">
						<tr>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="attendance.caption.qualCode" /> <fmt:message
									key="attendance.caption.qualCode" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="commons.caption.arName" /> <fmt:message
									key="commons.caption.arName" /></td>
							<td class="helpHed" nowrap="nowrap"><abc:i18n
									property="commons.caption.enName" /> <fmt:message
									key="commons.caption.enName" /></td>

							<td class="helpHed"></td>
						</tr>
						<c:forEach items="${records}" var="record">
							<tr height=20 bgcolor="#F8F8F8">
								<td class="helpBod" nowrap>${record.qual}</td>

								<td class="helpBod" nowrap>${record.name}</td>

								<td class="helpBod" nowrap>${record.ename}</td>

								<td class="helpBod" nowrap><abc:i18n
										property="commons.button.edit" /><a
									href="qualificationForm.html?qualificationCode=${record.qual}"><fmt:message
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
					class="button" onclick="window.location='qualificationForm.html'"></input>
				</td>
			</tr>


		</table>

</table>



<%@ include file="/web/common/includes/footer.jsp"%>

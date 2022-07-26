<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>

<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<form>
	<table width="70%" border=0 cellspacing=1 cellpadding=0 id="ep"
		style="margin-right: 40px">
		<tr>
			<td class="tableHeader" colspan="4" nowrap><abc:i18n
					property="attendance.header.triggerattendanalysis" /> <fmt:message
					key="attendance.header.triggerattendanalysis" /></td>

		</tr>
		<tr>
			<td nowrap class="formReq"><abc:i18n
					property="attendance.caption.month" /> <fmt:message
					key="attendance.caption.month" /></td>
			<td class="formBodControl"><select name="month"
				id="month">
					<option value=""><fmt:message key="commons.caption.select" /></option>
					<c:forEach items="${months}" var="mon">
						<option value="${mon.code}"
							${mon.code== month?'selected':''}>${mon.code}</option>
					</c:forEach>
			</select></td>
			<td nowrap class="formReq"><abc:i18n
					property="attendance.caption.year" /> <fmt:message
					key="attendance.caption.year" /></td>
			<td class="formBodControl"><select name="year"
				id="year">
					<option value=""><fmt:message key="commons.caption.select" /></option>
					<c:forEach items="${years}" var="y">
						<option value="${y}"
							${y== year?'selected':''}>${y}</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<c:choose>
				<c:when test="${failed==true}">
					<td colspan="4" nowrap><abc:i18n
							property="attendance.caption.attendanalysisfailed" /> <fmt:message
							key="attendance.caption.attendanalysisfailed" /></td>
				</c:when>
				<c:when test="${failed==false}">
					<td class="tableHeader" colspan="4" nowrap><abc:i18n
							property="attendance.caption.attendanalysisprepared" /> <fmt:message
							key="attendance.caption.attendanalysisprepared" /></td>
				</c:when>
			</c:choose>

		</tr>
		<tr id="btn">
			<td colspan="4" align="center"><abc:i18n
					property="commons.button.save" /><input type="submit" name="save"
				value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp;
				<abc:i18n property="commons.button.cancel" /><input type="reset"
				name="cancel" value="<fmt:message key="commons.button.cancel"/>"
				class="button" /></td>
		</tr>

	</table>
</form>
</table>



<%@ include file="/web/common/includes/footer.jsp"%>

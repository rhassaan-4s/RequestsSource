<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<abc:security property="1032"/>

<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
		<table border=0 cellspacing=1 cellpadding=0 id="ep"
			style="margin-right: 40px">
			<tr>
				<c:choose>
					<c:when test="${failed==true}">
						<td class="tableHeader" colspan=4 nowrap><abc:i18n
						property="attendance.header.vacbalancefailed" />
					<fmt:message key="attendance.header.vacbalancefailed" /></td>
					</c:when>
					<c:otherwise>
						<td class="tableHeader" colspan=4 nowrap><abc:i18n
						property="attendance.header.vacbalanceprepared" />
					<fmt:message key="attendance.header.vacbalanceprepared" /></td>
					</c:otherwise>
				</c:choose>
				
			</tr>

		</table>

</table>



<%@ include file="/web/common/includes/footer.jsp"%>

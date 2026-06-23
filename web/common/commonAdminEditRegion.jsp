<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<abc:security property="780" />
<br>


<script type='text/javascript'
	src='/Requests/dwr/interface/commonManager.js'></script>
<script type='text/javascript' src='/Requests/dwr/engine.js'></script>
<script type='text/javascript' src='/Requests/dwr/util.js'></script>

<!-- ///////////////////////////////////////////////////////////////////////   -->
<script language="JavaScript">
	function changeList() {
		var c = document.getElementById("country");
		cities = document.getElementById("cities");
		if (c.value != "") {
			DWRUtil.removeAllOptions(cities);
			commonManager.getCitiesByCountry(putNewList, c.value);

		} else {
			//DWRUtil.removeAllOptions(cities);
		}

	}

	function putNewList(data) {
		cities = document.getElementById("cities");
		DWRUtil.addOptions(cities, data, "id", "description");

	}
</script>
<!-- ///////////////////////////////////////////////////////////////////////// -->

<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
				property="commons.header.editRegion" />
			<fmt:message key="commons.header.editRegion" /></td>
		<td align="left"></td>
	</tr>
	<tr>
		<td colspan="2" bgcolor="#5A718B" height="2"></td>
	</tr>
	<tr>
		<td colspan="2" height="1"></td>
	</tr>

	<tr>
		<td colspan="2" height="20"></td>
	</tr>

	<tr>
		<td colspan="2"><form:form method="POST" modelAttribute="region"
				action="/Requests/common/commonAdminEditRegion.html">
				<spring:bind path="region.*">
					<c:if test="${not empty status.errorMessages}">
						<div>
							<c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}"
										escapeXml="false" /><br />
								</font>
							</c:forEach>
						</div>
					</c:if>
				</spring:bind>

				<spring:bind path="region.id">
					<input type="hidden" size=20 maxlength=40 name="regionId"
						value="${status.value}" />
				</spring:bind>

				<table border=0 cellspacing=1 cellpadding=0 id="ep"
					style="margin-right: 40px">

					<tr>
						<td colspan=5>&nbsp;</td>
					</tr>
					<tr id="head_1_ep">
						<td class="bodyBold" colspan=4 nowrap><abc:i18n
								property="commons.header.editRegion" />
							<fmt:message key="commons.header.editRegion" /></td>
						<td nowrap colspan=1 align=left><img
							src="/Requests/web/common/images/required_icon.gif" border="0"
							alt="Required Information" title="Required Information" width=18
							height=18 align="texttop"><span class="bodySmallBold">
								= <fmt:message key="commons.caption.requiredInformation" />
						</span></td>
					</tr>
					<TR>
						<TD CLASS="blackLine" COLSPAN=5><img
							src="/Requests/web/common/images/s.gif"></TD>
					</TR>
					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.caption.description" />
							<fmt:message key="commons.caption.description" /></td>

						<td colspan="4"><spring:bind path="region.description">
								<input type="text" size="40" name="${status.expression}"
									value="${status.value}"></input>
							</spring:bind></td>
					</tr>

					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.caption.regionCode" />
							<fmt:message key="commons.caption.regionCode" /></td>

						<td colspan="4"><spring:bind path="region.code">
								<input type="text" size="10" name="${status.expression}"
									value="${status.value}"></input>
							</spring:bind></td>
					</tr>

					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.caption.country" /> <fmt:message
								key="commons.caption.country" /></td>
						<td colspan="4"><spring:bind path="region.country">
								<select name="${status.expression}" id=country
									onchange="changeList();">
									<option value=""><fmt:message
											key="commons.caption.select" /></option>
									<c:forEach items="${countries}" var="c">
										<option value="${c.id}"
											${c.id == region.country.id ?' selected' : ''}>${c.description}</option>
									</c:forEach>
								</select>
							</spring:bind></td>
					</tr>

					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.caption.city" /> <fmt:message
								key="commons.caption.city" /></td>
						<td colspan="4"><select multiple size="5" name="cities"
							id=cities>
								<c:forEach items="${cities}" var="city">
									<c:set var="isSelected" value="" />
									<c:forEach items="${region.cities}" var="rCity">
										<c:if test="${rCity.id==city.id}">
											<c:set var="isSelected" value="selected" />
										</c:if>
									</c:forEach>
									<option value="${city.id}" ${isSelected}>${city.description}</option>
								</c:forEach>
						</select></td>
					</tr>



					<tr>
						<td colspan=5>&nbsp;</td>
					</tr>

					<tr id="btn">
						<td colspan=5 align=center><abc:i18n
								property="commons.button.save" /><input type="submit"
							name="save" value="<fmt:message key="commons.button.save"/>"
							class="button">&nbsp;&nbsp;&nbsp; <abc:i18n
								property="commons.button.cancel" /><input type="reset"
							name="cancel" value="<fmt:message key="commons.button.cancel"/>"
							class="button"></td>
					</tr>
				</table>
			</form:form></td>
	</tr>
</table>
<%@ include file="/web/common/includes/footer.jsp"%>
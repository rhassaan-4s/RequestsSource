<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<abc:security property="1029"/>
<script type="text/javascript">

	function addRow() {
		var table = document.getElementById("leaves");
		var rowLength = table.rows.length;
		var rowCount = (rowLength-1);
		//alert(rowLength);
		//var tr = table.insertRow(rowLength);
		var prevRow = table.rows[rowCount];
		//alert(prevRow);
		var tr = prevRow.cloneNode(true);
		///////////////////////////////////////
		//alert(tr.cells[0].innerHTML);
		//tr.cells[0].innerHTML="";
		var date = tr.cells[0].children[0];
		date.id='pubLeaves['+rowCount+'].indate';
		date.name='pubLeaves['+rowCount+'].indate';
		///////////////////////////////////////
		var desc = tr.cells[1].children[0];
		desc.id='pubLeaves['+rowCount+'].description';
		desc.name='pubLeaves['+rowCount+'].description';
		desc.value="";
		/////////////////////////////////////////
		//alert(tr.cells[2].children[0]);
		var weight = tr.cells[2].children[0];
		//alert(serviceYear.name);
		weight.id='pubLeaves['+rowCount+'].weight';
		weight.name='pubLeaves['+rowCount+'].weight';
		weight.value="";
		////////////////////////////////////////
		table.append(tr);
	}
	
</script>

<style>
.remove_user {
	width: 24px;
	height: 24px;
	float: left;
	cursor: pointer;
	background-repeat: no-repeat;
	background-position: center center;
	background-image: url("/Requests/web/common/images/publish_r.jpg");
}

.add_user {
	width: 24px;
	height: 24px;
	float: left;
	cursor: pointer;
	background-repeat: no-repeat;
	background-position: center center;
	background-image: url("/Requests/web/common/images/icon-16-add.png");
}

.edit_group {
	width: 24px;
	height: 24px;
	float: left;
	cursor: pointer;
	background-repeat: no-repeat;
	background-position: center center;
	background-image: url("/Requests/web/common/images/edit_button.gif");
}
</style>

<form id="pubLeavesForm" name="pubLeavesForm" method="POST"
	action="<c:url value="/attendance/publicLeavesForm.html"/>">


	<table width="90%" border="0" cellspacing="0" cellpadding="0"
		style="padding-right: 10px">

		<tr>
			<td><spring:bind path="leaves.*">
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
			<td>
				<table align="center" class="sofT">
					<tr id="head_1_ep">
						<td class="helpTitle" colspan="2" nowrap><abc:i18n
								property="attendance.header.publicleaves" /> <fmt:message
								key="attendance.header.publicleaves" />
						<span class="add_user" onclick="window.location='publicLeavesForm.html?addRow=true'" title="<fmt:message key="commons.button.add" />"></span></td>
					</tr>


					<tr>
						<td>
							<table width="100%" id="leaves">
								<tr>
									<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="commons.caption.date" /> <fmt:message
											key="commons.caption.date" /></td>
									<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="commons.caption.description" /> <fmt:message
											key="commons.caption.description" /></td>
									<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="attendance.caption.value" /> <fmt:message
											key="attendance.caption.value" /></td>
									<td class="helpHed"></td>

								</tr>

								<c:forEach items="${leaves.pubLeaves}" var="record" varStatus="loop">
									<tr height=20 bgcolor="#F8F8F8">
										<td class="helpBod"><spring:bind
												path="leaves.pubLeaves[${loop.index}].indate">
												<input type="text" class="calendar" class="MM_from_d"
													title="ccc" readonly="readonly" autocomplete="off"
													dir="ltr" size="10" name="${status.expression}"
													id="${status.expression}" value="${status.value}" />
											</spring:bind></td>

										<td class="helpBod" nowrap><spring:bind
												path="leaves.pubLeaves[${loop.index}].description">
												<input size="3" type="text" name="${status.expression}"
													value="${status.value}" />
											</spring:bind></td>

										<td class="helpBod" nowrap><spring:bind
												path="leaves.pubLeaves[${loop.index}].weight">
												<input size="3" type="text" name="${status.expression}"
													value="${status.value}" />
											</spring:bind></td>
										<td align="center" class="helpBod"><span
											class="remove_user"
											onclick="removeRow();"
											title="<fmt:message
										key="commons.button.delete" />">
										</span></td>

									</tr>
								</c:forEach>
							</table>
							<table width="100%"><tr id="btn">
									<td colspan="4" align="center"><input
										type="submit" name="save"
										value="<fmt:message key="commons.button.save"/>"
										class="button" />&nbsp;&nbsp;&nbsp; <abc:i18n
											property="commons.button.cancel" /><input type="reset"
										name="cancel"
										value="<fmt:message key="commons.button.cancel"/>"
										class="button" /></td>
								</tr></table>
					</tr>

				</table>
			</td>
		</tr>
		</table>
</form>


<%@ include file="/web/common/includes/footer.jsp"%>

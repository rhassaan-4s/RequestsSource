<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<script type="text/javascript">
function vacationChanged(index){
	var select = document.getElementById("rules["+index+"].vacation");
	var newVacCode = select.value;
	var td = document.getElementById("vacCode["+index+"]");
	td.innerHTML=newVacCode;
}
	function addRow() {
		var table = document.getElementById("vacRules");
		var rowLength = table.rows.length;
		var rowCount = (rowLength-1);
		//alert(rowLength);
		//var tr = table.insertRow(rowLength);
		var prevRow = table.rows[rowCount];
		//alert(prevRow);
		var tr = prevRow.cloneNode(true);
		///////////////////////////////////////
		//alert(tr.cells[0]);
		tr.cells[0].innerHTML="";
		tr.cells[0].id='vacCode['+rowCount+']';
		///////////////////////////////////////
		var select = tr.cells[1].children[0];
		select.id='rules['+rowCount+'].vacation';
		select.name='rules['+rowCount+'].vacation';
		//select.addEventListener('change',function(){vacationChanged(rowCount);},false);//function(){vacationChanged(rowCount)}
		select.setAttribute('onchange','vacationChanged('+rowCount+');');
		//alert(select.id);
		var vacList = select.options[0];
		vacList.selected = true;
		//alert(vacList);
		
		for(var j=1; j<vacList.length; j++) {
			var vac = vacList[j];
			vac.selected = false;
		}
		/////////////////////////////////////////
		//alert(tr.cells[2].children[0]);
		var serviceYear = tr.cells[2].children[0];
		//alert(serviceYear.name);
		serviceYear.name='rules['+rowCount+'].srvYear';
		serviceYear.value="0";
		////////////////////////////////////////
		//alert(tr.cells[3].id);
		var entitled = tr.cells[3].children[0];
		entitled.name='rules['+rowCount+'].entitled';
		entitled.value="0";
		/*for (var j = 0; j < children1.length; j++) {
		      var child = children1[j];
		      alert(child);
		 }*/
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

<form id="vacRuleForm" name="vacRuleForm" method="POST"
	action="<c:url value="/attendance/vacRuleForm.html"/>">


	<table width="90%" border="0" cellspacing="0" cellpadding="0"
		style="padding-right: 10px">

		<tr>
			<td><spring:bind path="rules.*">
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
								property="attendance.header.vacRules" /> <fmt:message
								key="attendance.header.vacRules" />
						<span class="add_user" onclick="addRow();" title="<fmt:message key="commons.button.add" />"></span></td>
					</tr>


					<tr>
						<td>
							<table id="vacRules">
								<tr>
									<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="attendance.caption.vacCode" /> <fmt:message
											key="attendance.caption.vacCode" /></td>
									<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="attendance.caption.vacName" /> <fmt:message
											key="attendance.caption.vacName" /></td>
									<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="attendance.caption.serviceYears" /> <fmt:message
											key="attendance.caption.serviceYears" /></td>
									<td class="helpHed" nowrap="nowrap"><abc:i18n
											property="attendance.caption.entitled" /> <fmt:message
											key="attendance.caption.entitled" /></td>
									<td class="helpHed"></td>

								</tr>

								<c:forEach items="${rules.rules}" var="record" varStatus="loop">
									<tr height=20 bgcolor="#F8F8F8">
										<td class="helpBod" nowrap id="vacCode[${loop.index}]">${record.vacation.vacation}</td>

										<td class="helpBod" nowrap><spring:bind
												path="rules.rules[${loop.index}].vacation">
												<select name="${status.expression}" id="${status.expression}" onchange="vacationChanged(${loop.index});">
													<option value=""><fmt:message
															key="commons.caption.select" /></option>
													<c:forEach items="${vacList}" var="vac">
														<option value="${vac.vacation}"
															${vac.vacation== rules.rules[loop.index].vacation.vacation?'selected':''}>${vac.name}</option>
													</c:forEach>
												</select>
											</spring:bind></td>

										<td class="helpBod" nowrap><spring:bind
												path="rules.rules[${loop.index}].srvYear">
												<input size="3" type="text" name="${status.expression}"
													value="${status.value}" />
											</spring:bind></td>
										<td class="helpBod" nowrap><spring:bind
												path="rules.rules[${loop.index}].entitled">
												<input size="2" type="text" name="${status.expression}"
													value="${status.value}" />
											</spring:bind></td>
										<td align="center" class="helpBod"><span
											class="remove_user"
											onclick="removeRule(${record.vacation.vacation},${record.srvYear});"
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
</form>


<%@ include file="/web/common/includes/footer.jsp"%>

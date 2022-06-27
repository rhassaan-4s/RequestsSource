<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<script type="text/javascript">
	function check(box,textfield) {
		var b = document.getElementById(box);
		//alert(b.checked);
		if (b.checked==false) {
			var t = document.getElementById(textfield);
			t.value=0;
			t.readOnly='readOnly';
		} else {
			var t = document.getElementById(textfield);
			//t.value=0;
			t.readOnly='';	
		}
	}
</script>

<form id="workperiodForm" name="workperiodForm" method="POST"
	action="<c:url value="/attendance/workperiodForm.html"/>">

	<input type="hidden" id="workperiodCode" name="workperiodCode"
		value="${workperiodCode}" />

	<table width="90%" border="0" cellspacing="0" cellpadding="0"
		style="padding-right: 10px">

		<tr>
			<td colspan="2"><spring:bind path="period.*">
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
			<table align="center" width="66%" class="sofT">
				<tr id="head_1_ep">
					<td class="helpTitle" colspan="6" nowrap><abc:i18n
							property="attendance.header.workperiods" /> <fmt:message
							key="attendance.header.workperiods" /></td>
				</tr>

				<!-- 	<tr>

					<c:set var="check" value="" />
					<c:if test="${code!=null && code!=''}">
						<c:set var="check" value="disabled" />
					</c:if>

						<td nowrap class="formReq" width="30%"><abc:i18n
								property="attendance.caption.vacationCode" /> <fmt:message
								key="attendance.caption.vacationCode" /></td>


						<td class="formBodControl">
								<input size="8" maxlength="8" type="text"
									value="${vacation.vacation}"
									readonly="readonly" />
							</td>
				</tr>
 -->

				<tr>

					<td nowrap class="formReq" width="30%" colspan="2"><abc:i18n
							property="commons.caption.arName" /> <fmt:message
							key="commons.caption.arName" /></td>

					<td class="formBodControl" width="70%"><spring:bind
							path="period.workperiods.name">
							<input size="40" type="text" name="${status.expression}" id="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
				
					<td nowrap class="formReq" width="30%" colspan="2"><abc:i18n
							property="commons.caption.enName" /> <fmt:message
							key="commons.caption.enName" /></td>

					<td class="formBodControl" width="70%"><spring:bind
							path="period.workperiods.ename">
							<input size="40" type="text" name="${status.expression}" id="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
				</tr>
				<tr>
					<td nowrap class="formReq" width="30%" colspan="2"><abc:i18n
							property="attendance.caption.start" /> <fmt:message
							key="attendance.caption.start" /></td>

					<td class="formBodControl"><spring:bind
							path="period.start_date">
							<input type="text" class="calendar" class="MM_from_d" title="ccc"  readonly="readonly"
								autocomplete="off" dir="ltr" name="${status.expression}"
								id="${status.expression}" value="${status.value}" />
						</spring:bind></td>
						
						<td nowrap class="formReq" width="30%" colspan="2"><abc:i18n
							property="attendance.caption.end" /> <fmt:message
							key="attendance.caption.end" /></td>

					<td class="formBodControl"><spring:bind
							path="period.end_date">
							<input type="text" class="calendar" class="MM_from_d" title="ccc"  readonly="readonly"
								autocomplete="off" dir="ltr" name="${status.expression}"
								id="${status.expression}" value="${status.value}" />
						</spring:bind></td>
				</tr>
				<tr>

					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.defaultperiod" /> <fmt:message
							key="attendance.caption.defaultperiod" /></td>
					<td class="formBod"><spring:bind path="period.is_default">
							<input type="checkbox" name="${status.expression}" id="${status.expression}"
								${status.value=='1'?'checked':''} />
						</spring:bind></td>
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.periodstartdate" /> <fmt:message
							key="attendance.caption.periodstartdate" /></td>
					<td class="formBod">

						<div class="input-group clockpicker" data-placement="left"
							data-align="top" data-autoclose="true">
							<spring:bind path="period.time_in">
								<input type="text" class="form-control" id="${status.expression}"
									name="${status.expression}" value="${status.value}"
									readonly="readonly">
							</spring:bind>
							<span class="input-group-addon"> <span
								class="glyphicon glyphicon-time"></span>
							</span>
						</div> <script type="text/javascript">
						$('.clockpicker').clockpicker();
					</script>

					</td>

					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.periodduration" /> <fmt:message
							key="attendance.caption.periodduration" /></td>
					<td class="formBodControl" width="70%"><spring:bind
							path="period.required_hours">
							<input type="text" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
				</tr>

				<tr>
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.delaycalculated" /> <fmt:message
							key="attendance.caption.delaycalculated" /></td>
					<td class="formBod"><input type="checkbox" id ="considerdelay" onchange="check('considerdelay','alloweddelay');"
						name="considerdelay" ${period.consider_delay==1?'checked':''} />
					</td>

					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.alloweddelayminutes" /> <fmt:message
							key="attendance.caption.alloweddelayminutes" /></td>

					<td colspan="3" class="formBodControl" width="70%"><spring:bind
							path="period.allowance_minute">
							<input size="20" type="text" id="alloweddelay" name="${status.expression}" ${period.consider_delay==0?'readOnly':''}
								value="${status.value}" />
						</spring:bind></td>
				</tr>


				<tr>
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.earlydismissalcalculated" /> <fmt:message
							key="attendance.caption.earlydismissalcalculated" /></td>
					<td class="formBod"><input type="checkbox" id ="considerearly" onchange="check('considerearly','allowedearly');"
						name="considerearly" ${period.consider_early==1?'checked':''} />
					</td>

					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.alloweddismissalminutes" /> <fmt:message
							key="attendance.caption.alloweddismissalminutes" /></td>

					<td colspan="3" class="formBodControl" width="70%"><spring:bind
							path="period.allowance_minute2">
							<input size="20" type="text" id="allowedearly" name="${status.expression}"  ${period.consider_early==0?'readOnly':''}
								value="${status.value}" />
						</spring:bind></td>
				</tr>


				<tr>
					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.overtimecalculated" /> <fmt:message
							key="attendance.caption.overtimecalculated" /></td>
					<td class="formBod"><input type="checkbox"
						name="considerovertime" id ="considerovertime" onchange="check('considerovertime','allowedovertime');"
						${period.consider_overtime==1?'checked':''} /></td>

					<td nowrap class="formReq" width="30%"><abc:i18n
							property="attendance.caption.minimumovertimeminutes" /> <fmt:message
							key="attendance.caption.minimumovertimeminutes" /></td>

					<td colspan="3" class="formBodControl" width="70%"><spring:bind
							path="period.min_overtime_minutes">
							<input size="20" type="text" id="allowedovertime" name="${status.expression}"  ${period.consider_overtime==0?'readOnly':''}
								value="${status.value}" />
						</spring:bind></td>
				</tr>


				<tr id="btn">
					<td colspan="4" align="center"><abc:i18n
							property="commons.button.save" /><input type="submit"
						name="save" value="<fmt:message key="commons.button.save"/>"
						class="button" />&nbsp;&nbsp;&nbsp; <abc:i18n
							property="commons.button.cancel" /><input type="reset"
						name="cancel" value="<fmt:message key="commons.button.cancel"/>"
						class="button" /></td>
				</tr>
			</table>
		</tr>

	</table>
</form>


<%@ include file="/web/common/includes/footer.jsp"%>

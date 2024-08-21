<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<abc:security property="1034"/>


<script type="text/javascript">

function periodChanged() {
	alert("emp changed");
}
	function addRow() {
		var table = document.getElementById("periods");
		//alert(table);
		var rowLength = table.rows.length;
		//alert(rowLength);
		var rowCount = (rowLength);
		//alert(rowCount);
		//alert(rowLength);
		//var tr = table.insertRow(rowLength);
		var prevRow = table.rows[rowCount-1];
		//alert(prevRow);
		var tr = prevRow.cloneNode(true);
		
		///////////////////////////////////////
		var select = tr.cells[0].children[0];
		select.id='empPeriods['+rowCount+'].work_period';
		select.name='empPeriods['+rowCount+'].work_period';
		//alert(select.id);
		var select = select.options[0];
		select.selected = true;
		
//		for(var j=1; j<vacList.length; j++) {
	//		var vac = vacList[j];
		//	vac.selected = false;
		//}
		///////////////////////////////////////
		//alert(tr.cells[1].children[0]);
		var startdate = tr.cells[1].children[0];
		startdate.className="";
		startdate.classList.remove('calendar');
		startdate.classList.remove('hasCalendarsPicker');
		startdate.classList.add('calendar', 'MM_from_d');
		//alert(startdate.id);
		//alert(startdate.name);
		//alert(startdate.title);
		startdate.id='empPeriods['+rowCount+'].start_date';
		startdate.name='empPeriods['+rowCount+'].start_date';
		startdate.title='start_date'+rowCount+'';
		
		startdate.value='';
		//alert(startdate.id);
		//alert(startdate.name);
		//alert(startdate.title);
		////////////////////////////////////////
		///////////////////////////////////////
		//alert(tr.cells[2].children[0]);
		var enddate = tr.cells[2].children[0];
		//alert(serviceYear.name);
		enddate.id='empPeriods['+rowCount+'].end_date';
		enddate.name='empPeriods['+rowCount+'].end_date';
		enddate.title='end_date'+rowCount+'';
		enddate.value='';
		////////////////////////////////////////
		
		table.append(tr);
	}
	function onsubmit(data) {
		alert(data);
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

<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/timepicker/jquery.multiselect.css" />
<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/timepicker/jquery.multiselect.filter.css" />
<script type="text/javascript"
	src="/Requests/web/common/timepicker/jquery.multiselect.min.js"></script>
<script type="text/javascript"
	src="/Requests/web/common/timepicker/jquery.multiselect.filter.min.js"></script>

<form id="empworkperiodForm" name="empworkperiodForm" method="POST"  action="<c:url value="/attendance/empWorkPeriodForm.html"/>">

	<input type="hidden" id="success" name="success" value="${success}"/>
	<input type="hidden" id="periodsSize" id="periodsSize" value="${periods.empPeriods.size()}"/>
	<input type="hidden" id="emp_code" name="emp_code" value="${empCode}"/>
	<table width="90%" border="0" cellspacing="0" cellpadding="0"
		style="padding-right: 10px">

		<tr>
			<td><spring:bind path="periods.*">
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
				<table width="80%" align="center" class="sofT">
					<tr id="head_1_ep">

						<td colspan="4" nowrap><c:if test="${success=='true'}"><font color="blue">
								<abc:i18n property="attendance.caption.empworkperiodsuccess" />
								<fmt:message key="attendance.caption.empworkperiodsuccess" /></font>
							</c:if></td>
					</tr>
					<tr id="head_1_ep">
						<td class="helpTitle" colspan="4" nowrap><abc:i18n
								property="attendance.header.empworkperiod" /> <fmt:message
								key="attendance.header.empworkperiod" /> <span class="add_user" id="add_user"
							title="<fmt:message key="commons.button.add" />"></span></td>
					</tr>
					<tr>

						<td class="formBodControl"><abc:i18n
								property="requestsApproval.caption.userCode" /> <fmt:message
								key="requestsApproval.caption.userCode" /></td>
						<td class="formBodControl"><spring:bind
								path="periods.empCode">
								<select name="${status.expression}" 
									multiple="multiple" id="${status.expression}">
									<c:forEach items="${empBasic}" var="emp">
										<option value="${emp.empCode}"
											${periods.empCode.empCode==emp.empCode?'selected':''}
											title="${emp.department.name}">${emp.empCode}-${emp.empName}</option>
									</c:forEach>
								</select>
							</spring:bind></td>
							
							
					<!-- 	<td class="formBodControl"><abc:i18n
								property="attendance.caption.department" /> <fmt:message
								key="attendance.caption.department" /></td>
						<td class="formBodControl"><input type="text" id="department"
							value="${periods.empCode.department.name}" name="department"
							disabled="disabled" /></td> -->
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table width="80%" align="center" class="sofT">
					<tr>
						<td width="39%" class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.workperiod" /> <fmt:message
								key="attendance.caption.workperiod" /></td>
						<td width="28%" class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.start" /> <fmt:message
								key="attendance.caption.start" /></td>
						<td width="28%" class="helpHed" nowrap="nowrap"><abc:i18n
								property="attendance.caption.end" /> <fmt:message
								key="attendance.caption.end" /></td>
						<td width="5%" class="helpHed">&nbsp;</td>

					</tr>
					<tr>
				
						<td colspan="4"><table id="periods" width="100%">
								<c:forEach items="${periods.empPeriods}" var="record"
									varStatus="loop">
									<tr height=20 bgcolor="#F8F8F8">

										<td width="40%" class="helpBod" nowrap>${periods.empPeriods[loop.index].work_period.workperiods}<spring:bind
												path="periods.empPeriods[${loop.index}].work_period">
												<select name="${status.expression}"
													id="${status.expression}">
													<option value=""><fmt:message
															key="commons.caption.select" /></option>
													<c:forEach items="${workPeriods}" var="period">
														<option value="${period.workperiods.workperiods}"
															${period.workperiods.workperiods== periods.empPeriods[loop.index].work_period.workperiods?'selected':''}>${period.workperiods.name}</option>
													</c:forEach>
												</select>
											</spring:bind></td>

										<td width="25%" class="formBodControl"><spring:bind
												path="periods.empPeriods[${loop.index}].start_date">
												<input type="text" class="calendar" class="MM_from_d"
													title="start_date${loop.index}" readonly="readonly" autocomplete="off"
													dir="ltr" name="${status.expression}"
													id="${status.expression}" value="${status.value}" />
											</spring:bind></td>

										<td width="25%" class="formBodControl"><spring:bind
												path="periods.empPeriods[${loop.index}].end_date">
												<input type="text" class="calendar" class="MM_to_d"
													title="end_date${loop.index}" readonly="readonly" autocomplete="off"
													dir="ltr" name="${status.expression}"
													id="${status.expression}" value="${status.value}" />
											</spring:bind></td>

										<td width="10%" align="center" class="helpBod"><span
											class="remove_user"
											onclick="removeRule(${record.start_date});"
											title="<fmt:message
										key="commons.button.delete" />">
										</span></td>

									</tr>
								</c:forEach>
							</table></td>
					</tr>
				</table>
				<table width="100%">
					<tr id="btn">
						<td colspan="4" align="center"><input type="submit" onclick="onsubmit()"
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
<script type="text/javascript">
$("span#add_user").on('click', function() {
    //var $val = $('#add_user').text();
    //alert($val);
    var selected = $(".select").multiselect("widget").find("input:checked").val();
     window.location.href ="/Requests/attendance/empWorkPeriodForm.html?new_period=true&emp="+selected;
});
$("select:first").multiselect({ 
		header: false,
	 	selectedList: 4,
	 	show: ["bounce", 200],
		  
	    click: function(e){
	       if( $(this).multiselect("widget").find("input:checked").length > 1 ){
	           //warning.addClass("error").removeClass("success").html("You can only one employee!");
	           return false;
	       } else {
	         //  warning.addClass("success").removeClass("error").html("");
	       }
	   }
	   ,
	   close: function(e){
		   var selected = $("select:first");
		   selected.each(function(index){
			   var dep = $(this).multiselect("widget").find("input:checked").attr("title");
			   $( "input[id][name$='department']" ).val(dep);
			   window.location.href ="/Requests/attendance/empWorkPeriodForm.html?emp="+$(this).multiselect("widget").find("input:checked").val();
			  // $.post("/Requests/attendance/empWorkPeriodForm.html",
				//	   {
				  //		 empCode: $(this).multiselect("widget").find("input:checked").val()
					//   },
					  // function(data, status){
					    // alert( "Status: " + status);
					   //});
		   });
	   }
	}).multiselectfilter();
	
$("select:first").multiselect({
	  header: "",
	  selectedList: 4,
	   show: ["bounce", 200],
	   hide: ["explode", 1000]}).multiselectfilter();
</script>

<%@ include file="/web/common/includes/footer.jsp"%>
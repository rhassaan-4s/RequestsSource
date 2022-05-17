<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true" />


<html>
<head>
<script type='text/javascript' src='/Requests/dwr/interface/attendanceManager.js'></script>
<script type='text/javascript' src='/Requests/dwr/engine.js'></script>
<title>Insert title here</title>
</head>
<body>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>


	<script type="text/javascript">
		function drawAbsenceAttendanceByDepartment() {
			var hostName=document.getElementById('hostName').value;
			 var serviceName=document.getElementById('serviceName').value;
			 var userName=document.getElementById('userName').value;
			 var password=document.getElementById('password').value;
			attendanceManager.getNumberOfAttendeesAndWorkersByDepartment(fillChartByDepartment,hostName,serviceName,userName,password);
		}
		
		function fillChartByDepartment(data) {
		//alert(data);

			var absence = data[0];
			var attendance = data[1];
			var locations = data[2];

			//alert("absence " + absence[0]);
			//alert("attendance " + attendance[0]);
			//alert("locations " + locations[0]);
			var msg = {
				report2 : "<fmt:message key="attendance.caption.absenceAttendanceByDepartment"/>",
			};
			var msgLab = {
				label1 : "<fmt:message key="attendance.caption.absence" />",
				label2 : "<fmt:message key="attendance.caption.attendance"/>",
			};
			var ab = data.absence;
			var at = data.attendance;
			new Chart(document
					.getElementById("dashboardAbsenceAttendanceByDepartment"),
					{
						type : 'bar',
						data : {
							datasets : [ {
								backgroundColor : 'rgba(255, 99, 132, 0.2)',
								borderColor : 'rgba(255, 99, 132, 1)',
								type : 'bar',
								label : msgLab.label1,
								data : absence
							}, {
								backgroundColor : 'rgba(54, 162, 235, 0.2)',
								borderColor : 'rgba(54, 162, 235, 1)',
								type : 'bar',
								label : msgLab.label2,
								data : attendance,
							} ],
							labels : locations
						},

						options : {
							legend : {
								display : true
							},
							title : {
								display : true,
								text : msg.report2
							},

							scales : {
								yAxes : [ {
									ticks : {
										beginAtZero : true
									}
								} ]
							}

						}

					});
		}
		
		function drawRequests() {
			var hostName=document.getElementById('hostName').value;
			 var serviceName=document.getElementById('serviceName').value;
			 var userName=document.getElementById('userName').value;
			 var password=document.getElementById('password').value;
			attendanceManager.getDashboardRequests(fillRequests,hostName,serviceName,userName,password);
		}
		
		function fillRequests(data) {
		//alert(data);

			var employees = data[0];
			var requestTypes = data[1];

			//alert("absence " + absence[0]);
			//alert("attendance " + attendance[0]);
			//alert("locations " + locations[0]);
			var msg = {
				report3 : "<fmt:message key="attendance.caption.requests"/>",
			};
			var msgLab = {
				label1 : "<fmt:message key="attendance.caption.absence" />",
				label2 : "<fmt:message key="attendance.caption.attendance"/>",
			};
			var ab = data.absence;
			var at = data.attendance;
			new Chart(document
					.getElementById("dashboardRequests"),
					{
						type : 'bar',
						data : {
							datasets : [ {
								backgroundColor : 'rgba(255, 99, 132, 0.2)',
								borderColor : 'rgba(255, 99, 132, 1)',
								type : 'bar',
								data : employees
							}],
							labels : requestTypes
						},

						options : {
							legend : {
								display : false
							},
							title : {
								display : true,
								text : msg.report3
							},

							scales : {
								yAxes : [ {
									ticks : {
										beginAtZero : true
									}
								} ]
							}

						}

					});
		}

		function drawAbsenceAttendance() {
			var msg = {
				report1 : "<fmt:message key="attendance.caption.absenceAttendance" />",
			};
			var msgLab = {
				label1 : "<fmt:message key="attendance.caption.absence" />",
				label2 : "<fmt:message key="attendance.caption.attendance"/>",
			};
			var ab = document.getElementById("absence").value;
			var at = document.getElementById("attendance").value;
			new Chart(document.getElementById("dashboardAbsenceAttendance"), {
				type : 'bar',
				data : {
					labels : [ msgLab.label1, msgLab.label2 ],
					datasets : [ {
						backgroundColor : [ 'rgba(255, 99, 132, 0.2)',
								'rgba(54, 162, 235, 0.2)' ],
						borderColor : [ 'rgba(255, 99, 132, 1)',
								'rgba(54, 162, 235, 1)' ],
						borderWidth : 1,
						barThickness : 5,
						maxBarThickness : 1,
						data : [ ab, at ]
					} ]
				},

				options : {
					legend : {
						display : false
					},
					title : {
						display : true,
						text : msg.report1
					},

					scales : {
						yAxes : [ {
							ticks : {
								beginAtZero : true
							}
						} ]
					}

				}

			});
		}
	</script>



	<form method="POST"
		action="<c:url value="/attendance/dashboard.html"/>">

		<input type="hidden" id="absence" name="absence" value="${absence}" />
		<input type="hidden" id="attendance" name="attendance"
			value="${attendance}" />
		 <input type="hidden" name="hostName" id="hostName" value="${settings.server}"/>
		 <input type="hidden" name="serviceName" id="serviceName" value="${settings.service}"/>
		 <input type="hidden" name="userName" id="userName" value="${settings.username}"/>
		 <input type="hidden" name="password" id="password" value="${settings.password}"/>

		<table width="90%" border="0" cellspacing="0" cellpadding="0"
			style="padding-right: 10px">
			<table width="70%" border="0" cellspacing="1" cellpadding="0" id="ep"
				style="margin-right: 40px">
				<tr>
					<td class="tableHeader" colspan="2" nowrap><abc:i18n
							property="attendance.header.dashboard" /> <fmt:message
							key="attendance.header.dashboard" /></td>
				</tr>
				<tr>
					<td>
						<table>
							<tr>
								<td><a href="javascript:void(0);"
									onclick="drawAbsenceAttendance();"> <abc:i18n
											property="attendance.caption.dashboardAbsenceAttendance" />
										<fmt:message
											key="attendance.caption.dashboardAbsenceAttendance" />
								</a></td>
							</tr>
							<tr>
								<td><a href="javascript:void(0);"
									onclick="drawAbsenceAttendanceByDepartment();"> <abc:i18n
											property="attendance.caption.dashboardAbsenceAttendanceByDepartment" />
										<fmt:message
											key="attendance.caption.dashboardAbsenceAttendanceByDepartment" />
								</a></td>
							</tr>
							<tr>
								<td><a href="javascript:void(0);"
									onclick="drawRequests();"> <abc:i18n
											property="attendance.caption.dashboardRequests" />
										<fmt:message
											key="attendance.caption.dashboardRequests" />
								</a></td>
							</tr>
						</table>
					</td>
					<td><canvas id="dashboardAbsenceAttendance" width="150" height="75"></canvas>
					<canvas id="dashboardAbsenceAttendanceByDepartment" width="150" height="75"></canvas>
					<canvas id="dashboardRequests" width="150" height="75"></canvas></td>
				</tr>

			</table>

		</table>

	</form>



	<%@ include file="/web/common/includes/footer.jsp"%>
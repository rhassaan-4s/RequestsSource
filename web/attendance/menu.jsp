<%@ include file="/web/common/includes/taglibs.jsp"%>
<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/css/all.css">
<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/css/autoComplete.css">
<link type="text/css" href="/Requests/web/common/css/main.css"
	rel="stylesheet" media="screen" />
<link rel="stylesheet" type="text/css" media="print, handheld"
	href="/Requests/web/common/css/print.css">



<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="authz"%>
<div id="smoothmenu1" class="ddsmoothmenu">
	<ul>
		<li><a href="#"><fmt:message
					key="attendance.menu.basicScreens" /></a>
			<ul>


				<!-- <li>
			<abc:i18n property="attendance.header.religion" />
			<a href="/Requests/attendance/religionView.html"> &nbsp;&nbsp;
				<fmt:message key="attendance.header.religion" />
			</a>
		</li> -->

				<li><abc:i18n property="attendance.header.qualification" /> <a
					href="/Requests/attendance/qualificationView.html">
						&nbsp;&nbsp; <fmt:message key="attendance.header.qualification" />
				</a></li>

				<li><abc:i18n property="attendance.header.department" /> <a
					href="/Requests/attendance/departmentView.html"> &nbsp;&nbsp; <fmt:message
							key="attendance.header.department" />
				</a></li>


				<li><abc:i18n property="attendance.header.title" /> <a
					href="/Requests/attendance/titleView.html"> &nbsp;&nbsp; <fmt:message
							key="attendance.header.title" />
				</a></li>


				<li><abc:i18n property="attendance.header.empBasic" /> <a
					href="/Requests/attendance/empBasicView.html"> &nbsp;&nbsp; <fmt:message
							key="attendance.header.empBasic" />
				</a></li>
				<li><abc:i18n property="requestsapproval.header.employmenthistory" /> <a
					href="/Requests/attendance/empHistView.html"> &nbsp;&nbsp; <fmt:message
							key="requestsapproval.header.employmenthistory" />
				</a></li>

				<li><a href="#"><fmt:message
							key="attendance.header.vacandworkperiods" /></a>
					<ul>
						<li><abc:i18n property="attendance.header.vacRules" /> <a
							href="/Requests/attendance/vacRuleForm.html"> &nbsp;&nbsp; <fmt:message
									key="attendance.header.vacRules" />
						</a></li>
						<li><abc:i18n property="attendance.header.publicleaves" /> <a
							href="/Requests/attendance/publicLeavesForm.html"> &nbsp;&nbsp; <fmt:message
									key="attendance.header.publicleaves" />
						</a></li>
						<li><abc:i18n property="attendance.header.vacation" /> <a
							href="/Requests/attendance/vacationView.html"> &nbsp;&nbsp; <fmt:message
									key="attendance.header.vacation" />
						</a></li>
						<li><abc:i18n property="attendance.header.workperiods" /> <a
							href="/Requests/attendance/workperiodView.html"> &nbsp;&nbsp; <fmt:message
									key="attendance.header.workperiods" />
						</a></li>
						<li><abc:i18n property="attendance.header.triggervacbalance" /> <a
							href="/Requests/attendance/triggerVacBalanceView.html"> &nbsp;&nbsp; <fmt:message
									key="attendance.header.triggervacbalance" />
						</a></li>
					</ul></li>

			</ul></li>
		<li><a href="#"><fmt:message key="attendance.header.transactions" /></a>
			<ul>
				<li><abc:i18n property="attendance.header.empworkperiod" /> <a
							href="/Requests/attendance/empWorkPeriodForm.html"> &nbsp;&nbsp; <fmt:message
									key="attendance.header.empworkperiod" />
						</a></li>
				<li><abc:i18n property="attendance.header.triggerattendanalysis" /> <a
					href="/Requests/attendance/triggerAttendAnalysisView.html"> &nbsp;&nbsp; <fmt:message
							key="attendance.header.triggerattendanalysis" />
				</a></li>
			</ul></li>
		<li><a href="#"><fmt:message key="commons.menu.reports" /></a>
			<ul>
				<li><abc:i18n property="attendance.header.dashboard" /> <a
					href="/Requests/attendance/dashboard.html"> &nbsp;&nbsp; <fmt:message
							key="attendance.header.dashboard" />
				</a></li>
			</ul></li>
		<li><a href="/Requests/security/logout.html"><fmt:message
					key="commons.menu.logout" /></a></li>
	</ul>
	<br style="clear: left" /> <br style="clear: left" />
</div>

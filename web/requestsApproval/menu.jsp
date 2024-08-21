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
					key="requestsApproval.menu.basicScreens" /></a>
			<ul>

				<li><a
					href="/Requests/requestsApproval/attendanceSignInOutForm.html">
						<abc:i18n property="requestsApproval.header.AttendanceSignInOut" />
						<fmt:message key="requestsApproval.header.AttendanceSignInOut" />
				</a></li>
				<li><a
					href="/Requests/requestsApproval/loginUsersRequestsForm.html">
						<abc:i18n property="requestsApproval.header.loginUsersRequests" />
						<fmt:message key="requestsApproval.header.loginUsersRequests" />
				</a></li>
				<li><a
					href="/Requests/requestsApproval/loginUsersRequestsView.html">
						<abc:i18n
							property="requestsApproval.header.loginUsersRequestsView" /> <fmt:message
							key="requestsApproval.header.loginUsersRequestsView" />
				</a></li>

				<authz:authorize access="hasAuthority('CAN_VIEW_page_requestsApproval')">
					<li><a href="/Requests/requestsApproval/reports.html"> <abc:i18n
								property="requestsApproval.menu.allReports" /> <fmt:message
								key="requestsApproval.menu.allReports" />
					</a></li>
				</authz:authorize>

				<%
		com._4s_.common.model.Settings settings = (com._4s_.common.model.Settings) session.getAttribute("settings");
	System.out.println("settings " + settings.getId());
	System.out.println("getAttendanceRequestEn " + settings.getAttendanceRequestEn());
		if (settings.getAttendanceRequestEn().equals(new Boolean(true))) {
	%>
				<li><a
					href="/Requests/requestsApproval/attendanceRequestForm.html"> <abc:i18n
							property="requestsApproval.header.AttendanceRequest" /> <fmt:message
							key="requestsApproval.header.AttendanceRequest" />
				</a></li>
				<% } %>


			</ul></li>

		<authz:authorize access="hasAuthority('CAN_VIEW_page_requestsApproval')">
			<li><a href="#"><fmt:message
						key="requestsApproval.menu.reports" /></a>
				<ul>
					<li><a
						href="/Requests/requestsApproval/empRequestsReportsForm.html?requestType=3">
							<abc:i18n
								property="requestsApproval.header.empRequestsReportsForm3" /> <fmt:message
								key="requestsApproval.header.empRequestsReportsForm3" />
					</a></li>

					<li><abc:i18n
							property="requestsApproval.header.empAnnualVacationsReportsForm" />
						<a
						href="/Requests/requestsApproval/empRequestsReportsForm.html?requestType=2">
							<fmt:message
								key="requestsApproval.header.empAnnualVacationsReportsForm" />
					</a></li>

					<li><a
						href="/Requests/requestsApproval/empRequestsReportsForm.html?requestType=1">
							<abc:i18n
								property="requestsApproval.header.empSpecialVacationsReportsForm" />
							<fmt:message
								key="requestsApproval.header.empSpecialVacationsReportsForm" />
					</a></li>

					<li><a
						href="/Requests/requestsApproval/empRequestsReportsForm.html?requestType=7">
							<abc:i18n
								property="requestsApproval.header.empErrandsReportsForm" /> <fmt:message
								key="requestsApproval.header.empErrandsReportsForm" />
					</a></li>


					<%
				if (settings.getAttendanceRequestEn().equals(new Boolean(true))){ 
			%>
					<li><a
						href="/Requests/requestsApproval/attendanceRequestsReports.html">
							<abc:i18n
								property="requestsApproval.header.attendanceRequestsReports" />
							<fmt:message
								key="requestsApproval.header.attendanceRequestsReports" />
					</a></li>
					<% } %>

				</ul></li>
		</authz:authorize>

		<li><a href="#"><fmt:message key="commons.menu.reports" /></a>
			<ul>
				<li><a
					href="/Requests/requestsApproval/timeAttendanceReport.html"> <abc:i18n
							property="requestsApproval.header.timeAttendanceReport" /> <fmt:message
							key="requestsApproval.header.timeAttendanceReport" />
				</a></li>

				<%
					if (settings.getAnnualVacBalDaysEnabled().equals(new Boolean(true))) {
				%>
				<li><a
					href="/Requests/requestsApproval/annualVacationBalance.html"> <abc:i18n
							property="requestsApproval.header.annualVacationBalance2" /> <fmt:message
							key="requestsApproval.header.annualVacationBalance2" />
				</a></li>
				<%
					}
				%>
				<%
					if (settings.getAttendanceRequestEn().equals(new Boolean(true))) {
				%>
				<li><a
					href="/Requests/requestsApproval/attendanceVacationReport.html"><abc:i18n
							property="requestsApproval.header.attendanceVacationReport" /> <fmt:message
							key="requestsApproval.header.attendanceVacationReport" /> </a></li>
				<%
					}
				%>

				<li><a
					href="/Requests/requestsApproval/requestStatusReports.html"> <abc:i18n
							property="requestsApproval.header.requestStatusReports" /> <fmt:message
							key="requestsApproval.header.requestStatusReports" />
				</a></li>
			</ul></li>


		<%if(settings.getCompanyRulesEn().equals(new Boolean(true))){ %>
		<li><a href="#"><fmt:message key="commons.menu.help" /></a>
			<ul>
				<li><abc:i18n property="requestsApproval.header.CompanyRules" />
					<a href="Requests/../../CompanyRules.pdf"> <fmt:message
							key="requestsApproval.header.CompanyRules" />
				</a></li>
				<li><abc:i18n property="requestsApproval.header.manual" /> <a
					href="Requests/../../manual.pdf"> <fmt:message
							key="requestsApproval.header.manual" />
				</a></li>
			</ul></li>
		<%} %>

		<li><a href="/Requests/security/logout.html"><fmt:message
					key="commons.menu.logout" /></a></li>
	</ul>
	<br style="clear: left" /> <br style="clear: left" />
</div>

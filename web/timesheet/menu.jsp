<%@page import="com._4s_.timesheet.model.TimesheetSpecs"%>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/css/all.css">
<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/css/autoComplete.css">
<link type="text/css" href="/Requests/web/common/css/main.css"
	rel="stylesheet" media="screen" />
<link rel="stylesheet" type="text/css" media="print, handheld"
	href="/Requests/web/common/css/print.css">

	<%
		TimesheetSpecs specs = (TimesheetSpecs) session.getAttribute("timesheetSpecs");
		Object partName1 = session.getAttribute("partName1");
		System.out.println(partName1);
		Object partName2 = session.getAttribute("partName2");
		System.out.println(partName2);
		Object partName3 = session.getAttribute("partName3");
		System.out.println(partName3);
	%>
	
<div id="smoothmenu1" class="ddsmoothmenu">
	<ul>
		<authz:authorize access="hasRole('CAN_VIEW_page_requestsApproval')">

			<li><a href="#"><fmt:message
						key="timesheet.menu.basicScreens" /></a>
				<ul>
					<li><a
						href="/Requests/timesheet/activityView.html"> &nbsp;&nbsp; <fmt:message
								key="timesheet.header.activity" />
					</a></li>
					<%
						if (partName1!=null) {
					%>
					<li><a
						href="/Requests/timesheet/partView.html?partNo=1"> &nbsp;&nbsp; <%out.println((String)partName1);%>
					</a></li>

					<%
						}
					%>


					<%
						if (partName2!=null) {
					%>
					<li><a
						href="/Requests/timesheet/partView.html?partNo=2"> &nbsp;&nbsp; <%out.println((String)partName2);%>
					</a></li>

					<%
						}
					%>
					
					
					<%
						if (partName3!=null) {
					%>
					<li><a
						href="/Requests/timesheet/partView.html?partNo=3"> &nbsp;&nbsp; <%out.println((String)partName3);%>
					</a></li>

					<%
						}
					%>

					<li><abc:i18n property="timesheet.header.specs" /> <a
						href="/Requests/timesheet/timesheetSpecs.html"> &nbsp;&nbsp; <fmt:message
								key="timesheet.header.specs" />
					</a></li>

				</ul></li>
		</authz:authorize>
		
			<li><a href="/Requests/timesheet/timesheetTrans.html"><fmt:message
						key="timesheet.header.timesheetTrans" /></a></li>
		<!-- 	
	<authz:authorize access="hasRole('CAN_VIEW_page_requestsApproval')" >
	<li><a href="#"><fmt:message key="requestsApproval.menu.reports" /></a>
		<ul>
	        <li>
	        	<abc:i18n property="requestsApproval.header.empRequestsReportsForm3" />
	        	<a href="/Requests/requestsApproval/empRequestsReportsForm.html?requestType=3"> &nbsp;&nbsp;
	        	<fmt:message key="requestsApproval.header.empRequestsReportsForm3" />
	        	</a>
			</li>
			 
	         <li>
	        	<abc:i18n property="requestsApproval.header.empAnnualVacationsReportsForm" />
	        	<a href="/Requests/requestsApproval/empRequestsReportsForm.html?requestType=2"> &nbsp;&nbsp;
	        	<fmt:message key="requestsApproval.header.empAnnualVacationsReportsForm" />
	        	</a>
			</li>
	        
			<li>
	        	<abc:i18n property="requestsApproval.header.empSpecialVacationsReportsForm" />
	        	<a href="/Requests/requestsApproval/empRequestsReportsForm.html?requestType=1"> &nbsp;&nbsp;
	        	<fmt:message key="requestsApproval.header.empSpecialVacationsReportsForm" />
	        	</a>
			</li>
			
	         <li>
	        	<abc:i18n property="requestsApproval.header.empErrandsReportsForm" />
	        	<a href="/Requests/requestsApproval/empRequestsReportsForm.html?requestType=7"> &nbsp;&nbsp;
	        	<fmt:message key="requestsApproval.header.empErrandsReportsForm" />
	        	</a>
			</li>
			
			
				<li>
		        	<abc:i18n property="requestsApproval.header.attendanceRequestsReports" />
		        	<a href="/Requests/requestsApproval/attendanceRequestsReports.html"> &nbsp;&nbsp;
		        	<fmt:message key="requestsApproval.header.attendanceRequestsReports" />
		        	</a>
				</li>
			
		</ul>		
	</li>
	</authz:authorize>
	 -->
		<!-- 
	<li><a href="#"><fmt:message key="commons.menu.reports" /></a>
			<ul>
	        <li>
	        	<abc:i18n property="requestsApproval.header.timeAttendanceReport" />
	        	<a href="/Requests/requestsApproval/timeAttendanceReport.html"> &nbsp;&nbsp;
	        	<fmt:message key="requestsApproval.header.timeAttendanceReport" />
	        	</a>
			</li>

				<li><abc:i18n
						property="requestsApproval.header.annualVacationBalance2" /> <a
					href="/Requests/requestsApproval/annualVacationBalance.html">
						&nbsp;&nbsp; <fmt:message
							key="requestsApproval.header.annualVacationBalance2" />
				</a></li>
				<li><abc:i18n
						property="requestsApproval.header.attendanceVacationReport" /> <a
					href="/Requests/requestsApproval/attendanceVacationReport.html">
						&nbsp;&nbsp; <fmt:message
							key="requestsApproval.header.attendanceVacationReport" />
				</a></li>
				
				<li>
		        	<abc:i18n property="requestsApproval.header.requestStatusReports" />
		        	<a href="/Requests/requestsApproval/requestStatusReports.html"> &nbsp;&nbsp;
		        	<fmt:message key="requestsApproval.header.requestStatusReports" />
		        	</a>
				</li>
			</ul>	
		
	</li>
		
	 -->
		<!-- 
	<li><a href="#"><fmt:message
		key="requestsApproval.menu.settings" /></a>
		<ul>
			<li><a href="/Requests/requestsApproval/changeProfileForm.html"><abc:i18n
				property="requestsApproval.header.changePassword" /> <fmt:message
				key="requestsApproval.header.changePassword" /></a></li>		
		
			<li><a href="/Requests/requestsApproval/annualVacLimitSetting.html"><fmt:message
				key="requestsApproval.header.annualVacLimitSetting" /></a>
			</li>
		</ul>
	</li>
		<li><a href="#"><fmt:message key="commons.menu.help" /></a>
				<ul>
		        <li>
		        	<abc:i18n property="requestsApproval.header.CompanyRules" />
		        	<a href="Requests/../../CompanyRules.pdf"> &nbsp;&nbsp;
		        	<fmt:message key="requestsApproval.header.CompanyRules" />
		        	</a>
				</li>
				 <li>
		        	<abc:i18n property="requestsApproval.header.manual" />
		        	<a href="Requests/../../manual.pdf"> &nbsp;&nbsp;
		        	<fmt:message key="requestsApproval.header.manual" />
		        	</a>
				</li>
				</ul>	
			
		</li>
 -->
		<li><a href="/Requests/security/logout.html"><fmt:message
					key="commons.menu.logout" /></a></li>
	</ul>

	<br style="clear: left" /> <br style="clear: left" />
</div>

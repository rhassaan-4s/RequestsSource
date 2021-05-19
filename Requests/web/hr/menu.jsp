<%@ include file="/web/common/includes/taglibs.jsp"%>
<link type="text/css"  rel="stylesheet" href="/Requests/web/common/css/all.css">
<link type="text/css"  rel="stylesheet" href="/Requests/web/common/css/autoComplete.css">
<link type="text/css" href="/Requests/web/common/css/main.css" rel="stylesheet" media="screen" />
<link rel="stylesheet" type="text/css" media="print, handheld" href="/Requests/web/common/css/print.css">



<%@ taglib uri="http://acegisecurity.org/authz" prefix="authz" %>
<div id="smoothmenu1" class="ddsmoothmenu" >
<ul>
	<li><a href="#"><fmt:message key="hr.menu.basicData" /></a>
		<ul>		
<!-- awlweat :D -->
<!-- 		<li>
			<abc:i18n property="requestsApproval.header.requestTypesView" />
			<a href="/Requests/requestsApproval/requestTypesView.html"> &nbsp;&nbsp;
				<fmt:message key="requestsApproval.header.requestTypesView" />
			</a>
		</li>
-->
			<li>
				<abc:i18n property="requestsApproval.header.loginUsersRequestsView" />
				<a href="/Requests/requestsApproval/loginUsersRequestsView.html"> &nbsp;&nbsp;
					<fmt:message key="requestsApproval.header.loginUsersRequestsView" />
				</a>
			</li>	
			<li><a href="#"><fmt:message key="requestsApproval.menu.requestsApprovalPriorities" /></a>
				<ul>
					<li>
						<abc:i18n property="requestsApproval.header.accessLevelsForm" />
						<a href="/Requests/requestsApproval/accessLevelsForm.html"> &nbsp;&nbsp;
							<fmt:message key="requestsApproval.header.accessLevelsForm" />
						</a>
					</li>				

					<li>
						<abc:i18n property="requestsApproval.header.empReqTypeGroupForm" />
						<a href="/Requests/requestsApproval/empReqTypeGroupForm.html"> &nbsp;&nbsp;
							<fmt:message key="requestsApproval.header.empReqTypeGroupForm" />
						</a>
					</li>				
				</ul>
			</li>		    	
		</ul>
	</li>
	
	<li><a href="#"><fmt:message key="requestsApproval.menu.reports" /></a>
		<ul>
	        <li>
	        	<abc:i18n property="requestsApproval.header.empRequestsReportsForm" />
	        	<a href="/Requests/requestsApproval/empRequestsReportsForm.html?requestType=1"> &nbsp;&nbsp;
	        	<fmt:message key="requestsApproval.header.empRequestsReportsForm" />
	        	</a>
			</li>
			 
	         <li>
	        	<abc:i18n property="requestsApproval.header.empVacationsReportsForm" />
	        	<a href="/Requests/requestsApproval/empRequestsReportsForm.html?requestType=2"> &nbsp;&nbsp;
	        	<fmt:message key="requestsApproval.header.empVacationsReportsForm" />
	        	</a>
			</li>
	        
	         <li>
	        	<abc:i18n property="requestsApproval.header.empErrandsReportsForm" />
	        	<a href="/Requests/requestsApproval/empRequestsReportsForm.html?requestType=3"> &nbsp;&nbsp;
	        	<fmt:message key="requestsApproval.header.empErrandsReportsForm" />
	        	</a>
			</li>
		</ul>		
	</li>
	
	<li><a href="/Requests/security/logout.html"><fmt:message
		key="commons.menu.logout" /></a>
	</li>
</ul>
<br style="clear: left" />
<br style="clear: left" />
</div>

<%@ include file="/web/common/includes/taglibs.jsp"%>
<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/css/all.css">
<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/css/autoComplete.css">
<link type="text/css" href="/Requests/web/common/css/main.css"
	rel="stylesheet" media="screen" />
<link rel="stylesheet" type="text/css" media="print, handheld"
	href="/Requests/web/common/css/print.css">



<%@ taglib uri="http://acegisecurity.org/authz" prefix="authz"%>
<div id="smoothmenu1" class="ddsmoothmenu">
	<ul>
		<li><a href="#"><fmt:message key="hr.menu.basicData" /></a>
			<ul>
				<li><a href="#"><fmt:message
							key="hr.menu.organizationStructureCodes" /></a>
					<ul>
						<li><abc:i18n property="hr.header.internalDivisionTree" /> <a
							href="/Requests/hr/internalLevelView.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.internalDivisionTree" />
						</a></li>

						<li><abc:i18n property="hr.header.countriesView" /> <a
							href="/Requests/hr/countriesView.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.countriesView" />
						</a></li>
						<li><abc:i18n property="hr.header.regionsView" /> <a
							href="/Requests/hr/regionsView.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.regionsView" />
						</a></li>
						<li><abc:i18n property="hr.header.sectorsView" /> <a
							href="/Requests/hr/sectorsView.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.sectorsView" />
						</a></li>

						<li><abc:i18n property="hr.header.locationsView" /> <a
							href="/Requests/hr/locationsView.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.locationsView" />
						</a></li>

						<li><abc:i18n property="hr.header.internalDivisionsView" />
							<a href="/Requests/hr/internalDivisionsView.html">
								&nbsp;&nbsp; <fmt:message key="hr.header.internalDivisionsView" />
						</a></li>
						<li><abc:i18n
								property="hr.header.internalDivisionBranchesView" /> <a
							href="/Requests/hr/internalDivisionBranchesView.html">
								&nbsp;&nbsp; <fmt:message
									key="hr.header.internalDivisionBranchesView" />
						</a></li>
						<li><abc:i18n property="hr.caption.job" /> <a
							href="/Requests/hr/jobsView.html"> &nbsp;&nbsp; <fmt:message
									key="hr.caption.job" />
						</a></li>
					</ul></li>
				<li><abc:i18n property="hr.header.degreesView" /> <a
					href="/Requests/hr/degreesView.html"> &nbsp;&nbsp; <fmt:message
							key="hr.header.degreesView" />
				</a></li>
				<!-- aldaragat el wazeefeya -->

				<li><a href="#"><fmt:message key="hr.menu.evaluation" /></a>
					<ul>
						<li><abc:i18n property="hr.menu.gradesView" /> <a
							href="/Requests/hr/gradesView.html"> &nbsp;&nbsp; <fmt:message
									key="hr.menu.gradesView" />
						</a></li>


						<li><abc:i18n property="hr.header.salaryRaiseView" /> <a
							href="/Requests/hr/salaryRaiseView.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.salaryRaiseView" />
						</a></li>


						<li><abc:i18n property="hr.header.punishmentsView" /> <a
							href="/Requests/hr/punishmentsView.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.punishmentsView" />
						</a></li>
						<li><abc:i18n property="hr.header.endServiceReasonsView" />
							<a href="/Requests/hr/endServiceReasonsView.html">
								&nbsp;&nbsp; <fmt:message key="hr.header.endServiceReasonsView" />
						</a></li>

					</ul></li>


				<li><a href="#"><fmt:message
							key="hr.menu.geographicalLocation" /></a>
					<ul>
						<li><abc:i18n property="hr.header.citiesView" /> <a
							href="/Requests/hr/citiesView.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.citiesView" />
						</a></li>
						<li><abc:i18n property="hr.header.centersView" /> <a
							href="/Requests/hr/centersView.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.centersView" />
						</a></li>

					</ul></li>

				<li><a href="#"><fmt:message key="hr.menu.educationalCodes" /></a>
					<ul>
						<li><a href="#"><fmt:message key="hr.menu.qualification" /></a>
							<ul>
								<li><abc:i18n property="hr.header.schoolsView" /> <a
									href="/Requests/hr/schoolsView.html"> &nbsp;&nbsp; <fmt:message
											key="hr.header.schoolsView" />
								</a></li>
								<li><abc:i18n property="hr.header.instituteView" /> <a
									href="/Requests/hr/instituteForm.html"> &nbsp;&nbsp; <fmt:message
											key="hr.header.instituteView" />
								</a></li>


								<li><abc:i18n property="hr.header.universitiesView" /> <a
									href="/Requests/hr/universitiesView.html"> &nbsp;&nbsp; <fmt:message
											key="hr.header.universitiesView" />
								</a></li>
								<li><abc:i18n property="hr.header.qualificationsView" /> <a
									href="/Requests/hr/qualificationsView.html"> &nbsp;&nbsp; <fmt:message
											key="hr.header.qualificationsView" /></a></li>





								<li><abc:i18n
										property="hr.header.qualificationDivisionTree" /> <a
									href="/Requests/hr/qualificationDivisionTree.html">
										&nbsp;&nbsp; <fmt:message
											key="hr.header.qualificationDivisionTree" />
								</a></li>
							</ul></li>



						<li><a href="#"><fmt:message key="hr.menu.courses" /></a>
							<ul>

								<li><abc:i18n property="hr.menu.coursesView" /> <a
									href="/Requests/hr/coursesView.html"> &nbsp;&nbsp; <fmt:message
											key="hr.menu.coursesView" />
								</a></li>
								<li><abc:i18n property="hr.menu.courseSpecialtiesView" />
									<a href="/Requests/hr/courseSpecialtiesView.html">
										&nbsp;&nbsp; <fmt:message key="hr.menu.courseSpecialtiesView" />
								</a></li>
								<li><abc:i18n property="hr.menu.courseOrganizationsView" />
									<a href="/Requests/hr/courseOrganizationsView.html">
										&nbsp;&nbsp; <fmt:message
											key="hr.menu.courseOrganizationsView" />
								</a></li>

								<li><abc:i18n property="hr.menu.courseResultsView" /> <a
									href="/Requests/hr/courseResultsView.html"> &nbsp;&nbsp; <fmt:message
											key="hr.menu.courseResultsView" />
								</a></li>

							</ul></li>
					</ul></li>

				<li><a href="#"><fmt:message key="hr.menu.vacationCodes" /></a>
					<ul>
						<li><abc:i18n property="vacationRulesForm" /> <a
							href="/Requests/hr/vacationRulesForm.html"> &nbsp;&nbsp; <fmt:message
									key="vacationRulesForm" />
						</a></li>
						<li><abc:i18n property="hr.header.yearAllowVacationTransferForm" /> <a
							href="/Requests/hr/yearAllowVacationTransferForm.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.yearAllowVacationTransferForm" />
						</a></li>
						<li><abc:i18n property="hr.header.vacationsView" /> <a
							href="/Requests/hr/vacationsView.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.vacationsView" />
						</a></li>
						<li><abc:i18n property="hr.header.employeeVacationAtInstallationForm" /> <a
							href="/Requests/hr/employeeVacationAtInstallationForm.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.employeeVacationAtInstallationForm" />
						</a></li>
					</ul></li>

				<li><a href="#"><fmt:message key="hr.menu.healthCodes" /></a>
					<ul>
						<li><abc:i18n property="hr.header.insuranceView" /> <a
							href="/Requests/hr/insuranceView.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.insuranceView" />
						</a></li>
					</ul></li>
					
					

				<li><a href="#"><fmt:message key="hr.menu.employeesData" /></a>
					<ul>
						<li><abc:i18n property="hr.header.nationalitiesView" /> <a
							href="/Requests/hr/nationalitiesView.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.nationalitiesView" />
						</a></li>
						<li><abc:i18n property="hr.header.religionsView" /> <a
							href="/Requests/hr/religionsView.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.religionsView" /></a></li>
						<li><abc:i18n property="hr.header.employeeMaritalStatusView" />
							<a href="/Requests/hr/employeeMaritalStatusView.html">
								&nbsp;&nbsp; <fmt:message
									key="hr.header.employeeMaritalStatusView" />
						</a></li>
						<li><abc:i18n property="hr.header.viewEmployeePhoto" /> <a
							href="/Requests/hr/viewEmployeePhoto.html"> &nbsp;&nbsp; <fmt:message
									key="hr.header.viewEmployeePhoto" /></a></li>


					</ul></li>



			</ul></li>

		<li><a href="/Requests/security/logout.html"><fmt:message
					key="commons.menu.logout" /></a></li>
	</ul>
	<br style="clear: left" /> <br style="clear: left" />
</div>

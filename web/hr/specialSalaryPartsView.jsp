<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>

<abc:security property=""/><br>

<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
	<form id="specialSalaryPartsView" name="specialSalaryPartsView" method="POST" action="<c:url value="/hr/specialSalaryPartsView.html"/>">
 		<input type="hidden"  id="displayed_flag" name="displayed_flag" value="${displayed_flag}"/>
 		<tr>
			<td>
				<table rules="all" align="center" width="70%" class="sofT">
					<tr>
						<td colspan="8" class="helpTitle">
							<abc:i18n property="hr.header.specialSalaryPartsView"/>
							<fmt:message key="hr.header.specialSalaryPartsView"/>
						</td>
					</tr>

					<tr>

					 	<td class="helpHed" nowrap="nowrap">
					 		<abc:i18n property="commons.caption.code"/>
							<fmt:message key="commons.caption.code"/>
						</td>
						<td class="helpHed" nowrap="nowrap">
							<abc:i18n property="commons.caption.name"/>
							<fmt:message key="commons.caption.name"/>
						</td>
						<td class="helpHed" nowrap="nowrap">
							<abc:i18n property="commons.caption.englishName"/>
							<fmt:message key="commons.caption.englishName"/>
						</td>
						<td class="helpHed" nowrap="nowrap">
							<abc:i18n property="hr.caption.displayed_flag" />
							<fmt:message key="hr.caption.displayed_flag" />
						</td>
					
						<td class="helpHed">
						</td>
					</tr>
					
					<c:forEach items="${records}" var="record">
						<tr height=20 bgcolor="#F8F8F8">
                        	<td class="helpBod" nowrap>
								${record.effcode}
							</td>
								  
							<td class="helpBod" nowrap>
					        	${record.effname}
						  	</td>
								  
						   	<td class="helpBod" nowrap>
					        	${record.eng_name}
						  	</td>
						  	
						  	<td class="helpBod" nowrap>
								<c:if test="${record.displayed_flag=='F'}">
									<abc:i18n property="hr.caption.4s" />
									<fmt:message key="hr.caption.4s" />
								</c:if>
								<c:if test="${record.displayed_flag=='T'}">
									<abc:i18n property="hr.caption.normal_user" />
									<fmt:message key="hr.caption.normal_user" />
								</c:if>
							</td>	 
								  
							<td class="helpBod" nowrap>
								<abc:i18n property="commons.button.edit"/>
								<a href="specialSalaryPartsForm.html?id=${record.id}">
									<fmt:message key="commons.button.edit" />
								</a>
							</td>
						</tr>
				   	</c:forEach>
				</table>
			</td>
		</tr>
		
		<c:if test="${not empty displayed_flag}">
			<tr>
				<td colspan="2" align="center">
					<br>
					<abc:i18n property="commons.button.add"/>
					<input type="button" value="<fmt:message key="commons.button.add"/>" name="add" class="button"	onclick="window.location='specialSalaryPartsForm.html?displayed_flag=${displayed_flag}'"></input>
				</td>
			</tr>
		</c:if>
	</form>
</table>
		
<%@ include file="/web/common/includes/footer.jsp" %>
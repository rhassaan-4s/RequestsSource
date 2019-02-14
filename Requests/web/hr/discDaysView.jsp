<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>

<abc:security property=""/><br>

<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   <form id="discDaysView" name="discDaysView" method="POST" action="<c:url value="/hr/discDaysView.html"/>">
		<input type="hidden"  id="disc_type" name="disc_type" value="${disc_type}"/>
		<tr>
			<td>
				<table rules="all" align="center" width="70%" class="sofT">
					<tr>
						<td colspan="8" class="helpTitle">
						<c:if test="${disc_type==1}">
							<abc:i18n property="hr.header.discDaysViewWithoutSal" />
							<fmt:message key="hr.header.discDaysViewWithoutSal" />
						</c:if>
						<c:if test="${disc_type==2}">
							<abc:i18n property="hr.header.discDaysViewByDisc" />
							<fmt:message key="hr.header.discDaysViewByDisc" />
						</c:if>
						</td>
					</tr>
	
					<tr>
						<td class="helpHed" nowrap="nowrap">
							<abc:i18n property="hr.caption.code" />
							<fmt:message key="hr.caption.code" />
						</td>
						
						<td class="helpHed" nowrap="nowrap">
							<abc:i18n property="hr.caption.name" />
							<fmt:message key="hr.caption.name" />
						</td>
						
						<td class="helpHed" nowrap="nowrap">
							<abc:i18n property="hr.caption.month" />
							<fmt:message key="hr.caption.month" />
						</td>
						
						<td class="helpHed" nowrap="nowrap">
							<abc:i18n property="hr.caption.year" />
							<fmt:message key="hr.caption.year" />
						</td>
						
						<td class="helpHed" nowrap="nowrap">
							<abc:i18n property="hr.caption.no_days" />
							<fmt:message key="hr.caption.no_days" />
						</td>
						
						<td class="helpHed" nowrap="nowrap">
							<abc:i18n property="hr.caption.disc_type" />
							<fmt:message key="hr.caption.disc_type" />
						</td>
					
						<td class="helpHed">
						</td>
					</tr>
					
				    <c:forEach items="${records}" var="record">
						<tr height=20 bgcolor="#F8F8F8">
							
							<td class="helpBod" nowrap>
								${record.emp_id}
							</td>
							
							<td class="helpBod" nowrap>
								${record.empName}
							</td>
							
							<td class="helpBod" nowrap>
								${record.month.month}
							</td>
							
							<td class="helpBod" nowrap>
								${record.year.year}
							</td>
							
							<td class="helpBod" nowrap>
								${record.no_days}
							</td>
							
							<td class="helpBod" nowrap>
								<c:if test="${record.disc_type=='1'}">
									<abc:i18n property="hr.caption.withoutSal" />
									<fmt:message key="hr.caption.withoutSal" />
								</c:if>
								<c:if test="${record.disc_type=='2'}">
									<abc:i18n property="hr.caption.ByDisc" />
									<fmt:message key="hr.caption.ByDisc" />
								</c:if>
							</td>	  
								  
							<td class="helpBod" nowrap>
								<abc:i18n property="commons.button.edit" />
								<a href="discDaysForm.html?discId=${record.id}">
									<fmt:message key="commons.button.edit" />
								</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		
		
			<tr>
				<td colspan="2" align="center">
					<br>
					<abc:i18n property="commons.button.add"/>
					<input type="button" value="<fmt:message key="commons.button.add"/>" name="add" class="button"	onclick="window.location='discDaysForm.html?disc_type=${disc_type}'"></input>
				</td>
			</tr>
		
		
	</form>
</table>  
		
<%@ include file="/web/common/includes/footer.jsp" %>
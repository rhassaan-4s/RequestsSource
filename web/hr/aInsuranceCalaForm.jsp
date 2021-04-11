<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>

<abc:security property=""/><br>

<script type="text/javascript">
function searchForm (){
	var year=document.getElementById("year").value; 
	var month=document.getElementById("month").value; 
	var insurance=document.getElementById("insuranceCode").value;

	var emp_code=document.getElementById("empCodehidden").value;
	var empName=document.getElementById("namehidden").value;
	var res = ""
	for (i=0;i < empName.length; i++) {
		res += empName.charCodeAt(i) + ',';
	}
	res = res.substr(0, res.length - 1);
	
	
	var insuranceNo=document.getElementById("insuranceNo").value;
	var subscriptionDate=document.getElementById("subscriptionDate").value;
	var groupBy=document.getElementById("groupBy").value;
	var search = "true";
	
	var URL='aInsuranceCalaForm.html?year='+year+'&month='+month+'&insuranceCode='+insurance+'&emp_code='+emp_code+'&empName='+res+'&insuranceNo='+insuranceNo+'&subscriptionDate='+subscriptionDate+'&groupBy='+groupBy+'&search='+search;
	window.location.href=URL;
}

function copyData (){
	var year=document.getElementById("year").value; 
	var month=document.getElementById("month").value; 
	var insurance=document.getElementById("insuranceCode").value;

	var copy = "true";
	
	var URL='aInsuranceCalaForm.html?year='+year+'&month='+month+'&insuranceCode='+insurance+'&copy='+copy;
	window.location.href=URL;
}
</script>

<form name="testForm" id="testForm" method="post" action="<c:url value="aInsuranceCalaForm.html"/>">
	<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
		
<!--		<input type="hidden"  id="aicId" name="aicId" value="${aicId}"/>-->
<!--		<input type="hidden" id="enteredmonth" name="enteredmonth" value="${month}"/>-->
<!--		<input type="hidden" id="enteredyear" name="enteredyear" value="${year}"/>-->
<!--		<input type="hidden" id="enteredinsurance" name="enteredinsurance" value="${insurance}"/>-->
<!--		-->
		<tr>
			<TD>
				<spring:bind path="aic.*">
					<c:if test="${not empty status.errorMessages}">
						<div>
							<c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}" escapeXml="false" />
									<br/>
								</font>
							</c:forEach>
						</div>
					</c:if>
				</spring:bind>
			</TD>
		</tr>
		
		<tr>
			<td>
				<table>
					<tr>
						<td>
							<table>
								<tr>
									<td class="formReq">
										<fmt:message key="hr.caption.month"></fmt:message>
									</td>
						   			<td  class="formBod">
						      			<select name="month" id="month">
											<option value="">
												<fmt:message key="commons.caption.select" />
											</option>
											<c:forEach items="${monthList}" var="mon">
												<option value="${mon.id}"${month==mon.id?' selected' : ''}>
													${mon.month}
												</option>
											</c:forEach>
										</select>
									</td>
									
									<td class="formReq">
										<fmt:message key="hr.caption.year"></fmt:message>
									</td>
						   			<td  class="formBod">
						      			<select name="year" id="year">
											<option value="">
												<fmt:message key="commons.caption.select" />
											</option>
											<c:forEach items="${yearList}" var="yea">
												<option value="${yea.id}"${year==yea.id?' selected' : ''} >
													${yea.year}
												</option>
											</c:forEach>
										</select>
									</td>
									
									<td class="formBodControl">
										<fmt:message key="commons.caption.insuranceCode"></fmt:message>
									</td>
									<td  class="formBod">
								 		<select name="insuranceCode" id="insuranceCode">
											<option value="">
												<fmt:message key="commons.caption.select" />
											</option>
				 							<c:forEach items="${insuranceList}" var="insuCode">
				 								<option value="${insuCode.id}"${insuranceCode==insuCode.id?' selected' : ''} >
				 									${insuCode.name}
				  						 		</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								
								<tr>
									<td>
										<abc:i18n property="commons.button.copyDataFromPreMonth"/>
										<input type="button" name="ccc" onclick="copyData()" value="<fmt:message key="commons.button.copyDataFromPreMonth"/> "/>
									</td>
								</tr>
								
								<tr>
									<td class="formBodControl">
										<abc:i18n property="commons.caption.code"/>
										<fmt:message key="commons.caption.code"/>
									</td>
									<td  class="formBod"> 
										<spring:bind path="aic.emp_code">
											<abc:autocomplete 
												inputId="empCode" 
												inputName="${status.expression}" 
												table="hr_employee" 
												firstKey="commons.caption.code"
												secondKey="commons.caption.name"
												firstParam="empCode"
												secondParam="name"
												bindById="false"
												valueString="${employeeCode}"
												valueId="${employeeCode}"/>
										</spring:bind>
									</td>
								
									<td class="formBodControl">
										<abc:i18n property="commons.caption.name"/>
										<fmt:message key="commons.caption.name"/>
									</td>
									<td  class="formBod"> 
										<abc:autocomplete 
											inputId="name" 
											inputName="${status.expression}"
											table="hr_employee" 
											firstKey="commons.caption.name"
											secondKey="commons.caption.code"
											firstParam="name"
											secondParam="empCode"
											bindById="false"
											valueString="${employeeName}"
											valueId="${employeeName}"/>
									</td>
									
									<td class="formBodControl">
										<fmt:message key="hr.caption.insuranceNumber" />
									</td>
									<td class="helpBod" nowrap>
										<input type="text" id="insuranceNo" name="insuranceNo" value="${insuranceNo}" />     
									</td>
									
									<td class="formBodControl">
										<fmt:message key="hr.caption.subscriptionDate" />
									</td>
									<td class="helpBod" nowrap>
										<input type="text" id="subscriptionDate" name="subscriptionDate" value="${subscriptionDate}" />     
									</td>
								</tr>
								
								<tr>
									<td class="formBodControl">
										<fmt:message key="commons.caption.groupBy"></fmt:message>
									</td>
									<td  class="formBod">
								 		<select name="groupBy" id="groupBy">
											<option value="">
												<fmt:message key="commons.caption.select" />
											</option>
				 							<c:forEach items="${groupByList}" var="group">
				 								<option value="${group.id}"${groupBy==group.id?' selected' : ''} >
				 									${group.name}
				  						 		</option>
											</c:forEach>
										</select>
									</td>
									
									<td>
										<abc:i18n property="commons.button.search"/>
										<input type="button" name="aaa" onclick="searchForm()" value="<fmt:message key="commons.button.search"/> " class="button"/>
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>

					<tr height="10">
					</tr>

					<tr>
						<td>
							<table rules="all" align="center" width="70%" class="sofT">
								<tr>
									<td colspan="14" class="helpTitle">
										<abc:i18n property="hr.header.aInsuranceCala" />
										<fmt:message key="hr.header.aInsuranceCala" />
									</td>
								</tr>
								
								<tr>
									<c:set var="check"  value=""/>
						
									<c:if test="${aicId!=null && aicId!='' }">
										<c:set var="check"  value="disabled"/>
									</c:if>
		    
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="hr.caption.code" />
										<fmt:message key="hr.caption.code" />
									</td>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="hr.caption.employeeName" />
										<fmt:message key="hr.caption.employeeName" />
									</td>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="hr.caption.insuranceNumber" />
									 	<fmt:message key="hr.caption.insuranceNumber" />
									</td>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="hr.caption.subscriptionDate" />
									 	<fmt:message key="hr.caption.subscriptionDate" />
									</td>
										
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="hr.caption.basic_sal" />
									 	<fmt:message key="hr.caption.basic_sal" />
									</td>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="hr.caption.tot_value" />
									 	<fmt:message key="hr.caption.tot_value" />
									</td>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="hr.caption.tot_sal" />
									 	<fmt:message key="hr.caption.tot_sal" />
									</td>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="hr.caption.basic_emp" />
									 	<fmt:message key="hr.caption.basic_emp" />
									</td>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="hr.caption.var_emp" />
									 	<fmt:message key="hr.caption.var_emp" />
									</td>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="hr.caption.tot_emp" />
									 	<fmt:message key="hr.caption.tot_emp" />
									</td>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="hr.caption.basic_co" />
									 	<fmt:message key="hr.caption.basic_co" />
									</td>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="hr.caption.var_co" />
									 	<fmt:message key="hr.caption.var_co" />
									</td>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="hr.caption.tot_co" />
									 	<fmt:message key="hr.caption.tot_co" />
									</td>
									<td class="helpHed" nowrap="nowrap">
										<abc:i18n property="hr.caption.tot_insu" />
									 	<fmt:message key="hr.caption.tot_insu" />
									</td>
								</tr>
								
								<c:forEach varStatus="loop" var="record" items="${results}">
									<tr height=20 bgcolor="#F8F8F8">
										<td class="helpBod" nowrap>
											${record.emp_code}
										</td>
										<td class="helpBod" nowrap>
											${record.empName}
										</td>
										<td class="helpBod" nowrap>
											${record.insuranceNo}
										</td>
										<td class="helpBod" nowrap>
											${record.subscriptionDate}
										</td>
										<td class="helpBod" nowrap>
											<input type="text" name="basic_sal${loop.index }" id="basic_sal${loop.index }" value="${record.basic_sal}">	  
										</td>
										<td class="helpBod" nowrap>
											<input type="text" id="tot_value${loop.index}" name="tot_value${loop.index}" value="${record.tot_value}" />     
										</td>
										<td class="helpBod" nowrap>
											${record.basic_sal+record.tot_value}
										</td>
										
										<td class="helpBod" nowrap>
											<input type="text" id="basic_emp${loop.index}" name="basic_emp${loop.index}" value="${record.basic_emp}" />     
										</td>
										<td class="helpBod" nowrap>
											<input type="text" id="var_emp${loop.index}" name="var_emp${loop.index}" value="${record.var_emp}" />     
										</td>
										<td class="helpBod" nowrap>
											${record.basic_emp+record.var_emp}
										</td>
										
										<td class="helpBod" nowrap>
											<input type="text" id="basic_co${loop.index}" name="basic_co${loop.index}" value="${record.basic_co}" />     
										</td>
										<td class="helpBod" nowrap>
											<input type="text" id="var_co${loop.index}" name="var_co${loop.index}" value="${record.var_co}" />     
										</td>
										<td class="helpBod" nowrap>
											${record.basic_co+record.var_co}
										</td>
										
										<td class="helpBod" nowrap>
											${record.basic_emp+record.var_emp+record.basic_co+record.var_co}
										</td>	
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>	
				</table>
			</td>		
		</tr>
			
		<tr>
			<td>
				<table align="center">
					<tr id="btn">
						<td colspan=5 align=center>
							<abc:i18n property="commons.button.save" />	
							<input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp; 
							<abc:i18n property="commons.button.cancel" />
							<input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>

<%@ include file="/web/common/includes/footer.jsp" %>
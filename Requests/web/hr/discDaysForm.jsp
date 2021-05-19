<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>

<abc:security property=""/><br>

<!--  <STYLE type='text/css'>
textarea {overflow-x: hidden; overflow-y: scroll}
</STYLE>-->

<script language="JavaScript">
	function fillCode(text){
		// alert('inside fillCode');
		// alert('text.length'+text.value.length);
	  	var maxLength = parseInt(text.getAttribute('maxlength')); 
	 	// alert('text.maxlength'+maxLength );
		if(text.value.length<maxLength){
			//alert('inside length !=maxlength');
			var diff=maxLength-text.value.length;
			//alert('diff>>>'+diff);
			var num=0;
			for(j=0;j<diff-1;j++){
				num+='0';  
			}
		text.value=num+text.value;
		}
	}
</script>
 
<form id="discDaysForm" name="discDaysForm" method="POST"	action="<c:url value="/hr/discDaysForm.html"/>">
	<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   		
   		<input type="hidden"  id="discId" name="discId" value="${discId}"/>
		<input type="hidden"  id="disc_type" name="disc_type" value="${disc_type}"/>
		
		<tr>
			<td colspan="2">
				<spring:bind path="discDays.*">
					<c:if test="${not empty status.errorMessages}">
						<div>
							<c:forEach var="error" items="${status.errorMessages}">
								<font color="red">
									<c:out value="${error}" escapeXml="false"/>
									<br/>
								</font>
							</c:forEach>
						</div>
					</c:if>
				</spring:bind>
			</td>
		</tr>
		
		<tr>
  			<td>
				<table  align="center" width="66%" class="sofT" >
					<tr id="head_1_ep">
						<td class="helpTitle" colspan="2" nowrap>
							<abc:i18n property="hr.header.discDaysForm" />
							<fmt:message key="hr.header.discDaysForm" />
						</td>
					</tr>

					<tr>
						<c:set var="check"  value=""/>
						<c:if test="${discId!=null && discId!='' }">
							<c:set var="check"  value="disabled"/>
						</c:if>
						
						<td nowrap class="formReq">
							<abc:i18n property="commons.caption.code"/>
							<fmt:message key="commons.caption.code"/>
						</td>
						<td  class="formBod"> 
							<spring:bind path="discDays.emp_id">
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
					
						<td nowrap class="formReq">
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
		  			</tr>
		  			
		  			<tr height="10">
		  			</tr>
		  			
		  			<tr>
		   				<td nowrap class="formReq">
							<abc:i18n property="hr.caption.month"/>
							<fmt:message key="hr.caption.month"/>
						</td>
						<td class="formBodControl">
							<spring:bind path="discDays.month">
						 		<select name="${status.expression}" value="${status.value}">
									<option value="">
										<abc:i18n property="commons.caption.select"/>
										<fmt:message key="commons.caption.select"/>
									</option>
		 							<c:forEach var="mon"  items="${monthList}">
		 								<option value="${mon.id }" ${mon.id == discDays.month.id ? 'selected' :'' }>
		 									${ mon.month}
		  						 		</option>
		 							</c:forEach>
			 					</select>
							</spring:bind>
						</td>

						<td nowrap class="formReq">
							<abc:i18n property="hr.caption.year"/>
							<fmt:message key="hr.caption.year"/>
						</td>
						<td class="formBodControl">
							<spring:bind path="discDays.year">
					 			<select name="${status.expression}" value="${status.value}">
									<option value="">
										<abc:i18n property="commons.caption.select"/>
										<fmt:message key="commons.caption.select"/>
									</option>
									<c:forEach var="yea"  items="${yearList}">
					 					<option value="${yea.id }" ${yea.id == discDays.year.id ? 'selected' :'' }>
								 			${yea.year}
					  					</option>
					 				</c:forEach>
								</select>
							</spring:bind>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>		
				<table  align="center" width="66%" class="sofT" >
					<tr>
			  			<td>
							<table  align="center" width="66%" class="sofT" >
					  			<tr>
									<td colspan="3" class="helpTitle">
										<abc:i18n property="hr.header.disc" />
										<fmt:message key="hr.header.disc" />
									</td>
								</tr>
			
								<tr>
									<td class="formBod">
										<abc:i18n property="hr.caption.minute" />
										<fmt:message key="hr.caption.minute" />
									</td>
									<td class="formBod">
										<abc:i18n property="hr.caption.hour" />
										<fmt:message key="hr.caption.hour" />
									</td>	
									<td class="formBod">
										<abc:i18n property="hr.caption.day" />
										<fmt:message key="hr.caption.day" />
									</td>
								</tr>
			 				 
			 				 	<tr height=20 bgcolor="#F8F8F8">
									<td class="formBodControl">
										<spring:bind path="discDays.minute">
											<input type="text"	name="${status.expression}" value="${status.value}"/> 	
										</spring:bind>
									</td>
									<td class="formBodControl">
										<spring:bind path="discDays.hour">
											<input type="text"	name="${status.expression}" value="${status.value}"/> 	
										</spring:bind>
									</td>
									<td class="formBodControl">
										<spring:bind path="discDays.day">
											<input type="text"	name="${status.expression}" value="${status.value}"/> 	
										</spring:bind>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr id="btn">
						<td colspan="2" align="center">
							<abc:i18n property="commons.button.save"/>
							<input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp;  
							
							<abc:i18n property="commons.button.cancel"/>
							<input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
  		
<%@ include file="/web/common/includes/footer.jsp" %>
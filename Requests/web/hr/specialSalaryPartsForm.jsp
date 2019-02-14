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
     
<form id="specialSalaryPartsForm" name="specialSalaryPartsForm" method="POST" action="<c:url value="/hr/specialSalaryPartsForm.html"/>">
	<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   		
   		<input type="hidden"  id="sEffectId" name="sEffectId" value="${sEffectId}"/>
		<input type="hidden"  id="displayed_flag" name="displayed_flag" value="${displayed_flag}"/>
		
		<tr>
			<td colspan="2">
				<spring:bind path="sEffect.*">
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
	  			<table rules="all" align="center" width="70%" class="sofT">
					<tr id="head_1_ep">
						<td colspan="8" class="helpTitle">
							<abc:i18n property="hr.header.addSpecialSalaryParts" />
							<fmt:message key="hr.header.addSpecialSalaryParts" />
						</td>
					</tr>
					
					<tr>
						<c:set var="check"  value=""/>
						
						<c:if test="${sEffectId!=null && sEffectId!='' }">
							<c:set var="check"  value="disabled"/>
						</c:if>
						
						<td nowrap class="formReq" width="10%">
							<abc:i18n property="commons.caption.code"/>
							<fmt:message key="commons.caption.code"/>
						</td>
						<td class="formBodControl" width="23%">
							<spring:bind path="sEffect.effcode">
								<input size="8" maxlength="3" type="text"	name="${status.expression}" value="${status.value}" onchange="fillCode(this);" ${check}"/> 
							</spring:bind>
						</td>
						
						<td nowrap class="formReq" width="10%">
							<abc:i18n property="commons.caption.name"/>
							<fmt:message key="commons.caption.name"/>
						</td>
						<td class="formBodControl" width="23%">
							<spring:bind path="sEffect.effname">
								<input type="text"	name="${status.expression}" value="${status.value}"/> 
							</spring:bind>
						</td>
						
						<td class="formBodControl" nowrap="nowrap" width="10%">
							<abc:i18n property="commons.caption.englishName"/>
							<fmt:message key="commons.caption.englishName"/>
						</td>
						<td class="formBodControl" width="23%">
							<spring:bind path="sEffect.eng_name">
<!--						    <input type="text" dir="ltr" name="${status.expression}" value="${status.value}"/>-->
								<input type="text"	name="${status.expression}" value="${status.value}"/> 
							</spring:bind>
						</td>
		  			</tr>
		  			
		  			<tr>
		  				<td class="formBodControl" nowrap="nowrap" width="7.5%">
							<abc:i18n property="commons.caption.shortName"/>
							<fmt:message key="commons.caption.shortName"/>
						</td>
						<td class="formBodControl" width="17.5%">
							<spring:bind path="sEffect.scrbrif">
<!--							<input type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>-->
								<input type="text"	name="${status.expression}" value="${status.value}"/> 
							</spring:bind>
						</td>

			    		<td class="formBodControl" nowrap="nowrap" width="7.5%">
			            	<abc:i18n property="hr.caption.securedFromApplication"/>
							<fmt:message key="hr.caption.securedFromApplication"/>
			      		</td>
			     		<td class="formBodControl" width="17.5%">
			      			<spring:bind path="sEffect.secured">
								<input  type="checkbox"	name="secured" ${sEffect.secured == true ? ' checked':""}> 			
							</spring:bind>
			      		</td>
			      		
			      		
			      		<td nowrap class="formReq" width="7.5%">
							<abc:i18n property="hr.caption.effectNature"/>
							<fmt:message key="hr.caption.effectNature"/>
						</td>
						<td class="formBodControl" width="17.5%">
							<spring:bind path="sEffect.effnature">
						 		<select name="${status.expression}" value="${status.value}">
									<option value="">
										<abc:i18n property="commons.caption.select"/>
										<fmt:message key="commons.caption.select"/>
									</option>
		 							<c:forEach var="effectNature"  items="${effectNaturesList}">
		 								<option value="${effectNature.id }" ${effectNature.id == sEffect.effnature.id ? 'selected' :'' }>
		 									${ effectNature.name}
		  						 		</option>
		 							</c:forEach>
			 					</select>
							</spring:bind>
						</td>
			      		
			      		<td class="formBodControl" nowrap="nowrap" width="7.5%">
							<abc:i18n property="hr.caption.effectRule"/>
							<fmt:message key="hr.caption.effectRule"/>
						</td>
						<td class="formBodControl" width="17.5%">
							<spring:bind path="sEffect.effrule1">
						 		<select name="${status.expression}" value="${status.value}">
									<option value="">
										<abc:i18n property="commons.caption.select"/>
										<fmt:message key="commons.caption.select"/>
									</option>
		 							<c:forEach var="effectRule"  items="${effectRulesList}">
		 								<option value="${effectRule.id }" ${effectRule.id == sEffect.effrule1.id ? 'selected' :'' }>
		 									${ effectRule.name}
		  						 		</option>
		 							</c:forEach>
			 					</select>
							</spring:bind>
						</td>
			  		</tr>
			  		
			  		<tr>
			   			<td colspan="8">
			    			<table>
			     				<tr>
			      					<td class="helpTitle" colspan="2" width="25%">
			               				<abc:i18n property="hr.caption.affectsIn"/>
										<fmt:message key="hr.caption.affectsIn"/> :
			        				</td>
	
			      	  				<td class="formReq" width="7.5%">
			                     		<abc:i18n property="hr.caption.effectTax"/>
										<fmt:message key="hr.caption.effectTax"/>
			   						</td>
			   						<td class="formBodControl" width="17.5%">
			      						<spring:bind path="sEffect.efftax">
						 					<select name="${status.expression}" value="${status.value}">
												<option value="">
													<fmt:message key="commons.caption.select" />
												</option>						
												<option value="F" ${sEffect.efftax == 'F'?' selected' : ''}>
													<fmt:message key="commons.caption.no" />
												</option>
												<option value="T" ${sEffect.efftax == 'T'?' selected' : ''}>
													<fmt:message key="commons.caption.yes" />
												</option>
						 					</select>
										</spring:bind>
									</td>
			      
			     	   				<td class="formReq" width="7.5%">
			                    		<abc:i18n property="hr.caption.effectInsurance"/>
										<fmt:message key="hr.caption.effectInsurance"/>
			   						</td>
			    					<td class="formBodControl" width="17.5%">
			      						<spring:bind path="sEffect.effinsur">
						 					<select name="${status.expression}" value="${status.value}">
												<option value="">
													<fmt:message key="commons.caption.select" />
												</option>						
												<option value="F" ${sEffect.effinsur == 'F'?' selected' : ''}>
													<fmt:message key="commons.caption.no" />
												</option>
												<option value="T" ${sEffect.effinsur == 'T'?' selected' : ''}>
													<fmt:message key="commons.caption.yes" />
												</option>
											</select>
										</spring:bind>
									</td>
									
			       	   				<td class="formBodControl" nowrap="nowrap" width="7.5%">
			                     		<abc:i18n property="hr.caption.effectHallmark"/>
										<fmt:message key="hr.caption.effectHallmark"/>
			   						</td>
								    <td class="formBodControl" width="17.5%">
								    	<spring:bind path="sEffect.effdam">
											<select name="${status.expression}" value="${status.value}">
												<option value="">
													<fmt:message key="commons.caption.select" />
												</option>						
												<option value="F" ${sEffect.effdam == 'F'?' selected' : ''}>
													<fmt:message key="commons.caption.no" />
												</option>
												<option value="T" ${sEffect.effdam == 'T'?' selected' : ''}>
													<fmt:message key="commons.caption.yes" />
												</option>
											</select>
										</spring:bind>
									</td>
			     				</tr>
			     				
			     				<tr>
			        				<td class="formBodControl" nowrap="nowrap" width="7.5%">
			             				<abc:i18n property="hr.caption.effectDiscountDays"/>
										<fmt:message key="hr.caption.effectDiscountDays"/>
			   						</td>
			    					<td class="formBodControl" width="17.5%">
			      						<spring:bind path="sEffect.effdisc_days">
						 					<select name="${status.expression}" value="${status.value}">
												<option value="">
													<abc:i18n property="commons.caption.select"/>
													<fmt:message key="commons.caption.select"/>
												</option>
					 							<c:forEach var="effectDisc"  items="${effectDiscDaysList}">
					 								<option value="${effectDisc.id }" ${effectDisc.id == sEffect.effdisc_days.id ? 'selected' :'' }>
					 									${ effectDisc.name}
					  						 		</option>
					 							</c:forEach>
						 					</select>
										</spring:bind>
									</td>
									
				 					<td class="formBodControl" nowrap="nowrap" width="7.5%">
			               				<abc:i18n property="hr.caption.anualmarg"/>
										<fmt:message key="hr.caption.anualmarg"/>
			   						</td>
			  						<td class="formBodControl" width="17.5%">
			  							<spring:bind path="sEffect.anualmarg">
			     							<input type="text" name="${status.expression}" value="${status.value}"/>
			     						</spring:bind>
			  						</td>
				
				 					<td class="formBodControl" nowrap="nowrap" width="7.5%">
							  	    	<abc:i18n property="hr.caption.effectExemptionLimit"/>
							        	<fmt:message key="hr.caption.effectExemptionLimit"/>
			   						</td>
								    <td class="formBodControl" width="17.5%">
								    	<spring:bind path="sEffect.anualmarg_is_ratio">
											<select name="${status.expression}" value="${status.value}">
												<option value="">
													<abc:i18n property="commons.caption.select"/>
													<fmt:message key="commons.caption.select"/>
												</option>
												<c:forEach var="effExemptionLimit"  items="${effectExemptionLimitList}">
											 		<option value="${effExemptionLimit.id }" ${effExemptionLimit.id == sEffect.anualmarg_is_ratio.id ? 'selected' :'' }>
											 			${effExemptionLimit.name}
											  		</option>
											 	</c:forEach>
											 </select>
										</spring:bind>
									</td>
									
									<td class="formBodControl" nowrap="nowrap" width="7.5%">
			               				<abc:i18n property="hr.caption.group"/>
										<fmt:message key="hr.caption.group"/>
			   						</td>
			  						<td class="formBodControl" width="17.5%">
			  							<spring:bind path="sEffect.group_code">
			     							<input type="text" name="${status.expression}" value="${status.value}"/>
			     						</spring:bind>
			  						</td>
			  					</tr>
			  					
			  	 				<tr>
			  	  					<td class="formBodControl" nowrap="nowrap" width="7.5%">
			               				<abc:i18n property="hr.caption.viewOrder"/>
										<fmt:message key="hr.caption.viewOrder"/>
			   						</td>
			   						<td class="formBodControl" width="17.5%">
								   		<spring:bind path="sEffect.scrpos">
								     		<input type="text" name="${status.expression}" value="${status.value}"/>
								    	</spring:bind>
								    </td>
		   						</tr>
		   						<tr>
		   							<td class="helpTitle" colspan="2" width="25%">
			               				<abc:i18n property="hr.caption.rounding"/>
										<fmt:message key="hr.caption.rounding"/> :
			        				</td>
							
								    <td class="formBodControl" nowrap="nowrap" width="7.5%">
								  		<abc:i18n property="hr.caption.roundType"/>
										<fmt:message key="hr.caption.roundType"/>
								   </td>
								   <td class="formBodControl" width="17.5%">
								   		<spring:bind path="sEffect.round_type">
											<select name="${status.expression}" value="${status.value}">
												<option value="">
													<abc:i18n property="commons.caption.select"/>
													<fmt:message key="commons.caption.select"/>
												</option>
											 	<c:forEach var="roundType"  items="${effectRoundTypesList}">
											 		<option value="${roundType.id }" ${roundType.id == sEffect.round_type.id ? 'selected' :'' }>
											 			${roundType.name}
											  		</option>
											 	</c:forEach>
											</select>
										</spring:bind>
									</td>
								       
								    <td class="formBodControl" nowrap="nowrap" width="7.5%">
								    	<abc:i18n property="hr.caption.roundValue"/>
										<fmt:message key="hr.caption.roundValue"/>
								   	</td>
								 	<td class="formBodControl" width="17.5%">
								    	<spring:bind path="sEffect.round_value">
<!--										<input type="text" dir="ltr" name="${status.expression}" value="${status.value}"/>-->
											<input type="text"	name="${status.expression}" value="${status.value}"/>
										</spring:bind>
								  	</td>
			  	 				</tr>
			    			</table>
			  			</td>
			 		</tr>
			 	</table>
			</td>
		</tr>
		
		<tr>
			<td>
				<table>
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
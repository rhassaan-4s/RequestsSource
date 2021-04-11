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

<form id="effectForm" name="effectForm" method="POST"	action="<c:url value="/hr/salaryPartsForm.html"/>">
	<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   
		<input type="hidden"  id="effectId" name="effectId" value="${effectId}"/>
		<input type="hidden"  id="displayed_flag" name="displayed_flag" value="${displayed_flag}"/>
		
		<tr>
			<td colspan="2">
				<spring:bind path="effect.*">
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
							<abc:i18n property="hr.header.addSalaryParts"/>
							<fmt:message key="hr.header.addSalaryParts"/>
						</td>
					</tr>
				
			    	<tr>
						<c:set var="check"  value=""/>
						
						<c:if test="${effectId!=null && effectId!='' }">
							<c:set var="check"  value="disabled"/>
						</c:if>
		    
			        	<td nowrap class="formReq" width="10%">
							<abc:i18n property="commons.caption.code"/>
							<fmt:message key="commons.caption.code"/>
						</td>
						<td class="formBodControl" width="23%">
							<spring:bind path="effect.effcode">
								<input size="8" maxlength="3" type="text" name="${status.expression}" value="${status.value}" onchange="fillCode(this);"  ${check}" readonly="readonly"/> 			
							</spring:bind>
						</td>
						
						<td nowrap class="formReq" width="10%">
							<abc:i18n property="commons.caption.name"/>
							<fmt:message key="commons.caption.name"/>
						</td>
						<td class="formBodControl" width="23%">
							<spring:bind path="effect.effname">
								<input  type="text"	name="${status.expression}" value="${status.value}"/> 		
							</spring:bind>
						</td>
					
						<td nowrap class="formReq" width="10%">
							<abc:i18n property="commons.caption.englishName"/>
							<fmt:message key="commons.caption.englishName"/>
						</td>
						<td  class="formBodControl"  width="23%">
							<spring:bind path="effect.eng_name">
								<input type="text" dir="ltr" name="${status.expression}" value="${status.value}"/> 				
							</spring:bind>
						</td>
		  			</tr>
		  			
			  		<tr>
			  			<td  nowrap class="formReq" width="7.5%">
			               	<abc:i18n property="hr.caption.shortName"/>
							<fmt:message key="hr.caption.shortName"/>
			   			</td>
						<td class="formBodControl"  width="17.5%">
							<spring:bind path="effect.scrbrif">
								 <input type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
							</spring:bind>
						</td>	
			  
			    		<td class="formBodControl" nowrap="nowrap" width="7.5%">
			            	<abc:i18n property="hr.caption.securedFromApplication"/>
							<fmt:message key="hr.caption.securedFromApplication"/>
			      		</td>
			     		<td class="formBodControl" width="17.5%">
			      			<spring:bind path="effect.secured">
								<input  type="checkbox"	name="secured" ${effect.secured == true ? ' checked':""}> 			
							</spring:bind>
			      		</td>
			      
			       		<td class="formBodControl" nowrap="nowrap" width="7.5%">
			            	<abc:i18n property="hr.caption.effectNature"/>
							<fmt:message key="hr.caption.effectNature"/>
			   			</td>
			   			<td class="formBodControl" width="17.5%">
			      			<spring:bind path="effect.effnature">
						 		<select name="${status.expression}" value="${status.value}">
									<option value="">
										<abc:i18n property="commons.caption.select"/>
										<fmt:message key="commons.caption.select"/>
									</option>
									<c:forEach var="effectNature"  items="${effectNaturesList}">
						 				<option value="${effectNature.id }" ${effectNature.id == effect.effnature.id ? 'selected' :'' }>
											${effectNature.name}
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
			      			<spring:bind path="effect.effrule1">
								<select name="${status.expression}" value="${status.value}">
									<option value="">
										<abc:i18n property="commons.caption.select"/>
										<fmt:message key="commons.caption.select"/>
									</option>
									<c:forEach var="effectRule"  items="${effectRulesList}">
						 				<option value="${effectRule.id }" ${effectRule.id == effect.effrule1.id ? 'selected' :'' }>
						 					${effectRule.name}
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
			      					<td class="helpTitle" colspan="2">
			               				<abc:i18n property="hr.caption.affectsIn"/>
										<fmt:message key="hr.caption.affectsIn"/> :
			        				</td>
	
			      	  				<td class="formBodControl" nowrap="nowrap" width="7.5%">
			                     		<abc:i18n property="hr.caption.effectTax"/>
										<fmt:message key="hr.caption.effectTax"/>
			   						</td>
			   						<td class="formBodControl" width="17.5%">
			      						<spring:bind path="effect.efftax">
						 					<select name="${status.expression}" value="${status.value}">
												<option value="">
													<fmt:message key="commons.caption.select" />
												</option>						
												<option value="F" ${effect.efftax == 'F'?' selected' : ''}>
													<fmt:message key="commons.caption.no" />
												</option>
												<option value="T" ${effect.efftax == 'T'?' selected' : ''}>
													<fmt:message key="commons.caption.yes" />
												</option>
						 					</select>
										</spring:bind>
									</td>
			      
			     	   				<td class="formBodControl" nowrap="nowrap" width="7.5%">
			                    		<abc:i18n property="hr.caption.effectInsurance"/>
										<fmt:message key="hr.caption.effectInsurance"/>
			   						</td>
			    					<td class="formBodControl" width="17.5%">
			      						<spring:bind path="effect.effinsur">
						 					<select name="${status.expression}" value="${status.value}">
												<option value="">
													<fmt:message key="commons.caption.select" />
												</option>						
												<option value="F" ${effect.effinsur == 'F'?' selected' : ''}>
													<fmt:message key="commons.caption.no" />
												</option>
												<option value="T" ${effect.effinsur == 'T'?' selected' : ''}>
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
								    	<spring:bind path="effect.effdam">
											<select name="${status.expression}" value="${status.value}">
												<option value="">
													<fmt:message key="commons.caption.select" />
												</option>						
												<option value="F" ${effect.effdam == 'F'?' selected' : ''}>
													<fmt:message key="commons.caption.no" />
												</option>
												<option value="T" ${effect.effdam == 'T'?' selected' : ''}>
													<fmt:message key="commons.caption.yes" />
												</option>
											</select>
										</spring:bind>
									</td>
			     				</tr>
			     				
			     				<tr>
			        				<td class="formBodControl" nowrap="nowrap" width="10%">
			             				<abc:i18n property="hr.caption.effectDiscountDays"/>
										<fmt:message key="hr.caption.effectDiscountDays"/>
			   						</td>
			    					<td class="formBodControl" width="23%">
			      						<spring:bind path="effect.effdisc_days">
						 					<select name="${status.expression}" value="${status.value}">
												<option value="">
													<abc:i18n property="commons.caption.select"/>
													<fmt:message key="commons.caption.select"/>
												</option>
					 							<c:forEach var="effectDisc"  items="${effectDiscDaysList}">
					 								<option value="${effectDisc.id }" ${effectDisc.id == effect.effdisc_days.id ? 'selected' :'' }>
					 									${ effectDisc.name}
					  						 		</option>
					 							</c:forEach>
						 					</select>
										</spring:bind>
									</td>
									
				 					<td class="formBodControl" nowrap="nowrap" width="10%">
			               				<abc:i18n property="hr.caption.group"/>
										<fmt:message key="hr.caption.group"/>
			   						</td>
			  						<td class="formBodControl"  width="23%">
			  							<spring:bind path="effect.group_code">
			     							<input type="text" name="${status.expression}" value="${status.value}"/>
			     						</spring:bind>
			  						</td>
				
				 					<td class="formBodControl" nowrap="nowrap" width="10%">
			               				<abc:i18n property="hr.caption.viewOrder"/>
										<fmt:message key="hr.caption.viewOrder"/>
			   						</td>
								    <td class="formBodControl"  width="23%">
								   		<spring:bind path="effect.scrpos">
								     		<input type="text" name="${status.expression}" value="${status.value}"/>
								    	</spring:bind>
								    </td>
			  					</tr>
			  					
			  	 				<tr>
			  	     				<td class="formBodControl" nowrap="nowrap" width="7.5%">
						             	<abc:i18n property="hr.caption.addedTowholePayment"/>
										<fmt:message key="hr.caption.addedTowholePayment"/>
			   						</td>
			    					<td class="formBodControl" width="17.5%">
								     	<spring:bind path="effect.eff_shamel">
											<select name="${status.expression}" value="${status.value}">
												<option value="">
													<fmt:message key="commons.caption.select" />
												</option>						
												<option value="F" ${effect.eff_shamel == 'F'?' selected' : ''}>
													<fmt:message key="commons.caption.no" />
												</option>
												<option value="T" ${effect.eff_shamel == 'T'?' selected' : ''}>
													<fmt:message key="commons.caption.yes" />
												</option>
											</select>
										</spring:bind>
									</td>
									
				 					<td class="formBodControl" nowrap="nowrap" width="7.5%">
			              				<abc:i18n property="hr.caption.addedToNetPayment"/>
										<fmt:message key="hr.caption.addedToNetPayment"/>
			   						</td>
			    					<td class="formBodControl" width="17.5%">
			      						<spring:bind path="effect.eff_net">
											<select name="${status.expression}" value="${status.value}">
												<option value="">
													<fmt:message key="commons.caption.select" />
												</option>						
												<option value="F" ${effect.eff_net == 'F'?' selected' : ''}>
													<fmt:message key="commons.caption.no" />
												</option>
												<option value="T" ${effect.eff_net == 'T'?' selected' : ''}>
													<fmt:message key="commons.caption.yes" />
												</option>
						 					</select>
										</spring:bind>
									</td>
				
									<td class="formBodControl" nowrap="nowrap" width="7.5%">
										<abc:i18n property="hr.caption.payForBeginner"/>
										<fmt:message key="hr.caption.payForBeginner"/>
									</td>
									<td class="formBodControl" width="17.5%">
										<spring:bind path="effect.pay_mandated">
											<select name="${status.expression}" value="${status.value}">
												<option value="">
													<fmt:message key="commons.caption.select" />
												</option>						
												<option value="F" ${effect.pay_mandated == 'F'?' selected' : ''}>
													<fmt:message key="commons.caption.no" />
												</option>
												<option value="T" ${effect.pay_mandated == 'T'?' selected' : ''}>
													<fmt:message key="commons.caption.yes" />
												</option>
											</select>
										</spring:bind>
									</td>
									
				 					<td class="formBodControl" nowrap="nowrap" width="7.5%">
			              				<abc:i18n property="hr.caption.affectIllness"/>
										<fmt:message key="hr.caption.affectIllness"/>
			   						</td>
								    <td class="formBodControl" width="17.5%">
								    	<spring:bind path="effect.affect_illness">
											<select name="${status.expression}" value="${status.value}">
												<option value="">
													<fmt:message key="commons.caption.select" />
												</option>						
												<option value="0" ${effect.affect_illness == 0?' selected' : ''}>
													<fmt:message key="commons.caption.no" />
												</option>
												<option value="1" ${effect.affect_illness == 1?' selected' : ''}>
													<fmt:message key="commons.caption.yes" />
												</option>
											</select>
										</spring:bind>
									</td>
					  	 		</tr>
			  	 
			  					<tr>
			  	 					<td class="formBodControl" nowrap="nowrap" width="10%">
							  	    	<abc:i18n property="hr.caption.effectApplyType"/>
							        	<fmt:message key="hr.caption.effectApplyType"/>
			   						</td>
								    <td class="formBodControl" width="23%">
								    	<spring:bind path="effect.eff_apply_type">
											<select name="${status.expression}" value="${status.value}">
												<option value="">
													<abc:i18n property="commons.caption.select"/>
													<fmt:message key="commons.caption.select"/>
												</option>
												<c:forEach var="effAppType"  items="${effectApplyTypesList}">
											 		<option value="${effAppType.id }" ${effAppType.id == effect.eff_apply_type.id ? 'selected' :'' }>
											 			${ effAppType.name}
											  		</option>
											 	</c:forEach>
											 </select>
										</spring:bind>
									</td>
				
									<td class="formBodControl" nowrap="nowrap" width="10%">
								    	<abc:i18n property="hr.caption.applyOrder"/>
										<fmt:message key="hr.caption.applyOrder"/>
								   	</td>
								  	<td class="formBodControl"  width="23%">
								  		<spring:bind path="effect.eff_apply_rank">
								     		<input type="text" name="${status.expression}" value="${status.value}"/>
								     	</spring:bind>
								  	</td>
								       
								    <td class="formBodControl" nowrap="nowrap" width="10%">
								    	<abc:i18n property="hr.caption.addToWage"/>
										<fmt:message key="hr.caption.addToWage"/>
								    </td>
								    <td class="formBodControl" width="23%">
								    	<spring:bind path="effect.is_temp">
											<input  type="checkbox"	name="addToWage" ${effect.is_temp == true ? ' checked':""}> 			
										</spring:bind>
								 	</td>
			  					</tr>
			  	 
			  	 				<tr>
			  	  					<td class="formBodControl" nowrap="nowrap" width="7.5%">
			               				<abc:i18n property="hr.caption.defaultValue"/>
										<fmt:message key="hr.caption.defaultValue"/>
			      					</td>
								    <td class="formBodControl"  width="17.5%">
								    	<spring:bind path="effect.default_value">
											<input   type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/>
										</spring:bind>
								    </td>
								       
									<td class="formBodControl" nowrap="nowrap" width="25%">
								    	<abc:i18n property="hr.caption.rounding"/>
										<fmt:message key="hr.caption.rounding"/> :
									</td>
									
								    <td class="formBodControl" nowrap="nowrap" width="7.5%">
								  		<abc:i18n property="hr.caption.roundType"/>
										<fmt:message key="hr.caption.roundType"/>
								   </td>
								   <td class="formBodControl" width="17.5%">
								   		<spring:bind path="effect.round_type">
											<select name="${status.expression}" value="${status.value}">
												<option value="">
													<abc:i18n property="commons.caption.select"/>
													<fmt:message key="commons.caption.select"/>
												</option>
											 	<c:forEach var="roundType"  items="${effectRoundTypesList}">
											 		<option value="${roundType.id }" ${roundType.id == effect.round_type.id ? 'selected' :'' }>
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
								 	<td class="formBodControl"  width="17.5%">
								    	<spring:bind path="effect.round_value">
											<input type="text" dir="ltr" name="${status.expression}" value="${status.value}"/>
										</spring:bind>
								  	</td>
			  	 				</tr>		
			  	 				
							  	<c:if test="${effect.id!= null && effect.id!=''}">
					            	<tr id="btn">
										<td nowrap="nowrap" colspan="4" align="center">
											<table>
												<tr>
													<td>
														<abc:i18n property="hr.caption.minValue"/>
														<input type="button" name="cancel" value=" <fmt:message key="hr.caption.minValue"/>"" class="button" onclick="javascript:createWindow('effectEquation.html?type=minValue&effectId=${effect.id}',450,900)" /> &nbsp;&nbsp;&nbsp;
														
														<abc:i18n property="hr.caption.maxValue"/>
														<input type="button" name="cancel" value="<fmt:message key="hr.caption.maxValue"/>" class="button"	onclick="javascript:createWindow('effectEquation.html?type=maxValue&effectId=${effect.id}',450,900)" /> &nbsp;&nbsp;&nbsp;
									
														<abc:i18n property="hr.button.discountAnnualTax"/>
														<input type="button" name="cancel" value="<fmt:message key="hr.button.discountAnnualTax"/>" class="button"	onclick="javascript:createWindow('effectEquation.html?type=taxDiscount&effectId=${effect.id}',450,900)" />&nbsp;&nbsp;&nbsp; 
														
														<abc:i18n property="hr.button.effectEquation"/>
														<input type="button" name="cancel" value="<fmt:message key="hr.button.effectEquation"/>" class="button"	onclick="javascript:createWindow('effectEquation.html?type=equation&effectId=${effect.id}',450,900)" />
													</td>
												</tr>
											</table>		
										</td>
									</tr>
								</c:if>
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
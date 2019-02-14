<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree"%>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>

<form id="myForm" name="myForm" method="POST"	action="<c:url value="copyFormController.html"/>">

        <input type="hidden" size=20 maxlength=40 name="copyTo" value="${copyTo}" />
		<input type="hidden" size=20 maxlength=40 name="copyId"  id="copyId" value="${copyId}" />
		<INPUT type="hidden" size="20" name="className" value="${className}"/>
		<INPUT type="hidden" size="20" name="parentLongCode" value="${parentLongCode}"/>
<table >

<tr height="10">
<td>
&nbsp;
</td>
</tr>

		
<tr>
<table align="center" width="66%" class="sofT" >

<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap>
			
				<abc:i18n property="hr.header.copyBranches" />
				<fmt:message key="hr.header.copyBranches" />
	</td>
	</tr>
<c:if test="${className=='HRInternalDivision'}">
<!--  <tr>

 <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.code"/>
								<fmt:message key="commons.caption.code"/></td>
					
										
				<td class="formBodControl">
					<spring:bind path="copyCommand.internalDivision.code">
						<input size="8" maxlength="3" type="text"	name="${status.expression}" value="${status.value}"  ${check}"/>&nbsp;${parentLongCode}			
					</spring:bind>
					</td>
  </tr> -->
  
 <tr>
		   
		    <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.name"/>
								<fmt:message key="commons.caption.name"/></td>
			
				<td class="formBodControl" width="70%">
					<spring:bind path="copyCommand.internalDivision.ardesc">
					 <input size="40" type="text"	name="${status.expression}" value="${status.value}"/> 		
					</spring:bind>
					</td>
		  </tr>
		 
		
		  <tr>
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.englishName"/>
								<fmt:message key="commons.caption.englishName"/></td>
			
				<td  class="formBodControl" width="70%">
					<spring:bind path="copyCommand.internalDivision.endesc">
					 <input size="40"  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/> 				
					</spring:bind>
					</td>
		  </tr>
		  
		  <tr>
		  <td nowrap class="formReq" width="30%">
		      <fmt:message key="hr.caption.level"/>
		  </td>
		  <td  class="formBodControl">
		  <c:choose>
		  <c:when test="${copyCommand.internalDivision.divisionLevel!=null}">
		    ${copyCommand.internalDivision.divisionLevel.name}  
		    </c:when> 
		    <c:otherwise>
		          ${level.name }
		    </c:otherwise>
		    </c:choose>
		  </td>
		  </tr>
		  
		     <tr id="btn">
							<td colspan="2" align="center">
							<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp; 
								<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
							</td>
							
			</tr>
	</c:if>
	
	<c:if test="${className=='HRSpecialtyDivision'}">

  
 <tr>
		   
		    <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.name"/>
								<fmt:message key="commons.caption.name"/></td>
			
				<td class="formBodControl" width="70%">
					<spring:bind path="copyCommand.specialtyDivision.ardesc">
					 <input size="40" type="text"	name="${status.expression}" value="${status.value}"/> 		
					</spring:bind>
					</td>
		  </tr>
		 
		
		  <tr>
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.englishName"/>
								<fmt:message key="commons.caption.englishName"/></td>
			
				<td  class="formBodControl" width="70%">
					<spring:bind path="copyCommand.SpecialtyDivision.endesc">
					 <input size="40"  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/> 				
					</spring:bind>
					</td>
		  </tr>
		  
		  <tr>
		  <td nowrap class="formReq" width="30%">
		      <fmt:message key="hr.caption.level"/>
		  </td>
		  <td  class="formBodControl">
		   <c:choose>
		  <c:when test="${copyCommand.SpecialtyDivision.divisionLevel!=null}">
		    ${copyCommand.SpecialtyDivision.divisionLevel.name}  
		    </c:when> 
		    <c:otherwise>
		          ${level.name }
		    </c:otherwise>
		    </c:choose>
		    
		  </td>
		  </tr>
		  
		     <tr id="btn">
							<td colspan="2" align="center">
							<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp; 
								<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
							</td>
							
			</tr>
	</c:if>
	
	
	<c:if test="${className=='HRGeographicalDivision'}">

 <tr>
		   
		    <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.name"/>
								<fmt:message key="commons.caption.name"/></td>
			
				<td class="formBodControl" width="70%">
					<spring:bind path="copyCommand.geographicalDivision.ardesc">
					 <input size="40" type="text"	name="${status.expression}" value="${status.value}"/> 		
					</spring:bind>
					</td>
		  </tr>
		 
		
		  <tr>
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.englishName"/>
								<fmt:message key="commons.caption.englishName"/></td>
			
				<td  class="formBodControl" width="70%">
					<spring:bind path="copyCommand.geographicalDivision.endesc">
					 <input size="40"  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/> 				
					</spring:bind>
					</td>
		  </tr>
		  
		  <tr>
		  <td nowrap class="formReq" width="30%">
		      <fmt:message key="hr.caption.level"/>
		  </td>
		  <td  class="formBodControl">
		  
		  <c:choose>
		  <c:when test="${copyCommand.geographicalDivision.divisionLevel!=null}">
		    ${copyCommand.geographicalDivision.divisionLevel.name}  
		    </c:when> 
		    <c:otherwise>
		          ${level.name }
		    </c:otherwise>
		    </c:choose>   
		  </td>
		  </tr>
		  
		     <tr id="btn">
							<td colspan="2" align="center">
							<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp; 
								<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
							</td>
							
			</tr>
	</c:if>
	
	<c:if test="${className=='HRQualificationDivision'}">

  
 <tr>
		   
		    <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.name"/>
								<fmt:message key="commons.caption.name"/></td>
			
				<td class="formBodControl" width="70%">
					<spring:bind path="copyCommand.qualificationDivision.ardesc">
					 <input size="40" type="text"	name="${status.expression}" value="${status.value}"/> 		
					</spring:bind>
					</td>
		  </tr>
		 
		
		  <tr>
		   <td nowrap class="formReq" width="30%">
								<abc:i18n property="commons.caption.englishName"/>
								<fmt:message key="commons.caption.englishName"/></td>
			
				<td  class="formBodControl" width="70%">
					<spring:bind path="copyCommand.qualificationDivision.endesc">
					 <input size="40"  type="text"	dir="ltr" name="${status.expression}" value="${status.value}"/> 				
					</spring:bind>
					</td>
		  </tr>
		  
		  <tr>
		  <td nowrap class="formReq" width="30%">
		      <fmt:message key="hr.caption.level"/>
		  </td>
		  <td  class="formBodControl">
		  <c:choose>
		  <c:when test="${copyCommand.qualificationDivision.divisionLevel!=null}">
		    ${copyCommand.qualificationDivision.divisionLevel.name}  
		    </c:when> 
		    <c:otherwise>
		          ${level.name }
		    </c:otherwise>
		    </c:choose> 
		      
		  </td>
		  </tr>
		  
		     <tr id="btn">
							<td colspan="2" align="center">
							<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp; 
								<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
							</td>
							
			</tr>
	</c:if>
	</table>
	</tr>	  
		  </table>
		  </form>








<%@ include file="/web/common/includes/footer.jsp"%>
		
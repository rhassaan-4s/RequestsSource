 <jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree"%>


<abc:security property=""/><br>
<script type="text/javascript">



function deleteAddedRow()
{
	//alert('inside deleteRow');
	y = document.getElementById("counter").value;
	//alert(y);
	var tbl = document.getElementById("table1");
	var lastRow = tbl.rows.length;
	var id=document.getElementById("id"+(lastRow-1));
	
	if(id==null ||id=='')
	{
	  var tr =tbl.deleteRow(lastRow-1);
	}

	y--;
	document.getElementById("counter").value = y;
	
}
</script>

<form id="empDocuments" name="empDocuments"  enctype="multipart/form-data" method="POST"	action="<c:url value="employeeDocumentsForm.html"/>" >

<table width="90%" class="sofT" >
<input type="hidden" id="employeeId" name="employeeId" value="${employeeId }"/>
<input type="hidden" id="formType" name="formType" value="${formType}"/>
<input type="hidden"  id="empDocumentsId" name="empDocumentsId" value="${empDocumentsId }"/>

<tr>
		<td colspan="2">
			<spring:bind path="hrEmployeeDocuments.*">
						<c:if test="${not empty status.errorMessages}">
							<div><c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
								</font>
							</c:forEach></div>
						</c:if>
			</spring:bind>
			</td>
  </tr>

<tr height="20">
	
	      <td class="helpTitle" >
	      
	          <b> <fmt:message	key="hr.header.employeeDocumentsForm" /> </b>
	      </td>
		</tr>
<tr>
		<td colspan="2" valign="top">

		<table cellspacing="0" cellpadding="0" border="0" align="right"
			width="90%">
			
			
				
				<tr>
			<td>
	
			<table cellspacing="0" cellpadding="0" border="0" width="50%" >
	
		       <tr height="5">
					</tr> 
					
					<tr>
					<td class="formReq" colspan="2">
					<b><fmt:message key="hr.caption.documentType" /></b>&nbsp;
					</td>
					
					  
					   
					   
					  <td class="formBodControl">
					     <spring:bind path="hrEmployeeDocuments.document">
					 
					     <select name="${status.expression}" value="${status.value}">
					       <option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
				             <c:forEach var="document"  items="${documentsList}">
							 <option value="${document.id }" ${document.id == hrEmployeeDocuments.document.id ? 'selected' :'' }>
							 ${ document.name}
							  </option>
							 </c:forEach>
					
					      </select>
					     </spring:bind>
					 
					   </td>
					   
					  </tr>
					  
					 
					
					
					
						<tr>
					<td class="formReq">
					<b><fmt:message key="hr.caption.documentPhoto" /></b>&nbsp;
					</td>
					
					    <td  class="formBodControl">
					     <input type="file" size="40" name="employeeDocumentFile" id="employeeDocumentFile"/>
			
					   </td>
					   <td>
					   <c:if test="${hrEmployeeDocuments.documentPhoto!=null}">
					  <a href="viewDocumentPhoto.html?hrEmployeeDocumentId=${hrEmployeeDocuments.id}" ><fmt:message key="hr.caption.showEmployeeDocument"/></a>
					  </c:if> 
					   </td>
					   </tr>
					   <tr>
					   <td class="formReq" colspan="2">
					     <fmt:message key="hr.caption.documentOwner"/>
					   </td>
					   
					  <td class="formBodControl">
					        <spring:bind path="hrEmployeeDocuments.documentOwner">
					                  <select name="${status.expression}" value="${status.value}">
										<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/></option>
									     <c:forEach var="relative"   items="${empRelativeList}">
										 <option value="${relative.id }" ${relative.id == hrEmployeeDocuments.documentOwner.id ? 'selected' :'' }>
										 ${relative.relativeName}
										  </option>
										 </c:forEach>
										
								    </select>
					        </spring:bind>
					 
					   </td>
					   
					  </tr>
					  
					
					  
					  <tr>
					  <td class="formReq" colspan="2">
					  	    <fmt:message key="hr.caption.expireDate" /> 
					   </td> 
					   
					   <td class="formBodControl">
					   <spring:bind path="hrEmployeeDocuments.expireDate">
					     <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					    </spring:bind>
					   </td>
					  </tr>
					  
					 
					
					  
					  
					  <tr> 
					   <td colspan="3">
					   <table>
					   <tr>
					   <td nowrap="nowrap" class="formReq">
					  
					    <fmt:message	key="hr.caption.releaseDate" /> 
					   
					   </td>
					   
					   <td class="formBodControl">
					     <spring:bind path="hrEmployeeDocuments.releaseDate">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					   </td>
					   
 
					   
					   <td nowrap="nowrap" class="formReq">
					  
					    <fmt:message	key="hr.caption.releasePoint" /> 
					   
					   </td>
					  
					   <td class="formBodControl">
					     <spring:bind path="hrEmployeeDocuments.releaseOrganization">
					    <input type="text" id="${status.expression }" name="${status.expression }" value="${status.value }">
					     </spring:bind>
					   </td>
					   </tr>
					   </table>
					  </td>
					</tr>
					
					<tr height="10" >
					</tr>
					
					
					  <tr id="btn">
					  <td colspan="3" align="center">
					  <table>
					  <tr align="center">
							<td colspan=""  align="center">
							<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" onclick="detectForm();"/>&nbsp;&nbsp;&nbsp;
							</td >
							<td colspan="" align="center">
								<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
							</td>
						</tr>
						</table>
						</td>	
					</tr>
					
			</table>
			</td>
			</tr>
			</table>
			</td>
			</tr>
			</table>
					
		</form>			
<%@ include file="/web/common/includes/footer.jsp"%>
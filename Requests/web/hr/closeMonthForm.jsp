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

<form id="closeMonthForm" name="closeMonthForm" method="POST"	action="<c:url value="/hr/closeMonthForm.html"/>">
	<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   		
   		<input type="hidden"  id="closeId" name="closeId" value="${closeId }"/>
		
		<tr>
			<td colspan="2">
				<spring:bind path="closeMonth.*">
					<c:if test="${not empty status.errorMessages}">
						<div>
							<c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
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
						<td class="helpTitle" colspan="2" nowrap><abc:i18n
							property="hr.header.closeMonth" /><fmt:message
							key="hr.header.closeMonth" />
						</td>
					</tr>
					
		  			<tr>
		   				<td nowrap class="formReq" width="30%">
							<abc:i18n property="hr.caption.month"/>
							<fmt:message key="hr.caption.month"/>
						</td>
						<td class="formBodControl" width="70%">
							<spring:bind path="closeMonth.month_id">
					 			<select name="${status.expression}" id="${status.expression}" value="${status.value}" onclick="showStateRow()">
									<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/>
									</option>
									<c:forEach var="mon"  items="${monthList}">
					 					<option value="${mon.id }" ${mon.id == closeMonth.month_id.id ? 'selected' :'' }>
								 			${mon.month}
					  					</option>
					 				</c:forEach>
								</select>
							</spring:bind>
						</td>
		  			</tr>
		  			
		  			<tr>
		   				<td nowrap class="formReq" width="30%">
							<abc:i18n property="hr.caption.year"/>
							<fmt:message key="hr.caption.year"/>
						</td>
						<td class="formBodControl" width="70%">
							<spring:bind path="closeMonth.year_id">
					 			<select name="${status.expression}" id="${status.expression}" value="${status.value}" onclick="showStateRow()">
									<option value=""><abc:i18n property="commons.caption.select"/><fmt:message key="commons.caption.select"/>
									</option>
									<c:forEach var="yea"  items="${yearList}">
					 					<option value="${yea.id }" ${yea.id == closeMonth.year_id.id ? 'selected' :'' }>
								 			${yea.year}
					  					</option>
					 				</c:forEach>
								</select>
							</spring:bind>
						</td>
		  			</tr>
		  			 
		  			<tr id="btn">
						<td colspan="2" align="center">
							<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp; 
							<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>		
		
<%@ include file="/web/common/includes/footer.jsp" %>
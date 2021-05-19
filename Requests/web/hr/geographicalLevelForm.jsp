<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<abc:security property="79" />
<script type="text/javascript">
function generateCells(){

	var levels=document.getElementById('noOfLevels');
	//alert('levels>>>>>>>>>>>>>'+levels);
	var table1=document.getElementById('table1');
	var ss=table1.rows.length;
	
	var levelsNo=levels.value;
	//alert('levelsNo>>>>>>>>>>>>>'+levelsNo);
	for(i=0;i<ss;i++){
		var ee=document.getElementById('levelDatas'+i);
	//	alert('levelDatas>>>>>>>>>>>>>'+ee);
		table1.deleteRow(ee);	
			
	}
	for (i=0 ; i < levelsNo ; i++) {
			titleRow=table1.insertRow(i);
			titleRow.setAttribute('id','levelDatas'+i);
			var cell0=titleRow.insertCell(0);
			var cell1=titleRow.insertCell(1);
			var cell2=titleRow.insertCell(2);
			var cell3= titleRow.insertCell(3);
			var arr = i+1;
			cell1.innerHTML =(i+1==levelsNo)?arr+"&nbsp;<fmt:message key='gl.caption.lastLevel'/>":arr+"&nbsp;<fmt:message key='gl.caption.level'/>";
			cell1.setAttribute('class','formBod');
			cell1.setAttribute('width','20%');
			var e1=document.createElement('input');
			e1.setAttribute('type','text');
			e1.setAttribute('size','10');
			e1.setAttribute('name','levelData'+i);
				
			cell4=titleRow.insertCell(4);
			cell2.setAttribute('class','formBodControl');
			document.certificate.noOfLevels.value=document.levels.noOfLevels1.value;
			
			cell2.appendChild(e1);
	}	
}
function init(){
if(document.levels.canEdit.value=='false'){document.levels.noOfLevels1.disabled=true;
document.levels.but1.disabled=true;
}

	document.certificate.noOfLevels.value=document.levels.noOfLevels1.value;

}
window.onload=init;
</script>
<form id="levels" name="levels" method="get">
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right:10px ">


	<tr>
		<td colspan="2">
				<table rules="all" align="center" width="70%" class="sofT" >
			<tr id="head_1_ep">
			<td class="helpTitle" colspan="2" nowrap>	
		<fmt:message
			key="hr.header.geographicalDivisionCodes" />
			</td>
			</tr>
				<tr>
					<td>
					
					
					<table   border=0 cellspacing=0 cellpadding=0 id="ep"	 width="100%">
						
							<tr align="right">
							
								<td nowrap class="formReq" width="40%"><abc:i18n property="gl.caption.noOfLevels" /><fmt:message key="gl.caption.noOfLevels"/></td>
								<td class="formBodControl">
								<input  id="noOfLevels" maxlength="2" type="text" size="2" name="noOfLevels1" value="${noOfLevels}"
										></input>
										
								</td>
								<td class="formBodControl">
								<INPUT id="but1" name="but1" type="button" value="<fmt:message key="commons.button.enter"/>" class="button" onclick="JavaScript:generateCells();"/>
								</td>
							</tr>
		
					</table>
					</form>
					</td>
				
				</tr>
				<tr>
							
					<td colspan="2">
					<form id="certificate" name="certificate" method="POST"	action="<c:url value="/hr/geographicalLevelForm.html"/>" onLoad="return init();">
						<spring:bind path="geographicalLevelCommand.*">
							<c:if test="${not empty status.errorMessages}">
								<div><c:forEach var="error" items="${status.errorMessages}">
									<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
									</font>
								</c:forEach></div>
							</c:if>
						</spring:bind>
						
						
				<INPUT type="hidden" size="20" name="noOfLevels" value=""/>
				
				<TABLE id="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			
						<c:forEach var="lvl" items="${geographicalLevelCommand.levels}"
							varStatus="loop">
							<tr id="levelDatas${loop.index}">
									<INPUT type="hidden" value="${lvl.id}"
										name="id${loop.index}" id="id${loop.index}" />
								<td align="center" class="formBod" width="20%">${lvl.levelNo}
								<c:if test="${lvl.isLastLevel==true}">	
								<fmt:message key='gl.caption.lastLevel'/> 
								</c:if>
							<c:if test="${lvl.isLastLevel!=true}">	
								<fmt:message key='gl.caption.level'/> 
							</c:if>
								</td>
								<td align="center" class="formBodControl">
							
								<INPUT type="text" size="10" value="${lvl.length}"
									name="levelData${loop.index}" id="lengthId${loop.index}" ${canEdit == false ? 'disabled':''}/>
									
									
								</td>
																										
							</tr>
						</c:forEach>
					
					
				</TABLE>
				<table align="center">
				<tr id="btn">
						<td colspan=5 align=center>
							<abc:i18n property="commons.button.save" />	
							<input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" onclick="javascript:document.certificate.noOfLevels.value=document.levels.noOfLevels1.value" ${canEdit == false ? 'disabled':''} />&nbsp;&nbsp;&nbsp; 
							<abc:i18n property="commons.button.cancel" />
							<input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"	/>
						</td>
						</tr></table>
				
							
						</form>	
	
<%@ include file="/web/common/includes/footer.jsp" %>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ include file="/web/common/includes/popupheader.jsp"%>

<abc:security property="106" />
<script language="JavaScript">


function refreshParent() {
    self.close(); 
}
function reload()
{
var optionList=document.forms[0].zeft.options[document.forms[0].zeft.selectedIndex].index;
var option=document.forms[0].zeft.options[optionList].value;
createWindow('addUserToRole.html?option='+option+"&userId="+document.forms[0].userId.value+"&my="+document.forms[0].my.value);
	
}


function init(){
if(document.forms[0].userId.value != ""){
		 document.forms[0].zeft.disabled=false;
		 }
	else{
			document.forms[0].zeft.disabled=true;
		}
	}


function getBranch(crl){
	var branchList = document.getElementById("branchList");
	var addBranch =  document.getElementById("addBranch");
	if(crl.checked==true){
		branchList.disabled=false;
		addBranch.setAttribute("class","formReq");
	}else{
		branchList.disabled=true;
		addBranch.setAttribute("class","formBod");
	}
}


		window.onload=init;


</script>

<abc:security property="106" />
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	

	<tr>
		<td><spring:bind path="userPrivilege.*">
			<c:if test="${not empty status.errorMessages}">
				<div><c:forEach var="error" items="${status.errorMessages}">
					<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
					</font>
				</c:forEach></div>
			</c:if>
		</spring:bind></td>
	</tr>

	<tr>
		<td colspan="2">
		<form id="fields" name="fields" method="POST"
			action="<c:url value="/security/addAuthority.html"/>">
			
		<br />
		<br />
		<br />
		<table rules="all" align="center" width="70%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="4" nowrap><abc:i18n
					property="security.header.editSecurity" /> <fmt:message
					key="security.header.editSecurity" /><spring:bind
					path="userPrivilege.id">
					<input type="hidden" size=20 maxlength=40 name="${status.expression}"
						value="${status.value}"/>
					</spring:bind>
					<spring:bind path="userPrivilege.user">
					<c:if test="${status.value != null && status.value !=''}">
					<input type="hidden" size=20 maxlength=40 id="${status.expression}" name="${status.expression}" value="${userPrivilege.user.id}" />
					
					</c:if>
					<c:if test="${status.value == null || status.value ==''}">
					<input type="hidden" size=20 maxlength=40 id="${status.expression}" name="${status.expression}" value="${userId}" />
					</c:if>
					</spring:bind>
					</td>
				
			</tr>
					
			<tr>
				<td  nowrap class="formBod" >
					<abc:i18n property="security.caption.canAddBasicData"/>
					<fmt:message
					key="security.caption.canAddBasicData" /></td>
					<td class="formBodControl" >
						<spring:bind path="userPrivilege.addBasic">
						<c:if test="${status.value==true}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}" 
							 checked="checked"  onchange="changeCase();"/>
						</c:if>
						<c:if test="${status.value==false || status.value==null}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}"  onchange="changeCase();" />
						</c:if>
						
						</spring:bind>
					</td>
					
					<td  nowrap class="formBod" >
					<abc:i18n property="security.caption.canAddTrans"/>
					<fmt:message
					key="security.caption.canAddTrans" /></td>
					<td class="formBodControl" >
						<spring:bind path="userPrivilege.addTrans">
						<c:if test="${status.value==true}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}" 
							 checked="checked"  onchange="changeCase();"/>
						</c:if>
						<c:if test="${status.value==false || status.value==null}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}"  onchange="changeCase();" />
						</c:if>
						
						</spring:bind>
					</td>
			</tr>
			<tr>
				<td  nowrap class="formBod" >
					<abc:i18n property="security.caption.canEditBasicData"/>
					<fmt:message
					key="security.caption.canEditBasicData" /></td>
					<td class="formBodControl" >
						<spring:bind path="userPrivilege.updateBasic">
						<c:if test="${status.value==true}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}" 
							 checked="checked"  onchange="changeCase();"/>
						</c:if>
						<c:if test="${status.value==false || status.value==null}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}"  onchange="changeCase();" />
						</c:if>
						
						</spring:bind>
					</td>
					
					<td  nowrap class="formBod" >
					<abc:i18n property="security.caption.canEditTrans"/>
					<fmt:message
					key="security.caption.canEditTrans" /></td>
					<td class="formBodControl" >
						<spring:bind path="userPrivilege.updateTrans">
						<c:if test="${status.value==true}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}" 
							 checked="checked"  onchange="changeCase();"/>
						</c:if>
						<c:if test="${status.value==false || status.value==null}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}"  onchange="changeCase();" />
						</c:if>
						
						</spring:bind>
					</td>
			</tr>
			<tr>
				<td  nowrap class="formBod" >
					<abc:i18n property="security.caption.canDelBasicData"/>
					<fmt:message
					key="security.caption.canDelBasicData" /></td>
					<td class="formBodControl" >
						<spring:bind path="userPrivilege.delBasic">
						<c:if test="${status.value==true}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}" 
							 checked="checked"  onchange="changeCase();"/>
						</c:if>
						<c:if test="${status.value==false || status.value==null}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}"  onchange="changeCase();" />
						</c:if>
						
						</spring:bind>
					</td>
					
					<td  nowrap class="formBod" >
					<abc:i18n property="security.caption.canDelTrans"/>
					<fmt:message
					key="security.caption.canDelTrans" /></td>
					<td class="formBodControl" >
						<spring:bind path="userPrivilege.delTrans">
						<c:if test="${status.value==true}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}" 
							 checked="checked"  onchange="changeCase();"/>
						</c:if>
						<c:if test="${status.value==false || status.value==null}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}"  onchange="changeCase();" />
						</c:if>
						
						</spring:bind>
					</td>
			</tr>
			
			<tr>
				<td  nowrap class="formBod" ><abc:i18n property="security.caption.viewMenuSpecs"/>
					<fmt:message
					key="security.caption.viewMenuSpecs" /></td>
					<td class="formBodControl" >
						<spring:bind path="userPrivilege.viewMenuSpecs">
						<c:if test="${status.value==true}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}" 
							 checked="checked"  onchange="changeCase();"/>
						</c:if>
						<c:if test="${status.value==false || status.value==null}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}"  onchange="changeCase();" />
						</c:if>
						
						</spring:bind>
					</td>
					
					<td  nowrap class="formBod" >
					<abc:i18n property="security.caption.editTransNumber"/>
					<fmt:message
					key="security.caption.editTransNumber" /></td>
					<td class="formBodControl" >
						<spring:bind path="userPrivilege.editTransNumber">
						<c:if test="${status.value==true}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}" 
							 checked="checked"  onchange="changeCase();"/>
						</c:if>
						<c:if test="${status.value==false || status.value==null}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}"  onchange="changeCase();" />
						</c:if>
						
						</spring:bind>
					</td>
			</tr>

			<tr>
				<td  nowrap class="formBod" ><abc:i18n property="security.caption.canEditBranchInRunTime"/>
					<fmt:message
					key="security.caption.canEditBranchInRunTime" /></td>
					<td class="formBodControl" >
						<spring:bind path="userPrivilege.editBranch">
						<c:if test="${status.value==true}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}" 
							 checked="checked"  onchange="getBranch(this);"/>
						</c:if>
						<c:if test="${status.value==false || status.value==null}">
							<input type="checkbox" id="${status.expression}" name="${status.expression}"  onchange="getBranch(this);" />
						</c:if>
						
						</spring:bind>
					</td>
					<c:if test="${userPrivilege.editBranch==true}">
					<td id="addBranch" nowrap class="formReq" >
					<fmt:message
					key="stores.menu.refrigerators" /></td>
					</c:if>
					<c:if test="${userPrivilege.editBranch==false}">
					<td id="addBranch" nowrap class="formBod" >
					<fmt:message
					key="stores.menu.refrigerators" /></td>
					</c:if>					
					<td  nowrap class="formBod">&nbsp;
 				
 					<c:if test="${fn:length(userBranches)>0}">
 					<c:if test="${userPrivilege.editBranch==true}">
					<select id="branchList" name="branchList" multiple="multiple">
					</c:if>
					<c:if test="${userPrivilege.editBranch==false}">
					<select id="branchList" name="branchList" multiple="multiple" disabled="disabled">
					</c:if>
					<c:forEach items="${branchList}" var="list">
					
					<c:set var="isSelected" value="" />
					<c:forEach items="${userBranches}" var="Branches">	
					<c:if test="${list.code == Branches.branch.code}">
								<c:set var="isSelected" value="selected" />
							</c:if>
					</c:forEach>
					<option value="${list.code}"${isSelected}>${list.descr}</option>
					</c:forEach>
					</SELECT>
 					</c:if>
 					
 					<c:if test="${fn:length(userBranches)==0}">
					<c:if test="${userPrivilege.editBranch==true}">
					<select id="branchList" name="branchList" multiple="multiple">
					</c:if>
					<c:if test="${userPrivilege.editBranch==false}">
					<select id="branchList" name="branchList" multiple="multiple" disabled="disabled">
					</c:if>
					<c:forEach items="${branchList}" var="list">
					<option value="${list.code}">${list.descr}</option>
					</c:forEach>
					</SELECT>
 					</c:if>
 					
 					</td>
					
			</tr>


			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			
			<tr id="btn">
				<td colspan="4" align="center">
				<abc:i18n property="commons.button.save" /> <input type="submit"
					name="save" value="<fmt:message key="commons.button.save"/>"
					class="button" />&nbsp;&nbsp;&nbsp; <abc:i18n
					property="commons.button.cancel" /> <input type="reset"
					name="cancel" value="<fmt:message key="commons.button.cancel"/>"
					class="button" /> <abc:i18n property="commons.button.close" /> <input
					type="button" name="Close"
					value="<fmt:message key="commons.button.close"/>" class="button"
					onclick="opener.location.reload();self.close()" /></td>

			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>
<%@ include file="/web/common/includes/popupfooter.jsp"%>


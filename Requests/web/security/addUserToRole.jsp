<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ include file="/web/common/includes/popupheader.jsp"%>


<script language="JavaScript">
<!--
function disableOther(selectedRadio){
	if (selectedRadio == 'isManager'){
		var isDepartmentManagerTrue = document.getElementById("isDepartmentManagerTrue");
		var isDepartmentManagerFalse = document.getElementById("isDepartmentManagerFalse");
		/*isDepartmentManagerTrue.checked = false;*/
		isDepartmentManagerFalse.checked = true;
	}
	else if (selectedRadio == 'isDepartmentManager'){
		var isManagerTrue = document.getElementById("isManagerTrue");
		var isManagerFalse = document.getElementById("isManagerFalse");
		/*isManagerTrue.checked = false;*/
		isManagerFalse.checked = true;
	}
}

function canSeePrivateGLAccountsChanged() {
	//alert(document.forms[0].canSeePrivateGLAccounts.name);
	var canSeePrivate = document.getElementById("canSeePrivateGLAccounts");
	//alert("checked" +canSeePrivate.checked);
	//alert("value" + canSeePrivate.value);
	if (canSeePrivate.checked ==true){
		canSeePrivate.value=true;
	}
	else {
		canSeePrivate.value=false;
	}
	//alert("value" + canSeePrivate.value);
}

function changeCase(){
	var canSeeStore = document.getElementById("canSeeAllStore");
	
	if (canSeeStore.checked ==true){
		
		canSeeStore.value=true;
	}else {
		
		canSeeStore.value=false;
	}
}

function refreshParent() {
    self.close(); 
}
function reload()
{
var optionList=document.forms[0].zeft.options[document.forms[0].zeft.selectedIndex].index;
var option=document.forms[0].zeft.options[optionList].value;
createWindow('addUserToRole.html?option='+option+"&userId="+document.forms[0].userId.value+"&my="+document.forms[0].my.value);
	
}
function validate(chk)
{
	if(chk.checked == 1)
	{
		var optionList=document.forms[0].zeft.options[document.forms[0].zeft.selectedIndex].index;
		var myCheck = document.forms[0].zeft.options[optionList].value;
		document.forms[0].my.value = myCheck;
	}
}

function init(){
if(document.forms[0].userId.value != ""){
		 document.forms[0].zeft.disabled=false;
		 }
	else{
			document.forms[0].zeft.disabled=true;
		}
}

function disableAttendanceCode()
{
	var attendanceCode = document.getElementById("attendanceCode");
	attendanceCode.value = null;
	attendanceCode.disabled = true;
}

function enableAttendanceCode()
{
	var attendanceCode = document.getElementById("attendanceCode");	
	attendanceCode.disabled = false;
	//attendanceCode.value = aCode;
}


window.onload=init;

//-->
</script>

<abc:security property="289" />
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<!--tr>
		<td class="tableHeader" height="1">
	</td>
	</tr>
	<tr>
		<td class="tableHeader">
		<abc:i18n property="security.header.editUserDetails"/>
		<fmt:message
			key="security.header.editUserDetails" /></td>
		<td align="left"></td>
	</tr>
	<tr>
		<td colspan="2" bgcolor="#5A718B" height="2"></td>
	</tr>
	<tr>
		<td colspan="2" height="1"></td>
	</tr>

	<tr>
		<td colspan="2" height="20"></td>
	</tr-->

	<tr>
		<td><spring:bind path="user.*">
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
			action="<c:url value="/security/addUserToRole.html"/>"><input
			type="hidden" size=20 maxlength=40 name="option" value="${option}" />
		<br />
		<br />
		<br />
		<table rules="all" align="center" width="70%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="4" nowrap><abc:i18n
					property="security.header.editUserDetails" /> <fmt:message
					key="security.header.editUserDetails" /><spring:bind
					path="user.id">
					<input type="text" size=20 maxlength=40 name="userId"
						value="${status.value}"
						style="visibility: hidden; position: absolute;" />
				</spring:bind><spring:bind path="user.id">
					<input type="hidden" size=20 maxlength=40 name="my" value="${my}" />
				</spring:bind></td>
			</tr>
 			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="commons.caption.firstName" /> <fmt:message
					key="commons.caption.firstName" /></td>
				<td class="formBodControl"><spring:bind path="user.employee.firstName">
					<input type="text" size=20 maxlength=40 name="${status.expression}" readonly="readonly"
						value="${status.value}" />
				</spring:bind></td>
<!--

				<td nowrap class="formReq"><abc:i18n
					property="commons.caption.lastName" /> <fmt:message
					key="commons.caption.lastName" /></td>
				<td class="formBodControl"><spring:bind path="user.employee.lastName">
					<input type="text" size=20 maxlength=40 name="${status.expression}"
						value="${status.value}" />
				</spring:bind></td>
			</tr>
 -->
			<tr>
 			
				<td nowrap class="formReq"><abc:i18n
					property="commons.caption.userCode" /> <fmt:message
					key="commons.caption.userCode" /></td>
				<td class="formBodControl"><spring:bind path="user.username">
					<input type="text" size="20" name="${status.expression}" readonly="readonly"
						value="${status.value}"></input>
				</spring:bind></td>

				<td nowrap class="formReq"><abc:i18n
					property="commons.caption.password" /> <fmt:message
					key="commons.caption.password" /></td>
				<td class="formBodControl"><spring:bind path="user.password">
					<input type="password" size="20" name="${status.expression}"
						value="${status.value}"></input>
				</spring:bind></td>
			</tr>

			<!--tr>
				<td nowrap class="requiredInput">
					<abc:i18n property="commons.caption.isEmployee"/>
					<fmt:message key="commons.caption.isEmployee" />
				</td>
				<td>
					<spring:bind path="user.employee.isEmployee">
							<table>
								<tr>
									<td>
										<input type="radio" name="${status.expression}" id="isEmployeeFalse" value=false ${user.employee.isEmployee == false ? ' checked':''} onClick="disableAttendanceCode();"><fmt:message key="commons.caption.no"/>
									</td>
									<td>
										<input type="radio" name="${status.expression}" id="isEmployeeTrue" value=true ${user.employee.isEmployee == true ? ' checked':''} onClick="enableAttendanceCode();"><fmt:message key="commons.caption.yes"/>
									</td>
								</tr>
							</table>
					</spring:bind>
				</td>
			</tr>
			
			<tr>
				<td nowrap class="requiredInput">
				<abc:i18n property="commons.caption.code"/>
				<fmt:message key="commons.caption.code" /></td>
				<td>
					<spring:bind path="user.employee.employeeCode">
						<input type="text" size=20 maxlength=40 name="${status.expression}" value="${status.value}" />
					</spring:bind>
				</td>
			
				<td nowrap class="requiredInput">
					<abc:i18n property="commons.caption.attendanceCode"/>
					<fmt:message key="commons.caption.attendanceCode" />
				</td>
				<td>
					<c:set var="disable" value="" />
					<c:if test="${user.employee.isEmployee==false}">
						<c:set var="disable" value="disabled" />
					</c:if>
					<spring:bind path="user.employee.attendanceCode">
						<input type="text" ${disable} size=20 maxlength=40 name="${status.expression}" id="attendanceCode" value="${status.value}" />
					</spring:bind>
				</td>
			</tr>
			
			
			<tr>
				<td nowrap class="requiredInput">
				<abc:i18n property="commons.caption.department"/>
				<fmt:message key="commons.caption.department" /></td>
				<td>
					<spring:bind path="user.employee.department">
						<select name="${status.expression}">
							<option value=""><fmt:message key="commons.caption.select"/></option>
							<c:forEach items="${departments}" var="result">
								<option value="${result.id}" ${result.id == user.employee.department.id ? ' selected' : ''}>${result.description}</option>
							</c:forEach>
						</select>
					</spring:bind>
				</td>
			</tr>
			
			<tr>
				<td nowrap class="requiredInput">
				<abc:i18n property="commons.caption.isManager"/>
				<fmt:message key="commons.caption.isManager" /></td>
				<td>
					<spring:bind path="user.employee.isManager">
							<table>
								<tr>
									<td>
										<input type="radio" name="${status.expression}"  id="isManagerFalse" value=false ${user.employee.isManager == false ? ' checked':''} ><fmt:message key="commons.caption.no"/>
									</td>
									<td>
										<input type="radio" name="${status.expression}" id="isManagerTrue" value=true ${user.employee.isManager == true ? ' checked':''} onClick="disableOther('isManager');"><fmt:message key="commons.caption.yes"/>
									</td>
								</tr>
							</table>
					</spring:bind>
				</td>
			
				<td nowrap class="requiredInput">
				<abc:i18n property="commons.caption.isDepartmentManager"/>
				<fmt:message key="commons.caption.isDepartmentManager" /></td>
				<td>
					<spring:bind path="user.employee.isDepartmentManager">
							<table>
								<tr>
									<td>
										<input type="radio" name="${status.expression}" id="isDepartmentManagerFalse" value=false ${user.employee.isDepartmentManager == false ? ' checked':''} ><fmt:message key="commons.caption.no"/>
									</td>
									<td>
										<input type="radio" name="${status.expression}" id="isDepartmentManagerTrue" value=true ${user.employee.isDepartmentManager == true ? ' checked':''} onClick="disableOther('isDepartmentManager');"><fmt:message key="commons.caption.yes"/>
									</td>
								</tr>
							</table>
					</spring:bind>
				</td>
			</tr>
			
			<tr>
				<td nowrap class="requiredInput">
				<abc:i18n property="commons.caption.canViewDepartmentMessages"/>
				<fmt:message key="commons.caption.canViewDepartmentMessages" /></td>
				<td>
					<spring:bind path="user.employee.canViewDepartmentMessages">
							<table>
								<tr>
									<td>
										<input type="radio" name="${status.expression}" value=false ${user.employee.canViewDepartmentMessages == false ? ' checked':''}><fmt:message key="commons.caption.no"/>
									</td>
									<td>
										<input type="radio" name="${status.expression}" value=true ${user.employee.canViewDepartmentMessages == true ? ' checked':''}><fmt:message key="commons.caption.yes"/>
									</td>
								</tr>
							</table>
					</spring:bind>
				</td>
			</tr-->
<!-- 
			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="commons.caption.jobTitle" /> <fmt:message
					key="commons.caption.jobTitle" /></td>
				<td class="formBodControl"><spring:bind path="user.employee.jobTitle">
					<input type="text" size=20 maxlength=40 name="${status.expression}"
						value="${status.value}" />
				</spring:bind></td>
				
				<td nowrap class="formReq"><abc:i18n
					property="commons.caption.email" /> <fmt:message
					key="commons.caption.email" /></td>
				<td class="formBodControl"><spring:bind path="user.employee.email">
					<input type="text" size=20 maxlength=40 name="${status.expression}"
						value="${status.value}" />
				</spring:bind></td>
			</tr>

			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="commons.caption.councilBranch" /> <fmt:message
					key="commons.caption.councilBranch" /></td>
				<td  class="formBodControl"><spring:bind path="user.employee.branch">
					<select name="${status.expression}">
						<option value=""><fmt:message
							key="commons.caption.select" /></option>
						<c:forEach items="${branches}" var="aBranch">
							<option value="${aBranch.code}" ${aBranch.code==user.employee.branch.code ? ' selected' : ''}>${aBranch.descr}</option>
						</c:forEach>
					</select>
				</spring:bind></td>
				
				<td  nowrap class="formBod" >
					<abc:i18n property="security.caption.canSeePrivateGLAccounts"/>
					<fmt:message
					key="security.caption.canSeePrivateGLAccounts" /></td>
					<td class="formBodControl">
						<spring:bind path="user.employee.canSeePrivateGLAccounts">
						<c:if test="${status.value==true}">
							<input type="checkbox" id="canSeePrivateGLAccounts" name="canSeePrivateGLAccounts"  checked="checked" 
								onchange="canSeePrivateGLAccountsChanged();"  />
						</c:if>
						<c:if test="${status.value==false || status.value==null}">
							<input type="checkbox" id="canSeePrivateGLAccounts" name="canSeePrivateGLAccounts" 
								onchange="canSeePrivateGLAccountsChanged();"  />
						</c:if>
						
						</spring:bind>
					</td>
			</tr>

			<tr>
				<td nowrap class="formBod"><abc:i18n
					property="commons.caption.address" /> <fmt:message
					key="commons.caption.address" /></td>
				<td class="formBodControl"><spring:bind path="user.employee.address">
					<input type="text" size=20 maxlength=40 name="${status.expression}"
						value="${status.value}" />
				</spring:bind></td>
				
				<td nowrap class="formBod"><abc:i18n
					property="commons.caption.tel" /> <fmt:message
					key="commons.caption.tel" /></td>
				<td class="formBodControl"><spring:bind path="user.employee.tel">
					<input type="text" size=20 maxlength=40 name="${status.expression}"
						value="${status.value}" />
				</spring:bind></td>

				<!--td nowrap class="dataLabel">
				<abc:i18n property="commons.caption.city"/>
				<fmt:message key="commons.caption.city" /></td>
				<td>
					<spring:bind path="user.employee.city">
						<select name="${status.expression}">
							<option value=""><fmt:message key="commons.caption.select"/></option>
							<c:forEach items="${cities}" var="result">
								<option value="${result.id}" ${result.id == user.employee.city.id ? ' selected' : ''}>${result.description}</option>
							</c:forEach>
						</select>
					</spring:bind>
				</td-->
			<!-- </tr>-->			
 
			<tr>
				 <td nowrap class="formReq"><abc:i18n
					property="security.caption.application" /> <fmt:message
					key="security.caption.application" /></td>

				<td class="formBodControl"><select name="zeft" onchange="return reload();">
					<c:forEach items="${applications}" var="app" varStatus="loop">
						<option value="${app.id}" ${option==app.id?' selected' : ''}>${app.name}</option>

					</c:forEach>
				</select></td>
		
				<td nowrap class="formReq" rowspan="2"><abc:i18n
					property="security.caption.roles" /> <fmt:message
					key="security.caption.roles" /></td>

						<TD colspan="3" class="formBodControl" rowspan="2"><select
							name="userRoles" size="3" multiple>
								<c:forEach items="${roles}" var="role">

									<c:if test="${role.id!=6}"> <!-- Hiding 4s Administration  -->
										<c:set var="isSelected" value="" />
										<c:forEach items="${user.roles}" var="userRole">

											<c:if test="${role.id == userRole.id}">
												<c:set var="isSelected" value="selected" />
											</c:if>
										</c:forEach>

										<option value="${role.id}" ${isSelected}>${role.rolename}</option>
									</c:if>
								</c:forEach>
						</select></TD>

					</tr>
<!-- 			
			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="security.caption.userDefaultApplication" /> <fmt:message
					key="security.caption.userDefaultApplication" /></td>
				<td class="formBodControl"><INPUT type="checkbox" name="myDefaultApplication"
					onchange="return validate(myDefaultApplication);"
					${user.defaultApplication.id== option ?'checked' : ''}/></td>
			</tr>
			
			<tr>
				<td  nowrap class="formBod" >
					<abc:i18n property="security.caption.canSeeAllStore"/>
					<fmt:message
					key="security.caption.canSeeAllStore" /></td>
					<td class="formBodControl" colspan="3">
						<spring:bind path="user.employee.canSeeAllStore">
						<c:if test="${status.value==true}">
							<input type="checkbox" id="canSeeAllStore" name="canSeeAllStore" 
							 checked="checked"  onchange="changeCase();"/>
						</c:if>
						<c:if test="${status.value==false || status.value==null}">
							<input type="checkbox" id="canSeeAllStore" name="canSeeAllStore"  onchange="changeCase();" />
						</c:if>
						
						</spring:bind>
					</td>
			</tr>
			 -->
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


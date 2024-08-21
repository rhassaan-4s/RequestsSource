<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<abc:security property="1035"/>
<script type="text/javascript">
function removeEl(ele,id,gid) {
	//$("#viewer").html('<img src="images/Loading2.gif"> loading ...');
	//document.getElementById("status").style.display = "none";
	var count=$(".num"+gid).size();

	if(count!=1){
		gid=-1;
		
		}
	$.ajax( {
		type :"GET",
		url :"/Requests/requestsApproval/ajaxRequest.html?accLevel="+id+"&groupId="+gid,
		data :"",
		success : function(msg) {
	
		if(msg=="ok")
		  $(ele).parent().parent().remove();
		else
		 alert("لا يمكن الحذف لوجود اشخاص يستخدمونه");
		}
	});

}
function removegroup(ele,gid){	
	$.ajax( {
		type :"GET",
		url :"/Requests/requestsApproval/ajaxRequest.html?accLevelall=all&groupId="+gid,
		data :"",
		success : function(msg) {		
		if(msg=="ok")
		  $(ele).parent().parent().remove();
		else
		alert("لا يمكن الحذف لوجود اشخاص يستخدمونه");
		}
	});
	

}
var tempstr="";
function editEl(gid) {
	//$("#viewer").html('<img src="images/Loading2.gif"> loading ...');
	//document.getElementById("status").style.display = "none";
	var text=$("#text"+gid).text();
	tempstr=text;
	var tex=$("#val"+gid).val();
	if(!tex)
	$("#text"+gid).html('<input type="text"  onblur="savedata('+gid+')" id="val'+gid+'" value="'+text+'">');


}

function savedata(id){


	var text=$("#val"+id).val();
	//alert((/^\s*$/).test(text));
if(!(/^\s*$/).test(text)!=""&&text!=tempstr){
	$.ajax({
		type :"POST",
		url :"/Requests/requestsApproval/ajaxRequest.html?groupId="+id,
		data :"grouptitle="+text,
		success : function(msg) {
		    if(msg=="ok")
			$("#text"+id).html(text);
			else{
			alert("الاسم موجود مسبقا");
			$("#text"+id).html(tempstr);
			}
			
		}
	  });
	}
   else
	$("#text"+id).html(tempstr);

	
}
function adddata(id){
	$("#groupAdd").val(id);	
	document.accessLevelsForm.submit();	

	
}

</script>
<link type="text/css" rel="stylesheet"	href="/Requests/web/common/timepicker/jquery.multiselect.css" />
<link type="text/css" rel="stylesheet"	href="/Requests/web/common/timepicker/jquery.multiselect.filter.css" />
<script type="text/javascript" src="/Requests/web/common/timepicker/jquery.multiselect.min.js"></script>
<script type="text/javascript" src="/Requests/web/common/timepicker/jquery.multiselect.filter.min.js"></script>


<style>
.remove_user{
 width:24px;
 height: 24px;
 float:left;
 cursor:pointer;
 background-repeat:no-repeat;
 background-position:  center center;
 background-image: url("/Requests/web/common/images/publish_r.jpg");
}
.add_user{
 width:24px;
 height: 24px;
 float:left;
 cursor:pointer;
 background-repeat:no-repeat;
 background-position:  center center;
 background-image: url("/Requests/web/common/images/icon-16-add.png");
}
.edit_group{
 width:24px;
 height: 24px;
 float:left;
 cursor:pointer;
 background-repeat:no-repeat;
 background-position:  center center;
 background-image: url("/Requests/web/common/images/edit_button.gif");
}
</style>

<form id="accessLevelsForm" name="accessLevelsForm" method="POST"
	action="<c:url value="/requestsApproval/accessLevelsForm.html"/>">
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">

	<tr>
		<td colspan="2">
<input type="hidden" id="groupAdd" name="groupAdd" value=""> 
		<spring:bind path="accessLevel.*">
			<c:if test="${not empty status.errorMessages}">
				<div><c:forEach var="error" items="${status.errorMessages}">
					<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
					</font>
				  </c:forEach>
				</div>
			</c:if>
		</spring:bind></td>
	</tr>

	<table align="center" width="66%" class="sofT">
		<tr id="head_1_ep">
			<td class="helpTitle" colspan="3" nowrap><abc:i18n
				property="requestsApproval.header.accessLevelsForm" /><fmt:message
				key="requestsApproval.header.accessLevelsForm" /></td>
		</tr>


		<tr>

			<td nowrap class="formReq" width="30%" colspan="2" ><abc:i18n
				property="requestsApproval.requestsApprovalForm.reqResTitle" /> <fmt:message
				key="requestsApproval.requestsApprovalForm.reqResTitle" /></td>

			<td class="formBodControl" width="70%"><input size="40"
				type="text" name="groupTitle" value="" /></td>
		</tr>


		<tr>
			<td nowrap class="formReq" width="30%" colspan="2" ><abc:i18n
				property="requestsApproval.caption.userName" /> <fmt:message
				key="requestsApproval.caption.userName" /></td>

			<td class="formBodControl" width="70%">
				<select name="employee"
					id="employee" multiple="multiple" >
					<c:forEach items="${model.loginUsers}" var="user">
						<c:if test="${not empty user}">
							<option value="${user.id}">${user.name}</option>
						</c:if>
					</c:forEach>
				</select>
			</td>
		</tr>

		<tr id="btn">
			<td colspan="3" align="center"><abc:i18n
				property="commons.button.save" /><input type="submit" name="save"
				value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp;
			<abc:i18n property="commons.button.cancel" /><input type="reset"
				name="cancel" value="<fmt:message key="commons.button.cancel"/>"
				class="button" " /> <br>
			</td>
		</tr>

		<tr>
			<td align="center" class="formReq" colspan="2"><abc:i18n
				property="requestsApproval.requestsApprovalForm.reqResTitle" /> <fmt:message
				key="requestsApproval.requestsApprovalForm.reqResTitle" /></td>
			<td align="center" class="formBodControl" style="text-align: center;"><abc:i18n
				property="requestsApproval.caption.userName" /> <fmt:message
				key="requestsApproval.caption.userName" /></td>
		</tr>
		<c:forEach items="${groupList}" var="group">
			<tr style="border: 2px solid #a49bfa;" >
				<td align="center" class="formReq"
					style="border-bottom: 1px solid #a49bfa;" id="text${group.id}" > ${group.title}</td>
				<td align="center" class="formReq"
					style="border-bottom: 2px solid #a49bfa;">
					
					<span  class="edit_group" onclick="editEl('${group.id}')" title="تعديل الاسم"></span>
					<span  class="add_user" onclick="adddata('${group.id}')" title="اضافة موظفين"></span>
					<span  class="remove_user" onclick="removegroup(this,'${group.id}')" title="حذف المجموعة"></span>
					</td>
				<td align="center" class="formBodControl">
				<table width="100%" cellspacing="2">

					
					<c:forEach items="${group.accessLevel}" var="accl">
						<tr style="border-bottom: 1px solid #91C8FF;" class="num${group.id}">
							<td style="border-bottom: 1px solid #91C8FF;" width="60%">
							${accl.emp_id.name}</td>
							<td style="border-bottom: 1px solid #91C8FF;" style="text-align: center;" width="40%">
							<span  class="remove_user" onclick="removeEl(this,'${accl.id}','${group.id}')"></span>
							</td>
						
						</tr>
					</c:forEach>

				</table>
				</td>
			</tr>
		</c:forEach>
	</table>



</table>
</form>

<script type="text/javascript">
$("select").multiselect({
	  header: "",
	  selectedList: 20,
	   show: ["bounce", 200],
	   hide: ["explode", 1000]}).multiselectfilter();
</script>
<%@ include file="/web/common/includes/footer.jsp"%>

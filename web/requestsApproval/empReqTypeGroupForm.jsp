<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<abc:security property="1035"/>

<style>
.group_cont{
  
width: 200px;
float: right;
text-align: right;

}
.group_choose{
  
width: 235px;
float: right;
text-align: left;

}
#group_div{
width: 235px;
}
.remove_group{
 width:24px;
 height: 24px;
 float:left;
 cursor:pointer;
 background-repeat:no-repeat;
 background-position:  center center;
 background-image: url("/Requests/web/common/images/publish_r.jpg");
}
.add_group{
 width:24px;
 height: 24px;
 float:right;
 cursor:pointer;
 background-image: url("/Requests/web/common/images/notice-download.png");
}
.g_order{
 width:35px;
 height: 24px;
 float: right;
}
</style>



<script type="text/javascript">
var groupArr=new Array();
function show(){
	var x=document.getElementById("group1");
	var index=x.selectedIndex;
    var val=x.options[index].text;
    var id=x.options[index].value;
    var cc=$('.g_order').size()+1;
 
    if(!groupArr[id]){
    groupArr[id]=1;
    
	jQuery("#group_div").append("<div class=group_cont><span class=remove_group onclick='removeEl(this,"+id+")' ></span><span class=g_order >"+cc+"</span>"+
			"<input type=text readonly  value='"+val+"' /><input type=\"hidden\"  value=\""+id+"\" name=\"submitgroup\" /></div>");

    }
}
function removeEl(ele,id){
	groupArr[id]=0;
	
$(ele).parent().remove();
	
	$('.g_order').each(function(index) {
	   $(this).text(index+1);
	});
	
}
</script>

<link type="text/css" rel="stylesheet"	href="/Requests/web/common/timepicker/jquery.multiselect.css" />
<link type="text/css" rel="stylesheet"	href="/Requests/web/common/timepicker/jquery.multiselect.filter.css" />
<script type="text/javascript" src="/Requests/web/common/timepicker/jquery.multiselect.min.js"></script>
<script type="text/javascript" src="/Requests/web/common/timepicker/jquery.multiselect.filter.min.js"></script>



<form id="empReqTypeGroupForm" name="empReqTypeGroupForm" method="POST"	action="<c:url value="/requestsApproval/empReqTypeGroupForm.html"/>">
<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   
	<tr>
		<td colspan="2">
			<spring:bind path="empReqTypeAcc.*">
				<c:if test="${not empty status.errorMessages}">
					<div><c:forEach var="error" items="${status.errorMessages}">
						<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
						</font>
					</c:forEach></div>
				</c:if>
			</spring:bind>
		</td>
	</tr>
  	<tr>
	  	<table  align="center" width="66%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap><abc:i18n
						property="requestsApproval.header.empReqTypeGroupForm" /><fmt:message
						key="requestsApproval.header.empReqTypeGroupForm" />
				</td>
			</tr>
			    
			<tr>
				<td nowrap class="formReq" width="30%">
					<abc:i18n property="requestsApproval.caption.reponsibles"/>
					<fmt:message key="requestsApproval.caption.reponsibles"/>
				</td>
				
				<td class="formBodControl" width="70%">	
				    <div id="group_div">
				    <div class="group_choose">
				    <select name="group1" id="group1" >														
						<c:forEach items="${model.groups}" var="group">
							<option value="${group.id}">${group.title}</option>
						</c:forEach>
					</select>
				    <span class="add_group" onclick="show();" title=" Add "></span>
				    
				    </div>
				    </div>					
						
				</td>
			</tr>		  

				
			<tr>
				<td nowrap class="formReq" width="30%">
					<abc:i18n property="requestsApproval.caption.requestType"/>
					<fmt:message key="requestsApproval.caption.requestType"/>
				</td>
				
				<td class="formBodControl" width="70%">						
					<select name="request" class="multi"id="request" multiple="multiple">
						<c:forEach items="${model.requests}" var="request">
							<option value="${request.id}">${request.description}</option>
						</c:forEach>
					</select>	
				</td>
			</tr>
			
			<tr>
				<td nowrap class="formReq" width="30%">
					<abc:i18n property="requestsApproval.caption.userName"/>
					<fmt:message key="requestsApproval.caption.userName"/>
				</td>
				
				<td class="formBodControl" width="70%">						
					<select name="loginUser" class="multi" id="loginUser" multiple="multiple">													
						<c:forEach items="${model.loginUsers}" var="loginUser">
							<option value="${loginUser.id}">${loginUser.name}</option>
						</c:forEach>
					</select>	
				</td>
			</tr>
									  
            <tr id="btn">
				<td colspan="2" align="center">
				<abc:i18n property="commons.button.save"/><input type="submit" name="save" value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp; 
				<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button"/>
				</td>
			</tr>
		</table>
	</tr>


</table>
</form>
	<script type="text/javascript">
$(".multi").multiselect({
	  header: "",
	  selectedList: 100,
	   show: ["bounce", 200],
	   hide: ["explode", 1000]}).multiselectfilter();
</script>
			
<%@ include file="/web/common/includes/footer.jsp" %>

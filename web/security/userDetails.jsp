<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<abc:security property="290" />
<script>
$(document).ready(function()
		{
		    $('#search').keyup(function()
		    {
			    
		        searchTable($(this).val());
		    });
		});
		 
		function searchTable(inputVal)
		{
		    var table = $('#tblData');
		    table.find('tr').each(function(index, row)
		    {		    	
		        var allCells = $(row).find('td');
		        if(allCells.length > 0)
		        {
		            var found = false;
		            allCells.each(function(index, td)
		            {
		                var regExp = new RegExp(inputVal, 'i');
		                if(regExp.test($(td).text()))
		                {
		                    found = true;
		                    return false;
		                }
		            });
		            if(found == true)$(row).show();else $(row).hide();
		        }
		    });
		}
		
		function confirmComplete() {
			//alert("confirmComplete");
			var answer=confirm("Are you sure you want to remove ALL IMEI?");
			if (answer==true)
			  {
			    //return true;
			    window.location="userDetails.html?confirmDeleteImei=true";
			  }
			else
			  {
				 window.location="userDetails.html?confirmDeleteImei=false";
			  }
		}
		
		function confirmCompleteIP() {
			//alert("confirmComplete");
			var answer=confirm("Are you sure you want to remove ALL IP Addresses?");
			if (answer==true)
			  {
			    //return true;
			    window.location="userDetails.html?confirmDeleteIP=true";
			  }
			else
			  {
				 window.location="userDetails.html?confirmDeleteIP=false";
			  }
		}
		

</script>
<center>
<br>
&nbsp; <abc:i18n property="commons.caption.username" />
		<fmt:message key="commons.caption.username" />&nbsp;
<input type="text" id="search" />

<br>
</center>
<input type="hidden" id="confirmDeleteImei" name="confirmDeleteImei" value="${confirmDeleteImei}"/>

<table rules="all" align="center" width="70%" class="sofT" id="tblData">
	<tr id="head_1_ep">
		<td class="helpTitle" colspan="7" nowrap><abc:i18n
			property="security.header.userDetails" /> <fmt:message
			key="security.header.userDetails" /></td>
	</tr>
	<tr>
		<td nowrap class="helpHed">&nbsp; <abc:i18n property="commons.caption.userCode" />
		<fmt:message key="commons.caption.userCode" />&nbsp;</td>
		<td nowrap class="helpHed">&nbsp; <abc:i18n property="commons.caption.username" />
		<fmt:message key="commons.caption.username" />&nbsp;</td>

		<td class="helpHed">&nbsp;</td>
		<td class="helpHed">&nbsp;<abc:i18n property="commons.caption.removeAllImei" />
		<a	class="actionLink" href="javascript:confirmComplete();"><fmt:message key="commons.caption.removeAllImei" /></a></TD>&nbsp;</td>

	<td class="helpHed">&nbsp;<abc:i18n property="commons.caption.removeAllIP" />
		<a	class="actionLink" href="javascript:confirmCompleteIP();"><fmt:message key="commons.caption.removeAllIP" /></a></TD>&nbsp;</td>
	
	</tr>
	<c:forEach var="result" items="${users}">
		<c:if test="${result.username!='admin'}">
		<tr>
			<td class="helpBod">${result.username}</td>
			<td class="helpBod">${result.employee.firstName}</td>
			<TD class="helpBod">&nbsp; <abc:i18n property="commons.button.edit" /> <a
				href="javascript:createWindow('addUserToRole.html?userId=${result.id}',450,900)"
				class="actionLink"><fmt:message key="commons.button.edit" /></a></TD>
			<TD class="helpBod">&nbsp; <abc:i18n property="commons.button.imei" /> <a
				href="javascript:createWindow('imeiView.html?userId=${result.id}',450,900)"
				class="actionLink"><fmt:message key="commons.button.imei" /></a></TD>
			<TD class="helpBod">&nbsp; <abc:i18n property="commons.button.ipAdd" /> <a
				href="javascript:createWindow('ipView.html?userId=${result.id}',450,900)"
				class="actionLink"><fmt:message key="commons.button.ipAdd" /></a></TD>
		</tr>
		</c:if>
	</c:forEach>
</table>
<%@ include file="/web/common/includes/footer.jsp"%>
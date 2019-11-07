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

</script>
<center>
<br>
&nbsp; <abc:i18n property="commons.caption.username" />
		<fmt:message key="commons.caption.username" />&nbsp;
<input type="text" id="search" />

<br>
</center>
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

	</tr>
	<c:forEach var="result" items="${users}">
		<c:if test="${result.username!='1'}">
		<tr>
			<td class="helpBod">${result.username}</td>
			<td class="helpBod">${result.employee.firstName}</td>
			<TD class="helpBod">&nbsp; <abc:i18n property="commons.button.edit" /> <a
				href="javascript:createWindow('addUserToRole.html?userId=${result.id}',450,900)"
				class="actionLink"><fmt:message key="commons.button.edit" /></a></TD>
			<!-- <TD class="helpBod">&nbsp; <abc:i18n property="commons.button.delete" /> <a
				href="userDetails.html?deleteId=${result.id}"
				class="actionLink"><fmt:message key="commons.button.delete" /></a></TD>
				 -->
		</tr>
		</c:if>
	</c:forEach>
</table>
<!-- 

<table>
	<tr>
		<td align="center"><br>
		<abc:i18n property="commons.button.add" /> <input type="button"
			name="add" value=" <fmt:message key="commons.button.add"/> "
			class="button"
			onclick="javascript: createWindow('addUserToRole.html', 450, 900);"></td>
	</tr>
</table>
 -->
<%@ include file="/web/common/includes/footer.jsp"%>
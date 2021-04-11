<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>

<abc:security property="56"/><br>

<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   <form id="locationsView" name="locationsView" method="POST" action="<c:url value="/hr/locationsView.html"/>">
 
			
			
			<tr>
				<td>
					<table rules="all" align="center" width="70%" class="sofT">
			<tr>
				<td colspan="8" class="helpTitle"><abc:i18n
					property="hr.header.locationsView" /><fmt:message
					key="hr.header.locationsView" /></td>
			</tr>

			<tr>

				<!--		<td><fmt:message key="gl.caption.id"/></td><TD WIDTH=1 NOWRAP><IMG SRC="<c:url value="/web/common/images/s.gif"/>"></TD>-->
			    <td class="helpHed" nowrap="nowrap"><abc:i18n
					property="commons.caption.code" /> <fmt:message
					key="commons.caption.code" /></td>
				<td class="helpHed" nowrap="nowrap"><abc:i18n
					property="commons.caption.name" /> <fmt:message
					key="commons.caption.name" /></td>
				<td class="helpHed" nowrap="nowrap"><abc:i18n
					property="commons.caption.englishName" /> <fmt:message
					key="commons.caption.englishName" /></td>
				
				<td class="helpHed">
				</td>
			</tr>
						   <c:forEach items="${records}" var="record">
							   <tr height=20 bgcolor="#F8F8F8">
                                   
								  <td class="helpBod" nowrap>
							         ${record.location }
							
								  </td>
								  
								  <td class="helpBod" nowrap>
							         ${record.name }
							
								  </td>
								  
								   <td class="helpBod" nowrap>
							         ${record.ename }
							
								  </td>
								  
								  <td class="helpBod" nowrap><abc:i18n
									property="commons.button.edit" /><a
									href="locationForm.html?locationId=${record.id}"><fmt:message
									key="commons.button.edit" /></a></td>
									        </tr>
						   </c:forEach>
					
					</table>
				
				</td>
			</tr>
			
			<tr>
		<td colspan="2" align="center">
		<br>
			<abc:i18n property="commons.button.add"/>
			<input type="button" value="<fmt:message key="commons.button.add"/>" name="add" class="button"	onclick="window.location='locationForm.html'"></input>
		</td>
	</tr>
			
			
	</table>
   
   </form>

</table>

	
			
<%@ include file="/web/common/includes/footer.jsp" %>
   
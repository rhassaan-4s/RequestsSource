<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="url" required="false" %>
<%@ attribute name="page" required="false" type="java.lang.Integer" %>
<%@ attribute name="numberOfPages" required="false" type="java.lang.Integer" %>
<%@ attribute name="next" required="false" type="java.lang.Boolean" %>
<%@ attribute name="previous" required="false" type="java.lang.Boolean" %>
<%@ attribute name="parametersString" required="false" %>

<c:if test="${model.numberOfPages !=null && model.numberOfPages!=''}">
		<c:set var="numberOfPages" value="${model.numberOfPages}" />
</c:if>
<c:if test="${model.page !=null && model.page!=''}">
		<c:set var="page" value="${model.page}" />
</c:if>
<c:if test="${model.next !=null && model.next!=''}">
		<c:set var="next" value="${model.next}" />
</c:if>
<c:if test="${model.previous !=null && model.previous!=''}">
		<c:set var="previous" value="${model.previous}" />
</c:if>
<c:if test="${model.parametersString !=null && model.parametersString!=''}">
		<c:set var="parametersString" value="${model.parametersString}" />
</c:if>
		

<c:if test="${numberOfPages > 1}">
<table cellspacing="3" cellpadding="0" border="0" height="20" align="center">
	<tr>
		<td valign="top">
			<c:if test="${previous == true}">
				<td valign="top" align="center" style="border:1px solid black;" width="20px" class="page">
					<a href="${url}?page=0&${parametersString}">&lt;&lt;</a>
				</td>
			</c:if>
			<c:if test="${previous == null ||previous == false || previous ==''}">
				<td valign="top" align="center" style="border:1px solid black;" width="20px" class="page">
					&lt;&lt;
				</td>
			</c:if>
		</td>
		
		<td valign="top">
			<c:if test="${previous == true}">
				<td valign="top" align="center" style="border:1px solid black;" width="20px" class="page">
					<a href="${url}?page=${page -1}&${parametersString}">&lt;</a>
				</td>
			</c:if>
			<c:if test="${previous == null ||previous == false || previous ==''}">
				<td valign="top" align="center" style="border:1px solid black;" width="20px" class="page">
					&lt;
				</td>
			</c:if>
		</td>
		
		
		
	
		<c:forEach begin="0" end="3" varStatus="status">
			<c:if test="${page + status.index - 3  > 0  && page + status.index -3 <= page}">
				<td valign="top" align="center" style="border:1px solid black;" width="20px" class="page">
		  			<font size="-1"><a href="${url}?page=${page + status.index -4}&${parametersString}">${page + status.index - 3}</a></font>
		  		</td>
	  		</c:if>
		</c:forEach>
	
		
		
		<td valign="top" align="center" style="border:1px solid black;" width="20px" class="page">
			<font size="-1">${page + 1}</font>
		</td>
		
		
		<c:forEach begin="0" end="3" varStatus="status">
			<c:if test="${page + status.index +2 <= numberOfPages}">
			<td valign="top" align="center" style="border:1px solid black;" width="20px" class="page">
	  			<font size="-1"><a href="${url}?page=${page + status.index + 1 }&${parametersString}">${page + status.index + 2 }</a></font>
	  		</td>
	  		</c:if>
		</c:forEach>
		
		
		
		<td valign="top">
			<c:if test="${next == true}">
				<td valign="top" align="center" style="border:1px solid black;" width="20px" class="page">
					<a href="${url}?page=${page + 1}&${parametersString}">&gt;</a>
				</td>
			</c:if>
			<c:if test="${next == null ||next == false || next ==''}">
				<td valign="top" align="center" style="border:1px solid black;" width="20px" class="page">
	  				<font size="-1">&gt;</font>&nbsp;
	  			</td>
			</c:if>
		</td>
		
		<td valign="top">
			<c:if test="${next == true}">
				<td valign="top" align="center" style="border:1px solid black;" width="20px" class="page">
					<a href="${url}?page=${numberOfPages -1}&${parametersString}">&gt;&gt;</a>
				</td>
			</c:if>
			<c:if test="${next == null ||next == false || next ==''}">
				<td valign="top" align="center" style="border:1px solid black;" width="20px" class="page">
	  				<font size="-1">&gt;&gt;</font>&nbsp;
	  			</td>
			</c:if>
		</td>
	</tr>
</table>
</c:if>
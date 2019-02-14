<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ attribute name="inputId" required="true" %>
<%@ attribute name="inputName" required="true" %>
<%@ attribute name="inputCSSClass" required="false" %>
<%@ attribute name="valueString" required="false" %>
<%@ attribute name="valueId" required="false" %>

<%@ attribute name="buttonCSSClass" required="false" %>
<%@ attribute name="table" required="true" %>
<%@ attribute name="firstKey" required="true" %>
<%@ attribute name="secondKey" required="false" %>

<%@ attribute name="firstParam" required="true" %>
<%@ attribute name="secondParam" required="false" %>
<%@ attribute name="paramString" required="false"%>

<%@ attribute name="actionString" required="false"%>
<%@ attribute name="bindById" required="true"%>
<%@ attribute name="showSearchButton" required="false" %>
<%@ attribute name="onblur" required="false" %>
<%@ attribute name="onlinkclick" required="false" %>
<%@ attribute name="jsIncludes" required="false" %>
<%@ attribute name="onchange" required="false" %>
<%@ attribute name="searchButtonUrl" required="false" %>

<c:set var="searchUrl" value="/common/searchForm.html" />
<c:if test="${searchButtonUrl != null}">
	<c:set var="searchUrl" value="${searchButtonUrl}" />
</c:if>

	<table>
		<tr>
			<td valign="top">
				<input type="text" 
					id="${inputId}" 
					name="${inputId}"
					size="20" 
					onkeypress="liveSearchStart('${inputId}','${inputId}hidden');" 
					onblur="liveSearchHide('${table}','${firstParam}','${paramString}');${onblur}" 
					autocomplete="off"
					livesearch = "true"
					class="${inputCSSClass}"
					table="${table}"
					firstParam="${firstParam}"
					secondParam="${secondParam}"
					paramString="${paramString}"
					actionString="${actionString}"
					value="${valueString}"
					bindById="${bindById}"
					showSearchButton="${showSearchButton}"
					firstKey="${firstKey}"
					secondKey="${secondKey}"
					myonblur="${onblur}"
					onlinkclick="${onlinkclick}"
					jsIncludes="${jsIncludes}"
					addNewRecord="${addNewRecord}"
					addNewRecordMethod="${addNewRecordMethod}"
					onchange="${onchange}"
					readonly="readonly"
					/>
					
					
					<div id="${inputId}div"  style="display: none; " class="LSResult">
						<ul id="${inputId}ul" class="LSShadow"><li> </li></ul>
					</div>
					
					
					<input type="hidden" value="${valueId}" name="${inputName}" id="${inputId}hidden"/>
					
					
					<input type="hidden" value="<c:url value="${searchUrl}?"/>"  id="${inputId}hiddenUrl"/>
					
				</td>
				<td valign="top">
					 <input id="${inputId}sb"
						${showSearchButton == 'false' ? 'type="hidden"' : 'type="button"'} class="${buttonCSSClass}"
						value="<fmt:message key='commons.button.search'/>"
						onclick="javascript:createWindow(&quot;<c:url value="${searchUrl}?table=${table}&firstKey=${firstKey}&secondKey=${secondKey}&firstParam=${firstParam}&secondParam=${secondParam}&inputId=${inputId}&paramString=${paramString}&onblur=${onblur}&onlinkclick=${onlinkclick}&jsIncludes=${jsIncludes}"/>&quot;,420,700)"/>		
				</td>
			</tr>
		</table>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
   <form id="welcomePage" name="welcomePage" method="POST" action="<c:url value="/requestsApproval/welcome.html"/>">
 
			<br><br><br><h3 align="center">
				مرحبا بكم موظفي شركة 
				<B class="normalSmallWhite"><c:forEach items="${records}" var="record">
				${record.co_name }</B>
				</c:forEach>				
   				الكرام
   			</h3>
   			<br><br>
   			
   			
   			
   			<br><br><br><br>
   			<p align="center"><b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   			مع تحيات شركة   
   			<img src="/Requests/web/common/images/logonew.gif"  align="center" height="80" /> <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   			عنوان: 33 شارع دمشق, المهندسين  <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   			تليفون: +202 37492333 , +202 37601777  <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   			فاكس: +202 33388624  <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   			www.4s-systems.com  
   			</b></p>
   </form>

</table>

	
			
<%@ include file="/web/common/includes/footer.jsp" %>
   
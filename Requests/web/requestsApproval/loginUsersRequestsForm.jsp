<%@page import="com._4s_.common.model.Settings"%>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ page import="java.util.*,java.text.SimpleDateFormat"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<abc:security property="106"/>
<html>
<head>

<title>Insert title here</title>
<script type='text/javascript' src='/Requests/dwr/interface/requestsDwr.js'></script>
<script type='text/javascript' src='/Requests/dwr/engine.js'></script>


</head>
<body>
<script type="text/javascript">

function showVacationTypes(){

	document.getElementById("errandDuration").className ='hideClass';
	document.getElementById("leavePermission").className ='hideClass';
	document.getElementById("vacationDates").className ='hideClass';
	document.getElementById("alternativeDate").className='hideClass';
	document.getElementById("annualVacationTypes").className ='hideClass';
	document.getElementById("specialVacationTypes").className ='hideClass';
	document.getElementById("vacWithoutSal").className ='hideClass';
	
	
	var request_id=document.getElementById("request_id");
	
	//alert("request " + request_id.value);

	if(request_id.value!=null && request_id.value!=''){
		if(request_id.value=="1"){
		//	alert('---1');
			document.getElementById("specialVacationTypes").className ='showClass';
			//alert('---1.sh');
			document.getElementById("annualVacationTypes").className ='hideClass';
			//alert('---1.hi');
			document.getElementById("errandDuration").className ='hideClass';
			document.getElementById("leavePermission").className ='hideClass';
			//alert('---1.hi');
			<% 
			Settings settings = (Settings) session.getAttribute("settings");
			if(settings.getWithoutSalaryVacEnabled().booleanValue()==false){
			%> //alert("lehaa");
				document.getElementById("vacWithoutSal").className ='hideClass';
				document.getElementById("vacationDates").className ='showClass';
				document.getElementById("alternativeDate").className='hideClass';
			<%} else {%>
			//alert("lotus");
					var vac_id=document.getElementById("specialVacation");
				//	var vac_id2=document.getElementById("annualVacation");
					//alert('vac_id....='+vac_id.value);
				//	alert('vac_id2....='+vac_id2.value);
					if(vac_id.value!=null&& vac_id.value!=''){
						//alert('vac_id....='+vac_id.value);
						if(vac_id.value=='008'){
							document.getElementById("vacWithoutSal").className ='showClass';
							document.getElementById("vacationDates").className ='hideClass';
							document.getElementById("alternativeDate").className='hideClass';
						} else if (vac_id.value=='010'){
					//		alert("ra7aa esboo3eya");
							document.getElementById("vacWithoutSal").className ='hideClass';
							document.getElementById("vacationDates").className ='showClass';
							document.getElementById("alternativeDate").className='showClass';
						}
						else{
							document.getElementById("vacationDates").className ='showClass';
							document.getElementById("vacWithoutSal").className ='hideClass';
							document.getElementById("alternativeDate").className='hideClass';
						}					
					}
			<%}%>
		}
		else if(request_id.value=="2"){
			//alert("2");
			<%if(settings.getSpecialVacExcep().booleanValue()==false){%>
				document.getElementById("specialVacationTypes").className ='hideClass';
				document.getElementById("annualVacationTypes").className ='showClass';
				document.getElementById("errandDuration").className ='hideClass';
				document.getElementById("leavePermission").className ='hideClass';
				
				var vac_id=document.getElementById("annualVacation");
				//alert('vac_id....='+vac_id.value);
				if(vac_id.value!=null&& vac_id.value!=''){
					//alert('vac_id....='+vac_id.value);
					if(vac_id.value=='001'){
						//alert("showing vac without sal");
						document.getElementById("vacWithoutSal").className ='showClass';
						document.getElementById("vacationDates").className ='hideClass';
						document.getElementById("alternativeDate").className='hideClass';
					}
					else{
						document.getElementById("vacationDates").className ='showClass';
						document.getElementById("vacWithoutSal").className ='hideClass';
						document.getElementById("alternativeDate").className='hideClass';
					}					
				}
			<%} else {%>
				document.getElementById("specialVacationTypes").className ='hideClass';
				document.getElementById("annualVacationTypes").className ='showClass';
				document.getElementById("errandDuration").className ='hideClass';
				document.getElementById("leavePermission").className ='hideClass';
				document.getElementById("vacationDates").className ='showClass';
				document.getElementById("vacWithoutSal").className ='hideClass';
				document.getElementById("alternativeDate").className='hideClass';
			<%}%>
		}			
		else if(request_id.value=="3"){
			document.getElementById("specialVacationTypes").className ='hideClass';
			document.getElementById("annualVacationTypes").className ='hideClass';
			document.getElementById("errandDuration").className ='showClass';
			document.getElementById("leavePermission").className ='showClass';
			document.getElementById("vacationDates").className ='hideClass';
			document.getElementById("vacWithoutSal").className ='hideClass';
			document.getElementById("alternativeDate").className='hideClass';
		}
		else if(request_id.value=="4"){
			document.getElementById("specialVacationTypes").className ='hideClass';
			document.getElementById("annualVacationTypes").className ='hideClass';
			document.getElementById("errandDuration").className ='hideClass';
			document.getElementById("leavePermission").className ='hideClass';
			document.getElementById("vacationDates").className ='showClass';
			document.getElementById("vacWithoutSal").className ='hideClass';
			document.getElementById("alternativeDate").className='hideClass';
		} else if(request_id.value=="5"){
			document.getElementById("specialVacationTypes").className ='hideClass';
			document.getElementById("annualVacationTypes").className ='hideClass';
			document.getElementById("leavePermission").className ='hideClass';
			document.getElementById("errandDuration").className ='showClass';
			document.getElementById("vacationDates").className ='hideClass';
			document.getElementById("vacWithoutSal").className ='hideClass';
			document.getElementById("alternativeDate").className='hideClass';
		}
		else{
			document.getElementById("specialVacationTypes").className ='hideClass';
			document.getElementById("annualVacationTypes").className ='hideClass';
			document.getElementById("errandDuration").className ='hideClass';
			document.getElementById("leavePermission").className ='hideClass';
			document.getElementById("vacationDates").className ='hideClass';
			document.getElementById("vacWithoutSal").className ='hideClass';
			document.getElementById("alternativeDate").className='hideClass';
		}
	}	
}

function getVacCredit(){
	//alert('-----vacId--'+vacId.value);
	//alert('---ann---'+document.getElementById("annualVacationTypes").value);
	var request_id=document.getElementById("request_id").value;
	
	var vacId=document.getElementById("annualVacation").value;
	var from_date=document.getElementById("from_date").value;
	//alert('-----vacId--'+vacId);

	var URL='loginUsersRequestsForm.html?vacation='+vacId+'&fromDate='+from_date+'&request_id='+request_id;
	window.location.href=URL;
	//document.loginUsersRequestsForm.action = URL;
//	document.loginUsersRequestsForm.submit();
	//alert('Your balance = '+document.getElementById("vacCredit").value);
}

function calculateDiff(){
	var dateFrom = document.getElementById('from_date').value;
	//alert('---- dateFrom----'+dateFrom);
	var dateTo   = document.getElementById('to_date').value;
	//alert('---- dateTo----'+dateTo); 
	if(dateFrom != '' && dateTo != ''){
		var one_day=1000*60*60*24;
		var z1=dateFrom.substring(0,10);
		var z2=dateTo.substring(0,10);
		//alert('--z1--'+z1);
		//alert('--z2--'+z2);
        var x = z1.split("/");
        //alert('--x--'+x);
        var y = z2.split("/");
       // alert('--y--'+y);
        
        //alert('--x[0]---'+x[0]);
        //alert('--x[1]---'+x[1]);
        //alert('--x[2]---'+x[2]);
        
        var date1=new Date();
        date1.setFullYear(x[2]);
        date1.setMonth(x[1]-1);
        var date2=new Date();
        date2.setFullYear(y[2]);
		date2.setMonth(y[1]-1);

		// to get no of days in each month
        date2.setMonth(date2.getMonth()+1, 0);
        date1.setMonth(date1.getMonth()+1, 0);
        //alert('----date1---'+date1.getDate());

        // no of days in month
        day1=date1.getDate();
        day2=date2.getDate();

        
       	diffMonths=date2.getMonth()-date1.getMonth();
       //	alert('---diffMonths---'+diffMonths);            

        date1.setDate(x[0]) ;
        date2.setDate(y[0]);
        
        //alert('----date1---'+date1.getDate());
        //alert('----date2---'+date2.getDate());
        // selected days
        day11=date1.getDate();
        day22=date2.getDate();
        
       	if(diffMonths!=0){
       		toEnd=day1-day11;   	
           	fromStart=day22;
           	days=toEnd+fromStart;
            document.getElementById('withdrawDays').value = Math.abs(days)+1;
          //alert('----Math.abs(days)---'+Math.abs(days));
       	}
       	else if(diffMonths==0){
       		diff=(date2.getDate()-date1.getDate())+1;
       		document.getElementById('withdrawDays').value = Math.abs(diff);
       	    //alert('----Math.abs(diff)---'+Math.abs(diff));
       	}
        //alert('----date2---'+date2.getDate());
        
        //alert('----diff2---'+diff2);
        //alert('---- diff----'+diff);
       
	}
	//var diff = dateTo.getDate()-dateFrom.getDate() ;
	//alert('---- diff----'+diff);
	//document.getElementById('withdrawDays').value = diff;	
}


function calculateDiff2(){
	var dateFrom;
	var dateTo;
	
	<%if(settings.getPeriodFromToEnabled().booleanValue()==true){%>
		dateFrom = document.getElementById('vac_period_from').value;
		//alert('---- dateFrom----'+dateFrom);
		dateTo   = document.getElementById('vac_period_to').value;
	<%}else {%>
		dateFrom = document.getElementById('from_date').value;
		//alert('---- dateFrom----'+dateFrom);
		dateTo   = document.getElementById('to_date').value;
		//alert('---- dateTo----'+dateTo);
	<%}%>
	//alert('---- dateTo----'+dateTo); 
	if(dateFrom != '' && dateTo != ''){
		var one_day=1000*60*60*24;
		var z1=dateFrom.substring(0,10);
		var z2=dateTo.substring(0,10);
		//alert('--z1--'+z1);
		//alert('--z2--'+z2);
        var x = z1.split("/");
        //alert('--x--'+x);
        var y = z2.split("/");        
        var date1=new Date();
        
       <%if(settings.getPeriodFromToEnabled().booleanValue()==true){%>
        	date1.setFullYear(x[0]);
        <%}else{%>
        	date1.setFullYear(x[2]);
       <% }%>
       
        date1.setMonth(x[1]-1);
        var date2=new Date();
        
        <%if(settings.getPeriodFromToEnabled().booleanValue()==true){%>
       	 	date2.setFullYear(y[0]);
       	<%}else{%>
    		date2.setFullYear(y[2]);
   		<% }%>
   		
		date2.setMonth(y[1]-1);

		// to get no of days in each month
        date2.setMonth(date2.getMonth()+1, 0);
        date1.setMonth(date1.getMonth()+1, 0);
        //alert('----date1---'+date1.getDate());

        // no of days in month
        day1=date1.getDate();
        //alert("day1 " +day1);
        day2=date2.getDate();
        //alert("day2 " +day2);

        
       	diffMonths=date2.getMonth()-date1.getMonth();
      	//alert('---diffMonths---'+diffMonths);            

       	<%if(settings.getPeriodFromToEnabled().booleanValue()==true){%>
	       	date1.setDate(x[2]) ;
	        date2.setDate(y[2]);
	    <%}else{%>
		    date1.setDate(x[0]) ;
	        date2.setDate(y[0]);
	   <% }%>
        
        
        //alert('----date1---'+date1.getDate());
        //alert('----date2---'+date2.getDate());
        // selected days
        day11=date1.getDate();
        day22=date2.getDate();
        
       	if(diffMonths!=0){
       		
       		toEnd=day1-day11+1;
       		//alert("to end " + toEnd);
           	fromStart=day22;
           	days=toEnd+fromStart;
            document.getElementById('withdraw').value = Math.abs(days);
          //alert('----Math.abs(days)---'+Math.abs(days));
       	}
       	else if(diffMonths==0){
       		diff=(date2.getDate()-date1.getDate())+1;
       		//alert(diff);
       		document.getElementById('withdraw').value = Math.abs(diff);
       	}
	}
}
 function getBalanceVac(){
	 var empCode =document.getElementById('empCode').value;
	 var vacId=document.getElementById("annualVacation").value;
	 var from_date=document.getElementById("from_date").value;
	 var hostName=document.getElementById('hostName').value;;
	 var serviceName=document.getElementById('serviceName').value;;
	 var userName=document.getElementById('userName').value;;
	 var password=document.getElementById('password').value;;
	 
	 
	// alert("1 "+from_date);
	 if(vacId!=null && vacId!=''){
	 
	 	 if(from_date==null || from_date==""){
			var period_from= document.getElementById("period_from").value;
			var z1;
				z1=period_from.substring(0,10);
				// alert("2 "+z1);
			 var x = z1.split("/");
			// alert("3  "+x);
			 var dFrom=new Date();
				 dFrom.setDate(x[2]) ;
				 dFrom.setFullYear(x[0]);
			 dFrom.setMonth(x[1]-1);
			 
			// alert("4 "+dFrom);
			 requestsDwr.getVacationCredit(fillBalance,empCode,2,vacId, dFrom, hostName, serviceName, userName, password);
			
		} else if(from_date!=null && from_date!=''){
		 var z1=from_date.substring(0,10);
		 var x = z1.split("/");
		 var dFrom=new Date();
		 dFrom.setDate(x[0]) ;
		 dFrom.setMonth(x[1]-1);
		 dFrom.setFullYear(x[2]);
		 		
		 requestsDwr.getVacationCredit(fillBalance,empCode,2,vacId, dFrom);
		 }
	 }
	 

 }
  
 function  fillBalance(data){
	// alert('--data='+data);
	 if (data!=null){
		 document.getElementById('vacCredit').value=data;
	 }
 }
 
 var options = {
		  enableHighAccuracy: true,
		  timeout: 5000,
		  maximumAge: 0
		};

		function success(pos) {
		  var crd = pos.coords;

		  //alert (crd.latitude+","+crd.longitude+","+crd.accuracy);
		  document.getElementById("longitude").setAttribute("value", crd.longitude);
		  document.getElementById("latitude").setAttribute("value", crd.latitude);
		  document.getElementById("accuracy").setAttribute("value", crd.accuracy);
		  
		  console.log('Your current position is:');
		  console.log('Latitude : ${crd.latitude}');
		  console.log('Longitude: ${crd.longitude}');
		  console.log('More or less ${crd.accuracy} meters.');
		}

		function error(err) {
			alert(err.code + " - " + err.message);
		}

		navigator.geolocation.getCurrentPosition(success, error, options);
</script>
<%
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String nowDate=sdf.format(cal.getTime());
	%>
<table width="90%"  border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n property="requestsApproval.header.loginUsersRequests"/><fmt:message key="requestsApproval.header.loginUsersRequests"/></td><td align="left"></td>
	</tr>
	<tr>
		<td colspan="2" bgcolor="#5A718B" height="2"></td>
	</tr>
	<tr>
		<td colspan="2" height="1"></td>
	</tr>
	<tr>
		<td colspan="2" height="20"></td>
	</tr>
	<tr>
		<td colspan="2">
			<spring:bind path="loginUsersRequests.*">
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
		<td>
			<c:if test="${done==true}"><font color="blue" size="5"> 
	        <abc:i18n	property="requestsApproval.loginUsersRequests.saveSuccess" /><fmt:message
					key="requestsApproval.loginUsersRequests.saveSuccess" />
	 		</font>
	 		</c:if>
		</td>
		
	</tr>
	<tr>
		<td>
			<c:if test="${limit!=null}"><font color="red"> 
	        <abc:i18n	property="requestsApproval.errors.vacLimit" /><fmt:message
					key="requestsApproval.errors.vacLimit" />&nbsp; ${limit }&nbsp;
			<abc:i18n	property="requestsApproval.errors.day" /><fmt:message
			key="requestsApproval.errors.day" />
	 		</font>
	 		</c:if>
		</td>
	</tr>
	<tr>
		<td>
			<form id="loginUsersRequestsForm" name="loginUsersRequestsForm"	method="POST" action="<c:url value="/requestsApproval/loginUsersRequestsForm.html"/>">
					 <c:set var="dateNow" value="<%=nowDate%>" />
					 
					  <input type="hidden" name="longitude" id="longitude" value=""/>
					 <input type="hidden" name="latitude" id="latitude" value=""/>
					 <input type="hidden" name="accuracy" id="accuracy" value=""/>
					 
					 <input type="hidden"  id="empRequestTypeId" name="empRequestTypeId" value="${empRequestTypeId}"/>
					 
					 <input type="hidden" name="hostName" id="hostName" value="${settings.server}"/>
					 <input type="hidden" name="serviceName" id="serviceName" value="${settings.service}"/>
					 <input type="hidden" name="userName" id="userName" value="${settings.username}"/>
					 <input type="hidden" name="password" id="password" value="${settings.password}"/>
					 
					<table border=0 cellspacing=1 cellpadding=0 id="ep" style="margin-right:40px">
						<tr id="head_1_ep">
							<td class="bodyBold" colspan=4 nowrap>
							<abc:i18n property="requestsApproval.header.loginUsersRequests"/><fmt:message key="requestsApproval.header.loginUsersRequests"/></td>
							<td nowrap colspan=1 align=left> <abc:i18n property="commons.caption.requiredInformation"/><span class="bodySmallBold"><fmt:message key="commons.caption.requiredInformation"/></span></td>
						</tr>
						<c:set var="check"  value=""/>
				  		<c:if test="${empRequestTypeId!=null && empRequestTypeId!='' }">
							 <c:set var="check"  value="disabled"/>
						</c:if>
						

						<tr class="showClass">
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.userCode"/>
								<fmt:message key="requestsApproval.caption.userCode"/>
							</td>						
							<td  class="formBodControl"> 
							<input type="hidden" name="empCode" id="empCode" value="${employeeCode}"/>
							${employeeCode}
							</td>
						
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.userName"/>
								<fmt:message key="requestsApproval.caption.userName"/>
							</td>							
	 						<td class="formBodControl" id="employeeName" > 
							&nbsp;
							${employeeName}
							</td>													
						</tr>
						
						<tr>	
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.requestDate"/>
								<fmt:message key="requestsApproval.caption.requestDate"/>
							</td>
							<td  class="formBodControl">
								<spring:bind path="loginUsersRequests.request_date">
									<input type="text"  readonly="readonly"  dir="ltr"  name="${status.expression}" id="${status.expression}"  value="${dateNow}" />
								</spring:bind>
							</td>
						</tr>								
						<tr>
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.requestType"/>
								<fmt:message key="requestsApproval.caption.requestType"/>
							</td>
							<td  class="formBodControl">
								<spring:bind path="loginUsersRequests.request_id">
									<select name="request_id" id="${status.expression}" onchange="showVacationTypes();">
										<option value=""><fmt:message key="commons.caption.select" /></option>						
											<c:forEach items="${requestTypeList}" var="request">
												<option value="${request.id}" ${request.id == loginUsersRequests.request_id.id ?' selected' : ''} onclick="showVacationTypes()">${request.description}</option>
											</c:forEach>	
									</select>
								</spring:bind>
							</td>						
						</tr>
						
						<c:if test="${(loginUsersRequests.request_id.id == null || loginUsersRequests.request_id.id== '')}">
						
						<tr id="specialVacationTypes" class="hideClass">
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.vacationType"/>
								<fmt:message key="requestsApproval.caption.vacationType"/>
							</td>
							<td  class="formBodControl" >
								<select name="specialVacation" id="specialVacation"  onchange="showVacationTypes()">
									<option value=""><fmt:message key="commons.caption.select" /></option>						
										<c:forEach items="${specialVacations}" var="vacation">
											<option value="${vacation.vacation}" ${vacation.vacation == loginUsersRequests.vacation.vacation?' selected' : ''} onclick="showVacationTypes()">${vacation.name}</option>
										</c:forEach>
								</select>
							</td>					
						</tr>

							<tr id="annualVacationTypes" class="hideClass">
								<td nowrap class="formReq"><abc:i18n
										property="requestsApproval.caption.vacationType" /> <fmt:message
										key="requestsApproval.caption.vacationType" /></td>
								<td class="formBodControl"><select name="annualVacation"
									id="annualVacation" onchange="getBalanceVac();showVacationTypes()">
										<option value=""><fmt:message
												key="commons.caption.select" /></option>
										<c:forEach items="${annualVacations}" var="vacation">
											<option value="${vacation.vacation}"
												${vacation.vacation == loginUsersRequests.vacation.vacation?' selected' : ''}>${vacation.name}</option>
										</c:forEach>
								</select></td>

								<td></td>

								<c:if test="${settings.annualVacBalDaysEnabled==true}">
									<td class="formBodControl"><abc:i18n
											property="commons.button.getVacCredit" /> <fmt:message
											key="commons.button.getVacCredit" /></td>


									<td class="formBodControl"><input type="text" size="10"
										readonly="readonly" name="vacCredit"
										id="vacCredit" value="${vacCredit}" /></td>
								</c:if>

							</tr>

							<tr id="vacationDates" class="hideClass">
					  		<td nowrap class="formReq" >
								<abc:i18n property="commons.caption.fromDate"/>
								<fmt:message key="commons.caption.fromDate"/>
							</td>
							<td  class="formBodControl" >
								<spring:bind path="loginUsersRequests.from_date">
									<input type="text"  class="calendar"  readonly="readonly" autocomplete="off" dir="ltr"  name="${status.expression}" id="${status.expression}" value="${status.value}" />
								</spring:bind>
							</td>

					  		<td nowrap class="formReq" >
								<abc:i18n property="commons.caption.toDate"/>
								<fmt:message key="commons.caption.toDate"/>
							</td>
							<td  class="formBodControl" >
								<spring:bind path="loginUsersRequests.to_date">
									<input type="text"  class="calendar"  readonly="readonly" autocomplete="off" dir="ltr"  name="${status.expression}" id="${status.expression}" value="${status.value}" />
								</spring:bind>
							</td>
							
					  		<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.requestsApprovalForm.reqPeriod"/>
								<fmt:message key="requestsApproval.requestsApprovalForm.reqPeriod"/>
							</td>
							<td  class="formBodControl" >
								<input type="text"  name="withdrawDays" id="withdrawDays" onfocus="calculateDiff()"  />
							</td>							
						</tr>
						<tr id="alternativeDate" class="hideClass">
							<td nowrap class="formReq" >
								<abc:i18n property="commons.caption.altDate"/>
								<fmt:message key="commons.caption.altDate"/>
							</td>
							<td  class="formBodControl" >
								<spring:bind path="loginUsersRequests.altDate">
									<input type="text"  class="calendar"  readonly="readonly" autocomplete="off" dir="ltr"  name="${status.expression}" id="${status.expression}" value="${status.value}" />
								</spring:bind>
							</td>
						</tr>

							<tr id="errandDuration" class="hideClass">
								<td nowrap class="formReq"><abc:i18n
										property="commons.caption.from" /> <fmt:message
										key="commons.caption.from" /></td>
								<td class="formBodControl"><spring:bind
										path="loginUsersRequests.period_from">
										<input type="text" class="MM_from" title="ccc" dir="ltr"
											name="${status.expression}" id="${status.expression}"
											value="${status.value}" />
									</spring:bind></td>

								<td nowrap class="formReq"><abc:i18n
										property="commons.caption.to" /> <fmt:message
										key="commons.caption.to" /></td>
								<td class="formBodControl"><spring:bind
										path="loginUsersRequests.period_to">
										<input type="text" class="MM_to" title="ccc"
											autocomplete="off" dir="ltr" name="${status.expression}"
											id="${status.expression}" value="${status.value}" />
									</spring:bind></td>
							</tr>
							
							
							<tr id="leavePermission" class="hideClass">						
					  									
							<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.leaveEffect"/>
								<fmt:message key="requestsApproval.caption.leaveEffect"/>
							</td>
							<td  class="formBodControl" >
								<spring:bind path="loginUsersRequests.leave_effect">
									<select name="${status.expression}" id="${status.expression}" >
										<option value=""><fmt:message key="commons.caption.select" /></option>						
										<!-- <option value="0" ${status.value == "0" ? ' selected':''}><fmt:message key="requestsApproval.caption.without"/></option>-->
										<option value="1" ${status.value == "1" ? ' selected':''}><fmt:message key="requestsApproval.caption.delay"/></option>
										<option value="2" ${status.value == "2" ? ' selected':''}><fmt:message key="requestsApproval.caption.early"/></option>
									</select>
								</spring:bind>
							</td>														
						</tr>				
						
						<tr id="vacWithoutSal" class="hideClass">						
					  		<td nowrap class="formReq">
								<abc:i18n property="commons.caption.from"/>
								<fmt:message key="commons.caption.from"/>
							</td>
							<td  class="formBodControl" >
							<spring:bind path="loginUsersRequests.vac_period_from">
								<input type="text"  class="MM_from_vac" title="ccc" dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
							</spring:bind>
							</td>

					  		<td nowrap class="formReq" >
								<abc:i18n property="commons.caption.to"/>
								<fmt:message key="commons.caption.to"/>
							</td>
							<td  class="formBodControl" >
							<spring:bind path="loginUsersRequests.vac_period_to">
								<input type="text"  class="MM_to_vac" title="ccc"  dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
							</spring:bind>
							</td>
							
							<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.requestsApprovalForm.reqPeriod"/>
								<fmt:message key="requestsApproval.requestsApprovalForm.reqPeriod"/>
							</td>
							<td  class="formBodControl" >
								<select name="withoutSalPeriod" id="withoutSalPeriod" >
									<option value=""><fmt:message key="commons.caption.select" /></option>
									<%if(settings.getWithdrawDaysQuartPolicy().booleanValue()==true){%>						
										<option value="quar" ${status.value == "quar" ? ' selected':''}><fmt:message key="requestsApproval.caption.quarDay"/></option>
									<%} %>
									<option value="half" ${status.value == "half" ? ' selected':''}><fmt:message key="requestsApproval.caption.halfDay"/></option>
									<option value="full" ${status.value == "full" ? ' selected':''}><fmt:message key="requestsApproval.errors.day"/></option>
								</select>
							</td>
							
						
						<%if(settings.getExecuseEnabled().booleanValue()==true){ %>
							<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.leaveTime"/>
								<fmt:message key="requestsApproval.caption.leaveTime"/>
							</td>
							
							<td  class="formBodControl" >
								<select name="leaveTime" id="leaveTime" >
									<option value=""><fmt:message key="commons.caption.select" /></option>						
									<option value="start" ${status.value == "start" ? ' selected':''}><fmt:message key="requestsApproval.caption.leaveTimeStart"/></option>
									<option value="end" ${status.value == "end" ? ' selected':''}><fmt:message key="requestsApproval.caption.leaveTimeEnd"/></option>
								</select>
							</td>	
							<%} %>
							<%if(settings.getPeriodFromToEnabled().booleanValue()==true){ %>	
							<td nowrap class="formBodControl" >
								<abc:i18n property="requestsApproval.caption.reqPeriod"/>
								<fmt:message key="requestsApproval.caption.reqPeriod"/>
							</td>
							<td  class="formBodControl" >
								<input type="text"  name="withdraw" id="withdraw" onfocus="calculateDiff2()" value="" />
							</td>	
							<%} %>						
						</tr>				
						</c:if>
						
						<c:if test="${(loginUsersRequests.request_id.id!=null && loginUsersRequests.request_id.id!='')}">
						<c:if test="${loginUsersRequests.request_id.id!=3}">
						
							<c:if test="${loginUsersRequests.request_id.id==1}">
								<tr id="specialVacationTypes" class="showClass">
							  		<td nowrap class="formReq">
										<abc:i18n property="requestsApproval.caption.vacationType"/>
										<fmt:message key="requestsApproval.caption.vacationType"/>
									</td>
									<td  class="formBodControl" >
										<select name="specialVacation" id="specialVacation" onchange="showVacationTypes()" >
											<option value=""><fmt:message key="commons.caption.select" /></option>						
												<c:forEach items="${specialVacations}" var="vacation">
													<option value="${vacation.vacation}" ${vacation.vacation == loginUsersRequests.vacation.vacation?' selected' : ''} onclick="showVacationTypes()">${vacation.name}</option>
												</c:forEach>
										</select>
									</td>						
								</tr>
								<c:if test="${loginUsersRequests.vacation.vacation=='008'}">
									<tr id="vacWithoutSal" class="showClass">						
					  					<td nowrap class="formReq">
											<abc:i18n property="commons.caption.from"/>
											<fmt:message key="commons.caption.from"/>
											</td>
											<td  class="formBodControl" >
											<spring:bind path="loginUsersRequests.vac_period_from">
													<input type="text"   dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
											</spring:bind>
											</td>

											<td nowrap class="formReq" >
												<abc:i18n property="commons.caption.to"/>
												<fmt:message key="commons.caption.to"/>
											</td>
											<td  class="formBodControl" >
											<spring:bind path="loginUsersRequests.vac_period_to">
													<input type="text"  dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
											</spring:bind>									
											</td>
											
											<td nowrap class="formReq" >
												<abc:i18n property="requestsApproval.requestsApprovalForm.reqPeriod"/>
												<fmt:message key="requestsApproval.requestsApprovalForm.reqPeriod"/>
											</td>
											<td  class="formBodControl" >
												<select name="withoutSalPeriod" id="withoutSalPeriod" >
													<option value=""><fmt:message key="commons.caption.select" /></option>						
													<%if(settings.getWithdrawDaysQuartPolicy().booleanValue()==true){ %>
													<option value="quar" ${status.value == "quar" ? ' selected':''}><fmt:message key="requestsApproval.caption.quarDay"/></option>
													<%} %>
													<option value="half" ${status.value == "half" ? ' selected':''}><fmt:message key="requestsApproval.caption.halfDay"/></option>
													<option value="full" ${status.value == "full" ? ' selected':''}><fmt:message key="requestsApproval.errors.day"/></option>
												</select>
											</td>

										<%if(settings.getExecuseEnabled().booleanValue()==true){ %>
											<td nowrap class="formReq" >
												<abc:i18n property="requestsApproval.caption.leaveTime"/>
												<fmt:message key="requestsApproval.caption.leaveTime"/>
											</td>
											<td  class="formBodControl" >
												<select name="leaveTime" id="leaveTime" >
													<option value=""><fmt:message key="commons.caption.select" /></option>						
													<option value="start" ${status.value == "start" ? ' selected':''}><fmt:message key="requestsApproval.caption.leaveTimeStart"/></option>
													<option value="end" ${status.value == "end" ? ' selected':''}><fmt:message key="requestsApproval.caption.leaveTimeEnd"/></option>
												</select>
											</td>
										<%} %>						
										<%if(settings.getWithoutSalPeriodValidation().booleanValue()==true){ %>					
											<td nowrap class="formBodControl" >
												<abc:i18n property="requestsApproval.caption.reqPeriod"/>
												<fmt:message key="requestsApproval.caption.reqPeriod"/>
											</td>
											<td  class="formBodControl" >
												<input type="text"  name="withdraw" id="withdraw" onfocus="calculateDiff2()" value="${loginUsersRequests.withdrawDays}" />
											</td>
											<%} %>
										</tr>				
								</c:if>
								
								<c:if test="${loginUsersRequests.vacation.vacation!='008'}">
									<tr id="vacationDates" class="showClass">
							  			<td nowrap class="formReq" >
											<abc:i18n property="commons.caption.fromDate"/>
											<fmt:message key="commons.caption.fromDate"/>
										</td>
										<td  class="formBodControl" >
											<spring:bind path="loginUsersRequests.from_date">
												<input type="text" title="cc" class="calendar"  readonly="readonly" autocomplete="off" dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
											</spring:bind>
										</td>
		
								  		<td nowrap class="formReq" >
											<abc:i18n property="commons.caption.toDate"/>
											<fmt:message key="commons.caption.toDate"/>
										</td>
										<td  class="formBodControl">
											<spring:bind path="loginUsersRequests.to_date">
												<input type="text"  title="cc" class="calendar" readonly="readonly" autocomplete="off" dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
											</spring:bind>
										</td>
																
							  			<td nowrap class="formReq" >
											<abc:i18n property="requestsApproval.requestsApprovalForm.reqPeriod"/>
											<fmt:message key="requestsApproval.requestsApprovalForm.reqPeriod"/>
										</td>
									
										<c:if test="${loginUsersRequests!=null}">
										<td  class="formBodControl" >
											<input type="text"  name="withdrawDays" id="withdrawDays" value="${loginUsersRequests.withdrawDays}" onfocus="calculateDiff()"  />
										</td>
										</c:if>
										<c:if test="${loginUsersRequests==null}">
										<td  class="formBodControl" >
											<input type="text"  name="withdrawDays" id="withdrawDays" value="${withdrawDays}" onfocus="calculateDiff()"  />
										</td>
										</c:if>
									</tr>
									<c:if test="${loginUsersRequests.vacation.vacation=='010'}">
										<tr id="alternativeDate" class="showClass">
											<td nowrap class="formReq" >
												<abc:i18n property="commons.caption.altDate"/>
												<fmt:message key="commons.caption.altDate"/>
											</td>
											<td  class="formBodControl" >
												<spring:bind path="loginUsersRequests.altDate">
													<input type="text"  class="calendar"  readonly="readonly" autocomplete="off" dir="ltr"  name="${status.expression}" id="${status.expression}" value="${status.value}" />
												</spring:bind>
											</td>
										</tr>
									</c:if>
								</c:if>							
								<tr id="annualVacationTypes" class="hideClass">
							  		<td nowrap class="formReq" >
										<abc:i18n property="requestsApproval.caption.vacationType"/>
										<fmt:message key="requestsApproval.caption.vacationType"/>
									</td>
									<td  class="formBodControl">
										<select name="annualVacation" id="annualVacation"  onchange="showVacationTypes()" >
											<option value=""><fmt:message key="commons.caption.select" /></option>						
												<c:forEach items="${annualVacations}" var="vacation">
													<option value="${vacation.vacation}" ${vacation.vacation == loginUsersRequests.vacation.vacation?' selected' : ''} >${vacation.name}</option>
												</c:forEach>
										</select>
									</td>
									
																
									<td>
									</td>
									
									<td  class="formBodControl">
										<abc:i18n property="commons.button.getVacCredit"/><fmt:message key="commons.button.getVacCredit"/>
									</td>
									
									<td  class="formBodControl" >
										<input type="text" size="10"  readonly="readonly" onfocus="getBalanceVac()" name="vacCredit" id="vacCredit" value="${vacCredit}" />
									</td>
																
								</tr>
								
								<tr id="vacWithoutSal" class="hideClass">						
									<td nowrap class="formReq">
										<abc:i18n property="commons.caption.from"/>
										<fmt:message key="commons.caption.from"/>
									</td>
									<td  class="formBodControl" >
									<spring:bind path="loginUsersRequests.vac_period_from">
										<input type="text"  class="MM_from_vac" title="ccc" dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
									</spring:bind>
									</td>

									<td nowrap class="formReq" >
										<abc:i18n property="commons.caption.to"/>
										<fmt:message key="commons.caption.to"/>
									</td>
									<td  class="formBodControl" >
									<spring:bind path="loginUsersRequests.vac_period_to">
										<input type="text"  class="MM_to_vac" title="ccc"  dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
									</spring:bind>
									</td>
									
									<td nowrap class="formReq" >
										<abc:i18n property="requestsApproval.requestsApprovalForm.reqPeriod"/>
										<fmt:message key="requestsApproval.requestsApprovalForm.reqPeriod"/>
									</td>
									<td  class="formBodControl" >
										<select name="withoutSalPeriod" id="withoutSalPeriod" >
											<option value=""><fmt:message key="commons.caption.select" /></option>						
<!--											<option value="quar" ${status.value == "quar" ? ' selected':''}><fmt:message key="requestsApproval.caption.quarDay"/></option>-->
											<option value="half" ${status.value == "half" ? ' selected':''}><fmt:message key="requestsApproval.caption.halfDay"/></option>
											<option value="full" ${status.value == "full" ? ' selected':''}><fmt:message key="requestsApproval.errors.day"/></option>
										</select>
									</td>
									
								<!--
									<td nowrap class="formReq" >
										<abc:i18n property="requestsApproval.caption.leaveTime"/>
										<fmt:message key="requestsApproval.caption.leaveTime"/>
									</td>
									
									<td  class="formBodControl" >
										<select name="leaveTime" id="leaveTime" >
											<option value=""><fmt:message key="commons.caption.select" /></option>						
											<option value="start" ${status.value == "start" ? ' selected':''}><fmt:message key="requestsApproval.caption.leaveTimeStart"/></option>
											<option value="end" ${status.value == "end" ? ' selected':''}><fmt:message key="requestsApproval.caption.leaveTimeEnd"/></option>
										</select>
									</td>		-->
									<td nowrap class="formBodControl" >
										<abc:i18n property="requestsApproval.caption.reqPeriod"/>
										<fmt:message key="requestsApproval.caption.reqPeriod"/>
									</td>
									<td  class="formBodControl" >
										<input type="text"  name="withdraw" id="withdraw" onfocus="calculateDiff2()" value="${loginUsersRequests.withdrawDays}" />
									</td>							
								</tr>			
								
								
							</c:if>
							
							<c:if test="${loginUsersRequests.request_id.id==2}">
													
								<tr id="annualVacationTypes" class="showClass">
							  		<td nowrap class="formReq" >
										<abc:i18n property="requestsApproval.caption.vacationType"/>
										<fmt:message key="requestsApproval.caption.vacationType"/>
									</td>
									<td  class="formBodControl">
										<select name="annualVacation" id="annualVacation"  onchange="showVacationTypes()">
											<option value=""><fmt:message key="commons.caption.select" /></option>						
												<c:forEach items="${annualVacations}" var="vacation">
													<option value="${vacation.vacation}" ${vacation.vacation == loginUsersRequests.vacation.vacation?' selected' : ''} >${vacation.name}</option>
												</c:forEach>
										</select>
									</td>
																
									<td>
									</td>
									
									<td  class="formBodControl">
										<abc:i18n property="commons.button.getVacCredit"/><fmt:message key="commons.button.getVacCredit"/>
									</td>
									
									<td  class="formBodControl" >
										<input type="text" size="10"   readonly="readonly" onfocus="getBalanceVac()" name="vacCredit" id="vacCredit" value="${vacCredit}" />
									</td>															
								</tr>
								<c:choose>
								<c:when  test="${loginUsersRequests.vacation.vacation=='001'}">
								<tr id="vacWithoutSal" class="showClass">						
							  		<td nowrap class="formReq">
										<abc:i18n property="commons.caption.from"/>
										<fmt:message key="commons.caption.from"/>
									</td>
									<td  class="formBodControl" >
									<spring:bind path="loginUsersRequests.vac_period_from">
										<input type="text"  class="MM_from_vac" title="ccc" dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
									</spring:bind>
									</td>
		
							  		<td nowrap class="formReq" >
										<abc:i18n property="commons.caption.to"/>
										<fmt:message key="commons.caption.to"/>
									</td>
									<td  class="formBodControl" >
									<spring:bind path="loginUsersRequests.vac_period_to">
										<input type="text"  class="MM_to_vac" title="ccc"  dir="ltr"  name="${status.expression}" id="${status.expression}" value="${status.value}"/>
									</spring:bind>
									</td>
									
									<td nowrap class="formReq" >
										<abc:i18n property="requestsApproval.requestsApprovalForm.reqPeriod"/>
										<fmt:message key="requestsApproval.requestsApprovalForm.reqPeriod"/>
									</td>
									<td  class="formBodControl" >
										<select name="withoutSalPeriod" id="withoutSalPeriod" >
											<option value=""><fmt:message key="commons.caption.select" /></option>						
<!--											<option value="quar" ${status.value == "quar" ? ' selected':''}><fmt:message key="requestsApproval.caption.quarDay"/></option>-->
											<option value="half" ${status.value == "half" ? ' selected':''}><fmt:message key="requestsApproval.caption.halfDay"/></option>
											<option value="full" ${status.value == "full" ? ' selected':''}><fmt:message key="requestsApproval.errors.day"/></option>
										</select>
									</td>

									<td  class="formBodControl" >
										<abc:i18n property="requestsApproval.caption.reqPeriod"/>
										<fmt:message key="requestsApproval.caption.reqPeriod"/>
									</td>
									<td  class="formBodControl" >
										<input type="text"  name="withdraw" id="withdraw" onfocus="calculateDiff2()" value="${loginUsersRequests.withdrawDays}" />
									</td>													
								</tr>
								
								<tr id="vacationDates" class="hideClass">
							  		<td nowrap class="formReq" >
										<abc:i18n property="commons.caption.fromDate"/>
										<fmt:message key="commons.caption.fromDate"/>
									</td>
									<td  class="formBodControl" >
										<spring:bind path="loginUsersRequests.from_date">
											<input type="text" title="cc" class="calendar"  readonly="readonly" autocomplete="off" dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
										</spring:bind>
									</td>
		
							  		<td nowrap class="formReq" >
										<abc:i18n property="commons.caption.toDate"/>
										<fmt:message key="commons.caption.toDate"/>
									</td>
									<td  class="formBodControl">
										<spring:bind path="loginUsersRequests.to_date">
											<input type="text"  title="cc" class="calendar" readonly="readonly" autocomplete="off" dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
										</spring:bind>
									</td>
																
							  		<td nowrap class="formReq" >
										<abc:i18n property="requestsApproval.requestsApprovalForm.reqPeriod"/>
										<fmt:message key="requestsApproval.requestsApprovalForm.reqPeriod"/>
									</td>
									
									<c:if test="${loginUsersRequests!=null}">
									<td  class="formBodControl" >
										<input type="text"  name="withdrawDays" id="withdrawDays" value="${loginUsersRequests.withdrawDays}" onfocus="calculateDiff()"  />
									</td>
									</c:if>
									<c:if test="${loginUsersRequests==null}">
									<td  class="formBodControl" >
										<input type="text"  name="withdrawDays" id="withdrawDays" value="${withdrawDays}" onfocus="calculateDiff()"  />
									</td>
									</c:if>
								</tr>
								</c:when>
								<c:otherwise>
								<tr id="vacationDates" class="showClass">
							  		<td nowrap class="formReq" >
										<abc:i18n property="commons.caption.fromDate"/>
										<fmt:message key="commons.caption.fromDate"/>
									</td>
									<td  class="formBodControl" >
										<spring:bind path="loginUsersRequests.from_date">
											<input type="text" title="cc" class="calendar"  readonly="readonly" autocomplete="off" dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
										</spring:bind>
									</td>
		
							  		<td nowrap class="formReq" >
										<abc:i18n property="commons.caption.toDate"/>
										<fmt:message key="commons.caption.toDate"/>
									</td>
									<td  class="formBodControl">
										<spring:bind path="loginUsersRequests.to_date">
											<input type="text"  title="cc" class="calendar" readonly="readonly" autocomplete="off" dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
										</spring:bind>
									</td>
																
							  		<td nowrap class="formReq" >
										<abc:i18n property="requestsApproval.requestsApprovalForm.reqPeriod"/>
										<fmt:message key="requestsApproval.requestsApprovalForm.reqPeriod"/>
									</td>
									
									<c:if test="${loginUsersRequests!=null}">
									<td  class="formBodControl" >
										<input type="text"  name="withdrawDays" id="withdrawDays" value="${loginUsersRequests.withdrawDays}" onfocus="calculateDiff()"  />
									</td>
									</c:if>
									<c:if test="${loginUsersRequests==null}">
									<td  class="formBodControl" >
										<input type="text"  name="withdrawDays" id="withdrawDays" value="${withdrawDays}" onfocus="calculateDiff()"  />
									</td>
									</c:if>
								</tr>
								
								<tr id="vacWithoutSal" class="hideClass">						
							  		<td nowrap class="formReq">
										<abc:i18n property="commons.caption.from"/>
										<fmt:message key="commons.caption.from"/>
									</td>
									<td  class="formBodControl" >
									<spring:bind path="loginUsersRequests.vac_period_from">
										<input type="text"  class="MM_from_vac" title="ccc" dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
									</spring:bind>
									</td>
		
							  		<td nowrap class="formReq" >
										<abc:i18n property="commons.caption.to"/>
										<fmt:message key="commons.caption.to"/>
									</td>
									<td  class="formBodControl" >
									<spring:bind path="loginUsersRequests.vac_period_to">
										<input type="text"  class="MM_to_vac" title="ccc"  dir="ltr"  name="${status.expression}" id="${status.expression}" value="${status.value}"/>
									</spring:bind>
									</td>
									
									<td nowrap class="formReq" >
										<abc:i18n property="requestsApproval.requestsApprovalForm.reqPeriod"/>
										<fmt:message key="requestsApproval.requestsApprovalForm.reqPeriod"/>
									</td>
									<td  class="formBodControl" >
										<select name="withoutSalPeriod" id="withoutSalPeriod" >
											<option value=""><fmt:message key="commons.caption.select" /></option>						
<!--											<option value="quar" ${status.value == "quar" ? ' selected':''}><fmt:message key="requestsApproval.caption.quarDay"/></option>-->
											<option value="half" ${status.value == "half" ? ' selected':''}><fmt:message key="requestsApproval.caption.halfDay"/></option>
											<option value="full" ${status.value == "full" ? ' selected':''}><fmt:message key="requestsApproval.errors.day"/></option>
										</select>
									</td>
									<!--
									<td nowrap class="formReq" >
										<abc:i18n property="requestsApproval.caption.leaveTime"/>
										<fmt:message key="requestsApproval.caption.leaveTime"/>
									</td>
									<td  class="formBodControl" >
										<select name="leaveTime" id="leaveTime" >
											<option value=""><fmt:message key="commons.caption.select" /></option>						
											<option value="start" ${status.value == "start" ? ' selected':''}><fmt:message key="requestsApproval.caption.leaveTimeStart"/></option>
											<option value="end" ${status.value == "end" ? ' selected':''}><fmt:message key="requestsApproval.caption.leaveTimeEnd"/></option>
										</select>
									</td>-->
									<td  class="formBodControl" >
										<abc:i18n property="requestsApproval.caption.reqPeriod"/>
										<fmt:message key="requestsApproval.caption.reqPeriod"/>
									</td>
									<td  class="formBodControl" >
										<input type="text"  name="withdraw" id="withdraw" onfocus="calculateDiff2()" value="${loginUsersRequests.withdrawDays}" />
									</td>													
								</tr>
								</c:otherwise>
								</c:choose>
														
								<tr id="specialVacationTypes" class="hideClass">
							  		<td nowrap class="formReq" >
										<abc:i18n property="requestsApproval.caption.vacationType"/>
										<fmt:message key="requestsApproval.caption.vacationType"/>
									</td>
									<td  class="formBodControl" >
										<select name="specialVacation" id="specialVacation" onchange="showVacationTypes()">
											<option value=""><fmt:message key="commons.caption.select" /></option>						
												<c:forEach items="${specialVacations}" var="vacation">
													<option value="${vacation.vacation}" ${vacation.vacation == loginUsersRequests.vacation.vacation?' selected' : ''} onclick="showVacationTypes()">${vacation.name}</option>
												</c:forEach>
										</select>
									</td>						
								</tr>
								
						
							</c:if>
							
							<c:if test="${loginUsersRequests.request_id.id==4}">
								<tr id="vacationDates" class="showClass">
							  		<td nowrap class="formReq" >
										<abc:i18n property="commons.caption.fromDate"/>
										<fmt:message key="commons.caption.fromDate"/>
									</td>
									<td  class="formBodControl" >
										<spring:bind path="loginUsersRequests.from_date">
											<input type="text" title="cc" class="calendar"  readonly="readonly" autocomplete="off" dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
										</spring:bind>
									</td>
		
							  		<td nowrap class="formReq" >
										<abc:i18n property="commons.caption.toDate"/>
										<fmt:message key="commons.caption.toDate"/>
									</td>
									<td  class="formBodControl">
										<spring:bind path="loginUsersRequests.to_date">
											<input type="text"  title="cc" class="calendar" readonly="readonly" autocomplete="off" dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
										</spring:bind>
									</td>
																
							  		<td nowrap class="formReq" >
										<abc:i18n property="requestsApproval.requestsApprovalForm.reqPeriod"/>
										<fmt:message key="requestsApproval.requestsApprovalForm.reqPeriod"/>
									</td>
									
									<c:if test="${loginUsersRequests!=null}">
									<td  class="formBodControl" >
										<input type="text"  name="withdrawDays" id="withdrawDays" value="${loginUsersRequests.withdrawDays}" onfocus="calculateDiff()"  />
									</td>
									</c:if>
									<c:if test="${loginUsersRequests==null}">
									<td  class="formBodControl" >
										<input type="text"  name="withdrawDays" id="withdrawDays" value="${withdrawDays}" onfocus="calculateDiff()"  />
									</td>
									</c:if>
								</tr>
															
								<tr id="annualVacationTypes" class="hideClass">
							  		<td nowrap class="formReq" >
										<abc:i18n property="requestsApproval.caption.vacationType"/>
										<fmt:message key="requestsApproval.caption.vacationType"/>
									</td>
									<td  class="formBodControl">
										<select name="annualVacation" id="annualVacation"  onchange="showVacationTypes()">
											<option value=""><fmt:message key="commons.caption.select" /></option>						
												<c:forEach items="${annualVacations}" var="vacation">
													<option value="${vacation.vacation}" ${vacation.vacation == loginUsersRequests.vacation.vacation?' selected' : ''} >${vacation.name}</option>
												</c:forEach>
										</select>
									</td>
																
									<td>
									</td>
									
									<td  class="formBodControl">
										<abc:i18n property="commons.button.getVacCredit"/><fmt:message key="commons.button.getVacCredit"/>
									</td>
									
									<td  class="formBodControl" >
										<input type="text" size="10"   readonly="readonly" onfocus="getBalanceVac()" name="vacCredit" id="vacCredit" value="${vacCredit}" />
									</td>															
								</tr>
								
								<tr id="specialVacationTypes" class="hideClass">
							  		<td nowrap class="formReq" >
										<abc:i18n property="requestsApproval.caption.vacationType"/>
										<fmt:message key="requestsApproval.caption.vacationType"/>
									</td>
									<td  class="formBodControl" >
										<select name="specialVacation" id="specialVacation" onchange="showVacationTypes()">
											<option value=""><fmt:message key="commons.caption.select" /></option>						
												<c:forEach items="${specialVacations}" var="vacation">
													<option value="${vacation.vacation}" ${vacation.vacation == loginUsersRequests.vacation.vacation?' selected' : ''} onclick="showVacationTypes()">${vacation.name}</option>
												</c:forEach>
										</select>
									</td>						
								</tr>
								<tr id="vacWithoutSal" class="hideClass">						
							  		<td nowrap class="formReq">
										<abc:i18n property="commons.caption.from"/>
										<fmt:message key="commons.caption.from"/>
									</td>
									<td  class="formBodControl" >
									<spring:bind path="loginUsersRequests.vac_period_from">
											<input type="text"  class="MM_from_vac" title="ccc" dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
									</spring:bind>	
									</td>
		
							  		<td nowrap class="formReq" >
										<abc:i18n property="commons.caption.to"/>
										<fmt:message key="commons.caption.to"/>
									</td>
									<td  class="formBodControl" >
									<spring:bind path="loginUsersRequests.vac_period_to">
											<input type="text"  class="MM_to_vac" title="ccc" dir="ltr"  name="${status.expression}" id="${status.expression}" value="${status.value}" />
									</spring:bind>
									</td>
									
									<td nowrap class="formReq" >
										<abc:i18n property="requestsApproval.requestsApprovalForm.reqPeriod"/>
										<fmt:message key="requestsApproval.requestsApprovalForm.reqPeriod"/>
									</td>
									<td  class="formBodControl" >
										<select name="withoutSalPeriod" id="withoutSalPeriod" >
											<option value=""><fmt:message key="commons.caption.select" /></option>						
<!--											<option value="quar" ${status.value == "quar" ? ' selected':''}><fmt:message key="requestsApproval.caption.quarDay"/></option>-->
											<option value="half" ${status.value == "half" ? ' selected':''}><fmt:message key="requestsApproval.caption.halfDay"/></option>
											<option value="full" ${status.value == "full" ? ' selected':''}><fmt:message key="requestsApproval.errors.day"/></option>
										</select>
									</td>
									<!--
									<td nowrap class="formReq" >
										<abc:i18n property="requestsApproval.caption.leaveTime"/>
										<fmt:message key="requestsApproval.caption.leaveTime"/>
									</td>
									<td  class="formBodControl" >
										<select name="leaveTime" id="leaveTime" >
											<option value=""><fmt:message key="commons.caption.select" /></option>						
											<option value="start" ${status.value == "start" ? ' selected':''}><fmt:message key="requestsApproval.caption.leaveTimeStart"/></option>
											<option value="end" ${status.value == "end" ? ' selected':''}><fmt:message key="requestsApproval.caption.leaveTimeEnd"/></option>
										</select>
									</td>
									-->
									<td  class="formBodControl" >
										<abc:i18n property="requestsApproval.caption.reqPeriod"/>
										<fmt:message key="requestsApproval.caption.reqPeriod"/>
									</td>
									<td  class="formBodControl" >
										<input type="text"  name="withdraw" id="withdraw" onfocus="calculateDiff2()" value="${loginUsersRequests.withdrawDays}" />
									</td>
									
								</tr>														
							</c:if>
						<tr id="errandDuration" class="hideClass">
								<td nowrap class="formReq"><abc:i18n
										property="commons.caption.from" /> <fmt:message
										key="commons.caption.from" /></td>
								<td class="formBodControl"><spring:bind
										path="loginUsersRequests.period_from">
										<input type="text" class="MM_from" title="ccc" dir="ltr"
											name="${status.expression}" id="${status.expression}"
											value="${status.value}" />
									</spring:bind></td>

								<td nowrap class="formReq"><abc:i18n
										property="commons.caption.to" /> <fmt:message
										key="commons.caption.to" /></td>
								<td class="formBodControl"><spring:bind
										path="loginUsersRequests.period_to">
										<input type="text" class="MM_to" title="ccc"
											autocomplete="off" dir="ltr" name="${status.expression}"
											id="${status.expression}" value="${status.value}" />
									</spring:bind></td>
							</tr>
							
						<tr id="leavePermission" class="hideClass">						
							<td nowrap class="formReq" >
								<abc:i18n property="requestsApproval.caption.leaveEffect"/>
								<fmt:message key="requestsApproval.caption.leaveEffect"/>
							</td>
							<td  class="formBodControl" >
								<spring:bind path="loginUsersRequests.leave_effect">
									<select name="${status.expression}" id="${status.expression}" >
										<option value=""><fmt:message key="commons.caption.select" /></option>						
										<!-- <option value="0" ${status.value == "0" ? ' selected':''}><fmt:message key="requestsApproval.caption.without"/></option> -->
										<option value="1" ${status.value == "1" ? ' selected':''}><fmt:message key="requestsApproval.caption.delay"/></option>
										<option value="2" ${status.value == "2" ? ' selected':''}><fmt:message key="requestsApproval.caption.early"/></option>
									</select>
								</spring:bind>
							</td>														
						</tr>				
						
						</c:if>
						<c:if test="${loginUsersRequests.request_id.id==5 || loginUsersRequests.request_id.id==3}">
						<tr id="errandDuration" class="showClass">
								<td nowrap class="formReq"><abc:i18n
										property="commons.caption.from" /> <fmt:message
										key="commons.caption.from" /></td>
								<td class="formBodControl"><spring:bind
										path="loginUsersRequests.period_from">
										<input type="text" class="MM_from" title="ccc" dir="ltr"
											name="${status.expression}" id="${status.expression}"
											value="${status.value}" />
									</spring:bind></td>

								<td nowrap class="formReq"><abc:i18n
										property="commons.caption.to" /> <fmt:message
										key="commons.caption.to" /></td>
								<td class="formBodControl"><spring:bind
										path="loginUsersRequests.period_to">
										<input type="text" class="MM_to" title="ccc"
											autocomplete="off" dir="ltr" name="${status.expression}"
											id="${status.expression}" value="${status.value}" />
									</spring:bind></td>
							</tr>
							</c:if>
						<c:if test="${loginUsersRequests.request_id.id==3}">
							<tr id="leavePermission" class="showClass">						
								<td nowrap class="formReq" >
									<abc:i18n property="requestsApproval.caption.leaveEffect"/>
									<fmt:message key="requestsApproval.caption.leaveEffect"/>
								</td>
								<td  class="formBodControl" >
									<spring:bind path="loginUsersRequests.leave_effect">
										<select name="${status.expression}" id="${status.expression}" >
											<option value=""><fmt:message key="commons.caption.select" /></option>						
											<!-- <option value="0" ${status.value == "0" ? ' selected':''}><fmt:message key="requestsApproval.caption.without"/></option> -->
											<option value="1" ${status.value == "1" ? ' selected':''}><fmt:message key="requestsApproval.caption.delay"/></option>
											<option value="2" ${status.value == "2" ? ' selected':''}><fmt:message key="requestsApproval.caption.early"/></option>
										</select>
									</spring:bind>
								</td>														
							</tr>
							
							<tr id="vacationDates" class="hideClass">
						  		<td nowrap class="formReq" >
									<abc:i18n property="commons.caption.fromDate"/>
									<fmt:message key="commons.caption.fromDate"/>
								</td>
								<td  class="formBodControl" >
									<spring:bind path="loginUsersRequests.from_date">
										<input type="text" title="cc" class="calendar"  readonly="readonly" autocomplete="off" dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
									</spring:bind>
								</td>
	
						  		<td nowrap class="formReq" >
									<abc:i18n property="commons.caption.toDate"/>
									<fmt:message key="commons.caption.toDate"/>
								</td>
								<td  class="formBodControl">
									<spring:bind path="loginUsersRequests.to_date">
										<input type="text"  title="cc" class="calendar" readonly="readonly" autocomplete="off" dir="ltr" name="${status.expression}" id="${status.expression}" value="${status.value}" />
									</spring:bind>
								</td>
															
						  		<td nowrap class="formReq" >
									<abc:i18n property="requestsApproval.requestsApprovalForm.reqPeriod"/>
									<fmt:message key="requestsApproval.requestsApprovalForm.reqPeriod"/>
								</td>
								
								<c:if test="${loginUsersRequests!=null}">
								<td  class="formBodControl" >
									<input type="text"  name="withdrawDays" id="withdrawDays" value="${loginUsersRequests.withdrawDays}" onfocus="calculateDiff()"  />
								</td>
								</c:if>
								<c:if test="${loginUsersRequests==null}">
								<td  class="formBodControl" >
									<input type="text"  name="withdrawDays" id="withdrawDays" value="${withdrawDays}" onfocus="calculateDiff()"  />
								</td>
								</c:if>
							</tr>
							
							<tr id="annualVacationTypes" class="hideClass">
						  		<td nowrap class="formReq" >
									<abc:i18n property="requestsApproval.caption.vacationType"/>
									<fmt:message key="requestsApproval.caption.vacationType"/>
								</td>
								<td  class="formBodControl">
									<select name="annualVacation" id="annualVacation" onchange="showVacationTypes()">
										<option value=""><fmt:message key="commons.caption.select" /></option>						
											<c:forEach items="${annualVacations}" var="vacation">
												<option value="${vacation.vacation}" ${vacation.vacation == loginUsersRequests.vacation.vacation?' selected' : ''} >${vacation.name}</option>
											</c:forEach>
									</select>
								</td>
															
								<td>
								</td>
								
								<td  class="formBodControl">
									<abc:i18n property="commons.button.getVacCredit"/><fmt:message key="commons.button.getVacCredit"/>
								</td>
								
								<td  class="formBodControl" >
									<input type="text"  size="10"  readonly="readonly" onfocus="getBalanceVac()" name="vacCredit" id="vacCredit" value="${vacCredit}" />
								</td>															
							</tr>
							
							<tr id="specialVacationTypes" class="hideClass">
						  		<td nowrap class="formReq" >
									<abc:i18n property="requestsApproval.caption.vacationType"/>
									<fmt:message key="requestsApproval.caption.vacationType"/>
								</td>
								<td  class="formBodControl" >
									<select name="specialVacation" id="specialVacation" onchange="showVacationTypes()">
										<option value=""><fmt:message key="commons.caption.select" /></option>						
											<c:forEach items="${specialVacations}" var="vacation">
												<option value="${vacation.vacation}" ${vacation.vacation == loginUsersRequests.vacation.vacation?' selected' : ''} onclick="showVacationTypes()">${vacation.name}</option>
											</c:forEach>
									</select>
								</td>						
							</tr>
													
						</c:if>				
						</c:if>	

																	
						<tr>
						<c:choose>
							<c:when test="${loginUsersRequests.request_id.id==4}">
								<td nowrap class="formReq" >
									<abc:i18n property="commons.caption.notes"/>
									<fmt:message key="commons.caption.notes"/>
								</td>
							</c:when>
							<c:otherwise>
							<%if(settings.getNotesValidation().booleanValue()==false){ %>
								<td  class="formBodControl" >
								<abc:i18n property="commons.caption.notes"/>
									<fmt:message key="commons.caption.notes"/>
								</td>
								<%} else{ %>
								<td  class="formReq" >
								<abc:i18n property="commons.caption.notes"/>
									<fmt:message key="commons.caption.notes"/>
								</td>
								<%} %>
							</c:otherwise>
						</c:choose>
							<td  class="formBodControl" >
								<spring:bind path="loginUsersRequests.notes">
								<textarea cols="35" name="${status.expression}" id="${status.expression}" value="${status.value}">${loginUsersRequests.notes}</textarea>
<!--									<input type="text" size="50"  name="${status.expression}" id="${status.expression}" value="${status.value}" maxlength="255" />-->
								</spring:bind>
							</td>
						</tr>
						
						<tr id="btn">
							<td colspan=4>
								<abc:i18n property="commons.button.submit"/><input type="submit" name="save" value="<fmt:message key="commons.button.submit"/>" class="button"/>&nbsp;&nbsp;&nbsp;
								<abc:i18n property="commons.button.cancel"/><input type="reset" name="cancel" value="<fmt:message key="commons.button.cancel"/>" class="button" >
							</td>
						</tr>					
					</table>
			</form>
		</td>
	</tr>
</table>

<script language="JavaScript" type="text/javascript" src="/Orders/web/common/js/wz_tooltip.js"></script>

<%@ include file="/web/common/includes/footer.jsp" %>
</body>
</html>
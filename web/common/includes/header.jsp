<%@page import="com._4s_.common.model.Settings"%>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.List"%>

<%@page import="com._4s_.security.model.SecurityApplication"%>
<html dir="<fmt:message key="commons.language.dir"/>"
	xml:lang="<fmt:message key="commons.language.code"/>"
	lang="<fmt:message key="commons.language.code"/>">
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<meta http-equiv=“Content-Security-Policy” content=“default-src;‘self’;
	gap://ready
	file://* *; style-src ‘self’ ‘unsafe-inline’; script-src ‘self’ ‘unsafe-inline’ ‘unsafe-eval’; charset=UTF-8” />
<!-- ‘self’ gap://ready file://* *; style-src ‘self’ ‘unsafe-inline’; script-src ‘self’ ‘unsafe-inline’ ‘unsafe-eval’ -->
<title>Requests System</title>
<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/css/all.css">
<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/css/autoComplete.css">
<link type="text/css" href="/Requests/web/common/css/main.css"
	rel="stylesheet" media="screen" />
<link rel="stylesheet" type="text/css" media="print, handheld"
	href="/Requests/web/common/css/print.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="/Requests/web/common/css/onlyScreen.css">
<!--  link type="text/css"  rel="stylesheet" href="/Requests/web/common/css/liveSearch.css">
<link type="text/css"  rel="stylesheet" href="/Requests/web/common/css/liveSearch2.css">-->
<script language="JavaScript" type="text/javascript"
	src="/Requests/web/common/js/toolTipContent.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/Requests/web/common/js/popup.js"></SCRIPT>
<script type="text/javascript"
	src="/Requests/web/common/js/myLiveSearch.js"></script>
<script type="text/javascript" src="/Requests/dwr/interface/qry.js"></script>
<script type="text/javascript" src="/Requests/dwr/engine.js"></script>
<script type="text/javascript" src="/Requests/dwr/util.js"></script>
<script type="text/javascript"
	src="/Requests/web/common/js/Tokenizer.js"></script>
<script type="text/javascript"
	src="/Requests/web/common/js/sorttable.js"></script>
<script type="text/javascript" src="/Requests/web/common/js/extAjax.js"></script>

<script type="text/javascript" src="/Requests/web/common/js/initMap.js"></script>



<!--  ///////////////////////// Islamic Calendar -->
<script type="text/javascript"
	src="/Requests/web/common/js/jquery.min.js"></script>

<script type="text/javascript"
	src="/Requests/web/common/js/jquery.calendars.js"></script>
<script type="text/javascript"
	src="/Requests/web/common/js/jquery.calendars.plus.js"></script>
<link rel="stylesheet" type="text/css"
	href="/Requests/web/common/css/jquery.calendars.picker.css" />
<script type="text/javascript"
	src="/Requests/web/common/js/jquery.calendars.picker.js"></script>
<script type="text/javascript"
	src="/Requests/web/common/js/jquery.calendars.islamic.js"></script>
<script type="text/javascript"
	src="/Requests/web/common/js/calendarDate.js"></script>

<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/timepicker/ui-lightness/jquery-ui-1.8.21.custom.css" />
<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/timepicker/jquery-ui-timepicker-addon.css" />
<script type="text/javascript"
	src="/Requests/web/common/timepicker/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="/Requests/web/common/timepicker/jquery-ui.js"></script>
<script type="text/javascript"
	src="/Requests/web/common/timepicker/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript"
	src="/Requests/web/common/timepicker/jquery-ui-sliderAccess.js"></script>

<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/clockpicker/clockpicker.css" />
<link type="text/css" rel="stylesheet"
	href="/Requests/web/common/clockpicker/standalone.css" />
<script type="text/javascript"
	src="/Requests/web/common/clockpicker/clockpicker.js"></script>


<script type="text/javascript">
			
			$(function(){
				//alert('I hate tomatoes.');
				console.log($( ".MM_from_d" ).datetimepicker);
		          // $('.timepac').datetimepicker();

          $('.timepaconly').datetimepicker({
		        		ampm: false,
		        		hourMin: 8,
		        		hourMax: 18
		        	});
		

			$('.timepac').datetimepicker({
				ampm: true,
				showTimepicker:false
				
			});

			

			$( ".MM_from" ).datetimepicker({
				onSelect: function( selectedDate ) {
					alert('I hate tomatoes.');
					console.log('I hate tomatoes.');
				$('input.MM_to[title="'+this.title+'"]').datepicker( "option", "minDate", selectedDate );}});
			
		    $( ".MM_to" ).datetimepicker({		
			onSelect: function( selectedDate ){
			$('input.MM_from[title="'+this.title+'"]').datepicker( "option", "maxDate", selectedDate );}});

		    
		   	$( ".MM_from_vac" ).datetimepicker({
				onSelect: function( selectedDate ) {			
				$('input.MM_to[title="'+this.title+'"]').datepicker( "option", "minDate", selectedDate );}});
			
		    $( ".MM_to_vac" ).datetimepicker({		
			onSelect: function( selectedDate ){
			$('input.MM_from[title="'+this.title+'"]').datepicker( "option", "maxDate", selectedDate );}});
		    
			$( ".MM_from_d" ).datepicker({
				onSelect:function( selectedDate ) {	
				$('input.MM_from_d[title="'+this.title+'"]').datepicker( "option", "minDate", selectedDate );}});
			
		    $( ".MM_to_d" ).datepicker({		
			onSelect: function( selectedDate ){
			$('input.MM_to_d[title="'+this.title+'"]').datepicker( "option", "maxDate", selectedDate );}});
		    

			$('.timepac').datepicker( "option", "dateFormat", "yy/mm/dd" );
			$('.MM_from').datepicker( "option", "dateFormat", "yy/mm/dd" );
			$('.MM_to').datepicker( "option", "dateFormat", "yy/mm/dd" );
			$('.MM_from_d').datetimepicker( "option", "dateFormat", "yy/mm/dd" );
			$('.MM_to_d').datetimepicker( "option", "dateFormat", "yy/mm/dd" );
			$('.MM_from_vac').datetimepicker( "option", "dateFormat", "yy/mm/dd" );
			$('.MM_to_vac').datetimepicker( "option", "dateFormat", "yy/mm/dd" );
			

 
				});

			
			
		</script>
<script type="text/javascript">
     $(document).ready(function(){
	  CalendarChecktype();
	});
  </script>
<!-- link calendar files  -->
<!-- script language="JavaScript" src="/Requests/web/common/tigra_calendar/calendar_eu.js"></script>-->
<!-- <script language="JavaScript" src="/Requests/web/common/tigra_calendar/calendar_db.js"></script>-->

<SCRIPT LANGUAGE="JavaScript"
	SRC="/Requests/web/common/AnyTimePicker/anytime.js"></SCRIPT>
<link rel="stylesheet"
	href="/Requests/web/common/tigra_calendar/calendar.css">
<link rel="stylesheet"
	href="/Requests/web/common/AnyTimePicker/anytime.css">

<%
	String applicationName = (String) session.getAttribute("appName");
	//System.out.println("applicationName " + applicationName);

	Settings settings = (Settings) request.getSession().getAttribute(
			"settings");
	System.out.println("settings in header" + settings);
	List activeApplications = (List) request.getSession().getAttribute(
			"activeApplications");
	String locale = ((String) request.getSession().getAttribute(
			"locale"));
	String align = "";
	if (locale != null && locale.equalsIgnoreCase("en")) {
		align = "left";
%>
<script language="JavaScript"
	src="/Requests/web/common/tigra_calendar/calendar_us.js"></script>
<link href="/Requests/web/common/css/tables_en.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="/Requests/web/common/css/ddsmoothmenu_en.css" />
<script type="text/javascript"
	src="/Requests/web/common/js/ddsmoothmenu_en.js"></script>
<link href="/Requests/web/common/css/vertical_menu_en.css"
	rel="stylesheet" type="text/css" />
<link href="/Requests/web/common/css/tab_en.css" rel="stylesheet"
	type="text/css" />
<%
	} else {
		align = "right";
%>

<script language="JavaScript"
	src="/Requests/web/common/tigra_calendar/calendar_ar.js"></script>
<link href="/Requests/web/common/css/tables_ar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="/Requests/web/common/css/ddsmoothmenu_ar.css" />
<script type="text/javascript"
	src="/Requests/web/common/js/ddsmoothmenu_ar.js"></script>
<link href="/Requests/web/common/css/vertical_menu_ar.css"
	rel="stylesheet" type="text/css" />
<link href="/Requests/web/common/css/tab_ar.css" rel="stylesheet"
	type="text/css" />

<link rel="stylesheet" type="text/css"
	href="/Requests/web/common/css/epoch_styles.css" />
<script type="text/javascript"
	src="/Requests/web/common/js/epoch_classes.js"></script>

<%
	}
%>
<script type="text/java">
	/*	
	 function noBack(){
	 window.history.forward(1)
	 alert("back is not allowed");
	 }
	
	 noBack(); 
	
	 window.onload=noBack; 
	
	 window.onpageshow=function(evt){if(evt.persisted)noBack()} 
	
	 window.onunload=function(){
	 void(0)
	 }

	 */
</script>
<style type="text/css">
/*Menu Links*/

/*NOTE: anything not specified for the #(menu id's) a selector and its pseudo classes
may be inherited in some browsers from other 'a' element styles (if any) on the page*/
#menu1 a {
	color: black;
	background-color: white;
	text-decoration: none;
	text-indent: 1ex;
}

#menu1 a:active {
	color: black;
	text-decoration: none;
}

#menu1 a:hover {
	color: black;
	background-color: #FFFF99
}

#menu1 a:visited {
	color: black;
	text-decoration: none;
}

#menu2 a {
	color: white;
	background-color: #2175bc;
	text-decoration: none;
	text-indent: 1ex;
}

#menu2 a:active {
	color: white;
	text-decoration: none;
}

#menu2 a:visited {
	color: white;
	text-decoration: none;
}

#menu2 a:hover {
	color: white;
	background-color: #2586d7
}

#menu3 a { /*Menu3 Links*/
	color: black;
	background-color: white;
	text-decoration: none;
	text-indent: 1ex;
}

#menu3 a:hover {
	color: black;
	background-color: #FFFF99;
}

#menu3 a:active {
	color: black;
	text-decoration: none;
}

#menu3 a:visited {
	color: black;
	text-decoration: none;
}

/*End Menu Links*/
</style>
<script src="/Requests/web/common/js/mmenu.js" type="text/javascript"></script>
<script type="text/javascript">

/***********************************************
* Omni Slide Menu script -  John Davenport Scheuer: http://home.comcast.net/~jscheuer1/
* very freely adapted from Dynamic-FX Slide-In Menu (v 6.5) script- by maXimus
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full original source code
***********************************************/

</script>
<script>
/***********************************************
* Omni Slide Menu script -  John Davenport Scheuer
* very freely adapted from Dynamic-FX Slide-In Menu (v 6.5) script- by maXimus
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full original source code
* as first mentioned in http://www.dynamicdrive.com/forums
* username:jscheuer1
***********************************************/

//One global variable to set, use true if you want the menus to reinit when the user changes text size (recommended):
resizereinit=true;



menu[2] = {  // REQUIRED!!  This menu explicitly declares all available options even if they are the same as the defaults
id:'menu2', //use unique quoted id (quoted) REQUIRED!!
/////////////////////////////////////
///////////// no quotes for these properties (numbers represent pixels unless otherwise noted): ////////////
/////////////////////////////////////
user_defined_stylesheet:false, //if true, prevents script from generating stylesheet for this menu
user_defined_markup:false, //if true, prevents script from generating markup for this menu
design_mode:false,  //if true, generates a report of the script generated/intended styles and markup (as a design aid)
menutop:160,     // initial top offset - except for top menu, where it is meaningless
menuleft:'50%',    // initial left offset - only for top menu, as pixels (can be a quoted percentage - ex: '50%')
keepinview:80,   // Use false (for not static) - OR - true or numeric top offset when page scrolls
menuspeed:20,    // Speed of menu sliding smaller is faster (interval of milliseconds)
menupause:500,   // How long menu stays out when mouse leaves it (in milliseconds)
d_colspan:3,     // Available columns in menu body as integer
allowtransparent:false, // true to allow page to show through menu if other bg's are transparent or border has gaps
barwidth:20,     // bar (the vertical cell) width
wrapbar:true,    // extend and wrap bar below menu for a more solid look (default false) - will revert to false for top menu
hdingwidth:210,  // heading - non linked horizontal cells width
hdingheight:25,  // heading - non linked horizontal cells height
hdingindent:1,   // heading - non linked horizontal cells text-indent represents ex units (@8 pixels decimals allowed)
linkheight:20,   // linked horizontal cells height
linktopad:3,     // linked horizontal cells top padding
borderwidth:0,   // inner border-width used for this menu
/////////////////////////// quote these properties: /////////////////////
bordercolor:'#90bade', // inner border color
borderstyle:'solid',    // inner border style (solid, dashed, inset, etc.)
outbrdwidth:'0ex 0ex 0ex 0ex', // outer border-width used for this menu (top right bottom left)
outbrdcolor:'lightblue',  // outer border color
outbrdstyle:'solid',     // outer border style (solid, dashed, inset, etc.)
barcolor:'white',        // bar (the vertical cell) text color
barbgcolor:'black',   // bar (the vertical cell) background color #1c5a80
barfontweight:'bold',    // bar (the vertical cell) font weight
baralign:'center',       // bar (the vertical cell) right left or center text alignment
menufont:'verdana',      // menu font
fontsize:'90%',          // express as percentage with the % sign
hdingcolor:'white',      // heading - non linked horizontal cells text color
hdingbgcolor:'black',  // heading - non linked horizontal cells background color
hdingfontweight:'bold',  // heading - non linked horizontal cells font weight
hdingvalign:'middle',    // heading - non linked horizontal cells vertical align (top, middle or center)
hdingtxtalign:'<%=align%>',    // heading - non linked horizontal cells right left or center text alignment
linktxtalign:'<%=align%>',     // linked horizontal cells right left or center text alignment
linktarget:'',           // default link target, leave blank for same window (other choices: _new, _top, or a window or frame name)
kviewtype:'fixed',       // Type of keepinview - 'fixed' utilizes fixed positioning where available, 'absolute' fluidly follows page scroll
menupos:'top',         // set side that menu slides in from (right or left or top)
bartext:'MAIN MENU',       // bar text (the vertical cell) use text or img tag
///////////////////////////
menuItems:[
//[name, link, target, colspan, endrow?] - leave 'link' and 'target' blank to make a header
	["<b><fmt:message key="commons.caption.multiItemGroups" /></b>"],
	["<b><fmt:message key="commons.button.editUserPreferences" /></b>", "/Requests/security/changeUserApplication.html", "_new"],
	["<b><fmt:message key="commons.caption.date" /></b>", "javascript:createWindow('<c:url value="/common/commonAdminDateConverter.html"/>')", "_new"],
	["<b><fmt:message key="commons.caption.applications" /></b>"]
<%System.out.println("application " + activeApplications);
			SecurityApplication securityApplication = null;
			if (activeApplications != null) {
				for (int i = 0; i < activeApplications.size(); i++) {
					securityApplication = (SecurityApplication) activeApplications
							.get(i);
					System.out.println("active app "
							+ securityApplication.getName());%>
,[
<%if (securityApplication.getName().equals("ASSETS")) {%>"<b><fmt:message key='assets.caption.applicationName' /></b>"<%} else if (securityApplication.getName().equals(
							"ADMINISTRATION")) {%>"<b><fmt:message key='administration.caption.applicationName' /></b>"<%} else if (securityApplication.getName().equals(
							"requestsApproval")) {%>"<b><fmt:message key='requestsApproval.caption.applicationName' /></b>"<%} else if (securityApplication.getName()
							.equals("timesheet")) {
						if (settings.getIsTimesheetEnabled().equals(
								new Boolean(true))) {%>"<b><fmt:message key='timesheet.caption.applicationName' /></b>"<%}
					}else if (securityApplication.getName()
							.equals("attendance")) {
						if (settings.getWebAttendanceAppEnabled().equals(
								new Boolean(true))) {%>"<b><fmt:message key='attendance.caption.applicationName' /></b>"<%}
					}%>, "/Requests/common/changeApplication.html?application=<%=securityApplication.getName()%>", "_new"]
<%}
			}%>
]}; // REQUIRED!! do not edit or remove

//if((securityApplication.getName()!=null) || !securityApplication.getName().equals("")) {
make_menus();
</script>
</head>
<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0"
	marginwidth="0" marginheight="0" onload="liveSearchInit();">
	<_4s_:changeLocale
		value='<%=(String) request.getSession().getAttribute("locale")%>' />
	<input id="dateType" type="hidden" value="0">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<!-- Top Header Begin------------------------------------------------ -->
				<div class="formHeader">

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>

							<td height="100" colspan="7" valign="top" align="center"><img
								src="/Requests/web/common/images/erp_header.jpg" width="100%" /></td>
						</tr>
						<tr>
							<td class="appName">
								<%
									String applicationNameString = "";
									/* if (applicationName.equals("ASSETS")) {
										applicationNameString = "gl.caption.applicationName";
									 }else */
									if (applicationName != null
											&& applicationName.equals("ADMINISTRATION")) {
										applicationNameString = "administration.caption.applicationName";
									} else if (applicationName != null
											&& applicationName.equals("requestsApproval")) {
										applicationNameString = "requestsApproval.caption.applicationName";
									} else if (applicationName != null
											&& applicationName.equals("timesheet")) {
										applicationNameString = "timesheet.caption.applicationName";
									} else if (applicationName != null
											&& applicationName.equals("attendance")) {
										applicationNameString = "attendance.caption.applicationName";
									}
									session.setAttribute("applicationNameString", applicationNameString);
								%>
							
						</tr>
					</table>
					<!-- Top Header End------------------------------------------------ -->
				</div>
			</td>
		</tr>
		<tr>
			<td bgcolor="#ECF5FF" valign="top">
				<!-- Menu Begin------------------------------------------------ -->
				<div class="menu">
					<%
						System.out.println("applicationName in header.jsp "
								+ applicationName);
						if (applicationName != null
								&& applicationName.equals("ADMINISTRATION")) {
					%>
					<jsp:include page="/web/common/menu.jsp" flush="true" />
					<%
						}
						if (applicationName != null
								&& applicationName.equals("requestsApproval")) {
					%>
					<jsp:include page="/web/requestsApproval/menu.jsp" flush="true" />
					<%
						}
						if (applicationName != null && applicationName.equals("timesheet")) {
					%>
					<jsp:include page="/web/timesheet/menu.jsp" flush="true" />
					<%
						}
						if (applicationName != null && applicationName.equals("attendance")) {
					%>
					<jsp:include page="/web/attendance/menu.jsp" flush="true" />
					<%
						}
					%>

					<!-- REQUESTSAPPROVAL -->
					<!-- Menu End------------------------------------------------ -->
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<!-- Username section Begin------------------------------------------------ -->
				<div class="formHeader">
					<table width="100%" border="0" bgcolor="#91C8FF">
						<tr class="normalMediumWhite">
							<td width="50%"></td>
							<_4s_:dayOfWeek />
							<td align="right" nowrap="nowrap">&nbsp;<font
								class="normalSmallWhite"><fmt:message
										key="commons.caption.date" /> :</font>&nbsp;
							</td>
							<td align="right" nowrap="nowrap">&nbsp;<font
								class="normalSmallWhite"><fmt:message>${dayOfWeek}</fmt:message></font>&nbsp;
							</td>

							<td align="right" nowrap="nowrap"><font
								class="normalSmallWhite"><_4s_:formatMiladiDate_rtl
										value="${loginDate}" /> </font></td>
							<td></td>

							<td align="right" colspan="4" nowrap="nowrap">&nbsp;&nbsp;<font
								class="normalSmallWhite"><fmt:message
										key="commons.caption.username" />
									:${employee.firstName}&nbsp;</font>&nbsp;<a href=""></a></td>


							<td width="50%"></td>
						</tr>
					</table>
				</div>
			</td>
			<!-- Username section End------------------------------------------------ -->
		</tr>
		<tr>
			<!--td>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td width="15%" valign="top">
				<div id="ddblueblockmenu">

				<div class="menutitle">&nbsp;&nbsp;&nbsp;<b><fmt:message
					key="commons.caption.applications" /></b></div>
				<!%
					if (activeApplications != null) {
				%>
				<ul>
					<!%
						for (int i = 0; i < activeApplications.size(); i++) {
								SecurityApplication securityApplication = (SecurityApplication) activeApplications
										.get(i);
					%>
					<li><a
						href="/Requests/common/changeApplication.html?application=<!%=securityApplication.getName()%>">&nbsp;&nbsp;<b>
					<!%
						if (securityApplication.getName().equals("STORE")) {
					%> <fmt:message key="stores.caption.applicationName" /> <!%
 	} else if (securityApplication.getName().equals("GL")) {
 %> <fmt:message key="gl.caption.applicationName" /> <!%
 	} else if (securityApplication.getName().equals("ASSETS")) {
 %> <fmt:message key="assets.caption.applicationName" /> <!%
 	} else if (securityApplication.getName().equals(
 					"ADMINISTRATION")) {
 %> <fmt:message key="administration.caption.applicationName" /> <!%
 	} else if (securityApplication.getName().equals("PORTAL")) {
 %> <fmt:message key="portal.caption.applicationName" /> <!%
 	}

 			else if (applicationName.equals("HR")) {
 				applicationNameString = "hr.caption.applicationName";
 			}
 %></b></a></li>
					<!%
						}
					%>
				</ul>
				<!%
					}
				%>

				<div class="menutitle">&nbsp;&nbsp;&nbsp;<b><fmt:message
					key="commons.caption.multiItemGroups" /></b></div>
				<ul>
					<li><a
						href="/Requests/security/changeUserApplication.html">
					&nbsp;&nbsp;&nbsp;<b><fmt:message
						key="commons.button.editUserPreferences" /></b></a></li>
					<li><a
						href="javascript:createWindow('<c:url value="/common/commonAdminDateConverter.html"/>')">&nbsp;&nbsp;&nbsp;
					<b><fmt:message key="commons.caption.date" /></b></a></li>
				</ul>

				</div>
				</td>
				<td width="10px;">&nbsp;</td-->
			<td valign="top">
				<!-- Body Start------------------------------------------------ -->
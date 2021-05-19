<!--
  File Version Start - Do not remove this if you are modifying the file
  Build: 11.0.0
  File Version End

	(c) 2004 Business Objects, Inc.  All rights reserved.
	This code or any derivative work thereof may not be distributed without the express written
	permission of Business Objects.
-->
<%@ Language=JScript codepage=65001%>
<%
// *********************************************************************
// SERVER-SIDE STRING VARIABLES FOR LOCALIZING
// *********************************************************************
var L_LOG_ON = "Log On";
var L_ACCOUNT_INFO = "Account Information Not Recognized";
var L_PLS_CHK_APS = "Please check that the appropriate CMS name and authentication type are specified.";
var L_REENTER_USERNAME = "Re-enter your user name and password, and click Log On.";
var L_IF_YOU_ARE_UNSURE = "If you are unsure of your account information, contact your system administrator.";
var L_RETRIEVE_ERROR = "Unable to retrieve objects: ";
var vbCRLF = "\n";
// *********************************************************************
%>

<%

function GetSession(name)
{
	return Session.Contents(name);
}

function SetSession(name, value)
{
	Session.Contents(name) = value;
}

function RemoveSession(name)
{
	Session.Contents.Remove(name);
}

function GetCookie(cookies, name)
{
	var value = cookies(name);
	var retVal = "";
	if (value != undefined)
		retVal =/* URLDecode*/(value);
	return retVal;		
}
 
function PreventCaching()
{
	// prevent browser from caching the page
	var cache_date = new Date();
	cache_date.setFullYear( cache_date.getFullYear() - 1 );
	Response.ExpiresAbsolute = cache_date.getVarDate();
}

// FUNCTION WHICH WILL CREATE AN ABSOLUTE PATH SO OUR LINKS WILL WORK IN CGI
function GetLinkPath()
{
	var ret = "";
	var path_info = String(Request.ServerVariables.Item("PATH_INFO"));
	var regex = /[^\/]*\//g;
	var matchArr = path_info.match(regex);
	for(var i = 0; i < matchArr.length; ++i)
	{
		ret += matchArr[i];
	}

	return ret;
}

// Draw a button, with text buttontext, and link buttonlink (include "href=").
// buttonstate is a string with two possible values: "" for normal button
//                                                   "d" for disabled button
//
function DrawButton(imgpath, buttontext, buttonlink, buttonstate)
{
    Response.Write("<table border=0 cellpadding=0 cellspacing=0>");
    Response.Write("<tr valign=center>");
    Response.Write("<td><img src=\"" + imgpath + "images/buttonl" + buttonstate + ".gif\"></td>");
    Response.Write("<td class=\"clsButton" + buttonstate + "\" valign=middle nowrap background=\"" + imgpath + "images/buttonm" + buttonstate + ".gif\">");
    Response.Write("<div class=\"clsButton\">");
    if (buttonlink != "" && buttonstate != "d")
        Response.Write("<a " + buttonlink + ">");
    Response.Write(buttontext);
    if (buttonlink != "" && buttonstate != "d")
        Response.Write("</a>");
    Response.Write("</div></td>");
    Response.Write("<td><img src=\"" + imgpath + "images/buttonr" + buttonstate + ".gif\"></td>");
    Response.Write("</tr>");
    Response.Write("</table>");
}
// This function decodes the any string that's been encoded using URL encoding
function URLDecode(strString)
{
    var lsRegExp = /\+/g;
    return unescape(String(strString).replace(lsRegExp, " "));
}

function RebuildQueryString()
{	 
	var result = "";
	var count = Request.QueryString.Count;
	if (count > 0)
		result = "?";
	for (var i=1; i <= count; i++)
	{
		var key = Request.QueryString.Key(i);
		var value = Request.QueryString.Item(i);
		if (value != undefined && value.Count > 0)
			value = value.Item(value.Count);
		 
		if (i != 1)
			result += "&";			
		result += Server.UrlEncode( key ) + "=" + Server.UrlEncode( value );		
	}

	return result;
}

PreventCaching();

//var apsName = Request.Cookies("CMS_NAME");
var viewrptLogonCookies = Request.Cookies.Item("VIEWRPTLOGONCOOKIE");
var apsName, lastUsr, lastAut;
apsName = GetCookie(viewrptLogonCookies, "cmsname");
lastUsr = GetCookie(viewrptLogonCookies, "apsuser");
lastAut = GetCookie(viewrptLogonCookies, "apsauthtype");

var actionString = "./viewrpt.asp";
if ( Request.QueryString.Count > 0)
	actionString +=  RebuildQueryString();
			
%>
<HTML>
<TITLE>
Viewer Logon
</TITLE>
<HEAD>
<meta http-equiv=content-type content="text/html; charset=utf-8"> 
<LINK rel="stylesheet" type="text/css" href="css/default.css">
</HEAD>
<BODY onLoad="usernameFocus();">

<script language=JavaScript>

function logon()
{
  document.forms["logonform"].submit();
}

//FUNCTION WHICH SETS FOCUS TO THE USERNAME TEXT BOX
function usernameFocus() {
  document.logonform.apsuser.focus();
  document.logonform.apsuser.select();
}
</script>
<form name="logonform" method="post" action="<%= actionString %>">
<table class="list" width="100%" border="0" cellpadding="3" cellspacing="0" style="background-color:white">
<tr>
  <td class="list">
    <span class="listSelected">BusinessObjects Enterprise Log On</span><br>
    <hr size=1>
  </td>
  <%
	// Form Data
	var count = Request.Form.Count;
	for (var i=1; i<=count; i++)
	{
		var key = Request.Form.Key(i);
		if (key != "apsuser" && key != "apspassword" && key != "cmsname" && key != "apsauthtype")
		{
			var value = Request.Form.Item(i);			
			if (value != undefined && value.Count > 0)
				value = Request.Form.Item(i).Item(value.Count)		
			%>
			<td class="list"><input type=hidden name="<%= Server.HTMLEncode(key)%>" value="<%= Server.HTMLEncode(value)%>"></td>
			<%
		}
	}
  %>
</tr>
</table>
<br>
<TABLE class="list" cellpadding="3" cellspacing="0" border="0" width="100%">
<TR>
  <TD class="list" valign="top">
    <TABLE class="list" cellpadding="3" cellspacing="0" border="0">
    <TR>
      <TD class="list"> CMS Name: </TD>
      <TD class="list"><input type=text size=30 name="cmsname" value="<% = Server.HTMLEncode( apsName ) %>"></TD>
    </TR>
    <TR>
      <TD class="list"> User Name: </TD>
      <TD class="list"><input type=text size=30 name="apsuser" value="<% = Server.HTMLEncode( lastUsr ) %>"></TD>
    </TR>
    <TR>
      <TD class="list"> Password: </TD>
      <TD class="list"><input type=password size=30 name="apspassword" value=""></TD>
    </TR>
    <TR>
      <TD class="list"> Authentication: </TD>
      <TD class="list"><select size=1 name="apsauthtype" style="width:200px">
<%
try
{
  // INSTANTIATE SESSION MANAGER OBJECT
  var sm = Server.CreateObject("CrystalEnterprise.SessionMgr");

  var authProgIds = sm.InstalledAuthProgIds;
  var count = authProgIds.Count;
  for(var i = 1; i <= count; ++i)
  {
    var ptypename = authProgIds.Item(i);
    var pname = sm.NameFromProgId(ptypename);
    Response.Write("<option value='" + ptypename + "'");
    if(lastAut == ptypename)
      Response.Write(" selected");
    Response.Write(">" + pname + vbCRLF);
  }
}
catch(e)
{
  Response.Write(L_RETRIEVE_ERROR + e.description);
}
%>
        </select>
      </TD>
    </TR>
    <TR>
      <td class="list">&nbsp;</td>
      <td class="list">
<% DrawButton(GetLinkPath(), L_LOG_ON, "href=\"javascript:logon();\"", ""); %>
      </td>
    </TR>
    </TABLE>
  </TD>
  <TD valign=top>
  <!-- Error Message Here -->
<%

var strErrMessage = GetSession("VIEWRPTLOGONERRMSG");
if (strErrMessage != undefined)
{
  RemoveSession("VIEWRPTLOGONERRMSG"); 
 
  Response.Write("    <span class='list' style='color:red'><b>" + L_ACCOUNT_INFO + "</b><ul style='margin-top:0;margin-bottom:0;'>" + vbCRLF);
  if( strErrMessage != "" )
    Response.Write("    <li><b>" + strErrMessage + "</b>" + vbCRLF);
  Response.Write("    <li>" + L_PLS_CHK_APS + vbCRLF);
  Response.Write("    <li>" + L_REENTER_USERNAME + vbCRLF);
  Response.Write("    <li>" + L_IF_YOU_ARE_UNSURE + vbCRLF);
  Response.Write("    </ul></span>" + vbCRLF); 
}
else
  Response.Write("    &nbsp;" + vbCRLF);
%>
  </TD>
</TR>
</TABLE>
</FORM>
</BODY>
</HTML>
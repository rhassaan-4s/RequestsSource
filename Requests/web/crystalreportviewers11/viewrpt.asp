<!--
  File Version Start - Do not remove this if you are modifying the file
  Build: 11.0.0
  File Version End

	(c) 2004 Business Objects, Inc.  All rights reserved.
	This code or any derivative work thereof may not be distributed without the express written
	permission of Business Objects.
-->
<%@ Language=JScript codepage=65001 %>
<%
// *********************************************************************
// SERVER-SIDE STRING VARIABLES FOR LOCALIZING
// *********************************************************************
var L_SESSION_EXPIRED = "Unable to retrieve Object.";
// *********************************************************************
%>


<%


function GetSession(name)
{
	return Session.Value(name);
}

function SetSession(name, value)
{
	Session.Value(name) = value;
}

function PreventCaching()
{
	// prevent browser from caching the page
	var cache_date = new Date();
	cache_date.setFullYear( cache_date.getFullYear() - 1 );
	Response.ExpiresAbsolute = cache_date.getVarDate();
}

PreventCaching();

try
{  
  var bridge;
  bridge = GetSession("RptSrcBridge");
  if (typeof(bridge) != "object")
  {
    bridge = Server.CreateObject( "CrystalReports.CRReportSourceBridge" );
    SetSession("RptSrcBridge", bridge);
  }
     
 bridge.ProcessHttpRequest();    
}
catch(e)
{
  Response.Write( L_SESSION_EXPIRED + "<BR>" );
  Response.Write( e.description );
}

%>

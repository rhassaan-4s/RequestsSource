<%@ Page Language="JScript" codepage=65001 %>
<script runat=server>

const C_ASPVIEWERPAGENAME = "viewrpt.aspx";

function getAspViewerPage()
{
	var viewer = C_ASPVIEWERPAGENAME;
	var path = ConfigurationSettings.AppSettings("path.dhtmlViewer");

	if ( typeof(path) != "string" )
		path = "";

	if (path.length == 0 || path.charAt(path.length-1) == "/")
		return ( path + viewer );
	else
		return ( path + "/" + viewer );
}

function getFromQueryString( keyName )
{
	return Context.Request.QueryString[keyName];
}

function getFromForm( keyName )
{
	return Context.Request.Form[keyName];
}

function getAllQueryString()
{
	var allKeys = Context.Request.QueryString.AllKeys;
	var valArr : String[];
	var result = "";

	for (var i=0; i < allKeys.Length; i++)
	{
		valArr = Context.Request.QueryString.GetValues(i);
		for (var j=0; j < valArr.Length; j++ )
		{
			if ((i != 0) || (j !=0))
				result += "&";
			result += Server.UrlEncode( allKeys[i] ) + "=" + Server.UrlEncode( valArr[j] );
		}
	}

	return result;
}

//
</script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<title></title>
	</head>
	<body onload="document.forms['Form1'].submit();">
		<form id="Form1" method="post" action="<%=getAspViewerPage()%>?<%=getAllQueryString()%>">
		<%
			// Form Data
			var allKeys = Context.Request.Form.Keys;
			for (var key in allKeys)
			{
			%>
			<input type='hidden' name="<%=Context.Server.HtmlEncode(key)%>" value="<%=Context.Server.HtmlEncode(getFromForm(key))%>">
			<%
			}
		%>
		</form>
	</body>
</html>

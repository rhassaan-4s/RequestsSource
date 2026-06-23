<%@ include file="/web/common/includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="-1">
</head>
<body id="ng_bitflux_org" onload="liveSearchInit()">

	<form:form style="margin:0px;" method="POST" modelAttribute="search"
		action="/Requests/common/sampleLiveSearch.html.html">
		<table>
			<tr>
				<td>employee name</td>
				<td><abc:autocomplete inputId="livesearch" inputName="q"
						table="common_employee" firstKey="commons.caption.name"
						secondKey="commons.caption.id" firstParam="firstName"
						secondParam="id" 
						bindById="id"/></td>
			</tr>

			<tr>
				<td>address</td>
				<td><input type="text" id="address" name="address" value="" />
				</td>
			</tr>

			<tr>
				<td>city</td>
				<td><abc:autocomplete inputId="city" inputName="city"
						table="common_city" firstKey="commons.caption.city"
						secondKey="commons.caption.id" firstParam="description"
						secondParam="id" 
						bindById="id"/></td>
			</tr>

			<tr>
				<td>birthdate</td>
				<td><input type="text" id="birthdate" name="birthdate" value="" />
				</td>
			</tr>

			<tr>
				<td>country</td>
				<td><abc:autocomplete inputId="country" inputName="country"
						table="common_country" firstKey="commons.caption.country"
						secondKey="commons.caption.id" firstParam="description"
						secondParam="id"
						bindById="id"/></td>
			</tr>

		</table>
	</form:form>
</body>
</html>
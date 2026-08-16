<%@ page language="java" isErrorPage="true" %>
<%@ include file="/web/common/includes/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <meta charset="UTF-8">
    <title><fmt:message key="errorPage.title"/></title>
    <link rel="stylesheet" type="text/css" media="all" 
        href="<c:url value="/styles/default.css"/>" /> 
        
        <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            margin: 0;
            padding: 0;
        }

        .error-container {
            width: 600px;
            margin: 100px auto;
            padding: 40px;
            background: white;
            border-radius: 8px;
            text-align: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.15);
        }

        .error-title {
            font-size: 26px;
            margin-bottom: 20px;
        }

        .error-message {
            font-size: 16px;
            font-weight: bold;
            color: darkred;
            margin-bottom: 30px;
            border: 2px solid #ddd;
        }
    </style>
        
</head>

<%@ page contentType="text/html; charset=UTF-8" %>




<body>

<div class="error-container">
	 <div class="error-title">
	 	<img src="${pageContext.request.contextPath}/web/common/images/logonew.gif" alt="" />
	 </div>
    <div class="error-title">
        Something went wrong, Try to re-login or contact the technical support team.
    </div>

    <div class="error-message">
        ${errorMessage}
    </div>

    <button type="button" onclick="history.back();">
        Go Back
    </button>

</div>

</body>
</html>
